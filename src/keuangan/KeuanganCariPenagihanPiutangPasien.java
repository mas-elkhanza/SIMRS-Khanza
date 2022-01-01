package keuangan;
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPegawai;
import simrskhanza.DlgCariCaraBayar;

public class KeuanganCariPenagihanPiutangPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private DlgAkunPenagihanPiutang akuntagih=new DlgAkunPenagihanPiutang(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String nopenagihan="",tanggal="",status="",penjamin="",bagianpenagihan="",transfer="";
    private double nilaitagihan=0,totaltagihan=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public KeuanganCariPenagihanPiutangPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tgl.Penagihan","Tempo","Tgl.Tempo","No.Penagihan","Bagian Penagihan","Menyetujui","Penjamin","Catatan","Transfer Ke","Status Penagihan"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 10; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(78);
            }else if(i==1){
                column.setPreferredWidth(41);
            }else if(i==2){
                column.setPreferredWidth(65);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(190);
            }else if(i==5){
                column.setPreferredWidth(190);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(250);
            }else if(i==9){
                column.setPreferredWidth(95);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoPenagihan.setDocument(new batasInput((byte)20).getKata(NoPenagihan));
        kdpenjab.setDocument(new batasInput((byte)5).getKata(kdpenjab));
        KdPeg.setDocument(new batasInput((byte)20).getKata(KdPeg));
        NamaBank.setDocument(new batasInput((byte)15).getKata(NamaBank));
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){                   
                    KdPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NmPeg.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                }            
                KdPeg.requestFocus();
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
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString()); 
                    Perusahaan.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),3).toString());
                    AlamatAsuransi.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),4).toString());
                    NoTelp.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),5).toString());
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        akuntagih.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akuntagih.getTable().getSelectedRow()!= -1){
                    KdAkun.setText(akuntagih.getTable().getValueAt(akuntagih.getTable().getSelectedRow(),1).toString());
                    NamaBank.setText(akuntagih.getTable().getValueAt(akuntagih.getTable().getSelectedRow(),3).toString());
                    AtasNama.setText(akuntagih.getTable().getValueAt(akuntagih.getTable().getSelectedRow(),4).toString());
                    NoRek.setText(akuntagih.getTable().getValueAt(akuntagih.getTable().getSelectedRow(),5).toString());
                }      
                KdAkun.requestFocus();
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
        
        akuntagih.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    akuntagih.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm();
        ChkAccor.setSelected(false);
        isPhoto();
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppHapus = new javax.swing.JMenuItem();
        ppDisetujui = new javax.swing.JMenuItem();
        ppTidakDisetujui = new javax.swing.JMenuItem();
        ppVerifikasi = new javax.swing.JMenuItem();
        Perusahaan = new widget.TextBox();
        AlamatAsuransi = new widget.TextBox();
        NoTelp = new widget.TextBox();
        KdAkun = new widget.TextBox();
        AtasNama = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label15 = new widget.Label();
        NoPenagihan = new widget.TextBox();
        label11 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdpenjab = new widget.TextBox();
        KdPeg = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        NmPeg = new widget.TextBox();
        btnSuplier = new widget.Button();
        btnPetugas = new widget.Button();
        label12 = new widget.Label();
        Tanggal2 = new widget.Tanggal();
        btnBarang = new widget.Button();
        NoRek = new widget.TextBox();
        NamaBank = new widget.TextBox();
        label17 = new widget.Label();
        label14 = new widget.Label();
        Status = new widget.ComboBox();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        btnAmbilPhoto = new widget.Button();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(50, 50, 50));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Penagihan Piutang");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        ppDisetujui.setBackground(new java.awt.Color(255, 255, 254));
        ppDisetujui.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDisetujui.setForeground(new java.awt.Color(50, 50, 50));
        ppDisetujui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDisetujui.setText("Sudah Dibayar");
        ppDisetujui.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDisetujui.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDisetujui.setName("ppDisetujui"); // NOI18N
        ppDisetujui.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDisetujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDisetujuiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDisetujui);

        ppTidakDisetujui.setBackground(new java.awt.Color(255, 255, 254));
        ppTidakDisetujui.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTidakDisetujui.setForeground(new java.awt.Color(50, 50, 50));
        ppTidakDisetujui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTidakDisetujui.setText("Proses Penagihan");
        ppTidakDisetujui.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTidakDisetujui.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTidakDisetujui.setName("ppTidakDisetujui"); // NOI18N
        ppTidakDisetujui.setPreferredSize(new java.awt.Dimension(200, 25));
        ppTidakDisetujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTidakDisetujuiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTidakDisetujui);

        ppVerifikasi.setBackground(new java.awt.Color(255, 255, 254));
        ppVerifikasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppVerifikasi.setForeground(new java.awt.Color(50, 50, 50));
        ppVerifikasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppVerifikasi.setText("Validasi Tagihan");
        ppVerifikasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppVerifikasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppVerifikasi.setName("ppVerifikasi"); // NOI18N
        ppVerifikasi.setPreferredSize(new java.awt.Dimension(200, 25));
        ppVerifikasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppVerifikasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppVerifikasi);

        Perusahaan.setHighlighter(null);
        Perusahaan.setName("Perusahaan"); // NOI18N

        AlamatAsuransi.setHighlighter(null);
        AlamatAsuransi.setName("AlamatAsuransi"); // NOI18N

        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N

        KdAkun.setEditable(false);
        KdAkun.setName("KdAkun"); // NOI18N
        KdAkun.setPreferredSize(new java.awt.Dimension(60, 23));
        KdAkun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdAkunKeyPressed(evt);
            }
        });

        AtasNama.setEditable(false);
        AtasNama.setName("AtasNama"); // NOI18N
        AtasNama.setPreferredSize(new java.awt.Dimension(170, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Penagihan Piutang Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setToolTipText("Silahkan klik pada nomor tagihan untuk verifikasi pilihan");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 125));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(null);

        label15.setText("No.Penagihan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label15);
        label15.setBounds(0, 10, 92, 23);

        NoPenagihan.setName("NoPenagihan"); // NOI18N
        NoPenagihan.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPenagihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPenagihanKeyPressed(evt);
            }
        });
        FormInput.add(NoPenagihan);
        NoPenagihan.setBounds(95, 10, 170, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(0, 40, 92, 23);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        FormInput.add(Tanggal1);
        Tanggal1.setBounds(95, 40, 90, 23);

        label16.setText("Penjamin :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(326, 10, 110, 23);

        label13.setText("Bagian Penagihan :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(326, 40, 110, 23);

        kdpenjab.setEditable(false);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(kdpenjab);
        kdpenjab.setBounds(439, 10, 90, 23);

        KdPeg.setEditable(false);
        KdPeg.setName("KdPeg"); // NOI18N
        KdPeg.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPeg);
        KdPeg.setBounds(439, 40, 90, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmpenjab);
        nmpenjab.setBounds(531, 10, 200, 23);

        NmPeg.setEditable(false);
        NmPeg.setName("NmPeg"); // NOI18N
        NmPeg.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPeg);
        NmPeg.setBounds(531, 40, 200, 23);

        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSuplier.setMnemonic('1');
        btnSuplier.setToolTipText("Alt+1");
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        FormInput.add(btnSuplier);
        btnSuplier.setBounds(734, 10, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(734, 40, 28, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(189, 40, 27, 23);

        Tanggal2.setDisplayFormat("dd-MM-yyyy");
        Tanggal2.setName("Tanggal2"); // NOI18N
        Tanggal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal2KeyPressed(evt);
            }
        });
        FormInput.add(Tanggal2);
        Tanggal2.setBounds(220, 40, 90, 23);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('4');
        btnBarang.setToolTipText("Alt+4");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        FormInput.add(btnBarang);
        btnBarang.setBounds(734, 70, 28, 23);

        NoRek.setEditable(false);
        NoRek.setName("NoRek"); // NOI18N
        NoRek.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NoRek);
        NoRek.setBounds(611, 70, 120, 23);

        NamaBank.setEditable(false);
        NamaBank.setName("NamaBank"); // NOI18N
        NamaBank.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(NamaBank);
        NamaBank.setBounds(439, 70, 170, 23);

        label17.setText("Transfer :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label17);
        label17.setBounds(326, 70, 110, 23);

        label14.setText("Status :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 92, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Proses Penagihan", "Sudah Dibayar" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(40, 23));
        FormInput.add(Status);
        Status.setBounds(95, 70, 170, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
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
        panelisi1.add(BtnAll);

        label9.setText("Total Tagihan :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(93, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(140, 23));
        panelisi1.add(LTotal);

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

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Penagihan/Bayar : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbilPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbilPhoto.setMnemonic('U');
        btnAmbilPhoto.setText("Ambil");
        btnAmbilPhoto.setToolTipText("Alt+U");
        btnAmbilPhoto.setName("btnAmbilPhoto"); // NOI18N
        btnAmbilPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbilPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(btnAmbilPhoto);

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        pegawai.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,NamaBank);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        Valid.pindah(evt,NoPenagihan,kdpenjab);
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        akuntagih.isCek();
        akuntagih.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        akuntagih.setLocationRelativeTo(internalFrame1);
        akuntagih.setAlwaysOnTop(false);
        akuntagih.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void NoPenagihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPenagihanKeyPressed
        Valid.pindah(evt, BtnKeluar, kdpenjab);
    }//GEN-LAST:event_NoPenagihanKeyPressed

    private void Tanggal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal2KeyPressed

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
        NoPenagihan.setText("");
        NamaBank.setText("");
        NoRek.setText("");
        AtasNama.setText("");
        KdAkun.setText("");
        kdpenjab.setText("");
        nmpenjab.setText("");
        Perusahaan.setText("");
        AlamatAsuransi.setText("");
        NoTelp.setText("");
        KdPeg.setText("");
        NmPeg.setText("");
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
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("truncate table temporary");
            
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penagihan Piutang Pasien"); 
            }
            Sequel.menyimpan("temporary","'0','Total Tagihan :','','','','','','','"+Valid.SetAngka(totaltagihan)+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penagihan Piutang Pasien"); 
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptPenagihanPiutangPasien.jasper","report","::[ Data Penagihan Piutang Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
    if(tbDokter.getSelectedRow()> -1){
        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
            Valid.textKosong(TCari,"pilihan data");
        }else{
            Sequel.queryu("delete from penagihan_piutang where no_tagihan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString().trim());
            tampil();
        }  
    }  
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppDisetujuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDisetujuiActionPerformed
        if(tbDokter.getSelectedRow()> -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"pilihan data");
            }else{
                Sequel.queryu("update penagihan_piutang set status='Sudah Dibayar' where no_tagihan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString().trim());
                tampil();
            }
        }
            
    }//GEN-LAST:event_ppDisetujuiActionPerformed

    private void ppTidakDisetujuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTidakDisetujuiActionPerformed
        if(tbDokter.getSelectedRow()> -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"pilihan data");
            }else{
                Sequel.queryu("update penagihan_piutang set status='Proses Penagihan' where no_tagihan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString().trim());
                tampil();
            }
        }
    }//GEN-LAST:event_ppTidakDisetujuiActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KdAkunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdAkunKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdAkunKeyPressed

    private void ppVerifikasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppVerifikasiActionPerformed
        if(tbDokter.getSelectedRow()> -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"pilihan data");
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                KeuanganPiutangBelumLunas form=new KeuanganPiutangBelumLunas(null,false);
                form.isCek();
                form.tampiltagihan(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString().trim());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppVerifikasiActionPerformed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Penagihan");
            }else{
                isPhoto();
                panggilPhoto();
            }
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Faktur Penerimaan...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPhotoActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Penagihan");
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.meghapus("bukti_penagihan_piutang", "no_tagihan",tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                Sequel.menyimpan2("bukti_penagihan_piutang","?,''",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()});
                Valid.panggilUrl("penagihanpiutang/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&notagihan="+tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_btnAmbilPhotoActionPerformed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tbDokter.getSelectedRow()!= -1){  
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                isPhoto();
                panggilPhoto();
            }else{
                ChkAccor.setSelected(false);
                isPhoto();
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganCariPenagihanPiutangPasien dialog = new KeuanganCariPenagihanPiutangPasien(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatAsuransi;
    private widget.TextBox AtasNama;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox KdAkun;
    private widget.TextBox KdPeg;
    private widget.Label LTotal;
    private widget.editorpane LoadHTML;
    private widget.TextBox NamaBank;
    private widget.TextBox NmPeg;
    private widget.TextBox NoPenagihan;
    private widget.TextBox NoRek;
    private widget.TextBox NoTelp;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Perusahaan;
    private widget.ScrollPane Scroll4;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private widget.Button btnAmbilPhoto;
    private widget.Button btnBarang;
    private widget.Button btnPetugas;
    private widget.Button btnSuplier;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpenjab;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label9;
    private widget.TextBox nmpenjab;
    private widget.panelisi panelisi1;
    private javax.swing.JMenuItem ppDisetujui;
    private javax.swing.JMenuItem ppHapus;
    private javax.swing.JMenuItem ppTidakDisetujui;
    private javax.swing.JMenuItem ppVerifikasi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
         Valid.tabelKosong(tabMode);
         try{
             nopenagihan="";status="";penjamin="";bagianpenagihan="";transfer="";
             if(!NoPenagihan.getText().trim().equals("")){
                 nopenagihan=" and penagihan_piutang.no_tagihan like '%"+NoPenagihan.getText()+"%' ";
             }
             
             tanggal=" penagihan_piutang.tanggal between '"+Valid.SetTgl(Tanggal1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tanggal2.getSelectedItem()+"")+"' ";
             if(!Status.getSelectedItem().toString().equals("Semua")){
                 status=" and penagihan_piutang.status like '%"+Status.getSelectedItem().toString()+"%' ";
             }
             
             if(!nmpenjab.getText().trim().equals("")){
                 penjamin=" and concat(penagihan_piutang.kd_pj,penjab.nama_perusahaan) like '%"+kdpenjab.getText()+Perusahaan.getText()+"%' ";
             }
             
             if(!NmPeg.getText().trim().equals("")){
                 bagianpenagihan=" and concat(penagihan_piutang.nip,bagianpenagihan.nama) like '%"+KdPeg.getText()+NmPeg.getText()+"%' ";
             }
             
             if(!NamaBank.getText().trim().equals("")){
                 transfer=" and concat(akun_penagihan_piutang.nama_bank,akun_penagihan_piutang.no_rek) like '%"+NamaBank.getText()+NoRek.getText()+"%' ";
             }
             
             ps=koneksi.prepareStatement(
                     "select penagihan_piutang.no_tagihan, penagihan_piutang.tanggal, penagihan_piutang.tanggaltempo, penagihan_piutang.tempo,"+
                     "penagihan_piutang.nip,bagianpenagihan.nama as bagianpenagihan,penagihan_piutang.nip_menyetujui,menyetujui.nama as menyetujui,"+
                     "penagihan_piutang.kd_pj,penjab.nama_perusahaan,penagihan_piutang.catatan,penagihan_piutang.kd_rek,akun_penagihan_piutang.nama_bank,"+
                     "akun_penagihan_piutang.no_rek,penagihan_piutang.status "+
                     "from penagihan_piutang inner join pegawai as bagianpenagihan on bagianpenagihan.nik=penagihan_piutang.nip "+
                     "inner join pegawai as menyetujui on menyetujui.nik=penagihan_piutang.nip_menyetujui "+
                     "inner join penjab on penagihan_piutang.kd_pj=penjab.kd_pj "+
                     "inner join akun_penagihan_piutang on akun_penagihan_piutang.kd_rek=penagihan_piutang.kd_rek where "+
                     tanggal+status+nopenagihan+penjamin+bagianpenagihan+transfer+" and penagihan_piutang.no_tagihan like ? or "+
                     tanggal+status+nopenagihan+penjamin+bagianpenagihan+transfer+" and bagianpenagihan.nama like ? or "+
                     tanggal+status+nopenagihan+penjamin+bagianpenagihan+transfer+" and menyetujui.nama like ? or "+
                     tanggal+status+nopenagihan+penjamin+bagianpenagihan+transfer+" and penjab.nama_perusahaan like ? or "+
                     tanggal+status+nopenagihan+penjamin+bagianpenagihan+transfer+" and akun_penagihan_piutang.nama_bank like ? or "+
                     tanggal+status+nopenagihan+penjamin+bagianpenagihan+transfer+" and akun_penagihan_piutang.no_rek like ? "+
                     "order by penagihan_piutang.tanggal");
             try {
                ps.setString(1,"%"+TCari.getText()+"%");
                ps.setString(2,"%"+TCari.getText()+"%");
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                ps.setString(5,"%"+TCari.getText()+"%");
                ps.setString(6,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                totaltagihan=0;
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("tanggal"),rs.getString("tempo"),rs.getString("tanggaltempo"),rs.getString("no_tagihan"),rs.getString("nip")+" "+rs.getString("bagianpenagihan"),
                        rs.getString("nip_menyetujui")+" "+rs.getString("menyetujui"),rs.getString("kd_pj")+" "+rs.getString("nama_perusahaan"),rs.getString("catatan"),
                        rs.getString("nama_bank")+" No.Rek. "+rs.getString("no_rek"),rs.getString("status")
                    });
                    tabMode.addRow(new Object[]{
                        "","","Tgl.Piutang","NIP","Asal Perusahaan","No.Rawat/No.Tagihan","No.Peserta","Piutang","No.Rm & Nama Pasien","Status"
                    });  
                    nilaitagihan=0;
                    ps2=koneksi.prepareStatement(
                            "select piutang_pasien.tgl_piutang,pasien.nip,perusahaan_pasien.nama_perusahaan,piutang_pasien.no_rkm_medis,pasien.nm_pasien,"+
                            "pasien.no_peserta,detail_penagihan_piutang.sisapiutang,detail_penagihan_piutang.no_rawat,reg_periksa.status_lanjut "+
                            "from detail_penagihan_piutang inner join piutang_pasien on piutang_pasien.no_rawat=detail_penagihan_piutang.no_rawat "+
                            "inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat "+
                            "inner join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien "+
                            "where detail_penagihan_piutang.no_tagihan=? order by piutang_pasien.tgl_piutang");
                    try {
                        ps2.setString(1,rs.getString("no_tagihan"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "","",rs2.getString("tgl_piutang"),rs2.getString("nip"),rs2.getString("nama_perusahaan"),rs2.getString("no_rawat"),
                                rs2.getString("no_peserta"),Valid.SetAngka(rs2.getDouble("sisapiutang")),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien"),rs2.getString("status_lanjut")
                            }); 
                            nilaitagihan=nilaitagihan+rs2.getDouble("sisapiutang");
                            totaltagihan=totaltagihan+rs2.getDouble("sisapiutang");
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
                    tabMode.addRow(new Object[]{
                        "","","","Nilai Tagihan :","","","",Valid.SetAngka(nilaitagihan),"",""
                    }); 
                }
                LTotal.setText(Valid.SetAngka(totaltagihan));
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

    public void emptTeks() {
        TCari.setText("");
        NoPenagihan.setText("");
        NamaBank.setText("");
        NoRek.setText("");
        AtasNama.setText("");
        KdAkun.setText("");
        kdpenjab.setText("");
        nmpenjab.setText("");
        Perusahaan.setText("");
        AlamatAsuransi.setText("");
        NoTelp.setText("");
        KdPeg.setText("");
        NmPeg.setText("");
        TCari.requestFocus();        
    }
    
    public void isCek(){
        TCari.requestFocus();
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }    
        ppVerifikasi.setEnabled(akses.getbayar_piutang());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,126));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame1.getWidth()-300,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select photo from bukti_penagihan_piutang where no_tagihan=?");
                try {
                    ps.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penagihanpiutang/"+rs.getString("photo")+"' alt='photo' width='"+(internalFrame1.getWidth()-330)+"' height='"+(internalFrame1.getHeight()-315)+"'/></center></body></html>");
                        }  
                    }else{
                        LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
}

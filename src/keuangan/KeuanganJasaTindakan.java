package keuangan;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPegawai;
import simrskhanza.DlgCariCaraBayar;

public class KeuanganJasaTindakan extends javax.swing.JDialog {
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPegawai dokter=new DlgCariPegawai(null,false);
    private DlgCariCaraBayar carabayar=new DlgCariCaraBayar(null,false);
    private int i=0,z=0;
    private StringBuilder htmlContent;
    private String pilihan="";
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private double subjasasarana=0,subjasamedis=0,subjasamenejemen=0,subbhp=0,subtotal=0,
                   ttljasasarana=0,ttljasamedis=0,ttljasamenejemen=0,ttlbhp=0,ttltotal=0,
                   jasasarana=0,jasamedis=0,jasamenejemen=0,bhp=0,total=0;
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public KeuanganJasaTindakan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
                
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                KdDokter.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {dokter.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        carabayar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carabayar.getTable().getSelectedRow()!= -1){
                    KdCaraBayar.setText(carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),1).toString());
                    NmCaraBayar.setText(carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),2).toString());
                }     
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {carabayar.onCari();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        carabayar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    carabayar.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
     
        ChkInput.setSelected(false);
        isForm();
        
        LoadHTML.setEditable(true);
        LoadHTML2.setEditable(true);
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc);
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
        KdDokter = new widget.TextBox();
        KdCaraBayar = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        chkRalan = new widget.CekBox();
        chkRanap = new widget.CekBox();
        chkOperasi = new widget.CekBox();
        chkLaborat = new widget.CekBox();
        chkRadiologi = new widget.CekBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        NmDokter = new widget.TextBox();
        btnDokter = new widget.Button();
        label19 = new widget.Label();
        NmCaraBayar = new widget.TextBox();
        BtnCaraBayarRalanDokter = new widget.Button();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        CmbJam2 = new widget.ComboBox();
        CmbMenit2 = new widget.ComboBox();
        CmbDetik2 = new widget.ComboBox();
        cmbStatus = new widget.ComboBox();
        jLabel12 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        scrollPane2 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        scrollPane3 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(70, 23));

        KdCaraBayar.setEditable(false);
        KdCaraBayar.setName("KdCaraBayar"); // NOI18N
        KdCaraBayar.setPreferredSize(new java.awt.Dimension(50, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Jasa Tindakan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        chkRalan.setSelected(true);
        chkRalan.setText("Ralan");
        chkRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRalan.setName("chkRalan"); // NOI18N
        chkRalan.setOpaque(false);
        chkRalan.setPreferredSize(new java.awt.Dimension(85, 30));
        chkRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRalanActionPerformed(evt);
            }
        });
        panelisi1.add(chkRalan);

        chkRanap.setSelected(true);
        chkRanap.setText("Ranap");
        chkRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRanap.setName("chkRanap"); // NOI18N
        chkRanap.setOpaque(false);
        chkRanap.setPreferredSize(new java.awt.Dimension(85, 30));
        chkRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRanapActionPerformed(evt);
            }
        });
        panelisi1.add(chkRanap);

        chkOperasi.setSelected(true);
        chkOperasi.setText("Operasi");
        chkOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkOperasi.setName("chkOperasi"); // NOI18N
        chkOperasi.setOpaque(false);
        chkOperasi.setPreferredSize(new java.awt.Dimension(85, 30));
        chkOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOperasiActionPerformed(evt);
            }
        });
        panelisi1.add(chkOperasi);

        chkLaborat.setSelected(true);
        chkLaborat.setText("Laboratorium");
        chkLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLaborat.setName("chkLaborat"); // NOI18N
        chkLaborat.setOpaque(false);
        chkLaborat.setPreferredSize(new java.awt.Dimension(95, 30));
        chkLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLaboratActionPerformed(evt);
            }
        });
        panelisi1.add(chkLaborat);

        chkRadiologi.setSelected(true);
        chkRadiologi.setText("Radiologi");
        chkRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRadiologi.setName("chkRadiologi"); // NOI18N
        chkRadiologi.setOpaque(false);
        chkRadiologi.setPreferredSize(new java.awt.Dimension(85, 30));
        chkRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRadiologiActionPerformed(evt);
            }
        });
        panelisi1.add(chkRadiologi);

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

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 96));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 44));
        FormInput.setLayout(null);

        label11.setText("Pendaftaran :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label11);
        label11.setBounds(0, 10, 80, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        FormInput.add(Tgl1);
        Tgl1.setBounds(84, 10, 90, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        FormInput.add(label18);
        label18.setBounds(379, 10, 58, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        FormInput.add(Tgl2);
        Tgl2.setBounds(441, 10, 90, 23);

        label17.setText("Dr/Pr :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label17);
        label17.setBounds(243, 40, 45, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(230, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(292, 40, 171, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(467, 40, 28, 23);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label19);
        label19.setBounds(0, 40, 80, 23);

        NmCaraBayar.setEditable(false);
        NmCaraBayar.setName("NmCaraBayar"); // NOI18N
        NmCaraBayar.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmCaraBayar);
        NmCaraBayar.setBounds(84, 40, 120, 23);

        BtnCaraBayarRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRalanDokter.setMnemonic('3');
        BtnCaraBayarRalanDokter.setToolTipText("Alt+3");
        BtnCaraBayarRalanDokter.setName("BtnCaraBayarRalanDokter"); // NOI18N
        BtnCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRalanDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnCaraBayarRalanDokter);
        BtnCaraBayarRalanDokter.setBounds(208, 40, 28, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(CmbJam);
        CmbJam.setBounds(179, 10, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(CmbMenit);
        CmbMenit.setBounds(246, 10, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(CmbDetik);
        CmbDetik.setBounds(313, 10, 62, 23);

        CmbJam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam2.setSelectedIndex(23);
        CmbJam2.setName("CmbJam2"); // NOI18N
        CmbJam2.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(CmbJam2);
        CmbJam2.setBounds(536, 10, 62, 23);

        CmbMenit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit2.setSelectedIndex(59);
        CmbMenit2.setName("CmbMenit2"); // NOI18N
        CmbMenit2.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(CmbMenit2);
        CmbMenit2.setBounds(603, 10, 62, 23);

        CmbDetik2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik2.setSelectedIndex(59);
        CmbDetik2.setName("CmbDetik2"); // NOI18N
        CmbDetik2.setPreferredSize(new java.awt.Dimension(62, 23));
        FormInput.add(CmbDetik2);
        CmbDetik2.setBounds(670, 10, 62, 23);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Piutang Belum Lunas", "Piutang Sudah Lunas", "Sudah Bayar Non Piutang", "Belum Terclosing Kasir" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(115, 23));
        FormInput.add(cmbStatus);
        cmbStatus.setBounds(557, 40, 175, 23);

        jLabel12.setText("Status :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(jLabel12);
        jLabel12.setBounds(503, 40, 50, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

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

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        scrollPane2.setViewportView(LoadHTML);

        TabRawat.addTab("Laporan 1", scrollPane2);

        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        scrollPane3.setViewportView(LoadHTML2);

        TabRawat.addTab("Laporan 2", scrollPane3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(TabRawat.getSelectedIndex()==0){
            try {            
                File g = new File("jasatindakan.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                    ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f = new File("JasaTindakanPasien.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"jasatindakan.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>JASA TINDAKAN PASIEN<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } 
        }else if(TabRawat.getSelectedIndex()==1){
            try {            
                File g = new File("jasatindakan.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                    ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f = new File("JasaTindakanPasien.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML2.getText().replaceAll("<head>","<head><link href=\"jasatindakan.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>JASA TINDAKAN PASIEN<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } 
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        //TCari.setText("");
        KdDokter.setText("");
        NmDokter.setText("");
        KdCaraBayar.setText("");
        NmCaraBayar.setText("");
        cmbStatus.setSelectedIndex(0);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, KdDokter, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    prosesCari();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, KdDokter, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        htmlContent = new StringBuilder();
        htmlContent.append(
            "<tr class='isi'>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                 "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
            "</tr>"
        );
        LoadHTML.setText(
            "<html>"+
              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
               htmlContent.toString()+
              "</table>"+
            "</html>"
        );
        Tgl1.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,KdDokter);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void chkRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRalanActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRalanActionPerformed

    private void chkRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRadiologiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRadiologiActionPerformed

    private void chkLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLaboratActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkLaboratActionPerformed

    private void chkOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOperasiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkOperasiActionPerformed

    private void chkRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRanapActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRanapActionPerformed

    private void BtnCaraBayarRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRalanDokterActionPerformed
        carabayar.isCek();
        carabayar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carabayar.setLocationRelativeTo(internalFrame1);
        carabayar.setAlwaysOnTop(false);
        carabayar.setVisible(true);
    }//GEN-LAST:event_BtnCaraBayarRalanDokterActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        prosesCari();
    }//GEN-LAST:event_TabRawatMouseClicked

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganJasaTindakan dialog = new KeuanganJasaTindakan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCaraBayarRalanDokter;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkInput;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbDetik2;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbJam2;
    private widget.ComboBox CmbMenit;
    private widget.ComboBox CmbMenit2;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.TextBox KdCaraBayar;
    private widget.TextBox KdDokter;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NmCaraBayar;
    private widget.TextBox NmDokter;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnDokter;
    private widget.CekBox chkLaborat;
    private widget.CekBox chkOperasi;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    // End of variables declaration//GEN-END:variables

    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,95));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }

    private void prosesCari() {
        if(TabRawat.getSelectedIndex()==0){
            if(cmbStatus.getSelectedItem().equals("Semua")){
                prosesCariSemua();
            }else if(cmbStatus.getSelectedItem().equals("Piutang Belum Lunas")){
                prosesCariPiutangBelumLunas();
            }else if(cmbStatus.getSelectedItem().equals("Piutang Sudah Lunas")){
                prosesCariPiutangSudahLunas();
            }else if(cmbStatus.getSelectedItem().equals("Sudah Bayar Non Piutang")){
                prosesCariSudahBayarNonPiutang();
            }else if(cmbStatus.getSelectedItem().equals("Belum Terclosing Kasir")){
                prosesCariBelumTerclosing();
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(cmbStatus.getSelectedItem().equals("Semua")){
                prosesCariSemua2();
            }else if(cmbStatus.getSelectedItem().equals("Piutang Belum Lunas")){
                prosesCariPiutangBelumLunas2();
            }else if(cmbStatus.getSelectedItem().equals("Piutang Sudah Lunas")){
                prosesCariPiutangSudahLunas2();
            }else if(cmbStatus.getSelectedItem().equals("Sudah Bayar Non Piutang")){
                prosesCariSudahBayarNonPiutang2();
            }else if(cmbStatus.getSelectedItem().equals("Belum Terclosing Kasir")){
                prosesCariBelumTerclosing2();
            }
        }
    }

    private void prosesCariSemua() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa where concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "where reg_periksa.kd_poli=? and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }   
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariPiutangBelumLunas() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' "+
                                "and reg_periksa.kd_poli=? and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and "+
                        "reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariPiutangSudahLunas() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' "+
                                "and reg_periksa.kd_poli=? and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and "+
                        "reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariSudahBayarNonPiutang() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "where reg_periksa.kd_poli=? and reg_periksa.status_bayar='Sudah Bayar' "+
                                "and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) "+
                                "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_bayar='Sudah Bayar' "+
                        "and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and "+
                        "reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariBelumTerclosing() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa where reg_periksa.status_bayar='Belum Bayar' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "where reg_periksa.status_lanjut='Ralan' and reg_periksa.kd_poli=? "+
                                "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.status_bayar='Belum Bayar' and reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_bayar='Belum Bayar' and "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_bayar='Belum Bayar' "+
                        "and reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_bayar='Belum Bayar' and "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Valid.SetAngka(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Valid.SetAngka(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void prosesCariSemua2() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa where concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "where reg_periksa.kd_poli=? and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }   
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML2.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariPiutangBelumLunas2() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' "+
                                "and reg_periksa.kd_poli=? and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and "+
                        "reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML2.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariPiutangSudahLunas2() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' "+
                                "and reg_periksa.kd_poli=? and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and "+
                        "reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML2.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariSudahBayarNonPiutang2() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "where reg_periksa.kd_poli=? and reg_periksa.status_bayar='Sudah Bayar' "+
                                "and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) "+
                                "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_bayar='Sudah Bayar' "+
                        "and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and "+
                        "reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML2.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void prosesCariBelumTerclosing2() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>No.Rawat</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pasien</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Masuk</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Tgl.Keluar</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Sarana</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Medis</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Jasa Menejemen</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Paket Obat/BHP</td>"+
                     "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Total</td>"+
                "</tr>"
            );
            
            ttljasasarana=0;ttljasamedis=0;ttljasamenejemen=0;ttlbhp=0;ttltotal=0;
            if(chkRalan.isSelected()==true){
                ps=koneksi.prepareStatement(
                        "select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik where poliklinik.kd_poli in "+
                        "(select reg_periksa.kd_poli from reg_periksa where reg_periksa.status_bayar='Belum Bayar' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "group by reg_periksa.kd_poli) "+
                        " order by poliklinik.nm_poli");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ "+rs.getString("nm_poli").toUpperCase()+"</td>"+
                            "</tr>"
                        );
                        subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                        ps2=koneksi.prepareStatement(
                                "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "where reg_periksa.status_bayar='Belum Bayar' and reg_periksa.kd_poli=? "+
                                "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                                (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                                "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                        try {
                            ps2.setString(1,rs.getString("kd_poli"));
                            ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                                jasasarana=Sequel.cariIsiAngka("select sum(rawat_jl_dr.material)+sum(rawat_jl_dr.kso) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_pr.material)+sum(rawat_jl_pr.kso) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                           Sequel.cariIsiAngka("select sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.kso) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                jasamedis=Sequel.cariIsiAngka("select sum(rawat_jl_dr.tarif_tindakandr) from rawat_jl_dr where rawat_jl_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_dr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_pr.tarif_tindakanpr) from rawat_jl_pr where rawat_jl_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_pr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakandr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"))+
                                          Sequel.cariIsiAngka("select sum(rawat_jl_drpr.tarif_tindakanpr) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_jl_drpr.nip='"+KdDokter.getText()+"' "),rs2.getString("no_rawat"));
                                jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_jl_dr.menejemen) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_pr.menejemen) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                              Sequel.cariIsiAngka("select sum(rawat_jl_drpr.menejemen) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                bhp=Sequel.cariIsiAngka("select sum(rawat_jl_dr.bhp) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_pr.bhp) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select sum(rawat_jl_drpr.bhp) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));
                                total=Sequel.cariIsiAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat=? ",rs2.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat=? ",rs2.getString("no_rawat"));

                                subjasasarana=subjasasarana+jasasarana;
                                ttljasasarana=ttljasasarana+jasasarana;
                                subjasamedis=subjasamedis+jasamedis;
                                ttljasamedis=ttljasamedis+jasamedis;
                                subjasamenejemen=subjasamenejemen+jasamenejemen;
                                ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                                subbhp=subbhp+bhp;
                                ttlbhp=ttlbhp+bhp;
                                subtotal=subtotal+total;
                                ttltotal=ttltotal+total;

                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rs2.getString("nm_pasien")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                                    "</tr>"
                                );
                            }
                            rs2.last();
                            if(rs2.getRow()>0){
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                         "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                                    "</tr>"
                                );
                            }
                        }catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }
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
            }
                
            if(chkRanap.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                        "reg_periksa.status_bayar='Belum Bayar' and reg_periksa.status_lanjut='Ranap' "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RAWAT INAP</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(rawat_inap_dr.material)+sum(rawat_inap_dr.kso) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_pr.material)+sum(rawat_inap_pr.kso) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.kso) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(rawat_inap_dr.tarif_tindakandr) from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_dr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_pr.tarif_tindakanpr) from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_pr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakandr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(rawat_inap_drpr.tarif_tindakanpr) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and rawat_inap_drpr.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(rawat_inap_dr.menejemen) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_pr.menejemen) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(rawat_inap_drpr.menejemen) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(rawat_inap_dr.bhp) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_pr.bhp) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(rawat_inap_drpr.bhp) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat"))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkOperasi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_bayar='Belum Bayar' and "+
                        "reg_periksa.no_rawat in (select operasi.no_rawat from operasi group by operasi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RUANG OK/VK</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaoperator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_umum) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_umum='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_dokter_pjanak) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_pjanak='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayadokter_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.dokter_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator1) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator1='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_operator3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_operator3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayainstrumen) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.instrumen='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawaat_resusitas) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawaat_resusitas='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaasisten_anestesi2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.asisten_anestesi2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayabidan3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.bidan3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biayaperawat_luar) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.perawat_luar='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop2) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop2='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop3) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop3='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop4) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop4='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(operasi.biaya_omloop5) from operasi where operasi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and operasi.omloop5='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(operasi.bagian_rs) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1)+sum(operasi.biayaoperator2)+"+
                                "sum(operasi.biayaoperator3)+sum(operasi.biayaasisten_operator1)+sum(operasi.biayaasisten_operator2)+"+
                                "sum(operasi.biayaasisten_operator3)+sum(operasi.biayainstrumen)+sum(operasi.biayadokter_anak)+"+
                                "sum(operasi.biayaperawaat_resusitas)+sum(operasi.biayadokter_anestesi)+sum(operasi.biayaasisten_anestesi)+"+
                                "sum(operasi.biayaasisten_anestesi2)+sum(operasi.biayabidan)+sum(operasi.biayabidan2)+sum(operasi.biayabidan3)+"+
                                "sum(operasi.biayaperawat_luar)+sum(operasi.biaya_omloop)+sum(operasi.biaya_omloop2)+sum(operasi.biaya_omloop3)+"+
                                "sum(operasi.biaya_omloop4)+sum(operasi.biaya_omloop5)+sum(operasi.biaya_dokter_pjanak)+"+
                                "sum(operasi.biaya_dokter_umum)+sum(operasi.biayaalat)+sum(operasi.biayasewaok)+sum(operasi.akomodasi)+"+
                                "sum(operasi.bagian_rs)+sum(operasi.biayasarpras) from operasi where operasi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;

                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;

                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;

                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>0</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkLaborat.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_bayar='Belum Bayar' "+
                        "and reg_periksa.no_rawat in (select periksa_lab.no_rawat from periksa_lab group by periksa_lab.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ LABORATORIUM</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_lab.bagian_rs)+sum(periksa_lab.kso) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                   Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_rs)+sum(detail_periksa_lab.kso) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_dokter) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_perujuk) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_lab.tarif_tindakan_petugas) from periksa_lab where periksa_lab.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_dokter) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      "where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_perujuk) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(detail_periksa_lab.bagian_laborat) from detail_periksa_lab inner join periksa_lab "+
                                                      "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                                      "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                                                      " where detail_periksa_lab.no_rawat=?"+(NmDokter.getText().trim().equals("")?"":" and periksa_lab.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_lab.menejemen) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                                      Sequel.cariIsiAngka("select sum(detail_periksa_lab.menejemen) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_lab.bhp) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                            Sequel.cariIsiAngka("select sum(detail_periksa_lab.bhp) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=? ",rs.getString("no_rawat"))+
                              Sequel.cariIsiAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            
            if(chkRadiologi.isSelected()==true){
                subjasasarana=0;subjasamedis=0;subjasamenejemen=0;subbhp=0;subtotal=0;
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,reg_periksa.status_lanjut "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.status_bayar='Belum Bayar' and "+
                        "reg_periksa.no_rawat in (select periksa_radiologi.no_rawat from periksa_radiologi group by periksa_radiologi.no_rawat) "+
                        "and concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? "+
                        (NmCaraBayar.getText().trim().equals("")?"":" and reg_periksa.kd_pj='"+KdCaraBayar.getText()+"' ")+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
                try {
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>@ RADIOLOGI</td>"+
                            "</tr>"
                        );
                    }
                    rs.beforeFirst();
                    while(rs.next()){
                        jasasarana=0;jasamedis=0;jasamenejemen=0;bhp=0;total=0;
                        jasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs)+sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        jasamedis=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.kd_dokter='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.dokter_perujuk='"+KdDokter.getText()+"' "),rs.getString("no_rawat"))+
                                  Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat=? "+(NmDokter.getText().trim().equals("")?"":" and periksa_radiologi.nip='"+KdDokter.getText()+"' "),rs.getString("no_rawat"));
                        jasamenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        bhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));
                        total=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat=? ",rs.getString("no_rawat"));

                        subjasasarana=subjasasarana+jasasarana;
                        ttljasasarana=ttljasasarana+jasasarana;
                        subjasamedis=subjasamedis+jasamedis;
                        ttljasamedis=ttljasamedis+jasamedis;
                        subjasamenejemen=subjasamenejemen+jasamenejemen;
                        ttljasamenejemen=ttljasamenejemen+jasamenejemen;
                        subbhp=subbhp+bhp;
                        ttlbhp=ttlbhp+bhp;
                        subtotal=subtotal+total;
                        ttltotal=ttltotal+total;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rawat")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                                 "<td valign='middle' align='left'>"+rs.getString("nm_pasien")+"</td>"+
                                 "<td valign='middle' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                                 "<td valign='middle' align='center'>"+(rs.getString("status_lanjut").equals("Ralan")?rs.getString("tgl_registrasi"):Sequel.cariIsi("select if(kamar_inap.tgl_keluar='0000-00-00','Belum Pulang',kamar_inap.tgl_keluar) from kamar_inap where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc limit 1 ",rs.getString("no_rawat")))+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(jasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(bhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(total)+"</td>"+
                            "</tr>"
                        );
                    }
                    rs.last();
                    if(rs.getRow()>0){
                        htmlContent.append(
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='5'>SUBTOTAL</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasasarana)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamedis)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subjasamenejemen)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subbhp)+"</td>"+
                                 "<td valign='middle' align='right'>"+Math.round(subtotal)+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                 "<td valign='middle' align='left' colspan='10'>&nbsp;</td>"+
                            "</tr>"
                        );
                    }
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            }
            if(ttltotal>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='left' colspan='5'>JUMLAH TOTAL</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasasarana)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamedis)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttljasamenejemen)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttlbhp)+"</td>"+
                         "<td valign='middle' align='right'>"+Math.round(ttltotal)+"</td>"+
                    "</tr>"
                );
            }
            
            LoadHTML2.setText(
                "<html>"+
                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
}

package keuangan;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariCaraBayar;

public class DlgRHJmDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariCaraBayar carabayar=new DlgCariCaraBayar(null,false);
    private int i=0,z=0;
    double total=0,totaljm=0;
    private StringBuilder htmlContent;
    private String pilihan="";
    private PreparedStatement ps,psrawatjalandr,psrawatjalandrpr,psrawatinapdr,psrawatinapdrpr,psbiayaoperator1,psbiayaoperator2,psbiayaoperator3,psbiayadokter_anak,
            psbiayadokter_anestesi,psdetaillab,psperiksa_lab,psperiksa_radiologi,psperiksa_lab2,psdetaillab2,psperiksa_radiologi2,psbiaya_dokter_pjanak,psbiaya_dokter_umum;
    private ResultSet rs,rsrawatjalandr,rsrawatjalandrpr,rsrawatinapdr,rsrawatinapdrpr,rsbiayaoperator1,rsbiayaoperator2,rsbiayaoperator3,rsbiayadokter_anak,
            rsbiayadokter_anestesi,rsdetaillab,rsperiksa_lab,rsperiksa_radiologi,rsbiaya_dokter_pjanak,rsbiaya_dokter_umum;
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRHJmDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Nama Dokter","Tgl.Tindakan","No.Rawat","No.RM","Nama Pasien","Tindakan Medis","Jasa Medis"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setPreferredWidth(135);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(170);
            }else if(i==6){
                column.setPreferredWidth(280);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
                
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                kddokter.requestFocus();
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
        
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
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

        Kd2 = new widget.TextBox();
        kddokter = new widget.TextBox();
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
        nmdokter = new widget.TextBox();
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
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(70, 23));

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Harian Jasa Medis Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label11.setText("Tgl.Tindakan :");
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

        label17.setText("Dokter :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label17);
        label17.setBounds(243, 40, 50, 23);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(230, 23));
        FormInput.add(nmdokter);
        nmdokter.setBounds(297, 40, 166, 23);

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
        tbDokter.setToolTipText("");
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        TabRawat.addTab("Laporan 1", scrollPane1);

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        scrollPane2.setViewportView(LoadHTML);

        TabRawat.addTab("Laporan 2", scrollPane2);

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
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                int row=tabMode.getRowCount();
                for(int r=0;r<row;r++){  
                    Sequel.menyimpan("temporary","'"+r+"','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                }
                Map<String, Object> param = new HashMap<>();                 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptRHTindakanDokter.jasper","report","::[ Rekap Harian Tindakan Dokter ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            }
        }else if(TabRawat.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {            
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f;            
                BufferedWriter bw; 

                pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            f = new File("RHDokter.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>REKAP PEMBAYARAN RAWAT JALAN PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            f = new File("RHDokter.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>DETAIL JM DOKTER PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                }                 
            } catch (Exception e) {
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
        kddokter.setText("");
        nmdokter.setText("");
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
            Valid.pindah(evt, kddokter, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
}//GEN-LAST:event_btnDokterActionPerformed

private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_btnDokterKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    prosesCari();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kddokter);
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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRHJmDokter dialog = new DlgRHJmDokter(new javax.swing.JFrame(), true);
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
    private widget.editorpane LoadHTML;
    private widget.TextBox NmCaraBayar;
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
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        if(TabRawat.getSelectedIndex()==0){
            Valid.tabelKosong(tabMode);
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
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    tabMode.addRow(new Object[]{i+".",rs.getString("nm_dokter"),"","","","","",""});   
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();
                             rsrawatjalandr.last();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             rsrawatjalandrpr.last();  
                             if((rsrawatjalandr.getRow()>0)||(rsrawatjalandrpr.getRow()>0)){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Jalan ","","","","",""});   
                             }

                             rsrawatjalandr.beforeFirst();
                             while(rsrawatjalandr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",
                                     rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             rsrawatjalandrpr.beforeFirst();
                             while(rsrawatjalandrpr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",
                                     rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();
                             rsrawatinapdr.last(); 

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             rsrawatinapdrpr.last();

                             if((rsrawatinapdr.getRow()>0)||(rsrawatinapdrpr.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Inap ","","","","",""});  
                             }

                             rsrawatinapdr.beforeFirst();
                             while(rsrawatinapdr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",
                                     rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             rsrawatinapdrpr.beforeFirst();
                             while(rsrawatinapdrpr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",
                                     rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();
                             rsbiayaoperator1.last();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();
                             rsbiayaoperator2.last();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();
                             rsbiayaoperator3.last(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                             rsbiayadokter_anak.last();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                             rsbiaya_dokter_umum.last();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                             rsbiaya_dokter_pjanak.last();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             rsbiayadokter_anestesi.last();

                             if((rsbiayaoperator1.getRow()>0)||(rsbiayaoperator2.getRow()>0)||(rsbiayaoperator3.getRow()>0)||(rsbiayadokter_anak.getRow()>0)||(rsbiaya_dokter_pjanak.getRow()>0)||(rsbiaya_dokter_umum.getRow()>0)||(rsbiayadokter_anestesi.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Operasi/VK ","","","","",""});   
                             }

                             //operator operasi1  
                             rsbiayaoperator1.beforeFirst();
                             while(rsbiayaoperator1.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator1.getString("tgl_operasi"),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",
                                     rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)",Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))
                                 });      
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2               
                             rsbiayaoperator2.beforeFirst();
                             while(rsbiayaoperator2.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator2.getString("tgl_operasi"),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",
                                     rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)",Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))
                                 });  
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3               
                             rsbiayaoperator3.beforeFirst();
                             while(rsbiayaoperator3.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator3.getString("tgl_operasi"),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",
                                     rsbiayaoperator3.getString("nm_perawatan")+"  ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)",Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))
                                 });       
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             rsbiayadokter_anak.beforeFirst();
                             while(rsbiayadokter_anak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anak.getString("tgl_operasi"),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",
                                     rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)",Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))
                                 });    
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             rsbiayadokter_anestesi.beforeFirst();
                             while(rsbiayadokter_anestesi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anestesi.getString("tgl_operasi"),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",
                                     rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)",Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))
                                 });      
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             rsbiaya_dokter_pjanak.beforeFirst();
                             while(rsbiaya_dokter_pjanak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_pjanak.getString("tgl_operasi"),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",
                                     rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)",Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))
                                 });    
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             rsbiaya_dokter_umum.beforeFirst();
                             while(rsbiaya_dokter_umum.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_umum.getString("tgl_operasi"),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",
                                     rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)",Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))
                                 });    
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){                                
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))
                                 });    
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where concat(detail_periksa_lab.tgl_periksa,' ',detail_periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Pemeriksaan Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                                     rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))
                                 });        
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where concat(detail_periksa_lab.tgl_periksa,' ',detail_periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Perujuk Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),
                                     rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))
                                 });      
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))
                                 });     
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    if(total>0){
                       tabMode.addRow(new Object[]{"","","Total :","","","","",Valid.SetAngka(total)});                    
                    }              
                    i++;
                    totaljm=totaljm+total;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","","","","","",Valid.SetAngka(totaljm)});     
            }
         }catch(SQLException e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariSemua2() {
         try{     
            htmlContent = new StringBuilder();
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='7'>"+i+". "+rs.getString("nm_dokter")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Tanggal</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>No.Rawat</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22%'>Nama Pasien</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='33%'>Tindakan Medis</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Jasa Medis</td>"+
                                    "</tr>"
                    );
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             
                             while(rsrawatjalandr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );               
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             while(rsrawatjalandrpr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             while(rsrawatinapdr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             while(rsrawatinapdrpr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();

                             //operator operasi1  
                             while(rsbiayaoperator1.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2            
                             while(rsbiayaoperator2.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3            
                             while(rsbiayaoperator3.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_perawatan")+" ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             while(rsbiayadokter_anak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             while(rsbiayadokter_anestesi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             while(rsbiaya_dokter_pjanak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             while(rsbiaya_dokter_umum.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){       
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where concat(detail_periksa_lab.tgl_periksa,' ',detail_periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))+"</td>"+
                                    "</tr>"
                                 );  
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where concat(detail_periksa_lab.tgl_periksa,' ',detail_periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }           
                    
                    htmlContent.append(
                        "<tr class='isi'>"+
                             "<td valign='middle' align='left' colspan='6'>&nbsp;Total :</td>"+
                             "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                        "</tr>"
                    );  
                    totaljm=totaljm+total;
                    
                    htmlContent.append(
                                "</table><br>"+
                            "</td>"+
                        "</tr>"
                    );
                    i++;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            if(totaljm>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='right'>Total Jasa Medis : "+Valid.SetAngka(totaljm)+"</td>"+
                    "</tr>"
                );
            }
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    public void isCek(){
        //BtnPrint.setEnabled(akses.getharian_dokter());
    }
    
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

    private void prosesCariPiutangBelumLunas() {
        try{             
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    tabMode.addRow(new Object[]{i+".",rs.getString("nm_dokter"),"","","","","",""});   
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(rawat_jl_dr.tgl_perawatan,' ',rawat_jl_dr.jam_rawat) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw,"+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(rawat_jl_drpr.tgl_perawatan,' ',rawat_jl_drpr.jam_rawat) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();
                             rsrawatjalandr.last();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             rsrawatjalandrpr.last();  
                             if((rsrawatjalandr.getRow()>0)||(rsrawatjalandrpr.getRow()>0)){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Jalan ","","","","",""});   
                             }

                             rsrawatjalandr.beforeFirst();
                             while(rsrawatjalandr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",
                                     rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             rsrawatjalandrpr.beforeFirst();
                             while(rsrawatjalandrpr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",
                                     rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();
                             rsrawatinapdr.last(); 

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             rsrawatinapdrpr.last();

                             if((rsrawatinapdr.getRow()>0)||(rsrawatinapdrpr.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Inap ","","","","",""});  
                             }

                             rsrawatinapdr.beforeFirst();
                             while(rsrawatinapdr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",
                                     rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             rsrawatinapdrpr.beforeFirst();
                             while(rsrawatinapdrpr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",
                                     rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();
                             rsbiayaoperator1.last();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();
                             rsbiayaoperator2.last();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();
                             rsbiayaoperator3.last(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                             rsbiayadokter_anak.last();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                             rsbiaya_dokter_umum.last();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                             rsbiaya_dokter_pjanak.last();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             rsbiayadokter_anestesi.last();

                             if((rsbiayaoperator1.getRow()>0)||(rsbiayaoperator2.getRow()>0)||(rsbiayaoperator3.getRow()>0)||(rsbiayadokter_anak.getRow()>0)||(rsbiaya_dokter_pjanak.getRow()>0)||(rsbiaya_dokter_umum.getRow()>0)||(rsbiayadokter_anestesi.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Operasi/VK ","","","","",""});   
                             }

                             //operator operasi1  
                             rsbiayaoperator1.beforeFirst();
                             while(rsbiayaoperator1.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator1.getString("tgl_operasi"),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",
                                     rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)",Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))
                                 });      
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2               
                             rsbiayaoperator2.beforeFirst();
                             while(rsbiayaoperator2.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator2.getString("tgl_operasi"),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",
                                     rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)",Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))
                                 });  
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3               
                             rsbiayaoperator3.beforeFirst();
                             while(rsbiayaoperator3.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator3.getString("tgl_operasi"),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",
                                     rsbiayaoperator3.getString("nm_perawatan")+"  ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)",Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))
                                 });       
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             rsbiayadokter_anak.beforeFirst();
                             while(rsbiayadokter_anak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anak.getString("tgl_operasi"),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",
                                     rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)",Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))
                                 });    
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             rsbiayadokter_anestesi.beforeFirst();
                             while(rsbiayadokter_anestesi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anestesi.getString("tgl_operasi"),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",
                                     rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)",Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))
                                 });      
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             rsbiaya_dokter_pjanak.beforeFirst();
                             while(rsbiaya_dokter_pjanak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_pjanak.getString("tgl_operasi"),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",
                                     rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)",Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))
                                 });    
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             rsbiaya_dokter_umum.beforeFirst();
                             while(rsbiaya_dokter_umum.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_umum.getString("tgl_operasi"),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",
                                     rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)",Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))
                                 });    
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){                                
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))
                                 });    
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Pemeriksaan Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),
                                     rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))
                                 });        
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Perujuk Lab ","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),
                                     rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))
                                 });      
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))
                                 });     
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    if(total>0){
                       tabMode.addRow(new Object[]{"","","Total :","","","","",Valid.SetAngka(total)});                    
                    }              
                    i++;
                    totaljm=totaljm+total;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","","","","","",Valid.SetAngka(totaljm)});     
            }
         }catch(SQLException e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariPiutangBelumLunas2() {
        try{    
            htmlContent = new StringBuilder();
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='7'>"+i+". "+rs.getString("nm_dokter")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Tanggal</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>No.Rawat</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22%'>Nama Pasien</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='33%'>Tindakan Medis</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Jasa Medis</td>"+
                                    "</tr>"
                    );
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(rawat_jl_dr.tgl_perawatan,' ',rawat_jl_dr.jam_rawat) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw,"+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(rawat_jl_drpr.tgl_perawatan,' ',rawat_jl_drpr.jam_rawat) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             while(rsrawatjalandr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );               
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             while(rsrawatjalandrpr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             
                             while(rsrawatinapdr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             while(rsrawatinapdrpr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();

                             //operator operasi1  
                             while(rsbiayaoperator1.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2            
                             while(rsbiayaoperator2.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3            
                             while(rsbiayaoperator3.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_perawatan")+" ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             while(rsbiayadokter_anak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             while(rsbiayadokter_anestesi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             while(rsbiaya_dokter_pjanak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             while(rsbiaya_dokter_umum.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){       
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))+"</td>"+
                                    "</tr>"
                                 );  
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    htmlContent.append(
                        "<tr class='isi'>"+
                             "<td valign='middle' align='left' colspan='6'>&nbsp;Total :</td>"+
                             "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                        "</tr>"
                    );  
                    totaljm=totaljm+total;
                    
                    htmlContent.append(
                                "</table><br>"+
                            "</td>"+
                        "</tr>"
                    );
                    i++;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='right'>Total Jasa Medis : "+Valid.SetAngka(totaljm)+"</td>"+
                    "</tr>"
                );
            }
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }

    private void prosesCariPiutangSudahLunas() {
        try{             
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    tabMode.addRow(new Object[]{i+".",rs.getString("nm_dokter"),"","","","","",""});   
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(rawat_jl_dr.tgl_perawatan,' ',rawat_jl_dr.jam_rawat) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(rawat_jl_drpr.tgl_perawatan,' ',rawat_jl_drpr.jam_rawat) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();
                             rsrawatjalandr.last();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             rsrawatjalandrpr.last();  
                             if((rsrawatjalandr.getRow()>0)||(rsrawatjalandrpr.getRow()>0)){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Jalan ","","","","",""});   
                             }

                             rsrawatjalandr.beforeFirst();
                             while(rsrawatjalandr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",
                                     rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             rsrawatjalandrpr.beforeFirst();
                             while(rsrawatjalandrpr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",
                                     rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();
                             rsrawatinapdr.last(); 

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             rsrawatinapdrpr.last();

                             if((rsrawatinapdr.getRow()>0)||(rsrawatinapdrpr.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Inap ","","","","",""});  
                             }

                             rsrawatinapdr.beforeFirst();
                             while(rsrawatinapdr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",
                                     rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             rsrawatinapdrpr.beforeFirst();
                             while(rsrawatinapdrpr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",
                                     rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();
                             rsbiayaoperator1.last();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();
                             rsbiayaoperator2.last();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();
                             rsbiayaoperator3.last(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                             rsbiayadokter_anak.last();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                             rsbiaya_dokter_umum.last();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                             rsbiaya_dokter_pjanak.last();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             rsbiayadokter_anestesi.last();

                             if((rsbiayaoperator1.getRow()>0)||(rsbiayaoperator2.getRow()>0)||(rsbiayaoperator3.getRow()>0)||(rsbiayadokter_anak.getRow()>0)||(rsbiaya_dokter_pjanak.getRow()>0)||(rsbiaya_dokter_umum.getRow()>0)||(rsbiayadokter_anestesi.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Operasi/VK ","","","","",""});   
                             }

                             //operator operasi1  
                             rsbiayaoperator1.beforeFirst();
                             while(rsbiayaoperator1.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator1.getString("tgl_operasi"),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",
                                     rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)",Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))
                                 });      
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2               
                             rsbiayaoperator2.beforeFirst();
                             while(rsbiayaoperator2.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator2.getString("tgl_operasi"),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",
                                     rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)",Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))
                                 });  
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3               
                             rsbiayaoperator3.beforeFirst();
                             while(rsbiayaoperator3.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator3.getString("tgl_operasi"),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",
                                     rsbiayaoperator3.getString("nm_perawatan")+"  ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)",Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))
                                 });       
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             rsbiayadokter_anak.beforeFirst();
                             while(rsbiayadokter_anak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anak.getString("tgl_operasi"),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",
                                     rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)",Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))
                                 });    
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             rsbiayadokter_anestesi.beforeFirst();
                             while(rsbiayadokter_anestesi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anestesi.getString("tgl_operasi"),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",
                                     rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)",Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))
                                 });      
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             rsbiaya_dokter_pjanak.beforeFirst();
                             while(rsbiaya_dokter_pjanak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_pjanak.getString("tgl_operasi"),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",
                                     rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)",Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))
                                 });    
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             rsbiaya_dokter_umum.beforeFirst();
                             while(rsbiaya_dokter_umum.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_umum.getString("tgl_operasi"),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",
                                     rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)",Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))
                                 });    
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){                                
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))
                                 });    
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Pemeriksaan Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),
                                     rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))
                                 });        
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Perujuk Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),
                                     rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))
                                 });      
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))
                                 });     
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    if(total>0){
                       tabMode.addRow(new Object[]{"","","Total :","","","","",Valid.SetAngka(total)});                    
                    }              
                    i++;
                    totaljm=totaljm+total;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","","","","","",Valid.SetAngka(totaljm)});     
            }
         }catch(SQLException e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariPiutangSudahLunas2() {
        try{             
            htmlContent = new StringBuilder();
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='7'>"+i+". "+rs.getString("nm_dokter")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Tanggal</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>No.Rawat</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22%'>Nama Pasien</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='33%'>Tindakan Medis</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Jasa Medis</td>"+
                                    "</tr>"
                    );
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(rawat_jl_dr.tgl_perawatan,' ',rawat_jl_dr.jam_rawat) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(rawat_jl_drpr.tgl_perawatan,' ',rawat_jl_drpr.jam_rawat) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                        try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             
                             while(rsrawatjalandr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );               
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             while(rsrawatjalandrpr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             while(rsrawatinapdr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             while(rsrawatinapdrpr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();

                             //operator operasi1  
                             while(rsbiayaoperator1.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2            
                             while(rsbiayaoperator2.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3            
                             while(rsbiayaoperator3.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_perawatan")+" ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             while(rsbiayadokter_anak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             while(rsbiayadokter_anestesi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             while(rsbiaya_dokter_pjanak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             while(rsbiaya_dokter_umum.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }    
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){       
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))+"</td>"+
                                    "</tr>"
                                 );  
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    htmlContent.append(
                        "<tr class='isi'>"+
                             "<td valign='middle' align='left' colspan='6'>&nbsp;Total :</td>"+
                             "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                        "</tr>"
                    );  
                    totaljm=totaljm+total;
                    
                    htmlContent.append(
                                "</table><br>"+
                            "</td>"+
                        "</tr>"
                    );
                    i++;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='right'>Total Jasa Medis : "+Valid.SetAngka(totaljm)+"</td>"+
                    "</tr>"
                );
            }
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }

    private void prosesCariSudahBayarNonPiutang() {
        try{             
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    tabMode.addRow(new Object[]{i+".",rs.getString("nm_dokter"),"","","","","",""});   
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(rawat_jl_dr.tgl_perawatan,' ',rawat_jl_dr.jam_rawat) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(rawat_jl_drpr.tgl_perawatan,' ',rawat_jl_drpr.jam_rawat) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();
                             rsrawatjalandr.last();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             rsrawatjalandrpr.last();  
                             if((rsrawatjalandr.getRow()>0)||(rsrawatjalandrpr.getRow()>0)){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Jalan ","","","","",""});   
                             }

                             rsrawatjalandr.beforeFirst();
                             while(rsrawatjalandr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",
                                     rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             rsrawatjalandrpr.beforeFirst();
                             while(rsrawatjalandrpr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",
                                     rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();
                             rsrawatinapdr.last(); 

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             rsrawatinapdrpr.last();

                             if((rsrawatinapdr.getRow()>0)||(rsrawatinapdrpr.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Inap ","","","","",""});  
                             }

                             rsrawatinapdr.beforeFirst();
                             while(rsrawatinapdr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",
                                     rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             rsrawatinapdrpr.beforeFirst();
                             while(rsrawatinapdrpr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",
                                     rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();
                             rsbiayaoperator1.last();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();
                             rsbiayaoperator2.last();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();
                             rsbiayaoperator3.last(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                             rsbiayadokter_anak.last();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                             rsbiaya_dokter_umum.last();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                             rsbiaya_dokter_pjanak.last();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             rsbiayadokter_anestesi.last();

                             if((rsbiayaoperator1.getRow()>0)||(rsbiayaoperator2.getRow()>0)||(rsbiayaoperator3.getRow()>0)||(rsbiayadokter_anak.getRow()>0)||(rsbiaya_dokter_pjanak.getRow()>0)||(rsbiaya_dokter_umum.getRow()>0)||(rsbiayadokter_anestesi.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Operasi/VK ","","","","",""});   
                             }

                             //operator operasi1  
                             rsbiayaoperator1.beforeFirst();
                             while(rsbiayaoperator1.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator1.getString("tgl_operasi"),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",
                                     rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)",Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))
                                 });      
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2               
                             rsbiayaoperator2.beforeFirst();
                             while(rsbiayaoperator2.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator2.getString("tgl_operasi"),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",
                                     rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)",Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))
                                 });  
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3               
                             rsbiayaoperator3.beforeFirst();
                             while(rsbiayaoperator3.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator3.getString("tgl_operasi"),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",
                                     rsbiayaoperator3.getString("nm_perawatan")+"  ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)",Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))
                                 });       
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             rsbiayadokter_anak.beforeFirst();
                             while(rsbiayadokter_anak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anak.getString("tgl_operasi"),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",
                                     rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)",Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))
                                 });    
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             rsbiayadokter_anestesi.beforeFirst();
                             while(rsbiayadokter_anestesi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anestesi.getString("tgl_operasi"),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",
                                     rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)",Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))
                                 });      
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             rsbiaya_dokter_pjanak.beforeFirst();
                             while(rsbiaya_dokter_pjanak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_pjanak.getString("tgl_operasi"),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",
                                     rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)",Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))
                                 });    
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             rsbiaya_dokter_umum.beforeFirst();
                             while(rsbiaya_dokter_umum.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_umum.getString("tgl_operasi"),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",
                                     rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)",Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))
                                 });    
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){                                
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))
                                 });    
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Pemeriksaan Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),
                                     rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))
                                 });        
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Perujuk Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),
                                     rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))
                                 });      
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))
                                 });     
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    if(total>0){
                       tabMode.addRow(new Object[]{"","","Total :","","","","",Valid.SetAngka(total)});                    
                    }              
                    i++;
                    totaljm=totaljm+total;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","","","","","",Valid.SetAngka(totaljm)});     
            }
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariSudahBayarNonPiutang2() {
        try{             
            htmlContent = new StringBuilder();
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='7'>"+i+". "+rs.getString("nm_dokter")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Tanggal</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>No.Rawat</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22%'>Nama Pasien</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='33%'>Tindakan Medis</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Jasa Medis</td>"+
                                    "</tr>"
                    );
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(rawat_jl_dr.tgl_perawatan,' ',rawat_jl_dr.jam_rawat) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(rawat_jl_drpr.tgl_perawatan,' ',rawat_jl_drpr.jam_rawat) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             
                             while(rsrawatjalandr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );               
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             while(rsrawatjalandrpr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw, " +
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             while(rsrawatinapdr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             while(rsrawatinapdrpr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();

                             //operator operasi1  
                             while(rsbiayaoperator1.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2            
                             while(rsbiayaoperator2.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3            
                             while(rsbiayaoperator3.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_perawatan")+" ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             while(rsbiayadokter_anak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             while(rsbiayadokter_anestesi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             while(rsbiaya_dokter_pjanak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             while(rsbiaya_dokter_umum.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){       
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))+"</td>"+
                                    "</tr>"
                                 );  
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    htmlContent.append(
                        "<tr class='isi'>"+
                             "<td valign='middle' align='left' colspan='6'>&nbsp;Total :</td>"+
                             "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                        "</tr>"
                    );  
                    totaljm=totaljm+total;
                    
                    htmlContent.append(
                                "</table><br>"+
                            "</td>"+
                        "</tr>"
                    );
                    i++;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='right'>Total Jasa Medis : "+Valid.SetAngka(totaljm)+"</td>"+
                    "</tr>"
                );
            }
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
         }catch(Exception e){
             System.out.println("Notifikasi : "+e);
         }
    }

    private void prosesCariBelumTerclosing() {
        try{             
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    tabMode.addRow(new Object[]{i+".",rs.getString("nm_dokter"),"","","","","",""});   
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(rawat_jl_dr.tgl_perawatan,' ',rawat_jl_dr.jam_rawat) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(rawat_jl_drpr.tgl_perawatan,' ',rawat_jl_drpr.jam_rawat) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();
                             rsrawatjalandr.last();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             rsrawatjalandrpr.last();  
                             if((rsrawatjalandr.getRow()>0)||(rsrawatjalandrpr.getRow()>0)){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Jalan ","","","","",""});   
                             }

                             rsrawatjalandr.beforeFirst();
                             while(rsrawatjalandr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat"),rsrawatjalandr.getString("no_rawat"),rsrawatjalandr.getString("no_rkm_medis"),rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")",
                                     rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             rsrawatjalandrpr.beforeFirst();
                             while(rsrawatjalandrpr.next()){
                                 tabMode.addRow(new Object[]{"","","     "+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat"),rsrawatjalandrpr.getString("no_rawat"),rsrawatjalandrpr.getString("no_rkm_medis"),rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")",
                                     rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))});                   
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw " +
                             "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw " +
                             "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();
                             rsrawatinapdr.last(); 

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             rsrawatinapdrpr.last();

                             if((rsrawatinapdr.getRow()>0)||(rsrawatinapdrpr.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Rawat Inap ","","","","",""});  
                             }

                             rsrawatinapdr.beforeFirst();
                             while(rsrawatinapdr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat"),rsrawatinapdr.getString("no_rawat"),rsrawatinapdr.getString("no_rkm_medis"),rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")",
                                     rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             rsrawatinapdrpr.beforeFirst();
                             while(rsrawatinapdrpr.next()){ 
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat"),rsrawatinapdrpr.getString("no_rawat"),rsrawatinapdrpr.getString("no_rkm_medis"),rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")",
                                     rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")",Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))
                                 });          
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();
                             rsbiayaoperator1.last();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();
                             rsbiayaoperator2.last();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();
                             rsbiayaoperator3.last(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();
                             rsbiayadokter_anak.last();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();
                             rsbiaya_dokter_umum.last();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();
                             rsbiaya_dokter_pjanak.last();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             rsbiayadokter_anestesi.last();

                             if((rsbiayaoperator1.getRow()>0)||(rsbiayaoperator2.getRow()>0)||(rsbiayaoperator3.getRow()>0)||(rsbiayadokter_anak.getRow()>0)||(rsbiaya_dokter_pjanak.getRow()>0)||(rsbiaya_dokter_umum.getRow()>0)||(rsbiayadokter_anestesi.getRow()>0)){
                                 z++; 
                                 tabMode.addRow(new Object[]{"","",z+". Operasi/VK ","","","","",""});   
                             }

                             //operator operasi1  
                             rsbiayaoperator1.beforeFirst();
                             while(rsbiayaoperator1.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator1.getString("tgl_operasi"),rsbiayaoperator1.getString("no_rawat"),rsbiayaoperator1.getString("no_rkm_medis"),rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")",
                                     rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)",Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))
                                 });      
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2               
                             rsbiayaoperator2.beforeFirst();
                             while(rsbiayaoperator2.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator2.getString("tgl_operasi"),rsbiayaoperator2.getString("no_rawat"),rsbiayaoperator2.getString("no_rkm_medis"),rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")",
                                     rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)",Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))
                                 });  
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3               
                             rsbiayaoperator3.beforeFirst();
                             while(rsbiayaoperator3.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayaoperator3.getString("tgl_operasi"),rsbiayaoperator3.getString("no_rawat"),rsbiayaoperator3.getString("no_rkm_medis"),rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")",
                                     rsbiayaoperator3.getString("nm_perawatan")+"  ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)",Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))
                                 });       
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             rsbiayadokter_anak.beforeFirst();
                             while(rsbiayadokter_anak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anak.getString("tgl_operasi"),rsbiayadokter_anak.getString("no_rawat"),rsbiayadokter_anak.getString("no_rkm_medis"),rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")",
                                     rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)",Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))
                                 });    
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             rsbiayadokter_anestesi.beforeFirst();
                             while(rsbiayadokter_anestesi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiayadokter_anestesi.getString("tgl_operasi"),rsbiayadokter_anestesi.getString("no_rawat"),rsbiayadokter_anestesi.getString("no_rkm_medis"),rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")",
                                     rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)",Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))
                                 });      
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             rsbiaya_dokter_pjanak.beforeFirst();
                             while(rsbiaya_dokter_pjanak.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_pjanak.getString("tgl_operasi"),rsbiaya_dokter_pjanak.getString("no_rawat"),rsbiaya_dokter_pjanak.getString("no_rkm_medis"),rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")",
                                     rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)",Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))
                                 });    
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             rsbiaya_dokter_umum.beforeFirst();
                             while(rsbiaya_dokter_umum.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsbiaya_dokter_umum.getString("tgl_operasi"),rsbiaya_dokter_umum.getString("no_rawat"),rsbiaya_dokter_umum.getString("no_rkm_medis"),rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")",
                                     rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)",Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))
                                 });    
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){                                
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))
                                 });    
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Pemeriksaan Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),
                                     rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             rsperiksa_lab.last();
                             if(rsperiksa_lab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk Lab ","","","","",""});
                             }               
                             rsperiksa_lab.beforeFirst();
                             while(rsperiksa_lab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam"),rsperiksa_lab.getString("no_rawat"),rsperiksa_lab.getString("no_rkm_medis"),rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")",
                                     rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))
                                 });        
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             rsdetaillab.last();
                             if(rsdetaillab.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Detail Perujuk Lab ","","","","",""});
                             } 
                             rsdetaillab.beforeFirst();
                             while(rsdetaillab.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam"),
                                     rsdetaillab.getString("no_rawat"),rsdetaillab.getString("no_rkm_medis"),rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")",
                                     rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")",Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))
                                 });    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Pemeriksaan radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))
                                 });      
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             rsperiksa_radiologi.last();
                             if(rsperiksa_radiologi.getRow()>0){
                                 z++;
                                 tabMode.addRow(new Object[]{"","",z+". Perujuk radiologi ","","","","",""});
                             }               
                             rsperiksa_radiologi.beforeFirst();
                             while(rsperiksa_radiologi.next()){
                                 tabMode.addRow(new Object[]{
                                     "","","     "+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam"),rsperiksa_radiologi.getString("no_rawat"),rsperiksa_radiologi.getString("no_rkm_medis"),rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")",
                                     rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")",Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))
                                 });     
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    if(total>0){
                       tabMode.addRow(new Object[]{"","","Total :","","","","",Valid.SetAngka(total)});                    
                    }              
                    i++;
                    totaljm=totaljm+total;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","","","","","",Valid.SetAngka(totaljm)});     
            }
         }catch(SQLException e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
    private void prosesCariBelumTerclosing2() {
        try{             
            htmlContent = new StringBuilder();
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    total=0;
                    z=0;
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left' colspan='7'>"+i+". "+rs.getString("nm_dokter")+"</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Tanggal</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>No.Rawat</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>No.RM</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22%'>Nama Pasien</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='33%'>Tindakan Medis</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status</td>"+
                                         "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Jasa Medis</td>"+
                                    "</tr>"
                    );
                    if(chkRalan.isSelected()==true){
                         //rawat jalan     
                         psrawatjalandr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_dr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,rawat_jl_dr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(rawat_jl_dr.tgl_perawatan,' ',rawat_jl_dr.jam_rawat) between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         psrawatjalandrpr=koneksi.prepareStatement("select pasien.nm_pasien,rawat_jl_drpr.tarif_tindakandr,"+
                             "jns_perawatan.nm_perawatan,rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,rawat_jl_drpr.kd_jenis_prw, "+
                             "reg_periksa.no_rawat,reg_periksa.no_rkm_medis from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(rawat_jl_drpr.tgl_perawatan,' ',rawat_jl_drpr.jam_rawat) between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
                         try {
                             psrawatjalandr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandr=psrawatjalandr.executeQuery();

                             psrawatjalandrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatjalandrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatjalandrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatjalandrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatjalandrpr=psrawatjalandrpr.executeQuery();
                             
                             while(rsrawatjalandr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("tgl_perawatan")+" "+rsrawatjalandr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_pasien")+" ("+rsrawatjalandr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandr.getString("nm_perawatan")+" ("+rsrawatjalandr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );               
                                 total=total+rsrawatjalandr.getDouble("tarif_tindakandr");
                             }  

                             while(rsrawatjalandrpr.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("tgl_perawatan")+" "+rsrawatjalandrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatjalandrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_pasien")+" ("+rsrawatjalandrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatjalandrpr.getString("nm_perawatan")+" ("+rsrawatjalandrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Jalan</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatjalandrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatjalandrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi RJ : "+e);
                         } finally{
                             if(rsrawatjalandr!=null){
                                rsrawatjalandr.close(); 
                             }
                             if(psrawatjalandr!=null){
                                psrawatjalandr.close(); 
                             }
                             if(rsrawatjalandrpr!=null){
                                rsrawatjalandrpr.close(); 
                             }
                             if(psrawatjalandrpr!=null){
                                psrawatjalandrpr.close(); 
                             }
                         }
                    }

                    if(chkRanap.isSelected()==true){
                         //rawat inap    
                         psrawatinapdr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.tarif_tindakandr,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,rawat_inap_dr.kd_jenis_prw " +
                             "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(rawat_inap_dr.tgl_perawatan,' ',rawat_inap_dr.jam_rawat) between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0 order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         psrawatinapdrpr=koneksi.prepareStatement(
                             "select pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.tarif_tindakandr,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,rawat_inap_drpr.kd_jenis_prw " +
                             "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(rawat_inap_drpr.tgl_perawatan,' ',rawat_inap_drpr.jam_rawat) between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0 order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
                         try {                            
                             psrawatinapdr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdr=psrawatinapdr.executeQuery();

                             psrawatinapdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psrawatinapdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psrawatinapdrpr.setString(3,rs.getString("kd_dokter"));
                             psrawatinapdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsrawatinapdrpr=psrawatinapdrpr.executeQuery();
                             while(rsrawatinapdr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("tgl_perawatan")+" "+rsrawatinapdr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_pasien")+" ("+rsrawatinapdr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdr.getString("nm_perawatan")+" ("+rsrawatinapdr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsrawatinapdr.getDouble("tarif_tindakandr");
                             }

                             while(rsrawatinapdrpr.next()){ 
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("tgl_perawatan")+" "+rsrawatinapdrpr.getString("jam_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsrawatinapdrpr.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_pasien")+" ("+rsrawatinapdrpr.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsrawatinapdrpr.getString("nm_perawatan")+" ("+rsrawatinapdrpr.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Rawat Inap</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsrawatinapdrpr.getDouble("tarif_tindakandr"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsrawatinapdrpr.getDouble("tarif_tindakandr");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsrawatinapdr!=null){
                                 rsrawatinapdr.close();
                             }
                             if(psrawatinapdr!=null){
                                 psrawatinapdr.close();
                             }
                             if(rsrawatinapdrpr!=null){
                                 rsrawatinapdrpr.close();
                             }
                             if(psrawatinapdrpr!=null){
                                 psrawatinapdrpr.close();
                             }
                         }
                    }               

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_umum,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biaya_dokter_pjanak,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select pasien.nm_pasien,paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                             "operasi.tgl_operasi,reg_periksa.kd_pj,operasi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                             "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan  ");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));               
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));  
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery();

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));  
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));  
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));                 
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();

                             //operator operasi1  
                             while(rsbiayaoperator1.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator1.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_pasien")+" ("+rsbiayaoperator1.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator1.getString("nm_perawatan")+" ("+rsbiayaoperator1.getString("kode_paket")+")(Operator 1)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator1.getDouble("biayaoperator1"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator1.getDouble("biayaoperator1");
                             }

                             //operator operasi2            
                             while(rsbiayaoperator2.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator2.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_pasien")+" ("+rsbiayaoperator2.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator2.getString("nm_perawatan")+" ("+rsbiayaoperator2.getString("kode_paket")+")(Operator 2)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator2.getDouble("biayaoperator2"))+"</td>"+
                                    "</tr>"
                                 ); 
                                 total=total+rsbiayaoperator2.getDouble("biayaoperator2");
                             }

                             //operator operasi3            
                             while(rsbiayaoperator3.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayaoperator3.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_pasien")+" ("+rsbiayaoperator3.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayaoperator3.getString("nm_perawatan")+" ("+rsbiayaoperator3.getString("kode_paket")+")(Operator 3)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayaoperator3.getDouble("biayaoperator3"))+"</td>"+
                                    "</tr>"
                                 );        
                                 total=total+rsbiayaoperator3.getDouble("biayaoperator3");
                             }

                             //dr anak               
                             while(rsbiayadokter_anak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_pasien")+" ("+rsbiayadokter_anak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anak.getString("nm_perawatan")+" ("+rsbiayadokter_anak.getString("kode_paket")+")(dr Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anak.getDouble("biayadokter_anak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiayadokter_anak.getDouble("biayadokter_anak");
                             }

                             //dr anasthesi              
                             while(rsbiayadokter_anestesi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiayadokter_anestesi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_pasien")+" ("+rsbiayadokter_anestesi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiayadokter_anestesi.getString("nm_perawatan")+" ("+rsbiayadokter_anestesi.getString("kode_paket")+")(dr Anestesi)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiayadokter_anestesi.getDouble("biayadokter_anestesi"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsbiayadokter_anestesi.getDouble("biayadokter_anestesi");
                             }

                             //dr pj anak               
                             while(rsbiaya_dokter_pjanak.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_pjanak.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_pasien")+" ("+rsbiaya_dokter_pjanak.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_pjanak.getString("nm_perawatan")+" ("+rsbiaya_dokter_pjanak.getString("kode_paket")+")(dr Pj Anak)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_pjanak.getDouble("biaya_dokter_pjanak");
                             }

                             //dr umum
                             while(rsbiaya_dokter_umum.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("tgl_operasi")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsbiaya_dokter_umum.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_pasien")+" ("+rsbiaya_dokter_umum.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsbiaya_dokter_umum.getString("nm_perawatan")+" ("+rsbiaya_dokter_umum.getString("kode_paket")+")(dr Umum)"+"</td>"+
                                         "<td valign='middle' align='center'>Operasi/VK</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsbiaya_dokter_umum.getDouble("biaya_dokter_umum"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsbiaya_dokter_umum.getDouble("biaya_dokter_umum");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                        //periksa lab  
                        psperiksa_lab=koneksi.prepareStatement(
                             "select periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_tindakan_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){       
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         //detail periksa lab
                         psdetaillab=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_dokter,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Pemeriksaan Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_dokter"))+"</td>"+
                                    "</tr>"
                                 );  
                                 total=total+rsdetaillab.getDouble("bagian_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //periksa lab perujuk                         
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select periksa_lab.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.no_rawat,periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             " and periksa_lab.tarif_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");            
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("tgl_periksa")+" "+rsperiksa_lab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_lab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_pasien")+" ("+rsperiksa_lab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_lab.getString("nm_perawatan")+" ("+rsperiksa_lab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_lab.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_lab.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select detail_periksa_lab.bagian_perujuk,pasien.nm_pasien,"+
                             "template_laboratorium.Pemeriksaan,reg_periksa.kd_pj,reg_periksa.no_rawat,reg_periksa.no_rkm_medis, "+
                             "periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw "+
                             "from detail_periksa_lab inner join periksa_lab "+
                             "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                             "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                             "and periksa_lab.jam=detail_periksa_lab.jam "+
                             "and periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                             "where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_lab.tgl_periksa,' ',periksa_lab.jam) between ? and ? "+
                             "and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0 order by periksa_lab.tgl_periksa,periksa_lab.jam");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("tgl_periksa")+" "+rsdetaillab.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsdetaillab.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("nm_pasien")+" ("+rsdetaillab.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsdetaillab.getString("Pemeriksaan")+" ("+rsdetaillab.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Detail Perujuk Lab</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsdetaillab.getDouble("bagian_perujuk"))+"</td>"+
                                    "</tr>"
                                 );    
                                 total=total+rsdetaillab.getDouble("bagian_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }


                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement("select periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Pemeriksaan Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_tindakan_dokter"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_tindakan_dokter");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //periksa radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement("select periksa_radiologi.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                             "jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.no_rawat,periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                             " from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             " inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                             " inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             " where reg_periksa.status_bayar='Belum Bayar' and concat(periksa_radiologi.tgl_periksa,' ',periksa_radiologi.jam) between ? and ? and periksa_radiologi.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam,jns_perawatan_radiologi.nm_perawatan  ");            
                         try {
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" "+CmbJam2.getSelectedItem()+":"+CmbMenit2.getSelectedItem()+":"+CmbDetik2.getSelectedItem());
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 htmlContent.append(
                                    "<tr class='isi'>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("tgl_periksa")+" "+rsperiksa_radiologi.getString("jam")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rawat")+"</td>"+
                                         "<td valign='middle' align='center'>"+rsperiksa_radiologi.getString("no_rkm_medis")+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_pasien")+" ("+rsperiksa_radiologi.getString("kd_pj")+")"+"</td>"+
                                         "<td valign='middle' align='left'>"+rsperiksa_radiologi.getString("nm_perawatan")+" ("+rsperiksa_radiologi.getString("kd_jenis_prw")+")"+"</td>"+
                                         "<td valign='middle' align='center'>Perujuk Radiologi</td>"+
                                         "<td valign='middle' align='right'>"+Valid.SetAngka(rsperiksa_radiologi.getDouble("tarif_perujuk"))+"</td>"+
                                    "</tr>"
                                 );
                                 total=total+rsperiksa_radiologi.getDouble("tarif_perujuk");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }               

                    htmlContent.append(
                        "<tr class='isi'>"+
                             "<td valign='middle' align='left' colspan='6'>&nbsp;Total :</td>"+
                             "<td valign='middle' align='right'>"+Valid.SetAngka(total)+"</td>"+
                        "</tr>"
                    );  
                    totaljm=totaljm+total;
                    
                    htmlContent.append(
                                "</table><br>"+
                            "</td>"+
                        "</tr>"
                    );
                    i++;
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Pasien : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

            if(totaljm>0){
                htmlContent.append(
                    "<tr class='isi'>"+
                         "<td valign='middle' align='right'>Total Jasa Medis : "+Valid.SetAngka(totaljm)+"</td>"+
                    "</tr>"
                );
            }
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
         }catch(SQLException e){
             System.out.println("Notifikasi : "+e);
         }
    }
    
}

package rekammedis;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgGolonganPolri;
import simrskhanza.DlgJabatanPolri;
import simrskhanza.DlgPangkatPolri;
import simrskhanza.DlgSatuanPolri;

public class DlgSOAPRalanAggotaPolri extends javax.swing.JDialog {
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private final Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgGolonganPolri golonganpolri=new DlgGolonganPolri(null,false);
    private DlgSatuanPolri satuanpolri=new DlgSatuanPolri(null,false);
    private DlgPangkatPolri pangkatpolri=new DlgPangkatPolri(null,false);
    private DlgJabatanPolri jabatanpolri=new DlgJabatanPolri(null,false);
    private final DlgCariPoli poli=new DlgCariPoli(null,false);
    private final DlgCariDokter dokter=new DlgCariDokter(null,false);
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgSOAPRalanAggotaPolri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        golonganpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(golonganpolri.getTable().getSelectedRow()!= -1){
                    Golongan.setText(golonganpolri.getTable().getValueAt(golonganpolri.getTable().getSelectedRow(),1).toString());
                }  
                Golongan.requestFocus();
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
        
        golonganpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    golonganpolri.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        jabatanpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jabatanpolri.getTable().getSelectedRow()!= -1){
                    Jabatan.setText(jabatanpolri.getTable().getValueAt(jabatanpolri.getTable().getSelectedRow(),1).toString());
                }  
                Jabatan.requestFocus();
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
        
        jabatanpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jabatanpolri.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        satuanpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(satuanpolri.getTable().getSelectedRow()!= -1){
                    Satuan.setText(satuanpolri.getTable().getValueAt(satuanpolri.getTable().getSelectedRow(),1).toString());
                }  
                Satuan.requestFocus();
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
        
        satuanpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        satuanpolri.dispose();
                    }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pangkatpolri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pangkatpolri.getTable().getSelectedRow()!= -1){
                    Pangkat.setText(pangkatpolri.getTable().getValueAt(pangkatpolri.getTable().getSelectedRow(),1).toString());
                }  
                Pangkat.requestFocus();
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
        
        pangkatpolri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pangkatpolri.dispose();
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
                    Poli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {poli.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    Dokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }      
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
        
        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
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
        LoadHTML.setDocument(doc);
        
        ChkInput.setSelected(false);
        isForm();
    }
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        btnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        Satuan = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label20 = new widget.Label();
        Golongan = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label21 = new widget.Label();
        Jabatan = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        label22 = new widget.Label();
        Pangkat = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        label23 = new widget.Label();
        Dokter = new widget.TextBox();
        BtnSeek7 = new widget.Button();
        label24 = new widget.Label();
        Poli = new widget.TextBox();
        BtnSeek8 = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ SOAP Rawat Jalan Anggota Polri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi1.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi1.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi1.add(Tgl2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(160, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        btnCari.setMnemonic('2');
        btnCari.setToolTipText("Alt+2");
        btnCari.setName("btnCari"); // NOI18N
        btnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        btnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariKeyPressed(evt);
            }
        });
        panelisi1.add(btnCari);

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

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(20, 30));
        panelisi1.add(label9);

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

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 104));
        FormInput.setLayout(null);

        label17.setText("Satuan :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label17);
        label17.setBounds(0, 10, 70, 23);

        Satuan.setEditable(false);
        Satuan.setName("Satuan"); // NOI18N
        Satuan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Satuan);
        Satuan.setBounds(74, 10, 260, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(337, 10, 28, 23);

        label20.setText("Golongan :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(0, 40, 70, 23);

        Golongan.setEditable(false);
        Golongan.setName("Golongan"); // NOI18N
        Golongan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Golongan);
        Golongan.setBounds(74, 40, 260, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(337, 40, 28, 23);

        label21.setText("Jabatan :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label21);
        label21.setBounds(429, 10, 87, 23);

        Jabatan.setEditable(false);
        Jabatan.setName("Jabatan"); // NOI18N
        Jabatan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Jabatan);
        Jabatan.setBounds(519, 10, 260, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('3');
        BtnSeek5.setToolTipText("Alt+3");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek5);
        BtnSeek5.setBounds(782, 10, 28, 23);

        label22.setText("Pangkat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label22);
        label22.setBounds(429, 40, 87, 23);

        Pangkat.setEditable(false);
        Pangkat.setName("Pangkat"); // NOI18N
        Pangkat.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Pangkat);
        Pangkat.setBounds(519, 40, 260, 23);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('3');
        BtnSeek6.setToolTipText("Alt+3");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek6);
        BtnSeek6.setBounds(782, 40, 28, 23);

        label23.setText("Dokter :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label23);
        label23.setBounds(0, 70, 70, 23);

        Dokter.setEditable(false);
        Dokter.setName("Dokter"); // NOI18N
        Dokter.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Dokter);
        Dokter.setBounds(74, 70, 260, 23);

        BtnSeek7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek7.setMnemonic('3');
        BtnSeek7.setToolTipText("Alt+3");
        BtnSeek7.setName("BtnSeek7"); // NOI18N
        BtnSeek7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek7ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek7);
        BtnSeek7.setBounds(337, 70, 28, 23);

        label24.setText("Poliklinik :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label24);
        label24.setBounds(429, 70, 87, 23);

        Poli.setEditable(false);
        Poli.setName("Poli"); // NOI18N
        Poli.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(Poli);
        Poli.setBounds(519, 70, 260, 23);

        BtnSeek8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek8.setMnemonic('3');
        BtnSeek8.setToolTipText("Alt+3");
        BtnSeek8.setName("BtnSeek8"); // NOI18N
        BtnSeek8.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek8ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek8);
        BtnSeek8.setBounds(782, 70, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

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
            
            File f = new File("SOAPRalanAnggotaPolri.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
            bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                            "<tr class='isi2'>"+
                                "<td valign='top' align='center'>"+
                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+       
                                "</td>"+
                           "</tr>"+
                        "</table>")
            );
            bw.close();                         
            Desktop.getDesktop().browse(f.toURI());
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }     
        
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
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

private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
    prosesCari();
}//GEN-LAST:event_btnCariActionPerformed

private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            btnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, Tgl2, BtnPrint);
        }
}//GEN-LAST:event_btnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            btnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            prosesCari();
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        satuanpolri.isCek();
        satuanpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        satuanpolri.setLocationRelativeTo(internalFrame1);
        satuanpolri.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        golonganpolri.isCek();
        golonganpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        golonganpolri.setLocationRelativeTo(internalFrame1);
        golonganpolri.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        jabatanpolri.isCek();
        jabatanpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jabatanpolri.setLocationRelativeTo(internalFrame1);
        jabatanpolri.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        pangkatpolri.isCek();
        pangkatpolri.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pangkatpolri.setLocationRelativeTo(internalFrame1);
        pangkatpolri.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void BtnSeek7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek7ActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek7ActionPerformed

    private void BtnSeek8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek8ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek8ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSOAPRalanAggotaPolri dialog = new DlgSOAPRalanAggotaPolri(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSeek7;
    private widget.Button BtnSeek8;
    private widget.CekBox ChkInput;
    private widget.TextBox Dokter;
    private widget.panelisi FormInput;
    private widget.TextBox Golongan;
    private widget.TextBox Jabatan;
    private widget.TextBox Kd2;
    private widget.editorpane LoadHTML;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pangkat;
    private widget.TextBox Poli;
    private widget.TextBox Satuan;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='65px'>Registrasi</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>NRP/NIP</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='180px'>Nama Pasien</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>Golongan</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>Pangkat</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>Kesatuan</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'>Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'>Diagnosa</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'>Dokter</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'>Poliklinik</td>"+
                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='90px'>Tgl & Jam SOAP</td>"+
                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='150px'>Dilakukan</td>"+
                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='200px'>Subjek</td>"+
                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='200px'>Objek</td>"+
                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='200px'>Asesmen</td>"+
                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='200px'>Plan</td>"+
                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='200px'>Instruksi</td>"+
                    "<td valign='middle' bgcolor='#FFFFF8' align='center' width='200px'>Evaluasi</td>"+
                "</tr>"
            );     
            if(Pangkat.getText().trim().equals("")&&Satuan.getText().trim().equals("")&&Golongan.getText().trim().equals("")&&
                    Jabatan.getText().trim().equals("")&&Poli.getText().trim().equals("")&&Dokter.getText().trim().equals("")
                       &&TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,pasien.nm_pasien,pangkat_polri.nama_pangkat,pasien.nip as nrp,satuan_polri.nama_satuan,"+
                    "reg_periksa.no_rawat,golongan_polri.nama_golongan,jabatan_polri.nama_jabatan,pasien.jk,pasien.umur,dokter.nm_dokter,poliklinik.nm_poli,"+
                    "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,"+
                    "pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.spo2,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, "+
                    "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,"+
                    "pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama,pegawai.jbtn from reg_periksa "+
                    "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                    "inner join pasien_polri on pasien_polri.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pangkat_polri on pasien_polri.pangkat_polri=pangkat_polri.id "+
                    "inner join satuan_polri on pasien_polri.satuan_polri=satuan_polri.id "+
                    "inner join golongan_polri on pasien_polri.golongan_polri=golongan_polri.id "+
                    "inner join jabatan_polri on pasien_polri.jabatan_polri=jabatan_polri.id "+
                    "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join pemeriksaan_ralan on reg_periksa.no_rawat=pemeriksaan_ralan.no_rawat "+
                    "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.status_lanjut='Ralan' order by reg_periksa.tgl_registrasi");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,pasien.nm_pasien,pangkat_polri.nama_pangkat,pasien.nip as nrp,satuan_polri.nama_satuan,"+
                    "reg_periksa.no_rawat,golongan_polri.nama_golongan,jabatan_polri.nama_jabatan,pasien.jk,pasien.umur,dokter.nm_dokter,poliklinik.nm_poli,"+
                    "pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,"+
                    "pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.spo2,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, "+
                    "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,"+
                    "pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama,pegawai.jbtn from reg_periksa "+
                    "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                    "inner join pasien_polri on pasien_polri.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join pangkat_polri on pasien_polri.pangkat_polri=pangkat_polri.id "+
                    "inner join satuan_polri on pasien_polri.satuan_polri=satuan_polri.id "+
                    "inner join golongan_polri on pasien_polri.golongan_polri=golongan_polri.id "+
                    "inner join jabatan_polri on pasien_polri.jabatan_polri=jabatan_polri.id "+
                    "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join pemeriksaan_ralan on reg_periksa.no_rawat=pemeriksaan_ralan.no_rawat "+
                    "inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where "+
                    "reg_periksa.tgl_registrasi between ? and ? and pangkat_polri.nama_pangkat like ? and satuan_polri.nama_satuan like ? and golongan_polri.nama_golongan like ? and jabatan_polri.nama_jabatan like ? and "+
                    "poliklinik.nm_poli like ? and dokter.nm_dokter like ? and reg_periksa.status_lanjut='Ralan' and (pasien.nm_pasien like ? or pasien.nip like ?) order by reg_periksa.tgl_registrasi");
            }
                
            try {
                if(Pangkat.getText().trim().equals("")&&Satuan.getText().trim().equals("")&&Golongan.getText().trim().equals("")&&
                    Jabatan.getText().trim().equals("")&&Poli.getText().trim().equals("")&&Dokter.getText().trim().equals("")
                       &&TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+Pangkat.getText().trim()+"%");
                    ps.setString(4,"%"+Satuan.getText().trim()+"%");
                    ps.setString(5,"%"+Golongan.getText().trim()+"%");
                    ps.setString(6,"%"+Jabatan.getText().trim()+"%");
                    ps.setString(7,"%"+Poli.getText().trim()+"%");
                    ps.setString(8,"%"+Dokter.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("nrp")+"</td>"+
                            "<td valign='top' align='left'>"+rs.getString("nm_pasien")+" / "+rs.getString("jk")+" / "+rs.getString("umur")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("nama_golongan")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("nama_pangkat")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("nama_satuan")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("nama_jabatan")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("nm_dokter")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("nm_poli")+"</td>"+
                            "<td valign='top' align='center'>"+Sequel.cariIsi("select penyakit.nm_penyakit from penyakit inner join diagnosa_pasien on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.no_rawat=?",rs.getString("no_rawat"))+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_perawatan")+"<br>"+rs.getString("jam_rawat")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("nip")+"<br>"+rs.getString("nama")+"</td>"+
                            "<td valign='top' align='left'>"+rs.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                            "<td valign='top' align='left'>"+rs.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+
                            (rs.getString("alergi").equals("")?"":"<br>Alergi : "+rs.getString("alergi"))+
                            (rs.getString("suhu_tubuh").equals("")?"":"<br>Suhu(C) : "+rs.getString("suhu_tubuh"))+
                            (rs.getString("tensi").equals("")?"":"<br>Tensi : "+rs.getString("tensi"))+
                            (rs.getString("nadi").equals("")?"":"<br>Nadi(/menit) : "+rs.getString("nadi"))+
                            (rs.getString("respirasi").equals("")?"":"<br>Respirasi(/menit) : "+rs.getString("respirasi"))+
                            (rs.getString("tinggi").equals("")?"":"<br>Tinggi(Cm) : "+rs.getString("tinggi"))+
                            (rs.getString("berat").equals("")?"":"<br>Berat(Kg) : "+rs.getString("berat"))+
                            (rs.getString("spo2").equals("")?"":"<br>SpO2(%) : "+rs.getString("spo2"))+
                            (rs.getString("gcs").equals("")?"":"<br>GCS(E,V,M) : "+rs.getString("gcs"))+
                            (rs.getString("kesadaran").equals("")?"":"<br>Kesadaran : "+rs.getString("kesadaran"))+
                            "</td>"+
                            "<td valign='top' align='left'>"+rs.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                            "<td valign='top' align='left'>"+rs.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                            "<td valign='top' align='left'>"+rs.getString("instruksi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                            "<td valign='top' align='left'>"+rs.getString("evaluasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                        "</tr>"
                     );
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
            
            LoadHTML.setText(
                    "<html>"+
                      "<table width='2000px' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("laporan.DlgRL4A.prosesCari() 5 : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getsoap_ralan_polri());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,125));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
}

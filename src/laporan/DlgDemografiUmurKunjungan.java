package laporan;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import fungsi.sekuel;
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
import javax.swing.JOptionPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;
import simrskhanza.DlgCariCaraBayar;

public class DlgDemografiUmurKunjungan extends javax.swing.JDialog {
    private final validasi Valid=new validasi();
    private final Connection koneksi=koneksiDB.condb();
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgKabupaten kabupaten=new DlgKabupaten(null,false);
    private DlgKecamatan kecamatan=new DlgKecamatan(null,false);
    private DlgKelurahan kelurahan=new DlgKelurahan(null,false);
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private sekuel Sequel = new sekuel();
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2;
    private String querytambahan,querydokter,querypoli,querycarabayar,tambahanquery;
    private StringBuilder htmlContent;
    private int hr07l=0,hr07p=0,thk1l=0,thk1p=0,th14l=0,th14p=0,th59l=0,th59p=0,th1014l=0,th1014p=0,
                th1519l=0,th1519p=0,th2044l=0,th2044p=0,th4554l=0,th4554p=0,th5559l=0,th5559p=0,
                th6069l=0,th6069p=0,thl70l=0,thl70p=0,hr830l=0,hr830p=0,totall=0,totalp=0,totallp=0,
                registrasibaru=0,registrasilama=0,polilama=0,polibaru=0,
                ttlhr07l=0,ttlhr07p=0,ttlthk1l=0,ttlthk1p=0,ttlth14l=0,ttlth14p=0,ttlth59l=0,ttlth59p=0,ttlth1014l=0,ttlth1014p=0,
                ttlth1519l=0,ttlth1519p=0,ttlth2044l=0,ttlth2044p=0,ttlth4554l=0,ttlth4554p=0,ttlth5559l=0,ttlth5559p=0,
                ttlth6069l=0,ttlth6069p=0,ttlthl70l=0,ttlthl70p=0,ttlhr830l=0,ttlhr830p=0,ttltotall=0,ttltotalp=0,ttltotallp=0,
                ttlregistrasibaru=0,ttlregistrasilama=0,ttlpolilama=0,ttlpolibaru=0;
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgDemografiUmurKunjungan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    nmpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                }      
                kdpoli.requestFocus();
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
        
        kabupaten.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kabupaten.getTable().getSelectedRow()!= -1){
                    nmkabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(),0).toString());
                }      
                nmkabupaten.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {kabupaten.emptTeks();}
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
                    nmkecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(),0).toString());
                }      
                nmkecamatan.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {kecamatan.emptTeks();}
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
                    nmkelurahan.setText(kelurahan.getTable().getValueAt(kelurahan.getTable().getSelectedRow(),0).toString());
                }      
                nmkelurahan.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {kelurahan.emptTeks();}
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
        
        ChkInput.setSelected(false);
        isForm();
    }
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private int i=0;

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
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label19 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        label20 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label21 = new widget.Label();
        nmkabupaten = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        label22 = new widget.Label();
        nmkecamatan = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        BtnSeek7 = new widget.Button();
        nmkelurahan = new widget.TextBox();
        label23 = new widget.Label();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Laporan Demografi Umur Kunjungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi1.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(Tgl2);

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
        label9.setPreferredSize(new java.awt.Dimension(100, 30));
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

        label17.setText("Unit/Poli :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label17);
        label17.setBounds(0, 10, 75, 23);

        kdpoli.setEditable(false);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setPreferredSize(new java.awt.Dimension(75, 23));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        FormInput.add(kdpoli);
        kdpoli.setBounds(78, 10, 85, 23);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmpoli);
        nmpoli.setBounds(165, 10, 228, 23);

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
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(396, 10, 28, 23);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label19);
        label19.setBounds(0, 70, 75, 23);

        kdpenjab.setEditable(false);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(75, 23));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        FormInput.add(kdpenjab);
        kdpenjab.setBounds(78, 70, 85, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmpenjab);
        nmpenjab.setBounds(165, 70, 228, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(396, 70, 28, 23);

        label20.setText("Dokter :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(0, 40, 75, 23);

        kddokter.setEditable(false);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(75, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(78, 40, 85, 23);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmdokter);
        nmdokter.setBounds(165, 40, 228, 23);

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
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(396, 40, 28, 23);

        label21.setText("Kab/Kota :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label21);
        label21.setBounds(429, 10, 87, 23);

        nmkabupaten.setEditable(false);
        nmkabupaten.setName("nmkabupaten"); // NOI18N
        nmkabupaten.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkabupaten);
        nmkabupaten.setBounds(519, 10, 260, 23);

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
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek5);
        BtnSeek5.setBounds(782, 10, 28, 23);

        label22.setText("Kecamatan :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label22);
        label22.setBounds(429, 40, 87, 23);

        nmkecamatan.setEditable(false);
        nmkecamatan.setName("nmkecamatan"); // NOI18N
        nmkecamatan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkecamatan);
        nmkecamatan.setBounds(519, 40, 260, 23);

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
        BtnSeek6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek6KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek6);
        BtnSeek6.setBounds(782, 40, 28, 23);

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
        BtnSeek7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek7KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek7);
        BtnSeek7.setBounds(782, 70, 28, 23);

        nmkelurahan.setEditable(false);
        nmkelurahan.setName("nmkelurahan"); // NOI18N
        nmkelurahan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkelurahan);
        nmkelurahan.setBounds(519, 70, 260, 23);

        label23.setText("Kelurahan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label23);
        label23.setBounds(429, 70, 87, 23);

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
            
            File f = new File("rl4b.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
            bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                            "<tr class='isi2'>"+
                                "<td valign='top' align='center'>"+
                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                    "<font size='2' face='Tahoma'>DATA DEMOGRAFI UMUR KUNJUNGAN<br>PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpoliKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        kabupaten.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kabupaten.setLocationRelativeTo(internalFrame1);
        kabupaten.setAlwaysOnTop(false);
        kabupaten.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek5KeyPressed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        if(nmkabupaten.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih Kabupaten terlebih dahulu..!!");
        }else{
            kecamatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kecamatan.setLocationRelativeTo(internalFrame1);
            kecamatan.setAlwaysOnTop(false);
            kecamatan.setVisible(true);
        }
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void BtnSeek6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek6KeyPressed

    private void BtnSeek7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek7ActionPerformed
        if(nmkecamatan.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih Kecamatan terlebih dahulu..!!");
        }else{
            kelurahan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kelurahan.setLocationRelativeTo(internalFrame1);
            kelurahan.setAlwaysOnTop(false);
            kelurahan.setVisible(true);
        }
    }//GEN-LAST:event_BtnSeek7ActionPerformed

    private void BtnSeek7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek7KeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdpoli.setText("");
        nmpoli.setText("");
        kddokter.setText("");
        nmdokter.setText("");
        kdpenjab.setText("");
        nmpenjab.setText("");
        nmkabupaten.setText("");
        nmkecamatan.setText("");
        nmkelurahan.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{

        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDemografiUmurKunjungan dialog = new DlgDemografiUmurKunjungan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSeek7;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.editorpane LoadHTML;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnCari;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kddokter;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdpoli;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label9;
    private widget.TextBox nmdokter;
    private widget.TextBox nmkabupaten;
    private widget.TextBox nmkecamatan;
    private widget.TextBox nmkelurahan;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.panelisi panelisi1;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            querydokter="";
            if(!nmdokter.getText().equals("")){
                querydokter=" and reg_periksa.kd_dokter='"+kddokter.getText()+"' ";
            }
            querypoli="";
            if(!nmpoli.getText().equals("")){
                querypoli=" and reg_periksa.kd_poli='"+kdpoli.getText()+"' ";
            }
            querycarabayar="";
            if(!nmpenjab.getText().equals("")){
                querycarabayar=" and reg_periksa.kd_pj='"+kdpenjab.getText()+"' ";
            }

            querytambahan=querydokter+querypoli+querycarabayar;
                    
            if(!nmkelurahan.getText().equals("")){
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%' rowspan='3'>No.</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%' rowspan='3'>Desa/Alamat</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55%' colspan='26'>Jumlah Kunjungan/Golongan Umur</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='3'>Total</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%' colspan='2'>Jenis Kunjungan</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='13%' colspan='3'>Jumlah Kunjungan/Kasus</td>"+
                    "</tr>"+  
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>0-7Hr</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>8-30hr</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>&lt;1Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>1-4Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>5-9Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>10-14Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>15-19Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>20-44Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>45-54Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>55-59Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>60-69Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>&gt;=70Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>Total<br>L|P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Baru</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Lama</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Baru</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Lama</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Total</td>"+
                    "</tr>"+
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                    "</tr>"
                );

                ps=koneksi.prepareStatement("select pasien.alamat as nama,kelurahan.kd_kel,"+
                          "kabupaten.kd_kab,kecamatan.kd_kec from reg_periksa "+
                          "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                          "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                          "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                          "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                          "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                          "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and kabupaten.nm_kab='"+nmkabupaten.getText()+"' "+
                          "and kecamatan.nm_kec='"+nmkecamatan.getText()+"' and kelurahan.nm_kel='"+nmkelurahan.getText()+"' "+
                          querytambahan+" group by kelurahan.nm_kel");
                try {
                    rs=ps.executeQuery();
                    i=1;
                    ttlhr07l=0;ttlhr07p=0;ttlthk1l=0;ttlthk1p=0;ttlth14l=0;ttlth14p=0;ttlth59l=0;ttlth59p=0;ttlth1014l=0;ttlth1014p=0;
                    ttlth1519l=0;ttlth1519p=0;ttlth2044l=0;ttlth2044p=0;ttlth4554l=0;ttlth4554p=0;ttlth5559l=0;ttlth5559p=0;
                    ttlth6069l=0;ttlth6069p=0;ttlthl70l=0;ttlthl70p=0;ttlhr830l=0;ttlhr830p=0;ttltotall=0;ttltotalp=0;ttltotallp=0;
                    ttlregistrasibaru=0;ttlregistrasilama=0;ttlpolilama=0;ttlpolibaru=0;
                    while(rs.next()){
                        hr07l=0;hr07p=0;thk1l=0;thk1p=0;th14l=0;th14p=0;th59l=0;th59p=0;th1014l=0;th1014p=0;
                        th1519l=0;th1519p=0;th2044l=0;th2044p=0;th4554l=0;th4554p=0;th5559l=0;th5559p=0;
                        th6069l=0;th6069p=0;thl70l=0;thl70p=0;hr830l=0;hr830p=0;totall=0;totalp=0;totallp=0;
                        registrasibaru=0;registrasilama=0;polilama=0;polibaru=0;

                        hr07l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '0' and '7' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        hr07p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '0' and '7' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        hr830l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '8' and '31' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        hr830p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '8' and '31' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        thk1l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Bl' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        thk1p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Bl' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th14l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '1' and '4' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th14p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '1' and '4' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th59l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '5' and '9' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th59p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '5' and '9' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th1014l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '10' and '14' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th1014p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '10' and '14' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th1519l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '15' and '19' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th1519p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '15' and '19' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th2044l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '20' and '44' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th2044p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '20' and '44' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th4554l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '45' and '54' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th4554p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '45' and '54' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th5559l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '55' and '59' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th5559p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '55' and '59' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th6069l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '60' and '69' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th6069p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '60' and '69' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        thl70l=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar >='70' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        thl70p=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar >='70' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        totall=hr07l+thk1l+th14l+th59l+th1014l+th1519l+th2044l+th4554l+th5559l+th6069l+thl70l+hr830l;
                        totalp=hr07p+thk1p+th14p+th59p+th1014p+th1519p+th2044p+th4554p+th5559p+th6069p+thl70p+hr830p;
                        totallp=totall+totalp;
                        registrasibaru=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and reg_periksa.stts_daftar='Baru' "+querytambahan+" group by pasien.kd_kel");
                        registrasilama=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and reg_periksa.stts_daftar='Lama' "+querytambahan+" group by pasien.kd_kel");
                        polibaru=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and reg_periksa.status_poli='Baru' "+querytambahan+" group by pasien.kd_kel");
                        polilama=Sequel.cariInteger(
                             "select count(pasien.alamat)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kel='"+rs.getString("kd_kel")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.alamat='"+rs.getString("nama")+"' "+
                             "and reg_periksa.status_poli='Lama' "+querytambahan+" group by pasien.kd_kel");

                        ttlhr07l=ttlhr07l+hr07l;
                        ttlhr07p=ttlhr07p+hr07p;
                        ttlthk1l=ttlthk1l+thk1l;
                        ttlthk1p=ttlthk1p+thk1p;
                        ttlth14l=ttlth14l+th14l;
                        ttlth14p=ttlth14p+th14p;
                        ttlth59l=ttlth59l+th59l;
                        ttlth59p=ttlth59p+th59p;
                        ttlth1014l=ttlth1014l+th1014l;
                        ttlth1014p=ttlth1014p+th1014p;
                        ttlth1519l=ttlth1519l+th1519l;
                        ttlth1519p=ttlth1519p+th1519p;
                        ttlth2044l=ttlth2044l+th2044l;
                        ttlth2044p=ttlth2044p+th2044p;
                        ttlth4554l=ttlth4554l+th4554l;
                        ttlth4554p=ttlth4554p+th4554p;
                        ttlth5559l=ttlth5559l+th5559l;
                        ttlth5559p=ttlth5559p+th5559p;
                        ttlth6069l=ttlth6069l+th6069l;
                        ttlth6069p=ttlth6069p+th6069p;
                        ttlthl70l=ttlthl70l+thl70l;
                        ttlthl70p=ttlthl70p+thl70p;
                        ttlhr830l=ttlhr830l+hr830l;
                        ttlhr830p=ttlhr830p+hr830p;
                        ttltotall=ttltotall+totall;
                        ttltotalp=ttltotalp+totalp;
                        ttltotallp=ttltotallp+totallp;
                        ttlregistrasibaru=ttlregistrasibaru+registrasibaru;
                        ttlregistrasilama=ttlregistrasilama+registrasilama;
                        ttlpolilama=ttlpolilama+polilama;
                        ttlpolibaru=ttlpolibaru+polibaru;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+i+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama")+"</td>"+
                                "<td valign='middle' align='center'>"+hr07l+"</td>"+
                                "<td valign='middle' align='center'>"+hr07p+"</td>"+
                                "<td valign='middle' align='center'>"+hr830l+"</td>"+
                                "<td valign='middle' align='center'>"+hr830p+"</td>"+
                                "<td valign='middle' align='center'>"+thk1l+"</td>"+
                                "<td valign='middle' align='center'>"+thk1p+"</td>"+
                                "<td valign='middle' align='center'>"+th14l+"</td>"+
                                "<td valign='middle' align='center'>"+th14p+"</td>"+
                                "<td valign='middle' align='center'>"+th59l+"</td>"+
                                "<td valign='middle' align='center'>"+th59p+"</td>"+
                                "<td valign='middle' align='center'>"+th1014l+"</td>"+
                                "<td valign='middle' align='center'>"+th1014p+"</td>"+
                                "<td valign='middle' align='center'>"+th1519l+"</td>"+
                                "<td valign='middle' align='center'>"+th1519p+"</td>"+
                                "<td valign='middle' align='center'>"+th2044l+"</td>"+
                                "<td valign='middle' align='center'>"+th2044p+"</td>"+
                                "<td valign='middle' align='center'>"+th4554l+"</td>"+
                                "<td valign='middle' align='center'>"+th4554p+"</td>"+
                                "<td valign='middle' align='center'>"+th5559l+"</td>"+
                                "<td valign='middle' align='center'>"+th5559p+"</td>"+
                                "<td valign='middle' align='center'>"+th6069l+"</td>"+
                                "<td valign='middle' align='center'>"+th6069p+"</td>"+
                                "<td valign='middle' align='center'>"+thl70l+"</td>"+
                                "<td valign='middle' align='center'>"+thl70p+"</td>"+
                                "<td valign='middle' align='center'>"+totall+"</td>"+
                                "<td valign='middle' align='center'>"+totalp+"</td>"+
                                "<td valign='middle' align='center'>"+totallp+"</td>"+
                                "<td valign='middle' align='center'>"+registrasibaru+"</td>"+
                                "<td valign='middle' align='center'>"+registrasilama+"</td>"+
                                "<td valign='middle' align='center'>"+polibaru+"</td>"+
                                "<td valign='middle' align='center'>"+polilama+"</td>"+
                                "<td valign='middle' align='center'>"+(polibaru+polilama)+"</td>"+
                            "</tr>"
                        );
                        i++;
                    }
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'></td>"+
                            "<td valign='middle' align='left'>TOTAL</td>"+
                            "<td valign='middle' align='center'>"+ttlhr07l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr07p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr830l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr830p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthk1l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthk1p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth14l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth14p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth59l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth59p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1014l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1014p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1519l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1519p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth2044l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth2044p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth4554l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth4554p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth5559l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth5559p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth6069l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth6069p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthl70l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthl70p+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotall+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotalp+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotallp+"</td>"+
                            "<td valign='middle' align='center'>"+ttlregistrasibaru+"</td>"+
                            "<td valign='middle' align='center'>"+ttlregistrasilama+"</td>"+
                            "<td valign='middle' align='center'>"+ttlpolibaru+"</td>"+
                            "<td valign='middle' align='center'>"+ttlpolilama+"</td>"+
                            "<td valign='middle' align='center'>"+(ttlpolibaru+ttlpolilama)+"</td>"+
                        "</tr>"
                    );
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
            }else if(!nmkecamatan.getText().equals("")){
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%' rowspan='3'>No.</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%' rowspan='3'>Kelurahan</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55%' colspan='26'>Jumlah Kunjungan/Golongan Umur</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='3'>Total</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%' colspan='2'>Jenis Kunjungan</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='13%' colspan='3'>Jumlah Kunjungan/Kasus</td>"+
                    "</tr>"+  
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>0-7Hr</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>8-30hr</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>&lt;1Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>1-4Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>5-9Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>10-14Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>15-19Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>20-44Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>45-54Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>55-59Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>60-69Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>&gt;=70Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>Total<br>L|P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Baru</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Lama</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Baru</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Lama</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Total</td>"+
                    "</tr>"+
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                    "</tr>"
                );

                ps=koneksi.prepareStatement("select kelurahan.kd_kel as kode,kelurahan.nm_kel as nama,"+
                          "kabupaten.kd_kab,kecamatan.kd_kec from reg_periksa "+
                          "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                          "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                          "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                          "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                          "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                          "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and kabupaten.nm_kab='"+nmkabupaten.getText()+"' "+
                          "and kecamatan.nm_kec='"+nmkecamatan.getText()+"' "+querytambahan+" group by kelurahan.nm_kel");
                try {
                    rs=ps.executeQuery();
                    i=1;
                    ttlhr07l=0;ttlhr07p=0;ttlthk1l=0;ttlthk1p=0;ttlth14l=0;ttlth14p=0;ttlth59l=0;ttlth59p=0;ttlth1014l=0;ttlth1014p=0;
                    ttlth1519l=0;ttlth1519p=0;ttlth2044l=0;ttlth2044p=0;ttlth4554l=0;ttlth4554p=0;ttlth5559l=0;ttlth5559p=0;
                    ttlth6069l=0;ttlth6069p=0;ttlthl70l=0;ttlthl70p=0;ttlhr830l=0;ttlhr830p=0;ttltotall=0;ttltotalp=0;ttltotallp=0;
                    ttlregistrasibaru=0;ttlregistrasilama=0;ttlpolilama=0;ttlpolibaru=0;
                    while(rs.next()){
                        hr07l=0;hr07p=0;thk1l=0;thk1p=0;th14l=0;th14p=0;th59l=0;th59p=0;th1014l=0;th1014p=0;
                        th1519l=0;th1519p=0;th2044l=0;th2044p=0;th4554l=0;th4554p=0;th5559l=0;th5559p=0;
                        th6069l=0;th6069p=0;thl70l=0;thl70p=0;hr830l=0;hr830p=0;totall=0;totalp=0;totallp=0;
                        registrasibaru=0;registrasilama=0;polilama=0;polibaru=0;

                        hr07l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '0' and '7' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        hr07p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '0' and '7' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        hr830l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '8' and '31' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        hr830p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '8' and '31' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        thk1l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Bl' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        thk1p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Bl' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th14l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '1' and '4' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th14p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '1' and '4' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th59l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '5' and '9' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th59p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '5' and '9' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th1014l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '10' and '14' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th1014p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '10' and '14' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th1519l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '15' and '19' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th1519p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '15' and '19' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th2044l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '20' and '44' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th2044p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '20' and '44' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th4554l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '45' and '54' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th4554p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '45' and '54' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th5559l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '55' and '59' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th5559p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '55' and '59' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        th6069l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '60' and '69' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        th6069p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '60' and '69' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        thl70l=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar >='70' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kel");
                        thl70p=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar >='70' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kel");
                        totall=hr07l+thk1l+th14l+th59l+th1014l+th1519l+th2044l+th4554l+th5559l+th6069l+thl70l+hr830l;
                        totalp=hr07p+thk1p+th14p+th59p+th1014p+th1519p+th2044p+th4554p+th5559p+th6069p+thl70p+hr830p;
                        totallp=totall+totalp;
                        registrasibaru=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and reg_periksa.stts_daftar='Baru' "+querytambahan+" group by pasien.kd_kel");
                        registrasilama=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and reg_periksa.stts_daftar='Lama' "+querytambahan+" group by pasien.kd_kel");
                        polibaru=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and reg_periksa.status_poli='Baru' "+querytambahan+" group by pasien.kd_kel");
                        polilama=Sequel.cariInteger(
                             "select count(pasien.kd_kel)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kel='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kd_kec")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and reg_periksa.status_poli='Lama' "+querytambahan+" group by pasien.kd_kel");

                        ttlhr07l=ttlhr07l+hr07l;
                        ttlhr07p=ttlhr07p+hr07p;
                        ttlthk1l=ttlthk1l+thk1l;
                        ttlthk1p=ttlthk1p+thk1p;
                        ttlth14l=ttlth14l+th14l;
                        ttlth14p=ttlth14p+th14p;
                        ttlth59l=ttlth59l+th59l;
                        ttlth59p=ttlth59p+th59p;
                        ttlth1014l=ttlth1014l+th1014l;
                        ttlth1014p=ttlth1014p+th1014p;
                        ttlth1519l=ttlth1519l+th1519l;
                        ttlth1519p=ttlth1519p+th1519p;
                        ttlth2044l=ttlth2044l+th2044l;
                        ttlth2044p=ttlth2044p+th2044p;
                        ttlth4554l=ttlth4554l+th4554l;
                        ttlth4554p=ttlth4554p+th4554p;
                        ttlth5559l=ttlth5559l+th5559l;
                        ttlth5559p=ttlth5559p+th5559p;
                        ttlth6069l=ttlth6069l+th6069l;
                        ttlth6069p=ttlth6069p+th6069p;
                        ttlthl70l=ttlthl70l+thl70l;
                        ttlthl70p=ttlthl70p+thl70p;
                        ttlhr830l=ttlhr830l+hr830l;
                        ttlhr830p=ttlhr830p+hr830p;
                        ttltotall=ttltotall+totall;
                        ttltotalp=ttltotalp+totalp;
                        ttltotallp=ttltotallp+totallp;
                        ttlregistrasibaru=ttlregistrasibaru+registrasibaru;
                        ttlregistrasilama=ttlregistrasilama+registrasilama;
                        ttlpolilama=ttlpolilama+polilama;
                        ttlpolibaru=ttlpolibaru+polibaru;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+i+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama")+"</td>"+
                                "<td valign='middle' align='center'>"+hr07l+"</td>"+
                                "<td valign='middle' align='center'>"+hr07p+"</td>"+
                                "<td valign='middle' align='center'>"+hr830l+"</td>"+
                                "<td valign='middle' align='center'>"+hr830p+"</td>"+
                                "<td valign='middle' align='center'>"+thk1l+"</td>"+
                                "<td valign='middle' align='center'>"+thk1p+"</td>"+
                                "<td valign='middle' align='center'>"+th14l+"</td>"+
                                "<td valign='middle' align='center'>"+th14p+"</td>"+
                                "<td valign='middle' align='center'>"+th59l+"</td>"+
                                "<td valign='middle' align='center'>"+th59p+"</td>"+
                                "<td valign='middle' align='center'>"+th1014l+"</td>"+
                                "<td valign='middle' align='center'>"+th1014p+"</td>"+
                                "<td valign='middle' align='center'>"+th1519l+"</td>"+
                                "<td valign='middle' align='center'>"+th1519p+"</td>"+
                                "<td valign='middle' align='center'>"+th2044l+"</td>"+
                                "<td valign='middle' align='center'>"+th2044p+"</td>"+
                                "<td valign='middle' align='center'>"+th4554l+"</td>"+
                                "<td valign='middle' align='center'>"+th4554p+"</td>"+
                                "<td valign='middle' align='center'>"+th5559l+"</td>"+
                                "<td valign='middle' align='center'>"+th5559p+"</td>"+
                                "<td valign='middle' align='center'>"+th6069l+"</td>"+
                                "<td valign='middle' align='center'>"+th6069p+"</td>"+
                                "<td valign='middle' align='center'>"+thl70l+"</td>"+
                                "<td valign='middle' align='center'>"+thl70p+"</td>"+
                                "<td valign='middle' align='center'>"+totall+"</td>"+
                                "<td valign='middle' align='center'>"+totalp+"</td>"+
                                "<td valign='middle' align='center'>"+totallp+"</td>"+
                                "<td valign='middle' align='center'>"+registrasibaru+"</td>"+
                                "<td valign='middle' align='center'>"+registrasilama+"</td>"+
                                "<td valign='middle' align='center'>"+polibaru+"</td>"+
                                "<td valign='middle' align='center'>"+polilama+"</td>"+
                                "<td valign='middle' align='center'>"+(polibaru+polilama)+"</td>"+
                            "</tr>"
                        );
                        i++;
                    }
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'></td>"+
                            "<td valign='middle' align='left'>TOTAL</td>"+
                            "<td valign='middle' align='center'>"+ttlhr07l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr07p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr830l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr830p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthk1l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthk1p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth14l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth14p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth59l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth59p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1014l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1014p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1519l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1519p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth2044l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth2044p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth4554l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth4554p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth5559l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth5559p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth6069l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth6069p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthl70l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthl70p+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotall+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotalp+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotallp+"</td>"+
                            "<td valign='middle' align='center'>"+ttlregistrasibaru+"</td>"+
                            "<td valign='middle' align='center'>"+ttlregistrasilama+"</td>"+
                            "<td valign='middle' align='center'>"+ttlpolibaru+"</td>"+
                            "<td valign='middle' align='center'>"+ttlpolilama+"</td>"+
                            "<td valign='middle' align='center'>"+(ttlpolibaru+ttlpolilama)+"</td>"+
                        "</tr>"
                    );
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
            }else if(!nmkabupaten.getText().equals("")){
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%' rowspan='3'>No.</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%' rowspan='3'>Kecamatan</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55%' colspan='26'>Jumlah Kunjungan/Golongan Umur</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='3'>Total</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%' colspan='2'>Jenis Kunjungan</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='13%' colspan='3'>Jumlah Kunjungan/Kasus</td>"+
                    "</tr>"+  
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>0-7Hr</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>8-30hr</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>&lt;1Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>1-4Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>5-9Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>10-14Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>15-19Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>20-44Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>45-54Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>55-59Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>60-69Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>&gt;=70Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>Total<br>L|P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Baru</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Lama</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Baru</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Lama</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Total</td>"+
                    "</tr>"+
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                    "</tr>"
                );

                ps=koneksi.prepareStatement("select kecamatan.kd_kec as kode,kecamatan.nm_kec as nama,kabupaten.kd_kab from reg_periksa "+
                          "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                          "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                          "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                          "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                          "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and kabupaten.nm_kab='"+nmkabupaten.getText()+"' "+
                          querytambahan+" group by kecamatan.nm_kec");
                try {
                    rs=ps.executeQuery();
                    i=1;
                    ttlhr07l=0;ttlhr07p=0;ttlthk1l=0;ttlthk1p=0;ttlth14l=0;ttlth14p=0;ttlth59l=0;ttlth59p=0;ttlth1014l=0;ttlth1014p=0;
                    ttlth1519l=0;ttlth1519p=0;ttlth2044l=0;ttlth2044p=0;ttlth4554l=0;ttlth4554p=0;ttlth5559l=0;ttlth5559p=0;
                    ttlth6069l=0;ttlth6069p=0;ttlthl70l=0;ttlthl70p=0;ttlhr830l=0;ttlhr830p=0;ttltotall=0;ttltotalp=0;ttltotallp=0;
                    ttlregistrasibaru=0;ttlregistrasilama=0;ttlpolilama=0;ttlpolibaru=0;
                    while(rs.next()){
                        hr07l=0;hr07p=0;thk1l=0;thk1p=0;th14l=0;th14p=0;th59l=0;th59p=0;th1014l=0;th1014p=0;
                        th1519l=0;th1519p=0;th2044l=0;th2044p=0;th4554l=0;th4554p=0;th5559l=0;th5559p=0;
                        th6069l=0;th6069p=0;thl70l=0;thl70p=0;hr830l=0;hr830p=0;totall=0;totalp=0;totallp=0;
                        registrasibaru=0;registrasilama=0;polilama=0;polibaru=0;

                        hr07l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '0' and '7' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        hr07p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '0' and '7' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        hr830l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '8' and '31' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        hr830p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '8' and '31' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        thk1l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Bl' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        thk1p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Bl' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        th14l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '1' and '4' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        th14p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '1' and '4' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        th59l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '5' and '9' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        th59p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '5' and '9' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        th1014l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '10' and '14' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        th1014p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '10' and '14' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        th1519l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '15' and '19' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        th1519p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '15' and '19' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        th2044l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '20' and '44' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        th2044p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '20' and '44' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        th4554l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '45' and '54' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        th4554p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '45' and '54' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        th5559l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '55' and '59' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        th5559p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '55' and '59' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        th6069l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '60' and '69' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        th6069p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '60' and '69' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        thl70l=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar >='70' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kec");
                        thl70p=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar >='70' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kec");
                        totall=hr07l+thk1l+th14l+th59l+th1014l+th1519l+th2044l+th4554l+th5559l+th6069l+thl70l+hr830l;
                        totalp=hr07p+thk1p+th14p+th59p+th1014p+th1519p+th2044p+th4554p+th5559p+th6069p+thl70p+hr830p;
                        totallp=totall+totalp;
                        registrasibaru=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and reg_periksa.stts_daftar='Baru' "+querytambahan+" group by pasien.kd_kec");
                        registrasilama=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and reg_periksa.stts_daftar='Lama' "+querytambahan+" group by pasien.kd_kec");
                        polibaru=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and reg_periksa.status_poli='Baru' "+querytambahan+" group by pasien.kd_kec");
                        polilama=Sequel.cariInteger(
                             "select count(pasien.kd_kec)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kec='"+rs.getString("kode")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kd_kab")+"' "+
                             "and reg_periksa.status_poli='Lama' "+querytambahan+" group by pasien.kd_kec");

                        ttlhr07l=ttlhr07l+hr07l;
                        ttlhr07p=ttlhr07p+hr07p;
                        ttlthk1l=ttlthk1l+thk1l;
                        ttlthk1p=ttlthk1p+thk1p;
                        ttlth14l=ttlth14l+th14l;
                        ttlth14p=ttlth14p+th14p;
                        ttlth59l=ttlth59l+th59l;
                        ttlth59p=ttlth59p+th59p;
                        ttlth1014l=ttlth1014l+th1014l;
                        ttlth1014p=ttlth1014p+th1014p;
                        ttlth1519l=ttlth1519l+th1519l;
                        ttlth1519p=ttlth1519p+th1519p;
                        ttlth2044l=ttlth2044l+th2044l;
                        ttlth2044p=ttlth2044p+th2044p;
                        ttlth4554l=ttlth4554l+th4554l;
                        ttlth4554p=ttlth4554p+th4554p;
                        ttlth5559l=ttlth5559l+th5559l;
                        ttlth5559p=ttlth5559p+th5559p;
                        ttlth6069l=ttlth6069l+th6069l;
                        ttlth6069p=ttlth6069p+th6069p;
                        ttlthl70l=ttlthl70l+thl70l;
                        ttlthl70p=ttlthl70p+thl70p;
                        ttlhr830l=ttlhr830l+hr830l;
                        ttlhr830p=ttlhr830p+hr830p;
                        ttltotall=ttltotall+totall;
                        ttltotalp=ttltotalp+totalp;
                        ttltotallp=ttltotallp+totallp;
                        ttlregistrasibaru=ttlregistrasibaru+registrasibaru;
                        ttlregistrasilama=ttlregistrasilama+registrasilama;
                        ttlpolilama=ttlpolilama+polilama;
                        ttlpolibaru=ttlpolibaru+polibaru;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+i+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama")+"</td>"+
                                "<td valign='middle' align='center'>"+hr07l+"</td>"+
                                "<td valign='middle' align='center'>"+hr07p+"</td>"+
                                "<td valign='middle' align='center'>"+hr830l+"</td>"+
                                "<td valign='middle' align='center'>"+hr830p+"</td>"+
                                "<td valign='middle' align='center'>"+thk1l+"</td>"+
                                "<td valign='middle' align='center'>"+thk1p+"</td>"+
                                "<td valign='middle' align='center'>"+th14l+"</td>"+
                                "<td valign='middle' align='center'>"+th14p+"</td>"+
                                "<td valign='middle' align='center'>"+th59l+"</td>"+
                                "<td valign='middle' align='center'>"+th59p+"</td>"+
                                "<td valign='middle' align='center'>"+th1014l+"</td>"+
                                "<td valign='middle' align='center'>"+th1014p+"</td>"+
                                "<td valign='middle' align='center'>"+th1519l+"</td>"+
                                "<td valign='middle' align='center'>"+th1519p+"</td>"+
                                "<td valign='middle' align='center'>"+th2044l+"</td>"+
                                "<td valign='middle' align='center'>"+th2044p+"</td>"+
                                "<td valign='middle' align='center'>"+th4554l+"</td>"+
                                "<td valign='middle' align='center'>"+th4554p+"</td>"+
                                "<td valign='middle' align='center'>"+th5559l+"</td>"+
                                "<td valign='middle' align='center'>"+th5559p+"</td>"+
                                "<td valign='middle' align='center'>"+th6069l+"</td>"+
                                "<td valign='middle' align='center'>"+th6069p+"</td>"+
                                "<td valign='middle' align='center'>"+thl70l+"</td>"+
                                "<td valign='middle' align='center'>"+thl70p+"</td>"+
                                "<td valign='middle' align='center'>"+totall+"</td>"+
                                "<td valign='middle' align='center'>"+totalp+"</td>"+
                                "<td valign='middle' align='center'>"+totallp+"</td>"+
                                "<td valign='middle' align='center'>"+registrasibaru+"</td>"+
                                "<td valign='middle' align='center'>"+registrasilama+"</td>"+
                                "<td valign='middle' align='center'>"+polibaru+"</td>"+
                                "<td valign='middle' align='center'>"+polilama+"</td>"+
                                "<td valign='middle' align='center'>"+(polibaru+polilama)+"</td>"+
                            "</tr>"
                        );
                        i++;
                    }
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'></td>"+
                            "<td valign='middle' align='left'>TOTAL</td>"+
                            "<td valign='middle' align='center'>"+ttlhr07l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr07p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr830l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr830p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthk1l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthk1p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth14l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth14p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth59l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth59p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1014l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1014p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1519l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1519p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth2044l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth2044p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth4554l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth4554p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth5559l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth5559p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth6069l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth6069p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthl70l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthl70p+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotall+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotalp+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotallp+"</td>"+
                            "<td valign='middle' align='center'>"+ttlregistrasibaru+"</td>"+
                            "<td valign='middle' align='center'>"+ttlregistrasilama+"</td>"+
                            "<td valign='middle' align='center'>"+ttlpolibaru+"</td>"+
                            "<td valign='middle' align='center'>"+ttlpolilama+"</td>"+
                            "<td valign='middle' align='center'>"+(ttlpolibaru+ttlpolilama)+"</td>"+
                        "</tr>"
                    );
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
            }else if(nmkabupaten.getText().equals("")){
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%' rowspan='3'>No.</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%' rowspan='3'>Kabupaten</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55%' colspan='26'>Jumlah Kunjungan/Golongan Umur</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='3'>Total</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%' colspan='2'>Jenis Kunjungan</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' width='13%' colspan='3'>Jumlah Kunjungan/Kasus</td>"+
                    "</tr>"+  
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>0-7Hr</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>8-30hr</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>&lt;1Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>1-4Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>5-9Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>10-14Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>15-19Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>20-44Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>45-54Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>55-59Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>60-69Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>&gt;=70Th</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>Total<br>L|P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Baru</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Lama</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Baru</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Lama</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>Total</td>"+
                    "</tr>"+
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>L</td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>P</td>"+
                    "</tr>"
                );

                ps=koneksi.prepareStatement("select kabupaten.kd_kab as kode,kabupaten.nm_kab as nama from reg_periksa "+
                          "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                          "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                          "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                          "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+querytambahan+" group by kabupaten.nm_kab");
                try {
                    rs=ps.executeQuery();
                    i=1;
                    ttlhr07l=0;ttlhr07p=0;ttlthk1l=0;ttlthk1p=0;ttlth14l=0;ttlth14p=0;ttlth59l=0;ttlth59p=0;ttlth1014l=0;ttlth1014p=0;
                    ttlth1519l=0;ttlth1519p=0;ttlth2044l=0;ttlth2044p=0;ttlth4554l=0;ttlth4554p=0;ttlth5559l=0;ttlth5559p=0;
                    ttlth6069l=0;ttlth6069p=0;ttlthl70l=0;ttlthl70p=0;ttlhr830l=0;ttlhr830p=0;ttltotall=0;ttltotalp=0;ttltotallp=0;
                    ttlregistrasibaru=0;ttlregistrasilama=0;ttlpolilama=0;ttlpolibaru=0;
                    while(rs.next()){
                        hr07l=0;hr07p=0;thk1l=0;thk1p=0;th14l=0;th14p=0;th59l=0;th59p=0;th1014l=0;th1014p=0;
                        th1519l=0;th1519p=0;th2044l=0;th2044p=0;th4554l=0;th4554p=0;th5559l=0;th5559p=0;
                        th6069l=0;th6069p=0;thl70l=0;thl70p=0;hr830l=0;hr830p=0;totall=0;totalp=0;totallp=0;
                        registrasibaru=0;registrasilama=0;polilama=0;polibaru=0;

                        hr07l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '0' and '7' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        hr07p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '0' and '7' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        hr830l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '8' and '31' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        hr830p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Hr' and umurdaftar between '8' and '31' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        thk1l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Bl' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        thk1p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Bl' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        th14l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '1' and '4' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        th14p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '1' and '4' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        th59l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '5' and '9' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        th59p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '5' and '9' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        th1014l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '10' and '14' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        th1014p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '10' and '14' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        th1519l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '15' and '19' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        th1519p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '15' and '19' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        th2044l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '20' and '44' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        th2044p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '20' and '44' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        th4554l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '45' and '54' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        th4554p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '45' and '54' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        th5559l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '55' and '59' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        th5559p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '55' and '59' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        th6069l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '60' and '69' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        th6069p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar between '60' and '69' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        thl70l=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar >='70' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='L' "+querytambahan+" group by pasien.kd_kab");
                        thl70p=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and sttsumur='Th' and umurdaftar >='70' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and pasien.jk='P' "+querytambahan+" group by pasien.kd_kab");
                        totall=hr07l+thk1l+th14l+th59l+th1014l+th1519l+th2044l+th4554l+th5559l+th6069l+thl70l+hr830l;
                        totalp=hr07p+thk1p+th14p+th59p+th1014p+th1519p+th2044p+th4554p+th5559p+th6069p+thl70p+hr830p;
                        totallp=totall+totalp;
                        registrasibaru=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and reg_periksa.stts_daftar='Baru' "+querytambahan+" group by pasien.kd_kab");
                        registrasilama=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and reg_periksa.stts_daftar='Lama' "+querytambahan+" group by pasien.kd_kab");
                        polibaru=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and reg_periksa.status_poli='Baru' "+querytambahan+" group by pasien.kd_kab");
                        polilama=Sequel.cariInteger(
                             "select count(pasien.kd_kab)as jumlah from reg_periksa "+
                             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                             "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' "+
                             "and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
                             "and pasien.kd_kab='"+rs.getString("kode")+"' "+
                             "and reg_periksa.status_poli='Lama' "+querytambahan+" group by pasien.kd_kab");

                        ttlhr07l=ttlhr07l+hr07l;
                        ttlhr07p=ttlhr07p+hr07p;
                        ttlthk1l=ttlthk1l+thk1l;
                        ttlthk1p=ttlthk1p+thk1p;
                        ttlth14l=ttlth14l+th14l;
                        ttlth14p=ttlth14p+th14p;
                        ttlth59l=ttlth59l+th59l;
                        ttlth59p=ttlth59p+th59p;
                        ttlth1014l=ttlth1014l+th1014l;
                        ttlth1014p=ttlth1014p+th1014p;
                        ttlth1519l=ttlth1519l+th1519l;
                        ttlth1519p=ttlth1519p+th1519p;
                        ttlth2044l=ttlth2044l+th2044l;
                        ttlth2044p=ttlth2044p+th2044p;
                        ttlth4554l=ttlth4554l+th4554l;
                        ttlth4554p=ttlth4554p+th4554p;
                        ttlth5559l=ttlth5559l+th5559l;
                        ttlth5559p=ttlth5559p+th5559p;
                        ttlth6069l=ttlth6069l+th6069l;
                        ttlth6069p=ttlth6069p+th6069p;
                        ttlthl70l=ttlthl70l+thl70l;
                        ttlthl70p=ttlthl70p+thl70p;
                        ttlhr830l=ttlhr830l+hr830l;
                        ttlhr830p=ttlhr830p+hr830p;
                        ttltotall=ttltotall+totall;
                        ttltotalp=ttltotalp+totalp;
                        ttltotallp=ttltotallp+totallp;
                        ttlregistrasibaru=ttlregistrasibaru+registrasibaru;
                        ttlregistrasilama=ttlregistrasilama+registrasilama;
                        ttlpolilama=ttlpolilama+polilama;
                        ttlpolibaru=ttlpolibaru+polibaru;

                        htmlContent.append(
                            "<tr class='isi'>"+
                                "<td valign='middle' align='center'>"+i+"</td>"+
                                "<td valign='middle' align='left'>"+rs.getString("nama")+"</td>"+
                                "<td valign='middle' align='center'>"+hr07l+"</td>"+
                                "<td valign='middle' align='center'>"+hr07p+"</td>"+
                                "<td valign='middle' align='center'>"+hr830l+"</td>"+
                                "<td valign='middle' align='center'>"+hr830p+"</td>"+
                                "<td valign='middle' align='center'>"+thk1l+"</td>"+
                                "<td valign='middle' align='center'>"+thk1p+"</td>"+
                                "<td valign='middle' align='center'>"+th14l+"</td>"+
                                "<td valign='middle' align='center'>"+th14p+"</td>"+
                                "<td valign='middle' align='center'>"+th59l+"</td>"+
                                "<td valign='middle' align='center'>"+th59p+"</td>"+
                                "<td valign='middle' align='center'>"+th1014l+"</td>"+
                                "<td valign='middle' align='center'>"+th1014p+"</td>"+
                                "<td valign='middle' align='center'>"+th1519l+"</td>"+
                                "<td valign='middle' align='center'>"+th1519p+"</td>"+
                                "<td valign='middle' align='center'>"+th2044l+"</td>"+
                                "<td valign='middle' align='center'>"+th2044p+"</td>"+
                                "<td valign='middle' align='center'>"+th4554l+"</td>"+
                                "<td valign='middle' align='center'>"+th4554p+"</td>"+
                                "<td valign='middle' align='center'>"+th5559l+"</td>"+
                                "<td valign='middle' align='center'>"+th5559p+"</td>"+
                                "<td valign='middle' align='center'>"+th6069l+"</td>"+
                                "<td valign='middle' align='center'>"+th6069p+"</td>"+
                                "<td valign='middle' align='center'>"+thl70l+"</td>"+
                                "<td valign='middle' align='center'>"+thl70p+"</td>"+
                                "<td valign='middle' align='center'>"+totall+"</td>"+
                                "<td valign='middle' align='center'>"+totalp+"</td>"+
                                "<td valign='middle' align='center'>"+totallp+"</td>"+
                                "<td valign='middle' align='center'>"+registrasibaru+"</td>"+
                                "<td valign='middle' align='center'>"+registrasilama+"</td>"+
                                "<td valign='middle' align='center'>"+polibaru+"</td>"+
                                "<td valign='middle' align='center'>"+polilama+"</td>"+
                                "<td valign='middle' align='center'>"+(polibaru+polilama)+"</td>"+
                            "</tr>"
                        );
                        i++;
                    }
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'></td>"+
                            "<td valign='middle' align='left'>TOTAL</td>"+
                            "<td valign='middle' align='center'>"+ttlhr07l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr07p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr830l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlhr830p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthk1l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthk1p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth14l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth14p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth59l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth59p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1014l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1014p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1519l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth1519p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth2044l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth2044p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth4554l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth4554p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth5559l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth5559p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth6069l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlth6069p+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthl70l+"</td>"+
                            "<td valign='middle' align='center'>"+ttlthl70p+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotall+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotalp+"</td>"+
                            "<td valign='middle' align='center'>"+ttltotallp+"</td>"+
                            "<td valign='middle' align='center'>"+ttlregistrasibaru+"</td>"+
                            "<td valign='middle' align='center'>"+ttlregistrasilama+"</td>"+
                            "<td valign='middle' align='center'>"+ttlpolibaru+"</td>"+
                            "<td valign='middle' align='center'>"+ttlpolilama+"</td>"+
                            "<td valign='middle' align='center'>"+(ttlpolibaru+ttlpolilama)+"</td>"+
                        "</tr>"
                    );
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
            
            LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("laporan.DlgRL4A.prosesCari() 5 : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getdemografi_umur_kunjungan());
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
}

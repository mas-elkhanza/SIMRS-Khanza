package laporan;
import fungsi.koneksiDB;
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariCaraBayar;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;

public class DlgRL4B extends javax.swing.JDialog {
    private final validasi Valid=new validasi();
    private final Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2;
    private StringBuilder htmlContent;
    private int hr0s6l=0,hr0s6p=0,hr7s28l=0,hr7s28p=0,hr28s1thl=0,hr28s1thp=0,th1s4l=0,th1s4p=0,th5s14l=0,th5s14p=0,
                th15s24l=0,th15s24p=0,th25s44l=0,th25s44p=0,th45s64l=0,th45s64p=0,lbth65l=0,lbth65p=0,mati=0;
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgKabupaten kabupaten=new DlgKabupaten(null,false);
    private DlgKecamatan kecamatan=new DlgKecamatan(null,false);
    private DlgKelurahan kelurahan=new DlgKelurahan(null,false);
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRL4B(java.awt.Frame parent, boolean modal) {
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
        
        ChkInput.setSelected(false);
        isForm();
    }
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
        label9 = new widget.Label();
        BtnAll = new widget.Button();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ RL 4B Data Keadaan Morbiditas Pasien Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(100, 30));
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
        label21.setBounds(449, 10, 87, 23);

        nmkabupaten.setEditable(false);
        nmkabupaten.setName("nmkabupaten"); // NOI18N
        nmkabupaten.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkabupaten);
        nmkabupaten.setBounds(539, 10, 260, 23);

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
        BtnSeek5.setBounds(802, 10, 28, 23);

        label22.setText("Kecamatan :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label22);
        label22.setBounds(449, 40, 87, 23);

        nmkecamatan.setEditable(false);
        nmkecamatan.setName("nmkecamatan"); // NOI18N
        nmkecamatan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkecamatan);
        nmkecamatan.setBounds(539, 40, 260, 23);

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
        BtnSeek6.setBounds(802, 40, 28, 23);

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
        BtnSeek7.setBounds(802, 70, 28, 23);

        nmkelurahan.setEditable(false);
        nmkelurahan.setName("nmkelurahan"); // NOI18N
        nmkelurahan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkelurahan);
        nmkelurahan.setBounds(539, 70, 260, 23);

        label23.setText("Kelurahan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label23);
        label23.setBounds(449, 70, 87, 23);

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
                                    "<font size='2' face='Tahoma'>DATA KEADAAN MORBIDITAS PASIEN RAWAT JALAN RUMAH SAKIT PENYEBAB KECELAKAN<br>PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
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
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
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
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
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
        kecamatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kecamatan.setLocationRelativeTo(internalFrame1);
        kecamatan.setAlwaysOnTop(false);
        kecamatan.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void BtnSeek6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek6KeyPressed

    private void BtnSeek7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek7ActionPerformed
        kelurahan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kelurahan.setLocationRelativeTo(internalFrame1);
        kelurahan.setAlwaysOnTop(false);
        kelurahan.setVisible(true);
    }//GEN-LAST:event_BtnSeek7ActionPerformed

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
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRL4B dialog = new DlgRL4B(new javax.swing.JFrame(), true);
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
            htmlContent.append(                             
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%' rowspan='3'>No.Urut</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='3'>No.Daftar Terperinci</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='18%' rowspan='3'>Golongan Sebab Penyakit</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55%' colspan='18'>Jumlah Pasien Hidup dan Mati menurut Golongan Umur & Jenis Kelamin</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%' colspan='2'>Pasien Keluar (Hidup & Mati) Menurut Jenis Kelamin</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='3'>Jumlah Pasien Keluar Hidup (23+24)</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%' rowspan='3'>Jumlah Pasien Keluar Mati</td>"+
                "</tr>"+
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>0-6 hr</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>7-28hr</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>28hr-<1th</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>1-4th</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>5-14th</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>15-24th</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>25-44th</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>45-64th</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' colspan='2'>> 65</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>LK</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' rowspan='2'>PR</td>"+
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
                "</tr>"+
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>1</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>2</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>3</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>4</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>5</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>6</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>7</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>8</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>9</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>10</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>11</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>12</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>13</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>14</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>15</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>16</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>17</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>18</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>19</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>20</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>21</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>22</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>23</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>24</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>25</td>"+
                "</tr>"
            );            
            ps=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as nm_penyakit from diagnosa_pasien "+
                    "inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "inner join reg_periksa on reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "where diagnosa_pasien.status='Ralan' and status_penyakit='Baru' and reg_periksa.tgl_registrasi between ? and ?  "+
                    (nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")?
                    "":"and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? ")+
                    "and (left(diagnosa_pasien.kd_penyakit,1)<>'V' or left(diagnosa_pasien.kd_penyakit,1)<>'W' or left(diagnosa_pasien.kd_penyakit,1)<>'X' or left(diagnosa_pasien.kd_penyakit,1)<>'Y') "+
                    " group by diagnosa_pasien.kd_penyakit order by diagnosa_pasien.kd_penyakit");
            try {
                if(nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(4,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(5,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    hr0s6l=0;hr0s6p=0;hr7s28l=0;hr7s28p=0;hr28s1thl=0;hr28s1thp=0;th1s4l=0;th1s4p=0;th5s14l=0;th5s14p=0;
                    th15s24l=0;th15s24p=0;th25s44l=0;th25s44p=0;th45s64l=0;th45s64p=0;lbth65l=0;lbth65p=0;mati=0;
                    ps2=koneksi.prepareStatement(
                            "select diagnosa_pasien.kd_penyakit,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.jk from diagnosa_pasien "+
                            "inner join reg_periksa on reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                            "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "where diagnosa_pasien.status='Ralan' and status_penyakit='Baru' and reg_periksa.tgl_registrasi between ? and ? and diagnosa_pasien.kd_penyakit=? "+
                            (nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")?
                            "":"and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? "));
                    try {
                        if(nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")){
                            ps2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps2.setString(3,rs.getString("kd_penyakit"));
                        }else{
                            ps2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps2.setString(3,rs.getString("kd_penyakit"));
                            ps2.setString(4,"%"+nmpoli.getText().trim()+"%");
                            ps2.setString(5,"%"+nmdokter.getText().trim()+"%");
                            ps2.setString(6,"%"+nmpenjab.getText().trim()+"%");
                            ps2.setString(7,"%"+nmkabupaten.getText().trim()+"%");
                            ps2.setString(8,"%"+nmkecamatan.getText().trim()+"%");
                            ps2.setString(9,"%"+nmkelurahan.getText().trim()+"%");
                        }
                            
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            if(rs2.getString("sttsumur").equals("Hr")){
                                if((rs2.getInt("umurdaftar")>=0)&&(rs2.getInt("umurdaftar")<=6)){
                                    if(rs2.getString("jk").equals("L")){
                                        hr0s6l++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        hr0s6p++;
                                    }
                                }else if((rs2.getInt("umurdaftar")>=7)&&(rs2.getInt("umurdaftar")<=28)){
                                    if(rs2.getString("jk").equals("L")){
                                        hr7s28l++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        hr7s28p++;
                                    }
                                }else if(rs2.getInt("umurdaftar")>28){
                                    if(rs2.getString("jk").equals("L")){
                                        hr28s1thl++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        hr28s1thp++;
                                    }
                                }
                            }else if(rs2.getString("sttsumur").equals("Bl")){
                                if(rs2.getString("jk").equals("L")){
                                    hr28s1thl++;
                                }else if(rs2.getString("jk").equals("P")){
                                    hr28s1thp++;
                                }
                            }else if(rs2.getString("sttsumur").equals("Th")){
                                if((rs2.getInt("umurdaftar")>=0)&&(rs2.getInt("umurdaftar")<=4)){
                                    if(rs2.getString("jk").equals("L")){
                                        th1s4l++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        th1s4p++;
                                    }
                                }else if((rs2.getInt("umurdaftar")>=5)&&(rs2.getInt("umurdaftar")<=14)){
                                    if(rs2.getString("jk").equals("L")){
                                        th5s14l++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        th5s14p++;
                                    }
                                }else if((rs2.getInt("umurdaftar")>=15)&&(rs2.getInt("umurdaftar")<=24)){
                                    if(rs2.getString("jk").equals("L")){
                                        th15s24l++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        th15s24p++;
                                    }
                                }else if((rs2.getInt("umurdaftar")>=25)&&(rs2.getInt("umurdaftar")<=44)){
                                    if(rs2.getString("jk").equals("L")){
                                        th25s44l++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        th25s44p++;
                                    }
                                }else if((rs2.getInt("umurdaftar")>=45)&&(rs2.getInt("umurdaftar")<=64)){
                                    if(rs2.getString("jk").equals("L")){
                                        th45s64l++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        th45s64p++;
                                    }
                                }else if(rs2.getInt("umurdaftar")>=65){
                                    if(rs2.getString("jk").equals("L")){
                                        lbth65l++;
                                    }else if(rs2.getString("jk").equals("P")){
                                        lbth65p++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("laporan.DlgRL4B.prosesCari() 1 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps3=koneksi.prepareStatement(
                            "select count(pasien_mati.no_rkm_medis) from diagnosa_pasien "+
                            "inner join reg_periksa on reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join pasien_mati on pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                            "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                            "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                            "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                            "where diagnosa_pasien.status='Ralan' and status_penyakit='Baru' and reg_periksa.tgl_registrasi between ? and ? and diagnosa_pasien.kd_penyakit=? "+
                            (nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")?
                            "":"and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? ")+
                            "group by diagnosa_pasien.kd_penyakit");
                    try {
                        if(nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")){
                            ps3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps3.setString(3,rs.getString("kd_penyakit"));
                        }else{
                            ps3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps3.setString(3,rs.getString("kd_penyakit"));
                            ps3.setString(4,"%"+nmpoli.getText().trim()+"%");
                            ps3.setString(5,"%"+nmdokter.getText().trim()+"%");
                            ps3.setString(6,"%"+nmpenjab.getText().trim()+"%");
                            ps3.setString(7,"%"+nmkabupaten.getText().trim()+"%");
                            ps3.setString(8,"%"+nmkecamatan.getText().trim()+"%");
                            ps3.setString(9,"%"+nmkelurahan.getText().trim()+"%");
                        }
                            
                        rs2=ps3.executeQuery();
                        while(rs2.next()){
                            mati=rs2.getInt(1);
                        }
                    } catch (Exception e) {
                        System.out.println("laporan.DlgRL4A.prosesCari() 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='middle' align='center'>"+i+"</td>"+
                            "<td valign='middle' align='center'>"+rs.getString("kd_penyakit")+"</td>"+
                            "<td valign='middle' align='left'>"+rs.getString("nm_penyakit")+"</td>"+
                            "<td valign='middle' align='center'>"+hr0s6l+"</td>"+
                            "<td valign='middle' align='center'>"+hr0s6p+"</td>"+
                            "<td valign='middle' align='center'>"+hr7s28l+"</td>"+
                            "<td valign='middle' align='center'>"+hr7s28p+"</td>"+
                            "<td valign='middle' align='center'>"+hr28s1thl+"</td>"+
                            "<td valign='middle' align='center'>"+hr28s1thp+"</td>"+
                            "<td valign='middle' align='center'>"+th1s4l+"</td>"+
                            "<td valign='middle' align='center'>"+th1s4p+"</td>"+
                            "<td valign='middle' align='center'>"+th5s14l+"</td>"+
                            "<td valign='middle' align='center'>"+th5s14p+"</td>"+
                            "<td valign='middle' align='center'>"+th15s24l+"</td>"+
                            "<td valign='middle' align='center'>"+th15s24p+"</td>"+
                            "<td valign='middle' align='center'>"+th25s44l+"</td>"+
                            "<td valign='middle' align='center'>"+th25s44p+"</td>"+
                            "<td valign='middle' align='center'>"+th45s64l+"</td>"+
                            "<td valign='middle' align='center'>"+th45s64p+"</td>"+
                            "<td valign='middle' align='center'>"+lbth65l+"</td>"+
                            "<td valign='middle' align='center'>"+lbth65p+"</td>"+
                            "<td valign='middle' align='center'>"+(hr0s6l+hr7s28l+hr28s1thl+th1s4l+th5s14l+th15s24l+th25s44l+th45s64l+lbth65l)+"</td>"+
                            "<td valign='middle' align='center'>"+(hr0s6p+hr7s28p+hr28s1thp+th1s4p+th5s14p+th15s24p+th25s44p+th45s64p+lbth65p)+"</td>"+
                            "<td valign='middle' align='center'>"+(hr0s6l+hr7s28l+hr28s1thl+th1s4l+th5s14l+th15s24l+th25s44l+th45s64l+lbth65l+hr0s6p+hr7s28p+hr28s1thp+th1s4p+th5s14p+th15s24p+th25s44p+th45s64p+lbth65p-mati)+"</td>"+
                            "<td valign='middle' align='center'>"+mati+"</td>"+
                        "</tr>"
                    );
                    i++;
                }
            } catch (Exception e) {
                System.out.println("laporan.DlgRL4A.prosesCari() 3 : "+e);
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
        BtnPrint.setEnabled(akses.getrl4bsebab());
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

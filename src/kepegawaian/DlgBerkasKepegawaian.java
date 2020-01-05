package kepegawaian;

import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author perpustakaan
 */
public final class DlgBerkasKepegawaian extends javax.swing.JDialog {
    private final JFXPanel jfxPanel = new JFXPanel();
    private final JTextField txtURL = new JTextField();
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel lblStatus = new JLabel();
    private final Properties prop = new Properties(); 
    private WebEngine engine;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private int i=0,i2=0;
    private StringBuilder htmlContent;
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgBerkasKepegawaian(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        
        initComponents2();
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));                                
        } catch (Exception e) {
        }
        
        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        TNm.setDocument(new batasInput((byte)50).getKata(TNm));

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
                    TKd.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                    TNm.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                    JK.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),2).toString());
                    Pendidikan.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),10).toString());
                    Bidang.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),6).toString());
                    Departemen.setText(Sequel.cariIsi("select nama from departemen where dep_id=?",pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),5).toString()));
                    Jabatan.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),3).toString());
                    JenjangJabatan.setText(Sequel.cariIsi("select nama from jnj_jabatan where kode=?",pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),4).toString()));
 
                    try {
                        loadURL("http://" +koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/loginberkaspegawai.php?act=login&usere=admin&passwordte=akusayangsamakamu&nik="+TKd.getText().replaceAll(" ","_")+"&kategori="+CmbKategori.getSelectedItem().toString().replaceAll(" ","_")+"");
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                    }
                }   
                TKd.requestFocus();
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML1.setEditable(true);
        LoadHTML1.setEditorKit(kit);
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
        LoadHTML3.setEditable(true);
        LoadHTML3.setEditorKit(kit);
        LoadHTML4.setEditable(true);
        LoadHTML4.setEditorKit(kit);
        LoadHTML5.setEditable(true);
        LoadHTML5.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML1.setDocument(doc);
        LoadHTML2.setDocument(doc);
        LoadHTML3.setDocument(doc);
        LoadHTML4.setDocument(doc);
        LoadHTML5.setDocument(doc);
        
        LoadHTML1.setEditable(false);
        LoadHTML2.setEditable(false);
        LoadHTML3.setEditable(false);
        LoadHTML4.setEditable(false);
        LoadHTML5.setEditable(false);
        
        LoadHTML1.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML2.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML3.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML4.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML5.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
    }    
    
    private void initComponents2() {           
        txtURL.addActionListener((ActionEvent e) -> {
            loadURL(txtURL.getText());
        });
  
        progressBar.setPreferredSize(new Dimension(150, 18));
        progressBar.setStringPainted(true);
        
        PanelContent.add(jfxPanel, BorderLayout.CENTER);
        internalFrame2.add(PanelContent);        
    }
    
     private void createScene() {        
        Platform.runLater(new Runnable() {

            public void run() {
                WebView view = new WebView();
                
                engine = view.getEngine();
                engine.setJavaScriptEnabled(true);
                
                engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
                    @Override
                    public WebEngine call(PopupFeatures p) {
                        Stage stage = new Stage(StageStyle.TRANSPARENT);
                        return view.getEngine();
                    }
                });
                
                engine.titleProperty().addListener((ObservableValue<? extends String> observable, String oldValue, final String newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        DlgBerkasKepegawaian.this.setTitle(newValue);
                    });
                });
                
                
                engine.setOnStatusChanged((final WebEvent<String> event) -> {
                    SwingUtilities.invokeLater(() -> {
                        lblStatus.setText(event.getData());
                    });
                });
                
                
                engine.getLoadWorker().workDoneProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(newValue.intValue());
                    });                                                   
                });
                
                engine.getLoadWorker().exceptionProperty().addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                    if (engine.getLoadWorker().getState() == FAILED) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    PanelContent,
                                    (value != null) ?
                                            engine.getLocation() + "\n" + value.getMessage() :
                                            engine.getLocation() + "\nUnexpected Catatan.",
                                    "Loading Catatan...",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });
                
                
                engine.locationProperty().addListener((ObservableValue<? extends String> ov, String oldValue, final String newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        txtURL.setText(newValue);
                    });
                });
                
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            try {
                                if(engine.getLocation().replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/","").contains("penggajian/pages")){
                                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    Valid.panggilUrl(engine.getLocation().replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/","").replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+"/"+prop.getProperty("HYBRIDWEB")+"/",""));
                                    engine.executeScript("history.back()");
                                    setCursor(Cursor.getDefaultCursor());
                                }else if(engine.getLocation().replaceAll("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/","").contains("Keluar")){
                                    dispose();    
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : "+ex);
                            }
                        } 
                    }
                });
                
                jfxPanel.setScene(new Scene(view));
            }
        });
    }
 
    public void loadURL(String url) {  
        try {
            createScene();
        } catch (Exception e) {
        }
        
        Platform.runLater(() -> {
            try {
                engine.load(url);
            }catch (Exception exception) {
                engine.load(url);
            }
        });        
    }    
    
    public void CloseScane(){
        Platform.setImplicitExit(false);
    }
    
    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
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
        panelGlass5 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        jLabel3 = new widget.Label();
        TKd = new widget.TextBox();
        BtnCariPegawai = new widget.Button();
        TNm = new widget.TextBox();
        CmbKategori = new widget.ComboBox();
        jLabel9 = new widget.Label();
        jLabel4 = new widget.Label();
        JK = new widget.TextBox();
        jLabel5 = new widget.Label();
        Jabatan = new widget.TextBox();
        jLabel8 = new widget.Label();
        Bidang = new widget.TextBox();
        jLabel10 = new widget.Label();
        JenjangJabatan = new widget.TextBox();
        Departemen = new widget.TextBox();
        jLabel11 = new widget.Label();
        Pendidikan = new widget.TextBox();
        jLabel12 = new widget.Label();
        PanelContent = new widget.panelisi();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        LoadHTML1 = new widget.editorpane();
        internalFrame4 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        internalFrame5 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        LoadHTML3 = new widget.editorpane();
        internalFrame6 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        LoadHTML4 = new widget.editorpane();
        internalFrame7 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        LoadHTML5 = new widget.editorpane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Berkas Kepegawaian ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Keyword :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(460, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TCariKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

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
        panelGlass5.add(BtnCari);

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
        panelGlass5.add(BtnAll);

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(jLabel7);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.setOpaque(true);
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setBorder(null);
        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 95));
        panelGlass6.setLayout(null);

        jLabel3.setText("Pegawai :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass6.add(jLabel3);
        jLabel3.setBounds(0, 10, 60, 23);

        TKd.setEditable(false);
        TKd.setBackground(new java.awt.Color(245, 250, 240));
        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });
        panelGlass6.add(TKd);
        TKd.setBounds(64, 10, 110, 23);

        BtnCariPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCariPegawai.setMnemonic('1');
        BtnCariPegawai.setToolTipText("ALt+1");
        BtnCariPegawai.setName("BtnCariPegawai"); // NOI18N
        BtnCariPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPegawaiActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnCariPegawai);
        BtnCariPegawai.setBounds(408, 10, 28, 23);

        TNm.setEditable(false);
        TNm.setBackground(new java.awt.Color(245, 250, 240));
        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        panelGlass6.add(TNm);
        TNm.setBounds(176, 10, 230, 23);

        CmbKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tenaga klinis Dokter Umum", "Tenaga klinis Dokter Spesialis", "Tenaga klinis Perawat dan Bidan", "Tenaga klinis Profesi Lain", "Tenaga Non Klinis" }));
        CmbKategori.setName("CmbKategori"); // NOI18N
        CmbKategori.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbKategoriItemStateChanged(evt);
            }
        });
        CmbKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbKategoriKeyPressed(evt);
            }
        });
        panelGlass6.add(CmbKategori);
        CmbKategori.setBounds(538, 10, 220, 23);

        jLabel9.setText("Kategori Berkas :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass6.add(jLabel9);
        jLabel9.setBounds(434, 10, 100, 23);

        jLabel4.setText("J.K. :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass6.add(jLabel4);
        jLabel4.setBounds(0, 40, 60, 23);

        JK.setEditable(false);
        JK.setBackground(new java.awt.Color(245, 250, 240));
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });
        panelGlass6.add(JK);
        JK.setBounds(64, 40, 60, 23);

        jLabel5.setText("Jabatan :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass6.add(jLabel5);
        jLabel5.setBounds(434, 40, 100, 23);

        Jabatan.setEditable(false);
        Jabatan.setBackground(new java.awt.Color(245, 250, 240));
        Jabatan.setHighlighter(null);
        Jabatan.setName("Jabatan"); // NOI18N
        Jabatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JabatanKeyPressed(evt);
            }
        });
        panelGlass6.add(Jabatan);
        Jabatan.setBounds(538, 40, 220, 23);

        jLabel8.setText("Bidang :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass6.add(jLabel8);
        jLabel8.setBounds(0, 70, 60, 23);

        Bidang.setEditable(false);
        Bidang.setBackground(new java.awt.Color(245, 250, 240));
        Bidang.setHighlighter(null);
        Bidang.setName("Bidang"); // NOI18N
        Bidang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BidangKeyPressed(evt);
            }
        });
        panelGlass6.add(Bidang);
        Bidang.setBounds(64, 70, 130, 23);

        jLabel10.setText("Jenjang Jabatan :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelGlass6.add(jLabel10);
        jLabel10.setBounds(434, 70, 100, 23);

        JenjangJabatan.setEditable(false);
        JenjangJabatan.setBackground(new java.awt.Color(245, 250, 240));
        JenjangJabatan.setHighlighter(null);
        JenjangJabatan.setName("JenjangJabatan"); // NOI18N
        JenjangJabatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenjangJabatanKeyPressed(evt);
            }
        });
        panelGlass6.add(JenjangJabatan);
        JenjangJabatan.setBounds(538, 70, 220, 23);

        Departemen.setEditable(false);
        Departemen.setBackground(new java.awt.Color(245, 250, 240));
        Departemen.setHighlighter(null);
        Departemen.setName("Departemen"); // NOI18N
        Departemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DepartemenKeyPressed(evt);
            }
        });
        panelGlass6.add(Departemen);
        Departemen.setBounds(304, 70, 132, 23);

        jLabel11.setText("Departemen :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass6.add(jLabel11);
        jLabel11.setBounds(200, 70, 100, 23);

        Pendidikan.setEditable(false);
        Pendidikan.setBackground(new java.awt.Color(245, 250, 240));
        Pendidikan.setHighlighter(null);
        Pendidikan.setName("Pendidikan"); // NOI18N
        Pendidikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendidikanKeyPressed(evt);
            }
        });
        panelGlass6.add(Pendidikan);
        Pendidikan.setBounds(240, 40, 196, 23);

        jLabel12.setText("Pendidikan :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass6.add(jLabel12);
        jLabel12.setBounds(152, 40, 85, 23);

        internalFrame2.add(panelGlass6, java.awt.BorderLayout.PAGE_START);

        PanelContent.setBorder(null);
        PanelContent.setName("PanelContent"); // NOI18N
        PanelContent.setPreferredSize(new java.awt.Dimension(55, 55));
        PanelContent.setLayout(new java.awt.BorderLayout());
        internalFrame2.add(PanelContent, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Data", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML1.setBorder(null);
        LoadHTML1.setName("LoadHTML1"); // NOI18N
        Scroll.setViewportView(LoadHTML1);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tenaga klinis Dokter Umum", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll1.setViewportView(LoadHTML2);

        internalFrame4.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tenaga klinis Dokter Spesialis", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        LoadHTML3.setBorder(null);
        LoadHTML3.setName("LoadHTML3"); // NOI18N
        Scroll2.setViewportView(LoadHTML3);

        internalFrame5.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tenaga klinis Perawat dan Bidan", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        LoadHTML4.setBorder(null);
        LoadHTML4.setName("LoadHTML4"); // NOI18N
        Scroll3.setViewportView(LoadHTML4);

        internalFrame6.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tenaga klinis Profesi Lain", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        LoadHTML5.setBorder(null);
        LoadHTML5.setName("LoadHTML5"); // NOI18N
        Scroll4.setViewportView(LoadHTML5);

        internalFrame7.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tenaga Non Klinis", internalFrame7);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if(!TKd.getText().equals("")){
                    try {
                        loadURL("http://" +koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/index.php?act=DetailBerkasPegawai&action=TAMBAH&nik="+TKd.getText().replaceAll(" ","_")+"&kategori="+CmbKategori.getSelectedItem().toString().replaceAll(" ","_")+"&keyword="+TCari.getText().replaceAll(" ","_"));
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                    }
                } 
                break;
            case 1:
                tampil();
                break;
            case 2:
                tampil2();
                break;
            case 3:
                tampil3();
                break;
            case 4:
                tampil4();
                break;
            case 5:
                tampil5();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnCariPegawaiActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,TNm);
        }
    }//GEN-LAST:event_TKdKeyPressed

    private void BtnCariPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPegawaiActionPerformed
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnCariPegawaiActionPerformed

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        //Valid.pindah(evt,TKd,CmbJk);
    }//GEN-LAST:event_TNmKeyPressed

    private void TCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyTyped
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyTyped

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
        TabRawatMouseClicked(null);
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
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void CmbKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbKategoriKeyPressed
        Valid.pindah(evt,TKd,TNm);
    }//GEN-LAST:event_CmbKategoriKeyPressed

    private void CmbKategoriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbKategoriItemStateChanged
        if(!TKd.getText().equals("")){
            try {
                loadURL("http://" +koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"penggajian/loginberkaspegawai.php?act=login&usere=admin&passwordte=akusayangsamakamu&nik="+TKd.getText().replaceAll(" ","_")+"&kategori="+CmbKategori.getSelectedItem().toString().replaceAll(" ","_")+"");
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }
        }            
    }//GEN-LAST:event_CmbKategoriItemStateChanged

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JKKeyPressed

    private void JabatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JabatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JabatanKeyPressed

    private void BidangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BidangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BidangKeyPressed

    private void JenjangJabatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenjangJabatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenjangJabatanKeyPressed

    private void DepartemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DepartemenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DepartemenKeyPressed

    private void PendidikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendidikanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendidikanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBerkasKepegawaian dialog = new DlgBerkasKepegawaian(new javax.swing.JFrame(), true);
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
    private widget.TextBox Bidang;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCariPegawai;
    private widget.Button BtnKeluar;
    private widget.ComboBox CmbKategori;
    private widget.TextBox Departemen;
    private widget.TextBox JK;
    private widget.TextBox Jabatan;
    private widget.TextBox JenjangJabatan;
    private widget.editorpane LoadHTML1;
    private widget.editorpane LoadHTML2;
    private widget.editorpane LoadHTML3;
    private widget.editorpane LoadHTML4;
    private widget.editorpane LoadHTML5;
    private widget.panelisi PanelContent;
    private widget.TextBox Pendidikan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.TextBox TNm;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass6;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='head'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>NIP</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pegawai</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jenjang Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Departemen</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Bidang</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status Karyawan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Pendidikan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Mulai Kerja</td>"+
                "</tr>"); 
            ps=koneksi.prepareStatement(
                 "select pegawai.nik,pegawai.nama,pegawai.jbtn,pegawai.jnj_jabatan,"+
                 "pegawai.departemen,pegawai.bidang,pegawai.stts_kerja,pegawai.pendidikan,"+
                 "pegawai.mulai_kerja from pegawai inner join berkas_pegawai inner join master_berkas_pegawai "+
                 "on pegawai.nik=berkas_pegawai.nik and berkas_pegawai.kode_berkas=master_berkas_pegawai.kode where "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' and pegawai.nik like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' and pegawai.nama like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' and pegawai.jbtn like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' and pegawai.jnj_jabatan like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' and pegawai.departemen like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' and pegawai.bidang like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' and pegawai.stts_kerja like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' and pegawai.pendidikan like ?  "+
                 "group by pegawai.nik order by pegawai.jnj_jabatan,pegawai.departemen,pegawai.bidang,pegawai.nama");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+TCari.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'>"+i+"</td>"+
                          "<td valign='middle' align='center'>"+rs.getString("nik")+"</td>"+
                          "<td valign='middle'>"+rs.getString("nama")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jbtn")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jnj_jabatan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("departemen")+"</td>"+
                          "<td valign='middle'>"+rs.getString("bidang")+"</td>"+
                          "<td valign='middle'>"+rs.getString("stts_kerja")+"</td>"+
                          "<td valign='middle'>"+rs.getString("pendidikan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("mulai_kerja")+"</td>"+
                        "</tr>");
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'></td>"+
                          "<td valign='middle' colspan='9'>"+
                             "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='2%'>No.</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='10%'>Tgl.Uploud</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='88%'>Berkas Pegawai</td>"+
                                "</tr>");
                    
                    ps2=koneksi.prepareStatement("SELECT berkas_pegawai.nik, berkas_pegawai.tgl_uploud,berkas_pegawai.kode_berkas," +
                        "master_berkas_pegawai.nama_berkas,berkas_pegawai.berkas from berkas_pegawai inner join " +
                        "master_berkas_pegawai on berkas_pegawai.kode_berkas=master_berkas_pegawai.kode " +
                        "where berkas_pegawai.nik=? and master_berkas_pegawai.kategori='Tenaga klinis Dokter Umum' " +
                        "order by master_berkas_pegawai.no_urut");
                    try {
                        i2=1;
                        ps2.setString(1,rs.getString("nik"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='middle' align='center'>"+i2+"</td>"+
                                    "<td valign='middle' align='center'>"+rs2.getString("tgl_uploud")+"</td>"+
                                    "<td valign='middle'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/penggajian/"+rs2.getString("berkas")+"'>"+rs2.getString("nama_berkas")+"</a></td>"+
                                "</tr>");
                            i2++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    htmlContent.append(
                            "</table>"+
                          "</td>"+
                        "</tr>");
                    i++;
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
            LoadHTML1.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil2(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='head'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>NIP</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pegawai</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jenjang Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Departemen</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Bidang</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status Karyawan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Pendidikan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Mulai Kerja</td>"+
                "</tr>"); 
            ps=koneksi.prepareStatement(
                 "select pegawai.nik,pegawai.nama,pegawai.jbtn,pegawai.jnj_jabatan,"+
                 "pegawai.departemen,pegawai.bidang,pegawai.stts_kerja,pegawai.pendidikan,"+
                 "pegawai.mulai_kerja from pegawai inner join berkas_pegawai inner join master_berkas_pegawai "+
                 "on pegawai.nik=berkas_pegawai.nik and berkas_pegawai.kode_berkas=master_berkas_pegawai.kode where "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' and pegawai.nik like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' and pegawai.nama like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' and pegawai.jbtn like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' and pegawai.jnj_jabatan like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' and pegawai.departemen like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' and pegawai.bidang like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' and pegawai.stts_kerja like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' and pegawai.pendidikan like ?  "+
                 "group by pegawai.nik order by pegawai.jnj_jabatan,pegawai.departemen,pegawai.bidang,pegawai.nama");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+TCari.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'>"+i+"</td>"+
                          "<td valign='middle' align='center'>"+rs.getString("nik")+"</td>"+
                          "<td valign='middle'>"+rs.getString("nama")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jbtn")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jnj_jabatan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("departemen")+"</td>"+
                          "<td valign='middle'>"+rs.getString("bidang")+"</td>"+
                          "<td valign='middle'>"+rs.getString("stts_kerja")+"</td>"+
                          "<td valign='middle'>"+rs.getString("pendidikan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("mulai_kerja")+"</td>"+
                        "</tr>");
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'></td>"+
                          "<td valign='middle' colspan='9'>"+
                             "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='2%'>No.</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='10%'>Tgl.Uploud</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='88%'>Berkas Pegawai</td>"+
                                "</tr>");
                    
                    ps2=koneksi.prepareStatement("SELECT berkas_pegawai.nik, berkas_pegawai.tgl_uploud,berkas_pegawai.kode_berkas," +
                        "master_berkas_pegawai.nama_berkas,berkas_pegawai.berkas from berkas_pegawai inner join " +
                        "master_berkas_pegawai on berkas_pegawai.kode_berkas=master_berkas_pegawai.kode " +
                        "where berkas_pegawai.nik=? and master_berkas_pegawai.kategori='Tenaga klinis Dokter Spesialis' " +
                        "order by master_berkas_pegawai.no_urut");
                    try {
                        i2=1;
                        ps2.setString(1,rs.getString("nik"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='middle' align='center'>"+i2+"</td>"+
                                    "<td valign='middle' align='center'>"+rs2.getString("tgl_uploud")+"</td>"+
                                    "<td valign='middle'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/penggajian/"+rs2.getString("berkas")+"'>"+rs2.getString("nama_berkas")+"</a></td>"+
                                "</tr>");
                            i2++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    htmlContent.append(
                            "</table>"+
                          "</td>"+
                        "</tr>");
                    i++;
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
            LoadHTML2.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil3(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='head'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>NIP</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pegawai</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jenjang Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Departemen</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Bidang</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status Karyawan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Pendidikan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Mulai Kerja</td>"+
                "</tr>"); 
            ps=koneksi.prepareStatement(
                 "select pegawai.nik,pegawai.nama,pegawai.jbtn,pegawai.jnj_jabatan,"+
                 "pegawai.departemen,pegawai.bidang,pegawai.stts_kerja,pegawai.pendidikan,"+
                 "pegawai.mulai_kerja from pegawai inner join berkas_pegawai inner join master_berkas_pegawai "+
                 "on pegawai.nik=berkas_pegawai.nik and berkas_pegawai.kode_berkas=master_berkas_pegawai.kode where "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' and pegawai.nik like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' and pegawai.nama like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' and pegawai.jbtn like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' and pegawai.jnj_jabatan like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' and pegawai.departemen like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' and pegawai.bidang like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' and pegawai.stts_kerja like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' and pegawai.pendidikan like ?  "+
                 "group by pegawai.nik order by pegawai.jnj_jabatan,pegawai.departemen,pegawai.bidang,pegawai.nama");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+TCari.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'>"+i+"</td>"+
                          "<td valign='middle' align='center'>"+rs.getString("nik")+"</td>"+
                          "<td valign='middle'>"+rs.getString("nama")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jbtn")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jnj_jabatan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("departemen")+"</td>"+
                          "<td valign='middle'>"+rs.getString("bidang")+"</td>"+
                          "<td valign='middle'>"+rs.getString("stts_kerja")+"</td>"+
                          "<td valign='middle'>"+rs.getString("pendidikan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("mulai_kerja")+"</td>"+
                        "</tr>");
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'></td>"+
                          "<td valign='middle' colspan='9'>"+
                             "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='2%'>No.</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='10%'>Tgl.Uploud</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='88%'>Berkas Pegawai</td>"+
                                "</tr>");
                    
                    ps2=koneksi.prepareStatement("SELECT berkas_pegawai.nik, berkas_pegawai.tgl_uploud,berkas_pegawai.kode_berkas," +
                        "master_berkas_pegawai.nama_berkas,berkas_pegawai.berkas from berkas_pegawai inner join " +
                        "master_berkas_pegawai on berkas_pegawai.kode_berkas=master_berkas_pegawai.kode " +
                        "where berkas_pegawai.nik=? and master_berkas_pegawai.kategori='Tenaga klinis Perawat dan Bidan' " +
                        "order by master_berkas_pegawai.no_urut");
                    try {
                        i2=1;
                        ps2.setString(1,rs.getString("nik"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='middle' align='center'>"+i2+"</td>"+
                                    "<td valign='middle' align='center'>"+rs2.getString("tgl_uploud")+"</td>"+
                                    "<td valign='middle'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/penggajian/"+rs2.getString("berkas")+"'>"+rs2.getString("nama_berkas")+"</a></td>"+
                                "</tr>");
                            i2++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    htmlContent.append(
                            "</table>"+
                          "</td>"+
                        "</tr>");
                    i++;
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
            LoadHTML3.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void tampil4(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='head'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>NIP</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pegawai</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jenjang Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Departemen</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Bidang</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status Karyawan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Pendidikan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Mulai Kerja</td>"+
                "</tr>"); 
            ps=koneksi.prepareStatement(
                 "select pegawai.nik,pegawai.nama,pegawai.jbtn,pegawai.jnj_jabatan,"+
                 "pegawai.departemen,pegawai.bidang,pegawai.stts_kerja,pegawai.pendidikan,"+
                 "pegawai.mulai_kerja from pegawai inner join berkas_pegawai inner join master_berkas_pegawai "+
                 "on pegawai.nik=berkas_pegawai.nik and berkas_pegawai.kode_berkas=master_berkas_pegawai.kode where "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' and pegawai.nik like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' and pegawai.nama like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' and pegawai.jbtn like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' and pegawai.jnj_jabatan like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' and pegawai.departemen like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' and pegawai.bidang like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' and pegawai.stts_kerja like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' and pegawai.pendidikan like ?  "+
                 "group by pegawai.nik order by pegawai.jnj_jabatan,pegawai.departemen,pegawai.bidang,pegawai.nama");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+TCari.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'>"+i+"</td>"+
                          "<td valign='middle' align='center'>"+rs.getString("nik")+"</td>"+
                          "<td valign='middle'>"+rs.getString("nama")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jbtn")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jnj_jabatan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("departemen")+"</td>"+
                          "<td valign='middle'>"+rs.getString("bidang")+"</td>"+
                          "<td valign='middle'>"+rs.getString("stts_kerja")+"</td>"+
                          "<td valign='middle'>"+rs.getString("pendidikan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("mulai_kerja")+"</td>"+
                        "</tr>");
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'></td>"+
                          "<td valign='middle' colspan='9'>"+
                             "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='2%'>No.</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='10%'>Tgl.Uploud</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='88%'>Berkas Pegawai</td>"+
                                "</tr>");
                    
                    ps2=koneksi.prepareStatement("SELECT berkas_pegawai.nik, berkas_pegawai.tgl_uploud,berkas_pegawai.kode_berkas," +
                        "master_berkas_pegawai.nama_berkas,berkas_pegawai.berkas from berkas_pegawai inner join " +
                        "master_berkas_pegawai on berkas_pegawai.kode_berkas=master_berkas_pegawai.kode " +
                        "where berkas_pegawai.nik=? and master_berkas_pegawai.kategori='Tenaga klinis Profesi Lain' " +
                        "order by master_berkas_pegawai.no_urut");
                    try {
                        i2=1;
                        ps2.setString(1,rs.getString("nik"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='middle' align='center'>"+i2+"</td>"+
                                    "<td valign='middle' align='center'>"+rs2.getString("tgl_uploud")+"</td>"+
                                    "<td valign='middle'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/penggajian/"+rs2.getString("berkas")+"'>"+rs2.getString("nama_berkas")+"</a></td>"+
                                "</tr>");
                            i2++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    htmlContent.append(
                            "</table>"+
                          "</td>"+
                        "</tr>");
                    i++;
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
            LoadHTML4.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil5(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='head'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>NIP</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='23%'>Nama Pegawai</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Jenjang Jabatan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Departemen</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Bidang</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Status Karyawan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Pendidikan</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Mulai Kerja</td>"+
                "</tr>"); 
            ps=koneksi.prepareStatement(
                 "select pegawai.nik,pegawai.nama,pegawai.jbtn,pegawai.jnj_jabatan,"+
                 "pegawai.departemen,pegawai.bidang,pegawai.stts_kerja,pegawai.pendidikan,"+
                 "pegawai.mulai_kerja from pegawai inner join berkas_pegawai inner join master_berkas_pegawai "+
                 "on pegawai.nik=berkas_pegawai.nik and berkas_pegawai.kode_berkas=master_berkas_pegawai.kode where "+
                 "master_berkas_pegawai.kategori='Tenaga Non Klinis' and pegawai.nik like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga Non Klinis' and pegawai.nama like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga Non Klinis' and pegawai.jbtn like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga Non Klinis' and pegawai.jnj_jabatan like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga Non Klinis' and pegawai.departemen like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga Non Klinis' and pegawai.bidang like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga Non Klinis' and pegawai.stts_kerja like ? or "+
                 "master_berkas_pegawai.kategori='Tenaga Non Klinis' and pegawai.pendidikan like ?  "+
                 "group by pegawai.nik order by pegawai.jnj_jabatan,pegawai.departemen,pegawai.bidang,pegawai.nama");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+TCari.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'>"+i+"</td>"+
                          "<td valign='middle' align='center'>"+rs.getString("nik")+"</td>"+
                          "<td valign='middle'>"+rs.getString("nama")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jbtn")+"</td>"+
                          "<td valign='middle'>"+rs.getString("jnj_jabatan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("departemen")+"</td>"+
                          "<td valign='middle'>"+rs.getString("bidang")+"</td>"+
                          "<td valign='middle'>"+rs.getString("stts_kerja")+"</td>"+
                          "<td valign='middle'>"+rs.getString("pendidikan")+"</td>"+
                          "<td valign='middle'>"+rs.getString("mulai_kerja")+"</td>"+
                        "</tr>");
                    htmlContent.append(
                        "<tr class='isi'>"+
                          "<td valign='middle' align='center'></td>"+
                          "<td valign='middle' colspan='9'>"+
                             "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='2%'>No.</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='10%'>Tgl.Uploud</td>"+
                                    "<td valign='middle' bgcolor='#fdfff9' align='center' width='88%'>Berkas Pegawai</td>"+
                                "</tr>");
                    
                    ps2=koneksi.prepareStatement("SELECT berkas_pegawai.nik, berkas_pegawai.tgl_uploud,berkas_pegawai.kode_berkas," +
                        "master_berkas_pegawai.nama_berkas,berkas_pegawai.berkas from berkas_pegawai inner join " +
                        "master_berkas_pegawai on berkas_pegawai.kode_berkas=master_berkas_pegawai.kode " +
                        "where berkas_pegawai.nik=? and master_berkas_pegawai.kategori='Tenaga Non Klinis' " +
                        "order by master_berkas_pegawai.no_urut");
                    try {
                        i2=1;
                        ps2.setString(1,rs.getString("nik"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='middle' align='center'>"+i2+"</td>"+
                                    "<td valign='middle' align='center'>"+rs2.getString("tgl_uploud")+"</td>"+
                                    "<td valign='middle'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/penggajian/"+rs2.getString("berkas")+"'>"+rs2.getString("nama_berkas")+"</a></td>"+
                                "</tr>");
                            i2++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    htmlContent.append(
                            "</table>"+
                          "</td>"+
                        "</tr>");
                    i++;
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
            LoadHTML5.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }
}

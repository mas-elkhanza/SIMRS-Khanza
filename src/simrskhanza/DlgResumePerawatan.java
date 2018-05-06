/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package simrskhanza;

import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author perpustakaan
 */
public final class DlgResumePerawatan extends javax.swing.JDialog {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final Properties prop = new Properties(); 
    private validasi Valid=new validasi();
    private ResultSet rs,rs2,rs3,rs4,rshal;
    private String sql;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgResumePerawatan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        
        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        
         pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){                   
                    KdRw.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                    TPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),2).toString());
                }    
                KdRw.requestFocus();
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
        } catch (Exception e) {
            System.out.println("simrskhanza.DlgResumePerawatan.<init>() : "+e);
        }
        
        LoadHTML.setEditable(true);
        LoadHTML2.setEditable(true);
        LoadHTML3.setEditable(true);        
        LoadHTML4.setEditable(true);        
        LoadHTML5.setEditable(true);        
        LoadHTML6.setEditable(true);        
        LoadHTML7.setEditable(true);        
        LoadHTML8.setEditable(true);        
        LoadHTML9.setEditable(true);        
        LoadHTML10.setEditable(true);       
        LoadHTML11.setEditable(true);
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditorKit(kit);
        LoadHTML3.setEditorKit(kit);
        LoadHTML4.setEditorKit(kit);
        LoadHTML5.setEditorKit(kit);
        LoadHTML6.setEditorKit(kit);
        LoadHTML7.setEditorKit(kit);
        LoadHTML8.setEditorKit(kit);
        LoadHTML9.setEditorKit(kit);
        LoadHTML10.setEditorKit(kit);
        LoadHTML11.setEditorKit(kit);
        
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(".isi td{border-right: 1px solid #edf2e8;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #edf2e8;background: #ffffff;color:#5a7850;}");
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc);
        LoadHTML3.setDocument(doc);
        LoadHTML4.setDocument(doc);
        LoadHTML5.setDocument(doc);
        LoadHTML6.setDocument(doc);
        LoadHTML7.setDocument(doc);
        LoadHTML8.setDocument(doc);
        LoadHTML9.setDocument(doc);
        LoadHTML10.setDocument(doc);
        LoadHTML11.setDocument(doc);
        LoadHTML.setEditable(false);
        LoadHTML2.setEditable(false);
        LoadHTML3.setEditable(false);
        LoadHTML4.setEditable(false);
        LoadHTML5.setEditable(false);
        LoadHTML6.setEditable(false);
        LoadHTML7.setEditable(false);
        LoadHTML8.setEditable(false);
        LoadHTML9.setEditable(false);
        LoadHTML10.setEditable(false);
        LoadHTML11.setEditable(false);
        
        LoadHTML.addHyperlinkListener(e -> {
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
              System.out.println(e.getURL());
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
        LoadHTML6.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML7.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML8.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML9.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML10.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML11.addHyperlinkListener(e -> {
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
    
    DlgPasien pasien=new DlgPasien(null,false);
    int y=0,w=0,urut;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        ChkTanggal = new widget.CekBox();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label19 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        KdRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari1 = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        internalFrame4 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        LoadHTML3 = new widget.editorpane();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        LoadHTML4 = new widget.editorpane();
        internalFrame6 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        LoadHTML5 = new widget.editorpane();
        internalFrame7 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        LoadHTML6 = new widget.editorpane();
        internalFrame8 = new widget.InternalFrame();
        Scroll6 = new widget.ScrollPane();
        LoadHTML7 = new widget.editorpane();
        internalFrame9 = new widget.InternalFrame();
        Scroll7 = new widget.ScrollPane();
        LoadHTML8 = new widget.editorpane();
        internalFrame10 = new widget.InternalFrame();
        Scroll8 = new widget.ScrollPane();
        LoadHTML9 = new widget.editorpane();
        internalFrame11 = new widget.InternalFrame();
        Scroll9 = new widget.ScrollPane();
        LoadHTML10 = new widget.editorpane();
        internalFrame12 = new widget.InternalFrame();
        Scroll10 = new widget.ScrollPane();
        LoadHTML11 = new widget.editorpane();
        internalFrame13 = new widget.InternalFrame();
        Scroll11 = new widget.ScrollPane();
        LoadHTML12 = new widget.editorpane();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Resume/Rincian Tindakan/Terapi Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(90, 120, 80))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        ChkTanggal.setBorder(null);
        ChkTanggal.setText("Tgl.Rawat :");
        ChkTanggal.setBorderPainted(true);
        ChkTanggal.setBorderPaintedFlat(true);
        ChkTanggal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTanggal.setName("ChkTanggal"); // NOI18N
        ChkTanggal.setOpaque(false);
        ChkTanggal.setPreferredSize(new java.awt.Dimension(85, 23));
        ChkTanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkTanggalItemStateChanged(evt);
            }
        });
        panelGlass5.add(ChkTanggal);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(label18);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass5.add(Tgl2);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(125, 23));
        panelGlass5.add(label19);

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
        panelGlass5.add(BtnPrint);

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

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label17.setText("Pasien :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label17);

        KdRw.setName("KdRw"); // NOI18N
        KdRw.setPreferredSize(new java.awt.Dimension(120, 23));
        KdRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRwKeyPressed(evt);
            }
        });
        panelisi4.add(KdRw);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(390, 23));
        panelisi4.add(TPasien);

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
        panelisi4.add(BtnSeek2);

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
        panelisi4.add(BtnCari1);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(250, 255, 245));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)));
        TabRawat.setForeground(new java.awt.Color(90, 120, 80));
        TabRawat.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Seluruh Riwayat Perawatan", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll1.setViewportView(LoadHTML2);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Penyakit/ICD 10", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        LoadHTML3.setBorder(null);
        LoadHTML3.setName("LoadHTML3"); // NOI18N
        Scroll2.setViewportView(LoadHTML3);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Prosedur/ICD 9", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        LoadHTML4.setBorder(null);
        LoadHTML4.setName("LoadHTML4"); // NOI18N
        Scroll3.setViewportView(LoadHTML4);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Rawat Jalan", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        LoadHTML5.setBorder(null);
        LoadHTML5.setName("LoadHTML5"); // NOI18N
        Scroll4.setViewportView(LoadHTML5);

        internalFrame6.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Rawat Inap", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        LoadHTML6.setBorder(null);
        LoadHTML6.setName("LoadHTML6"); // NOI18N
        Scroll5.setViewportView(LoadHTML6);

        internalFrame7.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Operasi", internalFrame7);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        LoadHTML7.setBorder(null);
        LoadHTML7.setName("LoadHTML7"); // NOI18N
        Scroll6.setViewportView(LoadHTML7);

        internalFrame8.add(Scroll6, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Radiologi", internalFrame8);

        internalFrame9.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame9.setBorder(null);
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        LoadHTML8.setBorder(null);
        LoadHTML8.setName("LoadHTML8"); // NOI18N
        Scroll7.setViewportView(LoadHTML8);

        internalFrame9.add(Scroll7, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Laborat", internalFrame9);

        internalFrame10.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame10.setBorder(null);
        internalFrame10.setName("internalFrame10"); // NOI18N
        internalFrame10.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        LoadHTML9.setBorder(null);
        LoadHTML9.setName("LoadHTML9"); // NOI18N
        Scroll8.setViewportView(LoadHTML9);

        internalFrame10.add(Scroll8, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Pemberian Obat", internalFrame10);

        internalFrame11.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame11.setBorder(null);
        internalFrame11.setName("internalFrame11"); // NOI18N
        internalFrame11.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        LoadHTML10.setBorder(null);
        LoadHTML10.setName("LoadHTML10"); // NOI18N
        Scroll9.setViewportView(LoadHTML10);

        internalFrame11.add(Scroll9, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Obat Operasi", internalFrame11);

        internalFrame12.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame12.setBorder(null);
        internalFrame12.setName("internalFrame12"); // NOI18N
        internalFrame12.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        LoadHTML11.setBorder(null);
        LoadHTML11.setName("LoadHTML11"); // NOI18N
        Scroll10.setViewportView(LoadHTML11);

        internalFrame12.add(Scroll10, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Resep Pulang", internalFrame12);

        internalFrame13.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame13.setBorder(null);
        internalFrame13.setName("internalFrame13"); // NOI18N
        internalFrame13.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        LoadHTML12.setBorder(null);
        LoadHTML12.setName("LoadHTML12"); // NOI18N
        Scroll11.setViewportView(LoadHTML12);

        internalFrame13.add(Scroll11, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Berkas Digital Perawatan", internalFrame13);

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
        }else{Valid.pindah(evt,Tgl1,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void KdRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPasien();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isPasien();
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isPasien();
            BtnPrint.requestFocus();
        }
}//GEN-LAST:event_KdRwKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        pasien.isCek();
        pasien.emptTeks();
        pasien.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
    //Valid.pindah(evt,Tgl2,TKd);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        TabRawatMouseClicked(null);
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TabRawatMouseClicked(null);
        }else{
           // Valid.pindah(evt, TKd, BtnAll);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            File g = new File("file.css");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(".isi td{border-right: 1px solid #edf2e8;font: 11px tahoma;height:12px;border-bottom: 1px solid #edf2e8;background: #ffffff;color:#5a7850;}");
            bg.close();
            
            File f = new File("resumemedis.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    bw.write(LoadHTML.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 1:
                    bw.write(LoadHTML2.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 2:
                    bw.write(LoadHTML3.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 3:
                    bw.write(LoadHTML4.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 4:
                    bw.write(LoadHTML5.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 5:
                    bw.write(LoadHTML6.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 6:
                    bw.write(LoadHTML7.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 7:
                    bw.write(LoadHTML8.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 8:
                    bw.write(LoadHTML9.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 9:
                    bw.write(LoadHTML10.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                case 10:
                    bw.write(LoadHTML11.getText().replaceAll(
                            "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
                    );  bw.close();
                    break;
                default:
                    break;
            }
                
            Desktop.getDesktop().browse(f.toURI());
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }     
        
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,KdRw);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void ChkTanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkTanggalItemStateChanged
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_ChkTanggalItemStateChanged

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            case 2:
                tampil3();
                break;
            case 3:
                tampil4();
                break;
            case 4:
                tampil5();
                break;
            case 5:
                tampil6();
                break;
            case 6:
                tampil7();
                break;
            case 7:
                tampil8();
                break;
            case 8:
                tampil9();
                break;
            case 9:
                tampil10();
                break;
            case 10:
                tampil11();
                break;
            case 11:
                tampil12();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgResumePerawatan dialog = new DlgResumePerawatan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.CekBox ChkTanggal;
    private widget.TextBox KdRw;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML10;
    private widget.editorpane LoadHTML11;
    private widget.editorpane LoadHTML12;
    private widget.editorpane LoadHTML2;
    private widget.editorpane LoadHTML3;
    private widget.editorpane LoadHTML4;
    private widget.editorpane LoadHTML5;
    private widget.editorpane LoadHTML6;
    private widget.editorpane LoadHTML7;
    private widget.editorpane LoadHTML8;
    private widget.editorpane LoadHTML9;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextBox TKd;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame10;
    private widget.InternalFrame internalFrame11;
    private widget.InternalFrame internalFrame12;
    private widget.InternalFrame internalFrame13;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelisi4;
    // End of variables declaration//GEN-END:variables

    public void tampil(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            //menampilkan diagnosa penyakit                            
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status "+
                                        "from diagnosa_pasien inner join penyakit "+
                                        "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                                        "where diagnosa_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Diagnosa/Penyakit/ICD 10</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td><td valign='top' width='24%' bgcolor='#f7fcf2'>Kode</td><td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Penyakit</td><td valign='top' width='23%' bgcolor='#f7fcf2'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kd_penyakit")+"</td><td valign='top'>"+rs3.getString("nm_penyakit")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan prosedur tindakan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                                        "from prosedur_pasien inner join icd9 "+
                                        "on prosedur_pasien.kode=icd9.kode "+
                                        "where prosedur_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prosedur Tindakan/ICD 9</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td><td valign='top' width='24%' bgcolor='#f7fcf2'>Kode</td><td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Prosedur</td><td valign='top' width='23%' bgcolor='#f7fcf2'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kode")+"</td><td valign='top'>"+rs3.getString("deskripsi_panjang")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan ralan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,"+
                                        "pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.keluhan, "+
                                        "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.imun_ke,pemeriksaan_ralan.rtl from pemeriksaan_ralan where "+
                                        "pemeriksaan_ralan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Rawat Jalan</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Suhu(C)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tensi</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Nadi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Respirasi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tinggi(Cm)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Berat(Kg)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>GCS(E,V,M)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Imunisasi Ke</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("suhu_tubuh")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tensi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nadi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("respirasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("berat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("gcs")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("imun_ke")+"</td>"+
                                             "</tr>"); 
                                        if(!rs3.getString("keluhan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Keluhan</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("keluhan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pemeriksaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pemeriksaan</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("pemeriksaan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("alergi").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Alergi</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("alergi")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("rtl").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Tindak Lanjut</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("rtl")+"</td>"+
                                                 "</tr>");
                                        }
                                            
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan ranap
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi,pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi," +
                                        "pemeriksaan_ranap.tinggi,pemeriksaan_ranap.berat,pemeriksaan_ranap.gcs,pemeriksaan_ranap.keluhan," +
                                        "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat from pemeriksaan_ranap where pemeriksaan_ranap.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Rawat Inap</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                                "<td valign='top' width='5%' bgcolor='#f7fcf2'>Suhu(C)</td>"+
                                                "<td valign='top' width='5%' bgcolor='#f7fcf2'>Tensi</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Nadi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Respirasi(/menit)</td>"+
                                                "<td valign='top' width='5%' bgcolor='#f7fcf2'>Tinggi(Cm)</td>"+
                                                "<td valign='top' width='5%' bgcolor='#f7fcf2'>Berat(Kg)</td>"+
                                                "<td valign='top' width='5%' bgcolor='#f7fcf2'>GCS(E,V,M)</td>"+
                                                "<td valign='top' width='15%' bgcolor='#f7fcf2'>Keluhan</td>"+
                                                "<td valign='top' width='15%' bgcolor='#f7fcf2'>Pemeriksaan</td>"+
                                                "<td valign='top' width='10%' bgcolor='#f7fcf2'>Alergi</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("suhu_tubuh")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tensi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nadi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("respirasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("berat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("gcs")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("keluhan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("pemeriksaan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("alergi")+"</td>"+
                                             "</tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //biaya administrasi
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"+
                                     "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                       "<tr>"+
                                         "<td valign='top' width='89%'>Administrasi</td>"+
                                         "<td valign='top' width='1%' align='right'>:</td>"+
                                         "<td valign='top' width='10%' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_reg"))+"</td>"+
                                       "</tr>"+
                                     "</table>"
                            );
                            
                            //tindakan dokter ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,rawat_jl_dr.biaya_rawat "+
                                        "from rawat_jl_dr inner join jns_perawatan inner join dokter "+
                                        "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_dr.kd_dokter=dokter.kd_dokter where rawat_jl_dr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='45%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,petugas.nama,rawat_jl_pr.biaya_rawat "+
                                        "from rawat_jl_pr inner join jns_perawatan inner join petugas "+
                                        "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_pr.nip=petugas.nip where rawat_jl_pr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+                                        
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+      
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='45%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan ralan dokter dan paramedis
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_jl_drpr.biaya_rawat "+
                                        "from rawat_jl_drpr inner join jns_perawatan inner join dokter inner join petugas "+
                                        "on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and rawat_jl_drpr.nip=petugas.nip "+
                                        "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter where rawat_jl_drpr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Jalan Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='25%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Dokter</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,"+
                                        "rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "dokter.nm_dokter,rawat_inap_dr.biaya_rawat "+
                                        "from rawat_inap_dr inner join jns_perawatan_inap inner join dokter "+
                                        "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_dr.kd_dokter=dokter.kd_dokter where rawat_inap_dr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,"+
                                        "rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "petugas.nama,rawat_inap_pr.biaya_rawat "+
                                        "from rawat_inap_pr inner join jns_perawatan_inap inner join petugas "+
                                        "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_pr.nip=petugas.nip where rawat_inap_pr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }      
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis dan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_jenis_prw,"+
                                        "jns_perawatan_inap.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_inap_drpr.biaya_rawat "+
                                        "from rawat_inap_drpr inner join jns_perawatan_inap inner join dokter inner join petugas "+
                                        "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_drpr.nip=petugas.nip "+
                                        "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter where rawat_inap_drpr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Inap Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Dokter</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //kamar inap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.tgl_masuk, kamar_inap.tgl_keluar, "+
                                        "kamar_inap.stts_pulang,kamar_inap.lama,kamar_inap.jam_masuk,kamar_inap.jam_keluar,"+
                                        "kamar_inap.ttl_biaya from kamar_inap inner join bangsal inner join kamar "+
                                        "on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal  "+
                                        "where kamar_inap.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Penggunaan Kamar</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal Masuk</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggak Keluar</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Lama Inap</td>"+
                                          "<td valign='top' width='35%' bgcolor='#f7fcf2'>Kamar</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Status</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_masuk")+" "+rs3.getString("jam_masuk")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_keluar")+" "+rs3.getString("jam_keluar")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("lama")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_kamar")+", "+rs3.getString("nm_bangsal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("stts_pulang")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("ttl_biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select operasi.tgl_operasi,operasi.jenis_anasthesi,operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"+
                                        "operasi.asisten_operator2,operasi.asisten_operator3,operasi.biayaasisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "+
                                        "operasi.dokter_anestesi, operasi.asisten_anestesi, operasi.asisten_anestesi2,operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, operasi.omloop,"+
                                        "operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum, "+
                                        "operasi.kode_paket,paket_operasi.nm_perawatan, operasi.biayaoperator1, operasi.biayaoperator2, operasi.biayaoperator3, "+
                                        "operasi.biayaasisten_operator1, operasi.biayaasisten_operator2, operasi.biayaasisten_operator3, operasi.biayainstrumen, "+
                                        "operasi.biayadokter_anak, operasi.biayaperawaat_resusitas, operasi.biayadokter_anestesi, "+
                                        "operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2, operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3, operasi.biayaperawat_luar, operasi.biayaalat,"+
                                        "operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,"+
                                        "operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum,"+
                                        "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+"+
                                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                                        "operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+"+
                                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+"+
                                        "operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as total from operasi inner join paket_operasi "+
                                        "on operasi.kode_paket=paket_operasi.kode_paket where operasi.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Operasi/VK</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Tindakan</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Anastesi</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_operasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_paket")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+" (");
                                        if(rs3.getDouble("biayaoperator1")>0){
                                            htmlContent.append("Operator 1 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator2")>0){
                                            htmlContent.append("Operator 2 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator3")>0){
                                            htmlContent.append("Operator 3 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator1")>0){
                                            htmlContent.append("Asisten Operator 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator2")>0){
                                            htmlContent.append("Asisten Operator 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator3")>0){
                                            htmlContent.append("Asisten Operator 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayainstrumen")>0){
                                            htmlContent.append("Instrumen : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("instrumen"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anak")>0){
                                            htmlContent.append("Dokter Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anak"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawaat_resusitas")>0){
                                            htmlContent.append("Perawat Resusitas : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawaat_resusitas"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anestesi")>0){
                                            htmlContent.append("Dokter Anestesi : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi")>0){
                                            htmlContent.append("Asisten Anestesi : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi2")>0){
                                            htmlContent.append("Asisten Anestesi 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan")>0){
                                            htmlContent.append("Bidan 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan2")>0){
                                            htmlContent.append("Bidan 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan3")>0){
                                            htmlContent.append("Bidan 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawat_luar")>0){
                                            htmlContent.append("Perawat Luar : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawat_luar"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop")>0){
                                            htmlContent.append("Onloop 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop2")>0){
                                            htmlContent.append("Onloop 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop2"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop3")>0){
                                            htmlContent.append("Onloop 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop3"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop4")>0){
                                            htmlContent.append("Onloop 4 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop4"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop5")>0){
                                            htmlContent.append("Onloop 5 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop5"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_pjanak")>0){
                                            htmlContent.append("Dokter Pj Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_pjanak"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_umum")>0){
                                            htmlContent.append("Dokter Umum : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_umum"))+", ");
                                        }
                                        htmlContent.append(
                                                ")</td>"+
                                                "<td valign='top'>"+rs3.getString("jenis_anasthesi")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_jenis_prw, "+
                                     "jns_perawatan_radiologi.nm_perawatan,petugas.nama,periksa_radiologi.biaya,periksa_radiologi.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_radiologi inner join jns_perawatan_radiologi inner join petugas inner join dokter "+
                                     "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_radiologi.nip=petugas.nip  where periksa_radiologi.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Radiologi</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#f7fcf2'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //hasil pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, hasil from hasil_radiologi where no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Bacaan/Hasil Radiologi</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#f7fcf2'>Hasil Pemeriksaan</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("hasil")+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //gambar pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, lokasi_gambar from gambar_radiologi where no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Gambar Radiologi</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#f7fcf2'>Gambar Radiologi</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/radiologi/"+rs3.getString("lokasi_gambar")+"'>"+rs3.getString("lokasi_gambar").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan pemeriksaan laborat
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                                     "jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.biaya,periksa_lab.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_lab inner join jns_perawatan_lab inner join petugas inner join dokter "+
                                     "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_lab.nip=petugas.nip  where periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Laboratorium</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#f7fcf2'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"
                                        ); 
                                        try {
                                            rs4=koneksi.prepareStatement(
                                                "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                                                "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                                "detail_periksa_lab.keterangan from detail_periksa_lab inner join "+
                                                "template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                                "where detail_periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"' and "+
                                                "detail_periksa_lab.kd_jenis_prw='"+rs3.getString("kd_jenis_prw")+"' and "+
                                                "detail_periksa_lab.tgl_periksa='"+rs3.getString("tgl_periksa")+"' and "+
                                                "detail_periksa_lab.jam='"+rs3.getString("jam")+"'").executeQuery();
                                            if(rs4.next()){ 
                                                htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top' align='center' bgcolor='#f7fcf2'>Detail Pemeriksaan</td>"+
                                                       "<td valign='top' align='center' bgcolor='#f7fcf2'>Hasil</td>"+
                                                       "<td valign='top' align='center' bgcolor='#f7fcf2'>Nilai Rujukan</td>"+
                                                       "<td valign='top' align='right'></td>"+
                                                    "</tr>");
                                                rs4.beforeFirst();
                                                while(rs4.next()){
                                                    htmlContent.append(
                                                        "<tr>"+
                                                           "<td valign='top' align='center'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'>"+rs4.getString("Pemeriksaan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai")+" "+rs4.getString("satuan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai_rujukan")+"</td>"+
                                                           "<td valign='top' align='right'>"+Valid.SetAngka(rs4.getDouble("biaya_item"))+"</td>"+
                                                        "</tr>"); 
                                                }                                               
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi : "+e);
                                        } finally{
                                            if(rs4!=null){
                                                rs4.close();
                                            }
                                        }
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //pemberian obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,databarang.kode_sat, "+
                                    "detail_pemberian_obat.kode_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                                    "databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                                    "on detail_pemberian_obat.kode_brng=databarang.kode_brng  "+
                                    "where detail_pemberian_obat.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemberian Obat/BHP/Alkes</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='35%' bgcolor='#f7fcf2'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Jumlah</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Aturan Pakai</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top'>"+Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='"+rs3.getString("tgl_perawatan")+"' and jam='"+rs3.getString("jam")+"' and no_rawat='"+rs2.getString("no_rawat")+"' and kode_brng='"+rs3.getString("kode_brng")+"'")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //pemberian obat Operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select beri_obat_operasi.tanggal,beri_obat_operasi.kd_obat,beri_obat_operasi.hargasatuan,obatbhp_ok.kode_sat, "+
                                    "beri_obat_operasi.jumlah, obatbhp_ok.nm_obat,(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                                    "from beri_obat_operasi inner join obatbhp_ok  on  beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat  "+
                                    "where beri_obat_operasi.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Penggunaan Obat/BHP Operasi</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Obat/BHP</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tanggal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Resep Pulang
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select resep_pulang.kode_brng,databarang.nama_brng,resep_pulang.dosis,resep_pulang.jml_barang, "+
                                    "databarang.kode_sat,resep_pulang.dosis,resep_pulang.total from resep_pulang inner join databarang "+
                                    "on resep_pulang.kode_brng=databarang.kode_brng where "+
                                    "resep_pulang.no_rawat='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Resep Pulang</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Dosis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dosis")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml_barang")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Retur Obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select databarang.kode_brng,databarang.nama_brng,detreturjual.kode_sat,detreturjual.h_retur, "+
				    "(detreturjual.jml_retur * -1) as jumlah,(detreturjual.subtotal * -1) as total from detreturjual "+
				    "inner join databarang inner join returjual on detreturjual.kode_brng=databarang.kode_brng "+
				    "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Retur Obat</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='65%' bgcolor='#f7fcf2'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Tambahan Biaya
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select nama_biaya, besar_biaya from tambahan_biaya where no_rawat='"+rs2.getString("no_rawat")+"' order by nama_biaya").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='2'>Tambahan Biaya</td><td valign='top' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='84%' bgcolor='#f7fcf2'>Nama Tambahan</td>"+
                                          "<td valign='top' width='1%' bgcolor='#f7fcf2'></td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_biaya")+"</td>"+
                                                "<td valign='top'></td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("besar_biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Pengurangan Biaya
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select nama_pengurangan, (-1*besar_pengurangan) as besar_pengurangan from pengurangan_biaya where no_rawat='"+rs2.getString("no_rawat")+"' order by nama_pengurangan").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='2'>Potongan Biaya</td><td valign='top' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='84%' bgcolor='#f7fcf2'>Nama Potongan</td>"+
                                          "<td valign='top' width='1%' bgcolor='#f7fcf2'></td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_pengurangan")+"</td>"+
                                                "<td valign='top'></td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("besar_pengurangan"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //berkas digital perawatan
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file "+
				    "from berkas_digital_perawatan inner join master_berkas_digital "+
                                    "on berkas_digital_perawatan.kode=master_berkas_digital.kode "+
                                    "where berkas_digital_perawatan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Berkas Digital Perawatan</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='95%' bgcolor='#f7fcf2'>Berkas Digital</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/berkasrawat/"+rs3.getString("lokasi_file")+"'>"+rs3.getString("nama").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }        
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void tampil2(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            //menampilkan diagnosa penyakit                            
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status "+
                                        "from diagnosa_pasien inner join penyakit "+
                                        "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                                        "where diagnosa_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Diagnosa/Penyakit/ICD 10</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td><td valign='top' width='24%' bgcolor='#f7fcf2'>Kode</td><td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Penyakit</td><td valign='top' width='23%' bgcolor='#f7fcf2'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kd_penyakit")+"</td><td valign='top'>"+rs3.getString("nm_penyakit")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }    
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML2.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil3(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            //menampilkan prosedur tindakan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                                        "from prosedur_pasien inner join icd9 "+
                                        "on prosedur_pasien.kode=icd9.kode "+
                                        "where prosedur_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prosedur Tindakan/ICD 9</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td><td valign='top' width='24%' bgcolor='#f7fcf2'>Kode</td><td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Prosedur</td><td valign='top' width='23%' bgcolor='#f7fcf2'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kode")+"</td><td valign='top'>"+rs3.getString("deskripsi_panjang")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML3.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil4(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            //tindakan dokter ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,rawat_jl_dr.biaya_rawat "+
                                        "from rawat_jl_dr inner join jns_perawatan inner join dokter "+
                                        "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_dr.kd_dokter=dokter.kd_dokter where rawat_jl_dr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='45%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,petugas.nama,rawat_jl_pr.biaya_rawat "+
                                        "from rawat_jl_pr inner join jns_perawatan inner join petugas "+
                                        "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_pr.nip=petugas.nip where rawat_jl_pr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+                                        
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+      
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='45%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan ralan dokter dan paramedis
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_jl_drpr.biaya_rawat "+
                                        "from rawat_jl_drpr inner join jns_perawatan inner join dokter inner join petugas "+
                                        "on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and rawat_jl_drpr.nip=petugas.nip "+
                                        "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter where rawat_jl_drpr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Jalan Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='25%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Dokter</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs2.getString("tgl_registrasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML4.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil5(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //tindakan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,"+
                                        "rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "dokter.nm_dokter,rawat_inap_dr.biaya_rawat "+
                                        "from rawat_inap_dr inner join jns_perawatan_inap inner join dokter "+
                                        "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_dr.kd_dokter=dokter.kd_dokter where rawat_inap_dr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,"+
                                        "rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "petugas.nama,rawat_inap_pr.biaya_rawat "+
                                        "from rawat_inap_pr inner join jns_perawatan_inap inner join petugas "+
                                        "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_pr.nip=petugas.nip where rawat_inap_pr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#f7fcf2'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }      
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis dan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_jenis_prw,"+
                                        "jns_perawatan_inap.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_inap_drpr.biaya_rawat "+
                                        "from rawat_inap_drpr inner join jns_perawatan_inap inner join dokter inner join petugas "+
                                        "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_drpr.nip=petugas.nip "+
                                        "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter where rawat_inap_drpr.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Inap Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#f7fcf2'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Dokter</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML5.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil6(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select operasi.tgl_operasi,operasi.jenis_anasthesi,operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"+
                                        "operasi.asisten_operator2,operasi.asisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "+
                                        "operasi.dokter_anestesi, operasi.asisten_anestesi, operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, operasi.omloop,"+
                                        "operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum, "+
                                        "operasi.kode_paket,paket_operasi.nm_perawatan, operasi.biayaoperator1, operasi.biayaoperator2, operasi.biayaoperator3, "+
                                        "operasi.biayaasisten_operator1, operasi.biayaasisten_operator2, operasi.biayaasisten_operator3, operasi.biayainstrumen, "+
                                        "operasi.biayadokter_anak, operasi.biayaperawaat_resusitas, operasi.biayadokter_anestesi, "+
                                        "operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2, operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3, operasi.biayaperawat_luar, operasi.biayaalat,"+
                                        "operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,"+
                                        "operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum,"+
                                        "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+"+
                                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                                        "operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+"+
                                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+"+
                                        "operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as total from operasi inner join paket_operasi "+
                                        "on operasi.kode_paket=paket_operasi.kode_paket where operasi.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Operasi/VK</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Tindakan</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Anastesi</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_operasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_paket")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+" (");
                                        if(rs3.getDouble("biayaoperator1")>0){
                                            htmlContent.append("Operator 1 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator2")>0){
                                            htmlContent.append("Operator 2 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator3")>0){
                                            htmlContent.append("Operator 3 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator1")>0){
                                            htmlContent.append("Asisten Operator 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator2")>0){
                                            htmlContent.append("Asisten Operator 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator3")>0){
                                            htmlContent.append("Asisten Operator 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayainstrumen")>0){
                                            htmlContent.append("Instrumen : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("instrumen"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anak")>0){
                                            htmlContent.append("Dokter Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anak"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawaat_resusitas")>0){
                                            htmlContent.append("Perawat Resusitas : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawaat_resusitas"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anestesi")>0){
                                            htmlContent.append("Dokter Anestesi : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi")>0){
                                            htmlContent.append("Asisten Anestesi : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi2")>0){
                                            htmlContent.append("Asisten Anestesi 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan")>0){
                                            htmlContent.append("Bidan 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan2")>0){
                                            htmlContent.append("Bidan 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan3")>0){
                                            htmlContent.append("Bidan 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawat_luar")>0){
                                            htmlContent.append("Perawat Luar : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawat_luar"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop")>0){
                                            htmlContent.append("Onloop 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop2")>0){
                                            htmlContent.append("Onloop 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop2"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop3")>0){
                                            htmlContent.append("Onloop 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop3"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop4")>0){
                                            htmlContent.append("Onloop 4 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop4"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop5")>0){
                                            htmlContent.append("Onloop 5 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop5"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_pjanak")>0){
                                            htmlContent.append("Dokter Pj Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_pjanak"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_umum")>0){
                                            htmlContent.append("Dokter Umum : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_umum"))+", ");
                                        }
                                        htmlContent.append(
                                                ")</td>"+
                                                "<td valign='top'>"+rs3.getString("jenis_anasthesi")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML6.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil7(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //tindakan pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_jenis_prw, "+
                                     "jns_perawatan_radiologi.nm_perawatan,petugas.nama,periksa_radiologi.biaya,periksa_radiologi.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_radiologi inner join jns_perawatan_radiologi inner join petugas inner join dokter "+
                                     "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_radiologi.nip=petugas.nip  where periksa_radiologi.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Radiologi</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#f7fcf2'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //hasil pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, hasil from hasil_radiologi where no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Bacaan/Hasil Radiologi</td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#f7fcf2'>Hasil Pemeriksaan</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("hasil")+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //gambar pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, lokasi_gambar from gambar_radiologi where no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Gambar Radiologi</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#f7fcf2'>Gambar Radiologi</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/radiologi/"+rs3.getString("lokasi_gambar")+"'>"+rs3.getString("lokasi_gambar").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML7.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil8(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //tindakan pemeriksaan laborat
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                                     "jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.biaya,periksa_lab.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_lab inner join jns_perawatan_lab inner join petugas inner join dokter "+
                                     "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_lab.nip=petugas.nip  where periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Laboratorium</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#f7fcf2'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#f7fcf2'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"
                                        ); 
                                        try {
                                            rs4=koneksi.prepareStatement(
                                                "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                                                "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                                "detail_periksa_lab.keterangan from detail_periksa_lab inner join "+
                                                "template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                                "where detail_periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"' and "+
                                                "detail_periksa_lab.kd_jenis_prw='"+rs3.getString("kd_jenis_prw")+"' and "+
                                                "detail_periksa_lab.tgl_periksa='"+rs3.getString("tgl_periksa")+"' and "+
                                                "detail_periksa_lab.jam='"+rs3.getString("jam")+"'").executeQuery();
                                            if(rs4.next()){ 
                                                htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top' align='center' bgcolor='#f7fcf2'>Detail Pemeriksaan</td>"+
                                                       "<td valign='top' align='center' bgcolor='#f7fcf2'>Hasil</td>"+
                                                       "<td valign='top' align='center' bgcolor='#f7fcf2'>Nilai Rujukan</td>"+
                                                       "<td valign='top' align='right'></td>"+
                                                    "</tr>");
                                                rs4.beforeFirst();
                                                while(rs4.next()){
                                                    htmlContent.append(
                                                        "<tr>"+
                                                           "<td valign='top' align='center'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'>"+rs4.getString("Pemeriksaan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai")+" "+rs4.getString("satuan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai_rujukan")+"</td>"+
                                                           "<td valign='top' align='right'>"+Valid.SetAngka(rs4.getDouble("biaya_item"))+"</td>"+
                                                        "</tr>"); 
                                                }                                               
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi : "+e);
                                        } finally{
                                            if(rs4!=null){
                                                rs4.close();
                                            }
                                        }
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML8.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil9(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //pemberian obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,databarang.kode_sat, "+
                                    "detail_pemberian_obat.kode_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                                    "databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                                    "on detail_pemberian_obat.kode_brng=databarang.kode_brng  "+
                                    "where detail_pemberian_obat.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemberian Obat/BHP/Alkes</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='35%' bgcolor='#f7fcf2'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Jumlah</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Aturan Pakai</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top'>"+Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='"+rs3.getString("tgl_perawatan")+"' and jam='"+rs3.getString("jam")+"' and no_rawat='"+rs2.getString("no_rawat")+"' and kode_brng='"+rs3.getString("kode_brng")+"'")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Retur Obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select databarang.kode_brng,databarang.nama_brng,detreturjual.kode_sat,detreturjual.h_retur, "+
				    "(detreturjual.jml_retur * -1) as jumlah,(detreturjual.subtotal * -1) as total from detreturjual "+
				    "inner join databarang inner join returjual on detreturjual.kode_brng=databarang.kode_brng "+
				    "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Retur Obat</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='65%' bgcolor='#f7fcf2'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML9.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil10(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //pemberian obat Operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select beri_obat_operasi.tanggal,beri_obat_operasi.kd_obat,beri_obat_operasi.hargasatuan,obatbhp_ok.kode_sat, "+
                                    "beri_obat_operasi.jumlah, obatbhp_ok.nm_obat,(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                                    "from beri_obat_operasi inner join obatbhp_ok  on  beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat  "+
                                    "where beri_obat_operasi.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Penggunaan Obat/BHP Operasi</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Obat/BHP</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tanggal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML10.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil11(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //Resep Pulang
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select resep_pulang.kode_brng,databarang.nama_brng,resep_pulang.dosis,resep_pulang.jml_barang, "+
                                    "databarang.kode_sat,resep_pulang.dosis,resep_pulang.total from resep_pulang inner join databarang "+
                                    "on resep_pulang.kode_brng=databarang.kode_brng where "+
                                    "resep_pulang.no_rawat='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Resep Pulang</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#f7fcf2'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='15%' bgcolor='#f7fcf2'>Dosis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#f7fcf2'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dosis")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml_barang")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML11.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil12(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            StringBuilder htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+KdRw.getText()+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(ChkTanggal.isSelected()==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"'").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' ").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );                            
                            
                            //berkas digital perawatan
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file "+
                                        "from berkas_digital_perawatan inner join master_berkas_digital "+
                                        "on berkas_digital_perawatan.kode=master_berkas_digital.kode "+
                                        "where berkas_digital_perawatan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Berkas Digital Perawatan</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#f7fcf2'>No.</td>"+
                                          "<td valign='top' width='95%' bgcolor='#f7fcf2'>Berkas Digital</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOST()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/radiologi/"+rs3.getString("lokasi_file")+"'>"+rs3.getString("nama").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML7.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void isPasien(){
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",TPasien,KdRw.getText());
    }

    public void setNoRm(String norm,String nama) {
        KdRw.setText(norm);
        TPasien.setText(nama);
    }

}

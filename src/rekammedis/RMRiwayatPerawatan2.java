/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import simrskhanza.DlgPasien;

/**
 *
 * @author windiarto
 */
public final class RMRiwayatPerawatan2 extends javax.swing.JDialog {    
    private validasi Valid=new validasi();    
    private final sekuel Sequel=new sekuel();
    private DefaultTableModel tabModeRegistrasi;
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2,rs3,rs4;
    private Connection koneksi=koneksiDB.condb();
    private int i=0,urut=0,w=0;
    private String kddpjp="",dpjp="",dokterrujukan="",polirujukan="",keputusan="",ke1="",ke2="",ke3="",ke4="",ke5="",ke6="";
    private StringBuilder htmlContent;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public RMRiwayatPerawatan2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        
        tabModeRegistrasi=new DefaultTableModel(null,new Object[]{
                "No.","No.Rawat","Tanggal","Jam","Kd.Dokter","Dokter Dituju/DPJP","Umur","Poliklinik/Kamar","Jenis Bayar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRegistrasi.setModel(tabModeRegistrasi);

        tbRegistrasi.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRegistrasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbRegistrasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(60);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(250);   
            }else if(i==6){
                column.setPreferredWidth(40);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(110);
            }
        }
        tbRegistrasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoRM.setDocument(new batasInput((byte)20).getKata(TKd));
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){                   
                    NoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                    NmPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),2).toString());
                }    
                if(pasien.getTable2().getSelectedRow()!= -1){                   
                    NoRM.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),1).toString());
                    NmPasien.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),2).toString());
                } 
                if(pasien.getTable3().getSelectedRow()!= -1){                   
                    NoRM.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),1).toString());
                    NmPasien.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),2).toString());
                } 
                NoRM.requestFocus();
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
        
        pasien.getTable2().addKeyListener(new KeyListener() {
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
        
        pasien.getTable3().addKeyListener(new KeyListener() {
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}.isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;border: white;}");
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML.setEditable(false);
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
    }
    
    DlgPasien pasien=new DlgPasien(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        R3 = new widget.RadioButton();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        BtnCari1 = new widget.Button();
        label19 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        NoRM = new widget.TextBox();
        NmPasien = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label20 = new widget.Label();
        Jk = new widget.TextBox();
        label21 = new widget.Label();
        TempatLahir = new widget.TextBox();
        label22 = new widget.Label();
        Alamat = new widget.TextBox();
        label23 = new widget.Label();
        GD = new widget.TextBox();
        label24 = new widget.Label();
        IbuKandung = new widget.TextBox();
        TanggalLahir = new widget.TextBox();
        label25 = new widget.Label();
        Agama = new widget.TextBox();
        StatusNikah = new widget.TextBox();
        label26 = new widget.Label();
        Pendidikan = new widget.TextBox();
        label27 = new widget.Label();
        label28 = new widget.Label();
        Bahasa = new widget.TextBox();
        label29 = new widget.Label();
        CacatFisik = new widget.TextBox();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        chkSemua = new widget.CekBox();
        chkDiagnosaPenyakit = new widget.CekBox();
        chkProsedurTindakan = new widget.CekBox();
        chkTriase = new widget.CekBox();
        chkAsuhanKeperawatanRalan = new widget.CekBox();
        chkAsuhanKeperawatanRalanGigi = new widget.CekBox();
        chkAsuhanKeperawatanRalanBayi = new widget.CekBox();
        chkAsuhanKeperawatanRalanKandungan = new widget.CekBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbRegistrasi = new widget.Table();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Resume/Rincian Tindakan/Terapi Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        R1.setBackground(new java.awt.Color(240, 250, 230));
        R1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("3 Riwayat Terakhir");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass5.add(R1);

        R2.setBackground(new java.awt.Color(240, 250, 230));
        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setText("Semua Riwayat");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(105, 23));
        panelGlass5.add(R2);

        R3.setBackground(new java.awt.Color(240, 250, 230));
        R3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R3);
        R3.setText("Tgl.Rawat :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass5.add(R3);

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

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass5.add(Tgl2);

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
        panelGlass5.add(BtnCari1);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(25, 23));
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
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 104));
        panelisi4.setLayout(null);

        label17.setText("Pasien :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label17);
        label17.setBounds(5, 10, 55, 23);

        NoRM.setName("NoRM"); // NOI18N
        NoRM.setPreferredSize(new java.awt.Dimension(100, 23));
        NoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRMKeyPressed(evt);
            }
        });
        panelisi4.add(NoRM);
        NoRM.setBounds(64, 10, 100, 23);

        NmPasien.setEditable(false);
        NmPasien.setName("NmPasien"); // NOI18N
        NmPasien.setPreferredSize(new java.awt.Dimension(220, 23));
        panelisi4.add(NmPasien);
        NmPasien.setBounds(167, 10, 220, 23);

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
        BtnSeek2.setBounds(390, 10, 28, 23);

        label20.setText("J.K. :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label20);
        label20.setBounds(431, 10, 30, 23);

        Jk.setEditable(false);
        Jk.setName("Jk"); // NOI18N
        Jk.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Jk);
        Jk.setBounds(465, 10, 40, 23);

        label21.setText("Tmp/Tgl.Lahir :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label21);
        label21.setBounds(513, 10, 80, 23);

        TempatLahir.setEditable(false);
        TempatLahir.setName("TempatLahir"); // NOI18N
        TempatLahir.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(TempatLahir);
        TempatLahir.setBounds(597, 10, 140, 23);

        label22.setText("Alamat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label22);
        label22.setBounds(5, 40, 55, 23);

        Alamat.setEditable(false);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Alamat);
        Alamat.setBounds(64, 40, 354, 23);

        label23.setText("G.D. :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label23);
        label23.setBounds(431, 40, 30, 23);

        GD.setEditable(false);
        GD.setName("GD"); // NOI18N
        GD.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(GD);
        GD.setBounds(465, 40, 40, 23);

        label24.setText("Ibu Kandung :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label24);
        label24.setBounds(513, 40, 80, 23);

        IbuKandung.setEditable(false);
        IbuKandung.setName("IbuKandung"); // NOI18N
        IbuKandung.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(IbuKandung);
        IbuKandung.setBounds(597, 40, 225, 23);

        TanggalLahir.setEditable(false);
        TanggalLahir.setName("TanggalLahir"); // NOI18N
        TanggalLahir.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(TanggalLahir);
        TanggalLahir.setBounds(739, 10, 83, 23);

        label25.setText("Agama :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label25);
        label25.setBounds(5, 70, 55, 23);

        Agama.setEditable(false);
        Agama.setName("Agama"); // NOI18N
        Agama.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Agama);
        Agama.setBounds(64, 70, 100, 23);

        StatusNikah.setEditable(false);
        StatusNikah.setName("StatusNikah"); // NOI18N
        StatusNikah.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(StatusNikah);
        StatusNikah.setBounds(235, 70, 100, 23);

        label26.setText("Stts.Nikah :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label26);
        label26.setBounds(166, 70, 65, 23);

        Pendidikan.setEditable(false);
        Pendidikan.setName("Pendidikan"); // NOI18N
        Pendidikan.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Pendidikan);
        Pendidikan.setBounds(409, 70, 80, 23);

        label27.setText("Pendidikan :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label27);
        label27.setBounds(335, 70, 70, 23);

        label28.setText("Bahasa :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label28);
        label28.setBounds(490, 70, 50, 23);

        Bahasa.setEditable(false);
        Bahasa.setName("Bahasa"); // NOI18N
        Bahasa.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Bahasa);
        Bahasa.setBounds(544, 70, 100, 23);

        label29.setText("Cacat Fisik :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label29);
        label29.setBounds(643, 70, 70, 23);

        CacatFisik.setEditable(false);
        CacatFisik.setName("CacatFisik"); // NOI18N
        CacatFisik.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(CacatFisik);
        CacatFisik.setBounds(717, 70, 105, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
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

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll.setViewportView(LoadHTML);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(255, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout());

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
        ChkAccor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 255, 248)));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.EAST);

        ScrollMenu.setBorder(null);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        chkSemua.setText("Semua");
        chkSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkSemua.setName("chkSemua"); // NOI18N
        chkSemua.setOpaque(false);
        chkSemua.setPreferredSize(new java.awt.Dimension(230, 20));
        chkSemua.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkSemuaItemStateChanged(evt);
            }
        });
        FormMenu.add(chkSemua);

        chkDiagnosaPenyakit.setSelected(true);
        chkDiagnosaPenyakit.setText("Diagnosa/Penyakit (ICD 10)");
        chkDiagnosaPenyakit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkDiagnosaPenyakit.setName("chkDiagnosaPenyakit"); // NOI18N
        chkDiagnosaPenyakit.setOpaque(false);
        chkDiagnosaPenyakit.setPreferredSize(new java.awt.Dimension(230, 20));
        FormMenu.add(chkDiagnosaPenyakit);

        chkProsedurTindakan.setSelected(true);
        chkProsedurTindakan.setText("Prosedur/Tidakan (ICD 9)");
        chkProsedurTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkProsedurTindakan.setName("chkProsedurTindakan"); // NOI18N
        chkProsedurTindakan.setOpaque(false);
        chkProsedurTindakan.setPreferredSize(new java.awt.Dimension(230, 20));
        FormMenu.add(chkProsedurTindakan);

        chkTriase.setText("Triase IGD/UGD");
        chkTriase.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTriase.setName("chkTriase"); // NOI18N
        chkTriase.setOpaque(false);
        chkTriase.setPreferredSize(new java.awt.Dimension(230, 20));
        FormMenu.add(chkTriase);

        chkAsuhanKeperawatanRalan.setText("Asuhan Keperawatan Ralan Umum");
        chkAsuhanKeperawatanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRalan.setName("chkAsuhanKeperawatanRalan"); // NOI18N
        chkAsuhanKeperawatanRalan.setOpaque(false);
        chkAsuhanKeperawatanRalan.setPreferredSize(new java.awt.Dimension(230, 20));
        FormMenu.add(chkAsuhanKeperawatanRalan);

        chkAsuhanKeperawatanRalanGigi.setText("Asuhan Keperawatan Ralan Gigi");
        chkAsuhanKeperawatanRalanGigi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRalanGigi.setName("chkAsuhanKeperawatanRalanGigi"); // NOI18N
        chkAsuhanKeperawatanRalanGigi.setOpaque(false);
        chkAsuhanKeperawatanRalanGigi.setPreferredSize(new java.awt.Dimension(230, 20));
        FormMenu.add(chkAsuhanKeperawatanRalanGigi);

        chkAsuhanKeperawatanRalanBayi.setText("Asuhan Keperawatan Ralan Bayi/Anak");
        chkAsuhanKeperawatanRalanBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRalanBayi.setName("chkAsuhanKeperawatanRalanBayi"); // NOI18N
        chkAsuhanKeperawatanRalanBayi.setOpaque(false);
        chkAsuhanKeperawatanRalanBayi.setPreferredSize(new java.awt.Dimension(230, 20));
        FormMenu.add(chkAsuhanKeperawatanRalanBayi);

        chkAsuhanKeperawatanRalanKandungan.setText("Asuhan Keperawatan Ralan Kandungan");
        chkAsuhanKeperawatanRalanKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRalanKandungan.setName("chkAsuhanKeperawatanRalanKandungan"); // NOI18N
        chkAsuhanKeperawatanRalanKandungan.setOpaque(false);
        chkAsuhanKeperawatanRalanKandungan.setPreferredSize(new java.awt.Dimension(230, 20));
        FormMenu.add(chkAsuhanKeperawatanRalanKandungan);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame2.add(PanelAccor, java.awt.BorderLayout.WEST);

        TabRawat.addTab("Riwayat Perawatan", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRegistrasi.setName("tbRegistrasi"); // NOI18N
        Scroll1.setViewportView(tbRegistrasi);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Riwayat Kunjungan", internalFrame3);

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

private void NoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRMKeyPressed
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
}//GEN-LAST:event_NoRMKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        pasien.isCek();
        pasien.emptTeks();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
    //Valid.pindah(evt,Tgl2,TKd);
}//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,NoRM);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed

    }//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        if(NoRM.getText().equals("")||NmPasien.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                tampilPerawatan();
            }else if(TabRawat.getSelectedIndex()==1){
                tampilKunjungan();
            }
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        
    }//GEN-LAST:event_TabRawatMouseClicked

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        isMenu();
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void chkSemuaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSemuaItemStateChanged
        if(chkSemua.isSelected()==true){
            chkTriase.setSelected(true);
            chkAsuhanKeperawatanRalan.setSelected(true);
            chkAsuhanKeperawatanRalanGigi.setSelected(true);
            chkAsuhanKeperawatanRalanBayi.setSelected(true);
            chkAsuhanKeperawatanRalanKandungan.setSelected(true);
            chkDiagnosaPenyakit.setSelected(true);
            chkProsedurTindakan.setSelected(true);
        }else{
            chkTriase.setSelected(false);
            chkAsuhanKeperawatanRalan.setSelected(false);
            chkAsuhanKeperawatanRalanGigi.setSelected(false);
            chkAsuhanKeperawatanRalanBayi.setSelected(false);
            chkAsuhanKeperawatanRalanKandungan.setSelected(false);
            chkDiagnosaPenyakit.setSelected(false);
            chkProsedurTindakan.setSelected(false);
        }
    }//GEN-LAST:event_chkSemuaItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMRiwayatPerawatan2 dialog = new RMRiwayatPerawatan2(new javax.swing.JFrame(), true);
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
    private widget.TextBox Agama;
    private widget.TextBox Alamat;
    private widget.TextBox Bahasa;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.TextBox CacatFisik;
    private widget.CekBox ChkAccor;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox GD;
    private widget.TextBox IbuKandung;
    private widget.TextBox Jk;
    private widget.editorpane LoadHTML;
    private widget.TextBox NmPasien;
    private widget.TextBox NoRM;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox Pendidikan;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox StatusNikah;
    private widget.TextBox TKd;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalLahir;
    private widget.TextBox TempatLahir;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.CekBox chkAsuhanKeperawatanRalan;
    private widget.CekBox chkAsuhanKeperawatanRalanBayi;
    private widget.CekBox chkAsuhanKeperawatanRalanGigi;
    private widget.CekBox chkAsuhanKeperawatanRalanKandungan;
    private widget.CekBox chkDiagnosaPenyakit;
    private widget.CekBox chkProsedurTindakan;
    private widget.CekBox chkSemua;
    private widget.CekBox chkTriase;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelisi4;
    private widget.Table tbRegistrasi;
    // End of variables declaration//GEN-END:variables

    public void setNoRm(String norm,String nama) {
        NoRM.setText(norm);
        NmPasien.setText(nama);
        isPasien();
    }

    private void isPasien() {
        try{
            ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.agama,"+
                    "bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,pasien.gol_darah,pasien.nm_ibu,pasien.stts_nikah,pasien.pnd, "+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "where pasien.no_rkm_medis=?");
            try {
                ps.setString(1,NoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    NoRM.setText(rs.getString("no_rkm_medis"));
                    NmPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TempatLahir.setText(rs.getString("tmp_lahir"));
                    TanggalLahir.setText(rs.getString("tgl_lahir"));
                    Alamat.setText(rs.getString("alamat"));
                    GD.setText(rs.getString("gol_darah"));
                    IbuKandung.setText(rs.getString("nm_ibu"));
                    Agama.setText(rs.getString("agama"));
                    StatusNikah.setText(rs.getString("stts_nikah"));
                    Pendidikan.setText(rs.getString("pnd"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    CacatFisik.setText(rs.getString("nama_cacat"));
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
    
    private void tampilKunjungan() {
        Valid.tabelKosong(tabModeRegistrasi);
        try{   
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.status_lanjut,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "poliklinik.kd_poli,poliklinik.nm_poli,penjab.png_jawab from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj  "+
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 3");
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.status_lanjut,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "poliklinik.kd_poli,poliklinik.nm_poli,penjab.png_jawab from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab  on reg_periksa.kd_pj=penjab.kd_pj  "+
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi");
            }else if(R3.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.status_lanjut,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "poliklinik.kd_poli,poliklinik.nm_poli,penjab.png_jawab from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj  "+
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? and "+
                    "reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
            }
            
            try {
                i=0;
                if(R1.isSelected()==true){
                    ps.setString(1,NoRM.getText());
                }else if(R2.isSelected()==true){
                    ps.setString(1,NoRM.getText());
                }else if(R3.isSelected()==true){
                    ps.setString(1,NoRM.getText());
                    ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }                    
                rs=ps.executeQuery();
                while(rs.next()){
                    i++;
                    tabModeRegistrasi.addRow(new String[]{
                        i+"",rs.getString("no_rawat"),rs.getString("tgl_registrasi"),rs.getString("jam_reg"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                        rs.getString("kd_poli")+" "+rs.getString("nm_poli"),rs.getString("png_jawab")
                    });
                    ps2=koneksi.prepareStatement(
                            "select rujukan_internal_poli.kd_dokter,dokter.nm_dokter,"+
                            "rujukan_internal_poli.kd_poli,poliklinik.nm_poli from rujukan_internal_poli "+
                            "inner join dokter inner join poliklinik on rujukan_internal_poli.kd_dokter=dokter.kd_dokter "+
                            "and rujukan_internal_poli.kd_poli=poliklinik.kd_poli where rujukan_internal_poli.no_rawat=?");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs=ps2.executeQuery();
                        while(rs.next()){                            
                            i++;
                            tabModeRegistrasi.addRow(new String[]{
                                i+"",rs.getString("no_rawat"),rs.getString("tgl_registrasi"),"",
                                rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                                rs.getString("kd_poli")+" "+rs.getString("nm_poli"),rs.getString("png_jawab")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }  
                    kddpjp="";
                    dpjp="";
                    if(rs.getString("status_lanjut").equals("Ranap")){
                        kddpjp=Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=?",rs.getString("no_rawat"));
                        if(!kddpjp.equals("")){
                            dpjp=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kddpjp);
                        }else{
                            kddpjp=rs.getString("kd_dokter");
                            dpjp=rs.getString("nm_dokter");
                        }
                    }                        
                    ps2=koneksi.prepareStatement(
                            "select kamar_inap.tgl_masuk,kamar_inap.jam_masuk,kamar_inap.kd_kamar,bangsal.nm_bangsal "+
                            "from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal  "+
                            "on kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=?");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs=ps2.executeQuery();
                        while(rs.next()){                            
                            i++;                            
                            tabModeRegistrasi.addRow(new String[]{
                                i+"",rs.getString("no_rawat"),rs.getString("tgl_masuk"),rs.getString("jam_masuk"),
                                kddpjp,dpjp,rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                                rs.getString("kd_kamar")+" "+rs.getString("nm_bangsal"),rs.getString("png_jawab")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(255,HEIGHT));
            FormMenu.setVisible(true); 
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){  
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);    
            ChkAccor.setVisible(true);
        }
    }

    private void tampilPerawatan() {
        try{   
            htmlContent = new StringBuilder();
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                    "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                    "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 3");
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                    "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                    "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi");
            }else if(R3.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                    "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                    "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? and "+
                    "reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
            }
            
            try {
                i=0;
                if(R1.isSelected()==true){
                    ps.setString(1,NoRM.getText());
                }else if(R2.isSelected()==true){
                    ps.setString(1,NoRM.getText());
                }else if(R3.isSelected()==true){
                    ps.setString(1,NoRM.getText());
                    ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }            
                urut=1;
                rs=ps.executeQuery();
                while(rs.next()){
                    try {
                        dokterrujukan="";
                        polirujukan="";
                        rs2=koneksi.prepareStatement(
                            "select poliklinik.nm_poli,dokter.nm_dokter from rujukan_internal_poli "+
                            "inner join poliklinik on rujukan_internal_poli.kd_poli=poliklinik.kd_poli "+
                            "inner join dokter on rujukan_internal_poli.kd_dokter=dokter.kd_dokter "+
                            "where no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                        while(rs2.next()){
                            polirujukan=polirujukan+", "+rs2.getString("nm_poli");
                            dokterrujukan=dokterrujukan+", "+rs2.getString("nm_dokter");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }   

                    htmlContent.append(
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'>"+urut+"</td>"+
                        "<td valign='top' width='18%'>No.Rawat</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("no_rawat")+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+
                        "<td valign='top' width='18%'>No.Registrasi</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("no_reg")+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+
                        "<td valign='top' width='18%'>Tanggal Registrasi</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg")+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+
                        "<td valign='top' width='18%'>Unit/Poliklinik</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("nm_poli")+polirujukan+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+        
                        "<td valign='top' width='18%'>Dokter</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("nm_dokter")+dokterrujukan+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+
                        "<td valign='top' width='18%'>Cara Bayar</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("png_jawab")+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+        
                        "<td valign='top' width='18%'>Penanggung Jawab</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("p_jawab")+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+         
                        "<td valign='top' width='18%'>Alamat P.J.</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("almt_pj")+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+        
                        "<td valign='top' width='18%'>Hubungan P.J.</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("hubunganpj")+"</td>"+
                      "</tr>"+
                      "<tr class='isi'>"+ 
                        "<td valign='top' width='2%'></td>"+        
                        "<td valign='top' width='18%'>Status</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("status_lanjut")+"</td>"+
                      "</tr>"
                    );                            
                    urut++;
                    //menampilkan triase IGD
                    if(chkTriase.isSelected()==true){
                        //triase primer
                        try {
                            rs2=koneksi.prepareStatement(
                                    "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                                    "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.kd_dokter,data_triase_igd.tekanan_darah,"+
                                    "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                    "data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                                    "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus,dokter.nm_dokter "+
                                    "from data_triase_igdprimer inner join data_triase_igd on data_triase_igd.no_rawat=data_triase_igdprimer.no_rawat "+
                                    "inner join master_triase_macam_kasus on data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus "+
                                    "inner join dokter on data_triase_igdprimer.kd_dokter=data_triase_igdprimer.kd_dokter where data_triase_igd.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                    "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Triase Gawat Darurat</td>"+
                                      "<td valign='top' width='1%' align='center'>:</td>"+
                                      "<td valign='top' width='79%'>"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                          "<tr class='isi'>"+
                                              "<td valign='top'>Cara Masuk</td><td valign='top'>: "+rs2.getString("cara_masuk")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+            
                                              "<td valign='top'>Transportasi</td><td valign='top'>: "+rs2.getString("alat_transportasi")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td valign='top'>Alasan Kedatangan</td><td valign='top'>: "+rs2.getString("alasan_kedatangan")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td valign='top'>Keterangan Kedatangan</td><td valign='top'>: "+rs2.getString("keterangan_kedatangan")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td valign='top'>Macam Kasus</td><td valign='top'>: "+rs2.getString("macam_kasus")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%'>Keterangan</td>"+
                                              "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65%'>Triase Primer</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td valign='middle'>Keluhan Utama</td>"+
                                              "<td valign='middle'>"+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td valign='middle'>Tanda Vital</td>"+
                                              "<td valign='middle'>Suhu (C) : "+rs2.getString("suhu")+", Nyeri : "+rs2.getString("nyeri")+", Tensi : "+rs2.getString("tekanan_darah")+", Nadi(/menit) : "+rs2.getString("nadi")+", Saturasi O²(%) : "+rs2.getString("saturasi_o2")+", Respirasi(/menit) : "+rs2.getString("pernapasan")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td valign='middle'>Kebutuhan Khusus</td>"+
                                              "<td valign='middle'>"+rs2.getString("kebutuhan_khusus")+"</td>"+
                                          "</tr>"
                                );
                                
                                try {
                                    rs3=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala1 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan "+
                                        "inner join data_triase_igddetail_skala1 on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 "+
                                        "where data_triase_igddetail_skala1.no_rawat='"+rs.getString("no_rawat")+"' "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                    if(rs3.next()){
                                        htmlContent.append(                             
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                "<td valign='middle' bgcolor='#AA0000' color='ffffff' align='center'>Immediate/Segera</td>"+
                                            "</tr>"
                                        );
                                        rs3.beforeFirst();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle'>"+rs3.getString("nama_pemeriksaan")+"</td>"+
                                                    "<td valign='middle' bgcolor='#AA0000' color='ffffff'>"+
                                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                            );
                                            try {
                                                rs4=koneksi.prepareStatement(
                                                    "select master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                                    "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where "+
                                                    "master_triase_skala1.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala1.no_rawat='"+rs.getString("no_rawat")+"' "+
                                                    "order by data_triase_igddetail_skala1.kode_skala1").executeQuery();
                                                while(rs4.next()){
                                                    htmlContent.append(                             
                                                        "<tr class='isi'>"+
                                                            "<td border='0' valign='middle' bgcolor='#AA0000' color='ffffff' width='100%'>"+rs4.getString("pengkajian_skala1")+"</td>"+
                                                        "</tr>"
                                                    );
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif Master Triase Skala1 : "+e);
                                            } finally{
                                                if(rs4!=null){
                                                    rs4.close();
                                                }
                                            }
                                            htmlContent.append(
                                                        "</table>"+
                                                    "</td>"+
                                                "</tr>"
                                            );
                                        }
                                        keputusan="#AA0000";
                                    }
                                } catch (Exception e) {
                                    System.out.println("Data Triase IGD Skala 1 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                }

                                try {
                                    rs3=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala2 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan "+
                                        "inner join data_triase_igddetail_skala2 on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat='"+rs.getString("no_rawat")+"' "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                    if(rs3.next()){
                                        htmlContent.append(                             
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                "<td valign='middle' bgcolor='#FF0000' color='ffffff' align='center'>Emergensi</td>"+
                                            "</tr>"
                                        );
                                        rs3.beforeFirst();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle'>"+rs3.getString("nama_pemeriksaan")+"</td>"+
                                                    "<td valign='middle' bgcolor='#FF0000' color='ffffff'>"+
                                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                            );
                                            try {
                                                rs4=koneksi.prepareStatement(
                                                    "select master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                                    "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where "+
                                                    "master_triase_skala2.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala2.no_rawat='"+rs.getString("no_rawat")+"' "+
                                                    "order by data_triase_igddetail_skala2.kode_skala2").executeQuery();
                                                while(rs4.next()){
                                                    htmlContent.append(                             
                                                        "<tr class='isi'>"+
                                                            "<td border='0' valign='middle' bgcolor='#FF0000' color='ffffff' width='100%'>"+rs4.getString("pengkajian_skala2")+"</td>"+
                                                        "</tr>"
                                                    );
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif Master Triase Skala 2 : "+e);
                                            } finally{
                                                if(rs4!=null){
                                                    rs4.close();
                                                }
                                            }
                                            htmlContent.append(
                                                        "</table>"+
                                                    "</td>"+
                                                "</tr>"
                                            );
                                        }
                                        keputusan="#FF0000";
                                    }
                                } catch (Exception e) {
                                    System.out.println("Data Triase IGD Skala 2 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                }
                                    
                                htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Plan/Keputusan</td>"+
                                            "<td valign='middle' bgcolor='"+keputusan+"' color='ffffff'>Zona Merah "+rs2.getString("plan")+"</td>"+
                                        "</tr>"+                       
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>&nbsp;</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>Petugas Triase Primer</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Tanggal & Jam</td>"+
                                            "<td valign='middle'>"+rs2.getString("tanggaltriase")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Catatan</td>"+
                                            "<td valign='middle'>"+rs2.getString("catatan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Dokter IGD</td>"+
                                            "<td valign='middle'>"+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                        "</tr>"+
                                      "</table>"+
                                    "</td>"+
                                  "</tr>");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Data Triase IGD : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                        
                        //triase sekunder
                        try {
                            rs2=koneksi.prepareStatement(
                                    "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                                    "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.kd_dokter,data_triase_igd.tekanan_darah,"+
                                    "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                    "data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                                    "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus,dokter.nm_dokter "+
                                    "from data_triase_igdsekunder inner join data_triase_igd on data_triase_igd.no_rawat=data_triase_igdsekunder.no_rawat "+
                                    "inner join master_triase_macam_kasus on data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus "+
                                    "inner join dokter on data_triase_igdsekunder.kd_dokter=dokter.kd_dokter where data_triase_igd.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Triase Gawat Darurat</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr class='isi'>"+
                                            "<td valign='top'>Cara Masuk</td><td valign='top'>: "+rs2.getString("cara_masuk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+            
                                            "<td valign='top'>Transportasi</td><td valign='top'>: "+rs2.getString("alat_transportasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='top'>Alasan Kedatangan</td><td valign='top'>: "+rs2.getString("alasan_kedatangan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='top'>Keterangan Kedatangan</td><td valign='top'>: "+rs2.getString("keterangan_kedatangan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='top'>Macam Kasus</td><td valign='top'>: "+rs2.getString("macam_kasus")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%'>Keterangan</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65%'>Triase Sekunder</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Anamnesa Singkat</td>"+
                                            "<td valign='middle'>"+rs2.getString("anamnesa_singkat").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Tanda Vital</td>"+
                                            "<td valign='middle'>Suhu (C) : "+rs2.getString("suhu")+", Nyeri : "+rs2.getString("nyeri")+", Tensi : "+rs2.getString("tekanan_darah")+", Nadi(/menit) : "+rs2.getString("nadi")+", Saturasi O²(%) : "+rs2.getString("saturasi_o2")+", Respirasi(/menit) : "+rs2.getString("pernapasan")+"</td>"+
                                        "</tr>"
                                );

                                try {
                                    rs3=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala3 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan "+
                                        "inner join data_triase_igddetail_skala3 on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat='"+rs.getString("no_rawat")+"' "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                    if(rs3.next()){
                                        htmlContent.append(                             
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                "<td valign='middle' bgcolor='#C8C800' color='ffffff' align='center'>Urgensi</td>"+
                                            "</tr>"
                                        );
                                        rs3.beforeFirst();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle'>"+rs3.getString("nama_pemeriksaan")+"</td>"+
                                                    "<td valign='middle' bgcolor='#C8C800' color='ffffff'>"+
                                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                            );
                                            try {
                                                rs4=koneksi.prepareStatement(
                                                    "select master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                                    "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where "+
                                                    "master_triase_skala3.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala3.no_rawat='"+rs.getString("no_rawat")+"' "+
                                                    "order by data_triase_igddetail_skala3.kode_skala3").executeQuery();
                                                while(rs4.next()){
                                                    htmlContent.append(                             
                                                        "<tr class='isi'>"+
                                                            "<td border='0' valign='middle' bgcolor='#C8C800' color='ffffff' width='100%'>"+rs4.getString("pengkajian_skala3")+"</td>"+
                                                        "</tr>"
                                                    );
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif Master Skala 3: "+e);
                                            } finally{
                                                if(rs4!=null){
                                                    rs4.close();
                                                }
                                            }
                                            htmlContent.append(
                                                        "</table>"+
                                                    "</td>"+
                                                "</tr>"
                                            );
                                        }
                                        keputusan="#C8C800";
                                    }
                                } catch (Exception e) {
                                    System.out.println("Data Triase IGD Skala 3 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                }

                                try {
                                    rs3=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala4 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan "+
                                        "inner join data_triase_igddetail_skala4 on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat='"+rs.getString("no_rawat")+"' "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                    if(rs3.next()){
                                        htmlContent.append(                             
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                "<td valign='middle' bgcolor='#00AA00' color='ffffff' align='center'>Semi Urgensi/Urgensi Rendah</td>"+
                                            "</tr>"
                                        );
                                        rs3.beforeFirst();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle'>"+rs3.getString("nama_pemeriksaan")+"</td>"+
                                                    "<td valign='middle' bgcolor='#00AA00' color='ffffff'>"+
                                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                            );
                                            try {
                                                rs4=koneksi.prepareStatement(
                                                    "select master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                                    "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where "+
                                                    "master_triase_skala4.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala4.no_rawat='"+rs.getString("no_rawat")+"' "+
                                                    "order by data_triase_igddetail_skala4.kode_skala4").executeQuery();
                                                while(rs4.next()){
                                                    htmlContent.append(                             
                                                        "<tr class='isi'>"+
                                                            "<td border='0' valign='middle' bgcolor='#00AA00' color='ffffff' width='100%'>"+rs4.getString("pengkajian_skala4")+"</td>"+
                                                        "</tr>"
                                                    );
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif Master Triase Skala 4: "+e);
                                            } finally{
                                                if(rs4!=null){
                                                    rs4.close();
                                                }
                                            }
                                            htmlContent.append(
                                                        "</table>"+
                                                    "</td>"+
                                                "</tr>"
                                            );
                                        }
                                        keputusan="#00AA00";
                                    }
                                } catch (Exception e) {
                                    System.out.println("Data Triase IGD Skala 4 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                }

                                try {
                                    rs3=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala5 on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan "+
                                        "inner join data_triase_igddetail_skala5 on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat='"+rs.getString("no_rawat")+"' "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                    if(rs3.next()){
                                        htmlContent.append(                             
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                "<td valign='middle' bgcolor='#969696' color='ffffff' align='center'>Non Urgensi</td>"+
                                            "</tr>"
                                        );
                                        rs3.beforeFirst();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle'>"+rs3.getString("nama_pemeriksaan")+"</td>"+
                                                    "<td valign='middle' bgcolor='#969696' color='ffffff'>"+
                                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                            );
                                            try {
                                                rs4=koneksi.prepareStatement(
                                                    "select master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                                    "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where "+
                                                    "master_triase_skala5.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala5.no_rawat='"+rs.getString("no_rawat")+"' "+
                                                    "order by data_triase_igddetail_skala5.kode_skala5").executeQuery();
                                                while(rs4.next()){
                                                    htmlContent.append(                             
                                                        "<tr class='isi'>"+
                                                            "<td border='0' valign='middle' bgcolor='#969696' color='ffffff' width='100%'>"+rs4.getString("pengkajian_skala5")+"</td>"+
                                                        "</tr>"
                                                    );
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif Master Triase Skala 5 : "+e);
                                            } finally{
                                                if(rs4!=null){
                                                    rs4.close();
                                                }
                                            }
                                            htmlContent.append(
                                                        "</table>"+
                                                    "</td>"+
                                                "</tr>"
                                            );
                                        }
                                        keputusan="#969696";
                                    }
                                } catch (Exception e) {
                                    System.out.println("Data Triase IGD Skala 5 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                }

                                htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Plan/Keputusan</td>"+
                                            "<td valign='middle' bgcolor='"+keputusan+"' color='ffffff'>"+rs2.getString("plan")+"</td>"+
                                        "</tr>"+                       
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>&nbsp;</td>"+
                                            "<td valign='middle' bgcolor='#FFFAF8' align='center'>Petugas Triase Sekunder</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Tanggal & Jam</td>"+
                                            "<td valign='middle'>"+rs2.getString("tanggaltriase")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Catatan</td>"+
                                            "<td valign='middle'>"+rs2.getString("catatan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>Dokter IGD</td>"+
                                            "<td valign='middle'>"+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                        "</tr>"+
                                      "</table>"+
                                    "</td>"+
                                  "</tr>");
                            }                                    
                        } catch (Exception e) {
                            System.out.println("Notifikasi Triase Sekuder : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan asuhan awal keperawatan rawat jalan
                    if(chkAsuhanKeperawatanRalan.isSelected()==true){
                        try {
                            rs2=koneksi.prepareStatement(
                                "select penilaian_awal_keperawatan_ralan.tanggal,penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                                "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                                "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                                "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                                "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                                "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                                "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                                "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                                "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                                "from penilaian_awal_keperawatan_ralan inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip where "+
                                "penilaian_awal_keperawatan_ralan.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Penilaian Awal Keperawatan Rawat Jalan</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                                );
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "YANG MELAKUKAN PENGKAJIAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>Tanggal : "+rs2.getString("tanggal")+"</td>"+
                                                      "<td width='33%' border='0'>Petugas : "+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                                      "<td width='33%' border='0'>Informasi didapat dari : "+rs2.getString("informasi")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "I. KEADAAN UMUM"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='20%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                                      "<td width='20%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                                      "<td width='20%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                                      "<td width='20%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                                      "<td width='20%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "II. STATUS NUTRISI"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                                      "<td width='33%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                                      "<td width='33%' border='0'>BMI : "+rs2.getString("bmi")+" Kg/m²</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "III. RIWAYAT KESEHATAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd")+"</td>"+
                                                      "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk")+"</td>"+
                                                      "<td width='50%'>Riwayat Pengobatan : "+rs2.getString("rpd")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "IV. FUNGSIONAL"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>Alat Bantu : "+rs2.getString("alat_bantu")+(rs2.getString("ket_bantu").equals("")?"":", "+rs2.getString("ket_bantu"))+"</td>"+
                                                      "<td width='33%' border='0'>Prothesa : "+rs2.getString("prothesa")+(rs2.getString("ket_pro").equals("")?"":", "+rs2.getString("ket_pro"))+"</td>"+
                                                      "<td width='33%' border='0'>Aktivitas Sehari-hari ( ADL ) : "+rs2.getString("adl")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "V. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN BUDAYA"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Status Psikologis</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("status_psiko")+(rs2.getString("ket_psiko").equals("")?"":", "+rs2.getString("ket_psiko"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td border='0' colspan='2'>Status Sosial dan ekonomi :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;a. Hubungan pasien dengan anggota keluarga</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("hub_keluarga")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;b. Tinggal dengan</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("tinggal_dengan")+(rs2.getString("ket_tinggal").equals("")?"":", "+rs2.getString("ket_tinggal"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;c. Ekonomi</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("ekonomi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("budaya")+(rs2.getString("ket_budaya").equals("")?"":", "+rs2.getString("ket_budaya"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Edukasi diberikan kepada</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("edukasi")+(rs2.getString("ket_edukasi").equals("")?"":", "+rs2.getString("ket_edukasi"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VI. PENILAIAN RESIKO JATUH"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td colpsan='2' border='0'>a. Cara Berjalan :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;1. Tidak seimbang / sempoyongan / limbung</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_a")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_b")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>b. Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja / benda lain sebagai penopang</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_c")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td colspan='2' border='0'>Hasil : "+rs2.getString("hasil")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dilaporkan kepada dokter ? "+rs2.getString("lapor")+(rs2.getString("ket_lapor").equals("")?"":" Jam dilaporkan : "+rs2.getString("ket_lapor"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VII. SKRINING GIZI"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>No</td>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70%'>Parameter</td>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='25%' colspan='2'>Nilai</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td valign='top'>1</td>"+
                                                      "<td valign='top'>Apakah ada penurunan berat badan yang tidak diinginkan selama enam bulan terakhir ?</td>"+
                                                      "<td valign='top' align='center' width='20%'>"+rs2.getString("sg1")+"</td>"+
                                                      "<td valign='top' align='right' width='5%'>"+rs2.getString("nilai1")+"&nbsp;&nbsp;</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td valign='top'>2</td>"+
                                                      "<td valign='top'>Apakah nafsu makan berkurang karena tidak nafsu makan ?</td>"+
                                                      "<td valign='top' align='center' width='20%'>"+rs2.getString("sg2")+"</td>"+
                                                      "<td valign='top' align='right' width='5%'>"+rs2.getString("nilai2")+"&nbsp;&nbsp;</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td valign='top' align='right' colspan='2'>Total Skor</td>"+
                                                      "<td valign='top' align='right' colspan='2'>"+rs2.getString("total_hasil")+"&nbsp;&nbsp;</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VIII. PENILAIAN TINGKAT NYERI"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Tingkat Nyeri : "+rs2.getString("nyeri")+", Waktu / Durasi : "+rs2.getString("durasi")+" Menit</td>"+
                                                      "<td width='50%' border='0'>Penyebab : "+rs2.getString("provokes")+(rs2.getString("ket_provokes").equals("")?"":", "+rs2.getString("ket_provokes"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Kualitas : "+rs2.getString("quality")+(rs2.getString("ket_quality").equals("")?"":", "+rs2.getString("ket_quality"))+"</td>"+
                                                      "<td width='50%' border='0'>Severity : Skala Nyeri "+rs2.getString("skala_nyeri")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td colspan='0' border='0'>Wilayah :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;Lokasi : "+rs2.getString("lokasi")+"</td>"+
                                                      "<td width='50%' border='0'>Menyebar : "+rs2.getString("menyebar")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Nyeri hilang bila : "+rs2.getString("nyeri_hilang")+(rs2.getString("ket_nyeri").equals("")?"":", "+rs2.getString("ket_nyeri"))+"</td>"+
                                                      "<td width='50%' border='0'>Diberitahukan pada dokter ? "+rs2.getString("pada_dokter")+(rs2.getString("ket_dokter").equals("")?"":", Jam : "+rs2.getString("ket_dokter"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>MASALAH KEPERAWATAN :</td>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>RENCANA KEPERAWATAN :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                       "<td>");
                                    try {
                                        rs3=koneksi.prepareStatement(
                                            "select master_masalah_keperawatan.kode_masalah,master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                                            "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                                            "where penilaian_awal_keperawatan_ralan_masalah.no_rawat='"+rs.getString("no_rawat")+"' order by kode_masalah").executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(rs3.getString("nama_masalah")+"<br>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }
                                    htmlContent.append("</td>"+
                                                       "<td>"+rs2.getString("rencana")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"
                                    );  
                                }
                                htmlContent.append(
                                      "</table>"+
                                    "</td>"+
                                  "</tr>");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan asuhan awal keperawatan rawat jalan gigi
                    if(chkAsuhanKeperawatanRalanGigi.isSelected()==true){
                        try {
                            rs2=koneksi.prepareStatement(
                                    "select penilaian_awal_keperawatan_gigi.tanggal,penilaian_awal_keperawatan_gigi.informasi,penilaian_awal_keperawatan_gigi.td,penilaian_awal_keperawatan_gigi.nadi,penilaian_awal_keperawatan_gigi.rr,penilaian_awal_keperawatan_gigi.suhu,penilaian_awal_keperawatan_gigi.bb,"+
                                    "penilaian_awal_keperawatan_gigi.tb,penilaian_awal_keperawatan_gigi.bmi,penilaian_awal_keperawatan_gigi.keluhan_utama,penilaian_awal_keperawatan_gigi.riwayat_penyakit,penilaian_awal_keperawatan_gigi.ket_riwayat_penyakit,"+
                                    "penilaian_awal_keperawatan_gigi.alergi,penilaian_awal_keperawatan_gigi.riwayat_perawatan_gigi,penilaian_awal_keperawatan_gigi.ket_riwayat_perawatan_gigi,penilaian_awal_keperawatan_gigi.kebiasaan_sikat_gigi,"+
                                    "penilaian_awal_keperawatan_gigi.kebiasaan_lain,penilaian_awal_keperawatan_gigi.ket_kebiasaan_lain,penilaian_awal_keperawatan_gigi.obat_yang_diminum_saatini,penilaian_awal_keperawatan_gigi.alat_bantu,"+
                                    "penilaian_awal_keperawatan_gigi.ket_alat_bantu,penilaian_awal_keperawatan_gigi.prothesa,penilaian_awal_keperawatan_gigi.ket_pro,penilaian_awal_keperawatan_gigi.status_psiko,penilaian_awal_keperawatan_gigi.ket_psiko,"+
                                    "penilaian_awal_keperawatan_gigi.hub_keluarga,penilaian_awal_keperawatan_gigi.tinggal_dengan,penilaian_awal_keperawatan_gigi.ket_tinggal,penilaian_awal_keperawatan_gigi.ekonomi,penilaian_awal_keperawatan_gigi.budaya,"+
                                    "penilaian_awal_keperawatan_gigi.ket_budaya,penilaian_awal_keperawatan_gigi.edukasi,penilaian_awal_keperawatan_gigi.ket_edukasi,penilaian_awal_keperawatan_gigi.berjalan_a,penilaian_awal_keperawatan_gigi.berjalan_b,"+
                                    "penilaian_awal_keperawatan_gigi.berjalan_c,penilaian_awal_keperawatan_gigi.hasil,penilaian_awal_keperawatan_gigi.lapor,penilaian_awal_keperawatan_gigi.ket_lapor,penilaian_awal_keperawatan_gigi.nyeri,"+
                                    "penilaian_awal_keperawatan_gigi.lokasi,penilaian_awal_keperawatan_gigi.skala_nyeri,penilaian_awal_keperawatan_gigi.durasi,penilaian_awal_keperawatan_gigi.frekuensi,penilaian_awal_keperawatan_gigi.nyeri_hilang,"+
                                    "penilaian_awal_keperawatan_gigi.ket_nyeri,penilaian_awal_keperawatan_gigi.pada_dokter,penilaian_awal_keperawatan_gigi.ket_dokter,penilaian_awal_keperawatan_gigi.kebersihan_mulut,penilaian_awal_keperawatan_gigi.mukosa_mulut,"+
                                    "penilaian_awal_keperawatan_gigi.karies,penilaian_awal_keperawatan_gigi.karang_gigi,penilaian_awal_keperawatan_gigi.gingiva,penilaian_awal_keperawatan_gigi.palatum,penilaian_awal_keperawatan_gigi.rencana,"+
                                    "penilaian_awal_keperawatan_gigi.nip,petugas.nama "+
                                    "from penilaian_awal_keperawatan_gigi inner join petugas on penilaian_awal_keperawatan_gigi.nip=petugas.nip "+
                                    "where penilaian_awal_keperawatan_gigi.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Penilaian Awal Keperawatan Rawat Jalan Gigi & Mulut</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                                );
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "YANG MELAKUKAN PENGKAJIAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>Tanggal : "+rs2.getString("tanggal")+"</td>"+
                                                      "<td width='33%' border='0'>Petugas : "+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                                      "<td width='33%' border='0'>Informasi didapat dari : "+rs2.getString("informasi")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "I. KEADAAN UMUM"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='20%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                                      "<td width='20%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                                      "<td width='20%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                                      "<td width='20%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "II. STATUS NUTRISI"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                                      "<td width='33%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                                      "<td width='33%' border='0'>BMI : "+rs2.getString("bmi")+" Kg/m²</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "III. RIWAYAT KESEHATAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%'>Riwayat Penyakit : "+rs2.getString("riwayat_penyakit")+(rs2.getString("ket_riwayat_penyakit").equals("")?"":", "+rs2.getString("ket_riwayat_penyakit"))+"</td>"+
                                                      "<td width='50%'>Riwayat Perawatan Gigi : "+rs2.getString("riwayat_perawatan_gigi")+(rs2.getString("ket_riwayat_perawatan_gigi").equals("")?"":", "+rs2.getString("ket_riwayat_perawatan_gigi"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                                      "<td width='50%'>Kebiasaan Lain : "+rs2.getString("kebiasaan_lain")+(rs2.getString("ket_kebiasaan_lain").equals("")?"":", "+rs2.getString("ket_kebiasaan_lain"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%'>Kebiasaan Sikat Gigi : "+rs2.getString("kebiasaan_sikat_gigi")+"</td>"+
                                                      "<td width='50%'>Obat Yang Diminum Saat Ini : "+rs2.getString("obat_yang_diminum_saatini")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "IV. FUNGSIONAL"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Alat Bantu : "+rs2.getString("alat_bantu")+(rs2.getString("ket_alat_bantu").equals("")?"":", "+rs2.getString("ket_alat_bantu"))+"</td>"+
                                                      "<td width='50%' border='0'>Prothesa : "+rs2.getString("prothesa")+(rs2.getString("ket_pro").equals("")?"":", "+rs2.getString("ket_pro"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "V. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN BUDAYA"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Status Psikologis</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("status_psiko")+(rs2.getString("ket_psiko").equals("")?"":", "+rs2.getString("ket_psiko"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td border='0' colspan='2'>Status Sosial dan ekonomi :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;a. Hubungan pasien dengan anggota keluarga</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("hub_keluarga")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;b. Tinggal dengan</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("tinggal_dengan")+(rs2.getString("ket_tinggal").equals("")?"":", "+rs2.getString("ket_tinggal"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;c. Ekonomi</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("ekonomi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("budaya")+(rs2.getString("ket_budaya").equals("")?"":", "+rs2.getString("ket_budaya"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Edukasi diberikan kepada</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("edukasi")+(rs2.getString("ket_edukasi").equals("")?"":", "+rs2.getString("ket_edukasi"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VI. PENILAIAN RESIKO JATUH"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td colpsan='2' border='0'>a. Cara Berjalan :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;1. Tidak seimbang / sempoyongan / limbung</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_a")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_b")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>b. Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja / benda lain sebagai penopang</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_c")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td colspan='2' border='0'>Hasil : "+rs2.getString("hasil")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dilaporkan kepada dokter ? "+rs2.getString("lapor")+(rs2.getString("ket_lapor").equals("")?"":" Jam dilaporkan : "+rs2.getString("ket_lapor"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VII. PENILAIAN TINGKAT NYERI"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Tingkat Nyeri : "+rs2.getString("nyeri")+"</td>"+
                                                      "<td width='50%' border='0'>Skala Nyeri : "+rs2.getString("skala_nyeri")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Lokasi : "+rs2.getString("lokasi")+"</td>"+
                                                      "<td width='50%' border='0'>Durasi : "+rs2.getString("durasi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Frekuensi : "+rs2.getString("frekuensi")+"</td>"+
                                                      "<td width='50%' border='0'>Nyeri hilang bila : "+rs2.getString("nyeri_hilang")+(rs2.getString("ket_nyeri").equals("")?"":", "+rs2.getString("ket_nyeri"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td border='0'  colspan='2'>Diberitahukan pada dokter ? "+rs2.getString("pada_dokter")+(rs2.getString("ket_dokter").equals("")?"":" Jam "+rs2.getString("ket_dokter"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                            "<td valign='top'>"+
                                               "VIII. PENILAIAN INTRAORAL"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Kebersihan Mulut : "+rs2.getString("kebersihan_mulut")+"</td>"+
                                                      "<td width='50%' border='0'>Mukosa Mulut : "+rs2.getString("mukosa_mulut")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Karies : "+rs2.getString("karies")+"</td>"+
                                                      "<td width='50%' border='0'>Karang Gigi : "+rs2.getString("karang_gigi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Gingiva : "+rs2.getString("gingiva")+"</td>"+
                                                      "<td width='50%' border='0'>Palatum : "+rs2.getString("palatum")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>MASALAH KEPERAWATAN :</td>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>RENCANA KEPERAWATAN :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                       "<td>");
                                    try {
                                        rs3=koneksi.prepareStatement(
                                            "select master_masalah_keperawatan_gigi.kode_masalah,master_masalah_keperawatan_gigi.nama_masalah from master_masalah_keperawatan_gigi "+
                                            "inner join penilaian_awal_keperawatan_gigi_masalah on penilaian_awal_keperawatan_gigi_masalah.kode_masalah=master_masalah_keperawatan_gigi.kode_masalah "+
                                            "where penilaian_awal_keperawatan_gigi_masalah.no_rawat='"+rs.getString("no_rawat")+"' order by kode_masalah").executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(rs3.getString("nama_masalah")+"<br>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }
                                    htmlContent.append("</td>"+
                                                       "<td>"+rs2.getString("rencana")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"
                                    );    
                                }
                                htmlContent.append(
                                      "</table>"+
                                    "</td>"+
                                  "</tr>");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan asuhan awal keperawatan rawat jalan bayi
                    if(chkAsuhanKeperawatanRalanBayi.isSelected()==true){
                        try {
                            rs2=koneksi.prepareStatement(
                                    "select penilaian_awal_keperawatan_ralan_bayi.tanggal,penilaian_awal_keperawatan_ralan_bayi.informasi,penilaian_awal_keperawatan_ralan_bayi.td,penilaian_awal_keperawatan_ralan_bayi.nadi,penilaian_awal_keperawatan_ralan_bayi.rr,penilaian_awal_keperawatan_ralan_bayi.suhu,penilaian_awal_keperawatan_ralan_bayi.gcs,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.bb,penilaian_awal_keperawatan_ralan_bayi.tb,penilaian_awal_keperawatan_ralan_bayi.lp,penilaian_awal_keperawatan_ralan_bayi.lk,penilaian_awal_keperawatan_ralan_bayi.ld,penilaian_awal_keperawatan_ralan_bayi.keluhan_utama,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.rpd,penilaian_awal_keperawatan_ralan_bayi.rpk,penilaian_awal_keperawatan_ralan_bayi.rpo,penilaian_awal_keperawatan_ralan_bayi.alergi,penilaian_awal_keperawatan_ralan_bayi.anakke,penilaian_awal_keperawatan_ralan_bayi.darisaudara,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.caralahir,penilaian_awal_keperawatan_ralan_bayi.ket_caralahir,penilaian_awal_keperawatan_ralan_bayi.umurkelahiran,penilaian_awal_keperawatan_ralan_bayi.kelainanbawaan,penilaian_awal_keperawatan_ralan_bayi.ket_kelainan_bawaan,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.usiatengkurap,penilaian_awal_keperawatan_ralan_bayi.usiaduduk,penilaian_awal_keperawatan_ralan_bayi.usiaberdiri,penilaian_awal_keperawatan_ralan_bayi.usiagigipertama,penilaian_awal_keperawatan_ralan_bayi.usiaberjalan,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.usiabicara,penilaian_awal_keperawatan_ralan_bayi.usiamembaca,penilaian_awal_keperawatan_ralan_bayi.usiamenulis,penilaian_awal_keperawatan_ralan_bayi.gangguanemosi,penilaian_awal_keperawatan_ralan_bayi.alat_bantu,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.ket_bantu,penilaian_awal_keperawatan_ralan_bayi.prothesa,penilaian_awal_keperawatan_ralan_bayi.ket_pro,penilaian_awal_keperawatan_ralan_bayi.adl,penilaian_awal_keperawatan_ralan_bayi.status_psiko,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.ket_psiko,penilaian_awal_keperawatan_ralan_bayi.hub_keluarga,penilaian_awal_keperawatan_ralan_bayi.pengasuh,penilaian_awal_keperawatan_ralan_bayi.ket_pengasuh,penilaian_awal_keperawatan_ralan_bayi.ekonomi,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.budaya,penilaian_awal_keperawatan_ralan_bayi.ket_budaya,penilaian_awal_keperawatan_ralan_bayi.edukasi,penilaian_awal_keperawatan_ralan_bayi.ket_edukasi,penilaian_awal_keperawatan_ralan_bayi.berjalan_a,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.berjalan_b,penilaian_awal_keperawatan_ralan_bayi.berjalan_c,penilaian_awal_keperawatan_ralan_bayi.hasil,penilaian_awal_keperawatan_ralan_bayi.lapor,penilaian_awal_keperawatan_ralan_bayi.ket_lapor,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.sg1,penilaian_awal_keperawatan_ralan_bayi.nilai1,penilaian_awal_keperawatan_ralan_bayi.sg2,penilaian_awal_keperawatan_ralan_bayi.nilai2,penilaian_awal_keperawatan_ralan_bayi.sg3,penilaian_awal_keperawatan_ralan_bayi.nilai3,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.sg4,penilaian_awal_keperawatan_ralan_bayi.nilai4,penilaian_awal_keperawatan_ralan_bayi.total_hasil,penilaian_awal_keperawatan_ralan_bayi.wajah,penilaian_awal_keperawatan_ralan_bayi.nilaiwajah,penilaian_awal_keperawatan_ralan_bayi.kaki,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.nilaikaki,penilaian_awal_keperawatan_ralan_bayi.aktifitas,penilaian_awal_keperawatan_ralan_bayi.nilaiaktifitas,penilaian_awal_keperawatan_ralan_bayi.menangis,penilaian_awal_keperawatan_ralan_bayi.nilaimenangis,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.bersuara,penilaian_awal_keperawatan_ralan_bayi.nilaibersuara,penilaian_awal_keperawatan_ralan_bayi.hasilnyeri,penilaian_awal_keperawatan_ralan_bayi.nyeri,penilaian_awal_keperawatan_ralan_bayi.lokasi,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.durasi,penilaian_awal_keperawatan_ralan_bayi.frekuensi,penilaian_awal_keperawatan_ralan_bayi.nyeri_hilang,penilaian_awal_keperawatan_ralan_bayi.ket_nyeri,penilaian_awal_keperawatan_ralan_bayi.pada_dokter,"+
                                    "penilaian_awal_keperawatan_ralan_bayi.ket_dokter,penilaian_awal_keperawatan_ralan_bayi.rencana,penilaian_awal_keperawatan_ralan_bayi.nip,petugas.nama "+
                                    "from penilaian_awal_keperawatan_ralan_bayi inner join petugas on penilaian_awal_keperawatan_ralan_bayi.nip=petugas.nip "+
                                    "where penilaian_awal_keperawatan_ralan_bayi.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Penilaian Awal Keperawatan Rawat Jalan Bayi/Anak</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                                );
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "YANG MELAKUKAN PENGKAJIAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>Tanggal : "+rs2.getString("tanggal")+"</td>"+
                                                      "<td width='33%' border='0'>Petugas : "+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                                      "<td width='33%' border='0'>Informasi didapat dari : "+rs2.getString("informasi")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "I. KEADAAN UMUM"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='20%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                                      "<td width='20%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                                      "<td width='20%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                                      "<td width='20%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                                      "<td width='20%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+" °C</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='20%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                                      "<td width='20%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                                      "<td width='20%' border='0'>LP : "+rs2.getString("lp")+" Cm</td>"+
                                                      "<td width='20%' border='0'>LK : "+rs2.getString("lk")+" Cm</td>"+
                                                      "<td width='20%' border='0'>LD : "+rs2.getString("ld")+" Cm</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "II. RIWAYAT KESEHATAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd")+"</td>"+
                                                      "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk")+"</td>"+
                                                      "<td width='50%'>Riwayat Pengobatan : "+rs2.getString("rpd")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "III. RIWAYAT TUMBUH KEMBANG DAN PERINATAL CARE"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Riwayat Kelahiran : Anak ke "+rs2.getString("anakke")+" dari "+rs2.getString("darisaudara")+" saudara</td>"+
                                                      "<td width='50%' border='0'>Cara kelahiran : "+rs2.getString("caralahir")+(rs2.getString("ket_caralahir").equals("")?"":", "+rs2.getString("ket_caralahir"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Umur Kelahiran : "+rs2.getString("umurkelahiran")+"</td>"+
                                                      "<td width='50%' border='0'>Kelainan Bawaan : "+rs2.getString("kelainanbawaan")+(rs2.getString("ket_kelainan_bawaan").equals("")?"":", "+rs2.getString("ket_kelainan_bawaan"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "IV. RIWAYAT IMUNISASI"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='70%' bgcolor='#FFFAF8' align='center' valign='middle'>Imunisasi</td>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle'>1</td>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle'>2</td>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle'>3</td>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle'>4</td>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle'>5</td>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle'>6</td>"+
                                                  "</tr>");
                                    try {
                                        rs3=koneksi.prepareStatement(
                                            "select master_imunisasi.kode_imunisasi,master_imunisasi.nama_imunisasi from master_imunisasi inner join riwayat_imunisasi on riwayat_imunisasi.kode_imunisasi=master_imunisasi.kode_imunisasi "+
                                            "where riwayat_imunisasi.no_rkm_medis='"+NoRM.getText()+"' group by master_imunisasi.kode_imunisasi order by master_imunisasi.kode_imunisasi").executeQuery();
                                        while(rs3.next()){
                                            ke1="";ke2="";ke3="";ke4="";ke5="";ke6="";
                                            try {
                                                rs4=koneksi.prepareStatement("select * from riwayat_imunisasi where no_rkm_medis='"+NoRM.getText()+"' and kode_imunisasi='"+rs3.getString("kode_imunisasi")+"'").executeQuery();
                                                while(rs4.next()){
                                                    if(rs4.getInt("no_imunisasi")==1){
                                                        ke1="&#10003;";
                                                    }
                                                    if(rs4.getInt("no_imunisasi")==2){
                                                        ke2="&#10003;";
                                                    }
                                                    if(rs4.getInt("no_imunisasi")==3){
                                                        ke3="&#10003;";
                                                    }
                                                    if(rs4.getInt("no_imunisasi")==4){
                                                        ke4="&#10003;";
                                                    }
                                                    if(rs4.getInt("no_imunisasi")==5){
                                                        ke5="&#10003;";
                                                    }
                                                    if(rs4.getInt("no_imunisasi")==6){
                                                        ke6="&#10003;";
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif : "+e);
                                            } finally{
                                                if(rs4!=null){
                                                    rs4.close();
                                                }
                                            }
                                            htmlContent.append("<tr>"+
                                                                  "<td align='left'>"+rs3.getString("nama_imunisasi")+"</td>"+
                                                                  "<td align='center'>"+ke1+"</td>"+
                                                                  "<td align='center'>"+ke2+"</td>"+
                                                                  "<td align='center'>"+ke3+"</td>"+
                                                                  "<td align='center'>"+ke4+"</td>"+
                                                                  "<td align='center'>"+ke5+"</td>"+
                                                                  "<td align='center'>"+ke6+"</td>"+
                                                               "</tr>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }

                                    htmlContent.append(
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "V. RIWAYAT TUMBUH KEMBANG ANAK"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>a. Tengkurap, usia "+rs2.getString("usiatengkurap")+"</td>"+
                                                      "<td width='33%' border='0'>b. Duduk, usia "+rs2.getString("usiaduduk")+"</td>"+
                                                      "<td width='33%' border='0'>c. Berdiri, usia "+rs2.getString("usiaberdiri")+"</td>"+
                                                      "<td width='33%' border='0'>d. Pertumbuhan gigi pertama, usia "+rs2.getString("usiagigipertama")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>e. Berjalan, usia "+rs2.getString("usiaberjalan")+"</td>"+
                                                      "<td width='33%' border='0'>f. Bicara, usia "+rs2.getString("usiabicara")+"</td>"+
                                                      "<td width='33%' border='0'>g. Mulai bisa membaca, usia "+rs2.getString("usiamembaca")+"</td>"+
                                                      "<td width='33%' border='0'>h. Mulai bisa menulis, usia "+rs2.getString("usiamenulis")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='100%' border='0' colspan='4'>Gangguan perkembangan mental / emosi, bila ada, jelaskan "+rs2.getString("gangguanemosi")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VI. FUNGSIONAL"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>Alat Bantu : "+rs2.getString("alat_bantu")+(rs2.getString("ket_bantu").equals("")?"":", "+rs2.getString("ket_bantu"))+"</td>"+
                                                      "<td width='33%' border='0'>Prothesa : "+rs2.getString("prothesa")+(rs2.getString("ket_pro").equals("")?"":", "+rs2.getString("ket_pro"))+"</td>"+
                                                      "<td width='33%' border='0'>Aktivitas Sehari-hari ( ADL ) : "+rs2.getString("adl")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VII. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN BUDAYA"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Status Psikologis</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("status_psiko")+(rs2.getString("ket_psiko").equals("")?"":", "+rs2.getString("ket_psiko"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td border='0' colspan='2'>Status Sosial dan Ekonomi :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;a. Hubungan pasien dengan anggota keluarga</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("hub_keluarga")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;b. Pengasuh</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("pengasuh")+(rs2.getString("ket_pengasuh").equals("")?"":", "+rs2.getString("ket_pengasuh"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;c. Ekonomi (Orang tua)</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("ekonomi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("budaya")+(rs2.getString("ket_budaya").equals("")?"":", "+rs2.getString("ket_budaya"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Edukasi diberikan kepada</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("edukasi")+(rs2.getString("ket_edukasi").equals("")?"":", "+rs2.getString("ket_edukasi"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VIII. PENILAIAN RESIKO JATUH"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td colpsan='2' border='0'>a. Cara Berjalan :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;1. Tidak seimbang / sempoyongan / limbung</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_a")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_b")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>b. Duduk di kursi tanpa menggunakan tangan sebagai penopang (tampak memegang kursi atau meja/ benda lain)</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_c")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td colspan='2' border='0'>Hasil : "+rs2.getString("hasil")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Diberitahukan ke dokter ? "+rs2.getString("lapor")+(rs2.getString("ket_lapor").equals("")?"":" Jam dilaporkan : "+rs2.getString("ket_lapor"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "IX. SKRINING GIZI (Strong kid)"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle'>No.</td>"+
                                                      "<td width='75%' bgcolor='#FFFAF8' align='center' valign='middle'>Parameter</td>"+
                                                      "<td width='20%' bgcolor='#FFFAF8' colspan='2' align='center' valign='middle'>Nilai</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td>1</td>"+
                                                      "<td>Apakah pasien tampak kurus</td>"+
                                                      "<td align='center'>"+rs2.getString("sg1")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilai1")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td>2</td>"+
                                                      "<td>Apakah terdapat penurunan berat badan selama satu bulan terakhir? (berdasarkan penilaian objektif data berat badan bila ada atau untuk bayi < 1 tahun ; berat badan tidak naik selama 3 bulan terakhir</td>"+
                                                      "<td align='center'>"+rs2.getString("sg2")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilai2")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td>3</td>"+
                                                      "<td>Apakah terdapat salah satu dari kondisi tersebut? Diare > 5 kali/hari dan/muntah > 3 kali/hari salam seminggu terakhir; Asupan makanan berkurang selama 1 minggu terakhir</td>"+
                                                      "<td align='center'>"+rs2.getString("sg3")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilai3")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td>4</td>"+
                                                      "<td>Apakah terdapat penyakit atau keadaan yang menyebabkan pasien beresiko mengalami malnutrisi?</td>"+
                                                      "<td align='center'>"+rs2.getString("sg4")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilai4")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td align='center' colspan='3'>Total Skor</td>"+
                                                      "<td align='center'>"+rs2.getString("total_hasil")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "X. PENILAIAN TINGKAT NYERI (Skala FLACCS)"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='30%' bgcolor='#FFFAF8' align='center' valign='middle'>Pengkajian</td>"+
                                                      "<td width='60%' bgcolor='#FFFAF8' align='center' valign='middle'>Parameter</td>"+
                                                      "<td width='10%' bgcolor='#FFFAF8' align='center' valign='middle'>Nilai</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td align='center'>Wajah</td>"+
                                                      "<td align='center'>"+rs2.getString("wajah")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilaiwajah")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td align='center'>Kaki</td>"+
                                                      "<td align='center'>"+rs2.getString("kaki")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilaikaki")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td align='center'>Aktifitas</td>"+
                                                      "<td align='center'>"+rs2.getString("aktifitas")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilaiaktifitas")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td align='center'>Menangis</td>"+
                                                      "<td align='center'>"+rs2.getString("menangis")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilaimenangis")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td align='center'>Bersuara</td>"+
                                                      "<td align='center'>"+rs2.getString("bersuara")+"</td>"+
                                                      "<td align='center'>"+rs2.getString("nilaibersuara")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td align='left' colspan='2'>Keterangan : 0 : Nyaman 1-3 : Kurang nyaman 4-6 : Nyeri sedang 7-10 : Nyeri berat</td>"+
                                                      "<td align='center'>"+rs2.getString("hasilnyeri")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Tingkat Nyeri : "+rs2.getString("nyeri")+"</td>"+
                                                      "<td width='50%' border='0'>Lokasi : "+rs2.getString("lokasi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Durasi : "+rs2.getString("durasi")+"</td>"+
                                                      "<td width='50%' border='0'>Frekuensi : "+rs2.getString("frekuensi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Nyeri hilang bila : "+rs2.getString("nyeri_hilang")+(rs2.getString("ket_nyeri").equals("")?"":", "+rs2.getString("ket_nyeri"))+"</td>"+
                                                      "<td width='50%' border='0'>Diberitahukan pada dokter ? "+rs2.getString("pada_dokter")+(rs2.getString("ket_dokter").equals("")?"":", Jam : "+rs2.getString("ket_dokter"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>MASALAH KEPERAWATAN :</td>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>RENCANA KEPERAWATAN :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                       "<td>");
                                    try {
                                        rs3=koneksi.prepareStatement(
                                            "select master_masalah_keperawatan_anak.kode_masalah,master_masalah_keperawatan_anak.nama_masalah from master_masalah_keperawatan_anak "+
                                            "inner join penilaian_awal_keperawatan_ralan_bayi_masalah on penilaian_awal_keperawatan_ralan_bayi_masalah.kode_masalah=master_masalah_keperawatan_anak.kode_masalah "+
                                            "where penilaian_awal_keperawatan_ralan_bayi_masalah.no_rawat='"+rs.getString("no_rawat")+"' order by kode_masalah").executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(rs3.getString("nama_masalah")+"<br>");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }
                                    htmlContent.append("</td>"+
                                                       "<td>"+rs2.getString("rencana")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>");
                                }
                                htmlContent.append(
                                      "</table>"+
                                    "</td>"+
                                  "</tr>");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan asuhan awal keperawatan rawat jalan kandungan
                    if(chkAsuhanKeperawatanRalanKandungan.isSelected()==true){
                        try {
                            rs2=koneksi.prepareStatement(
                                    "select penilaian_awal_keperawatan_kebidanan.tanggal,penilaian_awal_keperawatan_kebidanan.informasi,penilaian_awal_keperawatan_kebidanan.td,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.bb,"+
                                    "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.nadi,penilaian_awal_keperawatan_kebidanan.rr,penilaian_awal_keperawatan_kebidanan.suhu,penilaian_awal_keperawatan_kebidanan.gcs,penilaian_awal_keperawatan_kebidanan.bb,"+
                                    "penilaian_awal_keperawatan_kebidanan.tb,penilaian_awal_keperawatan_kebidanan.bmi,penilaian_awal_keperawatan_kebidanan.lila,penilaian_awal_keperawatan_kebidanan.tfu,penilaian_awal_keperawatan_kebidanan.tbj,penilaian_awal_keperawatan_kebidanan.letak,"+
                                    "penilaian_awal_keperawatan_kebidanan.presentasi,penilaian_awal_keperawatan_kebidanan.penurunan,penilaian_awal_keperawatan_kebidanan.his,penilaian_awal_keperawatan_kebidanan.kekuatan,penilaian_awal_keperawatan_kebidanan.lamanya,penilaian_awal_keperawatan_kebidanan.bjj,"+
                                    "penilaian_awal_keperawatan_kebidanan.ket_bjj,penilaian_awal_keperawatan_kebidanan.portio,penilaian_awal_keperawatan_kebidanan.serviks,penilaian_awal_keperawatan_kebidanan.ketuban,penilaian_awal_keperawatan_kebidanan.hodge,penilaian_awal_keperawatan_kebidanan.inspekulo,"+
                                    "penilaian_awal_keperawatan_kebidanan.ket_inspekulo,penilaian_awal_keperawatan_kebidanan.ctg,penilaian_awal_keperawatan_kebidanan.ket_ctg,penilaian_awal_keperawatan_kebidanan.usg,penilaian_awal_keperawatan_kebidanan.ket_usg,penilaian_awal_keperawatan_kebidanan.lab,"+
                                    "penilaian_awal_keperawatan_kebidanan.ket_lab,penilaian_awal_keperawatan_kebidanan.lakmus,penilaian_awal_keperawatan_kebidanan.ket_lakmus,penilaian_awal_keperawatan_kebidanan.panggul,penilaian_awal_keperawatan_kebidanan.keluhan_utama,penilaian_awal_keperawatan_kebidanan.umur,"+
                                    "penilaian_awal_keperawatan_kebidanan.lama,penilaian_awal_keperawatan_kebidanan.banyaknya,penilaian_awal_keperawatan_kebidanan.haid,penilaian_awal_keperawatan_kebidanan.siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus,penilaian_awal_keperawatan_kebidanan.ket_siklus1,"+
                                    "penilaian_awal_keperawatan_kebidanan.status,penilaian_awal_keperawatan_kebidanan.kali,penilaian_awal_keperawatan_kebidanan.usia1,penilaian_awal_keperawatan_kebidanan.ket1,penilaian_awal_keperawatan_kebidanan.usia2,penilaian_awal_keperawatan_kebidanan.ket2,"+
                                    "penilaian_awal_keperawatan_kebidanan.usia3,penilaian_awal_keperawatan_kebidanan.ket3,penilaian_awal_keperawatan_kebidanan.hpht,penilaian_awal_keperawatan_kebidanan.usia_kehamilan,penilaian_awal_keperawatan_kebidanan.tp,penilaian_awal_keperawatan_kebidanan.imunisasi,"+
                                    "penilaian_awal_keperawatan_kebidanan.ket_imunisasi,penilaian_awal_keperawatan_kebidanan.g,penilaian_awal_keperawatan_kebidanan.p,penilaian_awal_keperawatan_kebidanan.a,penilaian_awal_keperawatan_kebidanan.hidup,penilaian_awal_keperawatan_kebidanan.ginekologi,"+
                                    "penilaian_awal_keperawatan_kebidanan.kebiasaan,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan,penilaian_awal_keperawatan_kebidanan.kebiasaan1,penilaian_awal_keperawatan_kebidanan.ket_kebiasaan1,penilaian_awal_keperawatan_kebidanan.kebiasaan2,"+
                                    "penilaian_awal_keperawatan_kebidanan.ket_kebiasaan2,penilaian_awal_keperawatan_kebidanan.kebiasaan3,penilaian_awal_keperawatan_kebidanan.kb,penilaian_awal_keperawatan_kebidanan.ket_kb,penilaian_awal_keperawatan_kebidanan.komplikasi,"+
                                    "penilaian_awal_keperawatan_kebidanan.ket_komplikasi,penilaian_awal_keperawatan_kebidanan.berhenti,penilaian_awal_keperawatan_kebidanan.alasan,penilaian_awal_keperawatan_kebidanan.alat_bantu,penilaian_awal_keperawatan_kebidanan.ket_bantu,"+
                                    "penilaian_awal_keperawatan_kebidanan.prothesa,penilaian_awal_keperawatan_kebidanan.ket_pro,penilaian_awal_keperawatan_kebidanan.adl,penilaian_awal_keperawatan_kebidanan.status_psiko,penilaian_awal_keperawatan_kebidanan.ket_psiko,"+
                                    "penilaian_awal_keperawatan_kebidanan.hub_keluarga,penilaian_awal_keperawatan_kebidanan.tinggal_dengan,penilaian_awal_keperawatan_kebidanan.ket_tinggal,penilaian_awal_keperawatan_kebidanan.ekonomi,penilaian_awal_keperawatan_kebidanan.budaya,"+
                                    "penilaian_awal_keperawatan_kebidanan.ket_budaya,penilaian_awal_keperawatan_kebidanan.edukasi,penilaian_awal_keperawatan_kebidanan.ket_edukasi,penilaian_awal_keperawatan_kebidanan.berjalan_a,penilaian_awal_keperawatan_kebidanan.berjalan_b,"+
                                    "penilaian_awal_keperawatan_kebidanan.berjalan_c,penilaian_awal_keperawatan_kebidanan.hasil,penilaian_awal_keperawatan_kebidanan.lapor,penilaian_awal_keperawatan_kebidanan.ket_lapor,penilaian_awal_keperawatan_kebidanan.sg1,"+
                                    "penilaian_awal_keperawatan_kebidanan.nilai1,penilaian_awal_keperawatan_kebidanan.sg2,penilaian_awal_keperawatan_kebidanan.nilai2,penilaian_awal_keperawatan_kebidanan.total_hasil,penilaian_awal_keperawatan_kebidanan.nyeri,"+
                                    "penilaian_awal_keperawatan_kebidanan.provokes,penilaian_awal_keperawatan_kebidanan.ket_provokes,penilaian_awal_keperawatan_kebidanan.quality,penilaian_awal_keperawatan_kebidanan.ket_quality,penilaian_awal_keperawatan_kebidanan.lokasi,"+
                                    "penilaian_awal_keperawatan_kebidanan.menyebar,penilaian_awal_keperawatan_kebidanan.skala_nyeri,penilaian_awal_keperawatan_kebidanan.durasi,penilaian_awal_keperawatan_kebidanan.nyeri_hilang,penilaian_awal_keperawatan_kebidanan.ket_nyeri,"+
                                    "penilaian_awal_keperawatan_kebidanan.pada_dokter,penilaian_awal_keperawatan_kebidanan.ket_dokter,penilaian_awal_keperawatan_kebidanan.masalah,penilaian_awal_keperawatan_kebidanan.tindakan,penilaian_awal_keperawatan_kebidanan.nip,petugas.nama "+
                                    "from penilaian_awal_keperawatan_kebidanan inner join petugas on penilaian_awal_keperawatan_kebidanan.nip=petugas.nip "+
                                    "where penilaian_awal_keperawatan_kebidanan.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Penilaian Awal Keperawatan Rawat Jalan Kandungan</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                                );
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "YANG MELAKUKAN PENGKAJIAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>Tanggal : "+rs2.getString("tanggal")+"</td>"+
                                                      "<td width='33%' border='0'>Petugas : "+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                                      "<td width='33%' border='0'>Informasi didapat dari : "+rs2.getString("informasi")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "I. KEADAAN UMUM"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='20%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                                      "<td width='20%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                                      "<td width='20%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                                      "<td width='20%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                                      "<td width='20%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+" °C</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='20%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                                      "<td width='20%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                                      "<td width='20%' border='0'>LILA : "+rs2.getString("lila")+" Cm</td>"+
                                                      "<td width='20%' border='0'>BMI : "+rs2.getString("bmi")+" Kg/m²</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "II. PEMERIKSAAN KEBIDANAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='20%' border='0'>TFU : "+rs2.getString("tfu")+" cm</td>"+
                                                      "<td width='20%' border='0'>TBJ : "+rs2.getString("tbj")+"</td>"+
                                                      "<td width='20%' border='0'>Letak : "+rs2.getString("letak")+"</td>"+
                                                      "<td width='20%' border='0'>Presentasi : "+rs2.getString("presentasi")+"</td>"+
                                                      "<td width='20%' border='0'>Penurunan : "+rs2.getString("penurunan")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='25%' border='0'>Kontraksi/HIS : "+rs2.getString("his")+" x/10’</td>"+
                                                      "<td width='25%' border='0'>Kekuatan : "+rs2.getString("kekuatan")+"</td>"+
                                                      "<td width='25%' border='0'>Lamanya : "+rs2.getString("lamanya")+"</td>"+
                                                      "<td width='25%' border='0'>Gerak janin x/30 menit, BJJ : "+rs2.getString("bjj")+" "+rs2.getString("ket_bjj")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='25%' border='0'>Portio : "+rs2.getString("portio")+"</td>"+
                                                      "<td width='25%' border='0'>Pembukaan Serviks : "+rs2.getString("serviks")+" cm</td>"+
                                                      "<td width='25%' border='0'>Ketuban : "+rs2.getString("ketuban")+" kep/bok</td>"+
                                                      "<td width='25%' border='0'>Hodge : "+rs2.getString("hodge")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td border='0' colspan='4'>Pemeriksaan Penunjang :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='10%' border='0' align='right'>Inspekulo</td>"+
                                                      "<td width='40%' border='0'>: "+rs2.getString("inspekulo")+", Hasil : "+rs2.getString("ket_inspekulo")+"</td>"+
                                                      "<td colspan='2' border='0' >Laboratorium : "+rs2.getString("lab")+", Hasil : "+rs2.getString("ket_lab")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='10%' border='0' align='right'>CTG</td>"+
                                                      "<td width='40%' border='0'>: "+rs2.getString("ctg")+", Hasil : "+rs2.getString("ket_ctg")+"</td>"+
                                                      "<td colspan='2' border='0' >Lakmus : "+rs2.getString("lakmus")+", Hasil : "+rs2.getString("ket_lakmus")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='10%' border='0' align='right'>USG</td>"+
                                                      "<td width='40%' border='0'>: "+rs2.getString("usg")+", Hasil : "+rs2.getString("ket_usg")+"</td>"+
                                                      "<td colspan='2' border='0' >Pemeriksaan Panggul : "+rs2.getString("panggul")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "III. RIWAYAT KESEHATAN"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='20%' colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama")+"</td>"+
                                                  "</tr>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='20%'>Riwayat Menstruasi</td>"+
                                                      "<td width='80%'>Umur Menarche : "+rs2.getString("umur")+" tahun, lamanya : "+rs2.getString("lama")+" hari, banyaknya : "+rs2.getString("banyaknya")+" pembalut. Haid Terakhir : "+rs2.getString("haid")+", Siklus : "+rs2.getString("siklus")+" hari, ( "+rs2.getString("ket_siklus")+" ), "+rs2.getString("ket_siklus1")+"</td>"+
                                                  "</tr>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='20%'>Riwayat Perkawinan</td>"+
                                                      "<td width='80%'>Status Menikah : "+rs2.getString("status")+" "+rs2.getString("kali")+" kali. Usia Perkawinan 1 : "+rs2.getString("usia1")+" tahun, Status : "+rs2.getString("ket1")+".<br>Usia Perkawinan 2 : "+rs2.getString("usia2")+" tahun, Status : "+rs2.getString("ket2")+". Usia Perkawinan 3 : "+rs2.getString("usia3")+" tahun, Status : "+rs2.getString("ket3")+"</td>"+
                                                  "</tr>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='20%'>Riwayat Kehamilan Tetap</td>"+
                                                      "<td width='80%'>HPHT : "+rs2.getString("hpht")+", Usia Kehamilan : "+rs2.getString("usia_kehamilan")+" bln/mgg, TP : "+rs2.getString("tp")+". Riwayat Imunisasi : "+rs2.getString("imunisasi")+(rs2.getString("ket_imunisasi").equals("")?"":", "+rs2.getString("ket_imunisasi")+" kali")+". G : "+rs2.getString("g")+", P : "+rs2.getString("p")+", A : "+rs2.getString("a")+", Hidup : "+rs2.getString("hidup")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='3%' bgcolor='#FFFAF8' align='center' valign='middle' rowspan='2'>No</td>"+
                                                      "<td width='8%' bgcolor='#FFFAF8' align='center' valign='middle' rowspan='2'>Tgl/Thn Persalinan</td>"+
                                                      "<td width='23%' bgcolor='#FFFAF8' align='center' valign='middle' rowspan='2'>Tempat Persalinan</td>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle' rowspan='2'>Usia Hamil</td>"+
                                                      "<td width='8%' bgcolor='#FFFAF8' align='center' valign='middle' rowspan='2'>Jenis persalinan</td>"+
                                                      "<td width='16%' bgcolor='#FFFAF8' align='center' valign='middle' rowspan='2'>Penolong</td>"+
                                                      "<td width='16%' bgcolor='#FFFAF8' align='center' valign='middle' rowspan='2'>Penyulit</td>"+
                                                      "<td bgcolor='#FFFAF8' align='center' valign='middle' colspan='3'>Anak</td>"+
                                                  "</tr>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='3%' bgcolor='#FFFAF8' align='center' valign='middle'>JK</td>"+
                                                      "<td width='5%' bgcolor='#FFFAF8' align='center' valign='middle'>BB/PB</td>"+
                                                      "<td width='13%' bgcolor='#FFFAF8' align='center' valign='middle'>Keadaan</td>"+
                                                  "</tr>");
                                    try {
                                        w=1;
                                        rs3=koneksi.prepareStatement(
                                            "select * from riwayat_persalinan_pasien where no_rkm_medis='"+NoRM.getText()+"' order by tgl_thn").executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(
                                                   "<tr>"+
                                                      "<td align='center'>"+w+"</td>"+
                                                      "<td align='center'>"+rs3.getString("tgl_thn")+"</td>"+
                                                      "<td>"+rs3.getString("tempat_persalinan")+"</td>"+
                                                      "<td align='center'>"+rs3.getString("usia_hamil")+"</td>"+
                                                      "<td align='center'>"+rs3.getString("jenis_persalinan")+"</td>"+
                                                      "<td>"+rs3.getString("penolong")+"</td>"+
                                                      "<td>"+rs3.getString("penyulit")+"</td>"+
                                                      "<td align='center'>"+rs3.getString("jk")+"</td>"+
                                                      "<td align='center'>"+rs3.getString("bbpb")+"</td>"+
                                                      "<td>"+rs3.getString("keadaan")+"</td>"+
                                                   "</tr>");
                                            w++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }
                                    htmlContent.append(
                                               "</table>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='20%'>Riwayat Ginekologi</td>"+
                                                      "<td width='80%'>"+rs2.getString("ginekologi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='20%'>Riwayat Kebiasaan</td>"+
                                                      "<td width='80%'>Obat/Vitamin : "+rs2.getString("kebiasaan")+(rs2.getString("ket_kebiasaan").equals("")?"":", "+rs2.getString("ket_kebiasaan"))+". Merokok : "+rs2.getString("kebiasaan1")+(rs2.getString("ket_kebiasaan1").equals("")?"":", "+rs2.getString("ket_kebiasaan1")+" batang/hari")+". Alkohol : "+rs2.getString("kebiasaan2")+(rs2.getString("ket_kebiasaan2").equals("")?"":" "+rs2.getString("ket_kebiasaan2")+" gelas/hari")+". Obat Tidur/Narkoba : "+rs2.getString("kebiasaan3")+"</td>"+
                                                  "</tr>"+
                                                  "<tr class='isi'>"+
                                                      "<td width='20%'>Riwayat K.B.</td>"+
                                                      "<td width='80%'>"+rs2.getString("kb")+", Lamanya : "+rs2.getString("ket_kb")+". Komplikasi KB : "+rs2.getString("komplikasi")+(rs2.getString("ket_komplikasi").equals("")?"":", "+rs2.getString("ket_komplikasi"))+". Berhenti KB : "+rs2.getString("berhenti")+", Alasan : "+rs2.getString("alasan")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "IV. FUNGSIONAL"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='33%' border='0'>Alat Bantu : "+rs2.getString("alat_bantu")+(rs2.getString("ket_bantu").equals("")?"":", "+rs2.getString("ket_bantu"))+"</td>"+
                                                      "<td width='33%' border='0'>Prothesa : "+rs2.getString("prothesa")+(rs2.getString("ket_pro").equals("")?"":", "+rs2.getString("ket_pro"))+"</td>"+
                                                      "<td width='33%' border='0'>Aktivitas Sehari-hari ( ADL ) : "+rs2.getString("adl")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "V. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN BUDAYA"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Status Psikologis</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("status_psiko")+(rs2.getString("ket_psiko").equals("")?"":", "+rs2.getString("ket_psiko"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td border='0' colspan='2'>Status Sosial dan ekonomi :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;a. Hubungan pasien dengan anggota keluarga</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("hub_keluarga")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;b. Tinggal dengan</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("tinggal_dengan")+(rs2.getString("ket_tinggal").equals("")?"":", "+rs2.getString("ket_tinggal"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;c. Ekonomi</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("ekonomi")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("budaya")+(rs2.getString("ket_budaya").equals("")?"":", "+rs2.getString("ket_budaya"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Edukasi diberikan kepada</td>"+
                                                      "<td width='50%' border='0'>: "+rs2.getString("edukasi")+(rs2.getString("ket_edukasi").equals("")?"":", "+rs2.getString("ket_edukasi"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VI. PENILAIAN RESIKO JATUH"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td colpsan='2' border='0'>a. Cara Berjalan :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;1. Tidak seimbang / sempoyongan / limbung</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_a")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_b")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='75%' border='0'>b. Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja / benda lain sebagai penopang</td>"+
                                                      "<td width='25%' border='0'>: "+rs2.getString("berjalan_c")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td colspan='2' border='0'>Hasil : "+rs2.getString("hasil")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dilaporkan kepada dokter ? "+rs2.getString("lapor")+(rs2.getString("ket_lapor").equals("")?"":" Jam dilaporkan : "+rs2.getString("ket_lapor"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VII. SKRINING GIZI"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>No</td>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70%'>Parameter</td>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='25%' colspan='2'>Nilai</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td valign='top'>1</td>"+
                                                      "<td valign='top'>Apakah ada penurunan berat badanyang tidak diinginkan selama enam bulan terakhir ?</td>"+
                                                      "<td valign='top' align='center' width='20%'>"+rs2.getString("sg1")+"</td>"+
                                                      "<td valign='top' align='right' width='5%'>"+rs2.getString("nilai1")+"&nbsp;&nbsp;</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td valign='top'>2</td>"+
                                                      "<td valign='top'>Apakah nafsu makan berkurang karena tidak nafsu makan ?</td>"+
                                                      "<td valign='top' align='center' width='20%'>"+rs2.getString("sg2")+"</td>"+
                                                      "<td valign='top' align='right' width='5%'>"+rs2.getString("nilai2")+"&nbsp;&nbsp;</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td valign='top' align='right' colspan='2'>Total Skor</td>"+
                                                      "<td valign='top' align='right' colspan='2'>"+rs2.getString("total_hasil")+"&nbsp;&nbsp;</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "VIII. PENILAIAN TINGKAT NYERI"+  
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Tingkat Nyeri : "+rs2.getString("nyeri")+", Waktu / Durasi : "+rs2.getString("durasi")+" Menit</td>"+
                                                      "<td width='50%' border='0'>Penyebab : "+rs2.getString("provokes")+(rs2.getString("ket_provokes").equals("")?"":", "+rs2.getString("ket_provokes"))+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Kualitas : "+rs2.getString("quality")+(rs2.getString("ket_quality").equals("")?"":", "+rs2.getString("ket_quality"))+"</td>"+
                                                      "<td width='50%' border='0'>Severity : Skala Nyeri "+rs2.getString("skala_nyeri")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td colspan='0' border='0'>Wilayah :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;Lokasi : "+rs2.getString("lokasi")+"</td>"+
                                                      "<td width='50%' border='0'>Menyebar : "+rs2.getString("menyebar")+"</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td width='50%' border='0'>Nyeri hilang bila : "+rs2.getString("nyeri_hilang")+(rs2.getString("ket_nyeri").equals("")?"":", "+rs2.getString("ket_nyeri"))+"</td>"+
                                                      "<td width='50%' border='0'>Diberitahukan pada dokter ? "+rs2.getString("pada_dokter")+(rs2.getString("ket_dokter").equals("")?"":", Jam : "+rs2.getString("ket_dokter"))+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top'>"+
                                               "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                  "<tr>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>MASALAH KEBIDANAN :</td>"+
                                                       "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>TINDAKAN :</td>"+
                                                  "</tr>"+
                                                  "<tr>"+
                                                      "<td>"+rs2.getString("masalah")+"</td>"+
                                                      "<td>"+rs2.getString("tindakan")+"</td>"+
                                                  "</tr>"+
                                               "</table>"+
                                            "</td>"+
                                         "</tr>"
                                    );   
                                }
                                htmlContent.append(
                                      "</table>"+
                                    "</td>"+
                                  "</tr>");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan diagnosa penyakit
                    if(chkDiagnosaPenyakit.isSelected()==true){
                        try {
                            rs2=koneksi.prepareStatement(
                                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status "+
                                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                                    "where diagnosa_pasien.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Diagnosa/Penyakit/ICD 10</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                         "<tr align='center'><td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Penyakit</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
                                );
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs2.getString("kd_penyakit")+"</td><td valign='top'>"+rs2.getString("nm_penyakit")+"</td><td valign='top'>"+rs2.getString("status")+"</td></tr>");                                        
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
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan prosedur tindakan
                    if(chkProsedurTindakan.isSelected()==true){
                        try {
                            rs2=koneksi.prepareStatement(
                                    "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                                    "from prosedur_pasien inner join icd9 "+
                                    "on prosedur_pasien.kode=icd9.kode "+
                                    "where prosedur_pasien.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Prosedur/Tindakan/ICD 9</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                         "<tr align='center'><td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Prosedur</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
                                );
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs2.getString("kode")+"</td><td valign='top'>"+rs2.getString("deskripsi_panjang")+"</td><td valign='top'>"+rs2.getString("status")+"</td></tr>");                                        
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
                            if(rs2!=null){
                                rs2.close();
                            }
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
                System.out.println("Notifikasi : "+e);
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
}

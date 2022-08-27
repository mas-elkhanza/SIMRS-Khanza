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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import simrskhanza.DlgCariPasien;

/**
 *
 * @author windiarto
 */
public final class RMRiwayatPerawatan extends javax.swing.JDialog {    
    private validasi Valid=new validasi();    
    private final sekuel Sequel=new sekuel();
    private DefaultTableModel tabModeRegistrasi;
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2,rs3,rs4;
    private Connection koneksi=koneksiDB.condb();
    private int i=0,urut=0,w=0,s=0,urutdpjp=0;
    private double biayaperawatan=0;
    private String kddpjp="",dpjp="",dokterrujukan="",polirujukan="",keputusan="",ke1="",ke2="",ke3="",ke4="",ke5="",ke6="",file="";
    private StringBuilder htmlContent;
    private HttpClient http = new HttpClient();
    private GetMethod get;
    private DlgCariPasien pasien=new DlgCariPasien(null,true);

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public RMRiwayatPerawatan(java.awt.Frame parent, boolean modal) {
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
        
        NoRM.setDocument(new batasInput((byte)20).getKata(NoRM));
        NoRawat.setDocument(new batasInput((byte)20).getKata(NoRawat));
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){                   
                    NoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),0).toString());
                    NmPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                    Jk.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),3).toString());
                    TempatLahir.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),4).toString());
                    TanggalLahir.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),5).toString());
                    IbuKandung.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),6).toString());
                    Alamat.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),7).toString());
                    GD.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),8).toString());
                    StatusNikah.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),10).toString());
                    Agama.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),11).toString());
                    Pendidikan.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),15).toString());
                    Bahasa.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),26).toString());
                    CacatFisik.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),32).toString());
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTMLRiwayatPerawatan.setEditorKit(kit);
        LoadHTMLSOAPI.setEditorKit(kit);
        LoadHTMLPembelian.setEditorKit(kit);
        LoadHTMLPiutang.setEditorKit(kit);
        LoadHTMLRetensi.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}.isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;border: white;}");
        Document doc = kit.createDefaultDocument();
        LoadHTMLRiwayatPerawatan.setDocument(doc);
        LoadHTMLRiwayatPerawatan.setEditable(false);
        LoadHTMLRiwayatPerawatan.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTMLSOAPI.setDocument(doc);
        LoadHTMLSOAPI.setEditable(false);
        LoadHTMLSOAPI.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                Desktop desktop = Desktop.getDesktop();
                try {
                   desktop.browse(e.getURL().toURI());
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
            }
        });
        LoadHTMLPembelian.setDocument(doc);
        LoadHTMLPembelian.setEditable(false);
        LoadHTMLPembelian.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                Desktop desktop = Desktop.getDesktop();
                try {
                   desktop.browse(e.getURL().toURI());
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
            }
        });
        LoadHTMLPiutang.setDocument(doc);
        LoadHTMLPiutang.setEditable(false);
        LoadHTMLPiutang.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                Desktop desktop = Desktop.getDesktop();
                try {
                   desktop.browse(e.getURL().toURI());
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
            }
        });
        LoadHTMLRetensi.setDocument(doc);
        LoadHTMLRetensi.setEditable(false);
        LoadHTMLRetensi.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                Desktop desktop = Desktop.getDesktop();
                try {
                   desktop.browse(e.getURL().toURI());
                } catch (Exception ex) {
                  ex.printStackTrace();
                }
            }
        });
        
        ChkAccor.setSelected(false);
        isMenu();
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        Pekerjaan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        R3 = new widget.RadioButton();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        R4 = new widget.RadioButton();
        NoRawat = new widget.TextBox();
        BtnCari1 = new widget.Button();
        label19 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll1 = new widget.ScrollPane();
        tbRegistrasi = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        LoadHTMLSOAPI = new widget.editorpane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        LoadHTMLRiwayatPerawatan = new widget.editorpane();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormMenu = new widget.PanelBiasa();
        chkSemua = new widget.CekBox();
        chkDiagnosaPenyakit = new widget.CekBox();
        chkProsedurTindakan = new widget.CekBox();
        chkPemeriksaanRalan = new widget.CekBox();
        chkPemeriksaanObstetriRalan = new widget.CekBox();
        chkPemeriksaanGenekologiRalan = new widget.CekBox();
        chkPemeriksaanRanap = new widget.CekBox();
        chkPemeriksaanObstetriRanap = new widget.CekBox();
        chkPemeriksaanGenekologiRanap = new widget.CekBox();
        chkCatatanDokter = new widget.CekBox();
        chkCatatanObservasiIGD = new widget.CekBox();
        chkCatatanObservasiRanap = new widget.CekBox();
        chkCatatanObservasiRanapKebidanan = new widget.CekBox();
        chkCatatanObservasiRanapPostPartum = new widget.CekBox();
        chkCatatanKeperawatanRanap = new widget.CekBox();
        chkTriase = new widget.CekBox();
        chkAsuhanKeperawatanIGD = new widget.CekBox();
        chkAsuhanKeperawatanRalan = new widget.CekBox();
        chkAsuhanKeperawatanRalanGigi = new widget.CekBox();
        chkAsuhanKeperawatanRalanBayi = new widget.CekBox();
        chkAsuhanKeperawatanRalanKandungan = new widget.CekBox();
        chkAsuhanFisioterapi = new widget.CekBox();
        chkAsuhanPsikolog = new widget.CekBox();
        chkAsuhanKeperawatanRanap = new widget.CekBox();
        chkAsuhanKeperawatanRanapKandungan = new widget.CekBox();
        chkAsuhanMedisIGD = new widget.CekBox();
        chkAsuhanMedisRalan = new widget.CekBox();
        chkAsuhanMedisRalanKandungan = new widget.CekBox();
        chkAsuhanMedisRalanBayi = new widget.CekBox();
        chkAsuhanMedisRalanTHT = new widget.CekBox();
        chkAsuhanMedisRalanPsikiatri = new widget.CekBox();
        chkAsuhanMedisRalanPenyakitDalam = new widget.CekBox();
        chkAsuhanMedisRalanMata = new widget.CekBox();
        chkAsuhanMedisRalanNeurologi = new widget.CekBox();
        chkAsuhanMedisRalanOrthopedi = new widget.CekBox();
        chkAsuhanMedisRalanBedah = new widget.CekBox();
        chkAsuhanMedisRanap = new widget.CekBox();
        chkAsuhanMedisRanapKandungan = new widget.CekBox();
        chkUjiFungsiKFR = new widget.CekBox();
        chkHemodialisa = new widget.CekBox();
        chkSkriningGiziLanjut = new widget.CekBox();
        chkAsuhanGizi = new widget.CekBox();
        chkMonitoringGizi = new widget.CekBox();
        chkBerkasDigital = new widget.CekBox();
        chkResume = new widget.CekBox();
        chkTindakanRalanDokter = new widget.CekBox();
        chkTindakanRalanParamedis = new widget.CekBox();
        chkTindakanRalanDokterParamedis = new widget.CekBox();
        chkTindakanRanapDokter = new widget.CekBox();
        chkTindakanRanapParamedis = new widget.CekBox();
        chkTindakanRanapDokterParamedis = new widget.CekBox();
        chkPenggunaanKamar = new widget.CekBox();
        chkOperasiVK = new widget.CekBox();
        chkPemeriksaanRadiologi = new widget.CekBox();
        chkPemeriksaanLaborat = new widget.CekBox();
        chkPemberianObat = new widget.CekBox();
        chkPenggunaanObatOperasi = new widget.CekBox();
        chkResepPulang = new widget.CekBox();
        chkTambahanBiaya = new widget.CekBox();
        chkPotonganBiaya = new widget.CekBox();
        Scroll4 = new widget.ScrollPane();
        LoadHTMLPembelian = new widget.editorpane();
        Scroll5 = new widget.ScrollPane();
        LoadHTMLPiutang = new widget.editorpane();
        Scroll3 = new widget.ScrollPane();
        LoadHTMLRetensi = new widget.editorpane();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        NoRM = new widget.TextBox();
        NmPasien = new widget.TextBox();
        BtnPasien = new widget.Button();
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

        Pekerjaan.setEditable(false);
        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.setPreferredSize(new java.awt.Dimension(100, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Riwayat/Rincian Tindakan/Terapi Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        R1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("5 Riwayat Terakhir");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass5.add(R1);

        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setText("Semua Riwayat");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(104, 23));
        panelGlass5.add(R2);

        R3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R3);
        R3.setText("Tanggal :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(75, 23));
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
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
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

        R4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R4);
        R4.setText("Nomor :");
        R4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R4.setName("R4"); // NOI18N
        R4.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass5.add(R4);

        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.setPreferredSize(new java.awt.Dimension(135, 23));
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        panelGlass5.add(NoRawat);

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
        label19.setPreferredSize(new java.awt.Dimension(15, 23));
        panelGlass5.add(label19);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        panelGlass5.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRegistrasi.setName("tbRegistrasi"); // NOI18N
        Scroll1.setViewportView(tbRegistrasi);

        TabRawat.addTab("Riwayat Kunjungan", Scroll1);

        Scroll2.setBorder(null);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        LoadHTMLSOAPI.setBorder(null);
        LoadHTMLSOAPI.setName("LoadHTMLSOAPI"); // NOI18N
        Scroll2.setViewportView(LoadHTMLSOAPI);

        TabRawat.addTab("Riwayat S.O.A.P.I.E", Scroll2);

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTMLRiwayatPerawatan.setBorder(null);
        LoadHTMLRiwayatPerawatan.setName("LoadHTMLRiwayatPerawatan"); // NOI18N
        Scroll.setViewportView(LoadHTMLRiwayatPerawatan);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(275, 43));
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
        ScrollMenu.setPreferredSize(new java.awt.Dimension(255, 1197));

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(255, 1387));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1));

        chkSemua.setSelected(true);
        chkSemua.setText("Semua");
        chkSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkSemua.setName("chkSemua"); // NOI18N
        chkSemua.setOpaque(false);
        chkSemua.setPreferredSize(new java.awt.Dimension(245, 22));
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
        chkDiagnosaPenyakit.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkDiagnosaPenyakit);

        chkProsedurTindakan.setSelected(true);
        chkProsedurTindakan.setText("Prosedur/Tidakan (ICD 9)");
        chkProsedurTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkProsedurTindakan.setName("chkProsedurTindakan"); // NOI18N
        chkProsedurTindakan.setOpaque(false);
        chkProsedurTindakan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkProsedurTindakan);

        chkPemeriksaanRalan.setSelected(true);
        chkPemeriksaanRalan.setText("Pemeriksaan Ralan");
        chkPemeriksaanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemeriksaanRalan.setName("chkPemeriksaanRalan"); // NOI18N
        chkPemeriksaanRalan.setOpaque(false);
        chkPemeriksaanRalan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemeriksaanRalan);

        chkPemeriksaanObstetriRalan.setSelected(true);
        chkPemeriksaanObstetriRalan.setText("Pemeriksaan Obstetri Ralan");
        chkPemeriksaanObstetriRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemeriksaanObstetriRalan.setName("chkPemeriksaanObstetriRalan"); // NOI18N
        chkPemeriksaanObstetriRalan.setOpaque(false);
        chkPemeriksaanObstetriRalan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemeriksaanObstetriRalan);

        chkPemeriksaanGenekologiRalan.setSelected(true);
        chkPemeriksaanGenekologiRalan.setText("Pemeriksaan Genekologi Ralan");
        chkPemeriksaanGenekologiRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemeriksaanGenekologiRalan.setName("chkPemeriksaanGenekologiRalan"); // NOI18N
        chkPemeriksaanGenekologiRalan.setOpaque(false);
        chkPemeriksaanGenekologiRalan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemeriksaanGenekologiRalan);

        chkPemeriksaanRanap.setSelected(true);
        chkPemeriksaanRanap.setText("Pemeriksaan Ranap");
        chkPemeriksaanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemeriksaanRanap.setName("chkPemeriksaanRanap"); // NOI18N
        chkPemeriksaanRanap.setOpaque(false);
        chkPemeriksaanRanap.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemeriksaanRanap);

        chkPemeriksaanObstetriRanap.setSelected(true);
        chkPemeriksaanObstetriRanap.setText("Pemeriksaan Obstetri Ranap");
        chkPemeriksaanObstetriRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemeriksaanObstetriRanap.setName("chkPemeriksaanObstetriRanap"); // NOI18N
        chkPemeriksaanObstetriRanap.setOpaque(false);
        chkPemeriksaanObstetriRanap.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemeriksaanObstetriRanap);

        chkPemeriksaanGenekologiRanap.setSelected(true);
        chkPemeriksaanGenekologiRanap.setText("Pemeriksaan Genekologi Ranap");
        chkPemeriksaanGenekologiRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemeriksaanGenekologiRanap.setName("chkPemeriksaanGenekologiRanap"); // NOI18N
        chkPemeriksaanGenekologiRanap.setOpaque(false);
        chkPemeriksaanGenekologiRanap.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemeriksaanGenekologiRanap);

        chkCatatanDokter.setSelected(true);
        chkCatatanDokter.setText("Catatan Dokter");
        chkCatatanDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkCatatanDokter.setName("chkCatatanDokter"); // NOI18N
        chkCatatanDokter.setOpaque(false);
        chkCatatanDokter.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkCatatanDokter);

        chkCatatanObservasiIGD.setSelected(true);
        chkCatatanObservasiIGD.setText("Catatan Observasi IGD");
        chkCatatanObservasiIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkCatatanObservasiIGD.setName("chkCatatanObservasiIGD"); // NOI18N
        chkCatatanObservasiIGD.setOpaque(false);
        chkCatatanObservasiIGD.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkCatatanObservasiIGD);

        chkCatatanObservasiRanap.setSelected(true);
        chkCatatanObservasiRanap.setText("Catatan Observasi Ranap");
        chkCatatanObservasiRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkCatatanObservasiRanap.setName("chkCatatanObservasiRanap"); // NOI18N
        chkCatatanObservasiRanap.setOpaque(false);
        chkCatatanObservasiRanap.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkCatatanObservasiRanap);

        chkCatatanObservasiRanapKebidanan.setSelected(true);
        chkCatatanObservasiRanapKebidanan.setText("Catatan Observasi Ranap Kebidanan");
        chkCatatanObservasiRanapKebidanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkCatatanObservasiRanapKebidanan.setName("chkCatatanObservasiRanapKebidanan"); // NOI18N
        chkCatatanObservasiRanapKebidanan.setOpaque(false);
        chkCatatanObservasiRanapKebidanan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkCatatanObservasiRanapKebidanan);

        chkCatatanObservasiRanapPostPartum.setSelected(true);
        chkCatatanObservasiRanapPostPartum.setText("Catatan Observasi Ranap Post Partum");
        chkCatatanObservasiRanapPostPartum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkCatatanObservasiRanapPostPartum.setName("chkCatatanObservasiRanapPostPartum"); // NOI18N
        chkCatatanObservasiRanapPostPartum.setOpaque(false);
        chkCatatanObservasiRanapPostPartum.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkCatatanObservasiRanapPostPartum);

        chkCatatanKeperawatanRanap.setSelected(true);
        chkCatatanKeperawatanRanap.setText("Catatan Keperawatan Ranap");
        chkCatatanKeperawatanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkCatatanKeperawatanRanap.setName("chkCatatanKeperawatanRanap"); // NOI18N
        chkCatatanKeperawatanRanap.setOpaque(false);
        chkCatatanKeperawatanRanap.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkCatatanKeperawatanRanap);

        chkTriase.setSelected(true);
        chkTriase.setText("Triase IGD/UGD");
        chkTriase.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTriase.setName("chkTriase"); // NOI18N
        chkTriase.setOpaque(false);
        chkTriase.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkTriase);

        chkAsuhanKeperawatanIGD.setSelected(true);
        chkAsuhanKeperawatanIGD.setText("Awal Keperawatan IGD");
        chkAsuhanKeperawatanIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanIGD.setName("chkAsuhanKeperawatanIGD"); // NOI18N
        chkAsuhanKeperawatanIGD.setOpaque(false);
        chkAsuhanKeperawatanIGD.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanKeperawatanIGD);

        chkAsuhanKeperawatanRalan.setSelected(true);
        chkAsuhanKeperawatanRalan.setText("Awal Keperawatan Ralan Umum");
        chkAsuhanKeperawatanRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRalan.setName("chkAsuhanKeperawatanRalan"); // NOI18N
        chkAsuhanKeperawatanRalan.setOpaque(false);
        chkAsuhanKeperawatanRalan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanKeperawatanRalan);

        chkAsuhanKeperawatanRalanGigi.setSelected(true);
        chkAsuhanKeperawatanRalanGigi.setText("Awal Keperawatan Ralan Gigi");
        chkAsuhanKeperawatanRalanGigi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRalanGigi.setName("chkAsuhanKeperawatanRalanGigi"); // NOI18N
        chkAsuhanKeperawatanRalanGigi.setOpaque(false);
        chkAsuhanKeperawatanRalanGigi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanKeperawatanRalanGigi);

        chkAsuhanKeperawatanRalanBayi.setSelected(true);
        chkAsuhanKeperawatanRalanBayi.setText("Awal Keperawatan Ralan Bayi/Anak");
        chkAsuhanKeperawatanRalanBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRalanBayi.setName("chkAsuhanKeperawatanRalanBayi"); // NOI18N
        chkAsuhanKeperawatanRalanBayi.setOpaque(false);
        chkAsuhanKeperawatanRalanBayi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanKeperawatanRalanBayi);

        chkAsuhanKeperawatanRalanKandungan.setSelected(true);
        chkAsuhanKeperawatanRalanKandungan.setText("Awal Keperawatan Ralan Kandungan");
        chkAsuhanKeperawatanRalanKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRalanKandungan.setName("chkAsuhanKeperawatanRalanKandungan"); // NOI18N
        chkAsuhanKeperawatanRalanKandungan.setOpaque(false);
        chkAsuhanKeperawatanRalanKandungan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanKeperawatanRalanKandungan);

        chkAsuhanFisioterapi.setSelected(true);
        chkAsuhanFisioterapi.setText("Awal Fisioterapi");
        chkAsuhanFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanFisioterapi.setName("chkAsuhanFisioterapi"); // NOI18N
        chkAsuhanFisioterapi.setOpaque(false);
        chkAsuhanFisioterapi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanFisioterapi);

        chkAsuhanPsikolog.setSelected(true);
        chkAsuhanPsikolog.setText("Penilaian Psikolog");
        chkAsuhanPsikolog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanPsikolog.setName("chkAsuhanPsikolog"); // NOI18N
        chkAsuhanPsikolog.setOpaque(false);
        chkAsuhanPsikolog.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanPsikolog);

        chkAsuhanKeperawatanRanap.setSelected(true);
        chkAsuhanKeperawatanRanap.setText("Awal Keperawatan Ranap Umum");
        chkAsuhanKeperawatanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRanap.setName("chkAsuhanKeperawatanRanap"); // NOI18N
        chkAsuhanKeperawatanRanap.setOpaque(false);
        chkAsuhanKeperawatanRanap.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanKeperawatanRanap);

        chkAsuhanKeperawatanRanapKandungan.setSelected(true);
        chkAsuhanKeperawatanRanapKandungan.setText("Awal Keperawatan Ranap Kandungan");
        chkAsuhanKeperawatanRanapKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanKeperawatanRanapKandungan.setName("chkAsuhanKeperawatanRanapKandungan"); // NOI18N
        chkAsuhanKeperawatanRanapKandungan.setOpaque(false);
        chkAsuhanKeperawatanRanapKandungan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanKeperawatanRanapKandungan);

        chkAsuhanMedisIGD.setSelected(true);
        chkAsuhanMedisIGD.setText("Awal Medis IGD");
        chkAsuhanMedisIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisIGD.setName("chkAsuhanMedisIGD"); // NOI18N
        chkAsuhanMedisIGD.setOpaque(false);
        chkAsuhanMedisIGD.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisIGD);

        chkAsuhanMedisRalan.setSelected(true);
        chkAsuhanMedisRalan.setText("Awal Medis Ralan Umum");
        chkAsuhanMedisRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalan.setName("chkAsuhanMedisRalan"); // NOI18N
        chkAsuhanMedisRalan.setOpaque(false);
        chkAsuhanMedisRalan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalan);

        chkAsuhanMedisRalanKandungan.setSelected(true);
        chkAsuhanMedisRalanKandungan.setText("Awal Medis Ralan Kandungan");
        chkAsuhanMedisRalanKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanKandungan.setName("chkAsuhanMedisRalanKandungan"); // NOI18N
        chkAsuhanMedisRalanKandungan.setOpaque(false);
        chkAsuhanMedisRalanKandungan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanKandungan);

        chkAsuhanMedisRalanBayi.setSelected(true);
        chkAsuhanMedisRalanBayi.setText("Awal Medis Ralan Bayi/Anak");
        chkAsuhanMedisRalanBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanBayi.setName("chkAsuhanMedisRalanBayi"); // NOI18N
        chkAsuhanMedisRalanBayi.setOpaque(false);
        chkAsuhanMedisRalanBayi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanBayi);

        chkAsuhanMedisRalanTHT.setSelected(true);
        chkAsuhanMedisRalanTHT.setText("Awal Medis Ralan THT");
        chkAsuhanMedisRalanTHT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanTHT.setName("chkAsuhanMedisRalanTHT"); // NOI18N
        chkAsuhanMedisRalanTHT.setOpaque(false);
        chkAsuhanMedisRalanTHT.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanTHT);

        chkAsuhanMedisRalanPsikiatri.setSelected(true);
        chkAsuhanMedisRalanPsikiatri.setText("Awal Medis Ralan Psikiatri");
        chkAsuhanMedisRalanPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanPsikiatri.setName("chkAsuhanMedisRalanPsikiatri"); // NOI18N
        chkAsuhanMedisRalanPsikiatri.setOpaque(false);
        chkAsuhanMedisRalanPsikiatri.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanPsikiatri);

        chkAsuhanMedisRalanPenyakitDalam.setSelected(true);
        chkAsuhanMedisRalanPenyakitDalam.setText("Awal Medis Ralan Penyakit Dalam");
        chkAsuhanMedisRalanPenyakitDalam.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanPenyakitDalam.setName("chkAsuhanMedisRalanPenyakitDalam"); // NOI18N
        chkAsuhanMedisRalanPenyakitDalam.setOpaque(false);
        chkAsuhanMedisRalanPenyakitDalam.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanPenyakitDalam);

        chkAsuhanMedisRalanMata.setSelected(true);
        chkAsuhanMedisRalanMata.setText("Awal Medis Ralan Mata");
        chkAsuhanMedisRalanMata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanMata.setName("chkAsuhanMedisRalanMata"); // NOI18N
        chkAsuhanMedisRalanMata.setOpaque(false);
        chkAsuhanMedisRalanMata.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanMata);

        chkAsuhanMedisRalanNeurologi.setSelected(true);
        chkAsuhanMedisRalanNeurologi.setText("Awal Medis Ralan Neurologi");
        chkAsuhanMedisRalanNeurologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanNeurologi.setName("chkAsuhanMedisRalanNeurologi"); // NOI18N
        chkAsuhanMedisRalanNeurologi.setOpaque(false);
        chkAsuhanMedisRalanNeurologi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanNeurologi);

        chkAsuhanMedisRalanOrthopedi.setSelected(true);
        chkAsuhanMedisRalanOrthopedi.setText("Awal Medis Ralan Orthopedi");
        chkAsuhanMedisRalanOrthopedi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanOrthopedi.setName("chkAsuhanMedisRalanOrthopedi"); // NOI18N
        chkAsuhanMedisRalanOrthopedi.setOpaque(false);
        chkAsuhanMedisRalanOrthopedi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanOrthopedi);

        chkAsuhanMedisRalanBedah.setSelected(true);
        chkAsuhanMedisRalanBedah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRalanBedah.setLabel("Awal Medis Ralan Bedah");
        chkAsuhanMedisRalanBedah.setName("chkAsuhanMedisRalanBedah"); // NOI18N
        chkAsuhanMedisRalanBedah.setOpaque(false);
        chkAsuhanMedisRalanBedah.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRalanBedah);

        chkAsuhanMedisRanap.setSelected(true);
        chkAsuhanMedisRanap.setText("Awal Medis Ranap Umum");
        chkAsuhanMedisRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRanap.setName("chkAsuhanMedisRanap"); // NOI18N
        chkAsuhanMedisRanap.setOpaque(false);
        chkAsuhanMedisRanap.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRanap);

        chkAsuhanMedisRanapKandungan.setSelected(true);
        chkAsuhanMedisRanapKandungan.setText("Awal Medis Ranap Kandungan");
        chkAsuhanMedisRanapKandungan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanMedisRanapKandungan.setName("chkAsuhanMedisRanapKandungan"); // NOI18N
        chkAsuhanMedisRanapKandungan.setOpaque(false);
        chkAsuhanMedisRanapKandungan.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanMedisRanapKandungan);

        chkUjiFungsiKFR.setSelected(true);
        chkUjiFungsiKFR.setText("Uji Fungsi/Prosedur KFR");
        chkUjiFungsiKFR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkUjiFungsiKFR.setName("chkUjiFungsiKFR"); // NOI18N
        chkUjiFungsiKFR.setOpaque(false);
        chkUjiFungsiKFR.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkUjiFungsiKFR);

        chkHemodialisa.setSelected(true);
        chkHemodialisa.setText("Hemodialisa");
        chkHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkHemodialisa.setName("chkHemodialisa"); // NOI18N
        chkHemodialisa.setOpaque(false);
        chkHemodialisa.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkHemodialisa);

        chkSkriningGiziLanjut.setSelected(true);
        chkSkriningGiziLanjut.setText("Skrining Gizi Lanjut");
        chkSkriningGiziLanjut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkSkriningGiziLanjut.setName("chkSkriningGiziLanjut"); // NOI18N
        chkSkriningGiziLanjut.setOpaque(false);
        chkSkriningGiziLanjut.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkSkriningGiziLanjut);

        chkAsuhanGizi.setSelected(true);
        chkAsuhanGizi.setText("Asuhan Gizi");
        chkAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAsuhanGizi.setName("chkAsuhanGizi"); // NOI18N
        chkAsuhanGizi.setOpaque(false);
        chkAsuhanGizi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkAsuhanGizi);

        chkMonitoringGizi.setSelected(true);
        chkMonitoringGizi.setText("Monitoring Gizi");
        chkMonitoringGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkMonitoringGizi.setName("chkMonitoringGizi"); // NOI18N
        chkMonitoringGizi.setOpaque(false);
        chkMonitoringGizi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkMonitoringGizi);

        chkBerkasDigital.setSelected(true);
        chkBerkasDigital.setText("Berkas Digital Perawatan");
        chkBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBerkasDigital.setName("chkBerkasDigital"); // NOI18N
        chkBerkasDigital.setOpaque(false);
        chkBerkasDigital.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkBerkasDigital);

        chkResume.setSelected(true);
        chkResume.setText("Resume");
        chkResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkResume.setName("chkResume"); // NOI18N
        chkResume.setOpaque(false);
        chkResume.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkResume);

        chkTindakanRalanDokter.setSelected(true);
        chkTindakanRalanDokter.setText("Tindakan Ralan Dokter");
        chkTindakanRalanDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTindakanRalanDokter.setName("chkTindakanRalanDokter"); // NOI18N
        chkTindakanRalanDokter.setOpaque(false);
        chkTindakanRalanDokter.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkTindakanRalanDokter);

        chkTindakanRalanParamedis.setSelected(true);
        chkTindakanRalanParamedis.setText("Tindakan Ralan Paramedis");
        chkTindakanRalanParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTindakanRalanParamedis.setName("chkTindakanRalanParamedis"); // NOI18N
        chkTindakanRalanParamedis.setOpaque(false);
        chkTindakanRalanParamedis.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkTindakanRalanParamedis);

        chkTindakanRalanDokterParamedis.setSelected(true);
        chkTindakanRalanDokterParamedis.setText("Tindakan Ralan Dokter & Paramedis");
        chkTindakanRalanDokterParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTindakanRalanDokterParamedis.setName("chkTindakanRalanDokterParamedis"); // NOI18N
        chkTindakanRalanDokterParamedis.setOpaque(false);
        chkTindakanRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkTindakanRalanDokterParamedis);

        chkTindakanRanapDokter.setSelected(true);
        chkTindakanRanapDokter.setText("Tindakan Ranap Dokter");
        chkTindakanRanapDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTindakanRanapDokter.setName("chkTindakanRanapDokter"); // NOI18N
        chkTindakanRanapDokter.setOpaque(false);
        chkTindakanRanapDokter.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkTindakanRanapDokter);

        chkTindakanRanapParamedis.setSelected(true);
        chkTindakanRanapParamedis.setText("Tindakan Ranap Paramedis");
        chkTindakanRanapParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTindakanRanapParamedis.setName("chkTindakanRanapParamedis"); // NOI18N
        chkTindakanRanapParamedis.setOpaque(false);
        chkTindakanRanapParamedis.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkTindakanRanapParamedis);

        chkTindakanRanapDokterParamedis.setSelected(true);
        chkTindakanRanapDokterParamedis.setText("Tindakan Ranap Dokter & Paramedis");
        chkTindakanRanapDokterParamedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTindakanRanapDokterParamedis.setName("chkTindakanRanapDokterParamedis"); // NOI18N
        chkTindakanRanapDokterParamedis.setOpaque(false);
        chkTindakanRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkTindakanRanapDokterParamedis);

        chkPenggunaanKamar.setSelected(true);
        chkPenggunaanKamar.setText("Penggunaan Kamar");
        chkPenggunaanKamar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPenggunaanKamar.setName("chkPenggunaanKamar"); // NOI18N
        chkPenggunaanKamar.setOpaque(false);
        chkPenggunaanKamar.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPenggunaanKamar);

        chkOperasiVK.setSelected(true);
        chkOperasiVK.setText("Operasi/VK");
        chkOperasiVK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkOperasiVK.setName("chkOperasiVK"); // NOI18N
        chkOperasiVK.setOpaque(false);
        chkOperasiVK.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkOperasiVK);

        chkPemeriksaanRadiologi.setSelected(true);
        chkPemeriksaanRadiologi.setText("Pemeriksaan Radiologi");
        chkPemeriksaanRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemeriksaanRadiologi.setName("chkPemeriksaanRadiologi"); // NOI18N
        chkPemeriksaanRadiologi.setOpaque(false);
        chkPemeriksaanRadiologi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemeriksaanRadiologi);

        chkPemeriksaanLaborat.setSelected(true);
        chkPemeriksaanLaborat.setText("Pemeriksaan Laborat");
        chkPemeriksaanLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemeriksaanLaborat.setName("chkPemeriksaanLaborat"); // NOI18N
        chkPemeriksaanLaborat.setOpaque(false);
        chkPemeriksaanLaborat.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemeriksaanLaborat);

        chkPemberianObat.setSelected(true);
        chkPemberianObat.setText("Pemberian Obat/BHP/Alkes");
        chkPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPemberianObat.setName("chkPemberianObat"); // NOI18N
        chkPemberianObat.setOpaque(false);
        chkPemberianObat.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPemberianObat);

        chkPenggunaanObatOperasi.setSelected(true);
        chkPenggunaanObatOperasi.setText("Penggunaan Obat/BHP Operasi");
        chkPenggunaanObatOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPenggunaanObatOperasi.setName("chkPenggunaanObatOperasi"); // NOI18N
        chkPenggunaanObatOperasi.setOpaque(false);
        chkPenggunaanObatOperasi.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPenggunaanObatOperasi);

        chkResepPulang.setSelected(true);
        chkResepPulang.setText("Resep Pulang");
        chkResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkResepPulang.setName("chkResepPulang"); // NOI18N
        chkResepPulang.setOpaque(false);
        chkResepPulang.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkResepPulang);

        chkTambahanBiaya.setSelected(true);
        chkTambahanBiaya.setText("Tambahan Biaya");
        chkTambahanBiaya.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTambahanBiaya.setName("chkTambahanBiaya"); // NOI18N
        chkTambahanBiaya.setOpaque(false);
        chkTambahanBiaya.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkTambahanBiaya);

        chkPotonganBiaya.setSelected(true);
        chkPotonganBiaya.setText("Potongan Biaya");
        chkPotonganBiaya.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPotonganBiaya.setName("chkPotonganBiaya"); // NOI18N
        chkPotonganBiaya.setOpaque(false);
        chkPotonganBiaya.setPreferredSize(new java.awt.Dimension(245, 22));
        FormMenu.add(chkPotonganBiaya);

        ScrollMenu.setViewportView(FormMenu);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        internalFrame2.add(PanelAccor, java.awt.BorderLayout.WEST);

        TabRawat.addTab("Riwayat Perawatan", internalFrame2);

        Scroll4.setBorder(null);
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        LoadHTMLPembelian.setBorder(null);
        LoadHTMLPembelian.setName("LoadHTMLPembelian"); // NOI18N
        Scroll4.setViewportView(LoadHTMLPembelian);

        TabRawat.addTab("Pembelian Obat", Scroll4);

        Scroll5.setBorder(null);
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        LoadHTMLPiutang.setBorder(null);
        LoadHTMLPiutang.setName("LoadHTMLPiutang"); // NOI18N
        Scroll5.setViewportView(LoadHTMLPiutang);

        TabRawat.addTab("Piutang Obat", Scroll5);

        Scroll3.setBorder(null);
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        LoadHTMLRetensi.setBorder(null);
        LoadHTMLRetensi.setName("LoadHTMLRetensi"); // NOI18N
        Scroll3.setViewportView(LoadHTMLRetensi);

        TabRawat.addTab("Retensi Berkas", Scroll3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setSelected(true);
        ChkInput.setText(".: Tampilkan/Sembunyikan Data Pasien");
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

        label17.setText("Pasien :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label17);
        label17.setBounds(5, 10, 55, 23);

        NoRM.setName("NoRM"); // NOI18N
        NoRM.setPreferredSize(new java.awt.Dimension(100, 23));
        NoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRMKeyPressed(evt);
            }
        });
        FormInput.add(NoRM);
        NoRM.setBounds(64, 10, 100, 23);

        NmPasien.setEditable(false);
        NmPasien.setName("NmPasien"); // NOI18N
        NmPasien.setPreferredSize(new java.awt.Dimension(220, 23));
        FormInput.add(NmPasien);
        NmPasien.setBounds(167, 10, 220, 23);

        BtnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPasien.setMnemonic('3');
        BtnPasien.setToolTipText("Alt+3");
        BtnPasien.setName("BtnPasien"); // NOI18N
        BtnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPasienActionPerformed(evt);
            }
        });
        BtnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPasienKeyPressed(evt);
            }
        });
        FormInput.add(BtnPasien);
        BtnPasien.setBounds(390, 10, 28, 23);

        label20.setText("J.K. :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label20);
        label20.setBounds(436, 10, 30, 23);

        Jk.setEditable(false);
        Jk.setName("Jk"); // NOI18N
        Jk.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(Jk);
        Jk.setBounds(470, 10, 40, 23);

        label21.setText("Tempat & Tgl.Lahir :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label21);
        label21.setBounds(523, 10, 110, 23);

        TempatLahir.setEditable(false);
        TempatLahir.setName("TempatLahir"); // NOI18N
        TempatLahir.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(TempatLahir);
        TempatLahir.setBounds(637, 10, 140, 23);

        label22.setText("Alamat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label22);
        label22.setBounds(5, 40, 55, 23);

        Alamat.setEditable(false);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(Alamat);
        Alamat.setBounds(64, 40, 354, 23);

        label23.setText("G.D. :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label23);
        label23.setBounds(436, 40, 30, 23);

        GD.setEditable(false);
        GD.setName("GD"); // NOI18N
        GD.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(GD);
        GD.setBounds(470, 40, 40, 23);

        label24.setText("Nama Ibu Kandung :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label24);
        label24.setBounds(523, 40, 110, 23);

        IbuKandung.setEditable(false);
        IbuKandung.setName("IbuKandung"); // NOI18N
        IbuKandung.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(IbuKandung);
        IbuKandung.setBounds(637, 40, 225, 23);

        TanggalLahir.setEditable(false);
        TanggalLahir.setName("TanggalLahir"); // NOI18N
        TanggalLahir.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(TanggalLahir);
        TanggalLahir.setBounds(779, 10, 83, 23);

        label25.setText("Agama :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label25);
        label25.setBounds(5, 70, 55, 23);

        Agama.setEditable(false);
        Agama.setName("Agama"); // NOI18N
        Agama.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(Agama);
        Agama.setBounds(64, 70, 100, 23);

        StatusNikah.setEditable(false);
        StatusNikah.setName("StatusNikah"); // NOI18N
        StatusNikah.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(StatusNikah);
        StatusNikah.setBounds(245, 70, 100, 23);

        label26.setText("Stts.Nikah :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label26);
        label26.setBounds(176, 70, 65, 23);

        Pendidikan.setEditable(false);
        Pendidikan.setName("Pendidikan"); // NOI18N
        Pendidikan.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(Pendidikan);
        Pendidikan.setBounds(429, 70, 80, 23);

        label27.setText("Pendidikan :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label27);
        label27.setBounds(355, 70, 70, 23);

        label28.setText("Bahasa :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label28);
        label28.setBounds(520, 70, 50, 23);

        Bahasa.setEditable(false);
        Bahasa.setName("Bahasa"); // NOI18N
        Bahasa.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(Bahasa);
        Bahasa.setBounds(574, 70, 100, 23);

        label29.setText("Cacat Fisik :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label29);
        label29.setBounds(683, 70, 70, 23);

        CacatFisik.setEditable(false);
        CacatFisik.setName("CacatFisik"); // NOI18N
        CacatFisik.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(CacatFisik);
        CacatFisik.setBounds(757, 70, 105, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,Tgl1,NoRM);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void NoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRMKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPasien();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isPasien();
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPasienActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isPasien();
            BtnPrint.requestFocus();
        }
}//GEN-LAST:event_NoRMKeyPressed

private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
    if(akses.getpasien()==true){
        pasien.isCek();
        pasien.emptTeks();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }   
}//GEN-LAST:event_BtnPasienActionPerformed

private void BtnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPasienKeyPressed
    //Valid.pindah(evt,Tgl2,TKd);
}//GEN-LAST:event_BtnPasienKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(NoRM.getText().trim().equals("")||NmPasien.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    if(tabModeRegistrasi.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    }else if(tabModeRegistrasi.getRowCount()!=0){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                        Sequel.queryu("truncate table temporary_resume");

                        for(int i=0;i<tabModeRegistrasi.getRowCount();i++){  
                            Sequel.menyimpan("temporary_resume","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                "0",tabModeRegistrasi.getValueAt(i,0).toString(),tabModeRegistrasi.getValueAt(i,1).toString(),tabModeRegistrasi.getValueAt(i,2).toString(),
                                tabModeRegistrasi.getValueAt(i,3).toString(),tabModeRegistrasi.getValueAt(i,4).toString(),tabModeRegistrasi.getValueAt(i,5).toString(),
                                tabModeRegistrasi.getValueAt(i,6).toString(),tabModeRegistrasi.getValueAt(i,7).toString(),tabModeRegistrasi.getValueAt(i,8).toString(),
                                "","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                            });
                        }

                        Map<String, Object> param = new HashMap<>();  
                            param.put("namars",akses.getnamars());
                            param.put("alamatrs",akses.getalamatrs());
                            param.put("kotars",akses.getkabupatenrs());
                            param.put("propinsirs",akses.getpropinsirs());
                            param.put("kontakrs",akses.getkontakrs());
                            param.put("emailrs",akses.getemailrs());   
                            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                        Valid.MyReport2("rptRiwayatRegistrasi.jasper","report","::[ Riwayat Registrasi ]::",param);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                    break;
                case 1:
                    panggilLaporan(LoadHTMLSOAPI.getText()); 
                    break;
                case 2:
                    panggilLaporan(LoadHTMLRiwayatPerawatan.getText()); 
                    break;
                case 3:
                    panggilLaporan(LoadHTMLPembelian.getText()); 
                    break;
                case 4:
                    panggilLaporan(LoadHTMLPiutang.getText()); 
                    break;
                case 5:
                    panggilLaporan(LoadHTMLRetensi.getText()); 
                    break;
                default:
                    break;
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
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
        if(NoRM.getText().trim().equals("")||NmPasien.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    tampilKunjungan();
                    break;
                case 1:
                    tampilSoapi();
                    break;
                case 2:
                    tampilPerawatan();
                    break;
                case 3:
                    tampilPembelian();
                    break;
                case 4:
                    tampilPiutang();
                    break;
                case 5:
                    tampilRetensi();
                    break;
                default:
                    break;
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        BtnCari1ActionPerformed(null);
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
            chkAsuhanKeperawatanRanap.setSelected(true);
            chkAsuhanKeperawatanRanapKandungan.setSelected(true);
            chkAsuhanMedisRalan.setSelected(true);
            chkAsuhanMedisIGD.setSelected(true);
            chkAsuhanMedisRalanKandungan.setSelected(true);
            chkAsuhanMedisRalanBayi.setSelected(true);
            chkAsuhanMedisRalanTHT.setSelected(true);
            chkAsuhanMedisRalanPenyakitDalam.setSelected(true);
            chkAsuhanMedisRalanMata.setSelected(true);
            chkAsuhanMedisRalanNeurologi.setSelected(true);
            chkAsuhanMedisRalanOrthopedi.setSelected(true);
            chkAsuhanMedisRalanBedah.setSelected(true);
            chkAsuhanMedisRanap.setSelected(true);
            chkAsuhanMedisRanapKandungan.setSelected(true);
            chkDiagnosaPenyakit.setSelected(true);
            chkProsedurTindakan.setSelected(true);
            chkCatatanDokter.setSelected(true);
            chkHemodialisa.setSelected(true);
            chkPemeriksaanRalan.setSelected(true);
            chkPemeriksaanObstetriRalan.setSelected(true);
            chkPemeriksaanGenekologiRalan.setSelected(true);
            chkPemeriksaanRanap.setSelected(true);
            chkPemeriksaanObstetriRanap.setSelected(true);
            chkPemeriksaanGenekologiRanap.setSelected(true);
            chkSkriningGiziLanjut.setSelected(true);
            chkAsuhanGizi.setSelected(true);
            chkMonitoringGizi.setSelected(true);
            chkTindakanRalanDokter.setSelected(true);
            chkTindakanRalanParamedis.setSelected(true);
            chkTindakanRalanDokterParamedis.setSelected(true);
            chkTindakanRanapDokter.setSelected(true);
            chkTindakanRanapParamedis.setSelected(true);
            chkTindakanRanapDokterParamedis.setSelected(true);
            chkPenggunaanKamar.setSelected(true);
            chkOperasiVK.setSelected(true);
            chkPemeriksaanRadiologi.setSelected(true);
            chkPemeriksaanLaborat.setSelected(true);
            chkPemberianObat.setSelected(true);
            chkPenggunaanObatOperasi.setSelected(true);
            chkResepPulang.setSelected(true);
            chkTambahanBiaya.setSelected(true);
            chkPotonganBiaya.setSelected(true);
            chkResume.setSelected(true);
            chkBerkasDigital.setSelected(true);
            chkUjiFungsiKFR.setSelected(true);
            chkAsuhanKeperawatanIGD.setSelected(true);
            chkCatatanObservasiIGD.setSelected(true);
            chkCatatanObservasiRanap.setSelected(true);
            chkCatatanObservasiRanapKebidanan.setSelected(true);
            chkCatatanObservasiRanapPostPartum.setSelected(true);
            chkCatatanKeperawatanRanap.setSelected(true);
            chkAsuhanFisioterapi.setSelected(true);
            chkAsuhanPsikolog.setSelected(true);
            chkAsuhanMedisRalanPsikiatri.setSelected(true);
        }else{
            chkTriase.setSelected(false);
            chkAsuhanKeperawatanRalan.setSelected(false);
            chkAsuhanKeperawatanRalanGigi.setSelected(false);
            chkAsuhanKeperawatanRalanBayi.setSelected(false);
            chkAsuhanKeperawatanRalanKandungan.setSelected(false);
            chkAsuhanKeperawatanRanap.setSelected(false);
            chkAsuhanKeperawatanRanapKandungan.setSelected(false);
            chkAsuhanMedisRalan.setSelected(false);
            chkAsuhanMedisIGD.setSelected(false);
            chkAsuhanMedisRalanKandungan.setSelected(false);
            chkAsuhanMedisRalanBayi.setSelected(false);
            chkAsuhanMedisRalanTHT.setSelected(false);
            chkAsuhanMedisRalanPenyakitDalam.setSelected(false);
            chkAsuhanMedisRalanMata.setSelected(false);
            chkAsuhanMedisRalanNeurologi.setSelected(false);
            chkAsuhanMedisRalanOrthopedi.setSelected(false);
            chkAsuhanMedisRalanBedah.setSelected(false);
            chkAsuhanMedisRanap.setSelected(false);
            chkAsuhanMedisRanapKandungan.setSelected(true);
            chkDiagnosaPenyakit.setSelected(false);
            chkProsedurTindakan.setSelected(false);
            chkCatatanDokter.setSelected(false);
            chkHemodialisa.setSelected(false);
            chkPemeriksaanRalan.setSelected(false);
            chkPemeriksaanObstetriRalan.setSelected(false);
            chkPemeriksaanGenekologiRalan.setSelected(false);
            chkPemeriksaanRanap.setSelected(false);
            chkPemeriksaanObstetriRanap.setSelected(false);
            chkPemeriksaanGenekologiRanap.setSelected(false);
            chkSkriningGiziLanjut.setSelected(false);
            chkAsuhanGizi.setSelected(false);
            chkMonitoringGizi.setSelected(false);
            chkTindakanRalanDokter.setSelected(false);
            chkTindakanRalanParamedis.setSelected(false);
            chkTindakanRalanDokterParamedis.setSelected(false);
            chkTindakanRanapDokter.setSelected(false);
            chkTindakanRanapParamedis.setSelected(false);
            chkTindakanRanapDokterParamedis.setSelected(false);
            chkPenggunaanKamar.setSelected(false);
            chkOperasiVK.setSelected(false);
            chkPemeriksaanRadiologi.setSelected(false);
            chkPemeriksaanLaborat.setSelected(false);
            chkPemberianObat.setSelected(false);
            chkPenggunaanObatOperasi.setSelected(false);
            chkResepPulang.setSelected(false);
            chkTambahanBiaya.setSelected(false);
            chkPotonganBiaya.setSelected(false);
            chkResume.setSelected(false);
            chkBerkasDigital.setSelected(false);
            chkUjiFungsiKFR.setSelected(false);
            chkAsuhanKeperawatanIGD.setSelected(false);
            chkCatatanObservasiIGD.setSelected(false);
            chkCatatanObservasiRanap.setSelected(false);
            chkCatatanObservasiRanapKebidanan.setSelected(false);
            chkCatatanObservasiRanapPostPartum.setSelected(false);
            chkCatatanKeperawatanRanap.setSelected(false);
            chkAsuhanFisioterapi.setSelected(false);
            chkAsuhanPsikolog.setSelected(false);
            chkAsuhanMedisRalanPsikiatri.setSelected(false);
        }
    }//GEN-LAST:event_chkSemuaItemStateChanged

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRawatKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMRiwayatPerawatan dialog = new RMRiwayatPerawatan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPasien;
    private widget.Button BtnPrint;
    private widget.TextBox CacatFisik;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox GD;
    private widget.TextBox IbuKandung;
    private widget.TextBox Jk;
    private widget.editorpane LoadHTMLPembelian;
    private widget.editorpane LoadHTMLPiutang;
    private widget.editorpane LoadHTMLRetensi;
    private widget.editorpane LoadHTMLRiwayatPerawatan;
    private widget.editorpane LoadHTMLSOAPI;
    private widget.TextBox NmPasien;
    private widget.TextBox NoRM;
    private widget.TextBox NoRawat;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pekerjaan;
    private widget.TextBox Pendidikan;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton R4;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox StatusNikah;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalLahir;
    private widget.TextBox TempatLahir;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.CekBox chkAsuhanFisioterapi;
    private widget.CekBox chkAsuhanGizi;
    private widget.CekBox chkAsuhanKeperawatanIGD;
    private widget.CekBox chkAsuhanKeperawatanRalan;
    private widget.CekBox chkAsuhanKeperawatanRalanBayi;
    private widget.CekBox chkAsuhanKeperawatanRalanGigi;
    private widget.CekBox chkAsuhanKeperawatanRalanKandungan;
    private widget.CekBox chkAsuhanKeperawatanRanap;
    private widget.CekBox chkAsuhanKeperawatanRanapKandungan;
    private widget.CekBox chkAsuhanMedisIGD;
    private widget.CekBox chkAsuhanMedisRalan;
    private widget.CekBox chkAsuhanMedisRalanBayi;
    private widget.CekBox chkAsuhanMedisRalanBedah;
    private widget.CekBox chkAsuhanMedisRalanKandungan;
    private widget.CekBox chkAsuhanMedisRalanMata;
    private widget.CekBox chkAsuhanMedisRalanNeurologi;
    private widget.CekBox chkAsuhanMedisRalanOrthopedi;
    private widget.CekBox chkAsuhanMedisRalanPenyakitDalam;
    private widget.CekBox chkAsuhanMedisRalanPsikiatri;
    private widget.CekBox chkAsuhanMedisRalanTHT;
    private widget.CekBox chkAsuhanMedisRanap;
    private widget.CekBox chkAsuhanMedisRanapKandungan;
    private widget.CekBox chkAsuhanPsikolog;
    private widget.CekBox chkBerkasDigital;
    private widget.CekBox chkCatatanDokter;
    private widget.CekBox chkCatatanKeperawatanRanap;
    private widget.CekBox chkCatatanObservasiIGD;
    private widget.CekBox chkCatatanObservasiRanap;
    private widget.CekBox chkCatatanObservasiRanapKebidanan;
    private widget.CekBox chkCatatanObservasiRanapPostPartum;
    private widget.CekBox chkDiagnosaPenyakit;
    private widget.CekBox chkHemodialisa;
    private widget.CekBox chkMonitoringGizi;
    private widget.CekBox chkOperasiVK;
    private widget.CekBox chkPemberianObat;
    private widget.CekBox chkPemeriksaanGenekologiRalan;
    private widget.CekBox chkPemeriksaanGenekologiRanap;
    private widget.CekBox chkPemeriksaanLaborat;
    private widget.CekBox chkPemeriksaanObstetriRalan;
    private widget.CekBox chkPemeriksaanObstetriRanap;
    private widget.CekBox chkPemeriksaanRadiologi;
    private widget.CekBox chkPemeriksaanRalan;
    private widget.CekBox chkPemeriksaanRanap;
    private widget.CekBox chkPenggunaanKamar;
    private widget.CekBox chkPenggunaanObatOperasi;
    private widget.CekBox chkPotonganBiaya;
    private widget.CekBox chkProsedurTindakan;
    private widget.CekBox chkResepPulang;
    private widget.CekBox chkResume;
    private widget.CekBox chkSemua;
    private widget.CekBox chkSkriningGiziLanjut;
    private widget.CekBox chkTambahanBiaya;
    private widget.CekBox chkTindakanRalanDokter;
    private widget.CekBox chkTindakanRalanDokterParamedis;
    private widget.CekBox chkTindakanRalanParamedis;
    private widget.CekBox chkTindakanRanapDokter;
    private widget.CekBox chkTindakanRanapDokterParamedis;
    private widget.CekBox chkTindakanRanapParamedis;
    private widget.CekBox chkTriase;
    private widget.CekBox chkUjiFungsiKFR;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
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
    private widget.Table tbRegistrasi;
    // End of variables declaration//GEN-END:variables

    public void setNoRm(String norm,String nama) {
        NoRM.setText(norm);
        NmPasien.setText(nama);
        isPasien();
        BtnCari1ActionPerformed(null);
    }

    private void isPasien() {
        try{
            ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.agama,"+
                    "bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,pasien.gol_darah,pasien.nm_ibu,pasien.stts_nikah,pasien.pnd, "+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,pasien.pekerjaan "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "where pasien.no_rkm_medis=?");
            try {
                ps.setString(1,NoRM.getText().trim());
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
                    Pekerjaan.setText(rs.getString("pekerjaan"));
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
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 5");
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
            }else if(R4.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.status_lanjut,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "poliklinik.kd_poli,poliklinik.nm_poli,penjab.png_jawab from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj  "+
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? and reg_periksa.no_rawat=?");
            }
            
            try {
                i=0;
                if(R1.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R2.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R3.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else if(R4.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,NoRawat.getText().trim());
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
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            tabModeRegistrasi.addRow(new String[]{
                                "",rs.getString("no_rawat"),rs.getString("tgl_registrasi"),"",
                                rs2.getString("kd_dokter"),rs2.getString("nm_dokter"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                                rs2.getString("kd_poli")+" "+rs2.getString("nm_poli"),rs.getString("png_jawab")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 3 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }  
                    kddpjp="";
                    dpjp="";
                    if(rs.getString("status_lanjut").equals("Ranap")){
                        kddpjp=Sequel.cariIsi("select dpjp_ranap.kd_dokter from dpjp_ranap where dpjp_ranap.no_rawat=?",rs.getString("no_rawat"));
                        if(!kddpjp.equals("")){
                            dpjp=Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kddpjp);
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
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            tabModeRegistrasi.addRow(new String[]{
                                "",rs.getString("no_rawat"),rs2.getString("tgl_masuk"),rs2.getString("jam_masuk"),
                                kddpjp,dpjp,rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                                rs2.getString("kd_kamar")+" "+rs2.getString("nm_bangsal"),rs.getString("png_jawab")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    } 
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
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
            PanelAccor.setPreferredSize(new Dimension(275,HEIGHT));
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
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 5");
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
            }else if(R4.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                    "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                    "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? and reg_periksa.no_rawat=?");
            }
            
            try {
                i=0;
                if(R1.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R2.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R3.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else if(R4.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,NoRawat.getText().trim());
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
                        "<td valign='top' width='18%'>Dokter Poli</td>"+
                        "<td valign='top' width='1%' align='center'>:</td>"+
                        "<td valign='top' width='79%'>"+rs.getString("nm_dokter")+dokterrujukan+"</td>"+
                      "</tr>"
                    );
                    if(rs.getString("status_lanjut").equals("Ranap")){
                        try{
                            rs3=koneksi.prepareStatement(
                                "select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs3.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>DPJP Ranap</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"
                                );
                                rs3.beforeFirst();
                                urutdpjp=1;
                                while(rs3.next()){
                                    htmlContent.append(urutdpjp+". "+rs3.getString("nm_dokter")+"&nbsp;&nbsp;");
                                    urutdpjp++;
                                }
                                htmlContent.append("</td>"+
                                  "</tr>"
                                );    
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Data Triase IGD : "+e);
                        } finally{
                            if(rs3!=null){
                                rs3.close();
                            }
                        }
                    }
                    htmlContent.append( 
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
                    menampilkanTriaseIGD(rs.getString("no_rawat"));
                    //menampilkan asuhan awal keperawatan IGD
                    menampilkanAsuhanKeperawatanIGD(rs.getString("no_rawat"));
                    //menampilkan asuhan awal keperawatan rawat jalan
                    menampilkanAsuhanKeperawatanRalan(rs.getString("no_rawat"));
                    //menampilkan asuhan awal keperawatan rawat jalan gigi
                    menampilkanAsuhanKeperawatanRalanGigi(rs.getString("no_rawat"));
                    //menampilkan asuhan awal keperawatan rawat jalan bayi
                    menampilkanAsuhanKeperawatanRalanBayi(rs.getString("no_rawat"));
                    //menampilkan asuhan awal keperawatan rawat jalan kandungan
                    menampilkanAsuhanKeperawatanRalanKandungan(rs.getString("no_rawat"));
                    //menampilkan asuhan fisioterapi
                    menampilkanAsuhanFisioterapi(rs.getString("no_rawat"));
                    //menampilkan asuhan psikolog
                    menampilkanAsuhanPsikolog(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis IGD
                    menampilkanAsuhanMedisIGD(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat jalan
                    menampilkanAsuhanMedisRawatJalan(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat jalan kandungan
                    menampilkanAsuhanMedisRawatJalanKandungan(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat jalan bayi
                    menampilkanAsuhanMedisRawatJalanBayi(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat jalan THT
                    menampilkanAsuhanMedisRawatJalanTHT(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat jalan psikiatrik
                    menampilkanAsuhanMedisRawatJalanPsikiatrik(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat jalan penyakit dalam
                    menampilkanAsuhanMedisRawatJalanPenyakitDalam(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat jalan nata
                    menampilkanAsuhanMedisRawatJalanMata(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat neurologi
                    menampilkanAsuhanMedisRawatJalanNeurologi(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat orthopedi
                    menampilkanAsuhanMedisRawatJalanOrthopedi(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat bedah
                    menampilkanAsuhanMedisRawatJalanBedah(rs.getString("no_rawat"));
                    //menampilkan uji fungsi KFR
                    menampilkanUjiFungsiKFR(rs.getString("no_rawat"));
                    //menampilkan hemodialisa
                    menampilkanHemodialisa(rs.getString("no_rawat"));
                    //menampilkan pemeriksaan Ralan
                    menampilkanPemeriksaanRalan(rs.getString("no_rawat"));
                    //menampilkan asuhan keperawatan rawat inap
                    menampilkanAsuhanKeperawatanRawatInap(rs.getString("no_rawat"));
                    //menampilkan asuhan kebidanan rawat inap
                    menampilkanAsuhanKebidananRawatInap(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat inap
                    menampilkanAsuhanMedisRawatInap(rs.getString("no_rawat"));
                    //menampilkan asuhan awal medis rawat inap kebidanan
                    menampilkanAsuhanMedisRawatInapKebidanan(rs.getString("no_rawat"));
                    //menampilkan pemeriksaan rawat inap
                    menampilkanPemeriksaanRanap(rs.getString("no_rawat"));
                    //menampilkan skrining gizi lanjut
                    menampilkanGizi(rs.getString("no_rawat"));
                    //menampilkan diagnosa penyakit
                    menampilkanDiagnosa(rs.getString("no_rawat"));
                    //menampilkan berkas digital
                    menampilkanBerkasDigital(rs.getString("no_rawat"));
                    //menampilkan catatan dokter
                    if(chkCatatanDokter.isSelected()==true){
                        try {
                            rs2=koneksi.prepareStatement(
                                    "select catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"+
                                    "catatan_perawatan.catatan from catatan_perawatan inner join dokter on catatan_perawatan.kd_dokter=dokter.kd_dokter "+
                                    "where catatan_perawatan.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Catatan Dokter</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                         "<tr align='center'>"+
                                            "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                            "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                            "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode Dokter</td>"+
                                            "<td valign='top' width='20%' bgcolor='#FFFAF8'>Nama Dokter</td>"+
                                            "<td valign='top' width='51%' bgcolor='#FFFAF8'>Catatan</td>"+
                                         "</tr>"
                                );
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tanggal")+" "+rs2.getString("jam")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("catatan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    biayaperawatan=rs.getDouble("biaya_reg");
                    //biaya administrasi
                    htmlContent.append(
                       "<tr class='isi'>"+ 
                         "<td valign='top' width='2%'></td>"+        
                         "<td valign='top' width='18%'>Biaya & Perawatan</td>"+
                         "<td valign='top' width='1%' align='center'>:</td>"+
                         "<td valign='top' width='79%'>"+
                             "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                               "<tr>"+
                                 "<td valign='top' width='89%'>Administrasi</td>"+
                                 "<td valign='top' width='1%' align='right'>:</td>"+
                                 "<td valign='top' width='10%' align='right'>"+Valid.SetAngka(rs.getDouble("biaya_reg"))+"</td>"+
                               "</tr>"+
                             "</table>"
                    );
                    
                    //menampilkan tindakan rawat jalan dokter
                    if(chkTindakanRalanDokter.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                    "select rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,rawat_jl_dr.biaya_rawat, "+
                                    "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat from rawat_jl_dr inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter where rawat_jl_dr.no_rawat='"+rs.getString("no_rawat")+"' order by rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='41%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                      "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+" </td>"+
                                            "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_rawat"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("biaya_rawat");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan tindakan rawat jalan paramedis
                    if(chkTindakanRalanParamedis.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                    "select rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,petugas.nama,rawat_jl_pr.biaya_rawat, "+
                                    "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat from rawat_jl_pr inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "inner join petugas on rawat_jl_pr.nip=petugas.nip where rawat_jl_pr.no_rawat='"+rs.getString("no_rawat")+"' order by rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+                                        
                                    "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+      
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='41%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                      "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_rawat"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("biaya_rawat");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan tindakan rawat jalan dokter paramedis
                    if(chkTindakanRalanDokterParamedis.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                    "select rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_jl_drpr.biaya_rawat, "+
                                    "rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat from rawat_jl_drpr inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                    "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                                    "where rawat_jl_drpr.no_rawat='"+rs.getString("no_rawat")+"' order by rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='5'>Tindakan Rawat Jalan Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='27%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                      "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter</td>"+
                                      "<td valign='top' width='17%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_rawat"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("biaya_rawat");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan tindakan rawat inap dokter
                    if(chkTindakanRanapDokter.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                    "select rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,"+
                                    "rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                    "dokter.nm_dokter,rawat_inap_dr.biaya_rawat "+
                                    "from rawat_inap_dr inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                    "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter where rawat_inap_dr.no_rawat='"+rs.getString("no_rawat")+"' order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='41%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                      "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_rawat"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("biaya_rawat");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan tindakan rawat inap paramedis
                    if(chkTindakanRanapParamedis.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                    "select rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,"+
                                    "rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                    "petugas.nama,rawat_inap_pr.biaya_rawat "+
                                    "from rawat_inap_pr inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                    "inner join petugas on rawat_inap_pr.nip=petugas.nip where rawat_inap_pr.no_rawat='"+rs.getString("no_rawat")+"' order by rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='41%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                      "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_rawat"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("biaya_rawat");
                                }
                                htmlContent.append(
                                  "</table>");
                            }      
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan tindakan rawat inap Dokter paramedis
                    if(chkTindakanRanapDokterParamedis.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                    "select rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_jenis_prw,"+
                                    "jns_perawatan_inap.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_inap_drpr.biaya_rawat "+
                                    "from rawat_inap_drpr inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                    "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter inner join petugas on rawat_inap_drpr.nip=petugas.nip "+
                                    "where rawat_inap_drpr.no_rawat='"+rs.getString("no_rawat")+"' order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='5'>Tindakan Rawat Inap Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='27%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                      "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter</td>"+
                                      "<td valign='top' width='17%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_rawat"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("biaya_rawat");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan penggunaan kamar
                    if(chkPenggunaanKamar.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                    "select kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.tgl_masuk, kamar_inap.tgl_keluar, "+
                                    "kamar_inap.stts_pulang,kamar_inap.lama,kamar_inap.jam_masuk,kamar_inap.jam_keluar,"+
                                    "kamar_inap.ttl_biaya from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                                    "where kamar_inap.no_rawat='"+rs.getString("no_rawat")+"' order by kamar_inap.tgl_masuk,kamar_inap.jam_masuk").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='5'>Penggunaan Kamar</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal Masuk</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal Keluar</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Lama Inap</td>"+
                                      "<td valign='top' width='36%' bgcolor='#FFFAF8'>Kamar</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Status</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_masuk")+" "+rs2.getString("jam_masuk")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_keluar")+" "+rs2.getString("jam_keluar")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("lama")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_kamar")+", "+rs2.getString("nm_bangsal")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("stts_pulang")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("ttl_biaya"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("ttl_biaya");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan operasi/vk
                    if(chkOperasiVK.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
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
                                    "operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as total "+
                                    "from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                                    "where operasi.no_rawat='"+rs.getString("no_rawat")+"' order by operasi.tgl_operasi").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='4'>Operasi/VK</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='51%' bgcolor='#FFFAF8'>Nama Tindakan</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Anastesi</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_operasi")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kode_paket")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+" (");
                                    if(rs2.getDouble("biayaoperator1")>0){
                                        htmlContent.append("Operator 1 : "+Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs2.getString("operator1"))+", ");
                                    }
                                    if(rs2.getDouble("biayaoperator2")>0){
                                        htmlContent.append("Operator 2 : "+Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs2.getString("operator2"))+", ");
                                    }
                                    if(rs2.getDouble("biayaoperator3")>0){
                                        htmlContent.append("Operator 3 : "+Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs2.getString("operator3"))+", ");
                                    }
                                    if(rs2.getDouble("biayaasisten_operator1")>0){
                                        htmlContent.append("Asisten Operator 1 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("asisten_operator1"))+", ");
                                    }
                                    if(rs2.getDouble("biayaasisten_operator2")>0){
                                        htmlContent.append("Asisten Operator 2 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("asisten_operator2"))+", ");
                                    }
                                    if(rs2.getDouble("biayaasisten_operator3")>0){
                                        htmlContent.append("Asisten Operator 3 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("asisten_operator3"))+", ");
                                    }
                                    if(rs2.getDouble("biayainstrumen")>0){
                                        htmlContent.append("Instrumen : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("instrumen"))+", ");
                                    }
                                    if(rs2.getDouble("biayadokter_anak")>0){
                                        htmlContent.append("Dokter Anak : "+Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs2.getString("dokter_anak"))+", ");
                                    }
                                    if(rs2.getDouble("biayaperawaat_resusitas")>0){
                                        htmlContent.append("Perawat Resusitas : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("perawaat_resusitas"))+", ");
                                    }
                                    if(rs2.getDouble("biayadokter_anestesi")>0){
                                        htmlContent.append("Dokter Anestesi : "+Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs2.getString("dokter_anestesi"))+", ");
                                    }
                                    if(rs2.getDouble("biayaasisten_anestesi")>0){
                                        htmlContent.append("Asisten Anestesi : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("asisten_anestesi"))+", ");
                                    }
                                    if(rs2.getDouble("biayaasisten_anestesi2")>0){
                                        htmlContent.append("Asisten Anestesi 2 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("asisten_anestesi2"))+", ");
                                    }
                                    if(rs2.getDouble("biayabidan")>0){
                                        htmlContent.append("Bidan 1 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("bidan"))+", ");
                                    }
                                    if(rs2.getDouble("biayabidan2")>0){
                                        htmlContent.append("Bidan 2 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("bidan2"))+", ");
                                    }
                                    if(rs2.getDouble("biayabidan3")>0){
                                        htmlContent.append("Bidan 3 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("bidan3"))+", ");
                                    }
                                    if(rs2.getDouble("biayaperawat_luar")>0){
                                        htmlContent.append("Perawat Luar : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("perawat_luar"))+", ");
                                    }
                                    if(rs2.getDouble("biaya_omloop")>0){
                                        htmlContent.append("Onloop 1 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("omloop"))+", ");
                                    }
                                    if(rs2.getDouble("biaya_omloop2")>0){
                                        htmlContent.append("Onloop 2 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("omloop2"))+", ");
                                    }
                                    if(rs2.getDouble("biaya_omloop3")>0){
                                        htmlContent.append("Onloop 3 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("omloop3"))+", ");
                                    }
                                    if(rs2.getDouble("biaya_omloop4")>0){
                                        htmlContent.append("Onloop 4 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("omloop4"))+", ");
                                    }
                                    if(rs2.getDouble("biaya_omloop5")>0){
                                        htmlContent.append("Onloop 5 : "+Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs2.getString("omloop5"))+", ");
                                    }
                                    if(rs2.getDouble("biaya_dokter_pjanak")>0){
                                        htmlContent.append("Dokter Pj Anak : "+Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs2.getString("dokter_pjanak"))+", ");
                                    }
                                    if(rs2.getDouble("biaya_dokter_umum")>0){
                                        htmlContent.append("Dokter Umum : "+Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs2.getString("dokter_umum"))+", ");
                                    }
                                    htmlContent.append(
                                            ")</td>"+
                                            "<td valign='top'>"+rs2.getString("jenis_anasthesi")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("total"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("total");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                       
                        try{
                            rs2=koneksi.prepareStatement(
                                    "select tanggal, diagnosa_preop, diagnosa_postop, jaringan_dieksekusi, selesaioperasi, permintaan_pa, laporan_operasi "+
                                    "from laporan_operasi where no_rawat='"+rs.getString("no_rawat")+"' group by no_rawat,tanggal order by tanggal").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='3'>Laporan Operasi :</td></tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' width='4%' align='center'>"+w+"</td>"+
                                            "<td valign='top' width='21%'>Mulai Operasi</td>"+
                                            "<td valign='top' width='75%'>:&nbsp;"+rs2.getString("tanggal")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' width='4%' align='center'></td>"+
                                            "<td valign='top' width='21%'>Diagnosa Pre-operatif</td>"+
                                            "<td valign='top' width='75%'>:&nbsp;"+rs2.getString("diagnosa_preop")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' width='4%' align='center'></td>"+
                                            "<td valign='top' width='21%'>Jaringan Yang di-Eksisi/-Insisi</td>"+
                                            "<td valign='top' width='75%'>:&nbsp;"+rs2.getString("jaringan_dieksekusi")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' width='4%' align='center'></td>"+
                                            "<td valign='top' width='21%'>Diagnosa Post-operatif</td>"+
                                            "<td valign='top' width='75%'>:&nbsp;"+rs2.getString("diagnosa_postop")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' width='4%' align='center'></td>"+
                                            "<td valign='top' width='21%'>Selesai Operasi</td>"+
                                            "<td valign='top' width='75%'>:&nbsp;"+rs2.getString("selesaioperasi")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' width='4%' align='center'></td>"+
                                            "<td valign='top' width='21%'>Dikirim Untuk Pemeriksaan PA</td>"+
                                            "<td valign='top' width='75%'>:&nbsp;"+rs2.getString("permintaan_pa")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' width='4%' align='center'></td>"+
                                            "<td valign='top' width='21%'>Laporan</td>"+
                                            "<td valign='top' width='75%'>:&nbsp;"+rs2.getString("laporan_operasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"); 
                                    w++;
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan pemeriksaan radiologi
                    if(chkPemeriksaanRadiologi.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                 "select periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_jenis_prw, "+
                                 "jns_perawatan_radiologi.nm_perawatan,petugas.nama,periksa_radiologi.biaya,periksa_radiologi.dokter_perujuk,"+
                                 "dokter.nm_dokter,concat("+
                                "if(periksa_radiologi.proyeksi<>'',concat('Proyeksi : ',periksa_radiologi.proyeksi,', '),''),"+
                                "if(periksa_radiologi.kV<>'',concat('kV : ',periksa_radiologi.kV,', '),''),"+
                                "if(periksa_radiologi.mAS<>'',concat('mAS : ',periksa_radiologi.mAS,', '),''),"+
                                "if(periksa_radiologi.FFD<>'',concat('FFD : ',periksa_radiologi.FFD,', '),''),"+
                                "if(periksa_radiologi.BSF<>'',concat('BSF : ',periksa_radiologi.BSF,', '),''),"+
                                "if(periksa_radiologi.inak<>'',concat('Inak : ',periksa_radiologi.inak,', '),''),"+
                                "if(periksa_radiologi.jml_penyinaran<>'',concat('Jml Penyinaran : ',periksa_radiologi.jml_penyinaran,', '),''),"+
                                "if(periksa_radiologi.dosis<>'',concat('Dosis Radiasi : ',periksa_radiologi.dosis),'')) as proyeksi "+
                                 "from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                                 "inner join petugas on periksa_radiologi.nip=petugas.nip inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                 "where periksa_radiologi.no_rawat='"+rs.getString("no_rawat")+"' order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='5'>Pemeriksaan Radiologi</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                      "<td valign='top' width='18%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                      "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+"<br>"+rs2.getString("proyeksi")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("biaya");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }

                        //hasil pemeriksaan radiologi
                        try{
                            rs2=koneksi.prepareStatement(
                                 "select tgl_periksa,jam, hasil from hasil_radiologi where no_rawat='"+rs.getString("no_rawat")+"' order by tgl_periksa,jam").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='3'>Bacaan/Hasil Radiologi</td></tr>"+  
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='81%' bgcolor='#FFFAF8'>Hasil Pemeriksaan</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("hasil").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"); 
                                    w++;
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }

                        //gambar pemeriksaan radiologi
                        try{
                            rs2=koneksi.prepareStatement(
                                 "select tgl_periksa,jam, lokasi_gambar from gambar_radiologi where no_rawat='"+rs.getString("no_rawat")+"' order by tgl_periksa,jam").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='3'>Gambar Radiologi</td></tr>"+  
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='81%' bgcolor='#FFFAF8'>Gambar Radiologi</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"</td>"+
                                            "<td valign='top' align='center'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/radiologi/"+rs2.getString("lokasi_gambar")+"'><img alt='Gambar Radiologi' src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/radiologi/"+rs2.getString("lokasi_gambar")+"' width='450' height='450'/></a></td>"+
                                         "</tr>"); 
                                    w++;
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan pemeriksaan laborat
                    if(chkPemeriksaanLaborat.isSelected()==true){
                        try {
                            rs4=koneksi.prepareStatement(
                                 "select periksa_lab.tgl_periksa,periksa_lab.jam from periksa_lab where periksa_lab.kategori='PK' and periksa_lab.no_rawat='"+rs.getString("no_rawat")+"' "+
                                 "group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) order by periksa_lab.tgl_periksa,periksa_lab.jam").executeQuery();
                            if(rs4.next()){
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='5'>Pemeriksaan Laboratorium PK</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                      "<td valign='top' width='18%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                      "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs4.beforeFirst();
                                w=1;
                                while(rs4.next()){
                                    try{
                                        rs2=koneksi.prepareStatement(
                                             "select periksa_lab.kd_jenis_prw, "+
                                             "jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.biaya,periksa_lab.dokter_perujuk,dokter.nm_dokter "+
                                             "from periksa_lab inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                                             "inner join petugas on periksa_lab.nip=petugas.nip inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                                             "where periksa_lab.kategori='PK' and periksa_lab.no_rawat='"+rs.getString("no_rawat")+"' "+
                                             "and periksa_lab.tgl_periksa='"+rs4.getString("tgl_periksa")+"' and periksa_lab.jam='"+rs4.getString("jam")+"'").executeQuery();
                                        s=1;
                                        while(rs2.next()){
                                            if(s==1){
                                                htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'>"+w+"</td>"+
                                                       "<td valign='top'>"+rs4.getString("tgl_periksa")+" "+rs4.getString("jam")+"</td>"+
                                                       "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                                       "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                                       "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                                       "<td valign='top'>"+rs2.getString("nama")+"</td>"+
                                                       "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya"))+"</td>"+
                                                    "</tr>"
                                               ); 
                                            }else{
                                                htmlContent.append(
                                                     "<tr>"+
                                                        "<td valign='top' align='center'></td>"+
                                                        "<td valign='top'></td>"+
                                                        "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                                        "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                                        "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                                        "<td valign='top'>"+rs2.getString("nama")+"</td>"+
                                                        "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya"))+"</td>"+
                                                     "</tr>"
                                                ); 
                                            }
                                                
                                            biayaperawatan=biayaperawatan+rs2.getDouble("biaya");
                                            
                                            try {
                                                rs3=koneksi.prepareStatement(
                                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                                                    "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                                    "detail_periksa_lab.keterangan from detail_periksa_lab inner join "+
                                                    "template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                                    "where detail_periksa_lab.no_rawat='"+rs.getString("no_rawat")+"' and "+
                                                    "detail_periksa_lab.kd_jenis_prw='"+rs2.getString("kd_jenis_prw")+"' and "+
                                                    "detail_periksa_lab.tgl_periksa='"+rs4.getString("tgl_periksa")+"' and "+
                                                    "detail_periksa_lab.jam='"+rs4.getString("jam")+"' order by detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut ").executeQuery();
                                                if(rs3.next()){ 
                                                    htmlContent.append(
                                                        "<tr>"+
                                                           "<td valign='top' align='center'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top' align='center' bgcolor='#FFFAF8'>Detail Pemeriksaan</td>"+
                                                           "<td valign='top' align='center' bgcolor='#FFFAF8'>Hasil</td>"+
                                                           "<td valign='top' align='center' bgcolor='#FFFAF8'>Nilai Rujukan</td>"+
                                                           "<td valign='top' align='right'></td>"+
                                                        "</tr>");
                                                    rs3.beforeFirst();
                                                    while(rs3.next()){
                                                        htmlContent.append(
                                                            "<tr>"+
                                                               "<td valign='top' align='center'></td>"+
                                                               "<td valign='top'></td>"+
                                                               "<td valign='top'></td>"+
                                                               "<td valign='top'>"+rs3.getString("Pemeriksaan")+"</td>"+
                                                               "<td valign='top'>"+rs3.getString("nilai")+" "+rs3.getString("satuan")+"</td>"+
                                                               "<td valign='top'>"+rs3.getString("nilai_rujukan")+"</td>"+
                                                               "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_item"))+"</td>"+
                                                            "</tr>"); 
                                                        biayaperawatan=biayaperawatan+rs3.getDouble("biaya_item");
                                                    }                                               
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notifikasi : "+e);
                                            } finally{
                                                if(rs3!=null){
                                                    rs3.close();
                                                }
                                            }
                                            s++;
                                        }

                                        try {
                                            rs3=koneksi.prepareStatement("select saran,kesan from saran_kesan_lab where no_rawat='"+rs.getString("no_rawat")+"' and tgl_periksa='"+rs4.getString("tgl_periksa")+"' and jam='"+rs4.getString("jam")+"'").executeQuery();
                                            if(rs3.next()){      
                                                htmlContent.append(
                                                        "<tr>"+
                                                           "<td valign='top' align='center'></td>"+
                                                           "<td valign='top'>Kesan</td>"+
                                                           "<td valign='top' colspan='5'>: "+rs3.getString("kesan")+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                           "<td valign='top' align='center'></td>"+
                                                           "<td valign='top'>Saran</td>"+
                                                           "<td valign='top' colspan='5'>: "+rs3.getString("saran")+"</td>"+
                                                        "</tr>");
                                            } 
                                        } catch (Exception e) {
                                            System.out.println("Notif : "+e);
                                        } finally{
                                            if(rs3!=null){
                                                rs3.close();
                                            }
                                        }   
                                        w++;
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi Lab : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                    }
                                }
                                htmlContent.append(
                                              "</table>");
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs4!=null){
                                rs4.close();
                            }
                        }

                        try{
                            rs2=koneksi.prepareStatement(
                                 "select periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                                 "jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.biaya,periksa_lab.dokter_perujuk,dokter.nm_dokter "+
                                 "from periksa_lab inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                                 "inner join petugas on periksa_lab.nip=petugas.nip inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                                 "where periksa_lab.kategori='PA' and periksa_lab.no_rawat='"+rs.getString("no_rawat")+"' order by periksa_lab.tgl_periksa,periksa_lab.jam").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='5'>Pemeriksaan Laboratorium PA</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                      "<td valign='top' width='18%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                      "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_jenis_prw")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_perawatan")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya"))+"</td>"+
                                         "</tr>"
                                    ); 
                                    biayaperawatan=biayaperawatan+rs2.getDouble("biaya");
                                    try {
                                        rs3=koneksi.prepareStatement(
                                            "select diagnosa_klinik,makroskopik,mikroskopik,kesimpulan,kesan from detail_periksa_labpa "+
                                            "where no_rawat='"+rs.getString("no_rawat")+"' and kd_jenis_prw='"+rs2.getString("kd_jenis_prw")+"' and "+
                                            "tgl_periksa='"+rs2.getString("tgl_periksa")+"' and jam='"+rs2.getString("jam")+"'").executeQuery();
                                        if(rs3.next()){ 
                                            file=Sequel.cariIsi("select photo from detail_periksa_labpa_gambar where no_rawat='"+rs.getString("no_rawat")+"' and kd_jenis_prw='"+rs2.getString("kd_jenis_prw")+"' and tgl_periksa='"+rs2.getString("tgl_periksa")+"' and jam='"+rs2.getString("jam")+"'");
                                            htmlContent.append(
                                                "<tr>"+
                                                   "<td valign='top' align='center'></td>"+
                                                   "<td valign='top'></td>"+
                                                   "<td valign='top'>Diagnosa Klinis</td>"+
                                                   "<td valign='top' colspan='4'>: "+rs3.getString("diagnosa_klinik")+"</td>"+
                                                "</tr>"+
                                                "<tr>"+
                                                   "<td valign='top' align='center'></td>"+
                                                   "<td valign='top'></td>"+
                                                   "<td valign='top'>Makroskopik</td>"+
                                                   "<td valign='top' colspan='4'>: "+rs3.getString("makroskopik")+"</td>"+
                                                "</tr>"+
                                                "<tr>"+
                                                   "<td valign='top' align='center'></td>"+
                                                   "<td valign='top'></td>"+
                                                   "<td valign='top'>Mikroskopik</td>"+
                                                   "<td valign='top' colspan='4'>: "+rs3.getString("mikroskopik")+"</td>"+
                                                "</tr>"+
                                                "<tr>"+
                                                   "<td valign='top' align='center'></td>"+
                                                   "<td valign='top'></td>"+
                                                   "<td valign='top'>Kesimpulan</td>"+
                                                   "<td valign='top' colspan='4'>: "+rs3.getString("kesimpulan")+"</td>"+
                                                "</tr>"+
                                                "<tr>"+
                                                   "<td valign='top' align='center'></td>"+
                                                   "<td valign='top'></td>"+
                                                   "<td valign='top'>Kesan</td>"+
                                                   "<td valign='top' colspan='4'>: "+rs3.getString("kesan")+"</td>"+
                                                "</tr>"
                                            );  
                                            if(!file.equals("")){
                                                htmlContent.append(
                                                    "<tr>"+
                                                        "<td valign='top' align='center'></td>"+
                                                        "<td valign='top'></td>"+
                                                        "<td valign='top' colspan='5' align='center'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/labpa/"+file+"'><img alt='Gambar PA' src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/labpa/"+file+"' width='450' height='450'/></a></td>"+
                                                    "</tr>"
                                                );
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                    }
                                    w++;
                                }

                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi Lab : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan pemberian obat
                    if(chkPemberianObat.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,databarang.kode_sat, "+
                                "detail_pemberian_obat.kode_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                                "databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                                "on detail_pemberian_obat.kode_brng=databarang.kode_brng  "+
                                "where detail_pemberian_obat.no_rawat='"+rs.getString("no_rawat")+"' order by detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='5'>Pemberian Obat/BHP/Alkes</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='35%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                      "<td valign='top' width='16%' bgcolor='#FFFAF8'>Aturan Pakai</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kode_brng")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama_brng")+"</td>"+
                                            "<td valign='top'>"+rs2.getDouble("jml")+" "+rs2.getString("kode_sat")+"</td>"+
                                            "<td valign='top'>"+Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='"+rs2.getString("tgl_perawatan")+"' and jam='"+rs2.getString("jam")+"' and no_rawat='"+rs.getString("no_rawat")+"' and kode_brng='"+rs2.getString("kode_brng")+"'")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("total"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("total");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                        
                        try{
                            rs2=koneksi.prepareStatement(
                                "select databarang.kode_brng,databarang.nama_brng,detreturjual.kode_sat,detreturjual.h_retur, "+
                             "(detreturjual.jml_retur * -1) as jumlah,(detreturjual.subtotal * -1) as total from detreturjual "+
                             "inner join databarang on detreturjual.kode_brng=databarang.kode_brng  "+
                                "inner join returjual on returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual='"+rs.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='3'>Retur Obat</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='66%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kode_brng")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama_brng")+"</td>"+
                                            "<td valign='top'>"+rs2.getDouble("jumlah")+" "+rs2.getString("kode_sat")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("total"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("total");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan penggunaan obat operasi
                    if(chkPenggunaanObatOperasi.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                "select beri_obat_operasi.tanggal,beri_obat_operasi.kd_obat,beri_obat_operasi.hargasatuan,obatbhp_ok.kode_sat, "+
                                "beri_obat_operasi.jumlah, obatbhp_ok.nm_obat,(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                                "from beri_obat_operasi inner join obatbhp_ok  on  beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat  "+
                                "where beri_obat_operasi.no_rawat='"+rs.getString("no_rawat")+"' order by beri_obat_operasi.tanggal").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='4'>Penggunaan Obat/BHP Operasi</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='51%' bgcolor='#FFFAF8'>Nama Obat/BHP</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("tanggal")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_obat")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_obat")+"</td>"+
                                            "<td valign='top'>"+rs2.getDouble("jumlah")+" "+rs2.getString("kode_sat")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("total"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("total");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan resep pulang
                    if(chkResepPulang.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                "select resep_pulang.kode_brng,databarang.nama_brng,resep_pulang.dosis,resep_pulang.jml_barang, "+
                                "databarang.kode_sat,resep_pulang.dosis,resep_pulang.total from resep_pulang inner join databarang "+
                                "on resep_pulang.kode_brng=databarang.kode_brng where "+
                                "resep_pulang.no_rawat='"+rs.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='4'>Resep Pulang</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                      "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                      "<td valign='top' width='16%' bgcolor='#FFFAF8'>Dosis</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kode_brng")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama_brng")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("dosis")+"</td>"+
                                            "<td valign='top'>"+rs2.getDouble("jml_barang")+" "+rs2.getString("kode_sat")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("total"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("total");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan tambahan biaya
                    if(chkTambahanBiaya.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                "select nama_biaya, besar_biaya from tambahan_biaya where no_rawat='"+rs.getString("no_rawat")+"' order by nama_biaya").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='2'>Tambahan Biaya</td><td valign='top' align='right'>:</td><td></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='85%' bgcolor='#FFFAF8'>Nama Tambahan</td>"+
                                      "<td valign='top' width='1%' bgcolor='#FFFAF8'></td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama_biaya")+"</td>"+
                                            "<td valign='top'></td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("besar_biaya"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("besar_biaya");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    //menampilkan potongan biaya
                    if(chkPotonganBiaya.isSelected()==true){
                        try{
                            rs2=koneksi.prepareStatement(
                                "select nama_pengurangan, (-1*besar_pengurangan) as besar_pengurangan from pengurangan_biaya where no_rawat='"+rs.getString("no_rawat")+"' order by nama_pengurangan").executeQuery();
                            if(rs2.next()){                                    
                                htmlContent.append(  
                                  "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr><td valign='top' colspan='2'>Potongan Biaya</td><td valign='top' align='right'>:</td><td></td></tr>"+            
                                    "<tr align='center'>"+
                                      "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                      "<td valign='top' width='85%' bgcolor='#FFFAF8'>Nama Potongan</td>"+
                                      "<td valign='top' width='1%' bgcolor='#FFFAF8'></td>"+
                                      "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                w=1;
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+w+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nama_pengurangan")+"</td>"+
                                            "<td valign='top'></td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("besar_pengurangan"))+"</td>"+
                                         "</tr>"); 
                                    w++;
                                    biayaperawatan=biayaperawatan+rs2.getDouble("besar_pengurangan");
                                }
                                htmlContent.append(
                                  "</table>");
                            }                                
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                        }
                    }
                    
                    htmlContent.append(     
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                               "<tr><td valign='top' width='89%' colspan='2'>Total Biaya</td><td valign='top' width='1%' align='right'>:</td><td valign='top' width='10%' align='right'>"+Valid.SetAngka(biayaperawatan)+"</td></tr>"+            
                            "</table>"+
                         "</td>"+
                       "</tr>"                               
                    );
                    //menampilkan resume perawatan
                    if(chkResume.isSelected()==true){
                        try {
                            rs2=koneksi.prepareStatement(
                                "select resume_pasien.kd_dokter,dokter.nm_dokter,resume_pasien.kondisi_pulang,resume_pasien.keluhan_utama, "+
                                "resume_pasien.jalannya_penyakit,resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama, "+
                                "resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2, "+
                                "resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,resume_pasien.kd_diagnosa_sekunder4, "+
                                "resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder, "+
                                "resume_pasien.prosedur_sekunder2,resume_pasien.kd_prosedur_sekunder2,resume_pasien.prosedur_sekunder3,resume_pasien.kd_prosedur_sekunder3, "+
                                "resume_pasien.obat_pulang from resume_pasien inner join dokter on resume_pasien.kd_dokter=dokter.kd_dokter "+
                                "where resume_pasien.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Resume Pasien</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                         "<tr align='center'>"+
                                            "<td valign='top' width='20%' bgcolor='#FFFAF8'>Status</td>"+
                                            "<td valign='top' width='20%' bgcolor='#FFFAF8'>Kode Dokter</td>"+
                                            "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Dokter</td>"+
                                            "<td valign='top' width='20%' bgcolor='#FFFAF8'>Kondisi Pulang</td>"+
                                         "</tr>"
                                );
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top'>"+rs.getString("status_lanjut")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("kondisi_pulang")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='4'>Keluhan utama riwayat penyakit yang positif :<br>"+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='4'>Jalannya penyakit selama perawatan :<br>"+rs2.getString("jalannya_penyakit").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='4'>Pemeriksaan penunjang yang positif :<br>"+rs2.getString("pemeriksaan_penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='4'>Hasil laboratorium yang positif :<br>"+rs2.getString("hasil_laborat").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='4'>Diagnosa Akhir :<br>"+
                                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Utama</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_utama")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_utama")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Sekunder 1</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_sekunder")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_sekunder")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Sekunder 2</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_sekunder2")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_sekunder2")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Sekunder 3</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_sekunder3")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_sekunder3")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Sekunder 4</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_sekunder4")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_sekunder4")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Prosedur Utama</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("prosedur_utama")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_prosedur_utama")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Prosedur Sekunder 1</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("prosedur_sekunder")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_prosedur_sekunder")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Prosedur Sekunder 2</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("prosedur_sekunder2")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_prosedur_sekunder2")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Prosedur Sekunder 3</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("prosedur_sekunder3")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_prosedur_sekunder3")+"</td>"+
                                                    "</tr>"+
                                                "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='4'>Obat-obatan waktu pulang/nasihat :<br>"+rs2.getString("obat_pulang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"
                                    );                                        
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
                        try {
                            rs2=koneksi.prepareStatement(
                                "select resume_pasien_ranap.kd_dokter,dokter.nm_dokter,resume_pasien_ranap.diagnosa_awal,resume_pasien_ranap.alasan,resume_pasien_ranap.keluhan_utama,resume_pasien_ranap.pemeriksaan_fisik,"+
                                "resume_pasien_ranap.jalannya_penyakit,resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,resume_pasien_ranap.tindakan_dan_operasi,resume_pasien_ranap.obat_di_rs,"+
                                "resume_pasien_ranap.diagnosa_utama,resume_pasien_ranap.kd_diagnosa_utama,resume_pasien_ranap.diagnosa_sekunder,resume_pasien_ranap.kd_diagnosa_sekunder,resume_pasien_ranap.diagnosa_sekunder2,"+
                                "resume_pasien_ranap.kd_diagnosa_sekunder2,resume_pasien_ranap.diagnosa_sekunder3,resume_pasien_ranap.kd_diagnosa_sekunder3,resume_pasien_ranap.diagnosa_sekunder4,"+
                                "resume_pasien_ranap.kd_diagnosa_sekunder4,resume_pasien_ranap.prosedur_utama,resume_pasien_ranap.kd_prosedur_utama,resume_pasien_ranap.prosedur_sekunder,resume_pasien_ranap.kd_prosedur_sekunder,"+
                                "resume_pasien_ranap.prosedur_sekunder2,resume_pasien_ranap.kd_prosedur_sekunder2,resume_pasien_ranap.prosedur_sekunder3,resume_pasien_ranap.kd_prosedur_sekunder3,resume_pasien_ranap.alergi,"+
                                "resume_pasien_ranap.diet,resume_pasien_ranap.lab_belum,resume_pasien_ranap.edukasi,resume_pasien_ranap.cara_keluar,resume_pasien_ranap.ket_keluar,resume_pasien_ranap.keadaan,"+
                                "resume_pasien_ranap.ket_keadaan,resume_pasien_ranap.dilanjutkan,resume_pasien_ranap.ket_dilanjutkan,resume_pasien_ranap.kontrol,resume_pasien_ranap.obat_pulang "+
                                "from resume_pasien_ranap inner join dokter on resume_pasien_ranap.kd_dokter=dokter.kd_dokter where resume_pasien_ranap.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                            if(rs2.next()){
                                htmlContent.append(
                                  "<tr class='isi'>"+ 
                                    "<td valign='top' width='2%'></td>"+        
                                    "<td valign='top' width='18%'>Resume Pasien</td>"+
                                    "<td valign='top' width='1%' align='center'>:</td>"+
                                    "<td valign='top' width='79%'>"+
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                         "<tr align='center'>"+
                                            "<td valign='top' width='6%' bgcolor='#FFFAF8'>Status</td>"+
                                            "<td valign='top' width='15%' bgcolor='#FFFAF8'>Kode Dokter</td>"+
                                            "<td valign='top' width='27%' bgcolor='#FFFAF8'>Nama Dokter</td>"+
                                            "<td valign='top' width='13%' bgcolor='#FFFAF8'>Keadaan Pulang</td>"+
                                            "<td valign='top' width='13%' bgcolor='#FFFAF8'>Cara Keluar</td>"+
                                            "<td valign='top' width='13%' bgcolor='#FFFAF8'>Dilanjutkan</td>"+
                                            "<td valign='top' width='13%' bgcolor='#FFFAF8'>Tgl.Kontrol</td>"+
                                         "</tr>"
                                );
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(
                                         "<tr>"+
                                            "<td valign='top' align='center'>"+rs.getString("status_lanjut")+"</td>"+
                                            "<td valign='top' align='center'>"+rs2.getString("kd_dokter")+"</td>"+
                                            "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                            "<td valign='top' align='center'>"+rs2.getString("keadaan")+(rs2.getString("ket_keadaan").equals("")?"":", "+rs2.getString("ket_keadaan"))+"</td>"+
                                            "<td valign='top' align='center'>"+rs2.getString("cara_keluar")+(rs2.getString("ket_keluar").equals("")?"":", "+rs2.getString("ket_keluar"))+"</td>"+
                                            "<td valign='top' align='center'>"+rs2.getString("dilanjutkan")+(rs2.getString("ket_dilanjutkan").equals("")?"":", "+rs2.getString("ket_dilanjutkan"))+"</td>"+
                                            "<td valign='top' align='center'>"+rs2.getString("kontrol")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Diagnosa Awal Masuk :<br>"+rs2.getString("diagnosa_awal").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Alasan Masuk Dirawat :<br>"+rs2.getString("alasan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Keluhan Utama Riwayat Penyakit :<br>"+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Pemeriksaan Fisik :<br>"+rs2.getString("pemeriksaan_fisik").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Jalannya Penyakit Selama Perawatan :<br>"+rs2.getString("jalannya_penyakit").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Pemeriksaan Penunjang Rad Terpenting :<br>"+rs2.getString("pemeriksaan_penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Pemeriksaan Penunjang Lab Terpenting :<br>"+rs2.getString("hasil_laborat").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Tindakan/Operasi Selama Perawatan :<br>"+rs2.getString("tindakan_dan_operasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Obat-obatan Selama Perawatan :<br>"+rs2.getString("obat_di_rs").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Diagnosa Akhir :<br>"+
                                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Utama</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_utama")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_utama")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Sekunder 1</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_sekunder")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_sekunder")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Sekunder 2</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_sekunder2")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_sekunder2")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Sekunder 3</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_sekunder3")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_sekunder3")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Diagnosa Sekunder 4</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("diagnosa_sekunder4")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_diagnosa_sekunder4")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Prosedur Utama</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("prosedur_utama")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_prosedur_utama")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Prosedur Sekunder 1</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("prosedur_sekunder")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_prosedur_sekunder")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Prosedur Sekunder 2</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("prosedur_sekunder2")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_prosedur_sekunder2")+"</td>"+
                                                    "</tr>"+
                                                    "<tr align='left' border='0'>"+
                                                        "<td valign='top' width='20%' border='0'>Prosedur Sekunder 3</td><td valign='top' width='60%' border='0'>:&nbsp;"+rs2.getString("prosedur_sekunder3")+"</td><td valign='top' width='20%' border='0'>&nbsp;"+rs2.getString("kd_prosedur_sekunder3")+"</td>"+
                                                    "</tr>"+
                                                "</table>"+
                                            "</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Alergi Obat :<br>"+rs2.getString("alergi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Diet :<br>"+rs2.getString("diet").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Hasil Lab Yang Belum Selesai (Pending) :<br>"+rs2.getString("lab_belum").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Instruksi/Anjuran Dan Edukasi (Follow Up) :<br>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"+
                                         "<tr>"+
                                            "<td valign='top' colspan='7'>Obat-obatan Waktu Pulang :<br>"+rs2.getString("obat_pulang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                         "</tr>"
                                    );                                        
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
                    
                    if(R4.isSelected()==true){
                        if(rs.getString("status_lanjut").equals("Ralan")){
                            get = new GetMethod("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penggajian/generateqrcode.php?kodedokter="+rs.getString("kd_dokter").replace(" ","_"));
                            http.executeMethod(get);

                            htmlContent.append(
                                "<tr class='isi'>"+ 
                                   "<td valign='top' width='2%'></td>"+        
                                   "<td valign='middle' width='18%'>Tanda Tangan/Verifikasi</td>"+
                                   "<td valign='middle' width='1%' align='center'>:</td>"+
                                   "<td valign='middle' width='79%' align='center'>Dokter Poli<br><img width='90' height='90' src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penggajian/temp/"+rs.getString("kd_dokter")+".png'/><br>"+rs.getString("nm_dokter")+"</td>"+
                                "</tr>"
                            );
                        }else if(rs.getString("status_lanjut").equals("Ranap")){
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='"+rs.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+ 
                                          "<td valign='top' width='2%'></td>"+        
                                          "<td valign='middle' width='18%'>Tanda Tangan/Verifikasi</td>"+
                                          "<td valign='middle' width='1%' align='center'>:</td>"+
                                          "<td valign='top' width='79%' align='center'>"+
                                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                 "<tr class='isi'>"
                                      );
                                      rs3.beforeFirst();
                                      urutdpjp=1;
                                      while(rs3.next()){
                                          get = new GetMethod("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penggajian/generateqrcode.php?kodedokter="+rs3.getString("kd_dokter").replace(" ","_"));
                                          http.executeMethod(get);
                                          htmlContent.append("<td border='0' align='center'>Dokter DPJP "+urutdpjp+"<br><img width='90' height='90' src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penggajian/temp/"+rs3.getString("kd_dokter")+".png'/><br>"+rs3.getString("nm_dokter")+"</td>");
                                          urutdpjp++;
                                      }
                                      htmlContent.append(
                                                  "</tr>"+
                                              "</table>"+
                                          "</td>"+
                                        "</tr>"
                                      );    
                                }else{
                                    get = new GetMethod("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penggajian/generateqrcode.php?kodedokter="+rs.getString("kd_dokter").replace(" ","_"));
                                    http.executeMethod(get);

                                    htmlContent.append(
                                        "<tr class='isi'>"+ 
                                           "<td valign='top' width='2%'></td>"+        
                                           "<td valign='middle' width='18%'>Tanda Tangan/Verifikasi</td>"+
                                           "<td valign='middle' width='1%' align='center'>:</td>"+
                                           "<td valign='middle' width='79%' align='center'>Dokter DPJP<br><img width='90' height='90' src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penggajian/temp/"+rs.getString("kd_dokter")+".png'/><br>"+rs.getString("nm_dokter")+"</td>"+
                                        "</tr>"
                                    );
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi Data Triase IGD : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                        }
                    }
                    htmlContent.append(
                        "<tr class='isi'><td></td><td colspan='3' align='right'>&nbsp;</tr>"
                    );
                    
                }
                
                LoadHTMLRiwayatPerawatan.setText(
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

    private void tampilSoapi() {
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Tgl.Reg</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>No.Rawat</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Status</td>"+
                    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='84%'>S.O.A.P.I.E</td>"+
                "</tr>"
            );     
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.status_lanjut "+
                    "from reg_periksa where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 5");
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.status_lanjut "+
                    "from reg_periksa where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi");
            }else if(R3.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.status_lanjut "+
                    "from reg_periksa where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? and "+
                    "reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
            }else if(R4.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.status_lanjut "+
                    "from reg_periksa where reg_periksa.stts<>'Batal' and reg_periksa.no_rkm_medis=? and reg_periksa.no_rawat=?");
            }
            try {
                if(R1.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R2.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R3.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else if(R4.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,NoRawat.getText().trim());
                }  
                rs=ps.executeQuery();
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_registrasi")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("no_rawat")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("status_lanjut")+"</td>"+
                            "<td valign='top' align='center'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0'>");
                    try {
                        rs2=koneksi.prepareStatement(
                                "select pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,"+
                                "pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.spo2,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, "+
                                "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,"+
                                "pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama,pegawai.jbtn from pemeriksaan_ralan inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where "+
                                "pemeriksaan_ralan.no_rawat='"+rs.getString("no_rawat")+"' "+
                                "order by pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat").executeQuery();
                        if(rs2.next()){
                            htmlContent.append(
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='7%'>Tanggal</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='13%'>Dokter/Paramedis</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Subjek</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='13%'>Objek</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='13%'>Asesmen</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Plan</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Instruksi</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Evaluasi</td>"+
                                    "</tr>");
                            rs2.beforeFirst();
                            while(rs2.next()){
                                 htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td align='center'>"+rs2.getString("tgl_perawatan")+"<br>"+rs2.getString("jam_rawat")+"</td>"+
                                        "<td align='center'>"+rs2.getString("nip")+"<br>"+rs2.getString("nama")+"</td>"+
                                        "<td align='left'>"+rs2.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "<td align='left'>"+rs2.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+
                                        (rs2.getString("alergi").equals("")?"":"<br>Alergi : "+rs2.getString("alergi"))+
                                        (rs2.getString("suhu_tubuh").equals("")?"":"<br>Suhu(C) : "+rs2.getString("suhu_tubuh"))+
                                        (rs2.getString("tensi").equals("")?"":"<br>Tensi : "+rs2.getString("tensi"))+
                                        (rs2.getString("nadi").equals("")?"":"<br>Nadi(/menit) : "+rs2.getString("nadi"))+
                                        (rs2.getString("respirasi").equals("")?"":"<br>Respirasi(/menit) : "+rs2.getString("respirasi"))+
                                        (rs2.getString("tinggi").equals("")?"":"<br>Tinggi(Cm) : "+rs2.getString("tinggi"))+
                                        (rs2.getString("berat").equals("")?"":"<br>Berat(Kg) : "+rs2.getString("berat"))+
                                        (rs2.getString("lingkar_perut").equals("")?"":"<br>Lingkar Perut(Cm) : "+rs2.getString("lingkar_perut"))+
                                        (rs2.getString("spo2").equals("")?"":"<br>SpO2(%) : "+rs2.getString("spo2"))+
                                        (rs2.getString("gcs").equals("")?"":"<br>GCS(E,V,M) : "+rs2.getString("gcs"))+
                                        (rs2.getString("kesadaran").equals("")?"":"<br>Kesadaran : "+rs2.getString("kesadaran"))+
                                        "</td>"+
                                        "<td align='left'>"+rs2.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "<td align='left'>"+rs2.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "<td align='left'>"+rs2.getString("instruksi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "<td align='left'>"+rs2.getString("evaluasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                    "</tr>"
                                 );
                            } 
                        }       
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    
                    try {
                        rs2=koneksi.prepareStatement(
                                "select pemeriksaan_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                "pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat,pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi, " +
                                "pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi,pemeriksaan_ranap.tinggi, " +
                                "pemeriksaan_ranap.berat,pemeriksaan_ranap.spo2,pemeriksaan_ranap.gcs,pemeriksaan_ranap.kesadaran,pemeriksaan_ranap.keluhan, " +
                                "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl,"+
                                "pemeriksaan_ranap.instruksi,pemeriksaan_ranap.evaluasi,pemeriksaan_ranap.nip,pegawai.nama,pegawai.jbtn "+
                                "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join pemeriksaan_ranap on pemeriksaan_ranap.no_rawat=reg_periksa.no_rawat "+
                                "inner join pegawai on pemeriksaan_ranap.nip=pegawai.nik where pemeriksaan_ranap.no_rawat='"+rs.getString("no_rawat")+"' "+
                                "order by pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat").executeQuery();
                        if(rs2.next()){
                            htmlContent.append(
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='7%'>Tanggal</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='13%'>Dokter/Paramedis</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Subjek</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='13%'>Objek</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='13%'>Asesmen</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Plan</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Instruksi</td>"+
                                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='14%'>Evaluasi</td>"+
                                    "</tr>");
                            rs2.beforeFirst();
                            while(rs2.next()){
                                 htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td align='center'>"+rs2.getString("tgl_perawatan")+"<br>"+rs2.getString("jam_rawat")+"</td>"+
                                        "<td align='center'>"+rs2.getString("nip")+"<br>"+rs2.getString("nama")+"</td>"+
                                        "<td align='left'>"+rs2.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "<td align='left'>"+rs2.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+
                                        (rs2.getString("alergi").equals("")?"":"<br>Alergi : "+rs2.getString("alergi"))+
                                        (rs2.getString("suhu_tubuh").equals("")?"":"<br>Suhu(C) : "+rs2.getString("suhu_tubuh"))+
                                        (rs2.getString("tensi").equals("")?"":"<br>Tensi : "+rs2.getString("tensi"))+
                                        (rs2.getString("nadi").equals("")?"":"<br>Nadi(/menit) : "+rs2.getString("nadi"))+
                                        (rs2.getString("respirasi").equals("")?"":"<br>Respirasi(/menit) : "+rs2.getString("respirasi"))+
                                        (rs2.getString("tinggi").equals("")?"":"<br>Tinggi(Cm) : "+rs2.getString("tinggi"))+
                                        (rs2.getString("berat").equals("")?"":"<br>Berat(Kg) : "+rs2.getString("berat"))+
                                        (rs2.getString("spo2").equals("")?"":"<br>SpO2(%) : "+rs2.getString("spo2"))+
                                        (rs2.getString("gcs").equals("")?"":"<br>GCS(E,V,M) : "+rs2.getString("gcs"))+
                                        (rs2.getString("kesadaran").equals("")?"":"<br>Kesadaran : "+rs2.getString("kesadaran"))+
                                        "</td>"+
                                        "<td align='left'>"+rs2.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "<td align='left'>"+rs2.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "<td align='left'>"+rs2.getString("instruksi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                        "<td align='left'>"+rs2.getString("evaluasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                    "</tr>"
                                 ); 
                            } 
                        }       
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    htmlContent.append(
                                "</table>"+
                            "</td>"+
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
            
            LoadHTMLSOAPI.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        } catch (Exception e) {
            System.out.println("laporan.DlgRL4A.prosesCari() 5 : "+e);
        } 
    }

    private void tampilPembelian() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='7%'>No.Nota</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='6%'>Tanggal</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='20%'>Petugas</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='7%'>Jenis Jual</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='15%'>Keterangan</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='19%'>Asal Barang</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='16%'>Cara Bayar</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='8%'>PPN</td>"+
                "</tr>"); 
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement("select penjualan.nota_jual, penjualan.tgl_jual, "+
                    "penjualan.nip,petugas.nama, "+
                    "penjualan.no_rkm_medis,penjualan.nm_pasien,penjualan.nama_bayar, "+
                    "penjualan.keterangan, penjualan.jns_jual, penjualan.ongkir,bangsal.nm_bangsal,penjualan.status "+
                    " from penjualan inner join petugas on penjualan.nip=petugas.nip "+
                    " inner join bangsal on penjualan.kd_bangsal=bangsal.kd_bangsal "+
                    " where penjualan.status='Sudah Dibayar' and penjualan.no_rkm_medis=? order by penjualan.tgl_jual desc limit 5");
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement("select penjualan.nota_jual, penjualan.tgl_jual, "+
                    "penjualan.nip,petugas.nama, "+
                    "penjualan.no_rkm_medis,penjualan.nm_pasien,penjualan.nama_bayar, "+
                    "penjualan.keterangan, penjualan.jns_jual, penjualan.ongkir,bangsal.nm_bangsal,penjualan.status "+
                    " from penjualan inner join petugas on penjualan.nip=petugas.nip "+
                    " inner join bangsal on penjualan.kd_bangsal=bangsal.kd_bangsal "+
                    " where penjualan.status='Sudah Dibayar' and penjualan.no_rkm_medis=? order by penjualan.tgl_jual ");
            }else if(R3.isSelected()==true){
                ps=koneksi.prepareStatement("select penjualan.nota_jual, penjualan.tgl_jual, "+
                    "penjualan.nip,petugas.nama, "+
                    "penjualan.no_rkm_medis,penjualan.nm_pasien,penjualan.nama_bayar, "+
                    "penjualan.keterangan, penjualan.jns_jual, penjualan.ongkir,bangsal.nm_bangsal,penjualan.status "+
                    " from penjualan inner join petugas on penjualan.nip=petugas.nip "+
                    " inner join bangsal on penjualan.kd_bangsal=bangsal.kd_bangsal "+
                    " where penjualan.status='Sudah Dibayar' and penjualan.no_rkm_medis=? and penjualan.tgl_jual between ? and ? "+
                    " order by penjualan.tgl_jual ");
            }else if(R4.isSelected()==true){
                ps=koneksi.prepareStatement("select penjualan.nota_jual, penjualan.tgl_jual, "+
                    "penjualan.nip,petugas.nama, "+
                    "penjualan.no_rkm_medis,penjualan.nm_pasien,penjualan.nama_bayar, "+
                    "penjualan.keterangan, penjualan.jns_jual, penjualan.ongkir,bangsal.nm_bangsal,penjualan.status "+
                    " from penjualan inner join petugas on penjualan.nip=petugas.nip "+
                    " inner join bangsal on penjualan.kd_bangsal=bangsal.kd_bangsal "+
                    " where penjualan.status='Sudah Dibayar' and penjualan.no_rkm_medis=? and penjualan.nota_jual=?");
            }
            
            try {
                if(R1.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R2.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R3.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else if(R4.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,NoRawat.getText().trim());
                }  
                rs=ps.executeQuery();
                while(rs.next()){ 
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+rs.getString("nota_jual")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_jual")+"</td>"+
                            "<td valign='top'>"+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                            "<td valign='top'>"+rs.getString("jns_jual")+"</td>"+
                            "<td valign='top'>"+rs.getString("keterangan")+"</td>"+
                            "<td valign='top'>"+rs.getString("nm_bangsal")+"</td>"+
                            "<td valign='top'>"+rs.getString("nama_bayar")+"</td>"+
                            "<td valign='top' align='right'>"+Valid.SetAngka(rs.getDouble("ongkir"))+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+
                            "<td></td>"+
                            "<td colspan='7'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='1%'>No.</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='7%'>Kode Barang</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='17%'>Nama Barang</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='4%'>Jml</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='5%'>Satuan</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='8%'>Harga(Rp)</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='9%'>Subtotal(Rp)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='3%'>Ptg(%)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='5%'>Potongan(Rp)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='5%'>Tambahan(Rp)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='5%'>Embalase(Rp)</td>"+          
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='5%'>Tuslah(Rp)</td>"+          
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Total(Rp)</td>"+          
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='11%'>Aturan Pakai</td>"+       
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='5%'>No.Batch</td>"+                                        
                                    "</tr>");
                    ps2=koneksi.prepareStatement(
                            "select detailjual.kode_brng,databarang.nama_brng, detailjual.kode_sat,"+
                            " kodesatuan.satuan,detailjual.h_jual, detailjual.jumlah, "+
                            " detailjual.subtotal, detailjual.dis, detailjual.bsr_dis,"+
                            " detailjual.tambahan,detailjual.embalase,detailjual.tuslah,"+
                            " detailjual.aturan_pakai,detailjual.total,detailjual.no_batch from "+
                            " detailjual inner join databarang inner join kodesatuan inner join jenis "+
                            " on detailjual.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "+
                            " and detailjual.kode_sat=kodesatuan.kode_sat and "+
                            " detailjual.kode_brng not in(select kode_brng from detail_obat_racikan_jual where nota_jual='"+rs.getString("nota_jual")+"' group by kode_brng) where "+
                            " detailjual.nota_jual='"+rs.getString("nota_jual")+"' order by detailjual.kode_brng");
                    try {
                        i=1;
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='top' align='center'>"+i+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("kode_brng")+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("nama_brng")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("jumlah")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("satuan")+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("h_jual"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("subtotal"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("dis"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("bsr_dis"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("tambahan"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("embalase"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("tuslah"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("total"))+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("aturan_pakai")+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("no_batch")+"</td>"+
                                "</tr>");
                            i++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select obat_racikan_jual.no_racik,obat_racikan_jual.nama_racik,"+
                            "obat_racikan_jual.kd_racik,metode_racik.nm_racik as metode,"+
                            "obat_racikan_jual.jml_dr,obat_racikan_jual.aturan_pakai,"+
                            "obat_racikan_jual.keterangan from obat_racikan_jual inner join metode_racik "+
                            "on obat_racikan_jual.kd_racik=metode_racik.kd_racik where "+
                            "obat_racikan_jual.nota_jual=? order by obat_racikan_jual.no_racik");
                    try{
                        ps2.setString(1,rs.getString("nota_jual"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='top' align='center'>"+i+"</td>"+
                                    "<td valign='top' align='left' colspan='6'>"+rs2.getString("no_racik")+". "+rs2.getString("nama_racik")+"</td>"+
                                    "<td valign='top' align='left' colspan='3'>"+rs2.getString("jml_dr")+" "+rs2.getString("metode")+"</td>"+
                                    "<td valign='top' align='left' colspan='3'>"+rs2.getString("keterangan")+"</td>"+
                                    "<td valign='top' align='left' colspan='2'>"+rs2.getString("aturan_pakai")+"</td>"+
                                "</tr>");
                            try {
                                rs3=koneksi.prepareStatement("select detailjual.kode_brng,databarang.nama_brng, detailjual.kode_sat,"+
                                    " kodesatuan.satuan,detailjual.h_jual, detailjual.jumlah, "+
                                    " detailjual.subtotal, detailjual.dis, detailjual.bsr_dis,"+
                                    " detailjual.tambahan,detailjual.embalase,detailjual.tuslah,"+
                                    " detailjual.aturan_pakai,detailjual.total,detailjual.no_batch from "+
                                    " detailjual inner join databarang inner join kodesatuan inner join jenis inner join detail_obat_racikan_jual "+
                                    " on detailjual.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "+
                                    " and detailjual.kode_sat=kodesatuan.kode_sat and detailjual.kode_brng=detail_obat_racikan_jual.kode_brng "+
                                    " and detailjual.nota_jual=detail_obat_racikan_jual.nota_jual where "+
                                    " detail_obat_racikan_jual.no_racik='"+rs2.getString("no_racik")+"' and detailjual.nota_jual='"+rs.getString("nota_jual")+"' order by detailjual.kode_brng").executeQuery();
                                while(rs3.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='top' align='center'></td>"+
                                            "<td valign='top' align='left'>"+rs3.getString("kode_brng")+"</td>"+
                                            "<td valign='top' align='left'>"+rs3.getString("nama_brng")+"</td>"+
                                            "<td valign='top' align='center'>"+rs3.getString("jumlah")+"</td>"+
                                            "<td valign='top' align='center'>"+rs3.getString("satuan")+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("h_jual"))+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("subtotal"))+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("dis"))+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("bsr_dis"))+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("tambahan"))+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("embalase"))+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("tuslah"))+"</td>"+
                                            "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                            "<td valign='top' align='left'>"+rs3.getString("aturan_pakai")+"</td>"+
                                            "<td valign='top' align='left'>"+rs3.getString("no_batch")+"</td>"+
                                        "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : "+e);
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
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LoadHTMLPembelian.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        }catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
    }

    private void tampilPiutang() {
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='isi'>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='11%'>No.Nota</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='10%'>Tanggal</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='24%'>Petugas</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='11%'>Jenis</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='19%'>Catatan</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='23%'>Asal Barang</td>"+
                "</tr>"); 
            if(R1.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select piutang.nota_piutang, piutang.tgl_piutang, "+
                    "piutang.nip,petugas.nama, piutang.no_rkm_medis,piutang.nm_pasien,piutang.jns_jual,"+
                    "bangsal.nm_bangsal,piutang.catatan from piutang inner join petugas on piutang.nip=petugas.nip "+
                    " inner join bangsal on piutang.kd_bangsal=bangsal.kd_bangsal where piutang.no_rkm_medis=? "+
                    " order by piutang.tgl_piutang desc limit 5");
            }else if(R2.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select piutang.nota_piutang, piutang.tgl_piutang, "+
                    "piutang.nip,petugas.nama, piutang.no_rkm_medis,piutang.nm_pasien,piutang.jns_jual,"+
                    "bangsal.nm_bangsal,piutang.catatan from piutang inner join petugas on piutang.nip=petugas.nip "+
                    " inner join bangsal on piutang.kd_bangsal=bangsal.kd_bangsal where piutang.no_rkm_medis=? "+
                    " order by piutang.tgl_piutang order by penjualan.tgl_jual ");
            }else if(R3.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select piutang.nota_piutang, piutang.tgl_piutang, "+
                    "piutang.nip,petugas.nama, piutang.no_rkm_medis,piutang.nm_pasien,piutang.jns_jual,"+
                    "bangsal.nm_bangsal,piutang.catatan from piutang inner join petugas on piutang.nip=petugas.nip "+
                    " inner join bangsal on piutang.kd_bangsal=bangsal.kd_bangsal where piutang.no_rkm_medis=? "+
                    " and piutang.tgl_piutang between ? and ? order by piutang.tgl_piutang ");
            }else if(R4.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select piutang.nota_piutang, piutang.tgl_piutang, "+
                    "piutang.nip,petugas.nama, piutang.no_rkm_medis,piutang.nm_pasien,piutang.jns_jual,"+
                    "bangsal.nm_bangsal,piutang.catatan from piutang inner join petugas on piutang.nip=petugas.nip "+
                    " inner join bangsal on piutang.kd_bangsal=bangsal.kd_bangsal where piutang.no_rkm_medis=? "+
                    " and piutang.nota_piutang=? ");
            }
            
            try {
                if(R1.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R2.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                }else if(R3.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else if(R4.isSelected()==true){
                    ps.setString(1,NoRM.getText().trim());
                    ps.setString(2,NoRawat.getText().trim());
                } 
                rs=ps.executeQuery();
                while(rs.next()){ 
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+rs.getString("nota_piutang")+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("tgl_piutang")+"</td>"+
                            "<td valign='top'>"+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                            "<td valign='top'>"+rs.getString("jns_jual")+"</td>"+
                            "<td valign='top'>"+rs.getString("catatan")+"</td>"+
                            "<td valign='top'>"+rs.getString("nm_bangsal")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+
                            "<td></td>"+
                            "<td colspan='5'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='1%'>No.</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='7%'>Kode Barang</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='31%'>Nama Barang</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='4%'>Jml</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='5%'>Satuan</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Harga(Rp)</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='11%'>Subtotal(Rp)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='4%'>Ptg(%)</td>"+    
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='7%'>Potongan(Rp)</td>"+        
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='12%'>Total(Rp)</td>"+          
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='8%'>No.Batch</td>"+                                        
                                    "</tr>");
                    ps2=koneksi.prepareStatement("select detailpiutang.kode_brng,databarang.nama_brng, detailpiutang.kode_sat,"+
                            " kodesatuan.satuan,detailpiutang.h_jual, detailpiutang.jumlah, "+
                            " detailpiutang.subtotal, detailpiutang.dis, detailpiutang.bsr_dis,"+
                            " detailpiutang.total,detailpiutang.no_batch from "+
                            " detailpiutang inner join databarang inner join kodesatuan inner join jenis "+
                            " on detailpiutang.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "+
                            " and detailpiutang.kode_sat=kodesatuan.kode_sat "+
                            " where detailpiutang.nota_piutang='"+rs.getString("nota_piutang")+"' order by detailpiutang.kode_brng");
                    try {
                        i=1;
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='top' align='center'>"+i+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("kode_brng")+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("nama_brng")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("jumlah")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("satuan")+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("h_jual"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("subtotal"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("dis"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("bsr_dis"))+"</td>"+
                                    "<td valign='top' align='right'>"+Valid.SetAngka(rs2.getDouble("total"))+"</td>"+
                                    "<td valign='top' align='left'>"+rs2.getString("no_batch")+"</td>"+
                                "</tr>");
                            i++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
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
                }
            } catch (Exception e) {
                System.out.println("Notif 2 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LoadHTMLPiutang.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
        }catch (Exception e) {
            System.out.println("Notif 1 : "+e);
        } 
    }

    private void tampilRetensi() {
        try{
            htmlContent = new StringBuilder();
            try{
                rs3=koneksi.prepareStatement(
                     "select * from retensi_pasien where no_rkm_medis='"+NoRM.getText().trim()+"' order by tgl_retensi").executeQuery();
                if(rs3.next()){                                    
                    htmlContent.append(  
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                        "<tr class='isi'>"+
                          "<td valign='top' width='2%' align='center' bgcolor='#FFFAF8'>No.</td>"+
                          "<td valign='top' width='8%' align='center' bgcolor='#FFFAF8'>Tgl.Retensi</td>"+
                          "<td valign='top' width='90%' align='center' bgcolor='#FFFAF8'>File Retensi</td>"+
                        "</tr>");
                    rs3.beforeFirst();
                    w=1;
                    while(rs3.next()){
                        htmlContent.append(
                             "<tr class='isi'>"+
                                "<td valign='top' align='center'>"+w+"</td>"+
                                "<td valign='top'>"+rs3.getString("tgl_retensi")+"</td>"+
                                "<td valign='top' align='center'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/medrec/"+rs3.getString("lokasi_pdf")+"'><img alt='Gambar Retensi' src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/medrec/"+rs3.getString("lokasi_pdf")+"' width='"+(TabRawat.getWidth()-550)+"' height='"+(TabRawat.getWidth()-550)+"'/></a></td>"+
                             "</tr>"); 
                        w++;
                    }
                    htmlContent.append(
                      "</table>");
                } else{
                    htmlContent.append(  
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                        "<tr class='isi'>"+
                          "<td valign='top' width='2%' align='center' bgcolor='#FFFAF8'>No.</td>"+
                          "<td valign='top' width='8%' align='center' bgcolor='#FFFAF8'>Tgl.Retensi</td>"+
                          "<td valign='top' width='90%' align='center' bgcolor='#FFFAF8'>File Retensi</td>"+
                        "</tr>"+
                      "</table>");
                }                              
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs3!=null){
                    rs3.close();
                }
            }
            LoadHTMLRetensi.setText("<html>"+htmlContent.toString()+"</html>");
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void panggilLaporan(String teks) {
        try{
            File g = new File("file.css");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}.isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;border: white;}");
            bg.close();

            File f = new File("riwayat.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(
                 teks.replaceAll("<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />").
                      replaceAll("<body>",
                                 "<body>"+
                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>No.RM</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+NoRM.getText().trim()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Nama Pasien</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+NmPasien.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Alamat</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+Alamat.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Jenis Kelamin</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+Jk.getText().replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+TempatLahir.getText()+" "+TanggalLahir.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Ibu Kandung</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+IbuKandung.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Golongan Darah</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+GD.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Status Nikah</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+StatusNikah.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Agama</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+Agama.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+Pendidikan.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Bahasa Dipakai</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+Bahasa.getText()+"</td>"+
                                       "</tr>"+
                                       "<tr class='isi'>"+ 
                                         "<td valign='top' width='20%'>Cacat Fisik</td>"+
                                         "<td valign='top' width='1%' align='center'>:</td>"+
                                         "<td valign='top' width='79%'>"+CacatFisik.getText()+"</td>"+
                                       "</tr>"+
                                    "</table>"            
                      )
            );  
            bw.close();
            Desktop.getDesktop().browse(f.toURI());
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }   
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

    private void menampilkanTriaseIGD(String norawat) {
        try {
            if(chkTriase.isSelected()==true){
                //triase primer
                try {
                    rs2=koneksi.prepareStatement(
                            "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                            "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.nik,data_triase_igd.tekanan_darah,"+
                            "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                            "data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                            "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus,pegawai.nama "+
                            "from data_triase_igdprimer inner join data_triase_igd on data_triase_igd.no_rawat=data_triase_igdprimer.no_rawat "+
                            "inner join master_triase_macam_kasus on data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus "+
                            "inner join pegawai on data_triase_igdprimer.nik=data_triase_igdprimer.nik where data_triase_igd.no_rawat='"+norawat+"'").executeQuery();
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
                                "where data_triase_igddetail_skala1.no_rawat='"+norawat+"' "+
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
                                            "master_triase_skala1.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala1.no_rawat='"+norawat+"' "+
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
                                "inner join data_triase_igddetail_skala2 on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat='"+norawat+"' "+
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
                                            "master_triase_skala2.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala2.no_rawat='"+norawat+"' "+
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
                                    "<td valign='middle'>Dokter/Petugas IGD</td>"+
                                    "<td valign='middle'>"+rs2.getString("nik")+" "+rs2.getString("nama")+"</td>"+
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
                            "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nik,data_triase_igd.tekanan_darah,"+
                            "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                            "data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                            "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus,pegawai.nama "+
                            "from data_triase_igdsekunder inner join data_triase_igd on data_triase_igd.no_rawat=data_triase_igdsekunder.no_rawat "+
                            "inner join master_triase_macam_kasus on data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus "+
                            "inner join pegawai on data_triase_igdsekunder.nik=pegawai.nik where data_triase_igd.no_rawat='"+norawat+"'").executeQuery();
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
                                "inner join data_triase_igddetail_skala3 on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat='"+norawat+"' "+
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
                                            "master_triase_skala3.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala3.no_rawat='"+norawat+"' "+
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
                                "inner join data_triase_igddetail_skala4 on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat='"+norawat+"' "+
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
                                            "master_triase_skala4.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala4.no_rawat='"+norawat+"' "+
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
                                "inner join data_triase_igddetail_skala5 on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat='"+norawat+"' "+
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
                                            "master_triase_skala5.kode_pemeriksaan='"+rs3.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala5.no_rawat='"+norawat+"' "+
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
                                    "<td valign='middle'>Dokter/Petugas IGD</td>"+
                                    "<td valign='middle'>"+rs2.getString("nik")+" "+rs2.getString("nama")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Triase IGD : "+e);
        }
    }

    private void menampilkanAsuhanKeperawatanIGD(String norawat) {
        try {
            if(chkAsuhanKeperawatanIGD.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                        "select penilaian_awal_keperawatan_igd.tanggal,penilaian_awal_keperawatan_igd.informasi,"+
                        "penilaian_awal_keperawatan_igd.keluhan_utama,penilaian_awal_keperawatan_igd.rpd,penilaian_awal_keperawatan_igd.rpo,penilaian_awal_keperawatan_igd.status_kehamilan,penilaian_awal_keperawatan_igd.gravida,penilaian_awal_keperawatan_igd.para,"+
                        "penilaian_awal_keperawatan_igd.abortus,penilaian_awal_keperawatan_igd.hpht,penilaian_awal_keperawatan_igd.tekanan,penilaian_awal_keperawatan_igd.pupil,penilaian_awal_keperawatan_igd.neurosensorik,penilaian_awal_keperawatan_igd.integumen,penilaian_awal_keperawatan_igd.turgor,"+ 
                        "penilaian_awal_keperawatan_igd.edema,penilaian_awal_keperawatan_igd.mukosa,penilaian_awal_keperawatan_igd.perdarahan,penilaian_awal_keperawatan_igd.jumlah_perdarahan,penilaian_awal_keperawatan_igd.warna_perdarahan,penilaian_awal_keperawatan_igd.intoksikasi,"+
                        "penilaian_awal_keperawatan_igd.bab,penilaian_awal_keperawatan_igd.xbab,penilaian_awal_keperawatan_igd.kbab,penilaian_awal_keperawatan_igd.wbab,penilaian_awal_keperawatan_igd.bak,penilaian_awal_keperawatan_igd.xbak,penilaian_awal_keperawatan_igd.wbak,"+
                        "penilaian_awal_keperawatan_igd.lbak,penilaian_awal_keperawatan_igd.psikologis,penilaian_awal_keperawatan_igd.jiwa,penilaian_awal_keperawatan_igd.perilaku,penilaian_awal_keperawatan_igd.dilaporkan,penilaian_awal_keperawatan_igd.sebutkan,penilaian_awal_keperawatan_igd.hubungan,"+ 
                        "penilaian_awal_keperawatan_igd.tinggal_dengan,penilaian_awal_keperawatan_igd.ket_tinggal,penilaian_awal_keperawatan_igd.budaya,penilaian_awal_keperawatan_igd.ket_budaya,penilaian_awal_keperawatan_igd.pendidikan_pj,penilaian_awal_keperawatan_igd.ket_pendidikan_pj,"+  
                        "penilaian_awal_keperawatan_igd.edukasi,penilaian_awal_keperawatan_igd.ket_edukasi,penilaian_awal_keperawatan_igd.kemampuan,penilaian_awal_keperawatan_igd.aktifitas,penilaian_awal_keperawatan_igd.alat_bantu,penilaian_awal_keperawatan_igd.ket_bantu,"+
                        "penilaian_awal_keperawatan_igd.nyeri,penilaian_awal_keperawatan_igd.provokes,penilaian_awal_keperawatan_igd.ket_provokes,penilaian_awal_keperawatan_igd.quality,penilaian_awal_keperawatan_igd.ket_quality,penilaian_awal_keperawatan_igd.lokasi,penilaian_awal_keperawatan_igd.menyebar,"+
                        "penilaian_awal_keperawatan_igd.skala_nyeri,penilaian_awal_keperawatan_igd.durasi,penilaian_awal_keperawatan_igd.nyeri_hilang,penilaian_awal_keperawatan_igd.ket_nyeri,penilaian_awal_keperawatan_igd.pada_dokter,penilaian_awal_keperawatan_igd.ket_dokter,"+
                        "penilaian_awal_keperawatan_igd.berjalan_a,penilaian_awal_keperawatan_igd.berjalan_b,penilaian_awal_keperawatan_igd.berjalan_c,penilaian_awal_keperawatan_igd.hasil,penilaian_awal_keperawatan_igd.lapor,penilaian_awal_keperawatan_igd.ket_lapor,"+
                        "penilaian_awal_keperawatan_igd.rencana,penilaian_awal_keperawatan_igd.nip,petugas.nama "+
                        "from penilaian_awal_keperawatan_igd inner join petugas on penilaian_awal_keperawatan_igd.nip=petugas.nip where "+
                        "penilaian_awal_keperawatan_igd.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Keperawatan IGD</td>"+
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
                                       "I. RIWAYAT KESEHATAN PASIEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%'>Riwayat Penyakit Sekarang : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Status Kehamilan : "+rs2.getString("status_kehamilan")+(rs2.getString("para").equals("")?"":", Para : "+rs2.getString("para"))+(rs2.getString("abortus").equals("")?"":", Abortus : "+rs2.getString("abortus"))+(rs2.getString("gravida").equals("")?"":", Gravida : "+rs2.getString("gravida"))+(rs2.getString("hpht").equals("")?"":", HPHT : "+rs2.getString("hpht"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>Tekanan Intrakranial : "+rs2.getString("tekanan")+"</td>"+
                                              "<td width='33%' border='0'>Pupil : "+rs2.getString("pupil")+"</td>"+
                                              "<td width='33%' border='0'>Neurosensorik / Muskuloskeletal : "+rs2.getString("neurosensorik")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>Integumen : "+rs2.getString("integumen")+"</td>"+
                                              "<td width='33%' border='0'>Turgor Kulit : "+rs2.getString("turgor")+"</td>"+
                                              "<td width='33%' border='0'>Edema : "+rs2.getString("edema")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>Mukosa Mulut : "+rs2.getString("mukosa")+"</td>"+
                                              "<td width='33%' border='0'>Perdarahan : "+rs2.getString("perdarahan")+(rs2.getString("jumlah_perdarahan").equals("")?"":", Jumlah : "+rs2.getString("jumlah_perdarahan").equals("")+" cc")+(rs2.getString("warna_perdarahan").equals("")?"":", Warna : "+rs2.getString("warna_perdarahan").equals(""))+"</td>"+
                                              "<td width='33%' border='0'>Intoksikasi : "+rs2.getString("intoksikasi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%' border='0' colspan='3'>Eliminasi :</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%' border='0' colspan='3'>"+
                                                   "&nbsp;&nbsp;&nbsp;&nbsp;BAB : Frekuensi : "+rs2.getString("bab")+" X/ "+rs2.getString("xbab")+(rs2.getString("kbab").equals("")?"":", Konsistensi : "+rs2.getString("kbab").equals(""))+(rs2.getString("kbab").equals("")?"":", Warna : "+rs2.getString("wbab").equals(""))+"<br>"+
                                                   "&nbsp;&nbsp;&nbsp;&nbsp;BAK : Frekuensi : "+rs2.getString("bak")+" X/ "+rs2.getString("xbak")+(rs2.getString("wbak").equals("")?"":", Warna : "+rs2.getString("wbak").equals(""))+(rs2.getString("lbak").equals("")?"":", Lain-lain : "+rs2.getString("lbak").equals(""))+
                                              "</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. RIWAYAT PSIKOLOGIS - SOSIAL - EKONOMI - BUDAYA - SPIRITUAL"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Kondisi Psikologis</td>"+
                                              "<td width='50%' border='0'>: "+rs2.getString("psikologis")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Gangguan Jiwa Di Masa Lalu</td>"+
                                              "<td width='50%' border='0'>: "+rs2.getString("jiwa")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Adakah Perilaku</td>"+
                                              "<td width='50%' border='0'>: "+rs2.getString("perilaku")+(rs2.getString("dilaporkan").equals("")?"":", Dilaporkan Ke : "+rs2.getString("dilaporkan").equals(""))+(rs2.getString("sebutkan").equals("")?"":", Sebutkan : "+rs2.getString("sebutkan").equals(""))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Hubungan Pasien Dengan Anggota Keluarga</td>"+
                                              "<td width='50%' border='0'>: "+rs2.getString("hubungan")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Tinggal Dengan</td>"+
                                              "<td width='50%' border='0'>: "+rs2.getString("tinggal_dengan")+(rs2.getString("ket_tinggal").equals("")?"":", "+rs2.getString("ket_tinggal").equals(""))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Kepercayaan / Budaya / Nilai-nilai Khusus Yang Perlu Diperhatikan</td>"+
                                              "<td width='50%' border='0'>: "+rs2.getString("budaya")+(rs2.getString("ket_budaya").equals("")?"":", "+rs2.getString("ket_budaya").equals(""))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Pendidikan Penanggung Jawab</td>"+
                                              "<td width='50%' border='0'>: "+rs2.getString("pendidikan_pj")+(rs2.getString("ket_pendidikan_pj").equals("")?"":", "+rs2.getString("ket_pendidikan_pj").equals(""))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Edukasi Diberikan Kepada</td>"+
                                              "<td width='50%' border='0'>: "+rs2.getString("edukasi")+(rs2.getString("ket_edukasi").equals("")?"":", "+rs2.getString("ket_edukasi").equals(""))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PENGKAJIAN FUNGSI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='42%' border='0'>Kemampuan Aktifitas Sehari-hari : "+rs2.getString("kemampuan")+"</td>"+
                                              "<td width='20%' border='0'>Aktifitas : "+rs2.getString("aktifitas")+"</td>"+
                                              "<td width='38%' border='0'>Alat Bantu : "+rs2.getString("alat_bantu")+(rs2.getString("ket_bantu").equals("")?"":", "+rs2.getString("ket_bantu"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. SKALA NYERI"+  
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
                                       "VI. PENILAIAN RESIKO JATUH (GET UP AND GO)"+  
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
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>MASALAH KEPERAWATAN :</td>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>RENCANA KEPERAWATAN :</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td valign='top'>");
                            try {
                                rs3=koneksi.prepareStatement(
                                    "select master_masalah_keperawatan_igd.nama_masalah from master_masalah_keperawatan_igd "+
                                    "inner join penilaian_awal_keperawatan_igd_masalah on penilaian_awal_keperawatan_igd_masalah.kode_masalah=master_masalah_keperawatan_igd.kode_masalah "+
                                    "where penilaian_awal_keperawatan_igd_masalah.no_rawat='"+norawat+"' order by penilaian_awal_keperawatan_igd_masalah.kode_masalah").executeQuery();
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
                                               "<td valign='top'>"+rs2.getString("rencana")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Keperawatan IGD : "+e);
        }
    }

    private void menampilkanAsuhanKeperawatanRalan(String norawat) {
        try{
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
                        "penilaian_awal_keperawatan_ralan.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Keperawatan Rawat Jalan Umum</td>"+
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
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Pengobatan : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
                                               "<td valign='top'>");
                            try {
                                rs3=koneksi.prepareStatement(
                                    "select master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                                    "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                                    "where penilaian_awal_keperawatan_ralan_masalah.no_rawat='"+norawat+"' order by penilaian_awal_keperawatan_ralan_masalah.kode_masalah").executeQuery();
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
                                               "<td valign='top'>");
                            try {
                                rs3=koneksi.prepareStatement(
                                    "select master_rencana_keperawatan.rencana_keperawatan from master_rencana_keperawatan "+
                                    "inner join penilaian_awal_keperawatan_ralan_rencana on penilaian_awal_keperawatan_ralan_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
                                    "where penilaian_awal_keperawatan_ralan_rencana.no_rawat='"+norawat+"' order by penilaian_awal_keperawatan_ralan_rencana.kode_rencana").executeQuery();
                                while(rs3.next()){
                                    htmlContent.append(rs3.getString("rencana_keperawatan")+"<br>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            htmlContent.append(rs2.getString("rencana")+
                                            "</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Keperawatan Ralan : "+e);
        }
    }

    private void menampilkanAsuhanKeperawatanRalanGigi(String norawat) {
        try{
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
                            "where penilaian_awal_keperawatan_gigi.no_rawat='"+norawat+"'").executeQuery();
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
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
                                               "<td valign='top'>");
                            try {
                                rs3=koneksi.prepareStatement(
                                    "select master_masalah_keperawatan_gigi.nama_masalah from master_masalah_keperawatan_gigi "+
                                    "inner join penilaian_awal_keperawatan_gigi_masalah on penilaian_awal_keperawatan_gigi_masalah.kode_masalah=master_masalah_keperawatan_gigi.kode_masalah "+
                                    "where penilaian_awal_keperawatan_gigi_masalah.no_rawat='"+norawat+"' order by penilaian_awal_keperawatan_gigi_masalah.kode_masalah").executeQuery();
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
                                               "<td valign='top'>"+rs2.getString("rencana")+"</td>"+
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
        }catch (Exception e) {
            System.out.println("Notif Asuhan Keperawatan Ralan Gigi : "+e);
        }
    }

    private void menampilkanAsuhanKeperawatanRalanBayi(String norawat) {
        try {
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
                            "where penilaian_awal_keperawatan_ralan_bayi.no_rawat='"+norawat+"'").executeQuery();
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
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Pengobatan : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
                                    "where riwayat_imunisasi.no_rkm_medis='"+NoRM.getText().trim()+"' group by master_imunisasi.kode_imunisasi order by master_imunisasi.kode_imunisasi").executeQuery();
                                while(rs3.next()){
                                    ke1="";ke2="";ke3="";ke4="";ke5="";ke6="";
                                    try {
                                        rs4=koneksi.prepareStatement("select * from riwayat_imunisasi where no_rkm_medis='"+NoRM.getText().trim()+"' and kode_imunisasi='"+rs3.getString("kode_imunisasi")+"'").executeQuery();
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
                                               "<td valign='top'>");
                            try {
                                rs3=koneksi.prepareStatement(
                                    "select master_masalah_keperawatan_anak.nama_masalah from master_masalah_keperawatan_anak "+
                                    "inner join penilaian_awal_keperawatan_ralan_bayi_masalah on penilaian_awal_keperawatan_ralan_bayi_masalah.kode_masalah=master_masalah_keperawatan_anak.kode_masalah "+
                                    "where penilaian_awal_keperawatan_ralan_bayi_masalah.no_rawat='"+norawat+"' order by penilaian_awal_keperawatan_ralan_bayi_masalah.kode_masalah").executeQuery();
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
                                               "<td valign='top'>"+rs2.getString("rencana")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Keperawatan Ralan Bayi : "+e);
        }
    }

    private void menampilkanAsuhanKeperawatanRalanKandungan(String norawat) {
        try {
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
                            "where penilaian_awal_keperawatan_kebidanan.no_rawat='"+norawat+"'").executeQuery();
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
                                              "<td width='80%'>Umur Menarche : "+rs2.getString("umur")+" tahun, lamanya : "+rs2.getString("lama")+" hari, banyaknya : "+rs2.getString("banyaknya")+" pembalut&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Haid Terakhir : "+rs2.getString("haid")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Siklus : "+rs2.getString("siklus")+" hari, ( "+rs2.getString("ket_siklus")+" ), "+rs2.getString("ket_siklus1")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td width='20%'>Riwayat Perkawinan</td>"+
                                              "<td width='80%'>Status Menikah : "+rs2.getString("status")+" "+rs2.getString("kali")+" kali&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Usia Perkawinan 1 : "+rs2.getString("usia1")+" tahun, Status : "+rs2.getString("ket1")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Usia Perkawinan 2 : "+rs2.getString("usia2")+" tahun, Status : "+rs2.getString("ket2")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Usia Perkawinan 3 : "+rs2.getString("usia3")+" tahun, Status : "+rs2.getString("ket3")+"</td>"+
                                          "</tr>"+
                                          "<tr class='isi'>"+
                                              "<td width='20%'>Riwayat Kehamilan Tetap</td>"+
                                              "<td width='80%'>HPHT : "+rs2.getString("hpht")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Usia Kehamilan : "+rs2.getString("usia_kehamilan")+" bln/mgg&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TP : "+rs2.getString("tp")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Riwayat Imunisasi : "+rs2.getString("imunisasi")+(rs2.getString("ket_imunisasi").equals("")?"":", "+rs2.getString("ket_imunisasi")+" kali")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;G : "+rs2.getString("g")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P : "+rs2.getString("p")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A : "+rs2.getString("a")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hidup : "+rs2.getString("hidup")+"</td>"+
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
                                    "select * from riwayat_persalinan_pasien where riwayat_persalinan_pasien.no_rkm_medis='"+NoRM.getText().trim()+"' order by riwayat_persalinan_pasien.tgl_thn").executeQuery();
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Keperawatan Ralan Kandungan : "+e);
        }
    }

    private void menampilkanAsuhanFisioterapi(String norawat) {
        try {
            if(chkAsuhanFisioterapi.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_fisioterapi.tanggal,penilaian_fisioterapi.informasi,penilaian_fisioterapi.keluhan_utama,penilaian_fisioterapi.rps,"+
                            "penilaian_fisioterapi.rpd,penilaian_fisioterapi.td,penilaian_fisioterapi.hr,penilaian_fisioterapi.rr,penilaian_fisioterapi.suhu,"+
                            "penilaian_fisioterapi.nyeri_tekan,penilaian_fisioterapi.nyeri_gerak,"+
                            "penilaian_fisioterapi.nyeri_diam,penilaian_fisioterapi.palpasi,penilaian_fisioterapi.luas_gerak_sendi,penilaian_fisioterapi.kekuatan_otot,penilaian_fisioterapi.statis,penilaian_fisioterapi.dinamis,penilaian_fisioterapi.kognitif,"+
                            "penilaian_fisioterapi.auskultasi,penilaian_fisioterapi.alat_bantu,penilaian_fisioterapi.ket_bantu,penilaian_fisioterapi.prothesa,penilaian_fisioterapi.ket_pro,penilaian_fisioterapi.deformitas,penilaian_fisioterapi.ket_deformitas,"+
                            "penilaian_fisioterapi.resikojatuh,penilaian_fisioterapi.ket_resikojatuh,penilaian_fisioterapi.adl,penilaian_fisioterapi.lainlain_fungsional,penilaian_fisioterapi.ket_fisik,penilaian_fisioterapi.pemeriksaan_musculoskeletal,"+
                            "penilaian_fisioterapi.pemeriksaan_neuromuscular,penilaian_fisioterapi.pemeriksaan_cardiopulmonal,penilaian_fisioterapi.pemeriksaan_integument,penilaian_fisioterapi.pengukuran_musculoskeletal,penilaian_fisioterapi.pengukuran_neuromuscular,"+
                            "penilaian_fisioterapi.pengukuran_cardiopulmonal,penilaian_fisioterapi.pengukuran_integument,penilaian_fisioterapi.penunjang,penilaian_fisioterapi.diagnosis_fisio,penilaian_fisioterapi.rencana_terapi,penilaian_fisioterapi.nip,petugas.nama "+
                            "from penilaian_fisioterapi inner join petugas on penilaian_fisioterapi.nip=petugas.nip "+
                            "where penilaian_fisioterapi.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Fisioterapi</td>"+
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
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu & Penyerta : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%'>HR : "+rs2.getString("hr")+" x/menit</td>"+
                                               "<td width='25%'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                               "<td width='25%'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                          "</tr>"+
                                       "</table>"+
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='33%'>Nyeri Tekan : "+rs2.getString("nyeri_tekan")+"</td>"+
                                               "<td width='33%'>Nyeri Gerak : "+rs2.getString("nyeri_gerak")+"</td>"+
                                               "<td width='33%'>Nyeri Diam : "+rs2.getString("nyeri_diam")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%'>Palpasi : "+rs2.getString("palpasi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%'>Luas Gerak Sendi : "+rs2.getString("luas_gerak_sendi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%'>Kekuatan Otot (MMT) : "+rs2.getString("kekuatan_otot")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%'>"+
                                                   "Inspeksi :"+
                                                   "<table width='98%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                        "<tr>"+
                                                             "<td width='50%' border='0' valign='top'>Statis : "+rs2.getString("statis")+"</td>"+
                                                             "<td width='50%' border='0' valign='top'>Dinamis : "+rs2.getString("dinamis")+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='50%' border='0' valign='top'>Kognitif : "+rs2.getString("kognitif")+"</td>"+
                                                             "<td width='50%' border='0' valign='top'>Auskultasi : "+rs2.getString("auskultasi")+"</td>"+
                                                        "</tr>"+
                                                   "</table>"+
                                               "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%'>"+
                                                   "Kemampuan Fungsional :"+
                                                   "<table width='98%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                        "<tr>"+
                                                             "<td width='50%' border='0' valign='top'>Alat Bantu : "+rs2.getString("alat_bantu")+(rs2.getString("ket_bantu").equals("")?"":", "+rs2.getString("ket_bantu"))+"</td>"+
                                                             "<td width='50%' border='0' valign='top'>Prothesa : "+rs2.getString("prothesa")+(rs2.getString("ket_pro").equals("")?"":", "+rs2.getString("ket_pro"))+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='50%' border='0' valign='top'>Deformitas : "+rs2.getString("deformitas")+(rs2.getString("ket_deformitas").equals("")?"":", "+rs2.getString("ket_deformitas"))+"</td>"+
                                                             "<td width='50%' border='0' valign='top'>Resiko Jatuh : "+rs2.getString("resikojatuh")+(rs2.getString("ket_resikojatuh").equals("")?"":", "+rs2.getString("ket_resikojatuh"))+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='50%' border='0' valign='top'>Aktivitas Kehidupan Sehari-hari ( ADL ) : "+rs2.getString("adl")+"</td>"+
                                                             "<td width='50%' border='0' valign='top'>Lain-lain : "+rs2.getString("lainlain_fungsional")+"</td>"+
                                                        "</tr>"+
                                                   "</table>"+
                                               "</td>"+
                                          "</tr>"+ 
                                          "<tr>"+
                                               "<td width='100%'>Keterangan Fisik : "+rs2.getString("ket_fisik").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%'>"+
                                                   "Pemeriksaan Sistemik Khusus  :"+
                                                   "<table width='98%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                        "<tr>"+
                                                             "<td width='100%' border='0' valign='top'>a. Musculoskeletal : "+rs2.getString("pemeriksaan_musculoskeletal").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='100%' border='0' valign='top'>b. Neuromuscular : "+rs2.getString("pemeriksaan_neuromuscular").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='100%' border='0' valign='top'>c. CardioPulmonal : "+rs2.getString("pemeriksaan_cardiopulmonal").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='100%' border='0' valign='top'>d. Integument : "+rs2.getString("pemeriksaan_integument").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                        "</tr>"+
                                                   "</table>"+
                                               "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%'>"+
                                                   "Pengukuran Khusus :"+
                                                   "<table width='98%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                        "<tr>"+
                                                             "<td width='100%' border='0' valign='top'>a. Musculoskeletal : "+rs2.getString("pengukuran_musculoskeletal").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='100%' border='0' valign='top'>b. Neuromuscular : "+rs2.getString("pengukuran_neuromuscular").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='100%' border='0' valign='top'>c. CardioPulmonal : "+rs2.getString("pengukuran_cardiopulmonal").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                        "</tr>"+
                                                        "<tr>"+
                                                             "<td width='100%' border='0' valign='top'>d. Integument : "+rs2.getString("pengukuran_integument").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                        "</tr>"+
                                                   "</table>"+
                                               "</td>"+
                                          "</tr>"+  
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>"+rs2.getString("penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. DIAGNOSIS FISIOTERAPI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>"+rs2.getString("diagnosis_fisio").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. RENCANA INTERVENSI FISIOTERAPI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>"+rs2.getString("rencana_terapi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Fisioterapi : "+e);
        }
    }

    private void menampilkanAsuhanMedisIGD(String norawat) {
        try {
            if(chkAsuhanMedisIGD.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_medis_igd.tanggal,penilaian_medis_igd.kd_dokter,penilaian_medis_igd.anamnesis,penilaian_medis_igd.hubungan,penilaian_medis_igd.keluhan_utama,penilaian_medis_igd.rps,"+
                            "penilaian_medis_igd.rpk,penilaian_medis_igd.rpd,penilaian_medis_igd.rpo,penilaian_medis_igd.alergi,penilaian_medis_igd.keadaan,penilaian_medis_igd.gcs,penilaian_medis_igd.kesadaran,"+
                            "penilaian_medis_igd.td,penilaian_medis_igd.nadi,penilaian_medis_igd.rr,penilaian_medis_igd.suhu,penilaian_medis_igd.spo,penilaian_medis_igd.bb,penilaian_medis_igd.tb,penilaian_medis_igd.kepala,"+
                            "penilaian_medis_igd.mata,penilaian_medis_igd.gigi,penilaian_medis_igd.leher,penilaian_medis_igd.thoraks,penilaian_medis_igd.abdomen,penilaian_medis_igd.ekstremitas,penilaian_medis_igd.genital,"+
                            "penilaian_medis_igd.ket_fisik,penilaian_medis_igd.ket_lokalis,penilaian_medis_igd.ekg,penilaian_medis_igd.rad,penilaian_medis_igd.lab,penilaian_medis_igd.diagnosis,penilaian_medis_igd.tata,dokter.nm_dokter "+
                            "from penilaian_medis_igd inner join dokter on penilaian_medis_igd.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_medis_igd.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis IGD</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Keadaan Umum : "+rs2.getString("keadaan")+"</td>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='25%' border='0'>SpO2 : "+rs2.getString("spo")+" %</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Mata : "+rs2.getString("mata")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Gigi & Mulut : "+rs2.getString("gigi")+"</td>"+
                                               "<td width='25%' border='0'>Leher : "+rs2.getString("leher")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Genital & Anus : "+rs2.getString("genital")+"</td>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                               "<td width='50%' border='0' colpan='2'>Keterangan Fisik : "+rs2.getString("ket_fisik")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS LOKALIS"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("ket_lokalis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='33%' border='0'>EKG : "+rs2.getString("ekg").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='33%' border='0'>Radiologi : "+rs2.getString("rad").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='33%' border='0'>Laborat : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("tata").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis IGD : "+e);
        }
    }

    private void menampilkanAsuhanMedisRawatJalan(String norawat) {
        try {
            if(chkAsuhanMedisRalan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_medis_ralan.tanggal,penilaian_medis_ralan.kd_dokter,penilaian_medis_ralan.anamnesis,penilaian_medis_ralan.hubungan,"+
                            "penilaian_medis_ralan.keluhan_utama,penilaian_medis_ralan.rps,penilaian_medis_ralan.rpd,penilaian_medis_ralan.rpk,penilaian_medis_ralan.rpo,"+
                            "penilaian_medis_ralan.alergi,penilaian_medis_ralan.keadaan,penilaian_medis_ralan.gcs,penilaian_medis_ralan.kesadaran,penilaian_medis_ralan.td,"+
                            "penilaian_medis_ralan.nadi,penilaian_medis_ralan.rr,penilaian_medis_ralan.suhu,penilaian_medis_ralan.spo,penilaian_medis_ralan.bb,"+
                            "penilaian_medis_ralan.tb,penilaian_medis_ralan.kepala,penilaian_medis_ralan.gigi,penilaian_medis_ralan.tht,penilaian_medis_ralan.thoraks,"+
                            "penilaian_medis_ralan.abdomen,penilaian_medis_ralan.genital,penilaian_medis_ralan.ekstremitas,penilaian_medis_ralan.kulit,"+
                            "penilaian_medis_ralan.ket_fisik,penilaian_medis_ralan.ket_lokalis,penilaian_medis_ralan.penunjang,penilaian_medis_ralan.diagnosis,"+
                            "penilaian_medis_ralan.tata,penilaian_medis_ralan.konsulrujuk,dokter.nm_dokter "+
                            "from penilaian_medis_ralan inner join dokter on penilaian_medis_ralan.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_medis_ralan.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Umum</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Keadaan Umum : "+rs2.getString("keadaan")+"</td>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='25%' border='0'>SpO2 : "+rs2.getString("spo")+" %</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Gigi & Mulut : "+rs2.getString("gigi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>THT : "+rs2.getString("tht")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                               "<td width='25%' border='0'>Genital & Anus : "+rs2.getString("genital")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                               "<td width='25%' border='0'>Kulit : "+rs2.getString("kulit")+"</td>"+
                                               "<td width='50%' border='0' colpan='2'>Keterangan Fisik : "+rs2.getString("ket_fisik")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS LOKALIS"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("ket_lokalis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("tata").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. KONSUL/RUJUK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("konsulrujuk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan : "+e);
        }
    }

    private void menampilkanAsuhanMedisRawatJalanKandungan(String norawat) {
        try {
            if(chkAsuhanMedisRalanKandungan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_medis_ralan_kandungan.tanggal,penilaian_medis_ralan_kandungan.kd_dokter,penilaian_medis_ralan_kandungan.anamnesis,penilaian_medis_ralan_kandungan.hubungan,"+
                            "penilaian_medis_ralan_kandungan.keluhan_utama,penilaian_medis_ralan_kandungan.rps,penilaian_medis_ralan_kandungan.rpk,penilaian_medis_ralan_kandungan.rpd,penilaian_medis_ralan_kandungan.rpo,"+
                            "penilaian_medis_ralan_kandungan.alergi,penilaian_medis_ralan_kandungan.keadaan,penilaian_medis_ralan_kandungan.gcs,penilaian_medis_ralan_kandungan.kesadaran,penilaian_medis_ralan_kandungan.td,"+
                            "penilaian_medis_ralan_kandungan.nadi,penilaian_medis_ralan_kandungan.rr,penilaian_medis_ralan_kandungan.suhu,penilaian_medis_ralan_kandungan.spo,penilaian_medis_ralan_kandungan.bb,"+
                            "penilaian_medis_ralan_kandungan.tb,penilaian_medis_ralan_kandungan.kepala,penilaian_medis_ralan_kandungan.mata,penilaian_medis_ralan_kandungan.gigi,penilaian_medis_ralan_kandungan.tht,"+
                            "penilaian_medis_ralan_kandungan.thoraks,penilaian_medis_ralan_kandungan.abdomen,penilaian_medis_ralan_kandungan.ekstremitas,penilaian_medis_ralan_kandungan.genital,"+
                            "penilaian_medis_ralan_kandungan.kulit,penilaian_medis_ralan_kandungan.ket_fisik,penilaian_medis_ralan_kandungan.tfu,penilaian_medis_ralan_kandungan.tbj,penilaian_medis_ralan_kandungan.his,"+
                            "penilaian_medis_ralan_kandungan.kontraksi,penilaian_medis_ralan_kandungan.djj,penilaian_medis_ralan_kandungan.inspeksi,penilaian_medis_ralan_kandungan.inspekulo,penilaian_medis_ralan_kandungan.vt,"+
                            "penilaian_medis_ralan_kandungan.rt,penilaian_medis_ralan_kandungan.ultra,penilaian_medis_ralan_kandungan.kardio,penilaian_medis_ralan_kandungan.lab,penilaian_medis_ralan_kandungan.diagnosis,"+
                            "penilaian_medis_ralan_kandungan.tata,penilaian_medis_ralan_kandungan.konsul,dokter.nm_dokter "+
                            "from penilaian_medis_ralan_kandungan inner join dokter on penilaian_medis_ralan_kandungan.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_medis_ralan_kandungan.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Kebidanan & Kandungan</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Keadaan Umum : "+rs2.getString("keadaan")+"</td>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='25%' border='0'>SpO2 : "+rs2.getString("spo")+" %</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Mata : "+rs2.getString("mata")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Gigi & Mulut : "+rs2.getString("gigi")+"</td>"+
                                               "<td width='25%' border='0'>THT : "+rs2.getString("tht")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Genital & Anus : "+rs2.getString("genital")+"</td>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                               "<td width='25%' border='0'>Kulit : "+rs2.getString("kulit")+"</td>"+
                                               "<td width='25%' border='0'>Keterangan Fisik : "+rs2.getString("ket_fisik")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS OBSTETRI / GINEKOLOGI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0' colspan='2'>TFU : "+rs2.getString("tfu")+" Cm&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TBJ : "+rs2.getString("tbj")+" gram&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;His : "+rs2.getString("his")+" x/10 Menit&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Kontraksi : "+rs2.getString("kontraksi")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DJJ : "+rs2.getString("djj")+"Dpm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%' border='0'>Inspeksi : "+rs2.getString("inspeksi")+"</td>"+
                                               "<td width='50%' border='0'>VT : "+rs2.getString("vt")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%' border='0'>Inspekulo : "+rs2.getString("inspekulo")+"</td>"+
                                               "<td width='50%' border='0'>RT : "+rs2.getString("rt")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='33%' border='0'>Ultrasonografi : "+rs2.getString("ultra")+"</td>"+
                                               "<td width='33%' border='0'>Kardiotokografi : "+rs2.getString("kardio")+"</td>"+
                                               "<td width='33%' border='0'>Laboratorium : "+rs2.getString("lab")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("diagnosis")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("tata")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. KONSUL/RUJUK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("konsul")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan Kandungan : "+e);
        }
    }

    private void menampilkanAsuhanMedisRawatJalanBayi(String norawat) {
        try {
            if(chkAsuhanMedisRalanBayi.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_medis_ralan_anak.tanggal,penilaian_medis_ralan_anak.kd_dokter,penilaian_medis_ralan_anak.anamnesis,penilaian_medis_ralan_anak.hubungan,"+
                            "penilaian_medis_ralan_anak.keluhan_utama,penilaian_medis_ralan_anak.rps,penilaian_medis_ralan_anak.rpk,penilaian_medis_ralan_anak.rpd,penilaian_medis_ralan_anak.rpo,"+
                            "penilaian_medis_ralan_anak.alergi,penilaian_medis_ralan_anak.keadaan,penilaian_medis_ralan_anak.gcs,penilaian_medis_ralan_anak.kesadaran,penilaian_medis_ralan_anak.td,"+
                            "penilaian_medis_ralan_anak.nadi,penilaian_medis_ralan_anak.rr,penilaian_medis_ralan_anak.suhu,penilaian_medis_ralan_anak.spo,penilaian_medis_ralan_anak.bb,"+
                            "penilaian_medis_ralan_anak.tb,penilaian_medis_ralan_anak.kepala,penilaian_medis_ralan_anak.mata,penilaian_medis_ralan_anak.gigi,penilaian_medis_ralan_anak.tht,"+
                            "penilaian_medis_ralan_anak.thoraks,penilaian_medis_ralan_anak.abdomen,penilaian_medis_ralan_anak.ekstremitas,penilaian_medis_ralan_anak.genital,penilaian_medis_ralan_anak.kulit,"+
                            "penilaian_medis_ralan_anak.ket_fisik,penilaian_medis_ralan_anak.ket_lokalis,penilaian_medis_ralan_anak.penunjang,penilaian_medis_ralan_anak.diagnosis,"+
                            "penilaian_medis_ralan_anak.tata,penilaian_medis_ralan_anak.konsul,dokter.nm_dokter "+
                            "from penilaian_medis_ralan_anak inner join dokter on penilaian_medis_ralan_anak.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_medis_ralan_anak.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Bayi/Anak</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Keadaan Umum : "+rs2.getString("keadaan")+"</td>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='25%' border='0'>SpO2 : "+rs2.getString("spo")+" %</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Mata : "+rs2.getString("mata")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Gigi & Mulut : "+rs2.getString("gigi")+"</td>"+
                                               "<td width='25%' border='0'>THT : "+rs2.getString("tht")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Genital & Anus : "+rs2.getString("genital")+"</td>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                               "<td width='25%' border='0'>Kulit : "+rs2.getString("kulit")+"</td>"+
                                               "<td width='25%' border='0'>Keterangan Fisik : "+rs2.getString("ket_fisik")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS LOKALIS"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("ket_lokalis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("tata").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. KONSUL/RUJUK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("konsul").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan Bayi : "+e);
        }
    }

    private void menampilkanAsuhanMedisRawatJalanTHT(String norawat) {
        try{
            if(chkAsuhanMedisRalanTHT.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_medis_ralan_tht.tanggal,penilaian_medis_ralan_tht.kd_dokter,penilaian_medis_ralan_tht.anamnesis,penilaian_medis_ralan_tht.hubungan,penilaian_medis_ralan_tht.keluhan_utama,"+
                            "penilaian_medis_ralan_tht.rps,penilaian_medis_ralan_tht.rpd,penilaian_medis_ralan_tht.rpo,penilaian_medis_ralan_tht.alergi,penilaian_medis_ralan_tht.td,penilaian_medis_ralan_tht.nadi,"+
                            "penilaian_medis_ralan_tht.rr,penilaian_medis_ralan_tht.suhu,penilaian_medis_ralan_tht.bb,penilaian_medis_ralan_tht.tb,penilaian_medis_ralan_tht.nyeri,penilaian_medis_ralan_tht.status_nutrisi,"+
                            "penilaian_medis_ralan_tht.kondisi,penilaian_medis_ralan_tht.ket_lokalis,penilaian_medis_ralan_tht.lab,penilaian_medis_ralan_tht.rad,penilaian_medis_ralan_tht.tes_pendengaran,"+
                            "penilaian_medis_ralan_tht.penunjang,penilaian_medis_ralan_tht.diagnosis,penilaian_medis_ralan_tht.diagnosisbanding,penilaian_medis_ralan_tht.permasalahan,"+
                            "penilaian_medis_ralan_tht.terapi,penilaian_medis_ralan_tht.tindakan,penilaian_medis_ralan_tht.tatalaksana,penilaian_medis_ralan_tht.edukasi,dokter.nm_dokter "+
                            "from penilaian_medis_ralan_tht inner join dokter on penilaian_medis_ralan_tht.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_medis_ralan_tht.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan THT</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='16%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='16%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                               "<td width='16%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='16%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='16%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='16%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%' colspan='3' valign='top'>Nyeri : "+rs2.getString("nyeri")+"</td>"+
                                               "<td width='50%' colspan='3' valign='top'>Status Nutrisi : "+rs2.getString("status_nutrisi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' colspan='6' valign='top'>Kondisi Umum : "+rs2.getString("kondisi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS LOKALIS"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("ket_lokalis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='50%' valign='top' border='0'>Laboratorium : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='50%' valign='top' border='0'>Radiologi : "+rs2.getString("rad").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%' valign='top' border='0'>Tes Pendengaran : "+rs2.getString("tes_pendengaran").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='50%' valign='top' border='0'>Penunjang Lainnya : "+rs2.getString("penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='50%' valign='top'>Asesmen Kerja : "+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='50%' valign='top'>Asesmen Banding : "+rs2.getString("diagnosisbanding").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. PERMASALAHAN & TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='50%' valign='top'>Permasalahan : "+rs2.getString("permasalahan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='50%' valign='top'>Terapi/Pengobatan : "+rs2.getString("terapi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%' valign='top'>Tindakan/Rencana Tindakan : "+rs2.getString("tindakan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='50%' valign='top'>Tatalaksana Lainnya : "+rs2.getString("tatalaksana").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. EDUKASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        }catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan THT : "+e);
        }
    }

    private void menampilkanUjiFungsiKFR(String norawat) {
        try {
            if(chkUjiFungsiKFR.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select uji_fungsi_kfr.tanggal,uji_fungsi_kfr.diagnosis_fungsional,uji_fungsi_kfr.diagnosis_medis,uji_fungsi_kfr.hasil_didapat,"+
                            "uji_fungsi_kfr.kesimpulan,uji_fungsi_kfr.rekomedasi,uji_fungsi_kfr.kd_dokter,dokter.nm_dokter "+
                            "from uji_fungsi_kfr inner join dokter on uji_fungsi_kfr.kd_dokter=dokter.kd_dokter where "+
                            "uji_fungsi_kfr.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Uji Fungsi/Prosedur KFR</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                        );
                        rs2.beforeFirst();
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "YANG MELAKUKAN PEMERIKSAAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>Tanggal : "+rs2.getString("tanggal")+"</td>"+
                                              "<td width='66%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='20%' border='0'>Diagnosa Fungsional</td>"+
                                              "<td width='80%' border='0'>: "+rs2.getString("diagnosis_fungsional")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='20%' border='0'>Diagnosa Medis</td>"+
                                              "<td width='80%' border='0'>: "+rs2.getString("diagnosis_medis")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "INSTRUMEN UJI FUNGSI/PROSEDUR KFR"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Hasil yang didapat : "+rs2.getString("hasil_didapat")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Kesimpulan : "+rs2.getString("kesimpulan")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Rekomendasi : "+rs2.getString("rekomedasi")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Uji Fungsi KFR : "+e);
        }
    }

    private void menampilkanDiagnosa(String norawat) {
        try{
            if(chkDiagnosaPenyakit.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status "+
                            "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                            "where diagnosa_pasien.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Diagnosa/Penyakit/ICD 10</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'><td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='51%' bgcolor='#FFFAF8'>Nama Penyakit</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
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

            if(chkProsedurTindakan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                            "from prosedur_pasien inner join icd9 "+
                            "on prosedur_pasien.kode=icd9.kode "+
                            "where prosedur_pasien.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Prosedur/Tindakan/ICD 9</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'><td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='51%' bgcolor='#FFFAF8'>Nama Prosedur</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
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
        } catch (Exception e) {
            System.out.println("Notif Diagnosa : "+e);
        }
    }

    private void menampilkanHemodialisa(String norawat) {
        try {
            if(chkHemodialisa.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select hemodialisa.tanggal,hemodialisa.lama,hemodialisa.akses,hemodialisa.dialist,hemodialisa.transfusi,hemodialisa.penarikan, "+
                            "hemodialisa.qb,hemodialisa.qd,hemodialisa.ureum,hemodialisa.hb,hemodialisa.hbsag,creatinin,hemodialisa.hiv,hemodialisa.hcv,hemodialisa.lain, "+
                            "hemodialisa.kd_dokter,dokter.nm_dokter,hemodialisa.kd_penyakit,penyakit.nm_penyakit from hemodialisa inner join dokter on hemodialisa.kd_dokter=dokter.kd_dokter "+
                            "inner join penyakit on hemodialisa.kd_penyakit=penyakit.kd_penyakit where hemodialisa.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Hemodialisa</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode Dokter P.J.</td>"+
                                    "<td valign='top' width='20%' bgcolor='#FFFAF8'>Nama Dokter P.J.</td>"+
                                    "<td valign='top' width='10%' bgcolor='#FFFAF8'>ICD 10</td>"+
                                    "<td valign='top' width='41%' bgcolor='#FFFAF8'>Nama Penyakit</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tanggal")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("kd_dokter")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("nm_dokter")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("kd_penyakit")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("nm_penyakit")+"</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top' align='center'></td>"+
                                    "<td valign='top' colspan='5'>KETERANGAN : "+
                                        "Lama : "+rs2.getString("lama")+" Jam, "+
                                        "Akses : "+rs2.getString("akses")+", "+
                                        "Dialist : "+rs2.getString("dialist")+", "+
                                        "Transfusi : "+rs2.getString("transfusi")+" Kalf/Durante HD, "+
                                        "Penarikan Cairan : "+rs2.getString("penarikan")+" ml, "+
                                        "QB : "+rs2.getString("qb")+", "+
                                        "QD : "+rs2.getString("qd")+", "+
                                        "Ureum : "+rs2.getString("ureum")+", "+
                                        "Hb : "+rs2.getString("hb")+", "+
                                        "HbsAg : "+rs2.getString("hbsag")+", "+
                                        "Creatinin : "+rs2.getString("creatinin")+", "+
                                        "HIV : "+rs2.getString("hiv")+", "+
                                        "HCV : "+rs2.getString("hcv")+", "+
                                        "Lain-Lain : "+rs2.getString("lain")+
                                    "</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notif Hemodialisa : "+e);
        }
    }

    private void menampilkanPemeriksaanRalan(String norawat) {
        try {
            //SOAP Ralan
            if(chkPemeriksaanRalan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,"+
                            "pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,pemeriksaan_ralan.spo2,pemeriksaan_ralan.gcs,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, "+
                            "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,"+
                            "pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pemeriksaan_ralan.nip,pegawai.nama,pegawai.jbtn from pemeriksaan_ralan inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where "+
                            "pemeriksaan_ralan.no_rawat='"+norawat+"' order by pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Pemeriksaan Rawat Jalan</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='54%' bgcolor='#FFFAF8' colspan='7'>Dokter/Paramedis</td>"+
                                    "<td valign='top' width='27%' bgcolor='#FFFAF8' colspan='3'>Profesi/Jabatan/Departemen</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top' colspan='7'>"+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                    "<td valign='top' colspan='3'>"+rs2.getString("jbtn")+"</td>"+
                                 "</tr>"); 
                            if(!rs2.getString("keluhan").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Subjek</td>"+
                                        "<td valign='top' colspan='8'> : "+rs2.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("pemeriksaan").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Objek</td>"+
                                        "<td valign='top' colspan='8'> : "+rs2.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            htmlContent.append(
                                    "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>Suhu(C)</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>Tensi</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>Nadi(/menit)</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>Respirasi(/menit)</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>Tinggi(Cm)</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>Berat(Kg)</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>SpO2(%)</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>GCS(E,V,M)</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>Kesadaran</td>"+
                                        "<td valign='top' width='8%' bgcolor='#FFFAF8' align='center'>L.P.(Cm)</td>"+
                                    "</tr>"+
                                    "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top'></td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("suhu_tubuh")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("tensi")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("nadi")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("respirasi")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("tinggi")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("berat")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("spo2")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("gcs")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("kesadaran")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("lingkar_perut")+"</td>"+
                                     "</tr>");

                            if(!rs2.getString("alergi").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Alergi</td>"+
                                        "<td valign='top' colspan='8'> : "+rs2.getString("alergi")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("penilaian").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Asesmen</td>"+
                                        "<td valign='top' colspan='8'> : "+rs2.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("rtl").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Plan</td>"+
                                        "<td valign='top' colspan='8'> : "+rs2.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("instruksi").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Instruksi</td>"+
                                        "<td valign='top' colspan='8'> : "+rs2.getString("instruksi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("evaluasi").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Evaluasi</td>"+
                                        "<td valign='top' colspan='8'> : "+rs2.getString("evaluasi")+"</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan obstetri Ralan
            if(chkPemeriksaanObstetriRalan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, " +
                            "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, " +
                            "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, " +
                            "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan," +
                            "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto " +
                            "from pemeriksaan_obstetri_ralan where pemeriksaan_obstetri_ralan.no_rawat='"+norawat+"' order by pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Pemeriksaan Obstetri Rawat Jalan</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tinggi Fundus</td>"+
                                    "<td valign='top' width='6%' bgcolor='#FFFAF8'>Janin</td>"+
                                    "<td valign='top' width='6%' bgcolor='#FFFAF8'>Letak</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Panggul</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Denyut</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kontraksi</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Mnt</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Detik</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Fluksus</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Albus</td>"+
                                    "<td valign='top' width='6%' bgcolor='#FFFAF8'>Dalam</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tebal</td>"+
                                    "<td valign='top' width='6%' bgcolor='#FFFAF8'>Arah</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Ketuban</td>"+
                                    "<td valign='top' width='7%' bgcolor='#FFFAF8'>Feto</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tinggi_uteri")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("janin")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("letak")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("panggul")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("denyut")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("kontraksi")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("kualitas_mnt")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("kualitas_dtk")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("fluksus")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("albus")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("dalam")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tebal")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("arah")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("ketuban")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("feto")+"</td>"+
                                 "</tr>"); 
                            if(!rs2.getString("vulva").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Vulva</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("vulva")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("portio").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Portio</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("portio")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("pembukaan").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Pembukaan</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("pembukaan")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("penurunan").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Penurunan</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("penurunan")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("denominator").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Denominator</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("denominator")+"</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan genekologi Ralan
            if(chkPemeriksaanGenekologiRalan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, " +
                            "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, " +
                            "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, " +
                            "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri," +
                            "pemeriksaan_ginekologi_ralan.cavum_douglas " +
                            "from pemeriksaan_ginekologi_ralan where pemeriksaan_ginekologi_ralan.no_rawat='"+norawat+"' order by pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Pemeriksaan Ginekologi Rawat Jalan</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='81%' bgcolor='#FFFAF8'>Pemeriksaan</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top'>"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>Inspeksi</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("inspeksi")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Uretra/Vagina</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("inspeksi_vulva")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>Inspekulo</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("inspekulo_gine")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Fluxus</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("fluxus_gine")+",&nbsp;&nbsp;Fluor Albus : "+rs2.getString("fluor_gine")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Vagina</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("vulva_inspekulo")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("portio_inspekulo")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Sondage</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("sondage")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>Pemeriksaan Dalam</td>"+
                                               "<td border='0' valign='top' width='70%'>:</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("portio_dalam")+",&nbsp;&nbsp;Bentuk : "+rs2.getString("bentuk")+"</td>"+
                                            "</tr>"+   
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Uteri</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("cavum_uteri")+",&nbsp;&nbsp;Mobilitas : "+rs2.getString("mobilitas")+"</td>"+
                                            "</tr>"+ 
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;</td>"+
                                               "<td border='0' valign='top' width='70%'>&nbsp;&nbsp;&nbsp;Ukuran : "+rs2.getString("ukuran")+",&nbsp;&nbsp;Nyeri Tekan : "+rs2.getString("nyeri_tekan")+"</td>"+
                                            "</tr>"+ 
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Adnexa/Parametrium</td>"+
                                               "<td border='0' valign='top' width='70%'>: Kanan : "+rs2.getString("adnexa_kanan")+",&nbsp;&nbsp;Kiri : "+rs2.getString("adnexa_kiri")+"</td>"+
                                            "</tr>"+ 
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Douglas</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("cavum_douglas")+"</td>"+
                                            "</tr>"+ 
                                        "</table>"+
                                    "</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan catatan observasi IGD
            if(chkCatatanObservasiIGD.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select catatan_observasi_igd.tgl_perawatan,catatan_observasi_igd.jam_rawat,catatan_observasi_igd.gcs,"+
                            "catatan_observasi_igd.td,catatan_observasi_igd.hr,catatan_observasi_igd.rr,catatan_observasi_igd.suhu,catatan_observasi_igd.spo2,"+
                            "catatan_observasi_igd.nip,petugas.nama from catatan_observasi_igd inner join petugas on catatan_observasi_igd.nip=petugas.nip "+
                            "where catatan_observasi_igd.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Catatan Observasi IGD</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='middle' width='4%' bgcolor='#FFFAF8' rowspan='2'>No.</td>"+
                                    "<td valign='middle' width='15%' bgcolor='#FFFAF8' rowspan='2'>Tanggal</td>"+
                                    "<td valign='top' width='58%' bgcolor='#FFFAF8' colspan='6'>Monitoring</td>"+
                                    "<td valign='middle' width='23%' bgcolor='#FFFAF8' rowspan='2'>Perawat/Paramedis</td>"+
                                 "</tr>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='11%' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                    "<td valign='top' width='10%' bgcolor='#FFFAF8'>TD</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>HR (/menit)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>RR (/menit)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>SpO2(%)</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("gcs")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("td")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("hr")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("rr")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("suhu")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("spo2")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notif Pemeriksaan Ralan : "+e);
        }
    }

    private void menampilkanAsuhanKebidananRawatInap(String norawat) {
        try {
            if(chkAsuhanKeperawatanRanapKandungan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_awal_keperawatan_kebidanan_ranap.tanggal,penilaian_awal_keperawatan_kebidanan_ranap.informasi,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.tiba_diruang_rawat,penilaian_awal_keperawatan_kebidanan_ranap.cara_masuk,penilaian_awal_keperawatan_kebidanan_ranap.keluhan,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.rpk,penilaian_awal_keperawatan_kebidanan_ranap.psk,penilaian_awal_keperawatan_kebidanan_ranap.rp,penilaian_awal_keperawatan_kebidanan_ranap.alergi,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.komplikasi_sebelumnya,penilaian_awal_keperawatan_kebidanan_ranap.keterangan_komplikasi_sebelumnya,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_mens_umur,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_mens_lamanya,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_mens_banyaknya,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_mens_siklus,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_mens_ket_siklus,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_mens_dirasakan,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_perkawinan_status,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_perkawinan_ket_status,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_perkawinan_usia1,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_perkawinan_ket_usia1,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_perkawinan_usia2,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_perkawinan_ket_usia2,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_perkawinan_usia3,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_perkawinan_ket_usia3,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_persalinan_g,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_persalinan_p,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_persalinan_a,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_persalinan_hidup,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_hpht,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_usiahamil,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_tp,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_imunisasi,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_anc,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_ancke,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_ket_ancke,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_keluhan_hamil_muda,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_hamil_keluhan_hamil_tua,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kb,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kb_lamanya,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kb_komplikasi,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kb_ket_komplikasi,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kb_kapaberhenti,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kb_alasanberhenti,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_genekologi,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kebiasaan_obat,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kebiasaan_ket_obat,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kebiasaan_merokok,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kebiasaan_ket_merokok,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kebiasaan_alkohol,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kebiasaan_ket_alkohol,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_kebiasaan_narkoba,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_mental,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_keadaan_umum,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_gcs,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_td,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_nadi,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_rr,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_suhu,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_spo2,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_bb,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_tb,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_lila,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_tfu,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_tbj,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_letak,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_presentasi,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_penurunan,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_his,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_kekuatan,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_lamanya,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_djj,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_ket_djj,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_portio,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_pembukaan,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_ketuban,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_hodge,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_panggul,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_inspekulo,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_ket_inspekulo,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_lakmus,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_ket_lakmus,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_ctg,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_kebidanan_ket_ctg,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_kepala,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_muka,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_mata,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_hidung,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_telinga,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_mulut,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_leher,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_dada,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_perut,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_genitalia,penilaian_awal_keperawatan_kebidanan_ranap.pemeriksaan_umum_ekstrimitas,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_kemampuan_aktifitas,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_ket_berjalan,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_aktivitas,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_ambulasi,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_ket_ekstrimitas_atas,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_ekstrimitas_bawah,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_ket_ekstrimitas_bawah,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_kemampuan_menggenggam,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_ket_kemampuan_menggenggam,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_ket_koordinasi,penilaian_awal_keperawatan_kebidanan_ranap.pengkajian_fungsi_gangguan_fungsi,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_kondisipsiko,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_adakah_prilaku,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_ket_adakah_prilaku,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_hubungan_pasien,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_tinggal_dengan,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_ket_tinggal_dengan,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_budaya,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_ket_budaya,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_pend_pj,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_edukasi_pada,penilaian_awal_keperawatan_kebidanan_ranap.riwayat_psiko_ket_edukasi_pada,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_ket_penyebab,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_kualitas,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_lokasi,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_skala,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_waktu,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_hilang,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_diberitahukan_dokter,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_skala1,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_nilai1,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_skala2,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_nilai2,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_skala3,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_nilai3,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_skala4,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_nilai4,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_skala5,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_nilai5,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_skala6,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_nilai6,penilaian_awal_keperawatan_kebidanan_ranap.penilaian_jatuh_totalnilai,penilaian_awal_keperawatan_kebidanan_ranap.skrining_gizi1,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.nilai_gizi1,penilaian_awal_keperawatan_kebidanan_ranap.skrining_gizi2,penilaian_awal_keperawatan_kebidanan_ranap.nilai_gizi2,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.nilai_total_gizi,penilaian_awal_keperawatan_kebidanan_ranap.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_kebidanan_ranap.skrining_gizi_ket_diagnosa_khusus,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_kebidanan_ranap.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_kebidanan_ranap.masalah,"+
                            "penilaian_awal_keperawatan_kebidanan_ranap.rencana,penilaian_awal_keperawatan_kebidanan_ranap.nip1,penilaian_awal_keperawatan_kebidanan_ranap.nip2,penilaian_awal_keperawatan_kebidanan_ranap.kd_dokter, "+
                            "pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter "+
                            "from penilaian_awal_keperawatan_kebidanan_ranap inner join petugas as pengkaji1 on penilaian_awal_keperawatan_kebidanan_ranap.nip1=pengkaji1.nip "+
                            "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_kebidanan_ranap.nip2=pengkaji2.nip "+
                            "inner join dokter on penilaian_awal_keperawatan_kebidanan_ranap.kd_dokter=dokter.kd_dokter where penilaian_awal_keperawatan_kebidanan_ranap.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Keperawatan Rawat Inap Kandungan</td>"+
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
                                              "<td width='16%' border='0' align='left'>Tanggal</td>"+
                                              "<td width='35%' border='0'>: "+rs2.getString("tanggal")+"</td>"+
                                              "<td width='11%' border='0' align='left'>Anamnesis</td>"+
                                              "<td width='38%' border='0'>: "+rs2.getString("informasi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='16%' border='0' align='left'>Tiba di Ruang Rawat</td>"+
                                              "<td width='35%' border='0'>: "+rs2.getString("tiba_diruang_rawat")+"</td>"+
                                              "<td width='11%' border='0' align='left'>Cara Masuk</td>"+
                                              "<td width='38%' border='0'>: "+rs2.getString("cara_masuk")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='16%' border='0' align='left'>Pengkaji 1</td>"+
                                              "<td width='35%' border='0'>: "+rs2.getString("nip1")+" "+rs2.getString("pengkaji1")+"</td>"+
                                              "<td width='11%' border='0' align='left'>Pengkaji 2</td>"+
                                              "<td width='38%' border='0'>: "+rs2.getString("nip2")+" "+rs2.getString("pengkaji2")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='16%' border='0' align='left'>DPJP</td>"+
                                              "<td width='35%' border='0' colspan='3'>: "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='50%'>Keluhan Utama : "+rs2.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Penyakit Selama Kehamilan : "+rs2.getString("psk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Pembedahan : "+rs2.getString("rp").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                              "<td width='50%'>Komplikasi Kehamilan Sebelumnya : "+rs2.getString("komplikasi_sebelumnya")+(rs2.getString("keterangan_komplikasi_sebelumnya").equals("")?"":", "+rs2.getString("keterangan_komplikasi_sebelumnya"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>"+
                                                  "Riwayat Menstruasi :"+
                                                  "<table width='99%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                      "<tr>"+
                                                           "<td width='14%' border='0' align='left'>Umur Menarche</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_mens_umur")+" tahun</td>"+
                                                           "<td width='20%' border='0' align='left'>Lamanya</td>"+
                                                           "<td width='21%' border='0'>: "+rs2.getString("riwayat_mens_lamanya")+" hari</td>"+
                                                           "<td width='10%' border='0' align='left'>Banyaknya</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_mens_banyaknya")+" pembalut</td>"+
                                                      "</tr>"+
                                                      "<tr>"+
                                                           "<td width='14%' border='0' align='left'>Siklus</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_mens_siklus")+"hari, ("+rs2.getString("riwayat_mens_ket_siklus")+")</td>"+
                                                           "<td width='20%' border='0' align='left'>Dirasakan Saat Menstruasi</td>"+
                                                           "<td border='0' colspan='3'>: "+rs2.getString("riwayat_mens_dirasakan")+"</td>"+
                                                      "</tr>"+
                                                  "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>"+
                                                  "Riwayat Perkawinan :"+
                                                  "<table width='99%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                      "<tr>"+
                                                           "<td width='18%' border='0' align='left'>Status Menikah</td>"+
                                                           "<td width='32%' border='0'>: "+rs2.getString("riwayat_perkawinan_status")+", "+rs2.getString("riwayat_perkawinan_ket_status")+" kali</td>"+
                                                           "<td width='18%' border='0' align='left'>Usia Perkawinan Ke 1</td>"+
                                                           "<td width='32%' border='0'>: "+rs2.getString("riwayat_perkawinan_usia1")+" tahun, Status : "+rs2.getString("riwayat_perkawinan_ket_usia1")+"</td>"+
                                                      "</tr>"+
                                                      "<tr>"+
                                                           "<td width='18%' border='0' align='left'>Usia Perkawinan Ke 2</td>"+
                                                           "<td width='32%' border='0'>: "+rs2.getString("riwayat_perkawinan_usia2")+" tahun, Status : "+rs2.getString("riwayat_perkawinan_ket_usia2")+"</td>"+
                                                           "<td width='18%' border='0' align='left'>Usia Perkawinan Ke 3</td>"+
                                                           "<td width='32%' border='0'>: "+rs2.getString("riwayat_perkawinan_usia3")+" tahun, Status : "+rs2.getString("riwayat_perkawinan_ket_usia3")+"</td>"+
                                                      "</tr>"+
                                                  "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>"+
                                                  "Riwayat Persalinan & Nifas :"+
                                                  "<table width='99%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                       "<tr class='isi'>"+
                                                            "<td width='100%' colspan='10' border='0'>G : "+rs2.getString("riwayat_persalinan_g")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P : "+rs2.getString("riwayat_persalinan_p")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A : "+rs2.getString("riwayat_persalinan_a")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Anak Yang Hidup : "+rs2.getString("riwayat_persalinan_hidup")+"</td>"+
                                                        "</tr>"+
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
                                                "select * from riwayat_persalinan_pasien where no_rkm_medis='"+NoRM.getText().trim()+"' order by tgl_thn").executeQuery();
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
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>"+
                                                  "Riwayat Hamil Sekarang :"+
                                                  "<table width='99%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                      "<tr>"+
                                                           "<td width='16%' border='0' align='left'>HPHT</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_hamil_hpht")+"</td>"+
                                                           "<td width='16%' border='0' align='left'>Usia Hamil</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_hamil_usiahamil")+"</td>"+
                                                           "<td width='16%' border='0' align='left'>Tanggal Perkiraan</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_hamil_tp")+"</td>"+
                                                      "</tr>"+
                                                      "<tr>"+
                                                           "<td width='16%' border='0' align='left'>Riwayat Imunisasi</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_hamil_imunisasi")+"</td>"+
                                                           "<td width='16%' border='0' align='left'>ANC</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_hamil_anc")+" X</td>"+
                                                           "<td width='16%' border='0' align='left'>ANC Ke</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_hamil_ancke")+" "+rs2.getString("riwayat_hamil_ket_ancke")+"</td>"+
                                                      "</tr>"+
                                                      "<tr>"+
                                                           "<td width='16%' border='0' align='left'>Keluhan Hamil Muda</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_hamil_keluhan_hamil_muda")+"</td>"+
                                                           "<td width='16%' border='0' align='left'>Keluhan Hamil Tua</td>"+
                                                           "<td border='0' colspan='3'>: "+rs2.getString("riwayat_hamil_keluhan_hamil_tua")+"</td>"+
                                                      "</tr>"+
                                                  "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>"+
                                                  "Riwayat Keluarga Berencana :"+
                                                  "<table width='99%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                      "<tr>"+
                                                           "<td width='16%' border='0' align='left'>Status</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_kb")+"</td>"+
                                                           "<td width='10%' border='0' align='left'>Lamanya</td>"+
                                                           "<td width='20%' border='0'>: "+rs2.getString("riwayat_kb_lamanya")+"</td>"+
                                                           "<td width='10%' border='0' align='left'>Komplikasi</td>"+
                                                           "<td width='26%' border='0'>: "+rs2.getString("riwayat_kb_komplikasi")+(rs2.getString("riwayat_kb_ket_komplikasi").equals("")?"":", "+rs2.getString("riwayat_kb_ket_komplikasi"))+"</td>"+
                                                      "</tr>"+
                                                      "<tr>"+
                                                           "<td width='16%' border='0' align='left'>Kapan Berhenti KB</td>"+
                                                           "<td width='17%' border='0'>: "+rs2.getString("riwayat_kb_kapaberhenti")+"</td>"+
                                                           "<td width='10%' border='0' align='left'>Alasan</td>"+
                                                           "<td colspan='3' border='0'>: "+rs2.getString("riwayat_kb_alasanberhenti")+"</td>"+
                                                      "</tr>"+
                                                  "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>"+
                                                  "Riwayat Ginekologi : "+rs2.getString("riwayat_genekologi")+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>"+
                                                  "Riwayat Kebiasaan :"+
                                                  "<table width='99%' border='0' align='right' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                      "<tr>"+
                                                           "<td width='15%' border='0' align='left'>Obat/Vitamin</td>"+
                                                           "<td width='35%' border='0'>: "+rs2.getString("riwayat_kebiasaan_obat")+(rs2.getString("riwayat_kebiasaan_ket_obat").equals("")?"":", "+rs2.getString("riwayat_kebiasaan_ket_obat"))+"</td>"+
                                                           "<td width='20%' border='0' align='left'>Merokok</td>"+
                                                           "<td width='30%' border='0'>: "+rs2.getString("riwayat_kebiasaan_merokok")+(rs2.getString("riwayat_kebiasaan_ket_merokok").equals("")?"":", "+rs2.getString("riwayat_kebiasaan_ket_merokok")+" batang/hari")+"</td>"+
                                                      "</tr>"+
                                                      "<tr>"+
                                                           "<td width='15%' border='0' align='left'>Alkohol</td>"+
                                                           "<td width='35%' border='0'>: "+rs2.getString("riwayat_kebiasaan_alkohol")+(rs2.getString("riwayat_kebiasaan_ket_alkohol").equals("")?"":", "+rs2.getString("riwayat_kebiasaan_ket_alkohol"))+" gelas/hari"+"</td>"+
                                                           "<td width='20%' border='0' align='left'>Obat Tidur/Narkoba</td>"+
                                                           "<td width='30%' border='0'>: "+rs2.getString("riwayat_kebiasaan_narkoba")+"</td>"+
                                                      "</tr>"+
                                                  "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN KEBIDANAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Kesadaran Mental : "+rs2.getString("pemeriksaan_kebidanan_mental")+"</td>"+
                                               "<td width='25%' border='0'>Keadaan Umum : "+rs2.getString("pemeriksaan_kebidanan_keadaan_umum")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("pemeriksaan_kebidanan_gcs")+"</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("pemeriksaan_kebidanan_td")+" mmHg</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("pemeriksaan_kebidanan_nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("pemeriksaan_kebidanan_rr")+" x/menit</td>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("pemeriksaan_kebidanan_suhu")+" °C</td>"+
                                               "<td width='25%' border='0'>SpO2 : "+rs2.getString("pemeriksaan_kebidanan_spo2")+" %</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("pemeriksaan_kebidanan_bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>TB : "+rs2.getString("pemeriksaan_kebidanan_tb")+" cm</td>"+
                                               "<td width='25%' border='0'>LILA : "+rs2.getString("pemeriksaan_kebidanan_lila")+" cm</td>"+
                                               "<td width='25%' border='0'>TFU : "+rs2.getString("pemeriksaan_kebidanan_tfu")+" cm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>TBJ : "+rs2.getString("pemeriksaan_kebidanan_tbj")+" gr</td>"+
                                               "<td width='25%' border='0'>Letak : "+rs2.getString("pemeriksaan_kebidanan_letak")+"</td>"+
                                               "<td width='25%' border='0'>Presentasi : "+rs2.getString("pemeriksaan_kebidanan_presentasi")+"</td>"+
                                               "<td width='25%' border='0'>Penurunan : "+rs2.getString("pemeriksaan_kebidanan_penurunan")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Kontraksi/HIS : "+rs2.getString("pemeriksaan_kebidanan_his")+" x/10’, Kekuatan : "+rs2.getString("pemeriksaan_kebidanan_kekuatan")+"</td>"+
                                               "<td width='25%' border='0'>Lamanya : "+rs2.getString("pemeriksaan_kebidanan_lamanya")+" detik</td>"+
                                               "<td width='25%' border='0'>DJJ :"+rs2.getString("pemeriksaan_kebidanan_djj")+"/mnt "+rs2.getString("pemeriksaan_kebidanan_ket_djj")+"</td>"+
                                               "<td width='25%' border='0'>Portio : "+rs2.getString("pemeriksaan_kebidanan_portio")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Pembukaan Serviks : "+rs2.getString("pemeriksaan_kebidanan_pembukaan")+" cm</td>"+
                                               "<td width='25%' border='0'>Ketuban : "+rs2.getString("pemeriksaan_kebidanan_ketuban")+" kep/bok</td>"+
                                               "<td width='25%' border='0'>Hodge : "+rs2.getString("pemeriksaan_kebidanan_hodge")+"</td>"+
                                               "<td width='25%' border='0'>Panggul : "+rs2.getString("pemeriksaan_kebidanan_panggul")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Inspekulo : "+rs2.getString("pemeriksaan_kebidanan_inspekulo")+(rs2.getString("pemeriksaan_kebidanan_ket_inspekulo").equals("")?"":", "+rs2.getString("pemeriksaan_kebidanan_ket_inspekulo"))+"</td>"+
                                               "<td width='25%' border='0'>Lakmus : "+rs2.getString("pemeriksaan_kebidanan_lakmus")+(rs2.getString("pemeriksaan_kebidanan_ket_lakmus").equals("")?"":", "+rs2.getString("pemeriksaan_kebidanan_ket_lakmus"))+"</td>"+
                                               "<td width='25%' border='0' colspan='2'>CTG : "+rs2.getString("pemeriksaan_kebidanan_ctg")+(rs2.getString("pemeriksaan_kebidanan_ket_ctg").equals("")?"":", "+rs2.getString("pemeriksaan_kebidanan_ket_ctg"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. PEMERIKSAAN UMUM"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("pemeriksaan_umum_kepala")+"</td>"+
                                               "<td width='25%' border='0'>Muka : "+rs2.getString("pemeriksaan_umum_muka")+"</td>"+
                                               "<td width='25%' border='0'>Mata : "+rs2.getString("pemeriksaan_umum_mata")+"</td>"+
                                               "<td width='25%' border='0'>Hidung : "+rs2.getString("pemeriksaan_umum_hidung")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Telinga : "+rs2.getString("pemeriksaan_umum_telinga")+"</td>"+
                                               "<td width='25%' border='0'>Mulut : "+rs2.getString("pemeriksaan_umum_mulut")+"</td>"+
                                               "<td width='25%' border='0'>Leher : "+rs2.getString("pemeriksaan_umum_leher")+"</td>"+
                                               "<td width='25%' border='0'>Dada : "+rs2.getString("pemeriksaan_umum_dada")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Perut : "+rs2.getString("pemeriksaan_umum_perut")+"</td>"+
                                               "<td width='25%' border='0'>Genitalia : "+rs2.getString("pemeriksaan_umum_genitalia")+"</td>"+
                                               "<td width='50%' border='0' colspan='2'>Ekstremitas : "+rs2.getString("pemeriksaan_umum_ekstrimitas")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PENGKAJIAN FUNGSI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>a. Kemampuan Aktifitas Sehari-hari</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_kemampuan_aktifitas")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>b. Berjalan</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_berjalan")+(rs2.getString("pengkajian_fungsi_ket_berjalan").equals("")?"":", "+rs2.getString("pengkajian_fungsi_ket_berjalan"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>c. Aktifitas</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_aktivitas")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>d. Alat Ambulasi</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_ambulasi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>e. Ekstremitas Atas</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_ekstrimitas_atas")+(rs2.getString("pengkajian_fungsi_ket_ekstrimitas_atas").equals("")?"":", "+rs2.getString("pengkajian_fungsi_ket_ekstrimitas_atas"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>f. Ekstremitas Bawah</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_ekstrimitas_bawah")+(rs2.getString("pengkajian_fungsi_ket_ekstrimitas_bawah").equals("")?"":", "+rs2.getString("pengkajian_fungsi_ket_ekstrimitas_bawah"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>g. Kemampuan Menggenggam</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_kemampuan_menggenggam")+(rs2.getString("pengkajian_fungsi_ket_kemampuan_menggenggam").equals("")?"":", "+rs2.getString("pengkajian_fungsi_ket_kemampuan_menggenggam"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>h. Kemampuan Koordinasi</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_koordinasi")+(rs2.getString("pengkajian_fungsi_ket_koordinasi").equals("")?"":", "+rs2.getString("pengkajian_fungsi_ket_koordinasi"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='37%' border='0'>i. Kesimpulan Gangguan Fungsi</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='62%' border='0'>"+rs2.getString("pengkajian_fungsi_gangguan_fungsi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. RIWAYAT PSIKOLOGIS – SOSIAL – EKONOMI – BUDAYA – SPIRITUAL"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>a. Kondisi Psikologis</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_kondisipsiko")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>b. Adakah Perilaku</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_adakah_prilaku")+(rs2.getString("riwayat_psiko_ket_adakah_prilaku").equals("")?"":", "+rs2.getString("riwayat_psiko_ket_adakah_prilaku"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>c. Gangguan Jiwa di Masa Lalu</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_gangguan_jiwa")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>d. Hubungan Pasien dengan Anggota Keluarga</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_hubungan_pasien")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>e. Agama</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+Agama.getText()+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>f. Tinggal Dengan</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_tinggal_dengan")+(rs2.getString("riwayat_psiko_ket_tinggal_dengan").equals("")?"":", "+rs2.getString("riwayat_psiko_ket_tinggal_dengan"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>g. Pekerjaan</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+Pekerjaan.getText()+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>h. Pembayaran</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs.getString("png_jawab")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>i. Nilai-nilai Kepercayaan/Budaya Yang Perlu Diperhatikan</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_budaya")+(rs2.getString("riwayat_psiko_ket_budaya").equals("")?"":", "+rs2.getString("riwayat_psiko_ket_budaya"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>j. Bahasa Sehari-hari</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+Bahasa.getText()+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>k. Pendidikan Pasien</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+Pendidikan.getText()+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>l. Pendidikan P.J.</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_pend_pj")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>m. Edukasi Diberikan Kepada</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_edukasi_pada")+(rs2.getString("riwayat_psiko_ket_edukasi_pada").equals("")?"":", "+rs2.getString("riwayat_psiko_ket_edukasi_pada"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. PENILAIAN TINGKAT NYERI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Tingkat Nyeri : "+rs2.getString("penilaian_nyeri")+", Waktu / Durasi : "+rs2.getString("penilaian_nyeri_waktu")+" Menit</td>"+
                                              "<td width='50%' border='0'>Penyebab : "+rs2.getString("penilaian_nyeri_penyebab")+(rs2.getString("penilaian_nyeri_ket_penyebab").equals("")?"":", "+rs2.getString("penilaian_nyeri_ket_penyebab"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Kualitas : "+rs2.getString("penilaian_nyeri_kualitas")+(rs2.getString("penilaian_nyeri_ket_kualitas").equals("")?"":", "+rs2.getString("penilaian_nyeri_ket_kualitas"))+"</td>"+
                                              "<td width='50%' border='0'>Severity : Skala Nyeri "+rs2.getString("penilaian_nyeri_skala")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='0' border='0'>Wilayah :</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;Lokasi : "+rs2.getString("penilaian_nyeri_lokasi")+"</td>"+
                                              "<td width='50%' border='0'>Menyebar : "+rs2.getString("penilaian_nyeri_menyebar")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Nyeri hilang bila : "+rs2.getString("penilaian_nyeri_hilang")+(rs2.getString("penilaian_nyeri_ket_hilang").equals("")?"":", "+rs2.getString("penilaian_nyeri_ket_hilang"))+"</td>"+
                                              "<td width='50%' border='0'>Diberitahukan pada dokter ? "+rs2.getString("penilaian_nyeri_diberitahukan_dokter")+(rs2.getString("penilaian_nyeri_jam_diberitahukan_dokter").equals("")?"":", Jam : "+rs2.getString("penilaian_nyeri_jam_diberitahukan_dokter"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. PENILAIAN RESIKO JATUH"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                         "<tr class='isi'>"+
                                              "<td width='50%' bgcolor='#FFFAF8' align='center'>Faktor Resiko</td>"+
                                              "<td width='40%' bgcolor='#FFFAF8' align='center'>Skala</td>"+
                                              "<td width='10%' bgcolor='#FFFAF8' align='center'>Poin</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td>1. Riwayat Jatuh</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_skala1")+"</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_nilai1")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td>2. Diagnosis Sekunder (≥ 2 Diagnosis Medis)</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_skala2")+"</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_nilai2")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td>3. Alat Bantu</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_skala3")+"</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_nilai3")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td>4. Terpasang Infuse</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_skala4")+"</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_nilai4")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td>5. Gaya Berjalan</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_skala5")+"</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_nilai5")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td>6. Status Mental</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_skala6")+"</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_nilai6")+"</td>"+
                                          "</tr>"+
                                              "<td align='right' colspan='2'>Total :</td>"+
                                              "<td align='center'>"+rs2.getString("penilaian_jatuh_totalnilai")+"</td>"+
                                          "</tr>"+
                                          "</tr>"+
                                              "<td align='center' colspan='3'>"
                            );
                                        
                            if(rs2.getInt("penilaian_jatuh_totalnilai")<25){
                                htmlContent.append("Tingkat Resiko : Risiko Rendah (0-24), Tindakan : Intervensi pencegahan risiko jatuh standar");
                            }else if(rs2.getInt("penilaian_jatuh_totalnilai")<45){
                                htmlContent.append("Tingkat Resiko : Risiko Sedang (25-44), Tindakan : Intervensi pencegahan risiko jatuh standar");
                            }else if(rs2.getInt("penilaian_jatuh_totalnilai")>=45){
                                htmlContent.append("Tingkat Resiko : Risiko Tinggi (> 45), Tindakan : Intervensi pencegahan risiko jatuh standar dan Intervensi risiko jatuh tinggi");
                            }
                                        
                            htmlContent.append(
                                              "</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VIII. SKRINING GIZI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>No</td>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55%'>Parameter</td>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%' colspan='2'>Nilai</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top'>1</td>"+
                                              "<td valign='top'>Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?</td>"+
                                              "<td valign='top' align='center' width='35%'>"+rs2.getString("skrining_gizi1")+"</td>"+
                                              "<td valign='top' align='right' width='5%'>"+rs2.getString("nilai_gizi1")+"&nbsp;&nbsp;</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top'>2</td>"+
                                              "<td valign='top'>Apakah asupan makan berkurang karena tidak nafsu makan ?</td>"+
                                              "<td valign='top' align='center' width='35%'>"+rs2.getString("skrining_gizi2")+"</td>"+
                                              "<td valign='top' align='right' width='5%'>"+rs2.getString("nilai_gizi2")+"&nbsp;&nbsp;</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top' align='left' colspan='2'>Total Skor : </td>"+
                                              "<td valign='top' align='right' colspan='2'>"+rs2.getString("nilai_total_gizi")+"&nbsp;&nbsp;</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top' align='left' colspan='4' border='0'>Pasien dengan diagnosis khusus : "+rs2.getString("skrining_gizi_diagnosa_khusus")+(rs2.getString("skrining_gizi_ket_diagnosa_khusus").equals("")?"":", "+rs2.getString("skrining_gizi_ket_diagnosa_khusus"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top' align='left' colspan='4' border='0'>Sudah dibaca dan diketahui oleh Dietisen : "+rs2.getString("skrining_gizi_diketahui_dietisen")+(rs2.getString("skrining_gizi_jam_diketahui_dietisen").equals("")?"":", "+rs2.getString("skrining_gizi_jam_diketahui_dietisen"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>ASESMEN/PENILAIAN KEBIDANAN :</td>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>RENCANA KEBIDANAN :</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td>"+rs2.getString("masalah")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Kebidanan Rawat Inap: "+e);
        }
    }

    private void menampilkanAsuhanMedisRawatInap(String norawat) {
        try {
            if(chkAsuhanMedisRanap.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_medis_ranap.tanggal,penilaian_medis_ranap.kd_dokter,penilaian_medis_ranap.anamnesis,penilaian_medis_ranap.hubungan,"+
                            "penilaian_medis_ranap.keluhan_utama,penilaian_medis_ranap.rps,penilaian_medis_ranap.rpk,penilaian_medis_ranap.rpd,penilaian_medis_ranap.rpo,"+
                            "penilaian_medis_ranap.alergi,penilaian_medis_ranap.keadaan,penilaian_medis_ranap.gcs,penilaian_medis_ranap.kesadaran,penilaian_medis_ranap.td,"+
                            "penilaian_medis_ranap.nadi,penilaian_medis_ranap.rr,penilaian_medis_ranap.suhu,penilaian_medis_ranap.spo,penilaian_medis_ranap.bb,penilaian_medis_ranap.tb,"+
                            "penilaian_medis_ranap.kepala,penilaian_medis_ranap.mata,penilaian_medis_ranap.gigi,penilaian_medis_ranap.tht,penilaian_medis_ranap.thoraks,"+
                            "penilaian_medis_ranap.jantung,penilaian_medis_ranap.paru,penilaian_medis_ranap.abdomen,penilaian_medis_ranap.ekstremitas,penilaian_medis_ranap.genital,"+
                            "penilaian_medis_ranap.kulit,penilaian_medis_ranap.ket_fisik,penilaian_medis_ranap.ket_lokalis,penilaian_medis_ranap.lab,penilaian_medis_ranap.rad,"+
                            "penilaian_medis_ranap.penunjang,penilaian_medis_ranap.diagnosis,penilaian_medis_ranap.tata,penilaian_medis_ranap.edukasi,dokter.nm_dokter "+
                            "from penilaian_medis_ranap inner join dokter on penilaian_medis_ranap.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_medis_ranap.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Inap Umum</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Keadaan Umum : "+rs2.getString("keadaan")+"</td>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='25%' border='0'>SpO2 : "+rs2.getString("spo")+" %</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Mata : "+rs2.getString("mata")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Gigi & Mulut : "+rs2.getString("gigi")+"</td>"+
                                               "<td width='25%' border='0'>THT : "+rs2.getString("tht")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Jantung : "+rs2.getString("jantung")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Paru : "+rs2.getString("paru")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                               "<td width='25%' border='0'>Genital & Anus : "+rs2.getString("genital")+"</td>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Kulit : "+rs2.getString("kulit")+"</td>"+
                                               "<td width='75%' border='0' colpan='3'>Keterangan Fisik : "+rs2.getString("ket_fisik").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS LOKALIS"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("ket_lokalis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Laboratorium : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Radiologi : "+rs2.getString("rad").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Penunjang Lainnya : "+rs2.getString("penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("tata").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. EDUKASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan medis Rawat Inap: "+e);
        }
    }

    private void menampilkanAsuhanMedisRawatInapKebidanan(String norawat) {
        try {
            if(chkAsuhanMedisRanapKandungan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_medis_ranap_kandungan.tanggal,penilaian_medis_ranap_kandungan.kd_dokter,penilaian_medis_ranap_kandungan.anamnesis,"+
                            "penilaian_medis_ranap_kandungan.hubungan,penilaian_medis_ranap_kandungan.keluhan_utama,penilaian_medis_ranap_kandungan.rps,"+
                            "penilaian_medis_ranap_kandungan.rpk,penilaian_medis_ranap_kandungan.rpd,penilaian_medis_ranap_kandungan.rpo,penilaian_medis_ranap_kandungan.alergi,"+
                            "penilaian_medis_ranap_kandungan.keadaan,penilaian_medis_ranap_kandungan.gcs,penilaian_medis_ranap_kandungan.kesadaran,penilaian_medis_ranap_kandungan.td,"+
                            "penilaian_medis_ranap_kandungan.nadi,penilaian_medis_ranap_kandungan.rr,penilaian_medis_ranap_kandungan.suhu,penilaian_medis_ranap_kandungan.spo,"+
                            "penilaian_medis_ranap_kandungan.bb,penilaian_medis_ranap_kandungan.tb,penilaian_medis_ranap_kandungan.kepala,penilaian_medis_ranap_kandungan.mata,"+
                            "penilaian_medis_ranap_kandungan.gigi,penilaian_medis_ranap_kandungan.tht,penilaian_medis_ranap_kandungan.thoraks,penilaian_medis_ranap_kandungan.jantung,"+
                            "penilaian_medis_ranap_kandungan.paru,penilaian_medis_ranap_kandungan.abdomen,penilaian_medis_ranap_kandungan.ekstremitas,"+
                            "penilaian_medis_ranap_kandungan.genital,penilaian_medis_ranap_kandungan.kulit,penilaian_medis_ranap_kandungan.ket_fisik,"+
                            "penilaian_medis_ranap_kandungan.tfu,penilaian_medis_ranap_kandungan.tbj,penilaian_medis_ranap_kandungan.his,penilaian_medis_ranap_kandungan.kontraksi,"+
                            "penilaian_medis_ranap_kandungan.djj,penilaian_medis_ranap_kandungan.inspeksi,penilaian_medis_ranap_kandungan.inspekulo,penilaian_medis_ranap_kandungan.vt,"+
                            "penilaian_medis_ranap_kandungan.rt,penilaian_medis_ranap_kandungan.ultra,penilaian_medis_ranap_kandungan.kardio,penilaian_medis_ranap_kandungan.lab,"+
                            "penilaian_medis_ranap_kandungan.diagnosis,penilaian_medis_ranap_kandungan.tata,penilaian_medis_ranap_kandungan.edukasi,dokter.nm_dokter "+
                            "from penilaian_medis_ranap_kandungan inner join dokter on penilaian_medis_ranap_kandungan.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_medis_ranap_kandungan.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Inap Kebidanan</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Keadaan Umum : "+rs2.getString("keadaan")+"</td>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='25%' border='0'>SpO2 : "+rs2.getString("spo")+" %</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Mata : "+rs2.getString("mata")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Gigi & Mulut : "+rs2.getString("gigi")+"</td>"+
                                               "<td width='25%' border='0'>THT : "+rs2.getString("tht")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Jantung : "+rs2.getString("jantung")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Paru : "+rs2.getString("paru")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                               "<td width='25%' border='0'>Genital & Anus : "+rs2.getString("genital")+"</td>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Kulit : "+rs2.getString("kulit")+"</td>"+
                                               "<td width='75%' border='0' colpan='3'>Keterangan Fisik : "+rs2.getString("ket_fisik").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS OBSTETRI / GINEKOLOGI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' colspan='2'>TFU : "+rs2.getString("tfu")+" Cm    TBJ : "+rs2.getString("tbj")+" gram    HIS : "+rs2.getString("his")+" x/10 Menit    Kontraksi : "+rs2.getString("kontraksi")+"    DJJ : "+rs2.getString("djj")+" Dpm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%'>Inspeksi : "+rs2.getString("inspeksi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='50%'>VT : "+rs2.getString("vt").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%'>Inspekulo : "+rs2.getString("inspekulo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                               "<td width='50%'>RT : "+rs2.getString("rt").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Ultrasonografi : "+rs2.getString("ultra").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Kardiotokografi : "+rs2.getString("kardio").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Laboratorium : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("tata").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. EDUKASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan medis Rawat Inap Kebidanan : "+e);
        }
    }

    private void menampilkanPemeriksaanRanap(String norawat) {
        try {
            if(chkPemeriksaanRanap.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi,pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi," +
                            "pemeriksaan_ranap.tinggi,pemeriksaan_ranap.berat,pemeriksaan_ranap.spo2,pemeriksaan_ranap.gcs,pemeriksaan_ranap.kesadaran,pemeriksaan_ranap.keluhan,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl," +
                            "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat,pemeriksaan_ranap.nip,pegawai.nama,pegawai.jbtn,pemeriksaan_ranap.instruksi,pemeriksaan_ranap.evaluasi "+
                            "from pemeriksaan_ranap inner join pegawai on pemeriksaan_ranap.nip=pegawai.nik where pemeriksaan_ranap.no_rawat='"+norawat+"' order by pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Pemeriksaan Rawat Inap</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='51%' bgcolor='#FFFAF8' colspan='6'>Dokter/Paramedis</td>"+
                                    "<td valign='top' width='30%' bgcolor='#FFFAF8' colspan='3'>Profesi/Jabatan/Departemen</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top' colspan='6'>"+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                    "<td valign='top' colspan='3'>"+rs2.getString("jbtn")+"</td>"+
                                 "</tr>");   

                            if(!rs2.getString("keluhan").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Subjek</td>"+
                                        "<td valign='top' colspan='7'> : "+rs2.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("pemeriksaan").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Objek</td>"+
                                        "<td valign='top' colspan='7'> : "+rs2.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>Tensi</td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>Nadi(/menit)</td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>Respirasi(/menit)</td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>Tinggi(Cm)</td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>Berat(Kg)</td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>SpO2(%)</td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                        "<td valign='top' width='9%' align='center' bgcolor='#FFFAF8'>Kesadaran</td>"+
                                     "</tr>"+
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("suhu_tubuh")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("tensi")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("nadi")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("respirasi")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("tinggi")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("berat")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("spo2")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("gcs")+"</td>"+
                                        "<td valign='top' align='center'>"+rs2.getString("kesadaran")+"</td>"+
                                     "</tr>");

                            if(!rs2.getString("alergi").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Alergi</td>"+
                                        "<td valign='top' colspan='7'> : "+rs2.getString("alergi")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("penilaian").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Asesmen</td>"+
                                        "<td valign='top' colspan='7'> : "+rs2.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("rtl").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Plan</td>"+
                                        "<td valign='top' colspan='7'> : "+rs2.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("instruksi").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Instruksi</td>"+
                                        "<td valign='top' colspan='7'> : "+rs2.getString("instruksi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("evaluasi").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Evaluasi</td>"+
                                        "<td valign='top' colspan='7'> : "+rs2.getString("evaluasi")+"</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan pemeriksaan obstetri rawat inap
            if(chkPemeriksaanObstetriRanap.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat,pemeriksaan_obstetri_ranap.tinggi_uteri,pemeriksaan_obstetri_ranap.janin,pemeriksaan_obstetri_ranap.letak, " +
                            "pemeriksaan_obstetri_ranap.panggul,pemeriksaan_obstetri_ranap.denyut,pemeriksaan_obstetri_ranap.kontraksi, " +
                            "pemeriksaan_obstetri_ranap.kualitas_mnt,pemeriksaan_obstetri_ranap.kualitas_dtk,pemeriksaan_obstetri_ranap.fluksus,pemeriksaan_obstetri_ranap.albus, " +
                            "pemeriksaan_obstetri_ranap.vulva,pemeriksaan_obstetri_ranap.portio,pemeriksaan_obstetri_ranap.dalam, pemeriksaan_obstetri_ranap.tebal, pemeriksaan_obstetri_ranap.arah, pemeriksaan_obstetri_ranap.pembukaan," +
                            "pemeriksaan_obstetri_ranap.penurunan, pemeriksaan_obstetri_ranap.denominator, pemeriksaan_obstetri_ranap.ketuban, pemeriksaan_obstetri_ranap.feto " +
                            "from pemeriksaan_obstetri_ranap where pemeriksaan_obstetri_ranap.no_rawat='"+norawat+"' order by pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Pemeriksaan Obstetri Rawat Inap</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tinggi Fundus</td>"+
                                    "<td valign='top' width='6%' bgcolor='#FFFAF8'>Janin</td>"+
                                    "<td valign='top' width='6%' bgcolor='#FFFAF8'>Letak</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Panggul</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Denyut</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kontraksi</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Mnt</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Detik</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Fluksus</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Albus</td>"+
                                    "<td valign='top' width='6%' bgcolor='#FFFAF8'>Dalam</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tebal</td>"+
                                    "<td valign='top' width='6%' bgcolor='#FFFAF8'>Arah</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Ketuban</td>"+
                                    "<td valign='top' width='7%' bgcolor='#FFFAF8'>Feto</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tinggi_uteri")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("janin")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("letak")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("panggul")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("denyut")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("kontraksi")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("kualitas_mnt")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("kualitas_dtk")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("fluksus")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("albus")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("dalam")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tebal")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("arah")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("ketuban")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("feto")+"</td>"+
                                 "</tr>"); 
                            if(!rs2.getString("vulva").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Vulva</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("vulva")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("portio").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Portio</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("portio")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("pembukaan").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Pembukaan</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("pembukaan")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("penurunan").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Penurunan</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("penurunan")+"</td>"+
                                     "</tr>");
                            }

                            if(!rs2.getString("denominator").equals("")){
                                htmlContent.append(
                                     "<tr>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' align='center'></td>"+
                                        "<td valign='top' colspan='2'>Denominator</td>"+
                                        "<td valign='top' colspan='13'> : "+rs2.getString("denominator")+"</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan pemeriksaan genekologi rawat inap
            if(chkPemeriksaanGenekologiRanap.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat,pemeriksaan_ginekologi_ranap.inspeksi,pemeriksaan_ginekologi_ranap.inspeksi_vulva,pemeriksaan_ginekologi_ranap.inspekulo_gine, " +
                            "pemeriksaan_ginekologi_ranap.fluxus_gine,pemeriksaan_ginekologi_ranap.fluor_gine,pemeriksaan_ginekologi_ranap.vulva_inspekulo, " +
                            "pemeriksaan_ginekologi_ranap.portio_inspekulo,pemeriksaan_ginekologi_ranap.sondage,pemeriksaan_ginekologi_ranap.portio_dalam,pemeriksaan_ginekologi_ranap.bentuk, " +
                            "pemeriksaan_ginekologi_ranap.cavum_uteri,pemeriksaan_ginekologi_ranap.mobilitas,pemeriksaan_ginekologi_ranap.ukuran, pemeriksaan_ginekologi_ranap.nyeri_tekan, pemeriksaan_ginekologi_ranap.adnexa_kanan, pemeriksaan_ginekologi_ranap.adnexa_kiri," +
                            "pemeriksaan_ginekologi_ranap.cavum_douglas " +
                            "from pemeriksaan_ginekologi_ranap where pemeriksaan_ginekologi_ranap.no_rawat='"+norawat+"' order by pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Pemeriksaan Ginekologi Rawat Inap</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='81%' bgcolor='#FFFAF8'>Pemeriksaan</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top'>"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>Inspeksi</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("inspeksi")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Uretra/Vagina</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("inspeksi_vulva")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>Inspekulo</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("inspekulo_gine")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Fluxus</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("fluxus_gine")+",&nbsp;&nbsp;Fluor Albus : "+rs2.getString("fluor_gine")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Vagina</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("vulva_inspekulo")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("portio_inspekulo")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Sondage</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("sondage")+"</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>Pemeriksaan Dalam</td>"+
                                               "<td border='0' valign='top' width='70%'>:</td>"+
                                            "</tr>"+
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("portio_dalam")+",&nbsp;&nbsp;Bentuk : "+rs2.getString("bentuk")+"</td>"+
                                            "</tr>"+   
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Uteri</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("cavum_uteri")+",&nbsp;&nbsp;Mobilitas : "+rs2.getString("mobilitas")+"</td>"+
                                            "</tr>"+ 
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;</td>"+
                                               "<td border='0' valign='top' width='70%'>&nbsp;&nbsp;&nbsp;Ukuran : "+rs2.getString("ukuran")+",&nbsp;&nbsp;Nyeri Tekan : "+rs2.getString("nyeri_tekan")+"</td>"+
                                            "</tr>"+ 
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Adnexa/Parametrium</td>"+
                                               "<td border='0' valign='top' width='70%'>: Kanan : "+rs2.getString("adnexa_kanan")+",&nbsp;&nbsp;Kiri : "+rs2.getString("adnexa_kiri")+"</td>"+
                                            "</tr>"+ 
                                            "<tr align='left'>"+
                                               "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Douglas</td>"+
                                               "<td border='0' valign='top' width='70%'>: "+rs2.getString("cavum_douglas")+"</td>"+
                                            "</tr>"+ 
                                        "</table>"+
                                    "</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan catatan observasi rawat inap
            if(chkCatatanObservasiRanap.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select catatan_observasi_ranap.tgl_perawatan,catatan_observasi_ranap.jam_rawat,catatan_observasi_ranap.gcs,"+
                            "catatan_observasi_ranap.td,catatan_observasi_ranap.hr,catatan_observasi_ranap.rr,catatan_observasi_ranap.suhu,catatan_observasi_ranap.spo2,"+
                            "catatan_observasi_ranap.nip,petugas.nama from catatan_observasi_ranap inner join petugas on catatan_observasi_ranap.nip=petugas.nip "+
                            "where catatan_observasi_ranap.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Catatan Observasi Rawat Inap</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='middle' width='4%' bgcolor='#FFFAF8' rowspan='2'>No.</td>"+
                                    "<td valign='middle' width='15%' bgcolor='#FFFAF8' rowspan='2'>Tanggal</td>"+
                                    "<td valign='top' width='58%' bgcolor='#FFFAF8' colspan='6'>Monitoring</td>"+
                                    "<td valign='middle' width='23%' bgcolor='#FFFAF8' rowspan='2'>Perawat/Paramedis</td>"+
                                 "</tr>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='11%' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                    "<td valign='top' width='10%' bgcolor='#FFFAF8'>TD</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>HR (/menit)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>RR (/menit)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>SpO2(%)</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("gcs")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("td")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("hr")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("rr")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("suhu")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("spo2")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan catatan observasi rawat inap kebidanan
            if(chkCatatanObservasiRanapKebidanan.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select catatan_observasi_ranap_kebidanan.tgl_perawatan,catatan_observasi_ranap_kebidanan.jam_rawat,catatan_observasi_ranap_kebidanan.gcs,"+
                            "catatan_observasi_ranap_kebidanan.td,catatan_observasi_ranap_kebidanan.hr,catatan_observasi_ranap_kebidanan.rr,catatan_observasi_ranap_kebidanan.suhu,"+
                            "catatan_observasi_ranap_kebidanan.spo2,catatan_observasi_ranap_kebidanan.kontraksi,catatan_observasi_ranap_kebidanan.bjj,catatan_observasi_ranap_kebidanan.ppv,"+
                            "catatan_observasi_ranap_kebidanan.vt,catatan_observasi_ranap_kebidanan.nip,petugas.nama from catatan_observasi_ranap_kebidanan "+
                            "inner join petugas on catatan_observasi_ranap_kebidanan.nip=petugas.nip "+
                            "where catatan_observasi_ranap_kebidanan.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Catatan Observasi Rawat Inap Kebidanan</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='middle' width='4%' bgcolor='#FFFAF8' rowspan='2'>No.</td>"+
                                    "<td valign='middle' width='15%' bgcolor='#FFFAF8' rowspan='2'>Tanggal</td>"+
                                    "<td valign='top' width='58%' bgcolor='#FFFAF8' colspan='6'>Monitoring</td>"+
                                    "<td valign='middle' width='23%' bgcolor='#FFFAF8' rowspan='2'>Bidan</td>"+
                                 "</tr>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='11%' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                    "<td valign='top' width='10%' bgcolor='#FFFAF8'>TD</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>HR (/menit)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>RR (/menit)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>SpO2(%)</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center' rowspan='3'>"+w+"</td>"+
                                    "<td valign='top' rowspan='3'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("gcs")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("td")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("hr")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("rr")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("suhu")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("spo2")+"</td>"+
                                    "<td valign='top' rowspan='3'>"+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                 "</tr>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' bgcolor='#FFFAF8'>Kotraksi/HIS</td>"+
                                    "<td valign='top' bgcolor='#FFFAF8'>BJJ</td>"+
                                    "<td valign='top' bgcolor='#FFFAF8'>PPV</td>"+
                                    "<td valign='top' bgcolor='#FFFAF8' colspan='3'>VT</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+rs2.getString("kontraksi")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("bjj")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("ppv")+"</td>"+
                                    "<td valign='top' colspan='3'>"+rs2.getString("vt")+"</td>"+
                                 "</tr>"
                            );                                        
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

            //menampilkan catatan observasi rawat inap post partum
            if(chkCatatanObservasiRanapPostPartum.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select catatan_observasi_ranap_postpartum.tgl_perawatan,catatan_observasi_ranap_postpartum.jam_rawat,catatan_observasi_ranap_postpartum.gcs,"+
                            "catatan_observasi_ranap_postpartum.td,catatan_observasi_ranap_postpartum.hr,catatan_observasi_ranap_postpartum.rr,catatan_observasi_ranap_postpartum.suhu,catatan_observasi_ranap_postpartum.spo2,"+
                            "catatan_observasi_ranap_postpartum.tfu,catatan_observasi_ranap_postpartum.kontraksi,catatan_observasi_ranap_postpartum.perdarahan,catatan_observasi_ranap_postpartum.keterangan,"+
                            "catatan_observasi_ranap_postpartum.nip,petugas.nama from catatan_observasi_ranap_postpartum "+
                            "inner join petugas on catatan_observasi_ranap_postpartum.nip=petugas.nip "+
                            "where catatan_observasi_ranap_postpartum.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Catatan Observasi Rawat Inap Post Partum</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='middle' width='4%' bgcolor='#FFFAF8' rowspan='2'>No.</td>"+
                                    "<td valign='middle' width='15%' bgcolor='#FFFAF8' rowspan='2'>Tanggal</td>"+
                                    "<td valign='top' width='58%' bgcolor='#FFFAF8' colspan='6'>Monitoring</td>"+
                                    "<td valign='middle' width='23%' bgcolor='#FFFAF8' rowspan='2'>Bidan</td>"+
                                 "</tr>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='11%' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                    "<td valign='top' width='10%' bgcolor='#FFFAF8'>TD</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>HR (/menit)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>RR (/menit)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                    "<td valign='top' width='9%' bgcolor='#FFFAF8'>SpO2(%)</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center' rowspan='3'>"+w+"</td>"+
                                    "<td valign='top' rowspan='3'>"+rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("gcs")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("td")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("hr")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("rr")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("suhu")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("spo2")+"</td>"+
                                    "<td valign='top' rowspan='3'>"+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                 "</tr>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' bgcolor='#FFFAF8'>TFU</td>"+
                                    "<td valign='top' bgcolor='#FFFAF8'>Kontraksi</td>"+
                                    "<td valign='top' bgcolor='#FFFAF8' colspan='2'>Perdarahan</td>"+
                                    "<td valign='top' bgcolor='#FFFAF8' colspan='2'>Keterangan</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+rs2.getString("tfu")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("kontraksi")+"</td>"+
                                    "<td valign='top' colspan='2'>"+rs2.getString("perdarahan")+"</td>"+
                                    "<td valign='top' colspan='2'>"+rs2.getString("keterangan")+"</td>"+
                                 "</tr>"
                            );                                        
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
            
            //menampilkan catatan Keperawatan Rawat Inap
            if(chkCatatanKeperawatanRanap.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select catatan_keperawatan_ranap.tanggal,catatan_keperawatan_ranap.jam,catatan_keperawatan_ranap.uraian,catatan_keperawatan_ranap.nip,petugas.nama from catatan_keperawatan_ranap "+
                            "inner join petugas on catatan_keperawatan_ranap.nip=petugas.nip where catatan_keperawatan_ranap.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Catatan Keperawatan Rawat Inap</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='middle' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='middle' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='58%' bgcolor='#FFFAF8'>Uraian</td>"+
                                    "<td valign='middle' width='23%' bgcolor='#FFFAF8'>Petugas</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center' valign='top'>"+w+"</td>"+
                                    "<td valign='top' valign='top'>"+rs2.getString("tanggal")+" "+rs2.getString("jam")+"</td>"+
                                    "<td valign='top' valign='top'>"+rs2.getString("uraian")+"</td>"+
                                    "<td valign='top' valign='top'>"+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                 "</tr>"
                            );                                        
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
        } catch (Exception e) {
            System.out.println("Notif Catatan : "+e);
        }
    }

    private void menampilkanGizi(String norawat) {
        try {
            if(chkSkriningGiziLanjut.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select skrining_gizi.tanggal,skrining_gizi.skrining_bb,skrining_gizi.skrining_tb,skrining_gizi.alergi,"+
                            "skrining_gizi.parameter_imt,skrining_gizi.skor_imt,skrining_gizi.parameter_bb,skrining_gizi.skor_bb,skrining_gizi.parameter_penyakit,"+
                            "skrining_gizi.skor_penyakit,skrining_gizi.skor_total,skrining_gizi.parameter_total,skrining_gizi.nip,petugas.nama "+
                            "from skrining_gizi inner join petugas on skrining_gizi.nip=petugas.nip where "+
                            "skrining_gizi.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Skrining Gizi Lanjut</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='25%' bgcolor='#FFFAF8'>Pemeriksaan</td>"+
                                    "<td valign='top' width='40%' bgcolor='#FFFAF8'>Parameter</td>"+
                                    "<td valign='top' width='5%' bgcolor='#FFFAF8'>Skor</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Ahli Gizi</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' rowspan='2'>"+rs2.getString("tanggal")+"</td>"+
                                    "<td valign='top'>"+
                                            "BB : "+rs2.getString("skrining_bb")+"<br>"+
                                            "TB : "+rs2.getString("skrining_tb")+"<br>"+
                                            "Alergi : "+rs2.getString("alergi")+"<br>"+
                                    "</td>"+
                                    "<td valign='top'>"+
                                            "1. Skor IMT : "+rs2.getString("parameter_imt")+"<br>"+
                                            "2. Skor Kehilangan BB : "+rs2.getString("parameter_bb")+"<br>"+
                                            "3. Skor Efek Penyakit Akut : "+rs2.getString("parameter_penyakit")+"<br>"+
                                    "</td>"+
                                    "<td valign='top' align='center'>"+
                                            rs2.getString("skor_imt")+"<br>"+
                                            rs2.getString("skor_bb")+"<br>"+
                                            rs2.getString("skor_penyakit")+"<br>"+
                                    "</td>"+
                                    "<td valign='top' align='center' rowspan='2'>"+rs2.getString("nip")+"<br>"+rs2.getString("nama")+"</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>Total Skor : </td>"+
                                    "<td valign='top'>"+rs2.getString("parameter_total")+"</td>"+
                                    "<td valign='top' align='center'>"+rs2.getString("skor_total")+"</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan asuhan gizi
            if(chkAsuhanGizi.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select asuhan_gizi.tanggal,asuhan_gizi.antropometri_bb,asuhan_gizi.antropometri_tb,asuhan_gizi.antropometri_imt,asuhan_gizi.antropometri_lla,"+
                            "asuhan_gizi.antropometri_tl,asuhan_gizi.antropometri_ulna,asuhan_gizi.antropometri_bbideal,asuhan_gizi.antropometri_bbperu,"+
                            "asuhan_gizi.antropometri_tbperu,asuhan_gizi.antropometri_bbpertb,asuhan_gizi.antropometri_llaperu,asuhan_gizi.biokimia,"+
                            "asuhan_gizi.fisik_klinis,asuhan_gizi.alergi_telur,asuhan_gizi.alergi_susu_sapi,asuhan_gizi.alergi_kacang,asuhan_gizi.alergi_gluten,"+
                            "asuhan_gizi.alergi_udang,asuhan_gizi.alergi_ikan,asuhan_gizi.alergi_hazelnut,asuhan_gizi.pola_makan,asuhan_gizi.riwayat_personal,"+
                            "asuhan_gizi.diagnosis,asuhan_gizi.intervensi_gizi,asuhan_gizi.monitoring_evaluasi,asuhan_gizi.nip,petugas.nama "+
                            "from asuhan_gizi inner join petugas on asuhan_gizi.nip=petugas.nip where "+
                            "asuhan_gizi.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Asuhan Gizi</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='96%' bgcolor='#FFFAF8'>Penilaian Gizi</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='20%' border='0'>Tanggal</td><td border='0'>:</td><td width='79%' border='0'>"+rs2.getString("tanggal")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='20%' border='0'>Antropometri</td><td border='0'>:</td><td width='79%' border='0'>BB : "+rs2.getString("antropometri_bb")+" (Kg), TB : "+rs2.getString("antropometri_tb")+" (Cm), IMT : "+rs2.getString("antropometri_imt")+" (Kg/Cm), LiLA : "+rs2.getString("antropometri_lla")+" (Cm), TL : "+rs2.getString("antropometri_tl")+" (Cm), ULNA : "+rs2.getString("antropometri_ulna")+" (Cm), BB Ideal : "+rs2.getString("antropometri_bbideal")+" (Kg), BB/U : "+rs2.getString("antropometri_bbperu")+" (%), TB/U : "+rs2.getString("antropometri_tbperu")+" (%), BB/TB : "+rs2.getString("antropometri_bbpertb")+" (%), LiLA/U : "+rs2.getString("antropometri_llaperu")+" (%)</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='20%' border='0'>Biokimia</td><td border='0'>:</td><td width='79%' border='0'> "+rs2.getString("biokimia")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                             "<td width='20%' border='0'>Fisik/Klinis</td><td border='0'>:</td><td width='79%' border='0'> "+rs2.getString("fisik_klinis")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                             "<td width='20%' border='0'>Riwayat Gizi</td><td border='0'>:</td><td width='79%' border='0'><br>"+
                                                 "Alergi Makanan : <br>"+
                                                 "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                     "<tr>"+
                                                        "<td width='70%' border='0'>&nbsp;&nbsp;Telur</td><td border='0'>: "+rs2.getString("alergi_telur")+"</td>"+
                                                     "</tr>"+
                                                     "<tr>"+
                                                        "<td width='70%' border='0'>&nbsp;&nbsp;Susu sapi dan produk olahannya</td><td border='0'>: "+rs2.getString("alergi_susu_sapi")+"</td>"+
                                                     "</tr>"+
                                                     "<tr>"+
                                                        "<td width='70%' border='0'>&nbsp;&nbsp;Kacang kedelai / tanah</td><td border='0'>: "+rs2.getString("alergi_kacang")+"</td>"+
                                                     "</tr>"+
                                                     "<tr>"+
                                                        "<td width='70%' border='0'>&nbsp;&nbsp;Gluten / gandum</td><td border='0'>: "+rs2.getString("alergi_gluten")+"</td>"+
                                                     "</tr>"+
                                                     "<tr>"+
                                                        "<td width='70%' border='0'>&nbsp;&nbsp;Udang</td><td border='0'>: "+rs2.getString("alergi_udang")+"</td>"+
                                                     "</tr>"+
                                                     "<tr>"+
                                                        "<td width='70%' border='0'>&nbsp;&nbsp;Ikan</td><td border='0'>: "+rs2.getString("alergi_ikan")+"</td>"+
                                                     "</tr>"+
                                                     "<tr>"+
                                                        "<td width='70%' border='0'>&nbsp;&nbsp;Hazelnut / almont</td><td border='0'>: "+rs2.getString("alergi_hazelnut")+"</td>"+
                                                     "</tr>"+
                                                 "</table><br>"+
                                                 "Pola Makan : "+rs2.getString("pola_makan")+
                                             "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                             "<td width='20%' border='0'>Riwayat Personal</td><td border='0'>:</td><td width='79%' border='0'> "+rs2.getString("riwayat_personal")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                             "<td width='20%' border='0'>Diagnosis Gizi</td><td border='0'>:</td><td width='79%' border='0'> "+rs2.getString("diagnosis")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                             "<td width='20%' border='0'>Intervensi Gizi</td><td border='0'>:</td><td width='79%' border='0'> "+rs2.getString("intervensi_gizi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                             "<td width='20%' border='0'>Monitoring & Evaluasi</td><td border='0'>:</td><td width='79%' border='0'> "+rs2.getString("monitoring_evaluasi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='20%' border='0'>Petugas</td><td border='0'>:</td><td width='79%' border='0'>"+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }

            //menampilkan monitoring gizi
            if(chkMonitoringGizi.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select monitoring_asuhan_gizi.tanggal,monitoring_asuhan_gizi.monitoring,monitoring_asuhan_gizi.evaluasi,"+
                            "monitoring_asuhan_gizi.nip,petugas.nama from monitoring_asuhan_gizi inner join petugas on monitoring_asuhan_gizi.nip=petugas.nip where "+
                            "monitoring_asuhan_gizi.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Monitoring & Evaluasi Asuhan Gizi</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                    "<td valign='top' width='31%' bgcolor='#FFFAF8'>Monitoring</td>"+
                                    "<td valign='top' width='30%' bgcolor='#FFFAF8'>Evaluasi</td>"+
                                    "<td valign='top' width='20%' bgcolor='#FFFAF8'>Petugas</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tanggal")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("monitoring")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("evaluasi")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("nama")+"</td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notif Gizi : "+e);
        }
    }

    private void menampilkanAsuhanPsikolog(String norawat) {
        try {
            if(chkAsuhanPsikolog.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_psikologi.tanggal,penilaian_psikologi.nip,penilaian_psikologi.anamnesis,penilaian_psikologi.dikirim_dari,"+
                            "penilaian_psikologi.tujuan_pemeriksaan,penilaian_psikologi.ket_anamnesis,penilaian_psikologi.rupa,penilaian_psikologi.bentuk_tubuh,"+
                            "penilaian_psikologi.tindakan,penilaian_psikologi.pakaian,penilaian_psikologi.ekspresi,penilaian_psikologi.berbicara,"+
                            "penilaian_psikologi.penggunaan_kata,penilaian_psikologi.ciri_menyolok,penilaian_psikologi.hasil_psikotes,penilaian_psikologi.kepribadian,"+
                            "penilaian_psikologi.psikodinamika,penilaian_psikologi.kesimpulan_psikolog,petugas.nama "+
                            "from penilaian_psikologi inner join petugas on penilaian_psikologi.nip=petugas.nip "+
                            "where penilaian_psikologi.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Psikologi</td>"+
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
                                              "<td width='21%' border='0'>Tanggal : "+rs2.getString("tanggal")+"</td>"+
                                              "<td width='35%' border='0'>Psikolog : "+rs2.getString("nip")+" "+rs2.getString("nama")+"</td>"+
                                              "<td width='22%' border='0'>Tujuan Pemeriksaan : "+rs2.getString("tujuan_pemeriksaan")+"</td>"+
                                              "<td width='22%' border='0'>Dikirim Dari : "+rs2.getString("dikirim_dari")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='21%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+"</td>"+
                                              "<td width='79%' border='0' colspan='3'>Keterangan Anamnesis : "+rs2.getString("ket_anamnesis")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. OBSERVASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>Rupa / Wajah : "+rs2.getString("rupa")+"</td>"+
                                              "<td width='33%' border='0'>Pakaian / Aksesoris : "+rs2.getString("pakaian")+"</td>"+
                                              "<td width='33%' border='0'>Penyampaian / Ekspresi : "+rs2.getString("ekspresi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>Tindakan : "+rs2.getString("tindakan")+"</td>"+
                                              "<td width='33%' border='0'>Bentuk Tubuh : "+rs2.getString("bentuk_tubuh")+"</td>"+
                                              "<td width='33%' border='0'>Penggunaan Kata : "+rs2.getString("penggunaan_kata")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='99%' border='0' colspan='3'>Berbicara : "+rs2.getString("berbicara")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. CIRI YANG MENYOLOK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%'>"+rs2.getString("ciri_menyolok").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. HASIL PSIKOTES"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>"+rs2.getString("hasil_psikotes").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. KEPRIBADIAN DAN ASPEK-ASPEKNYA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>"+rs2.getString("kepribadian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. PSIKODINAMIKA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>"+rs2.getString("psikodinamika").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. KESIMPULAN PSIKOLOG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>"+rs2.getString("kesimpulan_psikolog").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Fisioterapi : "+e);
        }
    }

    private void menampilkanAsuhanKeperawatanRawatInap(String norawat) {
        try {
            if(chkAsuhanKeperawatanRanap.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_awal_keperawatan_ranap.tanggal,penilaian_awal_keperawatan_ranap.informasi,penilaian_awal_keperawatan_ranap.ket_informasi,penilaian_awal_keperawatan_ranap.tiba_diruang_rawat,"+
                            "penilaian_awal_keperawatan_ranap.kasus_trauma,penilaian_awal_keperawatan_ranap.cara_masuk,penilaian_awal_keperawatan_ranap.rps,penilaian_awal_keperawatan_ranap.rpd,penilaian_awal_keperawatan_ranap.rpk,penilaian_awal_keperawatan_ranap.rpo,"+
                            "penilaian_awal_keperawatan_ranap.riwayat_pembedahan,penilaian_awal_keperawatan_ranap.riwayat_dirawat_dirs,penilaian_awal_keperawatan_ranap.alat_bantu_dipakai,penilaian_awal_keperawatan_ranap.riwayat_kehamilan,"+
                            "penilaian_awal_keperawatan_ranap.riwayat_kehamilan_perkiraan,penilaian_awal_keperawatan_ranap.riwayat_tranfusi,penilaian_awal_keperawatan_ranap.riwayat_alergi,penilaian_awal_keperawatan_ranap.riwayat_merokok,"+
                            "penilaian_awal_keperawatan_ranap.riwayat_merokok_jumlah,penilaian_awal_keperawatan_ranap.riwayat_alkohol,penilaian_awal_keperawatan_ranap.riwayat_alkohol_jumlah,penilaian_awal_keperawatan_ranap.riwayat_narkoba,"+
                            "penilaian_awal_keperawatan_ranap.riwayat_olahraga,penilaian_awal_keperawatan_ranap.pemeriksaan_mental,penilaian_awal_keperawatan_ranap.pemeriksaan_keadaan_umum,penilaian_awal_keperawatan_ranap.pemeriksaan_gcs,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_td,penilaian_awal_keperawatan_ranap.pemeriksaan_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_rr,penilaian_awal_keperawatan_ranap.pemeriksaan_suhu,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_spo2,penilaian_awal_keperawatan_ranap.pemeriksaan_bb,penilaian_awal_keperawatan_ranap.pemeriksaan_tb,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kepala_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_wajah_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_leher,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang,penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_kejang_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_susunan_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_denyut_nadi,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_sirkulasi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_kardiovaskuler_pulsasi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_pola_nafas,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_retraksi,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_suara_nafas,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_volume_pernafasan,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_jenis_pernafasan_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_irama_nafas,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_respirasi_batuk,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_mulut_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_gigi_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_lidah_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_tenggorokan_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_abdomen_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_peistatik_usus,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_gastrointestinal_anus,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pengelihatan_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_alat_bantu_penglihatan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_pendengaran,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_bicara_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_sensorik,penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_motorik,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_neurologi_kekuatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_warnakulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_turgor,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_integument_kulit,penilaian_awal_keperawatan_ranap.pemeriksaan_integument_dekubitas,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_pergerakan_sendi,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_kekauatan_otot,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_nyeri_sendi_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_oedema_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_muskuloskletal_fraktur_keterangan,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_jumlah,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_frekuensi_durasi,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_konsistensi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bab_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_jumlah,"+
                            "penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_frekuensi_durasi,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_warna,penilaian_awal_keperawatan_ranap.pemeriksaan_eliminasi_bak_lainlain,"+
                            "penilaian_awal_keperawatan_ranap.pola_aktifitas_makanminum,penilaian_awal_keperawatan_ranap.pola_aktifitas_mandi,penilaian_awal_keperawatan_ranap.pola_aktifitas_eliminasi,penilaian_awal_keperawatan_ranap.pola_aktifitas_berpakaian,"+
                            "penilaian_awal_keperawatan_ranap.pola_aktifitas_berpindah,penilaian_awal_keperawatan_ranap.pola_nutrisi_frekuesi_makan,penilaian_awal_keperawatan_ranap.pola_nutrisi_jenis_makanan,penilaian_awal_keperawatan_ranap.pola_nutrisi_porsi_makan,"+
                            "penilaian_awal_keperawatan_ranap.pola_tidur_lama_tidur,penilaian_awal_keperawatan_ranap.pola_tidur_gangguan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kemampuan_sehari,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_aktifitas,"+
                            "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_berjalan_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ambulasi,"+
                            "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_atas_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah,"+
                            "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_ekstrimitas_bawah_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_menggenggam_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_koordinasi_keterangan,penilaian_awal_keperawatan_ranap.pengkajian_fungsi_kesimpulan,"+
                            "penilaian_awal_keperawatan_ranap.riwayat_psiko_kondisi_psiko,penilaian_awal_keperawatan_ranap.riwayat_psiko_gangguan_jiwa,penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku,"+
                            "penilaian_awal_keperawatan_ranap.riwayat_psiko_perilaku_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_hubungan_keluarga,penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal,"+
                            "penilaian_awal_keperawatan_ranap.riwayat_psiko_tinggal_keterangan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan,penilaian_awal_keperawatan_ranap.riwayat_psiko_nilai_kepercayaan_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.riwayat_psiko_pendidikan_pj,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan,penilaian_awal_keperawatan_ranap.riwayat_psiko_edukasi_diberikan_keterangan,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_nyeri,penilaian_awal_keperawatan_ranap.penilaian_nyeri_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_penyebab,penilaian_awal_keperawatan_ranap.penilaian_nyeri_kualitas,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_kualitas,penilaian_awal_keperawatan_ranap.penilaian_nyeri_lokasi,penilaian_awal_keperawatan_ranap.penilaian_nyeri_menyebar,penilaian_awal_keperawatan_ranap.penilaian_nyeri_skala,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_nyeri_waktu,penilaian_awal_keperawatan_ranap.penilaian_nyeri_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_ket_hilang,penilaian_awal_keperawatan_ranap.penilaian_nyeri_diberitahukan_dokter,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_nyeri_jam_diberitahukan_dokter,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala1,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai1,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai2,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala3,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala4,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai4,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai5,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_skala6,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhmorse_totalnilai,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala1,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai1,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala2,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai2,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai3,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala4,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai4,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala5,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai5,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai6,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala7,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai7,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala8,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai8,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai9,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala10,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai10,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_skala11,penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_nilai11,"+
                            "penilaian_awal_keperawatan_ranap.penilaian_jatuhsydney_totalnilai,penilaian_awal_keperawatan_ranap.skrining_gizi1,penilaian_awal_keperawatan_ranap.nilai_gizi1,penilaian_awal_keperawatan_ranap.skrining_gizi2,"+
                            "penilaian_awal_keperawatan_ranap.nilai_gizi2,penilaian_awal_keperawatan_ranap.nilai_total_gizi,penilaian_awal_keperawatan_ranap.skrining_gizi_diagnosa_khusus,penilaian_awal_keperawatan_ranap.skrining_gizi_ket_diagnosa_khusus,"+
                            "penilaian_awal_keperawatan_ranap.skrining_gizi_diketahui_dietisen,penilaian_awal_keperawatan_ranap.skrining_gizi_jam_diketahui_dietisen,penilaian_awal_keperawatan_ranap.rencana,"+
                            "penilaian_awal_keperawatan_ranap.nip1,penilaian_awal_keperawatan_ranap.nip2,penilaian_awal_keperawatan_ranap.kd_dokter,pengkaji1.nama as pengkaji1,pengkaji2.nama as pengkaji2,dokter.nm_dokter "+
                            "from penilaian_awal_keperawatan_ranap inner join petugas as pengkaji1 on penilaian_awal_keperawatan_ranap.nip1=pengkaji1.nip "+
                            "inner join petugas as pengkaji2 on penilaian_awal_keperawatan_ranap.nip2=pengkaji2.nip "+
                            "inner join dokter on penilaian_awal_keperawatan_ranap.kd_dokter=dokter.kd_dokter where penilaian_awal_keperawatan_ranap.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Keperawatan Rawat Inap Umum</td>"+
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
                                              "<td width='16%' border='0'>Tanggal</td>"+
                                              "<td width='35%' border='0'>: "+rs2.getString("tanggal")+"</td>"+
                                              "<td width='11%' border='0'>Anamnesis</td>"+
                                              "<td width='38%' border='0'>: "+rs2.getString("informasi")+(rs2.getString("ket_informasi").equals("")?"":", "+rs2.getString("ket_informasi"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='16%' border='0'>Tiba di Ruang Rawat</td>"+
                                              "<td width='35%' border='0'>: "+rs2.getString("tiba_diruang_rawat")+"</td>"+
                                              "<td width='11%' border='0'>Cara Masuk</td>"+
                                              "<td width='38%' border='0'>: "+rs2.getString("cara_masuk")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='16%' border='0'>Macam Kasus</td>"+
                                              "<td width='35%' border='0'>: "+rs2.getString("kasus_trauma")+"</td>"+
                                              "<td width='11%' border='0'>Pengkaji 1</td>"+
                                              "<td width='38%' border='0'>: "+rs2.getString("nip1")+" "+rs2.getString("pengkaji1")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='16%' border='0'>Pengkaji 2</td>"+
                                              "<td width='35%' border='0'>: "+rs2.getString("nip2")+" "+rs2.getString("pengkaji2")+"</td>"+
                                              "<td width='11%' border='0'>DPJP</td>"+
                                              "<td width='38%' border='0'>: "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Saat Ini : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Keluarga : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Pembedahan : "+rs2.getString("riwayat_pembedahan")+"</td>"+
                                              "<td width='50%'>Riwayat Dirawat Di RS : "+rs2.getString("riwayat_dirawat_dirs")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Alat Bantu Yang Dipakai : "+rs2.getString("alat_bantu_dipakai")+"</td>"+
                                              "<td width='50%'>Apakah Dalam Keadaan Hamil / Sedang Menyusui : "+rs2.getString("riwayat_kehamilan")+(rs2.getString("riwayat_kehamilan_perkiraan").equals("")?"":", "+rs2.getString("riwayat_kehamilan_perkiraan"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Transfusi Darah : "+rs2.getString("riwayat_tranfusi")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("riwayat_alergi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%' colspan='2'>Kebiasaan : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'"+
                                                    "<tr>"+
                                                         "<td border='0'>"+
                                                            "Merokok : "+rs2.getString("riwayat_merokok")+(rs2.getString("riwayat_merokok_jumlah").equals("")?"":", "+rs2.getString("riwayat_merokok_jumlah")+" batang/hari")+"&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                            "Alkohol : "+rs2.getString("riwayat_alkohol")+(rs2.getString("riwayat_alkohol_jumlah").equals("")?"":", "+rs2.getString("riwayat_alkohol_jumlah")+" gelas/hari")+"&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                            "Obat Tidur : "+rs2.getString("riwayat_narkoba")+"&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                            "Olah Raga : "+rs2.getString("riwayat_olahraga")+
                                                        "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+  
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%'>"+
                                                   "Kesadaran Mental : "+rs2.getString("pemeriksaan_mental")+"&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "Keadaan Umum : "+rs2.getString("pemeriksaan_keadaan_umum")+"&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "GCS(E,V,M) : "+rs2.getString("pemeriksaan_gcs")+"&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "TD : "+rs2.getString("pemeriksaan_td")+" mmHg&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "Nadi : "+rs2.getString("pemeriksaan_nadi")+" x/menit&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "RR : "+rs2.getString("pemeriksaan_rr")+" x/menit&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "Suhu : "+rs2.getString("pemeriksaan_suhu")+" °C&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "SpO2 : "+rs2.getString("pemeriksaan_spo2")+" %&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "BB : "+rs2.getString("pemeriksaan_bb")+" Kg&nbsp;&nbsp;&nbsp;&nbsp;"+
                                                   "TB : "+rs2.getString("pemeriksaan_tb")+" cm&nbsp;&nbsp;&nbsp;&nbsp;"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Sistem Susunan Saraf Pusat : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='35%' border='0'>"+
                                                            "Kepala : "+rs2.getString("pemeriksaan_susunan_kepala")+(rs2.getString("pemeriksaan_susunan_kepala_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_susunan_kepala_keterangan"))+
                                                         "</td>"+
                                                         "<td width='35%' border='0'>"+
                                                            "Wajah : "+rs2.getString("pemeriksaan_susunan_wajah")+(rs2.getString("pemeriksaan_susunan_wajah_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_susunan_wajah_keterangan"))+
                                                         "</td>"+
                                                         "<td width='30%' border='0'>"+
                                                            "Leher : "+rs2.getString("pemeriksaan_susunan_leher")+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td width='35%' border='0'>"+        
                                                            "Kejang : "+rs2.getString("pemeriksaan_susunan_kejang")+(rs2.getString("pemeriksaan_susunan_kejang_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_susunan_kejang_keterangan"))+
                                                         "</td>"+
                                                         "<td width='65%' border='0' colspan='2'>"+
                                                            "Leher : "+rs2.getString("pemeriksaan_susunan_sensorik")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Kardiovaskuler : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='30%' border='0'>"+
                                                            "Pulsasi : "+rs2.getString("pemeriksaan_kardiovaskuler_pulsasi")+
                                                         "</td>"+
                                                         "<td width='40%' border='0'>"+
                                                            "Sirkulasi : "+rs2.getString("pemeriksaan_kardiovaskuler_sirkulasi")+(rs2.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_kardiovaskuler_sirkulasi_keterangan"))+
                                                         "</td>"+
                                                         "<td width='30%' border='0'>"+
                                                            "Denyut Nadi : "+rs2.getString("pemeriksaan_kardiovaskuler_denyut_nadi")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Respirasi : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='25%' border='0'>"+
                                                            "Retraksi : "+rs2.getString("pemeriksaan_respirasi_retraksi")+
                                                         "</td>"+
                                                         "<td width='25%' border='0'>"+
                                                            "Pola Nafas : "+rs2.getString("pemeriksaan_respirasi_pola_nafas")+
                                                         "</td>"+
                                                         "<td width='25%' border='0'>"+
                                                            "Suara Nafas : "+rs2.getString("pemeriksaan_respirasi_suara_nafas")+
                                                         "</td>"+
                                                         "<td width='25%' border='0'>"+
                                                            "Batuk & Sekresi : "+rs2.getString("pemeriksaan_respirasi_batuk")+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td width='25%' border='0'>"+
                                                            "Volume : "+rs2.getString("pemeriksaan_respirasi_volume_pernafasan")+
                                                         "</td>"+
                                                         "<td width='50%' border='0' colspan='2'>"+
                                                            "Jenis Pernafasaan : "+rs2.getString("pemeriksaan_respirasi_jenis_pernafasan")+(rs2.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_respirasi_jenis_pernafasan_keterangan"))+
                                                         "</td>"+
                                                         "<td width='25%' border='0'>"+
                                                            "Irama : "+rs2.getString("pemeriksaan_respirasi_irama_nafas")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Gastrointestinal : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Mulut : "+rs2.getString("pemeriksaan_gastrointestinal_mulut")+(rs2.getString("pemeriksaan_gastrointestinal_mulut_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_gastrointestinal_mulut_keterangan"))+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Lidah : "+rs2.getString("pemeriksaan_gastrointestinal_lidah")+(rs2.getString("pemeriksaan_gastrointestinal_lidah_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_gastrointestinal_lidah_keterangan"))+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Gigi : "+rs2.getString("pemeriksaan_gastrointestinal_gigi")+(rs2.getString("pemeriksaan_gastrointestinal_gigi_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_gastrointestinal_gigi_keterangan"))+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Tenggorokan : "+rs2.getString("pemeriksaan_gastrointestinal_tenggorokan")+(rs2.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_gastrointestinal_tenggorokan_keterangan"))+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Abdomen : "+rs2.getString("pemeriksaan_gastrointestinal_abdomen")+(rs2.getString("pemeriksaan_gastrointestinal_abdomen_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_gastrointestinal_abdomen_keterangan"))+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Peistatik Usus : "+rs2.getString("pemeriksaan_gastrointestinal_peistatik_usus")+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td width='100%' border='0' colspan='3'>"+
                                                            "Anus : "+rs2.getString("pemeriksaan_gastrointestinal_anus")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Neurologi : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Sensorik : "+rs2.getString("pemeriksaan_neurologi_sensorik")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Penglihatan : "+rs2.getString("pemeriksaan_neurologi_pengelihatan")+(rs2.getString("pemeriksaan_neurologi_pengelihatan_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_neurologi_pengelihatan_keterangan"))+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Alat Bantu Penglihatan : "+rs2.getString("pemeriksaan_neurologi_alat_bantu_penglihatan")+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Motorik : "+rs2.getString("pemeriksaan_neurologi_motorik")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Bicara : "+rs2.getString("pemeriksaan_neurologi_pendengaran")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Pendengaran : "+rs2.getString("pemeriksaan_neurologi_bicara")+(rs2.getString("pemeriksaan_neurologi_bicara_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_neurologi_bicara_keterangan"))+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td border='99%' colspan='3'>"+
                                                            "Kekuatan Otot : "+rs2.getString("pemeriksaan_neurologi_kekuatan_otot")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Integument : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='24%' border='0'>"+
                                                            "Kulit : "+rs2.getString("pemeriksaan_integument_kulit")+
                                                         "</td>"+
                                                         "<td width='23%' border='0'>"+
                                                            "Warna Kulit : "+rs2.getString("pemeriksaan_integument_warnakulit")+
                                                         "</td>"+
                                                         "<td width='23%' border='0'>"+
                                                            "Turgor : "+rs2.getString("pemeriksaan_integument_turgor")+
                                                         "</td>"+
                                                         "<td width='30%' border='0'>"+
                                                            "Resiko Decubitus : "+rs2.getString("pemeriksaan_integument_dekubitas")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Muskuloskletal : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='40%' border='0'>"+
                                                            "Oedema : "+rs2.getString("pemeriksaan_muskuloskletal_oedema")+(rs2.getString("pemeriksaan_muskuloskletal_oedema_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_muskuloskletal_oedema_keterangan"))+
                                                         "</td>"+
                                                         "<td width='30%' border='0'>"+
                                                            "Pergerakan Sendi : "+rs2.getString("pemeriksaan_muskuloskletal_pergerakan_sendi")+
                                                         "</td>"+
                                                         "<td width='30%' border='0'>"+
                                                            "Kekuatan Otot : "+rs2.getString("pemeriksaan_muskuloskletal_kekauatan_otot")+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td width='40%' border='0'>"+
                                                            "Fraktur : "+rs2.getString("pemeriksaan_muskuloskletal_fraktur")+(rs2.getString("pemeriksaan_muskuloskletal_fraktur_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_muskuloskletal_fraktur_keterangan"))+
                                                         "</td>"+
                                                         "<td width='60%' border='0' colspan='2'>"+
                                                            "Nyeri Sendi : "+rs2.getString("pemeriksaan_muskuloskletal_nyeri_sendi")+(rs2.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan").equals("")?"":", "+rs2.getString("pemeriksaan_muskuloskletal_nyeri_sendi_keterangan"))+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>Eliminasi : "+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='33%' border='0'>"+
                                                            "BAB : Frekuensi : "+rs2.getString("pemeriksaan_eliminasi_bab_frekuensi_jumlah")+" X/ "+rs2.getString("pemeriksaan_eliminasi_bab_frekuensi_durasi")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Konsistensi : "+rs2.getString("pemeriksaan_eliminasi_bab_konsistensi")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Warna : "+rs2.getString("pemeriksaan_eliminasi_bab_warna")+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td width='33%' border='0'>"+
                                                            "BAK : Frekuensi : "+rs2.getString("pemeriksaan_eliminasi_bak_frekuensi_jumlah")+" X/ "+rs2.getString("pemeriksaan_eliminasi_bak_frekuensi_durasi")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Warna : "+rs2.getString("pemeriksaan_eliminasi_bak_warna")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Lain-lain : "+rs2.getString("pemeriksaan_eliminasi_bak_lainlain")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. POLA KEHIDUPAN SEHARI - HARI "+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%'>a. Pola Aktifitas :"+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Mandi : "+rs2.getString("pola_aktifitas_mandi")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Makan/Minum : "+rs2.getString("pola_aktifitas_makanminum")+
                                                         "</td>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Berpakaian : "+rs2.getString("pola_aktifitas_berpakaian")+
                                                         "</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                         "<td width='33%' border='0'>"+
                                                            "Eliminasi : "+rs2.getString("pola_aktifitas_eliminasi")+
                                                         "</td>"+
                                                         "<td width='66%' border='0' colspan='2'>"+
                                                            "Berpindah : "+rs2.getString("pola_aktifitas_berpindah")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>b. Pola Nutrisi :"+
                                                 "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                    "<tr>"+
                                                         "<td width='30%' border='0'>"+
                                                            "Porsi Makan : "+rs2.getString("pola_nutrisi_porsi_makan")+" porsi"+
                                                         "</td>"+
                                                         "<td width='30%' border='0'>"+
                                                            "Frekuensi Makan : "+rs2.getString("pola_nutrisi_frekuesi_makan")+"x/hari"+
                                                         "</td>"+
                                                         "<td width='40%' border='0'>"+
                                                            "Jenis Makanan : "+rs2.getString("pola_nutrisi_jenis_makanan")+
                                                         "</td>"+
                                                    "</tr>"+
                                                 "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%'>c. Pola Tidur : Lama Tidur "+rs2.getString("pola_tidur_lama_tidur")+" jam/hari, "+rs2.getString("pola_tidur_gangguan")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PENGKAJIAN FUNGSI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>a. Kemampuan Aktifitas Sehari-hari : "+rs2.getString("pengkajian_fungsi_kemampuan_sehari")+"</td>"+
                                              "<td width='33%' border='0'>b. Berjalan : "+rs2.getString("pengkajian_fungsi_berjalan")+(rs2.getString("pengkajian_fungsi_berjalan_keterangan").equals("")?"":", "+rs2.getString("pengkajian_fungsi_berjalan_keterangan"))+"</td>"+
                                              "<td width='33%' border='0'>c. Aktifitas : "+rs2.getString("pengkajian_fungsi_aktifitas")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>d. Alat Ambulasi : "+rs2.getString("pengkajian_fungsi_ambulasi")+"</td>"+
                                              "<td width='33%' border='0'>e. Ekstremitas Atas : "+rs2.getString("pengkajian_fungsi_ekstrimitas_atas")+(rs2.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan").equals("")?"":", "+rs2.getString("pengkajian_fungsi_ekstrimitas_atas_keterangan"))+"</td>"+
                                              "<td width='33%' border='0'>f. Ekstremitas Bawah : "+rs2.getString("pengkajian_fungsi_ekstrimitas_bawah")+(rs2.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan").equals("")?"":", "+rs2.getString("pengkajian_fungsi_ekstrimitas_bawah_keterangan"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='33%' border='0'>g. Kemampuan Menggenggam : "+rs2.getString("pengkajian_fungsi_menggenggam")+(rs2.getString("pengkajian_fungsi_menggenggam_keterangan").equals("")?"":", "+rs2.getString("pengkajian_fungsi_menggenggam_keterangan"))+"</td>"+
                                              "<td width='33%' border='0'>h. Kemampuan Koordinasi : "+rs2.getString("pengkajian_fungsi_koordinasi")+(rs2.getString("pengkajian_fungsi_koordinasi_keterangan").equals("")?"":", "+rs2.getString("pengkajian_fungsi_koordinasi_keterangan"))+"</td>"+
                                              "<td width='33%' border='0'>i. Kesimpulan Gangguan Fungsi : "+rs2.getString("pengkajian_fungsi_kesimpulan")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. RIWAYAT PSIKOLOGIS – SOSIAL – EKONOMI – BUDAYA – SPIRITUAL"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>a. Kondisi Psikologis</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_kondisi_psiko")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>b. Adakah Perilaku</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_perilaku")+(rs2.getString("riwayat_psiko_perilaku_keterangan").equals("")?"":", "+rs2.getString("riwayat_psiko_perilaku_keterangan"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>c. Gangguan Jiwa di Masa Lalu</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_gangguan_jiwa")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>d. Hubungan Pasien dengan Anggota Keluarga</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_hubungan_keluarga")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>e. Agama</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+Agama.getText()+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>f. Tinggal Dengan</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_tinggal")+(rs2.getString("riwayat_psiko_tinggal_keterangan").equals("")?"":", "+rs2.getString("riwayat_psiko_tinggal_keterangan"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>g. Pekerjaan</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+Pekerjaan.getText()+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>h. Pembayaran</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs.getString("png_jawab")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>i. Nilai-nilai Kepercayaan/Budaya Yang Perlu Diperhatikan</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_nilai_kepercayaan")+(rs2.getString("riwayat_psiko_nilai_kepercayaan_keterangan").equals("")?"":", "+rs2.getString("riwayat_psiko_nilai_kepercayaan_keterangan"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>j. Bahasa Sehari-hari</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+Bahasa.getText()+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>k. Pendidikan Pasien</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+Pendidikan.getText()+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>l. Pendidikan P.J.</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_pendidikan_pj")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='49%' border='0'>m. Edukasi Diberikan Kepada</td>"+
                                               "<td width='1%' border='0'>:</td>"+
                                               "<td width='50%' border='0'>"+rs2.getString("riwayat_psiko_edukasi_diberikan")+(rs2.getString("riwayat_psiko_edukasi_diberikan_keterangan").equals("")?"":", "+rs2.getString("riwayat_psiko_edukasi_diberikan_keterangan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. PENILAIAN TINGKAT NYERI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Tingkat Nyeri : "+rs2.getString("penilaian_nyeri")+", Waktu / Durasi : "+rs2.getString("penilaian_nyeri_waktu")+" Menit</td>"+
                                              "<td width='50%' border='0'>Penyebab : "+rs2.getString("penilaian_nyeri_penyebab")+(rs2.getString("penilaian_nyeri_ket_penyebab").equals("")?"":", "+rs2.getString("penilaian_nyeri_ket_penyebab"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Kualitas : "+rs2.getString("penilaian_nyeri_kualitas")+(rs2.getString("penilaian_nyeri_ket_kualitas").equals("")?"":", "+rs2.getString("penilaian_nyeri_ket_kualitas"))+"</td>"+
                                              "<td width='50%' border='0'>Severity : Skala Nyeri "+rs2.getString("penilaian_nyeri_skala")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='0' border='0'>Wilayah :</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>&nbsp;&nbsp;&nbsp;&nbsp;Lokasi : "+rs2.getString("penilaian_nyeri_lokasi")+"</td>"+
                                              "<td width='50%' border='0'>Menyebar : "+rs2.getString("penilaian_nyeri_menyebar")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%' border='0'>Nyeri hilang bila : "+rs2.getString("penilaian_nyeri_hilang")+(rs2.getString("penilaian_nyeri_ket_hilang").equals("")?"":", "+rs2.getString("penilaian_nyeri_ket_hilang"))+"</td>"+
                                              "<td width='50%' border='0'>Diberitahukan pada dokter ? "+rs2.getString("penilaian_nyeri_diberitahukan_dokter")+(rs2.getString("penilaian_nyeri_jam_diberitahukan_dokter").equals("")?"":", Jam : "+rs2.getString("penilaian_nyeri_jam_diberitahukan_dokter"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. PENILAIAN RESIKO JATUH"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>Skala Morse :"+
                                                    "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                      "<tr class='isi'>"+
                                                           "<td width='50%' bgcolor='#FFFAF8' align='center'>Faktor Resiko</td>"+
                                                           "<td width='40%' bgcolor='#FFFAF8' align='center'>Skala</td>"+
                                                           "<td width='10%' bgcolor='#FFFAF8' align='center'>Poin</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>1. Riwayat Jatuh</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_skala1")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_nilai1")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>2. Diagnosis Sekunder (≥ 2 Diagnosis Medis)</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_skala2")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_nilai2")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>3. Alat Bantu</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_skala3")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_nilai3")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>4. Terpasang Infuse</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_skala4")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_nilai4")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>5. Gaya Berjalan</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_skala5")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_nilai5")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>6. Status Mental</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_skala6")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_nilai6")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td align='right' colspan='2'>Total :</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhmorse_totalnilai")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td align='center' colspan='3'>"
                                         );

                                         if(rs2.getInt("penilaian_jatuhmorse_totalnilai")<25){
                                             htmlContent.append("Tingkat Resiko : Risiko Rendah (0-24), Tindakan : Intervensi pencegahan risiko jatuh standar");
                                         }else if(rs2.getInt("penilaian_jatuhmorse_totalnilai")<45){
                                             htmlContent.append("Tingkat Resiko : Risiko Sedang (25-44), Tindakan : Intervensi pencegahan risiko jatuh standar");
                                         }else if(rs2.getInt("penilaian_jatuhmorse_totalnilai")>=45){
                                             htmlContent.append("Tingkat Resiko : Risiko Tinggi (> 45), Tindakan : Intervensi pencegahan risiko jatuh standar dan Intervensi risiko jatuh tinggi");
                                         }

                                         htmlContent.append(
                                                           "</td>"+
                                                       "</tr>"+
                                                    "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='100%' border='0'>Skala Sydney :"+
                                                    "<table width='99%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                                      "<tr class='isi'>"+
                                                           "<td width='60%' bgcolor='#FFFAF8' align='center'>Faktor Resiko</td>"+
                                                           "<td width='30%' bgcolor='#FFFAF8' align='center'>Skala</td>"+
                                                           "<td width='10%' bgcolor='#FFFAF8' align='center'>Poin</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>1. Gangguan Gaya Berjalan (Diseret, Menghentak, Diayun)</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala1")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai1")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>2. Pusing / Pingsan Pada Posisi Tegak</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala2")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai2")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>3. Kebigungan Setiap Saat</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala3")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai3")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>4. Nokturia / Inkontinen</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala4")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai4")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>5. Kebingungan Intermiten</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala5")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai5")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>6. Kelemahan Umum</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala6")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai6")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td valign='middle'>7. Obat-obat Beresiko Tinggi (Diuretic, Narkotik, Sedativ, Anti Psikotik, Laksatif, Vasodilator Antiaritmia, Antihipertensi, Obat Hipoglikemik, Anti Depresan, Neuroleptik, NSAID)</td>"+
                                                           "<td align='center' valign='middle'>"+rs2.getString("penilaian_jatuhsydney_skala7")+"</td>"+
                                                           "<td align='center' valign='middle'>"+rs2.getString("penilaian_jatuhsydney_nilai7")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>8. Riwayat Jatuh Dalam Waktu 12 Bulan Sebelumnya</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala8")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai8")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>9. Osteoporosis</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala9")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai9")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>10. Gangguan Pendengaran Dan Atau Penglihatan</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala10")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai10")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td>11. Usia 70 Tahun Ke Atas</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_skala11")+"</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai11")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td align='right' colspan='2'>Total :</td>"+
                                                           "<td align='center'>"+rs2.getString("penilaian_jatuhsydney_nilai11")+"</td>"+
                                                       "</tr>"+
                                                       "<tr>"+
                                                           "<td align='center' colspan='3'>"
                                         );

                                         if(rs2.getInt("penilaian_jatuhsydney_totalnilai")<4){
                                             htmlContent.append("Tingkat Resiko : Risiko Rendah (1-3), Tindakan : Intervensi pencegahan risiko standar");
                                         }else if(rs2.getInt("penilaian_jatuhsydney_totalnilai")>=4){
                                             htmlContent.append("Tingkat Resiko : Risiko Sedang (> 4), Tindakan : Intervensi pencegahan risiko tinggi");
                                         }

                                         htmlContent.append(
                                                           "</td>"+
                                                       "</tr>"+
                                                    "</table>"+
                                              "</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VIII. SKRINING GIZI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>No</td>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55%'>Parameter</td>"+
                                               "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%' colspan='2'>Nilai</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top'>1</td>"+
                                              "<td valign='top'>Apakah ada penurunan BB yang tidak diinginkan selama 6 bulan terakhir ?</td>"+
                                              "<td valign='top' align='center' width='35%'>"+rs2.getString("skrining_gizi1")+"</td>"+
                                              "<td valign='top' align='right' width='5%'>"+rs2.getString("nilai_gizi1")+"&nbsp;&nbsp;</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top'>2</td>"+
                                              "<td valign='top'>Apakah asupan makan berkurang karena tidak nafsu makan ?</td>"+
                                              "<td valign='top' align='center' width='35%'>"+rs2.getString("skrining_gizi2")+"</td>"+
                                              "<td valign='top' align='right' width='5%'>"+rs2.getString("nilai_gizi2")+"&nbsp;&nbsp;</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top' align='left' colspan='2'>Total Skor : </td>"+
                                              "<td valign='top' align='right' colspan='2'>"+rs2.getString("nilai_total_gizi")+"&nbsp;&nbsp;</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top' align='left' colspan='4' border='0'>Pasien dengan diagnosis khusus : "+rs2.getString("skrining_gizi_diagnosa_khusus")+(rs2.getString("skrining_gizi_ket_diagnosa_khusus").equals("")?"":", "+rs2.getString("skrining_gizi_ket_diagnosa_khusus"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td valign='top' align='left' colspan='4' border='0'>Sudah dibaca dan diketahui oleh Dietisen : "+rs2.getString("skrining_gizi_diketahui_dietisen")+(rs2.getString("skrining_gizi_jam_diketahui_dietisen").equals("")?"":", "+rs2.getString("skrining_gizi_jam_diketahui_dietisen"))+"</td>"+
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
                                               "<td valign='top'>");
                            try {
                                rs3=koneksi.prepareStatement(
                                    "select master_masalah_keperawatan.nama_masalah from master_masalah_keperawatan "+
                                    "inner join penilaian_awal_keperawatan_ranap_masalah on penilaian_awal_keperawatan_ranap_masalah.kode_masalah=master_masalah_keperawatan.kode_masalah "+
                                    "where penilaian_awal_keperawatan_ranap_masalah.no_rawat='"+norawat+"' order by penilaian_awal_keperawatan_ranap_masalah.kode_masalah").executeQuery();
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
                                               "<td valign='top'>");
                            try {
                                rs3=koneksi.prepareStatement(
                                    "select master_rencana_keperawatan.rencana_keperawatan from master_rencana_keperawatan "+
                                    "inner join penilaian_awal_keperawatan_ranap_rencana on penilaian_awal_keperawatan_ranap_rencana.kode_rencana=master_rencana_keperawatan.kode_rencana "+
                                    "where penilaian_awal_keperawatan_ranap_rencana.no_rawat='"+norawat+"' order by penilaian_awal_keperawatan_ranap_rencana.kode_rencana").executeQuery();
                                while(rs3.next()){
                                    htmlContent.append(rs3.getString("rencana_keperawatan")+"<br>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            htmlContent.append(rs2.getString("rencana")+
                                            "</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Kebidanan Rawat Inap: "+e);
        }
    }

    private void menampilkanBerkasDigital(String norawat) {
        try {
            if(chkBerkasDigital.isSelected()==true){
                try{
                    rs2=koneksi.prepareStatement(
                        "select master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file "+
                        "from berkas_digital_perawatan inner join master_berkas_digital "+
                        "on berkas_digital_perawatan.kode=master_berkas_digital.kode "+
                        "where berkas_digital_perawatan.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){                                    
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Berkas Digital Perawatan</td>"+
                            "<td valign='top' width='1%' align='center'>:</td>"+
                            "<td valign='top' width='79%'>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                 "<tr align='center'>"+
                                    "<td valign='top' width='4%' bgcolor='#FFFAF8'>No.</td>"+
                                    "<td valign='top' width='96%' bgcolor='#FFFAF8'>Berkas Digital</td>"+
                                 "</tr>"
                        );
                        rs2.beforeFirst();
                        w=1;
                        while(rs2.next()){
                            htmlContent.append(
                                 "<tr>"+
                                    "<td valign='top' align='center'>"+w+"</td>"+
                                    "<td valign='top'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/berkasrawat/"+rs2.getString("lokasi_file")+"'>"+rs2.getString("nama").replaceAll("pages/upload/","")+"</a></td>"+
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
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("Notif Berkas Digitaln: "+e);
        }
    }

    private void menampilkanAsuhanMedisRawatJalanPsikiatrik(String norawat) {
        try{
            if(chkAsuhanMedisRalanPsikiatri.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_medis_ralan_psikiatrik.tanggal,penilaian_medis_ralan_psikiatrik.kd_dokter,penilaian_medis_ralan_psikiatrik.anamnesis,penilaian_medis_ralan_psikiatrik.hubungan,"+
                            "penilaian_medis_ralan_psikiatrik.keluhan_utama,penilaian_medis_ralan_psikiatrik.rps,penilaian_medis_ralan_psikiatrik.rpk,penilaian_medis_ralan_psikiatrik.rpd,penilaian_medis_ralan_psikiatrik.rpo,"+
                            "penilaian_medis_ralan_psikiatrik.penampilan,penilaian_medis_ralan_psikiatrik.pembicaraan,penilaian_medis_ralan_psikiatrik.psikomotor,penilaian_medis_ralan_psikiatrik.sikap,penilaian_medis_ralan_psikiatrik.mood,"+
                            "penilaian_medis_ralan_psikiatrik.fungsi_kognitif,penilaian_medis_ralan_psikiatrik.gangguan_persepsi,penilaian_medis_ralan_psikiatrik.proses_pikir,penilaian_medis_ralan_psikiatrik.pengendalian_impuls,"+
                            "penilaian_medis_ralan_psikiatrik.tilikan,penilaian_medis_ralan_psikiatrik.rta,penilaian_medis_ralan_psikiatrik.keadaan,penilaian_medis_ralan_psikiatrik.gcs,penilaian_medis_ralan_psikiatrik.kesadaran,"+
                            "penilaian_medis_ralan_psikiatrik.td,penilaian_medis_ralan_psikiatrik.nadi,penilaian_medis_ralan_psikiatrik.rr,penilaian_medis_ralan_psikiatrik.suhu,penilaian_medis_ralan_psikiatrik.spo,"+
                            "penilaian_medis_ralan_psikiatrik.bb,penilaian_medis_ralan_psikiatrik.tb,penilaian_medis_ralan_psikiatrik.kepala,penilaian_medis_ralan_psikiatrik.gigi,penilaian_medis_ralan_psikiatrik.tht,"+
                            "penilaian_medis_ralan_psikiatrik.thoraks,penilaian_medis_ralan_psikiatrik.abdomen,penilaian_medis_ralan_psikiatrik.ekstremitas,penilaian_medis_ralan_psikiatrik.genital,penilaian_medis_ralan_psikiatrik.kulit,"+
                            "penilaian_medis_ralan_psikiatrik.ket_fisik,penilaian_medis_ralan_psikiatrik.alergi,penilaian_medis_ralan_psikiatrik.penunjang,penilaian_medis_ralan_psikiatrik.diagnosis,penilaian_medis_ralan_psikiatrik.tata,"+
                            "penilaian_medis_ralan_psikiatrik.konsulrujuk,dokter.nm_dokter "+
                            "from penilaian_medis_ralan_psikiatrik inner join dokter on penilaian_medis_ralan_psikiatrik.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan_psikiatrik.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Psikiatri</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td colspan='2'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Fisik & Neurologi : "+rs2.getString("rpk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat NAPZA : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. STATUS PSIKIATRIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td width='50%'>Penampilan : "+rs2.getString("penampilan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Gangguan Persepsi : "+rs2.getString("gangguan_persepsi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Pembicaraan : "+rs2.getString("pembicaraan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Proses Pikir & Isi Pikir : "+rs2.getString("proses_pikir")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Psikomotor : "+rs2.getString("psikomotor").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Pengendalian Impuls : "+rs2.getString("pengendalian_impuls")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Sikap : "+rs2.getString("sikap").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Tilikan : "+rs2.getString("tilikan")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Mood / Afek : "+rs2.getString("mood").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Reality Testing Ability : "+rs2.getString("rta")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Fungsi Kognitif : "+rs2.getString("fungsi_kognitif").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'></td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Keadaan Umum : "+rs2.getString("keadaan")+"</td>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>TB : "+rs2.getString("tb")+" Cm</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='25%' border='0'>SpO2 : "+rs2.getString("spo")+" %</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Gigi & Mulut : "+rs2.getString("gigi")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>THT : "+rs2.getString("tht")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                               "<td width='25%' border='0'>Genital & Anus : "+rs2.getString("genital")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                               "<td width='25%' border='0'>Kulit : "+rs2.getString("kulit")+"</td>"+
                                               "<td width='50%' border='0' colpan='2'>Keterangan Fisik : "+rs2.getString("ket_fisik")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("tata").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. KONSUL/RUJUK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("konsulrujuk").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        }catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan THT : "+e);
        }
    }
    
    private void menampilkanAsuhanMedisRawatJalanPenyakitDalam(String norawat) {
        try {
            if(chkAsuhanMedisRalanPenyakitDalam.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                        "select penilaian_medis_ralan_penyakit_dalam.tanggal,penilaian_medis_ralan_penyakit_dalam.kd_dokter,penilaian_medis_ralan_penyakit_dalam.anamnesis,penilaian_medis_ralan_penyakit_dalam.hubungan,"+
                        "penilaian_medis_ralan_penyakit_dalam.keluhan_utama,penilaian_medis_ralan_penyakit_dalam.rps,penilaian_medis_ralan_penyakit_dalam.rpd,penilaian_medis_ralan_penyakit_dalam.rpo,penilaian_medis_ralan_penyakit_dalam.alergi,"+
                        "penilaian_medis_ralan_penyakit_dalam.kondisi,penilaian_medis_ralan_penyakit_dalam.status,penilaian_medis_ralan_penyakit_dalam.td,penilaian_medis_ralan_penyakit_dalam.nadi,"+
                        "penilaian_medis_ralan_penyakit_dalam.suhu,penilaian_medis_ralan_penyakit_dalam.rr,penilaian_medis_ralan_penyakit_dalam.bb,penilaian_medis_ralan_penyakit_dalam.nyeri,penilaian_medis_ralan_penyakit_dalam.gcs,"+
                        "penilaian_medis_ralan_penyakit_dalam.kepala,penilaian_medis_ralan_penyakit_dalam.thoraks,penilaian_medis_ralan_penyakit_dalam.abdomen,penilaian_medis_ralan_penyakit_dalam.ekstremitas,"+
                        "penilaian_medis_ralan_penyakit_dalam.lainnya,penilaian_medis_ralan_penyakit_dalam.lab,penilaian_medis_ralan_penyakit_dalam.rad,penilaian_medis_ralan_penyakit_dalam.penunjanglain,"+
                        "penilaian_medis_ralan_penyakit_dalam.diagnosis,penilaian_medis_ralan_penyakit_dalam.diagnosis2,penilaian_medis_ralan_penyakit_dalam.permasalahan,penilaian_medis_ralan_penyakit_dalam.terapi,"+
                        "penilaian_medis_ralan_penyakit_dalam.tindakan,penilaian_medis_ralan_penyakit_dalam.edukasi,dokter.nm_dokter,keterangan_kepala,penilaian_medis_ralan_penyakit_dalam.keterangan_thorak,"+
                        "penilaian_medis_ralan_penyakit_dalam.keterangan_abdomen,penilaian_medis_ralan_penyakit_dalam.keterangan_ekstremitas "+
                        "from penilaian_medis_ralan_penyakit_dalam inner join dokter on penilaian_medis_ralan_penyakit_dalam.kd_dokter=dokter.kd_dokter "+
                        "where penilaian_medis_ralan_penyakit_dalam.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Penyakit Dalam</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Status Nutrisi : "+rs2.getString("status")+"</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>Nyeri : "+rs2.getString("nyeri")+"</td>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS KELAINAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='50%'>Kepala : "+rs2.getString("kepala")+(rs2.getString("keterangan_kepala").equals("")?"":", "+rs2.getString("keterangan_kepala"))+"</td>"+
                                               "<td width='50%'>Thoraks : "+rs2.getString("thoraks")+(rs2.getString("keterangan_thorak").equals("")?"":", "+rs2.getString("keterangan_thorak"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%'>Abdomen : "+rs2.getString("abdomen")+(rs2.getString("keterangan_abdomen").equals("")?"":", "+rs2.getString("keterangan_abdomen"))+"</td>"+
                                               "<td width='50%'>Ekstremitas : "+rs2.getString("ekstremitas")+(rs2.getString("keterangan_ekstremitas").equals("")?"":", "+rs2.getString("keterangan_ekstremitas"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' colspan='2'>Lainnya : "+rs2.getString("lainnya").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Laboratorium : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Radiologi : "+rs2.getString("rad").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Penunjang Lainnya : "+rs2.getString("penunjanglain").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Kerja : "+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Banding : "+rs2.getString("diagnosis2").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. PERMASALAHAN & TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Permasalahan : "+rs2.getString("permasalahan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Terapi/Pengobatan : "+rs2.getString("terapi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Tindakan/Rencana Tindakan : "+rs2.getString("tindakan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. EDUKASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan : "+e);
        }
    }
    
    private void menampilkanAsuhanMedisRawatJalanMata(String norawat) {
        try {
            if(chkAsuhanMedisRalanMata.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                        "select penilaian_medis_ralan_mata.tanggal,penilaian_medis_ralan_mata.kd_dokter,penilaian_medis_ralan_mata.anamnesis,penilaian_medis_ralan_mata.hubungan,"+
                        "penilaian_medis_ralan_mata.keluhan_utama,penilaian_medis_ralan_mata.rps,penilaian_medis_ralan_mata.rpd,penilaian_medis_ralan_mata.rpo,penilaian_medis_ralan_mata.alergi,"+
                        "penilaian_medis_ralan_mata.status,penilaian_medis_ralan_mata.td,penilaian_medis_ralan_mata.nadi,penilaian_medis_ralan_mata.rr,penilaian_medis_ralan_mata.suhu,"+
                        "penilaian_medis_ralan_mata.nyeri,penilaian_medis_ralan_mata.bb,penilaian_medis_ralan_mata.visuskanan,penilaian_medis_ralan_mata.visuskiri,penilaian_medis_ralan_mata.cckanan,"+
                        "penilaian_medis_ralan_mata.cckiri,penilaian_medis_ralan_mata.palkanan,penilaian_medis_ralan_mata.palkiri,penilaian_medis_ralan_mata.conkanan,penilaian_medis_ralan_mata.conkiri,"+
                        "penilaian_medis_ralan_mata.corneakanan,penilaian_medis_ralan_mata.corneakiri,penilaian_medis_ralan_mata.coakanan,penilaian_medis_ralan_mata.coakiri,"+
                        "penilaian_medis_ralan_mata.pupilkanan,penilaian_medis_ralan_mata.pupilkiri,penilaian_medis_ralan_mata.lensakanan,penilaian_medis_ralan_mata.lensakiri,"+
                        "penilaian_medis_ralan_mata.funduskanan,penilaian_medis_ralan_mata.funduskiri,penilaian_medis_ralan_mata.papilkanan,penilaian_medis_ralan_mata.papilkiri,"+
                        "penilaian_medis_ralan_mata.retinakanan,penilaian_medis_ralan_mata.retinakiri,penilaian_medis_ralan_mata.makulakanan,penilaian_medis_ralan_mata.makulakiri,"+
                        "penilaian_medis_ralan_mata.tiokanan,penilaian_medis_ralan_mata.tiokiri,penilaian_medis_ralan_mata.mbokanan,penilaian_medis_ralan_mata.mbokiri,penilaian_medis_ralan_mata.lab,"+
                        "penilaian_medis_ralan_mata.rad,penilaian_medis_ralan_mata.penunjang,penilaian_medis_ralan_mata.tes,penilaian_medis_ralan_mata.pemeriksaan,"+
                        "penilaian_medis_ralan_mata.diagnosis,penilaian_medis_ralan_mata.diagnosisbdg,penilaian_medis_ralan_mata.permasalahan,penilaian_medis_ralan_mata.terapi,"+
                        "penilaian_medis_ralan_mata.tindakan,penilaian_medis_ralan_mata.edukasi,dokter.nm_dokter "+
                        "from penilaian_medis_ralan_mata inner join dokter on penilaian_medis_ralan_mata.kd_dokter=dokter.kd_dokter "+
                        "where penilaian_medis_ralan_mata.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Mata</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='20%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='20%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='20%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='20%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='20%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td border='0' colspan='2'>Nyeri : "+rs2.getString("nyeri")+"</td>"+
                                               "<td border='0' colspan='3'>Status Nutrisi : "+rs2.getString("status")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS OFTAMOLOGIS"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr align='center'>"+
                                            "<td valign='top' width='40%' bgcolor='#FFFAF8'>OD : Mata Kanan</td>"+
                                            "<td valign='top' width='20%' bgcolor='#FFFAF8'>Status</td>"+
                                            "<td valign='top' width='40%' bgcolor='#FFFAF8'>OS : Mata Kiri</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("visuskanan")+"</td>"+
                                               "<td>Visus SC</td>"+
                                               "<td>"+rs2.getString("visuskiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("cckanan")+"</td>"+
                                               "<td>CC</td>"+
                                               "<td>"+rs2.getString("cckiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("palkanan")+"</td>"+
                                               "<td>Palpebra</td>"+
                                               "<td>"+rs2.getString("palkiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("conkanan")+"</td>"+
                                               "<td>Conjungtiva</td>"+
                                               "<td>"+rs2.getString("conkiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("corneakanan")+"</td>"+
                                               "<td>Cornea</td>"+
                                               "<td>"+rs2.getString("corneakiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("coakanan")+"</td>"+
                                               "<td>COA</td>"+
                                               "<td>"+rs2.getString("coakiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("pupilkanan")+"</td>"+
                                               "<td>Pupil</td>"+
                                               "<td>"+rs2.getString("pupilkiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("lensakanan")+"</td>"+
                                               "<td>Lensa</td>"+
                                               "<td>"+rs2.getString("lensakiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("funduskanan")+"</td>"+
                                               "<td>Fundus Media</td>"+
                                               "<td>"+rs2.getString("funduskiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("papilkanan")+"</td>"+
                                               "<td>Papil</td>"+
                                               "<td>"+rs2.getString("papilkiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("retinakanan")+"</td>"+
                                               "<td>Retina</td>"+
                                               "<td>"+rs2.getString("retinakiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("makulakanan")+"</td>"+
                                               "<td>Makula</td>"+
                                               "<td>"+rs2.getString("makulakiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("tiokanan")+"</td>"+
                                               "<td>TIO</td>"+
                                               "<td>"+rs2.getString("tiokiri")+"</td>"+
                                          "</tr>"+
                                          "<tr align='center'>"+
                                               "<td>"+rs2.getString("mbokanan")+"</td>"+
                                               "<td>MBO</td>"+
                                               "<td>"+rs2.getString("mbokiri")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Laboratorium : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Radiologi : "+rs2.getString("rad").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Penunjang Lainnya : "+rs2.getString("penunjang").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Tes Penglihatan : "+rs2.getString("tes").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Pemeriksaan Lain : "+rs2.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Kerja : "+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Banding : "+rs2.getString("diagnosisbdg").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. PERMASALAHAN & TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Permasalahan : "+rs2.getString("permasalahan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Terapi/Pengobatan : "+rs2.getString("terapi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Tindakan/Rencana Tindakan : "+rs2.getString("tindakan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. EDUKASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan : "+e);
        }
    }
    
    private void menampilkanAsuhanMedisRawatJalanNeurologi(String norawat) {
        try {
            if(chkAsuhanMedisRalanNeurologi.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                        "select penilaian_medis_ralan_neurologi.tanggal,penilaian_medis_ralan_neurologi.kd_dokter,penilaian_medis_ralan_neurologi.anamnesis,penilaian_medis_ralan_neurologi.hubungan,penilaian_medis_ralan_neurologi.keluhan_utama,"+
                        "penilaian_medis_ralan_neurologi.rps,penilaian_medis_ralan_neurologi.rpd,penilaian_medis_ralan_neurologi.rpo,penilaian_medis_ralan_neurologi.alergi,penilaian_medis_ralan_neurologi.kesadaran,"+
                        "penilaian_medis_ralan_neurologi.status,penilaian_medis_ralan_neurologi.td,penilaian_medis_ralan_neurologi.nadi,penilaian_medis_ralan_neurologi.suhu,penilaian_medis_ralan_neurologi.rr,"+
                        "penilaian_medis_ralan_neurologi.bb,penilaian_medis_ralan_neurologi.nyeri,penilaian_medis_ralan_neurologi.gcs,penilaian_medis_ralan_neurologi.kepala,penilaian_medis_ralan_neurologi.keterangan_kepala,"+
                        "penilaian_medis_ralan_neurologi.thoraks,penilaian_medis_ralan_neurologi.keterangan_thoraks,penilaian_medis_ralan_neurologi.abdomen,penilaian_medis_ralan_neurologi.keterangan_abdomen,"+
                        "penilaian_medis_ralan_neurologi.ekstremitas,penilaian_medis_ralan_neurologi.keterangan_ekstremitas,penilaian_medis_ralan_neurologi.columna,penilaian_medis_ralan_neurologi.keterangan_columna,"+
                        "penilaian_medis_ralan_neurologi.muskulos,penilaian_medis_ralan_neurologi.keterangan_muskulos,penilaian_medis_ralan_neurologi.lainnya,penilaian_medis_ralan_neurologi.lab,"+
                        "penilaian_medis_ralan_neurologi.rad,penilaian_medis_ralan_neurologi.penunjanglain,penilaian_medis_ralan_neurologi.diagnosis,penilaian_medis_ralan_neurologi.diagnosis2,"+
                        "penilaian_medis_ralan_neurologi.permasalahan,penilaian_medis_ralan_neurologi.terapi,penilaian_medis_ralan_neurologi.tindakan,penilaian_medis_ralan_neurologi.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_neurologi on reg_periksa.no_rawat=penilaian_medis_ralan_neurologi.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_neurologi.kd_dokter=dokter.kd_dokter "+
                        "where penilaian_medis_ralan_neurologi.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Neurologi</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='20%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='20%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='20%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='20%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                               "<td width='20%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='20%' border='0'>Status Nutrisi : "+rs2.getString("status")+"</td>"+
                                               "<td width='20%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='20%' border='0'>Nyeri : "+rs2.getString("nyeri")+"</td>"+
                                               "<td width='20%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS KELAINAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='50%'>Kepala : "+rs2.getString("kepala")+(rs2.getString("keterangan_kepala").equals("")?"":", "+rs2.getString("keterangan_kepala"))+"</td>"+
                                               "<td width='50%'>Thoraks : "+rs2.getString("thoraks")+(rs2.getString("keterangan_thoraks").equals("")?"":", "+rs2.getString("keterangan_thoraks"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%'>Abdomen : "+rs2.getString("abdomen")+(rs2.getString("keterangan_abdomen").equals("")?"":", "+rs2.getString("keterangan_abdomen"))+"</td>"+
                                               "<td width='50%'>Ekstremitas : "+rs2.getString("ekstremitas")+(rs2.getString("keterangan_ekstremitas").equals("")?"":", "+rs2.getString("keterangan_ekstremitas"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='50%'>Columna Vertebralis : "+rs2.getString("columna")+(rs2.getString("keterangan_columna").equals("")?"":", "+rs2.getString("keterangan_columna"))+"</td>"+
                                               "<td width='50%'>Muskuloskeletal : "+rs2.getString("muskulos")+(rs2.getString("keterangan_muskulos").equals("")?"":", "+rs2.getString("keterangan_muskulos"))+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' colspan='2'>Lainnya : "+rs2.getString("lainnya").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Laboratorium : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Radiologi : "+rs2.getString("rad").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Penunjang Lainnya : "+rs2.getString("penunjanglain").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Kerja : "+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Banding : "+rs2.getString("diagnosis2").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. PERMASALAHAN & TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Permasalahan : "+rs2.getString("permasalahan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Terapi/Pengobatan : "+rs2.getString("terapi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Tindakan/Rencana Tindakan : "+rs2.getString("tindakan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. EDUKASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan Neurologi : "+e);
        }
    }
    
    private void menampilkanAsuhanMedisRawatJalanOrthopedi(String norawat) {
        try {
            if(chkAsuhanMedisRalanOrthopedi.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                        "select penilaian_medis_ralan_orthopedi.tanggal,penilaian_medis_ralan_orthopedi.kd_dokter,penilaian_medis_ralan_orthopedi.anamnesis,penilaian_medis_ralan_orthopedi.hubungan,penilaian_medis_ralan_orthopedi.keluhan_utama,"+
                        "penilaian_medis_ralan_orthopedi.rps,penilaian_medis_ralan_orthopedi.rpd,penilaian_medis_ralan_orthopedi.rpo,penilaian_medis_ralan_orthopedi.alergi,penilaian_medis_ralan_orthopedi.kesadaran,"+
                        "penilaian_medis_ralan_orthopedi.status,penilaian_medis_ralan_orthopedi.td,penilaian_medis_ralan_orthopedi.nadi,penilaian_medis_ralan_orthopedi.suhu,penilaian_medis_ralan_orthopedi.rr,"+
                        "penilaian_medis_ralan_orthopedi.bb,penilaian_medis_ralan_orthopedi.nyeri,penilaian_medis_ralan_orthopedi.gcs,penilaian_medis_ralan_orthopedi.kepala,penilaian_medis_ralan_orthopedi.thoraks,"+
                        "penilaian_medis_ralan_orthopedi.abdomen,penilaian_medis_ralan_orthopedi.ekstremitas,penilaian_medis_ralan_orthopedi.genetalia,penilaian_medis_ralan_orthopedi.columna,"+
                        "penilaian_medis_ralan_orthopedi.muskulos,penilaian_medis_ralan_orthopedi.lainnya,penilaian_medis_ralan_orthopedi.ket_lokalis,penilaian_medis_ralan_orthopedi.lab,"+
                        "penilaian_medis_ralan_orthopedi.rad,penilaian_medis_ralan_orthopedi.pemeriksaan,penilaian_medis_ralan_orthopedi.diagnosis,penilaian_medis_ralan_orthopedi.diagnosis2,"+
                        "penilaian_medis_ralan_orthopedi.permasalahan,penilaian_medis_ralan_orthopedi.terapi,penilaian_medis_ralan_orthopedi.tindakan,penilaian_medis_ralan_orthopedi.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_orthopedi on reg_periksa.no_rawat=penilaian_medis_ralan_orthopedi.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_orthopedi.kd_dokter=dokter.kd_dokter "+
                        "where penilaian_medis_ralan_orthopedi.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Orthopedi</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                               "<td width='25%' border='0'>Status Nutrisi : "+rs2.getString("status")+"</td>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>Nyeri : "+rs2.getString("nyeri")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                               "<td width='25%' border='0'>Columna Vertebralis : "+rs2.getString("columna")+"</td>"+
                                               "<td width='25%' border='0'>Muskuloskeletal : "+rs2.getString("muskulos")+"</td>"+
                                               "<td width='25%' border='0'>Genetalia Os Pubis : "+rs2.getString("genetalia")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' colspan='4'>Keterangan Lainnya : "+rs2.getString("lainnya").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS LOKALIS"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("ket_lokalis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Laboratorium : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Radiologi : "+rs2.getString("rad").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Penunjang Lainnya : "+rs2.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Kerja : "+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Banding : "+rs2.getString("diagnosis2").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. PERMASALAHAN & TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Permasalahan : "+rs2.getString("permasalahan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Terapi/Pengobatan : "+rs2.getString("terapi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Tindakan/Rencana Tindakan : "+rs2.getString("tindakan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. EDUKASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan Orthopedi : "+e);
        }
    }
    
    private void menampilkanAsuhanMedisRawatJalanBedah(String norawat) {
        try {
            if(chkAsuhanMedisRalanBedah.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                        "select penilaian_medis_ralan_bedah.tanggal,penilaian_medis_ralan_bedah.kd_dokter,penilaian_medis_ralan_bedah.anamnesis,penilaian_medis_ralan_bedah.hubungan,penilaian_medis_ralan_bedah.keluhan_utama,"+
                        "penilaian_medis_ralan_bedah.rps,penilaian_medis_ralan_bedah.rpd,penilaian_medis_ralan_bedah.rpo,penilaian_medis_ralan_bedah.alergi,penilaian_medis_ralan_bedah.kesadaran,"+
                        "penilaian_medis_ralan_bedah.status,penilaian_medis_ralan_bedah.td,penilaian_medis_ralan_bedah.nadi,penilaian_medis_ralan_bedah.suhu,penilaian_medis_ralan_bedah.rr,"+
                        "penilaian_medis_ralan_bedah.bb,penilaian_medis_ralan_bedah.nyeri,penilaian_medis_ralan_bedah.gcs,penilaian_medis_ralan_bedah.kepala,penilaian_medis_ralan_bedah.thoraks,"+
                        "penilaian_medis_ralan_bedah.abdomen,penilaian_medis_ralan_bedah.ekstremitas,penilaian_medis_ralan_bedah.genetalia,penilaian_medis_ralan_bedah.columna,"+
                        "penilaian_medis_ralan_bedah.muskulos,penilaian_medis_ralan_bedah.lainnya,penilaian_medis_ralan_bedah.ket_lokalis,penilaian_medis_ralan_bedah.lab,"+
                        "penilaian_medis_ralan_bedah.rad,penilaian_medis_ralan_bedah.pemeriksaan,penilaian_medis_ralan_bedah.diagnosis,penilaian_medis_ralan_bedah.diagnosis2,"+
                        "penilaian_medis_ralan_bedah.permasalahan,penilaian_medis_ralan_bedah.terapi,penilaian_medis_ralan_bedah.tindakan,penilaian_medis_ralan_bedah.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_bedah on reg_periksa.no_rawat=penilaian_medis_ralan_bedah.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_bedah.kd_dokter=dokter.kd_dokter "+
                        "where penilaian_medis_ralan_bedah.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>"+ 
                            "<td valign='top' width='2%'></td>"+        
                            "<td valign='top' width='18%'>Penilaian Awal Medis Rawat Jalan Bedah</td>"+
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
                                              "<td width='33%' border='0'>Dokter : "+rs2.getString("kd_dokter")+" "+rs2.getString("nm_dokter")+"</td>"+
                                              "<td width='33%' border='0'>Anamnesis : "+rs2.getString("anamnesis")+(rs2.getString("hubungan").equals("")?"":", "+rs2.getString("hubungan"))+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "I. RIWAYAT KESEHATAN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                              "<td colspan='2'>Keluhan Utama : "+rs2.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penyakit Sekarang : "+rs2.getString("rps").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Penyakit Dahulu : "+rs2.getString("rpd").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                              "<td width='50%'>Riwayat Penggunaan Obat : "+rs2.getString("rpo").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                              "<td width='50%'>Riwayat Alergi : "+rs2.getString("alergi")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "II. PEMERIKSAAN FISIK"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Kesadaran : "+rs2.getString("kesadaran")+"</td>"+
                                               "<td width='25%' border='0'>TD : "+rs2.getString("td")+" mmHg</td>"+
                                               "<td width='25%' border='0'>Nadi : "+rs2.getString("nadi")+" x/menit</td>"+
                                               "<td width='25%' border='0'>Suhu : "+rs2.getString("suhu")+" °C</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>RR : "+rs2.getString("rr")+" x/menit</td>"+
                                               "<td width='25%' border='0'>Status Nutrisi : "+rs2.getString("status")+"</td>"+
                                               "<td width='25%' border='0'>BB : "+rs2.getString("bb")+" Kg</td>"+
                                               "<td width='25%' border='0'>Nyeri : "+rs2.getString("nyeri")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>GCS(E,V,M) : "+rs2.getString("gcs")+"</td>"+
                                               "<td width='25%' border='0'>Kepala : "+rs2.getString("kepala")+"</td>"+
                                               "<td width='25%' border='0'>Thoraks : "+rs2.getString("thoraks")+"</td>"+
                                               "<td width='25%' border='0'>Abdomen : "+rs2.getString("abdomen")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='25%' border='0'>Ekstremitas : "+rs2.getString("ekstremitas")+"</td>"+
                                               "<td width='25%' border='0'>Columna Vertebralis : "+rs2.getString("columna")+"</td>"+
                                               "<td width='25%' border='0'>Muskuloskeletal : "+rs2.getString("muskulos")+"</td>"+
                                               "<td width='25%' border='0'>Genetalia Os Pubis : "+rs2.getString("genetalia")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' colspan='4'>Keterangan Lainnya : "+rs2.getString("lainnya").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "III. STATUS LOKALIS"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("ket_lokalis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "IV. PEMERIKSAAN PENUNJANG"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Laboratorium : "+rs2.getString("lab").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Radiologi : "+rs2.getString("rad").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Penunjang Lainnya : "+rs2.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "V. DIAGNOSIS/ASESMEN"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Kerja : "+rs2.getString("diagnosis").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Asesmen Banding : "+rs2.getString("diagnosis2").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VI. PERMASALAHAN & TATALAKSANA"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Permasalahan : "+rs2.getString("permasalahan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Terapi/Pengobatan : "+rs2.getString("terapi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>Tindakan/Rencana Tindakan : "+rs2.getString("tindakan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                          "</tr>"+
                                       "</table>"+
                                    "</td>"+
                                 "</tr>"+
                                 "<tr>"+
                                    "<td valign='top'>"+
                                       "VII. EDUKASI"+  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>"+
                                          "<tr>"+
                                               "<td width='100%' border='0'>"+rs2.getString("edukasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
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
        } catch (Exception e) {
            System.out.println("Notif Asuhan Medis Rawat Jalan Bedah : "+e);
        }
    }
}

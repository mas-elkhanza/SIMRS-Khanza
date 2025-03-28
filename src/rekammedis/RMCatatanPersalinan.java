/*
 * By Mas Elkhanza
 */


package rekammedis;

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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
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
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMCatatanPersalinan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    private String finger="",finger2="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMCatatanPersalinan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Dokter","Dokter Penanggung Jawab","NIP","Nama Bidan","Mulai Persalinan","Selesai Persalinan",
            "Catatan Persalinan","W.P.Kala 1","W.P.Kala 2","W.P.Kala 3","W.P.Jumlah","Perineum","Jahitan Luar 1","Jahitan Luar 2","Jahitan Dalam 1","Jahitan Dalam 2",
            "J.K. Anak","Status Lahir","APGAR Score","BB(gram)","PB(cm)","Kelainan","Ketuban","Placenta","Ukuran(gr)","Tali Pusat(cm)","Insertio","D.K.Kala 1",
            "D.K.Kala 2","D.K.Kala 3","D.K.Kala 4","D.K. Jumlah","Kondisi Umum","TD(mmHg)","Nadi(x/menit)","RR(x/menit)","Suhu(°C)","Kontraksi Uterus",
            "Perdarahan Per Vaginam (PPV)","Pengobatan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 45; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(115);
            }else if(i==10){
                column.setPreferredWidth(115);
            }else if(i==11){
                column.setPreferredWidth(300);
            }else if(i==12){
                column.setPreferredWidth(60);
            }else if(i==13){
                column.setPreferredWidth(60);
            }else if(i==14){
                column.setPreferredWidth(60);
            }else if(i==15){
                column.setPreferredWidth(65);
            }else if(i==16){
                column.setPreferredWidth(57);
            }else if(i==17){
                column.setPreferredWidth(78);
            }else if(i==18){
                column.setPreferredWidth(78);
            }else if(i==19){
                column.setPreferredWidth(87);
            }else if(i==20){
                column.setPreferredWidth(87);
            }else if(i==21){
                column.setPreferredWidth(63);
            }else if(i==22){
                column.setPreferredWidth(66);
            }else if(i==23){
                column.setPreferredWidth(100);
            }else if(i==24){
                column.setPreferredWidth(55);
            }else if(i==25){
                column.setPreferredWidth(45);
            }else if(i==26){
                column.setPreferredWidth(180);
            }else if(i==27){
                column.setPreferredWidth(70);
            }else if(i==28){
                column.setPreferredWidth(70);
            }else if(i==29){
                column.setPreferredWidth(60);
            }else if(i==30){
                column.setPreferredWidth(79);
            }else if(i==31){
                column.setPreferredWidth(70);
            }else if(i==32){
                column.setPreferredWidth(59);
            }else if(i==33){
                column.setPreferredWidth(59);
            }else if(i==34){
                column.setPreferredWidth(59);
            }else if(i==35){
                column.setPreferredWidth(59);
            }else if(i==36){
                column.setPreferredWidth(68);
            }else if(i==37){
                column.setPreferredWidth(180);
            }else if(i==38){
                column.setPreferredWidth(60);
            }else if(i==39){
                column.setPreferredWidth(77);
            }else if(i==40){
                column.setPreferredWidth(68);
            }else if(i==41){
                column.setPreferredWidth(53);
            }else if(i==42){
                column.setPreferredWidth(180);
            }else if(i==43){
                column.setPreferredWidth(180);
            }else if(i==44){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Catatan.setDocument(new batasInput((int)2000).getKata(Catatan));
        WaktuPersalinanKala1.setDocument(new batasInput((int)5).getKata(WaktuPersalinanKala1));
        WaktuPersalinanKala2.setDocument(new batasInput((int)5).getKata(WaktuPersalinanKala2));
        WaktuPersalinanKala3.setDocument(new batasInput((int)5).getKata(WaktuPersalinanKala3));
        WaktuPersalinanJumlah.setDocument(new batasInput((int)5).getKata(WaktuPersalinanJumlah));
        JahitanLuar1.setDocument(new batasInput((int)5).getKata(JahitanLuar1));
        JahitanLuar2.setDocument(new batasInput((int)5).getKata(JahitanLuar2));
        JahitanDalam1.setDocument(new batasInput((int)5).getKata(JahitanDalam1));
        JahitanDalam2.setDocument(new batasInput((int)5).getKata(JahitanDalam2));
        ApgarScore.setDocument(new batasInput((int)20).getKata(ApgarScore));
        BB.setDocument(new batasInput((int)5).getKata(BB));
        PB.setDocument(new batasInput((int)5).getKata(PB));
        Kelainan.setDocument(new batasInput((int)100).getKata(Kelainan));
        Ketuban.setDocument(new batasInput((int)20).getKata(Ketuban));
        Placenta.setDocument(new batasInput((int)20).getKata(Placenta));
        Ukuran.setDocument(new batasInput((int)5).getKata(Ukuran));
        TaliPusat.setDocument(new batasInput((int)5).getKata(TaliPusat));
        Insertio.setDocument(new batasInput((int)20).getKata(Insertio));
        DarahKeluarKala1.setDocument(new batasInput((int)5).getKata(DarahKeluarKala1));
        DarahKeluarKala2.setDocument(new batasInput((int)5).getKata(DarahKeluarKala2));
        DarahKeluarKala3.setDocument(new batasInput((int)5).getKata(DarahKeluarKala3));
        DarahKeluarKala4.setDocument(new batasInput((int)5).getKata(DarahKeluarKala4));
        DarahKeluarJumlah.setDocument(new batasInput((int)5).getKata(DarahKeluarJumlah));
        KondisiUmum.setDocument(new batasInput((int)100).getKata(KondisiUmum));
        TD.setDocument(new batasInput((int)8).getKata(TD));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        RR.setDocument(new batasInput((int)5).getKata(RR));
        Suhu.setDocument(new batasInput((int)5).getKata(Suhu));
        KontraksiUterus.setDocument(new batasInput((int)100).getKata(KontraksiUterus));
        Perdarahan.setDocument(new batasInput((int)100).getKata(Perdarahan));
        Pengobatan.setDocument(new batasInput((int)600).getKata(Pengobatan));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
                    KdDokter.requestFocus();
                }
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    NIP.requestFocus();
                }
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
        
        WaktuPersalinanKala1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
        });
        
        WaktuPersalinanKala2.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
        });
        
        WaktuPersalinanKala3.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isWaktuPersalinan();
            }
        });
        
        DarahKeluarKala1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
        });
        
        DarahKeluarKala2.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
        });
        
        DarahKeluarKala3.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
        });
        
        DarahKeluarKala4.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                isDarahKeluar();
            }
        });
        
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

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCatatanPersalinan = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        WaktuSelesai = new widget.Tanggal();
        scrollPane20 = new widget.ScrollPane();
        Catatan = new widget.TextArea();
        label12 = new widget.Label();
        WaktuMulai = new widget.Tanggal();
        label15 = new widget.Label();
        NIP = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        WaktuPersalinanKala1 = new widget.TextBox();
        label16 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        label17 = new widget.Label();
        label18 = new widget.Label();
        label19 = new widget.Label();
        WaktuPersalinanKala2 = new widget.TextBox();
        label20 = new widget.Label();
        WaktuPersalinanKala3 = new widget.TextBox();
        label21 = new widget.Label();
        WaktuPersalinanJumlah = new widget.TextBox();
        label23 = new widget.Label();
        Perineum = new widget.ComboBox();
        label22 = new widget.Label();
        label24 = new widget.Label();
        JahitanLuar1 = new widget.TextBox();
        JahitanLuar2 = new widget.TextBox();
        label25 = new widget.Label();
        label26 = new widget.Label();
        JahitanDalam1 = new widget.TextBox();
        label27 = new widget.Label();
        JahitanDalam2 = new widget.TextBox();
        label28 = new widget.Label();
        label29 = new widget.Label();
        Anak = new widget.ComboBox();
        label30 = new widget.Label();
        StatusLahir = new widget.ComboBox();
        label31 = new widget.Label();
        ApgarScore = new widget.TextBox();
        label32 = new widget.Label();
        BB = new widget.TextBox();
        label33 = new widget.Label();
        label34 = new widget.Label();
        PB = new widget.TextBox();
        label35 = new widget.Label();
        label36 = new widget.Label();
        Kelainan = new widget.TextBox();
        label38 = new widget.Label();
        Ketuban = new widget.TextBox();
        label37 = new widget.Label();
        Placenta = new widget.TextBox();
        label39 = new widget.Label();
        label40 = new widget.Label();
        Ukuran = new widget.TextBox();
        label41 = new widget.Label();
        label42 = new widget.Label();
        TaliPusat = new widget.TextBox();
        label43 = new widget.Label();
        label44 = new widget.Label();
        Insertio = new widget.TextBox();
        label45 = new widget.Label();
        DarahKeluarJumlah = new widget.TextBox();
        label46 = new widget.Label();
        DarahKeluarKala3 = new widget.TextBox();
        label47 = new widget.Label();
        DarahKeluarKala2 = new widget.TextBox();
        label48 = new widget.Label();
        DarahKeluarKala1 = new widget.TextBox();
        label49 = new widget.Label();
        label50 = new widget.Label();
        label51 = new widget.Label();
        DarahKeluarKala4 = new widget.TextBox();
        label52 = new widget.Label();
        label53 = new widget.Label();
        KondisiUmum = new widget.TextBox();
        label54 = new widget.Label();
        label55 = new widget.Label();
        jLabel23 = new widget.Label();
        TD = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel20 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel28 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel22 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel24 = new widget.Label();
        label57 = new widget.Label();
        KontraksiUterus = new widget.TextBox();
        label56 = new widget.Label();
        Perdarahan = new widget.TextBox();
        label58 = new widget.Label();
        label59 = new widget.Label();
        label60 = new widget.Label();
        scrollPane21 = new widget.ScrollPane();
        Pengobatan = new widget.TextArea();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCatatanPersalinan.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanPersalinan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanPersalinan.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanPersalinan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanPersalinan.setText("Catatan Persalinan");
        MnCatatanPersalinan.setName("MnCatatanPersalinan"); // NOI18N
        MnCatatanPersalinan.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCatatanPersalinan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanPersalinanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCatatanPersalinan);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Catatan Persalinan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

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
        panelGlass8.add(BtnPrint);

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
        panelGlass8.add(BtnAll);

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
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 773));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("DPJP :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 70, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 70, 170, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(338, 70, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(74, 40, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 40, 70, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 750, 1);

        label11.setText("Selesai Persalinan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(475, 40, 110, 23);

        WaktuSelesai.setForeground(new java.awt.Color(50, 70, 50));
        WaktuSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-12-2023 06:26:49" }));
        WaktuSelesai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuSelesai.setName("WaktuSelesai"); // NOI18N
        WaktuSelesai.setOpaque(false);
        WaktuSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuSelesaiKeyPressed(evt);
            }
        });
        FormInput.add(WaktuSelesai);
        WaktuSelesai.setBounds(589, 40, 135, 23);

        scrollPane20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane20.setName("scrollPane20"); // NOI18N

        Catatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Catatan.setColumns(20);
        Catatan.setRows(20);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        scrollPane20.setViewportView(Catatan);

        FormInput.add(scrollPane20);
        scrollPane20.setBounds(40, 120, 684, 223);

        label12.setText("Mulai Persalinan :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(181, 40, 110, 23);

        WaktuMulai.setForeground(new java.awt.Color(50, 70, 50));
        WaktuMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-12-2023 06:26:50" }));
        WaktuMulai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuMulai.setName("WaktuMulai"); // NOI18N
        WaktuMulai.setOpaque(false);
        WaktuMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuMulaiKeyPressed(evt);
            }
        });
        FormInput.add(WaktuMulai);
        WaktuMulai.setBounds(295, 40, 135, 23);

        label15.setText("Bidan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(380, 70, 50, 23);

        NIP.setEditable(false);
        NIP.setName("NIP"); // NOI18N
        NIP.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(NIP);
        NIP.setBounds(432, 70, 90, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(524, 70, 170, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(696, 70, 28, 23);

        WaktuPersalinanKala1.setName("WaktuPersalinanKala1"); // NOI18N
        WaktuPersalinanKala1.setPreferredSize(new java.awt.Dimension(80, 23));
        WaktuPersalinanKala1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuPersalinanKala1KeyPressed(evt);
            }
        });
        FormInput.add(WaktuPersalinanKala1);
        WaktuPersalinanKala1.setBounds(296, 370, 60, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("1. Waktu Persalinan : Kala I + Kala II + Kala III");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(40, 370, 250, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 350, 750, 1);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("CATATAN PERSALINAN");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 100, 180, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("KESIMPULAN POSTPARTUM");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 350, 180, 23);

        label17.setText("=");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(0, 370, 285, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("+");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(359, 370, 23, 23);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setText("+");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(448, 370, 23, 23);

        WaktuPersalinanKala2.setName("WaktuPersalinanKala2"); // NOI18N
        WaktuPersalinanKala2.setPreferredSize(new java.awt.Dimension(80, 23));
        WaktuPersalinanKala2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuPersalinanKala2KeyPressed(evt);
            }
        });
        FormInput.add(WaktuPersalinanKala2);
        WaktuPersalinanKala2.setBounds(385, 370, 60, 23);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label20.setText("=");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(537, 370, 23, 23);

        WaktuPersalinanKala3.setName("WaktuPersalinanKala3"); // NOI18N
        WaktuPersalinanKala3.setPreferredSize(new java.awt.Dimension(80, 23));
        WaktuPersalinanKala3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuPersalinanKala3KeyPressed(evt);
            }
        });
        FormInput.add(WaktuPersalinanKala3);
        WaktuPersalinanKala3.setBounds(474, 370, 60, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("Jam");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(636, 370, 50, 23);

        WaktuPersalinanJumlah.setName("WaktuPersalinanJumlah"); // NOI18N
        WaktuPersalinanJumlah.setPreferredSize(new java.awt.Dimension(80, 23));
        WaktuPersalinanJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuPersalinanJumlahKeyPressed(evt);
            }
        });
        FormInput.add(WaktuPersalinanJumlah);
        WaktuPersalinanJumlah.setBounds(563, 370, 70, 23);

        label23.setText(":");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(0, 400, 106, 23);

        Perineum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Utuh", "Rupture", "Episiotomi" }));
        Perineum.setName("Perineum"); // NOI18N
        Perineum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerineumKeyPressed(evt);
            }
        });
        FormInput.add(Perineum);
        Perineum.setBounds(110, 400, 105, 23);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label22.setText("2. Perineum");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(40, 400, 80, 23);

        label24.setText("Jahitan Luar :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(228, 400, 85, 23);

        JahitanLuar1.setName("JahitanLuar1"); // NOI18N
        JahitanLuar1.setPreferredSize(new java.awt.Dimension(80, 23));
        JahitanLuar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JahitanLuar1KeyPressed(evt);
            }
        });
        FormInput.add(JahitanLuar1);
        JahitanLuar1.setBounds(317, 400, 60, 23);

        JahitanLuar2.setName("JahitanLuar2"); // NOI18N
        JahitanLuar2.setPreferredSize(new java.awt.Dimension(80, 23));
        JahitanLuar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JahitanLuar2KeyPressed(evt);
            }
        });
        FormInput.add(JahitanLuar2);
        JahitanLuar2.setBounds(406, 400, 60, 23);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label25.setText("dg");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(380, 400, 23, 23);

        label26.setText("Jahitan Dalam :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(481, 400, 90, 23);

        JahitanDalam1.setName("JahitanDalam1"); // NOI18N
        JahitanDalam1.setPreferredSize(new java.awt.Dimension(80, 23));
        JahitanDalam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JahitanDalam1KeyPressed(evt);
            }
        });
        FormInput.add(JahitanDalam1);
        JahitanDalam1.setBounds(575, 400, 60, 23);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label27.setText("dg");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(638, 400, 23, 23);

        JahitanDalam2.setName("JahitanDalam2"); // NOI18N
        JahitanDalam2.setPreferredSize(new java.awt.Dimension(80, 23));
        JahitanDalam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JahitanDalam2KeyPressed(evt);
            }
        });
        FormInput.add(JahitanDalam2);
        JahitanDalam2.setBounds(664, 400, 60, 23);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("3. Anak");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(40, 430, 50, 23);

        label29.setText(":");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(0, 430, 84, 23);

        Anak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        Anak.setName("Anak"); // NOI18N
        Anak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnakKeyPressed(evt);
            }
        });
        FormInput.add(Anak);
        Anak.setBounds(88, 430, 110, 23);

        label30.setText("Status :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(215, 430, 50, 23);

        StatusLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hidup", "Mati" }));
        StatusLahir.setName("StatusLahir"); // NOI18N
        StatusLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusLahirKeyPressed(evt);
            }
        });
        FormInput.add(StatusLahir);
        StatusLahir.setBounds(269, 430, 85, 23);

        label31.setText("APGAR Score :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(363, 430, 90, 23);

        ApgarScore.setName("ApgarScore"); // NOI18N
        ApgarScore.setPreferredSize(new java.awt.Dimension(80, 23));
        ApgarScore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ApgarScoreKeyPressed(evt);
            }
        });
        FormInput.add(ApgarScore);
        ApgarScore.setBounds(457, 430, 130, 23);

        label32.setText("BB :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(601, 430, 30, 23);

        BB.setName("BB"); // NOI18N
        BB.setPreferredSize(new java.awt.Dimension(80, 23));
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(635, 430, 60, 23);

        label33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label33.setText("cm");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(138, 460, 40, 23);

        label34.setText(":");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(0, 460, 71, 23);

        PB.setName("PB"); // NOI18N
        PB.setPreferredSize(new java.awt.Dimension(80, 23));
        PB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PBKeyPressed(evt);
            }
        });
        FormInput.add(PB);
        PB.setBounds(75, 460, 60, 23);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("gr");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(432, 490, 30, 23);

        label36.setText("Kelainan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(160, 460, 70, 23);

        Kelainan.setName("Kelainan"); // NOI18N
        Kelainan.setPreferredSize(new java.awt.Dimension(80, 23));
        Kelainan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelainanKeyPressed(evt);
            }
        });
        FormInput.add(Kelainan);
        Kelainan.setBounds(234, 460, 490, 23);

        label38.setText(":");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(0, 490, 99, 23);

        Ketuban.setName("Ketuban"); // NOI18N
        Ketuban.setPreferredSize(new java.awt.Dimension(80, 23));
        Ketuban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetubanKeyPressed(evt);
            }
        });
        FormInput.add(Ketuban);
        Ketuban.setBounds(103, 490, 75, 23);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label37.setText("4. Ketuban");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(40, 490, 80, 23);

        Placenta.setName("Placenta"); // NOI18N
        Placenta.setPreferredSize(new java.awt.Dimension(80, 23));
        Placenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PlacentaKeyPressed(evt);
            }
        });
        FormInput.add(Placenta);
        Placenta.setBounds(240, 490, 75, 23);

        label39.setText("Placenta :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(176, 490, 60, 23);

        label40.setText("Ukuran :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label40);
        label40.setBounds(305, 490, 60, 23);

        Ukuran.setName("Ukuran"); // NOI18N
        Ukuran.setPreferredSize(new java.awt.Dimension(80, 23));
        Ukuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UkuranKeyPressed(evt);
            }
        });
        FormInput.add(Ukuran);
        Ukuran.setBounds(369, 490, 60, 23);

        label41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label41.setText("gram");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label41);
        label41.setBounds(698, 430, 40, 23);

        label42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label42.setText("cm");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label42);
        label42.setBounds(574, 490, 40, 23);

        TaliPusat.setName("TaliPusat"); // NOI18N
        TaliPusat.setPreferredSize(new java.awt.Dimension(80, 23));
        TaliPusat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TaliPusatKeyPressed(evt);
            }
        });
        FormInput.add(TaliPusat);
        TaliPusat.setBounds(511, 490, 60, 23);

        label43.setText("Tali Pusat :");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label43);
        label43.setBounds(437, 490, 70, 23);

        label44.setText("Insertio :");
        label44.setName("label44"); // NOI18N
        label44.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label44);
        label44.setBounds(585, 490, 60, 23);

        Insertio.setName("Insertio"); // NOI18N
        Insertio.setPreferredSize(new java.awt.Dimension(80, 23));
        Insertio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InsertioKeyPressed(evt);
            }
        });
        FormInput.add(Insertio);
        Insertio.setBounds(649, 490, 75, 23);

        label45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label45.setText("cc");
        label45.setName("label45"); // NOI18N
        label45.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label45);
        label45.setBounds(712, 520, 30, 23);

        DarahKeluarJumlah.setName("DarahKeluarJumlah"); // NOI18N
        DarahKeluarJumlah.setPreferredSize(new java.awt.Dimension(80, 23));
        DarahKeluarJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeluarJumlahKeyPressed(evt);
            }
        });
        FormInput.add(DarahKeluarJumlah);
        DarahKeluarJumlah.setBounds(659, 520, 50, 23);

        label46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label46.setText("=");
        label46.setName("label46"); // NOI18N
        label46.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label46);
        label46.setBounds(633, 520, 23, 23);

        DarahKeluarKala3.setName("DarahKeluarKala3"); // NOI18N
        DarahKeluarKala3.setPreferredSize(new java.awt.Dimension(80, 23));
        DarahKeluarKala3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeluarKala3KeyPressed(evt);
            }
        });
        FormInput.add(DarahKeluarKala3);
        DarahKeluarKala3.setBounds(501, 520, 50, 23);

        label47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label47.setText("+");
        label47.setName("label47"); // NOI18N
        label47.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label47);
        label47.setBounds(475, 520, 23, 23);

        DarahKeluarKala2.setName("DarahKeluarKala2"); // NOI18N
        DarahKeluarKala2.setPreferredSize(new java.awt.Dimension(80, 23));
        DarahKeluarKala2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeluarKala2KeyPressed(evt);
            }
        });
        FormInput.add(DarahKeluarKala2);
        DarahKeluarKala2.setBounds(422, 520, 50, 23);

        label48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label48.setText("+");
        label48.setName("label48"); // NOI18N
        label48.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label48);
        label48.setBounds(396, 520, 23, 23);

        DarahKeluarKala1.setName("DarahKeluarKala1"); // NOI18N
        DarahKeluarKala1.setPreferredSize(new java.awt.Dimension(80, 23));
        DarahKeluarKala1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeluarKala1KeyPressed(evt);
            }
        });
        FormInput.add(DarahKeluarKala1);
        DarahKeluarKala1.setBounds(343, 520, 50, 23);

        label49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label49.setText("5. Darah Yang Keluar : Kala I + Kala II + Kala III + Kala IV");
        label49.setName("label49"); // NOI18N
        label49.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label49);
        label49.setBounds(40, 520, 290, 23);

        label50.setText("=");
        label50.setName("label50"); // NOI18N
        label50.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label50);
        label50.setBounds(0, 520, 337, 23);

        label51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label51.setText("+");
        label51.setName("label51"); // NOI18N
        label51.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label51);
        label51.setBounds(554, 520, 23, 23);

        DarahKeluarKala4.setName("DarahKeluarKala4"); // NOI18N
        DarahKeluarKala4.setPreferredSize(new java.awt.Dimension(80, 23));
        DarahKeluarKala4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeluarKala4KeyPressed(evt);
            }
        });
        FormInput.add(DarahKeluarKala4);
        DarahKeluarKala4.setBounds(580, 520, 50, 23);

        label52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label52.setText("6. Keadaan Ibu Post Partum (2 jam) :");
        label52.setName("label52"); // NOI18N
        label52.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label52);
        label52.setBounds(40, 550, 290, 23);

        label53.setText(":");
        label53.setName("label53"); // NOI18N
        label53.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label53);
        label53.setBounds(0, 570, 130, 23);

        KondisiUmum.setName("KondisiUmum"); // NOI18N
        KondisiUmum.setPreferredSize(new java.awt.Dimension(80, 23));
        KondisiUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiUmumKeyPressed(evt);
            }
        });
        FormInput.add(KondisiUmum);
        KondisiUmum.setBounds(134, 570, 590, 23);

        label54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label54.setText("PB");
        label54.setName("label54"); // NOI18N
        label54.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label54);
        label54.setBounds(52, 460, 30, 23);

        label55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label55.setText("Kondisi Umum");
        label55.setName("label55"); // NOI18N
        label55.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label55);
        label55.setBounds(52, 570, 130, 23);

        jLabel23.setText(":");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 600, 118, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(122, 600, 75, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("mmHg");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(200, 600, 40, 23);

        jLabel20.setText("Nadi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(261, 600, 40, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(305, 600, 60, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("x/menit");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(367, 600, 50, 23);

        jLabel28.setText("RR :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(430, 600, 40, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(474, 600, 60, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(537, 600, 50, 23);

        jLabel22.setText("Suhu :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(594, 600, 50, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(648, 600, 60, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("°C");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(711, 600, 30, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Tensi Darah");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(52, 600, 60, 23);

        label57.setText(":");
        label57.setName("label57"); // NOI18N
        label57.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label57);
        label57.setBounds(0, 630, 140, 23);

        KontraksiUterus.setName("KontraksiUterus"); // NOI18N
        KontraksiUterus.setPreferredSize(new java.awt.Dimension(80, 23));
        KontraksiUterus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraksiUterusKeyPressed(evt);
            }
        });
        FormInput.add(KontraksiUterus);
        KontraksiUterus.setBounds(144, 630, 580, 23);

        label56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label56.setText("Kontraksi Uterus");
        label56.setName("label56"); // NOI18N
        label56.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label56);
        label56.setBounds(52, 630, 130, 23);

        Perdarahan.setName("Perdarahan"); // NOI18N
        Perdarahan.setPreferredSize(new java.awt.Dimension(80, 23));
        Perdarahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerdarahanKeyPressed(evt);
            }
        });
        FormInput.add(Perdarahan);
        Perdarahan.setBounds(214, 660, 510, 23);

        label58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label58.setText("Perdarahan Per Vaginam (PPV)");
        label58.setName("label58"); // NOI18N
        label58.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label58);
        label58.setBounds(52, 660, 170, 23);

        label59.setText(":");
        label59.setName("label59"); // NOI18N
        label59.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label59);
        label59.setBounds(0, 660, 210, 23);

        label60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label60.setText("7. Pengobatan :");
        label60.setName("label60"); // NOI18N
        label60.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label60);
        label60.setBounds(40, 690, 290, 23);

        scrollPane21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane21.setName("scrollPane21"); // NOI18N

        Pengobatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Pengobatan.setColumns(20);
        Pengobatan.setRows(20);
        Pengobatan.setName("Pengobatan"); // NOI18N
        Pengobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengobatanKeyPressed(evt);
            }
        });
        scrollPane21.setViewportView(Pengobatan);

        FormInput.add(scrollPane21);
        scrollPane21.setBounds(52, 710, 672, 53);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Catatan Persalinan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-12-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-12-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Catatan Persalinan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Bidan");
        }else if(Catatan.getText().trim().equals("")){
            Valid.textKosong(Catatan,"Catatan Persalinan");
        }else if(Pengobatan.getText().trim().equals("")){
            Valid.textKosong(Pengobatan,"Pengobatan");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            } 
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Pengobatan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }              
            
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Bidan");
        }else if(Catatan.getText().trim().equals("")){
            Valid.textKosong(Catatan,"Catatan Persalinan");
        }else if(Pengobatan.getText().trim().equals("")){
            Valid.textKosong(Pengobatan,"Pengobatan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19))==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dokter Penanggung Jawab</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Bidan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Mulai Persalinan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Selesai Persalinan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Catatan Persalinan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>W.P.Kala 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>W.P.Kala 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>W.P.Kala 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>W.P.Jumlah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perineum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jahitan Luar 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jahitan Luar 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jahitan Dalam 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jahitan Dalam 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K. Anak</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Status Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>APGAR Score</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB(gram)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>PB(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kelainan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ketuban</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Placenta</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ukuran(gr)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tali Pusat(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Insertio</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>D.K.Kala 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>D.K.Kala 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>D.K.Kala 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>D.K.Kala 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>D.K. Jumlah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kondisi_umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kontraksi Uterus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perdarahan Per Vaginam (PPV)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pengobatan</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,41).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,42).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,43).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,44).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
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
                bg.close();

                File f = new File("DataCatatanPersalinan.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CATATAN PERSALINAN<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,WaktuSelesai,Catatan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void WaktuSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuSelesaiKeyPressed
        Valid.pindah2(evt,WaktuMulai,BtnDokter);
    }//GEN-LAST:event_WaktuSelesaiKeyPressed

    private void MnCatatanPersalinanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanPersalinanActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString())); 
            finger2=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),7).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString())); 
            Valid.MyReportqry("rptCetakCatatanPersalinan.jasper","report","::[ Laporan Catatan Persalinan ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,catatan_persalinan.mulai,"+
                "catatan_persalinan.selesai,catatan_persalinan.kd_dokter,catatan_persalinan.nip,catatan_persalinan.catatan,catatan_persalinan.waktu_persalinan_kala_1,"+
                "catatan_persalinan.waktu_persalinan_kala_2,catatan_persalinan.waktu_persalinan_kala_3,catatan_persalinan.waktu_persalinan_jumlah,catatan_persalinan.perineum,"+
                "catatan_persalinan.jahitan_luar_1,catatan_persalinan.jahitan_luar_2,catatan_persalinan.jahitan_dalam_1,catatan_persalinan.jahitan_dalam_2,catatan_persalinan.anak,"+
                "catatan_persalinan.status_lahir,catatan_persalinan.apgar_score,catatan_persalinan.bb,catatan_persalinan.pb,catatan_persalinan.kelainan,catatan_persalinan.ketuban,"+
                "catatan_persalinan.placenta,catatan_persalinan.ukuran,catatan_persalinan.tali_pusat,catatan_persalinan.insertio,catatan_persalinan.darah_keluar_kala_1,"+
                "catatan_persalinan.darah_keluar_kala_2,catatan_persalinan.darah_keluar_kala_3,catatan_persalinan.darah_keluar_kala_4,catatan_persalinan.darah_keluar_jumlah,"+
                "catatan_persalinan.kondisi_umum,catatan_persalinan.td,catatan_persalinan.nadi,catatan_persalinan.rr,catatan_persalinan.suhu,catatan_persalinan.kontraksi_uterus,"+
                "catatan_persalinan.ppv,catatan_persalinan.pengobatan,dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join catatan_persalinan on reg_periksa.no_rawat=catatan_persalinan.no_rawat "+
                "inner join dokter on catatan_persalinan.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on catatan_persalinan.nip=petugas.nip where catatan_persalinan.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnCatatanPersalinanActionPerformed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah2(evt,BtnDokter,WaktuPersalinanKala1);
    }//GEN-LAST:event_CatatanKeyPressed

    private void WaktuMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuMulaiKeyPressed
       Valid.pindah2(evt,Pengobatan,WaktuSelesai);
    }//GEN-LAST:event_WaktuMulaiKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,BtnDokter,WaktuPersalinanKala1);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void WaktuPersalinanKala1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuPersalinanKala1KeyPressed
        Valid.pindah(evt,Catatan,WaktuPersalinanKala2);
    }//GEN-LAST:event_WaktuPersalinanKala1KeyPressed

    private void WaktuPersalinanKala2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuPersalinanKala2KeyPressed
        Valid.pindah(evt,WaktuPersalinanKala1,WaktuPersalinanKala3);
    }//GEN-LAST:event_WaktuPersalinanKala2KeyPressed

    private void WaktuPersalinanKala3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuPersalinanKala3KeyPressed
        Valid.pindah(evt,WaktuPersalinanKala2,WaktuPersalinanJumlah);
    }//GEN-LAST:event_WaktuPersalinanKala3KeyPressed

    private void WaktuPersalinanJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuPersalinanJumlahKeyPressed
        Valid.pindah(evt,WaktuPersalinanKala3,Perineum);
    }//GEN-LAST:event_WaktuPersalinanJumlahKeyPressed

    private void PerineumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerineumKeyPressed
        Valid.pindah(evt,WaktuPersalinanJumlah,JahitanLuar1);
    }//GEN-LAST:event_PerineumKeyPressed

    private void JahitanLuar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JahitanLuar1KeyPressed
        Valid.pindah(evt,Perineum,JahitanLuar2);
    }//GEN-LAST:event_JahitanLuar1KeyPressed

    private void JahitanLuar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JahitanLuar2KeyPressed
        Valid.pindah(evt,JahitanLuar1,JahitanDalam1);
    }//GEN-LAST:event_JahitanLuar2KeyPressed

    private void JahitanDalam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JahitanDalam1KeyPressed
        Valid.pindah(evt,JahitanLuar2,JahitanDalam2);
    }//GEN-LAST:event_JahitanDalam1KeyPressed

    private void JahitanDalam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JahitanDalam2KeyPressed
        Valid.pindah(evt,JahitanDalam1,Anak);
    }//GEN-LAST:event_JahitanDalam2KeyPressed

    private void AnakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnakKeyPressed
        Valid.pindah(evt,JahitanDalam2,StatusLahir);
    }//GEN-LAST:event_AnakKeyPressed

    private void StatusLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusLahirKeyPressed
        Valid.pindah(evt,Anak,ApgarScore);
    }//GEN-LAST:event_StatusLahirKeyPressed

    private void ApgarScoreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ApgarScoreKeyPressed
        Valid.pindah(evt,StatusLahir,BB);
    }//GEN-LAST:event_ApgarScoreKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,ApgarScore,PB);
    }//GEN-LAST:event_BBKeyPressed

    private void PBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PBKeyPressed
        Valid.pindah(evt,BB,Kelainan);
    }//GEN-LAST:event_PBKeyPressed

    private void KelainanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelainanKeyPressed
        Valid.pindah(evt,PB,Ketuban);
    }//GEN-LAST:event_KelainanKeyPressed

    private void KetubanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetubanKeyPressed
        Valid.pindah(evt,Kelainan,Placenta);
    }//GEN-LAST:event_KetubanKeyPressed

    private void PlacentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PlacentaKeyPressed
        Valid.pindah(evt,Ketuban,Ukuran);
    }//GEN-LAST:event_PlacentaKeyPressed

    private void UkuranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UkuranKeyPressed
        Valid.pindah(evt,Placenta,TaliPusat);
    }//GEN-LAST:event_UkuranKeyPressed

    private void TaliPusatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TaliPusatKeyPressed
        Valid.pindah(evt,Ukuran,Insertio);
    }//GEN-LAST:event_TaliPusatKeyPressed

    private void InsertioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InsertioKeyPressed
        Valid.pindah(evt,TaliPusat,DarahKeluarKala1);
    }//GEN-LAST:event_InsertioKeyPressed

    private void DarahKeluarJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeluarJumlahKeyPressed
        Valid.pindah(evt,DarahKeluarKala4,KondisiUmum);
    }//GEN-LAST:event_DarahKeluarJumlahKeyPressed

    private void DarahKeluarKala3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeluarKala3KeyPressed
        Valid.pindah(evt,DarahKeluarKala2,DarahKeluarKala4);
    }//GEN-LAST:event_DarahKeluarKala3KeyPressed

    private void DarahKeluarKala2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeluarKala2KeyPressed
        Valid.pindah(evt,DarahKeluarKala1,DarahKeluarKala3);
    }//GEN-LAST:event_DarahKeluarKala2KeyPressed

    private void DarahKeluarKala1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeluarKala1KeyPressed
        Valid.pindah(evt,Insertio,DarahKeluarKala2);
    }//GEN-LAST:event_DarahKeluarKala1KeyPressed

    private void DarahKeluarKala4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeluarKala4KeyPressed
        Valid.pindah(evt,DarahKeluarKala3,DarahKeluarJumlah);
    }//GEN-LAST:event_DarahKeluarKala4KeyPressed

    private void KondisiUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiUmumKeyPressed
        Valid.pindah(evt,DarahKeluarJumlah,TD);
    }//GEN-LAST:event_KondisiUmumKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,KondisiUmum,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,KontraksiUterus);
    }//GEN-LAST:event_SuhuKeyPressed

    private void KontraksiUterusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontraksiUterusKeyPressed
        Valid.pindah(evt,Suhu,Perdarahan);
    }//GEN-LAST:event_KontraksiUterusKeyPressed

    private void PerdarahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerdarahanKeyPressed
        Valid.pindah(evt,KontraksiUterus,Pengobatan);
    }//GEN-LAST:event_PerdarahanKeyPressed

    private void PengobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengobatanKeyPressed
        Valid.pindah2(evt,Perdarahan,BtnSimpan);
    }//GEN-LAST:event_PengobatanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMCatatanPersalinan dialog = new RMCatatanPersalinan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Anak;
    private widget.TextBox ApgarScore;
    private widget.TextBox BB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextArea Catatan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DarahKeluarJumlah;
    private widget.TextBox DarahKeluarKala1;
    private widget.TextBox DarahKeluarKala2;
    private widget.TextBox DarahKeluarKala3;
    private widget.TextBox DarahKeluarKala4;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Insertio;
    private widget.TextBox JahitanDalam1;
    private widget.TextBox JahitanDalam2;
    private widget.TextBox JahitanLuar1;
    private widget.TextBox JahitanLuar2;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox Kelainan;
    private widget.TextBox Ketuban;
    private widget.TextBox KondisiUmum;
    private widget.TextBox KontraksiUterus;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnCatatanPersalinan;
    private widget.TextBox NIP;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.TextBox PB;
    private widget.TextArea Pengobatan;
    private widget.TextBox Perdarahan;
    private widget.ComboBox Perineum;
    private widget.TextBox Placenta;
    private widget.TextBox RR;
    private widget.ScrollPane Scroll;
    private widget.ComboBox StatusLahir;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TaliPusat;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox Ukuran;
    private widget.Tanggal WaktuMulai;
    private widget.TextBox WaktuPersalinanJumlah;
    private widget.TextBox WaktuPersalinanKala1;
    private widget.TextBox WaktuPersalinanKala2;
    private widget.TextBox WaktuPersalinanKala3;
    private widget.Tanggal WaktuSelesai;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
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
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.Label label43;
    private widget.Label label44;
    private widget.Label label45;
    private widget.Label label46;
    private widget.Label label47;
    private widget.Label label48;
    private widget.Label label49;
    private widget.Label label50;
    private widget.Label label51;
    private widget.Label label52;
    private widget.Label label53;
    private widget.Label label54;
    private widget.Label label55;
    private widget.Label label56;
    private widget.Label label57;
    private widget.Label label58;
    private widget.Label label59;
    private widget.Label label60;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane20;
    private widget.ScrollPane scrollPane21;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,catatan_persalinan.mulai,"+
                        "catatan_persalinan.selesai,catatan_persalinan.kd_dokter,catatan_persalinan.nip,catatan_persalinan.catatan,catatan_persalinan.waktu_persalinan_kala_1,"+
                        "catatan_persalinan.waktu_persalinan_kala_2,catatan_persalinan.waktu_persalinan_kala_3,catatan_persalinan.waktu_persalinan_jumlah,catatan_persalinan.perineum,"+
                        "catatan_persalinan.jahitan_luar_1,catatan_persalinan.jahitan_luar_2,catatan_persalinan.jahitan_dalam_1,catatan_persalinan.jahitan_dalam_2,catatan_persalinan.anak,"+
                        "catatan_persalinan.status_lahir,catatan_persalinan.apgar_score,catatan_persalinan.bb,catatan_persalinan.pb,catatan_persalinan.kelainan,catatan_persalinan.ketuban,"+
                        "catatan_persalinan.placenta,catatan_persalinan.ukuran,catatan_persalinan.tali_pusat,catatan_persalinan.insertio,catatan_persalinan.darah_keluar_kala_1,"+
                        "catatan_persalinan.darah_keluar_kala_2,catatan_persalinan.darah_keluar_kala_3,catatan_persalinan.darah_keluar_kala_4,catatan_persalinan.darah_keluar_jumlah,"+
                        "catatan_persalinan.kondisi_umum,catatan_persalinan.td,catatan_persalinan.nadi,catatan_persalinan.rr,catatan_persalinan.suhu,catatan_persalinan.kontraksi_uterus,"+
                        "catatan_persalinan.ppv,catatan_persalinan.pengobatan,dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join catatan_persalinan on reg_periksa.no_rawat=catatan_persalinan.no_rawat "+
                        "inner join dokter on catatan_persalinan.kd_dokter=dokter.kd_dokter "+
                        "inner join petugas on catatan_persalinan.nip=petugas.nip where catatan_persalinan.mulai between ? and ? order by catatan_persalinan.mulai");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,catatan_persalinan.mulai,"+
                        "catatan_persalinan.selesai,catatan_persalinan.kd_dokter,catatan_persalinan.nip,catatan_persalinan.catatan,catatan_persalinan.waktu_persalinan_kala_1,"+
                        "catatan_persalinan.waktu_persalinan_kala_2,catatan_persalinan.waktu_persalinan_kala_3,catatan_persalinan.waktu_persalinan_jumlah,catatan_persalinan.perineum,"+
                        "catatan_persalinan.jahitan_luar_1,catatan_persalinan.jahitan_luar_2,catatan_persalinan.jahitan_dalam_1,catatan_persalinan.jahitan_dalam_2,catatan_persalinan.anak,"+
                        "catatan_persalinan.status_lahir,catatan_persalinan.apgar_score,catatan_persalinan.bb,catatan_persalinan.pb,catatan_persalinan.kelainan,catatan_persalinan.ketuban,"+
                        "catatan_persalinan.placenta,catatan_persalinan.ukuran,catatan_persalinan.tali_pusat,catatan_persalinan.insertio,catatan_persalinan.darah_keluar_kala_1,"+
                        "catatan_persalinan.darah_keluar_kala_2,catatan_persalinan.darah_keluar_kala_3,catatan_persalinan.darah_keluar_kala_4,catatan_persalinan.darah_keluar_jumlah,"+
                        "catatan_persalinan.kondisi_umum,catatan_persalinan.td,catatan_persalinan.nadi,catatan_persalinan.rr,catatan_persalinan.suhu,catatan_persalinan.kontraksi_uterus,"+
                        "catatan_persalinan.ppv,catatan_persalinan.pengobatan,dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join catatan_persalinan on reg_periksa.no_rawat=catatan_persalinan.no_rawat "+
                        "inner join dokter on catatan_persalinan.kd_dokter=dokter.kd_dokter "+
                        "inner join petugas on catatan_persalinan.nip=petugas.nip where "+
                        "catatan_persalinan.mulai between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "catatan_persalinan.status_lahir like ? or catatan_persalinan.pengobatan like ?) order by catatan_persalinan.mulai");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("nip"),rs.getString("nama"),
                        rs.getString("mulai"),rs.getString("selesai"),rs.getString("catatan"),rs.getString("waktu_persalinan_kala_1"),rs.getString("waktu_persalinan_kala_2"),rs.getString("waktu_persalinan_kala_3"),rs.getString("waktu_persalinan_jumlah"),
                        rs.getString("perineum"),rs.getString("jahitan_luar_1"),rs.getString("jahitan_luar_2"),rs.getString("jahitan_dalam_1"),rs.getString("jahitan_dalam_2"),rs.getString("anak"),rs.getString("status_lahir"),rs.getString("apgar_score"),
                        rs.getString("bb"),rs.getString("pb"),rs.getString("kelainan"),rs.getString("ketuban"),rs.getString("placenta"),rs.getString("ukuran"),rs.getString("tali_pusat"),rs.getString("insertio"),rs.getString("darah_keluar_kala_1"),
                        rs.getString("darah_keluar_kala_2"),rs.getString("darah_keluar_kala_3"),rs.getString("darah_keluar_kala_4"),rs.getString("darah_keluar_jumlah"),rs.getString("kondisi_umum"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),
                        rs.getString("suhu"),rs.getString("kontraksi_uterus"),rs.getString("ppv"),rs.getString("pengobatan")
                    });
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
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        WaktuMulai.setDate(new Date());
        WaktuSelesai.setDate(new Date());
        Catatan.setText("");
        WaktuPersalinanKala1.setText("");
        WaktuPersalinanKala2.setText("");
        WaktuPersalinanKala3.setText("");
        WaktuPersalinanJumlah.setText("");
        Perineum.setSelectedIndex(0);
        JahitanLuar1.setText("");
        JahitanLuar2.setText("");
        JahitanDalam1.setText("");
        JahitanDalam2.setText("");
        Anak.setSelectedIndex(0);
        StatusLahir.setSelectedIndex(0);
        ApgarScore.setText("");
        BB.setText("");
        PB.setText("");
        Kelainan.setText("");
        Ketuban.setText("");
        Placenta.setText("");
        Ukuran.setText("");
        TaliPusat.setText("");
        Insertio.setText("");
        DarahKeluarKala1.setText("");
        DarahKeluarKala2.setText("");
        DarahKeluarKala3.setText("");
        DarahKeluarKala4.setText("");
        DarahKeluarJumlah.setText("");
        KondisiUmum.setText("");
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        KontraksiUterus.setText("");
        Perdarahan.setText("");
        Pengobatan.setText("");
        Catatan.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            Catatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            WaktuPersalinanKala1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            WaktuPersalinanKala2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            WaktuPersalinanKala3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            WaktuPersalinanJumlah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Perineum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            JahitanLuar1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            JahitanLuar2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            JahitanDalam1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            JahitanDalam2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Anak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            StatusLahir.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            ApgarScore.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            PB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Kelainan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Ketuban.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Placenta.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Ukuran.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            TaliPusat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Insertio.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            DarahKeluarKala1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            DarahKeluarKala2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            DarahKeluarKala3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            DarahKeluarKala4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            DarahKeluarJumlah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KondisiUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            KontraksiUterus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Perdarahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Pengobatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Valid.SetTgl2(WaktuMulai,tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Valid.SetTgl2(WaktuSelesai,tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getcatatan_persalinan());
        BtnHapus.setEnabled(akses.getcatatan_persalinan());
        BtnEdit.setEnabled(akses.getcatatan_persalinan());
        BtnEdit.setEnabled(akses.getcatatan_persalinan());
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            BtnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(NIP.getText()));
            if(NmPetugas.getText().equals("")){
                NIP.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }           
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from catatan_persalinan where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("catatan_persalinan","no_rawat=?","no_rawat=?,mulai=?,selesai=?,kd_dokter=?,nip=?,catatan=?,waktu_persalinan_kala_1=?,"+
                "waktu_persalinan_kala_2=?,waktu_persalinan_kala_3=?,waktu_persalinan_jumlah=?,perineum=?,jahitan_luar_1=?,jahitan_luar_2=?,jahitan_dalam_1=?,"+
                "jahitan_dalam_2=?,anak=?,status_lahir=?,apgar_score=?,bb=?,pb=?,kelainan=?,ketuban=?,placenta=?,ukuran=?,tali_pusat=?,insertio=?,"+
                "darah_keluar_kala_1=?,darah_keluar_kala_2=?,darah_keluar_kala_3=?,darah_keluar_kala_4=?,darah_keluar_jumlah=?,kondisi_umum=?,td=?,nadi=?,rr=?,"+
                "suhu=?,kontraksi_uterus=?,ppv=?,pengobatan=?",40,new String[]{
                TNoRw.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),
                KdDokter.getText(),NIP.getText(),Catatan.getText(),WaktuPersalinanKala1.getText(),WaktuPersalinanKala2.getText(),
                WaktuPersalinanKala3.getText(),WaktuPersalinanJumlah.getText(),Perineum.getSelectedItem().toString(),JahitanLuar1.getText(),
                JahitanLuar2.getText(),JahitanDalam1.getText(),JahitanDalam2.getText(),Anak.getSelectedItem().toString(),StatusLahir.getSelectedItem().toString(),
                ApgarScore.getText(),BB.getText(),PB.getText(),Kelainan.getText(),Ketuban.getText(),Placenta.getText(),Ukuran.getText(),TaliPusat.getText(),
                Insertio.getText(),DarahKeluarKala1.getText(),DarahKeluarKala2.getText(),DarahKeluarKala3.getText(),DarahKeluarKala4.getText(),
                DarahKeluarJumlah.getText(),KondisiUmum.getText(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),KontraksiUterus.getText(),
                Perdarahan.getText(),Pengobatan.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(Catatan.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(WaktuPersalinanKala1.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(WaktuPersalinanKala2.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(WaktuPersalinanKala3.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(WaktuPersalinanJumlah.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(Perineum.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(JahitanLuar1.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(JahitanLuar2.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(JahitanDalam1.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(JahitanDalam2.getText(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(Anak.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(StatusLahir.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(ApgarScore.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(PB.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(Kelainan.getText(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(Ketuban.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(Placenta.getText(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(Ukuran.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(TaliPusat.getText(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(Insertio.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(DarahKeluarKala1.getText(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(DarahKeluarKala2.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(DarahKeluarKala3.getText(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(DarahKeluarKala4.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(DarahKeluarJumlah.getText(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(KondisiUmum.getText(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(KontraksiUterus.getText(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(Perdarahan.getText(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(Pengobatan.getText(),tbObat.getSelectedRow(),44);
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
    
    private void isWaktuPersalinan(){
        if((!WaktuPersalinanKala1.getText().equals(""))&&(!WaktuPersalinanKala2.getText().equals(""))&&(!WaktuPersalinanKala3.getText().equals(""))){
            try {
                WaktuPersalinanJumlah.setText(Valid.SetAngka8(Valid.SetAngka(WaktuPersalinanKala1.getText())+Valid.SetAngka(WaktuPersalinanKala2.getText())+Valid.SetAngka(WaktuPersalinanKala3.getText()),2)+"");
            } catch (Exception e) {
                WaktuPersalinanJumlah.setText("");
            }
        }
    }
    
    private void isDarahKeluar(){
        if((!DarahKeluarKala1.getText().equals(""))&&(!DarahKeluarKala2.getText().equals(""))&&(!DarahKeluarKala3.getText().equals(""))&&(!DarahKeluarKala4.getText().equals(""))){
            try {
                DarahKeluarJumlah.setText(Valid.SetAngka8(Valid.SetAngka(DarahKeluarKala1.getText())+Valid.SetAngka(DarahKeluarKala2.getText())+Valid.SetAngka(DarahKeluarKala3.getText())+Valid.SetAngka(DarahKeluarKala4.getText()),2)+"");
            } catch (Exception e) {
                DarahKeluarJumlah.setText("");
            }
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("catatan_persalinan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",39,new String[]{
                TNoRw.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),
                KdDokter.getText(),NIP.getText(),Catatan.getText(),WaktuPersalinanKala1.getText(),WaktuPersalinanKala2.getText(),
                WaktuPersalinanKala3.getText(),WaktuPersalinanJumlah.getText(),Perineum.getSelectedItem().toString(),JahitanLuar1.getText(),
                JahitanLuar2.getText(),JahitanDalam1.getText(),JahitanDalam2.getText(),Anak.getSelectedItem().toString(),StatusLahir.getSelectedItem().toString(),
                ApgarScore.getText(),BB.getText(),PB.getText(),Kelainan.getText(),Ketuban.getText(),Placenta.getText(),Ukuran.getText(),TaliPusat.getText(),
                Insertio.getText(),DarahKeluarKala1.getText(),DarahKeluarKala2.getText(),DarahKeluarKala3.getText(),DarahKeluarKala4.getText(),
                DarahKeluarJumlah.getText(),KondisiUmum.getText(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),KontraksiUterus.getText(),
                Perdarahan.getText(),Pengobatan.getText()
            })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdDokter.getText(),NmDokter.getText(),NIP.getText(),
                NmPetugas.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),Catatan.getText(),
                WaktuPersalinanKala1.getText(),WaktuPersalinanKala2.getText(),WaktuPersalinanKala3.getText(),WaktuPersalinanJumlah.getText(),
                Perineum.getSelectedItem().toString(),JahitanLuar1.getText(),JahitanLuar2.getText(),JahitanDalam1.getText(),JahitanDalam2.getText(),
                Anak.getSelectedItem().toString(),StatusLahir.getSelectedItem().toString(),ApgarScore.getText(),BB.getText(),PB.getText(),Kelainan.getText(),
                Ketuban.getText(),Placenta.getText(),Ukuran.getText(),TaliPusat.getText(),Insertio.getText(),DarahKeluarKala1.getText(),DarahKeluarKala2.getText(),
                DarahKeluarKala3.getText(),DarahKeluarKala4.getText(),DarahKeluarJumlah.getText(),KondisiUmum.getText(),TD.getText(),Nadi.getText(),
                RR.getText(),Suhu.getText(),KontraksiUterus.getText(),Perdarahan.getText(),Pengobatan.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }
    }
}

/*
 * Kontribusi RSUD Prembun
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMSkriningInstrumenSDQ extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningInstrumenSDQ(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.R.M.","Nama Pasien","Tgl.Lahir","JK","NIP","Petugas","Tanggal",
                "SDQ1","N.SDQ1","SDQ2","N.SDQ2","SDQ3","N.SDQ3","SDQ4","N.SDQ4","SDQ5","N.SDQ5",
                "SDQ6","N.SDQ6","SDQ7","N.SDQ7","SDQ8","N.SDQ8","SDQ9","N.SDQ9","SDQ10","N.SDQ10",
                "SDQ11","N.SDQ11","SDQ12","N.SDQ12","SDQ13","N.SDQ13","SDQ14","N.SDQ14","SDQ15","N.SDQ15",
                "SDQ16","N.SDQ16","SDQ17","N.SDQ17","SDQ18","N.SDQ18","SDQ19","N.SDQ19","SDQ20","N.SDQ20",
                "SDQ21","N.SDQ21","SDQ22","N.SDQ22","SDQ23","N.SDQ23","SDQ24","N.SDQ24","SDQ25","N.SDQ25",
                "Nilai Total SDQ","Gejala Emosional (E)","N.GE","Masalah Perilaku (C)","N.MP","Hiperaktivitas (H)","N.H",
                "Teman Sebaya (P)","N.TS","Kekuatan(Pr)","N.KPr","Kesulitan","N.K","Keterangan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 72; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(50);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(50);
            }else if(i==16){
                column.setPreferredWidth(80);
            }else if(i==17){
                column.setPreferredWidth(50);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(50);
            }else if(i==20){
                column.setPreferredWidth(80);
            }else if(i==21){
                column.setPreferredWidth(50);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(50);
            }else if(i==24){
                column.setPreferredWidth(80);
            }else if(i==25){
                column.setPreferredWidth(50);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(50);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(50);
            }else if(i==30){
                column.setPreferredWidth(80);
            }else if(i==31){
                column.setPreferredWidth(50);
            }else if(i==32){
                column.setPreferredWidth(80);
            }else if(i==33){
                column.setPreferredWidth(50);
            }else if(i==34){
                column.setPreferredWidth(80);
            }else if(i==35){
                column.setPreferredWidth(50);
            }else if(i==36){
                column.setPreferredWidth(80);
            }else if(i==37){
                column.setPreferredWidth(50);
            }else if(i==38){
                column.setPreferredWidth(80);
            }else if(i==39){
                column.setPreferredWidth(50);
            }else if(i==40){
                column.setPreferredWidth(80);
            }else if(i==41){
                column.setPreferredWidth(50);
            }else if(i==42){
                column.setPreferredWidth(80);
            }else if(i==43){
                column.setPreferredWidth(50);
            }else if(i==44){
                column.setPreferredWidth(80);
            }else if(i==45){
                column.setPreferredWidth(50);
            }else if(i==46){
                column.setPreferredWidth(80);
            }else if(i==47){
                column.setPreferredWidth(50);
            }else if(i==48){
                column.setPreferredWidth(80);
            }else if(i==49){
                column.setPreferredWidth(50);
            }else if(i==50){
                column.setPreferredWidth(80);
            }else if(i==51){
                column.setPreferredWidth(50);
            }else if(i==52){
                column.setPreferredWidth(80);
            }else if(i==53){
                column.setPreferredWidth(50);
            }else if(i==54){
                column.setPreferredWidth(80);
            }else if(i==55){
                column.setPreferredWidth(50);
            }else if(i==56){
                column.setPreferredWidth(80);
            }else if(i==57){
                column.setPreferredWidth(50);
            }else if(i==58){
                column.setPreferredWidth(82);
            }else if(i==59){
                column.setPreferredWidth(120);
            }else if(i==60){
                column.setPreferredWidth(45);
            }else if(i==61){
                column.setPreferredWidth(120);
            }else if(i==62){
                column.setPreferredWidth(45);
            }else if(i==63){
                column.setPreferredWidth(120);
            }else if(i==64){
                column.setPreferredWidth(45);
            }else if(i==65){
                column.setPreferredWidth(120);
            }else if(i==66){
                column.setPreferredWidth(45);
            }else if(i==67){
                column.setPreferredWidth(120);
            }else if(i==68){
                column.setPreferredWidth(45);
            }else if(i==69){
                column.setPreferredWidth(120);
            }else if(i==70){
                column.setPreferredWidth(45);
            }else if(i==71){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        Keterangan.setDocument(new batasInput((int)200).getKata(Keterangan));
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                NIP.requestFocus();
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
        
        ChkInput.setSelected(false);
        isForm();
        jam();
        
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
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnFormulirSkriningInstrumenSDQ = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        Jk = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        SDQ1 = new widget.ComboBox();
        jLabel218 = new widget.Label();
        NilaiSDQ1 = new widget.TextBox();
        jLabel220 = new widget.Label();
        jLabel222 = new widget.Label();
        jLabel223 = new widget.Label();
        jLabel225 = new widget.Label();
        jLabel226 = new widget.Label();
        jLabel228 = new widget.Label();
        jLabel229 = new widget.Label();
        jLabel231 = new widget.Label();
        jLabel234 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel232 = new widget.Label();
        jLabel236 = new widget.Label();
        jLabel239 = new widget.Label();
        jLabel240 = new widget.Label();
        jLabel241 = new widget.Label();
        jLabel242 = new widget.Label();
        jLabel230 = new widget.Label();
        jLabel233 = new widget.Label();
        jLabel243 = new widget.Label();
        jLabel244 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel235 = new widget.Label();
        jLabel237 = new widget.Label();
        jLabel238 = new widget.Label();
        jLabel245 = new widget.Label();
        jLabel246 = new widget.Label();
        jLabel247 = new widget.Label();
        jLabel248 = new widget.Label();
        jLabel249 = new widget.Label();
        jLabel250 = new widget.Label();
        jLabel251 = new widget.Label();
        jLabel252 = new widget.Label();
        jLabel253 = new widget.Label();
        jLabel254 = new widget.Label();
        jLabel255 = new widget.Label();
        jLabel256 = new widget.Label();
        SDQ2 = new widget.ComboBox();
        SDQ3 = new widget.ComboBox();
        SDQ4 = new widget.ComboBox();
        SDQ5 = new widget.ComboBox();
        SDQ6 = new widget.ComboBox();
        SDQ7 = new widget.ComboBox();
        SDQ8 = new widget.ComboBox();
        SDQ9 = new widget.ComboBox();
        SDQ10 = new widget.ComboBox();
        SDQ11 = new widget.ComboBox();
        SDQ12 = new widget.ComboBox();
        SDQ13 = new widget.ComboBox();
        SDQ14 = new widget.ComboBox();
        SDQ15 = new widget.ComboBox();
        SDQ16 = new widget.ComboBox();
        SDQ17 = new widget.ComboBox();
        SDQ18 = new widget.ComboBox();
        SDQ19 = new widget.ComboBox();
        SDQ20 = new widget.ComboBox();
        SDQ21 = new widget.ComboBox();
        SDQ22 = new widget.ComboBox();
        SDQ23 = new widget.ComboBox();
        SDQ24 = new widget.ComboBox();
        SDQ25 = new widget.ComboBox();
        NilaiSDQ2 = new widget.TextBox();
        NilaiSDQ3 = new widget.TextBox();
        NilaiSDQ4 = new widget.TextBox();
        NilaiSDQ5 = new widget.TextBox();
        NilaiSDQ6 = new widget.TextBox();
        NilaiSDQ7 = new widget.TextBox();
        NilaiSDQ8 = new widget.TextBox();
        NilaiSDQ9 = new widget.TextBox();
        NilaiSDQ10 = new widget.TextBox();
        NilaiSDQ11 = new widget.TextBox();
        jLabel257 = new widget.Label();
        NilaiSDQ12 = new widget.TextBox();
        jLabel258 = new widget.Label();
        jLabel259 = new widget.Label();
        NilaiSDQ13 = new widget.TextBox();
        jLabel260 = new widget.Label();
        NilaiSDQ14 = new widget.TextBox();
        jLabel261 = new widget.Label();
        NilaiSDQ15 = new widget.TextBox();
        jLabel262 = new widget.Label();
        NilaiSDQ16 = new widget.TextBox();
        jLabel263 = new widget.Label();
        NilaiSDQ17 = new widget.TextBox();
        jLabel264 = new widget.Label();
        NilaiSDQ18 = new widget.TextBox();
        jLabel265 = new widget.Label();
        NilaiSDQ19 = new widget.TextBox();
        jLabel266 = new widget.Label();
        NilaiSDQ20 = new widget.TextBox();
        jLabel267 = new widget.Label();
        NilaiSDQ21 = new widget.TextBox();
        jLabel268 = new widget.Label();
        NilaiSDQ22 = new widget.TextBox();
        jLabel269 = new widget.Label();
        NilaiSDQ23 = new widget.TextBox();
        jLabel270 = new widget.Label();
        NilaiSDQ24 = new widget.TextBox();
        jLabel271 = new widget.Label();
        NilaiSDQ25 = new widget.TextBox();
        jLabel272 = new widget.Label();
        KesimpulanKesulitan = new widget.TextBox();
        jLabel273 = new widget.Label();
        jLabel274 = new widget.Label();
        jLabel275 = new widget.Label();
        TotalNilai = new widget.TextBox();
        jLabel276 = new widget.Label();
        NilaiKesimpulanSkorE = new widget.TextBox();
        jLabel278 = new widget.Label();
        NilaiKesimpulanSkorC = new widget.TextBox();
        jLabel280 = new widget.Label();
        NilaiKesimpulanSkorH = new widget.TextBox();
        jLabel282 = new widget.Label();
        NilaiKesimpulanSkorP = new widget.TextBox();
        jLabel284 = new widget.Label();
        NilaiKesimpulanSkorPr = new widget.TextBox();
        jLabel286 = new widget.Label();
        NilaiKesimpulanKesulitan = new widget.TextBox();
        jLabel288 = new widget.Label();
        KesimpulanSkorE = new widget.TextBox();
        jLabel59 = new widget.Label();
        jLabel291 = new widget.Label();
        KesimpulanSkorC = new widget.TextBox();
        jLabel293 = new widget.Label();
        KesimpulanSkorH = new widget.TextBox();
        jLabel295 = new widget.Label();
        KesimpulanSkorP = new widget.TextBox();
        jLabel297 = new widget.Label();
        KesimpulanSkorPr = new widget.TextBox();
        jLabel299 = new widget.Label();
        jLabel300 = new widget.Label();
        jLabel301 = new widget.Label();
        jLabel302 = new widget.Label();
        jLabel303 = new widget.Label();
        jLabel304 = new widget.Label();
        jLabel305 = new widget.Label();
        jLabel306 = new widget.Label();
        jLabel307 = new widget.Label();
        jLabel308 = new widget.Label();
        jLabel309 = new widget.Label();
        jLabel310 = new widget.Label();
        jLabel311 = new widget.Label();
        jLabel312 = new widget.Label();
        jLabel313 = new widget.Label();
        jLabel314 = new widget.Label();
        jLabel315 = new widget.Label();
        jLabel316 = new widget.Label();
        jLabel317 = new widget.Label();
        jLabel318 = new widget.Label();
        jLabel227 = new widget.Label();
        jLabel224 = new widget.Label();
        jLabel221 = new widget.Label();
        jLabel319 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        Keterangan = new widget.TextBox();
        jLabel277 = new widget.Label();
        jLabel279 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnFormulirSkriningInstrumenSDQ.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulirSkriningInstrumenSDQ.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirSkriningInstrumenSDQ.setForeground(new java.awt.Color(50, 50, 50));
        MnFormulirSkriningInstrumenSDQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirSkriningInstrumenSDQ.setText("Formulir Skrining Instrumen SDQ");
        MnFormulirSkriningInstrumenSDQ.setName("MnFormulirSkriningInstrumenSDQ"); // NOI18N
        MnFormulirSkriningInstrumenSDQ.setPreferredSize(new java.awt.Dimension(250, 26));
        MnFormulirSkriningInstrumenSDQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirSkriningInstrumenSDQActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnFormulirSkriningInstrumenSDQ);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining SDQ Usia 11 - 18 Tahun ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-11-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-11-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setMinimumSize(new java.awt.Dimension(85, 60));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 476));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
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

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1103));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-11-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(84, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 80, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(178, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(243, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(308, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(373, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        FormInput.add(NIP);
        NIP.setBounds(474, 40, 94, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(570, 40, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(761, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        SDQ1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ1.setName("SDQ1"); // NOI18N
        SDQ1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ1ItemStateChanged(evt);
            }
        });
        SDQ1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ1KeyPressed(evt);
            }
        });
        FormInput.add(SDQ1);
        SDQ1.setBounds(590, 90, 115, 23);

        jLabel218.setText("Nilai :");
        jLabel218.setName("jLabel218"); // NOI18N
        FormInput.add(jLabel218);
        jLabel218.setBounds(695, 90, 50, 23);

        NilaiSDQ1.setEditable(false);
        NilaiSDQ1.setFocusTraversalPolicyProvider(true);
        NilaiSDQ1.setName("NilaiSDQ1"); // NOI18N
        FormInput.add(NilaiSDQ1);
        NilaiSDQ1.setBounds(749, 90, 40, 23);

        jLabel220.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel220.setText("2.");
        jLabel220.setName("jLabel220"); // NOI18N
        FormInput.add(jLabel220);
        jLabel220.setBounds(44, 120, 25, 23);

        jLabel222.setText("Nilai :");
        jLabel222.setName("jLabel222"); // NOI18N
        FormInput.add(jLabel222);
        jLabel222.setBounds(695, 120, 50, 23);

        jLabel223.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel223.setText("3.");
        jLabel223.setName("jLabel223"); // NOI18N
        FormInput.add(jLabel223);
        jLabel223.setBounds(44, 150, 25, 23);

        jLabel225.setText("Nilai :");
        jLabel225.setName("jLabel225"); // NOI18N
        FormInput.add(jLabel225);
        jLabel225.setBounds(695, 150, 50, 23);

        jLabel226.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel226.setText("4.");
        jLabel226.setName("jLabel226"); // NOI18N
        FormInput.add(jLabel226);
        jLabel226.setBounds(44, 180, 25, 23);

        jLabel228.setText("Nilai :");
        jLabel228.setName("jLabel228"); // NOI18N
        FormInput.add(jLabel228);
        jLabel228.setBounds(695, 180, 50, 23);

        jLabel229.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel229.setText("10.");
        jLabel229.setName("jLabel229"); // NOI18N
        FormInput.add(jLabel229);
        jLabel229.setBounds(44, 360, 25, 23);

        jLabel231.setText("Nilai :");
        jLabel231.setName("jLabel231"); // NOI18N
        FormInput.add(jLabel231);
        jLabel231.setBounds(695, 210, 50, 23);

        jLabel234.setText("Nilai :");
        jLabel234.setName("jLabel234"); // NOI18N
        FormInput.add(jLabel234);
        jLabel234.setBounds(695, 240, 50, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 810, 1);

        jLabel232.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel232.setText("5.");
        jLabel232.setName("jLabel232"); // NOI18N
        FormInput.add(jLabel232);
        jLabel232.setBounds(44, 210, 25, 23);

        jLabel236.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel236.setText("1.");
        jLabel236.setName("jLabel236"); // NOI18N
        FormInput.add(jLabel236);
        jLabel236.setBounds(44, 90, 25, 23);

        jLabel239.setText("Nilai :");
        jLabel239.setName("jLabel239"); // NOI18N
        FormInput.add(jLabel239);
        jLabel239.setBounds(695, 270, 50, 23);

        jLabel240.setText("Nilai :");
        jLabel240.setName("jLabel240"); // NOI18N
        FormInput.add(jLabel240);
        jLabel240.setBounds(695, 300, 50, 23);

        jLabel241.setText("Nilai :");
        jLabel241.setName("jLabel241"); // NOI18N
        FormInput.add(jLabel241);
        jLabel241.setBounds(695, 330, 50, 23);

        jLabel242.setText("Nilai :");
        jLabel242.setName("jLabel242"); // NOI18N
        FormInput.add(jLabel242);
        jLabel242.setBounds(695, 360, 50, 23);

        jLabel230.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel230.setText("6.");
        jLabel230.setName("jLabel230"); // NOI18N
        FormInput.add(jLabel230);
        jLabel230.setBounds(44, 240, 25, 23);

        jLabel233.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel233.setText("7.");
        jLabel233.setName("jLabel233"); // NOI18N
        FormInput.add(jLabel233);
        jLabel233.setBounds(44, 270, 25, 23);

        jLabel243.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel243.setText("8.");
        jLabel243.setName("jLabel243"); // NOI18N
        FormInput.add(jLabel243);
        jLabel243.setBounds(44, 300, 25, 23);

        jLabel244.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel244.setText("9.");
        jLabel244.setName("jLabel244"); // NOI18N
        FormInput.add(jLabel244);
        jLabel244.setBounds(44, 330, 25, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("I. DETEKSI DINI MASALAH EMOSI DAN PERILAKU MENGGUNAKAN KUISIONER KEKUATAN DAN KELEMAHAN (SDQ)");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(11, 70, 580, 23);

        jLabel235.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel235.setText("11.");
        jLabel235.setName("jLabel235"); // NOI18N
        FormInput.add(jLabel235);
        jLabel235.setBounds(44, 390, 25, 23);

        jLabel237.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel237.setText("12.");
        jLabel237.setName("jLabel237"); // NOI18N
        FormInput.add(jLabel237);
        jLabel237.setBounds(44, 420, 25, 23);

        jLabel238.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel238.setText("13.");
        jLabel238.setName("jLabel238"); // NOI18N
        FormInput.add(jLabel238);
        jLabel238.setBounds(44, 450, 25, 23);

        jLabel245.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel245.setText("14.");
        jLabel245.setName("jLabel245"); // NOI18N
        FormInput.add(jLabel245);
        jLabel245.setBounds(44, 480, 25, 23);

        jLabel246.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel246.setText("15.");
        jLabel246.setName("jLabel246"); // NOI18N
        FormInput.add(jLabel246);
        jLabel246.setBounds(44, 510, 25, 23);

        jLabel247.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel247.setText("16.");
        jLabel247.setName("jLabel247"); // NOI18N
        FormInput.add(jLabel247);
        jLabel247.setBounds(44, 540, 25, 23);

        jLabel248.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel248.setText("17.");
        jLabel248.setName("jLabel248"); // NOI18N
        FormInput.add(jLabel248);
        jLabel248.setBounds(44, 570, 25, 23);

        jLabel249.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel249.setText("18.");
        jLabel249.setName("jLabel249"); // NOI18N
        FormInput.add(jLabel249);
        jLabel249.setBounds(44, 600, 25, 23);

        jLabel250.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel250.setText("19.");
        jLabel250.setName("jLabel250"); // NOI18N
        FormInput.add(jLabel250);
        jLabel250.setBounds(44, 630, 25, 23);

        jLabel251.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel251.setText("20.");
        jLabel251.setName("jLabel251"); // NOI18N
        FormInput.add(jLabel251);
        jLabel251.setBounds(44, 660, 25, 23);

        jLabel252.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel252.setText("21.");
        jLabel252.setName("jLabel252"); // NOI18N
        FormInput.add(jLabel252);
        jLabel252.setBounds(44, 690, 25, 23);

        jLabel253.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel253.setText("22.");
        jLabel253.setName("jLabel253"); // NOI18N
        FormInput.add(jLabel253);
        jLabel253.setBounds(44, 720, 25, 23);

        jLabel254.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel254.setText("23.");
        jLabel254.setName("jLabel254"); // NOI18N
        FormInput.add(jLabel254);
        jLabel254.setBounds(44, 750, 25, 23);

        jLabel255.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel255.setText("24.");
        jLabel255.setName("jLabel255"); // NOI18N
        FormInput.add(jLabel255);
        jLabel255.setBounds(44, 780, 25, 23);

        jLabel256.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel256.setText("Memiliki perhatian yang baik terhadap apapun, mampu menyelesaikan tugas atau pekerjaan ");
        jLabel256.setName("jLabel256"); // NOI18N
        FormInput.add(jLabel256);
        jLabel256.setBounds(62, 805, 480, 23);

        SDQ2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ2.setName("SDQ2"); // NOI18N
        SDQ2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ2ItemStateChanged(evt);
            }
        });
        SDQ2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ2KeyPressed(evt);
            }
        });
        FormInput.add(SDQ2);
        SDQ2.setBounds(590, 120, 115, 23);

        SDQ3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ3.setName("SDQ3"); // NOI18N
        SDQ3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ3ItemStateChanged(evt);
            }
        });
        SDQ3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ3KeyPressed(evt);
            }
        });
        FormInput.add(SDQ3);
        SDQ3.setBounds(590, 150, 115, 23);

        SDQ4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ4.setName("SDQ4"); // NOI18N
        SDQ4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ4ItemStateChanged(evt);
            }
        });
        SDQ4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ4KeyPressed(evt);
            }
        });
        FormInput.add(SDQ4);
        SDQ4.setBounds(590, 180, 115, 23);

        SDQ5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ5.setName("SDQ5"); // NOI18N
        SDQ5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ5ItemStateChanged(evt);
            }
        });
        SDQ5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ5KeyPressed(evt);
            }
        });
        FormInput.add(SDQ5);
        SDQ5.setBounds(590, 210, 115, 23);

        SDQ6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ6.setName("SDQ6"); // NOI18N
        SDQ6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ6ItemStateChanged(evt);
            }
        });
        SDQ6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ6KeyPressed(evt);
            }
        });
        FormInput.add(SDQ6);
        SDQ6.setBounds(590, 240, 115, 23);

        SDQ7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ7.setName("SDQ7"); // NOI18N
        SDQ7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ7ItemStateChanged(evt);
            }
        });
        SDQ7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ7KeyPressed(evt);
            }
        });
        FormInput.add(SDQ7);
        SDQ7.setBounds(590, 270, 115, 23);

        SDQ8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ8.setName("SDQ8"); // NOI18N
        SDQ8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ8ItemStateChanged(evt);
            }
        });
        SDQ8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ8KeyPressed(evt);
            }
        });
        FormInput.add(SDQ8);
        SDQ8.setBounds(590, 300, 115, 23);

        SDQ9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ9.setName("SDQ9"); // NOI18N
        SDQ9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ9ItemStateChanged(evt);
            }
        });
        SDQ9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ9KeyPressed(evt);
            }
        });
        FormInput.add(SDQ9);
        SDQ9.setBounds(590, 330, 115, 23);

        SDQ10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ10.setName("SDQ10"); // NOI18N
        SDQ10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ10ItemStateChanged(evt);
            }
        });
        SDQ10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ10KeyPressed(evt);
            }
        });
        FormInput.add(SDQ10);
        SDQ10.setBounds(590, 360, 115, 23);

        SDQ11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ11.setName("SDQ11"); // NOI18N
        SDQ11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ11ItemStateChanged(evt);
            }
        });
        SDQ11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ11KeyPressed(evt);
            }
        });
        FormInput.add(SDQ11);
        SDQ11.setBounds(590, 390, 115, 23);

        SDQ12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ12.setName("SDQ12"); // NOI18N
        SDQ12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ12ItemStateChanged(evt);
            }
        });
        SDQ12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ12KeyPressed(evt);
            }
        });
        FormInput.add(SDQ12);
        SDQ12.setBounds(590, 420, 115, 23);

        SDQ13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ13.setName("SDQ13"); // NOI18N
        SDQ13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ13ItemStateChanged(evt);
            }
        });
        SDQ13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ13KeyPressed(evt);
            }
        });
        FormInput.add(SDQ13);
        SDQ13.setBounds(590, 450, 115, 23);

        SDQ14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ14.setName("SDQ14"); // NOI18N
        SDQ14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ14ItemStateChanged(evt);
            }
        });
        SDQ14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ14KeyPressed(evt);
            }
        });
        FormInput.add(SDQ14);
        SDQ14.setBounds(590, 480, 115, 23);

        SDQ15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ15.setName("SDQ15"); // NOI18N
        SDQ15.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ15ItemStateChanged(evt);
            }
        });
        SDQ15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ15KeyPressed(evt);
            }
        });
        FormInput.add(SDQ15);
        SDQ15.setBounds(590, 510, 115, 23);

        SDQ16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ16.setName("SDQ16"); // NOI18N
        SDQ16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ16ItemStateChanged(evt);
            }
        });
        SDQ16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ16KeyPressed(evt);
            }
        });
        FormInput.add(SDQ16);
        SDQ16.setBounds(590, 540, 115, 23);

        SDQ17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ17.setName("SDQ17"); // NOI18N
        SDQ17.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ17ItemStateChanged(evt);
            }
        });
        SDQ17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ17KeyPressed(evt);
            }
        });
        FormInput.add(SDQ17);
        SDQ17.setBounds(590, 570, 115, 23);

        SDQ18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ18.setName("SDQ18"); // NOI18N
        SDQ18.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ18ItemStateChanged(evt);
            }
        });
        SDQ18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ18KeyPressed(evt);
            }
        });
        FormInput.add(SDQ18);
        SDQ18.setBounds(590, 600, 115, 23);

        SDQ19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ19.setName("SDQ19"); // NOI18N
        SDQ19.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ19ItemStateChanged(evt);
            }
        });
        SDQ19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ19KeyPressed(evt);
            }
        });
        FormInput.add(SDQ19);
        SDQ19.setBounds(590, 630, 115, 23);

        SDQ20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ20.setName("SDQ20"); // NOI18N
        SDQ20.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ20ItemStateChanged(evt);
            }
        });
        SDQ20.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ20KeyPressed(evt);
            }
        });
        FormInput.add(SDQ20);
        SDQ20.setBounds(590, 660, 115, 23);

        SDQ21.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ21.setName("SDQ21"); // NOI18N
        SDQ21.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ21ItemStateChanged(evt);
            }
        });
        SDQ21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ21KeyPressed(evt);
            }
        });
        FormInput.add(SDQ21);
        SDQ21.setBounds(590, 690, 115, 23);

        SDQ22.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ22.setName("SDQ22"); // NOI18N
        SDQ22.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ22ItemStateChanged(evt);
            }
        });
        SDQ22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ22KeyPressed(evt);
            }
        });
        FormInput.add(SDQ22);
        SDQ22.setBounds(590, 720, 115, 23);

        SDQ23.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ23.setName("SDQ23"); // NOI18N
        SDQ23.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ23ItemStateChanged(evt);
            }
        });
        SDQ23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ23KeyPressed(evt);
            }
        });
        FormInput.add(SDQ23);
        SDQ23.setBounds(590, 750, 115, 23);

        SDQ24.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ24.setName("SDQ24"); // NOI18N
        SDQ24.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ24ItemStateChanged(evt);
            }
        });
        SDQ24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ24KeyPressed(evt);
            }
        });
        FormInput.add(SDQ24);
        SDQ24.setBounds(590, 780, 115, 23);

        SDQ25.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Benar", "Agak Benar", "Selalu Benar" }));
        SDQ25.setName("SDQ25"); // NOI18N
        SDQ25.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SDQ25ItemStateChanged(evt);
            }
        });
        SDQ25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SDQ25KeyPressed(evt);
            }
        });
        FormInput.add(SDQ25);
        SDQ25.setBounds(590, 810, 115, 23);

        NilaiSDQ2.setEditable(false);
        NilaiSDQ2.setFocusTraversalPolicyProvider(true);
        NilaiSDQ2.setName("NilaiSDQ2"); // NOI18N
        FormInput.add(NilaiSDQ2);
        NilaiSDQ2.setBounds(749, 120, 40, 23);

        NilaiSDQ3.setEditable(false);
        NilaiSDQ3.setFocusTraversalPolicyProvider(true);
        NilaiSDQ3.setName("NilaiSDQ3"); // NOI18N
        FormInput.add(NilaiSDQ3);
        NilaiSDQ3.setBounds(749, 150, 40, 23);

        NilaiSDQ4.setEditable(false);
        NilaiSDQ4.setFocusTraversalPolicyProvider(true);
        NilaiSDQ4.setName("NilaiSDQ4"); // NOI18N
        FormInput.add(NilaiSDQ4);
        NilaiSDQ4.setBounds(749, 180, 40, 23);

        NilaiSDQ5.setEditable(false);
        NilaiSDQ5.setFocusTraversalPolicyProvider(true);
        NilaiSDQ5.setName("NilaiSDQ5"); // NOI18N
        FormInput.add(NilaiSDQ5);
        NilaiSDQ5.setBounds(749, 210, 40, 23);

        NilaiSDQ6.setEditable(false);
        NilaiSDQ6.setFocusTraversalPolicyProvider(true);
        NilaiSDQ6.setName("NilaiSDQ6"); // NOI18N
        FormInput.add(NilaiSDQ6);
        NilaiSDQ6.setBounds(749, 240, 40, 23);

        NilaiSDQ7.setEditable(false);
        NilaiSDQ7.setFocusTraversalPolicyProvider(true);
        NilaiSDQ7.setName("NilaiSDQ7"); // NOI18N
        FormInput.add(NilaiSDQ7);
        NilaiSDQ7.setBounds(749, 270, 40, 23);

        NilaiSDQ8.setEditable(false);
        NilaiSDQ8.setFocusTraversalPolicyProvider(true);
        NilaiSDQ8.setName("NilaiSDQ8"); // NOI18N
        FormInput.add(NilaiSDQ8);
        NilaiSDQ8.setBounds(749, 300, 40, 23);

        NilaiSDQ9.setEditable(false);
        NilaiSDQ9.setFocusTraversalPolicyProvider(true);
        NilaiSDQ9.setName("NilaiSDQ9"); // NOI18N
        FormInput.add(NilaiSDQ9);
        NilaiSDQ9.setBounds(749, 330, 40, 23);

        NilaiSDQ10.setEditable(false);
        NilaiSDQ10.setFocusTraversalPolicyProvider(true);
        NilaiSDQ10.setName("NilaiSDQ10"); // NOI18N
        FormInput.add(NilaiSDQ10);
        NilaiSDQ10.setBounds(749, 360, 40, 23);

        NilaiSDQ11.setEditable(false);
        NilaiSDQ11.setFocusTraversalPolicyProvider(true);
        NilaiSDQ11.setName("NilaiSDQ11"); // NOI18N
        FormInput.add(NilaiSDQ11);
        NilaiSDQ11.setBounds(749, 390, 40, 23);

        jLabel257.setText("Nilai :");
        jLabel257.setName("jLabel257"); // NOI18N
        FormInput.add(jLabel257);
        jLabel257.setBounds(695, 390, 50, 23);

        NilaiSDQ12.setEditable(false);
        NilaiSDQ12.setFocusTraversalPolicyProvider(true);
        NilaiSDQ12.setName("NilaiSDQ12"); // NOI18N
        FormInput.add(NilaiSDQ12);
        NilaiSDQ12.setBounds(749, 420, 40, 23);

        jLabel258.setText("Nilai :");
        jLabel258.setName("jLabel258"); // NOI18N
        FormInput.add(jLabel258);
        jLabel258.setBounds(695, 420, 50, 23);

        jLabel259.setText("Nilai :");
        jLabel259.setName("jLabel259"); // NOI18N
        FormInput.add(jLabel259);
        jLabel259.setBounds(695, 450, 50, 23);

        NilaiSDQ13.setEditable(false);
        NilaiSDQ13.setFocusTraversalPolicyProvider(true);
        NilaiSDQ13.setName("NilaiSDQ13"); // NOI18N
        FormInput.add(NilaiSDQ13);
        NilaiSDQ13.setBounds(749, 450, 40, 23);

        jLabel260.setText("Nilai :");
        jLabel260.setName("jLabel260"); // NOI18N
        FormInput.add(jLabel260);
        jLabel260.setBounds(695, 480, 50, 23);

        NilaiSDQ14.setEditable(false);
        NilaiSDQ14.setFocusTraversalPolicyProvider(true);
        NilaiSDQ14.setName("NilaiSDQ14"); // NOI18N
        FormInput.add(NilaiSDQ14);
        NilaiSDQ14.setBounds(749, 480, 40, 23);

        jLabel261.setText("Nilai :");
        jLabel261.setName("jLabel261"); // NOI18N
        FormInput.add(jLabel261);
        jLabel261.setBounds(695, 510, 50, 23);

        NilaiSDQ15.setEditable(false);
        NilaiSDQ15.setFocusTraversalPolicyProvider(true);
        NilaiSDQ15.setName("NilaiSDQ15"); // NOI18N
        FormInput.add(NilaiSDQ15);
        NilaiSDQ15.setBounds(749, 510, 40, 23);

        jLabel262.setText("Nilai :");
        jLabel262.setName("jLabel262"); // NOI18N
        FormInput.add(jLabel262);
        jLabel262.setBounds(695, 540, 50, 23);

        NilaiSDQ16.setEditable(false);
        NilaiSDQ16.setFocusTraversalPolicyProvider(true);
        NilaiSDQ16.setName("NilaiSDQ16"); // NOI18N
        FormInput.add(NilaiSDQ16);
        NilaiSDQ16.setBounds(749, 540, 40, 23);

        jLabel263.setText("Nilai :");
        jLabel263.setName("jLabel263"); // NOI18N
        FormInput.add(jLabel263);
        jLabel263.setBounds(695, 570, 50, 23);

        NilaiSDQ17.setEditable(false);
        NilaiSDQ17.setFocusTraversalPolicyProvider(true);
        NilaiSDQ17.setName("NilaiSDQ17"); // NOI18N
        FormInput.add(NilaiSDQ17);
        NilaiSDQ17.setBounds(749, 570, 40, 23);

        jLabel264.setText("Nilai :");
        jLabel264.setName("jLabel264"); // NOI18N
        FormInput.add(jLabel264);
        jLabel264.setBounds(695, 600, 50, 23);

        NilaiSDQ18.setEditable(false);
        NilaiSDQ18.setFocusTraversalPolicyProvider(true);
        NilaiSDQ18.setName("NilaiSDQ18"); // NOI18N
        FormInput.add(NilaiSDQ18);
        NilaiSDQ18.setBounds(749, 600, 40, 23);

        jLabel265.setText("Nilai :");
        jLabel265.setName("jLabel265"); // NOI18N
        FormInput.add(jLabel265);
        jLabel265.setBounds(695, 630, 50, 23);

        NilaiSDQ19.setEditable(false);
        NilaiSDQ19.setFocusTraversalPolicyProvider(true);
        NilaiSDQ19.setName("NilaiSDQ19"); // NOI18N
        FormInput.add(NilaiSDQ19);
        NilaiSDQ19.setBounds(749, 630, 40, 23);

        jLabel266.setText("Nilai :");
        jLabel266.setName("jLabel266"); // NOI18N
        FormInput.add(jLabel266);
        jLabel266.setBounds(695, 660, 50, 23);

        NilaiSDQ20.setEditable(false);
        NilaiSDQ20.setFocusTraversalPolicyProvider(true);
        NilaiSDQ20.setName("NilaiSDQ20"); // NOI18N
        FormInput.add(NilaiSDQ20);
        NilaiSDQ20.setBounds(749, 660, 40, 23);

        jLabel267.setText("Nilai :");
        jLabel267.setName("jLabel267"); // NOI18N
        FormInput.add(jLabel267);
        jLabel267.setBounds(695, 690, 50, 23);

        NilaiSDQ21.setEditable(false);
        NilaiSDQ21.setFocusTraversalPolicyProvider(true);
        NilaiSDQ21.setName("NilaiSDQ21"); // NOI18N
        FormInput.add(NilaiSDQ21);
        NilaiSDQ21.setBounds(749, 690, 40, 23);

        jLabel268.setText("Nilai :");
        jLabel268.setName("jLabel268"); // NOI18N
        FormInput.add(jLabel268);
        jLabel268.setBounds(695, 720, 50, 23);

        NilaiSDQ22.setEditable(false);
        NilaiSDQ22.setFocusTraversalPolicyProvider(true);
        NilaiSDQ22.setName("NilaiSDQ22"); // NOI18N
        FormInput.add(NilaiSDQ22);
        NilaiSDQ22.setBounds(749, 720, 40, 23);

        jLabel269.setText("Nilai :");
        jLabel269.setName("jLabel269"); // NOI18N
        FormInput.add(jLabel269);
        jLabel269.setBounds(695, 750, 50, 23);

        NilaiSDQ23.setEditable(false);
        NilaiSDQ23.setFocusTraversalPolicyProvider(true);
        NilaiSDQ23.setName("NilaiSDQ23"); // NOI18N
        FormInput.add(NilaiSDQ23);
        NilaiSDQ23.setBounds(749, 750, 40, 23);

        jLabel270.setText("Nilai :");
        jLabel270.setName("jLabel270"); // NOI18N
        FormInput.add(jLabel270);
        jLabel270.setBounds(695, 780, 50, 23);

        NilaiSDQ24.setEditable(false);
        NilaiSDQ24.setFocusTraversalPolicyProvider(true);
        NilaiSDQ24.setName("NilaiSDQ24"); // NOI18N
        FormInput.add(NilaiSDQ24);
        NilaiSDQ24.setBounds(749, 780, 40, 23);

        jLabel271.setText("Nilai :");
        jLabel271.setName("jLabel271"); // NOI18N
        FormInput.add(jLabel271);
        jLabel271.setBounds(695, 810, 50, 23);

        NilaiSDQ25.setEditable(false);
        NilaiSDQ25.setFocusTraversalPolicyProvider(true);
        NilaiSDQ25.setName("NilaiSDQ25"); // NOI18N
        FormInput.add(NilaiSDQ25);
        NilaiSDQ25.setBounds(749, 810, 40, 23);

        jLabel272.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel272.setText("F. Pengkajian Skor Kesulitan ");
        jLabel272.setName("jLabel272"); // NOI18N
        FormInput.add(jLabel272);
        jLabel272.setBounds(44, 1040, 160, 23);

        KesimpulanKesulitan.setEditable(false);
        KesimpulanKesulitan.setFocusTraversalPolicyProvider(true);
        KesimpulanKesulitan.setName("KesimpulanKesulitan"); // NOI18N
        FormInput.add(KesimpulanKesulitan);
        KesimpulanKesulitan.setBounds(218, 1040, 240, 23);

        jLabel273.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel273.setText("rumah sampai selesai.");
        jLabel273.setName("jLabel273"); // NOI18N
        FormInput.add(jLabel273);
        jLabel273.setBounds(62, 817, 400, 23);

        jLabel274.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel274.setText("25.");
        jLabel274.setName("jLabel274"); // NOI18N
        FormInput.add(jLabel274);
        jLabel274.setBounds(44, 810, 20, 23);

        jLabel275.setText("Total Nilai :");
        jLabel275.setName("jLabel275"); // NOI18N
        FormInput.add(jLabel275);
        jLabel275.setBounds(615, 840, 130, 23);

        TotalNilai.setEditable(false);
        TotalNilai.setFocusTraversalPolicyProvider(true);
        TotalNilai.setName("TotalNilai"); // NOI18N
        FormInput.add(TotalNilai);
        TotalNilai.setBounds(749, 840, 40, 23);

        jLabel276.setText("Skor Gejala Emosional (E) :");
        jLabel276.setName("jLabel276"); // NOI18N
        FormInput.add(jLabel276);
        jLabel276.setBounds(565, 890, 180, 23);

        NilaiKesimpulanSkorE.setEditable(false);
        NilaiKesimpulanSkorE.setFocusTraversalPolicyProvider(true);
        NilaiKesimpulanSkorE.setName("NilaiKesimpulanSkorE"); // NOI18N
        FormInput.add(NilaiKesimpulanSkorE);
        NilaiKesimpulanSkorE.setBounds(749, 890, 40, 23);

        jLabel278.setText("Skor Masalah Perilaku (C) :");
        jLabel278.setName("jLabel278"); // NOI18N
        FormInput.add(jLabel278);
        jLabel278.setBounds(565, 920, 180, 23);

        NilaiKesimpulanSkorC.setEditable(false);
        NilaiKesimpulanSkorC.setFocusTraversalPolicyProvider(true);
        NilaiKesimpulanSkorC.setName("NilaiKesimpulanSkorC"); // NOI18N
        FormInput.add(NilaiKesimpulanSkorC);
        NilaiKesimpulanSkorC.setBounds(749, 920, 40, 23);

        jLabel280.setText("Skor Hiperaktivitas (H) :");
        jLabel280.setName("jLabel280"); // NOI18N
        FormInput.add(jLabel280);
        jLabel280.setBounds(565, 950, 180, 23);

        NilaiKesimpulanSkorH.setEditable(false);
        NilaiKesimpulanSkorH.setFocusTraversalPolicyProvider(true);
        NilaiKesimpulanSkorH.setName("NilaiKesimpulanSkorH"); // NOI18N
        FormInput.add(NilaiKesimpulanSkorH);
        NilaiKesimpulanSkorH.setBounds(749, 950, 40, 23);

        jLabel282.setText("Skor Masalah Teman Sebaya (P) :");
        jLabel282.setName("jLabel282"); // NOI18N
        FormInput.add(jLabel282);
        jLabel282.setBounds(565, 980, 180, 23);

        NilaiKesimpulanSkorP.setEditable(false);
        NilaiKesimpulanSkorP.setFocusTraversalPolicyProvider(true);
        NilaiKesimpulanSkorP.setName("NilaiKesimpulanSkorP"); // NOI18N
        FormInput.add(NilaiKesimpulanSkorP);
        NilaiKesimpulanSkorP.setBounds(749, 980, 40, 23);

        jLabel284.setText("Skor Kekuatan Proporsional (Pr) :");
        jLabel284.setName("jLabel284"); // NOI18N
        FormInput.add(jLabel284);
        jLabel284.setBounds(565, 1010, 180, 23);

        NilaiKesimpulanSkorPr.setEditable(false);
        NilaiKesimpulanSkorPr.setFocusTraversalPolicyProvider(true);
        NilaiKesimpulanSkorPr.setName("NilaiKesimpulanSkorPr"); // NOI18N
        FormInput.add(NilaiKesimpulanSkorPr);
        NilaiKesimpulanSkorPr.setBounds(749, 1010, 40, 23);

        jLabel286.setText("Skor Total Kesulitan :");
        jLabel286.setName("jLabel286"); // NOI18N
        FormInput.add(jLabel286);
        jLabel286.setBounds(565, 1040, 180, 23);

        NilaiKesimpulanKesulitan.setEditable(false);
        NilaiKesimpulanKesulitan.setFocusTraversalPolicyProvider(true);
        NilaiKesimpulanKesulitan.setName("NilaiKesimpulanKesulitan"); // NOI18N
        FormInput.add(NilaiKesimpulanKesulitan);
        NilaiKesimpulanKesulitan.setBounds(749, 1040, 40, 23);

        jLabel288.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel288.setText("A. Pengkajian Gejala Emosional (E)");
        jLabel288.setName("jLabel288"); // NOI18N
        FormInput.add(jLabel288);
        jLabel288.setBounds(44, 890, 170, 23);

        KesimpulanSkorE.setEditable(false);
        KesimpulanSkorE.setFocusTraversalPolicyProvider(true);
        KesimpulanSkorE.setName("KesimpulanSkorE"); // NOI18N
        FormInput.add(KesimpulanSkorE);
        KesimpulanSkorE.setBounds(218, 890, 240, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("II. INTEPRETASI DAN KESIMPULAN HASIL PEMERIKSAAN (SDQ) :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(11, 873, 550, 23);

        jLabel291.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel291.setText("B. Pengkajian Masalah Perilaku (C)");
        jLabel291.setName("jLabel291"); // NOI18N
        FormInput.add(jLabel291);
        jLabel291.setBounds(44, 920, 170, 23);

        KesimpulanSkorC.setEditable(false);
        KesimpulanSkorC.setFocusTraversalPolicyProvider(true);
        KesimpulanSkorC.setName("KesimpulanSkorC"); // NOI18N
        FormInput.add(KesimpulanSkorC);
        KesimpulanSkorC.setBounds(218, 920, 240, 23);

        jLabel293.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel293.setText("C. Pengkajian Hiperaktivitas (H)");
        jLabel293.setName("jLabel293"); // NOI18N
        FormInput.add(jLabel293);
        jLabel293.setBounds(44, 950, 170, 23);

        KesimpulanSkorH.setEditable(false);
        KesimpulanSkorH.setFocusTraversalPolicyProvider(true);
        KesimpulanSkorH.setName("KesimpulanSkorH"); // NOI18N
        FormInput.add(KesimpulanSkorH);
        KesimpulanSkorH.setBounds(218, 950, 240, 23);

        jLabel295.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel295.setText("D. Masalah Teman Sebaya (P)");
        jLabel295.setName("jLabel295"); // NOI18N
        FormInput.add(jLabel295);
        jLabel295.setBounds(44, 980, 160, 23);

        KesimpulanSkorP.setEditable(false);
        KesimpulanSkorP.setFocusTraversalPolicyProvider(true);
        KesimpulanSkorP.setName("KesimpulanSkorP"); // NOI18N
        FormInput.add(KesimpulanSkorP);
        KesimpulanSkorP.setBounds(218, 980, 240, 23);

        jLabel297.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel297.setText("E. Pengkajian Skor Kekuatan (Pr)");
        jLabel297.setName("jLabel297"); // NOI18N
        FormInput.add(jLabel297);
        jLabel297.setBounds(44, 1010, 170, 23);

        KesimpulanSkorPr.setEditable(false);
        KesimpulanSkorPr.setFocusTraversalPolicyProvider(true);
        KesimpulanSkorPr.setName("KesimpulanSkorPr"); // NOI18N
        FormInput.add(KesimpulanSkorPr);
        KesimpulanSkorPr.setBounds(218, 1010, 240, 23);

        jLabel299.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel299.setText("Banyak yang Saya takuti, Saya mudah menjadi takut.");
        jLabel299.setName("jLabel299"); // NOI18N
        FormInput.add(jLabel299);
        jLabel299.setBounds(62, 780, 520, 23);

        jLabel300.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel300.setText("Saya lebih mudah berteman dengan orang dewasa daripada dengan orang seusia Saya.");
        jLabel300.setName("jLabel300"); // NOI18N
        FormInput.add(jLabel300);
        jLabel300.setBounds(62, 750, 520, 23);

        jLabel301.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel301.setText("Saya mengambil barang yang bukan milik Saya dari rumah, sekolah atau dari mana saja.");
        jLabel301.setName("jLabel301"); // NOI18N
        FormInput.add(jLabel301);
        jLabel301.setBounds(62, 720, 520, 23);

        jLabel302.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel302.setText("Saya berpikir terlebih dulu akibat yang akan terjadi, sebelum berbuat atau melakukan sesuatu.");
        jLabel302.setName("jLabel302"); // NOI18N
        FormInput.add(jLabel302);
        jLabel302.setBounds(62, 690, 520, 23);

        jLabel303.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel303.setText("Saya sering menawarkan diri untuk membantu orang lain (orang tua, guru, anak-anak).");
        jLabel303.setName("jLabel303"); // NOI18N
        FormInput.add(jLabel303);
        jLabel303.setBounds(62, 660, 520, 23);

        jLabel304.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel304.setText("Saya sering diganggu atau dipermainkan oleh anak-anak atau remaja lainnya.");
        jLabel304.setName("jLabel304"); // NOI18N
        FormInput.add(jLabel304);
        jLabel304.setBounds(62, 630, 520, 23);

        jLabel305.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel305.setText("Saya sering dituduh berbohong atau berbuat curang.");
        jLabel305.setName("jLabel305"); // NOI18N
        FormInput.add(jLabel305);
        jLabel305.setBounds(62, 600, 520, 23);

        jLabel306.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel306.setText("Saya bersikap baik terhadap anak-anak yang lebih muda dari Saya.");
        jLabel306.setName("jLabel306"); // NOI18N
        FormInput.add(jLabel306);
        jLabel306.setBounds(62, 570, 520, 23);

        jLabel307.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel307.setText("Saya merasa gugup dalam situasi baru, Saya mudah kehilangan rasa percaya diri.");
        jLabel307.setName("jLabel307"); // NOI18N
        FormInput.add(jLabel307);
        jLabel307.setBounds(62, 540, 520, 23);

        jLabel308.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel308.setText("Perhatian Saya mudah teralih, Saya sulit untuk memusatkan perhatian pada apapun.");
        jLabel308.setName("jLabel308"); // NOI18N
        FormInput.add(jLabel308);
        jLabel308.setBounds(62, 510, 520, 23);

        jLabel309.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel309.setText("Orang lain seusia Saya umumnya menyukai Saya.");
        jLabel309.setName("jLabel309"); // NOI18N
        FormInput.add(jLabel309);
        jLabel309.setBounds(62, 480, 520, 23);

        jLabel310.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel310.setText("Saya sering merasa tidak bahagia, sedih atau menangis.");
        jLabel310.setName("jLabel310"); // NOI18N
        FormInput.add(jLabel310);
        jLabel310.setBounds(62, 450, 520, 23);

        jLabel311.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel311.setText("Saya sering bertengkar dengan orang lain. Saya dapat memaksa orang lain melakukan yang Saya inginkan.");
        jLabel311.setName("jLabel311"); // NOI18N
        FormInput.add(jLabel311);
        jLabel311.setBounds(62, 420, 550, 23);

        jLabel312.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel312.setText("Saya mempunyai satu orang teman baik atau lebih.");
        jLabel312.setName("jLabel312"); // NOI18N
        FormInput.add(jLabel312);
        jLabel312.setBounds(62, 390, 520, 23);

        jLabel313.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel313.setText("Bila sedang gelisah atau cemas badan Saya sering bergerak–gerak tanpa Saya sadari.");
        jLabel313.setName("jLabel313"); // NOI18N
        FormInput.add(jLabel313);
        jLabel313.setBounds(62, 360, 520, 23);

        jLabel314.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel314.setText("Saya selalu siap menolong jika seseorang terluka, kecewa atau merasa sakit.");
        jLabel314.setName("jLabel314"); // NOI18N
        FormInput.add(jLabel314);
        jLabel314.setBounds(62, 330, 530, 23);

        jLabel315.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel315.setText("Saya banyak merasa cemas atau khawatir terhadap apapun.");
        jLabel315.setName("jLabel315"); // NOI18N
        FormInput.add(jLabel315);
        jLabel315.setBounds(62, 300, 440, 23);

        jLabel316.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel316.setText("Saya biasanya melakukan apa yang diperintahkan oleh orang lain.");
        jLabel316.setName("jLabel316"); // NOI18N
        FormInput.add(jLabel316);
        jLabel316.setBounds(62, 270, 490, 23);

        jLabel317.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel317.setText("Saya lebih suka sendiri daripada bersama dengan orang yang seusiaku.");
        jLabel317.setName("jLabel317"); // NOI18N
        FormInput.add(jLabel317);
        jLabel317.setBounds(62, 240, 480, 23);

        jLabel318.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel318.setText("Saya menjadi sangat marah dan sering tidak dapat mengendalikan kemarahan Saya.");
        jLabel318.setName("jLabel318"); // NOI18N
        FormInput.add(jLabel318);
        jLabel318.setBounds(62, 210, 510, 23);

        jLabel227.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel227.setText("Kalau Saya memiliki mainan, CD, atau makanan, Saya biasanya berbagi dengan orang lain.");
        jLabel227.setName("jLabel227"); // NOI18N
        FormInput.add(jLabel227);
        jLabel227.setBounds(62, 180, 530, 23);

        jLabel224.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel224.setText("Saya sering sakit kepala, sakit perut atau macam-macam sakit lainnya.");
        jLabel224.setName("jLabel224"); // NOI18N
        FormInput.add(jLabel224);
        jLabel224.setBounds(62, 150, 530, 23);

        jLabel221.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel221.setText("Saya gelisah. Saya tidak dapat diam untuk waktu lama.");
        jLabel221.setName("jLabel221"); // NOI18N
        FormInput.add(jLabel221);
        jLabel221.setBounds(62, 120, 390, 23);

        jLabel319.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel319.setText("Saya berusaha baik kepada orang lain. Saya peduli dengan perasaan mereka.");
        jLabel319.setName("jLabel319"); // NOI18N
        FormInput.add(jLabel319);
        jLabel319.setBounds(62, 90, 520, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 870, 810, 1);

        Keterangan.setFocusTraversalPolicyProvider(true);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(111, 1070, 678, 23);

        jLabel277.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel277.setText("Keterangan");
        jLabel277.setName("jLabel277"); // NOI18N
        FormInput.add(jLabel277);
        jLabel277.setBounds(44, 1070, 80, 23);

        jLabel279.setText(":");
        jLabel279.setName("jLabel279"); // NOI18N
        FormInput.add(jLabel279);
        jLabel279.setBounds(0, 1070, 107, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,SDQ10,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
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
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
        petugas.dispose();
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.R.M.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>JK</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ14</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ14</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ15</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ15</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ16</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ16</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ17</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ17</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ18</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ18</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ19</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ19</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ20</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ20</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ21</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ21</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ22</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ22</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ23</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ23</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ24</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ24</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SDQ25</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.SDQ25</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nilai Total SDQ</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Gejala Emosional (E)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.GE</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Masalah Perilaku (C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.MP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hiperaktivitas (H)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.H</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Teman Sebaya (P)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.TS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kekuatan(Pr)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.KPr</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesulitan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>N.K</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,45).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,46).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,47).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,48).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,49).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,50).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,51).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,52).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,53).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,54).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,55).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,56).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,57).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,58).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,59).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,60).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,61).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,62).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,63).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,64).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,65).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,66).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,67).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,68).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,69).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,70).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,71).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("PenilaianSkriningInstrumenSDQ.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SKRINING INSTRUMEN SDQ<br><br></font>"+        
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

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
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
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Detik,SDQ1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnFormulirSkriningInstrumenSDQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirSkriningInstrumenSDQActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),28).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),27).toString():finger)+"\n"+Tanggal.getSelectedItem());
            Valid.MyReportqry("rptFormulirSkriningInstrumenSDQ.jasper","report","::[ Formulir Skrining Instrumen SDQ ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,skrining_instrumen_sdq.tanggal,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "skrining_instrumen_sdq.pernyataansdq1,skrining_instrumen_sdq.nilai_sdq1,skrining_instrumen_sdq.pernyataansdq2,skrining_instrumen_sdq.nilai_sdq2,"+
                    "skrining_instrumen_sdq.pernyataansdq3,skrining_instrumen_sdq.nilai_sdq3,skrining_instrumen_sdq.pernyataansdq4,skrining_instrumen_sdq.nilai_sdq4,"+
                    "skrining_instrumen_sdq.pernyataansdq5,skrining_instrumen_sdq.nilai_sdq5,skrining_instrumen_sdq.pernyataansdq6,skrining_instrumen_sdq.nilai_sdq6,"+
                    "skrining_instrumen_sdq.pernyataansdq7,skrining_instrumen_sdq.nilai_sdq7,skrining_instrumen_sdq.pernyataansdq8,skrining_instrumen_sdq.nilai_sdq8,"+
                    "skrining_instrumen_sdq.pernyataansdq9,skrining_instrumen_sdq.nilai_sdq9,skrining_instrumen_sdq.pernyataansdq10,skrining_instrumen_sdq.nilai_sdq10,"+
                    "skrining_instrumen_sdq.pernyataansdq11,skrining_instrumen_sdq.nilai_sdq11,skrining_instrumen_sdq.pernyataansdq12,skrining_instrumen_sdq.nilai_sdq12,"+
                    "skrining_instrumen_sdq.pernyataansdq13,skrining_instrumen_sdq.nilai_sdq13,skrining_instrumen_sdq.pernyataansdq14,skrining_instrumen_sdq.nilai_sdq14,"+
                    "skrining_instrumen_sdq.pernyataansdq15,skrining_instrumen_sdq.nilai_sdq15,skrining_instrumen_sdq.pernyataansdq16,skrining_instrumen_sdq.nilai_sdq16,"+
                    "skrining_instrumen_sdq.pernyataansdq17,skrining_instrumen_sdq.nilai_sdq17,skrining_instrumen_sdq.pernyataansdq18,skrining_instrumen_sdq.nilai_sdq18,"+
                    "skrining_instrumen_sdq.pernyataansdq19,skrining_instrumen_sdq.nilai_sdq19,skrining_instrumen_sdq.pernyataansdq20,skrining_instrumen_sdq.nilai_sdq20,"+
                    "skrining_instrumen_sdq.pernyataansdq21,skrining_instrumen_sdq.nilai_sdq21,skrining_instrumen_sdq.pernyataansdq22,skrining_instrumen_sdq.nilai_sdq22,"+
                    "skrining_instrumen_sdq.pernyataansdq23,skrining_instrumen_sdq.nilai_sdq23,skrining_instrumen_sdq.pernyataansdq24,skrining_instrumen_sdq.nilai_sdq24,"+
                    "skrining_instrumen_sdq.pernyataansdq25,skrining_instrumen_sdq.nilai_sdq25,skrining_instrumen_sdq.nilai_total_sdq,skrining_instrumen_sdq.gejala_emosional,"+
                    "skrining_instrumen_sdq.nilai_gejala_emosional,skrining_instrumen_sdq.masalah_perilaku,skrining_instrumen_sdq.nilai_masalah_perilaku,skrining_instrumen_sdq.hiperaktivitas,"+
                    "skrining_instrumen_sdq.nilai_hiperaktivitas,skrining_instrumen_sdq.teman_sebaya,skrining_instrumen_sdq.nilai_teman_sebaya,skrining_instrumen_sdq.kekuatan,"+
                    "skrining_instrumen_sdq.nilai_kekuatan,skrining_instrumen_sdq.kesulitan,skrining_instrumen_sdq.nilai_kesulitan,skrining_instrumen_sdq.keterangan,"+
                    "skrining_instrumen_sdq.nip,petugas.nama from skrining_instrumen_sdq inner join reg_periksa on skrining_instrumen_sdq.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_sdq.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnFormulirSkriningInstrumenSDQActionPerformed

    private void SDQ1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ1ItemStateChanged
        switch (SDQ1.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ1.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ1.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ1.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorPr();
    }//GEN-LAST:event_SDQ1ItemStateChanged

    private void SDQ1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ1KeyPressed
        Valid.pindah(evt,btnPetugas,SDQ2);
    }//GEN-LAST:event_SDQ1KeyPressed

    private void SDQ2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ2ItemStateChanged
        switch (SDQ2.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ2.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ2.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ2.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorH();
    }//GEN-LAST:event_SDQ2ItemStateChanged

    private void SDQ2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ2KeyPressed
        Valid.pindah(evt,SDQ1,SDQ3);
    }//GEN-LAST:event_SDQ2KeyPressed

    private void SDQ3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ3ItemStateChanged
        switch (SDQ3.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ3.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ3.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ3.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorE();
    }//GEN-LAST:event_SDQ3ItemStateChanged

    private void SDQ3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ3KeyPressed
        Valid.pindah(evt,SDQ2,SDQ4);
    }//GEN-LAST:event_SDQ3KeyPressed

    private void SDQ4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ4ItemStateChanged
        switch (SDQ4.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ4.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ4.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ4.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorPr();
    }//GEN-LAST:event_SDQ4ItemStateChanged

    private void SDQ4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ4KeyPressed
        Valid.pindah(evt,SDQ3,SDQ5);
    }//GEN-LAST:event_SDQ4KeyPressed

    private void SDQ5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ5ItemStateChanged
        switch (SDQ5.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ5.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ5.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ5.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorC();
    }//GEN-LAST:event_SDQ5ItemStateChanged

    private void SDQ5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ5KeyPressed
        Valid.pindah(evt,SDQ4,SDQ6);
    }//GEN-LAST:event_SDQ5KeyPressed

    private void SDQ6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ6ItemStateChanged
        switch (SDQ6.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ6.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ6.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ6.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorP();
    }//GEN-LAST:event_SDQ6ItemStateChanged

    private void SDQ6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ6KeyPressed
        Valid.pindah(evt,SDQ5,SDQ7);
    }//GEN-LAST:event_SDQ6KeyPressed

    private void SDQ7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ7ItemStateChanged
        switch (SDQ7.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ7.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ7.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ7.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorC();
    }//GEN-LAST:event_SDQ7ItemStateChanged

    private void SDQ7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ7KeyPressed
        Valid.pindah(evt,SDQ6,SDQ8);
    }//GEN-LAST:event_SDQ7KeyPressed

    private void SDQ8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ8ItemStateChanged
        switch (SDQ8.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ8.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ8.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ8.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorE();
    }//GEN-LAST:event_SDQ8ItemStateChanged

    private void SDQ8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ8KeyPressed
        Valid.pindah(evt,SDQ7,SDQ9);
    }//GEN-LAST:event_SDQ8KeyPressed

    private void SDQ9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ9ItemStateChanged
        switch (SDQ9.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ9.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ9.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ9.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorPr();
    }//GEN-LAST:event_SDQ9ItemStateChanged

    private void SDQ9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ9KeyPressed
        Valid.pindah(evt,SDQ8,SDQ10);
    }//GEN-LAST:event_SDQ9KeyPressed

    private void SDQ10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ10ItemStateChanged
        switch (SDQ10.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ10.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ10.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ10.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorH();
    }//GEN-LAST:event_SDQ10ItemStateChanged

    private void SDQ10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ10KeyPressed
        Valid.pindah(evt,SDQ9,SDQ11);
    }//GEN-LAST:event_SDQ10KeyPressed

    private void SDQ11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ11ItemStateChanged
        switch (SDQ11.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ11.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ11.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ11.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorP();
    }//GEN-LAST:event_SDQ11ItemStateChanged

    private void SDQ11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ11KeyPressed
        Valid.pindah(evt,SDQ10,SDQ12);
    }//GEN-LAST:event_SDQ11KeyPressed

    private void SDQ12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ12ItemStateChanged
        switch (SDQ12.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ12.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ12.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ12.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorC();
    }//GEN-LAST:event_SDQ12ItemStateChanged

    private void SDQ12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ12KeyPressed
        Valid.pindah(evt,SDQ11,SDQ13);
    }//GEN-LAST:event_SDQ12KeyPressed

    private void SDQ13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ13ItemStateChanged
        switch (SDQ13.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ13.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ13.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ13.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorE();
    }//GEN-LAST:event_SDQ13ItemStateChanged

    private void SDQ13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ13KeyPressed
        Valid.pindah(evt,SDQ12,SDQ14);
    }//GEN-LAST:event_SDQ13KeyPressed

    private void SDQ14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ14ItemStateChanged
        switch (SDQ14.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ14.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ14.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ14.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorP();
    }//GEN-LAST:event_SDQ14ItemStateChanged

    private void SDQ14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ14KeyPressed
        Valid.pindah(evt,SDQ13,SDQ15);
    }//GEN-LAST:event_SDQ14KeyPressed

    private void SDQ15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ15ItemStateChanged
        switch (SDQ15.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ15.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ15.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ15.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorH();
    }//GEN-LAST:event_SDQ15ItemStateChanged

    private void SDQ15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ15KeyPressed
        Valid.pindah(evt,SDQ16,SDQ17);
    }//GEN-LAST:event_SDQ15KeyPressed

    private void SDQ16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ16ItemStateChanged
        switch (SDQ16.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ16.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ16.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ16.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorE();
    }//GEN-LAST:event_SDQ16ItemStateChanged

    private void SDQ16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ16KeyPressed
        Valid.pindah(evt,SDQ15,SDQ17);
    }//GEN-LAST:event_SDQ16KeyPressed

    private void SDQ17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ17ItemStateChanged
        switch (SDQ17.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ17.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ17.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ17.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorPr();
    }//GEN-LAST:event_SDQ17ItemStateChanged

    private void SDQ17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ17KeyPressed
        Valid.pindah(evt,SDQ16,SDQ18);
    }//GEN-LAST:event_SDQ17KeyPressed

    private void SDQ18ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ18ItemStateChanged
       switch (SDQ18.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ18.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ18.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ18.setText("2");
                break;
            default:
                break;
        }
       isTotal();
       SkorC();
    }//GEN-LAST:event_SDQ18ItemStateChanged

    private void SDQ18KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ18KeyPressed
        Valid.pindah(evt,SDQ17,SDQ19);
    }//GEN-LAST:event_SDQ18KeyPressed

    private void SDQ19ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ19ItemStateChanged
        switch (SDQ19.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ19.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ19.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ19.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorP();
    }//GEN-LAST:event_SDQ19ItemStateChanged

    private void SDQ19KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ19KeyPressed
        Valid.pindah(evt,SDQ18,SDQ20);
    }//GEN-LAST:event_SDQ19KeyPressed

    private void SDQ20ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ20ItemStateChanged
        switch (SDQ20.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ20.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ20.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ20.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorPr();
    }//GEN-LAST:event_SDQ20ItemStateChanged

    private void SDQ20KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ20KeyPressed
        Valid.pindah(evt,SDQ19,SDQ21);
    }//GEN-LAST:event_SDQ20KeyPressed

    private void SDQ21ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ21ItemStateChanged
        switch (SDQ21.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ21.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ21.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ21.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorH();
    }//GEN-LAST:event_SDQ21ItemStateChanged

    private void SDQ21KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ21KeyPressed
        Valid.pindah(evt,SDQ20,SDQ22);
    }//GEN-LAST:event_SDQ21KeyPressed

    private void SDQ22ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ22ItemStateChanged
        switch (SDQ22.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ22.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ22.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ22.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorC();
    }//GEN-LAST:event_SDQ22ItemStateChanged

    private void SDQ22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ22KeyPressed
        Valid.pindah(evt,SDQ21,SDQ23);
    }//GEN-LAST:event_SDQ22KeyPressed

    private void SDQ23ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ23ItemStateChanged
        switch (SDQ23.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ23.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ23.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ23.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorP();
    }//GEN-LAST:event_SDQ23ItemStateChanged

    private void SDQ23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ23KeyPressed
        Valid.pindah(evt,SDQ22,SDQ24);
    }//GEN-LAST:event_SDQ23KeyPressed

    private void SDQ24ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ24ItemStateChanged
        switch (SDQ24.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ24.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ24.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ24.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorE();
    }//GEN-LAST:event_SDQ24ItemStateChanged

    private void SDQ24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ24KeyPressed
        Valid.pindah(evt,SDQ23,SDQ25);
    }//GEN-LAST:event_SDQ24KeyPressed

    private void SDQ25ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SDQ25ItemStateChanged
        switch (SDQ25.getSelectedItem().toString()) {
            case "Tidak Benar":
                NilaiSDQ25.setText("0");
                break;
            case "Agak Benar":
                NilaiSDQ25.setText("1");
                break;
            case "Selalu Benar":
                NilaiSDQ25.setText("2");
                break;
            default:
                break;
        }
        isTotal();
        SkorH();
    }//GEN-LAST:event_SDQ25ItemStateChanged

    private void SDQ25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SDQ25KeyPressed
        Valid.pindah(evt,SDQ24,Keterangan);
    }//GEN-LAST:event_SDQ25KeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,SDQ25,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningInstrumenSDQ dialog = new RMSkriningInstrumenSDQ(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jam;
    private widget.TextBox Jk;
    private widget.TextBox KesimpulanKesulitan;
    private widget.TextBox KesimpulanSkorC;
    private widget.TextBox KesimpulanSkorE;
    private widget.TextBox KesimpulanSkorH;
    private widget.TextBox KesimpulanSkorP;
    private widget.TextBox KesimpulanSkorPr;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnFormulirSkriningInstrumenSDQ;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NilaiKesimpulanKesulitan;
    private widget.TextBox NilaiKesimpulanSkorC;
    private widget.TextBox NilaiKesimpulanSkorE;
    private widget.TextBox NilaiKesimpulanSkorH;
    private widget.TextBox NilaiKesimpulanSkorP;
    private widget.TextBox NilaiKesimpulanSkorPr;
    private widget.TextBox NilaiSDQ1;
    private widget.TextBox NilaiSDQ10;
    private widget.TextBox NilaiSDQ11;
    private widget.TextBox NilaiSDQ12;
    private widget.TextBox NilaiSDQ13;
    private widget.TextBox NilaiSDQ14;
    private widget.TextBox NilaiSDQ15;
    private widget.TextBox NilaiSDQ16;
    private widget.TextBox NilaiSDQ17;
    private widget.TextBox NilaiSDQ18;
    private widget.TextBox NilaiSDQ19;
    private widget.TextBox NilaiSDQ2;
    private widget.TextBox NilaiSDQ20;
    private widget.TextBox NilaiSDQ21;
    private widget.TextBox NilaiSDQ22;
    private widget.TextBox NilaiSDQ23;
    private widget.TextBox NilaiSDQ24;
    private widget.TextBox NilaiSDQ25;
    private widget.TextBox NilaiSDQ3;
    private widget.TextBox NilaiSDQ4;
    private widget.TextBox NilaiSDQ5;
    private widget.TextBox NilaiSDQ6;
    private widget.TextBox NilaiSDQ7;
    private widget.TextBox NilaiSDQ8;
    private widget.TextBox NilaiSDQ9;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox SDQ1;
    private widget.ComboBox SDQ10;
    private widget.ComboBox SDQ11;
    private widget.ComboBox SDQ12;
    private widget.ComboBox SDQ13;
    private widget.ComboBox SDQ14;
    private widget.ComboBox SDQ15;
    private widget.ComboBox SDQ16;
    private widget.ComboBox SDQ17;
    private widget.ComboBox SDQ18;
    private widget.ComboBox SDQ19;
    private widget.ComboBox SDQ2;
    private widget.ComboBox SDQ20;
    private widget.ComboBox SDQ21;
    private widget.ComboBox SDQ22;
    private widget.ComboBox SDQ23;
    private widget.ComboBox SDQ24;
    private widget.ComboBox SDQ25;
    private widget.ComboBox SDQ3;
    private widget.ComboBox SDQ4;
    private widget.ComboBox SDQ5;
    private widget.ComboBox SDQ6;
    private widget.ComboBox SDQ7;
    private widget.ComboBox SDQ8;
    private widget.ComboBox SDQ9;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.TextBox TotalNilai;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel218;
    private widget.Label jLabel220;
    private widget.Label jLabel221;
    private widget.Label jLabel222;
    private widget.Label jLabel223;
    private widget.Label jLabel224;
    private widget.Label jLabel225;
    private widget.Label jLabel226;
    private widget.Label jLabel227;
    private widget.Label jLabel228;
    private widget.Label jLabel229;
    private widget.Label jLabel230;
    private widget.Label jLabel231;
    private widget.Label jLabel232;
    private widget.Label jLabel233;
    private widget.Label jLabel234;
    private widget.Label jLabel235;
    private widget.Label jLabel236;
    private widget.Label jLabel237;
    private widget.Label jLabel238;
    private widget.Label jLabel239;
    private widget.Label jLabel240;
    private widget.Label jLabel241;
    private widget.Label jLabel242;
    private widget.Label jLabel243;
    private widget.Label jLabel244;
    private widget.Label jLabel245;
    private widget.Label jLabel246;
    private widget.Label jLabel247;
    private widget.Label jLabel248;
    private widget.Label jLabel249;
    private widget.Label jLabel250;
    private widget.Label jLabel251;
    private widget.Label jLabel252;
    private widget.Label jLabel253;
    private widget.Label jLabel254;
    private widget.Label jLabel255;
    private widget.Label jLabel256;
    private widget.Label jLabel257;
    private widget.Label jLabel258;
    private widget.Label jLabel259;
    private widget.Label jLabel260;
    private widget.Label jLabel261;
    private widget.Label jLabel262;
    private widget.Label jLabel263;
    private widget.Label jLabel264;
    private widget.Label jLabel265;
    private widget.Label jLabel266;
    private widget.Label jLabel267;
    private widget.Label jLabel268;
    private widget.Label jLabel269;
    private widget.Label jLabel270;
    private widget.Label jLabel271;
    private widget.Label jLabel272;
    private widget.Label jLabel273;
    private widget.Label jLabel274;
    private widget.Label jLabel275;
    private widget.Label jLabel276;
    private widget.Label jLabel277;
    private widget.Label jLabel278;
    private widget.Label jLabel279;
    private widget.Label jLabel280;
    private widget.Label jLabel282;
    private widget.Label jLabel284;
    private widget.Label jLabel286;
    private widget.Label jLabel288;
    private widget.Label jLabel291;
    private widget.Label jLabel293;
    private widget.Label jLabel295;
    private widget.Label jLabel297;
    private widget.Label jLabel299;
    private widget.Label jLabel300;
    private widget.Label jLabel301;
    private widget.Label jLabel302;
    private widget.Label jLabel303;
    private widget.Label jLabel304;
    private widget.Label jLabel305;
    private widget.Label jLabel306;
    private widget.Label jLabel307;
    private widget.Label jLabel308;
    private widget.Label jLabel309;
    private widget.Label jLabel310;
    private widget.Label jLabel311;
    private widget.Label jLabel312;
    private widget.Label jLabel313;
    private widget.Label jLabel314;
    private widget.Label jLabel315;
    private widget.Label jLabel316;
    private widget.Label jLabel317;
    private widget.Label jLabel318;
    private widget.Label jLabel319;
    private widget.Label jLabel4;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,skrining_instrumen_sdq.tanggal,"+
                    "skrining_instrumen_sdq.pernyataansdq1,skrining_instrumen_sdq.nilai_sdq1,skrining_instrumen_sdq.pernyataansdq2,skrining_instrumen_sdq.nilai_sdq2,"+
                    "skrining_instrumen_sdq.pernyataansdq3,skrining_instrumen_sdq.nilai_sdq3,skrining_instrumen_sdq.pernyataansdq4,skrining_instrumen_sdq.nilai_sdq4,"+
                    "skrining_instrumen_sdq.pernyataansdq5,skrining_instrumen_sdq.nilai_sdq5,skrining_instrumen_sdq.pernyataansdq6,skrining_instrumen_sdq.nilai_sdq6,"+
                    "skrining_instrumen_sdq.pernyataansdq7,skrining_instrumen_sdq.nilai_sdq7,skrining_instrumen_sdq.pernyataansdq8,skrining_instrumen_sdq.nilai_sdq8,"+
                    "skrining_instrumen_sdq.pernyataansdq9,skrining_instrumen_sdq.nilai_sdq9,skrining_instrumen_sdq.pernyataansdq10,skrining_instrumen_sdq.nilai_sdq10,"+
                    "skrining_instrumen_sdq.pernyataansdq11,skrining_instrumen_sdq.nilai_sdq11,skrining_instrumen_sdq.pernyataansdq12,skrining_instrumen_sdq.nilai_sdq12,"+
                    "skrining_instrumen_sdq.pernyataansdq13,skrining_instrumen_sdq.nilai_sdq13,skrining_instrumen_sdq.pernyataansdq14,skrining_instrumen_sdq.nilai_sdq14,"+
                    "skrining_instrumen_sdq.pernyataansdq15,skrining_instrumen_sdq.nilai_sdq15,skrining_instrumen_sdq.pernyataansdq16,skrining_instrumen_sdq.nilai_sdq16,"+
                    "skrining_instrumen_sdq.pernyataansdq17,skrining_instrumen_sdq.nilai_sdq17,skrining_instrumen_sdq.pernyataansdq18,skrining_instrumen_sdq.nilai_sdq18,"+
                    "skrining_instrumen_sdq.pernyataansdq19,skrining_instrumen_sdq.nilai_sdq19,skrining_instrumen_sdq.pernyataansdq20,skrining_instrumen_sdq.nilai_sdq20,"+
                    "skrining_instrumen_sdq.pernyataansdq21,skrining_instrumen_sdq.nilai_sdq21,skrining_instrumen_sdq.pernyataansdq22,skrining_instrumen_sdq.nilai_sdq22,"+
                    "skrining_instrumen_sdq.pernyataansdq23,skrining_instrumen_sdq.nilai_sdq23,skrining_instrumen_sdq.pernyataansdq24,skrining_instrumen_sdq.nilai_sdq24,"+
                    "skrining_instrumen_sdq.pernyataansdq25,skrining_instrumen_sdq.nilai_sdq25,skrining_instrumen_sdq.nilai_total_sdq,skrining_instrumen_sdq.gejala_emosional,"+
                    "skrining_instrumen_sdq.nilai_gejala_emosional,skrining_instrumen_sdq.masalah_perilaku,skrining_instrumen_sdq.nilai_masalah_perilaku,skrining_instrumen_sdq.hiperaktivitas,"+
                    "skrining_instrumen_sdq.nilai_hiperaktivitas,skrining_instrumen_sdq.teman_sebaya,skrining_instrumen_sdq.nilai_teman_sebaya,skrining_instrumen_sdq.kekuatan,"+
                    "skrining_instrumen_sdq.nilai_kekuatan,skrining_instrumen_sdq.kesulitan,skrining_instrumen_sdq.nilai_kesulitan,skrining_instrumen_sdq.keterangan,"+
                    "skrining_instrumen_sdq.nip,petugas.nama from skrining_instrumen_sdq inner join reg_periksa on skrining_instrumen_sdq.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_sdq.nip=petugas.nip where "+
                    "skrining_instrumen_sdq.tanggal between ? and ? order by skrining_instrumen_sdq.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,skrining_instrumen_sdq.tanggal,"+
                    "skrining_instrumen_sdq.pernyataansdq1,skrining_instrumen_sdq.nilai_sdq1,skrining_instrumen_sdq.pernyataansdq2,skrining_instrumen_sdq.nilai_sdq2,"+
                    "skrining_instrumen_sdq.pernyataansdq3,skrining_instrumen_sdq.nilai_sdq3,skrining_instrumen_sdq.pernyataansdq4,skrining_instrumen_sdq.nilai_sdq4,"+
                    "skrining_instrumen_sdq.pernyataansdq5,skrining_instrumen_sdq.nilai_sdq5,skrining_instrumen_sdq.pernyataansdq6,skrining_instrumen_sdq.nilai_sdq6,"+
                    "skrining_instrumen_sdq.pernyataansdq7,skrining_instrumen_sdq.nilai_sdq7,skrining_instrumen_sdq.pernyataansdq8,skrining_instrumen_sdq.nilai_sdq8,"+
                    "skrining_instrumen_sdq.pernyataansdq9,skrining_instrumen_sdq.nilai_sdq9,skrining_instrumen_sdq.pernyataansdq10,skrining_instrumen_sdq.nilai_sdq10,"+
                    "skrining_instrumen_sdq.pernyataansdq11,skrining_instrumen_sdq.nilai_sdq11,skrining_instrumen_sdq.pernyataansdq12,skrining_instrumen_sdq.nilai_sdq12,"+
                    "skrining_instrumen_sdq.pernyataansdq13,skrining_instrumen_sdq.nilai_sdq13,skrining_instrumen_sdq.pernyataansdq14,skrining_instrumen_sdq.nilai_sdq14,"+
                    "skrining_instrumen_sdq.pernyataansdq15,skrining_instrumen_sdq.nilai_sdq15,skrining_instrumen_sdq.pernyataansdq16,skrining_instrumen_sdq.nilai_sdq16,"+
                    "skrining_instrumen_sdq.pernyataansdq17,skrining_instrumen_sdq.nilai_sdq17,skrining_instrumen_sdq.pernyataansdq18,skrining_instrumen_sdq.nilai_sdq18,"+
                    "skrining_instrumen_sdq.pernyataansdq19,skrining_instrumen_sdq.nilai_sdq19,skrining_instrumen_sdq.pernyataansdq20,skrining_instrumen_sdq.nilai_sdq20,"+
                    "skrining_instrumen_sdq.pernyataansdq21,skrining_instrumen_sdq.nilai_sdq21,skrining_instrumen_sdq.pernyataansdq22,skrining_instrumen_sdq.nilai_sdq22,"+
                    "skrining_instrumen_sdq.pernyataansdq23,skrining_instrumen_sdq.nilai_sdq23,skrining_instrumen_sdq.pernyataansdq24,skrining_instrumen_sdq.nilai_sdq24,"+
                    "skrining_instrumen_sdq.pernyataansdq25,skrining_instrumen_sdq.nilai_sdq25,skrining_instrumen_sdq.nilai_total_sdq,skrining_instrumen_sdq.gejala_emosional,"+
                    "skrining_instrumen_sdq.nilai_gejala_emosional,skrining_instrumen_sdq.masalah_perilaku,skrining_instrumen_sdq.nilai_masalah_perilaku,skrining_instrumen_sdq.hiperaktivitas,"+
                    "skrining_instrumen_sdq.nilai_hiperaktivitas,skrining_instrumen_sdq.teman_sebaya,skrining_instrumen_sdq.nilai_teman_sebaya,skrining_instrumen_sdq.kekuatan,"+
                    "skrining_instrumen_sdq.nilai_kekuatan,skrining_instrumen_sdq.kesulitan,skrining_instrumen_sdq.nilai_kesulitan,skrining_instrumen_sdq.keterangan,"+
                    "skrining_instrumen_sdq.nip,petugas.nama from skrining_instrumen_sdq inner join reg_periksa on skrining_instrumen_sdq.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_instrumen_sdq.nip=petugas.nip where "+
                    "skrining_instrumen_sdq.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or skrining_instrumen_sdq.nip like ? or petugas.nama like ?) "+
                    "order by skrining_instrumen_sdq.tanggal ");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),
                        rs.getString("pernyataansdq1"),rs.getString("nilai_sdq1"),rs.getString("pernyataansdq2"),rs.getString("nilai_sdq2"),rs.getString("pernyataansdq3"),rs.getString("nilai_sdq3"),rs.getString("pernyataansdq4"),
                        rs.getString("nilai_sdq4"),rs.getString("pernyataansdq5"),rs.getString("nilai_sdq5"),rs.getString("pernyataansdq6"),rs.getString("nilai_sdq6"),rs.getString("pernyataansdq7"),rs.getString("nilai_sdq7"),
                        rs.getString("pernyataansdq8"),rs.getString("nilai_sdq8"),rs.getString("pernyataansdq9"),rs.getString("nilai_sdq9"),rs.getString("pernyataansdq10"),rs.getString("nilai_sdq10"),rs.getString("pernyataansdq11"),
                        rs.getString("nilai_sdq11"),rs.getString("pernyataansdq12"),rs.getString("nilai_sdq12"),rs.getString("pernyataansdq13"),rs.getString("nilai_sdq13"),rs.getString("pernyataansdq14"),rs.getString("nilai_sdq14"),
                        rs.getString("pernyataansdq15"),rs.getString("nilai_sdq15"),rs.getString("pernyataansdq16"),rs.getString("nilai_sdq16"),rs.getString("pernyataansdq17"),rs.getString("nilai_sdq17"),rs.getString("pernyataansdq18"),
                        rs.getString("nilai_sdq18"),rs.getString("pernyataansdq19"),rs.getString("nilai_sdq19"),rs.getString("pernyataansdq20"),rs.getString("nilai_sdq20"),rs.getString("pernyataansdq21"),rs.getString("nilai_sdq21"),
                        rs.getString("pernyataansdq22"),rs.getString("nilai_sdq22"),rs.getString("pernyataansdq23"),rs.getString("nilai_sdq23"),rs.getString("pernyataansdq24"),rs.getString("nilai_sdq24"),rs.getString("pernyataansdq25"),
                        rs.getString("nilai_sdq25"),rs.getString("nilai_total_sdq"),rs.getString("gejala_emosional"),rs.getString("nilai_gejala_emosional"),rs.getString("masalah_perilaku"),rs.getString("nilai_masalah_perilaku"),
                        rs.getString("hiperaktivitas"),rs.getString("nilai_hiperaktivitas"),rs.getString("teman_sebaya"),rs.getString("nilai_teman_sebaya"),rs.getString("kekuatan"),rs.getString("nilai_kekuatan"),rs.getString("kesulitan"),
                        rs.getString("nilai_kesulitan"),rs.getString("keterangan")
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void emptTeks() {
        Tanggal.setDate(new Date());
        SDQ1.setSelectedIndex(0);
        NilaiSDQ1.setText("2");
        SDQ2.setSelectedIndex(0);
        NilaiSDQ2.setText("0");
        SDQ3.setSelectedIndex(0);
        NilaiSDQ3.setText("0");
        SDQ4.setSelectedIndex(0);
        NilaiSDQ4.setText("2");
        SDQ5.setSelectedIndex(0);
        NilaiSDQ5.setText("0");
        SDQ6.setSelectedIndex(0);
        NilaiSDQ6.setText("0");
        SDQ7.setSelectedIndex(0);
        NilaiSDQ7.setText("0");
        SDQ8.setSelectedIndex(0);
        NilaiSDQ8.setText("0");
        SDQ9.setSelectedIndex(0);
        NilaiSDQ9.setText("2");
        SDQ10.setSelectedIndex(0);
        NilaiSDQ10.setText("0");
        SDQ11.setSelectedIndex(0);
        NilaiSDQ11.setText("0");
        SDQ12.setSelectedIndex(0);
        NilaiSDQ12.setText("0");
        SDQ13.setSelectedIndex(0);
        NilaiSDQ13.setText("0");
        SDQ14.setSelectedIndex(0);
        NilaiSDQ14.setText("0");
        SDQ15.setSelectedIndex(0);
        NilaiSDQ15.setText("0");
        SDQ16.setSelectedIndex(0);
        NilaiSDQ16.setText("0");
        SDQ17.setSelectedIndex(0);
        NilaiSDQ17.setText("2");
        SDQ18.setSelectedIndex(0);
        NilaiSDQ18.setText("0");
        SDQ19.setSelectedIndex(0);
        NilaiSDQ19.setText("0");
        SDQ20.setSelectedIndex(0);
        NilaiSDQ20.setText("2");
        SDQ21.setSelectedIndex(0);
        NilaiSDQ21.setText("0");
        SDQ22.setSelectedIndex(0);
        NilaiSDQ22.setText("0");
        SDQ23.setSelectedIndex(0);
        NilaiSDQ23.setText("0");
        SDQ24.setSelectedIndex(0);
        NilaiSDQ24.setText("0");
        SDQ25.setSelectedIndex(0);
        NilaiSDQ25.setText("0");
        TotalNilai.setText("0");
        NilaiKesimpulanSkorE.setText("0");
        NilaiKesimpulanSkorC.setText("0");
        NilaiKesimpulanSkorH.setText("0");
        NilaiKesimpulanSkorP.setText("0");
        NilaiKesimpulanSkorPr.setText("0");
        NilaiKesimpulanKesulitan.setText("0");
        KesimpulanSkorE.setText("Normal");
        KesimpulanSkorC.setText("Normal");
        KesimpulanSkorH.setText("Normal");
        KesimpulanSkorP.setText("Normal");
        KesimpulanSkorPr.setText("Normal");
        KesimpulanKesulitan.setText("Normal");
        Keterangan.setText("");
        SDQ1.requestFocus();
    } 
    

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            SDQ1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiSDQ1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            SDQ2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiSDQ2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            SDQ3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NilaiSDQ3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            SDQ4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NilaiSDQ4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
             SDQ5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NilaiSDQ5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            SDQ6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NilaiSDQ6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            SDQ7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiSDQ7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            SDQ8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiSDQ8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            SDQ9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NilaiSDQ9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            SDQ10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NilaiSDQ10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            SDQ11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            NilaiSDQ11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            SDQ12.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            NilaiSDQ12.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            SDQ13.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            NilaiSDQ13.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            SDQ14.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            NilaiSDQ14.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            SDQ15.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            NilaiSDQ15.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            SDQ16.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            NilaiSDQ16.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            SDQ17.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            NilaiSDQ17.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            SDQ18.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            NilaiSDQ18.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            SDQ19.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            NilaiSDQ19.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            SDQ20.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            NilaiSDQ20.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            SDQ21.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            NilaiSDQ21.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            SDQ22.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            NilaiSDQ22.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            SDQ23.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            NilaiSDQ23.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            SDQ24.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            NilaiSDQ24.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            SDQ25.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            NilaiSDQ25.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            TotalNilai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            KesimpulanSkorE.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            NilaiKesimpulanSkorE.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            KesimpulanSkorC.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            NilaiKesimpulanSkorC.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            KesimpulanSkorH.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            NilaiKesimpulanSkorH.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            KesimpulanSkorP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            NilaiKesimpulanSkorP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            KesimpulanSkorPr.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            NilaiKesimpulanSkorPr.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            KesimpulanKesulitan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            NilaiKesimpulanKesulitan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getskrining_instrumen_sdq());
        BtnHapus.setEnabled(akses.getskrining_instrumen_sdq());
        BtnEdit.setEnabled(akses.getskrining_instrumen_sdq());
        BtnPrint.setEnabled(akses.getskrining_instrumen_sdq()); 
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
            if(NamaPetugas.getText().equals("")){
                NIP.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        } 
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        }
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        if(Sequel.mengedittf("skrining_instrumen_sdq","no_rawat=?","no_rawat=?,tanggal=?,nip=?,pernyataansdq1=?,nilai_sdq1=?,pernyataansdq2=?,nilai_sdq2=?,pernyataansdq3=?,nilai_sdq3=?,"+
                "pernyataansdq4=?,nilai_sdq4=?,pernyataansdq5=?,nilai_sdq5=?,pernyataansdq6=?,nilai_sdq6=?,pernyataansdq7=?,nilai_sdq7=?,pernyataansdq8=?,nilai_sdq8=?,pernyataansdq9=?,"+
                "nilai_sdq9=?,pernyataansdq10=?,nilai_sdq10=?,pernyataansdq11=?,nilai_sdq11=?,pernyataansdq12=?,nilai_sdq12=?,pernyataansdq13=?,nilai_sdq13=?,pernyataansdq14=?,nilai_sdq14=?,"+
                "pernyataansdq15=?,nilai_sdq15=?,pernyataansdq16=?,nilai_sdq16=?,pernyataansdq17=?,nilai_sdq17=?,pernyataansdq18=?,nilai_sdq18=?,pernyataansdq19=?,nilai_sdq19=?,pernyataansdq20=?,"+
                "nilai_sdq20=?,pernyataansdq21=?,nilai_sdq21=?,pernyataansdq22=?,nilai_sdq22=?,pernyataansdq23=?,nilai_sdq23=?,pernyataansdq24=?,nilai_sdq24=?,pernyataansdq25=?,nilai_sdq25=?,"+
                "nilai_total_sdq=?,gejala_emosional=?,nilai_gejala_emosional=?,masalah_perilaku=?,nilai_masalah_perilaku=?,hiperaktivitas=?,nilai_hiperaktivitas=?,teman_sebaya=?,nilai_teman_sebaya=?,"+
                "kekuatan=?,nilai_kekuatan=?,kesulitan=?,nilai_kesulitan=?,keterangan=?",68,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),NIP.getText(),
                SDQ1.getSelectedItem().toString(),NilaiSDQ1.getText(),SDQ2.getSelectedItem().toString(),NilaiSDQ2.getText(),SDQ3.getSelectedItem().toString(),NilaiSDQ3.getText(),SDQ4.getSelectedItem().toString(),NilaiSDQ4.getText(), 
                SDQ5.getSelectedItem().toString(),NilaiSDQ5.getText(),SDQ6.getSelectedItem().toString(),NilaiSDQ6.getText(),SDQ7.getSelectedItem().toString(),NilaiSDQ7.getText(),SDQ8.getSelectedItem().toString(),NilaiSDQ8.getText(), 
                SDQ9.getSelectedItem().toString(),NilaiSDQ9.getText(),SDQ10.getSelectedItem().toString(),NilaiSDQ10.getText(),SDQ11.getSelectedItem().toString(),NilaiSDQ11.getText(),SDQ12.getSelectedItem().toString(),NilaiSDQ12.getText(),
                SDQ13.getSelectedItem().toString(),NilaiSDQ13.getText(),SDQ14.getSelectedItem().toString(),NilaiSDQ14.getText(),SDQ15.getSelectedItem().toString(),NilaiSDQ15.getText(),SDQ16.getSelectedItem().toString(),NilaiSDQ16.getText(),
                SDQ17.getSelectedItem().toString(),NilaiSDQ17.getText(),SDQ18.getSelectedItem().toString(),NilaiSDQ18.getText(),SDQ19.getSelectedItem().toString(),NilaiSDQ19.getText(),SDQ20.getSelectedItem().toString(),NilaiSDQ20.getText(),
                SDQ21.getSelectedItem().toString(),NilaiSDQ21.getText(),SDQ22.getSelectedItem().toString(),NilaiSDQ22.getText(),SDQ23.getSelectedItem().toString(),NilaiSDQ23.getText(),SDQ24.getSelectedItem().toString(),NilaiSDQ24.getText(),
                SDQ25.getSelectedItem().toString(),NilaiSDQ25.getText(),TotalNilai.getText(),KesimpulanSkorE.getText(),NilaiKesimpulanSkorE.getText(),KesimpulanSkorC.getText(),NilaiKesimpulanSkorC.getText(),KesimpulanSkorH.getText(),
                NilaiKesimpulanSkorH.getText(),KesimpulanSkorP.getText(),NilaiKesimpulanSkorP.getText(),KesimpulanSkorPr.getText(),NilaiKesimpulanSkorPr.getText(),KesimpulanKesulitan.getText(),NilaiKesimpulanKesulitan.getText(),Keterangan.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(SDQ1.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NilaiSDQ1.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(SDQ2.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(NilaiSDQ2.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(SDQ3.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(NilaiSDQ3.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(SDQ4.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(NilaiSDQ4.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt( SDQ5.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(NilaiSDQ5.getText(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(SDQ6.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(NilaiSDQ6.getText(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(SDQ7.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(NilaiSDQ7.getText(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(SDQ8.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(NilaiSDQ8.getText(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(SDQ9.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(NilaiSDQ9.getText(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(SDQ10.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(NilaiSDQ10.getText(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(SDQ11.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(NilaiSDQ11.getText(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(SDQ12.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(NilaiSDQ12.getText(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(SDQ13.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(NilaiSDQ13.getText(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(SDQ14.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
            tbObat.setValueAt(NilaiSDQ14.getText(),tbObat.getSelectedRow(),35);
            tbObat.setValueAt(SDQ15.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
            tbObat.setValueAt(NilaiSDQ15.getText(),tbObat.getSelectedRow(),37);
            tbObat.setValueAt(SDQ16.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
            tbObat.setValueAt(NilaiSDQ16.getText(),tbObat.getSelectedRow(),39);
            tbObat.setValueAt(SDQ17.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
            tbObat.setValueAt(NilaiSDQ17.getText(),tbObat.getSelectedRow(),41);
            tbObat.setValueAt(SDQ18.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
            tbObat.setValueAt(NilaiSDQ18.getText(),tbObat.getSelectedRow(),43);
            tbObat.setValueAt(SDQ19.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
            tbObat.setValueAt(NilaiSDQ19.getText(),tbObat.getSelectedRow(),45);
            tbObat.setValueAt(SDQ20.getSelectedItem().toString(),tbObat.getSelectedRow(),46);
            tbObat.setValueAt(NilaiSDQ20.getText(),tbObat.getSelectedRow(),47);
            tbObat.setValueAt(SDQ21.getSelectedItem().toString(),tbObat.getSelectedRow(),48);
            tbObat.setValueAt(NilaiSDQ21.getText(),tbObat.getSelectedRow(),49);
            tbObat.setValueAt(SDQ22.getSelectedItem().toString(),tbObat.getSelectedRow(),50);
            tbObat.setValueAt(NilaiSDQ22.getText(),tbObat.getSelectedRow(),51);
            tbObat.setValueAt(SDQ23.getSelectedItem().toString(),tbObat.getSelectedRow(),52);
            tbObat.setValueAt(NilaiSDQ23.getText(),tbObat.getSelectedRow(),53);
            tbObat.setValueAt(SDQ24.getSelectedItem().toString(),tbObat.getSelectedRow(),54);
            tbObat.setValueAt(NilaiSDQ24.getText(),tbObat.getSelectedRow(),55);
            tbObat.setValueAt(SDQ25.getSelectedItem().toString(),tbObat.getSelectedRow(),56);
            tbObat.setValueAt(NilaiSDQ25.getText(),tbObat.getSelectedRow(),57);
            tbObat.setValueAt(TotalNilai.getText(),tbObat.getSelectedRow(),58);
            tbObat.setValueAt(KesimpulanSkorE.getText(),tbObat.getSelectedRow(),59);
            tbObat.setValueAt(NilaiKesimpulanSkorE.getText(),tbObat.getSelectedRow(),60);
            tbObat.setValueAt(KesimpulanSkorC.getText(),tbObat.getSelectedRow(),61);
            tbObat.setValueAt(NilaiKesimpulanSkorC.getText(),tbObat.getSelectedRow(),62);
            tbObat.setValueAt(KesimpulanSkorH.getText(),tbObat.getSelectedRow(),63);
            tbObat.setValueAt(NilaiKesimpulanSkorH.getText(),tbObat.getSelectedRow(),64);
            tbObat.setValueAt(KesimpulanSkorP.getText(),tbObat.getSelectedRow(),65);
            tbObat.setValueAt(NilaiKesimpulanSkorP.getText(),tbObat.getSelectedRow(),66);
            tbObat.setValueAt(KesimpulanSkorPr.getText(),tbObat.getSelectedRow(),67);
            tbObat.setValueAt(NilaiKesimpulanSkorPr.getText(),tbObat.getSelectedRow(),68);
            tbObat.setValueAt(KesimpulanKesulitan.getText(),tbObat.getSelectedRow(),69);
            tbObat.setValueAt(NilaiKesimpulanKesulitan.getText(),tbObat.getSelectedRow(),70);
            tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),71);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_instrumen_sdq where tanggal=? and no_rawat=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void isTotal() {
        TotalNilai.setText(""+(
            Integer.parseInt(NilaiSDQ1.getText())+Integer.parseInt(NilaiSDQ2.getText())+Integer.parseInt(NilaiSDQ3.getText())+Integer.parseInt(NilaiSDQ4.getText())+Integer.parseInt(NilaiSDQ5.getText())+
            Integer.parseInt(NilaiSDQ6.getText())+Integer.parseInt(NilaiSDQ7.getText())+Integer.parseInt(NilaiSDQ8.getText())+Integer.parseInt(NilaiSDQ9.getText())+Integer.parseInt(NilaiSDQ10.getText())+
            Integer.parseInt(NilaiSDQ11.getText())+Integer.parseInt(NilaiSDQ12.getText())+Integer.parseInt(NilaiSDQ13.getText())+Integer.parseInt(NilaiSDQ14.getText())+Integer.parseInt(NilaiSDQ15.getText())+
            Integer.parseInt(NilaiSDQ16.getText())+Integer.parseInt(NilaiSDQ17.getText())+Integer.parseInt(NilaiSDQ18.getText())+Integer.parseInt(NilaiSDQ19.getText())+Integer.parseInt(NilaiSDQ20.getText())+
            Integer.parseInt(NilaiSDQ21.getText())+Integer.parseInt(NilaiSDQ22.getText())+Integer.parseInt(NilaiSDQ23.getText())+Integer.parseInt(NilaiSDQ24.getText())+Integer.parseInt(NilaiSDQ25.getText())
        ));
    }
    
    private void SkorE() {
        try {
            NilaiKesimpulanSkorE.setText(""+(
                Integer.parseInt(NilaiSDQ3.getText())+Integer.parseInt(NilaiSDQ8.getText())+Integer.parseInt(NilaiSDQ13.getText())+Integer.parseInt(NilaiSDQ16.getText())+Integer.parseInt(NilaiSDQ24.getText())
            ));
            
            if(Integer.parseInt(NilaiKesimpulanSkorE.getText())>7){
                KesimpulanSkorE.setText("Abnormal");
            }else if(Integer.parseInt(TotalNilai.getText())>6){
                KesimpulanSkorE.setText("Ambang/Boderline");
            }else{
                KesimpulanSkorE.setText("Normal");
            }
        } catch (Exception e) {
            KesimpulanSkorE.setText("Normal");
        }
        SkorKesulitan();
    }
    
    private void SkorC() {
         try {
            NilaiKesimpulanSkorC.setText(""+(
                Integer.parseInt(NilaiSDQ5.getText())+Integer.parseInt(NilaiSDQ7.getText())+Integer.parseInt(NilaiSDQ12.getText())+Integer.parseInt(NilaiSDQ18.getText())+Integer.parseInt(NilaiSDQ22.getText())
            ));
            
            if(Integer.parseInt(NilaiKesimpulanSkorC.getText())>6){
                KesimpulanSkorC.setText("Abnormal");
            }else if(Integer.parseInt(TotalNilai.getText())>4){
                KesimpulanSkorC.setText("Ambang/Boderline");
            }else{
                KesimpulanSkorE.setText("Normal");
            }
        } catch (Exception e) {
            KesimpulanSkorC.setText("Normal");
        }
        SkorKesulitan();
    }
    
    private void SkorH() {
         try {
            NilaiKesimpulanSkorH.setText(""+(
                Integer.parseInt(NilaiSDQ2.getText())+Integer.parseInt(NilaiSDQ10.getText())+Integer.parseInt(NilaiSDQ15.getText())+Integer.parseInt(NilaiSDQ21.getText())+Integer.parseInt(NilaiSDQ25.getText())
            ));
            
            if(Integer.parseInt(NilaiKesimpulanSkorH.getText())>7){
                KesimpulanSkorH.setText("Abnormal");
            }else if(Integer.parseInt(TotalNilai.getText())>6){
                KesimpulanSkorH.setText("Ambang/Boderline");
            }else{
                KesimpulanSkorH.setText("Normal");
            }
        } catch (Exception e) {
            KesimpulanSkorH.setText("Normal");
        }
        SkorKesulitan();
    }
    
    private void SkorP() {
        try {
            NilaiKesimpulanSkorP.setText(""+(
                    Integer.parseInt(NilaiSDQ6.getText())+Integer.parseInt(NilaiSDQ11.getText())+Integer.parseInt(NilaiSDQ14.getText())+Integer.parseInt(NilaiSDQ19.getText())+Integer.parseInt(NilaiSDQ23.getText())
            ));
            
            if(Integer.parseInt(NilaiKesimpulanSkorP.getText())>6){
                KesimpulanSkorP.setText("Abnormal");
            }else if(Integer.parseInt(TotalNilai.getText())>4){
                KesimpulanSkorP.setText("Ambang/Boderline");
            }else{
                KesimpulanSkorP.setText("Normal");
            }
        } catch (Exception e) {
            KesimpulanSkorP.setText("Normal");
        }
        SkorKesulitan();
    }
    
    private void SkorPr() {
        try {
            NilaiKesimpulanSkorPr.setText(""+(
                Integer.parseInt(NilaiSDQ1.getText())+Integer.parseInt(NilaiSDQ4.getText())+Integer.parseInt(NilaiSDQ9.getText())+Integer.parseInt(NilaiSDQ17.getText())+Integer.parseInt(NilaiSDQ20.getText())
            ));
            
            if(Integer.parseInt(NilaiKesimpulanSkorPr.getText())<4){
                KesimpulanSkorPr.setText("Abnormal");
            }else if(Integer.parseInt(TotalNilai.getText())<5){
                KesimpulanSkorPr.setText("Ambang/Boderline");
            }else{
                KesimpulanSkorPr.setText("Normal");
            }
        } catch (Exception e) {
            KesimpulanSkorPr.setText("Normal");
        }
    }
    
    private void SkorKesulitan() {
        try {
            NilaiKesimpulanKesulitan.setText(""+(
                Integer.parseInt(NilaiKesimpulanSkorE.getText())+Integer.parseInt(NilaiKesimpulanSkorC.getText())+Integer.parseInt(NilaiKesimpulanSkorH.getText())+Integer.parseInt(NilaiKesimpulanSkorP.getText())
            ));
            
            if(Integer.parseInt(NilaiKesimpulanKesulitan.getText())>20){
                KesimpulanKesulitan.setText("Abnormal");
            }else if(Integer.parseInt(TotalNilai.getText())>16){
                KesimpulanKesulitan.setText("Ambang/Boderline");
            }else{
                KesimpulanKesulitan.setText("Normal");
            }
        } catch (Exception e) {
            KesimpulanKesulitan.setText("Normal");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("skrining_instrumen_sdq","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",67,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),NIP.getText(), 
            SDQ1.getSelectedItem().toString(),NilaiSDQ1.getText(),SDQ2.getSelectedItem().toString(),NilaiSDQ2.getText(),SDQ3.getSelectedItem().toString(),NilaiSDQ3.getText(),SDQ4.getSelectedItem().toString(),NilaiSDQ4.getText(), 
            SDQ5.getSelectedItem().toString(),NilaiSDQ5.getText(),SDQ6.getSelectedItem().toString(),NilaiSDQ6.getText(),SDQ7.getSelectedItem().toString(),NilaiSDQ7.getText(),SDQ8.getSelectedItem().toString(),NilaiSDQ8.getText(), 
            SDQ9.getSelectedItem().toString(),NilaiSDQ9.getText(),SDQ10.getSelectedItem().toString(),NilaiSDQ10.getText(),SDQ11.getSelectedItem().toString(),NilaiSDQ11.getText(),SDQ12.getSelectedItem().toString(),NilaiSDQ12.getText(),
            SDQ13.getSelectedItem().toString(),NilaiSDQ13.getText(),SDQ14.getSelectedItem().toString(),NilaiSDQ14.getText(),SDQ15.getSelectedItem().toString(),NilaiSDQ15.getText(),SDQ16.getSelectedItem().toString(),NilaiSDQ16.getText(),
            SDQ17.getSelectedItem().toString(),NilaiSDQ17.getText(),SDQ18.getSelectedItem().toString(),NilaiSDQ18.getText(),SDQ19.getSelectedItem().toString(),NilaiSDQ19.getText(),SDQ20.getSelectedItem().toString(),NilaiSDQ20.getText(),
            SDQ21.getSelectedItem().toString(),NilaiSDQ21.getText(),SDQ22.getSelectedItem().toString(),NilaiSDQ22.getText(),SDQ23.getSelectedItem().toString(),NilaiSDQ23.getText(),SDQ24.getSelectedItem().toString(),NilaiSDQ24.getText(),
            SDQ25.getSelectedItem().toString(),NilaiSDQ25.getText(),TotalNilai.getText(),KesimpulanSkorE.getText(),NilaiKesimpulanSkorE.getText(),KesimpulanSkorC.getText(),NilaiKesimpulanSkorC.getText(),KesimpulanSkorH.getText(),
            NilaiKesimpulanSkorH.getText(),KesimpulanSkorP.getText(),NilaiKesimpulanSkorP.getText(),KesimpulanSkorPr.getText(),NilaiKesimpulanSkorPr.getText(),KesimpulanKesulitan.getText(),NilaiKesimpulanKesulitan.getText(),Keterangan.getText()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),NIP.getText(),NamaPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                SDQ1.getSelectedItem().toString(),NilaiSDQ1.getText(),SDQ2.getSelectedItem().toString(),NilaiSDQ2.getText(),SDQ3.getSelectedItem().toString(),NilaiSDQ3.getText(),SDQ4.getSelectedItem().toString(),NilaiSDQ4.getText(), 
                SDQ5.getSelectedItem().toString(),NilaiSDQ5.getText(),SDQ6.getSelectedItem().toString(),NilaiSDQ6.getText(),SDQ7.getSelectedItem().toString(),NilaiSDQ7.getText(),SDQ8.getSelectedItem().toString(),NilaiSDQ8.getText(), 
                SDQ9.getSelectedItem().toString(),NilaiSDQ9.getText(),SDQ10.getSelectedItem().toString(),NilaiSDQ10.getText(),SDQ11.getSelectedItem().toString(),NilaiSDQ11.getText(),SDQ12.getSelectedItem().toString(),NilaiSDQ12.getText(),
                SDQ13.getSelectedItem().toString(),NilaiSDQ13.getText(),SDQ14.getSelectedItem().toString(),NilaiSDQ14.getText(),SDQ15.getSelectedItem().toString(),NilaiSDQ15.getText(),SDQ16.getSelectedItem().toString(),NilaiSDQ16.getText(),
                SDQ17.getSelectedItem().toString(),NilaiSDQ17.getText(),SDQ18.getSelectedItem().toString(),NilaiSDQ18.getText(),SDQ19.getSelectedItem().toString(),NilaiSDQ19.getText(),SDQ20.getSelectedItem().toString(),NilaiSDQ20.getText(),
                SDQ21.getSelectedItem().toString(),NilaiSDQ21.getText(),SDQ22.getSelectedItem().toString(),NilaiSDQ22.getText(),SDQ23.getSelectedItem().toString(),NilaiSDQ23.getText(),SDQ24.getSelectedItem().toString(),NilaiSDQ24.getText(),
                SDQ25.getSelectedItem().toString(),NilaiSDQ25.getText(),TotalNilai.getText(),KesimpulanSkorE.getText(),NilaiKesimpulanSkorE.getText(),KesimpulanSkorC.getText(),NilaiKesimpulanSkorC.getText(),KesimpulanSkorH.getText(),
                NilaiKesimpulanSkorH.getText(),KesimpulanSkorP.getText(),NilaiKesimpulanSkorP.getText(),KesimpulanSkorPr.getText(),NilaiKesimpulanSkorPr.getText(),KesimpulanKesulitan.getText(),NilaiKesimpulanKesulitan.getText(),Keterangan.getText()
            });
            emptTeks();
            LCount.setText(""+tabMode.getRowCount());
        } 
    }
}

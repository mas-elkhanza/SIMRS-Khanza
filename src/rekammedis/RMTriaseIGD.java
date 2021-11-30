/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
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
import kepegawaian.DlgCariPegawai;


/**
 *
 * @author perpustakaan
 */
public final class RMTriaseIGD extends javax.swing.JDialog {
    private DefaultTableModel tabMode,tabModePemeriksaan,tabModeSkala1,tabModeSkala2,tabModeSkala3,tabModeSkala4,tabModeSkala5,tabModePemeriksaan2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private int i=0,jml=0,index=0,jmlskala1=0,jmlskala2=0,jmlskala3=0,jmlskala4=0,jmlskala5=0;
    private MasterTriaseMacamKasus kasus=new MasterTriaseMacamKasus(null,false);
    private boolean[] pilih; 
    private String[] kode,pengkajian;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private String keputusan="",pilihan="",datatriase="",finger="";
    private StringBuilder htmlContent;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMTriaseIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Tgl.Kunjungan","Cara Masuk","Transportasi",
                "Alasan Kedatangan","Keterangan","Kode Kasus","Macam Kasus"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbTriase.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTriase.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTriase.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbTriase.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(300);
            }
        }
        tbTriase.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePemeriksaan=new DefaultTableModel(null,new Object[]{
                "Kode Pemeriksaan","Pemeriksaan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPemeriksaan.setModel(tabModePemeriksaan);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(255);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePemeriksaan2=new DefaultTableModel(null,new Object[]{
                "Kode Pemeriksaan","Pemeriksaan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPemeriksaan2.setModel(tabModePemeriksaan2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPemeriksaan2.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbPemeriksaan2.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(255);
            }
        }
        tbPemeriksaan2.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeSkala1=new DefaultTableModel(null,new Object[]{
                "P","Kode","Immediate/Segera"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbSkala1.setModel(tabModeSkala1);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala1.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSkala1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala1.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(340);
            }
        }
        tbSkala1.getTableHeader().setForeground(new Color(170,00,0));
        tbSkala1.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeSkala2=new DefaultTableModel(null,new Object[]{
                "P","Kode","Emergensi"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbSkala2.setModel(tabModeSkala2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala2.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSkala2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(340);
            }
        }
        tbSkala2.getTableHeader().setForeground(new Color(255,00,0));
        tbSkala2.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeSkala3=new DefaultTableModel(null,new Object[]{
                "P","Kode","Urgensi"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbSkala3.setModel(tabModeSkala3);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala3.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSkala3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala3.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(340);
            }
        }
        tbSkala3.getTableHeader().setForeground(new Color(200,200,0));
        tbSkala3.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeSkala4=new DefaultTableModel(null,new Object[]{
                "P","Kode","Semi Urgensi/Urgensi Rendah"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbSkala4.setModel(tabModeSkala4);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala4.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSkala4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala4.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(340);
            }
        }
        tbSkala4.getTableHeader().setForeground(new Color(0,170,0));
        tbSkala4.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeSkala5=new DefaultTableModel(null,new Object[]{
                "P","Kode","Non Urgensi"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbSkala5.setModel(tabModeSkala5);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala5.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSkala5.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala5.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(340);
            }
        }
        tbSkala5.getTableHeader().setForeground(new Color(150,150,150));
        tbSkala5.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        TCariPemeriksaan.setDocument(new batasInput((int)100).getKata(TCariPemeriksaan));
        TCariPemeriksaan2.setDocument(new batasInput((int)100).getKata(TCariPemeriksaan2));
        TCariSkala1.setDocument(new batasInput((int)100).getKata(TCariSkala1));
        TCariSkala3.setDocument(new batasInput((int)100).getKata(TCariSkala3));
        KeteranganKedatangan.setDocument(new batasInput((int)100).getKata(KeteranganKedatangan));
        PrimerSuhu.setDocument(new batasInput((byte)5).getKata(PrimerSuhu));
        SekunderSuhu.setDocument(new batasInput((byte)5).getKata(SekunderSuhu));
        PrimerNyeri.setDocument(new batasInput((byte)5).getKata(PrimerNyeri));
        SekunderNyeri.setDocument(new batasInput((byte)5).getKata(SekunderNyeri));
        PrimerTensi.setDocument(new batasInput((byte)8).getKata(PrimerTensi));
        SekunderTensi.setDocument(new batasInput((byte)8).getKata(SekunderTensi));
        PrimerNadi.setDocument(new batasInput((byte)3).getKata(PrimerNadi));
        SekunderNadi.setDocument(new batasInput((byte)3).getKata(SekunderNadi));
        PrimerSaturasi.setDocument(new batasInput((byte)3).getKata(PrimerSaturasi));
        SekunderSaturasi.setDocument(new batasInput((byte)3).getKata(SekunderSaturasi));
        PrimerRespirasi.setDocument(new batasInput((byte)3).getKata(PrimerRespirasi));
        SekunderRespirasi.setDocument(new batasInput((byte)3).getKata(SekunderRespirasi));
        PrimerCatatan.setDocument(new batasInput((int)100).getKata(PrimerCatatan));
        SekunderCatatan.setDocument(new batasInput((int)100).getKata(SekunderCatatan));
        PrimerKeluhanUtama.setDocument(new batasInput((int)400).getKata(PrimerKeluhanUtama));
        SekunderAnamnesa.setDocument(new batasInput((int)400).getKata(SekunderAnamnesa));
       
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
        
        kasus.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kasus.getTable().getSelectedRow()!= -1){
                    KdKasus.setText(kasus.getTable().getValueAt(kasus.getTable().getSelectedRow(),0).toString());
                    NmKasus.setText(kasus.getTable().getValueAt(kasus.getTable().getSelectedRow(),1).toString());
                }  
                btnKasus.requestFocus();
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
        
        kasus.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kasus.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){       
                    if(index==1){
                        PrimerKodePetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        PrimerNamaPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                        btnPrimerPetugas.requestFocus();
                    }else if(index==2){
                        SekunderKodePetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                        SekunderNamaPetugas.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                        btnSekunderPetugas.requestFocus();
                    }
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
        
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
        LoadHTML2 = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        TabPilihan = new javax.swing.JTabbedPane();
        ScrollTriase = new widget.ScrollPane();
        FormTriase = new widget.InternalFrame();
        TabTriase = new javax.swing.JTabbedPane();
        internalFrame5 = new widget.InternalFrame();
        internalFrame7 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        PrimerKeluhanUtama = new widget.TextArea();
        jLabel9 = new widget.Label();
        jLabel24 = new widget.Label();
        PrimerKubutuhanKusus = new widget.ComboBox();
        jLabel10 = new widget.Label();
        PrimerSuhu = new widget.TextBox();
        jLabel16 = new widget.Label();
        PrimerSaturasi = new widget.TextBox();
        jLabel25 = new widget.Label();
        PrimerNyeri = new widget.TextBox();
        PrimerNadi = new widget.TextBox();
        jLabel26 = new widget.Label();
        PrimerRespirasi = new widget.TextBox();
        jLabel27 = new widget.Label();
        PrimerTensi = new widget.TextBox();
        jLabel11 = new widget.Label();
        internalFrame8 = new widget.InternalFrame();
        jLabel12 = new widget.Label();
        PrimerKodePetugas = new widget.TextBox();
        PrimerNamaPetugas = new widget.TextBox();
        btnPrimerPetugas = new widget.Button();
        jLabel28 = new widget.Label();
        PrimerTanggalTriase = new widget.Tanggal();
        jLabel29 = new widget.Label();
        PrimerResusitasi = new widget.RadioButton();
        PrimerKritis = new widget.RadioButton();
        label14 = new widget.Label();
        PrimerCatatan = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        internalFrame9 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        TCariPemeriksaan = new widget.TextBox();
        BtnCariPemeriksaan = new widget.Button();
        BtnTambahPemeriksaan = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        jPanel4 = new javax.swing.JPanel();
        panelisi6 = new widget.panelisi();
        label11 = new widget.Label();
        TCariSkala1 = new widget.TextBox();
        BtnCariSkala1 = new widget.Button();
        BtnTambahSkala1 = new widget.Button();
        TabSkala1dan2 = new javax.swing.JTabbedPane();
        Scroll3 = new widget.ScrollPane();
        tbSkala1 = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbSkala2 = new widget.Table();
        internalFrame10 = new widget.InternalFrame();
        internalFrame11 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        SekunderAnamnesa = new widget.TextArea();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        SekunderSuhu = new widget.TextBox();
        jLabel17 = new widget.Label();
        SekunderSaturasi = new widget.TextBox();
        jLabel31 = new widget.Label();
        SekunderNyeri = new widget.TextBox();
        SekunderNadi = new widget.TextBox();
        jLabel32 = new widget.Label();
        SekunderRespirasi = new widget.TextBox();
        jLabel33 = new widget.Label();
        SekunderTensi = new widget.TextBox();
        jLabel15 = new widget.Label();
        internalFrame12 = new widget.InternalFrame();
        jLabel30 = new widget.Label();
        SekunderKodePetugas = new widget.TextBox();
        SekunderNamaPetugas = new widget.TextBox();
        btnSekunderPetugas = new widget.Button();
        jLabel37 = new widget.Label();
        SekunderTanggalTriase = new widget.Tanggal();
        jLabel38 = new widget.Label();
        SekunderZonaKuning = new widget.RadioButton();
        SekunderZonaHijau = new widget.RadioButton();
        label15 = new widget.Label();
        SekunderCatatan = new widget.TextBox();
        internalFrame13 = new widget.InternalFrame();
        internalFrame14 = new widget.InternalFrame();
        jPanel5 = new javax.swing.JPanel();
        panelisi7 = new widget.panelisi();
        label12 = new widget.Label();
        TCariPemeriksaan2 = new widget.TextBox();
        BtnCariPemeriksaan1 = new widget.Button();
        BtnTambahPemeriksaan1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        tbPemeriksaan2 = new widget.Table();
        jPanel6 = new javax.swing.JPanel();
        panelisi8 = new widget.panelisi();
        label13 = new widget.Label();
        TCariSkala3 = new widget.TextBox();
        BtnCariSkala2 = new widget.Button();
        BtnTambahSkala2 = new widget.Button();
        TabSkala3dan4dan5 = new javax.swing.JTabbedPane();
        Scroll6 = new widget.ScrollPane();
        tbSkala3 = new widget.Table();
        Scroll7 = new widget.ScrollPane();
        tbSkala4 = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbSkala5 = new widget.Table();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel18 = new widget.Label();
        TanggalKunjungan = new widget.Tanggal();
        jLabel20 = new widget.Label();
        CaraMasuk = new widget.ComboBox();
        jLabel22 = new widget.Label();
        Transportasi = new widget.ComboBox();
        jLabel23 = new widget.Label();
        AlasanKedatangan = new widget.ComboBox();
        jLabel5 = new widget.Label();
        KdKasus = new widget.TextBox();
        NmKasus = new widget.TextBox();
        btnKasus = new widget.Button();
        KeteranganKedatangan = new widget.TextBox();
        jLabel8 = new widget.Label();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbTriase = new widget.Table();
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
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabel34 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        BtnPrint1 = new widget.Button();
        ScrollHTML = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Triase IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabPilihan.setBackground(new java.awt.Color(255, 255, 254));
        TabPilihan.setForeground(new java.awt.Color(50, 50, 50));
        TabPilihan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabPilihan.setName("TabPilihan"); // NOI18N
        TabPilihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPilihanMouseClicked(evt);
            }
        });

        ScrollTriase.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollTriase.setName("ScrollTriase"); // NOI18N
        ScrollTriase.setOpaque(true);

        FormTriase.setBorder(null);
        FormTriase.setName("FormTriase"); // NOI18N
        FormTriase.setLayout(new java.awt.BorderLayout(1, 1));

        TabTriase.setBackground(new java.awt.Color(255, 255, 254));
        TabTriase.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        TabTriase.setForeground(new java.awt.Color(50, 50, 50));
        TabTriase.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabTriase.setName("TabTriase"); // NOI18N
        TabTriase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabTriaseMouseClicked(evt);
            }
        });

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout());

        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setPreferredSize(new java.awt.Dimension(0, 104));
        internalFrame7.setLayout(null);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        PrimerKeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PrimerKeluhanUtama.setColumns(20);
        PrimerKeluhanUtama.setRows(5);
        PrimerKeluhanUtama.setName("PrimerKeluhanUtama"); // NOI18N
        PrimerKeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerKeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(PrimerKeluhanUtama);

        internalFrame7.add(scrollPane1);
        scrollPane1.setBounds(97, 10, 320, 53);

        jLabel9.setText("Keluhan Utama :");
        jLabel9.setName("jLabel9"); // NOI18N
        internalFrame7.add(jLabel9);
        jLabel9.setBounds(0, 10, 93, 23);

        jLabel24.setText("Kebutuhan Khusus :");
        jLabel24.setName("jLabel24"); // NOI18N
        internalFrame7.add(jLabel24);
        jLabel24.setBounds(360, 70, 119, 23);

        PrimerKubutuhanKusus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "UPPA", "Airborne", "Dekontaminan" }));
        PrimerKubutuhanKusus.setName("PrimerKubutuhanKusus"); // NOI18N
        PrimerKubutuhanKusus.setPreferredSize(new java.awt.Dimension(55, 28));
        PrimerKubutuhanKusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerKubutuhanKususKeyPressed(evt);
            }
        });
        internalFrame7.add(PrimerKubutuhanKusus);
        PrimerKubutuhanKusus.setBounds(483, 70, 202, 23);

        jLabel10.setText("Suhu (C) :");
        jLabel10.setName("jLabel10"); // NOI18N
        internalFrame7.add(jLabel10);
        jLabel10.setBounds(420, 10, 59, 23);

        PrimerSuhu.setFocusTraversalPolicyProvider(true);
        PrimerSuhu.setName("PrimerSuhu"); // NOI18N
        PrimerSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerSuhuKeyPressed(evt);
            }
        });
        internalFrame7.add(PrimerSuhu);
        PrimerSuhu.setBounds(483, 10, 55, 23);

        jLabel16.setText("Saturasi O²(%) :");
        jLabel16.setName("jLabel16"); // NOI18N
        internalFrame7.add(jLabel16);
        jLabel16.setBounds(0, 70, 93, 23);

        PrimerSaturasi.setFocusTraversalPolicyProvider(true);
        PrimerSaturasi.setName("PrimerSaturasi"); // NOI18N
        PrimerSaturasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerSaturasiKeyPressed(evt);
            }
        });
        internalFrame7.add(PrimerSaturasi);
        PrimerSaturasi.setBounds(97, 70, 55, 23);

        jLabel25.setText("Nyeri :");
        jLabel25.setName("jLabel25"); // NOI18N
        internalFrame7.add(jLabel25);
        jLabel25.setBounds(547, 10, 79, 23);

        PrimerNyeri.setHighlighter(null);
        PrimerNyeri.setName("PrimerNyeri"); // NOI18N
        PrimerNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerNyeriKeyPressed(evt);
            }
        });
        internalFrame7.add(PrimerNyeri);
        PrimerNyeri.setBounds(630, 10, 55, 23);

        PrimerNadi.setFocusTraversalPolicyProvider(true);
        PrimerNadi.setName("PrimerNadi"); // NOI18N
        PrimerNadi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrimerNadiActionPerformed(evt);
            }
        });
        PrimerNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerNadiKeyPressed(evt);
            }
        });
        internalFrame7.add(PrimerNadi);
        PrimerNadi.setBounds(630, 40, 55, 23);

        jLabel26.setText("Nadi(/menit) :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame7.add(jLabel26);
        jLabel26.setBounds(547, 40, 79, 23);

        PrimerRespirasi.setHighlighter(null);
        PrimerRespirasi.setName("PrimerRespirasi"); // NOI18N
        PrimerRespirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerRespirasiKeyPressed(evt);
            }
        });
        internalFrame7.add(PrimerRespirasi);
        PrimerRespirasi.setBounds(284, 70, 55, 23);

        jLabel27.setText("Respirasi(/menit) :");
        jLabel27.setName("jLabel27"); // NOI18N
        internalFrame7.add(jLabel27);
        jLabel27.setBounds(180, 70, 100, 23);

        PrimerTensi.setHighlighter(null);
        PrimerTensi.setName("PrimerTensi"); // NOI18N
        PrimerTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerTensiKeyPressed(evt);
            }
        });
        internalFrame7.add(PrimerTensi);
        PrimerTensi.setBounds(483, 40, 55, 23);

        jLabel11.setText("Tensi :");
        jLabel11.setName("jLabel11"); // NOI18N
        internalFrame7.add(jLabel11);
        jLabel11.setBounds(420, 40, 59, 23);

        internalFrame5.add(internalFrame7, java.awt.BorderLayout.PAGE_START);

        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setPreferredSize(new java.awt.Dimension(0, 74));
        internalFrame8.setLayout(null);

        jLabel12.setText("Dokter/Petugas IGD :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame8.add(jLabel12);
        jLabel12.setBounds(212, 40, 120, 23);

        PrimerKodePetugas.setEditable(false);
        PrimerKodePetugas.setHighlighter(null);
        PrimerKodePetugas.setName("PrimerKodePetugas"); // NOI18N
        PrimerKodePetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerKodePetugasKeyPressed(evt);
            }
        });
        internalFrame8.add(PrimerKodePetugas);
        PrimerKodePetugas.setBounds(336, 40, 110, 23);

        PrimerNamaPetugas.setEditable(false);
        PrimerNamaPetugas.setName("PrimerNamaPetugas"); // NOI18N
        PrimerNamaPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerNamaPetugasKeyPressed(evt);
            }
        });
        internalFrame8.add(PrimerNamaPetugas);
        PrimerNamaPetugas.setBounds(448, 40, 210, 23);

        btnPrimerPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPrimerPetugas.setMnemonic('1');
        btnPrimerPetugas.setToolTipText("Alt+1");
        btnPrimerPetugas.setName("btnPrimerPetugas"); // NOI18N
        btnPrimerPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimerPetugasActionPerformed(evt);
            }
        });
        btnPrimerPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPrimerPetugasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnPrimerPetugasKeyReleased(evt);
            }
        });
        internalFrame8.add(btnPrimerPetugas);
        btnPrimerPetugas.setBounds(660, 40, 28, 23);

        jLabel28.setText("Plan/Keputusan :");
        jLabel28.setName("jLabel28"); // NOI18N
        internalFrame8.add(jLabel28);
        jLabel28.setBounds(362, 10, 90, 23);

        PrimerTanggalTriase.setForeground(new java.awt.Color(50, 70, 50));
        PrimerTanggalTriase.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-11-2021 16:46:58" }));
        PrimerTanggalTriase.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        PrimerTanggalTriase.setName("PrimerTanggalTriase"); // NOI18N
        PrimerTanggalTriase.setOpaque(false);
        PrimerTanggalTriase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerTanggalTriaseKeyPressed(evt);
            }
        });
        internalFrame8.add(PrimerTanggalTriase);
        PrimerTanggalTriase.setBounds(69, 40, 135, 23);

        jLabel29.setText("Tgl.Triase :");
        jLabel29.setName("jLabel29"); // NOI18N
        internalFrame8.add(jLabel29);
        jLabel29.setBounds(0, 40, 65, 23);

        buttonGroup1.add(PrimerResusitasi);
        PrimerResusitasi.setForeground(new java.awt.Color(170, 0, 0));
        PrimerResusitasi.setSelected(true);
        PrimerResusitasi.setText("Ruang Resusitasi");
        PrimerResusitasi.setName("PrimerResusitasi"); // NOI18N
        PrimerResusitasi.setPreferredSize(new java.awt.Dimension(40, 20));
        internalFrame8.add(PrimerResusitasi);
        PrimerResusitasi.setBounds(456, 10, 120, 23);

        buttonGroup1.add(PrimerKritis);
        PrimerKritis.setForeground(new java.awt.Color(250, 0, 0));
        PrimerKritis.setText("Ruang Kritis");
        PrimerKritis.setName("PrimerKritis"); // NOI18N
        internalFrame8.add(PrimerKritis);
        PrimerKritis.setBounds(590, 10, 100, 23);

        label14.setText("Catatan :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(60, 23));
        internalFrame8.add(label14);
        label14.setBounds(0, 10, 65, 23);

        PrimerCatatan.setToolTipText("Alt+C");
        PrimerCatatan.setName("PrimerCatatan"); // NOI18N
        PrimerCatatan.setPreferredSize(new java.awt.Dimension(140, 23));
        PrimerCatatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrimerCatatanKeyPressed(evt);
            }
        });
        internalFrame8.add(PrimerCatatan);
        PrimerCatatan.setBounds(69, 10, 290, 23);

        internalFrame5.add(internalFrame8, java.awt.BorderLayout.PAGE_END);

        internalFrame3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4));
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame9.setBorder(null);
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setPreferredSize(new java.awt.Dimension(691, 74));
        internalFrame9.setLayout(new java.awt.BorderLayout(2, 1));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(280, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi5.add(label10);

        TCariPemeriksaan.setToolTipText("Alt+C");
        TCariPemeriksaan.setName("TCariPemeriksaan"); // NOI18N
        TCariPemeriksaan.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPemeriksaanKeyPressed(evt);
            }
        });
        panelisi5.add(TCariPemeriksaan);

        BtnCariPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPemeriksaan.setMnemonic('1');
        BtnCariPemeriksaan.setToolTipText("Alt+1");
        BtnCariPemeriksaan.setName("BtnCariPemeriksaan"); // NOI18N
        BtnCariPemeriksaan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPemeriksaanActionPerformed(evt);
            }
        });
        BtnCariPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariPemeriksaanKeyPressed(evt);
            }
        });
        panelisi5.add(BtnCariPemeriksaan);

        BtnTambahPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahPemeriksaan.setMnemonic('3');
        BtnTambahPemeriksaan.setToolTipText("Alt+3");
        BtnTambahPemeriksaan.setName("BtnTambahPemeriksaan"); // NOI18N
        BtnTambahPemeriksaan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahPemeriksaanActionPerformed(evt);
            }
        });
        panelisi5.add(BtnTambahPemeriksaan);

        jPanel3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        tbPemeriksaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanMouseClicked(evt);
            }
        });
        tbPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyReleased(evt);
            }
        });
        Scroll2.setViewportView(tbPemeriksaan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        internalFrame9.add(jPanel3, java.awt.BorderLayout.WEST);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(295, 102));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setForeground(new java.awt.Color(170, 0, 0));
        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi6.add(label11);

        TCariSkala1.setForeground(new java.awt.Color(170, 0, 0));
        TCariSkala1.setToolTipText("Alt+C");
        TCariSkala1.setName("TCariSkala1"); // NOI18N
        TCariSkala1.setPreferredSize(new java.awt.Dimension(200, 23));
        TCariSkala1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariSkala1KeyPressed(evt);
            }
        });
        panelisi6.add(TCariSkala1);

        BtnCariSkala1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariSkala1.setMnemonic('1');
        BtnCariSkala1.setToolTipText("Alt+1");
        BtnCariSkala1.setName("BtnCariSkala1"); // NOI18N
        BtnCariSkala1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariSkala1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariSkala1ActionPerformed(evt);
            }
        });
        BtnCariSkala1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariSkala1KeyPressed(evt);
            }
        });
        panelisi6.add(BtnCariSkala1);

        BtnTambahSkala1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahSkala1.setMnemonic('3');
        BtnTambahSkala1.setToolTipText("Alt+3");
        BtnTambahSkala1.setName("BtnTambahSkala1"); // NOI18N
        BtnTambahSkala1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahSkala1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahSkala1ActionPerformed(evt);
            }
        });
        panelisi6.add(BtnTambahSkala1);

        jPanel4.add(panelisi6, java.awt.BorderLayout.PAGE_END);

        TabSkala1dan2.setBackground(new java.awt.Color(255, 255, 254));
        TabSkala1dan2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TabSkala1dan2.setForeground(new java.awt.Color(50, 50, 50));
        TabSkala1dan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabSkala1dan2.setName("TabSkala1dan2"); // NOI18N
        TabSkala1dan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabSkala1dan2MouseClicked(evt);
            }
        });

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll3.setForeground(new java.awt.Color(170, 0, 0));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbSkala1.setForeground(new java.awt.Color(170, 0, 0));
        tbSkala1.setName("tbSkala1"); // NOI18N
        tbSkala1.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll3.setViewportView(tbSkala1);

        TabSkala1dan2.addTab("Skala 1", Scroll3);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbSkala2.setForeground(new java.awt.Color(255, 0, 0));
        tbSkala2.setName("tbSkala2"); // NOI18N
        tbSkala2.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll4.setViewportView(tbSkala2);

        TabSkala1dan2.addTab("Skala 2", Scroll4);

        jPanel4.add(TabSkala1dan2, java.awt.BorderLayout.CENTER);

        internalFrame9.add(jPanel4, java.awt.BorderLayout.CENTER);

        internalFrame3.add(internalFrame9, java.awt.BorderLayout.WEST);

        internalFrame5.add(internalFrame3, java.awt.BorderLayout.CENTER);

        TabTriase.addTab("Triase Primer", internalFrame5);

        internalFrame10.setBorder(null);
        internalFrame10.setName("internalFrame10"); // NOI18N
        internalFrame10.setLayout(new java.awt.BorderLayout());

        internalFrame11.setBorder(null);
        internalFrame11.setName("internalFrame11"); // NOI18N
        internalFrame11.setPreferredSize(new java.awt.Dimension(0, 104));
        internalFrame11.setLayout(null);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        SekunderAnamnesa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        SekunderAnamnesa.setColumns(20);
        SekunderAnamnesa.setRows(5);
        SekunderAnamnesa.setName("SekunderAnamnesa"); // NOI18N
        SekunderAnamnesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderAnamnesaKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(SekunderAnamnesa);

        internalFrame11.add(scrollPane2);
        scrollPane2.setBounds(114, 10, 440, 53);

        jLabel13.setText("Anamnesa Singkat :");
        jLabel13.setName("jLabel13"); // NOI18N
        internalFrame11.add(jLabel13);
        jLabel13.setBounds(0, 10, 110, 23);

        jLabel14.setText("Suhu (C) :");
        jLabel14.setName("jLabel14"); // NOI18N
        internalFrame11.add(jLabel14);
        jLabel14.setBounds(547, 10, 79, 23);

        SekunderSuhu.setFocusTraversalPolicyProvider(true);
        SekunderSuhu.setName("SekunderSuhu"); // NOI18N
        SekunderSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderSuhuKeyPressed(evt);
            }
        });
        internalFrame11.add(SekunderSuhu);
        SekunderSuhu.setBounds(630, 10, 55, 23);

        jLabel17.setText("Saturasi O²(%) :");
        jLabel17.setName("jLabel17"); // NOI18N
        internalFrame11.add(jLabel17);
        jLabel17.setBounds(0, 70, 110, 23);

        SekunderSaturasi.setFocusTraversalPolicyProvider(true);
        SekunderSaturasi.setName("SekunderSaturasi"); // NOI18N
        SekunderSaturasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderSaturasiKeyPressed(evt);
            }
        });
        internalFrame11.add(SekunderSaturasi);
        SekunderSaturasi.setBounds(114, 70, 55, 23);

        jLabel31.setText("Nyeri :");
        jLabel31.setName("jLabel31"); // NOI18N
        internalFrame11.add(jLabel31);
        jLabel31.setBounds(547, 40, 79, 23);

        SekunderNyeri.setHighlighter(null);
        SekunderNyeri.setName("SekunderNyeri"); // NOI18N
        SekunderNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderNyeriKeyPressed(evt);
            }
        });
        internalFrame11.add(SekunderNyeri);
        SekunderNyeri.setBounds(630, 40, 55, 23);

        SekunderNadi.setFocusTraversalPolicyProvider(true);
        SekunderNadi.setName("SekunderNadi"); // NOI18N
        SekunderNadi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SekunderNadiActionPerformed(evt);
            }
        });
        SekunderNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderNadiKeyPressed(evt);
            }
        });
        internalFrame11.add(SekunderNadi);
        SekunderNadi.setBounds(630, 70, 55, 23);

        jLabel32.setText("Nadi(/menit) :");
        jLabel32.setName("jLabel32"); // NOI18N
        internalFrame11.add(jLabel32);
        jLabel32.setBounds(547, 70, 79, 23);

        SekunderRespirasi.setHighlighter(null);
        SekunderRespirasi.setName("SekunderRespirasi"); // NOI18N
        SekunderRespirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderRespirasiKeyPressed(evt);
            }
        });
        internalFrame11.add(SekunderRespirasi);
        SekunderRespirasi.setBounds(309, 70, 55, 23);

        jLabel33.setText("Respirasi(/menit) :");
        jLabel33.setName("jLabel33"); // NOI18N
        internalFrame11.add(jLabel33);
        jLabel33.setBounds(205, 70, 100, 23);

        SekunderTensi.setHighlighter(null);
        SekunderTensi.setName("SekunderTensi"); // NOI18N
        SekunderTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderTensiKeyPressed(evt);
            }
        });
        internalFrame11.add(SekunderTensi);
        SekunderTensi.setBounds(458, 70, 55, 23);

        jLabel15.setText("Tensi :");
        jLabel15.setName("jLabel15"); // NOI18N
        internalFrame11.add(jLabel15);
        jLabel15.setBounds(395, 70, 59, 23);

        internalFrame10.add(internalFrame11, java.awt.BorderLayout.PAGE_START);

        internalFrame12.setBorder(null);
        internalFrame12.setName("internalFrame12"); // NOI18N
        internalFrame12.setPreferredSize(new java.awt.Dimension(0, 74));
        internalFrame12.setLayout(null);

        jLabel30.setText("Dokter/Petugas IGD :");
        jLabel30.setName("jLabel30"); // NOI18N
        internalFrame12.add(jLabel30);
        jLabel30.setBounds(202, 40, 130, 23);

        SekunderKodePetugas.setEditable(false);
        SekunderKodePetugas.setHighlighter(null);
        SekunderKodePetugas.setName("SekunderKodePetugas"); // NOI18N
        SekunderKodePetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderKodePetugasKeyPressed(evt);
            }
        });
        internalFrame12.add(SekunderKodePetugas);
        SekunderKodePetugas.setBounds(336, 40, 110, 23);

        SekunderNamaPetugas.setEditable(false);
        SekunderNamaPetugas.setName("SekunderNamaPetugas"); // NOI18N
        SekunderNamaPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderNamaPetugasKeyPressed(evt);
            }
        });
        internalFrame12.add(SekunderNamaPetugas);
        SekunderNamaPetugas.setBounds(448, 40, 210, 23);

        btnSekunderPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSekunderPetugas.setMnemonic('1');
        btnSekunderPetugas.setToolTipText("Alt+1");
        btnSekunderPetugas.setName("btnSekunderPetugas"); // NOI18N
        btnSekunderPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSekunderPetugasActionPerformed(evt);
            }
        });
        btnSekunderPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSekunderPetugasKeyPressed(evt);
            }
        });
        internalFrame12.add(btnSekunderPetugas);
        btnSekunderPetugas.setBounds(660, 40, 28, 23);

        jLabel37.setText("Plan/Keputusan :");
        jLabel37.setName("jLabel37"); // NOI18N
        internalFrame12.add(jLabel37);
        jLabel37.setBounds(362, 10, 90, 23);

        SekunderTanggalTriase.setForeground(new java.awt.Color(50, 70, 50));
        SekunderTanggalTriase.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-11-2021 16:46:59" }));
        SekunderTanggalTriase.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        SekunderTanggalTriase.setName("SekunderTanggalTriase"); // NOI18N
        SekunderTanggalTriase.setOpaque(false);
        SekunderTanggalTriase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderTanggalTriaseKeyPressed(evt);
            }
        });
        internalFrame12.add(SekunderTanggalTriase);
        SekunderTanggalTriase.setBounds(69, 40, 135, 23);

        jLabel38.setText("Tgl.Triase :");
        jLabel38.setName("jLabel38"); // NOI18N
        internalFrame12.add(jLabel38);
        jLabel38.setBounds(0, 40, 65, 23);

        buttonGroup1.add(SekunderZonaKuning);
        SekunderZonaKuning.setForeground(new java.awt.Color(200, 200, 0));
        SekunderZonaKuning.setText("Zona Kuning");
        SekunderZonaKuning.setName("SekunderZonaKuning"); // NOI18N
        SekunderZonaKuning.setPreferredSize(new java.awt.Dimension(40, 20));
        internalFrame12.add(SekunderZonaKuning);
        SekunderZonaKuning.setBounds(456, 10, 140, 23);

        buttonGroup1.add(SekunderZonaHijau);
        SekunderZonaHijau.setForeground(new java.awt.Color(0, 170, 0));
        SekunderZonaHijau.setText("Zona Hijau");
        SekunderZonaHijau.setName("SekunderZonaHijau"); // NOI18N
        internalFrame12.add(SekunderZonaHijau);
        SekunderZonaHijau.setBounds(590, 10, 140, 23);

        label15.setText("Catatan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        internalFrame12.add(label15);
        label15.setBounds(0, 10, 65, 23);

        SekunderCatatan.setToolTipText("Alt+C");
        SekunderCatatan.setName("SekunderCatatan"); // NOI18N
        SekunderCatatan.setPreferredSize(new java.awt.Dimension(140, 23));
        SekunderCatatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SekunderCatatanKeyPressed(evt);
            }
        });
        internalFrame12.add(SekunderCatatan);
        SekunderCatatan.setBounds(69, 10, 290, 23);

        internalFrame10.add(internalFrame12, java.awt.BorderLayout.PAGE_END);

        internalFrame13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4));
        internalFrame13.setName("internalFrame13"); // NOI18N
        internalFrame13.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame14.setBorder(null);
        internalFrame14.setName("internalFrame14"); // NOI18N
        internalFrame14.setPreferredSize(new java.awt.Dimension(691, 74));
        internalFrame14.setLayout(new java.awt.BorderLayout(2, 1));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(280, 102));
        jPanel5.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi7.setBorder(null);
        panelisi7.setName("panelisi7"); // NOI18N
        panelisi7.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi7.add(label12);

        TCariPemeriksaan2.setToolTipText("Alt+C");
        TCariPemeriksaan2.setName("TCariPemeriksaan2"); // NOI18N
        TCariPemeriksaan2.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariPemeriksaan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPemeriksaan2KeyPressed(evt);
            }
        });
        panelisi7.add(TCariPemeriksaan2);

        BtnCariPemeriksaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPemeriksaan1.setMnemonic('1');
        BtnCariPemeriksaan1.setToolTipText("Alt+1");
        BtnCariPemeriksaan1.setName("BtnCariPemeriksaan1"); // NOI18N
        BtnCariPemeriksaan1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPemeriksaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPemeriksaan1ActionPerformed(evt);
            }
        });
        BtnCariPemeriksaan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariPemeriksaan1KeyPressed(evt);
            }
        });
        panelisi7.add(BtnCariPemeriksaan1);

        BtnTambahPemeriksaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahPemeriksaan1.setMnemonic('3');
        BtnTambahPemeriksaan1.setToolTipText("Alt+3");
        BtnTambahPemeriksaan1.setName("BtnTambahPemeriksaan1"); // NOI18N
        BtnTambahPemeriksaan1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahPemeriksaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahPemeriksaan1ActionPerformed(evt);
            }
        });
        panelisi7.add(BtnTambahPemeriksaan1);

        jPanel5.add(panelisi7, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbPemeriksaan2.setName("tbPemeriksaan2"); // NOI18N
        tbPemeriksaan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaan2MouseClicked(evt);
            }
        });
        tbPemeriksaan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemeriksaan2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaan2KeyReleased(evt);
            }
        });
        Scroll5.setViewportView(tbPemeriksaan2);

        jPanel5.add(Scroll5, java.awt.BorderLayout.CENTER);

        internalFrame14.add(jPanel5, java.awt.BorderLayout.WEST);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(295, 102));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 1));

        panelisi8.setBorder(null);
        panelisi8.setName("panelisi8"); // NOI18N
        panelisi8.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label13.setForeground(new java.awt.Color(200, 200, 0));
        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi8.add(label13);

        TCariSkala3.setForeground(new java.awt.Color(200, 200, 0));
        TCariSkala3.setToolTipText("Alt+C");
        TCariSkala3.setName("TCariSkala3"); // NOI18N
        TCariSkala3.setPreferredSize(new java.awt.Dimension(200, 23));
        TCariSkala3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariSkala3KeyPressed(evt);
            }
        });
        panelisi8.add(TCariSkala3);

        BtnCariSkala2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariSkala2.setMnemonic('1');
        BtnCariSkala2.setToolTipText("Alt+1");
        BtnCariSkala2.setName("BtnCariSkala2"); // NOI18N
        BtnCariSkala2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariSkala2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariSkala2ActionPerformed(evt);
            }
        });
        BtnCariSkala2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariSkala2KeyPressed(evt);
            }
        });
        panelisi8.add(BtnCariSkala2);

        BtnTambahSkala2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahSkala2.setMnemonic('3');
        BtnTambahSkala2.setToolTipText("Alt+3");
        BtnTambahSkala2.setName("BtnTambahSkala2"); // NOI18N
        BtnTambahSkala2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahSkala2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahSkala2ActionPerformed(evt);
            }
        });
        panelisi8.add(BtnTambahSkala2);

        jPanel6.add(panelisi8, java.awt.BorderLayout.PAGE_END);

        TabSkala3dan4dan5.setBackground(new java.awt.Color(255, 255, 254));
        TabSkala3dan4dan5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TabSkala3dan4dan5.setForeground(new java.awt.Color(50, 50, 50));
        TabSkala3dan4dan5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabSkala3dan4dan5.setName("TabSkala3dan4dan5"); // NOI18N
        TabSkala3dan4dan5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabSkala3dan4dan5MouseClicked(evt);
            }
        });

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbSkala3.setForeground(new java.awt.Color(200, 200, 0));
        tbSkala3.setName("tbSkala3"); // NOI18N
        tbSkala3.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll6.setViewportView(tbSkala3);

        TabSkala3dan4dan5.addTab("Skala 3", Scroll6);

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbSkala4.setForeground(new java.awt.Color(0, 170, 0));
        tbSkala4.setName("tbSkala4"); // NOI18N
        tbSkala4.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll7.setViewportView(tbSkala4);

        TabSkala3dan4dan5.addTab("Skala 4", Scroll7);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbSkala5.setForeground(new java.awt.Color(150, 150, 150));
        tbSkala5.setName("tbSkala5"); // NOI18N
        tbSkala5.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll8.setViewportView(tbSkala5);

        TabSkala3dan4dan5.addTab("Skala 5", Scroll8);

        jPanel6.add(TabSkala3dan4dan5, java.awt.BorderLayout.CENTER);

        internalFrame14.add(jPanel6, java.awt.BorderLayout.CENTER);

        internalFrame13.add(internalFrame14, java.awt.BorderLayout.WEST);

        internalFrame10.add(internalFrame13, java.awt.BorderLayout.CENTER);

        TabTriase.addTab("Triase Sekunder", internalFrame10);

        FormTriase.add(TabTriase, java.awt.BorderLayout.CENTER);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 104));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 89, 23);

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(93, 10, 122, 23);

        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(289, 10, 225, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 70, 23);

        jLabel18.setText("Tgl.Kunjungan :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 40, 89, 23);

        TanggalKunjungan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-11-2021 16:47:00" }));
        TanggalKunjungan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalKunjungan.setName("TanggalKunjungan"); // NOI18N
        TanggalKunjungan.setOpaque(false);
        TanggalKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKunjunganKeyPressed(evt);
            }
        });
        FormInput.add(TanggalKunjungan);
        TanggalKunjungan.setBounds(93, 40, 135, 23);

        jLabel20.setText("Cara Masuk :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(236, 40, 80, 23);

        CaraMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jalan", "Brankar", "Kursi Roda", "Digendong" }));
        CaraMasuk.setName("CaraMasuk"); // NOI18N
        CaraMasuk.setPreferredSize(new java.awt.Dimension(55, 28));
        CaraMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraMasukKeyPressed(evt);
            }
        });
        FormInput.add(CaraMasuk);
        CaraMasuk.setBounds(320, 40, 110, 23);

        jLabel22.setText("Transportasi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(531, 10, 80, 23);

        Transportasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "AGD", "Sendiri", "Swasta" }));
        Transportasi.setName("Transportasi"); // NOI18N
        Transportasi.setPreferredSize(new java.awt.Dimension(55, 28));
        Transportasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransportasiKeyPressed(evt);
            }
        });
        FormInput.add(Transportasi);
        Transportasi.setBounds(615, 10, 100, 23);

        jLabel23.setText("Alasan Kedatangan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(431, 40, 130, 23);

        AlasanKedatangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Datang Sendiri", "Polisi", "Rujukan", "-" }));
        AlasanKedatangan.setName("AlasanKedatangan"); // NOI18N
        AlasanKedatangan.setPreferredSize(new java.awt.Dimension(55, 28));
        AlasanKedatangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanKedatanganKeyPressed(evt);
            }
        });
        FormInput.add(AlasanKedatangan);
        AlasanKedatangan.setBounds(565, 40, 150, 23);

        jLabel5.setText("Macam Kasus :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 70, 89, 23);

        KdKasus.setEditable(false);
        KdKasus.setHighlighter(null);
        KdKasus.setName("KdKasus"); // NOI18N
        KdKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKasusKeyPressed(evt);
            }
        });
        FormInput.add(KdKasus);
        KdKasus.setBounds(93, 70, 50, 23);

        NmKasus.setEditable(false);
        NmKasus.setName("NmKasus"); // NOI18N
        NmKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKasusKeyPressed(evt);
            }
        });
        FormInput.add(NmKasus);
        NmKasus.setBounds(145, 70, 250, 23);

        btnKasus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKasus.setMnemonic('1');
        btnKasus.setToolTipText("Alt+1");
        btnKasus.setName("btnKasus"); // NOI18N
        btnKasus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKasusActionPerformed(evt);
            }
        });
        btnKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKasusKeyPressed(evt);
            }
        });
        FormInput.add(btnKasus);
        btnKasus.setBounds(397, 70, 28, 23);

        KeteranganKedatangan.setName("KeteranganKedatangan"); // NOI18N
        KeteranganKedatangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKedatanganKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKedatangan);
        KeteranganKedatangan.setBounds(520, 70, 195, 23);

        jLabel8.setText("Keterangan  :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(430, 70, 86, 23);

        FormTriase.add(FormInput, java.awt.BorderLayout.PAGE_START);

        ScrollTriase.setViewportView(FormTriase);

        TabPilihan.addTab("Input Triase", ScrollTriase);

        internalFrame4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbTriase.setAutoCreateRowSorter(true);
        tbTriase.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTriase.setName("tbTriase"); // NOI18N
        tbTriase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTriaseMouseClicked(evt);
            }
        });
        tbTriase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTriaseKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbTriaseKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbTriase);

        internalFrame4.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Triase :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-11-2021" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-11-2021" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(470, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormMenu.setBackground(new java.awt.Color(255, 255, 255));
        FormMenu.setBorder(null);
        FormMenu.setName("FormMenu"); // NOI18N
        FormMenu.setPreferredSize(new java.awt.Dimension(115, 43));
        FormMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel34.setText("Pasien :");
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setPreferredSize(new java.awt.Dimension(55, 23));
        FormMenu.add(jLabel34);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(100, 23));
        FormMenu.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setBackground(new java.awt.Color(245, 250, 240));
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(250, 23));
        FormMenu.add(TPasien1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormMenu.add(BtnPrint1);

        PanelAccor.add(FormMenu, java.awt.BorderLayout.NORTH);

        ScrollHTML.setBorder(null);
        ScrollHTML.setName("ScrollHTML"); // NOI18N
        ScrollHTML.setOpaque(true);
        ScrollHTML.setPreferredSize(new java.awt.Dimension(470, 16));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        ScrollHTML.setViewportView(LoadHTML);

        PanelAccor.add(ScrollHTML, java.awt.BorderLayout.CENTER);

        internalFrame4.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabPilihan.addTab("Data Triase", internalFrame4);

        TabPilihan.setSelectedIndex(1);

        internalFrame1.add(TabPilihan, java.awt.BorderLayout.CENTER);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdKasus.getText().trim().equals("")||NmKasus.getText().trim().equals("")){
            Valid.textKosong(btnKasus,"Macam Kasus");
        }else if(KeteranganKedatangan.equals("")){
            Valid.textKosong(btnKasus,"Keterangan");
        }else{
            if(TabTriase.getSelectedIndex()==0){
                jmlskala1=0;jmlskala2=0;
                for(i=0;i<tbSkala1.getRowCount();i++){ 
                    if(tbSkala1.getValueAt(i,0).toString().equals("true")){
                        jmlskala1++;
                    }
                }
                for(i=0;i<tbSkala2.getRowCount();i++){ 
                    if(tbSkala2.getValueAt(i,0).toString().equals("true")){
                        jmlskala2++;
                    }
                }
                if(PrimerKeluhanUtama.getText().trim().equals("")){
                    Valid.textKosong(PrimerKeluhanUtama,"Keluhan Utama");
                }else if(PrimerSuhu.getText().trim().equals("")){
                    Valid.textKosong(PrimerSuhu,"Suhu");
                }else if(PrimerNyeri.getText().trim().equals("")){
                    Valid.textKosong(PrimerNyeri,"Nyeri");
                }else if(PrimerTensi.getText().trim().equals("")){
                    Valid.textKosong(PrimerTensi,"Tensi");
                }else if(PrimerNadi.getText().trim().equals("")){
                    Valid.textKosong(PrimerNadi,"Nadi");
                }else if(PrimerSaturasi.getText().trim().equals("")){
                    Valid.textKosong(PrimerSaturasi,"Saturasi O²");
                }else if(PrimerRespirasi.getText().trim().equals("")){
                    Valid.textKosong(PrimerRespirasi,"Respirasi");
                }else if(PrimerCatatan.getText().trim().equals("")){
                    Valid.textKosong(PrimerCatatan,"Catatan");
                }else if(PrimerKodePetugas.getText().trim().equals("")||PrimerNamaPetugas.getText().trim().equals("")){
                    Valid.textKosong(btnPrimerPetugas,"Dokter/Petugas Triase");
                }else if((jmlskala1==0)&&(jmlskala2==0)){
                    Valid.textKosong(TCariPemeriksaan,"Skala 1 / Skala 2");
                }else{
                    if(Sequel.menyimpantf("data_triase_igd","?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",13,new String[]{
                            TNoRw.getText(),Valid.SetTgl(TanggalKunjungan.getSelectedItem()+"")+" "+TanggalKunjungan.getSelectedItem().toString().substring(11,19),
                            CaraMasuk.getSelectedItem().toString(),Transportasi.getSelectedItem().toString(),AlasanKedatangan.getSelectedItem().toString(), 
                            KeteranganKedatangan.getText(),KdKasus.getText(),PrimerTensi.getText(),PrimerNadi.getText(),PrimerRespirasi.getText(),PrimerSuhu.getText(),
                            PrimerSaturasi.getText(),PrimerNyeri.getText()
                        })==true){
                        if(PrimerResusitasi.isSelected()==true){
                            keputusan="Ruang Resusitasi";
                        }else if(PrimerKritis.isSelected()==true){
                            keputusan="Ruang Kritis";
                        }
                        Sequel.menyimpan("data_triase_igdprimer","?,?,?,?,?,?,?", 7,new String[]{
                            TNoRw.getText(),PrimerKeluhanUtama.getText(),PrimerKubutuhanKusus.getSelectedItem().toString(),PrimerCatatan.getText(),keputusan,
                            Valid.SetTgl(PrimerTanggalTriase.getSelectedItem()+"")+" "+PrimerTanggalTriase.getSelectedItem().toString().substring(11,19), 
                            PrimerKodePetugas.getText()
                        });
                        if(TabSkala1dan2.getSelectedIndex()==0){
                            for(i=0;i<tbSkala1.getRowCount();i++){ 
                                if(tbSkala1.getValueAt(i,0).toString().equals("true")){
                                    if(Sequel.menyimpantf2("data_triase_igddetail_skala1","?,?","Skala 1",2,new String[]{
                                            TNoRw.getText(),tbSkala1.getValueAt(i,1).toString()
                                        })==true){
                                            tbSkala1.setValueAt(false,i,0);
                                    }
                                }
                            }
                        }else if(TabSkala1dan2.getSelectedIndex()==1){
                            for(i=0;i<tbSkala2.getRowCount();i++){ 
                                if(tbSkala2.getValueAt(i,0).toString().equals("true")){
                                    if(Sequel.menyimpantf2("data_triase_igddetail_skala2","?,?","Skala 2",2,new String[]{
                                            TNoRw.getText(),tbSkala2.getValueAt(i,1).toString()
                                        })==true){
                                            tbSkala2.setValueAt(false,i,0);
                                    }
                                }
                            }
                        }
                        emptTeks();
                    }
                }
            }else if(TabTriase.getSelectedIndex()==1){
                jmlskala3=0;jmlskala4=0;jmlskala5=0;
                for(i=0;i<tbSkala3.getRowCount();i++){ 
                    if(tbSkala3.getValueAt(i,0).toString().equals("true")){
                        jmlskala3++;
                    }
                }
                for(i=0;i<tbSkala4.getRowCount();i++){ 
                    if(tbSkala4.getValueAt(i,0).toString().equals("true")){
                        jmlskala4++;
                    }
                }
                for(i=0;i<tbSkala5.getRowCount();i++){ 
                    if(tbSkala5.getValueAt(i,0).toString().equals("true")){
                        jmlskala5++;
                    }
                }
                if(SekunderAnamnesa.getText().trim().equals("")){
                    Valid.textKosong(SekunderAnamnesa,"Anamnesa");
                }else if(SekunderSuhu.getText().trim().equals("")){
                    Valid.textKosong(SekunderSuhu,"Suhu");
                }else if(SekunderNyeri.getText().trim().equals("")){
                    Valid.textKosong(SekunderNyeri,"Nyeri");
                }else if(SekunderTensi.getText().trim().equals("")){
                    Valid.textKosong(SekunderTensi,"Tensi");
                }else if(SekunderNadi.getText().trim().equals("")){
                    Valid.textKosong(SekunderNadi,"Nadi");
                }else if(SekunderSaturasi.getText().trim().equals("")){
                    Valid.textKosong(SekunderSaturasi,"Saturasi O²");
                }else if(SekunderRespirasi.getText().trim().equals("")){
                    Valid.textKosong(SekunderRespirasi,"Respirasi");
                }else if(SekunderCatatan.getText().trim().equals("")){
                    Valid.textKosong(SekunderCatatan,"Catatan");
                }else if(SekunderKodePetugas.getText().trim().equals("")||SekunderNamaPetugas.getText().trim().equals("")){
                    Valid.textKosong(btnSekunderPetugas,"Dokter Sekunder");
                }else if((jmlskala3==0)&&(jmlskala4==0)&&(jmlskala5==0)){
                    Valid.textKosong(TCariPemeriksaan2,"Skala 3 / Skala 4 / Skala 5");
                }else{
                    if(Sequel.menyimpantf("data_triase_igd","?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",13,new String[]{
                            TNoRw.getText(),Valid.SetTgl(TanggalKunjungan.getSelectedItem()+"")+" "+TanggalKunjungan.getSelectedItem().toString().substring(11,19),
                            CaraMasuk.getSelectedItem().toString(),Transportasi.getSelectedItem().toString(),AlasanKedatangan.getSelectedItem().toString(), 
                            KeteranganKedatangan.getText(),KdKasus.getText(),SekunderTensi.getText(),SekunderNadi.getText(),SekunderRespirasi.getText(),SekunderSuhu.getText(),
                            SekunderSaturasi.getText(),SekunderNyeri.getText()
                        })==true){
                        if(SekunderZonaKuning.isSelected()==true){
                            keputusan="Zona Kuning";
                        }else if(SekunderZonaHijau.isSelected()==true){
                            keputusan="Zona Hijau";
                        }
                        Sequel.menyimpan("data_triase_igdsekunder","?,?,?,?,?,?", 6,new String[]{
                            TNoRw.getText(),SekunderAnamnesa.getText(),SekunderCatatan.getText(),keputusan,
                            Valid.SetTgl(SekunderTanggalTriase.getSelectedItem()+"")+" "+SekunderTanggalTriase.getSelectedItem().toString().substring(11,19), 
                            SekunderKodePetugas.getText()
                        });
                        if(TabSkala3dan4dan5.getSelectedIndex()==0){
                            for(i=0;i<tbSkala3.getRowCount();i++){ 
                                if(tbSkala3.getValueAt(i,0).toString().equals("true")){
                                    if(Sequel.menyimpantf2("data_triase_igddetail_skala3","?,?","Skala 3",2,new String[]{
                                            TNoRw.getText(),tbSkala3.getValueAt(i,1).toString()
                                        })==true){
                                            tbSkala3.setValueAt(false,i,0);
                                    }
                                }
                            }
                        }else if(TabSkala3dan4dan5.getSelectedIndex()==1){
                            for(i=0;i<tbSkala4.getRowCount();i++){ 
                                if(tbSkala4.getValueAt(i,0).toString().equals("true")){
                                    if(Sequel.menyimpantf2("data_triase_igddetail_skala4","?,?","Skala 4",2,new String[]{
                                            TNoRw.getText(),tbSkala4.getValueAt(i,1).toString()
                                        })==true){
                                            tbSkala4.setValueAt(false,i,0);
                                    }
                                }
                            }
                        }else if(TabSkala3dan4dan5.getSelectedIndex()==2){
                            for(i=0;i<tbSkala5.getRowCount();i++){ 
                                if(tbSkala5.getValueAt(i,0).toString().equals("true")){
                                    if(Sequel.menyimpantf2("data_triase_igddetail_skala5","?,?","Skala 5",2,new String[]{
                                            TNoRw.getText(),tbSkala5.getValueAt(i,1).toString()
                                        })==true){
                                            tbSkala5.setValueAt(false,i,0);
                                    }
                                }
                            }
                        }
                        emptTeks();
                    }
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(TabTriase.getSelectedIndex()==0){
            Valid.pindah(evt,btnPrimerPetugas,BtnBatal);
        }else if(TabTriase.getSelectedIndex()==1){
            Valid.pindah(evt,btnSekunderPetugas,BtnBatal);
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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbTriase.getSelectedRow()!= -1){
                Sequel.meghapus("data_triase_igd","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igdprimer","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igdsekunder","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala1","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala2","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala3","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala4","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala5","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                tampil();
                TNoRM1.setText("");
                TPasien1.setText("");
                LoadHTML.setText("");
                ChkAccor.setSelected(false);
                isMenu();
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau dihapus terlebih dahulu ...!!!!");
            }
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
        }else if(KdKasus.getText().trim().equals("")||NmKasus.getText().trim().equals("")){
            Valid.textKosong(btnKasus,"Macam Kasus");
        }else if(KeteranganKedatangan.equals("")){
            Valid.textKosong(btnKasus,"Keterangan");
        }else{
            if(tbTriase.getSelectedRow()> -1){
                Sequel.meghapus("data_triase_igdprimer","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala1","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala2","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igdsekunder","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala3","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala4","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                Sequel.meghapus("data_triase_igddetail_skala5","no_rawat",tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                if(TabTriase.getSelectedIndex()==0){
                    jmlskala1=0;jmlskala2=0;
                    for(i=0;i<tbSkala1.getRowCount();i++){ 
                        if(tbSkala1.getValueAt(i,0).toString().equals("true")){
                            jmlskala1++;
                        }
                    }
                    for(i=0;i<tbSkala2.getRowCount();i++){ 
                        if(tbSkala2.getValueAt(i,0).toString().equals("true")){
                            jmlskala2++;
                        }
                    }
                    if(PrimerKeluhanUtama.getText().trim().equals("")){
                        Valid.textKosong(PrimerKeluhanUtama,"Keluhan Utama");
                    }else if(PrimerSuhu.getText().trim().equals("")){
                        Valid.textKosong(PrimerSuhu,"Suhu");
                    }else if(PrimerNyeri.getText().trim().equals("")){
                        Valid.textKosong(PrimerNyeri,"Nyeri");
                    }else if(PrimerTensi.getText().trim().equals("")){
                        Valid.textKosong(PrimerTensi,"Tensi");
                    }else if(PrimerNadi.getText().trim().equals("")){
                        Valid.textKosong(PrimerNadi,"Nadi");
                    }else if(PrimerSaturasi.getText().trim().equals("")){
                        Valid.textKosong(PrimerSaturasi,"Saturasi O²");
                    }else if(PrimerRespirasi.getText().trim().equals("")){
                        Valid.textKosong(PrimerRespirasi,"Respirasi");
                    }else if(PrimerCatatan.getText().trim().equals("")){
                        Valid.textKosong(PrimerCatatan,"Catatan");
                    }else if(PrimerKodePetugas.getText().trim().equals("")||PrimerNamaPetugas.getText().trim().equals("")){
                        Valid.textKosong(btnPrimerPetugas,"Dokter/Petugas Triase");
                    }else if((jmlskala1==0)&&(jmlskala2==0)){
                        Valid.textKosong(TCariPemeriksaan,"Skala 1 / Skala 2");
                    }else{
                        if(Sequel.mengedittf("data_triase_igd","no_rawat=?","no_rawat=?,tgl_kunjungan=?,cara_masuk=?,alat_transportasi=?,alasan_kedatangan=?,keterangan_kedatangan=?,kode_kasus=?,tekanan_darah=?,nadi=?,pernapasan=?,suhu=?,saturasi_o2=?,nyeri=?",14,new String[]{
                                TNoRw.getText(),Valid.SetTgl(TanggalKunjungan.getSelectedItem()+"")+" "+TanggalKunjungan.getSelectedItem().toString().substring(11,19),
                                CaraMasuk.getSelectedItem().toString(),Transportasi.getSelectedItem().toString(),AlasanKedatangan.getSelectedItem().toString(), 
                                KeteranganKedatangan.getText(),KdKasus.getText(),PrimerTensi.getText(),PrimerNadi.getText(),PrimerRespirasi.getText(),PrimerSuhu.getText(),
                                PrimerSaturasi.getText(),PrimerNyeri.getText(),tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString()
                            })==true){
                            if(PrimerResusitasi.isSelected()==true){
                                keputusan="Ruang Resusitasi";
                            }else if(PrimerKritis.isSelected()==true){
                                keputusan="Ruang Kritis";
                            }
                            Sequel.menyimpan("data_triase_igdprimer","?,?,?,?,?,?,?", 7,new String[]{
                                TNoRw.getText(),PrimerKeluhanUtama.getText(),PrimerKubutuhanKusus.getSelectedItem().toString(),PrimerCatatan.getText(),keputusan,
                                Valid.SetTgl(PrimerTanggalTriase.getSelectedItem()+"")+" "+PrimerTanggalTriase.getSelectedItem().toString().substring(11,19), 
                                PrimerKodePetugas.getText()
                            });
                            if(TabSkala1dan2.getSelectedIndex()==0){
                                for(i=0;i<tbSkala1.getRowCount();i++){ 
                                    if(tbSkala1.getValueAt(i,0).toString().equals("true")){
                                        if(Sequel.menyimpantf2("data_triase_igddetail_skala1","?,?","Skala 1",2,new String[]{
                                                TNoRw.getText(),tbSkala1.getValueAt(i,1).toString()
                                            })==true){
                                                tbSkala1.setValueAt(false,i,0);
                                        }
                                    }
                                }
                            }else if(TabSkala1dan2.getSelectedIndex()==1){
                                for(i=0;i<tbSkala2.getRowCount();i++){ 
                                    if(tbSkala2.getValueAt(i,0).toString().equals("true")){
                                        if(Sequel.menyimpantf2("data_triase_igddetail_skala2","?,?","Skala 2",2,new String[]{
                                                TNoRw.getText(),tbSkala2.getValueAt(i,1).toString()
                                            })==true){
                                                tbSkala2.setValueAt(false,i,0);
                                        }
                                    }
                                }
                            }
                            emptTeks();
                            tampil();
                            TabPilihan.setSelectedIndex(1);
                        }
                    }
                }else if(TabTriase.getSelectedIndex()==1){
                    jmlskala3=0;jmlskala4=0;jmlskala5=0;
                    for(i=0;i<tbSkala3.getRowCount();i++){ 
                        if(tbSkala3.getValueAt(i,0).toString().equals("true")){
                            jmlskala3++;
                        }
                    }
                    for(i=0;i<tbSkala4.getRowCount();i++){ 
                        if(tbSkala4.getValueAt(i,0).toString().equals("true")){
                            jmlskala4++;
                        }
                    }
                    for(i=0;i<tbSkala5.getRowCount();i++){ 
                        if(tbSkala5.getValueAt(i,0).toString().equals("true")){
                            jmlskala5++;
                        }
                    }
                    if(SekunderAnamnesa.getText().trim().equals("")){
                        Valid.textKosong(SekunderAnamnesa,"Anamnesa");
                    }else if(SekunderSuhu.getText().trim().equals("")){
                        Valid.textKosong(SekunderSuhu,"Suhu");
                    }else if(SekunderNyeri.getText().trim().equals("")){
                        Valid.textKosong(SekunderNyeri,"Nyeri");
                    }else if(SekunderTensi.getText().trim().equals("")){
                        Valid.textKosong(SekunderTensi,"Tensi");
                    }else if(SekunderNadi.getText().trim().equals("")){
                        Valid.textKosong(SekunderNadi,"Nadi");
                    }else if(SekunderSaturasi.getText().trim().equals("")){
                        Valid.textKosong(SekunderSaturasi,"Saturasi O²");
                    }else if(SekunderRespirasi.getText().trim().equals("")){
                        Valid.textKosong(SekunderRespirasi,"Respirasi");
                    }else if(SekunderCatatan.getText().trim().equals("")){
                        Valid.textKosong(SekunderCatatan,"Catatan");
                    }else if(SekunderKodePetugas.getText().trim().equals("")||SekunderNamaPetugas.getText().trim().equals("")){
                        Valid.textKosong(btnSekunderPetugas,"Dokter/Petugas Triase");
                    }else if((jmlskala3==0)&&(jmlskala4==0)&&(jmlskala5==0)){
                        Valid.textKosong(TCariPemeriksaan2,"Skala 3 / Skala 4 / Skala 5");
                    }else{
                        if(Sequel.mengedittf("data_triase_igd","no_rawat=?","no_rawat=?,tgl_kunjungan=?,cara_masuk=?,alat_transportasi=?,alasan_kedatangan=?,keterangan_kedatangan=?,kode_kasus=?,tekanan_darah=?,nadi=?,pernapasan=?,suhu=?,saturasi_o2=?,nyeri=?",14,new String[]{
                                TNoRw.getText(),Valid.SetTgl(TanggalKunjungan.getSelectedItem()+"")+" "+TanggalKunjungan.getSelectedItem().toString().substring(11,19),
                                CaraMasuk.getSelectedItem().toString(),Transportasi.getSelectedItem().toString(),AlasanKedatangan.getSelectedItem().toString(), 
                                KeteranganKedatangan.getText(),KdKasus.getText(),SekunderTensi.getText(),SekunderNadi.getText(),SekunderRespirasi.getText(),SekunderSuhu.getText(),
                                SekunderSaturasi.getText(),SekunderNyeri.getText(),tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString()
                            })==true){
                            if(SekunderZonaKuning.isSelected()==true){
                                keputusan="Zona Kuning";
                            }else if(SekunderZonaHijau.isSelected()==true){
                                keputusan="Zona Hijau";
                            }
                            Sequel.menyimpan("data_triase_igdsekunder","?,?,?,?,?,?", 6,new String[]{
                                TNoRw.getText(),SekunderAnamnesa.getText(),SekunderCatatan.getText(),keputusan,
                                Valid.SetTgl(SekunderTanggalTriase.getSelectedItem()+"")+" "+SekunderTanggalTriase.getSelectedItem().toString().substring(11,19), 
                                SekunderKodePetugas.getText()
                            });
                            if(TabSkala3dan4dan5.getSelectedIndex()==0){
                                for(i=0;i<tbSkala3.getRowCount();i++){ 
                                    if(tbSkala3.getValueAt(i,0).toString().equals("true")){
                                        if(Sequel.menyimpantf2("data_triase_igddetail_skala3","?,?","Skala 3",2,new String[]{
                                                TNoRw.getText(),tbSkala3.getValueAt(i,1).toString()
                                            })==true){
                                                tbSkala3.setValueAt(false,i,0);
                                        }
                                    }
                                }
                            }else if(TabSkala3dan4dan5.getSelectedIndex()==1){
                                for(i=0;i<tbSkala4.getRowCount();i++){ 
                                    if(tbSkala4.getValueAt(i,0).toString().equals("true")){
                                        if(Sequel.menyimpantf2("data_triase_igddetail_skala4","?,?","Skala 4",2,new String[]{
                                                TNoRw.getText(),tbSkala4.getValueAt(i,1).toString()
                                            })==true){
                                                tbSkala4.setValueAt(false,i,0);
                                        }
                                    }
                                }
                            }else if(TabSkala3dan4dan5.getSelectedIndex()==2){
                                for(i=0;i<tbSkala5.getRowCount();i++){ 
                                    if(tbSkala5.getValueAt(i,0).toString().equals("true")){
                                        if(Sequel.menyimpantf2("data_triase_igddetail_skala5","?,?","Skala 5",2,new String[]{
                                                TNoRw.getText(),tbSkala5.getValueAt(i,1).toString()
                                            })==true){
                                                tbSkala5.setValueAt(false,i,0);
                                        }
                                    }
                                }
                            }
                            emptTeks();
                            tampil();
                            TabPilihan.setSelectedIndex(1);
                        }
                    }
                }
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
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){ 
            keputusan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Laporan Triase IGD",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1","Laporan 2"},"Laporan 1");
            switch (keputusan) {
                case "Laporan 1":
                    Map<String, Object> param = new HashMap<>(); 
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReportqry("rptDataTriaseIGD.jasper","report","::[ Data Triase IGD ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,data_triase_igd.tgl_kunjungan,"+
                        "data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                        "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus "+
                        "from reg_periksa inner join pasien inner join data_triase_igd inner join master_triase_macam_kasus "+
                        "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=data_triase_igd.no_rawat "+
                        "and data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus "+
                        "where data_triase_igd.tgl_kunjungan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "data_triase_igd.tgl_kunjungan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "data_triase_igd.tgl_kunjungan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "data_triase_igd.tgl_kunjungan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and data_triase_igd.cara_masuk like '%"+TCari.getText().trim()+"%' or "+
                        "data_triase_igd.tgl_kunjungan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and data_triase_igd.alat_transportasi like '%"+TCari.getText().trim()+"%' or "+
                        "data_triase_igd.tgl_kunjungan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and data_triase_igd.alasan_kedatangan like '%"+TCari.getText().trim()+"%' or "+
                        "data_triase_igd.tgl_kunjungan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and data_triase_igd.keterangan_kedatangan like '%"+TCari.getText().trim()+"%' or "+
                        "data_triase_igd.tgl_kunjungan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' and master_triase_macam_kasus.macam_kasus like '%"+TCari.getText().trim()+"%' order by data_triase_igd.tgl_kunjungan",param);
                    break;
                case "Laporan 2":
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%'><b>Pasien</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%'><b>Kunjungan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='18%'><b>Kedatangan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='13%'><b>Keluhan/Anamnesis Singkat</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'><b>Tanda Vital</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='21%'><b>Pemeriksaan</b></td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='14%'><b>Keputusan</b></td>"+
                            "</tr>"
                        );
                        
                        for(i=0;i<tabMode.getRowCount();i++){  
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                            "<tr class='isi2'>"+
                                                "<td width='34%' valign='top'>No.Rawat</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+tbTriase.getValueAt(i,0).toString()+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi2'>"+
                                                "<td width='34%' valign='top'>No.R.M.</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+tbTriase.getValueAt(i,1).toString()+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi2'>"+
                                                "<td width='34%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+tbTriase.getValueAt(i,2).toString()+"</td>"+
                                            "</tr>"+
                                        "</table>"+
                                    "</td>"+
                                    "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"+
                                            "<tr class='isi2'>"+
                                                "<td width='34%' valign='top'>Tgl.Kunjungan</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+tbTriase.getValueAt(i,3).toString()+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi2'>"+
                                                "<td width='34%' valign='top'>Cara Masuk</td><td valign='top'>:</td><td width='65%' valign='top'>"+tbTriase.getValueAt(i,4).toString()+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi2'>"+
                                                "<td width='34%' valign='top'>Transportasi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+tbTriase.getValueAt(i,5).toString()+"</td>"+
                                            "</tr>"+
                                        "</table>"+
                                    "</td>"+
                                    "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                        "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"+
                                            "<tr class='isi2'>"+
                                                "<td width='31%' valign='top'>Alasan Kedatangan</td><td valign='top'>:&nbsp;</td><td width='68%' valign='top'>"+tbTriase.getValueAt(i,6).toString()+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi2'>"+
                                                "<td width='31%' valign='top'>Keterangan</td><td valign='top'>:&nbsp;</td><td width='68%' valign='top'>"+tbTriase.getValueAt(i,7).toString()+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi2'>"+
                                                "<td width='31%' valign='top'>Macam Kasus</td><td valign='top'>:&nbsp;</td><td width='68%' valign='top'>"+tbTriase.getValueAt(i,9).toString()+"</td>"+
                                            "</tr>"+
                                        "</table>"+
                                    "</td>"
                            );
                            
                            ps=koneksi.prepareStatement(
                                "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                                "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.nik,data_triase_igd.tekanan_darah,"+
                                "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                "data_triase_igd.no_rawat from data_triase_igdprimer inner join data_triase_igd on data_triase_igd.no_rawat="+
                                "data_triase_igdprimer.no_rawat where data_triase_igd.no_rawat=?");
                            try {
                                ps.setString(1,tbTriase.getValueAt(i,0).toString());
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    htmlContent.append(
                                        "<td valign='top'> Keluhan Utama : "+rs.getString("keluhan_utama")+"<br>Kebutuhan Khusus : "+rs.getString("kebutuhan_khusus")+"</td>"+
                                        "<td valign='top'> Suhu (C) : "+rs.getString("suhu")+", Respirasi(/menit) : "+rs.getString("pernapasan")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Nyeri : "+rs.getString("nyeri")+"</td>"
                                    );
                                    ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "+
                                        "master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where data_triase_igddetail_skala1.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                    try {
                                        ps2.setString(1,rs.getString("no_rawat"));
                                        rs2=ps2.executeQuery();
                                        if(rs2.next()){
                                            htmlContent.append(
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                            );
                                            rs2.beforeFirst();
                                            while(rs2.next()){
                                                htmlContent.append(
                                                    "<tr class='isi5'>"+
                                                        "<td width='40%' valign='top'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='top'>:&nbsp;</td>"+
                                                        "<td width='59%' valign='top'>"+
                                                            "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                                    );
                                                ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                                    "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where "+
                                                    "master_triase_skala1.kode_pemeriksaan=? and data_triase_igddetail_skala1.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala1.kode_skala1");
                                                try {
                                                    ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                    ps3.setString(2,rs.getString("no_rawat"));
                                                    rs3=ps3.executeQuery();
                                                    while(rs3.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi5'>"+
                                                                "<td border='0' valign='middle' width='100%'>"+rs3.getString("pengkajian_skala1")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs3!=null){
                                                        rs3.close();
                                                    }
                                                    if(ps3!=null){
                                                        ps3.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            htmlContent.append(
                                                    "</table>"+
                                                "</td>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                    
                                    ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan and "+
                                        "master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                    try {
                                        ps2.setString(1,rs.getString("no_rawat"));
                                        rs2=ps2.executeQuery();
                                        if(rs2.next()){
                                            htmlContent.append(
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                            );
                                            rs2.beforeFirst();
                                            while(rs2.next()){
                                                htmlContent.append(
                                                    "<tr class='isi6'>"+
                                                        "<td width='40%' valign='top'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='top'>:&nbsp;</td>"+
                                                        "<td width='59%' valign='top'>"+
                                                            "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                                    );
                                                ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                                    "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where "+
                                                    "master_triase_skala2.kode_pemeriksaan=? and data_triase_igddetail_skala2.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala2.kode_skala2");
                                                try {
                                                    ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                    ps3.setString(2,rs.getString("no_rawat"));
                                                    rs3=ps3.executeQuery();
                                                    while(rs3.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi6'>"+
                                                                "<td border='0' valign='middle' width='100%'>"+rs3.getString("pengkajian_skala2")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs3!=null){
                                                        rs3.close();
                                                    }
                                                    if(ps3!=null){
                                                        ps3.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            htmlContent.append(
                                                    "</table>"+
                                                "</td>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                    htmlContent.append(
                                        "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                            "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"+
                                                "<tr class='isi2'>"+
                                                    "<td width='34%' valign='top'>Keputusan</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>Zona Merah "+rs.getString("plan")+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td width='34%' valign='top'>Tanggal & Jam</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("tanggaltriase")+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td width='34%' valign='top'>Catatan</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("catatan")+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td width='34%' valign='top'>Dokter/Petugas</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("nik")+" "+Sequel.cariIsi("select nama from pegawai where nik=?",rs.getString("nik"))+"</td>"+
                                                "</tr>"+
                                            "</table>"+
                                        "</td>"
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
                            
                            ps=koneksi.prepareStatement(
                                "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                                "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nik,data_triase_igd.tekanan_darah,"+
                                "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                "data_triase_igd.no_rawat from data_triase_igdsekunder inner join data_triase_igd on data_triase_igd.no_rawat="+
                                "data_triase_igdsekunder.no_rawat where data_triase_igd.no_rawat=?");
                            try {
                                ps.setString(1,tbTriase.getValueAt(i,0).toString());
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    htmlContent.append(
                                        "<td valign='top'> Anamnesa Singkat : "+rs.getString("anamnesa_singkat")+"</td>"+
                                        "<td valign='top'> Suhu (C) : "+rs.getString("suhu")+", Respirasi(/menit) : "+rs.getString("pernapasan")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Nyeri : "+rs.getString("nyeri")+"</td>"
                                    );
                                    ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan and "+
                                        "master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                    try {
                                        ps2.setString(1,rs.getString("no_rawat"));
                                        rs2=ps2.executeQuery();
                                        if(rs2.next()){
                                            htmlContent.append(
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                            );
                                            rs2.beforeFirst();
                                            while(rs2.next()){
                                                htmlContent.append(
                                                    "<tr class='isi7'>"+
                                                        "<td width='40%' valign='top'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='top'>:&nbsp;</td>"+
                                                        "<td width='59%' valign='top'>"+
                                                            "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                                    );
                                                ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                                    "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where "+
                                                    "master_triase_skala3.kode_pemeriksaan=? and data_triase_igddetail_skala3.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala3.kode_skala3");
                                                try {
                                                    ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                    ps3.setString(2,rs.getString("no_rawat"));
                                                    rs3=ps3.executeQuery();
                                                    while(rs3.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi7'>"+
                                                                "<td border='0' valign='middle' width='100%'>"+rs3.getString("pengkajian_skala3")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs3!=null){
                                                        rs3.close();
                                                    }
                                                    if(ps3!=null){
                                                        ps3.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            htmlContent.append(
                                                    "</table>"+
                                                "</td>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                    
                                    ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan and "+
                                        "master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                    try {
                                        ps2.setString(1,rs.getString("no_rawat"));
                                        rs2=ps2.executeQuery();
                                        if(rs2.next()){
                                            htmlContent.append(
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                            );
                                            rs2.beforeFirst();
                                            while(rs2.next()){
                                                htmlContent.append(
                                                    "<tr class='isi8'>"+
                                                        "<td width='40%' valign='top'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='top'>:&nbsp;</td>"+
                                                        "<td width='59%' valign='top'>"+
                                                            "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                                    );
                                                ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                                    "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where "+
                                                    "master_triase_skala4.kode_pemeriksaan=? and data_triase_igddetail_skala4.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala4.kode_skala4");
                                                try {
                                                    ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                    ps3.setString(2,rs.getString("no_rawat"));
                                                    rs3=ps3.executeQuery();
                                                    while(rs3.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi8'>"+
                                                                "<td border='0' valign='middle' width='100%'>"+rs3.getString("pengkajian_skala4")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs3!=null){
                                                        rs3.close();
                                                    }
                                                    if(ps3!=null){
                                                        ps3.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            htmlContent.append(
                                                    "</table>"+
                                                "</td>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                    
                                    ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan and "+
                                        "master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                    try {
                                        ps2.setString(1,rs.getString("no_rawat"));
                                        rs2=ps2.executeQuery();
                                        if(rs2.next()){
                                            htmlContent.append(
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                            );
                                            rs2.beforeFirst();
                                            while(rs2.next()){
                                                htmlContent.append(
                                                    "<tr class='isi9'>"+
                                                        "<td width='40%' valign='top'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='top'>:&nbsp;</td>"+
                                                        "<td width='59%' valign='top'>"+
                                                            "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"
                                                    );
                                                ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                                    "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where "+
                                                    "master_triase_skala5.kode_pemeriksaan=? and data_triase_igddetail_skala5.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala5.kode_skala5");
                                                try {
                                                    ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                    ps3.setString(2,rs.getString("no_rawat"));
                                                    rs3=ps3.executeQuery();
                                                    while(rs3.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi9'>"+
                                                                "<td border='0' valign='middle' width='100%'>"+rs3.getString("pengkajian_skala5")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs3!=null){
                                                        rs3.close();
                                                    }
                                                    if(ps3!=null){
                                                        ps3.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            htmlContent.append(
                                                    "</table>"+
                                                "</td>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                    htmlContent.append(
                                        "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                            "<table width='100%' border='0' cellpadding='0' cellspacing='0' align='center'>"+
                                                "<tr class='isi2'>"+
                                                    "<td width='34%' valign='top'>Keputusan</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("plan")+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td width='34%' valign='top'>Tanggal & Jam</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("tanggaltriase")+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td width='34%' valign='top'>Catatan</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("catatan")+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td width='34%' valign='top'>Dokter/Petugas</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("nik")+" "+Sequel.cariIsi("select nama from pegawai where nik=?",rs.getString("nik"))+"</td>"+
                                                "</tr>"+
                                            "</table>"+
                                        "</td>"
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
                            
                            htmlContent.append(
                                "</tr>"
                            );
                        }
                        
                        LoadHTML2.setText(
                            "<html>"+
                              "<table width='1400px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                        File f = new File("DataTriaseIGD.html");            
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                        bw.write(LoadHTML2.getText().replaceAll("<head>","<head>"+
                                    "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                    "<table width='1400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr class='isi2'>"+
                                            "<td valign='top' align='center'>"+
                                                "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                "<font size='2' face='Tahoma'>DATA TRIASE IGD<br><br></font>"+        
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
                    TNoRM1.setText("");
                    TPasien1.setText("");
                    LoadHTML.setText("");
                    ChkAccor.setSelected(false);
                    isMenu();
                    break;
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

    private void tbTriaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTriaseMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                ChkAccor.setSelected(true);
                isMenu();
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbTriaseMouseClicked

    private void tbTriaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTriaseKeyPressed
       if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                if(tbTriase.getSelectedRow()!= -1){
                    try {
                        Valid.tabelKosong(tabModeSkala1);
                        Valid.tabelKosong(tabModeSkala2);
                        Valid.tabelKosong(tabModeSkala3);
                        Valid.tabelKosong(tabModeSkala4);
                        Valid.tabelKosong(tabModeSkala5);
                        TNoRw.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                        TNoRM.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),1).toString());
                        TPasien.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),2).toString());
                        Valid.SetTgl2(TanggalKunjungan,tbTriase.getValueAt(tbTriase.getSelectedRow(),3).toString());
                        CaraMasuk.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(),4).toString());
                        Transportasi.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(),5).toString());
                        AlasanKedatangan.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(),6).toString());
                        KeteranganKedatangan.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),7).toString());
                        KdKasus.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),8).toString());
                        NmKasus.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),9).toString());
                        TabPilihan.setSelectedIndex(0);
                        ps=koneksi.prepareStatement(
                                "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                                "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.nik,data_triase_igd.tekanan_darah,"+
                                "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                "data_triase_igd.no_rawat from data_triase_igdprimer inner join data_triase_igd on data_triase_igd.no_rawat="+
                                "data_triase_igdprimer.no_rawat where data_triase_igd.no_rawat=?");
                        try {
                            ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                PrimerKeluhanUtama.setText(rs.getString("keluhan_utama"));
                                PrimerSuhu.setText(rs.getString("suhu"));
                                PrimerNyeri.setText(rs.getString("nyeri"));
                                PrimerTensi.setText(rs.getString("tekanan_darah"));
                                PrimerNadi.setText(rs.getString("nadi"));
                                PrimerSaturasi.setText(rs.getString("saturasi_o2"));
                                PrimerRespirasi.setText(rs.getString("pernapasan"));
                                PrimerKubutuhanKusus.setSelectedItem(rs.getString("kebutuhan_khusus"));
                                TabTriase.setSelectedIndex(0);

                                ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "+
                                        "master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where data_triase_igddetail_skala1.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        TabSkala1dan2.setSelectedIndex(0);
                                        Valid.tabelKosong(tabModePemeriksaan);
                                        Valid.tabelKosong(tabModeSkala1);
                                        rs2.beforeFirst();
                                        while(rs2.next()){
                                            tabModePemeriksaan.addRow(new String[]{rs2.getString("kode_pemeriksaan"),rs2.getString("nama_pemeriksaan")});
                                            ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala1.kode_skala1,master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                                    "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where "+
                                                    "master_triase_skala1.kode_pemeriksaan=? and data_triase_igddetail_skala1.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala1.kode_skala1");
                                            try {
                                                ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                ps3.setString(2,rs.getString("no_rawat"));
                                                rs3=ps3.executeQuery();
                                                while(rs3.next()){
                                                    tabModeSkala1.addRow(new Object[]{true,rs3.getString("kode_skala1"),rs3.getString("pengkajian_skala1")});
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif : "+e);
                                            } finally{
                                                if(rs3!=null){
                                                    rs3.close();
                                                }
                                                if(ps3!=null){
                                                    ps3.close();
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }

                                ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan and "+
                                        "master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        TabSkala1dan2.setSelectedIndex(1);
                                        Valid.tabelKosong(tabModePemeriksaan);
                                        Valid.tabelKosong(tabModeSkala2);
                                        rs2.beforeFirst();
                                        while(rs2.next()){
                                            tabModePemeriksaan.addRow(new String[]{rs2.getString("kode_pemeriksaan"),rs2.getString("nama_pemeriksaan")});
                                            ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala2.kode_skala2,master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                                    "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where "+
                                                    "master_triase_skala2.kode_pemeriksaan=? and data_triase_igddetail_skala2.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala2.kode_skala2");
                                            try {
                                                ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                ps3.setString(2,rs.getString("no_rawat"));
                                                rs3=ps3.executeQuery();
                                                while(rs3.next()){
                                                    tabModeSkala2.addRow(new Object[]{true,rs3.getString("kode_skala2"),rs3.getString("pengkajian_skala2")});
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif : "+e);
                                            } finally{
                                                if(rs3!=null){
                                                    rs3.close();
                                                }
                                                if(ps3!=null){
                                                    ps3.close();
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }

                                if(rs.getString("plan").equals("Ruang Resusitasi")){
                                    PrimerResusitasi.setSelected(true);
                                }else if(rs.getString("plan").equals("Ruang Kritis")){
                                    PrimerKritis.setSelected(true);
                                }
                                
                                PrimerTanggalTriase.setDate(rs.getDate("tanggaltriase"));
                                PrimerCatatan.setText(rs.getString("catatan"));
                                PrimerKodePetugas.setText(rs.getString("nik"));
                                PrimerNamaPetugas.setText(Sequel.cariIsi("select nama from pegawai where nik=?",rs.getString("nik")));
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

                        ps=koneksi.prepareStatement(
                                "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                                "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nik,data_triase_igd.tekanan_darah,"+
                                "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                "data_triase_igd.no_rawat from data_triase_igdsekunder inner join data_triase_igd on data_triase_igd.no_rawat="+
                                "data_triase_igdsekunder.no_rawat where data_triase_igd.no_rawat=?");
                        try {
                            ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                SekunderAnamnesa.setText(rs.getString("anamnesa_singkat"));
                                SekunderSuhu.setText(rs.getString("suhu"));
                                SekunderNyeri.setText(rs.getString("nyeri"));
                                SekunderTensi.setText(rs.getString("tekanan_darah"));
                                SekunderNadi.setText(rs.getString("nadi"));
                                SekunderSaturasi.setText(rs.getString("saturasi_o2"));
                                SekunderRespirasi.setText(rs.getString("pernapasan"));
                                TabTriase.setSelectedIndex(1);

                                ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan and "+
                                        "master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        TabSkala3dan4dan5.setSelectedIndex(0);
                                        Valid.tabelKosong(tabModePemeriksaan2);
                                        Valid.tabelKosong(tabModeSkala3);
                                        rs2.beforeFirst();
                                        while(rs2.next()){
                                            tabModePemeriksaan2.addRow(new String[]{rs2.getString("kode_pemeriksaan"),rs2.getString("nama_pemeriksaan")});
                                            ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala3.kode_skala3,master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                                    "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where "+
                                                    "master_triase_skala3.kode_pemeriksaan=? and data_triase_igddetail_skala3.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala3.kode_skala3");
                                            try {
                                                ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                ps3.setString(2,rs.getString("no_rawat"));
                                                rs3=ps3.executeQuery();
                                                while(rs3.next()){
                                                    tabModeSkala3.addRow(new Object[]{true,rs3.getString("kode_skala3"),rs3.getString("pengkajian_skala3")});
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif : "+e);
                                            } finally{
                                                if(rs3!=null){
                                                    rs3.close();
                                                }
                                                if(ps3!=null){
                                                    ps3.close();
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }

                                ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan and "+
                                        "master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        TabSkala3dan4dan5.setSelectedIndex(1);
                                        Valid.tabelKosong(tabModePemeriksaan2);
                                        Valid.tabelKosong(tabModeSkala4);
                                        rs2.beforeFirst();
                                        while(rs2.next()){
                                            tabModePemeriksaan2.addRow(new String[]{rs2.getString("kode_pemeriksaan"),rs2.getString("nama_pemeriksaan")});
                                            ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala4.kode_skala4,master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                                    "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where "+
                                                    "master_triase_skala4.kode_pemeriksaan=? and data_triase_igddetail_skala4.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala4.kode_skala4");
                                            try {
                                                ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                ps3.setString(2,rs.getString("no_rawat"));
                                                rs3=ps3.executeQuery();
                                                while(rs3.next()){
                                                    tabModeSkala4.addRow(new Object[]{true,rs3.getString("kode_skala4"),rs3.getString("pengkajian_skala4")});
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif : "+e);
                                            } finally{
                                                if(rs3!=null){
                                                    rs3.close();
                                                }
                                                if(ps3!=null){
                                                    ps3.close();
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }

                                ps2=koneksi.prepareStatement(
                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                        "from master_triase_pemeriksaan inner join master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan and "+
                                        "master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat=? "+
                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        TabSkala3dan4dan5.setSelectedIndex(2);
                                        Valid.tabelKosong(tabModePemeriksaan2);
                                        Valid.tabelKosong(tabModeSkala5);
                                        rs2.beforeFirst();
                                        while(rs2.next()){
                                            tabModePemeriksaan2.addRow(new String[]{rs2.getString("kode_pemeriksaan"),rs2.getString("nama_pemeriksaan")});
                                            ps3=koneksi.prepareStatement(
                                                    "select master_triase_skala5.kode_skala5,master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                                    "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where "+
                                                    "master_triase_skala5.kode_pemeriksaan=? and data_triase_igddetail_skala5.no_rawat=? "+
                                                    "order by data_triase_igddetail_skala5.kode_skala5");
                                            try {
                                                ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                                ps3.setString(2,rs.getString("no_rawat"));
                                                rs3=ps3.executeQuery();
                                                while(rs3.next()){
                                                    tabModeSkala5.addRow(new Object[]{true,rs3.getString("kode_skala5"),rs3.getString("pengkajian_skala5")});
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif : "+e);
                                            } finally{
                                                if(rs3!=null){
                                                    rs3.close();
                                                }
                                                if(ps3!=null){
                                                    ps3.close();
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                } finally{
                                    if(rs2!=null){
                                        rs2.close();
                                    }
                                    if(ps2!=null){
                                        ps2.close();
                                    }
                                }

                                if(rs.getString("plan").equals("Zona Kuning")){
                                    SekunderZonaKuning.setSelected(true);
                                }else if(rs.getString("plan").equals("Zona Hijau")){
                                    SekunderZonaHijau.setSelected(true);
                                }
                                
                                SekunderTanggalTriase.setDate(rs.getDate("tanggaltriase"));
                                SekunderCatatan.setText(rs.getString("catatan"));
                                SekunderKodePetugas.setText(rs.getString("nik"));
                                SekunderNamaPetugas.setText(Sequel.cariIsi("select nama from pegawai where nik=?",rs.getString("nik")));
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
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    }
                    TNoRM1.setText("");
                    TPasien1.setText("");
                    LoadHTML.setText("");
                    ChkAccor.setSelected(false);
                    isMenu();
                }
            }           
        }
}//GEN-LAST:event_tbTriaseKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilPemeriksaan();
        tampilPemeriksaan2();
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void TabPilihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPilihanMouseClicked
        if(TabPilihan.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabPilihanMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getHeight()<620){   
            ScrollTriase.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormTriase.setPreferredSize(new Dimension(FormTriase.WIDTH,500));
            if(this.getWidth()<780){
                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormTriase.setPreferredSize(new Dimension(770,500));
            }else{
                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }else{
            ScrollTriase.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);            
            if(this.getWidth()<780){
                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormTriase.setPreferredSize(new Dimension(770,FormTriase.HEIGHT));
            }else{
                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void KdKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKasusKeyPressed
        //Valid.pindah(evt, TCari,kdskala);
    }//GEN-LAST:event_KdKasusKeyPressed

    private void NmKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKasusKeyPressed
        //Valid.pindah(evt,kdskala,BtnSimpan);
    }//GEN-LAST:event_NmKasusKeyPressed

    private void btnKasusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKasusActionPerformed
        kasus.isCek();
        kasus.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kasus.setLocationRelativeTo(internalFrame1);
        kasus.setVisible(true);
    }//GEN-LAST:event_btnKasusActionPerformed

    private void btnKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKasusKeyPressed
        Valid.pindah(evt,AlasanKedatangan,CaraMasuk);
    }//GEN-LAST:event_btnKasusKeyPressed

    private void TabTriaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabTriaseMouseClicked
        if(TabTriase.getSelectedIndex()==0){
            TabSkala1dan2MouseClicked(null);
        }else if(TabTriase.getSelectedIndex()==1){
            TabSkala3dan4dan5MouseClicked(null);
        }
    }//GEN-LAST:event_TabTriaseMouseClicked

    private void PrimerSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerSuhuKeyPressed
        Valid.pindah(evt,PrimerKeluhanUtama,PrimerNyeri);
    }//GEN-LAST:event_PrimerSuhuKeyPressed

    private void PrimerSaturasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerSaturasiKeyPressed
        Valid.pindah(evt,PrimerNadi,PrimerRespirasi);
    }//GEN-LAST:event_PrimerSaturasiKeyPressed

    private void PrimerNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerNyeriKeyPressed
        Valid.pindah(evt,PrimerSuhu,PrimerTensi);
    }//GEN-LAST:event_PrimerNyeriKeyPressed

    private void PrimerNadiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrimerNadiActionPerformed

    }//GEN-LAST:event_PrimerNadiActionPerformed

    private void PrimerNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerNadiKeyPressed
        Valid.pindah(evt,PrimerTensi,PrimerSaturasi);
    }//GEN-LAST:event_PrimerNadiKeyPressed

    private void PrimerRespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerRespirasiKeyPressed
        Valid.pindah(evt,PrimerSaturasi,PrimerKubutuhanKusus);
    }//GEN-LAST:event_PrimerRespirasiKeyPressed

    private void PrimerTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerTensiKeyPressed
        Valid.pindah(evt,PrimerNyeri,PrimerNadi);
    }//GEN-LAST:event_PrimerTensiKeyPressed

    private void PrimerKodePetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerKodePetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrimerKodePetugasKeyPressed

    private void PrimerNamaPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerNamaPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrimerNamaPetugasKeyPressed

    private void btnPrimerPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimerPetugasActionPerformed
        index=1;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPrimerPetugasActionPerformed

    private void btnPrimerPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPrimerPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrimerPetugasKeyPressed

    private void TCariPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPemeriksaanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilPemeriksaan();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            PrimerCatatan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            PrimerKubutuhanKusus.requestFocus();
        }
    }//GEN-LAST:event_TCariPemeriksaanKeyPressed

    private void BtnCariPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaanActionPerformed
        tampilPemeriksaan();
    }//GEN-LAST:event_BtnCariPemeriksaanActionPerformed

    private void BtnCariPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPemeriksaanKeyPressed

    private void BtnTambahPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahPemeriksaanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterTriasePemeriksaan form=new MasterTriasePemeriksaan(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahPemeriksaanActionPerformed

    private void TCariSkala1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariSkala1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariSkala1ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariSkala1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariSkala1KeyPressed

    private void BtnCariSkala1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariSkala1ActionPerformed
        if(tbPemeriksaan.getSelectedRow()!= -1){
            tampilskala1();
            tampilskala2();
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih pemeriksaan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnCariSkala1ActionPerformed

    private void BtnCariSkala1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariSkala1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariSkala1KeyPressed

    private void BtnTambahSkala1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahSkala1ActionPerformed
        if(TabSkala1dan2.getSelectedIndex()==0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MasterTriaseSkala1 form=new MasterTriaseSkala1(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabSkala1dan2.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MasterTriaseSkala2 form=new MasterTriaseSkala2(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTambahSkala1ActionPerformed

    private void tbPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyPressed
        if(tabModePemeriksaan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariPemeriksaan.setText("");
                TCariPemeriksaan.requestFocus();
            }           
        }
    }//GEN-LAST:event_tbPemeriksaanKeyPressed

    private void tbPemeriksaanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyReleased
        if(tabModePemeriksaan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    tampilskala1();
                    tampilskala2();
                } catch (java.lang.NullPointerException e) {
                }
            }          
        }
    }//GEN-LAST:event_tbPemeriksaanKeyReleased

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if(tabModePemeriksaan.getRowCount()!=0){
            try {
                tampilskala1();
                tampilskala2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void TabSkala1dan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabSkala1dan2MouseClicked
        if(TabSkala1dan2.getSelectedIndex()==0){
            label11.setForeground(new Color(170,00,0));
            TCariSkala1.setForeground(new Color(170,00,0));
            BtnTambahSkala1.setEnabled(akses.getmaster_triase_skala1());
            PrimerResusitasi.setSelected(true);
        }else if(TabSkala1dan2.getSelectedIndex()==1){
            label11.setForeground(new Color(255,0,0));
            TCariSkala1.setForeground(new Color(255,00,0));
            BtnTambahSkala1.setEnabled(akses.getmaster_triase_skala2());
            PrimerKritis.setSelected(true);
        }
    }//GEN-LAST:event_TabSkala1dan2MouseClicked

    private void SekunderSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderSuhuKeyPressed
        Valid.pindah(evt,SekunderAnamnesa,SekunderNyeri);
    }//GEN-LAST:event_SekunderSuhuKeyPressed

    private void SekunderSaturasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderSaturasiKeyPressed
        Valid.pindah(evt,SekunderNyeri,SekunderRespirasi);
    }//GEN-LAST:event_SekunderSaturasiKeyPressed

    private void SekunderNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderNyeriKeyPressed
        Valid.pindah(evt,SekunderSuhu,SekunderSaturasi);
    }//GEN-LAST:event_SekunderNyeriKeyPressed

    private void SekunderNadiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SekunderNadiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SekunderNadiActionPerformed

    private void SekunderNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderNadiKeyPressed
        Valid.pindah(evt,SekunderTensi,TCariPemeriksaan2);
    }//GEN-LAST:event_SekunderNadiKeyPressed

    private void SekunderRespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderRespirasiKeyPressed
        Valid.pindah(evt,SekunderSaturasi,SekunderTensi);
    }//GEN-LAST:event_SekunderRespirasiKeyPressed

    private void SekunderTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderTensiKeyPressed
        Valid.pindah(evt,SekunderRespirasi,SekunderNadi);
    }//GEN-LAST:event_SekunderTensiKeyPressed

    private void TCariPemeriksaan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPemeriksaan2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilPemeriksaan2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            SekunderCatatan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            SekunderNadi.requestFocus();
        }
    }//GEN-LAST:event_TCariPemeriksaan2KeyPressed

    private void BtnCariPemeriksaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaan1ActionPerformed
        tampilPemeriksaan2();
    }//GEN-LAST:event_BtnCariPemeriksaan1ActionPerformed

    private void BtnCariPemeriksaan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPemeriksaan1KeyPressed

    private void BtnTambahPemeriksaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahPemeriksaan1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterTriasePemeriksaan form=new MasterTriasePemeriksaan(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahPemeriksaan1ActionPerformed

    private void tbPemeriksaan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaan2MouseClicked
        if(tabModePemeriksaan2.getRowCount()!=0){
            try {
                tampilskala3();
                tampilskala4();
                tampilskala5();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPemeriksaan2MouseClicked

    private void tbPemeriksaan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaan2KeyPressed
        if(tabModePemeriksaan2.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCariPemeriksaan2.setText("");
                TCariPemeriksaan2.requestFocus();
            }           
        }
    }//GEN-LAST:event_tbPemeriksaan2KeyPressed

    private void tbPemeriksaan2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaan2KeyReleased
        if(tabModePemeriksaan2.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    tampilskala3();
                    tampilskala4();
                    tampilskala5();
                } catch (java.lang.NullPointerException e) {
                }
            }          
        }
    }//GEN-LAST:event_tbPemeriksaan2KeyReleased

    private void TCariSkala3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariSkala3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariSkala3KeyPressed

    private void BtnCariSkala2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariSkala2ActionPerformed
        if(tbPemeriksaan2.getSelectedRow()!= -1){
            tampilskala3();
            tampilskala4();
            tampilskala5();
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih pemeriksaan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnCariSkala2ActionPerformed

    private void BtnCariSkala2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariSkala2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariSkala2KeyPressed

    private void BtnTambahSkala2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahSkala2ActionPerformed
        if(TabSkala3dan4dan5.getSelectedIndex()==0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MasterTriaseSkala3 form=new MasterTriaseSkala3(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabSkala3dan4dan5.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MasterTriaseSkala4 form=new MasterTriaseSkala4(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabSkala3dan4dan5.getSelectedIndex()==2){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            MasterTriaseSkala5 form=new MasterTriaseSkala5(null,false);
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTambahSkala2ActionPerformed

    private void TabSkala3dan4dan5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabSkala3dan4dan5MouseClicked
        if(TabSkala3dan4dan5.getSelectedIndex()==0){
            label13.setForeground(new Color(200,200,0));
            TCariSkala3.setForeground(new Color(200,200,0));
            BtnTambahSkala2.setEnabled(akses.getmaster_triase_skala3());
            SekunderZonaKuning.setSelected(true);
        }else if(TabSkala3dan4dan5.getSelectedIndex()==1){
            label13.setForeground(new Color(0,170,0));
            TCariSkala3.setForeground(new Color(0,170,0));
            BtnTambahSkala2.setEnabled(akses.getmaster_triase_skala4());
            SekunderZonaHijau.setSelected(true);
        }else if(TabSkala3dan4dan5.getSelectedIndex()==2){
            label13.setForeground(new Color(150,150,150));
            TCariSkala3.setForeground(new Color(150,150,150));
            BtnTambahSkala2.setEnabled(akses.getmaster_triase_skala5());
            SekunderZonaHijau.setSelected(true);
        }
    }//GEN-LAST:event_TabSkala3dan4dan5MouseClicked

    private void PrimerCatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerCatatanKeyPressed
        Valid.pindah(evt,TCariPemeriksaan,PrimerTanggalTriase);
    }//GEN-LAST:event_PrimerCatatanKeyPressed

    private void SekunderKodePetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderKodePetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SekunderKodePetugasKeyPressed

    private void SekunderNamaPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderNamaPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SekunderNamaPetugasKeyPressed

    private void btnSekunderPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSekunderPetugasActionPerformed
        index=2;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnSekunderPetugasActionPerformed

    private void btnSekunderPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSekunderPetugasKeyPressed
        Valid.pindah(evt,SekunderTanggalTriase,BtnSimpan);
    }//GEN-LAST:event_btnSekunderPetugasKeyPressed

    private void SekunderCatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderCatatanKeyPressed
        Valid.pindah(evt,TCariPemeriksaan2,SekunderTanggalTriase);
    }//GEN-LAST:event_SekunderCatatanKeyPressed

    private void TransportasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransportasiKeyPressed
        Valid.pindah(evt,TCari,TanggalKunjungan);
    }//GEN-LAST:event_TransportasiKeyPressed

    private void TanggalKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKunjunganKeyPressed
        Valid.pindah(evt,Transportasi,CaraMasuk);
    }//GEN-LAST:event_TanggalKunjunganKeyPressed

    private void AlasanKedatanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanKedatanganKeyPressed
        Valid.pindah(evt,CaraMasuk,btnKasus);
    }//GEN-LAST:event_AlasanKedatanganKeyPressed

    private void CaraMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraMasukKeyPressed
        Valid.pindah(evt,TanggalKunjungan,AlasanKedatangan);
    }//GEN-LAST:event_CaraMasukKeyPressed

    private void KeteranganKedatanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKedatanganKeyPressed
        if(TabTriase.getSelectedIndex()==0){
            Valid.pindah(evt,CaraMasuk,PrimerKeluhanUtama);
        }else if(TabTriase.getSelectedIndex()==1){
            Valid.pindah(evt,CaraMasuk,SekunderAnamnesa);
        }
    }//GEN-LAST:event_KeteranganKedatanganKeyPressed

    private void SekunderAnamnesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderAnamnesaKeyPressed
        Valid.pindah2(evt,KeteranganKedatangan,SekunderSuhu);
    }//GEN-LAST:event_SekunderAnamnesaKeyPressed

    private void PrimerKeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerKeluhanUtamaKeyPressed
       Valid.pindah2(evt,KeteranganKedatangan,PrimerSuhu);
    }//GEN-LAST:event_PrimerKeluhanUtamaKeyPressed

    private void PrimerKubutuhanKususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerKubutuhanKususKeyPressed
        Valid.pindah(evt,PrimerRespirasi,TCariPemeriksaan);
    }//GEN-LAST:event_PrimerKubutuhanKususKeyPressed

    private void PrimerTanggalTriaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrimerTanggalTriaseKeyPressed
        Valid.pindah(evt,PrimerCatatan,btnPrimerPetugas);
    }//GEN-LAST:event_PrimerTanggalTriaseKeyPressed

    private void btnPrimerPetugasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPrimerPetugasKeyReleased
        Valid.pindah(evt,PrimerTanggalTriase,BtnSimpan);
    }//GEN-LAST:event_btnPrimerPetugasKeyReleased

    private void SekunderTanggalTriaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SekunderTanggalTriaseKeyPressed
        Valid.pindah(evt,SekunderCatatan,btnSekunderPetugas);
    }//GEN-LAST:event_SekunderTanggalTriaseKeyPressed

    private void tbTriaseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTriaseKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }          
        }
    }//GEN-LAST:event_tbTriaseKeyReleased

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbTriase.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan triasenya...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(TNoRM1.getText().trim().equals("")||TPasien1.getText().trim().equals("")){
            Valid.textKosong(TCari,"Pasien");
        }else{
            if(tbTriase.getSelectedRow()> -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(LoadHTML.getText().contains("#AA0000")){
                    Map<String, Object> param = new HashMap<>(); 
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    try {
                        ps=koneksi.prepareStatement(
                            "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                            "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.nik,data_triase_igd.tekanan_darah,"+
                            "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                            "data_triase_igd.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pegawai.nama,data_triase_igd.tgl_kunjungan, "+
                            "data_triase_igd.cara_masuk,master_triase_macam_kasus.macam_kasus from data_triase_igdprimer inner join data_triase_igd "+
                            "inner join pasien inner join pegawai inner join reg_periksa inner join master_triase_macam_kasus on "+
                            "data_triase_igd.no_rawat=data_triase_igdprimer.no_rawat and reg_periksa.no_rawat=data_triase_igd.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pegawai.nik=data_triase_igdprimer.nik "+
                            "and master_triase_macam_kasus.kode_kasus=data_triase_igd.kode_kasus where data_triase_igd.no_rawat=?");
                        try {
                            ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                param.put("norawat",rs.getString("no_rawat"));
                                param.put("norm",rs.getString("no_rkm_medis"));
                                param.put("namapasien",rs.getString("nm_pasien"));
                                param.put("tanggallahir",rs.getDate("tgl_lahir"));
                                param.put("jk",rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                                param.put("tanggalkunjungan",rs.getDate("tgl_kunjungan"));
                                param.put("jamkunjungan",rs.getString("tgl_kunjungan").toString().substring(11,19));
                                param.put("caradatang",rs.getString("cara_masuk"));
                                param.put("macamkasus",rs.getString("macam_kasus"));
                                param.put("keluhanutama",rs.getString("keluhan_utama"));
                                param.put("kebutuhankhusus",rs.getString("kebutuhan_khusus"));
                                param.put("plan",rs.getString("plan"));
                                param.put("tanggaltriase",rs.getDate("tanggaltriase"));
                                param.put("jamtriase",rs.getString("tanggaltriase").toString().substring(11,19));
                                param.put("pegawai",rs.getString("nama"));
                                param.put("catatan",rs.getString("catatan"));
                                param.put("tandavital","Suhu (C) : "+rs.getString("suhu")+", Nyeri : "+rs.getString("nyeri")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Respirasi(/menit) : "+rs.getString("pernapasan"));
                                finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nik"));
                                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nama")+"\nID "+(finger.equals("")?rs.getString("nik"):finger)+"\n"+Valid.SetTgl3(rs.getString("tanggaltriase"))); 
                                ps2=koneksi.prepareStatement(
                                    "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                    "from master_triase_pemeriksaan inner join master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                    "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "+
                                    "master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where data_triase_igddetail_skala1.no_rawat=? "+
                                    "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    Sequel.queryu("truncate table temporary");
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        datatriase="";
                                        ps3=koneksi.prepareStatement(
                                            "select master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                            "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where "+
                                            "master_triase_skala1.kode_pemeriksaan=? and data_triase_igddetail_skala1.no_rawat=? "+
                                            "order by data_triase_igddetail_skala1.kode_skala1");
                                        try {
                                            ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                            ps3.setString(2,rs.getString("no_rawat"));
                                            rs3=ps3.executeQuery();
                                            while(rs3.next()){
                                                datatriase=rs3.getString("pengkajian_skala1")+", "+datatriase;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notif : "+e);
                                        } finally{
                                            if(rs3!=null){
                                                rs3.close();
                                            }
                                            if(ps3!=null){
                                                ps3.close();
                                            }
                                        }
                                        
                                        if(datatriase.endsWith(", ")){
                                            datatriase = datatriase.substring(0,datatriase.length() - 2);
                                        }
                                        Sequel.menyimpan2("temporary","'0','"+rs2.getString("nama_pemeriksaan")+"','"+datatriase+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Pengkajian");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
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
                                
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih Lembar/PDF Triase Skala 1..!","Pilihan",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Lembar Triase Skala 1","PDF Triase Skala 1"},"Lambar Triase Skala 1");
                    switch (pilihan) {
                        case "Lembar Triase Skala 1":
                              Valid.MyReport("rptLembarTriaseSkala1.jasper","report","::[ Triase Skala 1 ]::",param);
                              break;
                        case "PDF Triase Skala 1":
                              Valid.MyReportPDF("rptLembarTriaseSkala1.jasper","report","::[ Triase Skala 1 ]::",param);
                              break;
                    } 
                }else if(LoadHTML.getText().contains("#FF0000")){
                    Map<String, Object> param = new HashMap<>(); 
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    try {
                        ps=koneksi.prepareStatement(
                            "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                            "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.nik,data_triase_igd.tekanan_darah,"+
                            "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                            "data_triase_igd.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pegawai.nama,data_triase_igd.tgl_kunjungan, "+
                            "data_triase_igd.cara_masuk,master_triase_macam_kasus.macam_kasus from data_triase_igdprimer inner join data_triase_igd "+
                            "inner join pasien inner join pegawai inner join reg_periksa inner join master_triase_macam_kasus on "+
                            "data_triase_igd.no_rawat=data_triase_igdprimer.no_rawat and reg_periksa.no_rawat=data_triase_igd.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pegawai.nik=data_triase_igdprimer.nik "+
                            "and master_triase_macam_kasus.kode_kasus=data_triase_igd.kode_kasus where data_triase_igd.no_rawat=?");
                        try {
                            ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                param.put("norawat",rs.getString("no_rawat"));
                                param.put("norm",rs.getString("no_rkm_medis"));
                                param.put("namapasien",rs.getString("nm_pasien"));
                                param.put("tanggallahir",rs.getDate("tgl_lahir"));
                                param.put("jk",rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                                param.put("tanggalkunjungan",rs.getDate("tgl_kunjungan"));
                                param.put("jamkunjungan",rs.getString("tgl_kunjungan").toString().substring(11,19));
                                param.put("caradatang",rs.getString("cara_masuk"));
                                param.put("macamkasus",rs.getString("macam_kasus"));
                                param.put("keluhanutama",rs.getString("keluhan_utama"));
                                param.put("kebutuhankhusus",rs.getString("kebutuhan_khusus"));
                                param.put("plan",rs.getString("plan"));
                                param.put("tanggaltriase",rs.getDate("tanggaltriase"));
                                param.put("tandavital","Suhu (C) : "+rs.getString("suhu")+", Nyeri : "+rs.getString("nyeri")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Respirasi(/menit) : "+rs.getString("pernapasan"));
                                param.put("jamtriase",rs.getString("tanggaltriase").toString().substring(11,19));
                                param.put("pegawai",rs.getString("nama"));
                                param.put("catatan",rs.getString("catatan"));
                                finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nik"));
                                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nama")+"\nID "+(finger.equals("")?rs.getString("nik"):finger)+"\n"+Valid.SetTgl3(rs.getString("tanggaltriase"))); 
                                ps2=koneksi.prepareStatement(
                                    "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                    "from master_triase_pemeriksaan inner join master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                    "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan and "+
                                    "master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat=? "+
                                    "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    Sequel.queryu("truncate table temporary");
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        datatriase="";
                                        ps3=koneksi.prepareStatement(
                                            "select master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                            "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where "+
                                            "master_triase_skala2.kode_pemeriksaan=? and data_triase_igddetail_skala2.no_rawat=? "+
                                            "order by data_triase_igddetail_skala2.kode_skala2");
                                        try {
                                            ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                            ps3.setString(2,rs.getString("no_rawat"));
                                            rs3=ps3.executeQuery();
                                            while(rs3.next()){
                                                datatriase=rs3.getString("pengkajian_skala2")+", "+datatriase;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notif : "+e);
                                        } finally{
                                            if(rs3!=null){
                                                rs3.close();
                                            }
                                            if(ps3!=null){
                                                ps3.close();
                                            }
                                        }
                                        
                                        if(datatriase.endsWith(", ")){
                                            datatriase = datatriase.substring(0,datatriase.length() - 2);
                                        }
                                        Sequel.menyimpan2("temporary","'0','"+rs2.getString("nama_pemeriksaan")+"','"+datatriase+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Pengkajian");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
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
                                
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih Lembar/PDF Triase Skala 2..!","Pilihan",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Lembar Triase Skala 2","PDF Triase Skala 2"},"Lambar Triase Skala 2");
                    switch (pilihan) {
                        case "Lembar Triase Skala 2":
                              Valid.MyReport("rptLembarTriaseSkala2.jasper","report","::[ Triase Skala 2 ]::",param);
                              break;
                        case "PDF Triase Skala 2":
                              Valid.MyReportPDF("rptLembarTriaseSkala2.jasper","report","::[ Triase Skala 2 ]::",param);
                              break;
                    } 
                }else if(LoadHTML.getText().contains("#C8C800")){
                    Map<String, Object> param = new HashMap<>(); 
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    try {
                        ps=koneksi.prepareStatement(
                            "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                            "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nik,data_triase_igd.tekanan_darah,"+
                            "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                            "data_triase_igd.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pegawai.nama,data_triase_igd.tgl_kunjungan, "+
                            "data_triase_igd.cara_masuk,master_triase_macam_kasus.macam_kasus from data_triase_igdsekunder inner join data_triase_igd "+
                            "inner join pasien inner join pegawai inner join reg_periksa inner join master_triase_macam_kasus on "+
                            "data_triase_igd.no_rawat=data_triase_igdsekunder.no_rawat and reg_periksa.no_rawat=data_triase_igd.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pegawai.nik=data_triase_igdsekunder.nik "+
                            "and master_triase_macam_kasus.kode_kasus=data_triase_igd.kode_kasus where data_triase_igd.no_rawat=?");
                        try {
                            ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                param.put("norawat",rs.getString("no_rawat"));
                                param.put("norm",rs.getString("no_rkm_medis"));
                                param.put("namapasien",rs.getString("nm_pasien"));
                                param.put("tanggallahir",rs.getDate("tgl_lahir"));
                                param.put("jk",rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                                param.put("tanggalkunjungan",rs.getDate("tgl_kunjungan"));
                                param.put("jamkunjungan",rs.getString("tgl_kunjungan").toString().substring(11,19));
                                param.put("caradatang",rs.getString("cara_masuk"));
                                param.put("macamkasus",rs.getString("macam_kasus"));
                                param.put("keluhanutama",rs.getString("anamnesa_singkat"));
                                param.put("plan",rs.getString("plan"));
                                param.put("tanggaltriase",rs.getDate("tanggaltriase"));
                                param.put("tandavital","Suhu (C) : "+rs.getString("suhu")+", Nyeri : "+rs.getString("nyeri")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Respirasi(/menit) : "+rs.getString("pernapasan"));
                                param.put("jamtriase",rs.getString("tanggaltriase").toString().substring(11,19));
                                param.put("pegawai",rs.getString("nama"));
                                param.put("catatan",rs.getString("catatan"));
                                finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nik"));
                                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nama")+"\nID "+(finger.equals("")?rs.getString("nik"):finger)+"\n"+Valid.SetTgl3(rs.getString("tanggaltriase"))); 
                                ps2=koneksi.prepareStatement(
                                    "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                    "from master_triase_pemeriksaan inner join master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                    "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan and "+
                                    "master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat=? "+
                                    "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    Sequel.queryu("truncate table temporary");
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        datatriase="";
                                        ps3=koneksi.prepareStatement(
                                            "select master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                            "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where "+
                                            "master_triase_skala3.kode_pemeriksaan=? and data_triase_igddetail_skala3.no_rawat=? "+
                                            "order by data_triase_igddetail_skala3.kode_skala3");
                                        try {
                                            ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                            ps3.setString(2,rs.getString("no_rawat"));
                                            rs3=ps3.executeQuery();
                                            while(rs3.next()){
                                                datatriase=rs3.getString("pengkajian_skala3")+", "+datatriase;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notif : "+e);
                                        } finally{
                                            if(rs3!=null){
                                                rs3.close();
                                            }
                                            if(ps3!=null){
                                                ps3.close();
                                            }
                                        }
                                        
                                        if(datatriase.endsWith(", ")){
                                            datatriase = datatriase.substring(0,datatriase.length() - 2);
                                        }
                                        Sequel.menyimpan2("temporary","'0','"+rs2.getString("nama_pemeriksaan")+"','"+datatriase+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Pengkajian");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
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
                                
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih Lembar/PDF Triase Skala 3..!","Pilihan",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Lembar Triase Skala 3","PDF Triase Skala 3"},"Lembar Triase Skala 3");
                    switch (pilihan) {
                        case "Lembar Triase Skala 3":
                              Valid.MyReport("rptLembarTriaseSkala3.jasper","report","::[ Triase Skala 3 ]::",param);
                              break;
                        case "PDF Triase Skala 3":
                              Valid.MyReportPDF("rptLembarTriaseSkala3.jasper","report","::[ Triase Skala 3 ]::",param);
                              break;
                    }
                }else if(LoadHTML.getText().contains("#00AA00")){
                    Map<String, Object> param = new HashMap<>(); 
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    try {
                        ps=koneksi.prepareStatement(
                            "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                            "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nik,data_triase_igd.tekanan_darah,"+
                            "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                            "data_triase_igd.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pegawai.nama,data_triase_igd.tgl_kunjungan, "+
                            "data_triase_igd.cara_masuk,master_triase_macam_kasus.macam_kasus from data_triase_igdsekunder inner join data_triase_igd "+
                            "inner join pasien inner join pegawai inner join reg_periksa inner join master_triase_macam_kasus on "+
                            "data_triase_igd.no_rawat=data_triase_igdsekunder.no_rawat and reg_periksa.no_rawat=data_triase_igd.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pegawai.nik=data_triase_igdsekunder.nik "+
                            "and master_triase_macam_kasus.kode_kasus=data_triase_igd.kode_kasus where data_triase_igd.no_rawat=?");
                        try {
                            ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                param.put("norawat",rs.getString("no_rawat"));
                                param.put("norm",rs.getString("no_rkm_medis"));
                                param.put("namapasien",rs.getString("nm_pasien"));
                                param.put("tanggallahir",rs.getDate("tgl_lahir"));
                                param.put("jk",rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                                param.put("tanggalkunjungan",rs.getDate("tgl_kunjungan"));
                                param.put("jamkunjungan",rs.getString("tgl_kunjungan").toString().substring(11,19));
                                param.put("caradatang",rs.getString("cara_masuk"));
                                param.put("macamkasus",rs.getString("macam_kasus"));
                                param.put("keluhanutama",rs.getString("anamnesa_singkat"));
                                param.put("plan",rs.getString("plan"));
                                param.put("tanggaltriase",rs.getDate("tanggaltriase"));
                                param.put("tandavital","Suhu (C) : "+rs.getString("suhu")+", Nyeri : "+rs.getString("nyeri")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Respirasi(/menit) : "+rs.getString("pernapasan"));
                                param.put("jamtriase",rs.getString("tanggaltriase").toString().substring(11,19));
                                param.put("pegawai",rs.getString("nama"));
                                param.put("catatan",rs.getString("catatan"));
                                finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nik"));
                                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nama")+"\nID "+(finger.equals("")?rs.getString("nik"):finger)+"\n"+Valid.SetTgl3(rs.getString("tanggaltriase"))); 
                                ps2=koneksi.prepareStatement(
                                    "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                    "from master_triase_pemeriksaan inner join master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                    "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan and "+
                                    "master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat=? "+
                                    "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    Sequel.queryu("truncate table temporary");
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        datatriase="";
                                        ps3=koneksi.prepareStatement(
                                            "select master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                            "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where "+
                                            "master_triase_skala4.kode_pemeriksaan=? and data_triase_igddetail_skala4.no_rawat=? "+
                                            "order by data_triase_igddetail_skala4.kode_skala4");
                                        try {
                                            ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                            ps3.setString(2,rs.getString("no_rawat"));
                                            rs3=ps3.executeQuery();
                                            while(rs3.next()){
                                                datatriase=rs3.getString("pengkajian_skala4")+", "+datatriase;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notif : "+e);
                                        } finally{
                                            if(rs3!=null){
                                                rs3.close();
                                            }
                                            if(ps3!=null){
                                                ps3.close();
                                            }
                                        }
                                        
                                        if(datatriase.endsWith(", ")){
                                            datatriase = datatriase.substring(0,datatriase.length() - 2);
                                        }
                                        Sequel.menyimpan2("temporary","'0','"+rs2.getString("nama_pemeriksaan")+"','"+datatriase+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Pengkajian");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
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
                                
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih Lembar/PDF Triase Skala 4..!","Pilihan",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Lembar Triase Skala 4","PDF Triase Skala 4"},"Lembar Triase Skala 4");
                    switch (pilihan) {
                        case "Lembar Triase Skala 4":
                              Valid.MyReport("rptLembarTriaseSkala4.jasper","report","::[ Triase Skala 4 ]::",param);
                              break;
                        case "PDF Triase Skala 4":
                              Valid.MyReportPDF("rptLembarTriaseSkala4.jasper","report","::[ Triase Skala 4 ]::",param);
                              break;
                    }
                }else if(LoadHTML.getText().contains("#969696")){
                    Map<String, Object> param = new HashMap<>(); 
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    try {
                        ps=koneksi.prepareStatement(
                            "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                            "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nik,data_triase_igd.tekanan_darah,"+
                            "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                            "data_triase_igd.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pegawai.nama,data_triase_igd.tgl_kunjungan, "+
                            "data_triase_igd.cara_masuk,master_triase_macam_kasus.macam_kasus from data_triase_igdsekunder inner join data_triase_igd "+
                            "inner join pasien inner join pegawai inner join reg_periksa inner join master_triase_macam_kasus on "+
                            "data_triase_igd.no_rawat=data_triase_igdsekunder.no_rawat and reg_periksa.no_rawat=data_triase_igd.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pegawai.nik=data_triase_igdsekunder.nik "+
                            "and master_triase_macam_kasus.kode_kasus=data_triase_igd.kode_kasus where data_triase_igd.no_rawat=?");
                        try {
                            ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                param.put("norawat",rs.getString("no_rawat"));
                                param.put("norm",rs.getString("no_rkm_medis"));
                                param.put("namapasien",rs.getString("nm_pasien"));
                                param.put("tanggallahir",rs.getDate("tgl_lahir"));
                                param.put("jk",rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                                param.put("tanggalkunjungan",rs.getDate("tgl_kunjungan"));
                                param.put("jamkunjungan",rs.getString("tgl_kunjungan").toString().substring(11,19));
                                param.put("caradatang",rs.getString("cara_masuk"));
                                param.put("macamkasus",rs.getString("macam_kasus"));
                                param.put("keluhanutama",rs.getString("anamnesa_singkat"));
                                param.put("plan",rs.getString("plan"));
                                param.put("tanggaltriase",rs.getDate("tanggaltriase"));
                                param.put("tandavital","Suhu (C) : "+rs.getString("suhu")+", Nyeri : "+rs.getString("nyeri")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Respirasi(/menit) : "+rs.getString("pernapasan"));
                                param.put("jamtriase",rs.getString("tanggaltriase").toString().substring(11,19));
                                param.put("pegawai",rs.getString("nama"));
                                param.put("catatan",rs.getString("catatan"));
                                finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nik"));
                                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nama")+"\nID "+(finger.equals("")?rs.getString("nik"):finger)+"\n"+Valid.SetTgl3(rs.getString("tanggaltriase"))); 
                                ps2=koneksi.prepareStatement(
                                    "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                    "from master_triase_pemeriksaan inner join master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                    "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan and "+
                                    "master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat=? "+
                                    "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                                try {
                                    Sequel.queryu("truncate table temporary");
                                    ps2.setString(1,rs.getString("no_rawat"));
                                    rs2=ps2.executeQuery();
                                    while(rs2.next()){
                                        datatriase="";
                                        ps3=koneksi.prepareStatement(
                                            "select master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                            "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where "+
                                            "master_triase_skala5.kode_pemeriksaan=? and data_triase_igddetail_skala5.no_rawat=? "+
                                            "order by data_triase_igddetail_skala5.kode_skala5");
                                        try {
                                            ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                            ps3.setString(2,rs.getString("no_rawat"));
                                            rs3=ps3.executeQuery();
                                            while(rs3.next()){
                                                datatriase=rs3.getString("pengkajian_skala5")+", "+datatriase;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notif : "+e);
                                        } finally{
                                            if(rs3!=null){
                                                rs3.close();
                                            }
                                            if(ps3!=null){
                                                ps3.close();
                                            }
                                        }
                                        
                                        if(datatriase.endsWith(", ")){
                                            datatriase = datatriase.substring(0,datatriase.length() - 2);
                                        }
                                        Sequel.menyimpan2("temporary","'0','"+rs2.getString("nama_pemeriksaan")+"','"+datatriase+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Pengkajian");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
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
                                
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih Lembar/PDF Triase Skala 5..!","Pilihan",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Lembar Triase Skala 5","PDF Triase Skala 5"},"Lembar Triase Skala 5");
                    switch (pilihan) {
                        case "Lembar Triase Skala 5":
                              Valid.MyReport("rptLembarTriaseSkala5.jasper","report","::[ Triase Skala 5 ]::",param);
                              break;
                        case "PDF Triase Skala 5":
                              Valid.MyReportPDF("rptLembarTriaseSkala5.jasper","report","::[ Triase Skala 5 ]::",param);
                              break;
                    }
                }
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih terlebih dahulu data yang mau dicetak data personal triasenya..!!!");
            }
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMTriaseIGD dialog = new RMTriaseIGD(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AlasanKedatangan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariPemeriksaan;
    private widget.Button BtnCariPemeriksaan1;
    private widget.Button BtnCariSkala1;
    private widget.Button BtnCariSkala2;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahPemeriksaan;
    private widget.Button BtnTambahPemeriksaan1;
    private widget.Button BtnTambahSkala1;
    private widget.Button BtnTambahSkala2;
    private widget.ComboBox CaraMasuk;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMenu;
    private widget.InternalFrame FormTriase;
    private widget.TextBox KdKasus;
    private widget.TextBox KeteranganKedatangan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NmKasus;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox PrimerCatatan;
    private widget.TextArea PrimerKeluhanUtama;
    private widget.TextBox PrimerKodePetugas;
    private widget.RadioButton PrimerKritis;
    private widget.ComboBox PrimerKubutuhanKusus;
    private widget.TextBox PrimerNadi;
    private widget.TextBox PrimerNamaPetugas;
    private widget.TextBox PrimerNyeri;
    private widget.TextBox PrimerRespirasi;
    private widget.RadioButton PrimerResusitasi;
    private widget.TextBox PrimerSaturasi;
    private widget.TextBox PrimerSuhu;
    private widget.Tanggal PrimerTanggalTriase;
    private widget.TextBox PrimerTensi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane ScrollHTML;
    private widget.ScrollPane ScrollTriase;
    private widget.TextArea SekunderAnamnesa;
    private widget.TextBox SekunderCatatan;
    private widget.TextBox SekunderKodePetugas;
    private widget.TextBox SekunderNadi;
    private widget.TextBox SekunderNamaPetugas;
    private widget.TextBox SekunderNyeri;
    private widget.TextBox SekunderRespirasi;
    private widget.TextBox SekunderSaturasi;
    private widget.TextBox SekunderSuhu;
    private widget.Tanggal SekunderTanggalTriase;
    private widget.TextBox SekunderTensi;
    private widget.RadioButton SekunderZonaHijau;
    private widget.RadioButton SekunderZonaKuning;
    private widget.TextBox TCari;
    private widget.TextBox TCariPemeriksaan;
    private widget.TextBox TCariPemeriksaan2;
    private widget.TextBox TCariSkala1;
    private widget.TextBox TCariSkala3;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabPilihan;
    private javax.swing.JTabbedPane TabSkala1dan2;
    private javax.swing.JTabbedPane TabSkala3dan4dan5;
    private javax.swing.JTabbedPane TabTriase;
    private widget.Tanggal TanggalKunjungan;
    private widget.ComboBox Transportasi;
    private widget.Button btnKasus;
    private widget.Button btnPrimerPetugas;
    private widget.Button btnSekunderPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame10;
    private widget.InternalFrame internalFrame11;
    private widget.InternalFrame internalFrame12;
    private widget.InternalFrame internalFrame13;
    private widget.InternalFrame internalFrame14;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
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
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.panelisi panelisi7;
    private widget.panelisi panelisi8;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbPemeriksaan;
    private widget.Table tbPemeriksaan2;
    private widget.Table tbSkala1;
    private widget.Table tbSkala2;
    private widget.Table tbSkala3;
    private widget.Table tbSkala4;
    private widget.Table tbSkala5;
    private widget.Table tbTriase;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,data_triase_igd.tgl_kunjungan,"+
                    "data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                    "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus "+
                    "from reg_periksa inner join pasien inner join data_triase_igd inner join master_triase_macam_kasus "+
                    "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=data_triase_igd.no_rawat "+
                    "and data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus "+
                    "where data_triase_igd.tgl_kunjungan between ? and ? and reg_periksa.no_rawat like ? or "+
                    "data_triase_igd.tgl_kunjungan between ? and ? and pasien.no_rkm_medis like ? or "+
                    "data_triase_igd.tgl_kunjungan between ? and ? and pasien.nm_pasien like ? or "+
                    "data_triase_igd.tgl_kunjungan between ? and ? and data_triase_igd.cara_masuk like ? or "+
                    "data_triase_igd.tgl_kunjungan between ? and ? and data_triase_igd.alat_transportasi like ? or "+
                    "data_triase_igd.tgl_kunjungan between ? and ? and data_triase_igd.alasan_kedatangan like ? or "+
                    "data_triase_igd.tgl_kunjungan between ? and ? and data_triase_igd.keterangan_kedatangan like ? or "+
                    "data_triase_igd.tgl_kunjungan between ? and ? and master_triase_macam_kasus.macam_kasus like ? order by data_triase_igd.tgl_kunjungan");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_kunjungan"),
                        rs.getString("cara_masuk"),rs.getString("alat_transportasi"),rs.getString("alasan_kedatangan"),
                        rs.getString("keterangan_kedatangan"),rs.getString("kode_kasus"),rs.getString("macam_kasus")
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
    
    private void emptTeks(){
        TNoRw.setText("");
        TPasien.setText("");
        TNoRM.setText("");
        Transportasi.setSelectedIndex(0);
        TanggalKunjungan.setDate(new Date());
        AlasanKedatangan.setSelectedIndex(0);
        KdKasus.setText("");
        NmKasus.setText("");
        CaraMasuk.setSelectedIndex(0);
        KeteranganKedatangan.setText("");
        PrimerKeluhanUtama.setText("");
        PrimerSuhu.setText("");
        PrimerNyeri.setText("");
        PrimerTensi.setText("");
        PrimerNadi.setText("");
        PrimerSaturasi.setText("");
        PrimerRespirasi.setText("");
        PrimerKubutuhanKusus.setSelectedIndex(0);
        PrimerCatatan.setText("");
        PrimerTanggalTriase.setDate(new Date());
        SekunderAnamnesa.setText("");
        SekunderSuhu.setText("");
        SekunderNyeri.setText("");
        SekunderSaturasi.setText("");
        SekunderRespirasi.setText("");
        SekunderTensi.setText("");
        SekunderNadi.setText("");
        SekunderCatatan.setText("");
        SekunderTanggalTriase.setDate(new Date());
        TabPilihan.setSelectedIndex(0);
        TabTriase.setSelectedIndex(0);
        Transportasi.requestFocus();
    }
    
    public void setNoRm(String norwt,String norm,String namapasien) {
        emptTeks();
        TNoRw.setText(norwt);
        TNoRM.setText(norm);
        TPasien.setText(namapasien);
        TCari.setText(norwt);   
    }
    
    public void tampilPemeriksaan() {        
        Valid.tabelKosong(tabModePemeriksaan);
        try{
            ps=koneksi.prepareStatement("select * from master_triase_pemeriksaan where kode_pemeriksaan like ? or nama_pemeriksaan like ? order by kode_pemeriksaan");
            try {
                ps.setString(1,"%"+TCariPemeriksaan.getText().trim()+"%");
                ps.setString(2,"%"+TCariPemeriksaan.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePemeriksaan.addRow(new String[]{rs.getString(1),rs.getString(2)});
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
    }
    
    public void tampilPemeriksaan2() {        
        Valid.tabelKosong(tabModePemeriksaan2);
        try{
            ps=koneksi.prepareStatement("select * from master_triase_pemeriksaan where kode_pemeriksaan like ? or nama_pemeriksaan like ? order by kode_pemeriksaan");
            try {
                ps.setString(1,"%"+TCariPemeriksaan2.getText().trim()+"%");
                ps.setString(2,"%"+TCariPemeriksaan2.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePemeriksaan2.addRow(new String[]{rs.getString(1),rs.getString(2)});
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
    }
      
    private void tampilskala1() {
        if(tbPemeriksaan.getSelectedRow()!= -1){
            try{
                jml=0;
                for(i=0;i<tbSkala1.getRowCount();i++){
                    if(tbSkala1.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
                
                pilih=null;
                pilih=new boolean[jml]; 
                kode=null;
                kode=new String[jml];
                pengkajian=null;
                pengkajian=new String[jml];
                
                index=0;        
                for(i=0;i<tbSkala1.getRowCount();i++){
                    if(tbSkala1.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbSkala1.getValueAt(i,1).toString();
                        pengkajian[index]=tbSkala1.getValueAt(i,2).toString();
                        index++;
                    }
                } 

                Valid.tabelKosong(tabModeSkala1);
                
                for(i=0;i<jml;i++){
                    tabModeSkala1.addRow(new Object[] {
                        pilih[i],kode[i],pengkajian[i]
                    });
                }
                
                ps=koneksi.prepareStatement(
                        "select * from master_triase_skala1 where kode_pemeriksaan=? and pengkajian_skala1 like ? order by master_triase_skala1.kode_skala1");
                try {
                    ps.setString(1,tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),0).toString());
                    ps.setString(2,"%"+TCariSkala1.getText().trim()+"%");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeSkala1.addRow(new Object[]{false,rs.getString("kode_skala1"),rs.getString("pengkajian_skala1")});
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
        }
    }
    
    private void tampilskala2() {
        if(tbPemeriksaan.getSelectedRow()!= -1){
            try{
                jml=0;
                for(i=0;i<tbSkala2.getRowCount();i++){
                    if(tbSkala2.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
                
                pilih=null;
                pilih=new boolean[jml]; 
                kode=null;
                kode=new String[jml];
                pengkajian=null;
                pengkajian=new String[jml];
                
                index=0;        
                for(i=0;i<tbSkala2.getRowCount();i++){
                    if(tbSkala2.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbSkala2.getValueAt(i,1).toString();
                        pengkajian[index]=tbSkala2.getValueAt(i,2).toString();
                        index++;
                    }
                } 

                Valid.tabelKosong(tabModeSkala2);
                
                for(i=0;i<jml;i++){
                    tabModeSkala2.addRow(new Object[] {
                        pilih[i],kode[i],pengkajian[i]
                    });
                }
                
                ps=koneksi.prepareStatement(
                        "select * from master_triase_skala2 where kode_pemeriksaan=? and pengkajian_skala2 like ? order by master_triase_skala2.kode_skala2");
                try {
                    ps.setString(1,tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),0).toString());
                    ps.setString(2,"%"+TCariSkala1.getText().trim()+"%");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeSkala2.addRow(new Object[]{false,rs.getString("kode_skala2"),rs.getString("pengkajian_skala2")});
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
        }
    }
    
    private void tampilskala3() {
        if(tbPemeriksaan2.getSelectedRow()!= -1){
            try{
                jml=0;
                for(i=0;i<tbSkala3.getRowCount();i++){
                    if(tbSkala3.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
                
                pilih=null;
                pilih=new boolean[jml]; 
                kode=null;
                kode=new String[jml];
                pengkajian=null;
                pengkajian=new String[jml];
                
                index=0;        
                for(i=0;i<tbSkala3.getRowCount();i++){
                    if(tbSkala3.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbSkala3.getValueAt(i,1).toString();
                        pengkajian[index]=tbSkala3.getValueAt(i,2).toString();
                        index++;
                    }
                } 

                Valid.tabelKosong(tabModeSkala3);
                
                for(i=0;i<jml;i++){
                    tabModeSkala3.addRow(new Object[] {
                        pilih[i],kode[i],pengkajian[i]
                    });
                }
                
                ps=koneksi.prepareStatement(
                        "select * from master_triase_skala3 where kode_pemeriksaan=? and pengkajian_skala3 like ? order by master_triase_skala3.kode_skala3");
                try {
                    ps.setString(1,tbPemeriksaan2.getValueAt(tbPemeriksaan2.getSelectedRow(),0).toString());
                    ps.setString(2,"%"+TCariSkala3.getText().trim()+"%");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeSkala3.addRow(new Object[]{false,rs.getString("kode_skala3"),rs.getString("pengkajian_skala3")});
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
        }
    }
    
    private void tampilskala4() {
        if(tbPemeriksaan2.getSelectedRow()!= -1){
            try{
                jml=0;
                for(i=0;i<tbSkala4.getRowCount();i++){
                    if(tbSkala4.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
                
                pilih=null;
                pilih=new boolean[jml]; 
                kode=null;
                kode=new String[jml];
                pengkajian=null;
                pengkajian=new String[jml];
                
                index=0;        
                for(i=0;i<tbSkala4.getRowCount();i++){
                    if(tbSkala4.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbSkala4.getValueAt(i,1).toString();
                        pengkajian[index]=tbSkala4.getValueAt(i,2).toString();
                        index++;
                    }
                } 

                Valid.tabelKosong(tabModeSkala4);
                
                for(i=0;i<jml;i++){
                    tabModeSkala4.addRow(new Object[] {
                        pilih[i],kode[i],pengkajian[i]
                    });
                }
                
                ps=koneksi.prepareStatement(
                        "select * from master_triase_skala4 where kode_pemeriksaan=? and pengkajian_skala4 like ? order by master_triase_skala4.kode_skala4");
                try {
                    ps.setString(1,tbPemeriksaan2.getValueAt(tbPemeriksaan2.getSelectedRow(),0).toString());
                    ps.setString(2,"%"+TCariSkala3.getText().trim()+"%");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeSkala4.addRow(new Object[]{false,rs.getString("kode_skala4"),rs.getString("pengkajian_skala4")});
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
        }
    }
    
    private void tampilskala5() {
        if(tbPemeriksaan2.getSelectedRow()!= -1){
            try{
                jml=0;
                for(i=0;i<tbSkala5.getRowCount();i++){
                    if(tbSkala5.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
                
                pilih=null;
                pilih=new boolean[jml]; 
                kode=null;
                kode=new String[jml];
                pengkajian=null;
                pengkajian=new String[jml];
                
                index=0;        
                for(i=0;i<tbSkala5.getRowCount();i++){
                    if(tbSkala5.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbSkala5.getValueAt(i,1).toString();
                        pengkajian[index]=tbSkala5.getValueAt(i,2).toString();
                        index++;
                    }
                } 

                Valid.tabelKosong(tabModeSkala5);
                
                for(i=0;i<jml;i++){
                    tabModeSkala5.addRow(new Object[] {
                        pilih[i],kode[i],pengkajian[i]
                    });
                }
                
                ps=koneksi.prepareStatement(
                        "select * from master_triase_skala5 where kode_pemeriksaan=? and pengkajian_skala5 like ? order by master_triase_skala5.kode_skala5");
                try {
                    ps.setString(1,tbPemeriksaan2.getValueAt(tbPemeriksaan2.getSelectedRow(),0).toString());
                    ps.setString(2,"%"+TCariSkala3.getText().trim()+"%");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeSkala5.addRow(new Object[]{false,rs.getString("kode_skala5"),rs.getString("pengkajian_skala5")});
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
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getdata_triase_igd());
        BtnHapus.setEnabled(akses.getdata_triase_igd());
        BtnPrint.setEnabled(akses.getdata_triase_igd());
        BtnEdit.setEnabled(akses.getdata_triase_igd());  
        BtnTambahPemeriksaan.setEnabled(akses.getmaster_triase_pemeriksaan());
        BtnTambahPemeriksaan1.setEnabled(akses.getmaster_triase_pemeriksaan());
        BtnTambahSkala1.setEnabled(akses.getmaster_triase_skala1());
        BtnTambahSkala2.setEnabled(akses.getmaster_triase_skala3());
        
        if(akses.getjml2()>=1){
            btnPrimerPetugas.setEnabled(false);
            btnSekunderPetugas.setEnabled(false);
            PrimerKodePetugas.setText(akses.getkode());
            SekunderKodePetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", PrimerNamaPetugas,PrimerKodePetugas.getText());
            SekunderNamaPetugas.setText(PrimerNamaPetugas.getText());
        } 
    }
    
    private void getData() {
        if(tbTriase.getSelectedRow()!= -1){
            try {
                TNoRM1.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),1).toString());
                TPasien1.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),2).toString());
                ps=koneksi.prepareStatement(
                        "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                        "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.nik,data_triase_igd.tekanan_darah,"+
                        "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                        "data_triase_igd.no_rawat from data_triase_igdprimer inner join data_triase_igd on data_triase_igd.no_rawat="+
                        "data_triase_igdprimer.no_rawat where data_triase_igd.no_rawat=?");
                try {
                    ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent = new StringBuilder();
                        htmlContent.append(    
                            "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%'>Keterangan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65%'>Triase Primer</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Keluhan Utama</td>"+
                                "<td valign='middle'>"+rs.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Tanda Vital</td>"+
                                "<td valign='middle'>Suhu (C) : "+rs.getString("suhu")+", Nyeri : "+rs.getString("nyeri")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Respirasi(/menit) : "+rs.getString("pernapasan")+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Kebutuhan Khusus</td>"+
                                "<td valign='middle'>"+rs.getString("kebutuhan_khusus")+"</td>"+
                            "</tr>"
                        );
                        
                        ps2=koneksi.prepareStatement(
                                "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                "from master_triase_pemeriksaan inner join master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "+
                                "master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where data_triase_igddetail_skala1.no_rawat=? "+
                                "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            if(rs2.next()){
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                        "<td valign='middle' bgcolor='#AA0000' color='ffffff' align='center'>Immediate/Segera</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(                             
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                            "<td valign='middle' bgcolor='#AA0000' color='ffffff'>"+
                                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                    );
                                    ps3=koneksi.prepareStatement(
                                            "select master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                            "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where "+
                                            "master_triase_skala1.kode_pemeriksaan=? and data_triase_igddetail_skala1.no_rawat=? "+
                                            "order by data_triase_igddetail_skala1.kode_skala1");
                                    try {
                                        ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                        ps3.setString(2,rs.getString("no_rawat"));
                                        rs3=ps3.executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td border='0' valign='middle' bgcolor='#AA0000' color='ffffff' width='100%'>"+rs3.getString("pengkajian_skala1")+"</td>"+
                                                "</tr>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                        if(ps3!=null){
                                            ps3.close();
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
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        ps2=koneksi.prepareStatement(
                                "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                "from master_triase_pemeriksaan inner join master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan and "+
                                "master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat=? "+
                                "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            if(rs2.next()){
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                        "<td valign='middle' bgcolor='#FF0000' color='ffffff' align='center'>Emergensi</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(                             
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                            "<td valign='middle' bgcolor='#FF0000' color='ffffff'>"+
                                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                    );
                                    ps3=koneksi.prepareStatement(
                                            "select master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                            "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where "+
                                            "master_triase_skala2.kode_pemeriksaan=? and data_triase_igddetail_skala2.no_rawat=? "+
                                            "order by data_triase_igddetail_skala2.kode_skala2");
                                    try {
                                        ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                        ps3.setString(2,rs.getString("no_rawat"));
                                        rs3=ps3.executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td border='0' valign='middle' bgcolor='#FF0000' color='ffffff' width='100%'>"+rs3.getString("pengkajian_skala2")+"</td>"+
                                                "</tr>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                        if(ps3!=null){
                                            ps3.close();
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
                            System.out.println("Notif : "+e);
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
                                "<td valign='middle'>Plan/Keputusan</td>"+
                                "<td valign='middle' bgcolor='"+keputusan+"' color='ffffff'>Zona Merah "+rs.getString("plan")+"</td>"+
                            "</tr>"+                       
                            "<tr class='isi'>"+
                                "<td valign='middle'>&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Dokter/Petugas Triase</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Tanggal & Jam</td>"+
                                "<td valign='middle'>"+rs.getString("tanggaltriase")+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Catatan</td>"+
                                "<td valign='middle'>"+rs.getString("catatan")+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Nama Dokter/Petugas</td>"+
                                "<td valign='middle'>"+rs.getString("nik")+" "+Sequel.cariIsi("select nama from pegawai where nik=?",rs.getString("nik"))+"</td>"+
                            "</tr>"
                        );
                        
                        LoadHTML.setText(
                            "<html>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                               htmlContent.toString()+
                              "</table>"+
                            "</html>");
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
                
                ps=koneksi.prepareStatement(
                        "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                        "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nik,data_triase_igd.tekanan_darah,"+
                        "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                        "data_triase_igd.no_rawat from data_triase_igdsekunder inner join data_triase_igd on data_triase_igd.no_rawat="+
                        "data_triase_igdsekunder.no_rawat where data_triase_igd.no_rawat=?");
                try {
                    ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        htmlContent = new StringBuilder();
                        htmlContent.append(    
                            "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%'>Keterangan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65%'>Triase Sekunder</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Anamnesa Singkat</td>"+
                                "<td valign='middle'>"+rs.getString("anamnesa_singkat").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Tanda Vital</td>"+
                                "<td valign='middle'>Suhu (C) : "+rs.getString("suhu")+", Nyeri : "+rs.getString("nyeri")+", Tensi : "+rs.getString("tekanan_darah")+", Nadi(/menit) : "+rs.getString("nadi")+", Saturasi O²(%) : "+rs.getString("saturasi_o2")+", Respirasi(/menit) : "+rs.getString("pernapasan")+"</td>"+
                            "</tr>"
                        );
                        
                        ps2=koneksi.prepareStatement(
                                "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                "from master_triase_pemeriksaan inner join master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan and "+
                                "master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat=? "+
                                "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            if(rs2.next()){
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                        "<td valign='middle' bgcolor='#C8C800' color='ffffff' align='center'>Urgensi</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(                             
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                            "<td valign='middle' bgcolor='#C8C800' color='ffffff'>"+
                                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                    );
                                    ps3=koneksi.prepareStatement(
                                            "select master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                            "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where "+
                                            "master_triase_skala3.kode_pemeriksaan=? and data_triase_igddetail_skala3.no_rawat=? "+
                                            "order by data_triase_igddetail_skala3.kode_skala3");
                                    try {
                                        ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                        ps3.setString(2,rs.getString("no_rawat"));
                                        rs3=ps3.executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td border='0' valign='middle' bgcolor='#C8C800' color='ffffff' width='100%'>"+rs3.getString("pengkajian_skala3")+"</td>"+
                                                "</tr>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                        if(ps3!=null){
                                            ps3.close();
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
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        ps2=koneksi.prepareStatement(
                                "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                "from master_triase_pemeriksaan inner join master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan and "+
                                "master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat=? "+
                                "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            if(rs2.next()){
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                        "<td valign='middle' bgcolor='#00AA00' color='ffffff' align='center'>Semi Urgensi/Urgensi Rendah</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(                             
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                            "<td valign='middle' bgcolor='#00AA00' color='ffffff'>"+
                                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                    );
                                    ps3=koneksi.prepareStatement(
                                            "select master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                            "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where "+
                                            "master_triase_skala4.kode_pemeriksaan=? and data_triase_igddetail_skala4.no_rawat=? "+
                                            "order by data_triase_igddetail_skala4.kode_skala4");
                                    try {
                                        ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                        ps3.setString(2,rs.getString("no_rawat"));
                                        rs3=ps3.executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td border='0' valign='middle' bgcolor='#00AA00' color='ffffff' width='100%'>"+rs3.getString("pengkajian_skala4")+"</td>"+
                                                "</tr>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                        if(ps3!=null){
                                            ps3.close();
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
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        ps2=koneksi.prepareStatement(
                                "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                "from master_triase_pemeriksaan inner join master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan and "+
                                "master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat=? "+
                                "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            if(rs2.next()){
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                        "<td valign='middle' bgcolor='#969696' color='ffffff' align='center'>Non Urgensi</td>"+
                                    "</tr>");
                                rs2.beforeFirst();
                                while(rs2.next()){
                                    htmlContent.append(                             
                                        "<tr class='isi'>"+
                                            "<td valign='middle'>"+rs2.getString("nama_pemeriksaan")+"</td>"+
                                            "<td valign='middle' bgcolor='#969696' color='ffffff'>"+
                                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                    );
                                    ps3=koneksi.prepareStatement(
                                            "select master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                            "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where "+
                                            "master_triase_skala5.kode_pemeriksaan=? and data_triase_igddetail_skala5.no_rawat=? "+
                                            "order by data_triase_igddetail_skala5.kode_skala5");
                                    try {
                                        ps3.setString(1,rs2.getString("kode_pemeriksaan"));
                                        ps3.setString(2,rs.getString("no_rawat"));
                                        rs3=ps3.executeQuery();
                                        while(rs3.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td border='0' valign='middle' bgcolor='#969696' color='ffffff' width='100%'>"+rs3.getString("pengkajian_skala5")+"</td>"+
                                                "</tr>"
                                            );
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs3!=null){
                                            rs3.close();
                                        }
                                        if(ps3!=null){
                                            ps3.close();
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
                            System.out.println("Notif : "+e);
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
                                "<td valign='middle'>Plan/Keputusan</td>"+
                                "<td valign='middle' bgcolor='"+keputusan+"' color='ffffff'>"+rs.getString("plan")+"</td>"+
                            "</tr>"
                        );
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle'>&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Dokter/Petugas Triase</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Tanggal & Jam</td>"+
                                "<td valign='middle'>"+rs.getString("tanggaltriase")+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Catatan</td>"+
                                "<td valign='middle'>"+rs.getString("catatan")+"</td>"+
                            "</tr>"+
                            "<tr class='isi'>"+
                                "<td valign='middle'>Nama Dokter/Petugas</td>"+
                                "<td valign='middle'>"+rs.getString("nik")+" "+Sequel.cariIsi("select nama from pegawai where nik=?",rs.getString("nik"))+"</td>"+
                            "</tr>"
                        );
                        
                        LoadHTML.setText(
                            "<html>"+
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                               htmlContent.toString()+
                              "</table>"+
                            "</html>");
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
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } 
        }
    }
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);  
            ScrollHTML.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){   
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);  
            ScrollHTML.setVisible(false);
            ChkAccor.setVisible(true);
            
        }
    }
    
}

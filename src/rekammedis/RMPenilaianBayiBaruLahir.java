/*
 * Kontribusi dari windiarto
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.WarnaTable5;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
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
import java.util.Base64;
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
import simrskhanza.DlgCariPasien;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianBayiBaruLahir extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeRiwayatKehamilan,tabModeAPGAR;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPasien ibubayi=new DlgCariPasien(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianBayiBaruLahir(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgRiwayatPersalinan.setSize(650,192);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Umur","NIK Bayi","No.RM IBU","Nama Ibu","Tgl.Lahir Ibu","NIK Ibu","Kode Dokter","Nama Dokter","Tanggal",
            "G","P","A","Hidup","Usia Hamil","HbsAg","HIV/AIDS","Syphilis","Riwayat Obstetri Ibu","Keterangan Riwayat Obstetri Ibu","Faktor Risiko Neonatal",
            "Keterangan Faktor Risiko Neonatal","Tgl & Jam Persalinan","Bersalin Di","Inisiasi Menyusui Dini","Jenis Persalinan","Indikasi/Keterangan","Aterm",
            "Bernafas/Menangis","Tonus Otot Baik","Cairan Amnion Jernih","F 1","U 1","T 1","R 1","W 1","N 1'","F 5","U 5","T 5","R 5","W 5","N 5'","F 10","U 10",
            "T 10","R 10","W 10","N 10'","Frekuensi Napas","N.F.N","Retraksi","N.R","Sianosis","N.S","Jalan Masuk Udara","N.J.M","Grunting","N.G","Ttl.D.S",
            "Keterangan Down Score","Nadi(x/menit)","RR(x/menit)","Suhu(°C)","Saturasi O2(%)","BB(gram)","PB(cm)","LK(cm)","LD(cm)","Keadaan Umum","Keterangan Keadaan Umum",
            "Kulit","Keterangan Kulit","Kepala","Keterangan Kepala","Mata","Keterangan Mata","Telinga","Keterangan Telinga","Hidung","Keterangan Hidung","Mulut",
            "Keterangan Mulut","Tenggorokan","Keterangan Tenggorokan","Leher","Keterangan Leher","Thorax","Keterangan Thorax","Abdomen","Keterangan Abdomen",
            "Genitalia","Keterangan Genitalia","Anus","Keterangan Anus","Muskuloskeletal","Keterangan Muskuloskeletal","Ekstrimitas","Keterangan Ekstrimitas",
            "Paru","Keterangan Paru","Refleks Primitif","Keterangan Refleks Primitif","Kelainan Lainnya","Pemeriksaan Regional/Khusus/Tambahan","Laboratorium",
            "Radiologi","Penunjang Lainnya","Diagnosis/Asesmen","Tatalaksana","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 115; i++) {
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
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(115);
            }else if(i==14){
                column.setPreferredWidth(25);
            }else if(i==15){
                column.setPreferredWidth(25);
            }else if(i==16){
                column.setPreferredWidth(25);
            }else if(i==17){
                column.setPreferredWidth(38);
            }else if(i==18){
                column.setPreferredWidth(60);
            }else if(i==19){
                column.setPreferredWidth(60);
            }else if(i==20){
                column.setPreferredWidth(60);
            }else if(i==21){
                column.setPreferredWidth(60);
            }else if(i==22){
                column.setPreferredWidth(140);
            }else if(i==23){
                column.setPreferredWidth(170);
            }else if(i==24){
                column.setPreferredWidth(125);
            }else if(i==25){
                column.setPreferredWidth(177);
            }else if(i==26){
                column.setPreferredWidth(115);
            }else if(i==27){
                column.setPreferredWidth(150);
            }else if(i==28){
                column.setPreferredWidth(115);
            }else if(i==29){
                column.setPreferredWidth(88);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(41);
            }else if(i==32){
                column.setPreferredWidth(102);
            }else if(i==33){
                column.setPreferredWidth(87);
            }else if(i==34){
                column.setPreferredWidth(114);
            }else if(i==35){
                column.setPreferredWidth(27);
            }else if(i==36){
                column.setPreferredWidth(27);
            }else if(i==37){
                column.setPreferredWidth(27);
            }else if(i==38){
                column.setPreferredWidth(27);
            }else if(i==39){
                column.setPreferredWidth(27);
            }else if(i==40){
                column.setPreferredWidth(32);
            }else if(i==41){
                column.setPreferredWidth(27);
            }else if(i==42){
                column.setPreferredWidth(27);
            }else if(i==43){
                column.setPreferredWidth(27);
            }else if(i==44){
                column.setPreferredWidth(27);
            }else if(i==45){
                column.setPreferredWidth(27);
            }else if(i==46){
                column.setPreferredWidth(32);
            }else if(i==47){
                column.setPreferredWidth(30);
            }else if(i==48){
                column.setPreferredWidth(30);
            }else if(i==49){
                column.setPreferredWidth(30);
            }else if(i==50){
                column.setPreferredWidth(30);
            }else if(i==51){
                column.setPreferredWidth(33);
            }else if(i==52){
                column.setPreferredWidth(35);
            }else if(i==53){
                column.setPreferredWidth(88);
            }else if(i==54){
                column.setPreferredWidth(35);
            }else if(i==55){
                column.setPreferredWidth(85);
            }else if(i==56){
                column.setPreferredWidth(30);
            }else if(i==57){
                column.setPreferredWidth(121);
            }else if(i==58){
                column.setPreferredWidth(30);
            }else if(i==59){
                column.setPreferredWidth(160);
            }else if(i==60){
                column.setPreferredWidth(35);
            }else if(i==61){
                column.setPreferredWidth(171);
            }else if(i==62){
                column.setPreferredWidth(30);
            }else if(i==63){
                column.setPreferredWidth(42);
            }else if(i==64){
                column.setPreferredWidth(150);
            }else if(i==65){
                column.setPreferredWidth(76);
            }else if(i==66){
                column.setPreferredWidth(68);
            }else if(i==67){
                column.setPreferredWidth(52);
            }else if(i==68){
                column.setPreferredWidth(85);
            }else if(i==69){
                column.setPreferredWidth(54);
            }else if(i==70){
                column.setPreferredWidth(42);
            }else if(i==71){
                column.setPreferredWidth(42);
            }else if(i==72){
                column.setPreferredWidth(42);
            }else if(i==73){
                column.setPreferredWidth(86);
            }else if(i==74){
                column.setPreferredWidth(150);
            }else if(i==75){
                column.setPreferredWidth(80);
            }else if(i==76){
                column.setPreferredWidth(150);
            }else if(i==77){
                column.setPreferredWidth(80);
            }else if(i==78){
                column.setPreferredWidth(150);
            }else if(i==79){
                column.setPreferredWidth(80);
            }else if(i==80){
                column.setPreferredWidth(150);
            }else if(i==81){
                column.setPreferredWidth(80);
            }else if(i==82){
                column.setPreferredWidth(150);
            }else if(i==83){
                column.setPreferredWidth(80);
            }else if(i==84){
                column.setPreferredWidth(150);
            }else if(i==85){
                column.setPreferredWidth(80);
            }else if(i==86){
                column.setPreferredWidth(150);
            }else if(i==87){
                column.setPreferredWidth(80);
            }else if(i==88){
                column.setPreferredWidth(150);
            }else if(i==89){
                column.setPreferredWidth(80);
            }else if(i==90){
                column.setPreferredWidth(150);
            }else if(i==91){
                column.setPreferredWidth(80);
            }else if(i==92){
                column.setPreferredWidth(150);
            }else if(i==93){
                column.setPreferredWidth(80);
            }else if(i==94){
                column.setPreferredWidth(150);
            }else if(i==95){
                column.setPreferredWidth(80);
            }else if(i==96){
                column.setPreferredWidth(150);
            }else if(i==97){
                column.setPreferredWidth(80);
            }else if(i==98){
                column.setPreferredWidth(150);
            }else if(i==99){
                column.setPreferredWidth(80);
            }else if(i==100){
                column.setPreferredWidth(150);
            }else if(i==101){
                column.setPreferredWidth(80);
            }else if(i==102){
                column.setPreferredWidth(150);
            }else if(i==103){
                column.setPreferredWidth(80);
            }else if(i==104){
                column.setPreferredWidth(150);
            }else if(i==105){
                column.setPreferredWidth(85);
            }else if(i==106){
                column.setPreferredWidth(150);
            }else if(i==107){
                column.setPreferredWidth(150);
            }else if(i==108){
                column.setPreferredWidth(250);
            }else if(i==109){
                column.setPreferredWidth(200);
            }else if(i==110){
                column.setPreferredWidth(200);
            }else if(i==111){
                column.setPreferredWidth(200);
            }else if(i==112){
                column.setPreferredWidth(200);
            }else if(i==113){
                column.setPreferredWidth(300);
            }else if(i==114){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRiwayatKehamilan=new DefaultTableModel(null,new Object[]{
                "No","Tgl/Thn","Tempat Persalinan","Usia Hamil","Jenis Persalinan","Penolong","Penyulit","J.K.","BB/PB","Keadaan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRiwayatKehamilan.setModel(tabModeRiwayatKehamilan);

        tbRiwayatKehamilan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRiwayatKehamilan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbRiwayatKehamilan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(30);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(150);
            }
        }
        tbRiwayatKehamilan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeAPGAR=new DefaultTableModel(null,new Object[]{
                "Tanda","0","1","2","N 1'","N 5'","N 10'"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if ((colIndex==4)||(colIndex==5)||(colIndex==6)) {
                        a=true;
                    }
                    return a;
             }
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }  
        };
        tbAPGAR.setModel(tabModeAPGAR);
        tbAPGAR.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (int i = 0; i < 7; i++) {
            TableColumn column = tbAPGAR.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(146);
            }else if(i==1){
                column.setPreferredWidth(146);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(145);
            }else if(i==4){
                column.setPreferredWidth(28);
            }else if(i==5){
                column.setPreferredWidth(28);
            }else if(i==6){
                column.setPreferredWidth(33);              
            }
        }
        
        tbAPGAR.setRowHeight(22);
        tbAPGAR.setDefaultRenderer(Object.class, new WarnaTable5());
        tabModeAPGAR.addRow(new Object[]{"Frekuensi Jantung","Tidak Ada","< 100","> 100","","",""});
        tabModeAPGAR.addRow(new Object[]{"Usaha Nafas","Tidak Ada","Lambat Tak Teratur","Menangis Kuat","","",""});
        tabModeAPGAR.addRow(new Object[]{"Tanus Otot","Lumpuh","Ext. Fleksi Sedikit","Gerakan Aktif","","",""});
        tabModeAPGAR.addRow(new Object[]{"Refleks","Tidak Ada Respon","Pergerakan Sedikit","Menangis","","",""});
        tabModeAPGAR.addRow(new Object[]{"Warna","Biru Pucat","Tubuh Kemerahan, Tangan & Kaki Biru","Kemerahan","","",""});
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        N1.setDocument(new batasInput((int)2).getOnlyAngka(N1));
        N5.setDocument(new batasInput((int)2).getOnlyAngka(N5));
        N10.setDocument(new batasInput((int)2).getOnlyAngka(N10));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        TempatPersalinan.setDocument(new batasInput((byte)30).getKata(TempatPersalinan));
        UsiaHamil.setDocument(new batasInput((byte)20).getKata(UsiaHamil));
        JenisPersalinan.setDocument(new batasInput((byte)20).getKata(JenisPersalinan));
        Penolong.setDocument(new batasInput((byte)30).getKata(Penolong));
        Penyulit.setDocument(new batasInput((byte)40).getKata(Penyulit));
        BBPB.setDocument(new batasInput((byte)10).getKata(BBPB));
        Keadaan.setDocument(new batasInput((byte)40).getKata(Keadaan));
        
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
        
        ibubayi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ibubayi.getTable().getSelectedRow()!= -1){                   
                    NoRMIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),0).toString());
                    NmIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),1).toString());
                    TglLahirIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),5).toString());
                    NIKIbu.setText(ibubayi.getTable().getValueAt(ibubayi.getTable().getSelectedRow(),2).toString());
                    tampilPersalinan();
                }  
                BtnIbuBayi.requestFocus();
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
        
        ibubayi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    ibubayi.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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

        LoadHTML = new widget.editorpane();
        DlgRiwayatPersalinan = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        TanggalPersalinan = new widget.Tanggal();
        jLabel100 = new widget.Label();
        BtnKeluarKehamilan = new widget.Button();
        jLabel107 = new widget.Label();
        UsiaHamil = new widget.TextBox();
        BtnSimpanRiwayatKehamilan = new widget.Button();
        jLabel108 = new widget.Label();
        TempatPersalinan = new widget.TextBox();
        jLabel109 = new widget.Label();
        JenisPersalinan = new widget.TextBox();
        jLabel110 = new widget.Label();
        JK = new widget.ComboBox();
        jLabel111 = new widget.Label();
        Penolong = new widget.TextBox();
        jLabel112 = new widget.Label();
        BBPB = new widget.TextBox();
        jLabel113 = new widget.Label();
        Penyulit = new widget.TextBox();
        jLabel114 = new widget.Label();
        Keadaan = new widget.TextBox();
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
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        Scroll6 = new widget.ScrollPane();
        tbRiwayatKehamilan = new widget.Table();
        BtnTambahMasalah = new widget.Button();
        BtnHapusRiwayatPersalinan = new widget.Button();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel115 = new widget.Label();
        jLabel74 = new widget.Label();
        Scroll2 = new widget.ScrollPane();
        tbAPGAR = new widget.Table();
        label71 = new widget.Label();
        N1 = new widget.TextBox2();
        N5 = new widget.TextBox2();
        N10 = new widget.TextBox2();
        jLabel14 = new widget.Label();
        NoRMIbu = new widget.TextBox();
        NmIbu = new widget.TextBox();
        BtnIbuBayi = new widget.Button();
        jLabel9 = new widget.Label();
        TglLahirIbu = new widget.TextBox();
        jLabel15 = new widget.Label();
        NIKIbu = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        NmIbu1 = new widget.TextBox();
        jLabel38 = new widget.Label();
        HbsAg = new widget.ComboBox();
        TglLahirIbu1 = new widget.TextBox();
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

        DlgRiwayatPersalinan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgRiwayatPersalinan.setName("DlgRiwayatPersalinan"); // NOI18N
        DlgRiwayatPersalinan.setUndecorated(true);
        DlgRiwayatPersalinan.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Riwayat Persalinan Ibu Bayi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        TanggalPersalinan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-02-2025" }));
        TanggalPersalinan.setDisplayFormat("dd-MM-yyyy");
        TanggalPersalinan.setName("TanggalPersalinan"); // NOI18N
        TanggalPersalinan.setOpaque(false);
        panelBiasa2.add(TanggalPersalinan);
        TanggalPersalinan.setBounds(530, 10, 95, 23);

        jLabel100.setText("Tempat Persalinan :");
        jLabel100.setName("jLabel100"); // NOI18N
        panelBiasa2.add(jLabel100);
        jLabel100.setBounds(0, 10, 110, 23);

        BtnKeluarKehamilan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarKehamilan.setMnemonic('U');
        BtnKeluarKehamilan.setText("Tutup");
        BtnKeluarKehamilan.setToolTipText("Alt+U");
        BtnKeluarKehamilan.setName("BtnKeluarKehamilan"); // NOI18N
        BtnKeluarKehamilan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarKehamilan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarKehamilanActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluarKehamilan);
        BtnKeluarKehamilan.setBounds(530, 130, 100, 30);

        jLabel107.setText("Usia Hamil :");
        jLabel107.setName("jLabel107"); // NOI18N
        panelBiasa2.add(jLabel107);
        jLabel107.setBounds(430, 40, 96, 23);

        UsiaHamil.setHighlighter(null);
        UsiaHamil.setName("UsiaHamil"); // NOI18N
        UsiaHamil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaHamilKeyPressed(evt);
            }
        });
        panelBiasa2.add(UsiaHamil);
        UsiaHamil.setBounds(530, 40, 95, 23);

        BtnSimpanRiwayatKehamilan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanRiwayatKehamilan.setMnemonic('S');
        BtnSimpanRiwayatKehamilan.setText("Simpan");
        BtnSimpanRiwayatKehamilan.setToolTipText("Alt+S");
        BtnSimpanRiwayatKehamilan.setName("BtnSimpanRiwayatKehamilan"); // NOI18N
        BtnSimpanRiwayatKehamilan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanRiwayatKehamilanActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSimpanRiwayatKehamilan);
        BtnSimpanRiwayatKehamilan.setBounds(420, 130, 100, 30);

        jLabel108.setText("Tanggal/Tahun :");
        jLabel108.setName("jLabel108"); // NOI18N
        panelBiasa2.add(jLabel108);
        jLabel108.setBounds(430, 10, 96, 23);

        TempatPersalinan.setHighlighter(null);
        TempatPersalinan.setName("TempatPersalinan"); // NOI18N
        TempatPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatPersalinanKeyPressed(evt);
            }
        });
        panelBiasa2.add(TempatPersalinan);
        TempatPersalinan.setBounds(114, 10, 290, 23);

        jLabel109.setText("Jenis Persalinan :");
        jLabel109.setName("jLabel109"); // NOI18N
        panelBiasa2.add(jLabel109);
        jLabel109.setBounds(0, 40, 110, 23);

        JenisPersalinan.setHighlighter(null);
        JenisPersalinan.setName("JenisPersalinan"); // NOI18N
        JenisPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPersalinanKeyPressed(evt);
            }
        });
        panelBiasa2.add(JenisPersalinan);
        JenisPersalinan.setBounds(114, 40, 290, 23);

        jLabel110.setText("Jenis Kelamin :");
        jLabel110.setName("jLabel110"); // NOI18N
        panelBiasa2.add(jLabel110);
        jLabel110.setBounds(430, 70, 96, 23);

        JK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-Laki", "Perempuan", "-" }));
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });
        panelBiasa2.add(JK);
        JK.setBounds(530, 70, 95, 23);

        jLabel111.setText("Penolong :");
        jLabel111.setName("jLabel111"); // NOI18N
        panelBiasa2.add(jLabel111);
        jLabel111.setBounds(0, 70, 110, 23);

        Penolong.setHighlighter(null);
        Penolong.setName("Penolong"); // NOI18N
        Penolong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenolongKeyPressed(evt);
            }
        });
        panelBiasa2.add(Penolong);
        Penolong.setBounds(114, 70, 290, 23);

        jLabel112.setText("BB/PB :");
        jLabel112.setName("jLabel112"); // NOI18N
        panelBiasa2.add(jLabel112);
        jLabel112.setBounds(430, 100, 96, 23);

        BBPB.setHighlighter(null);
        BBPB.setName("BBPB"); // NOI18N
        BBPB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBPBKeyPressed(evt);
            }
        });
        panelBiasa2.add(BBPB);
        BBPB.setBounds(530, 100, 95, 23);

        jLabel113.setText("Penyulit :");
        jLabel113.setName("jLabel113"); // NOI18N
        panelBiasa2.add(jLabel113);
        jLabel113.setBounds(0, 100, 110, 23);

        Penyulit.setHighlighter(null);
        Penyulit.setName("Penyulit"); // NOI18N
        Penyulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyulitKeyPressed(evt);
            }
        });
        panelBiasa2.add(Penyulit);
        Penyulit.setBounds(114, 100, 290, 23);

        jLabel114.setText("Keadaan :");
        jLabel114.setName("jLabel114"); // NOI18N
        panelBiasa2.add(jLabel114);
        jLabel114.setBounds(0, 130, 110, 23);

        Keadaan.setHighlighter(null);
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        panelBiasa2.add(Keadaan);
        Keadaan.setBounds(114, 130, 290, 23);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgRiwayatPersalinan.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Bayi Baru Lahir ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
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
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1703));
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

        label14.setText("Dokter PJ :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 130, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(206, 40, 417, 23);

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
        BtnDokter.setBounds(625, 40, 28, 23);

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
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 880, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. RIWAYAT MATERNAL");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 100, 180, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(668, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-02-2025 14:57:21" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(724, 40, 130, 23);

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbRiwayatKehamilan.setName("tbRiwayatKehamilan"); // NOI18N
        Scroll6.setViewportView(tbRiwayatKehamilan);

        FormInput.add(Scroll6);
        Scroll6.setBounds(110, 170, 744, 93);

        BtnTambahMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahMasalah.setMnemonic('3');
        BtnTambahMasalah.setToolTipText("Alt+3");
        BtnTambahMasalah.setName("BtnTambahMasalah"); // NOI18N
        BtnTambahMasalah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahMasalahActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahMasalah);
        BtnTambahMasalah.setBounds(77, 170, 28, 23);

        BtnHapusRiwayatPersalinan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusRiwayatPersalinan.setMnemonic('3');
        BtnHapusRiwayatPersalinan.setToolTipText("Alt+3");
        BtnHapusRiwayatPersalinan.setName("BtnHapusRiwayatPersalinan"); // NOI18N
        BtnHapusRiwayatPersalinan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapusRiwayatPersalinan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusRiwayatPersalinanActionPerformed(evt);
            }
        });
        FormInput.add(BtnHapusRiwayatPersalinan);
        BtnHapusRiwayatPersalinan.setBounds(77, 200, 28, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 380, 880, 1);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("II. PEMERIKSAAN FISIK");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(10, 380, 180, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("APGAR Score :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(44, 510, 182, 23);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        Scroll2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Scroll2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        Scroll2.setName("Scroll2"); // NOI18N

        tbAPGAR.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbAPGAR.setAutoscrolls(false);
        tbAPGAR.setGridColor(new java.awt.Color(150, 150, 150));
        tbAPGAR.setName("tbAPGAR"); // NOI18N
        tbAPGAR.setRowHeight(150);
        tbAPGAR.getTableHeader().setResizingAllowed(false);
        tbAPGAR.getTableHeader().setReorderingAllowed(false);
        tbAPGAR.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbAPGARPropertyChange(evt);
            }
        });
        Scroll2.setViewportView(tbAPGAR);

        FormInput.add(Scroll2);
        Scroll2.setBounds(77, 530, 777, 127);

        label71.setText("Jumlah Nilai :");
        label71.setName("label71"); // NOI18N
        label71.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label71);
        label71.setBounds(660, 656, 100, 27);

        N1.setEditable(false);
        N1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N1.setName("N1"); // NOI18N
        FormInput.add(N1);
        N1.setBounds(764, 656, 29, 27);

        N5.setEditable(false);
        N5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N5.setName("N5"); // NOI18N
        FormInput.add(N5);
        N5.setBounds(792, 656, 29, 27);

        N10.setEditable(false);
        N10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150)));
        N10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        N10.setName("N10"); // NOI18N
        FormInput.add(N10);
        N10.setBounds(820, 656, 34, 27);

        jLabel14.setText("Ibu Bayi :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 70, 70, 23);

        NoRMIbu.setEditable(false);
        NoRMIbu.setHighlighter(null);
        NoRMIbu.setName("NoRMIbu"); // NOI18N
        FormInput.add(NoRMIbu);
        NoRMIbu.setBounds(74, 70, 100, 23);

        NmIbu.setEditable(false);
        NmIbu.setHighlighter(null);
        NmIbu.setName("NmIbu"); // NOI18N
        FormInput.add(NmIbu);
        NmIbu.setBounds(176, 70, 270, 23);

        BtnIbuBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnIbuBayi.setMnemonic('2');
        BtnIbuBayi.setToolTipText("Alt+2");
        BtnIbuBayi.setName("BtnIbuBayi"); // NOI18N
        BtnIbuBayi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnIbuBayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIbuBayiActionPerformed(evt);
            }
        });
        BtnIbuBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnIbuBayiKeyPressed(evt);
            }
        });
        FormInput.add(BtnIbuBayi);
        BtnIbuBayi.setBounds(448, 70, 28, 23);

        jLabel9.setText("Tgl.Lahir Ibu :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(479, 70, 90, 23);

        TglLahirIbu.setEditable(false);
        TglLahirIbu.setHighlighter(null);
        TglLahirIbu.setName("TglLahirIbu"); // NOI18N
        FormInput.add(TglLahirIbu);
        TglLahirIbu.setBounds(573, 70, 80, 23);

        jLabel15.setText("NIK Ibu :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(660, 70, 60, 23);

        NIKIbu.setEditable(false);
        NIKIbu.setHighlighter(null);
        NIKIbu.setName("NIKIbu"); // NOI18N
        FormInput.add(NIKIbu);
        NIKIbu.setBounds(724, 70, 130, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Status Perkawinan Ibu");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(44, 120, 120, 23);

        jLabel17.setText(":");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 120, 160, 23);

        NmIbu1.setEditable(false);
        NmIbu1.setHighlighter(null);
        NmIbu1.setName("NmIbu1"); // NOI18N
        FormInput.add(NmIbu1);
        NmIbu1.setBounds(164, 120, 110, 23);

        jLabel38.setText("Penyakit Yang Diderita Ibu :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(275, 120, 150, 23);

        HbsAg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        HbsAg.setName("HbsAg"); // NOI18N
        HbsAg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HbsAgKeyPressed(evt);
            }
        });
        FormInput.add(HbsAg);
        HbsAg.setBounds(429, 120, 100, 23);

        TglLahirIbu1.setHighlighter(null);
        TglLahirIbu1.setName("TglLahirIbu1"); // NOI18N
        FormInput.add(TglLahirIbu1);
        TglLahirIbu1.setBounds(532, 120, 322, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-02-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-02-2025" }));
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

        TabRawat.addTab("Data Penilaian", internalFrame3);

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
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(NoRMIbu.getText().trim().equals("")){
            Valid.textKosong(BtnIbuBayi,"Ibu Bayi");
        }else if(tbAPGAR.getValueAt(0,4).toString().equals("")||tbAPGAR.getValueAt(1,4).toString().equals("")||tbAPGAR.getValueAt(2,4).toString().equals("")||
                tbAPGAR.getValueAt(3,4).toString().equals("")||tbAPGAR.getValueAt(4,4).toString().equals("")||tbAPGAR.getValueAt(0,5).toString().equals("")||
                tbAPGAR.getValueAt(1,5).toString().equals("")||tbAPGAR.getValueAt(2,5).toString().equals("")||tbAPGAR.getValueAt(3,5).toString().equals("")||
                tbAPGAR.getValueAt(4,5).toString().equals("")||tbAPGAR.getValueAt(0,6).toString().equals("")||tbAPGAR.getValueAt(1,6).toString().equals("")||
                tbAPGAR.getValueAt(2,6).toString().equals("")||tbAPGAR.getValueAt(3,6).toString().equals("")||tbAPGAR.getValueAt(4,6).toString().equals("")){
            JOptionPane.showMessageDialog(null,"Nilai APGAR harus valid...!!!");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,KetFisik,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString(),Sequel.ambiltanggalsekarang())==true){
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
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(NoRMIbu.getText().trim().equals("")){
            Valid.textKosong(BtnIbuBayi,"Ibu Bayi");
        }else if(tbAPGAR.getValueAt(0,4).toString().equals("")||tbAPGAR.getValueAt(1,4).toString().equals("")||tbAPGAR.getValueAt(2,4).toString().equals("")||
                tbAPGAR.getValueAt(3,4).toString().equals("")||tbAPGAR.getValueAt(4,4).toString().equals("")||tbAPGAR.getValueAt(0,5).toString().equals("")||
                tbAPGAR.getValueAt(1,5).toString().equals("")||tbAPGAR.getValueAt(2,5).toString().equals("")||tbAPGAR.getValueAt(3,5).toString().equals("")||
                tbAPGAR.getValueAt(4,5).toString().equals("")||tbAPGAR.getValueAt(0,6).toString().equals("")||tbAPGAR.getValueAt(1,6).toString().equals("")||
                tbAPGAR.getValueAt(2,6).toString().equals("")||tbAPGAR.getValueAt(3,6).toString().equals("")||tbAPGAR.getValueAt(4,6).toString().equals("")){
            JOptionPane.showMessageDialog(null,"Nilai APGAR harus valid...!!!");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19))==true){
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIK Bayi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM IBU</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIK Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>G</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>P</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>A</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hidup</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Usia Hamil</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>HbsAg</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>HIV/AIDS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Syphilis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Riwayat Obstetri Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Riwayat Obstetri Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Neonatal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Faktor Risiko Neonatal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl & Jam Persalinan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bersalin Di</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Inisiasi Menyusui Dini</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Persalinan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Indikasi/Keterangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aterm</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bernafas/Menangis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tonus Otot Baik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cairan Amnion Jernih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>F 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>U 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>W 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N 1'</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>F 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>U 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>W 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N 5'</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>F 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>U 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>T 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>R 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>W 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N 10'</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Frekuensi Napas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.F.N</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Retraksi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sianosis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.S</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jalan Masuk Udara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.J.M</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Grunting</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.G</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ttl.D.S</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Down Score</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nadi(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>RR(x/menit)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Saturasi O2(%)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BB(gram)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PB(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>LK(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>LD(cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kulit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kulit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Mata</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Telinga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Telinga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hidung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Hidung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mulut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Mulut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tenggorokan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Tenggorokan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Leher</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Thorax</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Thorax</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Abdomen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Genitalia</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Genitalia</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Anus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Anus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Muskuloskeletal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Muskuloskeletal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ekstrimitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Ekstrimitas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Paru</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Paru</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Refleks Primitif</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Refleks Primitif</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kelainan Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pemeriksaan Regional/Khusus/Tambahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Laboratorium</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penunjang Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosis/Asesmen</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tatalaksana</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Edukasi</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,72).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,73).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,74).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,75).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,76).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,77).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,78).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,79).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,80).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,81).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,82).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,83).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,84).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,85).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,86).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,87).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,88).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,89).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,90).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,91).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,92).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,93).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,94).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,95).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,96).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,97).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,98).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,99).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,100).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,101).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,102).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,103).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,104).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,105).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,106).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,107).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,108).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,109).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,110).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,111).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,112).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,113).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,114).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='10000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianAwalMedisRanapNeonatus.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='10000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT INAP NEONATUS<br><br></font>"+        
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
                tampilPersalinan();
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
                    tampilPersalinan();
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
        Valid.pindah(evt,BtnSimpan,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah2(evt,BtnDokter,BtnIbuBayi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        if(NoRMIbu.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu ibu bayi yang mau dimasukkan data kelahirannya...");
            //HbsAg.requestFocus();
        }else{
            emptTeksPersalinan();
            DlgRiwayatPersalinan.setLocationRelativeTo(internalFrame1);
            DlgRiwayatPersalinan.setVisible(true);
        }
    }//GEN-LAST:event_BtnTambahMasalahActionPerformed

    private void BtnHapusRiwayatPersalinanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusRiwayatPersalinanActionPerformed
        if(tbRiwayatKehamilan.getSelectedRow()>-1){
            Sequel.meghapus("riwayat_persalinan_pasien","no_rkm_medis","tgl_thn",NoRMIbu.getText(),tbRiwayatKehamilan.getValueAt(tbRiwayatKehamilan.getSelectedRow(),1).toString());
            tampilPersalinan();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnHapusRiwayatPersalinanActionPerformed

    private void BtnKeluarKehamilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarKehamilanActionPerformed
        DlgRiwayatPersalinan.dispose();
    }//GEN-LAST:event_BtnKeluarKehamilanActionPerformed

    private void UsiaHamilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaHamilKeyPressed
        Valid.pindah(evt,Keadaan,JK);
    }//GEN-LAST:event_UsiaHamilKeyPressed

    private void BtnSimpanRiwayatKehamilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanRiwayatKehamilanActionPerformed
        if(TempatPersalinan.getText().trim().equals("")){
            Valid.textKosong(TempatPersalinan,"Tempat Persalinan");
        }else if(JenisPersalinan.getText().trim().equals("")){
            Valid.textKosong(JenisPersalinan,"Jenis Persalinan");
        }else if(Penolong.getText().trim().equals("")){
            Valid.textKosong(Penolong,"Penolong Persalinan");
        }else if(Penyulit.getText().trim().equals("")){
            Valid.textKosong(Penyulit,"Penyulit Persalinan");
        }else if(Keadaan.getText().trim().equals("")){
            Valid.textKosong(Keadaan,"Keadaan Persalinan");
        }else if(UsiaHamil.getText().trim().equals("")){
            Valid.textKosong(UsiaHamil,"Usia Hamil");
        }else if(BBPB.getText().trim().equals("")){
            Valid.textKosong(BBPB,"BB/PB");
        }else{
            if(Sequel.menyimpantf("riwayat_persalinan_pasien","?,?,?,?,?,?,?,?,?,?","Riwayat Persalinan",10,new String[]{
                NoRMIbu.getText(),Valid.SetTgl(TanggalPersalinan.getSelectedItem()+""),TempatPersalinan.getText(),UsiaHamil.getText(),JenisPersalinan.getText(),Penolong.getText(),Penyulit.getText(),JK.getSelectedItem().toString().substring(0,1),BBPB.getText(),Keadaan.getText()
            })==true){
                emptTeksPersalinan();
                tampilPersalinan();
            }
        }
    }//GEN-LAST:event_BtnSimpanRiwayatKehamilanActionPerformed

    private void TempatPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatPersalinanKeyPressed
        Valid.pindah(evt,BtnKeluarKehamilan,JenisPersalinan);
    }//GEN-LAST:event_TempatPersalinanKeyPressed

    private void JenisPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPersalinanKeyPressed
        Valid.pindah(evt,TempatPersalinan,Penolong);
    }//GEN-LAST:event_JenisPersalinanKeyPressed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        Valid.pindah(evt,UsiaHamil,BBPB);
    }//GEN-LAST:event_JKKeyPressed

    private void PenolongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenolongKeyPressed
        Valid.pindah(evt,JenisPersalinan,Penyulit);
    }//GEN-LAST:event_PenolongKeyPressed

    private void BBPBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBPBKeyPressed
        Valid.pindah(evt,JK,BtnSimpanRiwayatKehamilan);
    }//GEN-LAST:event_BBPBKeyPressed

    private void PenyulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyulitKeyPressed
        Valid.pindah(evt,Penolong,Keadaan);
    }//GEN-LAST:event_PenyulitKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,Penyulit,UsiaHamil);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void tbAPGARPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAPGARPropertyChange
        if(this.isVisible()==true){
            getDataApgar();
        }
    }//GEN-LAST:event_tbAPGARPropertyChange

    private void BtnIbuBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIbuBayiActionPerformed
        ibubayi.emptTeks();
        ibubayi.isCek();
        ibubayi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ibubayi.setLocationRelativeTo(internalFrame1);
        ibubayi.setAlwaysOnTop(false);
        ibubayi.setVisible(true);
    }//GEN-LAST:event_BtnIbuBayiActionPerformed

    private void BtnIbuBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnIbuBayiKeyPressed
        //Valid.pindah(evt,TglAsuhan,G);
    }//GEN-LAST:event_BtnIbuBayiKeyPressed

    private void HbsAgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HbsAgKeyPressed
        //Valid.pindah(evt,UsiaKehamilan,HIV);
    }//GEN-LAST:event_HbsAgKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianBayiBaruLahir dialog = new RMPenilaianBayiBaruLahir(new javax.swing.JFrame(), true);
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
    private widget.TextBox BBPB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusRiwayatPersalinan;
    private widget.Button BtnIbuBayi;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarKehamilan;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanRiwayatKehamilan;
    private widget.Button BtnTambahMasalah;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JDialog DlgRiwayatPersalinan;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HbsAg;
    private widget.ComboBox JK;
    private widget.TextBox JenisPersalinan;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox Keadaan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox2 N1;
    private widget.TextBox2 N10;
    private widget.TextBox2 N5;
    private widget.TextBox NIKIbu;
    private widget.TextBox NmDokter;
    private widget.TextBox NmIbu;
    private widget.TextBox NmIbu1;
    private widget.TextBox NoRMIbu;
    private widget.TextBox Penolong;
    private widget.TextBox Penyulit;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalPersalinan;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TempatPersalinan;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox TglLahirIbu;
    private widget.TextBox TglLahirIbu1;
    private widget.TextBox UsiaHamil;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel107;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel114;
    private widget.Label jLabel115;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel38;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel74;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel99;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label71;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbAPGAR;
    private widget.Table tbObat;
    private widget.Table tbRiwayatKehamilan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_ktp,penilaian_medis_ranap_neonatus.no_rkm_medis_ibu,ibupasien.nm_pasien as nama_ibu,ibupasien.tgl_lahir as lahiribu,ibupasien.no_ktp as ktpibu,"+
                        "penilaian_medis_ranap_neonatus.tanggal,penilaian_medis_ranap_neonatus.kd_dokter,dokter.nm_dokter,penilaian_medis_ranap_neonatus.g,penilaian_medis_ranap_neonatus.p,penilaian_medis_ranap_neonatus.a,penilaian_medis_ranap_neonatus.hidup,penilaian_medis_ranap_neonatus.usiahamil,penilaian_medis_ranap_neonatus.hbsag,penilaian_medis_ranap_neonatus.hiv,"+
                        "penilaian_medis_ranap_neonatus.syphilis,penilaian_medis_ranap_neonatus.riwayat_obstetri_ibu,penilaian_medis_ranap_neonatus.keterangan_riwayat_obstetri_ibu,penilaian_medis_ranap_neonatus.faktor_risiko_neonatal,penilaian_medis_ranap_neonatus.keterangan_faktor_risiko_neonatal,penilaian_medis_ranap_neonatus.tanggal_persalinan,"+
                        "penilaian_medis_ranap_neonatus.bersalin_di,penilaian_medis_ranap_neonatus.inisiasi_menyusui,penilaian_medis_ranap_neonatus.jenis_persalinan,penilaian_medis_ranap_neonatus.indikasi,penilaian_medis_ranap_neonatus.aterm,penilaian_medis_ranap_neonatus.bernafas,penilaian_medis_ranap_neonatus.tanus_otot,penilaian_medis_ranap_neonatus.cairan_amnion,"+
                        "penilaian_medis_ranap_neonatus.f1,penilaian_medis_ranap_neonatus.u1,penilaian_medis_ranap_neonatus.t1,penilaian_medis_ranap_neonatus.r1,penilaian_medis_ranap_neonatus.w1,penilaian_medis_ranap_neonatus.n1,penilaian_medis_ranap_neonatus.f5,penilaian_medis_ranap_neonatus.u5,penilaian_medis_ranap_neonatus.t5,penilaian_medis_ranap_neonatus.r5,"+
                        "penilaian_medis_ranap_neonatus.w5,penilaian_medis_ranap_neonatus.n5,penilaian_medis_ranap_neonatus.f10,penilaian_medis_ranap_neonatus.u10,penilaian_medis_ranap_neonatus.t10,penilaian_medis_ranap_neonatus.r10,penilaian_medis_ranap_neonatus.w10,penilaian_medis_ranap_neonatus.n10,penilaian_medis_ranap_neonatus.frekuensi_napas,"+
                        "penilaian_medis_ranap_neonatus.nilai_frekuensi_napas,penilaian_medis_ranap_neonatus.retraksi,penilaian_medis_ranap_neonatus.nilai_retraksi,penilaian_medis_ranap_neonatus.sianosis,penilaian_medis_ranap_neonatus.nilai_sianosis,penilaian_medis_ranap_neonatus.jalan_masuk_udara,penilaian_medis_ranap_neonatus.nilai_jalan_masuk_udara,"+
                        "penilaian_medis_ranap_neonatus.grunting,penilaian_medis_ranap_neonatus.nilai_grunting,penilaian_medis_ranap_neonatus.total_down_score,penilaian_medis_ranap_neonatus.keterangan_down_Score,penilaian_medis_ranap_neonatus.nadi,penilaian_medis_ranap_neonatus.rr,penilaian_medis_ranap_neonatus.suhu,penilaian_medis_ranap_neonatus.saturasi,"+
                        "penilaian_medis_ranap_neonatus.bb,penilaian_medis_ranap_neonatus.pb,penilaian_medis_ranap_neonatus.lk,penilaian_medis_ranap_neonatus.ld,penilaian_medis_ranap_neonatus.keadaan_umum,penilaian_medis_ranap_neonatus.keterangan_keadaan_umum,penilaian_medis_ranap_neonatus.kulit,penilaian_medis_ranap_neonatus.keterangan_kulit,"+
                        "penilaian_medis_ranap_neonatus.kepala,penilaian_medis_ranap_neonatus.keterangan_kepala,penilaian_medis_ranap_neonatus.mata,penilaian_medis_ranap_neonatus.keterangan_mata,penilaian_medis_ranap_neonatus.telinga,penilaian_medis_ranap_neonatus.keterangan_telinga,penilaian_medis_ranap_neonatus.hidung,penilaian_medis_ranap_neonatus.keterangan_hidung,"+
                        "penilaian_medis_ranap_neonatus.mulut,penilaian_medis_ranap_neonatus.keterangan_mulut,penilaian_medis_ranap_neonatus.tenggorokan,penilaian_medis_ranap_neonatus.keterangan_tenggorokan,penilaian_medis_ranap_neonatus.leher,penilaian_medis_ranap_neonatus.keterangan_leher,penilaian_medis_ranap_neonatus.thorax,"+
                        "penilaian_medis_ranap_neonatus.keterangan_thorax,penilaian_medis_ranap_neonatus.abdomen,penilaian_medis_ranap_neonatus.keterangan_abdomen,penilaian_medis_ranap_neonatus.genitalia,penilaian_medis_ranap_neonatus.keterangan_genitalia,penilaian_medis_ranap_neonatus.anus,penilaian_medis_ranap_neonatus.keterangan_anus,"+
                        "penilaian_medis_ranap_neonatus.muskulos,penilaian_medis_ranap_neonatus.keterangan_muskulos,penilaian_medis_ranap_neonatus.ekstrimitas,penilaian_medis_ranap_neonatus.keterangan_ekstrimitas,penilaian_medis_ranap_neonatus.paru,penilaian_medis_ranap_neonatus.keterangan_paru,penilaian_medis_ranap_neonatus.refleks,"+
                        "penilaian_medis_ranap_neonatus.keterangan_refleks,penilaian_medis_ranap_neonatus.kelainan_lainnya,penilaian_medis_ranap_neonatus.pemeriksaan_regional,penilaian_medis_ranap_neonatus.lab,penilaian_medis_ranap_neonatus.radiologi,penilaian_medis_ranap_neonatus.penunjanglainnya,penilaian_medis_ranap_neonatus.diagnosis,"+
                        "penilaian_medis_ranap_neonatus.tata,penilaian_medis_ranap_neonatus.edukasi from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap_neonatus on reg_periksa.no_rawat=penilaian_medis_ranap_neonatus.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter "+
                        "inner join pasien as ibupasien on ibupasien.no_rkm_medis=penilaian_medis_ranap_neonatus.no_rkm_medis_ibu where "+
                        "penilaian_medis_ranap_neonatus.tanggal between ? and ? order by penilaian_medis_ranap_neonatus.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_ktp,penilaian_medis_ranap_neonatus.no_rkm_medis_ibu,ibupasien.nm_pasien as nama_ibu,ibupasien.tgl_lahir as lahiribu,ibupasien.no_ktp as ktpibu,"+
                        "penilaian_medis_ranap_neonatus.tanggal,penilaian_medis_ranap_neonatus.kd_dokter,dokter.nm_dokter,penilaian_medis_ranap_neonatus.g,penilaian_medis_ranap_neonatus.p,penilaian_medis_ranap_neonatus.a,penilaian_medis_ranap_neonatus.hidup,penilaian_medis_ranap_neonatus.usiahamil,penilaian_medis_ranap_neonatus.hbsag,penilaian_medis_ranap_neonatus.hiv,"+
                        "penilaian_medis_ranap_neonatus.syphilis,penilaian_medis_ranap_neonatus.riwayat_obstetri_ibu,penilaian_medis_ranap_neonatus.keterangan_riwayat_obstetri_ibu,penilaian_medis_ranap_neonatus.faktor_risiko_neonatal,penilaian_medis_ranap_neonatus.keterangan_faktor_risiko_neonatal,penilaian_medis_ranap_neonatus.tanggal_persalinan,"+
                        "penilaian_medis_ranap_neonatus.bersalin_di,penilaian_medis_ranap_neonatus.inisiasi_menyusui,penilaian_medis_ranap_neonatus.jenis_persalinan,penilaian_medis_ranap_neonatus.indikasi,penilaian_medis_ranap_neonatus.aterm,penilaian_medis_ranap_neonatus.bernafas,penilaian_medis_ranap_neonatus.tanus_otot,penilaian_medis_ranap_neonatus.cairan_amnion,"+
                        "penilaian_medis_ranap_neonatus.f1,penilaian_medis_ranap_neonatus.u1,penilaian_medis_ranap_neonatus.t1,penilaian_medis_ranap_neonatus.r1,penilaian_medis_ranap_neonatus.w1,penilaian_medis_ranap_neonatus.n1,penilaian_medis_ranap_neonatus.f5,penilaian_medis_ranap_neonatus.u5,penilaian_medis_ranap_neonatus.t5,penilaian_medis_ranap_neonatus.r5,"+
                        "penilaian_medis_ranap_neonatus.w5,penilaian_medis_ranap_neonatus.n5,penilaian_medis_ranap_neonatus.f10,penilaian_medis_ranap_neonatus.u10,penilaian_medis_ranap_neonatus.t10,penilaian_medis_ranap_neonatus.r10,penilaian_medis_ranap_neonatus.w10,penilaian_medis_ranap_neonatus.n10,penilaian_medis_ranap_neonatus.frekuensi_napas,"+
                        "penilaian_medis_ranap_neonatus.nilai_frekuensi_napas,penilaian_medis_ranap_neonatus.retraksi,penilaian_medis_ranap_neonatus.nilai_retraksi,penilaian_medis_ranap_neonatus.sianosis,penilaian_medis_ranap_neonatus.nilai_sianosis,penilaian_medis_ranap_neonatus.jalan_masuk_udara,penilaian_medis_ranap_neonatus.nilai_jalan_masuk_udara,"+
                        "penilaian_medis_ranap_neonatus.grunting,penilaian_medis_ranap_neonatus.nilai_grunting,penilaian_medis_ranap_neonatus.total_down_score,penilaian_medis_ranap_neonatus.keterangan_down_Score,penilaian_medis_ranap_neonatus.nadi,penilaian_medis_ranap_neonatus.rr,penilaian_medis_ranap_neonatus.suhu,penilaian_medis_ranap_neonatus.saturasi,"+
                        "penilaian_medis_ranap_neonatus.bb,penilaian_medis_ranap_neonatus.pb,penilaian_medis_ranap_neonatus.lk,penilaian_medis_ranap_neonatus.ld,penilaian_medis_ranap_neonatus.keadaan_umum,penilaian_medis_ranap_neonatus.keterangan_keadaan_umum,penilaian_medis_ranap_neonatus.kulit,penilaian_medis_ranap_neonatus.keterangan_kulit,"+
                        "penilaian_medis_ranap_neonatus.kepala,penilaian_medis_ranap_neonatus.keterangan_kepala,penilaian_medis_ranap_neonatus.mata,penilaian_medis_ranap_neonatus.keterangan_mata,penilaian_medis_ranap_neonatus.telinga,penilaian_medis_ranap_neonatus.keterangan_telinga,penilaian_medis_ranap_neonatus.hidung,penilaian_medis_ranap_neonatus.keterangan_hidung,"+
                        "penilaian_medis_ranap_neonatus.mulut,penilaian_medis_ranap_neonatus.keterangan_mulut,penilaian_medis_ranap_neonatus.tenggorokan,penilaian_medis_ranap_neonatus.keterangan_tenggorokan,penilaian_medis_ranap_neonatus.leher,penilaian_medis_ranap_neonatus.keterangan_leher,penilaian_medis_ranap_neonatus.thorax,"+
                        "penilaian_medis_ranap_neonatus.keterangan_thorax,penilaian_medis_ranap_neonatus.abdomen,penilaian_medis_ranap_neonatus.keterangan_abdomen,penilaian_medis_ranap_neonatus.genitalia,penilaian_medis_ranap_neonatus.keterangan_genitalia,penilaian_medis_ranap_neonatus.anus,penilaian_medis_ranap_neonatus.keterangan_anus,"+
                        "penilaian_medis_ranap_neonatus.muskulos,penilaian_medis_ranap_neonatus.keterangan_muskulos,penilaian_medis_ranap_neonatus.ekstrimitas,penilaian_medis_ranap_neonatus.keterangan_ekstrimitas,penilaian_medis_ranap_neonatus.paru,penilaian_medis_ranap_neonatus.keterangan_paru,penilaian_medis_ranap_neonatus.refleks,"+
                        "penilaian_medis_ranap_neonatus.keterangan_refleks,penilaian_medis_ranap_neonatus.kelainan_lainnya,penilaian_medis_ranap_neonatus.pemeriksaan_regional,penilaian_medis_ranap_neonatus.lab,penilaian_medis_ranap_neonatus.radiologi,penilaian_medis_ranap_neonatus.penunjanglainnya,penilaian_medis_ranap_neonatus.diagnosis,"+
                        "penilaian_medis_ranap_neonatus.tata,penilaian_medis_ranap_neonatus.edukasi from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap_neonatus on reg_periksa.no_rawat=penilaian_medis_ranap_neonatus.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter "+
                        "inner join pasien as ibupasien on ibupasien.no_rkm_medis=penilaian_medis_ranap_neonatus.no_rkm_medis_ibu where "+
                        "penilaian_medis_ranap_neonatus.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ranap_neonatus.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ranap_neonatus.tanggal");
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
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("no_ktp"),
                        rs.getString("no_rkm_medis_ibu"),rs.getString("nama_ibu"),rs.getString("lahiribu"),rs.getString("ktpibu"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("g"),rs.getString("p"),rs.getString("a"),
                        rs.getString("hidup"),rs.getString("usiahamil"),rs.getString("hbsag"),rs.getString("hiv"),rs.getString("syphilis"),rs.getString("riwayat_obstetri_ibu"),rs.getString("keterangan_riwayat_obstetri_ibu"),
                        rs.getString("faktor_risiko_neonatal"),rs.getString("keterangan_faktor_risiko_neonatal"),rs.getString("tanggal_persalinan"),rs.getString("bersalin_di"),rs.getString("inisiasi_menyusui"),rs.getString("jenis_persalinan"),
                        rs.getString("indikasi"),rs.getString("aterm"),rs.getString("bernafas"),rs.getString("tanus_otot"),rs.getString("cairan_amnion"),rs.getString("f1"),rs.getString("u1"),rs.getString("t1"),rs.getString("r1"),
                        rs.getString("w1"),rs.getString("n1"),rs.getString("f5"),rs.getString("u5"),rs.getString("t5"),rs.getString("r5"),rs.getString("w5"),rs.getString("n5"),rs.getString("f10"),rs.getString("u10"),rs.getString("t10"),
                        rs.getString("r10"),rs.getString("w10"),rs.getString("n10"),rs.getString("frekuensi_napas"),rs.getString("nilai_frekuensi_napas"),rs.getString("retraksi"),rs.getString("nilai_retraksi"),rs.getString("sianosis"),
                        rs.getString("nilai_sianosis"),rs.getString("jalan_masuk_udara"),rs.getString("nilai_jalan_masuk_udara"),rs.getString("grunting"),rs.getString("nilai_grunting"),rs.getString("total_down_score"),
                        rs.getString("keterangan_down_Score"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),rs.getString("saturasi"),rs.getString("bb"),rs.getString("pb"),rs.getString("lk"),rs.getString("ld"),
                        rs.getString("keadaan_umum"),rs.getString("keterangan_keadaan_umum"),rs.getString("kulit"),rs.getString("keterangan_kulit"),rs.getString("kepala"),rs.getString("keterangan_kepala"),rs.getString("mata"),
                        rs.getString("keterangan_mata"),rs.getString("telinga"),rs.getString("keterangan_telinga"),rs.getString("hidung"),rs.getString("keterangan_hidung"),rs.getString("mulut"),rs.getString("keterangan_mulut"),
                        rs.getString("tenggorokan"),rs.getString("keterangan_tenggorokan"),rs.getString("leher"),rs.getString("keterangan_leher"),rs.getString("thorax"),rs.getString("keterangan_thorax"),rs.getString("abdomen"),
                        rs.getString("keterangan_abdomen"),rs.getString("genitalia"),rs.getString("keterangan_genitalia"),rs.getString("anus"),rs.getString("keterangan_anus"),rs.getString("muskulos"),rs.getString("keterangan_muskulos"),
                        rs.getString("ekstrimitas"),rs.getString("keterangan_ekstrimitas"),rs.getString("paru"),rs.getString("keterangan_paru"),rs.getString("refleks"),rs.getString("keterangan_refleks"),rs.getString("kelainan_lainnya"),
                        rs.getString("pemeriksaan_regional"),rs.getString("lab"),rs.getString("radiologi"),rs.getString("penunjanglainnya"),rs.getString("diagnosis"),rs.getString("tata"),rs.getString("edukasi")
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
        TglAsuhan.setDate(new Date());
        NoRMIbu.setText("");
        NmIbu.setText("");
        TglLahirIbu.setText("");
        NIKIbu.setText("");
        Valid.tabelKosong(tabModeRiwayatKehamilan);
        Valid.tabelKosong(tabModeAPGAR);
        tabModeAPGAR.addRow(new Object[]{"Frekuensi Jantung","Tidak Ada","< 100","> 100","","",""});
        tabModeAPGAR.addRow(new Object[]{"Usaha Nafas","Tidak Ada","Lambat Tak Teratur","Menangis Kuat","","",""});
        tabModeAPGAR.addRow(new Object[]{"Tanus Otot","Lumpuh","Ext. Fleksi Sedikit","Gerakan Aktif","","",""});
        tabModeAPGAR.addRow(new Object[]{"Refleks","Tidak Ada Respon","Pergerakan Sedikit","Menangis","","",""});
        tabModeAPGAR.addRow(new Object[]{"Warna","Biru Pucat","Tubuh Kemerahan, Tangan & Kaki Biru","Kemerahan","","",""});
        N1.setText("");
        N5.setText("");
        N10.setText("");
        TabRawat.setSelectedIndex(0);
        BtnIbuBayi.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
           TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
           TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
           TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
           TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
           Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
           NoRMIbu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
           NmIbu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
           TglLahirIbu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
           NIKIbu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
           KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
           NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
           Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString(),0,4); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString(),1,4); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString(),2,4); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString(),3,4); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString(),4,4); 
           N1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString()); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString(),0,5); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString(),1,5);
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString(),2,5); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString(),3,5); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString(),4,5); 
           N5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString()); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString(),0,6); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString(),1,6); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString(),2,6); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString(),3,6); 
           tbAPGAR.setValueAt(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString(), 4,6);
           N10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString()); 
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, "+
                    "reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_ktp from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ranap_neonatus());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ranap_neonatus());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ranap_neonatus());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }   
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                TglAsuhan.setEditable(false);
                TglAsuhan.setEnabled(false);
            }
        }
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_ranap_neonatus where no_rawat=?",1,new String[]{
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
        /*if(Sequel.mengedittf("penilaian_medis_ranap_neonatus","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,no_rkm_medis_ibu=?,g=?,p=?,a=?,hidup=?,usiahamil=?,hbsag=?,hiv=?,syphilis=?,riwayat_obstetri_ibu=?,keterangan_riwayat_obstetri_ibu=?,"+
                "faktor_risiko_neonatal=?,keterangan_faktor_risiko_neonatal=?,tanggal_persalinan=?,bersalin_di=?,inisiasi_menyusui=?,jenis_persalinan=?,indikasi=?,aterm=?,bernafas=?,tanus_otot=?,cairan_amnion=?,f1=?,u1=?,t1=?,r1=?,w1=?,n1=?,f5=?,"+
                "u5=?,t5=?,r5=?,w5=?,n5=?,f10=?,u10=?,t10=?,r10=?,w10=?,n10=?,frekuensi_napas=?,nilai_frekuensi_napas=?,retraksi=?,nilai_retraksi=?,sianosis=?,nilai_sianosis=?,jalan_masuk_udara=?,nilai_jalan_masuk_udara=?,grunting=?,nilai_grunting=?,"+
                "total_down_score=?,keterangan_down_Score=?,nadi=?,rr=?,suhu=?,saturasi=?,bb=?,pb=?,lk=?,ld=?,keadaan_umum=?,keterangan_keadaan_umum=?,kulit=?,keterangan_kulit=?,kepala=?,keterangan_kepala=?,mata=?,keterangan_mata=?,telinga=?,"+
                "keterangan_telinga=?,hidung=?,keterangan_hidung=?,mulut=?,keterangan_mulut=?,tenggorokan=?,keterangan_tenggorokan=?,leher=?,keterangan_leher=?,thorax=?,keterangan_thorax=?,abdomen=?,keterangan_abdomen=?,genitalia=?,keterangan_genitalia=?,"+
                "anus=?,keterangan_anus=?,muskulos=?,keterangan_muskulos=?,ekstrimitas=?,keterangan_ekstrimitas=?,paru=?,keterangan_paru=?,refleks=?,keterangan_refleks=?,kelainan_lainnya=?,pemeriksaan_regional=?,lab=?,radiologi=?,penunjanglainnya=?,"+
                "diagnosis=?,tata=?,edukasi=?",106,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),NoRMIbu.getText(),G.getText(),P.getText(),A.getText(),Hidup.getText(),UsiaKehamilan.getText(),HbsAg.getSelectedItem().toString(),HIV.getSelectedItem().toString(), 
                Syphilis.getSelectedItem().toString(),RiwayatObstetri.getSelectedItem().toString(),KeteranganRiwayatObstetri.getText(),FaktorRisikoNeonatal.getSelectedItem().toString(),KeteranganFaktorRisikoNeonatal.getText(),Valid.SetTgl(TglJamPersalinan.getSelectedItem()+"")+" "+TglJamPersalinan.getSelectedItem().toString().substring(11,19), 
                BersalinDi.getText(),InisiasiMenyusui.getSelectedItem().toString(),JenisPersalinanBayi.getSelectedItem().toString(),IndikasiKeteranganPersalinan.getText(),Aterm.getSelectedItem().toString(),Bernafas.getSelectedItem().toString(),TonusOtotBaik.getSelectedItem().toString(),CairanAmnion.getSelectedItem().toString(),
                tbAPGAR.getValueAt(0,4).toString(),tbAPGAR.getValueAt(1,4).toString(),tbAPGAR.getValueAt(2,4).toString(),tbAPGAR.getValueAt(3,4).toString(),tbAPGAR.getValueAt(4,4).toString(),N1.getText(),tbAPGAR.getValueAt(0,5).toString(),tbAPGAR.getValueAt(1,5).toString(),tbAPGAR.getValueAt(2,5).toString(),tbAPGAR.getValueAt(3,5).toString(),
                tbAPGAR.getValueAt(4,5).toString(),N5.getText(),tbAPGAR.getValueAt(0,6).toString(),tbAPGAR.getValueAt(1,6).toString(),tbAPGAR.getValueAt(2,6).toString(),tbAPGAR.getValueAt(3,6).toString(),tbAPGAR.getValueAt(4,6).toString(),N10.getText(),FrekuensiNapas.getSelectedItem().toString(),NilaiFrekuensiNapas.getText(), 
                Retraksi.getSelectedItem().toString(),NilaiRetraksi.getText(),Sianosis.getSelectedItem().toString(),NilaiSianosis.getText(),JalanMasukUdara.getSelectedItem().toString(),NilaiJalanMasukUdara.getText(),Grunting.getSelectedItem().toString(),NilaiGrunting.getText(),TotalNilaiDownScore.getText(),KeteranganDownScore.getText(), 
                Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),BeratBadan.getText(),PanjangBadan.getText(),LingkarKepala.getText(),LingkarDada.getText(),KondisiUmum.getSelectedItem().toString(),KeteranganKondisiUmum.getText(),Kulit.getSelectedItem().toString(),KeteranganKulit.getText(),Kepala.getSelectedItem().toString(), 
                KeteranganKepala.getText(),Mata.getSelectedItem().toString(),KeteranganMata.getText(),Telinga.getSelectedItem().toString(),KeteranganTelinga.getText(),Hidung.getSelectedItem().toString(),KeteranganHidung.getText(),Mulut.getSelectedItem().toString(),KeteranganMulut.getText(),Tenggorokan.getSelectedItem().toString(),
                KeteranganTenggorokan.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Thorax.getSelectedItem().toString(),KeteranganThorax.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Genitalia.getSelectedItem().toString(),KeteranganGenitalia.getText(),Anus.getSelectedItem().toString(), 
                KeteranganAnus.getText(),Muskulos.getSelectedItem().toString(),KeteranganMuskulos.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),Paru.getSelectedItem().toString(),KeteranganParu.getText(),Refleks.getSelectedItem().toString(),KeteranganRefleks.getText(),KelainanLainnya.getText(), 
                PemeriksaanRegional.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(UmurBayi.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NIKBayi.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(NoRMIbu.getText(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(NmIbu.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(TglLahirIbu.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(NIKIbu.getText(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(G.getText(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(P.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(A.getText(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(Hidup.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(UsiaKehamilan.getText(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(HbsAg.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(HIV.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(Syphilis.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(RiwayatObstetri.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(KeteranganRiwayatObstetri.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(FaktorRisikoNeonatal.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(KeteranganFaktorRisikoNeonatal.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(Valid.SetTgl(TglJamPersalinan.getSelectedItem()+"")+" "+TglJamPersalinan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(BersalinDi.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(InisiasiMenyusui.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(JenisPersalinanBayi.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(IndikasiKeteranganPersalinan.getText(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(Aterm.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(Bernafas.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(TonusOtotBaik.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(CairanAmnion.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(tbAPGAR.getValueAt(0,4).toString(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(tbAPGAR.getValueAt(1,4).toString(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(tbAPGAR.getValueAt(2,4).toString(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(tbAPGAR.getValueAt(3,4).toString(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(tbAPGAR.getValueAt(4,4).toString(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(N1.getText(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(tbAPGAR.getValueAt(0,5).toString(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(tbAPGAR.getValueAt(1,5).toString(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(tbAPGAR.getValueAt(2,5).toString(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(tbAPGAR.getValueAt(3,5).toString(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(tbAPGAR.getValueAt(4,5).toString(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(N5.getText(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(tbAPGAR.getValueAt(0,6).toString(),tbObat.getSelectedRow(),47);
               tbObat.setValueAt(tbAPGAR.getValueAt(1,6).toString(),tbObat.getSelectedRow(),48);
               tbObat.setValueAt(tbAPGAR.getValueAt(2,6).toString(),tbObat.getSelectedRow(),49);
               tbObat.setValueAt(tbAPGAR.getValueAt(3,6).toString(),tbObat.getSelectedRow(),50);
               tbObat.setValueAt(tbAPGAR.getValueAt(4,6).toString(),tbObat.getSelectedRow(),51);
               tbObat.setValueAt(N10.getText(),tbObat.getSelectedRow(),52);
               tbObat.setValueAt(FrekuensiNapas.getSelectedItem().toString(),tbObat.getSelectedRow(),53);
               tbObat.setValueAt(NilaiFrekuensiNapas.getText(),tbObat.getSelectedRow(),54);
               tbObat.setValueAt(Retraksi.getSelectedItem().toString(),tbObat.getSelectedRow(),55);
               tbObat.setValueAt(NilaiRetraksi.getText(),tbObat.getSelectedRow(),56);
               tbObat.setValueAt(Sianosis.getSelectedItem().toString(),tbObat.getSelectedRow(),57);
               tbObat.setValueAt(NilaiSianosis.getText(),tbObat.getSelectedRow(),58);
               tbObat.setValueAt(JalanMasukUdara.getSelectedItem().toString(),tbObat.getSelectedRow(),59);
               tbObat.setValueAt(NilaiJalanMasukUdara.getText(),tbObat.getSelectedRow(),60);
               tbObat.setValueAt(Grunting.getSelectedItem().toString(),tbObat.getSelectedRow(),61);
               tbObat.setValueAt(NilaiGrunting.getText(),tbObat.getSelectedRow(),62);
               tbObat.setValueAt(TotalNilaiDownScore.getText(),tbObat.getSelectedRow(),63);
               tbObat.setValueAt(KeteranganDownScore.getText(),tbObat.getSelectedRow(),64);
               tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),65);
               tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),66);
               tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),67);
               tbObat.setValueAt(Saturasi.getText(),tbObat.getSelectedRow(),68);
               tbObat.setValueAt(BeratBadan.getText(),tbObat.getSelectedRow(),69);
               tbObat.setValueAt(PanjangBadan.getText(),tbObat.getSelectedRow(),70);
               tbObat.setValueAt(LingkarKepala.getText(),tbObat.getSelectedRow(),71);
               tbObat.setValueAt(LingkarDada.getText(),tbObat.getSelectedRow(),72);
               tbObat.setValueAt(KondisiUmum.getSelectedItem().toString(),tbObat.getSelectedRow(),73);
               tbObat.setValueAt(KeteranganKondisiUmum.getText(),tbObat.getSelectedRow(),74);
               tbObat.setValueAt(Kulit.getSelectedItem().toString(),tbObat.getSelectedRow(),75);
               tbObat.setValueAt(KeteranganKulit.getText(),tbObat.getSelectedRow(),76);
               tbObat.setValueAt(Kepala.getSelectedItem().toString(),tbObat.getSelectedRow(),77);
               tbObat.setValueAt(KeteranganKepala.getText(),tbObat.getSelectedRow(),78);
               tbObat.setValueAt(Mata.getSelectedItem().toString(),tbObat.getSelectedRow(),79);
               tbObat.setValueAt(KeteranganMata.getText(),tbObat.getSelectedRow(),80);
               tbObat.setValueAt(Telinga.getSelectedItem().toString(),tbObat.getSelectedRow(),81);
               tbObat.setValueAt(KeteranganTelinga.getText(),tbObat.getSelectedRow(),82);
               tbObat.setValueAt(Hidung.getSelectedItem().toString(),tbObat.getSelectedRow(),83);
               tbObat.setValueAt(KeteranganHidung.getText(),tbObat.getSelectedRow(),84);
               tbObat.setValueAt(Mulut.getSelectedItem().toString(),tbObat.getSelectedRow(),85);
               tbObat.setValueAt(KeteranganMulut.getText(),tbObat.getSelectedRow(),86);
               tbObat.setValueAt(Tenggorokan.getSelectedItem().toString(),tbObat.getSelectedRow(),87);
               tbObat.setValueAt(KeteranganTenggorokan.getText(),tbObat.getSelectedRow(),88);
               tbObat.setValueAt(Leher.getSelectedItem().toString(),tbObat.getSelectedRow(),89);
               tbObat.setValueAt(KeteranganLeher.getText(),tbObat.getSelectedRow(),90);
               tbObat.setValueAt(Thorax.getSelectedItem().toString(),tbObat.getSelectedRow(),91);
               tbObat.setValueAt(KeteranganThorax.getText(),tbObat.getSelectedRow(),92);
               tbObat.setValueAt(Abdomen.getSelectedItem().toString(),tbObat.getSelectedRow(),93);
               tbObat.setValueAt(KeteranganAbdomen.getText(),tbObat.getSelectedRow(),94);
               tbObat.setValueAt(Genitalia.getSelectedItem().toString(),tbObat.getSelectedRow(),95);
               tbObat.setValueAt(KeteranganGenitalia.getText(),tbObat.getSelectedRow(),96);
               tbObat.setValueAt(Anus.getSelectedItem().toString(),tbObat.getSelectedRow(),97);
               tbObat.setValueAt(KeteranganAnus.getText(),tbObat.getSelectedRow(),98);
               tbObat.setValueAt(Muskulos.getSelectedItem().toString(),tbObat.getSelectedRow(),99);
               tbObat.setValueAt(KeteranganMuskulos.getText(),tbObat.getSelectedRow(),100);
               tbObat.setValueAt(Ekstrimitas.getSelectedItem().toString(),tbObat.getSelectedRow(),101);
               tbObat.setValueAt(KeteranganEkstrimitas.getText(),tbObat.getSelectedRow(),102);
               tbObat.setValueAt(Paru.getSelectedItem().toString(),tbObat.getSelectedRow(),103);
               tbObat.setValueAt(KeteranganParu.getText(),tbObat.getSelectedRow(),104);
               tbObat.setValueAt(Refleks.getSelectedItem().toString(),tbObat.getSelectedRow(),105);
               tbObat.setValueAt(KeteranganRefleks.getText(),tbObat.getSelectedRow(),106);
               tbObat.setValueAt(KelainanLainnya.getText(),tbObat.getSelectedRow(),107);
               tbObat.setValueAt(PemeriksaanRegional.getText(),tbObat.getSelectedRow(),108);
               tbObat.setValueAt(Laborat.getText(),tbObat.getSelectedRow(),109);
               tbObat.setValueAt(Radiologi.getText(),tbObat.getSelectedRow(),110);
               tbObat.setValueAt(Penunjang.getText(),tbObat.getSelectedRow(),111);
               tbObat.setValueAt(Diagnosis.getText(),tbObat.getSelectedRow(),112);
               tbObat.setValueAt(Tatalaksana.getText(),tbObat.getSelectedRow(),113);
               tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),114);
               TabRawat.setSelectedIndex(1);
        }*/
    }
    
    private void getDataApgar() {
        try {
            if(tbAPGAR.getValueAt(0,4).toString().equals("")||tbAPGAR.getValueAt(1,4).toString().equals("")||tbAPGAR.getValueAt(2,4).toString().equals("")||
                tbAPGAR.getValueAt(3,4).toString().equals("")||tbAPGAR.getValueAt(4,4).toString().equals("")){
                N1.setText("");
            }else{
                N1.setText((Valid.SetInteger(tbAPGAR.getValueAt(0,4).toString())+Valid.SetInteger(tbAPGAR.getValueAt(1,4).toString())+Valid.SetInteger(tbAPGAR.getValueAt(2,4).toString())+Valid.SetInteger(tbAPGAR.getValueAt(3,4).toString())+Valid.SetInteger(tbAPGAR.getValueAt(4,4).toString()))+"");
            }
        } catch (Exception e) {
            N1.setText("");
        }
        
        try {
            if(tbAPGAR.getValueAt(0,5).toString().equals("")||tbAPGAR.getValueAt(1,5).toString().equals("")||tbAPGAR.getValueAt(2,5).toString().equals("")||
                tbAPGAR.getValueAt(3,5).toString().equals("")||tbAPGAR.getValueAt(4,5).toString().equals("")){
                N5.setText("");
            }else{
                N5.setText((Valid.SetInteger(tbAPGAR.getValueAt(0,5).toString())+Valid.SetInteger(tbAPGAR.getValueAt(1,5).toString())+Valid.SetInteger(tbAPGAR.getValueAt(2,5).toString())+Valid.SetInteger(tbAPGAR.getValueAt(3,5).toString())+Valid.SetInteger(tbAPGAR.getValueAt(4,5).toString()))+"");
            }
        } catch (Exception e) {
            N5.setText("");
        }
        
        try {
            if(tbAPGAR.getValueAt(0,6).toString().equals("")||tbAPGAR.getValueAt(1,6).toString().equals("")||tbAPGAR.getValueAt(2,6).toString().equals("")||
                tbAPGAR.getValueAt(3,6).toString().equals("")||tbAPGAR.getValueAt(4,6).toString().equals("")){
                N10.setText("");
            }else{
                N10.setText((Valid.SetInteger(tbAPGAR.getValueAt(0,6).toString())+Valid.SetInteger(tbAPGAR.getValueAt(1,6).toString())+Valid.SetInteger(tbAPGAR.getValueAt(2,6).toString())+Valid.SetInteger(tbAPGAR.getValueAt(3,6).toString())+Valid.SetInteger(tbAPGAR.getValueAt(4,6).toString()))+"");
            }
        } catch (Exception e) {
            N10.setText("");
        }
    }
    
    private void getNilaiDownScore() {
        /*try {
            if(NilaiFrekuensiNapas.getText().equals("")||NilaiRetraksi.getText().equals("")||NilaiSianosis.getText().equals("")||NilaiJalanMasukUdara.getText().equals("")||NilaiGrunting.getText().equals("")){
                TotalNilaiDownScore.setText("0");
            }else{
                TotalNilaiDownScore.setText((Integer.parseInt(NilaiFrekuensiNapas.getText())+Integer.parseInt(NilaiRetraksi.getText())+Integer.parseInt(NilaiSianosis.getText())+Integer.parseInt(NilaiJalanMasukUdara.getText())+Integer.parseInt(NilaiGrunting.getText()))+"");
            }
        } catch (Exception e) {
            TotalNilaiDownScore.setText("0");
        }*/
    }
    
    private void emptTeksPersalinan() {
        TempatPersalinan.setText("");
        JenisPersalinan.setText("");
        Penolong.setText("");
        Penyulit.setText("");
        Keadaan.setText("");
        UsiaHamil.setText("");
        BBPB.setText("");
        TanggalPersalinan.setDate(new Date());
        JK.setSelectedIndex(0);
        TempatPersalinan.requestFocus();
    }
    
    private void tampilPersalinan() {
        Valid.tabelKosong(tabModeRiwayatKehamilan);
        try {
            ps=koneksi.prepareStatement("select * from riwayat_persalinan_pasien where riwayat_persalinan_pasien.no_rkm_medis=? order by riwayat_persalinan_pasien.tgl_thn");
            try {
                ps.setString(1,NoRMIbu.getText());
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    tabModeRiwayatKehamilan.addRow(new String[]{
                        i+"",rs.getString("tgl_thn"),rs.getString("tempat_persalinan"),rs.getString("usia_hamil"),rs.getString("jenis_persalinan"),
                        rs.getString("penolong"),rs.getString("penyulit"),rs.getString("jk"),rs.getString("bbpb"),rs.getString("keadaan")
                    });
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    private void simpan() {
        /*if(Sequel.menyimpantf("penilaian_medis_ranap_neonatus","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",105,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),NoRMIbu.getText(),G.getText(),P.getText(),A.getText(),Hidup.getText(),UsiaKehamilan.getText(),HbsAg.getSelectedItem().toString(),HIV.getSelectedItem().toString(), 
                Syphilis.getSelectedItem().toString(),RiwayatObstetri.getSelectedItem().toString(),KeteranganRiwayatObstetri.getText(),FaktorRisikoNeonatal.getSelectedItem().toString(),KeteranganFaktorRisikoNeonatal.getText(),Valid.SetTgl(TglJamPersalinan.getSelectedItem()+"")+" "+TglJamPersalinan.getSelectedItem().toString().substring(11,19), 
                BersalinDi.getText(),InisiasiMenyusui.getSelectedItem().toString(),JenisPersalinanBayi.getSelectedItem().toString(),IndikasiKeteranganPersalinan.getText(),Aterm.getSelectedItem().toString(),Bernafas.getSelectedItem().toString(),TonusOtotBaik.getSelectedItem().toString(),CairanAmnion.getSelectedItem().toString(),
                tbAPGAR.getValueAt(0,4).toString(),tbAPGAR.getValueAt(1,4).toString(),tbAPGAR.getValueAt(2,4).toString(),tbAPGAR.getValueAt(3,4).toString(),tbAPGAR.getValueAt(4,4).toString(),N1.getText(),tbAPGAR.getValueAt(0,5).toString(),tbAPGAR.getValueAt(1,5).toString(),tbAPGAR.getValueAt(2,5).toString(),tbAPGAR.getValueAt(3,5).toString(),
                tbAPGAR.getValueAt(4,5).toString(),N5.getText(),tbAPGAR.getValueAt(0,6).toString(),tbAPGAR.getValueAt(1,6).toString(),tbAPGAR.getValueAt(2,6).toString(),tbAPGAR.getValueAt(3,6).toString(),tbAPGAR.getValueAt(4,6).toString(),N10.getText(),FrekuensiNapas.getSelectedItem().toString(),NilaiFrekuensiNapas.getText(), 
                Retraksi.getSelectedItem().toString(),NilaiRetraksi.getText(),Sianosis.getSelectedItem().toString(),NilaiSianosis.getText(),JalanMasukUdara.getSelectedItem().toString(),NilaiJalanMasukUdara.getText(),Grunting.getSelectedItem().toString(),NilaiGrunting.getText(),TotalNilaiDownScore.getText(),KeteranganDownScore.getText(), 
                Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),BeratBadan.getText(),PanjangBadan.getText(),LingkarKepala.getText(),LingkarDada.getText(),KondisiUmum.getSelectedItem().toString(),KeteranganKondisiUmum.getText(),Kulit.getSelectedItem().toString(),KeteranganKulit.getText(),Kepala.getSelectedItem().toString(), 
                KeteranganKepala.getText(),Mata.getSelectedItem().toString(),KeteranganMata.getText(),Telinga.getSelectedItem().toString(),KeteranganTelinga.getText(),Hidung.getSelectedItem().toString(),KeteranganHidung.getText(),Mulut.getSelectedItem().toString(),KeteranganMulut.getText(),Tenggorokan.getSelectedItem().toString(),
                KeteranganTenggorokan.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Thorax.getSelectedItem().toString(),KeteranganThorax.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Genitalia.getSelectedItem().toString(),KeteranganGenitalia.getText(),Anus.getSelectedItem().toString(), 
                KeteranganAnus.getText(),Muskulos.getSelectedItem().toString(),KeteranganMuskulos.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),Paru.getSelectedItem().toString(),KeteranganParu.getText(),Refleks.getSelectedItem().toString(),KeteranganRefleks.getText(),KelainanLainnya.getText(), 
                PemeriksaanRegional.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText()
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),UmurBayi.getText(),NIKBayi.getText(),NoRMIbu.getText(),NmIbu.getText(),TglLahirIbu.getText(),NIKIbu.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),G.getText(),
                    P.getText(),A.getText(),Hidup.getText(),UsiaKehamilan.getText(),HbsAg.getSelectedItem().toString(),HIV.getSelectedItem().toString(),Syphilis.getSelectedItem().toString(),RiwayatObstetri.getSelectedItem().toString(),KeteranganRiwayatObstetri.getText(),FaktorRisikoNeonatal.getSelectedItem().toString(),KeteranganFaktorRisikoNeonatal.getText(),
                    Valid.SetTgl(TglJamPersalinan.getSelectedItem()+"")+" "+TglJamPersalinan.getSelectedItem().toString().substring(11,19),BersalinDi.getText(),InisiasiMenyusui.getSelectedItem().toString(),JenisPersalinanBayi.getSelectedItem().toString(),IndikasiKeteranganPersalinan.getText(),Aterm.getSelectedItem().toString(),Bernafas.getSelectedItem().toString(),
                    TonusOtotBaik.getSelectedItem().toString(),CairanAmnion.getSelectedItem().toString(),tbAPGAR.getValueAt(0,4).toString(),tbAPGAR.getValueAt(1,4).toString(),tbAPGAR.getValueAt(2,4).toString(),tbAPGAR.getValueAt(3,4).toString(),tbAPGAR.getValueAt(4,4).toString(),N1.getText(),tbAPGAR.getValueAt(0,5).toString(),tbAPGAR.getValueAt(1,5).toString(),
                    tbAPGAR.getValueAt(2,5).toString(),tbAPGAR.getValueAt(3,5).toString(),tbAPGAR.getValueAt(4,5).toString(),N5.getText(),tbAPGAR.getValueAt(0,6).toString(),tbAPGAR.getValueAt(1,6).toString(),tbAPGAR.getValueAt(2,6).toString(),tbAPGAR.getValueAt(3,6).toString(),tbAPGAR.getValueAt(4,6).toString(),N10.getText(),FrekuensiNapas.getSelectedItem().toString(),
                    NilaiFrekuensiNapas.getText(),Retraksi.getSelectedItem().toString(),NilaiRetraksi.getText(),Sianosis.getSelectedItem().toString(),NilaiSianosis.getText(),JalanMasukUdara.getSelectedItem().toString(),NilaiJalanMasukUdara.getText(),Grunting.getSelectedItem().toString(),NilaiGrunting.getText(),TotalNilaiDownScore.getText(),KeteranganDownScore.getText(), 
                    Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),BeratBadan.getText(),PanjangBadan.getText(),LingkarKepala.getText(),LingkarDada.getText(),KondisiUmum.getSelectedItem().toString(),KeteranganKondisiUmum.getText(),Kulit.getSelectedItem().toString(),KeteranganKulit.getText(),Kepala.getSelectedItem().toString(), 
                    KeteranganKepala.getText(),Mata.getSelectedItem().toString(),KeteranganMata.getText(),Telinga.getSelectedItem().toString(),KeteranganTelinga.getText(),Hidung.getSelectedItem().toString(),KeteranganHidung.getText(),Mulut.getSelectedItem().toString(),KeteranganMulut.getText(),Tenggorokan.getSelectedItem().toString(),
                    KeteranganTenggorokan.getText(),Leher.getSelectedItem().toString(),KeteranganLeher.getText(),Thorax.getSelectedItem().toString(),KeteranganThorax.getText(),Abdomen.getSelectedItem().toString(),KeteranganAbdomen.getText(),Genitalia.getSelectedItem().toString(),KeteranganGenitalia.getText(),Anus.getSelectedItem().toString(), 
                    KeteranganAnus.getText(),Muskulos.getSelectedItem().toString(),KeteranganMuskulos.getText(),Ekstrimitas.getSelectedItem().toString(),KeteranganEkstrimitas.getText(),Paru.getSelectedItem().toString(),KeteranganParu.getText(),Refleks.getSelectedItem().toString(),KeteranganRefleks.getText(),KelainanLainnya.getText(), 
                    PemeriksaanRegional.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
        }*/
    }
}

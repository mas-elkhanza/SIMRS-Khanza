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
public final class RMPenilaianAwalMedisRanapNeonatus extends javax.swing.JDialog {
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
    public RMPenilaianAwalMedisRanapNeonatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgRiwayatPersalinan.setSize(650,192);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Umur","NIK Bayi","No.RM IBU","Nama Ibu","NIK Ibu","Kode Dokter","Nama Dokter","Tanggal",
            "G","P","A","Hidup","Usia Hamil","HbsAg","HIV/AIDS","Syphilis","Riwayat Obstetri Ibu","Keterangan Riwayat Obstetri Ibu","Faktor Risiko Neonatal",
            "Keterangan Faktor Risiko Neonatal","Tgl & Jam Persalinan","Bersalin Di","Inisiasi Menyusui Dini","Jenis Persalinan","Indikasi/Keterangan","Aterm",
            "Bernafas/Menangis","Tonus Otot Baik","Cairan Amnion Jernih","F 1","U 1","T 1","R 1","W 1","N 1'","F 5","U 5","T 5","R 5","W 5","N 5'","F 10","U 10",
            "T 10","R 10","W 10","N 10'","Frekuensi Napas","N.F.N","Retraksi","N.R","Sianosis","N.S","Jalan Masuk Udara","N.J.M","Grunting","N.G","Ttl Down Score",
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
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(80);
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
        G.setDocument(new batasInput((int)10).getKata(G));
        P.setDocument(new batasInput((int)10).getKata(P));
        A.setDocument(new batasInput((int)10).getKata(A));
        Hidup.setDocument(new batasInput((int)10).getKata(Hidup));
        UsiaKehamilan.setDocument(new batasInput((int)10).getKata(UsiaKehamilan));
        KeteranganRiwayatObstetri.setDocument(new batasInput((int)70).getKata(KeteranganRiwayatObstetri));
        KeteranganFaktorRisikoNeonatal.setDocument(new batasInput((int)70).getKata(KeteranganFaktorRisikoNeonatal));
        BersalinDi.setDocument(new batasInput((int)70).getKata(BersalinDi));
        IndikasiKeteranganPersalinan.setDocument(new batasInput((int)70).getKata(IndikasiKeteranganPersalinan));
        N1.setDocument(new batasInput((int)2).getOnlyAngka(N1));
        N5.setDocument(new batasInput((int)2).getOnlyAngka(N5));
        N10.setDocument(new batasInput((int)2).getOnlyAngka(N10));
        NilaiFrekuensiNapas.setDocument(new batasInput((int)2).getOnlyAngka(NilaiFrekuensiNapas));
        NilaiRetraksi.setDocument(new batasInput((int)2).getOnlyAngka(NilaiRetraksi));
        NilaiSianosis.setDocument(new batasInput((int)2).getOnlyAngka(NilaiSianosis));
        NilaiJalanMasukUdara.setDocument(new batasInput((int)2).getOnlyAngka(NilaiJalanMasukUdara));
        NilaiGrunting.setDocument(new batasInput((int)2).getOnlyAngka(NilaiGrunting));
        TotalNilaiDownScore.setDocument(new batasInput((int)2).getOnlyAngka(TotalNilaiDownScore));
        KeteranganDownScore.setDocument(new batasInput((int)40).getKata(KeteranganDownScore));
        Nadi.setDocument(new batasInput((int)5).getOnlyAngka(Nadi));
        RR.setDocument(new batasInput((int)5).getOnlyAngka(RR));
        Suhu.setDocument(new batasInput((int)5).getOnlyAngka(Suhu));
        Saturasi.setDocument(new batasInput((int)5).getOnlyAngka(Saturasi));
        BeratBadan.setDocument(new batasInput((int)5).getOnlyAngka(BeratBadan));
        PanjangBadan.setDocument(new batasInput((int)5).getOnlyAngka(PanjangBadan));
        LingkarKepala.setDocument(new batasInput((int)5).getOnlyAngka(LingkarKepala));
        LingkarDada.setDocument(new batasInput((int)5).getOnlyAngka(LingkarDada));
        KeteranganKondisiUmum.setDocument(new batasInput((int)50).getKata(KeteranganKondisiUmum));
        KeteranganKulit.setDocument(new batasInput((int)50).getKata(KeteranganKulit));
        KeteranganKepala.setDocument(new batasInput((int)50).getKata(KeteranganKepala));
        KeteranganMata.setDocument(new batasInput((int)50).getKata(KeteranganMata));
        KeteranganTelinga.setDocument(new batasInput((int)50).getKata(KeteranganTelinga));
        KeteranganHidung.setDocument(new batasInput((int)50).getKata(KeteranganHidung));
        KeteranganMulut.setDocument(new batasInput((int)50).getKata(KeteranganMulut));
        KeteranganTenggorokan.setDocument(new batasInput((int)50).getKata(KeteranganTenggorokan));
        KeteranganLeher.setDocument(new batasInput((int)50).getKata(KeteranganLeher));
        KeteranganThorax.setDocument(new batasInput((int)50).getKata(KeteranganThorax));
        KeteranganAbdomen.setDocument(new batasInput((int)50).getKata(KeteranganAbdomen));
        KeteranganGenitalia.setDocument(new batasInput((int)50).getKata(KeteranganGenitalia));
        KeteranganAnus.setDocument(new batasInput((int)50).getKata(KeteranganAnus));
        KeteranganMuskulos.setDocument(new batasInput((int)50).getKata(KeteranganMuskulos));
        KeteranganEkstrimitas.setDocument(new batasInput((int)50).getKata(KeteranganEkstrimitas));
        KeteranganParu.setDocument(new batasInput((int)50).getKata(KeteranganParu));
        KeteranganRefleks.setDocument(new batasInput((int)50).getKata(KeteranganRefleks));
        KelainanLainnya.setDocument(new batasInput((int)50).getKata(KelainanLainnya)); 
        PemeriksaanRegional.setDocument(new batasInput((int)500).getKata(PemeriksaanRegional)); 
        Laborat.setDocument(new batasInput((int)500).getKata(Laborat)); 
        Radiologi.setDocument(new batasInput((int)500).getKata(Radiologi)); 
        Penunjang.setDocument(new batasInput((int)500).getKata(Penunjang)); 
        Diagnosis.setDocument(new batasInput((int)500).getKata(Diagnosis)); 
        Tatalaksana.setDocument(new batasInput((int)2000).getKata(Tatalaksana)); 
        Edukasi.setDocument(new batasInput((int)1000).getKata(Edukasi)); 
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
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianMedis = new javax.swing.JMenuItem();
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
        HbsAg = new widget.ComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        KeteranganRiwayatObstetri = new widget.TextBox();
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel12 = new widget.Label();
        UmurBayi = new widget.TextBox();
        jLabel13 = new widget.Label();
        NIKBayi = new widget.TextBox();
        jLabel68 = new widget.Label();
        jLabel101 = new widget.Label();
        G = new widget.TextBox();
        jLabel102 = new widget.Label();
        P = new widget.TextBox();
        jLabel103 = new widget.Label();
        A = new widget.TextBox();
        jLabel104 = new widget.Label();
        Hidup = new widget.TextBox();
        jLabel105 = new widget.Label();
        UsiaKehamilan = new widget.TextBox();
        jLabel106 = new widget.Label();
        Scroll6 = new widget.ScrollPane();
        tbRiwayatKehamilan = new widget.Table();
        BtnTambahMasalah = new widget.Button();
        BtnHapusRiwayatPersalinan = new widget.Button();
        jLabel69 = new widget.Label();
        HIV = new widget.ComboBox();
        jLabel39 = new widget.Label();
        Syphilis = new widget.ComboBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        RiwayatObstetri = new widget.ComboBox();
        jLabel70 = new widget.Label();
        jLabel42 = new widget.Label();
        FaktorRisikoNeonatal = new widget.ComboBox();
        jLabel71 = new widget.Label();
        KeteranganFaktorRisikoNeonatal = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel115 = new widget.Label();
        label12 = new widget.Label();
        TglJamPersalinan = new widget.Tanggal();
        jLabel43 = new widget.Label();
        BersalinDi = new widget.TextBox();
        jLabel116 = new widget.Label();
        JenisPersalinanBayi = new widget.ComboBox();
        jLabel72 = new widget.Label();
        jLabel45 = new widget.Label();
        IndikasiKeteranganPersalinan = new widget.TextBox();
        jLabel117 = new widget.Label();
        jLabel73 = new widget.Label();
        Aterm = new widget.ComboBox();
        jLabel44 = new widget.Label();
        jLabel46 = new widget.Label();
        Bernafas = new widget.ComboBox();
        jLabel47 = new widget.Label();
        CairanAmnion = new widget.ComboBox();
        jLabel118 = new widget.Label();
        jLabel48 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        TonusOtotBaik = new widget.ComboBox();
        jLabel74 = new widget.Label();
        Scroll2 = new widget.ScrollPane();
        tbAPGAR = new widget.Table();
        label71 = new widget.Label();
        N1 = new widget.TextBox2();
        N5 = new widget.TextBox2();
        N10 = new widget.TextBox2();
        jLabel76 = new widget.Label();
        jLabel51 = new widget.Label();
        FrekuensiNapas = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Retraksi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        Sianosis = new widget.ComboBox();
        JalanMasukUdara = new widget.ComboBox();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        Grunting = new widget.ComboBox();
        NilaiSianosis = new widget.TextBox();
        NilaiFrekuensiNapas = new widget.TextBox();
        NilaiRetraksi = new widget.TextBox();
        NilaiJalanMasukUdara = new widget.TextBox();
        TotalNilaiDownScore = new widget.TextBox();
        NilaiGrunting = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        InisiasiMenyusui = new widget.ComboBox();
        jLabel77 = new widget.Label();
        jLabel119 = new widget.Label();
        jLabel120 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel121 = new widget.Label();
        jLabel122 = new widget.Label();
        RR = new widget.TextBox();
        jLabel123 = new widget.Label();
        jLabel124 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel125 = new widget.Label();
        jLabel126 = new widget.Label();
        Saturasi = new widget.TextBox();
        jLabel127 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel128 = new widget.Label();
        BeratBadan = new widget.TextBox();
        jLabel129 = new widget.Label();
        jLabel130 = new widget.Label();
        PanjangBadan = new widget.TextBox();
        jLabel131 = new widget.Label();
        jLabel132 = new widget.Label();
        LingkarKepala = new widget.TextBox();
        jLabel133 = new widget.Label();
        jLabel134 = new widget.Label();
        LingkarDada = new widget.TextBox();
        jLabel135 = new widget.Label();
        jLabel136 = new widget.Label();
        jLabel79 = new widget.Label();
        KondisiUmum = new widget.ComboBox();
        jLabel59 = new widget.Label();
        KeteranganKondisiUmum = new widget.TextBox();
        jLabel60 = new widget.Label();
        Kulit = new widget.ComboBox();
        KeteranganKulit = new widget.TextBox();
        jLabel61 = new widget.Label();
        Kepala = new widget.ComboBox();
        KeteranganKepala = new widget.TextBox();
        jLabel62 = new widget.Label();
        Mata = new widget.ComboBox();
        KeteranganMata = new widget.TextBox();
        jLabel63 = new widget.Label();
        Telinga = new widget.ComboBox();
        KeteranganTelinga = new widget.TextBox();
        jLabel64 = new widget.Label();
        Hidung = new widget.ComboBox();
        KeteranganHidung = new widget.TextBox();
        jLabel65 = new widget.Label();
        Mulut = new widget.ComboBox();
        KeteranganMulut = new widget.TextBox();
        jLabel66 = new widget.Label();
        KeteranganTenggorokan = new widget.TextBox();
        Tenggorokan = new widget.ComboBox();
        jLabel67 = new widget.Label();
        Leher = new widget.ComboBox();
        KeteranganLeher = new widget.TextBox();
        Thorax = new widget.ComboBox();
        jLabel75 = new widget.Label();
        KeteranganThorax = new widget.TextBox();
        jLabel80 = new widget.Label();
        Abdomen = new widget.ComboBox();
        KeteranganAbdomen = new widget.TextBox();
        jLabel81 = new widget.Label();
        Genitalia = new widget.ComboBox();
        KeteranganGenitalia = new widget.TextBox();
        jLabel82 = new widget.Label();
        Anus = new widget.ComboBox();
        KeteranganAnus = new widget.TextBox();
        jLabel83 = new widget.Label();
        Muskulos = new widget.ComboBox();
        KeteranganMuskulos = new widget.TextBox();
        jLabel84 = new widget.Label();
        Ekstrimitas = new widget.ComboBox();
        KeteranganEkstrimitas = new widget.TextBox();
        jLabel85 = new widget.Label();
        Paru = new widget.ComboBox();
        KeteranganParu = new widget.TextBox();
        jLabel86 = new widget.Label();
        KeteranganRefleks = new widget.TextBox();
        Refleks = new widget.ComboBox();
        jLabel137 = new widget.Label();
        jLabel87 = new widget.Label();
        KelainanLainnya = new widget.TextBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel138 = new widget.Label();
        scrollPane12 = new widget.ScrollPane();
        PemeriksaanRegional = new widget.TextArea();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel139 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Laborat = new widget.TextArea();
        scrollPane10 = new widget.ScrollPane();
        Radiologi = new widget.TextArea();
        scrollPane11 = new widget.ScrollPane();
        Penunjang = new widget.TextArea();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel140 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Diagnosis = new widget.TextArea();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel141 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Tatalaksana = new widget.TextArea();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel142 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        jLabel14 = new widget.Label();
        NoRMIbu = new widget.TextBox();
        NmIbu = new widget.TextBox();
        BtnIbuBayi = new widget.Button();
        jLabel9 = new widget.Label();
        TglLahirIbu = new widget.TextBox();
        jLabel15 = new widget.Label();
        NIKIbu = new widget.TextBox();
        KeteranganDownScore = new widget.TextBox();
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

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Laporan Penilaian Medis");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

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
        TanggalPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-01-2025" }));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Rawat Inap Neonatus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(280, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(354, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(446, 40, 180, 23);

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
        BtnDokter.setBounds(628, 40, 28, 23);

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

        HbsAg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif (-)", "Positif (+)", "Tidak Ada Keterangan" }));
        HbsAg.setName("HbsAg"); // NOI18N
        HbsAg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HbsAgKeyPressed(evt);
            }
        });
        FormInput.add(HbsAg);
        HbsAg.setBounds(119, 290, 160, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 880, 1);

        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 290, 115, 23);

        KeteranganRiwayatObstetri.setName("KeteranganRiwayatObstetri"); // NOI18N
        KeteranganRiwayatObstetri.setPreferredSize(new java.awt.Dimension(207, 23));
        KeteranganRiwayatObstetri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRiwayatObstetriKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRiwayatObstetri);
        KeteranganRiwayatObstetri.setBounds(344, 320, 510, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. ANAMNESIS");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 100, 180, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(668, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-01-2025 17:14:45" }));
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

        jLabel12.setText("Umur :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 40, 70, 23);

        UmurBayi.setHighlighter(null);
        UmurBayi.setName("UmurBayi"); // NOI18N
        UmurBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurBayiKeyPressed(evt);
            }
        });
        FormInput.add(UmurBayi);
        UmurBayi.setBounds(74, 40, 50, 23);

        jLabel13.setText("NIK :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(120, 40, 45, 23);

        NIKBayi.setHighlighter(null);
        NIKBayi.setName("NIKBayi"); // NOI18N
        NIKBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIKBayiKeyPressed(evt);
            }
        });
        FormInput.add(NIKBayi);
        NIKBayi.setBounds(169, 40, 125, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Riwayat Persalinan & Nifas Ibu :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(44, 120, 182, 23);

        jLabel101.setText(":");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 140, 91, 23);

        G.setFocusTraversalPolicyProvider(true);
        G.setName("G"); // NOI18N
        G.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GKeyPressed(evt);
            }
        });
        FormInput.add(G);
        G.setBounds(95, 140, 50, 23);

        jLabel102.setText("P :");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(181, 140, 30, 23);

        P.setFocusTraversalPolicyProvider(true);
        P.setName("P"); // NOI18N
        P.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PKeyPressed(evt);
            }
        });
        FormInput.add(P);
        P.setBounds(215, 140, 50, 23);

        jLabel103.setText("A :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(301, 140, 30, 23);

        A.setFocusTraversalPolicyProvider(true);
        A.setName("A"); // NOI18N
        A.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AKeyPressed(evt);
            }
        });
        FormInput.add(A);
        A.setBounds(335, 140, 50, 23);

        jLabel104.setText("Anak Yang Hidup :");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(430, 140, 110, 23);

        Hidup.setFocusTraversalPolicyProvider(true);
        Hidup.setName("Hidup"); // NOI18N
        Hidup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidupKeyPressed(evt);
            }
        });
        FormInput.add(Hidup);
        Hidup.setBounds(544, 140, 60, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("minggu");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(817, 140, 110, 23);

        UsiaKehamilan.setFocusTraversalPolicyProvider(true);
        UsiaKehamilan.setName("UsiaKehamilan"); // NOI18N
        UsiaKehamilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsiaKehamilanKeyPressed(evt);
            }
        });
        FormInput.add(UsiaKehamilan);
        UsiaKehamilan.setBounds(754, 140, 60, 23);

        jLabel106.setText("Usia Kehamilan :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(650, 140, 100, 23);

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

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Skrining Ibu :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(44, 270, 182, 23);

        HIV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif (-)", "Positif (+)", "Tidak Ada Keterangan" }));
        HIV.setName("HIV"); // NOI18N
        HIV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HIVKeyPressed(evt);
            }
        });
        FormInput.add(HIV);
        HIV.setBounds(412, 290, 160, 23);

        jLabel39.setText("HIV/AIDS :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(338, 290, 70, 23);

        Syphilis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Negatif (-)", "Positif (+)", "Tidak Ada Keterangan" }));
        Syphilis.setName("Syphilis"); // NOI18N
        Syphilis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SyphilisKeyPressed(evt);
            }
        });
        FormInput.add(Syphilis);
        Syphilis.setBounds(694, 290, 160, 23);

        jLabel40.setText("Syphilis :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(620, 290, 70, 23);

        jLabel41.setText(":");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 320, 152, 23);

        RiwayatObstetri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Demam Pada Ibu > 38°C", "Ketuban Pecah Dini", "Ada Ketuban Berbau/Keruh", "Nyeri Berkemih/ISK", "Ibu DM", "Ibu Hipertensi", "Ibu Perdarahan", "Ibu Eklamsia/Pre Eklamsia", "Lainnya" }));
        RiwayatObstetri.setName("RiwayatObstetri"); // NOI18N
        RiwayatObstetri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatObstetriKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatObstetri);
        RiwayatObstetri.setBounds(156, 320, 185, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel70.setText("Riwayat Obstetri Ibu");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(44, 320, 182, 23);

        jLabel42.setText(":");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 350, 160, 23);

        FaktorRisikoNeonatal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Kelahiran Preterm", "Kelahiran Post Date", "DJJ Abnormal", "Lainnya" }));
        FaktorRisikoNeonatal.setName("FaktorRisikoNeonatal"); // NOI18N
        FaktorRisikoNeonatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoNeonatalKeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoNeonatal);
        FaktorRisikoNeonatal.setBounds(164, 350, 150, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Faktor Risiko Neonatal");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(44, 350, 182, 23);

        KeteranganFaktorRisikoNeonatal.setName("KeteranganFaktorRisikoNeonatal"); // NOI18N
        KeteranganFaktorRisikoNeonatal.setPreferredSize(new java.awt.Dimension(207, 23));
        KeteranganFaktorRisikoNeonatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganFaktorRisikoNeonatalKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganFaktorRisikoNeonatal);
        KeteranganFaktorRisikoNeonatal.setBounds(317, 350, 537, 23);

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

        label12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label12.setText("Tanggal & Jam Persalinan");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(44, 400, 150, 23);

        TglJamPersalinan.setForeground(new java.awt.Color(50, 70, 50));
        TglJamPersalinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-01-2025 17:14:46" }));
        TglJamPersalinan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglJamPersalinan.setName("TglJamPersalinan"); // NOI18N
        TglJamPersalinan.setOpaque(false);
        TglJamPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglJamPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(TglJamPersalinan);
        TglJamPersalinan.setBounds(181, 400, 130, 23);

        jLabel43.setText(":");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 400, 177, 23);

        BersalinDi.setFocusTraversalPolicyProvider(true);
        BersalinDi.setName("BersalinDi"); // NOI18N
        BersalinDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BersalinDiKeyPressed(evt);
            }
        });
        FormInput.add(BersalinDi);
        BersalinDi.setBounds(416, 400, 205, 23);

        jLabel116.setText("Bersalin Di :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(332, 400, 80, 23);

        JenisPersalinanBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan/Normal", "Induksi", "Sectio Caesaria", "Vacum Ekstraksi" }));
        JenisPersalinanBayi.setName("JenisPersalinanBayi"); // NOI18N
        JenisPersalinanBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPersalinanBayiKeyPressed(evt);
            }
        });
        FormInput.add(JenisPersalinanBayi);
        JenisPersalinanBayi.setBounds(134, 430, 133, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Jenis Persalinan");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(44, 430, 130, 23);

        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 430, 130, 23);

        IndikasiKeteranganPersalinan.setName("IndikasiKeteranganPersalinan"); // NOI18N
        IndikasiKeteranganPersalinan.setPreferredSize(new java.awt.Dimension(207, 23));
        IndikasiKeteranganPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IndikasiKeteranganPersalinanKeyPressed(evt);
            }
        });
        FormInput.add(IndikasiKeteranganPersalinan);
        IndikasiKeteranganPersalinan.setBounds(416, 430, 438, 23);

        jLabel117.setText("Indikasi/Keterangan :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(292, 430, 120, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Penilaian Awal Lahir :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(44, 460, 182, 23);

        Aterm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Aterm.setName("Aterm"); // NOI18N
        Aterm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AtermKeyPressed(evt);
            }
        });
        FormInput.add(Aterm);
        Aterm.setBounds(119, 480, 80, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Aterm");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(77, 480, 70, 23);

        jLabel46.setText("Bernafas/Menangis ?");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(216, 480, 120, 23);

        Bernafas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Bernafas.setName("Bernafas"); // NOI18N
        Bernafas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BernafasKeyPressed(evt);
            }
        });
        FormInput.add(Bernafas);
        Bernafas.setBounds(340, 480, 80, 23);

        jLabel47.setText("Cairan Amnion Jernih ?");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(640, 480, 130, 23);

        CairanAmnion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        CairanAmnion.setName("CairanAmnion"); // NOI18N
        CairanAmnion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CairanAmnionKeyPressed(evt);
            }
        });
        FormInput.add(CairanAmnion);
        CairanAmnion.setBounds(774, 480, 80, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("G");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(77, 140, 91, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("HbsAg");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(77, 290, 115, 23);

        jLabel49.setText("?");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 480, 115, 23);

        jLabel50.setText("Tonus Otot Baik ?");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(420, 480, 120, 23);

        TonusOtotBaik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        TonusOtotBaik.setName("TonusOtotBaik"); // NOI18N
        TonusOtotBaik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TonusOtotBaikKeyPressed(evt);
            }
        });
        FormInput.add(TonusOtotBaik);
        TonusOtotBaik.setBounds(544, 480, 80, 23);

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

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Down Score :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(44, 680, 182, 23);

        jLabel51.setText(":");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(57, 700, 107, 23);

        FrekuensiNapas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "< 60", "60 - 80", "> 80" }));
        FrekuensiNapas.setName("FrekuensiNapas"); // NOI18N
        FrekuensiNapas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiNapasKeyPressed(evt);
            }
        });
        FormInput.add(FrekuensiNapas);
        FrekuensiNapas.setBounds(168, 700, 180, 23);

        jLabel52.setText("Retraksi :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(57, 730, 107, 23);

        Retraksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Retraksi Ringan", "Retraksi Berat" }));
        Retraksi.setName("Retraksi"); // NOI18N
        Retraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RetraksiKeyPressed(evt);
            }
        });
        FormInput.add(Retraksi);
        Retraksi.setBounds(168, 730, 180, 23);

        jLabel53.setText("Sianosis :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(57, 760, 107, 23);

        Sianosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Hilang Dengan O2", "Tidak Hilang Dengan O2" }));
        Sianosis.setName("Sianosis"); // NOI18N
        Sianosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SianosisKeyPressed(evt);
            }
        });
        FormInput.add(Sianosis);
        Sianosis.setBounds(168, 760, 180, 23);

        JalanMasukUdara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Penurunan Ringan Udara Masuk", "Tidak Ada Udara Masuk" }));
        JalanMasukUdara.setName("JalanMasukUdara"); // NOI18N
        JalanMasukUdara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalanMasukUdaraKeyPressed(evt);
            }
        });
        FormInput.add(JalanMasukUdara);
        JalanMasukUdara.setBounds(576, 700, 230, 23);

        jLabel54.setText("Jalan Masuk Udara :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(462, 700, 110, 23);

        jLabel55.setText("Grunting :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(462, 730, 110, 23);

        Grunting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Dapat Didengar Dengan Stetoskop", "Dapat Didengar Tanpa Stetoskop" }));
        Grunting.setName("Grunting"); // NOI18N
        Grunting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GruntingKeyPressed(evt);
            }
        });
        FormInput.add(Grunting);
        Grunting.setBounds(576, 730, 230, 23);

        NilaiSianosis.setEditable(false);
        NilaiSianosis.setFocusTraversalPolicyProvider(true);
        NilaiSianosis.setName("NilaiSianosis"); // NOI18N
        NilaiSianosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiSianosisKeyPressed(evt);
            }
        });
        FormInput.add(NilaiSianosis);
        NilaiSianosis.setBounds(351, 760, 45, 23);

        NilaiFrekuensiNapas.setEditable(false);
        NilaiFrekuensiNapas.setFocusTraversalPolicyProvider(true);
        NilaiFrekuensiNapas.setName("NilaiFrekuensiNapas"); // NOI18N
        NilaiFrekuensiNapas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiFrekuensiNapasKeyPressed(evt);
            }
        });
        FormInput.add(NilaiFrekuensiNapas);
        NilaiFrekuensiNapas.setBounds(351, 700, 45, 23);

        NilaiRetraksi.setEditable(false);
        NilaiRetraksi.setFocusTraversalPolicyProvider(true);
        NilaiRetraksi.setName("NilaiRetraksi"); // NOI18N
        NilaiRetraksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiRetraksiKeyPressed(evt);
            }
        });
        FormInput.add(NilaiRetraksi);
        NilaiRetraksi.setBounds(351, 730, 45, 23);

        NilaiJalanMasukUdara.setEditable(false);
        NilaiJalanMasukUdara.setFocusTraversalPolicyProvider(true);
        NilaiJalanMasukUdara.setName("NilaiJalanMasukUdara"); // NOI18N
        NilaiJalanMasukUdara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiJalanMasukUdaraKeyPressed(evt);
            }
        });
        FormInput.add(NilaiJalanMasukUdara);
        NilaiJalanMasukUdara.setBounds(809, 700, 45, 23);

        TotalNilaiDownScore.setEditable(false);
        TotalNilaiDownScore.setFocusTraversalPolicyProvider(true);
        TotalNilaiDownScore.setName("TotalNilaiDownScore"); // NOI18N
        TotalNilaiDownScore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalNilaiDownScoreKeyPressed(evt);
            }
        });
        FormInput.add(TotalNilaiDownScore);
        TotalNilaiDownScore.setBounds(809, 760, 45, 23);

        NilaiGrunting.setEditable(false);
        NilaiGrunting.setFocusTraversalPolicyProvider(true);
        NilaiGrunting.setName("NilaiGrunting"); // NOI18N
        NilaiGrunting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGruntingKeyPressed(evt);
            }
        });
        FormInput.add(NilaiGrunting);
        NilaiGrunting.setBounds(809, 730, 45, 23);

        jLabel56.setText("Total Score & Keterangan :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(402, 760, 170, 23);

        jLabel57.setText("Inisiasi Menyusui Dini :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(640, 400, 130, 23);

        InisiasiMenyusui.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        InisiasiMenyusui.setName("InisiasiMenyusui"); // NOI18N
        InisiasiMenyusui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InisiasiMenyusuiKeyPressed(evt);
            }
        });
        FormInput.add(InisiasiMenyusui);
        InisiasiMenyusui.setBounds(774, 400, 80, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("Tanda-tanda Vital :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(44, 790, 182, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("x/menit");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(172, 810, 40, 23);

        jLabel120.setText(":");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(0, 810, 105, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(109, 810, 60, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("Nadi");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(77, 810, 40, 23);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("x/menit");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(390, 810, 40, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(327, 810, 60, 23);

        jLabel123.setText("RR :");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(273, 810, 50, 23);

        jLabel124.setText("Suhu :");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(499, 810, 50, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(553, 810, 60, 23);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("°C");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(616, 810, 40, 23);

        jLabel126.setText("Saturasi O2 :");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(665, 810, 110, 23);

        Saturasi.setFocusTraversalPolicyProvider(true);
        Saturasi.setName("Saturasi"); // NOI18N
        Saturasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaturasiKeyPressed(evt);
            }
        });
        FormInput.add(Saturasi);
        Saturasi.setBounds(779, 810, 60, 23);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("%");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(842, 810, 40, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Frekuensi Napas");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(77, 700, 107, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Antropometri :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(44, 840, 182, 23);

        jLabel128.setText(":");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(0, 860, 143, 23);

        BeratBadan.setFocusTraversalPolicyProvider(true);
        BeratBadan.setName("BeratBadan"); // NOI18N
        BeratBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratBadanKeyPressed(evt);
            }
        });
        FormInput.add(BeratBadan);
        BeratBadan.setBounds(147, 860, 60, 23);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("gram");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(210, 860, 40, 23);

        jLabel130.setText("Panjang Badan :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(268, 860, 100, 23);

        PanjangBadan.setFocusTraversalPolicyProvider(true);
        PanjangBadan.setName("PanjangBadan"); // NOI18N
        PanjangBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangBadanKeyPressed(evt);
            }
        });
        FormInput.add(PanjangBadan);
        PanjangBadan.setBounds(372, 860, 60, 23);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("cm");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(435, 860, 40, 23);

        jLabel132.setText("Lingkar Kepala :");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(484, 860, 90, 23);

        LingkarKepala.setFocusTraversalPolicyProvider(true);
        LingkarKepala.setName("LingkarKepala"); // NOI18N
        LingkarKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarKepalaKeyPressed(evt);
            }
        });
        FormInput.add(LingkarKepala);
        LingkarKepala.setBounds(578, 860, 60, 23);

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel133.setText("cm");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(641, 860, 40, 23);

        jLabel134.setText("Lingkar Dada :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(662, 860, 110, 23);

        LingkarDada.setFocusTraversalPolicyProvider(true);
        LingkarDada.setName("LingkarDada"); // NOI18N
        LingkarDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LingkarDadaKeyPressed(evt);
            }
        });
        FormInput.add(LingkarDada);
        LingkarDada.setBounds(776, 860, 60, 23);

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setText("cm");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(839, 860, 40, 23);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("Berat Badan");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(77, 860, 60, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Status Kelainan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(44, 890, 182, 23);

        KondisiUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        KondisiUmum.setName("KondisiUmum"); // NOI18N
        KondisiUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiUmumKeyPressed(evt);
            }
        });
        FormInput.add(KondisiUmum);
        KondisiUmum.setBounds(157, 910, 129, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Kondisi Umum");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(77, 910, 128, 23);

        KeteranganKondisiUmum.setFocusTraversalPolicyProvider(true);
        KeteranganKondisiUmum.setName("KeteranganKondisiUmum"); // NOI18N
        KeteranganKondisiUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKondisiUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKondisiUmum);
        KeteranganKondisiUmum.setBounds(289, 910, 160, 23);

        jLabel60.setText("Kulit :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 940, 153, 23);

        Kulit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kulit.setName("Kulit"); // NOI18N
        Kulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KulitKeyPressed(evt);
            }
        });
        FormInput.add(Kulit);
        Kulit.setBounds(157, 940, 129, 23);

        KeteranganKulit.setFocusTraversalPolicyProvider(true);
        KeteranganKulit.setName("KeteranganKulit"); // NOI18N
        KeteranganKulit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKulitKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKulit);
        KeteranganKulit.setBounds(289, 940, 160, 23);

        jLabel61.setText("Kepala :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(0, 970, 153, 23);

        Kepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput.add(Kepala);
        Kepala.setBounds(157, 970, 129, 23);

        KeteranganKepala.setFocusTraversalPolicyProvider(true);
        KeteranganKepala.setName("KeteranganKepala"); // NOI18N
        KeteranganKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKepalaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKepala);
        KeteranganKepala.setBounds(289, 970, 160, 23);

        jLabel62.setText("Mata :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 1000, 153, 23);

        Mata.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput.add(Mata);
        Mata.setBounds(157, 1000, 129, 23);

        KeteranganMata.setFocusTraversalPolicyProvider(true);
        KeteranganMata.setName("KeteranganMata"); // NOI18N
        KeteranganMata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMataKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMata);
        KeteranganMata.setBounds(289, 1000, 160, 23);

        jLabel63.setText("Telinga :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 1030, 153, 23);

        Telinga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Telinga.setName("Telinga"); // NOI18N
        Telinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelingaKeyPressed(evt);
            }
        });
        FormInput.add(Telinga);
        Telinga.setBounds(157, 1030, 129, 23);

        KeteranganTelinga.setFocusTraversalPolicyProvider(true);
        KeteranganTelinga.setName("KeteranganTelinga"); // NOI18N
        KeteranganTelinga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTelingaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTelinga);
        KeteranganTelinga.setBounds(289, 1030, 160, 23);

        jLabel64.setText("Hidung :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 1060, 153, 23);

        Hidung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Hidung.setName("Hidung"); // NOI18N
        Hidung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidungKeyPressed(evt);
            }
        });
        FormInput.add(Hidung);
        Hidung.setBounds(157, 1060, 129, 23);

        KeteranganHidung.setFocusTraversalPolicyProvider(true);
        KeteranganHidung.setName("KeteranganHidung"); // NOI18N
        KeteranganHidung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganHidungKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganHidung);
        KeteranganHidung.setBounds(289, 1060, 160, 23);

        jLabel65.setText("Mulut :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 1090, 153, 23);

        Mulut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Mulut.setName("Mulut"); // NOI18N
        Mulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MulutKeyPressed(evt);
            }
        });
        FormInput.add(Mulut);
        Mulut.setBounds(157, 1090, 129, 23);

        KeteranganMulut.setFocusTraversalPolicyProvider(true);
        KeteranganMulut.setName("KeteranganMulut"); // NOI18N
        KeteranganMulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMulutKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMulut);
        KeteranganMulut.setBounds(289, 1090, 160, 23);

        jLabel66.setText("Tenggorokan :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 1120, 153, 23);

        KeteranganTenggorokan.setFocusTraversalPolicyProvider(true);
        KeteranganTenggorokan.setName("KeteranganTenggorokan"); // NOI18N
        KeteranganTenggorokan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganTenggorokanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganTenggorokan);
        KeteranganTenggorokan.setBounds(289, 1120, 160, 23);

        Tenggorokan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Tenggorokan.setName("Tenggorokan"); // NOI18N
        Tenggorokan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TenggorokanKeyPressed(evt);
            }
        });
        FormInput.add(Tenggorokan);
        Tenggorokan.setBounds(157, 1120, 129, 23);

        jLabel67.setText("Leher :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 1150, 153, 23);

        Leher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput.add(Leher);
        Leher.setBounds(157, 1150, 129, 23);

        KeteranganLeher.setFocusTraversalPolicyProvider(true);
        KeteranganLeher.setName("KeteranganLeher"); // NOI18N
        KeteranganLeher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganLeherKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganLeher);
        KeteranganLeher.setBounds(289, 1150, 160, 23);

        Thorax.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Thorax.setName("Thorax"); // NOI18N
        Thorax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ThoraxKeyPressed(evt);
            }
        });
        FormInput.add(Thorax);
        Thorax.setBounds(562, 910, 129, 23);

        jLabel75.setText("Thorax :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(458, 910, 100, 23);

        KeteranganThorax.setFocusTraversalPolicyProvider(true);
        KeteranganThorax.setName("KeteranganThorax"); // NOI18N
        KeteranganThorax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganThoraxKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganThorax);
        KeteranganThorax.setBounds(694, 910, 160, 23);

        jLabel80.setText("Abdomen :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(458, 940, 100, 23);

        Abdomen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput.add(Abdomen);
        Abdomen.setBounds(562, 940, 129, 23);

        KeteranganAbdomen.setFocusTraversalPolicyProvider(true);
        KeteranganAbdomen.setName("KeteranganAbdomen"); // NOI18N
        KeteranganAbdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAbdomenKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAbdomen);
        KeteranganAbdomen.setBounds(694, 940, 160, 23);

        jLabel81.setText("Genitalia :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(458, 970, 100, 23);

        Genitalia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Genitalia.setName("Genitalia"); // NOI18N
        Genitalia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenitaliaKeyPressed(evt);
            }
        });
        FormInput.add(Genitalia);
        Genitalia.setBounds(562, 970, 129, 23);

        KeteranganGenitalia.setFocusTraversalPolicyProvider(true);
        KeteranganGenitalia.setName("KeteranganGenitalia"); // NOI18N
        KeteranganGenitalia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganGenitaliaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganGenitalia);
        KeteranganGenitalia.setBounds(694, 970, 160, 23);

        jLabel82.setText("Anus :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(458, 1000, 100, 23);

        Anus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Anus.setName("Anus"); // NOI18N
        Anus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnusKeyPressed(evt);
            }
        });
        FormInput.add(Anus);
        Anus.setBounds(562, 1000, 129, 23);

        KeteranganAnus.setFocusTraversalPolicyProvider(true);
        KeteranganAnus.setName("KeteranganAnus"); // NOI18N
        KeteranganAnus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAnusKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAnus);
        KeteranganAnus.setBounds(694, 1000, 160, 23);

        jLabel83.setText("Muskuloskeletal :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(458, 1030, 100, 23);

        Muskulos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Muskulos.setName("Muskulos"); // NOI18N
        Muskulos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuskulosKeyPressed(evt);
            }
        });
        FormInput.add(Muskulos);
        Muskulos.setBounds(562, 1030, 129, 23);

        KeteranganMuskulos.setFocusTraversalPolicyProvider(true);
        KeteranganMuskulos.setName("KeteranganMuskulos"); // NOI18N
        KeteranganMuskulos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMuskulosKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMuskulos);
        KeteranganMuskulos.setBounds(694, 1030, 160, 23);

        jLabel84.setText("Ekstrimitas :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(458, 1060, 100, 23);

        Ekstrimitas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Ekstrimitas.setName("Ekstrimitas"); // NOI18N
        Ekstrimitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstrimitasKeyPressed(evt);
            }
        });
        FormInput.add(Ekstrimitas);
        Ekstrimitas.setBounds(562, 1060, 129, 23);

        KeteranganEkstrimitas.setFocusTraversalPolicyProvider(true);
        KeteranganEkstrimitas.setName("KeteranganEkstrimitas"); // NOI18N
        KeteranganEkstrimitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEkstrimitasKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEkstrimitas);
        KeteranganEkstrimitas.setBounds(694, 1060, 160, 23);

        jLabel85.setText("Paru :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(458, 1090, 100, 23);

        Paru.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Paru.setName("Paru"); // NOI18N
        Paru.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParuKeyPressed(evt);
            }
        });
        FormInput.add(Paru);
        Paru.setBounds(562, 1090, 129, 23);

        KeteranganParu.setFocusTraversalPolicyProvider(true);
        KeteranganParu.setName("KeteranganParu"); // NOI18N
        KeteranganParu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganParuKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganParu);
        KeteranganParu.setBounds(694, 1090, 160, 23);

        jLabel86.setText("Refleks Primitif :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(458, 1120, 100, 23);

        KeteranganRefleks.setFocusTraversalPolicyProvider(true);
        KeteranganRefleks.setName("KeteranganRefleks"); // NOI18N
        KeteranganRefleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRefleksKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRefleks);
        KeteranganRefleks.setBounds(694, 1120, 160, 23);

        Refleks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Abnormal", "Tidak Diperiksa" }));
        Refleks.setName("Refleks"); // NOI18N
        Refleks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RefleksKeyPressed(evt);
            }
        });
        FormInput.add(Refleks);
        Refleks.setBounds(562, 1120, 129, 23);

        jLabel137.setText(":");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(0, 910, 153, 23);

        jLabel87.setText("Lainnya :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(458, 1150, 100, 23);

        KelainanLainnya.setFocusTraversalPolicyProvider(true);
        KelainanLainnya.setName("KelainanLainnya"); // NOI18N
        KelainanLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelainanLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(KelainanLainnya);
        KelainanLainnya.setBounds(562, 1150, 292, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 1180, 880, 1);

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("III. PEMERIKSAAN REGIONAL/KHUSUS/TAMBAHAN");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(10, 1180, 340, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        PemeriksaanRegional.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanRegional.setColumns(20);
        PemeriksaanRegional.setRows(3);
        PemeriksaanRegional.setName("PemeriksaanRegional"); // NOI18N
        PemeriksaanRegional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanRegionalKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(PemeriksaanRegional);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(44, 1200, 810, 43);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 1250, 880, 1);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("IV. PEMERIKSAAN PENUNJANG");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(10, 1250, 190, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Laboratorium :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(44, 1270, 150, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Radiologi :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(319, 1270, 150, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Penunjang Lainnya :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(594, 1270, 150, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Laborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Laborat.setColumns(20);
        Laborat.setRows(5);
        Laborat.setName("Laborat"); // NOI18N
        Laborat.setPreferredSize(new java.awt.Dimension(102, 52));
        Laborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaboratKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Laborat);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(44, 1290, 260, 63);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        Radiologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Radiologi.setColumns(20);
        Radiologi.setRows(5);
        Radiologi.setName("Radiologi"); // NOI18N
        Radiologi.setPreferredSize(new java.awt.Dimension(102, 52));
        Radiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadiologiKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(Radiologi);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(319, 1290, 260, 63);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        Penunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penunjang.setColumns(20);
        Penunjang.setRows(5);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.setPreferredSize(new java.awt.Dimension(102, 52));
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(Penunjang);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(594, 1290, 260, 63);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 1360, 880, 1);

        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText("V. DIAGNOSIS/ASESMEN");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(10, 1360, 190, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Diagnosis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diagnosis.setColumns(20);
        Diagnosis.setRows(3);
        Diagnosis.setName("Diagnosis"); // NOI18N
        Diagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosisKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Diagnosis);

        FormInput.add(scrollPane13);
        scrollPane13.setBounds(44, 1380, 810, 43);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 1430, 880, 1);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setText("VI. TATALAKSANA");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(10, 1430, 190, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Tatalaksana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tatalaksana.setColumns(30);
        Tatalaksana.setRows(20);
        Tatalaksana.setName("Tatalaksana"); // NOI18N
        Tatalaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TatalaksanaKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Tatalaksana);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(44, 1450, 810, 153);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1610, 880, 1);

        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel142.setText("VII. EDUKASI");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(10, 1610, 190, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(Edukasi);

        FormInput.add(scrollPane15);
        scrollPane15.setBounds(44, 1630, 810, 63);

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

        KeteranganDownScore.setFocusTraversalPolicyProvider(true);
        KeteranganDownScore.setName("KeteranganDownScore"); // NOI18N
        KeteranganDownScore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganDownScoreKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganDownScore);
        KeteranganDownScore.setBounds(576, 760, 230, 23);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-01-2025" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-01-2025" }));
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
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
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPS.getText().trim().equals("")){
            Valid.textKosong(RPS,"Riwayat Penyakit Sekarang");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengunaan obat");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }*/
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_neonatus.tanggal,"+
                        "penilaian_medis_ranap_neonatus.kd_dokter,penilaian_medis_ranap_neonatus.anamnesis,penilaian_medis_ranap_neonatus.hubungan,penilaian_medis_ranap_neonatus.keluhan_utama,penilaian_medis_ranap_neonatus.rps,penilaian_medis_ranap_neonatus.rpk,penilaian_medis_ranap_neonatus.rpd,penilaian_medis_ranap_neonatus.rpo,penilaian_medis_ranap_neonatus.alergi,"+
                        "penilaian_medis_ranap_neonatus.keadaan,penilaian_medis_ranap_neonatus.gcs,penilaian_medis_ranap_neonatus.kesadaran,penilaian_medis_ranap_neonatus.td,penilaian_medis_ranap_neonatus.nadi,penilaian_medis_ranap_neonatus.rr,penilaian_medis_ranap_neonatus.suhu,penilaian_medis_ranap_neonatus.spo,penilaian_medis_ranap_neonatus.bb,penilaian_medis_ranap_neonatus.tb,"+
                        "penilaian_medis_ranap_neonatus.kepala,penilaian_medis_ranap_neonatus.mata,penilaian_medis_ranap_neonatus.gigi,penilaian_medis_ranap_neonatus.tht,penilaian_medis_ranap_neonatus.thoraks,penilaian_medis_ranap_neonatus.jantung,penilaian_medis_ranap_neonatus.paru,penilaian_medis_ranap_neonatus.abdomen,penilaian_medis_ranap_neonatus.ekstremitas,"+
                        "penilaian_medis_ranap_neonatus.genital,penilaian_medis_ranap_neonatus.kulit,penilaian_medis_ranap_neonatus.ket_fisik,penilaian_medis_ranap_neonatus.ket_lokalis,penilaian_medis_ranap_neonatus.lab,penilaian_medis_ranap_neonatus.rad,penilaian_medis_ranap_neonatus.penunjang,penilaian_medis_ranap_neonatus.diagnosis,penilaian_medis_ranap_neonatus.tata,"+
                        "penilaian_medis_ranap_neonatus.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap_neonatus on reg_periksa.no_rawat=penilaian_medis_ranap_neonatus.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ranap_neonatus.tanggal between ? and ? order by penilaian_medis_ranap_neonatus.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_neonatus.tanggal,"+
                        "penilaian_medis_ranap_neonatus.kd_dokter,penilaian_medis_ranap_neonatus.anamnesis,penilaian_medis_ranap_neonatus.hubungan,penilaian_medis_ranap_neonatus.keluhan_utama,penilaian_medis_ranap_neonatus.rps,penilaian_medis_ranap_neonatus.rpk,penilaian_medis_ranap_neonatus.rpd,penilaian_medis_ranap_neonatus.rpo,penilaian_medis_ranap_neonatus.alergi,"+
                        "penilaian_medis_ranap_neonatus.keadaan,penilaian_medis_ranap_neonatus.gcs,penilaian_medis_ranap_neonatus.kesadaran,penilaian_medis_ranap_neonatus.td,penilaian_medis_ranap_neonatus.nadi,penilaian_medis_ranap_neonatus.rr,penilaian_medis_ranap_neonatus.suhu,penilaian_medis_ranap_neonatus.spo,penilaian_medis_ranap_neonatus.bb,penilaian_medis_ranap_neonatus.tb,"+
                        "penilaian_medis_ranap_neonatus.kepala,penilaian_medis_ranap_neonatus.mata,penilaian_medis_ranap_neonatus.gigi,penilaian_medis_ranap_neonatus.tht,penilaian_medis_ranap_neonatus.thoraks,penilaian_medis_ranap_neonatus.jantung,penilaian_medis_ranap_neonatus.paru,penilaian_medis_ranap_neonatus.abdomen,penilaian_medis_ranap_neonatus.ekstremitas,"+
                        "penilaian_medis_ranap_neonatus.genital,penilaian_medis_ranap_neonatus.kulit,penilaian_medis_ranap_neonatus.ket_fisik,penilaian_medis_ranap_neonatus.ket_lokalis,penilaian_medis_ranap_neonatus.lab,penilaian_medis_ranap_neonatus.rad,penilaian_medis_ranap_neonatus.penunjang,penilaian_medis_ranap_neonatus.diagnosis,penilaian_medis_ranap_neonatus.tata,"+
                        "penilaian_medis_ranap_neonatus.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ranap_neonatus on reg_periksa.no_rawat=penilaian_medis_ranap_neonatus.no_rawat "+
                        "inner join dokter on penilaian_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter where "+
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
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='105px'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70px'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='55px'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kode Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Nama Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='115px'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Anamnesis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='100px'><b>Hubungan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Keluhan Utama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Sekarang</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Dahulu</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penyakit Keluarga</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Riwayat Penggunakan Obat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='120px'><b>Riwayat Alergi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='90px'><b>Keadaan Umum</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50px'><b>GCS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kesadaran</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60px'><b>TD(mmHg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='75px'><b>Nadi(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='67px'><b>RR(x/menit)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>SpO2</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>BB(Kg)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40px'><b>TB(cm)</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kepala</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Mata</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Gigi & Mulut</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>THT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Thoraks</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Jantung</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Paru</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Abdomen</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Genital & Anus</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Ekstremitas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80px'><b>Kulit</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Ket.Pemeriksaan Fisik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='200px'><b>Ket.Status Lokalis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Laboratorium</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Radiologi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='170px'><b>Penunjang Lainnya</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Diagnosis/Asesmen</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAF8' align='center' width='300px'><b>Tatalaksana</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='150px'><b>Edukasi</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_dokter")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("anamnesis")+"</td>"+
                               "<td valign='top'>"+rs.getString("hubungan")+"</td>"+
                               "<td valign='top'>"+rs.getString("keluhan_utama")+"</td>"+
                               "<td valign='top'>"+rs.getString("rps")+"</td>"+
                               "<td valign='top'>"+rs.getString("rpd")+"</td>"+
                               "<td valign='top'>"+rs.getString("rpk")+"</td>"+
                               "<td valign='top'>"+rs.getString("rpo")+"</td>"+
                               "<td valign='top'>"+rs.getString("alergi")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan")+"</td>"+
                               "<td valign='top'>"+rs.getString("gcs")+"</td>"+
                               "<td valign='top'>"+rs.getString("kesadaran")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi")+"</td>"+
                               "<td valign='top'>"+rs.getString("rr")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("spo")+"</td>"+
                               "<td valign='top'>"+rs.getString("bb")+"</td>"+
                               "<td valign='top'>"+rs.getString("tb")+"</td>"+
                               "<td valign='top'>"+rs.getString("kepala")+"</td>"+
                               "<td valign='top'>"+rs.getString("mata")+"</td>"+
                               "<td valign='top'>"+rs.getString("gigi")+"</td>"+
                               "<td valign='top'>"+rs.getString("tht")+"</td>"+
                               "<td valign='top'>"+rs.getString("thoraks")+"</td>"+
                               "<td valign='top'>"+rs.getString("jantung")+"</td>"+
                               "<td valign='top'>"+rs.getString("paru")+"</td>"+
                               "<td valign='top'>"+rs.getString("abdomen")+"</td>"+
                               "<td valign='top'>"+rs.getString("genital")+"</td>"+
                               "<td valign='top'>"+rs.getString("ekstremitas")+"</td>"+
                               "<td valign='top'>"+rs.getString("kulit")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_fisik")+"</td>"+
                               "<td valign='top'>"+rs.getString("ket_lokalis")+"</td>"+
                               "<td valign='top'>"+rs.getString("lab")+"</td>"+
                               "<td valign='top'>"+rs.getString("rad")+"</td>"+
                               "<td valign='top'>"+rs.getString("penunjang")+"</td>"+
                               "<td valign='top'>"+rs.getString("diagnosis")+"</td>"+
                               "<td valign='top'>"+rs.getString("tata")+"</td>"+
                               "<td valign='top'>"+rs.getString("edukasi")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalMedisRanap.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT INAP<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
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
        Valid.pindah(evt,BtnSimpan,TglAsuhan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void HbsAgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HbsAgKeyPressed
        Valid.pindah(evt,UsiaKehamilan,HIV);
    }//GEN-LAST:event_HbsAgKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah2(evt,BtnDokter,BtnIbuBayi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void KeteranganRiwayatObstetriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRiwayatObstetriKeyPressed
        Valid.pindah(evt,RiwayatObstetri,FaktorRisikoNeonatal);
    }//GEN-LAST:event_KeteranganRiwayatObstetriKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            try {
                param.put("lokalis",getClass().getResource("/picture/semua.png").openStream());
            } catch (Exception e) {
            } 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRanap.jasper","report","::[ Laporan Penilaian Awal Medis Rawat Inap ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ranap_neonatus.tanggal,"+
                "penilaian_medis_ranap_neonatus.kd_dokter,penilaian_medis_ranap_neonatus.anamnesis,penilaian_medis_ranap_neonatus.hubungan,penilaian_medis_ranap_neonatus.keluhan_utama,penilaian_medis_ranap_neonatus.rps,penilaian_medis_ranap_neonatus.rpk,penilaian_medis_ranap_neonatus.rpd,penilaian_medis_ranap_neonatus.rpo,penilaian_medis_ranap_neonatus.alergi,"+
                "penilaian_medis_ranap_neonatus.keadaan,penilaian_medis_ranap_neonatus.gcs,penilaian_medis_ranap_neonatus.kesadaran,penilaian_medis_ranap_neonatus.td,penilaian_medis_ranap_neonatus.nadi,penilaian_medis_ranap_neonatus.rr,penilaian_medis_ranap_neonatus.suhu,penilaian_medis_ranap_neonatus.spo,penilaian_medis_ranap_neonatus.bb,penilaian_medis_ranap_neonatus.tb,"+
                "penilaian_medis_ranap_neonatus.kepala,penilaian_medis_ranap_neonatus.mata,penilaian_medis_ranap_neonatus.gigi,penilaian_medis_ranap_neonatus.tht,penilaian_medis_ranap_neonatus.thoraks,penilaian_medis_ranap_neonatus.jantung,penilaian_medis_ranap_neonatus.paru,penilaian_medis_ranap_neonatus.abdomen,penilaian_medis_ranap_neonatus.ekstremitas,"+
                "penilaian_medis_ranap_neonatus.genital,penilaian_medis_ranap_neonatus.kulit,penilaian_medis_ranap_neonatus.ket_fisik,penilaian_medis_ranap_neonatus.ket_lokalis,penilaian_medis_ranap_neonatus.lab,penilaian_medis_ranap_neonatus.rad,penilaian_medis_ranap_neonatus.penunjang,penilaian_medis_ranap_neonatus.diagnosis,penilaian_medis_ranap_neonatus.tata,"+
                "penilaian_medis_ranap_neonatus.edukasi,dokter.nm_dokter "+
                "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join penilaian_medis_ranap_neonatus on reg_periksa.no_rawat=penilaian_medis_ranap_neonatus.no_rawat "+
                "inner join dokter on penilaian_medis_ranap_neonatus.kd_dokter=dokter.kd_dokter where penilaian_medis_ranap_neonatus.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void UmurBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UmurBayiKeyPressed

    private void NIKBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIKBayiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NIKBayiKeyPressed

    private void GKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GKeyPressed
        Valid.pindah(evt,BtnIbuBayi,P);
    }//GEN-LAST:event_GKeyPressed

    private void PKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PKeyPressed
        Valid.pindah(evt,G,A);
    }//GEN-LAST:event_PKeyPressed

    private void AKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AKeyPressed
        Valid.pindah(evt,P,Hidup);
    }//GEN-LAST:event_AKeyPressed

    private void HidupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidupKeyPressed
        Valid.pindah(evt,A,UsiaKehamilan);
    }//GEN-LAST:event_HidupKeyPressed

    private void UsiaKehamilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsiaKehamilanKeyPressed
        Valid.pindah(evt,Hidup,HbsAg);
    }//GEN-LAST:event_UsiaKehamilanKeyPressed

    private void BtnTambahMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalahActionPerformed
        if(NoRMIbu.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu ibu bayi yang mau dimasukkan data kelahirannya...");
            HbsAg.requestFocus();
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

    private void HIVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HIVKeyPressed
        Valid.pindah(evt,HbsAg,Syphilis);
    }//GEN-LAST:event_HIVKeyPressed

    private void SyphilisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SyphilisKeyPressed
        Valid.pindah(evt,HIV,RiwayatObstetri);
    }//GEN-LAST:event_SyphilisKeyPressed

    private void RiwayatObstetriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatObstetriKeyPressed
        Valid.pindah(evt,Syphilis,KeteranganRiwayatObstetri);
    }//GEN-LAST:event_RiwayatObstetriKeyPressed

    private void FaktorRisikoNeonatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoNeonatalKeyPressed
        Valid.pindah(evt,KeteranganRiwayatObstetri,KeteranganFaktorRisikoNeonatal);
    }//GEN-LAST:event_FaktorRisikoNeonatalKeyPressed

    private void KeteranganFaktorRisikoNeonatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganFaktorRisikoNeonatalKeyPressed
        Valid.pindah(evt,FaktorRisikoNeonatal,TglJamPersalinan);
    }//GEN-LAST:event_KeteranganFaktorRisikoNeonatalKeyPressed

    private void TglJamPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglJamPersalinanKeyPressed
        Valid.pindah2(evt,KeteranganFaktorRisikoNeonatal,BersalinDi);
    }//GEN-LAST:event_TglJamPersalinanKeyPressed

    private void BersalinDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BersalinDiKeyPressed
        Valid.pindah(evt,TglJamPersalinan,InisiasiMenyusui);
    }//GEN-LAST:event_BersalinDiKeyPressed

    private void JenisPersalinanBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPersalinanBayiKeyPressed
        Valid.pindah(evt,InisiasiMenyusui,IndikasiKeteranganPersalinan);
    }//GEN-LAST:event_JenisPersalinanBayiKeyPressed

    private void IndikasiKeteranganPersalinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IndikasiKeteranganPersalinanKeyPressed
        Valid.pindah(evt,JenisPersalinanBayi,Aterm);
    }//GEN-LAST:event_IndikasiKeteranganPersalinanKeyPressed

    private void AtermKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AtermKeyPressed
        Valid.pindah(evt,IndikasiKeteranganPersalinan,Bernafas);
    }//GEN-LAST:event_AtermKeyPressed

    private void BernafasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BernafasKeyPressed
        Valid.pindah(evt,Aterm,TonusOtotBaik);
    }//GEN-LAST:event_BernafasKeyPressed

    private void CairanAmnionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CairanAmnionKeyPressed
        Valid.pindah(evt,TonusOtotBaik,FrekuensiNapas);
    }//GEN-LAST:event_CairanAmnionKeyPressed

    private void TonusOtotBaikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TonusOtotBaikKeyPressed
        Valid.pindah(evt,Bernafas,CairanAmnion);
    }//GEN-LAST:event_TonusOtotBaikKeyPressed

    private void tbAPGARPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAPGARPropertyChange
        if(this.isVisible()==true){
            getDataApgar();
        }
    }//GEN-LAST:event_tbAPGARPropertyChange

    private void FrekuensiNapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiNapasKeyPressed
        Valid.pindah(evt,CairanAmnion,Retraksi);
    }//GEN-LAST:event_FrekuensiNapasKeyPressed

    private void RetraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RetraksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RetraksiKeyPressed

    private void SianosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SianosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SianosisKeyPressed

    private void JalanMasukUdaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalanMasukUdaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JalanMasukUdaraKeyPressed

    private void GruntingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GruntingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GruntingKeyPressed

    private void NilaiSianosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiSianosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiSianosisKeyPressed

    private void NilaiFrekuensiNapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiFrekuensiNapasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiFrekuensiNapasKeyPressed

    private void NilaiRetraksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiRetraksiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiRetraksiKeyPressed

    private void NilaiJalanMasukUdaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiJalanMasukUdaraKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiJalanMasukUdaraKeyPressed

    private void TotalNilaiDownScoreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalNilaiDownScoreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalNilaiDownScoreKeyPressed

    private void NilaiGruntingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGruntingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGruntingKeyPressed

    private void InisiasiMenyusuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InisiasiMenyusuiKeyPressed
        Valid.pindah(evt,BersalinDi,JenisPersalinanBayi);
    }//GEN-LAST:event_InisiasiMenyusuiKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuhuKeyPressed

    private void SaturasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaturasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SaturasiKeyPressed

    private void BeratBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratBadanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BeratBadanKeyPressed

    private void PanjangBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangBadanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PanjangBadanKeyPressed

    private void LingkarKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarKepalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LingkarKepalaKeyPressed

    private void LingkarDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LingkarDadaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LingkarDadaKeyPressed

    private void KondisiUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiUmumKeyPressed
        //Valid.pindah(evt,KondisiUmum,KeteranganKepala);
    }//GEN-LAST:event_KondisiUmumKeyPressed

    private void KeteranganKondisiUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKondisiUmumKeyPressed
        //Valid.pindah(evt,Kepala,Thoraks);
    }//GEN-LAST:event_KeteranganKondisiUmumKeyPressed

    private void KulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KulitKeyPressed

    private void KeteranganKulitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKulitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKulitKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepalaKeyPressed

    private void KeteranganKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKepalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKepalaKeyPressed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MataKeyPressed

    private void KeteranganMataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganMataKeyPressed

    private void TelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelingaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TelingaKeyPressed

    private void KeteranganTelingaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTelingaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTelingaKeyPressed

    private void HidungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HidungKeyPressed

    private void KeteranganHidungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganHidungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganHidungKeyPressed

    private void MulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MulutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MulutKeyPressed

    private void KeteranganMulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMulutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganMulutKeyPressed

    private void KeteranganTenggorokanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganTenggorokanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganTenggorokanKeyPressed

    private void TenggorokanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TenggorokanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TenggorokanKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeherKeyPressed

    private void KeteranganLeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganLeherKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganLeherKeyPressed

    private void ThoraxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ThoraxKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThoraxKeyPressed

    private void KeteranganThoraxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganThoraxKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganThoraxKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbdomenKeyPressed

    private void KeteranganAbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAbdomenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganAbdomenKeyPressed

    private void GenitaliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenitaliaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenitaliaKeyPressed

    private void KeteranganGenitaliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganGenitaliaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganGenitaliaKeyPressed

    private void AnusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnusKeyPressed

    private void KeteranganAnusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAnusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganAnusKeyPressed

    private void MuskulosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuskulosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MuskulosKeyPressed

    private void KeteranganMuskulosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMuskulosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganMuskulosKeyPressed

    private void EkstrimitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstrimitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EkstrimitasKeyPressed

    private void KeteranganEkstrimitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEkstrimitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganEkstrimitasKeyPressed

    private void KeteranganRefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRefleksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganRefleksKeyPressed

    private void RefleksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RefleksKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RefleksKeyPressed

    private void KelainanLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelainanLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KelainanLainnyaKeyPressed

    private void PemeriksaanRegionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanRegionalKeyPressed
        //Valid.pindah2(evt,Penunjang,Tatalaksana);
    }//GEN-LAST:event_PemeriksaanRegionalKeyPressed

    private void LaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratKeyPressed
        //Valid.pindah2(evt,KetLokalis,Radiologi);
    }//GEN-LAST:event_LaboratKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        //Valid.pindah2(evt,Laborat,Penunjang);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        //Valid.pindah2(evt,Radiologi,Diagnosis);
    }//GEN-LAST:event_PenunjangKeyPressed

    private void DiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosisKeyPressed
        //Valid.pindah2(evt,Penunjang,Tatalaksana);
    }//GEN-LAST:event_DiagnosisKeyPressed

    private void TatalaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TatalaksanaKeyPressed
        //Valid.pindah2(evt,Diagnosis,Edukasi);
    }//GEN-LAST:event_TatalaksanaKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,Tatalaksana,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void BtnIbuBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIbuBayiActionPerformed
        ibubayi.emptTeks();
        ibubayi.isCek();
        ibubayi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ibubayi.setLocationRelativeTo(internalFrame1);
        ibubayi.setAlwaysOnTop(false);
        ibubayi.setVisible(true);
    }//GEN-LAST:event_BtnIbuBayiActionPerformed

    private void BtnIbuBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnIbuBayiKeyPressed
        Valid.pindah(evt,TglAsuhan,G);
    }//GEN-LAST:event_BtnIbuBayiKeyPressed

    private void KeteranganParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganParuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganParuKeyPressed

    private void ParuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ParuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ParuKeyPressed

    private void KeteranganDownScoreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganDownScoreKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganDownScoreKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRanapNeonatus dialog = new RMPenilaianAwalMedisRanapNeonatus(new javax.swing.JFrame(), true);
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
    private widget.TextBox A;
    private widget.ComboBox Abdomen;
    private widget.ComboBox Anus;
    private widget.ComboBox Aterm;
    private widget.TextBox BBPB;
    private widget.TextBox BeratBadan;
    private widget.ComboBox Bernafas;
    private widget.TextBox BersalinDi;
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
    private widget.ComboBox CairanAmnion;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Diagnosis;
    private javax.swing.JDialog DlgRiwayatPersalinan;
    private widget.TextArea Edukasi;
    private widget.ComboBox Ekstrimitas;
    private widget.ComboBox FaktorRisikoNeonatal;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox FrekuensiNapas;
    private widget.TextBox G;
    private widget.ComboBox Genitalia;
    private widget.ComboBox Grunting;
    private widget.ComboBox HIV;
    private widget.ComboBox HbsAg;
    private widget.ComboBox Hidung;
    private widget.TextBox Hidup;
    private widget.TextBox IndikasiKeteranganPersalinan;
    private widget.ComboBox InisiasiMenyusui;
    private widget.ComboBox JK;
    private widget.ComboBox JalanMasukUdara;
    private widget.TextBox JenisPersalinan;
    private widget.ComboBox JenisPersalinanBayi;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox Keadaan;
    private widget.TextBox KelainanLainnya;
    private widget.ComboBox Kepala;
    private widget.TextBox KeteranganAbdomen;
    private widget.TextBox KeteranganAnus;
    private widget.TextBox KeteranganDownScore;
    private widget.TextBox KeteranganEkstrimitas;
    private widget.TextBox KeteranganFaktorRisikoNeonatal;
    private widget.TextBox KeteranganGenitalia;
    private widget.TextBox KeteranganHidung;
    private widget.TextBox KeteranganKepala;
    private widget.TextBox KeteranganKondisiUmum;
    private widget.TextBox KeteranganKulit;
    private widget.TextBox KeteranganLeher;
    private widget.TextBox KeteranganMata;
    private widget.TextBox KeteranganMulut;
    private widget.TextBox KeteranganMuskulos;
    private widget.TextBox KeteranganParu;
    private widget.TextBox KeteranganRefleks;
    private widget.TextBox KeteranganRiwayatObstetri;
    private widget.TextBox KeteranganTelinga;
    private widget.TextBox KeteranganTenggorokan;
    private widget.TextBox KeteranganThorax;
    private widget.ComboBox KondisiUmum;
    private widget.ComboBox Kulit;
    private widget.Label LCount;
    private widget.TextArea Laborat;
    private widget.ComboBox Leher;
    private widget.TextBox LingkarDada;
    private widget.TextBox LingkarKepala;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Mata;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.ComboBox Mulut;
    private widget.ComboBox Muskulos;
    private widget.TextBox2 N1;
    private widget.TextBox2 N10;
    private widget.TextBox2 N5;
    private widget.TextBox NIKBayi;
    private widget.TextBox NIKIbu;
    private widget.TextBox Nadi;
    private widget.TextBox NilaiFrekuensiNapas;
    private widget.TextBox NilaiGrunting;
    private widget.TextBox NilaiJalanMasukUdara;
    private widget.TextBox NilaiRetraksi;
    private widget.TextBox NilaiSianosis;
    private widget.TextBox NmDokter;
    private widget.TextBox NmIbu;
    private widget.TextBox NoRMIbu;
    private widget.TextBox P;
    private widget.TextBox PanjangBadan;
    private widget.ComboBox Paru;
    private widget.TextArea PemeriksaanRegional;
    private widget.TextBox Penolong;
    private widget.TextArea Penunjang;
    private widget.TextBox Penyulit;
    private widget.TextBox RR;
    private widget.TextArea Radiologi;
    private widget.ComboBox Refleks;
    private widget.ComboBox Retraksi;
    private widget.ComboBox RiwayatObstetri;
    private widget.TextBox Saturasi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll6;
    private widget.ComboBox Sianosis;
    private widget.TextBox Suhu;
    private widget.ComboBox Syphilis;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalPersalinan;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextArea Tatalaksana;
    private widget.ComboBox Telinga;
    private widget.TextBox TempatPersalinan;
    private widget.ComboBox Tenggorokan;
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglJamPersalinan;
    private widget.TextBox TglLahir;
    private widget.TextBox TglLahirIbu;
    private widget.ComboBox Thorax;
    private widget.ComboBox TonusOtotBaik;
    private widget.TextBox TotalNilaiDownScore;
    private widget.TextBox UmurBayi;
    private widget.TextBox UsiaHamil;
    private widget.TextBox UsiaKehamilan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel103;
    private widget.Label jLabel104;
    private widget.Label jLabel105;
    private widget.Label jLabel106;
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
    private widget.Label jLabel116;
    private widget.Label jLabel117;
    private widget.Label jLabel118;
    private widget.Label jLabel119;
    private widget.Label jLabel12;
    private widget.Label jLabel120;
    private widget.Label jLabel121;
    private widget.Label jLabel122;
    private widget.Label jLabel123;
    private widget.Label jLabel124;
    private widget.Label jLabel125;
    private widget.Label jLabel126;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
    private widget.Label jLabel13;
    private widget.Label jLabel130;
    private widget.Label jLabel131;
    private widget.Label jLabel132;
    private widget.Label jLabel133;
    private widget.Label jLabel134;
    private widget.Label jLabel135;
    private widget.Label jLabel136;
    private widget.Label jLabel137;
    private widget.Label jLabel138;
    private widget.Label jLabel139;
    private widget.Label jLabel14;
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel9;
    private widget.Label jLabel90;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label71;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbAPGAR;
    private widget.Table tbObat;
    private widget.Table tbRiwayatKehamilan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_ktp,penilaian_medis_ranap_neonatus.no_rkm_medis_ibu,ibupasien.nm_pasien as nama_ibu,ibupasien.no_ktp as ktpibu,penilaian_medis_ranap_neonatus.tanggal,"+
                        "penilaian_medis_ranap_neonatus.kd_dokter,dokter.nm_dokter,penilaian_medis_ranap_neonatus.g,penilaian_medis_ranap_neonatus.p,penilaian_medis_ranap_neonatus.a,penilaian_medis_ranap_neonatus.hidup,penilaian_medis_ranap_neonatus.usiahamil,penilaian_medis_ranap_neonatus.hbsag,penilaian_medis_ranap_neonatus.hiv,"+
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
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_ktp,penilaian_medis_ranap_neonatus.no_rkm_medis_ibu,ibupasien.nm_pasien as nama_ibu,ibupasien.no_ktp as ktpibu,penilaian_medis_ranap_neonatus.tanggal,"+
                        "penilaian_medis_ranap_neonatus.kd_dokter,dokter.nm_dokter,penilaian_medis_ranap_neonatus.g,penilaian_medis_ranap_neonatus.p,penilaian_medis_ranap_neonatus.a,penilaian_medis_ranap_neonatus.hidup,penilaian_medis_ranap_neonatus.usiahamil,penilaian_medis_ranap_neonatus.hbsag,penilaian_medis_ranap_neonatus.hiv,"+
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
                        rs.getString("no_rkm_medis_ibu"),rs.getString("nama_ibu"),rs.getString("ktpibu"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),rs.getString("g"),rs.getString("p"),rs.getString("a"),
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
        G.setText("");
        P.setText("");
        A.setText("");
        Hidup.setText("");
        UsiaKehamilan.setText("");
        Valid.tabelKosong(tabModeRiwayatKehamilan);
        HbsAg.setSelectedIndex(0);
        HIV.setSelectedIndex(0);
        Syphilis.setSelectedIndex(0);
        RiwayatObstetri.setSelectedIndex(0);
        KeteranganRiwayatObstetri.setText("");
        FaktorRisikoNeonatal.setSelectedIndex(0);
        KeteranganFaktorRisikoNeonatal.setText("");
        TglJamPersalinan.setDate(new Date());
        BersalinDi.setText("");
        InisiasiMenyusui.setSelectedIndex(0);
        JenisPersalinanBayi.setSelectedIndex(0);
        IndikasiKeteranganPersalinan.setText("");
        Aterm.setSelectedIndex(0);
        Bernafas.setSelectedIndex(0);
        TonusOtotBaik.setSelectedIndex(0);
        CairanAmnion.setSelectedIndex(0);
        Valid.tabelKosong(tabModeAPGAR);
        tabModeAPGAR.addRow(new Object[]{"Frekuensi Jantung","Tidak Ada","< 100","> 100","","",""});
        tabModeAPGAR.addRow(new Object[]{"Usaha Nafas","Tidak Ada","Lambat Tak Teratur","Menangis Kuat","","",""});
        tabModeAPGAR.addRow(new Object[]{"Tanus Otot","Lumpuh","Ext. Fleksi Sedikit","Gerakan Aktif","","",""});
        tabModeAPGAR.addRow(new Object[]{"Refleks","Tidak Ada Respon","Pergerakan Sedikit","Menangis","","",""});
        tabModeAPGAR.addRow(new Object[]{"Warna","Biru Pucat","Tubuh Kemerahan, Tangan & Kaki Biru","Kemerahan","","",""});
        N1.setText("");
        N5.setText("");
        N10.setText("");
        FrekuensiNapas.setSelectedIndex(0);
        NilaiFrekuensiNapas.setText("");
        Retraksi.setSelectedIndex(0);
        NilaiRetraksi.setText("");
        Sianosis.setSelectedIndex(0);
        NilaiSianosis.setText("");
        JalanMasukUdara.setSelectedIndex(0);
        NilaiJalanMasukUdara.setText("");
        Grunting.setSelectedIndex(0);
        NilaiGrunting.setText("");
        TotalNilaiDownScore.setText("");
        KeteranganDownScore.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        Saturasi.setText("");
        BeratBadan.setText("");
        PanjangBadan.setText("");
        LingkarKepala.setText("");
        LingkarDada.setText("");
        KondisiUmum.setSelectedIndex(0);
        KeteranganKondisiUmum.setText("");
        Kulit.setSelectedIndex(0);
        KeteranganKulit.setText("");
        Kepala.setSelectedIndex(0);
        KeteranganKepala.setText("");
        Mata.setSelectedIndex(0);
        KeteranganMata.setText("");
        Telinga.setSelectedIndex(0);
        KeteranganTelinga.setText("");
        Hidung.setSelectedIndex(0);
        KeteranganHidung.setText("");
        Mulut.setSelectedIndex(0);
        KeteranganMulut.setText("");
        Tenggorokan.setSelectedIndex(0);
        KeteranganTenggorokan.setText("");
        Leher.setSelectedIndex(0);
        KeteranganLeher.setText("");
        Thorax.setSelectedIndex(0);
        KeteranganThorax.setText("");
        Abdomen.setSelectedIndex(0);
        KeteranganAbdomen.setText("");
        Genitalia.setSelectedIndex(0);
        KeteranganGenitalia.setText("");
        Anus.setSelectedIndex(0);
        KeteranganAnus.setText("");
        Muskulos.setSelectedIndex(0);
        KeteranganMuskulos.setText("");
        Ekstrimitas.setSelectedIndex(0);
        KeteranganEkstrimitas.setText("");
        Paru.setSelectedIndex(0);
        KeteranganParu.setText("");
        Refleks.setSelectedIndex(0);
        KeteranganRefleks.setText("");
        KelainanLainnya.setText(""); 
        PemeriksaanRegional.setText(""); 
        Laborat.setText(""); 
        Radiologi.setText(""); 
        Penunjang.setText(""); 
        Diagnosis.setText(""); 
        Tatalaksana.setText(""); 
        Edukasi.setText(""); 
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
            HbsAg.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KeteranganRiwayatObstetri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
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
                    UmurBayi.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
                    NIKBayi.setText(rs.getString("no_ktp"));
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
        /*if(Sequel.mengedittf("penilaian_medis_ranap_neonatus","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,keluhan_utama=?,rps=?,rpk=?,rpd=?,rpo=?,alergi=?,keadaan=?,gcs=?,kesadaran=?,td=?,nadi=?,rr=?,suhu=?,"+
                "spo=?,bb=?,tb=?,kepala=?,mata=?,gigi=?,tht=?,thoraks=?,jantung=?,paru=?,abdomen=?,genital=?,ekstremitas=?,kulit=?,ket_fisik=?,ket_lokalis=?,lab=?,rad=?,penunjang=?,diagnosis=?,tata=?,edukasi=?",41,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    KeluhanUtama.getText(),RPS.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),Keadaan.getSelectedItem().toString(),GCS.getText(),Kesadaran.getSelectedItem().toString(),TD.getText(),
                    Nadi.getText(),RR.getText(),Suhu.getText(),SPO.getText(),BB.getText(),TB.getText(),Kepala.getSelectedItem().toString(),Mata.getSelectedItem().toString(),Gigi.getSelectedItem().toString(),THT.getSelectedItem().toString(),
                    Thoraks.getSelectedItem().toString(),Jantung.getSelectedItem().toString(),Paru.getSelectedItem().toString(),Abdomen.getSelectedItem().toString(),Genital.getSelectedItem().toString(),Ekstremitas.getSelectedItem().toString(),
                    Kulit.getSelectedItem().toString(),KetFisik.getText(),KetLokalis.getText(),Laborat.getText(),Radiologi.getText(),Penunjang.getText(),Diagnosis.getText(),Tatalaksana.getText(),Edukasi.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
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
        if(Sequel.menyimpantf("penilaian_medis_ranap_neonatus","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",105,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),NoRMIbu.getText(),G.getText(),P.getText(),A.getText(),Hidup.getText(),UsiaHamil.getText(),HbsAg.getSelectedItem().toString(),HIV.getSelectedItem().toString(), 
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
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),UmurBayi.getText(),NIKBayi.getText(),NoRMIbu.getText(),NmIbu.getText(),NIKIbu.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),G.getText(),
                    P.getText(),A.getText(),Hidup.getText(),UsiaHamil.getText(),HbsAg.getSelectedItem().toString(),HIV.getSelectedItem().toString(),Syphilis.getSelectedItem().toString(),RiwayatObstetri.getSelectedItem().toString(),KeteranganRiwayatObstetri.getText(),FaktorRisikoNeonatal.getSelectedItem().toString(),KeteranganFaktorRisikoNeonatal.getText(),
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
        }
    }
}

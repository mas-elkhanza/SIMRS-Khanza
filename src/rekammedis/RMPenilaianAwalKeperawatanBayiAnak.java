/*
 * Kontribusi dari Abdul Wahid, RSUD Cipayung Jakarta Timur
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
public final class RMPenilaianAwalKeperawatanBayiAnak extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeMasalah,tabModeDetailMasalah,tabModeImunisasi;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,jml=0,index=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private boolean[] pilih; 
    private String[] kode,masalah;
    private String masalahkeperawatan=""; 
    private StringBuilder htmlContent;
    private MasterImunisasi imunisasi=new MasterImunisasi(null,false);
    private boolean ke1=false,ke2=false,ke3=false,ke4=false,ke5=false,ke6=false;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalKeperawatanBayiAnak(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgRiwayatImunisasi.setSize(465,112);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Agama","Bahasa","Cacat Fisik","Tgl.Lahir","Tgl.Asuhan","Informasi","TD","Nadi","RR","Suhu",
            "GCS","BB","TB","BMI","Keluhan Utama","Riwayat Penyakit Dahulu","Riwayat Penyakit Keluarga","Riwayat Pengobatan",
            "Alergi","Alat Bantu","Ket. Alat Bantu","Prothesa","Ket. Prothesa","ADL","Stts Psikologi","Ket. Psikologi","Hubungan Keluarga","Tinggal Dengan",
            "Ket. Tinggal","Ekonomi","Budaya","Ket. Budaya","Edukasi","Ket. Edukasi","Cara Berjalan A","Cara Berjalan B","Cara Berjalan C",
            "Hasil Penilaian Resiko Jatuh","Lapor Dokter","Ket. Lapor","Skrining Gizi 1","Nilai 1","Skrining Gizi 2","Nilai 2","Total Skor","Tingkat Nyeri","Provokes",
            "Ket. Provokes","Kualitas","Ket. Kualitas","Lokasi","Menyebar","Skala Nyeri","Durasi","Nyeri Hilang","Ket. Hilang Nyeri","Lapor Ke Dokter",
            "Jam Lapor","Rencana","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 65; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(35);
            }else if(i==11){
                column.setPreferredWidth(40);
            }else if(i==12){
                column.setPreferredWidth(35);
            }else if(i==13){
                column.setPreferredWidth(40);
            }else if(i==14){
                column.setPreferredWidth(35);
            }else if(i==15){
                column.setPreferredWidth(35);
            }else if(i==16){
                column.setPreferredWidth(35);
            }else if(i==17){
                column.setPreferredWidth(35);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(150);
            }else if(i==22){
                column.setPreferredWidth(100);
            }else if(i==23){
                column.setPreferredWidth(60);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(60);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(60);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(100);
            }else if(i==30){
                column.setPreferredWidth(103);
            }else if(i==31){
                column.setPreferredWidth(87);
            }else if(i==32){
                column.setPreferredWidth(90);
            }else if(i==33){
                column.setPreferredWidth(50);
            }else if(i==34){
                column.setPreferredWidth(58);
            }else if(i==35){
                column.setPreferredWidth(90);
            }else if(i==36){
                column.setPreferredWidth(60);
            }else if(i==37){
                column.setPreferredWidth(90);
            }else if(i==38){
                column.setPreferredWidth(87);
            }else if(i==39){
                column.setPreferredWidth(87);
            }else if(i==40){
                column.setPreferredWidth(87);
            }else if(i==41){
                column.setPreferredWidth(206);
            }else if(i==42){
                column.setPreferredWidth(75);
            }else if(i==43){
                column.setPreferredWidth(90);
            }else if(i==44){
                column.setPreferredWidth(80);
            }else if(i==45){
                column.setPreferredWidth(40);
            }else if(i==46){
                column.setPreferredWidth(80);
            }else if(i==47){
                column.setPreferredWidth(40);
            }else if(i==48){
                column.setPreferredWidth(60);
            }else if(i==49){
                column.setPreferredWidth(87);
            }else if(i==50){
                column.setPreferredWidth(87);
            }else if(i==51){
                column.setPreferredWidth(87);
            }else if(i==52){
                column.setPreferredWidth(90);
            }else if(i==53){
                column.setPreferredWidth(90);
            }else if(i==54){
                column.setPreferredWidth(110);
            }else if(i==55){
                column.setPreferredWidth(56);
            }else if(i==56){
                column.setPreferredWidth(60);
            }else if(i==57){
                column.setPreferredWidth(50);
            }else if(i==58){
                column.setPreferredWidth(90);
            }else if(i==59){
                column.setPreferredWidth(90);
            }else if(i==60){
                column.setPreferredWidth(90);
            }else if(i==61){
                column.setPreferredWidth(70);
            }else if(i==62){
                column.setPreferredWidth(200);
            }else if(i==63){
                column.setPreferredWidth(80);
            }else if(i==64){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeMasalah=new DefaultTableModel(null,new Object[]{
                "P","KODE","MASALAH KEPERAWATAN"
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
        tbMasalahKeperawatan.setModel(tabModeMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahKeperawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahKeperawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbMasalahKeperawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(350);
            }
        }
        tbMasalahKeperawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMasalah=new DefaultTableModel(null,new Object[]{
                "Kode","Masalah Keperawatan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbMasalahDetailMasalah.setModel(tabModeDetailMasalah);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMasalahDetailMasalah.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMasalahDetailMasalah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbMasalahDetailMasalah.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(420);
            }
        }
        tbMasalahDetailMasalah.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeImunisasi=new DefaultTableModel(null,new Object[]{
                "Kode","Nama Imunisasi","Ke 1","Ke 2","Ke 3","Ke 4","Ke 5","Ke 6"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbImunisasi.setModel(tabModeImunisasi);
        tbImunisasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbImunisasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 8; i++) {
            TableColumn column = tbImunisasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else{
                column.setPreferredWidth(50);
            }
        }
        tbImunisasi.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TD.setDocument(new batasInput((byte)8).getKata(TD));
        Nadi.setDocument(new batasInput((byte)5).getKata(Nadi));
        RR.setDocument(new batasInput((byte)5).getKata(RR));
        Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        GCS.setDocument(new batasInput((byte)5).getKata(GCS));
        /*BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        BMI.setDocument(new batasInput((byte)5).getKata(BMI));
        KeluhanUtama.setDocument(new batasInput((int)150).getKata(KeluhanUtama));
        RPD.setDocument(new batasInput((int)100).getKata(RPD));
        RPK.setDocument(new batasInput((int)100).getKata(RPK));
        RPO.setDocument(new batasInput((int)100).getKata(RPO));
        Alergi.setDocument(new batasInput((int)25).getKata(Alergi));
        KetBantu.setDocument(new batasInput((int)50).getKata(KetBantu));
        KetProthesa.setDocument(new batasInput((int)50).getKata(KetProthesa));
        KetBudaya.setDocument(new batasInput((int)50).getKata(KetBudaya));
        KetPsiko.setDocument(new batasInput((int)70).getKata(KetPsiko));
        KetTinggal.setDocument(new batasInput((int)40).getKata(KetTinggal));
        KetEdukasi.setDocument(new batasInput((int)50).getKata(KetEdukasi));
        KetLapor.setDocument(new batasInput((int)15).getKata(KetLapor));
        KetProvokes.setDocument(new batasInput((int)40).getKata(KetProvokes));
        KetQuality.setDocument(new batasInput((int)50).getKata(KetQuality));
        Lokasi.setDocument(new batasInput((int)50).getKata(Lokasi));
        Durasi.setDocument(new batasInput((int)25).getKata(Durasi));
        KetNyeri.setDocument(new batasInput((int)40).getKata(KetNyeri));
        KetDokter.setDocument(new batasInput((int)15).getKata(KetDokter));
        Rencana.setDocument(new batasInput((int)200).getKata(Rencana));
        
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
        
        */
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
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
        
        imunisasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(imunisasi.getTable().getSelectedRow()!= -1){ 
                    KdImunisasi.setText(imunisasi.getTable().getValueAt(imunisasi.getTable().getSelectedRow(),0).toString());
                    NmImunisasi.setText(imunisasi.getTable().getValueAt(imunisasi.getTable().getSelectedRow(),1).toString());   
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
        
        imunisasi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    imunisasi.dispose();
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

        LoadHTML = new widget.editorpane();
        DlgRiwayatImunisasi = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        BtnKeluarImunisasi = new widget.Button();
        BtnSimpanImunisasi = new widget.Button();
        BtnImunisasi = new widget.Button();
        NmImunisasi = new widget.TextBox();
        KdImunisasi = new widget.TextBox();
        jLabel43 = new widget.Label();
        ImunisasiKe = new widget.ComboBox();
        BtnHapusImunisasi = new widget.Button();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        label11 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel25 = new widget.Label();
        RR = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel36 = new widget.Label();
        Informasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel28 = new widget.Label();
        GCS = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel24 = new widget.Label();
        TB = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel29 = new widget.Label();
        LK = new widget.TextBox();
        jLabel30 = new widget.Label();
        BB = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        LP = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel37 = new widget.Label();
        LD = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel39 = new widget.Label();
        Alergi = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel40 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RPD = new widget.TextArea();
        jLabel41 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        RPK = new widget.TextArea();
        jLabel42 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RPO = new widget.TextArea();
        jLabel94 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel95 = new widget.Label();
        jLabel44 = new widget.Label();
        Alergi1 = new widget.TextBox();
        Alergi2 = new widget.TextBox();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel55 = new widget.Label();
        AlatBantu = new widget.ComboBox();
        KetBantu = new widget.TextBox();
        AlatBantu1 = new widget.ComboBox();
        jLabel56 = new widget.Label();
        KetBantu1 = new widget.TextBox();
        AlatBantu2 = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel96 = new widget.Label();
        Scroll6 = new widget.ScrollPane();
        tbImunisasi = new widget.Table();
        BtnTambahImunisasi = new widget.Button();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel97 = new widget.Label();
        jLabel59 = new widget.Label();
        Alergi3 = new widget.TextBox();
        jLabel60 = new widget.Label();
        Alergi4 = new widget.TextBox();
        jLabel61 = new widget.Label();
        Alergi5 = new widget.TextBox();
        jLabel62 = new widget.Label();
        Alergi6 = new widget.TextBox();
        jLabel63 = new widget.Label();
        Alergi7 = new widget.TextBox();
        jLabel64 = new widget.Label();
        Alergi8 = new widget.TextBox();
        jLabel65 = new widget.Label();
        Alergi9 = new widget.TextBox();
        Alergi10 = new widget.TextBox();
        jLabel66 = new widget.Label();
        Alergi11 = new widget.TextBox();
        jLabel67 = new widget.Label();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel125 = new widget.Label();
        jLabel127 = new widget.Label();
        AlatBantu3 = new widget.ComboBox();
        KetBantu2 = new widget.TextBox();
        Prothesa = new widget.ComboBox();
        KetProthesa = new widget.TextBox();
        jLabel128 = new widget.Label();
        ADL = new widget.ComboBox();
        jLabel129 = new widget.Label();
        jLabel130 = new widget.Label();
        CacatFisik = new widget.TextBox();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel131 = new widget.Label();
        StatusPsiko = new widget.ComboBox();
        KetPsiko = new widget.TextBox();
        jLabel132 = new widget.Label();
        Bahasa = new widget.TextBox();
        jLabel133 = new widget.Label();
        jLabel134 = new widget.Label();
        jLabel135 = new widget.Label();
        HubunganKeluarga = new widget.ComboBox();
        TinggalDengan = new widget.ComboBox();
        KetTinggal = new widget.TextBox();
        jLabel136 = new widget.Label();
        Ekonomi = new widget.ComboBox();
        jLabel137 = new widget.Label();
        jLabel138 = new widget.Label();
        StatusBudaya = new widget.ComboBox();
        KetBudaya = new widget.TextBox();
        jLabel139 = new widget.Label();
        Edukasi = new widget.ComboBox();
        KetEdukasi = new widget.TextBox();
        jLabel140 = new widget.Label();
        Agama = new widget.TextBox();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel141 = new widget.Label();
        jLabel142 = new widget.Label();
        jLabel143 = new widget.Label();
        RJa1 = new widget.ComboBox();
        jLabel144 = new widget.Label();
        RJa2 = new widget.ComboBox();
        jLabel145 = new widget.Label();
        RJb = new widget.ComboBox();
        Lapor = new widget.ComboBox();
        Hasil = new widget.ComboBox();
        jLabel146 = new widget.Label();
        KetLapor = new widget.TextBox();
        jLabel147 = new widget.Label();
        jLabel148 = new widget.Label();
        jLabel149 = new widget.Label();
        jSeparator10 = new javax.swing.JSeparator();
        SG1 = new widget.ComboBox();
        jLabel150 = new widget.Label();
        jLabel152 = new widget.Label();
        SG2 = new widget.ComboBox();
        jLabel154 = new widget.Label();
        jLabel155 = new widget.Label();
        jLabel156 = new widget.Label();
        SG3 = new widget.ComboBox();
        jLabel158 = new widget.Label();
        jLabel159 = new widget.Label();
        jLabel160 = new widget.Label();
        Wajah = new widget.ComboBox();
        jLabel162 = new widget.Label();
        NilaiGizi1 = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel163 = new widget.Label();
        jLabel164 = new widget.Label();
        jLabel165 = new widget.Label();
        jLabel166 = new widget.Label();
        jLabel167 = new widget.Label();
        jLabel168 = new widget.Label();
        jLabel169 = new widget.Label();
        SG4 = new widget.ComboBox();
        NilaiGizi2 = new widget.TextBox();
        NilaiGizi3 = new widget.TextBox();
        NilaiGizi4 = new widget.TextBox();
        TotalHasil4 = new widget.TextBox();
        TotalNilaiGizi = new widget.TextBox();
        SG6 = new widget.ComboBox();
        TotalHasil6 = new widget.TextBox();
        SG7 = new widget.ComboBox();
        TotalHasil7 = new widget.TextBox();
        SG8 = new widget.ComboBox();
        TotalHasil8 = new widget.TextBox();
        SG9 = new widget.ComboBox();
        TotalHasil9 = new widget.TextBox();
        PanelWall = new usu.widget.glass.PanelGlass();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel170 = new widget.Label();
        TotalHasil10 = new widget.TextBox();
        Nyeri = new widget.ComboBox();
        jLabel88 = new widget.Label();
        Frekuensi = new widget.TextBox();
        jLabel83 = new widget.Label();
        Lokasi = new widget.TextBox();
        Durasi = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel89 = new widget.Label();
        NyeriHilang = new widget.ComboBox();
        KetNyeri = new widget.TextBox();
        jLabel86 = new widget.Label();
        PadaDokter = new widget.ComboBox();
        jLabel68 = new widget.Label();
        KetDokter = new widget.TextBox();
        jSeparator13 = new javax.swing.JSeparator();
        Scroll8 = new widget.ScrollPane();
        tbMasalahKeperawatan = new widget.Table();
        jLabel79 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        BtnTambahMasalah1 = new widget.Button();
        BtnCariPemeriksaan1 = new widget.Button();
        TCariMasalah = new widget.TextBox();
        label12 = new widget.Label();
        BtnPanggilHapusImunisasi = new widget.Button();
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
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormMenu = new widget.PanelBiasa();
        jLabel34 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        BtnPrint1 = new widget.Button();
        FormMasalahRencana = new widget.PanelBiasa();
        Scroll7 = new widget.ScrollPane();
        tbMasalahDetailMasalah = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        DetailRencana = new widget.TextArea();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        DlgRiwayatImunisasi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgRiwayatImunisasi.setName("DlgRiwayatImunisasi"); // NOI18N
        DlgRiwayatImunisasi.setUndecorated(true);
        DlgRiwayatImunisasi.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Riwayat Imunisasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel99.setText("Imunisasi :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(0, 13, 67, 23);

        BtnKeluarImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarImunisasi.setMnemonic('U');
        BtnKeluarImunisasi.setText("Tutup");
        BtnKeluarImunisasi.setToolTipText("Alt+U");
        BtnKeluarImunisasi.setName("BtnKeluarImunisasi"); // NOI18N
        BtnKeluarImunisasi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarImunisasiActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluarImunisasi);
        BtnKeluarImunisasi.setBounds(340, 50, 100, 30);

        BtnSimpanImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanImunisasi.setMnemonic('S');
        BtnSimpanImunisasi.setText("Simpan");
        BtnSimpanImunisasi.setToolTipText("Alt+S");
        BtnSimpanImunisasi.setName("BtnSimpanImunisasi"); // NOI18N
        BtnSimpanImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanImunisasiActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnSimpanImunisasi);
        BtnSimpanImunisasi.setBounds(10, 50, 100, 30);

        BtnImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnImunisasi.setMnemonic('2');
        BtnImunisasi.setToolTipText("Alt+2");
        BtnImunisasi.setName("BtnImunisasi"); // NOI18N
        BtnImunisasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImunisasiActionPerformed(evt);
            }
        });
        BtnImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnImunisasiKeyPressed(evt);
            }
        });
        panelBiasa2.add(BtnImunisasi);
        BtnImunisasi.setBounds(307, 13, 28, 23);

        NmImunisasi.setEditable(false);
        NmImunisasi.setName("NmImunisasi"); // NOI18N
        NmImunisasi.setPreferredSize(new java.awt.Dimension(207, 23));
        panelBiasa2.add(NmImunisasi);
        NmImunisasi.setBounds(124, 13, 180, 23);

        KdImunisasi.setEditable(false);
        KdImunisasi.setName("KdImunisasi"); // NOI18N
        KdImunisasi.setPreferredSize(new java.awt.Dimension(80, 23));
        KdImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdImunisasiKeyPressed(evt);
            }
        });
        panelBiasa2.add(KdImunisasi);
        KdImunisasi.setBounds(71, 13, 50, 23);

        jLabel43.setText("Ke :");
        jLabel43.setName("jLabel43"); // NOI18N
        panelBiasa2.add(jLabel43);
        jLabel43.setBounds(343, 13, 30, 23);

        ImunisasiKe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6" }));
        ImunisasiKe.setName("ImunisasiKe"); // NOI18N
        ImunisasiKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImunisasiKeKeyPressed(evt);
            }
        });
        panelBiasa2.add(ImunisasiKe);
        ImunisasiKe.setBounds(377, 13, 60, 23);

        BtnHapusImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusImunisasi.setMnemonic('H');
        BtnHapusImunisasi.setText("Hapus");
        BtnHapusImunisasi.setToolTipText("Alt+H");
        BtnHapusImunisasi.setName("BtnHapusImunisasi"); // NOI18N
        BtnHapusImunisasi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapusImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusImunisasiActionPerformed(evt);
            }
        });
        BtnHapusImunisasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusImunisasiKeyPressed(evt);
            }
        });
        panelBiasa2.add(BtnHapusImunisasi);
        BtnHapusImunisasi.setBounds(230, 50, 100, 30);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgRiwayatImunisasi.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Keperawatan Rawat Jalan Bayi/Anak ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1618));
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

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(74, 40, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(176, 40, 180, 23);

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
        BtnDokter.setBounds(358, 40, 28, 23);

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

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(395, 40, 57, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(298, 90, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(250, 90, 45, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(206, 90, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(566, 90, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(610, 90, 45, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 90, 70, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(74, 90, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(658, 90, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(137, 90, 50, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(470, 90, 50, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(422, 90, 45, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(378, 90, 40, 23);

        jLabel36.setText("Informasi didapat dari :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(592, 40, 130, 23);

        Informasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Informasi.setName("Informasi"); // NOI18N
        Informasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InformasiKeyPressed(evt);
            }
        });
        FormInput.add(Informasi);
        Informasi.setBounds(726, 40, 128, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. KEADAAN UMUM");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-09-2020 06:45:53" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(456, 40, 130, 23);

        jLabel28.setText("GCS(E,V,M) :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(700, 90, 90, 23);

        GCS.setFocusTraversalPolicyProvider(true);
        GCS.setName("GCS"); // NOI18N
        GCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GCSKeyPressed(evt);
            }
        });
        FormInput.add(GCS);
        GCS.setBounds(794, 90, 60, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(298, 120, 50, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(250, 120, 45, 23);

        jLabel27.setText("TB :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(206, 120, 40, 23);

        jLabel29.setText("LK :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(566, 120, 40, 23);

        LK.setFocusTraversalPolicyProvider(true);
        LK.setName("LK"); // NOI18N
        LK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LKKeyPressed(evt);
            }
        });
        FormInput.add(LK);
        LK.setBounds(610, 120, 45, 23);

        jLabel30.setText("BB :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 120, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(74, 120, 45, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("cm");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(842, 120, 30, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Kg");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(122, 120, 50, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("cm");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(470, 120, 50, 23);

        LP.setFocusTraversalPolicyProvider(true);
        LP.setName("LP"); // NOI18N
        LP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LPKeyPressed(evt);
            }
        });
        FormInput.add(LP);
        LP.setBounds(422, 120, 45, 23);

        jLabel35.setText("LP :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(378, 120, 40, 23);

        jLabel37.setText("LD :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(700, 120, 90, 23);

        LD.setFocusTraversalPolicyProvider(true);
        LD.setName("LD"); // NOI18N
        LD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LDKeyPressed(evt);
            }
        });
        FormInput.add(LD);
        LD.setBounds(794, 120, 45, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("cm");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(658, 120, 30, 23);

        jLabel9.setText("Riwayat Pengobatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(440, 220, 150, 23);

        jLabel39.setText("Riwayat Alergi :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 270, 175, 23);

        Alergi.setFocusTraversalPolicyProvider(true);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(179, 270, 260, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(179, 170, 260, 43);

        jLabel40.setText("Keluhan Utama :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 170, 175, 20);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RPD.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPD.setColumns(20);
        RPD.setRows(5);
        RPD.setName("RPD"); // NOI18N
        RPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPDKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(RPD);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(179, 220, 260, 43);

        jLabel41.setText("Riwayat Penyakit Dahulu :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 220, 175, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        RPK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPK.setColumns(20);
        RPK.setRows(5);
        RPK.setName("RPK"); // NOI18N
        RPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPKKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(RPK);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(594, 170, 260, 42);

        jLabel42.setText("Riwayat Penyakit Keluarga :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(440, 170, 150, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RPO.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RPO.setColumns(20);
        RPO.setRows(5);
        RPO.setName("RPO"); // NOI18N
        RPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RPOKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RPO);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(594, 220, 260, 42);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. RIWAYAT KESEHATAN DAHULU");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 150, 180, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 150, 880, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 300, 880, 1);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("III. RIWAYAT TUMBUH KEMBANG DAN PERINATAL CARE");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 300, 350, 23);

        jLabel44.setText("Anak ke :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(145, 320, 55, 23);

        Alergi1.setFocusTraversalPolicyProvider(true);
        Alergi1.setName("Alergi1"); // NOI18N
        Alergi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi1KeyPressed(evt);
            }
        });
        FormInput.add(Alergi1);
        Alergi1.setBounds(204, 320, 40, 23);

        Alergi2.setFocusTraversalPolicyProvider(true);
        Alergi2.setName("Alergi2"); // NOI18N
        Alergi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi2KeyPressed(evt);
            }
        });
        FormInput.add(Alergi2);
        Alergi2.setBounds(272, 320, 40, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("dari");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(246, 320, 24, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("saudara");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(315, 320, 50, 23);

        jLabel55.setText("Cara kelahiran :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(400, 320, 110, 23);

        AlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spontan", "Sectio Caesaria", "Lain-Lain" }));
        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(514, 320, 127, 23);

        KetBantu.setFocusTraversalPolicyProvider(true);
        KetBantu.setName("KetBantu"); // NOI18N
        KetBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBantuKeyPressed(evt);
            }
        });
        FormInput.add(KetBantu);
        KetBantu.setBounds(645, 320, 209, 23);

        AlatBantu1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        AlatBantu1.setName("AlatBantu1"); // NOI18N
        AlatBantu1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantu1KeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu1);
        AlatBantu1.setBounds(514, 350, 100, 23);

        jLabel56.setText("Kelainan Bawaan :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(400, 350, 110, 23);

        KetBantu1.setFocusTraversalPolicyProvider(true);
        KetBantu1.setName("KetBantu1"); // NOI18N
        KetBantu1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBantu1KeyPressed(evt);
            }
        });
        FormInput.add(KetBantu1);
        KetBantu1.setBounds(618, 350, 236, 23);

        AlatBantu2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cukup Bulan", "Kurang Bulan" }));
        AlatBantu2.setName("AlatBantu2"); // NOI18N
        AlatBantu2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantu2KeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu2);
        AlatBantu2.setBounds(150, 350, 140, 23);

        jLabel57.setText("Riwayat Kelahiran :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 320, 146, 23);

        jLabel58.setText("Umur Kelahiran :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 350, 146, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 380, 880, 1);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("IV. RIWAYAT IMUNISASI");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(10, 380, 350, 23);

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbImunisasi.setName("tbImunisasi"); // NOI18N
        Scroll6.setViewportView(tbImunisasi);

        FormInput.add(Scroll6);
        Scroll6.setBounds(94, 400, 760, 93);

        BtnTambahImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahImunisasi.setMnemonic('3');
        BtnTambahImunisasi.setToolTipText("Alt+3");
        BtnTambahImunisasi.setName("BtnTambahImunisasi"); // NOI18N
        BtnTambahImunisasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahImunisasiActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahImunisasi);
        BtnTambahImunisasi.setBounds(62, 400, 28, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 500, 880, 1);

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel97.setText("V. RIWAYAT TUMBUH KEMBANG ANAK");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(10, 500, 350, 23);

        jLabel59.setText("a. Tengkurap, usia :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 520, 133, 23);

        Alergi3.setFocusTraversalPolicyProvider(true);
        Alergi3.setName("Alergi3"); // NOI18N
        Alergi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi3KeyPressed(evt);
            }
        });
        FormInput.add(Alergi3);
        Alergi3.setBounds(137, 520, 90, 23);

        jLabel60.setText("b. Duduk, usia :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(244, 520, 90, 23);

        Alergi4.setFocusTraversalPolicyProvider(true);
        Alergi4.setName("Alergi4"); // NOI18N
        Alergi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi4KeyPressed(evt);
            }
        });
        FormInput.add(Alergi4);
        Alergi4.setBounds(337, 520, 90, 23);

        jLabel61.setText("c. Berdiri, usia :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(441, 520, 90, 23);

        Alergi5.setFocusTraversalPolicyProvider(true);
        Alergi5.setName("Alergi5"); // NOI18N
        Alergi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi5KeyPressed(evt);
            }
        });
        FormInput.add(Alergi5);
        Alergi5.setBounds(535, 520, 90, 23);

        jLabel62.setText("d. Gigi pertama, usia :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(630, 520, 130, 23);

        Alergi6.setFocusTraversalPolicyProvider(true);
        Alergi6.setName("Alergi6"); // NOI18N
        Alergi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi6KeyPressed(evt);
            }
        });
        FormInput.add(Alergi6);
        Alergi6.setBounds(764, 520, 90, 23);

        jLabel63.setText("e. Berjalan, usia :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(0, 550, 122, 23);

        Alergi7.setFocusTraversalPolicyProvider(true);
        Alergi7.setName("Alergi7"); // NOI18N
        Alergi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi7KeyPressed(evt);
            }
        });
        FormInput.add(Alergi7);
        Alergi7.setBounds(126, 550, 90, 23);

        jLabel64.setText("f. Bicara Usia, usia :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(301, 550, 110, 23);

        Alergi8.setFocusTraversalPolicyProvider(true);
        Alergi8.setName("Alergi8"); // NOI18N
        Alergi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi8KeyPressed(evt);
            }
        });
        FormInput.add(Alergi8);
        Alergi8.setBounds(415, 550, 90, 23);

        jLabel65.setText("g. Mulai bisa membaca, usia :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(580, 550, 180, 23);

        Alergi9.setFocusTraversalPolicyProvider(true);
        Alergi9.setName("Alergi9"); // NOI18N
        Alergi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi9KeyPressed(evt);
            }
        });
        FormInput.add(Alergi9);
        Alergi9.setBounds(764, 550, 90, 23);

        Alergi10.setFocusTraversalPolicyProvider(true);
        Alergi10.setName("Alergi10"); // NOI18N
        Alergi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi10KeyPressed(evt);
            }
        });
        FormInput.add(Alergi10);
        Alergi10.setBounds(176, 580, 90, 23);

        jLabel66.setText("h. Mulai bisa menulis, usia :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(0, 580, 172, 23);

        Alergi11.setFocusTraversalPolicyProvider(true);
        Alergi11.setName("Alergi11"); // NOI18N
        Alergi11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alergi11KeyPressed(evt);
            }
        });
        FormInput.add(Alergi11);
        Alergi11.setBounds(614, 580, 240, 23);

        jLabel67.setText("Gangguan perkembangan mental / emosi, bila ada, jelaskan :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(290, 580, 320, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 610, 880, 1);

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel125.setText("VI. FUNGSIONAL");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(10, 610, 230, 23);

        jLabel127.setText("Prothesa :");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(476, 630, 60, 23);

        AlatBantu3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AlatBantu3.setName("AlatBantu3"); // NOI18N
        AlatBantu3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantu3KeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu3);
        AlatBantu3.setBounds(124, 630, 90, 23);

        KetBantu2.setFocusTraversalPolicyProvider(true);
        KetBantu2.setName("KetBantu2"); // NOI18N
        KetBantu2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBantu2KeyPressed(evt);
            }
        });
        FormInput.add(KetBantu2);
        KetBantu2.setBounds(218, 630, 220, 23);

        Prothesa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Prothesa.setName("Prothesa"); // NOI18N
        Prothesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProthesaKeyPressed(evt);
            }
        });
        FormInput.add(Prothesa);
        Prothesa.setBounds(540, 630, 90, 23);

        KetProthesa.setFocusTraversalPolicyProvider(true);
        KetProthesa.setName("KetProthesa"); // NOI18N
        KetProthesa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetProthesaKeyPressed(evt);
            }
        });
        FormInput.add(KetProthesa);
        KetProthesa.setBounds(634, 630, 220, 23);

        jLabel128.setText("Alat Bantu :");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(0, 630, 120, 23);

        ADL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mandiri", "Dibantu" }));
        ADL.setName("ADL"); // NOI18N
        ADL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ADLKeyPressed(evt);
            }
        });
        FormInput.add(ADL);
        ADL.setBounds(724, 660, 130, 23);

        jLabel129.setText("Aktivitas Kehidupan Sehari-hari ( ADL ) :");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(440, 660, 280, 23);

        jLabel130.setText("Cacat Fisik :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(0, 660, 120, 23);

        CacatFisik.setEditable(false);
        CacatFisik.setFocusTraversalPolicyProvider(true);
        CacatFisik.setName("CacatFisik"); // NOI18N
        CacatFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CacatFisikKeyPressed(evt);
            }
        });
        FormInput.add(CacatFisik);
        CacatFisik.setBounds(124, 660, 314, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 690, 880, 1);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("VII. RIWAYAT PSIKO-SOSIAL, SPIRITUAL DAN BUDAYA");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(10, 690, 380, 23);

        StatusPsiko.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tenang", "Takut", "Tempertantrum", "Cemas", "Depresi", "Lain-lain" }));
        StatusPsiko.setName("StatusPsiko"); // NOI18N
        StatusPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPsikoKeyPressed(evt);
            }
        });
        FormInput.add(StatusPsiko);
        StatusPsiko.setBounds(134, 710, 140, 23);

        KetPsiko.setFocusTraversalPolicyProvider(true);
        KetPsiko.setName("KetPsiko"); // NOI18N
        KetPsiko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetPsikoKeyPressed(evt);
            }
        });
        FormInput.add(KetPsiko);
        KetPsiko.setBounds(278, 710, 200, 23);

        jLabel132.setText("Status Psikologis :");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(0, 710, 130, 23);

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N
        Bahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BahasaKeyPressed(evt);
            }
        });
        FormInput.add(Bahasa);
        Bahasa.setBounds(684, 710, 170, 23);

        jLabel133.setText("Bahasa yang digunakan sehari-hari :");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(450, 710, 230, 23);

        jLabel134.setText("Status Sosial dan ekonomi :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(0, 740, 176, 23);

        jLabel135.setText("a. Hubungan dengan anggota keluarga :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(35, 760, 210, 23);

        HubunganKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Tidak Baik" }));
        HubunganKeluarga.setName("HubunganKeluarga"); // NOI18N
        HubunganKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(HubunganKeluarga);
        HubunganKeluarga.setBounds(249, 760, 100, 23);

        TinggalDengan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Orang Tua", "Kakek/Nenek", "Keluarga Lainnya" }));
        TinggalDengan.setName("TinggalDengan"); // NOI18N
        TinggalDengan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggalDenganKeyPressed(evt);
            }
        });
        FormInput.add(TinggalDengan);
        TinggalDengan.setBounds(432, 760, 135, 23);

        KetTinggal.setFocusTraversalPolicyProvider(true);
        KetTinggal.setName("KetTinggal"); // NOI18N
        KetTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetTinggalKeyPressed(evt);
            }
        });
        FormInput.add(KetTinggal);
        KetTinggal.setBounds(570, 760, 85, 23);

        jLabel136.setText("b. Pengasuh :");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(353, 760, 75, 23);

        Ekonomi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Cukup", "Kurang" }));
        Ekonomi.setName("Ekonomi"); // NOI18N
        Ekonomi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkonomiKeyPressed(evt);
            }
        });
        FormInput.add(Ekonomi);
        Ekonomi.setBounds(770, 760, 84, 23);

        jLabel137.setText("c. Ekonomi (Ortu) :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(656, 760, 110, 23);

        jLabel138.setText("Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan :");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(0, 790, 366, 23);

        StatusBudaya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Ada" }));
        StatusBudaya.setName("StatusBudaya"); // NOI18N
        StatusBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusBudayaKeyPressed(evt);
            }
        });
        FormInput.add(StatusBudaya);
        StatusBudaya.setBounds(370, 790, 110, 23);

        KetBudaya.setFocusTraversalPolicyProvider(true);
        KetBudaya.setName("KetBudaya"); // NOI18N
        KetBudaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetBudayaKeyPressed(evt);
            }
        });
        FormInput.add(KetBudaya);
        KetBudaya.setBounds(484, 790, 370, 23);

        jLabel139.setText("Edukasi diberikan kepada :");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(226, 820, 140, 23);

        Edukasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Orang Tua", "Keluarga" }));
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        FormInput.add(Edukasi);
        Edukasi.setBounds(370, 820, 110, 23);

        KetEdukasi.setFocusTraversalPolicyProvider(true);
        KetEdukasi.setName("KetEdukasi"); // NOI18N
        KetEdukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetEdukasiKeyPressed(evt);
            }
        });
        FormInput.add(KetEdukasi);
        KetEdukasi.setBounds(484, 820, 370, 23);

        jLabel140.setText("Agama :");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(0, 820, 82, 23);

        Agama.setEditable(false);
        Agama.setFocusTraversalPolicyProvider(true);
        Agama.setName("Agama"); // NOI18N
        Agama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgamaKeyPressed(evt);
            }
        });
        FormInput.add(Agama);
        Agama.setBounds(86, 820, 110, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 850, 880, 1);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel141.setText("VIII. PENILAIAN RESIKO JATUH");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(10, 850, 380, 23);

        jLabel142.setText("a. Cara berjalan pasien (salah satu atau lebih) :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(0, 870, 273, 23);

        jLabel143.setText("1. Tidak seimbang/ Sempoyongan/ Limbung :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(35, 890, 250, 23);

        RJa1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RJa1.setName("RJa1"); // NOI18N
        RJa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RJa1KeyPressed(evt);
            }
        });
        FormInput.add(RJa1);
        RJa1.setBounds(289, 890, 80, 23);

        jLabel144.setText("2. Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain) :");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(370, 890, 400, 23);

        RJa2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RJa2.setName("RJa2"); // NOI18N
        RJa2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RJa2KeyPressed(evt);
            }
        });
        FormInput.add(RJa2);
        RJa2.setBounds(774, 890, 80, 23);

        jLabel145.setText("b. Duduk di kursi tanpa menggunakan tangan sebagai penopang (tampak memegang kursi atau meja/ benda lain) :");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(0, 920, 599, 23);

        RJb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        RJb.setName("RJb"); // NOI18N
        RJb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RJbKeyPressed(evt);
            }
        });
        FormInput.add(RJb);
        RJb.setBounds(603, 920, 80, 23);

        Lapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Lapor.setName("Lapor"); // NOI18N
        Lapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporKeyPressed(evt);
            }
        });
        FormInput.add(Lapor);
        Lapor.setBounds(575, 950, 80, 23);

        Hasil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak beresiko (tidak ditemukan a dan b)", "Resiko rendah (ditemukan a/b)", "Resiko tinggi (ditemukan a dan b)" }));
        Hasil.setName("Hasil"); // NOI18N
        Hasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilKeyPressed(evt);
            }
        });
        FormInput.add(Hasil);
        Hasil.setBounds(76, 950, 293, 23);

        jLabel146.setText("Hasil :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(0, 950, 72, 23);

        KetLapor.setFocusTraversalPolicyProvider(true);
        KetLapor.setName("KetLapor"); // NOI18N
        KetLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetLaporKeyPressed(evt);
            }
        });
        FormInput.add(KetLapor);
        KetLapor.setBounds(774, 950, 80, 23);

        jLabel147.setText("Dilaporkan kepada dokter ?");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(381, 950, 190, 23);

        jLabel148.setText("Jam dilaporkan :");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(680, 950, 90, 23);

        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel149.setText("IX. SKRINING GIZI (Strong kid)");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(10, 980, 380, 23);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 980, 880, 1);

        SG1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG1.setName("SG1"); // NOI18N
        SG1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG1ItemStateChanged(evt);
            }
        });
        SG1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG1KeyPressed(evt);
            }
        });
        FormInput.add(SG1);
        SG1.setBounds(700, 1000, 80, 23);

        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText("1.  Apakah pasien tampak kurus");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(40, 1000, 610, 23);

        jLabel152.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel152.setText("Apakah terdapat salah satu dari kondisi tersebut? Diare > 5 kali/hari dan/muntah > 3 kali/hari salam seminggu terakhir;");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(55, 1075, 610, 23);

        SG2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG2.setName("SG2"); // NOI18N
        SG2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG2ItemStateChanged(evt);
            }
        });
        SG2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG2KeyPressed(evt);
            }
        });
        FormInput.add(SG2);
        SG2.setBounds(700, 1037, 80, 23);

        jLabel154.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel154.setText("bila ada atau untuk bayi < 1 tahun ; berat badan tidak naik selama 3 bulan terakhir)");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(55, 1045, 600, 23);

        jLabel155.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel155.setText("2.");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(40, 1030, 20, 37);

        jLabel156.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel156.setText("Asupan makanan berkurang selama 1 minggu terakhir");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(55, 1090, 610, 23);

        SG3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG3.setName("SG3"); // NOI18N
        SG3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG3ItemStateChanged(evt);
            }
        });
        SG3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG3KeyPressed(evt);
            }
        });
        FormInput.add(SG3);
        SG3.setBounds(700, 1077, 80, 23);

        jLabel158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel158.setText("Apakah terdapat penurunan berat badan selama satu bulan terakhir? (berdasarkan penilaian objektif data berat badan");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(55, 1030, 600, 23);

        jLabel159.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel159.setText("3.");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(40, 1075, 20, 37);

        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText("4.  Apakah terdapat penyakit atau keadaan yang menyebabkan pasien beresiko mengalami malnutrisi?");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(40, 1120, 610, 23);

        Wajah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tersenyum/tidak ada ekspresi khusus", "Terkadang meringis/menarik diri", "Sering menggetarkan dagu dan mengatupkan rahang" }));
        Wajah.setName("Wajah"); // NOI18N
        Wajah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                WajahItemStateChanged(evt);
            }
        });
        Wajah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WajahKeyPressed(evt);
            }
        });
        FormInput.add(Wajah);
        Wajah.setBounds(108, 1220, 310, 23);

        jLabel162.setText("Total Skor :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(680, 1150, 90, 23);

        NilaiGizi1.setEditable(false);
        NilaiGizi1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi1.setText("0");
        NilaiGizi1.setFocusTraversalPolicyProvider(true);
        NilaiGizi1.setName("NilaiGizi1"); // NOI18N
        NilaiGizi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi1KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi1);
        NilaiGizi1.setBounds(794, 1000, 60, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 1180, 880, 1);

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("X. PENILAIAN TINGKAT NYERI");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(10, 1180, 380, 23);

        jLabel164.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel164.setText("Skala FLACCS :");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(40, 1200, 210, 23);

        jLabel165.setText("Wajah :");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(44, 1220, 60, 23);

        jLabel166.setText("Kaki :");
        jLabel166.setName("jLabel166"); // NOI18N
        FormInput.add(jLabel166);
        jLabel166.setBounds(44, 1250, 60, 23);

        jLabel167.setText("Aktifitas :");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(44, 1280, 60, 23);

        jLabel168.setText("Menangis :");
        jLabel168.setName("jLabel168"); // NOI18N
        FormInput.add(jLabel168);
        jLabel168.setBounds(480, 1220, 60, 23);

        jLabel169.setText("Bersuara :");
        jLabel169.setName("jLabel169"); // NOI18N
        FormInput.add(jLabel169);
        jLabel169.setBounds(480, 1250, 60, 23);

        SG4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SG4.setName("SG4"); // NOI18N
        SG4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG4ItemStateChanged(evt);
            }
        });
        SG4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG4KeyPressed(evt);
            }
        });
        FormInput.add(SG4);
        SG4.setBounds(700, 1120, 80, 23);

        NilaiGizi2.setEditable(false);
        NilaiGizi2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi2.setText("0");
        NilaiGizi2.setFocusTraversalPolicyProvider(true);
        NilaiGizi2.setName("NilaiGizi2"); // NOI18N
        NilaiGizi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi2KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi2);
        NilaiGizi2.setBounds(794, 1037, 60, 23);

        NilaiGizi3.setEditable(false);
        NilaiGizi3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi3.setText("0");
        NilaiGizi3.setFocusTraversalPolicyProvider(true);
        NilaiGizi3.setName("NilaiGizi3"); // NOI18N
        NilaiGizi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi3KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi3);
        NilaiGizi3.setBounds(794, 1077, 60, 23);

        NilaiGizi4.setEditable(false);
        NilaiGizi4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        NilaiGizi4.setText("0");
        NilaiGizi4.setFocusTraversalPolicyProvider(true);
        NilaiGizi4.setName("NilaiGizi4"); // NOI18N
        NilaiGizi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NilaiGizi4KeyPressed(evt);
            }
        });
        FormInput.add(NilaiGizi4);
        NilaiGizi4.setBounds(794, 1120, 60, 23);

        TotalHasil4.setEditable(false);
        TotalHasil4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalHasil4.setText("0");
        TotalHasil4.setFocusTraversalPolicyProvider(true);
        TotalHasil4.setName("TotalHasil4"); // NOI18N
        TotalHasil4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasil4KeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil4);
        TotalHasil4.setBounds(422, 1220, 40, 23);

        TotalNilaiGizi.setEditable(false);
        TotalNilaiGizi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalNilaiGizi.setText("0");
        TotalNilaiGizi.setFocusTraversalPolicyProvider(true);
        TotalNilaiGizi.setName("TotalNilaiGizi"); // NOI18N
        TotalNilaiGizi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalNilaiGiziKeyPressed(evt);
            }
        });
        FormInput.add(TotalNilaiGizi);
        TotalNilaiGizi.setBounds(774, 1150, 80, 23);

        SG6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gerakan normal/relaksasi", "Tidak tenang/tegang", "Kaki dibuat menendang/menarik" }));
        SG6.setName("SG6"); // NOI18N
        SG6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG6ItemStateChanged(evt);
            }
        });
        SG6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG6KeyPressed(evt);
            }
        });
        FormInput.add(SG6);
        SG6.setBounds(108, 1250, 310, 23);

        TotalHasil6.setEditable(false);
        TotalHasil6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalHasil6.setText("0");
        TotalHasil6.setFocusTraversalPolicyProvider(true);
        TotalHasil6.setName("TotalHasil6"); // NOI18N
        TotalHasil6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasil6KeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil6);
        TotalHasil6.setBounds(422, 1250, 40, 23);

        SG7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidur posisi normal, mudah bergerak", "Gerakan menggeliat/berguling, kaku", "Melengkungkan punggung/kaku menghentak" }));
        SG7.setName("SG7"); // NOI18N
        SG7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG7ItemStateChanged(evt);
            }
        });
        SG7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG7KeyPressed(evt);
            }
        });
        FormInput.add(SG7);
        SG7.setBounds(108, 1280, 310, 23);

        TotalHasil7.setEditable(false);
        TotalHasil7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalHasil7.setText("0");
        TotalHasil7.setFocusTraversalPolicyProvider(true);
        TotalHasil7.setName("TotalHasil7"); // NOI18N
        TotalHasil7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasil7KeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil7);
        TotalHasil7.setBounds(422, 1280, 40, 23);

        SG8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak menangis (mudah bergerak)", "Mengerang/merengek", "Menangis terus menerus, terisak, menjerit" }));
        SG8.setName("SG8"); // NOI18N
        SG8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG8ItemStateChanged(evt);
            }
        });
        SG8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG8KeyPressed(evt);
            }
        });
        FormInput.add(SG8);
        SG8.setBounds(544, 1220, 266, 23);

        TotalHasil8.setEditable(false);
        TotalHasil8.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalHasil8.setText("0");
        TotalHasil8.setFocusTraversalPolicyProvider(true);
        TotalHasil8.setName("TotalHasil8"); // NOI18N
        TotalHasil8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasil8KeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil8);
        TotalHasil8.setBounds(814, 1220, 40, 23);

        SG9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bersuara normal/tenang", "Tenang bila dipeluk, digendong/diajak bicara", "Sulit untuk menenangkan" }));
        SG9.setName("SG9"); // NOI18N
        SG9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG9ItemStateChanged(evt);
            }
        });
        SG9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG9KeyPressed(evt);
            }
        });
        FormInput.add(SG9);
        SG9.setBounds(544, 1250, 266, 23);

        TotalHasil9.setEditable(false);
        TotalHasil9.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalHasil9.setText("0");
        TotalHasil9.setFocusTraversalPolicyProvider(true);
        TotalHasil9.setName("TotalHasil9"); // NOI18N
        TotalHasil9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasil9KeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil9);
        TotalHasil9.setBounds(814, 1250, 40, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri.png"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput.add(PanelWall);
        PanelWall.setBounds(40, 1315, 320, 115);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(365, 1310, 1, 125);

        jLabel170.setText("Skala nyeri :");
        jLabel170.setName("jLabel170"); // NOI18N
        FormInput.add(jLabel170);
        jLabel170.setBounds(680, 1280, 90, 23);

        TotalHasil10.setEditable(false);
        TotalHasil10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TotalHasil10.setText("0");
        TotalHasil10.setFocusTraversalPolicyProvider(true);
        TotalHasil10.setName("TotalHasil10"); // NOI18N
        TotalHasil10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalHasil10KeyPressed(evt);
            }
        });
        FormInput.add(TotalHasil10);
        TotalHasil10.setBounds(774, 1280, 80, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Nyeri", "Nyeri Akut", "Nyeri Kronis" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(375, 1315, 140, 23);

        jLabel88.setText("Frekuensi :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(600, 1345, 100, 23);

        Frekuensi.setFocusTraversalPolicyProvider(true);
        Frekuensi.setName("Frekuensi"); // NOI18N
        Frekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiKeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi);
        Frekuensi.setBounds(704, 1345, 150, 23);

        jLabel83.setText("Lokasi :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(604, 1315, 46, 23);

        Lokasi.setFocusTraversalPolicyProvider(true);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(654, 1315, 200, 23);

        Durasi.setFocusTraversalPolicyProvider(true);
        Durasi.setName("Durasi"); // NOI18N
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(419, 1345, 150, 23);

        jLabel87.setText("Durasi :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(370, 1345, 45, 23);

        jLabel89.setText("Nyeri hilang bila :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(370, 1375, 93, 23);

        NyeriHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Minum Obat", "Istirahat", "Mendengar Music", "Berubah Posisi/Tidur", "Lain-lain" }));
        NyeriHilang.setName("NyeriHilang"); // NOI18N
        NyeriHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriHilangKeyPressed(evt);
            }
        });
        FormInput.add(NyeriHilang);
        NyeriHilang.setBounds(467, 1375, 165, 23);

        KetNyeri.setFocusTraversalPolicyProvider(true);
        KetNyeri.setName("KetNyeri"); // NOI18N
        KetNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetNyeriKeyPressed(evt);
            }
        });
        FormInput.add(KetNyeri);
        KetNyeri.setBounds(636, 1375, 218, 23);

        jLabel86.setText("Diberitahukan pada dokter ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(381, 1405, 190, 23);

        PadaDokter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        PadaDokter.setName("PadaDokter"); // NOI18N
        PadaDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PadaDokterKeyPressed(evt);
            }
        });
        FormInput.add(PadaDokter);
        PadaDokter.setBounds(575, 1405, 80, 23);

        jLabel68.setText("Jam diberitahukan :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(660, 1405, 110, 23);

        KetDokter.setFocusTraversalPolicyProvider(true);
        KetDokter.setName("KetDokter"); // NOI18N
        KetDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDokterKeyPressed(evt);
            }
        });
        FormInput.add(KetDokter);
        KetDokter.setBounds(774, 1405, 80, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 1435, 880, 1);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbMasalahKeperawatan.setName("tbMasalahKeperawatan"); // NOI18N
        Scroll8.setViewportView(tbMasalahKeperawatan);

        FormInput.add(Scroll8);
        Scroll8.setBounds(10, 1445, 400, 133);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Rencana Keperawatan :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(453, 1445, 140, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(5);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Rencana);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(453, 1465, 400, 143);

        BtnTambahMasalah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahMasalah1.setMnemonic('3');
        BtnTambahMasalah1.setToolTipText("Alt+3");
        BtnTambahMasalah1.setName("BtnTambahMasalah1"); // NOI18N
        BtnTambahMasalah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahMasalah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahMasalah1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambahMasalah1);
        BtnTambahMasalah1.setBounds(363, 1585, 28, 23);

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
        FormInput.add(BtnCariPemeriksaan1);
        BtnCariPemeriksaan1.setBounds(330, 1585, 28, 23);

        TCariMasalah.setToolTipText("Alt+C");
        TCariMasalah.setName("TCariMasalah"); // NOI18N
        TCariMasalah.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariMasalah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMasalahKeyPressed(evt);
            }
        });
        FormInput.add(TCariMasalah);
        TCariMasalah.setBounds(80, 1585, 245, 23);

        label12.setText("Key Word :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label12);
        label12.setBounds(16, 1585, 60, 23);

        BtnPanggilHapusImunisasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnPanggilHapusImunisasi.setMnemonic('3');
        BtnPanggilHapusImunisasi.setToolTipText("Alt+3");
        BtnPanggilHapusImunisasi.setName("BtnPanggilHapusImunisasi"); // NOI18N
        BtnPanggilHapusImunisasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPanggilHapusImunisasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanggilHapusImunisasiActionPerformed(evt);
            }
        });
        FormInput.add(BtnPanggilHapusImunisasi);
        BtnPanggilHapusImunisasi.setBounds(62, 430, 28, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-09-2020" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-09-2020" }));
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

        FormMasalahRencana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        FormMasalahRencana.setName("FormMasalahRencana"); // NOI18N
        FormMasalahRencana.setLayout(new java.awt.GridLayout(2, 0, 1, 1));

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbMasalahDetailMasalah.setName("tbMasalahDetailMasalah"); // NOI18N
        Scroll7.setViewportView(tbMasalahDetailMasalah);

        FormMasalahRencana.add(Scroll7);

        scrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 254)), "Rencana Keperawatan :", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        scrollPane6.setName("scrollPane6"); // NOI18N

        DetailRencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        DetailRencana.setColumns(20);
        DetailRencana.setRows(5);
        DetailRencana.setName("DetailRencana"); // NOI18N
        DetailRencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetailRencanaKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(DetailRencana);

        FormMasalahRencana.add(scrollPane6);

        PanelAccor.add(FormMasalahRencana, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

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
        /*if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(GCS.getText().trim().equals("")){
            Valid.textKosong(GCS,"GCS");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"BB(Kg)");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"TB(Cm)");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI(Kg/m2)");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengobatan");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(TotalHasil.getText().trim().equals("")){
            Valid.textKosong(TotalHasil,"Total Hasil");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(Rencana.getText().trim().equals("")){
            Valid.textKosong(Rencana,"Rencana");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
            if(Sequel.menyimpantf("penilaian_awal_keperawatan_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",57,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),
                    Suhu.getText(),GCS.getText(),BB.getText(),TB.getText(),BMI.getText(),KeluhanUtama.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),AlatBantu.getSelectedItem().toString(),KetBantu.getText(), 
                    Prothesa.getSelectedItem().toString(),KetProthesa.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(),HubunganKeluarga.getSelectedItem().toString(), 
                    TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),StatusBudaya.getSelectedItem().toString(),KetBudaya.getText(),Edukasi.getSelectedItem().toString(), 
                    KetEdukasi.getText(),ATS.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),Hasil.getSelectedItem().toString(),Lapor.getSelectedItem().toString(),KetLapor.getText(), 
                    SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),Nyeri.getSelectedItem().toString(),
                    Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),
                    Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),Rencana.getText(),KdPetugas.getText()
                })==true){
                    for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                        if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                            Sequel.menyimpan2("penilaian_awal_keperawatan_ralan_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                        }
                    }
                    emptTeks();
            }
        }*/
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Rencana,BtnBatal);
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
            if(Sequel.queryu2tf("delete from penilaian_awal_keperawatan_ralan where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                TNoRM1.setText("");
                TPasien1.setText("");
                Sequel.meghapus("penilaian_awal_keperawatan_ralan_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                Valid.tabelKosong(tabModeDetailMasalah);
                ChkAccor.setSelected(false);
                isMenu();
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
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
        }else if(TD.getText().trim().equals("")){
            Valid.textKosong(TD,"TD(mmHg)");
        }else if(Nadi.getText().trim().equals("")){
            Valid.textKosong(Nadi,"Nadi(x/menit)");
        }else if(RR.getText().trim().equals("")){
            Valid.textKosong(RR,"RR(x/menit)");
        }else if(Suhu.getText().trim().equals("")){
            Valid.textKosong(Suhu,"Suhu(C)");
        }else if(GCS.getText().trim().equals("")){
            Valid.textKosong(GCS,"GCS");
        }else if(BB.getText().trim().equals("")){
            Valid.textKosong(BB,"BB(Kg)");
        }else if(TB.getText().trim().equals("")){
            Valid.textKosong(TB,"TB(Cm)");
        }else if(BMI.getText().trim().equals("")){
            Valid.textKosong(BMI,"BMI(Kg/m2)");
        }else if(KeluhanUtama.getText().trim().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan Utama");
        }else if(RPD.getText().trim().equals("")){
            Valid.textKosong(RPD,"Riwayat Penyakit Dahulu");
        }else if(RPK.getText().trim().equals("")){
            Valid.textKosong(RPK,"Riwayat Penyakit Keluarga");
        }else if(RPO.getText().trim().equals("")){
            Valid.textKosong(RPO,"Riwayat Pengobatan");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Alergi");
        }else if(TotalHasil.getText().trim().equals("")){
            Valid.textKosong(TotalHasil,"Total Hasil");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(Rencana.getText().trim().equals("")){
            Valid.textKosong(Rencana,"Rencana");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("penilaian_awal_keperawatan_ralan","no_rawat=?","no_rawat=?,tanggal=?,informasi=?,td=?,nadi=?,rr=?,suhu=?,gcs=?,bb=?,tb=?,bmi=?,keluhan_utama=?,rpd=?,rpk=?,rpo=?,alergi=?,alat_bantu=?,ket_bantu=?,prothesa=?,ket_pro=?,adl=?,status_psiko=?,ket_psiko=?,hub_keluarga=?,tinggal_dengan=?,ket_tinggal=?,ekonomi=?,budaya=?,ket_budaya=?,edukasi=?,ket_edukasi=?,berjalan_a=?,berjalan_b=?,berjalan_c=?,hasil=?,lapor=?,ket_lapor=?,sg1=?,nilai1=?,sg2=?,nilai2=?,total_hasil=?,nyeri=?,provokes=?,ket_provokes=?,quality=?,ket_quality=?,lokasi=?,menyebar=?,skala_nyeri=?,durasi=?,nyeri_hilang=?,ket_nyeri=?,pada_dokter=?,ket_dokter=?,rencana=?,nip=?",58,new String[]{
                        TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),Informasi.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),
                        Suhu.getText(),GCS.getText(),BB.getText(),TB.getText(),BMI.getText(),KeluhanUtama.getText(),RPD.getText(),RPK.getText(),RPO.getText(),Alergi.getText(),AlatBantu.getSelectedItem().toString(),KetBantu.getText(), 
                        Prothesa.getSelectedItem().toString(),KetProthesa.getText(),ADL.getSelectedItem().toString(),StatusPsiko.getSelectedItem().toString(),KetPsiko.getText(),HubunganKeluarga.getSelectedItem().toString(), 
                        TinggalDengan.getSelectedItem().toString(),KetTinggal.getText(),Ekonomi.getSelectedItem().toString(),StatusBudaya.getSelectedItem().toString(),KetBudaya.getText(),Edukasi.getSelectedItem().toString(), 
                        KetEdukasi.getText(),ATS.getSelectedItem().toString(),BJM.getSelectedItem().toString(),MSA.getSelectedItem().toString(),Hasil.getSelectedItem().toString(),Lapor.getSelectedItem().toString(),KetLapor.getText(), 
                        SG1.getSelectedItem().toString(),Nilai1.getSelectedItem().toString(),SG2.getSelectedItem().toString(),Nilai2.getSelectedItem().toString(),TotalHasil.getText(),Nyeri.getSelectedItem().toString(),
                        Provokes.getSelectedItem().toString(),KetProvokes.getText(),Quality.getSelectedItem().toString(),KetQuality.getText(),Lokasi.getText(),Menyebar.getSelectedItem().toString(),SkalaNyeri.getSelectedItem().toString(),
                        Durasi.getText(),NyeriHilang.getSelectedItem().toString(),KetNyeri.getText(),PadaDokter.getSelectedItem().toString(),KetDokter.getText(),Rencana.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                     })==true){
                        Sequel.meghapus("penilaian_awal_keperawatan_ralan_masalah","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                        for (i = 0; i < tbMasalahKeperawatan.getRowCount(); i++) {
                            if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                                Sequel.menyimpan2("penilaian_awal_keperawatan_ralan_masalah","?,?",2,new String[]{TNoRw.getText(),tbMasalahKeperawatan.getValueAt(i,1).toString()});
                            }
                        }
                        getMasalah();
                        tampil();
                        emptTeks();
                        TabRawat.setSelectedIndex(1);
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
                if(TCari.getText().equals("")){
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                            "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                            "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                            "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                            "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                            "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                            "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                            "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                            "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                            "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? order by penilaian_awal_keperawatan_ralan.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                            "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                            "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                            "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                            "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                            "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                            "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                            "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                            "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                            "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                            "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                            "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                            "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                            "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                            "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and penilaian_awal_keperawatan_ralan.nip like ? or "+
                            "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and petugas.nama like ? order by penilaian_awal_keperawatan_ralan.tanggal");
                }

                try {
                    if(TCari.getText().equals("")){
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    }else{
                        ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(9,"%"+TCari.getText()+"%");
                        ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(12,"%"+TCari.getText()+"%");
                        ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                        ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                        ps.setString(15,"%"+TCari.getText()+"%");
                    }   
                    rs=ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'><b>PASIEN & PETUGAS</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'><b>I. KEADAAN UMUM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'><b>II. STATUS NUTRISI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='13%'><b>III. RIWAYAT KESEHATAN</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'><b>IV. FUNGSIONAL</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='16%'><b>V. RIWAYAT PSIKO-SOSIAL SPIRITUAL DAN BUDAYA</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='16%'><b>VI. PENILAIAN RESIKO JATUH</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'><b>VII. SKRINING GIZI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'><b>VIII. PENILAIAN TINGKAT NYERI</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'><b>MASALAH & RENCANA KEPERAWATAN</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        masalahkeperawatan="";
                        ps2=koneksi.prepareStatement(
                            "select master_masalah_keperawatan_anak.kode_masalah,master_masalah_keperawatan_anak.nama_masalah from master_masalah_keperawatan_anak "+
                            "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan_anak.kode_masalah "+
                            "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                masalahkeperawatan=rs2.getString("nama_masalah")+", "+masalahkeperawatan;
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
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>No.Rawat</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("no_rawat")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>No.R.M.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>J.K.</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("jk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Agama</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("agama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Bahasa</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nama_bahasa")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tgl.Lahir</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nama_cacat")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Cacat Fisik</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Petugas</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("nip")+" "+rs.getString("nama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Tgl.Asuhan</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("tanggal")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Informasi</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("informasi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TD</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("td")+"mmHg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Nadi</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("nadi")+"x/menit</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>RR</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("rr")+"x/menit</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>Suhu</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("suhu")+"°C</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>GCS</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("gcs")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>BB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("bb")+"Kg</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>TB</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("tb")+"cm</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='34%' valign='top'>BMI</td><td valign='top'>:&nbsp;</td><td width='65%' valign='top'>"+rs.getString("bmi")+"Kg/m²</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Keluhan Utama</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("keluhan_utama")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPD</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpd")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPK</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpk")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>RPO</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("rpo")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='32%' valign='top'>Alergi</td><td valign='top'>:&nbsp;</td><td width='67%' valign='top'>"+rs.getString("alergi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Alat Bantu</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("alat_bantu")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Alat Bantu</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_bantu")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Prothesa</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("prothesa")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Prothesa</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_pro")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>ADL</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("adl")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Status Psikologis</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("status_psiko")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Psikologi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_psiko")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Hubungan pasien dengan anggota keluarga</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("hub_keluarga")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Tinggal dengan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("tinggal_dengan")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Tinggal</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_tinggal")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ekonomi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ekonomi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Kepercayaan / Budaya / Nilai-nilai khusus yang perlu diperhatikan</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("budaya")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Budaya</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_budaya")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Edukasi diberikan kepada </td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("edukasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Ket. Edukasi</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_edukasi")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Tidak seimbang/sempoyongan/limbung</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_a")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Jalan dengan menggunakan alat bantu (kruk, tripot, kursi roda, orang lain)</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_b")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Menopang saat akan duduk, tampak memegang pinggiran kursi atau meja/benda lain sebagai penopang</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("berjalan_c")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Hasil</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("hasil")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Dilaporan ke dokter?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("lapor")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("ket_lapor")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Apakah ada penurunan berat badanyang tidak diinginkan selama enam bulan terakhir?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Apakah nafsu makan berkurang karena tidak nafsu makan?</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("sg2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Nilai 1</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai1")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Nilai 2</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("nilai2")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='64%' valign='top'>Total Skor</td><td valign='top'>:&nbsp;</td><td width='35%' valign='top'>"+rs.getString("total_hasil")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "<table width='100%' border='0' cellpadding='0' cellspacing='0'align='center'>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Tingkat Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Provokes</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("provokes")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Provokes</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_provokes")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Kualitas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("quality")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Kualitas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_quality")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Lokas</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("lokasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Menyebar</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("menyebar")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Skala Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("skala_nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Durasi</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("durasi")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Nyeri Hilang</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("nyeri_hilang")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Ket. Hilang Nyeri</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_nyeri")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Lapor Ke Dokter</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("pada_dokter")+"</td>"+
                                        "</tr>"+
                                        "<tr class='isi2'>"+
                                            "<td width='44%' valign='top'>Jam Lapor</td><td valign='top'>:&nbsp;</td><td width='55%' valign='top'>"+rs.getString("ket_dokter")+"</td>"+
                                        "</tr>"+
                                    "</table>"+
                                "</td>"+
                                "<td valign='top' cellpadding='0' cellspacing='0'>"+
                                    "Masalah Keperawatan : "+masalahkeperawatan+"<br><br>"+
                                    "Rencana Keperawatan : "+rs.getString("rencana")+
                                "</td>"+
                            "</tr>"
                        );
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='1800px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalKeperawatanRalan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='1800px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL KEPERAWATAN RAWAT JALAN<br><br></font>"+        
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
                ChkAccor.setSelected(true);
                isMenu();
                getMasalah();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    ChkAccor.setSelected(true);
                    isMenu();
                    getMasalah();
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

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,GCS);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Informasi,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void InformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InformasiKeyPressed
        Valid.pindah(evt,TglAsuhan,TD);
    }//GEN-LAST:event_InformasiKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        //Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void GCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GCSKeyPressed
        //Valid.pindah(evt,Suhu,BB);
    }//GEN-LAST:event_GCSKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilMasalah();
    }//GEN-LAST:event_formWindowOpened

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isMenu();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
       if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("nyeri",Sequel.cariGambar("select nyeri from gambar")); 
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),63).toString()));
            try {
                masalahkeperawatan="";
                ps2=koneksi.prepareStatement(
                    "select master_masalah_keperawatan_anak.kode_masalah,master_masalah_keperawatan_anak.nama_masalah from master_masalah_keperawatan_anak "+
                    "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan_anak.kode_masalah "+
                    "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps2.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        masalahkeperawatan=rs2.getString("nama_masalah")+", "+masalahkeperawatan;
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
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
            param.put("masalah",masalahkeperawatan);  
            Valid.MyReportqry("rptCetakPenilaianAwalKeperawatanRalan.jasper","report","::[ Laporan Penilaian Awal Keperawatan Ralan ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                        "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                        "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                        "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                        "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                        "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                        "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                        "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                        "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }  
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void DetailRencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetailRencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DetailRencanaKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBKeyPressed

    private void LKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LKKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BBKeyPressed

    private void LPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LPKeyPressed

    private void LDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LDKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        //Valid.pindah(evt,RPO,AlatBantu);
    }//GEN-LAST:event_AlergiKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        //Valid.pindah(evt,BMI,RPK);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void RPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPDKeyPressed
        Valid.pindah(evt,RPK,RPO);
    }//GEN-LAST:event_RPDKeyPressed

    private void RPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPKKeyPressed
        Valid.pindah(evt,KeluhanUtama,RPD);
    }//GEN-LAST:event_RPKKeyPressed

    private void RPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RPOKeyPressed
        Valid.pindah(evt,RPD,Alergi);
    }//GEN-LAST:event_RPOKeyPressed

    private void Alergi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi1KeyPressed

    private void Alergi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi2KeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        Valid.pindah(evt,Alergi,KetBantu);
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void KetBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBantuKeyPressed
        //Valid.pindah(evt,AlatBantu,Prothesa);
    }//GEN-LAST:event_KetBantuKeyPressed

    private void AlatBantu1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantu1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlatBantu1KeyPressed

    private void KetBantu1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBantu1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetBantu1KeyPressed

    private void AlatBantu2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantu2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlatBantu2KeyPressed

    private void BtnTambahImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahImunisasiActionPerformed
        if(TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu pasien yang mau dimasukkan data riwayat imunisasinya...");
            Informasi.requestFocus();
        }else{
            KdImunisasi.setText("");
            NmImunisasi.setText("");
            ImunisasiKe.setSelectedIndex(0);
            BtnImunisasi.setEnabled(true);
            BtnSimpanImunisasi.setVisible(true);
            BtnHapusImunisasi.setVisible(false);
            DlgRiwayatImunisasi.setLocationRelativeTo(internalFrame1);
            DlgRiwayatImunisasi.setVisible(true);
        }
    }//GEN-LAST:event_BtnTambahImunisasiActionPerformed

    private void Alergi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi3KeyPressed

    private void Alergi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi4KeyPressed

    private void Alergi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi5KeyPressed

    private void Alergi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi6KeyPressed

    private void Alergi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi7KeyPressed

    private void Alergi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi8KeyPressed

    private void Alergi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi9KeyPressed

    private void Alergi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi10KeyPressed

    private void Alergi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alergi11KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Alergi11KeyPressed

    private void AlatBantu3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantu3KeyPressed
        //Valid.pindah(evt,KebiasaanNarkoba,KetBantu);
    }//GEN-LAST:event_AlatBantu3KeyPressed

    private void KetBantu2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBantu2KeyPressed
        Valid.pindah(evt,AlatBantu,Prothesa);
    }//GEN-LAST:event_KetBantu2KeyPressed

    private void ProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProthesaKeyPressed
        Valid.pindah(evt,KetBantu,KetProthesa);
    }//GEN-LAST:event_ProthesaKeyPressed

    private void KetProthesaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetProthesaKeyPressed
        Valid.pindah(evt,Prothesa,ADL);
    }//GEN-LAST:event_KetProthesaKeyPressed

    private void ADLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ADLKeyPressed
        //Valid.pindah(evt,KetProthesa,StatusPsiko);
    }//GEN-LAST:event_ADLKeyPressed

    private void CacatFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CacatFisikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CacatFisikKeyPressed

    private void StatusPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPsikoKeyPressed
        Valid.pindah(evt,ADL,KetPsiko);
    }//GEN-LAST:event_StatusPsikoKeyPressed

    private void KetPsikoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetPsikoKeyPressed
        //Valid.pindah(evt,StatusPsiko,HubunganKeluarga);
    }//GEN-LAST:event_KetPsikoKeyPressed

    private void BahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaKeyPressed

    private void HubunganKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeluargaKeyPressed
        Valid.pindah(evt,KetPsiko,TinggalDengan);
    }//GEN-LAST:event_HubunganKeluargaKeyPressed

    private void TinggalDenganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggalDenganKeyPressed
        Valid.pindah(evt,HubunganKeluarga,KetTinggal);
    }//GEN-LAST:event_TinggalDenganKeyPressed

    private void KetTinggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetTinggalKeyPressed
        Valid.pindah(evt,TinggalDengan,Ekonomi);
    }//GEN-LAST:event_KetTinggalKeyPressed

    private void EkonomiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkonomiKeyPressed
        Valid.pindah(evt,KetTinggal,StatusBudaya);
    }//GEN-LAST:event_EkonomiKeyPressed

    private void StatusBudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusBudayaKeyPressed
        Valid.pindah(evt,Ekonomi,KetBudaya);
    }//GEN-LAST:event_StatusBudayaKeyPressed

    private void KetBudayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetBudayaKeyPressed
        Valid.pindah(evt,StatusBudaya,Edukasi);
    }//GEN-LAST:event_KetBudayaKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah(evt,KetBudaya,KetEdukasi);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void KetEdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetEdukasiKeyPressed
        Valid.pindah(evt,Edukasi,RJa1);
    }//GEN-LAST:event_KetEdukasiKeyPressed

    private void AgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AgamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgamaKeyPressed

    private void RJa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RJa1KeyPressed
        Valid.pindah(evt,KetEdukasi,RJa2);
    }//GEN-LAST:event_RJa1KeyPressed

    private void RJa2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RJa2KeyPressed
        Valid.pindah(evt,RJa1,RJb);
    }//GEN-LAST:event_RJa2KeyPressed

    private void RJbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RJbKeyPressed
        Valid.pindah(evt,RJa2,Hasil);
    }//GEN-LAST:event_RJbKeyPressed

    private void LaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaporKeyPressed
        Valid.pindah(evt,Hasil,KetLapor);
    }//GEN-LAST:event_LaporKeyPressed

    private void HasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilKeyPressed
        Valid.pindah(evt,RJb,Lapor);
    }//GEN-LAST:event_HasilKeyPressed

    private void KetLaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetLaporKeyPressed
        Valid.pindah(evt,Lapor,SG1);
    }//GEN-LAST:event_KetLaporKeyPressed

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG1ItemStateChanged
        NilaiGizi1.setText(SG1.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG1ItemStateChanged

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG1KeyPressed
        //Valid.pindah(evt,KetLapor,Nilai1);
    }//GEN-LAST:event_SG1KeyPressed

    private void SG2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG2ItemStateChanged
        NilaiGizi2.setText(SG2.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG2ItemStateChanged

    private void SG2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG2KeyPressed

    private void SG3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG3ItemStateChanged
        NilaiGizi3.setText(SG3.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG3ItemStateChanged

    private void SG3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG3KeyPressed

    private void WajahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_WajahItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_WajahItemStateChanged

    private void WajahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WajahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WajahKeyPressed

    private void NilaiGizi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi1KeyPressed

    private void SG4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG4ItemStateChanged
        NilaiGizi4.setText(SG4.getSelectedIndex()+"");
        TotalNilaiGizi.setText(""+(Integer.parseInt(NilaiGizi1.getText())+Integer.parseInt(NilaiGizi2.getText())+Integer.parseInt(NilaiGizi3.getText())+Integer.parseInt(NilaiGizi4.getText())));
    }//GEN-LAST:event_SG4ItemStateChanged

    private void SG4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG4KeyPressed

    private void NilaiGizi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi2KeyPressed

    private void NilaiGizi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi3KeyPressed

    private void NilaiGizi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NilaiGizi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiGizi4KeyPressed

    private void TotalHasil4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasil4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasil4KeyPressed

    private void TotalNilaiGiziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalNilaiGiziKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalNilaiGiziKeyPressed

    private void SG6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG6ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SG6ItemStateChanged

    private void SG6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG6KeyPressed

    private void TotalHasil6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasil6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasil6KeyPressed

    private void SG7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG7ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SG7ItemStateChanged

    private void SG7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG7KeyPressed

    private void TotalHasil7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasil7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasil7KeyPressed

    private void SG8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG8ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SG8ItemStateChanged

    private void SG8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG8KeyPressed

    private void TotalHasil8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasil8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasil8KeyPressed

    private void SG9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SG9ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_SG9ItemStateChanged

    private void SG9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SG9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SG9KeyPressed

    private void TotalHasil9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasil9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasil9KeyPressed

    private void TotalHasil10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalHasil10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalHasil10KeyPressed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
        //Valid.pindah(evt,Nilai2,Provokes);
    }//GEN-LAST:event_NyeriKeyPressed

    private void FrekuensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiKeyPressed
        //Valid.pindah(evt,Durasi,NyeriHilang);
    }//GEN-LAST:event_FrekuensiKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        //Valid.pindah(evt,SkalaNyeri,Durasi);
    }//GEN-LAST:event_LokasiKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DurasiKeyPressed
        Valid.pindah(evt,Lokasi,Frekuensi);
    }//GEN-LAST:event_DurasiKeyPressed

    private void NyeriHilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriHilangKeyPressed
        Valid.pindah(evt,Durasi,KetNyeri);
    }//GEN-LAST:event_NyeriHilangKeyPressed

    private void KetNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetNyeriKeyPressed
        Valid.pindah(evt,NyeriHilang,PadaDokter);
    }//GEN-LAST:event_KetNyeriKeyPressed

    private void PadaDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PadaDokterKeyPressed
        Valid.pindah(evt,KetNyeri,KetDokter);
    }//GEN-LAST:event_PadaDokterKeyPressed

    private void KetDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetDokterKeyPressed
        //Valid.pindah(evt,PadaDokter,Rencana);
    }//GEN-LAST:event_KetDokterKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RencanaKeyPressed

    private void BtnTambahMasalah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahMasalah1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        MasterMasalahKeperawatanAnak form=new MasterMasalahKeperawatanAnak(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahMasalah1ActionPerformed

    private void BtnCariPemeriksaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaan1ActionPerformed
        tampilMasalah();
    }//GEN-LAST:event_BtnCariPemeriksaan1ActionPerformed

    private void BtnCariPemeriksaan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPemeriksaan1KeyPressed

    private void TCariMasalahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMasalahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMasalah();
        }else if((evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN)||(evt.getKeyCode()==KeyEvent.VK_TAB)){
            Rencana.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //Palatum.requestFocus();
        }
    }//GEN-LAST:event_TCariMasalahKeyPressed

    private void BtnKeluarImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarImunisasiActionPerformed
        DlgRiwayatImunisasi.dispose();
    }//GEN-LAST:event_BtnKeluarImunisasiActionPerformed

    private void BtnSimpanImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanImunisasiActionPerformed
        if(KdImunisasi.getText().trim().equals("")||NmImunisasi.getText().trim().equals("")){
            Valid.textKosong(BtnImunisasi,"Imunisasi");
        }else{
            if(Sequel.menyimpantf("riwayat_imunisasi","?,?,?","Riwayat Imunisasi",3,new String[]{
                    TNoRM.getText(),KdImunisasi.getText(),ImunisasiKe.getSelectedItem().toString()
                })==true){
                KdImunisasi.setText("");
                NmImunisasi.setText("");
                ImunisasiKe.setSelectedIndex(0);
                tampilImunisasi();
            }
        }
    }//GEN-LAST:event_BtnSimpanImunisasiActionPerformed

    private void BtnImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImunisasiActionPerformed
        imunisasi.isCek();
        imunisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        imunisasi.setLocationRelativeTo(internalFrame1);
        imunisasi.setVisible(true);
    }//GEN-LAST:event_BtnImunisasiActionPerformed

    private void BtnImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnImunisasiKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnImunisasiKeyPressed

    private void KdImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdImunisasiKeyPressed

    }//GEN-LAST:event_KdImunisasiKeyPressed

    private void ImunisasiKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImunisasiKeKeyPressed
        Valid.pindah(evt,TglAsuhan,TD);
    }//GEN-LAST:event_ImunisasiKeKeyPressed

    private void BtnHapusImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusImunisasiActionPerformed
        Sequel.queryu2("delete from riwayat_imunisasi where no_rkm_medis=? and kode_imunisasi=? and no_imunisasi=?",3,new String[]{TNoRM.getText(),KdImunisasi.getText(),ImunisasiKe.getSelectedItem().toString()});
        DlgRiwayatImunisasi.dispose();
        tampilImunisasi();
    }//GEN-LAST:event_BtnHapusImunisasiActionPerformed

    private void BtnHapusImunisasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusImunisasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnHapusImunisasiKeyPressed

    private void BtnPanggilHapusImunisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanggilHapusImunisasiActionPerformed
        if(tbImunisasi.getSelectedRow()>-1){
            if(TNoRM.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu pasien yang mau dihapus data riwayat imunisasinya...");
                Informasi.requestFocus();
            }else{
                BtnImunisasi.setEnabled(false);
                KdImunisasi.setText(tbImunisasi.getValueAt(tbImunisasi.getSelectedRow(),0).toString());
                NmImunisasi.setText(tbImunisasi.getValueAt(tbImunisasi.getSelectedRow(),1).toString());
                BtnSimpanImunisasi.setVisible(false);
                BtnHapusImunisasi.setVisible(true);
                DlgRiwayatImunisasi.setLocationRelativeTo(internalFrame1);
                DlgRiwayatImunisasi.setVisible(true);
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnPanggilHapusImunisasiActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalKeperawatanBayiAnak dialog = new RMPenilaianAwalKeperawatanBayiAnak(new javax.swing.JFrame(), true);
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
    private widget.ComboBox ADL;
    private widget.TextBox Agama;
    private widget.ComboBox AlatBantu;
    private widget.ComboBox AlatBantu1;
    private widget.ComboBox AlatBantu2;
    private widget.ComboBox AlatBantu3;
    private widget.TextBox Alergi;
    private widget.TextBox Alergi1;
    private widget.TextBox Alergi10;
    private widget.TextBox Alergi11;
    private widget.TextBox Alergi2;
    private widget.TextBox Alergi3;
    private widget.TextBox Alergi4;
    private widget.TextBox Alergi5;
    private widget.TextBox Alergi6;
    private widget.TextBox Alergi7;
    private widget.TextBox Alergi8;
    private widget.TextBox Alergi9;
    private widget.TextBox BB;
    private widget.TextBox Bahasa;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariPemeriksaan1;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusImunisasi;
    private widget.Button BtnImunisasi;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarImunisasi;
    private widget.Button BtnPanggilHapusImunisasi;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanImunisasi;
    private widget.Button BtnTambahImunisasi;
    private widget.Button BtnTambahMasalah1;
    private widget.TextBox CacatFisik;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea DetailRencana;
    private javax.swing.JDialog DlgRiwayatImunisasi;
    private widget.TextBox Durasi;
    private widget.ComboBox Edukasi;
    private widget.ComboBox Ekonomi;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormMasalahRencana;
    private widget.PanelBiasa FormMenu;
    private widget.TextBox Frekuensi;
    private widget.TextBox GCS;
    private widget.ComboBox Hasil;
    private widget.ComboBox HubunganKeluarga;
    private widget.ComboBox ImunisasiKe;
    private widget.ComboBox Informasi;
    private widget.TextBox Jk;
    private widget.TextBox KdImunisasi;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetBantu;
    private widget.TextBox KetBantu1;
    private widget.TextBox KetBantu2;
    private widget.TextBox KetBudaya;
    private widget.TextBox KetDokter;
    private widget.TextBox KetEdukasi;
    private widget.TextBox KetLapor;
    private widget.TextBox KetNyeri;
    private widget.TextBox KetProthesa;
    private widget.TextBox KetPsiko;
    private widget.TextBox KetTinggal;
    private widget.Label LCount;
    private widget.TextBox LD;
    private widget.TextBox LK;
    private widget.TextBox LP;
    private widget.ComboBox Lapor;
    private widget.editorpane LoadHTML;
    private widget.TextBox Lokasi;
    private widget.TextBox Nadi;
    private widget.TextBox NilaiGizi1;
    private widget.TextBox NilaiGizi2;
    private widget.TextBox NilaiGizi3;
    private widget.TextBox NilaiGizi4;
    private widget.TextBox NmImunisasi;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Nyeri;
    private widget.ComboBox NyeriHilang;
    private widget.ComboBox PadaDokter;
    private widget.PanelBiasa PanelAccor;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.ComboBox Prothesa;
    private widget.ComboBox RJa1;
    private widget.ComboBox RJa2;
    private widget.ComboBox RJb;
    private widget.TextArea RPD;
    private widget.TextArea RPK;
    private widget.TextArea RPO;
    private widget.TextBox RR;
    private widget.TextArea Rencana;
    private widget.ComboBox SG1;
    private widget.ComboBox SG2;
    private widget.ComboBox SG3;
    private widget.ComboBox SG4;
    private widget.ComboBox SG6;
    private widget.ComboBox SG7;
    private widget.ComboBox SG8;
    private widget.ComboBox SG9;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ComboBox StatusBudaya;
    private widget.ComboBox StatusPsiko;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TCariMasalah;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TinggalDengan;
    private widget.TextBox TotalHasil10;
    private widget.TextBox TotalHasil4;
    private widget.TextBox TotalHasil6;
    private widget.TextBox TotalHasil7;
    private widget.TextBox TotalHasil8;
    private widget.TextBox TotalHasil9;
    private widget.TextBox TotalNilaiGizi;
    private widget.ComboBox Wajah;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel125;
    private widget.Label jLabel127;
    private widget.Label jLabel128;
    private widget.Label jLabel129;
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
    private widget.Label jLabel140;
    private widget.Label jLabel141;
    private widget.Label jLabel142;
    private widget.Label jLabel143;
    private widget.Label jLabel144;
    private widget.Label jLabel145;
    private widget.Label jLabel146;
    private widget.Label jLabel147;
    private widget.Label jLabel148;
    private widget.Label jLabel149;
    private widget.Label jLabel150;
    private widget.Label jLabel152;
    private widget.Label jLabel154;
    private widget.Label jLabel155;
    private widget.Label jLabel156;
    private widget.Label jLabel158;
    private widget.Label jLabel159;
    private widget.Label jLabel16;
    private widget.Label jLabel160;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel164;
    private widget.Label jLabel165;
    private widget.Label jLabel166;
    private widget.Label jLabel167;
    private widget.Label jLabel168;
    private widget.Label jLabel169;
    private widget.Label jLabel17;
    private widget.Label jLabel170;
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
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel53;
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
    private widget.Label jLabel7;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel83;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel99;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbImunisasi;
    private widget.Table tbMasalahDetailMasalah;
    private widget.Table tbMasalahKeperawatan;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                        "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                        "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                        "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                        "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                        "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                        "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                        "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                        "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? order by penilaian_awal_keperawatan_ralan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat,penilaian_awal_keperawatan_ralan.tanggal,"+
                        "penilaian_awal_keperawatan_ralan.informasi,penilaian_awal_keperawatan_ralan.td,penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,"+
                        "penilaian_awal_keperawatan_ralan.nadi,penilaian_awal_keperawatan_ralan.rr,penilaian_awal_keperawatan_ralan.suhu,penilaian_awal_keperawatan_ralan.gcs,penilaian_awal_keperawatan_ralan.bb,penilaian_awal_keperawatan_ralan.tb,penilaian_awal_keperawatan_ralan.bmi,penilaian_awal_keperawatan_ralan.keluhan_utama,"+
                        "penilaian_awal_keperawatan_ralan.rpd,penilaian_awal_keperawatan_ralan.rpk,penilaian_awal_keperawatan_ralan.rpo,penilaian_awal_keperawatan_ralan.alergi,penilaian_awal_keperawatan_ralan.alat_bantu,penilaian_awal_keperawatan_ralan.ket_bantu,penilaian_awal_keperawatan_ralan.prothesa,"+
                        "penilaian_awal_keperawatan_ralan.ket_pro,penilaian_awal_keperawatan_ralan.adl,penilaian_awal_keperawatan_ralan.status_psiko,penilaian_awal_keperawatan_ralan.ket_psiko,penilaian_awal_keperawatan_ralan.hub_keluarga,penilaian_awal_keperawatan_ralan.tinggal_dengan,"+
                        "penilaian_awal_keperawatan_ralan.ket_tinggal,penilaian_awal_keperawatan_ralan.ekonomi,penilaian_awal_keperawatan_ralan.edukasi,penilaian_awal_keperawatan_ralan.ket_edukasi,penilaian_awal_keperawatan_ralan.berjalan_a,penilaian_awal_keperawatan_ralan.berjalan_b,"+
                        "penilaian_awal_keperawatan_ralan.berjalan_c,penilaian_awal_keperawatan_ralan.hasil,penilaian_awal_keperawatan_ralan.lapor,penilaian_awal_keperawatan_ralan.ket_lapor,penilaian_awal_keperawatan_ralan.sg1,penilaian_awal_keperawatan_ralan.nilai1,penilaian_awal_keperawatan_ralan.sg2,penilaian_awal_keperawatan_ralan.nilai2,"+
                        "penilaian_awal_keperawatan_ralan.total_hasil,penilaian_awal_keperawatan_ralan.nyeri,penilaian_awal_keperawatan_ralan.provokes,penilaian_awal_keperawatan_ralan.ket_provokes,penilaian_awal_keperawatan_ralan.quality,penilaian_awal_keperawatan_ralan.ket_quality,penilaian_awal_keperawatan_ralan.lokasi,penilaian_awal_keperawatan_ralan.menyebar,"+
                        "penilaian_awal_keperawatan_ralan.skala_nyeri,penilaian_awal_keperawatan_ralan.durasi,penilaian_awal_keperawatan_ralan.nyeri_hilang,penilaian_awal_keperawatan_ralan.ket_nyeri,penilaian_awal_keperawatan_ralan.pada_dokter,penilaian_awal_keperawatan_ralan.ket_dokter,penilaian_awal_keperawatan_ralan.rencana,"+
                        "penilaian_awal_keperawatan_ralan.nip,petugas.nama,penilaian_awal_keperawatan_ralan.budaya,penilaian_awal_keperawatan_ralan.ket_budaya "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_awal_keperawatan_ralan on reg_periksa.no_rawat=penilaian_awal_keperawatan_ralan.no_rawat "+
                        "inner join petugas on penilaian_awal_keperawatan_ralan.nip=petugas.nip "+
                        "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                        "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik where "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and reg_periksa.no_rawat like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.no_rkm_medis like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and penilaian_awal_keperawatan_ralan.nip like ? or "+
                        "penilaian_awal_keperawatan_ralan.tanggal between ? and ? and petugas.nama like ? order by penilaian_awal_keperawatan_ralan.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(15,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("agama"),rs.getString("nama_bahasa"),rs.getString("nama_cacat"),
                        rs.getString("tgl_lahir"),rs.getString("tanggal"),rs.getString("informasi"),rs.getString("td"),rs.getString("nadi"),rs.getString("rr"),rs.getString("suhu"),
                        rs.getString("gcs"),rs.getString("bb"),rs.getString("tb"),rs.getString("bmi"),rs.getString("keluhan_utama"),rs.getString("rpd"),rs.getString("rpk"),rs.getString("rpo"),
                        rs.getString("alergi"),rs.getString("alat_bantu"),rs.getString("ket_bantu"),rs.getString("prothesa"),rs.getString("ket_pro"),rs.getString("adl"),rs.getString("status_psiko"),
                        rs.getString("ket_psiko"),rs.getString("hub_keluarga"),rs.getString("tinggal_dengan"),rs.getString("ket_tinggal"),rs.getString("ekonomi"),rs.getString("budaya"),
                        rs.getString("ket_budaya"),rs.getString("edukasi"),rs.getString("ket_edukasi"),rs.getString("berjalan_a"),rs.getString("berjalan_b"),rs.getString("berjalan_c"),
                        rs.getString("hasil"),rs.getString("lapor"),rs.getString("ket_lapor"),rs.getString("sg1"),rs.getString("nilai1"),rs.getString("sg2"),rs.getString("nilai2"),
                        rs.getString("total_hasil"),rs.getString("nyeri"),rs.getString("provokes"),rs.getString("ket_provokes"),rs.getString("quality"),rs.getString("ket_quality"),
                        rs.getString("lokasi"),rs.getString("menyebar"),rs.getString("skala_nyeri"),rs.getString("durasi"),rs.getString("nyeri_hilang"),rs.getString("ket_nyeri"),
                        rs.getString("pada_dokter"),rs.getString("ket_dokter"),rs.getString("rencana"),rs.getString("nip"),rs.getString("nama")
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
        Informasi.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        GCS.setText("");
        /*BB.setText("");
        TB.setText("");
        BMI.setText("");
        KeluhanUtama.setText("");
        RPK.setText("");
        RPD.setText("");
        RPO.setText("");
        Alergi.setText("");
        AlatBantu.setSelectedIndex(0);
        KetBantu.setText("");
        Prothesa.setSelectedIndex(0);
        KetProthesa.setText("");
        ADL.setSelectedIndex(0);
        StatusPsiko.setSelectedIndex(0);
        KetPsiko.setText("");
        HubunganKeluarga.setSelectedIndex(0);
        TinggalDengan.setSelectedIndex(0);
        KetTinggal.setText("");
        Ekonomi.setSelectedIndex(0);
        StatusBudaya.setSelectedIndex(0);
        KetBudaya.setText("");
        Edukasi.setSelectedIndex(0);
        KetEdukasi.setText("");
        ATS.setSelectedIndex(0);
        BJM.setSelectedIndex(0);
        MSA.setSelectedIndex(0);
        Hasil.setSelectedIndex(0);
        Lapor.setSelectedIndex(0);
        KetLapor.setText("");
        SG1.setSelectedIndex(0);
        Nilai1.setSelectedIndex(0);
        SG2.setSelectedIndex(0);
        Nilai2.setSelectedIndex(0);
        TotalHasil.setText("0");
        Nyeri.setSelectedIndex(0);
        Provokes.setSelectedIndex(0);
        KetProvokes.setText("");
        Quality.setSelectedIndex(0);
        KetQuality.setText("");
        Lokasi.setText("");
        Menyebar.setSelectedIndex(0);
        SkalaNyeri.setSelectedIndex(0);
        Durasi.setText("");
        NyeriHilang.setSelectedIndex(0);
        KetNyeri.setText("");
        PadaDokter.setSelectedIndex(0);
        KetDokter.setText("");
        Rencana.setText("");
        for (i = 0; i < tabModeMasalah.getRowCount(); i++) {
            tabModeMasalah.setValueAt(false,i,0);
        }
        TabRawat.setSelectedIndex(0);
        Informasi.requestFocus();*/
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            /*            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            Agama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Bahasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            CacatFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()); 
            Informasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            GCS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            BMI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            RPD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            RPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            RPO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            AlatBantu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KetBantu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Prothesa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KetProthesa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            ADL.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            StatusPsiko.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            KetPsiko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            HubunganKeluarga.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            TinggalDengan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            KetTinggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Ekonomi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            StatusBudaya.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            KetBudaya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Edukasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            KetEdukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            ATS.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            BJM.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            MSA.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Hasil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Lapor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            KetLapor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            SG1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Nilai1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            SG2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Nilai2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Provokes.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            KetProvokes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Quality.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            KetQuality.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Menyebar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            SkalaNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Durasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            NyeriHilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            KetNyeri.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            PadaDokter.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            KetLapor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Rencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            
            try {
                Valid.tabelKosong(tabModeMasalah);
                
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_anak.kode_masalah,master_masalah_keperawatan_anak.nama_masalah from master_masalah_keperawatan_anak "+
                        "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan_anak.kode_masalah "+
                        "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeMasalah.addRow(new Object[]{true,rs.getString(1),rs.getString(2)});
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
            }*/
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        tampilImunisasi();
        try {
            ps=koneksi.prepareStatement(
                    "select nm_pasien, if(jk='L','Laki-Laki','Perempuan') as jk,tgl_lahir,agama,bahasa_pasien.nama_bahasa,cacat_fisik.nama_cacat "+
                    "from pasien inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join cacat_fisik on cacat_fisik.id=pasien.cacat_fisik "+
                    "where no_rkm_medis=?");
            try {
                ps.setString(1,TNoRM.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    //Agama.setText(rs.getString("agama"));
                    //Bahasa.setText(rs.getString("nama_bahasa"));
                    //CacatFisik.setText(rs.getString("nama_cacat"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
        
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
        BtnHapus.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
        BtnEdit.setEnabled(akses.getpenilaian_awal_keperawatan_anak());
        BtnTambahImunisasi.setEnabled(akses.getmaster_masalah_keperawatan_anak());  
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas,KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
       tampil();
    }
    
    private void tampilMasalah() {
        try{
            jml=0;
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
            kode=null;
            kode=new String[jml];
            masalah=null;
            masalah=new String[jml];

            index=0;        
            for(i=0;i<tbMasalahKeperawatan.getRowCount();i++){
                if(tbMasalahKeperawatan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbMasalahKeperawatan.getValueAt(i,1).toString();
                    masalah[index]=tbMasalahKeperawatan.getValueAt(i,2).toString();
                    index++;
                }
            } 

            Valid.tabelKosong(tabModeMasalah);

            for(i=0;i<jml;i++){
                tabModeMasalah.addRow(new Object[] {
                    pilih[i],kode[i],masalah[i]
                });
            }
            ps=koneksi.prepareStatement("select * from master_masalah_keperawatan_anak where kode_masalah like ? or nama_masalah like ? order by kode_masalah");
            try {
                ps.setString(1,"%"+TCariMasalah.getText().trim()+"%");
                ps.setString(2,"%"+TCariMasalah.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeMasalah.addRow(new Object[]{false,rs.getString(1),rs.getString(2)});
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
    
    private void isMenu(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(470,HEIGHT));
            FormMenu.setVisible(true);  
            FormMasalahRencana.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){   
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormMenu.setVisible(false);  
            FormMasalahRencana.setVisible(false);   
            ChkAccor.setVisible(true);
        }
    }

    private void getMasalah() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRM1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()); 
            DetailRencana.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            try {
                Valid.tabelKosong(tabModeDetailMasalah);
                ps=koneksi.prepareStatement(
                        "select master_masalah_keperawatan_anak.kode_masalah,master_masalah_keperawatan_anak.nama_masalah from master_masalah_keperawatan_anak "+
                        "inner join penilaian_awal_keperawatan_ralan_masalah on penilaian_awal_keperawatan_ralan_masalah.kode_masalah=master_masalah_keperawatan_anak.kode_masalah "+
                        "where penilaian_awal_keperawatan_ralan_masalah.no_rawat=? order by kode_masalah");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        tabModeDetailMasalah.addRow(new Object[]{rs.getString(1),rs.getString(2)});
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
    }

    private void tampilImunisasi() {
        try {
            Valid.tabelKosong(tabModeImunisasi);
            ps=koneksi.prepareStatement("select master_imunisasi.kode_imunisasi,master_imunisasi.nama_imunisasi from master_imunisasi inner join riwayat_imunisasi on riwayat_imunisasi.kode_imunisasi=master_imunisasi.kode_imunisasi group by master_imunisasi.kode_imunisasi order by master_imunisasi.kode_imunisasi  ");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    ke1=false;ke2=false;ke3=false;ke4=false;ke5=false;ke6=false;
                    ps2=koneksi.prepareStatement("select * from riwayat_imunisasi where no_rkm_medis=? and kode_imunisasi=?");
                    try {
                        ps2.setString(1,TNoRM.getText());
                        ps2.setString(2,rs.getString(1));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            if(rs2.getInt("no_imunisasi")==1){
                                ke1=true;
                            }
                            if(rs2.getInt("no_imunisasi")==2){
                                ke2=true;
                            }
                            if(rs2.getInt("no_imunisasi")==3){
                                ke3=true;
                            }
                            if(rs2.getInt("no_imunisasi")==4){
                                ke4=true;
                            }
                            if(rs2.getInt("no_imunisasi")==5){
                                ke5=true;
                            }
                            if(rs2.getInt("no_imunisasi")==6){
                                ke6=true;
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
                    
                    tabModeImunisasi.addRow(new Object[]{rs.getString(1),rs.getString(2),ke1,ke2,ke3,ke4,ke5,ke6});
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
   
}

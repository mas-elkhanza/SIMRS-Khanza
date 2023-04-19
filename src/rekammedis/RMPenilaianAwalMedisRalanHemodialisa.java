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


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianAwalMedisRalanHemodialisa extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String finger="",finger1="",dbhipertensi="",dbdm="",dbbsk="",dbosk="",dbisk="",dbbst="",dbub="",dbpgl="",dbpl="",dbkon="",
            dbcapd="",dbtransplantasi="",dbthorax="",dbekg="",dbbno="",dbusg="",dbrenogram="",dbbiopsi="",dbctscan="",dbarteri="",dbkultur="",dblab="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianAwalMedisRalanHemodialisa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        
        Hipertensi.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(Hipertensi.getSelectedItem().toString())){
            TxtHipertensi.setEnabled(true);
            }else{
            TxtHipertensi.setEnabled(false);    
            }  
           }
        });
        DM.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(DM.getSelectedItem().toString())){
            Txtdm.setEnabled(true);
            }else{
            Txtdm.setEnabled(false);    
            }  
           }
        });
        BSK.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(BSK.getSelectedItem().toString())){
            Txtbsk.setEnabled(true);
            }else{
            Txtbsk.setEnabled(false);    
            }  
           }
        });
        OSK.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(OSK.getSelectedItem().toString())){
            Txtosk.setEnabled(true);
            }else{
            Txtosk.setEnabled(false);    
            }  
           }
        });
        ISK.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(ISK.getSelectedItem().toString())){
            Txtisk.setEnabled(true);
            }else{
            Txtisk.setEnabled(false);    
            }  
           }
        });
        BST.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(BST.getSelectedItem().toString())){
            Txtbst.setEnabled(true);
            }else{
            Txtbst.setEnabled(false);    
            }  
           }
        });
        UB.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(UB.getSelectedItem().toString())){
            Txtub.setEnabled(true);
            }else{
            Txtub.setEnabled(false);    
            }  
           }
        });
        PGL.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(PGL.getSelectedItem().toString())){
            Txtpgl.setEnabled(true);
            }else{
            Txtpgl.setEnabled(false);    
            }  
           }
        });
        PL.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(PL.getSelectedItem().toString())){
            Txtpl.setEnabled(true);
            }else{
            Txtpl.setEnabled(false);    
            }  
           }
        });
        KON.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(KON.getSelectedItem().toString())){
            Txtkon.setEnabled(true);
            }else{
            Txtkon.setEnabled(false);    
            }  
           }
        });
        CAPD.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(CAPD.getSelectedItem().toString())){
            TglCAPD.setEnabled(true);
            }else{
            TglCAPD.setEnabled(false);    
            }  
           }
        });
        Transplantasi.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
            if("YA".equals(Transplantasi.getSelectedItem().toString())){
            TglTransplantasi.setEnabled(true);
            }else{
            TglTransplantasi.setEnabled(false);    
            }  
           }
        });
        ActionListener cbthoraxact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbthorax.isSelected()){
                    TglThorax.setEnabled(true);
                }else{
                    TglThorax.setEnabled(false);    
                }  
            }
        };
        TglThorax.setEnabled(false);
        cbthorax.addActionListener(cbthoraxact);
        
        ActionListener cbekgact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbekg.isSelected()){
                    TglEKG.setEnabled(true);
                }else{
                    TglEKG.setEnabled(false);    
                }  
            }
        };
        TglEKG.setEnabled(false);
        cbekg.addActionListener(cbekgact);
        
        ActionListener cbbnoact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbbno.isSelected()){
                    TglBNO.setEnabled(true);
                }else{
                    TglBNO.setEnabled(false);    
                }  
            }
        };
        TglBNO.setEnabled(false);
        cbbno.addActionListener(cbbnoact);
        
        ActionListener cbusgact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbusg.isSelected()){
                    TglUSG.setEnabled(true);
                }else{
                    TglUSG.setEnabled(false);    
                }  
            }
        };
        TglUSG.setEnabled(false);
        cbusg.addActionListener(cbusgact);
        
        ActionListener cbrenogramact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbrenogram.isSelected()){
                    TglRenogram.setEnabled(true);
                }else{
                    TglRenogram.setEnabled(false);    
                }  
            }
        };
        TglRenogram.setEnabled(false);
        cbrenogram.addActionListener(cbrenogramact);
        
        ActionListener cbbiopsiact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbbiopsi.isSelected()){
                    TglBiopsi.setEnabled(true);
                }else{
                    TglBiopsi.setEnabled(false);    
                }  
            }
        };
        TglBiopsi.setEnabled(false);
        cbbiopsi.addActionListener(cbbiopsiact);
        
        ActionListener cbctscanact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbctscan.isSelected()){
                    TglCTscan.setEnabled(true);
                }else{
                    TglCTscan.setEnabled(false);    
                }  
            }
        };
        TglCTscan.setEnabled(false);
        cbctscan.addActionListener(cbctscanact);
        
        ActionListener cbarteriact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbarteri.isSelected()){
                    TglArteriografi.setEnabled(true);
                }else{
                    TglArteriografi.setEnabled(false);    
                }  
            }
        };
        TglArteriografi.setEnabled(false);
        cbarteri.addActionListener(cbarteriact);
        
        ActionListener cbkulturact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbkultur.isSelected()){
                    TglKultururin.setEnabled(true);
                }else{
                    TglKultururin.setEnabled(false);    
                }  
            }
        };
        TglKultururin.setEnabled(false);
        cbkultur.addActionListener(cbkulturact);
        
        ActionListener cblabact = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cblab.isSelected()){
                    TglLaboratorium.setEnabled(true);
                }else{
                    TglLaboratorium.setEnabled(false);    
                }  
            }
        };
        TglLaboratorium.setEnabled(false);
        cblab.addActionListener(cblabact);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","NIP","Nama Dokter","Tanggal","Anamnesis","Hubungan","Rawat Jalan","Rawat Inap","Riwayat Alergi","Nyeri","Status Nutrisi",
            "Hipertensi","Diabetes Melitus","Batu Saluran Kemih","Operasi Saluran Kemih","Infeksi Saluran Kemih","Bengkak Seluruh Tubuh","Urin Berdarah","Penyakit Ginjal Laom","Penyakit Lain","Konsumsi Obat Nefrotoksis",
            "Dialisis Pertama","CAPD","Pernah Transplantasi","Keadaan Umum","Kesadaran","BB(Kg)","TB(Cm)","Suhu","Nadi(x/menit)","TD(mmHg)","Napas(x/menit)","Sklera Ikterik","Konjungtiva","Tekanan Vena Jugularis","Jantung Kardiomegali","Jantung Bising",
            "Paru Whezzing","Paru Ronchi","Abdomen Hepatomegali","Abdomen Splenomegali","Abdomen Ascites","Ekstremitas Edema","Foto Thoraks","EKG","BNO","USG","Renogram","PA Biopsi Ginjal","CT Scan","Arteriografi","Kultur Urin","Laboratorium",
            "Hematokrit","Hemoglobin","Leukosit","Trombosit","Hitung Jenis","Ureum","Kreatinin","Asam Urat","SGOT","SGPT","HbsAg","CT","Urin Lengkap","CCT","Anti HCV","Edukasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 73; i++) {
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
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(300);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(120);
            }else if(i==15){
                column.setPreferredWidth(120);
            }else if(i==16){
                column.setPreferredWidth(100);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(100);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(100);
            }else if(i==21){
                column.setPreferredWidth(100);
            }else if(i==22){
                column.setPreferredWidth(100);
            }else if(i==23){
                column.setPreferredWidth(100);
            }else if(i==24){
                column.setPreferredWidth(100);
            }else if(i==25){
                column.setPreferredWidth(100);
            }else if(i==26){
                column.setPreferredWidth(100);
            }else if(i==27){
                column.setPreferredWidth(100);
            }else if(i==28){
                column.setPreferredWidth(100);
            }else if(i==39){
                column.setPreferredWidth(150);
            }else if(i==30){
                column.setPreferredWidth(40);
            }else if(i==31){
                column.setPreferredWidth(40);
            }else if(i==32){
                column.setPreferredWidth(40);
            }else if(i==33){
                column.setPreferredWidth(40);
            }else if(i==34){
                column.setPreferredWidth(40);
            }else if(i==35){
                column.setPreferredWidth(40);
            }else if(i==36){
                column.setPreferredWidth(40);
            }else if(i==37){
                column.setPreferredWidth(100);
            }else if(i==38){
                column.setPreferredWidth(100);
            }else if(i==39){
                column.setPreferredWidth(100);
            }else if(i==40){
                column.setPreferredWidth(100);
            }else if(i==41){
                column.setPreferredWidth(150);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(150);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(150);
            }else if(i==46){
                column.setPreferredWidth(150);
            }else if(i==47){
                column.setPreferredWidth(150);
            }else if(i==48){
                column.setPreferredWidth(150);
            }else if(i==49){
                column.setPreferredWidth(150);
            }else if(i==50){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(150);
            }else if(i==52){
                column.setPreferredWidth(150);
            }else if(i==53){
                column.setPreferredWidth(150);
            }else if(i==54){
                column.setPreferredWidth(150);
            }else if(i==55){
                column.setPreferredWidth(150);
            }else if(i==56){
                column.setPreferredWidth(150);
            }else if(i==57){
                column.setPreferredWidth(150);
            }else if(i==58){
                column.setPreferredWidth(150);
            }else if(i==59){
                column.setPreferredWidth(150);
            }else if(i==60){
                column.setPreferredWidth(150);
            }else if(i==61){
                column.setPreferredWidth(150);
            }else if(i==62){
                column.setPreferredWidth(150);
            }else if(i==63){
                column.setPreferredWidth(150);
            }else if(i==64){
                column.setPreferredWidth(150);
            }else if(i==65){
                column.setPreferredWidth(150);
            }else if(i==66){
                column.setPreferredWidth(150);
            }else if(i==67){
                column.setPreferredWidth(150);
            }else if(i==68){
                column.setPreferredWidth(150);
            }else if(i==69){
                column.setPreferredWidth(150);
            }else if(i==70){
                column.setPreferredWidth(150);
            }else if(i==71){
                column.setPreferredWidth(150);
            }else if(i==72){
                column.setPreferredWidth(500);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Hubungan.setDocument(new batasInput((int)30).getKata(Hubungan));
        Alergi.setDocument(new batasInput((int)50).getKata(Alergi));
        Status.setDocument(new batasInput((byte)100).getKata(Status));
        TD.setDocument(new batasInput((byte)10).getKata(TD));
        Nadi.setDocument(new batasInput((byte)10).getKata(Nadi));
        Suhu.setDocument(new batasInput((byte)10).getKata(Suhu));
        BB.setDocument(new batasInput((byte)10).getKata(BB));
        TB.setDocument(new batasInput((byte)10).getKata(TB));
        //Txtkepala.setDocument(new batasInput((byte)500).getKata(Txtkepala));
        //Txtthorax.setDocument(new batasInput((byte)500).getKata(Txtthorax));
        //Txtabdomen.setDocument(new batasInput((byte)500).getKata(Txtabdomen));
        //Txtekstremitas.setDocument(new batasInput((byte)500).getKata(Txtekstremitas));
        //Txtcolumna.setDocument(new batasInput((byte)500).getKata(Txtcolumna));
        //Txtmuskulos.setDocument(new batasInput((byte)500).getKata(Txtmuskulos));
        //Txtlainnya.setDocument(new batasInput((byte)500).getKata(Txtlainnya));
        //Lab.setDocument(new batasInput((byte)500).getKata(Lab));
        //Rad.setDocument(new batasInput((byte)500).getKata(Rad));
        //Pemeriksaan.setDocument(new batasInput((int)500).getKata(Pemeriksaan));
        //Diagnosis.setDocument(new batasInput((int)500).getKata(Diagnosis));
        //Diagnosis2.setDocument(new batasInput((int)500).getKata(Diagnosis2));
        //Permasalahan.setDocument(new batasInput((int)500).getKata(Permasalahan));
        //Tindakan.setDocument(new batasInput((byte)500).getKata(Tindakan));
        //Terapi.setDocument(new batasInput((byte)500).getKata(Terapi));
        Edukasi.setDocument(new batasInput((int)500).getKata(Edukasi));
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
        MnPenilaianMedis = new javax.swing.JMenuItem();
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
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        Anamnesis = new widget.ComboBox();
        scrollPane4 = new widget.ScrollPane();
        Alergi = new widget.TextArea();
        jLabel28 = new widget.Label();
        TB = new widget.TextBox();
        jLabel94 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel38 = new widget.Label();
        Hubungan = new widget.TextBox();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        Kesadaran = new widget.ComboBox();
        jLabel29 = new widget.Label();
        Status = new widget.TextBox();
        DM = new widget.ComboBox();
        jLabel44 = new widget.Label();
        ISK = new widget.ComboBox();
        jLabel45 = new widget.Label();
        BST = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Hipertensi = new widget.ComboBox();
        jLabel49 = new widget.Label();
        BSK = new widget.ComboBox();
        jLabel50 = new widget.Label();
        OSK = new widget.ComboBox();
        jLabel51 = new widget.Label();
        PGL = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel104 = new widget.Label();
        jLabel41 = new widget.Label();
        Nyeri = new widget.ComboBox();
        jLabel95 = new widget.Label();
        scrollPane14 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        TxtHipertensi = new java.awt.TextField();
        Txtdm = new java.awt.TextField();
        Txtbsk = new java.awt.TextField();
        Txtpgl = new java.awt.TextField();
        Txtosk = new java.awt.TextField();
        Txtisk = new java.awt.TextField();
        Txtbst = new java.awt.TextField();
        jLabel47 = new widget.Label();
        UB = new widget.ComboBox();
        Txtub = new java.awt.TextField();
        jLabel14 = new widget.Label();
        jLabel42 = new widget.Label();
        Keadaan = new widget.ComboBox();
        jLabel113 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel48 = new widget.Label();
        CAPD = new widget.ComboBox();
        Transplantasi = new widget.ComboBox();
        jLabel53 = new widget.Label();
        TglTransplantasi = new widget.Tanggal();
        TglCAPD = new widget.Tanggal();
        jLabel54 = new widget.Label();
        PL = new widget.ComboBox();
        Txtpl = new java.awt.TextField();
        Txtkon = new java.awt.TextField();
        KON = new widget.ComboBox();
        jLabel55 = new widget.Label();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel24 = new widget.Label();
        Napas = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel56 = new widget.Label();
        Ikterik = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        Konjungtiva = new widget.ComboBox();
        jLabel60 = new widget.Label();
        JVP = new widget.ComboBox();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        Kardiomegali = new widget.ComboBox();
        Bising = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Whezzing = new widget.ComboBox();
        Ronchi = new widget.ComboBox();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        Hepatomegali = new widget.ComboBox();
        Splenomegali = new widget.ComboBox();
        Ascites = new widget.ComboBox();
        Edema = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        jLabel72 = new widget.Label();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel73 = new widget.Label();
        TglThorax = new widget.Tanggal();
        TglDialisis = new widget.Tanggal();
        jLabel74 = new widget.Label();
        TglEKG = new widget.Tanggal();
        jLabel75 = new widget.Label();
        TglBNO = new widget.Tanggal();
        TglUSG = new widget.Tanggal();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        TglRenogram = new widget.Tanggal();
        TglLaboratorium = new widget.Tanggal();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        TglKultururin = new widget.Tanggal();
        TglArteriografi = new widget.Tanggal();
        TglCTscan = new widget.Tanggal();
        TglBiopsi = new widget.Tanggal();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel26 = new widget.Label();
        Hematokrit = new widget.TextBox();
        jLabel27 = new widget.Label();
        Hemoglobin = new widget.TextBox();
        jLabel30 = new widget.Label();
        Leukosit = new widget.TextBox();
        Trombosit = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        HitungJenis = new widget.TextBox();
        jLabel33 = new widget.Label();
        Ureum = new widget.TextBox();
        Kreatinin = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        AsamUrat = new widget.TextBox();
        jLabel36 = new widget.Label();
        SGOT = new widget.TextBox();
        SGPT = new widget.TextBox();
        CT = new widget.TextBox();
        UrinLengkap = new widget.TextBox();
        CCT = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        HbsAg = new widget.ComboBox();
        AntiHCV = new widget.ComboBox();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();
        Ranap = new widget.ComboBox();
        Rajal = new widget.ComboBox();
        cblab = new javax.swing.JCheckBox();
        cbrenogram = new javax.swing.JCheckBox();
        cbkultur = new javax.swing.JCheckBox();
        cbarteri = new javax.swing.JCheckBox();
        cbctscan = new javax.swing.JCheckBox();
        cbbiopsi = new javax.swing.JCheckBox();
        cbusg = new javax.swing.JCheckBox();
        cbbno = new javax.swing.JCheckBox();
        cbekg = new javax.swing.JCheckBox();
        cbthorax = new javax.swing.JCheckBox();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Awal Medis Pasien Hemodialisa ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 650));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1850));
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
        label14.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 40, 180, 23);

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
        BtnDokter.setBounds(348, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Riwayat Alergi obat :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(20, 140, 110, 23);

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

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(10, 810, 30, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(60, 810, 80, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Cm");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(160, 850, 30, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("kali/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(160, 930, 50, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(60, 930, 80, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(20, 930, 40, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(10, 890, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(60, 890, 80, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(20, 970, 30, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(60, 970, 80, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(160, 890, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(160, 970, 50, 23);

        Anamnesis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Autoanamnesis", "Alloanamnesis" }));
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(644, 40, 128, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Alergi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Alergi.setColumns(20);
        Alergi.setRows(5);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Alergi);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(140, 140, 260, 42);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("TB :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(20, 850, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(60, 850, 80, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("II. RIWAYAT DIALISIS/TRANSPLANTASI");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(10, 570, 220, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 880, 1);

        jLabel38.setText("Anamnesis :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 40, 70, 23);

        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.setPreferredSize(new java.awt.Dimension(207, 23));
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(774, 40, 80, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 221, 880, 2);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Keadaan Umum :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(20, 740, 90, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Diabetes Melitus :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(20, 290, 110, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Somnolen", "Sopor", "Stupor", "Koma" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(120, 770, 130, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Status Nutrisi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(290, 190, 90, 23);

        Status.setFocusTraversalPolicyProvider(true);
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        FormInput.add(Status);
        Status.setBounds(380, 190, 160, 23);

        DM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        DM.setName("DM"); // NOI18N
        DM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DMKeyPressed(evt);
            }
        });
        FormInput.add(DM);
        DM.setBounds(180, 290, 128, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Infeksi Saluran Kemih :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(20, 380, 120, 23);

        ISK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        ISK.setName("ISK"); // NOI18N
        ISK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ISKKeyPressed(evt);
            }
        });
        FormInput.add(ISK);
        ISK.setBounds(180, 380, 128, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Bengkak Seluruh Tubuh :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(20, 410, 140, 23);

        BST.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        BST.setName("BST"); // NOI18N
        BST.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BSTKeyPressed(evt);
            }
        });
        FormInput.add(BST);
        BST.setBounds(180, 410, 128, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Hipertensi :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(20, 260, 110, 23);

        Hipertensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Hipertensi.setName("Hipertensi"); // NOI18N
        Hipertensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HipertensiActionPerformed(evt);
            }
        });
        Hipertensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HipertensiKeyPressed(evt);
            }
        });
        FormInput.add(Hipertensi);
        Hipertensi.setBounds(180, 260, 128, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Batu Saluran Kemih :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(20, 320, 110, 23);

        BSK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        BSK.setName("BSK"); // NOI18N
        BSK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BSKKeyPressed(evt);
            }
        });
        FormInput.add(BSK);
        BSK.setBounds(180, 320, 128, 23);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText("Operasi Saluran Kemih :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(20, 350, 120, 23);

        OSK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        OSK.setName("OSK"); // NOI18N
        OSK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OSKActionPerformed(evt);
            }
        });
        OSK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OSKKeyPressed(evt);
            }
        });
        FormInput.add(OSK);
        OSK.setBounds(180, 350, 128, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Penyakit Ginjal Laom :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(20, 470, 110, 23);

        PGL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        PGL.setName("PGL"); // NOI18N
        PGL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PGLKeyPressed(evt);
            }
        });
        FormInput.add(PGL);
        PGL.setBounds(180, 470, 128, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 700, 880, 2);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("IV. PEMERIKSAAN PENUNJANG");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(20, 1080, 190, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(370, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023 08:40:58" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglAsuhanActionPerformed(evt);
            }
        });
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(430, 40, 140, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Edukasi :");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(20, 1730, 190, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Skala Nyeri :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(20, 190, 80, 23);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Nyeri", "Nyeri Ringan", "Nyeri Sedang", "Nyeri Berat", "Nyeri Sangat Berat", "Nyeri Tak Tertahankan" }));
        Nyeri.setName("Nyeri"); // NOI18N
        Nyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriKeyPressed(evt);
            }
        });
        FormInput.add(Nyeri);
        Nyeri.setBounds(140, 190, 130, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("I. RIWAYAT PENYAKIT");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(10, 230, 180, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(Edukasi);

        FormInput.add(scrollPane14);
        scrollPane14.setBounds(20, 1760, 800, 63);

        TxtHipertensi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TxtHipertensi.setForeground(new java.awt.Color(50, 50, 50));
        TxtHipertensi.setName("TxtHipertensi"); // NOI18N
        FormInput.add(TxtHipertensi);
        TxtHipertensi.setBounds(330, 260, 220, 20);

        Txtdm.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtdm.setForeground(new java.awt.Color(50, 50, 50));
        Txtdm.setName("Txtdm"); // NOI18N
        FormInput.add(Txtdm);
        Txtdm.setBounds(330, 290, 220, 18);

        Txtbsk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtbsk.setForeground(new java.awt.Color(50, 50, 50));
        Txtbsk.setName("Txtbsk"); // NOI18N
        FormInput.add(Txtbsk);
        Txtbsk.setBounds(330, 320, 220, 18);

        Txtpgl.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtpgl.setForeground(new java.awt.Color(50, 50, 50));
        Txtpgl.setName("Txtpgl"); // NOI18N
        FormInput.add(Txtpgl);
        Txtpgl.setBounds(330, 470, 220, 20);

        Txtosk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtosk.setForeground(new java.awt.Color(50, 50, 50));
        Txtosk.setName("Txtosk"); // NOI18N
        FormInput.add(Txtosk);
        Txtosk.setBounds(330, 350, 220, 20);

        Txtisk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtisk.setForeground(new java.awt.Color(50, 50, 50));
        Txtisk.setName("Txtisk"); // NOI18N
        FormInput.add(Txtisk);
        Txtisk.setBounds(330, 380, 220, 20);

        Txtbst.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtbst.setForeground(new java.awt.Color(50, 50, 50));
        Txtbst.setName("Txtbst"); // NOI18N
        FormInput.add(Txtbst);
        Txtbst.setBounds(330, 410, 220, 20);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("Urin Berdarah :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(20, 440, 100, 23);

        UB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        UB.setName("UB"); // NOI18N
        UB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UBKeyPressed(evt);
            }
        });
        FormInput.add(UB);
        UB.setBounds(180, 440, 128, 23);

        Txtub.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtub.setForeground(new java.awt.Color(50, 50, 50));
        Txtub.setName("Txtub"); // NOI18N
        FormInput.add(Txtub);
        Txtub.setBounds(330, 440, 220, 20);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Kg");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(160, 810, 30, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Kesadaran :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(20, 770, 80, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Sedang", "Buruk" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        FormInput.add(Keadaan);
        Keadaan.setBounds(120, 740, 130, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("III. PEMERIKSAAN FISIK PADA HD PERTAMA");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(10, 710, 240, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Pernah Transplantasi Ginjal :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(20, 670, 170, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Dialisis Pertama :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(20, 600, 100, 23);

        CAPD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        CAPD.setName("CAPD"); // NOI18N
        CAPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CAPDKeyPressed(evt);
            }
        });
        FormInput.add(CAPD);
        CAPD.setBounds(180, 640, 128, 23);

        Transplantasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Transplantasi.setName("Transplantasi"); // NOI18N
        Transplantasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransplantasiKeyPressed(evt);
            }
        });
        FormInput.add(Transplantasi);
        Transplantasi.setBounds(180, 670, 128, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Pernah CPAD :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(20, 640, 100, 23);

        TglTransplantasi.setEditable(false);
        TglTransplantasi.setForeground(new java.awt.Color(50, 70, 50));
        TglTransplantasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglTransplantasi.setDisplayFormat("dd-MM-yyyy");
        TglTransplantasi.setName("TglTransplantasi"); // NOI18N
        TglTransplantasi.setOpaque(false);
        TglTransplantasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglTransplantasiActionPerformed(evt);
            }
        });
        TglTransplantasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTransplantasiKeyPressed(evt);
            }
        });
        FormInput.add(TglTransplantasi);
        TglTransplantasi.setBounds(330, 670, 150, 20);

        TglCAPD.setEditable(false);
        TglCAPD.setForeground(new java.awt.Color(50, 70, 50));
        TglCAPD.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglCAPD.setDisplayFormat("dd-MM-yyyy");
        TglCAPD.setName("TglCAPD"); // NOI18N
        TglCAPD.setOpaque(false);
        TglCAPD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglCAPDActionPerformed(evt);
            }
        });
        TglCAPD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglCAPDKeyPressed(evt);
            }
        });
        FormInput.add(TglCAPD);
        TglCAPD.setBounds(330, 640, 150, 20);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Penyakit Lain :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(20, 500, 100, 23);

        PL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        PL.setName("PL"); // NOI18N
        PL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PLKeyPressed(evt);
            }
        });
        FormInput.add(PL);
        PL.setBounds(180, 500, 128, 23);

        Txtpl.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtpl.setForeground(new java.awt.Color(50, 50, 50));
        Txtpl.setName("Txtpl"); // NOI18N
        FormInput.add(Txtpl);
        Txtpl.setBounds(330, 500, 220, 20);

        Txtkon.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Txtkon.setForeground(new java.awt.Color(50, 50, 50));
        Txtkon.setName("Txtkon"); // NOI18N
        FormInput.add(Txtkon);
        Txtkon.setBounds(330, 530, 220, 20);

        KON.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        KON.setName("KON"); // NOI18N
        KON.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KONKeyPressed(evt);
            }
        });
        FormInput.add(KON);
        KON.setBounds(180, 530, 128, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Konsumsi Obat Nefrotoksis :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(20, 530, 150, 23);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 560, 880, 2);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Napas:");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(20, 1010, 40, 23);

        Napas.setFocusTraversalPolicyProvider(true);
        Napas.setName("Napas"); // NOI18N
        Napas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NapasKeyPressed(evt);
            }
        });
        FormInput.add(Napas);
        Napas.setBounds(60, 1010, 80, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("kali/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(160, 1010, 50, 23);

        jLabel56.setText("Ikterik:");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(380, 740, 60, 23);

        Ikterik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Ikterik.setName("Ikterik"); // NOI18N
        Ikterik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IkterikKeyPressed(evt);
            }
        });
        FormInput.add(Ikterik);
        Ikterik.setBounds(450, 740, 128, 23);

        jLabel57.setText("Jantung  :");
        jLabel57.setToolTipText("");
        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(280, 830, 80, 23);

        jLabel58.setText("Konjungtiva  :");
        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(260, 770, 100, 23);

        jLabel59.setText("Anemia:");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(380, 770, 60, 23);

        Konjungtiva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Konjungtiva.setName("Konjungtiva"); // NOI18N
        Konjungtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KonjungtivaKeyPressed(evt);
            }
        });
        FormInput.add(Konjungtiva);
        Konjungtiva.setBounds(450, 770, 128, 23);

        jLabel60.setText("Tekanan Vena Jugularis (JVP):");
        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(260, 800, 180, 23);

        JVP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NORMAL", "MENINGKAT" }));
        JVP.setName("JVP"); // NOI18N
        JVP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JVPKeyPressed(evt);
            }
        });
        FormInput.add(JVP);
        JVP.setBounds(450, 800, 128, 23);

        jLabel61.setText("Bising:");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(380, 860, 60, 23);

        jLabel62.setText("Kardiomegali:");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(360, 830, 80, 23);

        Kardiomegali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Kardiomegali.setName("Kardiomegali"); // NOI18N
        Kardiomegali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KardiomegaliKeyPressed(evt);
            }
        });
        FormInput.add(Kardiomegali);
        Kardiomegali.setBounds(450, 830, 128, 23);

        Bising.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Bising.setName("Bising"); // NOI18N
        Bising.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BisingKeyPressed(evt);
            }
        });
        FormInput.add(Bising);
        Bising.setBounds(450, 860, 128, 23);

        jLabel63.setText("Sklera  :");
        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(280, 740, 80, 23);

        jLabel64.setText("Paru  :");
        jLabel64.setToolTipText("");
        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(240, 890, 40, 23);

        jLabel65.setText("Sonor redup/pekak:whezzing:");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(280, 890, 160, 23);

        Whezzing.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Whezzing.setName("Whezzing"); // NOI18N
        Whezzing.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WhezzingKeyPressed(evt);
            }
        });
        FormInput.add(Whezzing);
        Whezzing.setBounds(450, 890, 128, 23);

        Ronchi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Ronchi.setName("Ronchi"); // NOI18N
        Ronchi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RonchiKeyPressed(evt);
            }
        });
        FormInput.add(Ronchi);
        Ronchi.setBounds(450, 920, 128, 23);

        jLabel66.setText("Ronchi:");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(380, 920, 60, 23);

        jLabel67.setText("Abdomen  :");
        jLabel67.setToolTipText("");
        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(280, 950, 80, 23);

        jLabel68.setText("Hepatomegali:");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(360, 950, 80, 23);

        Hepatomegali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Hepatomegali.setName("Hepatomegali"); // NOI18N
        Hepatomegali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HepatomegaliKeyPressed(evt);
            }
        });
        FormInput.add(Hepatomegali);
        Hepatomegali.setBounds(450, 950, 128, 23);

        Splenomegali.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Splenomegali.setName("Splenomegali"); // NOI18N
        Splenomegali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SplenomegaliKeyPressed(evt);
            }
        });
        FormInput.add(Splenomegali);
        Splenomegali.setBounds(450, 980, 128, 23);

        Ascites.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Ascites.setName("Ascites"); // NOI18N
        Ascites.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AscitesKeyPressed(evt);
            }
        });
        FormInput.add(Ascites);
        Ascites.setBounds(450, 1010, 128, 23);

        Edema.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TIDAK", "YA" }));
        Edema.setName("Edema"); // NOI18N
        Edema.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdemaKeyPressed(evt);
            }
        });
        FormInput.add(Edema);
        Edema.setBounds(450, 1040, 128, 23);

        jLabel69.setText("Edema:");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(380, 1040, 60, 23);

        jLabel70.setText("Ascites:");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(360, 1010, 80, 23);

        jLabel71.setText("Splenomegali:");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(360, 980, 80, 23);

        jLabel72.setText("Ekstremitas  :");
        jLabel72.setToolTipText("");
        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(270, 1040, 90, 23);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 1072, 880, 2);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("1. Foto Thorax :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(20, 1110, 100, 23);

        TglThorax.setForeground(new java.awt.Color(50, 70, 50));
        TglThorax.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglThorax.setDisplayFormat("dd-MM-yyyy");
        TglThorax.setName("TglThorax"); // NOI18N
        TglThorax.setOpaque(false);
        TglThorax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglThoraxActionPerformed(evt);
            }
        });
        TglThorax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglThoraxKeyPressed(evt);
            }
        });
        FormInput.add(TglThorax);
        TglThorax.setBounds(170, 1110, 100, 20);

        TglDialisis.setForeground(new java.awt.Color(50, 70, 50));
        TglDialisis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglDialisis.setDisplayFormat("dd-MM-yyyy");
        TglDialisis.setName("TglDialisis"); // NOI18N
        TglDialisis.setOpaque(false);
        TglDialisis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglDialisisActionPerformed(evt);
            }
        });
        TglDialisis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglDialisisKeyPressed(evt);
            }
        });
        FormInput.add(TglDialisis);
        TglDialisis.setBounds(180, 600, 150, 20);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("2. EKG :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(20, 1140, 100, 23);

        TglEKG.setForeground(new java.awt.Color(50, 70, 50));
        TglEKG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglEKG.setDisplayFormat("dd-MM-yyyy");
        TglEKG.setName("TglEKG"); // NOI18N
        TglEKG.setOpaque(false);
        TglEKG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglEKGActionPerformed(evt);
            }
        });
        TglEKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglEKGKeyPressed(evt);
            }
        });
        FormInput.add(TglEKG);
        TglEKG.setBounds(170, 1140, 100, 20);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("3. BNO/IVP :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(20, 1170, 100, 23);

        TglBNO.setForeground(new java.awt.Color(50, 70, 50));
        TglBNO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglBNO.setDisplayFormat("dd-MM-yyyy");
        TglBNO.setName("TglBNO"); // NOI18N
        TglBNO.setOpaque(false);
        TglBNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglBNOActionPerformed(evt);
            }
        });
        TglBNO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBNOKeyPressed(evt);
            }
        });
        FormInput.add(TglBNO);
        TglBNO.setBounds(170, 1170, 100, 20);

        TglUSG.setForeground(new java.awt.Color(50, 70, 50));
        TglUSG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglUSG.setDisplayFormat("dd-MM-yyyy");
        TglUSG.setName("TglUSG"); // NOI18N
        TglUSG.setOpaque(false);
        TglUSG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglUSGActionPerformed(evt);
            }
        });
        TglUSG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglUSGKeyPressed(evt);
            }
        });
        FormInput.add(TglUSG);
        TglUSG.setBounds(170, 1200, 100, 20);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("4. USG :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(20, 1200, 100, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("5. Renogram :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(20, 1230, 100, 23);

        TglRenogram.setForeground(new java.awt.Color(50, 70, 50));
        TglRenogram.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglRenogram.setDisplayFormat("dd-MM-yyyy");
        TglRenogram.setName("TglRenogram"); // NOI18N
        TglRenogram.setOpaque(false);
        TglRenogram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglRenogramActionPerformed(evt);
            }
        });
        TglRenogram.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglRenogramKeyPressed(evt);
            }
        });
        FormInput.add(TglRenogram);
        TglRenogram.setBounds(170, 1230, 100, 20);

        TglLaboratorium.setForeground(new java.awt.Color(50, 70, 50));
        TglLaboratorium.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglLaboratorium.setDisplayFormat("dd-MM-yyyy");
        TglLaboratorium.setName("TglLaboratorium"); // NOI18N
        TglLaboratorium.setOpaque(false);
        TglLaboratorium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglLaboratoriumActionPerformed(evt);
            }
        });
        TglLaboratorium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLaboratoriumKeyPressed(evt);
            }
        });
        FormInput.add(TglLaboratorium);
        TglLaboratorium.setBounds(470, 1230, 100, 20);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("10. Laboratorium :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(320, 1230, 100, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("9. Kultur Urin :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(320, 1200, 100, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("8. Arteriografi :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(320, 1170, 100, 23);

        TglKultururin.setForeground(new java.awt.Color(50, 70, 50));
        TglKultururin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglKultururin.setDisplayFormat("dd-MM-yyyy");
        TglKultururin.setName("TglKultururin"); // NOI18N
        TglKultururin.setOpaque(false);
        TglKultururin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglKultururinActionPerformed(evt);
            }
        });
        TglKultururin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKultururinKeyPressed(evt);
            }
        });
        FormInput.add(TglKultururin);
        TglKultururin.setBounds(470, 1200, 100, 20);

        TglArteriografi.setForeground(new java.awt.Color(50, 70, 50));
        TglArteriografi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglArteriografi.setDisplayFormat("dd-MM-yyyy");
        TglArteriografi.setName("TglArteriografi"); // NOI18N
        TglArteriografi.setOpaque(false);
        TglArteriografi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglArteriografiActionPerformed(evt);
            }
        });
        TglArteriografi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglArteriografiKeyPressed(evt);
            }
        });
        FormInput.add(TglArteriografi);
        TglArteriografi.setBounds(470, 1170, 100, 20);

        TglCTscan.setForeground(new java.awt.Color(50, 70, 50));
        TglCTscan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglCTscan.setDisplayFormat("dd-MM-yyyy");
        TglCTscan.setName("TglCTscan"); // NOI18N
        TglCTscan.setOpaque(false);
        TglCTscan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglCTscanActionPerformed(evt);
            }
        });
        TglCTscan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglCTscanKeyPressed(evt);
            }
        });
        FormInput.add(TglCTscan);
        TglCTscan.setBounds(470, 1140, 100, 20);

        TglBiopsi.setForeground(new java.awt.Color(50, 70, 50));
        TglBiopsi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
        TglBiopsi.setDisplayFormat("dd-MM-yyyy");
        TglBiopsi.setName("TglBiopsi"); // NOI18N
        TglBiopsi.setOpaque(false);
        TglBiopsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglBiopsiActionPerformed(evt);
            }
        });
        TglBiopsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglBiopsiKeyPressed(evt);
            }
        });
        FormInput.add(TglBiopsi);
        TglBiopsi.setBounds(470, 1110, 100, 20);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("7. CT Scan :");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(320, 1140, 100, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("6. PA Biopsi Ginjal :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(320, 1110, 100, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Hematokrit :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(20, 1270, 80, 23);

        Hematokrit.setFocusTraversalPolicyProvider(true);
        Hematokrit.setName("Hematokrit"); // NOI18N
        Hematokrit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HematokritKeyPressed(evt);
            }
        });
        FormInput.add(Hematokrit);
        Hematokrit.setBounds(140, 1270, 300, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Hemoglobin :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(20, 1300, 80, 23);

        Hemoglobin.setFocusTraversalPolicyProvider(true);
        Hemoglobin.setName("Hemoglobin"); // NOI18N
        Hemoglobin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HemoglobinKeyPressed(evt);
            }
        });
        FormInput.add(Hemoglobin);
        Hemoglobin.setBounds(140, 1300, 300, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Leukosit :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(20, 1330, 80, 23);

        Leukosit.setFocusTraversalPolicyProvider(true);
        Leukosit.setName("Leukosit"); // NOI18N
        Leukosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeukositKeyPressed(evt);
            }
        });
        FormInput.add(Leukosit);
        Leukosit.setBounds(140, 1330, 300, 23);

        Trombosit.setFocusTraversalPolicyProvider(true);
        Trombosit.setName("Trombosit"); // NOI18N
        Trombosit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TrombositKeyPressed(evt);
            }
        });
        FormInput.add(Trombosit);
        Trombosit.setBounds(140, 1360, 300, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Trombosit :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(20, 1360, 80, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Hitung Jenis :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(20, 1390, 80, 23);

        HitungJenis.setFocusTraversalPolicyProvider(true);
        HitungJenis.setName("HitungJenis"); // NOI18N
        HitungJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HitungJenisKeyPressed(evt);
            }
        });
        FormInput.add(HitungJenis);
        HitungJenis.setBounds(140, 1390, 300, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Ureum :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(20, 1420, 80, 23);

        Ureum.setFocusTraversalPolicyProvider(true);
        Ureum.setName("Ureum"); // NOI18N
        Ureum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UreumKeyPressed(evt);
            }
        });
        FormInput.add(Ureum);
        Ureum.setBounds(140, 1420, 300, 23);

        Kreatinin.setFocusTraversalPolicyProvider(true);
        Kreatinin.setName("Kreatinin"); // NOI18N
        Kreatinin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KreatininKeyPressed(evt);
            }
        });
        FormInput.add(Kreatinin);
        Kreatinin.setBounds(140, 1450, 300, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Kreatinin :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(20, 1450, 80, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Asam Urat :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(20, 1480, 80, 23);

        AsamUrat.setFocusTraversalPolicyProvider(true);
        AsamUrat.setName("AsamUrat"); // NOI18N
        AsamUrat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsamUratKeyPressed(evt);
            }
        });
        FormInput.add(AsamUrat);
        AsamUrat.setBounds(140, 1480, 300, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("SGOT :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(20, 1510, 80, 23);

        SGOT.setFocusTraversalPolicyProvider(true);
        SGOT.setName("SGOT"); // NOI18N
        SGOT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SGOTKeyPressed(evt);
            }
        });
        FormInput.add(SGOT);
        SGOT.setBounds(140, 1510, 300, 23);

        SGPT.setFocusTraversalPolicyProvider(true);
        SGPT.setName("SGPT"); // NOI18N
        SGPT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SGPTKeyPressed(evt);
            }
        });
        FormInput.add(SGPT);
        SGPT.setBounds(140, 1540, 300, 23);

        CT.setFocusTraversalPolicyProvider(true);
        CT.setName("CT"); // NOI18N
        CT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTKeyPressed(evt);
            }
        });
        FormInput.add(CT);
        CT.setBounds(140, 1600, 300, 23);

        UrinLengkap.setFocusTraversalPolicyProvider(true);
        UrinLengkap.setName("UrinLengkap"); // NOI18N
        UrinLengkap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrinLengkapKeyPressed(evt);
            }
        });
        FormInput.add(UrinLengkap);
        UrinLengkap.setBounds(140, 1630, 300, 23);

        CCT.setFocusTraversalPolicyProvider(true);
        CCT.setName("CCT"); // NOI18N
        CCT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CCTKeyPressed(evt);
            }
        });
        FormInput.add(CCT);
        CCT.setBounds(140, 1660, 300, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Anti HCV :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(20, 1690, 80, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("CCT :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(20, 1660, 80, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Urin Lengkap :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(20, 1630, 80, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("CT/BT :");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(20, 1600, 80, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("HbsAg :");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(20, 1570, 80, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("SGPT :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(20, 1540, 80, 23);

        HbsAg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NON REAKTIF", "REAKTIF" }));
        HbsAg.setName("HbsAg"); // NOI18N
        HbsAg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HbsAgKeyPressed(evt);
            }
        });
        FormInput.add(HbsAg);
        HbsAg.setBounds(140, 1570, 128, 23);

        AntiHCV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NON REAKTIF", "REAKTIF" }));
        AntiHCV.setName("AntiHCV"); // NOI18N
        AntiHCV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntiHCVKeyPressed(evt);
            }
        });
        FormInput.add(AntiHCV);
        AntiHCV.setBounds(140, 1690, 128, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Pasien dari :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(20, 80, 110, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Rawat Inap :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(20, 110, 110, 23);

        Ranap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "FLAMBOYAN", "SAKURA", "ANGGREK", "TULIP", "ICU", " " }));
        Ranap.setName("Ranap"); // NOI18N
        Ranap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RanapKeyPressed(evt);
            }
        });
        FormInput.add(Ranap);
        Ranap.setBounds(140, 110, 128, 23);

        Rajal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RAWAT JALAN", "RAWAT INAP", "IGD" }));
        Rajal.setName("Rajal"); // NOI18N
        Rajal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RajalActionPerformed(evt);
            }
        });
        Rajal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RajalKeyPressed(evt);
            }
        });
        FormInput.add(Rajal);
        Rajal.setBounds(140, 80, 128, 23);

        cblab.setName("cblab"); // NOI18N
        FormInput.add(cblab);
        cblab.setBounds(440, 1230, 20, 21);

        cbrenogram.setName("cbrenogram"); // NOI18N
        FormInput.add(cbrenogram);
        cbrenogram.setBounds(140, 1230, 20, 21);

        cbkultur.setName("cbkultur"); // NOI18N
        FormInput.add(cbkultur);
        cbkultur.setBounds(440, 1200, 20, 21);

        cbarteri.setName("cbarteri"); // NOI18N
        FormInput.add(cbarteri);
        cbarteri.setBounds(440, 1170, 20, 21);

        cbctscan.setName("cbctscan"); // NOI18N
        FormInput.add(cbctscan);
        cbctscan.setBounds(440, 1140, 21, 21);

        cbbiopsi.setName("cbbiopsi"); // NOI18N
        FormInput.add(cbbiopsi);
        cbbiopsi.setBounds(440, 1110, 21, 21);

        cbusg.setName("cbusg"); // NOI18N
        FormInput.add(cbusg);
        cbusg.setBounds(140, 1200, 20, 21);

        cbbno.setName("cbbno"); // NOI18N
        FormInput.add(cbbno);
        cbbno.setBounds(140, 1170, 20, 21);

        cbekg.setName("cbekg"); // NOI18N
        FormInput.add(cbekg);
        cbekg.setBounds(140, 1140, 20, 21);

        cbthorax.setName("cbthorax"); // NOI18N
        FormInput.add(cbthorax);
        cbthorax.setBounds(140, 1110, 20, 21);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-02-2023" }));
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
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Riwayat Penyakit Keluarga");
        }else{
            if(Hipertensi.getSelectedItem().toString()=="YA"){
                dbhipertensi=TxtHipertensi.getText();
            }else{
                dbhipertensi=Hipertensi.getSelectedItem().toString();
            }
            if(DM.getSelectedItem().toString()=="YA"){
                dbdm=Txtdm.getText();
            }else{
                dbdm=DM.getSelectedItem().toString();
            }
            if(BSK.getSelectedItem().toString()=="YA"){
                dbbsk=Txtbsk.getText();
            }else{
                dbbsk=BSK.getSelectedItem().toString();
            }
            if(OSK.getSelectedItem().toString()=="YA"){
                dbosk=Txtosk.getText();
            }else{
                dbosk=OSK.getSelectedItem().toString();
            }
            if(ISK.getSelectedItem().toString()=="YA"){
                dbisk=Txtisk.getText();
            }else{
                dbisk=ISK.getSelectedItem().toString();
            }
            if(BST.getSelectedItem().toString()=="YA"){
                dbbst=Txtbst.getText();
            }else{
                dbbst=BST.getSelectedItem().toString();
            }
            if(UB.getSelectedItem().toString()=="YA"){
                dbub=Txtub.getText();
            }else{
                dbub=UB.getSelectedItem().toString();
            }
            if(PGL.getSelectedItem().toString()=="YA"){
                dbpgl=Txtpgl.getText();
            }else{
                dbpgl=PGL.getSelectedItem().toString();
            }
            if(PL.getSelectedItem().toString()=="YA"){
                dbpl=Txtpl.getText();
            }else{
                dbpl=PL.getSelectedItem().toString();
            }
            if(KON.getSelectedItem().toString()=="YA"){
                dbkon=Txtkon.getText();
            }else{
                dbkon=KON.getSelectedItem().toString();
            }
            if(CAPD.getSelectedItem().toString()=="YA"){
                dbcapd=Valid.SetTgl(TglCAPD.getSelectedItem()+"");
            }else{
                dbcapd=CAPD.getSelectedItem().toString();
            }
            if(Transplantasi.getSelectedItem().toString()=="YA"){
                dbtransplantasi=Valid.SetTgl(TglTransplantasi.getSelectedItem()+"");
            }else{
                dbtransplantasi=Transplantasi.getSelectedItem().toString();
            }
            if(cbthorax.isSelected()){
                    dbthorax=Valid.SetTgl(TglThorax.getSelectedItem()+"");
                }else{
                    dbthorax="";   
                }
            if(cbekg.isSelected()){
                    dbekg=Valid.SetTgl(TglEKG.getSelectedItem()+"");
                }else{
                    dbekg="";   
                }
            if(cbbno.isSelected()){
                    dbbno=Valid.SetTgl(TglBNO.getSelectedItem()+"");
                }else{
                    dbbno="";   
                }
            if(cbusg.isSelected()){
                    dbusg=Valid.SetTgl(TglUSG.getSelectedItem()+"");
                }else{
                    dbusg="";   
                }
            if(cbrenogram.isSelected()){
                    dbrenogram=Valid.SetTgl(TglRenogram.getSelectedItem()+"");
                }else{
                    dbrenogram="";   
                }
            if(cbbiopsi.isSelected()){
                    dbbiopsi=Valid.SetTgl(TglBiopsi.getSelectedItem()+"");
                }else{
                    dbbiopsi="";   
                }
            if(cbctscan.isSelected()){
                    dbctscan=Valid.SetTgl(TglCTscan.getSelectedItem()+"");
                }else{
                    dbctscan="";  
                }
            if(cbarteri.isSelected()){
                    dbarteri=Valid.SetTgl(TglArteriografi.getSelectedItem()+"");
                }else{
                    dbarteri="";  
                }  
            if(cbkultur.isSelected()){
                    dbkultur=Valid.SetTgl(TglKultururin.getSelectedItem()+"");
                }else{
                    dbkultur="";  
                }  
            if(cblab.isSelected()){
                    dblab=Valid.SetTgl(TglLaboratorium.getSelectedItem()+"");
                }else{
                    dblab="";  
                }  
            //LocalDate dls = LocalDate.parse(TglDialisis.getSelectedItem().toString(), DateTimeFormatter.BASIC_ISO_DATE);
            String dls = TglDialisis.getSelectedItem().toString();
            if(Sequel.menyimpantf("penilaian_medis_ralan_hemodialisa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",68,new String[]{
                    TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    Rajal.getSelectedItem().toString(),Ranap.getSelectedItem().toString(),Alergi.getText(),Nyeri.getSelectedItem().toString(),Status.getText(),dbhipertensi,dbdm,dbbsk,dbosk,dbisk,dbbst,dbub,dbpgl,dbpl,dbkon,Valid.SetTgl(TglDialisis.getSelectedItem()+""),dbcapd,dbtransplantasi,
                    Keadaan.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),BB.getText(),TB.getText(),Suhu.getText(),Nadi.getText(),TD.getText(),Napas.getText(),Ikterik.getSelectedItem().toString(),Konjungtiva.getSelectedItem().toString(),JVP.getSelectedItem().toString(),
                    Kardiomegali.getSelectedItem().toString(),Bising.getSelectedItem().toString(),Whezzing.getSelectedItem().toString(),Ronchi.getSelectedItem().toString(),Hepatomegali.getSelectedItem().toString(),Splenomegali.getSelectedItem().toString(),Ascites.getSelectedItem().toString(),Edema.getSelectedItem().toString(),
                    dbthorax,dbekg,dbbno,dbusg,dbrenogram,dbbiopsi,dbctscan,dbarteri,dbkultur,dblab,
                    Hematokrit.getText(),Hemoglobin.getText(),Leukosit.getText(),Trombosit.getText(),HitungJenis.getText(),Ureum.getText(),Kreatinin.getText(),AsamUrat.getText(),SGOT.getText(),SGPT.getText(),HbsAg.getSelectedItem().toString(),CT.getText(),UrinLengkap.getText(),CCT.getText(),AntiHCV.getSelectedItem().toString(),Edukasi.getText()
                })!=true){
            } else {
                emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Edukasi,BtnBatal);
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Alergi.getText().trim().equals("")){
            Valid.textKosong(Alergi,"Riwayat Penyakit Keluarga");
        }else{
            if(Hipertensi.getSelectedItem()=="YA"){
               dbhipertensi=TxtHipertensi.getText();
            }else {
                dbhipertensi=Hipertensi.getSelectedItem().toString();
            }
            if(DM.getSelectedItem()=="YA"){
               dbdm=Txtdm.getText();
            }else {
                dbdm=DM.getSelectedItem().toString();
            }
            if(BSK.getSelectedItem()=="YA"){
               dbbsk=Txtbsk.getText();
            }else {
                dbbsk=BSK.getSelectedItem().toString();
            }
            if(OSK.getSelectedItem()=="YA"){
               dbosk=Txtosk.getText();
            }else {
                dbosk=OSK.getSelectedItem().toString();
            }
            if(ISK.getSelectedItem()=="YA"){
               dbisk=Txtisk.getText();
            }else {
                dbisk=ISK.getSelectedItem().toString();
            }
            if(BST.getSelectedItem()=="YA"){
               dbbst=Txtbst.getText();
            }else {
                dbbst=BST.getSelectedItem().toString();
            }
            if(UB.getSelectedItem()=="YA"){
               dbub=Txtub.getText();
            }else {
                dbub=UB.getSelectedItem().toString();
            }
            if(PGL.getSelectedItem()=="YA"){
               dbpgl=Txtpgl.getText();
            }else {
                dbpgl=PGL.getSelectedItem().toString();
            }
            if(PL.getSelectedItem()=="YA"){
               dbpl=Txtpl.getText();
            }else {
                dbpl=PL.getSelectedItem().toString();
            }
            if(KON.getSelectedItem()=="YA"){
               dbkon=Txtkon.getText();
            }else {
                dbkon=KON.getSelectedItem().toString();
            }
            if(cbthorax.isSelected()){
                    dbthorax=Valid.SetTgl(TglThorax.getSelectedItem()+"");
                }else{
                    dbthorax="";   
                }
            if(cbekg.isSelected()){
                    dbekg=Valid.SetTgl(TglEKG.getSelectedItem()+"");
                }else{
                    dbekg="";   
                }
            if(cbbno.isSelected()){
                    dbbno=Valid.SetTgl(TglBNO.getSelectedItem()+"");
                }else{
                    dbbno="";   
                }
            if(cbusg.isSelected()){
                    dbusg=Valid.SetTgl(TglUSG.getSelectedItem()+"");
                }else{
                    dbusg="";   
                }
            if(cbrenogram.isSelected()){
                    dbrenogram=Valid.SetTgl(TglRenogram.getSelectedItem()+"");
                }else{
                    dbrenogram="";   
                }
            if(cbbiopsi.isSelected()){
                    dbbiopsi=Valid.SetTgl(TglBiopsi.getSelectedItem()+"");
                }else{
                    dbbiopsi="";   
                }
            if(cbctscan.isSelected()){
                    dbctscan=Valid.SetTgl(TglCTscan.getSelectedItem()+"");
                }else{
                    dbctscan="";   
                }
            if(cbarteri.isSelected()){
                    dbarteri=Valid.SetTgl(TglArteriografi.getSelectedItem()+"");
                }else{
                    dbarteri="";   
                }
            if(cbkultur.isSelected()){
                    dbkultur=Valid.SetTgl(TglKultururin.getSelectedItem()+"");
                }else{
                    dbkultur="";   
                }  
            if(cblab.isSelected()){
                    dblab=Valid.SetTgl(TglLaboratorium.getSelectedItem()+"");
                }else{
                    dblab="";   
                }  
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_hemodialisa.tanggal,"+
                        "penilaian_medis_ralan_hemodialisa.kd_dokter,penilaian_medis_ralan_hemodialisa.anamnesis,penilaian_medis_ralan_hemodialisa.hubungan,penilaian_medis_ralan_hemodialisa.rajal,penilaian_medis_ralan_hemodialisa.ranap,penilaian_medis_ralan_hemodialisa.alergi,penilaian_medis_ralan_hemodialisa.nyeri,"+
                        "penilaian_medis_ralan_hemodialisa.status,penilaian_medis_ralan_hemodialisa.hipertensi,penilaian_medis_ralan_hemodialisa.dm,penilaian_medis_ralan_hemodialisa.bsk,penilaian_medis_ralan_hemodialisa.osk,penilaian_medis_ralan_hemodialisa.isk,penilaian_medis_ralan_hemodialisa.bst,penilaian_medis_ralan_hemodialisa.ub,penilaian_medis_ralan_hemodialisa.pgl,penilaian_medis_ralan_hemodialisa.pl,"+
                        "penilaian_medis_ralan_hemodialisa.kon,penilaian_medis_ralan_hemodialisa.dialisis,penilaian_medis_ralan_hemodialisa.capd,penilaian_medis_ralan_hemodialisa.transplantasi,penilaian_medis_ralan_hemodialisa.keadaan,penilaian_medis_ralan_hemodialisa.kesadaran,penilaian_medis_ralan_hemodialisa.bb,penilaian_medis_ralan_hemodialisa.tb,penilaian_medis_ralan_hemodialisa.suhu,penilaian_medis_ralan_hemodialisa.nadi,penilaian_medis_ralan_hemodialisa.td,penilaian_medis_ralan_hemodialisa.napas,"+
                        "penilaian_medis_ralan_hemodialisa.sklera,penilaian_medis_ralan_hemodialisa.konjungtiva,penilaian_medis_ralan_hemodialisa.jvp,penilaian_medis_ralan_hemodialisa.kardiomegali,penilaian_medis_ralan_hemodialisa.bising,penilaian_medis_ralan_hemodialisa.whezzing,penilaian_medis_ralan_hemodialisa.ronchi,penilaian_medis_ralan_hemodialisa.hepatomegali,penilaian_medis_ralan_hemodialisa.splenomegali,penilaian_medis_ralan_hemodialisa.ascites,"+
                        "penilaian_medis_ralan_hemodialisa.edema,penilaian_medis_ralan_hemodialisa.thorax,penilaian_medis_ralan_hemodialisa.ekg,penilaian_medis_ralan_hemodialisa.bno,penilaian_medis_ralan_hemodialisa.usg,penilaian_medis_ralan_hemodialisa.renogram,penilaian_medis_ralan_hemodialisa.biopsi,penilaian_medis_ralan_hemodialisa.ctscan,penilaian_medis_ralan_hemodialisa.arteriografi,penilaian_medis_ralan_hemodialisa.kultur,penilaian_medis_ralan_hemodialisa.lab,penilaian_medis_ralan_hemodialisa.hematokrit,"+
                        "penilaian_medis_ralan_hemodialisa.hemoglobin,penilaian_medis_ralan_hemodialisa.leukosit,penilaian_medis_ralan_hemodialisa.trombosit,penilaian_medis_ralan_hemodialisa.hitung,penilaian_medis_ralan_hemodialisa.ureum,penilaian_medis_ralan_hemodialisa.kreatinin,penilaian_medis_ralan_hemodialisa.asamurat,penilaian_medis_ralan_hemodialisa.sgot,penilaian_medis_ralan_hemodialisa.sgpt,penilaian_medis_ralan_hemodialisa.hbsag,penilaian_medis_ralan_hemodialisa.ct,penilaian_medis_ralan_hemodialisa.urin,"+
                        "penilaian_medis_ralan_hemodialisa.cct,penilaian_medis_ralan_hemodialisa.antihcv,penilaian_medis_ralan_hemodialisa.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_hemodialisa on reg_periksa.no_rawat=penilaian_medis_ralan_hemodialisa.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_hemodialisa.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_hemodialisa.tanggal between ? and ? order by penilaian_medis_ralan_hemodialisa.tanggal");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_hemodialisa.tanggal,"+
                        "penilaian_medis_ralan_hemodialisa.kd_dokter,penilaian_medis_ralan_hemodialisa.anamnesis,penilaian_medis_ralan_hemodialisa.hubungan,penilaian_medis_ralan_hemodialisa.rajal,penilaian_medis_ralan_hemodialisa.ranap,penilaian_medis_ralan_hemodialisa.alergi,penilaian_medis_ralan_hemodialisa.nyeri,"+
                        "penilaian_medis_ralan_hemodialisa.status,penilaian_medis_ralan_hemodialisa.hipertensi,penilaian_medis_ralan_hemodialisa.dm,penilaian_medis_ralan_hemodialisa.bsk,penilaian_medis_ralan_hemodialisa.osk,penilaian_medis_ralan_hemodialisa.isk,penilaian_medis_ralan_hemodialisa.bst,penilaian_medis_ralan_hemodialisa.ub,penilaian_medis_ralan_hemodialisa.pgl,penilaian_medis_ralan_hemodialisa.pl,"+
                        "penilaian_medis_ralan_hemodialisa.kon,penilaian_medis_ralan_hemodialisa.dialisis,penilaian_medis_ralan_hemodialisa.capd,penilaian_medis_ralan_hemodialisa.transplantasi,penilaian_medis_ralan_hemodialisa.keadaan,penilaian_medis_ralan_hemodialisa.kesadaran,penilaian_medis_ralan_hemodialisa.bb,penilaian_medis_ralan_hemodialisa.tb,penilaian_medis_ralan_hemodialisa.suhu,penilaian_medis_ralan_hemodialisa.nadi,penilaian_medis_ralan_hemodialisa.td,penilaian_medis_ralan_hemodialisa.napas,"+
                        "penilaian_medis_ralan_hemodialisa.sklera,penilaian_medis_ralan_hemodialisa.konjungtiva,penilaian_medis_ralan_hemodialisa.jvp,penilaian_medis_ralan_hemodialisa.kardiomegali,penilaian_medis_ralan_hemodialisa.bising,penilaian_medis_ralan_hemodialisa.whezzing,penilaian_medis_ralan_hemodialisa.ronchi,penilaian_medis_ralan_hemodialisa.hepatomegali,penilaian_medis_ralan_hemodialisa.splenomegali,penilaian_medis_ralan_hemodialisa.ascites,"+
                        "penilaian_medis_ralan_hemodialisa.edema,penilaian_medis_ralan_hemodialisa.thorax,penilaian_medis_ralan_hemodialisa.ekg,penilaian_medis_ralan_hemodialisa.bno,penilaian_medis_ralan_hemodialisa.usg,penilaian_medis_ralan_hemodialisa.renogram,penilaian_medis_ralan_hemodialisa.biopsi,penilaian_medis_ralan_hemodialisa.ctscan,penilaian_medis_ralan_hemodialisa.arteriografi,penilaian_medis_ralan_hemodialisa.kultur,penilaian_medis_ralan_hemodialisa.lab,penilaian_medis_ralan_hemodialisa.hematokrit,"+
                        "penilaian_medis_ralan_hemodialisa.hemoglobin,penilaian_medis_ralan_hemodialisa.leukosit,penilaian_medis_ralan_hemodialisa.trombosit,penilaian_medis_ralan_hemodialisa.hitung,penilaian_medis_ralan_hemodialisa.ureum,penilaian_medis_ralan_hemodialisa.kreatinin,penilaian_medis_ralan_hemodialisa.asamurat,penilaian_medis_ralan_hemodialisa.sgot,penilaian_medis_ralan_hemodialisa.sgpt,penilaian_medis_ralan_hemodialisa.hbsag,penilaian_medis_ralan_hemodialisa.ct,penilaian_medis_ralan_hemodialisa.urin,"+
                        "penilaian_medis_ralan_hemodialisa.cct,penilaian_medis_ralan_hemodialisa.antihcv,penilaian_medis_ralan_hemodialisa.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_hemodialisa on reg_periksa.no_rawat=penilaian_medis_ralan_hemodialisa.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_hemodialisa.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_hemodialisa.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_hemodialisa.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_hemodialisa.tanggal");
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
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='105px'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='55px'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>NIP</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Dokter</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='115px'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Anamnesis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Hubungan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Rajal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Ranap</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Alergi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='120px'><b>Nyeri</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Status</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Hipertensi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Diabetes Melitus</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>Batu Saluran Kemih</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75px'><b>Operasi Saluran Kemih</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Infeksi Saluran Kemih</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='67px'><b>Bengkak Seluruh Tubuh</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Urin Berdarah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>Penyakit Ginjal Laom</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>Penyakit Lain</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Konsumsi Obt Nefroktosis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dialisis pertama</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Pernah CAPD</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Pernah Transplantasi Ginjal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Keadaan Umum</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kesadaran</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>BB</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>TB</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>Suhu</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>Nadi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>td</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>Napas</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Ikterik</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Konjungtiva</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Tekanan vena jugularis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Jantung Kardiomegali</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Jantung Bising</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Paru Whezzing</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Paru Ronchi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>Abdomen Hepatomegali</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>Abdomen Splenomegali</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Abdomen Ascites</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Ekstremitas Edema</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Foto Thoraks</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>EKG</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>BNO</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>USG</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Renogram</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>PA Biopsi Ginjal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>CT Scan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Arteriografi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Kultur Urin</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Laboratorium</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Hematokrit</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Hemoglobin</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Leukosit</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Trombosit</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Hitung Jenis</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Ureum</b></td>"+
			    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Kreatinin</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>Asam Urat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>SGOT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>SGPT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>HbsAg</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>CT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Urin Lengkap</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>CCT</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Anti HCV</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Edukasi</b></td>"+
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
                               "<td valign='top'>"+rs.getString("rajal")+"</td>"+
                               "<td valign='top'>"+rs.getString("ranap")+"</td>"+
                               "<td valign='top'>"+rs.getString("alergi")+"</td>"+
                               "<td valign='top'>"+rs.getString("nyeri")+"</td>"+
                               "<td valign='top'>"+rs.getString("status")+"</td>"+
                               "<td valign='top'>"+rs.getString("hipertensi")+"</td>"+
                               "<td valign='top'>"+rs.getString("dm")+"</td>"+
                               "<td valign='top'>"+rs.getString("bsk")+"</td>"+
                               "<td valign='top'>"+rs.getString("osk")+"</td>"+
                               "<td valign='top'>"+rs.getString("isk")+"</td>"+
                               "<td valign='top'>"+rs.getString("bst")+"</td>"+
                               "<td valign='top'>"+rs.getString("ub")+"</td>"+
                               "<td valign='top'>"+rs.getString("pgl")+"</td>"+
                               "<td valign='top'>"+rs.getString("pl")+"</td>"+
                               "<td valign='top'>"+rs.getString("kon")+"</td>"+
                               "<td valign='top'>"+rs.getString("dialisis")+"</td>"+
                               "<td valign='top'>"+rs.getString("capd")+"</td>"+
                               "<td valign='top'>"+rs.getString("transplantasi")+"</td>"+
                               "<td valign='top'>"+rs.getString("keadaan")+"</td>"+
                               "<td valign='top'>"+rs.getString("kesadaran")+"</td>"+
                               "<td valign='top'>"+rs.getString("bb")+"</td>"+
                               "<td valign='top'>"+rs.getString("tb")+"</td>"+
                               "<td valign='top'>"+rs.getString("suhu")+"</td>"+
                               "<td valign='top'>"+rs.getString("nadi")+"</td>"+
                               "<td valign='top'>"+rs.getString("td")+"</td>"+
                               "<td valign='top'>"+rs.getString("napas")+"</td>"+
                               "<td valign='top'>"+rs.getString("sklera")+"</td>"+
                               "<td valign='top'>"+rs.getString("konjungtiva")+"</td>"+
                               "<td valign='top'>"+rs.getString("jvp")+"</td>"+
                               "<td valign='top'>"+rs.getString("kardiomegali")+"</td>"+
                               "<td valign='top'>"+rs.getString("bising")+"</td>"+
                               "<td valign='top'>"+rs.getString("whezzing")+"</td>"+
                               "<td valign='top'>"+rs.getString("ronchi")+"</td>"+
                               "<td valign='top'>"+rs.getString("hepatomegali")+"</td>"+
                               "<td valign='top'>"+rs.getString("splenomegali")+"</td>"+
                               "<td valign='top'>"+rs.getString("ascites")+"</td>"+
                               "<td valign='top'>"+rs.getString("edema")+"</td>"+
                               "<td valign='top'>"+rs.getString("thorax")+"</td>"+
                               "<td valign='top'>"+rs.getString("ekg")+"</td>"+
                               "<td valign='top'>"+rs.getString("bno")+"</td>"+
                               "<td valign='top'>"+rs.getString("usg")+"</td>"+
                               "<td valign='top'>"+rs.getString("renogram")+"</td>"+
                               "<td valign='top'>"+rs.getString("biopsi")+"</td>"+
                               "<td valign='top'>"+rs.getString("ctscan")+"</td>"+
                               "<td valign='top'>"+rs.getString("arteriografi")+"</td>"+
                               "<td valign='top'>"+rs.getString("kultur")+"</td>"+
                               "<td valign='top'>"+rs.getString("lab")+"</td>"+
                               "<td valign='top'>"+rs.getString("hematokrit")+"</td>"+
                               "<td valign='top'>"+rs.getString("hemoglobin")+"</td>"+
                               "<td valign='top'>"+rs.getString("leukosit")+"</td>"+
                               "<td valign='top'>"+rs.getString("trombosit")+"</td>"+
                               "<td valign='top'>"+rs.getString("hitung")+"</td>"+
                               "<td valign='top'>"+rs.getString("ureum")+"</td>"+
                               "<td valign='top'>"+rs.getString("kreatinin")+"</td>"+
                               "<td valign='top'>"+rs.getString("asamurat")+"</td>"+
                               "<td valign='top'>"+rs.getString("sgot")+"</td>"+
                               "<td valign='top'>"+rs.getString("sgpt")+"</td>"+
                               "<td valign='top'>"+rs.getString("hbsag")+"</td>"+
                               "<td valign='top'>"+rs.getString("ct")+"</td>"+
                               "<td valign='top'>"+rs.getString("urin")+"</td>"+
                               "<td valign='top'>"+rs.getString("cct")+"</td>"+
                               "<td valign='top'>"+rs.getString("antihcv")+"</td>"+
                               "<td valign='top'>"+rs.getString("edukasi")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='4400px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataPenilaianAwalMedisRalan.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='4400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA PENILAIAN AWAL MEDIS RAWAT JALAN<br><br></font>"+        
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

}//GEN-LAST:event_tbObatKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        
    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        //Valid.pindah(evt,RR,Status);
    }//GEN-LAST:event_BBKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,Suhu);
    }//GEN-LAST:event_NadiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        //Valid.pindah(evt,Nadi,RR);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Nyeri,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,TglAsuhan,Hubungan);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        //Valid.pindah2(evt,RPS,Alergi);
    }//GEN-LAST:event_AlergiKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        //Valid.pindah(evt,Status,Kepala);
    }//GEN-LAST:event_TBKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,Alergi,Nyeri);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        Valid.pindah(evt,BB,TB);
    }//GEN-LAST:event_StatusKeyPressed

    private void DMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DMKeyPressed
        //Valid.pindah(evt,GCS,Thoraks);
    }//GEN-LAST:event_DMKeyPressed

    private void ISKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ISKKeyPressed
        //Valid.pindah(evt,Columna,Lainnya);
    }//GEN-LAST:event_ISKKeyPressed

    private void BSTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BSTKeyPressed
       // Valid.pindah(evt,Muskulos,Lab);
    }//GEN-LAST:event_BSTKeyPressed

    private void HipertensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HipertensiKeyPressed
       // Valid.pindah(evt,Kepala,Abdomen);
    }//GEN-LAST:event_HipertensiKeyPressed

    private void BSKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BSKKeyPressed
        //Valid.pindah(evt,Thoraks,Ekstremitas);
    }//GEN-LAST:event_BSKKeyPressed

    private void OSKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OSKKeyPressed
        //Valid.pindah(evt,Ekstremitas,Lainnya);
    }//GEN-LAST:event_OSKKeyPressed

    private void PGLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PGLKeyPressed
        //Valid.pindah(evt,Abdomen,Columna);
    }//GEN-LAST:event_PGLKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        //Valid.pindah2(evt,Diagnosis2,BtnSimpan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,Edukasi,Anamnesis);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        //Valid.pindah(evt,Anamnesis,KeluhanUtama);
    }//GEN-LAST:event_HubunganKeyPressed

    private void MnPenilaianMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianMedisActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("lokalis",Sequel.cariGambar("select lokalis from gambar")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            finger1=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger1","Pasien \n"+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()+"\nNO RM "+(finger1.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),1).toString():finger1)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())); 
            
            Valid.MyReportqry("rptCetakPenilaianAwalMedisRalanHemodialisa.jasper","report","::[ Laporan Penilaian Awal Medis Rawat Jalan Hemodialisa]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_hemodialisa.tanggal,"+
                        "penilaian_medis_ralan_hemodialisa.kd_dokter,penilaian_medis_ralan_hemodialisa.anamnesis,penilaian_medis_ralan_hemodialisa.hubungan,penilaian_medis_ralan_hemodialisa.rajal,penilaian_medis_ralan_hemodialisa.ranap,penilaian_medis_ralan_hemodialisa.alergi,penilaian_medis_ralan_hemodialisa.nyeri,"+
                        "penilaian_medis_ralan_hemodialisa.status,penilaian_medis_ralan_hemodialisa.hipertensi,penilaian_medis_ralan_hemodialisa.dm,penilaian_medis_ralan_hemodialisa.bsk,penilaian_medis_ralan_hemodialisa.osk,penilaian_medis_ralan_hemodialisa.isk,penilaian_medis_ralan_hemodialisa.bst,penilaian_medis_ralan_hemodialisa.ub,penilaian_medis_ralan_hemodialisa.pgl,penilaian_medis_ralan_hemodialisa.pl,"+
                        "penilaian_medis_ralan_hemodialisa.kon,penilaian_medis_ralan_hemodialisa.dialisis,penilaian_medis_ralan_hemodialisa.capd,penilaian_medis_ralan_hemodialisa.transplantasi,penilaian_medis_ralan_hemodialisa.keadaan,penilaian_medis_ralan_hemodialisa.kesadaran,penilaian_medis_ralan_hemodialisa.bb,penilaian_medis_ralan_hemodialisa.td,penilaian_medis_ralan_hemodialisa.suhu,penilaian_medis_ralan_hemodialisa.konjungtiva,penilaian_medis_ralan_hemodialisa.tb,"+
                        "penilaian_medis_ralan_hemodialisa.nadi,penilaian_medis_ralan_hemodialisa.napas,penilaian_medis_ralan_hemodialisa.sklera,penilaian_medis_ralan_hemodialisa.jvp,penilaian_medis_ralan_hemodialisa.kardiomegali,penilaian_medis_ralan_hemodialisa.bising,penilaian_medis_ralan_hemodialisa.whezzing,penilaian_medis_ralan_hemodialisa.ronchi,penilaian_medis_ralan_hemodialisa.hepatomegali,penilaian_medis_ralan_hemodialisa.splenomegali,penilaian_medis_ralan_hemodialisa.ascites,"+
                        "penilaian_medis_ralan_hemodialisa.edema,penilaian_medis_ralan_hemodialisa.thorax,penilaian_medis_ralan_hemodialisa.ekg,penilaian_medis_ralan_hemodialisa.bno,penilaian_medis_ralan_hemodialisa.usg,penilaian_medis_ralan_hemodialisa.renogram,penilaian_medis_ralan_hemodialisa.biopsi,penilaian_medis_ralan_hemodialisa.ctscan,penilaian_medis_ralan_hemodialisa.arteriografi,penilaian_medis_ralan_hemodialisa.kultur,penilaian_medis_ralan_hemodialisa.lab,penilaian_medis_ralan_hemodialisa.hematokrit,"+
                        "penilaian_medis_ralan_hemodialisa.hemoglobin,penilaian_medis_ralan_hemodialisa.leukosit,penilaian_medis_ralan_hemodialisa.trombosit,penilaian_medis_ralan_hemodialisa.hitung,penilaian_medis_ralan_hemodialisa.ureum,penilaian_medis_ralan_hemodialisa.kreatinin,penilaian_medis_ralan_hemodialisa.asamurat,penilaian_medis_ralan_hemodialisa.sgot,penilaian_medis_ralan_hemodialisa.sgpt,penilaian_medis_ralan_hemodialisa.hbsag,penilaian_medis_ralan_hemodialisa.ct,penilaian_medis_ralan_hemodialisa.urin,"+
                        "penilaian_medis_ralan_hemodialisa.cct,penilaian_medis_ralan_hemodialisa.antihcv,penilaian_medis_ralan_hemodialisa.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_hemodialisa on reg_periksa.no_rawat=penilaian_medis_ralan_hemodialisa.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_hemodialisa.kd_dokter=dokter.kd_dokter where penilaian_medis_ralan_hemodialisa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void NyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriKeyPressed
         Valid.pindah2(evt,Kesadaran,TD);
    }//GEN-LAST:event_NyeriKeyPressed

    private void OSKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OSKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OSKActionPerformed

    private void HipertensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HipertensiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HipertensiActionPerformed

    private void UBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UBKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanKeyPressed

    private void CAPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CAPDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CAPDKeyPressed

    private void TransplantasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransplantasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransplantasiKeyPressed

    private void TglAsuhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglAsuhanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglAsuhanActionPerformed

    private void TglTransplantasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglTransplantasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTransplantasiActionPerformed

    private void TglTransplantasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTransplantasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTransplantasiKeyPressed

    private void TglCAPDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglCAPDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglCAPDActionPerformed

    private void TglCAPDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglCAPDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglCAPDKeyPressed

    private void PLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PLKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PLKeyPressed

    private void KONKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KONKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KONKeyPressed

    private void NapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NapasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NapasKeyPressed

    private void IkterikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IkterikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IkterikKeyPressed

    private void KonjungtivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KonjungtivaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KonjungtivaKeyPressed

    private void JVPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JVPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JVPKeyPressed

    private void KardiomegaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KardiomegaliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KardiomegaliKeyPressed

    private void BisingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BisingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BisingKeyPressed

    private void WhezzingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WhezzingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_WhezzingKeyPressed

    private void RonchiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RonchiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RonchiKeyPressed

    private void HepatomegaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HepatomegaliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HepatomegaliKeyPressed

    private void SplenomegaliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SplenomegaliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SplenomegaliKeyPressed

    private void AscitesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AscitesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AscitesKeyPressed

    private void EdemaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdemaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdemaKeyPressed

    private void TglThoraxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglThoraxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglThoraxActionPerformed

    private void TglThoraxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglThoraxKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglThoraxKeyPressed

    private void TglDialisisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglDialisisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglDialisisActionPerformed

    private void TglDialisisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglDialisisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglDialisisKeyPressed

    private void TglEKGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglEKGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglEKGActionPerformed

    private void TglEKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglEKGKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglEKGKeyPressed

    private void TglBNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglBNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBNOActionPerformed

    private void TglBNOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBNOKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBNOKeyPressed

    private void TglUSGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglUSGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglUSGActionPerformed

    private void TglUSGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglUSGKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglUSGKeyPressed

    private void TglRenogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglRenogramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglRenogramActionPerformed

    private void TglRenogramKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglRenogramKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglRenogramKeyPressed

    private void TglLaboratoriumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglLaboratoriumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglLaboratoriumActionPerformed

    private void TglLaboratoriumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglLaboratoriumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglLaboratoriumKeyPressed

    private void TglKultururinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglKultururinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglKultururinActionPerformed

    private void TglKultururinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKultururinKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglKultururinKeyPressed

    private void TglArteriografiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglArteriografiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglArteriografiActionPerformed

    private void TglArteriografiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglArteriografiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglArteriografiKeyPressed

    private void TglCTscanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglCTscanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglCTscanActionPerformed

    private void TglCTscanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglCTscanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglCTscanKeyPressed

    private void TglBiopsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglBiopsiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBiopsiActionPerformed

    private void TglBiopsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglBiopsiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglBiopsiKeyPressed

    private void HematokritKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HematokritKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HematokritKeyPressed

    private void HemoglobinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HemoglobinKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HemoglobinKeyPressed

    private void LeukositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeukositKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeukositKeyPressed

    private void TrombositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TrombositKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TrombositKeyPressed

    private void HitungJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HitungJenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HitungJenisKeyPressed

    private void UreumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UreumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UreumKeyPressed

    private void KreatininKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KreatininKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KreatininKeyPressed

    private void AsamUratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsamUratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AsamUratKeyPressed

    private void SGOTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SGOTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SGOTKeyPressed

    private void SGPTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SGPTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SGPTKeyPressed

    private void CTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTKeyPressed

    private void UrinLengkapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrinLengkapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UrinLengkapKeyPressed

    private void CCTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CCTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CCTKeyPressed

    private void HbsAgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HbsAgKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HbsAgKeyPressed

    private void AntiHCVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntiHCVKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AntiHCVKeyPressed

    private void RanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RanapKeyPressed

    private void RajalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RajalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RajalActionPerformed

    private void RajalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RajalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RajalKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianAwalMedisRalanHemodialisa dialog = new RMPenilaianAwalMedisRalanHemodialisa(new javax.swing.JFrame(), true);
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
    private widget.TextArea Alergi;
    private widget.ComboBox Anamnesis;
    private widget.ComboBox AntiHCV;
    private widget.TextBox AsamUrat;
    private widget.ComboBox Ascites;
    private widget.TextBox BB;
    private widget.ComboBox BSK;
    private widget.ComboBox BST;
    private widget.ComboBox Bising;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CAPD;
    private widget.TextBox CCT;
    private widget.TextBox CT;
    private widget.ComboBox DM;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Edema;
    private widget.TextArea Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HbsAg;
    private widget.TextBox Hematokrit;
    private widget.TextBox Hemoglobin;
    private widget.ComboBox Hepatomegali;
    private widget.ComboBox Hipertensi;
    private widget.TextBox HitungJenis;
    private widget.TextBox Hubungan;
    private widget.ComboBox ISK;
    private widget.ComboBox Ikterik;
    private widget.ComboBox JVP;
    private widget.TextBox Jk;
    private widget.ComboBox KON;
    private widget.ComboBox Kardiomegali;
    private widget.TextBox KdDokter;
    private widget.ComboBox Keadaan;
    private widget.ComboBox Kesadaran;
    private widget.ComboBox Konjungtiva;
    private widget.TextBox Kreatinin;
    private widget.Label LCount;
    private widget.TextBox Leukosit;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianMedis;
    private widget.TextBox Nadi;
    private widget.TextBox Napas;
    private widget.TextBox NmDokter;
    private widget.ComboBox Nyeri;
    private widget.ComboBox OSK;
    private widget.ComboBox PGL;
    private widget.ComboBox PL;
    private widget.ComboBox Rajal;
    private widget.ComboBox Ranap;
    private widget.ComboBox Ronchi;
    private widget.TextBox SGOT;
    private widget.TextBox SGPT;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Splenomegali;
    private widget.TextBox Status;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglArteriografi;
    private widget.Tanggal TglAsuhan;
    private widget.Tanggal TglBNO;
    private widget.Tanggal TglBiopsi;
    private widget.Tanggal TglCAPD;
    private widget.Tanggal TglCTscan;
    private widget.Tanggal TglDialisis;
    private widget.Tanggal TglEKG;
    private widget.Tanggal TglKultururin;
    private widget.Tanggal TglLaboratorium;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglRenogram;
    private widget.Tanggal TglThorax;
    private widget.Tanggal TglTransplantasi;
    private widget.Tanggal TglUSG;
    private widget.ComboBox Transplantasi;
    private widget.TextBox Trombosit;
    private java.awt.TextField TxtHipertensi;
    private java.awt.TextField Txtbsk;
    private java.awt.TextField Txtbst;
    private java.awt.TextField Txtdm;
    private java.awt.TextField Txtisk;
    private java.awt.TextField Txtkon;
    private java.awt.TextField Txtosk;
    private java.awt.TextField Txtpgl;
    private java.awt.TextField Txtpl;
    private java.awt.TextField Txtub;
    private widget.ComboBox UB;
    private widget.TextBox Ureum;
    private widget.TextBox UrinLengkap;
    private widget.ComboBox Whezzing;
    private javax.swing.JCheckBox cbarteri;
    private javax.swing.JCheckBox cbbiopsi;
    private javax.swing.JCheckBox cbbno;
    private javax.swing.JCheckBox cbctscan;
    private javax.swing.JCheckBox cbekg;
    private javax.swing.JCheckBox cbkultur;
    private javax.swing.JCheckBox cblab;
    private javax.swing.JCheckBox cbrenogram;
    private javax.swing.JCheckBox cbthorax;
    private javax.swing.JCheckBox cbusg;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel101;
    private widget.Label jLabel104;
    private widget.Label jLabel11;
    private widget.Label jLabel113;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
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
    private widget.Label jLabel35;
    private widget.Label jLabel36;
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
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator17;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane4;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_hemodialisa.tanggal,"+
                        "penilaian_medis_ralan_hemodialisa.kd_dokter,penilaian_medis_ralan_hemodialisa.anamnesis,penilaian_medis_ralan_hemodialisa.hubungan,penilaian_medis_ralan_hemodialisa.rajal,penilaian_medis_ralan_hemodialisa.ranap,penilaian_medis_ralan_hemodialisa.alergi,penilaian_medis_ralan_hemodialisa.nyeri,"+
                        "penilaian_medis_ralan_hemodialisa.status,penilaian_medis_ralan_hemodialisa.hipertensi,penilaian_medis_ralan_hemodialisa.dm,penilaian_medis_ralan_hemodialisa.bsk,penilaian_medis_ralan_hemodialisa.osk,penilaian_medis_ralan_hemodialisa.isk,penilaian_medis_ralan_hemodialisa.bst,penilaian_medis_ralan_hemodialisa.ub,penilaian_medis_ralan_hemodialisa.pgl,penilaian_medis_ralan_hemodialisa.pl,"+
                        "penilaian_medis_ralan_hemodialisa.kon,penilaian_medis_ralan_hemodialisa.dialisis,penilaian_medis_ralan_hemodialisa.capd,penilaian_medis_ralan_hemodialisa.transplantasi,penilaian_medis_ralan_hemodialisa.keadaan,penilaian_medis_ralan_hemodialisa.kesadaran,penilaian_medis_ralan_hemodialisa.bb,penilaian_medis_ralan_hemodialisa.tb,penilaian_medis_ralan_hemodialisa.suhu,penilaian_medis_ralan_hemodialisa.nadi,penilaian_medis_ralan_hemodialisa.td,penilaian_medis_ralan_hemodialisa.napas,"+
                        "penilaian_medis_ralan_hemodialisa.sklera,penilaian_medis_ralan_hemodialisa.konjungtiva,penilaian_medis_ralan_hemodialisa.jvp,penilaian_medis_ralan_hemodialisa.kardiomegali,penilaian_medis_ralan_hemodialisa.bising,penilaian_medis_ralan_hemodialisa.whezzing,penilaian_medis_ralan_hemodialisa.ronchi,penilaian_medis_ralan_hemodialisa.hepatomegali,penilaian_medis_ralan_hemodialisa.splenomegali,penilaian_medis_ralan_hemodialisa.ascites,"+
                        "penilaian_medis_ralan_hemodialisa.edema,penilaian_medis_ralan_hemodialisa.thorax,penilaian_medis_ralan_hemodialisa.ekg,penilaian_medis_ralan_hemodialisa.bno,penilaian_medis_ralan_hemodialisa.usg,penilaian_medis_ralan_hemodialisa.renogram,penilaian_medis_ralan_hemodialisa.biopsi,penilaian_medis_ralan_hemodialisa.ctscan,penilaian_medis_ralan_hemodialisa.arteriografi,penilaian_medis_ralan_hemodialisa.kultur,penilaian_medis_ralan_hemodialisa.lab,penilaian_medis_ralan_hemodialisa.hematokrit,"+
                        "penilaian_medis_ralan_hemodialisa.hemoglobin,penilaian_medis_ralan_hemodialisa.leukosit,penilaian_medis_ralan_hemodialisa.trombosit,penilaian_medis_ralan_hemodialisa.hitung,penilaian_medis_ralan_hemodialisa.ureum,penilaian_medis_ralan_hemodialisa.kreatinin,penilaian_medis_ralan_hemodialisa.asamurat,penilaian_medis_ralan_hemodialisa.sgot,penilaian_medis_ralan_hemodialisa.sgpt,penilaian_medis_ralan_hemodialisa.hbsag,penilaian_medis_ralan_hemodialisa.ct,penilaian_medis_ralan_hemodialisa.urin,"+
                        "penilaian_medis_ralan_hemodialisa.cct,penilaian_medis_ralan_hemodialisa.antihcv,penilaian_medis_ralan_hemodialisa.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_hemodialisa on reg_periksa.no_rawat=penilaian_medis_ralan_hemodialisa.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_hemodialisa.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_hemodialisa.tanggal between ? and ? order by penilaian_medis_ralan_hemodialisa.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,penilaian_medis_ralan_hemodialisa.tanggal,"+
                        "penilaian_medis_ralan_hemodialisa.kd_dokter,penilaian_medis_ralan_hemodialisa.anamnesis,penilaian_medis_ralan_hemodialisa.hubungan,penilaian_medis_ralan_hemodialisa.rajal,penilaian_medis_ralan_hemodialisa.ranap,penilaian_medis_ralan_hemodialisa.alergi,penilaian_medis_ralan_hemodialisa.nyeri,"+
                        "penilaian_medis_ralan_hemodialisa.status,penilaian_medis_ralan_hemodialisa.hipertensi,penilaian_medis_ralan_hemodialisa.dm,penilaian_medis_ralan_hemodialisa.bsk,penilaian_medis_ralan_hemodialisa.osk,penilaian_medis_ralan_hemodialisa.isk,penilaian_medis_ralan_hemodialisa.bst,penilaian_medis_ralan_hemodialisa.ub,penilaian_medis_ralan_hemodialisa.pgl,penilaian_medis_ralan_hemodialisa.pl,"+
                        "penilaian_medis_ralan_hemodialisa.kon,penilaian_medis_ralan_hemodialisa.dialisis,penilaian_medis_ralan_hemodialisa.capd,penilaian_medis_ralan_hemodialisa.transplantasi,penilaian_medis_ralan_hemodialisa.keadaan,penilaian_medis_ralan_hemodialisa.kesadaran,penilaian_medis_ralan_hemodialisa.bb,penilaian_medis_ralan_hemodialisa.tb,penilaian_medis_ralan_hemodialisa.suhu,penilaian_medis_ralan_hemodialisa.nadi,penilaian_medis_ralan_hemodialisa.td,penilaian_medis_ralan_hemodialisa.napas,"+
                        "penilaian_medis_ralan_hemodialisa.sklera,penilaian_medis_ralan_hemodialisa.konjungtiva,penilaian_medis_ralan_hemodialisa.jvp,penilaian_medis_ralan_hemodialisa.kardiomegali,penilaian_medis_ralan_hemodialisa.bising,penilaian_medis_ralan_hemodialisa.whezzing,penilaian_medis_ralan_hemodialisa.ronchi,penilaian_medis_ralan_hemodialisa.hepatomegali,penilaian_medis_ralan_hemodialisa.splenomegali,penilaian_medis_ralan_hemodialisa.ascites,"+
                        "penilaian_medis_ralan_hemodialisa.edema,penilaian_medis_ralan_hemodialisa.thorax,penilaian_medis_ralan_hemodialisa.ekg,penilaian_medis_ralan_hemodialisa.bno,penilaian_medis_ralan_hemodialisa.usg,penilaian_medis_ralan_hemodialisa.renogram,penilaian_medis_ralan_hemodialisa.biopsi,penilaian_medis_ralan_hemodialisa.ctscan,penilaian_medis_ralan_hemodialisa.arteriografi,penilaian_medis_ralan_hemodialisa.kultur,penilaian_medis_ralan_hemodialisa.lab,penilaian_medis_ralan_hemodialisa.hematokrit,"+
                        "penilaian_medis_ralan_hemodialisa.hemoglobin,penilaian_medis_ralan_hemodialisa.leukosit,penilaian_medis_ralan_hemodialisa.trombosit,penilaian_medis_ralan_hemodialisa.hitung,penilaian_medis_ralan_hemodialisa.ureum,penilaian_medis_ralan_hemodialisa.kreatinin,penilaian_medis_ralan_hemodialisa.asamurat,penilaian_medis_ralan_hemodialisa.sgot,penilaian_medis_ralan_hemodialisa.sgpt,penilaian_medis_ralan_hemodialisa.hbsag,penilaian_medis_ralan_hemodialisa.ct,penilaian_medis_ralan_hemodialisa.urin,"+
                        "penilaian_medis_ralan_hemodialisa.cct,penilaian_medis_ralan_hemodialisa.antihcv,penilaian_medis_ralan_hemodialisa.edukasi,dokter.nm_dokter "+
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_medis_ralan_hemodialisa on reg_periksa.no_rawat=penilaian_medis_ralan_hemodialisa.no_rawat "+
                        "inner join dokter on penilaian_medis_ralan_hemodialisa.kd_dokter=dokter.kd_dokter where "+
                        "penilaian_medis_ralan_hemodialisa.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_medis_ralan_hemodialisa.kd_dokter like ? or dokter.nm_dokter like ?) order by penilaian_medis_ralan_hemodialisa.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("anamnesis"),rs.getString("hubungan"),rs.getString("rajal"),rs.getString("ranap"),rs.getString("alergi"),rs.getString("nyeri"),rs.getString("status"),rs.getString("hipertensi"),rs.getString("dm"),
                        rs.getString("bsk"),rs.getString("osk"),rs.getString("isk"),rs.getString("bst"),rs.getString("ub"),rs.getString("pgl"),rs.getString("pl"),
                        rs.getString("kon"),rs.getString("dialisis"),rs.getString("capd"),rs.getString("transplantasi"),rs.getString("keadaan"),rs.getString("kesadaran"),rs.getString("bb"),rs.getString("tb"),rs.getString("suhu"),rs.getString("nadi"),rs.getString("td"),rs.getString("napas"),rs.getString("sklera"),rs.getString("konjungtiva"),
                        rs.getString("jvp"),rs.getString("kardiomegali"),rs.getString("bising"),rs.getString("whezzing"),rs.getString("ronchi"),rs.getString("hepatomegali"),
                        rs.getString("splenomegali"),rs.getString("ascites"),rs.getString("edema"),rs.getString("thorax"),rs.getString("ekg"),rs.getString("bno"),rs.getString("usg"),rs.getString("renogram"),rs.getString("biopsi"),rs.getString("ctscan"),rs.getString("arteriografi"),rs.getString("kultur"),rs.getString("lab"),rs.getString("hematokrit"),
                        rs.getString("hemoglobin"),rs.getString("leukosit"),rs.getString("trombosit"),rs.getString("hitung"),rs.getString("ureum"),rs.getString("kreatinin"),rs.getString("asamurat"),rs.getString("sgot"),rs.getString("sgpt"),rs.getString("hbsag"),rs.getString("ct"),rs.getString("urin"),rs.getString("cct"),rs.getString("antihcv"),rs.getString("edukasi")
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
        Anamnesis.setSelectedIndex(0);
        Hubungan.setText("");
        Rajal.setSelectedIndex(0);
        Ranap.setSelectedIndex(0);
        Alergi.setText("");
        Nyeri.setSelectedIndex(0);
        Status.setText("");
        Hipertensi.setSelectedIndex(0);
        DM.setSelectedIndex(0);
        BSK.setSelectedIndex(0);
        OSK.setSelectedIndex(0);
        ISK.setSelectedIndex(0);
        BST.setSelectedIndex(0);
        UB.setSelectedIndex(0);
        PGL.setSelectedIndex(0);
        PL.setSelectedIndex(0);
        KON.setSelectedIndex(0);
        TglDialisis.setDate(new Date());
        CAPD.setSelectedIndex(0);
        Transplantasi.setSelectedIndex(0);
        Keadaan.setSelectedIndex(0);
        Kesadaran.setSelectedIndex(0);
        BB.setText("");
        TB.setText("");
        Suhu.setText("");
        Nadi.setText("");
        TD.setText("");
        Napas.setText("");
        Ikterik.setSelectedIndex(0);
        Konjungtiva.setSelectedIndex(0);
        JVP.setSelectedIndex(0);
        Kardiomegali.setSelectedIndex(0);
        Bising.setSelectedIndex(0);
        Whezzing.setSelectedIndex(0);
        Ronchi.setSelectedIndex(0);
        Hepatomegali.setSelectedIndex(0);
        Splenomegali.setSelectedIndex(0);
        Ascites.setSelectedIndex(0);
        Edema.setSelectedIndex(0);
        TglThorax.setDate(new Date());
        TglEKG.setDate(new Date());
        TglBNO.setDate(new Date());
        TglUSG.setDate(new Date());
        TglRenogram.setDate(new Date());
        TglBiopsi.setDate(new Date());
        TglCTscan.setDate(new Date());
        TglArteriografi.setDate(new Date());
        TglKultururin.setDate(new Date());
        TglLaboratorium.setDate(new Date());
        Hematokrit.setText("");
        Hemoglobin.setText("");
        Leukosit.setText("");
        Trombosit.setText("");
        HitungJenis.setText("");
        Ureum.setText("");
        Kreatinin.setText("");
        AsamUrat.setText("");
        SGOT.setText("");
        SGPT.setText("");
        HbsAg.setSelectedIndex(0);
        CT.setText("");
        UrinLengkap.setText("");
        CCT.setText("");
        AntiHCV.setSelectedIndex(0);
        Edukasi.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        Anamnesis.requestFocus();
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
            Anamnesis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Hubungan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Rajal.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Ranap.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Status.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString())){
                Hipertensi.setSelectedItem("TIDAK");
                TxtHipertensi.setText("");
            }else{
                Hipertensi.setSelectedItem("YA");
                TxtHipertensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString())){
                DM.setSelectedItem("TIDAK");
                Txtdm.setText("");
            }else{
                DM.setSelectedItem("YA");
                Txtdm.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString())){
                BSK.setSelectedItem("TIDAK");
                Txtbsk.setText("");
            }else{
                BSK.setSelectedItem("YA");
                Txtbsk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString())){
                OSK.setSelectedItem("TIDAK");
                Txtosk.setText("");
            }else{
                OSK.setSelectedItem("YA");
                Txtosk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString())){
                ISK.setSelectedItem("TIDAK");
                Txtisk.setText("");
            }else{
                ISK.setSelectedItem("YA");
                Txtisk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString())){
                BST.setSelectedItem("TIDAK");
                Txtbst.setText("");
            }else{
                BST.setSelectedItem("YA");
                Txtbst.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString())){
                UB.setSelectedItem("TIDAK");
                Txtub.setText("");
            }else{
                UB.setSelectedItem("YA");
                Txtub.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString())){
                PGL.setSelectedItem("TIDAK");
                Txtpgl.setText("");
            }else{
                PGL.setSelectedItem("YA");
                Txtpgl.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString())){
                PL.setSelectedItem("TIDAK");
                Txtpl.setText("");
            }else{
                PL.setSelectedItem("YA");
                Txtpl.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString())){
                KON.setSelectedItem("TIDAK");
                Txtkon.setText("");
            }else{
                KON.setSelectedItem("YA");
                Txtkon.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            }
             Valid.SetTgl(TglDialisis,tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString())){
                CAPD.setSelectedItem("TIDAK");
            }else{
                CAPD.setSelectedItem("YA");
                Valid.SetTgl(TglCAPD,tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            }
            if("TIDAK".equals(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString())){
                Transplantasi.setSelectedItem("TIDAK");
            }else{
                Transplantasi.setSelectedItem("YA");
                Valid.SetTgl(TglTransplantasi,tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            }
            //CAPD.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            //Transplantasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Napas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Ikterik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Konjungtiva.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            JVP.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Kardiomegali.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Bising.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Whezzing.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Ronchi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Hepatomegali.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Splenomegali.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Ascites.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Edema.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Valid.SetTgl(TglThorax,tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Valid.SetTgl(TglEKG,tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Valid.SetTgl(TglBNO,tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Valid.SetTgl(TglUSG,tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Valid.SetTgl(TglRenogram,tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Valid.SetTgl(TglBiopsi,tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Valid.SetTgl(TglCTscan,tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Valid.SetTgl(TglArteriografi,tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Valid.SetTgl(TglKultururin,tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Valid.SetTgl(TglLaboratorium,tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Hematokrit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Hemoglobin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            Leukosit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            Trombosit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            HitungJenis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Ureum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            Kreatinin.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            AsamUrat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            SGOT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            SGPT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            HbsAg.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            CT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            UrinLengkap.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            CCT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            AntiHCV.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
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
        BtnSimpan.setEnabled(akses.getpenilaian_awal_medis_ralan());
        BtnHapus.setEnabled(akses.getpenilaian_awal_medis_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan());
        BtnEdit.setEnabled(akses.getpenilaian_awal_medis_ralan());
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
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_ralan_hemodialisa where no_rawat=?",1,new String[]{
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
            if(CAPD.getSelectedItem().toString()=="YA"){
                dbcapd=Valid.SetTgl(TglCAPD.getSelectedItem()+"");
                //dbcapd=TglCAPD.getSelectedItem().toString();
            }else{
                dbcapd=CAPD.getSelectedItem().toString();
            }
            if(Transplantasi.getSelectedItem().toString()=="YA"){
                dbtransplantasi=Valid.SetTgl(TglTransplantasi.getSelectedItem()+"");
                //dbtransplantasi=TglTransplantasi.getSelectedItem().toString();
            }else{
                dbtransplantasi=Transplantasi.getSelectedItem().toString();
            }
        String dls = TglDialisis.getSelectedItem().toString();
        if(Sequel.mengedittf("penilaian_medis_ralan_hemodialisa","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,anamnesis=?,hubungan=?,rajal=?,ranap=?,alergi=?,nyeri=?,status=?,hipertensi=?,dm=?,bsk=?,osk=?,isk=?,bst=?,ub=?,pgl=?,pl=?,kon=?,dialisis=?,capd=?,transplantasi=?,keadaan=?,kesadaran=?,bb=?,tb=?,suhu=?,nadi=?,td=?,napas=?,sklera=?,konjungtiva=?,jvp=?,kardiomegali=?,bising=?,whezzing=?,ronchi=?,hepatomegali=?,splenomegali=?,ascites=?,edema=?,thorax=?,ekg=?,bno=?,usg=?,renogram=?,biopsi=?,ctscan=?,arteriografi=?,kultur=?,lab=?,hematokrit=?,hemoglobin=?,leukosit=?,trombosit=?,hitung=?,ureum=?,kreatinin=?,asamurat=?,sgot=?,sgpt=?,hbsag=?,ct=?,urin=?,cct=?,antihcv=?,edukasi=?",69,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokter.getText(),Anamnesis.getSelectedItem().toString(),Hubungan.getText(),
                    Rajal.getSelectedItem().toString(),Ranap.getSelectedItem().toString(),Alergi.getText(),Nyeri.getSelectedItem().toString(),Status.getText(),dbhipertensi,dbdm,dbbsk,dbosk,dbisk,dbbst,dbub,dbpgl,dbpl,dbkon,Valid.SetTgl(TglDialisis.getSelectedItem()+""),dbcapd,dbtransplantasi,
                    Keadaan.getSelectedItem().toString(),Kesadaran.getSelectedItem().toString(),BB.getText(),TB.getText(),Suhu.getText(),Nadi.getText(),TD.getText(),Napas.getText(),Ikterik.getSelectedItem().toString(),Konjungtiva.getSelectedItem().toString(),JVP.getSelectedItem().toString(),
                    Kardiomegali.getSelectedItem().toString(),Bising.getSelectedItem().toString(),Whezzing.getSelectedItem().toString(),Ronchi.getSelectedItem().toString(),Hepatomegali.getSelectedItem().toString(),Splenomegali.getSelectedItem().toString(),Ascites.getSelectedItem().toString(),Edema.getSelectedItem().toString(),
                    dbthorax,dbekg,dbbno,dbusg,dbrenogram,dbbiopsi,dbctscan,dbarteri,dbkultur,dblab,
                    Hematokrit.getText(),Hemoglobin.getText(),Leukosit.getText(),Trombosit.getText(),HitungJenis.getText(),Ureum.getText(),Kreatinin.getText(),AsamUrat.getText(),SGOT.getText(),SGPT.getText(),HbsAg.getSelectedItem().toString(),CT.getText(),UrinLengkap.getText(),CCT.getText(),AntiHCV.getSelectedItem().toString(),Edukasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }
}

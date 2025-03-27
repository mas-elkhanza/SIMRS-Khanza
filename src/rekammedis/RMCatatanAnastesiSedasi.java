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
public final class RMCatatanAnastesiSedasi extends javax.swing.JDialog {
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
    private String finger="";
    private String TANGGALMUNDUR="yes";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMCatatanAnastesiSedasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode DPJP Anastesi","Nama DPJP Anastesi","NIP Petugas Anastesi","Nama Petugas Anastesi",
            "Kode DPJP Bedah","Nama DPJP Bedah","NIP Petugas Bedah","Nama Petugas Bedah","Tanggal","Diagnosa Pra Bedah","Tindakan/Jenis Pembedahan",
            "Diagnosa Pasca Bedah","Jam","Kesadaran","TD(mmHg)","Nadi(x/m)","RR(x/m)","Suhu(°C)","Saturasi O2","TB(Cm)","BB(Kg)","GD","Rhesus","HB(gr/dl)",
            "HT(%)","Leko(ul)","Trombo(ul)","BTCT(mnt)","GDS(MG/dl)","Pre Induksi Lainnya","Hiopotensi","TCI","CPB","Ventilator","Broncoskopy","Glidescope",
            "USG","Stimulator Syaraf","Teknik & Alat Khusus Lainnya","EKG","Keterangan EKG","Arteri Line","Keterangan Arteri Line","CVP","Keterangan CVP",
            "EtCO2","Stetoskop","NIBP","NGT","BIS","Cath A Pulmo","SpO2","Kateter Urine","Temp.","Monitoring Lainnya","ASA","Alergi","Keterangan Alergi",
            "Penyulit Pra Anastesi/Sedasi","Lanjut Tindakan","Sedasi","Keterangan Sedasi","Spinal","Anestesi Umum","Keterangan Anestesi Umum","Blok Perifer",
            "Keterangan Blok Perifer","Epidural","Batal Tindakan","Alasan/Keterangan Batal Tindakan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 75; i++) {
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
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(110);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(115);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(55);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(60);
            }else if(i==20){
                column.setPreferredWidth(55);
            }else if(i==21){
                column.setPreferredWidth(50);
            }else if(i==22){
                column.setPreferredWidth(50);
            }else if(i==23){
                column.setPreferredWidth(65);
            }else if(i==24){
                column.setPreferredWidth(45);
            }else if(i==25){
                column.setPreferredWidth(40);
            }else if(i==26){
                column.setPreferredWidth(32);
            }else if(i==27){
                column.setPreferredWidth(45);
            }else if(i==28){
                column.setPreferredWidth(50);
            }else if(i==29){
                column.setPreferredWidth(45);
            }else if(i==30){
                column.setPreferredWidth(47);
            }else if(i==31){
                column.setPreferredWidth(60);
            }else if(i==32){
                column.setPreferredWidth(60);
            }else if(i==33){
                column.setPreferredWidth(65);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(60);
            }else if(i==36){
                column.setPreferredWidth(45);
            }else if(i==37){
                column.setPreferredWidth(45);
            }else if(i==38){
                column.setPreferredWidth(60);
            }else if(i==39){
                column.setPreferredWidth(70);
            }else if(i==40){
                column.setPreferredWidth(60);
            }else if(i==41){
                column.setPreferredWidth(45);
            }else if(i==42){
                column.setPreferredWidth(100);
            }else if(i==43){
                column.setPreferredWidth(150);
            }else if(i==44){
                column.setPreferredWidth(40);
            }else if(i==45){
                column.setPreferredWidth(130);
            }else if(i==46){
                column.setPreferredWidth(60);
            }else if(i==47){
                column.setPreferredWidth(130);
            }else if(i==48){
                column.setPreferredWidth(40);
            }else if(i==49){
                column.setPreferredWidth(130);
            }else if(i==50){
                column.setPreferredWidth(45);
            }else if(i==51){
                column.setPreferredWidth(60);
            }else if(i==52){
                column.setPreferredWidth(40);
            }else if(i==53){
                column.setPreferredWidth(40);
            }else if(i==54){
                column.setPreferredWidth(40);
            }else if(i==55){
                column.setPreferredWidth(77);
            }else if(i==56){
                column.setPreferredWidth(40);
            }else if(i==57){
                column.setPreferredWidth(77);
            }else if(i==58){
                column.setPreferredWidth(45);
            }else if(i==59){
                column.setPreferredWidth(150);
            }else if(i==60){
                column.setPreferredWidth(35);
            }else if(i==61){
                column.setPreferredWidth(50);
            }else if(i==62){
                column.setPreferredWidth(150);
            }else if(i==63){
                column.setPreferredWidth(150);
            }else if(i==64){
                column.setPreferredWidth(90);
            }else if(i==65){
                column.setPreferredWidth(45);
            }else if(i==66){
                column.setPreferredWidth(150);
            }else if(i==67){
                column.setPreferredWidth(45);
            }else if(i==68){
                column.setPreferredWidth(87);
            }else if(i==69){
                column.setPreferredWidth(150);
            }else if(i==70){
                column.setPreferredWidth(70);
            }else if(i==71){
                column.setPreferredWidth(150);
            }else if(i==72){
                column.setPreferredWidth(52);
            }else if(i==73){
                column.setPreferredWidth(80);
            }else if(i==74){
                column.setPreferredWidth(180);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        DiagnosaPreBedah.setDocument(new batasInput((int)50).getKata(DiagnosaPreBedah));
        Tindakan.setDocument(new batasInput((int)50).getKata(Tindakan));
        DiagnosaPaskaBedah.setDocument(new batasInput((int)50).getKata(DiagnosaPaskaBedah));
        Jam.setDocument(new batasInput((int)10).getKata(Jam));
        TD.setDocument(new batasInput((int)8).getKata(TD));
        Nadi.setDocument(new batasInput((int)5).getKata(Nadi));
        RR.setDocument(new batasInput((int)5).getKata(RR));
        Suhu.setDocument(new batasInput((int)5).getKata(Suhu));
        Saturasi.setDocument(new batasInput((int)5).getKata(Saturasi));
        TB.setDocument(new batasInput((int)5).getKata(TB));
        BB.setDocument(new batasInput((int)5).getKata(BB));
        HB.setDocument(new batasInput((int)5).getKata(HB));
        HT.setDocument(new batasInput((int)5).getKata(HT));
        Leko.setDocument(new batasInput((int)5).getKata(Leko));
        Trombo.setDocument(new batasInput((int)5).getKata(Trombo));
        BTCT.setDocument(new batasInput((int)5).getKata(BTCT));
        GDS.setDocument(new batasInput((int)5).getKata(GDS));
        LainLainPrInduksi.setDocument(new batasInput((int)30).getKata(LainLainPrInduksi));
        TeknikAlatLainnya.setDocument(new batasInput((int)100).getKata(TeknikAlatLainnya));
        CVPKeterangan.setDocument(new batasInput((int)50).getKata(CVPKeterangan));
        ArteriLineKeterangan.setDocument(new batasInput((int)50).getKata(ArteriLineKeterangan));
        EKGLeadKeterangan.setDocument(new batasInput((int)50).getKata(EKGLeadKeterangan));
        MonitoringLainLain.setDocument(new batasInput((int)100).getKata(MonitoringLainLain));
        AlergiKeterangan.setDocument(new batasInput((int)50).getKata(AlergiKeterangan));
        PenyulitPra.setDocument(new batasInput((int)150).getKata(PenyulitPra));
        SedasiKeterangan.setDocument(new batasInput((int)30).getKata(SedasiKeterangan));
        AnastesiUmumKeterangan.setDocument(new batasInput((int)30).getKata(AnastesiUmumKeterangan));
        BlokPeriferKeterangan.setDocument(new batasInput((int)30).getKata(BlokPeriferKeterangan));
        BatalTindakanKeterangan.setDocument(new batasInput((int)150).getKata(BatalTindakanKeterangan));
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
                    if(i==1){
                        KdDokterAnestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NmDokterAnastesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        BtnDokterAnestesi.requestFocus();
                    }else if(i==2){
                        KdDokterBedah.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NmDokterBedah.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        BtnDokterBedah.requestFocus();
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    if(i==1){
                        KdPetugasAnastesi.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasAnastesi.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        BtnPetugasAnastesi.requestFocus();
                    }else if(i==2){
                        KdPetugasBedah.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasBedah.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        BtnPetugasBedah.requestFocus();
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
        MnPenilaianSedasi = new javax.swing.JMenuItem();
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
        KdDokterAnestesi = new widget.TextBox();
        NmDokterAnastesi = new widget.TextBox();
        BtnDokterAnestesi = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel12 = new widget.Label();
        DiagnosaPreBedah = new widget.TextBox();
        jLabel13 = new widget.Label();
        Tindakan = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel14 = new widget.Label();
        DiagnosaPaskaBedah = new widget.TextBox();
        label15 = new widget.Label();
        KdDokterBedah = new widget.TextBox();
        NmDokterBedah = new widget.TextBox();
        BtnDokterBedah = new widget.Button();
        label16 = new widget.Label();
        KdPetugasAnastesi = new widget.TextBox();
        NmPetugasAnastesi = new widget.TextBox();
        BtnPetugasAnastesi = new widget.Button();
        label17 = new widget.Label();
        KdPetugasBedah = new widget.TextBox();
        NmPetugasBedah = new widget.TextBox();
        BtnPetugasBedah = new widget.Button();
        jLabel109 = new widget.Label();
        jLabel15 = new widget.Label();
        Jam = new widget.TextBox();
        jLabel16 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel29 = new widget.Label();
        Saturasi = new widget.TextBox();
        Kesadaran = new widget.ComboBox();
        jLabel18 = new widget.Label();
        jLabel20 = new widget.Label();
        RR = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        TB = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel30 = new widget.Label();
        BB = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        GD = new widget.TextBox();
        jLabel33 = new widget.Label();
        Rhesus = new widget.ComboBox();
        jLabel34 = new widget.Label();
        HB = new widget.TextBox();
        jLabel35 = new widget.Label();
        HT = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        Leko = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel40 = new widget.Label();
        Trombo = new widget.TextBox();
        jLabel41 = new widget.Label();
        jLabel42 = new widget.Label();
        BTCT = new widget.TextBox();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        GDS = new widget.TextBox();
        jLabel45 = new widget.Label();
        LainLainPrInduksi = new widget.TextBox();
        jLabel46 = new widget.Label();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel110 = new widget.Label();
        jLabel47 = new widget.Label();
        TCI = new widget.ComboBox();
        jLabel48 = new widget.Label();
        Glidescope = new widget.ComboBox();
        jLabel49 = new widget.Label();
        StimulatorSaraf = new widget.ComboBox();
        jLabel50 = new widget.Label();
        CPB = new widget.ComboBox();
        jLabel51 = new widget.Label();
        USG = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Ventilator = new widget.ComboBox();
        jLabel53 = new widget.Label();
        Broncoskopy = new widget.ComboBox();
        jLabel54 = new widget.Label();
        Hiopotensi = new widget.ComboBox();
        jLabel55 = new widget.Label();
        TeknikAlatLainnya = new widget.TextBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel111 = new widget.Label();
        jLabel56 = new widget.Label();
        Etco2 = new widget.ComboBox();
        jLabel57 = new widget.Label();
        SpO2 = new widget.ComboBox();
        jLabel58 = new widget.Label();
        Stetoskop = new widget.ComboBox();
        jLabel59 = new widget.Label();
        NIBP = new widget.ComboBox();
        jLabel60 = new widget.Label();
        CathAPulmo = new widget.ComboBox();
        jLabel61 = new widget.Label();
        KateterUrine = new widget.ComboBox();
        jLabel62 = new widget.Label();
        NGT = new widget.ComboBox();
        BIS = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        CVP = new widget.ComboBox();
        CVPKeterangan = new widget.TextBox();
        ArteriLineKeterangan = new widget.TextBox();
        ArteriLine = new widget.ComboBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        Temp = new widget.ComboBox();
        jLabel67 = new widget.Label();
        EKGLead = new widget.ComboBox();
        EKGLeadKeterangan = new widget.TextBox();
        jLabel68 = new widget.Label();
        MonitoringLainLain = new widget.TextBox();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel112 = new widget.Label();
        jLabel129 = new widget.Label();
        AngkaAsa = new widget.ComboBox();
        jLabel69 = new widget.Label();
        PenyulitPra = new widget.TextBox();
        jLabel70 = new widget.Label();
        Alergi = new widget.ComboBox();
        AlergiKeterangan = new widget.TextBox();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel113 = new widget.Label();
        LanjutTindakan = new widget.ComboBox();
        jLabel130 = new widget.Label();
        Sedasi = new widget.ComboBox();
        jLabel71 = new widget.Label();
        SedasiKeterangan = new widget.TextBox();
        jLabel72 = new widget.Label();
        Spinal = new widget.ComboBox();
        jLabel73 = new widget.Label();
        Epidural = new widget.ComboBox();
        AnastesiUmum = new widget.ComboBox();
        jLabel74 = new widget.Label();
        AnastesiUmumKeterangan = new widget.TextBox();
        jLabel75 = new widget.Label();
        BlokPerifer = new widget.ComboBox();
        BlokPeriferKeterangan = new widget.TextBox();
        jLabel76 = new widget.Label();
        BatalTindakan = new widget.ComboBox();
        BatalTindakanKeterangan = new widget.TextBox();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
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

        MnPenilaianSedasi.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianSedasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianSedasi.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianSedasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianSedasi.setText("Laporan Catatan Anestesi-Sedasi");
        MnPenilaianSedasi.setName("MnPenilaianSedasi"); // NOI18N
        MnPenilaianSedasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianSedasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianSedasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianSedasi);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Anestesi-Sedasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(750, 783));
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

        label14.setText("DPJP Anestesi :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 110, 90, 23);

        KdDokterAnestesi.setEditable(false);
        KdDokterAnestesi.setName("KdDokterAnestesi"); // NOI18N
        KdDokterAnestesi.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokterAnestesi);
        KdDokterAnestesi.setBounds(94, 110, 90, 23);

        NmDokterAnastesi.setEditable(false);
        NmDokterAnastesi.setName("NmDokterAnastesi"); // NOI18N
        NmDokterAnastesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokterAnastesi);
        NmDokterAnastesi.setBounds(187, 110, 150, 23);

        BtnDokterAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterAnestesi.setMnemonic('2');
        BtnDokterAnestesi.setToolTipText("Alt+2");
        BtnDokterAnestesi.setName("BtnDokterAnestesi"); // NOI18N
        BtnDokterAnestesi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterAnestesiActionPerformed(evt);
            }
        });
        BtnDokterAnestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterAnestesiKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokterAnestesi);
        BtnDokterAnestesi.setBounds(340, 110, 28, 23);

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

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(160, 40, 52, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024 10:25:18" }));
        TglAsuhan.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(216, 40, 130, 23);

        jLabel12.setText("Diagnosa Pra-Bedah :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(344, 40, 120, 23);

        DiagnosaPreBedah.setHighlighter(null);
        DiagnosaPreBedah.setName("DiagnosaPreBedah"); // NOI18N
        DiagnosaPreBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPreBedahKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaPreBedah);
        DiagnosaPreBedah.setBounds(468, 40, 256, 23);

        jLabel13.setText("Tindakan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 70, 70, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(74, 70, 256, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 100, 750, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 170, 750, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 170, 750, 1);

        jLabel14.setText("Diagnosa Paska-Bedah :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(334, 70, 130, 23);

        DiagnosaPaskaBedah.setHighlighter(null);
        DiagnosaPaskaBedah.setName("DiagnosaPaskaBedah"); // NOI18N
        DiagnosaPaskaBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaPaskaBedahKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaPaskaBedah);
        DiagnosaPaskaBedah.setBounds(468, 70, 256, 23);

        label15.setText("DPJP Bedah :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(370, 110, 76, 23);

        KdDokterBedah.setEditable(false);
        KdDokterBedah.setName("KdDokterBedah"); // NOI18N
        KdDokterBedah.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokterBedah);
        KdDokterBedah.setBounds(450, 110, 90, 23);

        NmDokterBedah.setEditable(false);
        NmDokterBedah.setName("NmDokterBedah"); // NOI18N
        NmDokterBedah.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokterBedah);
        NmDokterBedah.setBounds(543, 110, 150, 23);

        BtnDokterBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterBedah.setMnemonic('2');
        BtnDokterBedah.setToolTipText("Alt+2");
        BtnDokterBedah.setName("BtnDokterBedah"); // NOI18N
        BtnDokterBedah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterBedahActionPerformed(evt);
            }
        });
        BtnDokterBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterBedahKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokterBedah);
        BtnDokterBedah.setBounds(696, 110, 28, 23);

        label16.setText("Pr. Anestesi :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(0, 140, 90, 23);

        KdPetugasAnastesi.setEditable(false);
        KdPetugasAnastesi.setName("KdPetugasAnastesi"); // NOI18N
        KdPetugasAnastesi.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasAnastesi);
        KdPetugasAnastesi.setBounds(94, 140, 90, 23);

        NmPetugasAnastesi.setEditable(false);
        NmPetugasAnastesi.setName("NmPetugasAnastesi"); // NOI18N
        NmPetugasAnastesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasAnastesi);
        NmPetugasAnastesi.setBounds(187, 140, 150, 23);

        BtnPetugasAnastesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasAnastesi.setMnemonic('2');
        BtnPetugasAnastesi.setToolTipText("Alt+2");
        BtnPetugasAnastesi.setName("BtnPetugasAnastesi"); // NOI18N
        BtnPetugasAnastesi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasAnastesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasAnastesiActionPerformed(evt);
            }
        });
        BtnPetugasAnastesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasAnastesiKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugasAnastesi);
        BtnPetugasAnastesi.setBounds(340, 140, 28, 23);

        label17.setText("Pr. Bedah :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(370, 140, 76, 23);

        KdPetugasBedah.setEditable(false);
        KdPetugasBedah.setName("KdPetugasBedah"); // NOI18N
        KdPetugasBedah.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPetugasBedah);
        KdPetugasBedah.setBounds(450, 140, 90, 23);

        NmPetugasBedah.setEditable(false);
        NmPetugasBedah.setName("NmPetugasBedah"); // NOI18N
        NmPetugasBedah.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugasBedah);
        NmPetugasBedah.setBounds(543, 140, 150, 23);

        BtnPetugasBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasBedah.setMnemonic('2');
        BtnPetugasBedah.setToolTipText("Alt+2");
        BtnPetugasBedah.setName("BtnPetugasBedah"); // NOI18N
        BtnPetugasBedah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasBedahActionPerformed(evt);
            }
        });
        BtnPetugasBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasBedahKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugasBedah);
        BtnPetugasBedah.setBounds(696, 140, 28, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("I. PENILAIAN PRA INDUKSI");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(10, 170, 170, 23);

        jLabel15.setText("Kesadaran :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(173, 190, 80, 23);

        Jam.setFocusTraversalPolicyProvider(true);
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(94, 190, 55, 23);

        jLabel16.setText("Nadi :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(600, 190, 40, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(644, 190, 55, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("x/m");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(702, 190, 30, 23);

        jLabel22.setText("TD :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(414, 190, 40, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(458, 190, 76, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(537, 190, 50, 23);

        jLabel29.setText("Saturasi O2 :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(311, 220, 90, 23);

        Saturasi.setFocusTraversalPolicyProvider(true);
        Saturasi.setName("Saturasi"); // NOI18N
        Saturasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaturasiKeyPressed(evt);
            }
        });
        FormInput.add(Saturasi);
        Saturasi.setBounds(405, 220, 55, 23);

        Kesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compos Mentis", "Somnolence", "Sopor", "Coma", "Alert", "Confusion", "Voice", "Pain", "Unresponsive", "Apatis", "Delirium" }));
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(257, 190, 130, 23);

        jLabel18.setText("Jam :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 190, 90, 23);

        jLabel20.setText("RR :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 220, 90, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(94, 220, 55, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("x/m");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(152, 220, 30, 23);

        jLabel25.setText("Suhu :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(170, 220, 60, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(234, 220, 55, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("°C");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(292, 220, 30, 23);

        jLabel27.setText("TB :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(470, 220, 40, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(514, 220, 55, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("Cm");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(572, 220, 30, 23);

        jLabel30.setText("BB :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(600, 220, 40, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(644, 220, 55, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Kg");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(702, 220, 30, 23);

        jLabel32.setText("GD :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 250, 90, 23);

        GD.setEditable(false);
        GD.setFocusTraversalPolicyProvider(true);
        GD.setName("GD"); // NOI18N
        FormInput.add(GD);
        GD.setBounds(94, 250, 55, 23);

        jLabel33.setText("Rhesus :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(165, 250, 55, 23);

        Rhesus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "+", "-" }));
        Rhesus.setName("Rhesus"); // NOI18N
        Rhesus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RhesusKeyPressed(evt);
            }
        });
        FormInput.add(Rhesus);
        Rhesus.setBounds(224, 250, 60, 23);

        jLabel34.setText("HB :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(311, 250, 40, 23);

        HB.setFocusTraversalPolicyProvider(true);
        HB.setName("HB"); // NOI18N
        HB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HBKeyPressed(evt);
            }
        });
        FormInput.add(HB);
        HB.setBounds(355, 250, 55, 23);

        jLabel35.setText("HT :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(470, 250, 40, 23);

        HT.setFocusTraversalPolicyProvider(true);
        HT.setName("HT"); // NOI18N
        HT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HTKeyPressed(evt);
            }
        });
        FormInput.add(HT);
        HT.setBounds(514, 250, 55, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("gr/dl");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(413, 250, 30, 23);

        jLabel37.setText("Leko :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(600, 250, 40, 23);

        Leko.setFocusTraversalPolicyProvider(true);
        Leko.setName("Leko"); // NOI18N
        Leko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LekoKeyPressed(evt);
            }
        });
        FormInput.add(Leko);
        Leko.setBounds(644, 250, 55, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("ul");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(702, 250, 30, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("%");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(572, 250, 30, 23);

        jLabel40.setText("Trombo :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 280, 90, 23);

        Trombo.setFocusTraversalPolicyProvider(true);
        Trombo.setName("Trombo"); // NOI18N
        Trombo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TromboKeyPressed(evt);
            }
        });
        FormInput.add(Trombo);
        Trombo.setBounds(94, 280, 55, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("ul");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(152, 280, 30, 23);

        jLabel42.setText("BT-CT :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(165, 280, 55, 23);

        BTCT.setFocusTraversalPolicyProvider(true);
        BTCT.setName("BTCT"); // NOI18N
        BTCT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BTCTKeyPressed(evt);
            }
        });
        FormInput.add(BTCT);
        BTCT.setBounds(224, 280, 55, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("mnt");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(282, 280, 30, 23);

        jLabel44.setText("GDS :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(311, 280, 40, 23);

        GDS.setFocusTraversalPolicyProvider(true);
        GDS.setName("GDS"); // NOI18N
        GDS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GDSKeyPressed(evt);
            }
        });
        FormInput.add(GDS);
        GDS.setBounds(355, 280, 55, 23);

        jLabel45.setText("Lain-lain :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(460, 280, 50, 23);

        LainLainPrInduksi.setFocusTraversalPolicyProvider(true);
        LainLainPrInduksi.setName("LainLainPrInduksi"); // NOI18N
        LainLainPrInduksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainLainPrInduksiKeyPressed(evt);
            }
        });
        FormInput.add(LainLainPrInduksi);
        LainLainPrInduksi.setBounds(514, 280, 210, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("MG/dl");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(413, 280, 40, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 310, 750, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 310, 750, 1);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("II. TEKNIK & ALAT KHUSUS");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(10, 310, 160, 23);

        jLabel47.setText("TCI :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(0, 330, 90, 23);

        TCI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        TCI.setName("TCI"); // NOI18N
        TCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCIKeyPressed(evt);
            }
        });
        FormInput.add(TCI);
        TCI.setBounds(94, 330, 80, 23);

        jLabel48.setText("Glidescope :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(185, 330, 80, 23);

        Glidescope.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Glidescope.setName("Glidescope"); // NOI18N
        Glidescope.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GlidescopeKeyPressed(evt);
            }
        });
        FormInput.add(Glidescope);
        Glidescope.setBounds(269, 330, 80, 23);

        jLabel49.setText("Stimulator Saraf :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(366, 330, 100, 23);

        StimulatorSaraf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        StimulatorSaraf.setName("StimulatorSaraf"); // NOI18N
        StimulatorSaraf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StimulatorSarafKeyPressed(evt);
            }
        });
        FormInput.add(StimulatorSaraf);
        StimulatorSaraf.setBounds(470, 330, 80, 23);

        jLabel50.setText("CPB :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(590, 330, 50, 23);

        CPB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CPB.setName("CPB"); // NOI18N
        CPB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CPBKeyPressed(evt);
            }
        });
        FormInput.add(CPB);
        CPB.setBounds(644, 330, 80, 23);

        jLabel51.setText("USG :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 360, 90, 23);

        USG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        USG.setName("USG"); // NOI18N
        USG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGKeyPressed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(94, 360, 80, 23);

        jLabel52.setText("Ventilator :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(185, 360, 80, 23);

        Ventilator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Ventilator.setName("Ventilator"); // NOI18N
        Ventilator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VentilatorKeyPressed(evt);
            }
        });
        FormInput.add(Ventilator);
        Ventilator.setBounds(269, 360, 80, 23);

        jLabel53.setText("Broncoskopy :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(366, 360, 100, 23);

        Broncoskopy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Broncoskopy.setName("Broncoskopy"); // NOI18N
        Broncoskopy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BroncoskopyKeyPressed(evt);
            }
        });
        FormInput.add(Broncoskopy);
        Broncoskopy.setBounds(470, 360, 80, 23);

        jLabel54.setText("Hiopotensi :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(570, 360, 70, 23);

        Hiopotensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Hiopotensi.setName("Hiopotensi"); // NOI18N
        Hiopotensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HiopotensiKeyPressed(evt);
            }
        });
        FormInput.add(Hiopotensi);
        Hiopotensi.setBounds(644, 360, 80, 23);

        jLabel55.setText("Lainnya :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(0, 390, 90, 23);

        TeknikAlatLainnya.setFocusTraversalPolicyProvider(true);
        TeknikAlatLainnya.setName("TeknikAlatLainnya"); // NOI18N
        TeknikAlatLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TeknikAlatLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(TeknikAlatLainnya);
        TeknikAlatLainnya.setBounds(94, 390, 630, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 420, 750, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 420, 750, 1);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("III. MONITORING");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(10, 420, 130, 23);

        jLabel56.setText("EtCO2 :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(0, 440, 90, 23);

        Etco2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Etco2.setName("Etco2"); // NOI18N
        Etco2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Etco2KeyPressed(evt);
            }
        });
        FormInput.add(Etco2);
        Etco2.setBounds(94, 440, 80, 23);

        jLabel57.setText("SpO2 :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 470, 90, 23);

        SpO2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        SpO2.setName("SpO2"); // NOI18N
        SpO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpO2KeyPressed(evt);
            }
        });
        FormInput.add(SpO2);
        SpO2.setBounds(94, 470, 80, 23);

        jLabel58.setText("Stetoskop :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(195, 440, 80, 23);

        Stetoskop.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Stetoskop.setName("Stetoskop"); // NOI18N
        Stetoskop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StetoskopKeyPressed(evt);
            }
        });
        FormInput.add(Stetoskop);
        Stetoskop.setBounds(279, 440, 80, 23);

        jLabel59.setText("NIBP :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(195, 470, 80, 23);

        NIBP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        NIBP.setName("NIBP"); // NOI18N
        NIBP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIBPKeyPressed(evt);
            }
        });
        FormInput.add(NIBP);
        NIBP.setBounds(279, 470, 80, 23);

        jLabel60.setText("Cath A Pulmo :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(383, 440, 100, 23);

        CathAPulmo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CathAPulmo.setName("CathAPulmo"); // NOI18N
        CathAPulmo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CathAPulmoKeyPressed(evt);
            }
        });
        FormInput.add(CathAPulmo);
        CathAPulmo.setBounds(487, 440, 80, 23);

        jLabel61.setText("Kateter Urine :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(383, 470, 100, 23);

        KateterUrine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KateterUrine.setName("KateterUrine"); // NOI18N
        KateterUrine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KateterUrineKeyPressed(evt);
            }
        });
        FormInput.add(KateterUrine);
        KateterUrine.setBounds(487, 470, 80, 23);

        jLabel62.setText("NGT :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(590, 440, 50, 23);

        NGT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        NGT.setName("NGT"); // NOI18N
        NGT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NGTKeyPressed(evt);
            }
        });
        FormInput.add(NGT);
        NGT.setBounds(644, 440, 80, 23);

        BIS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BIS.setName("BIS"); // NOI18N
        BIS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BISKeyPressed(evt);
            }
        });
        FormInput.add(BIS);
        BIS.setBounds(644, 470, 80, 23);

        jLabel63.setText("BIS :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(590, 470, 50, 23);

        jLabel64.setText("CVP :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 500, 90, 23);

        CVP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        CVP.setName("CVP"); // NOI18N
        CVP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CVPKeyPressed(evt);
            }
        });
        FormInput.add(CVP);
        CVP.setBounds(94, 500, 80, 23);

        CVPKeterangan.setFocusTraversalPolicyProvider(true);
        CVPKeterangan.setName("CVPKeterangan"); // NOI18N
        CVPKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CVPKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(CVPKeterangan);
        CVPKeterangan.setBounds(177, 500, 125, 23);

        ArteriLineKeterangan.setFocusTraversalPolicyProvider(true);
        ArteriLineKeterangan.setName("ArteriLineKeterangan"); // NOI18N
        ArteriLineKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ArteriLineKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(ArteriLineKeterangan);
        ArteriLineKeterangan.setBounds(461, 500, 125, 23);

        ArteriLine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        ArteriLine.setName("ArteriLine"); // NOI18N
        ArteriLine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ArteriLineKeyPressed(evt);
            }
        });
        FormInput.add(ArteriLine);
        ArteriLine.setBounds(378, 500, 80, 23);

        jLabel65.setText("Arteri Line :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(304, 500, 70, 23);

        jLabel66.setText("Temp. :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(590, 500, 50, 23);

        Temp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Temp.setName("Temp"); // NOI18N
        Temp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempKeyPressed(evt);
            }
        });
        FormInput.add(Temp);
        Temp.setBounds(644, 500, 80, 23);

        jLabel67.setText("EKG Lead :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(0, 530, 90, 23);

        EKGLead.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        EKGLead.setName("EKGLead"); // NOI18N
        EKGLead.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EKGLeadKeyPressed(evt);
            }
        });
        FormInput.add(EKGLead);
        EKGLead.setBounds(94, 530, 80, 23);

        EKGLeadKeterangan.setFocusTraversalPolicyProvider(true);
        EKGLeadKeterangan.setName("EKGLeadKeterangan"); // NOI18N
        EKGLeadKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EKGLeadKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(EKGLeadKeterangan);
        EKGLeadKeterangan.setBounds(177, 530, 125, 23);

        jLabel68.setText("Lain-lain :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(304, 530, 70, 23);

        MonitoringLainLain.setFocusTraversalPolicyProvider(true);
        MonitoringLainLain.setName("MonitoringLainLain"); // NOI18N
        MonitoringLainLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MonitoringLainLainKeyPressed(evt);
            }
        });
        FormInput.add(MonitoringLainLain);
        MonitoringLainLain.setBounds(378, 530, 346, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 560, 750, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 560, 750, 1);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel112.setText("IV. STATUS FISIK");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(10, 560, 130, 23);

        jLabel129.setText("Angka ASA :");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(0, 580, 105, 23);

        AngkaAsa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "E" }));
        AngkaAsa.setName("AngkaAsa"); // NOI18N
        AngkaAsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AngkaAsaKeyPressed(evt);
            }
        });
        FormInput.add(AngkaAsa);
        AngkaAsa.setBounds(109, 580, 60, 23);

        jLabel69.setText("Penyulit Pra :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 610, 105, 23);

        PenyulitPra.setFocusTraversalPolicyProvider(true);
        PenyulitPra.setName("PenyulitPra"); // NOI18N
        PenyulitPra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyulitPraKeyPressed(evt);
            }
        });
        FormInput.add(PenyulitPra);
        PenyulitPra.setBounds(109, 610, 615, 23);

        jLabel70.setText("Alergi :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(195, 580, 70, 23);

        Alergi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(269, 580, 80, 23);

        AlergiKeterangan.setFocusTraversalPolicyProvider(true);
        AlergiKeterangan.setName("AlergiKeterangan"); // NOI18N
        AlergiKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(AlergiKeterangan);
        AlergiKeterangan.setBounds(352, 580, 372, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 640, 750, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 640, 750, 1);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("V. PERENCANAAN");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(10, 640, 130, 23);

        LanjutTindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        LanjutTindakan.setName("LanjutTindakan"); // NOI18N
        LanjutTindakan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LanjutTindakanItemStateChanged(evt);
            }
        });
        LanjutTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LanjutTindakanKeyPressed(evt);
            }
        });
        FormInput.add(LanjutTindakan);
        LanjutTindakan.setBounds(133, 660, 80, 23);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("Lanjut Tindakan");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(44, 660, 125, 23);

        Sedasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Sedang", "Dalam", "Lain-lain" }));
        Sedasi.setName("Sedasi"); // NOI18N
        Sedasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SedasiKeyPressed(evt);
            }
        });
        FormInput.add(Sedasi);
        Sedasi.setBounds(123, 690, 95, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Sedasi");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(80, 690, 60, 23);

        SedasiKeterangan.setFocusTraversalPolicyProvider(true);
        SedasiKeterangan.setName("SedasiKeterangan"); // NOI18N
        SedasiKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SedasiKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(SedasiKeterangan);
        SedasiKeterangan.setBounds(221, 690, 200, 23);

        jLabel72.setText("Spinal :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(590, 690, 50, 23);

        Spinal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Spinal.setName("Spinal"); // NOI18N
        Spinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpinalKeyPressed(evt);
            }
        });
        FormInput.add(Spinal);
        Spinal.setBounds(644, 690, 80, 23);

        jLabel73.setText("Epidural :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(433, 690, 60, 23);

        Epidural.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Epidural.setName("Epidural"); // NOI18N
        Epidural.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EpiduralKeyPressed(evt);
            }
        });
        FormInput.add(Epidural);
        Epidural.setBounds(497, 690, 80, 23);

        AnastesiUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        AnastesiUmum.setName("AnastesiUmum"); // NOI18N
        AnastesiUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnastesiUmumKeyPressed(evt);
            }
        });
        FormInput.add(AnastesiUmum);
        AnastesiUmum.setBounds(168, 720, 80, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Anastesi Umum");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(80, 720, 100, 23);

        AnastesiUmumKeterangan.setFocusTraversalPolicyProvider(true);
        AnastesiUmumKeterangan.setName("AnastesiUmumKeterangan"); // NOI18N
        AnastesiUmumKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnastesiUmumKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(AnastesiUmumKeterangan);
        AnastesiUmumKeterangan.setBounds(251, 720, 150, 23);

        jLabel75.setText("Blok Perifer :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(407, 720, 80, 23);

        BlokPerifer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BlokPerifer.setName("BlokPerifer"); // NOI18N
        BlokPerifer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BlokPeriferKeyPressed(evt);
            }
        });
        FormInput.add(BlokPerifer);
        BlokPerifer.setBounds(491, 720, 80, 23);

        BlokPeriferKeterangan.setFocusTraversalPolicyProvider(true);
        BlokPeriferKeterangan.setName("BlokPeriferKeterangan"); // NOI18N
        BlokPeriferKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BlokPeriferKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(BlokPeriferKeterangan);
        BlokPeriferKeterangan.setBounds(574, 720, 150, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Batal Tindakan");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(44, 750, 100, 23);

        BatalTindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BatalTindakan.setName("BatalTindakan"); // NOI18N
        BatalTindakan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BatalTindakanItemStateChanged(evt);
            }
        });
        BatalTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BatalTindakanKeyPressed(evt);
            }
        });
        FormInput.add(BatalTindakan);
        BatalTindakan.setBounds(128, 750, 80, 23);

        BatalTindakanKeterangan.setFocusTraversalPolicyProvider(true);
        BatalTindakanKeterangan.setName("BatalTindakanKeterangan"); // NOI18N
        BatalTindakanKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BatalTindakanKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(BatalTindakanKeterangan);
        BatalTindakanKeterangan.setBounds(211, 750, 513, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 660, 129, 23);

        jLabel78.setText(":");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(0, 750, 124, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(0, 690, 119, 23);

        jLabel80.setText(":");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(0, 720, 164, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Catatan", internalFrame2);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-10-2024" }));
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

        TabRawat.addTab("Data Catatan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnDokterAnestesi);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokterAnastesi.getText().trim().equals("")){
            Valid.textKosong(BtnDokterAnestesi,"Dokter Anastesi");
        }else if(NmDokterBedah.getText().trim().equals("")){
            Valid.textKosong(BtnDokterBedah,"Dokter Bedah");
        }else if(NmPetugasAnastesi.getText().trim().equals("")){
            Valid.textKosong(BtnPetugasAnastesi,"Petugas Anastesi");
        }else if(NmPetugasBedah.getText().trim().equals("")){
            Valid.textKosong(BtnPetugasBedah,"Petugas Bedah");
        }else if(DiagnosaPreBedah.getText().trim().equals("")){
            Valid.textKosong(DiagnosaPreBedah,"Diagnosa Pra Bedah");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan/Jenis Pembedahan");
        }else if(DiagnosaPaskaBedah.getText().trim().equals("")){
            Valid.textKosong(DiagnosaPaskaBedah,"Diagnosa Pasca Bedah");
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
           Valid.pindah(evt,BatalTindakanKeterangan,BtnBatal);
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
                if(KdDokterAnestesi.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
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
        }else if(NmDokterAnastesi.getText().trim().equals("")){
            Valid.textKosong(BtnDokterAnestesi,"Dokter");
        }else if(DiagnosaPreBedah.getText().trim().equals("")){
            Valid.textKosong(DiagnosaPreBedah,"Diagnosa");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Rencana Tindakan");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Rencana Tindakan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokterAnestesi.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
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
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode DPJP Anastesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama DPJP Anastesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Petugas Anastesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Petugas Anastesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode DPJP Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama DPJP Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP Petugas Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Petugas Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Pra Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tindakan/Jenis Pembedahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa Pasca Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Jam</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kesadaran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TD(mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nadi(x/m)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>RR(x/m)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Suhu(°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Saturasi O2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TB(Cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BB(Kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>GD</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rhesus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>HB(gr/dl)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>HT(%)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Leko(ul)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Trombo(ul)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BTCT(mnt)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>GDS(MG/dl)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pre Induksi Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hiopotensi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>TCI</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>CPB</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Ventilator</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Broncoskopy</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Glidescope</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>USG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Stimulator Syaraf</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Teknik & Alat Khusus Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Arteri Line</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Arteri Line</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>CVP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan CVP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>EtCO2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Stetoskop</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIBP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NGT</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>BIS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Cath A Pulmo</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SpO2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kateter Urine</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Temp.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Monitoring Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>ASA</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Alergi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penyulit Pra Anastesi/Sedasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Lanjut Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Sedasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Sedasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Spinal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anestesi Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Anestesi Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Blok Perifer</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Blok Perifer</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Epidural</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Batal Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alasan/Keterangan Batal Tindakan</b></td>"+
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
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4800px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianPreAnestesi.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4800px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN PRE ANESTESI<br><br></font>"+        
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

    private void BtnDokterAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterAnestesiActionPerformed
        i=1;
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterAnestesiActionPerformed

    private void BtnDokterAnestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterAnestesiKeyPressed
        Valid.pindah(evt,DiagnosaPaskaBedah,BtnDokterBedah);
    }//GEN-LAST:event_BtnDokterAnestesiKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,BtnDokterAnestesi,DiagnosaPreBedah);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void MnPenilaianSedasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianSedasiActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString())); 
            
            Valid.MyReportqry("rptCetakCatatanSedasi.jasper","report","::[ Laporan Catatan Anestesi-Sedasi ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,catatan_anestesi_sedasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,"+
                "catatan_anestesi_sedasi.nip_perawat_anestesi,petugasanestesi.nama as petugasanestesi,catatan_anestesi_sedasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                "catatan_anestesi_sedasi.nip_perawat_ok,petugasbedah.nama as petugasbedah,catatan_anestesi_sedasi.diagnosa_pre_bedah,catatan_anestesi_sedasi.tindakan_jenis_pembedahan,"+
                "catatan_anestesi_sedasi.diagnosa_pasca_bedah,catatan_anestesi_sedasi.pre_induksi_jam,catatan_anestesi_sedasi.pre_induksi_kesadaran,catatan_anestesi_sedasi.pre_induksi_td,"+
                "catatan_anestesi_sedasi.pre_induksi_nadi,catatan_anestesi_sedasi.pre_induksi_rr,catatan_anestesi_sedasi.pre_induksi_suhu,catatan_anestesi_sedasi.pre_induksi_o2,"+
                "catatan_anestesi_sedasi.pre_induksi_tb,catatan_anestesi_sedasi.pre_induksi_bb,catatan_anestesi_sedasi.pre_induksi_rhesus,catatan_anestesi_sedasi.pre_induksi_hb,"+
                "catatan_anestesi_sedasi.pre_induksi_ht,catatan_anestesi_sedasi.pre_induksi_leko,catatan_anestesi_sedasi.pre_induksi_trombo,catatan_anestesi_sedasi.pre_induksi_btct,"+
                "catatan_anestesi_sedasi.pre_induksi_gds,catatan_anestesi_sedasi.pre_induksi_lainlain,catatan_anestesi_sedasi.teknik_alat_hiopotensi,catatan_anestesi_sedasi.teknik_alat_tci,"+
                "catatan_anestesi_sedasi.teknik_alat_cpb,catatan_anestesi_sedasi.teknik_alat_ventilasi,catatan_anestesi_sedasi.teknik_alat_broncoskopy,catatan_anestesi_sedasi.teknik_alat_glidescopi,"+
                "catatan_anestesi_sedasi.teknik_alat_usg,catatan_anestesi_sedasi.teknik_alat_stimulator_saraf,catatan_anestesi_sedasi.teknik_alat_lainlain,catatan_anestesi_sedasi.monitoring_ekg,"+
                "catatan_anestesi_sedasi.monitoring_ekg_keterangan,catatan_anestesi_sedasi.monitoring_arteri,catatan_anestesi_sedasi.monitoring_arteri_keterangan,catatan_anestesi_sedasi.monitoring_cvp,"+
                "catatan_anestesi_sedasi.monitoring_cvp_keterangan,catatan_anestesi_sedasi.monitoring_etco,catatan_anestesi_sedasi.monitoring_stetoskop,catatan_anestesi_sedasi.monitoring_nibp,"+
                "catatan_anestesi_sedasi.monitoring_ngt,catatan_anestesi_sedasi.monitoring_bis,catatan_anestesi_sedasi.monitoring_cath_a_pulmo,catatan_anestesi_sedasi.monitoring_spo2,"+
                "catatan_anestesi_sedasi.monitoring_kateter,catatan_anestesi_sedasi.monitoring_temp,catatan_anestesi_sedasi.monitoring_lainlain,catatan_anestesi_sedasi.status_fisik_asa,"+
                "catatan_anestesi_sedasi.status_fisik_alergi,catatan_anestesi_sedasi.status_fisik_alergi_keterangan,catatan_anestesi_sedasi.status_fisik_penyulit_sedasi,catatan_anestesi_sedasi.perencanaan_lanjut,"+
                "catatan_anestesi_sedasi.perencanaan_lanjut_sedasi,catatan_anestesi_sedasi.perencanaan_lanjut_sedasi_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_spinal,"+
                "catatan_anestesi_sedasi.perencanaan_lanjut_anestesi_umum,catatan_anestesi_sedasi.perencanaan_lanjut_anestesi_umum_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_blok_perifer,"+
                "catatan_anestesi_sedasi.perencanaan_lanjut_blok_perifer_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_epidural,catatan_anestesi_sedasi.perencanaan_batal,"+
                "catatan_anestesi_sedasi.perencanaan_batal_alasan,catatan_anestesi_sedasi.tanggal,pasien.gol_darah from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join catatan_anestesi_sedasi on reg_periksa.no_rawat=catatan_anestesi_sedasi.no_rawat "+
                "inner join dokter as dokteranestesi on catatan_anestesi_sedasi.kd_dokter_anestesi=dokteranestesi.kd_dokter "+
                "inner join dokter as dokterbedah on catatan_anestesi_sedasi.kd_dokter_bedah=dokterbedah.kd_dokter "+
                "inner join petugas as petugasanestesi on catatan_anestesi_sedasi.nip_perawat_anestesi=petugasanestesi.nip "+
                "inner join petugas as petugasbedah on catatan_anestesi_sedasi.nip_perawat_ok=petugasbedah.nip where catatan_anestesi_sedasi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' "+
                "and catatan_anestesi_sedasi.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianSedasiActionPerformed

    private void DiagnosaPreBedahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaPreBedahKeyPressed
        Valid.pindah(evt,TglAsuhan,Tindakan);
    }//GEN-LAST:event_DiagnosaPreBedahKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,DiagnosaPreBedah,DiagnosaPaskaBedah);
    }//GEN-LAST:event_TindakanKeyPressed

    private void DiagnosaPaskaBedahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaPaskaBedahKeyPressed
        Valid.pindah(evt,Tindakan,BtnDokterAnestesi);
    }//GEN-LAST:event_DiagnosaPaskaBedahKeyPressed

    private void BtnDokterBedahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterBedahActionPerformed
        i=2;
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterBedahActionPerformed

    private void BtnDokterBedahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterBedahKeyPressed
        Valid.pindah(evt,DiagnosaPaskaBedah,BtnPetugasAnastesi);
    }//GEN-LAST:event_BtnDokterBedahKeyPressed

    private void BtnPetugasAnastesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasAnastesiActionPerformed
        i=1;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasAnastesiActionPerformed

    private void BtnPetugasAnastesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasAnastesiKeyPressed
        Valid.pindah(evt,BtnDokterBedah,BtnPetugasBedah);
    }//GEN-LAST:event_BtnPetugasAnastesiKeyPressed

    private void BtnPetugasBedahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasBedahActionPerformed
        i=2;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasBedahActionPerformed

    private void BtnPetugasBedahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasBedahKeyPressed
        Valid.pindah(evt,BtnPetugasAnastesi,Jam);
    }//GEN-LAST:event_BtnPetugasBedahKeyPressed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,BtnPetugasBedah,Kesadaran);
    }//GEN-LAST:event_JamKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed
        Valid.pindah(evt,Kesadaran,Nadi);
    }//GEN-LAST:event_TDKeyPressed

    private void SaturasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaturasiKeyPressed
        Valid.pindah(evt,Suhu,TB);
    }//GEN-LAST:event_SaturasiKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        Valid.pindah(evt,Jam,TD);
    }//GEN-LAST:event_KesadaranKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,Saturasi);
    }//GEN-LAST:event_SuhuKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,Saturasi,BB);
    }//GEN-LAST:event_TBKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TB,Rhesus);
    }//GEN-LAST:event_BBKeyPressed

    private void RhesusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RhesusKeyPressed
        Valid.pindah(evt,BB,HB);
    }//GEN-LAST:event_RhesusKeyPressed

    private void HBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HBKeyPressed
        Valid.pindah(evt,Rhesus,HT);
    }//GEN-LAST:event_HBKeyPressed

    private void HTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HTKeyPressed
        Valid.pindah(evt,HB,Leko);
    }//GEN-LAST:event_HTKeyPressed

    private void LekoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LekoKeyPressed
        Valid.pindah(evt,HT,Trombo);
    }//GEN-LAST:event_LekoKeyPressed

    private void TromboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TromboKeyPressed
        Valid.pindah(evt,Leko,BTCT);
    }//GEN-LAST:event_TromboKeyPressed

    private void BTCTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BTCTKeyPressed
        Valid.pindah(evt,Trombo,GDS);
    }//GEN-LAST:event_BTCTKeyPressed

    private void GDSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GDSKeyPressed
        Valid.pindah(evt,BTCT,LainLainPrInduksi);
    }//GEN-LAST:event_GDSKeyPressed

    private void LainLainPrInduksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainLainPrInduksiKeyPressed
        Valid.pindah(evt,GDS,TCI);
    }//GEN-LAST:event_LainLainPrInduksiKeyPressed

    private void TCIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCIKeyPressed
        Valid.pindah(evt,LainLainPrInduksi,Glidescope);
    }//GEN-LAST:event_TCIKeyPressed

    private void GlidescopeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GlidescopeKeyPressed
        Valid.pindah(evt,TCI,StimulatorSaraf);
    }//GEN-LAST:event_GlidescopeKeyPressed

    private void StimulatorSarafKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StimulatorSarafKeyPressed
        Valid.pindah(evt,Glidescope,CPB);
    }//GEN-LAST:event_StimulatorSarafKeyPressed

    private void CPBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CPBKeyPressed
        Valid.pindah(evt,StimulatorSaraf,USG);
    }//GEN-LAST:event_CPBKeyPressed

    private void USGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_USGKeyPressed
        Valid.pindah(evt,CPB,Ventilator);
    }//GEN-LAST:event_USGKeyPressed

    private void VentilatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VentilatorKeyPressed
        Valid.pindah(evt,USG,Broncoskopy);
    }//GEN-LAST:event_VentilatorKeyPressed

    private void BroncoskopyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BroncoskopyKeyPressed
        Valid.pindah(evt,Ventilator,Hiopotensi);
    }//GEN-LAST:event_BroncoskopyKeyPressed

    private void HiopotensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HiopotensiKeyPressed
        Valid.pindah(evt,Broncoskopy,TeknikAlatLainnya);
    }//GEN-LAST:event_HiopotensiKeyPressed

    private void TeknikAlatLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeknikAlatLainnyaKeyPressed
        Valid.pindah(evt,Hiopotensi,Etco2);
    }//GEN-LAST:event_TeknikAlatLainnyaKeyPressed

    private void Etco2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Etco2KeyPressed
        Valid.pindah(evt,TeknikAlatLainnya,Stetoskop);
    }//GEN-LAST:event_Etco2KeyPressed

    private void SpO2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpO2KeyPressed
        Valid.pindah(evt,NGT,NIBP);
    }//GEN-LAST:event_SpO2KeyPressed

    private void StetoskopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StetoskopKeyPressed
        Valid.pindah(evt,Etco2,CathAPulmo);
    }//GEN-LAST:event_StetoskopKeyPressed

    private void NIBPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIBPKeyPressed
        Valid.pindah(evt,SpO2,KateterUrine);
    }//GEN-LAST:event_NIBPKeyPressed

    private void CathAPulmoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CathAPulmoKeyPressed
        Valid.pindah(evt,Stetoskop,NGT);
    }//GEN-LAST:event_CathAPulmoKeyPressed

    private void KateterUrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KateterUrineKeyPressed
        Valid.pindah(evt,NIBP,BIS);
    }//GEN-LAST:event_KateterUrineKeyPressed

    private void NGTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NGTKeyPressed
        Valid.pindah(evt,CathAPulmo,SpO2);
    }//GEN-LAST:event_NGTKeyPressed

    private void BISKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BISKeyPressed
        Valid.pindah(evt,KateterUrine,CVP);
    }//GEN-LAST:event_BISKeyPressed

    private void CVPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CVPKeyPressed
        Valid.pindah(evt,BIS,CVPKeterangan);
    }//GEN-LAST:event_CVPKeyPressed

    private void CVPKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CVPKeteranganKeyPressed
        Valid.pindah(evt,CVP,ArteriLine);
    }//GEN-LAST:event_CVPKeteranganKeyPressed

    private void ArteriLineKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ArteriLineKeteranganKeyPressed
        Valid.pindah(evt,ArteriLine,Temp);
    }//GEN-LAST:event_ArteriLineKeteranganKeyPressed

    private void ArteriLineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ArteriLineKeyPressed
        Valid.pindah(evt,CVPKeterangan,ArteriLineKeterangan);
    }//GEN-LAST:event_ArteriLineKeyPressed

    private void TempKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempKeyPressed
        Valid.pindah(evt,ArteriLineKeterangan,EKGLead);
    }//GEN-LAST:event_TempKeyPressed

    private void EKGLeadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EKGLeadKeyPressed
        Valid.pindah(evt,Temp,EKGLeadKeterangan);
    }//GEN-LAST:event_EKGLeadKeyPressed

    private void EKGLeadKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EKGLeadKeteranganKeyPressed
        Valid.pindah(evt,EKGLead,MonitoringLainLain);
    }//GEN-LAST:event_EKGLeadKeteranganKeyPressed

    private void MonitoringLainLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MonitoringLainLainKeyPressed
        Valid.pindah(evt,EKGLeadKeterangan,AngkaAsa);
    }//GEN-LAST:event_MonitoringLainLainKeyPressed

    private void AngkaAsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AngkaAsaKeyPressed
        Valid.pindah(evt,MonitoringLainLain,Alergi);
    }//GEN-LAST:event_AngkaAsaKeyPressed

    private void PenyulitPraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyulitPraKeyPressed
        Valid.pindah(evt,AlergiKeterangan,LanjutTindakan);
    }//GEN-LAST:event_PenyulitPraKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,AngkaAsa,AlergiKeterangan);
    }//GEN-LAST:event_AlergiKeyPressed

    private void AlergiKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeteranganKeyPressed
        Valid.pindah(evt,Alergi,PenyulitPra);
    }//GEN-LAST:event_AlergiKeteranganKeyPressed

    private void LanjutTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LanjutTindakanKeyPressed
        Valid.pindah(evt,PenyulitPra,Sedasi);
    }//GEN-LAST:event_LanjutTindakanKeyPressed

    private void SedasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SedasiKeyPressed
        Valid.pindah(evt,LanjutTindakan,SedasiKeterangan);
    }//GEN-LAST:event_SedasiKeyPressed

    private void SedasiKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SedasiKeteranganKeyPressed
        Valid.pindah(evt,Sedasi,Epidural);
    }//GEN-LAST:event_SedasiKeteranganKeyPressed

    private void SpinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpinalKeyPressed
        Valid.pindah(evt,Epidural,AnastesiUmum);
    }//GEN-LAST:event_SpinalKeyPressed

    private void EpiduralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EpiduralKeyPressed
        Valid.pindah(evt,SedasiKeterangan,Spinal);
    }//GEN-LAST:event_EpiduralKeyPressed

    private void AnastesiUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnastesiUmumKeyPressed
        Valid.pindah(evt,Spinal,AnastesiUmumKeterangan);
    }//GEN-LAST:event_AnastesiUmumKeyPressed

    private void AnastesiUmumKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnastesiUmumKeteranganKeyPressed
        Valid.pindah(evt,AnastesiUmum,BlokPerifer);
    }//GEN-LAST:event_AnastesiUmumKeteranganKeyPressed

    private void BlokPeriferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BlokPeriferKeyPressed
        Valid.pindah(evt,AnastesiUmumKeterangan,BlokPeriferKeterangan);
    }//GEN-LAST:event_BlokPeriferKeyPressed

    private void BlokPeriferKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BlokPeriferKeteranganKeyPressed
        Valid.pindah(evt,BlokPerifer,BatalTindakan);
    }//GEN-LAST:event_BlokPeriferKeteranganKeyPressed

    private void BatalTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BatalTindakanKeyPressed
        Valid.pindah(evt,BlokPeriferKeterangan,BatalTindakanKeterangan);
    }//GEN-LAST:event_BatalTindakanKeyPressed

    private void BatalTindakanKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BatalTindakanKeteranganKeyPressed
        Valid.pindah(evt,BatalTindakan,BtnSimpan);
    }//GEN-LAST:event_BatalTindakanKeteranganKeyPressed

    private void LanjutTindakanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LanjutTindakanItemStateChanged
        if(LanjutTindakan.getSelectedIndex()==1){
            Sedasi.setSelectedIndex(0);
            SedasiKeterangan.setText("");
            Epidural.setSelectedIndex(0);
            Spinal.setSelectedIndex(0);
            AnastesiUmum.setSelectedIndex(0);
            AnastesiUmumKeterangan.setText("");
            BlokPerifer.setSelectedIndex(0);
            BlokPeriferKeterangan.setText("");
        }else if(LanjutTindakan.getSelectedIndex()==0){
            BatalTindakan.setSelectedIndex(0);
            BatalTindakanKeterangan.setText("");
        }
    }//GEN-LAST:event_LanjutTindakanItemStateChanged

    private void BatalTindakanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BatalTindakanItemStateChanged
        if(BatalTindakan.getSelectedIndex()==1){
            LanjutTindakan.setSelectedIndex(1);
            Sedasi.setSelectedIndex(0);
            SedasiKeterangan.setText("");
            Epidural.setSelectedIndex(0);
            Spinal.setSelectedIndex(0);
            AnastesiUmum.setSelectedIndex(0);
            AnastesiUmumKeterangan.setText("");
            BlokPerifer.setSelectedIndex(0);
            BlokPeriferKeterangan.setText("");
        }else if(BatalTindakan.getSelectedIndex()==0){
            BatalTindakanKeterangan.setText("");
        }
    }//GEN-LAST:event_BatalTindakanItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMCatatanAnastesiSedasi dialog = new RMCatatanAnastesiSedasi(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Alergi;
    private widget.TextBox AlergiKeterangan;
    private widget.ComboBox AnastesiUmum;
    private widget.TextBox AnastesiUmumKeterangan;
    private widget.ComboBox AngkaAsa;
    private widget.ComboBox ArteriLine;
    private widget.TextBox ArteriLineKeterangan;
    private widget.TextBox BB;
    private widget.ComboBox BIS;
    private widget.TextBox BTCT;
    private widget.ComboBox BatalTindakan;
    private widget.TextBox BatalTindakanKeterangan;
    private widget.ComboBox BlokPerifer;
    private widget.TextBox BlokPeriferKeterangan;
    private widget.ComboBox Broncoskopy;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokterAnestesi;
    private widget.Button BtnDokterBedah;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugasAnastesi;
    private widget.Button BtnPetugasBedah;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CPB;
    private widget.ComboBox CVP;
    private widget.TextBox CVPKeterangan;
    private widget.ComboBox CathAPulmo;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaPaskaBedah;
    private widget.TextBox DiagnosaPreBedah;
    private widget.ComboBox EKGLead;
    private widget.TextBox EKGLeadKeterangan;
    private widget.ComboBox Epidural;
    private widget.ComboBox Etco2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GD;
    private widget.TextBox GDS;
    private widget.ComboBox Glidescope;
    private widget.TextBox HB;
    private widget.TextBox HT;
    private widget.ComboBox Hiopotensi;
    private widget.TextBox Jam;
    private widget.TextBox Jk;
    private widget.ComboBox KateterUrine;
    private widget.TextBox KdDokterAnestesi;
    private widget.TextBox KdDokterBedah;
    private widget.TextBox KdPetugasAnastesi;
    private widget.TextBox KdPetugasBedah;
    private widget.ComboBox Kesadaran;
    private widget.Label LCount;
    private widget.TextBox LainLainPrInduksi;
    private widget.ComboBox LanjutTindakan;
    private widget.TextBox Leko;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianSedasi;
    private widget.TextBox MonitoringLainLain;
    private widget.ComboBox NGT;
    private widget.ComboBox NIBP;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokterAnastesi;
    private widget.TextBox NmDokterBedah;
    private widget.TextBox NmPetugasAnastesi;
    private widget.TextBox NmPetugasBedah;
    private widget.TextBox PenyulitPra;
    private widget.TextBox RR;
    private widget.ComboBox Rhesus;
    private widget.TextBox Saturasi;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Sedasi;
    private widget.TextBox SedasiKeterangan;
    private widget.ComboBox SpO2;
    private widget.ComboBox Spinal;
    private widget.ComboBox Stetoskop;
    private widget.ComboBox StimulatorSaraf;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.ComboBox TCI;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TeknikAlatLainnya;
    private widget.ComboBox Temp;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.TextBox Trombo;
    private widget.ComboBox USG;
    private widget.ComboBox Ventilator;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel110;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel12;
    private widget.Label jLabel129;
    private widget.Label jLabel13;
    private widget.Label jLabel130;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,catatan_anestesi_sedasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,"+
                        "catatan_anestesi_sedasi.nip_perawat_anestesi,petugasanestesi.nama as petugasanestesi,catatan_anestesi_sedasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                        "catatan_anestesi_sedasi.nip_perawat_ok,petugasbedah.nama as petugasbedah,catatan_anestesi_sedasi.diagnosa_pre_bedah,catatan_anestesi_sedasi.tindakan_jenis_pembedahan,"+
                        "catatan_anestesi_sedasi.diagnosa_pasca_bedah,catatan_anestesi_sedasi.pre_induksi_jam,catatan_anestesi_sedasi.pre_induksi_kesadaran,catatan_anestesi_sedasi.pre_induksi_td,"+
                        "catatan_anestesi_sedasi.pre_induksi_nadi,catatan_anestesi_sedasi.pre_induksi_rr,catatan_anestesi_sedasi.pre_induksi_suhu,catatan_anestesi_sedasi.pre_induksi_o2,"+
                        "catatan_anestesi_sedasi.pre_induksi_tb,catatan_anestesi_sedasi.pre_induksi_bb,catatan_anestesi_sedasi.pre_induksi_rhesus,catatan_anestesi_sedasi.pre_induksi_hb,"+
                        "catatan_anestesi_sedasi.pre_induksi_ht,catatan_anestesi_sedasi.pre_induksi_leko,catatan_anestesi_sedasi.pre_induksi_trombo,catatan_anestesi_sedasi.pre_induksi_btct,"+
                        "catatan_anestesi_sedasi.pre_induksi_gds,catatan_anestesi_sedasi.pre_induksi_lainlain,catatan_anestesi_sedasi.teknik_alat_hiopotensi,catatan_anestesi_sedasi.teknik_alat_tci,"+
                        "catatan_anestesi_sedasi.teknik_alat_cpb,catatan_anestesi_sedasi.teknik_alat_ventilasi,catatan_anestesi_sedasi.teknik_alat_broncoskopy,catatan_anestesi_sedasi.teknik_alat_glidescopi,"+
                        "catatan_anestesi_sedasi.teknik_alat_usg,catatan_anestesi_sedasi.teknik_alat_stimulator_saraf,catatan_anestesi_sedasi.teknik_alat_lainlain,catatan_anestesi_sedasi.monitoring_ekg,"+
                        "catatan_anestesi_sedasi.monitoring_ekg_keterangan,catatan_anestesi_sedasi.monitoring_arteri,catatan_anestesi_sedasi.monitoring_arteri_keterangan,catatan_anestesi_sedasi.monitoring_cvp,"+
                        "catatan_anestesi_sedasi.monitoring_cvp_keterangan,catatan_anestesi_sedasi.monitoring_etco,catatan_anestesi_sedasi.monitoring_stetoskop,catatan_anestesi_sedasi.monitoring_nibp,"+
                        "catatan_anestesi_sedasi.monitoring_ngt,catatan_anestesi_sedasi.monitoring_bis,catatan_anestesi_sedasi.monitoring_cath_a_pulmo,catatan_anestesi_sedasi.monitoring_spo2,"+
                        "catatan_anestesi_sedasi.monitoring_kateter,catatan_anestesi_sedasi.monitoring_temp,catatan_anestesi_sedasi.monitoring_lainlain,catatan_anestesi_sedasi.status_fisik_asa,"+
                        "catatan_anestesi_sedasi.status_fisik_alergi,catatan_anestesi_sedasi.status_fisik_alergi_keterangan,catatan_anestesi_sedasi.status_fisik_penyulit_sedasi,catatan_anestesi_sedasi.perencanaan_lanjut,"+
                        "catatan_anestesi_sedasi.perencanaan_lanjut_sedasi,catatan_anestesi_sedasi.perencanaan_lanjut_sedasi_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_spinal,"+
                        "catatan_anestesi_sedasi.perencanaan_lanjut_anestesi_umum,catatan_anestesi_sedasi.perencanaan_lanjut_anestesi_umum_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_blok_perifer,"+
                        "catatan_anestesi_sedasi.perencanaan_lanjut_blok_perifer_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_epidural,catatan_anestesi_sedasi.perencanaan_batal,"+
                        "catatan_anestesi_sedasi.perencanaan_batal_alasan,catatan_anestesi_sedasi.tanggal,pasien.gol_darah from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join catatan_anestesi_sedasi on reg_periksa.no_rawat=catatan_anestesi_sedasi.no_rawat "+
                        "inner join dokter as dokteranestesi on catatan_anestesi_sedasi.kd_dokter_anestesi=dokteranestesi.kd_dokter "+
                        "inner join dokter as dokterbedah on catatan_anestesi_sedasi.kd_dokter_bedah=dokterbedah.kd_dokter "+
                        "inner join petugas as petugasanestesi on catatan_anestesi_sedasi.nip_perawat_anestesi=petugasanestesi.nip "+
                        "inner join petugas as petugasbedah on catatan_anestesi_sedasi.nip_perawat_ok=petugasbedah.nip where "+
                        "catatan_anestesi_sedasi.tanggal between ? and ? order by catatan_anestesi_sedasi.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,catatan_anestesi_sedasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,"+
                        "catatan_anestesi_sedasi.nip_perawat_anestesi,petugasanestesi.nama as petugasanestesi,catatan_anestesi_sedasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                        "catatan_anestesi_sedasi.nip_perawat_ok,petugasbedah.nama as petugasbedah,catatan_anestesi_sedasi.diagnosa_pre_bedah,catatan_anestesi_sedasi.tindakan_jenis_pembedahan,"+
                        "catatan_anestesi_sedasi.diagnosa_pasca_bedah,catatan_anestesi_sedasi.pre_induksi_jam,catatan_anestesi_sedasi.pre_induksi_kesadaran,catatan_anestesi_sedasi.pre_induksi_td,"+
                        "catatan_anestesi_sedasi.pre_induksi_nadi,catatan_anestesi_sedasi.pre_induksi_rr,catatan_anestesi_sedasi.pre_induksi_suhu,catatan_anestesi_sedasi.pre_induksi_o2,"+
                        "catatan_anestesi_sedasi.pre_induksi_tb,catatan_anestesi_sedasi.pre_induksi_bb,catatan_anestesi_sedasi.pre_induksi_rhesus,catatan_anestesi_sedasi.pre_induksi_hb,"+
                        "catatan_anestesi_sedasi.pre_induksi_ht,catatan_anestesi_sedasi.pre_induksi_leko,catatan_anestesi_sedasi.pre_induksi_trombo,catatan_anestesi_sedasi.pre_induksi_btct,"+
                        "catatan_anestesi_sedasi.pre_induksi_gds,catatan_anestesi_sedasi.pre_induksi_lainlain,catatan_anestesi_sedasi.teknik_alat_hiopotensi,catatan_anestesi_sedasi.teknik_alat_tci,"+
                        "catatan_anestesi_sedasi.teknik_alat_cpb,catatan_anestesi_sedasi.teknik_alat_ventilasi,catatan_anestesi_sedasi.teknik_alat_broncoskopy,catatan_anestesi_sedasi.teknik_alat_glidescopi,"+
                        "catatan_anestesi_sedasi.teknik_alat_usg,catatan_anestesi_sedasi.teknik_alat_stimulator_saraf,catatan_anestesi_sedasi.teknik_alat_lainlain,catatan_anestesi_sedasi.monitoring_ekg,"+
                        "catatan_anestesi_sedasi.monitoring_ekg_keterangan,catatan_anestesi_sedasi.monitoring_arteri,catatan_anestesi_sedasi.monitoring_arteri_keterangan,catatan_anestesi_sedasi.monitoring_cvp,"+
                        "catatan_anestesi_sedasi.monitoring_cvp_keterangan,catatan_anestesi_sedasi.monitoring_etco,catatan_anestesi_sedasi.monitoring_stetoskop,catatan_anestesi_sedasi.monitoring_nibp,"+
                        "catatan_anestesi_sedasi.monitoring_ngt,catatan_anestesi_sedasi.monitoring_bis,catatan_anestesi_sedasi.monitoring_cath_a_pulmo,catatan_anestesi_sedasi.monitoring_spo2,"+
                        "catatan_anestesi_sedasi.monitoring_kateter,catatan_anestesi_sedasi.monitoring_temp,catatan_anestesi_sedasi.monitoring_lainlain,catatan_anestesi_sedasi.status_fisik_asa,"+
                        "catatan_anestesi_sedasi.status_fisik_alergi,catatan_anestesi_sedasi.status_fisik_alergi_keterangan,catatan_anestesi_sedasi.status_fisik_penyulit_sedasi,catatan_anestesi_sedasi.perencanaan_lanjut,"+
                        "catatan_anestesi_sedasi.perencanaan_lanjut_sedasi,catatan_anestesi_sedasi.perencanaan_lanjut_sedasi_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_spinal,"+
                        "catatan_anestesi_sedasi.perencanaan_lanjut_anestesi_umum,catatan_anestesi_sedasi.perencanaan_lanjut_anestesi_umum_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_blok_perifer,"+
                        "catatan_anestesi_sedasi.perencanaan_lanjut_blok_perifer_keterangan,catatan_anestesi_sedasi.perencanaan_lanjut_epidural,catatan_anestesi_sedasi.perencanaan_batal,"+
                        "catatan_anestesi_sedasi.perencanaan_batal_alasan,catatan_anestesi_sedasi.tanggal,pasien.gol_darah from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join catatan_anestesi_sedasi on reg_periksa.no_rawat=catatan_anestesi_sedasi.no_rawat "+
                        "inner join dokter as dokteranestesi on catatan_anestesi_sedasi.kd_dokter_anestesi=dokteranestesi.kd_dokter "+
                        "inner join dokter as dokterbedah on catatan_anestesi_sedasi.kd_dokter_bedah=dokterbedah.kd_dokter "+
                        "inner join petugas as petugasanestesi on catatan_anestesi_sedasi.nip_perawat_anestesi=petugasanestesi.nip "+
                        "inner join petugas as petugasbedah on catatan_anestesi_sedasi.nip_perawat_ok=petugasbedah.nip where "+
                        "catatan_anestesi_sedasi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "catatan_anestesi_sedasi.kd_dokter_anestesi like ? or dokterbedah.nm_dokter like ?) order by catatan_anestesi_sedasi.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_dokter_anestesi"),rs.getString("dokteranestesi"),rs.getString("nip_perawat_anestesi"),rs.getString("petugasanestesi"),
                        rs.getString("kd_dokter_bedah"),rs.getString("dokterbedah"),rs.getString("nip_perawat_ok"),rs.getString("petugasbedah"),rs.getString("tanggal"),rs.getString("diagnosa_pre_bedah"),rs.getString("tindakan_jenis_pembedahan"),rs.getString("diagnosa_pasca_bedah"),rs.getString("pre_induksi_jam"),
                        rs.getString("pre_induksi_kesadaran"),rs.getString("pre_induksi_td"),rs.getString("pre_induksi_nadi"),rs.getString("pre_induksi_rr"),rs.getString("pre_induksi_suhu"),rs.getString("pre_induksi_o2"),rs.getString("pre_induksi_tb"),rs.getString("pre_induksi_bb"),rs.getString("gol_darah"),
                        rs.getString("pre_induksi_rhesus"),rs.getString("pre_induksi_hb"),rs.getString("pre_induksi_ht"),rs.getString("pre_induksi_leko"),rs.getString("pre_induksi_trombo"),rs.getString("pre_induksi_btct"),rs.getString("pre_induksi_gds"),rs.getString("pre_induksi_lainlain"),
                        rs.getString("teknik_alat_hiopotensi"),rs.getString("teknik_alat_tci"),rs.getString("teknik_alat_cpb"),rs.getString("teknik_alat_ventilasi"),rs.getString("teknik_alat_broncoskopy"),rs.getString("teknik_alat_glidescopi"),rs.getString("teknik_alat_usg"),
                        rs.getString("teknik_alat_stimulator_saraf"),rs.getString("teknik_alat_lainlain"),rs.getString("monitoring_ekg"),rs.getString("monitoring_ekg_keterangan"),rs.getString("monitoring_arteri"),rs.getString("monitoring_arteri_keterangan"),rs.getString("monitoring_cvp"),
                        rs.getString("monitoring_cvp_keterangan"),rs.getString("monitoring_etco"),rs.getString("monitoring_stetoskop"),rs.getString("monitoring_nibp"),rs.getString("monitoring_ngt"),rs.getString("monitoring_bis"),rs.getString("monitoring_cath_a_pulmo"),rs.getString("monitoring_spo2"),
                        rs.getString("monitoring_kateter"),rs.getString("monitoring_temp"),rs.getString("monitoring_lainlain"),rs.getString("status_fisik_asa"),rs.getString("status_fisik_alergi"),rs.getString("status_fisik_alergi_keterangan"),rs.getString("status_fisik_penyulit_sedasi"),
                        rs.getString("perencanaan_lanjut"),rs.getString("perencanaan_lanjut_sedasi"),rs.getString("perencanaan_lanjut_sedasi_keterangan"),rs.getString("perencanaan_lanjut_spinal"),rs.getString("perencanaan_lanjut_anestesi_umum"),rs.getString("perencanaan_lanjut_anestesi_umum_keterangan"),
                        rs.getString("perencanaan_lanjut_blok_perifer"),rs.getString("perencanaan_lanjut_blok_perifer_keterangan"),rs.getString("perencanaan_lanjut_epidural"),rs.getString("perencanaan_batal"),rs.getString("perencanaan_batal_alasan")
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
        DiagnosaPreBedah.setText("");
        Tindakan.setText("");
        DiagnosaPaskaBedah.setText("");
        KdPetugasAnastesi.setText("");
        NmPetugasAnastesi.setText("");
        KdDokterBedah.setText("");
        NmDokterBedah.setText("");
        KdPetugasBedah.setText("");
        NmPetugasBedah.setText("");
        Jam.setText("");
        Kesadaran.setSelectedIndex(0);
        TD.setText("");
        Nadi.setText("");
        RR.setText("");
        Suhu.setText("");
        Saturasi.setText("");
        TB.setText("");
        BB.setText("");
        Rhesus.setSelectedIndex(0);
        HB.setText("");
        HT.setText("");
        Leko.setText("");
        Trombo.setText("");
        BTCT.setText("");
        GDS.setText("");
        LainLainPrInduksi.setText("");
        TCI.setSelectedIndex(0);
        Glidescope.setSelectedIndex(0);
        StimulatorSaraf.setSelectedIndex(0);
        CPB.setSelectedIndex(0);
        USG.setSelectedIndex(0);
        Ventilator.setSelectedIndex(0);
        Broncoskopy.setSelectedIndex(0);
        Hiopotensi.setSelectedIndex(0);
        TeknikAlatLainnya.setText("");
        Etco2.setSelectedIndex(0);
        Stetoskop.setSelectedIndex(0);
        CathAPulmo.setSelectedIndex(0);
        NGT.setSelectedIndex(0);
        SpO2.setSelectedIndex(0);
        NIBP.setSelectedIndex(0);
        KateterUrine.setSelectedIndex(0);
        BIS.setSelectedIndex(0);
        CVP.setSelectedIndex(0);
        CVPKeterangan.setText("");
        ArteriLine.setSelectedIndex(0);
        ArteriLineKeterangan.setText("");
        Temp.setSelectedIndex(0);
        EKGLead.setSelectedIndex(0);
        EKGLeadKeterangan.setText("");
        MonitoringLainLain.setText("");
        AngkaAsa.setSelectedIndex(0);
        Alergi.setSelectedIndex(0);
        AlergiKeterangan.setText("");
        PenyulitPra.setText("");
        LanjutTindakan.setSelectedIndex(0);
        Sedasi.setSelectedIndex(0);
        SedasiKeterangan.setText("");
        Epidural.setSelectedIndex(0);
        Spinal.setSelectedIndex(0);
        AnastesiUmum.setSelectedIndex(0);
        AnastesiUmumKeterangan.setText("");
        BlokPerifer.setSelectedIndex(0);
        BlokPeriferKeterangan.setText("");
        BatalTindakan.setSelectedIndex(0);
        BatalTindakanKeterangan.setText("");
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        DiagnosaPreBedah.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString().replaceAll("L","Laki-laki").replaceAll("P","Perempuan"));
            KdDokterAnestesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NmDokterAnastesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KdPetugasAnastesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NmPetugasAnastesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KdDokterBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            NmDokterBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KdPetugasBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NmPetugasBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            DiagnosaPreBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            DiagnosaPaskaBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Jam.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Kesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Saturasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            TB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            GD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Rhesus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            HB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            HT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Leko.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Trombo.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            BTCT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            GDS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            LainLainPrInduksi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Hiopotensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            TCI.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            CPB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Ventilator.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Broncoskopy.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Glidescope.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            USG.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            StimulatorSaraf.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            TeknikAlatLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            EKGLead.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            EKGLeadKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            ArteriLine.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            ArteriLineKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            CVP.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            CVPKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Etco2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Stetoskop.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            NIBP.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            NGT.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            BIS.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            CathAPulmo.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            SpO2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            KateterUrine.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Temp.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            MonitoringLainLain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            AngkaAsa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            Alergi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            AlergiKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            PenyulitPra.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            LanjutTindakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            Sedasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            SedasiKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            Spinal.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            AnastesiUmum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            AnastesiUmumKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            BlokPerifer.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            BlokPeriferKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Epidural.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            BatalTindakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            BatalTindakanKeterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-laki','Perempuan') as jk,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.gol_darah from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
                    GD.setText(rs.getString("gol_darah"));
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
        BtnSimpan.setEnabled(akses.getcatatan_anestesi_sedasi());
        BtnHapus.setEnabled(akses.getcatatan_anestesi_sedasi());
        BtnEdit.setEnabled(akses.getcatatan_anestesi_sedasi());
        BtnEdit.setEnabled(akses.getcatatan_anestesi_sedasi());
        if(akses.getjml2()>=1){
            KdDokterAnestesi.setEditable(false);
            BtnDokterAnestesi.setEnabled(false);
            KdDokterAnestesi.setText(akses.getkode());
            NmDokterAnastesi.setText(dokter.tampil3(KdDokterAnestesi.getText()));
            if(NmDokterAnastesi.getText().equals("")){
                KdDokterAnestesi.setText("");
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
        if(Sequel.queryu2tf("delete from catatan_anestesi_sedasi where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("catatan_anestesi_sedasi","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,kd_dokter_bedah=?,kd_dokter_anestesi=?,diagnosa_pre_bedah=?,tindakan_jenis_pembedahan=?,diagnosa_pasca_bedah=?,"+
                "pre_induksi_jam=?,pre_induksi_kesadaran=?,pre_induksi_td=?,pre_induksi_nadi=?,pre_induksi_rr=?,pre_induksi_suhu=?,pre_induksi_o2=?,pre_induksi_tb=?,pre_induksi_bb=?,pre_induksi_rhesus=?,pre_induksi_hb=?,"+
                "pre_induksi_ht=?,pre_induksi_leko=?,pre_induksi_trombo=?,pre_induksi_btct=?,pre_induksi_gds=?,pre_induksi_lainlain=?,teknik_alat_hiopotensi=?,teknik_alat_tci=?,teknik_alat_cpb=?,teknik_alat_ventilasi=?,"+
                "teknik_alat_broncoskopy=?,teknik_alat_glidescopi=?,teknik_alat_usg=?,teknik_alat_stimulator_saraf=?,teknik_alat_lainlain=?,monitoring_ekg=?,monitoring_ekg_keterangan=?,monitoring_arteri=?,"+
                "monitoring_arteri_keterangan=?,monitoring_cvp=?,monitoring_cvp_keterangan=?,monitoring_etco=?,monitoring_stetoskop=?,monitoring_nibp=?,monitoring_ngt=?,monitoring_bis=?,monitoring_cath_a_pulmo=?,"+
                "monitoring_spo2=?,monitoring_kateter=?,monitoring_temp=?,monitoring_lainlain=?,status_fisik_asa=?,status_fisik_alergi=?,status_fisik_alergi_keterangan=?,status_fisik_penyulit_sedasi=?,perencanaan_lanjut=?,"+
                "perencanaan_lanjut_sedasi=?,perencanaan_lanjut_sedasi_keterangan=?,perencanaan_lanjut_spinal=?,perencanaan_lanjut_anestesi_umum=?,perencanaan_lanjut_anestesi_umum_keterangan=?,perencanaan_lanjut_blok_perifer=?,"+
                "perencanaan_lanjut_blok_perifer_keterangan=?,perencanaan_lanjut_epidural=?,perencanaan_batal=?,perencanaan_batal_alasan=?,nip_perawat_ok=?,nip_perawat_anestesi=?",68,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokterBedah.getText(),KdDokterAnestesi.getText(),DiagnosaPreBedah.getText(),Tindakan.getText(),DiagnosaPaskaBedah.getText(),
                Jam.getText(),Kesadaran.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),TB.getText(),BB.getText(),Rhesus.getSelectedItem().toString(),HB.getText(),HT.getText(),Leko.getText(),Trombo.getText(),
                BTCT.getText(),GDS.getText(),LainLainPrInduksi.getText(),Hiopotensi.getSelectedItem().toString(),TCI.getSelectedItem().toString(),CPB.getSelectedItem().toString(),Ventilator.getSelectedItem().toString(),Broncoskopy.getSelectedItem().toString(),
                Glidescope.getSelectedItem().toString(),USG.getSelectedItem().toString(),StimulatorSaraf.getSelectedItem().toString(),TeknikAlatLainnya.getText(),EKGLead.getSelectedItem().toString(),EKGLeadKeterangan.getText(),ArteriLine.getSelectedItem().toString(),
                ArteriLineKeterangan.getText(),CVP.getSelectedItem().toString(),CVPKeterangan.getText(),Etco2.getSelectedItem().toString(),Stetoskop.getSelectedItem().toString(),NIBP.getSelectedItem().toString(),NGT.getSelectedItem().toString(),BIS.getSelectedItem().toString(),
                CathAPulmo.getSelectedItem().toString(),SpO2.getSelectedItem().toString(),KateterUrine.getSelectedItem().toString(),Temp.getSelectedItem().toString(),MonitoringLainLain.getText(),AngkaAsa.getSelectedItem().toString(),Alergi.getSelectedItem().toString(),
                AlergiKeterangan.getText(),PenyulitPra.getText(),LanjutTindakan.getSelectedItem().toString(),Sedasi.getSelectedItem().toString(),SedasiKeterangan.getText(),Spinal.getSelectedItem().toString(),AnastesiUmum.getSelectedItem().toString(),AnastesiUmumKeterangan.getText(),
                BlokPerifer.getSelectedItem().toString(),BlokPeriferKeterangan.getText(),Epidural.getSelectedItem().toString(),BatalTindakan.getSelectedItem().toString(),BatalTindakanKeterangan.getText(),KdPetugasBedah.getText(),KdPetugasAnastesi.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(Jk.getText().substring(0,1),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(KdDokterAnestesi.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(NmDokterAnastesi.getText(),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(KdPetugasAnastesi.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(NmPetugasAnastesi.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(KdDokterBedah.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(NmDokterBedah.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(KdPetugasBedah.getText(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(NmPetugasBedah.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(DiagnosaPreBedah.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(DiagnosaPaskaBedah.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(Jam.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(Kesadaran.getSelectedItem(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(Saturasi.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(TB.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(BB.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(GD.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(Rhesus.getSelectedItem(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(HB.getText(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(HT.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(Leko.getText(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(Trombo.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(BTCT.getText(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(GDS.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(LainLainPrInduksi.getText(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(Hiopotensi.getSelectedItem(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(TCI.getSelectedItem(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(CPB.getSelectedItem(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(Ventilator.getSelectedItem(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(Broncoskopy.getSelectedItem(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(Glidescope.getSelectedItem(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(USG.getSelectedItem(),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(StimulatorSaraf.getSelectedItem(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(TeknikAlatLainnya.getText(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(EKGLead.getSelectedItem(),tbObat.getSelectedRow(),44);
                tbObat.setValueAt(EKGLeadKeterangan.getText(),tbObat.getSelectedRow(),45);
                tbObat.setValueAt(ArteriLine.getSelectedItem(),tbObat.getSelectedRow(),46);
                tbObat.setValueAt(ArteriLineKeterangan.getText(),tbObat.getSelectedRow(),47);
                tbObat.setValueAt(CVP.getSelectedItem(),tbObat.getSelectedRow(),48);
                tbObat.setValueAt(CVPKeterangan.getText(),tbObat.getSelectedRow(),49);
                tbObat.setValueAt(Etco2.getSelectedItem(),tbObat.getSelectedRow(),50);
                tbObat.setValueAt(Stetoskop.getSelectedItem(),tbObat.getSelectedRow(),51);
                tbObat.setValueAt(NIBP.getSelectedItem(),tbObat.getSelectedRow(),52);
                tbObat.setValueAt(NGT.getSelectedItem(),tbObat.getSelectedRow(),53);
                tbObat.setValueAt(BIS.getSelectedItem(),tbObat.getSelectedRow(),54);
                tbObat.setValueAt(CathAPulmo.getSelectedItem(),tbObat.getSelectedRow(),55);
                tbObat.setValueAt(SpO2.getSelectedItem(),tbObat.getSelectedRow(),56);
                tbObat.setValueAt(KateterUrine.getSelectedItem(),tbObat.getSelectedRow(),57);
                tbObat.setValueAt(Temp.getSelectedItem(),tbObat.getSelectedRow(),58);
                tbObat.setValueAt(MonitoringLainLain.getText(),tbObat.getSelectedRow(),59);
                tbObat.setValueAt(AngkaAsa.getSelectedItem(),tbObat.getSelectedRow(),60);
                tbObat.setValueAt(Alergi.getSelectedItem(),tbObat.getSelectedRow(),61);
                tbObat.setValueAt(AlergiKeterangan.getText(),tbObat.getSelectedRow(),62);
                tbObat.setValueAt(PenyulitPra.getText(),tbObat.getSelectedRow(),63);
                tbObat.setValueAt(LanjutTindakan.getSelectedItem(),tbObat.getSelectedRow(),64);
                tbObat.setValueAt(Sedasi.getSelectedItem(),tbObat.getSelectedRow(),65);
                tbObat.setValueAt(SedasiKeterangan.getText(),tbObat.getSelectedRow(),66);
                tbObat.setValueAt(Spinal.getSelectedItem(),tbObat.getSelectedRow(),67);
                tbObat.setValueAt(AnastesiUmum.getSelectedItem(),tbObat.getSelectedRow(),68);
                tbObat.setValueAt(AnastesiUmumKeterangan.getText(),tbObat.getSelectedRow(),69);
                tbObat.setValueAt(BlokPerifer.getSelectedItem(),tbObat.getSelectedRow(),70);
                tbObat.setValueAt(BlokPeriferKeterangan.getText(),tbObat.getSelectedRow(),71);
                tbObat.setValueAt(Epidural.getSelectedItem(),tbObat.getSelectedRow(),72);
                tbObat.setValueAt(BatalTindakan.getSelectedItem(),tbObat.getSelectedRow(),73);
                tbObat.setValueAt(BatalTindakanKeterangan.getText(),tbObat.getSelectedRow(),74);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("catatan_anestesi_sedasi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat, Tanggal & Jam",66,new String[]{
                TNoRw.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),KdDokterBedah.getText(),KdDokterAnestesi.getText(),DiagnosaPreBedah.getText(),Tindakan.getText(),DiagnosaPaskaBedah.getText(),
                Jam.getText(),Kesadaran.getSelectedItem().toString(),TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),TB.getText(),BB.getText(),Rhesus.getSelectedItem().toString(),HB.getText(),HT.getText(),Leko.getText(),Trombo.getText(),
                BTCT.getText(),GDS.getText(),LainLainPrInduksi.getText(),Hiopotensi.getSelectedItem().toString(),TCI.getSelectedItem().toString(),CPB.getSelectedItem().toString(),Ventilator.getSelectedItem().toString(),Broncoskopy.getSelectedItem().toString(),
                Glidescope.getSelectedItem().toString(),USG.getSelectedItem().toString(),StimulatorSaraf.getSelectedItem().toString(),TeknikAlatLainnya.getText(),EKGLead.getSelectedItem().toString(),EKGLeadKeterangan.getText(),ArteriLine.getSelectedItem().toString(),
                ArteriLineKeterangan.getText(),CVP.getSelectedItem().toString(),CVPKeterangan.getText(),Etco2.getSelectedItem().toString(),Stetoskop.getSelectedItem().toString(),NIBP.getSelectedItem().toString(),NGT.getSelectedItem().toString(),BIS.getSelectedItem().toString(),
                CathAPulmo.getSelectedItem().toString(),SpO2.getSelectedItem().toString(),KateterUrine.getSelectedItem().toString(),Temp.getSelectedItem().toString(),MonitoringLainLain.getText(),AngkaAsa.getSelectedItem().toString(),Alergi.getSelectedItem().toString(),
                AlergiKeterangan.getText(),PenyulitPra.getText(),LanjutTindakan.getSelectedItem().toString(),Sedasi.getSelectedItem().toString(),SedasiKeterangan.getText(),Spinal.getSelectedItem().toString(),AnastesiUmum.getSelectedItem().toString(),AnastesiUmumKeterangan.getText(),
                BlokPerifer.getSelectedItem().toString(),BlokPeriferKeterangan.getText(),Epidural.getSelectedItem().toString(),BatalTindakan.getSelectedItem().toString(),BatalTindakanKeterangan.getText(),KdPetugasBedah.getText(),KdPetugasAnastesi.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText().substring(0,1),KdDokterAnestesi.getText(),NmDokterAnastesi.getText(),KdPetugasAnastesi.getText(),NmPetugasAnastesi.getText(),KdDokterBedah.getText(),NmDokterBedah.getText(),
                    KdPetugasBedah.getText(),NmPetugasBedah.getText(),Valid.SetTgl(TglAsuhan.getSelectedItem()+"")+" "+TglAsuhan.getSelectedItem().toString().substring(11,19),DiagnosaPreBedah.getText(),Tindakan.getText(),DiagnosaPaskaBedah.getText(),Jam.getText(),Kesadaran.getSelectedItem().toString(),
                    TD.getText(),Nadi.getText(),RR.getText(),Suhu.getText(),Saturasi.getText(),TB.getText(),BB.getText(),GD.getText(),Rhesus.getSelectedItem().toString(),HB.getText(),HT.getText(),Leko.getText(),Trombo.getText(),BTCT.getText(),GDS.getText(),LainLainPrInduksi.getText(),
                    Hiopotensi.getSelectedItem().toString(),TCI.getSelectedItem().toString(),CPB.getSelectedItem().toString(),Ventilator.getSelectedItem().toString(),Broncoskopy.getSelectedItem().toString(),Glidescope.getSelectedItem().toString(),USG.getSelectedItem().toString(),
                    StimulatorSaraf.getSelectedItem().toString(),TeknikAlatLainnya.getText(),EKGLead.getSelectedItem().toString(),EKGLeadKeterangan.getText(),ArteriLine.getSelectedItem().toString(),ArteriLineKeterangan.getText(),CVP.getSelectedItem().toString(),CVPKeterangan.getText(),
                    Etco2.getSelectedItem().toString(),Stetoskop.getSelectedItem().toString(),NIBP.getSelectedItem().toString(),NGT.getSelectedItem().toString(),BIS.getSelectedItem().toString(),CathAPulmo.getSelectedItem().toString(),SpO2.getSelectedItem().toString(),KateterUrine.getSelectedItem().toString(),
                    Temp.getSelectedItem().toString(),MonitoringLainLain.getText(),AngkaAsa.getSelectedItem().toString(),Alergi.getSelectedItem().toString(),AlergiKeterangan.getText(),PenyulitPra.getText(),LanjutTindakan.getSelectedItem().toString(),Sedasi.getSelectedItem().toString(),
                    SedasiKeterangan.getText(),Spinal.getSelectedItem().toString(),AnastesiUmum.getSelectedItem().toString(),AnastesiUmumKeterangan.getText(),BlokPerifer.getSelectedItem().toString(),BlokPeriferKeterangan.getText(),Epidural.getSelectedItem().toString(),BatalTindakan.getSelectedItem().toString(),
                    BatalTindakanKeterangan.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
        }
    }
}

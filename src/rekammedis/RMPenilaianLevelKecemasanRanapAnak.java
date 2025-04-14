/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianLevelKecemasanRanapAnak extends javax.swing.JDialog {
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
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianLevelKecemasanRanapAnak(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Cemas","Firasat Buruk","Takut Pikiran Sendiri","Mudah Tersinggung",
            "Merasa Tegang","Lesu","Tak Bisa Istirahat Tenang","Mudah Terkejut","Mudah Menangis","Gemetar","Gelisah","Takut Pada Gelap",
            "Takut Pada Orang Asing","Takut Pada Kerumunan Banyak Orang","Takut Pada Binatang Besar","Takut Pada Keramaian Lalu Lintas",
            "Takut Ditinggal Sendiri","Sulit Tidur","Terbangun Malam Hari","Tidur Tidak Nyeyak","Mimpi Buruk","Bangun Dengan Lesu",
            "Banyak Mengalami Mimpi","Mimpi Menakutkan","Sulit Konsentrasi","Daya Ingat Buruk","Hilangnya Minat","Berkurangnya Kesenangan Pada Hobi",
            "Sedih","Bangun Dini Hari","Perasaan Berubah","Sakit Nyeri Di Otot","Kaku","Kedutan Otot","Gigi Gemerutuk","Suara Tidak Stabil",
            "Tinnitus","Penglihatan Kabur","Muka Merah Gejala Somatic","Merasa Lemah","Perasaan Ditusuk","Takhikardia","Berdebar","Nyeri Di Dada",
            "Denyut Nadi Mengeras","Perasaan Lesu","Detak Jantung Menghilang","Merasa Tertekan","Perasaan Tercekik","Sering Menarik Napas",
            "Napas Pendek","Bulu Berdiri","Sulit Menelan","Perut Melilit","Ganguan Pencernaan","Rasa Kembung","Nyeri Makan","Terbakar Perut",
            "Sukar BAB","Muntah","BAB Lembek","Kehilangan BB","Mual","Sering BAK","Tidak Bisa Menahan Kencing","Menjadi Dingin","Manorrhagia",
            "Amenorrhoea","Ejakulasi Praecocks","Ereksi Hilang","Impotensi","Mulut Kering","Gejala Otonom Muka Merah","Mudah Berkeringat",
            "Gejala Otonom Bulu Berdiri","Sakit Kepala","Gelisah Wawancara","Napas Pendek Wawancara","Jari Gemetar","Kerut Kening","Muka Tegang",
            "Tonus Meningkat","Tidak Tenang","Muka Merah Wawancara","Total Skor","Keterangan Skor","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 94; i++) {
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
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(42);
            }else if(i==7){
                column.setPreferredWidth(72);
            }else if(i==8){
                column.setPreferredWidth(109);
            }else if(i==9){
                column.setPreferredWidth(105);
            }else if(i==10){
                column.setPreferredWidth(85);
            }else if(i==11){
                column.setPreferredWidth(32);
            }else if(i==12){
                column.setPreferredWidth(132);
            }else if(i==13){
                column.setPreferredWidth(84);
            }else if(i==14){
                column.setPreferredWidth(89);
            }else if(i==15){
                column.setPreferredWidth(51);
            }else if(i==16){
                column.setPreferredWidth(45);
            }else if(i==17){
                column.setPreferredWidth(95);
            }else if(i==18){
                column.setPreferredWidth(127);
            }else if(i==19){
                column.setPreferredWidth(190);
            }else if(i==20){
                column.setPreferredWidth(140);
            }else if(i==21){
                column.setPreferredWidth(172);
            }else if(i==22){
                column.setPreferredWidth(120);
            }else if(i==23){
                column.setPreferredWidth(60);
            }else if(i==24){
                column.setPreferredWidth(120);
            }else if(i==25){
                column.setPreferredWidth(100);
            }else if(i==26){
                column.setPreferredWidth(69);
            }else if(i==27){
                column.setPreferredWidth(110);
            }else if(i==28){
                column.setPreferredWidth(130);
            }else if(i==29){
                column.setPreferredWidth(99);
            }else if(i==30){
                column.setPreferredWidth(90);
            }else if(i==31){
                column.setPreferredWidth(93);
            }else if(i==32){
                column.setPreferredWidth(87);
            }else if(i==33){
                column.setPreferredWidth(188);
            }else if(i==34){
                column.setPreferredWidth(36);
            }else if(i==35){
                column.setPreferredWidth(90);
            }else if(i==36){
                column.setPreferredWidth(97);
            }else if(i==37){
                column.setPreferredWidth(99);
            }else if(i==38){
                column.setPreferredWidth(33);
            }else if(i==39){
                column.setPreferredWidth(74);
            }else if(i==40){
                column.setPreferredWidth(85);
            }else if(i==41){
                column.setPreferredWidth(97);
            }else if(i==42){
                column.setPreferredWidth(47);
            }else if(i==43){
                column.setPreferredWidth(98);
            }else if(i==44){
                column.setPreferredWidth(142);
            }else if(i==45){
                column.setPreferredWidth(79);
            }else if(i==46){
                column.setPreferredWidth(93);
            }else if(i==47){
                column.setPreferredWidth(65);
            }else if(i==48){
                column.setPreferredWidth(53);
            }else if(i==49){
                column.setPreferredWidth(75);
            }else if(i==50){
                column.setPreferredWidth(118);
            }else if(i==51){
                column.setPreferredWidth(79);
            }else if(i==52){
                column.setPreferredWidth(137);
            }else if(i==53){
                column.setPreferredWidth(90);
            }else if(i==54){
                column.setPreferredWidth(98);
            }else if(i==55){
                column.setPreferredWidth(114);
            }else if(i==56){
                column.setPreferredWidth(78);
            }else if(i==57){
                column.setPreferredWidth(65);
            }else if(i==58){
                column.setPreferredWidth(75);
            }else if(i==59){
                column.setPreferredWidth(67);
            }else if(i==60){
                column.setPreferredWidth(113);
            }else if(i==61){
                column.setPreferredWidth(80);
            }else if(i==62){
                column.setPreferredWidth(68);
            }else if(i==63){
                column.setPreferredWidth(80);
            }else if(i==64){
                column.setPreferredWidth(57);
            }else if(i==65){
                column.setPreferredWidth(45);
            }else if(i==66){
                column.setPreferredWidth(68);
            }else if(i==67){
                column.setPreferredWidth(78);
            }else if(i==68){
                column.setPreferredWidth(32);
            }else if(i==69){
                column.setPreferredWidth(62);
            }else if(i==70){
                column.setPreferredWidth(147);
            }else if(i==71){
                column.setPreferredWidth(81);
            }else if(i==72){
                column.setPreferredWidth(70);
            }else if(i==73){
                column.setPreferredWidth(75);
            }else if(i==74){
                column.setPreferredWidth(104);
            }else if(i==75){
                column.setPreferredWidth(72);
            }else if(i==76){
                column.setPreferredWidth(58);
            }else if(i==77){
                column.setPreferredWidth(70);
            }else if(i==78){
                column.setPreferredWidth(143);
            }else if(i==79){
                column.setPreferredWidth(100);
            }else if(i==80){
                column.setPreferredWidth(141);
            }else if(i==81){
                column.setPreferredWidth(69);
            }else if(i==82){
                column.setPreferredWidth(105);
            }else if(i==83){
                column.setPreferredWidth(137);
            }else if(i==84){
                column.setPreferredWidth(71);
            }else if(i==85){
                column.setPreferredWidth(70);
            }else if(i==86){
                column.setPreferredWidth(72);
            }else if(i==87){
                column.setPreferredWidth(90);
            }else if(i==88){
                column.setPreferredWidth(74);
            }else if(i==89){
                column.setPreferredWidth(126);
            }else if(i==90){
                column.setPreferredWidth(60);
            }else if(i==91){
                column.setPreferredWidth(150);
            }else if(i==92){
                column.setPreferredWidth(90);
            }else if(i==93){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
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
                    KodePetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    btnPetugas.requestFocus();
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
        
        ChkInput.setSelected(false);
        isForm();
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnLevelKecemasan = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
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
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel23 = new widget.Label();
        KodePetugas = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel61 = new widget.Label();
        FirasatBuruk = new widget.ComboBox();
        TakutPikiranSendiri = new widget.ComboBox();
        jLabel63 = new widget.Label();
        Cemas = new widget.ComboBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        MudahTersinggung = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        MerasaTegang = new widget.ComboBox();
        jLabel67 = new widget.Label();
        jLabel69 = new widget.Label();
        TakBisaIstirahatTenang = new widget.ComboBox();
        jLabel70 = new widget.Label();
        Lesu = new widget.ComboBox();
        jLabel71 = new widget.Label();
        MudahTerkejut = new widget.ComboBox();
        MudahMenangis = new widget.ComboBox();
        jLabel73 = new widget.Label();
        jLabel74 = new widget.Label();
        Gemetar = new widget.ComboBox();
        TakutPadaGelap = new widget.ComboBox();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        Gelisah = new widget.ComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel78 = new widget.Label();
        TakutPadaOrangAsing = new widget.ComboBox();
        jLabel80 = new widget.Label();
        TakutDitinggalSendiri = new widget.ComboBox();
        TakutPadaBinatangBesar = new widget.ComboBox();
        jLabel81 = new widget.Label();
        jLabel83 = new widget.Label();
        TakutPadaKeramaianLaluLintas = new widget.ComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel56 = new widget.Label();
        jLabel84 = new widget.Label();
        TakutPadaKerumunanBanyakOrang = new widget.ComboBox();
        SulitTidur = new widget.ComboBox();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        TerbangunMalamHari = new widget.ComboBox();
        jLabel89 = new widget.Label();
        TidurTidakNyeyak = new widget.ComboBox();
        jLabel90 = new widget.Label();
        MimpiBuruk = new widget.ComboBox();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        BangunDenganLesu = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel91 = new widget.Label();
        jLabel93 = new widget.Label();
        DayaIngatBuruk = new widget.ComboBox();
        jLabel94 = new widget.Label();
        SulitKonsentrasi = new widget.ComboBox();
        MimpiMenakutkan = new widget.ComboBox();
        jLabel96 = new widget.Label();
        jLabel97 = new widget.Label();
        BanyakMengalamiMimpi = new widget.ComboBox();
        HilangnyaMinat = new widget.ComboBox();
        jLabel98 = new widget.Label();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel100 = new widget.Label();
        BerkurangnyaKesenanganPadaHobi = new widget.ComboBox();
        jLabel102 = new widget.Label();
        Sedih = new widget.ComboBox();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel104 = new widget.Label();
        BangunDiniHari = new widget.ComboBox();
        jLabel105 = new widget.Label();
        jLabel107 = new widget.Label();
        PerasaanBerubah = new widget.ComboBox();
        jLabel108 = new widget.Label();
        KedutanOtot = new widget.ComboBox();
        SakitNyeriDiOtot = new widget.ComboBox();
        jLabel109 = new widget.Label();
        jLabel110 = new widget.Label();
        Kaku = new widget.ComboBox();
        jLabel112 = new widget.Label();
        GigiGemerutuk = new widget.ComboBox();
        SuaraTidakStabil = new widget.ComboBox();
        jLabel113 = new widget.Label();
        Tinnitus = new widget.ComboBox();
        jLabel115 = new widget.Label();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel106 = new widget.Label();
        jLabel116 = new widget.Label();
        PenglihatanKabur = new widget.ComboBox();
        MukaMerahGejalaSomatic = new widget.ComboBox();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        MerasaLemah = new widget.ComboBox();
        PerasaanDitusuk = new widget.ComboBox();
        jLabel119 = new widget.Label();
        jSeparator17 = new javax.swing.JSeparator();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel111 = new widget.Label();
        Takhikardia = new widget.ComboBox();
        jLabel120 = new widget.Label();
        jLabel121 = new widget.Label();
        Berdebar = new widget.ComboBox();
        jLabel122 = new widget.Label();
        NyeriDiDada = new widget.ComboBox();
        jLabel123 = new widget.Label();
        DenyutNadiMengeras = new widget.ComboBox();
        PerasaanLesu = new widget.ComboBox();
        jLabel124 = new widget.Label();
        jLabel125 = new widget.Label();
        DetakJantungMenghilang = new widget.ComboBox();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator20 = new javax.swing.JSeparator();
        jLabel114 = new widget.Label();
        MerasaTertekan = new widget.ComboBox();
        jLabel126 = new widget.Label();
        NapasPendek = new widget.ComboBox();
        jLabel127 = new widget.Label();
        jLabel128 = new widget.Label();
        SeringMenarikNapas = new widget.ComboBox();
        jLabel129 = new widget.Label();
        PerasaanTercekik = new widget.ComboBox();
        jLabel130 = new widget.Label();
        BuluBerdiri = new widget.ComboBox();
        jSeparator21 = new javax.swing.JSeparator();
        jSeparator22 = new javax.swing.JSeparator();
        jLabel131 = new widget.Label();
        SulitMenelan = new widget.ComboBox();
        jLabel132 = new widget.Label();
        jLabel133 = new widget.Label();
        PerutMelilit = new widget.ComboBox();
        jLabel134 = new widget.Label();
        GanguanPencernaan = new widget.ComboBox();
        jLabel135 = new widget.Label();
        RasaKembung = new widget.ComboBox();
        NyeriMakan = new widget.ComboBox();
        jLabel136 = new widget.Label();
        jLabel137 = new widget.Label();
        TerbakarPerut = new widget.ComboBox();
        jLabel138 = new widget.Label();
        Mual = new widget.ComboBox();
        Muntah = new widget.ComboBox();
        jLabel139 = new widget.Label();
        jLabel140 = new widget.Label();
        BABLembek = new widget.ComboBox();
        jLabel141 = new widget.Label();
        KehilanganBB = new widget.ComboBox();
        jLabel142 = new widget.Label();
        SukarBAB = new widget.ComboBox();
        jSeparator23 = new javax.swing.JSeparator();
        jSeparator24 = new javax.swing.JSeparator();
        jLabel143 = new widget.Label();
        SeringBAK = new widget.ComboBox();
        jLabel144 = new widget.Label();
        jLabel145 = new widget.Label();
        TidakBisaMenahanKencing = new widget.ComboBox();
        jLabel146 = new widget.Label();
        MenjadiDingin = new widget.ComboBox();
        jLabel147 = new widget.Label();
        Amenorrhoea = new widget.ComboBox();
        Manorrhagia = new widget.ComboBox();
        jLabel148 = new widget.Label();
        EjakulasiPraecocks = new widget.ComboBox();
        jLabel149 = new widget.Label();
        jLabel150 = new widget.Label();
        EreksiHilang = new widget.ComboBox();
        jLabel151 = new widget.Label();
        Impotensi = new widget.ComboBox();
        jSeparator25 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jLabel152 = new widget.Label();
        MulutKering = new widget.ComboBox();
        jLabel153 = new widget.Label();
        jLabel154 = new widget.Label();
        MukaMerahGejalaOtonom = new widget.ComboBox();
        jLabel155 = new widget.Label();
        MudahBerkeringat = new widget.ComboBox();
        jLabel156 = new widget.Label();
        BuluBerdiriGejalaOtonom = new widget.ComboBox();
        SakitKepala = new widget.ComboBox();
        jLabel157 = new widget.Label();
        jSeparator27 = new javax.swing.JSeparator();
        jSeparator28 = new javax.swing.JSeparator();
        jLabel158 = new widget.Label();
        GelisahWawancara = new widget.ComboBox();
        jLabel159 = new widget.Label();
        jLabel160 = new widget.Label();
        NapasPendekWawancara = new widget.ComboBox();
        jLabel161 = new widget.Label();
        JariGemetar = new widget.ComboBox();
        jLabel162 = new widget.Label();
        KerutKening = new widget.ComboBox();
        jLabel163 = new widget.Label();
        MukaMerahWawancara = new widget.ComboBox();
        TidakTenang = new widget.ComboBox();
        jLabel164 = new widget.Label();
        TonusMeningkat = new widget.ComboBox();
        jLabel165 = new widget.Label();
        MukaTegang = new widget.ComboBox();
        jLabel166 = new widget.Label();
        jLabel68 = new widget.Label();
        jLabel72 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel85 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel92 = new widget.Label();
        jLabel95 = new widget.Label();
        jLabel101 = new widget.Label();
        jLabel103 = new widget.Label();
        jLabel167 = new widget.Label();
        jLabel168 = new widget.Label();
        jLabel169 = new widget.Label();
        jLabel170 = new widget.Label();
        jLabel171 = new widget.Label();
        jLabel172 = new widget.Label();
        jLabel173 = new widget.Label();
        jLabel174 = new widget.Label();
        jLabel175 = new widget.Label();
        jLabel176 = new widget.Label();
        jLabel177 = new widget.Label();
        jLabel178 = new widget.Label();
        jLabel179 = new widget.Label();
        jLabel180 = new widget.Label();
        jLabel181 = new widget.Label();
        jLabel182 = new widget.Label();
        jLabel183 = new widget.Label();
        jSeparator29 = new javax.swing.JSeparator();
        jSeparator30 = new javax.swing.JSeparator();
        jLabel24 = new widget.Label();
        KeteranganSkor = new widget.TextBox();
        TotalSkor = new widget.TextBox();
        jLabel184 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLevelKecemasan.setBackground(new java.awt.Color(255, 255, 254));
        MnLevelKecemasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLevelKecemasan.setForeground(new java.awt.Color(50, 50, 50));
        MnLevelKecemasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLevelKecemasan.setText("Formulir Level Kecemasan Ranap Anak");
        MnLevelKecemasan.setName("MnLevelKecemasan"); // NOI18N
        MnLevelKecemasan.setPreferredSize(new java.awt.Dimension(260, 26));
        MnLevelKecemasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLevelKecemasanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLevelKecemasan);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Penilaian Level Kecemasan Rawat Inap Anak ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-08-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-08-2023" }));
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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 386));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1233));
        FormInput.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No.Rawat");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(21, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

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
        jLabel16.setBounds(0, 40, 75, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "08-08-2023 06:18:12" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 130, 23);

        jLabel23.setText("Petugas :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(221, 40, 140, 23);

        KodePetugas.setEditable(false);
        KodePetugas.setHighlighter(null);
        KodePetugas.setName("KodePetugas"); // NOI18N
        FormInput.add(KodePetugas);
        KodePetugas.setBounds(365, 40, 127, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(494, 40, 265, 23);

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

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 810, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 810, 1);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("1. Perasaan Ansietas (Cemas) :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        jLabel61.setText("Firasat Buruk :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(170, 90, 100, 23);

        FirasatBuruk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        FirasatBuruk.setName("FirasatBuruk"); // NOI18N
        FirasatBuruk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FirasatBurukItemStateChanged(evt);
            }
        });
        FirasatBuruk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FirasatBurukKeyPressed(evt);
            }
        });
        FormInput.add(FirasatBuruk);
        FirasatBuruk.setBounds(274, 90, 60, 23);

        TakutPikiranSendiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TakutPikiranSendiri.setName("TakutPikiranSendiri"); // NOI18N
        TakutPikiranSendiri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakutPikiranSendiriItemStateChanged(evt);
            }
        });
        TakutPikiranSendiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakutPikiranSendiriKeyPressed(evt);
            }
        });
        FormInput.add(TakutPikiranSendiri);
        TakutPikiranSendiri.setBounds(519, 90, 60, 23);

        jLabel63.setText("Takut Akan Pikiran Sendiri :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(365, 90, 150, 23);

        Cemas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Cemas.setName("Cemas"); // NOI18N
        Cemas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CemasItemStateChanged(evt);
            }
        });
        Cemas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CemasKeyPressed(evt);
            }
        });
        FormInput.add(Cemas);
        Cemas.setBounds(88, 90, 60, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Cemas");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(44, 90, 50, 23);

        jLabel66.setText("Mudah Tersinggung :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(605, 90, 120, 23);

        MudahTersinggung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MudahTersinggung.setName("MudahTersinggung"); // NOI18N
        MudahTersinggung.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MudahTersinggungItemStateChanged(evt);
            }
        });
        MudahTersinggung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MudahTersinggungKeyPressed(evt);
            }
        });
        FormInput.add(MudahTersinggung);
        MudahTersinggung.setBounds(729, 90, 60, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 120, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 120, 810, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("2. Ketegangan :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 120, 180, 23);

        MerasaTegang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MerasaTegang.setName("MerasaTegang"); // NOI18N
        MerasaTegang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MerasaTegangItemStateChanged(evt);
            }
        });
        MerasaTegang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerasaTegangKeyPressed(evt);
            }
        });
        FormInput.add(MerasaTegang);
        MerasaTegang.setBounds(129, 140, 60, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Merasa Tegang");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(44, 140, 100, 23);

        jLabel69.setText("Tak Bisa Istirahat Tenang :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(365, 140, 160, 23);

        TakBisaIstirahatTenang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TakBisaIstirahatTenang.setName("TakBisaIstirahatTenang"); // NOI18N
        TakBisaIstirahatTenang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakBisaIstirahatTenangItemStateChanged(evt);
            }
        });
        TakBisaIstirahatTenang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakBisaIstirahatTenangKeyPressed(evt);
            }
        });
        FormInput.add(TakBisaIstirahatTenang);
        TakBisaIstirahatTenang.setBounds(529, 140, 60, 23);

        jLabel70.setText("Lesu :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(228, 140, 50, 23);

        Lesu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Lesu.setName("Lesu"); // NOI18N
        Lesu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LesuItemStateChanged(evt);
            }
        });
        Lesu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LesuKeyPressed(evt);
            }
        });
        FormInput.add(Lesu);
        Lesu.setBounds(282, 140, 60, 23);

        jLabel71.setText("Mudah Terkejut :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(625, 140, 100, 23);

        MudahTerkejut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MudahTerkejut.setName("MudahTerkejut"); // NOI18N
        MudahTerkejut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MudahTerkejutItemStateChanged(evt);
            }
        });
        MudahTerkejut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MudahTerkejutKeyPressed(evt);
            }
        });
        FormInput.add(MudahTerkejut);
        MudahTerkejut.setBounds(729, 140, 60, 23);

        MudahMenangis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MudahMenangis.setName("MudahMenangis"); // NOI18N
        MudahMenangis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MudahMenangisItemStateChanged(evt);
            }
        });
        MudahMenangis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MudahMenangisKeyPressed(evt);
            }
        });
        FormInput.add(MudahMenangis);
        MudahMenangis.setBounds(136, 170, 60, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Mudah Menangis");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(44, 170, 100, 23);

        jLabel74.setText("Gemetar :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(241, 170, 90, 23);

        Gemetar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Gemetar.setName("Gemetar"); // NOI18N
        Gemetar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GemetarItemStateChanged(evt);
            }
        });
        Gemetar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GemetarKeyPressed(evt);
            }
        });
        FormInput.add(Gemetar);
        Gemetar.setBounds(335, 170, 60, 23);

        TakutPadaGelap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TakutPadaGelap.setName("TakutPadaGelap"); // NOI18N
        TakutPadaGelap.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakutPadaGelapItemStateChanged(evt);
            }
        });
        TakutPadaGelap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakutPadaGelapKeyPressed(evt);
            }
        });
        FormInput.add(TakutPadaGelap);
        TakutPadaGelap.setBounds(141, 220, 60, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("Takut Pada Gelap");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(44, 220, 120, 23);

        jLabel76.setText("Gelisah :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(435, 170, 90, 23);

        Gelisah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Gelisah.setName("Gelisah"); // NOI18N
        Gelisah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GelisahItemStateChanged(evt);
            }
        });
        Gelisah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GelisahKeyPressed(evt);
            }
        });
        FormInput.add(Gelisah);
        Gelisah.setBounds(529, 170, 60, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 200, 810, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 200, 810, 1);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("3. Ketakutan :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 200, 130, 23);

        jLabel78.setText("Takut Pada Orang Asing :");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(256, 220, 140, 23);

        TakutPadaOrangAsing.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TakutPadaOrangAsing.setName("TakutPadaOrangAsing"); // NOI18N
        TakutPadaOrangAsing.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakutPadaOrangAsingItemStateChanged(evt);
            }
        });
        TakutPadaOrangAsing.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakutPadaOrangAsingKeyPressed(evt);
            }
        });
        FormInput.add(TakutPadaOrangAsing);
        TakutPadaOrangAsing.setBounds(400, 220, 60, 23);

        jLabel80.setText("Takut Ditinggal Sendiri :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(575, 250, 150, 23);

        TakutDitinggalSendiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TakutDitinggalSendiri.setName("TakutDitinggalSendiri"); // NOI18N
        TakutDitinggalSendiri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakutDitinggalSendiriItemStateChanged(evt);
            }
        });
        TakutDitinggalSendiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakutDitinggalSendiriKeyPressed(evt);
            }
        });
        FormInput.add(TakutDitinggalSendiri);
        TakutDitinggalSendiri.setBounds(729, 250, 60, 23);

        TakutPadaBinatangBesar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TakutPadaBinatangBesar.setName("TakutPadaBinatangBesar"); // NOI18N
        TakutPadaBinatangBesar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakutPadaBinatangBesarItemStateChanged(evt);
            }
        });
        TakutPadaBinatangBesar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakutPadaBinatangBesarKeyPressed(evt);
            }
        });
        FormInput.add(TakutPadaBinatangBesar);
        TakutPadaBinatangBesar.setBounds(186, 250, 60, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("Takut Pada Binatang Besar");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(44, 250, 160, 23);

        jLabel83.setText("Takut Pada Keramaian Lalu Lintas :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(271, 250, 210, 23);

        TakutPadaKeramaianLaluLintas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TakutPadaKeramaianLaluLintas.setName("TakutPadaKeramaianLaluLintas"); // NOI18N
        TakutPadaKeramaianLaluLintas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakutPadaKeramaianLaluLintasItemStateChanged(evt);
            }
        });
        TakutPadaKeramaianLaluLintas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakutPadaKeramaianLaluLintasKeyPressed(evt);
            }
        });
        FormInput.add(TakutPadaKeramaianLaluLintas);
        TakutPadaKeramaianLaluLintas.setBounds(485, 250, 60, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 280, 810, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 280, 810, 1);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("4. Gangguan Tidur :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 280, 150, 23);

        jLabel84.setText("Takut Pada Kerumunan Banyak Orang :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(515, 220, 210, 23);

        TakutPadaKerumunanBanyakOrang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TakutPadaKerumunanBanyakOrang.setName("TakutPadaKerumunanBanyakOrang"); // NOI18N
        TakutPadaKerumunanBanyakOrang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakutPadaKerumunanBanyakOrangItemStateChanged(evt);
            }
        });
        TakutPadaKerumunanBanyakOrang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakutPadaKerumunanBanyakOrangKeyPressed(evt);
            }
        });
        FormInput.add(TakutPadaKerumunanBanyakOrang);
        TakutPadaKerumunanBanyakOrang.setBounds(729, 220, 60, 23);

        SulitTidur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SulitTidur.setName("SulitTidur"); // NOI18N
        SulitTidur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SulitTidurItemStateChanged(evt);
            }
        });
        SulitTidur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SulitTidurKeyPressed(evt);
            }
        });
        FormInput.add(SulitTidur);
        SulitTidur.setBounds(106, 300, 60, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Sulit Tidur");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(44, 300, 80, 23);

        jLabel87.setText("Terbangun Malam Hari :");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(195, 300, 140, 23);

        TerbangunMalamHari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TerbangunMalamHari.setName("TerbangunMalamHari"); // NOI18N
        TerbangunMalamHari.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TerbangunMalamHariItemStateChanged(evt);
            }
        });
        TerbangunMalamHari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerbangunMalamHariKeyPressed(evt);
            }
        });
        FormInput.add(TerbangunMalamHari);
        TerbangunMalamHari.setBounds(339, 300, 60, 23);

        jLabel89.setText("Tidur Tidak Nyenyak :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(430, 300, 120, 23);

        TidurTidakNyeyak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TidurTidakNyeyak.setName("TidurTidakNyeyak"); // NOI18N
        TidurTidakNyeyak.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TidurTidakNyeyakItemStateChanged(evt);
            }
        });
        TidurTidakNyeyak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TidurTidakNyeyakKeyPressed(evt);
            }
        });
        FormInput.add(TidurTidakNyeyak);
        TidurTidakNyeyak.setBounds(554, 300, 60, 23);

        jLabel90.setText("Mimpi Buruk :");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(625, 300, 100, 23);

        MimpiBuruk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MimpiBuruk.setName("MimpiBuruk"); // NOI18N
        MimpiBuruk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MimpiBurukItemStateChanged(evt);
            }
        });
        MimpiBuruk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MimpiBurukKeyPressed(evt);
            }
        });
        FormInput.add(MimpiBuruk);
        MimpiBuruk.setBounds(729, 300, 60, 23);

        jSeparator9.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator9.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator9.setName("jSeparator9"); // NOI18N
        FormInput.add(jSeparator9);
        jSeparator9.setBounds(0, 360, 810, 1);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput.add(jSeparator10);
        jSeparator10.setBounds(0, 360, 810, 1);

        BangunDenganLesu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        BangunDenganLesu.setName("BangunDenganLesu"); // NOI18N
        BangunDenganLesu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BangunDenganLesuItemStateChanged(evt);
            }
        });
        BangunDenganLesu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BangunDenganLesuKeyPressed(evt);
            }
        });
        FormInput.add(BangunDenganLesu);
        BangunDenganLesu.setBounds(156, 330, 60, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("5. Gangguan Kecerdasan :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(10, 360, 350, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Bangun Dengan Lesu");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(44, 330, 130, 23);

        jLabel93.setText("Daya Ingat Buruk :");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(266, 380, 120, 23);

        DayaIngatBuruk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        DayaIngatBuruk.setName("DayaIngatBuruk"); // NOI18N
        DayaIngatBuruk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DayaIngatBurukItemStateChanged(evt);
            }
        });
        DayaIngatBuruk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DayaIngatBurukKeyPressed(evt);
            }
        });
        FormInput.add(DayaIngatBuruk);
        DayaIngatBuruk.setBounds(390, 380, 60, 23);

        jLabel94.setText("Mimpi Menakutkan :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(585, 330, 140, 23);

        SulitKonsentrasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SulitKonsentrasi.setName("SulitKonsentrasi"); // NOI18N
        SulitKonsentrasi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SulitKonsentrasiItemStateChanged(evt);
            }
        });
        SulitKonsentrasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SulitKonsentrasiKeyPressed(evt);
            }
        });
        FormInput.add(SulitKonsentrasi);
        SulitKonsentrasi.setBounds(137, 380, 60, 23);

        MimpiMenakutkan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MimpiMenakutkan.setName("MimpiMenakutkan"); // NOI18N
        MimpiMenakutkan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MimpiMenakutkanItemStateChanged(evt);
            }
        });
        MimpiMenakutkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MimpiMenakutkanKeyPressed(evt);
            }
        });
        FormInput.add(MimpiMenakutkan);
        MimpiMenakutkan.setBounds(729, 330, 60, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("Sulit Konsentrasi");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(44, 380, 150, 23);

        jLabel97.setText("Banyak Mengalami Mimpi-mimpi :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(281, 330, 190, 23);

        BanyakMengalamiMimpi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        BanyakMengalamiMimpi.setName("BanyakMengalamiMimpi"); // NOI18N
        BanyakMengalamiMimpi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BanyakMengalamiMimpiItemStateChanged(evt);
            }
        });
        BanyakMengalamiMimpi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BanyakMengalamiMimpiKeyPressed(evt);
            }
        });
        FormInput.add(BanyakMengalamiMimpi);
        BanyakMengalamiMimpi.setBounds(475, 330, 60, 23);

        HilangnyaMinat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        HilangnyaMinat.setName("HilangnyaMinat"); // NOI18N
        HilangnyaMinat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HilangnyaMinatItemStateChanged(evt);
            }
        });
        HilangnyaMinat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HilangnyaMinatKeyPressed(evt);
            }
        });
        FormInput.add(HilangnyaMinat);
        HilangnyaMinat.setBounds(133, 430, 60, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("Hilangnya Minat");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(44, 430, 110, 23);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput.add(jSeparator11);
        jSeparator11.setBounds(0, 410, 810, 1);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 410, 810, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("6. Perasaan Depresi :");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 410, 230, 23);

        jLabel100.setText("Berkurangnya Kesenangan Pada Hobi :");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(281, 430, 220, 23);

        BerkurangnyaKesenanganPadaHobi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        BerkurangnyaKesenanganPadaHobi.setName("BerkurangnyaKesenanganPadaHobi"); // NOI18N
        BerkurangnyaKesenanganPadaHobi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BerkurangnyaKesenanganPadaHobiItemStateChanged(evt);
            }
        });
        BerkurangnyaKesenanganPadaHobi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BerkurangnyaKesenanganPadaHobiKeyPressed(evt);
            }
        });
        FormInput.add(BerkurangnyaKesenanganPadaHobi);
        BerkurangnyaKesenanganPadaHobi.setBounds(505, 430, 60, 23);

        jLabel102.setText("Sedih :");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(665, 430, 60, 23);

        Sedih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Sedih.setName("Sedih"); // NOI18N
        Sedih.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SedihItemStateChanged(evt);
            }
        });
        Sedih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SedihKeyPressed(evt);
            }
        });
        FormInput.add(Sedih);
        Sedih.setBounds(729, 430, 60, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(0, 490, 810, 1);

        jSeparator14.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator14.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator14.setName("jSeparator14"); // NOI18N
        FormInput.add(jSeparator14);
        jSeparator14.setBounds(0, 490, 810, 1);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("7. Gejala Somatic (Otot) :");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(10, 490, 180, 23);

        BangunDiniHari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        BangunDiniHari.setName("BangunDiniHari"); // NOI18N
        BangunDiniHari.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BangunDiniHariItemStateChanged(evt);
            }
        });
        BangunDiniHari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BangunDiniHariKeyPressed(evt);
            }
        });
        FormInput.add(BangunDiniHari);
        BangunDiniHari.setBounds(136, 460, 60, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("Bangun Dini Hari");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(44, 460, 110, 23);

        jLabel107.setText("Perasaan Berubah-ubah Sepanjang Hari :");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(256, 460, 245, 23);

        PerasaanBerubah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        PerasaanBerubah.setName("PerasaanBerubah"); // NOI18N
        PerasaanBerubah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PerasaanBerubahItemStateChanged(evt);
            }
        });
        PerasaanBerubah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerasaanBerubahKeyPressed(evt);
            }
        });
        FormInput.add(PerasaanBerubah);
        PerasaanBerubah.setBounds(505, 460, 60, 23);

        jLabel108.setText("Kedutan Otot :");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(436, 510, 90, 23);

        KedutanOtot.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        KedutanOtot.setName("KedutanOtot"); // NOI18N
        KedutanOtot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KedutanOtotItemStateChanged(evt);
            }
        });
        KedutanOtot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KedutanOtotKeyPressed(evt);
            }
        });
        FormInput.add(KedutanOtot);
        KedutanOtot.setBounds(530, 510, 60, 23);

        SakitNyeriDiOtot.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SakitNyeriDiOtot.setName("SakitNyeriDiOtot"); // NOI18N
        SakitNyeriDiOtot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SakitNyeriDiOtotItemStateChanged(evt);
            }
        });
        SakitNyeriDiOtot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SakitNyeriDiOtotKeyPressed(evt);
            }
        });
        FormInput.add(SakitNyeriDiOtot);
        SakitNyeriDiOtot.setBounds(179, 510, 60, 23);

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel109.setText("Sakit & Nyeri Di Otot-otot");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(44, 510, 150, 23);

        jLabel110.setText("Kaku :");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(281, 510, 50, 23);

        Kaku.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Kaku.setName("Kaku"); // NOI18N
        Kaku.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KakuItemStateChanged(evt);
            }
        });
        Kaku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KakuKeyPressed(evt);
            }
        });
        FormInput.add(Kaku);
        Kaku.setBounds(335, 510, 60, 23);

        jLabel112.setText("Gigi Gemerutuk :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(625, 510, 100, 23);

        GigiGemerutuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        GigiGemerutuk.setName("GigiGemerutuk"); // NOI18N
        GigiGemerutuk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GigiGemerutukItemStateChanged(evt);
            }
        });
        GigiGemerutuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GigiGemerutukKeyPressed(evt);
            }
        });
        FormInput.add(GigiGemerutuk);
        GigiGemerutuk.setBounds(729, 510, 60, 23);

        SuaraTidakStabil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SuaraTidakStabil.setName("SuaraTidakStabil"); // NOI18N
        SuaraTidakStabil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SuaraTidakStabilItemStateChanged(evt);
            }
        });
        SuaraTidakStabil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuaraTidakStabilKeyPressed(evt);
            }
        });
        FormInput.add(SuaraTidakStabil);
        SuaraTidakStabil.setBounds(143, 540, 60, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("Suara Tidak Stabil");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(44, 540, 120, 23);

        Tinnitus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Tinnitus.setName("Tinnitus"); // NOI18N
        Tinnitus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TinnitusItemStateChanged(evt);
            }
        });
        Tinnitus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinnitusKeyPressed(evt);
            }
        });
        FormInput.add(Tinnitus);
        Tinnitus.setBounds(94, 590, 60, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Tinnitus");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(44, 590, 70, 23);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput.add(jSeparator15);
        jSeparator15.setBounds(0, 570, 810, 1);

        jSeparator16.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator16.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator16.setName("jSeparator16"); // NOI18N
        FormInput.add(jSeparator16);
        jSeparator16.setBounds(0, 570, 810, 1);

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("8. Gejala Somatic (Sensorik) :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(10, 570, 350, 23);

        jLabel116.setText("Penglihatan Kabur :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(189, 590, 120, 23);

        PenglihatanKabur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        PenglihatanKabur.setName("PenglihatanKabur"); // NOI18N
        PenglihatanKabur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PenglihatanKaburItemStateChanged(evt);
            }
        });
        PenglihatanKabur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenglihatanKaburKeyPressed(evt);
            }
        });
        FormInput.add(PenglihatanKabur);
        PenglihatanKabur.setBounds(313, 590, 60, 23);

        MukaMerahGejalaSomatic.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MukaMerahGejalaSomatic.setName("MukaMerahGejalaSomatic"); // NOI18N
        MukaMerahGejalaSomatic.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MukaMerahGejalaSomaticItemStateChanged(evt);
            }
        });
        MukaMerahGejalaSomatic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MukaMerahGejalaSomaticKeyPressed(evt);
            }
        });
        FormInput.add(MukaMerahGejalaSomatic);
        MukaMerahGejalaSomatic.setBounds(530, 590, 60, 23);

        jLabel117.setText("Muka Merah/Pucat :");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(406, 590, 120, 23);

        jLabel118.setText("Merasa Lemah :");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(615, 590, 110, 23);

        MerasaLemah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MerasaLemah.setName("MerasaLemah"); // NOI18N
        MerasaLemah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MerasaLemahItemStateChanged(evt);
            }
        });
        MerasaLemah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerasaLemahKeyPressed(evt);
            }
        });
        FormInput.add(MerasaLemah);
        MerasaLemah.setBounds(729, 590, 60, 23);

        PerasaanDitusuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        PerasaanDitusuk.setName("PerasaanDitusuk"); // NOI18N
        PerasaanDitusuk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PerasaanDitusukItemStateChanged(evt);
            }
        });
        PerasaanDitusuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerasaanDitusukKeyPressed(evt);
            }
        });
        FormInput.add(PerasaanDitusuk);
        PerasaanDitusuk.setBounds(170, 620, 60, 23);

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel119.setText("Perasaan Ditusuk-tusuk");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(44, 620, 140, 23);

        jSeparator17.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator17.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator17.setName("jSeparator17"); // NOI18N
        FormInput.add(jSeparator17);
        jSeparator17.setBounds(0, 650, 810, 1);

        jSeparator18.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator18.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator18.setName("jSeparator18"); // NOI18N
        FormInput.add(jSeparator18);
        jSeparator18.setBounds(0, 650, 810, 1);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("9. Gejala Kardiovaskular :");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(10, 650, 350, 23);

        Takhikardia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Takhikardia.setName("Takhikardia"); // NOI18N
        Takhikardia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TakhikardiaItemStateChanged(evt);
            }
        });
        Takhikardia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TakhikardiaKeyPressed(evt);
            }
        });
        FormInput.add(Takhikardia);
        Takhikardia.setBounds(111, 670, 60, 23);

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("Takhikardia");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(44, 670, 80, 23);

        jLabel121.setText("Berdebar :");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(195, 670, 90, 23);

        Berdebar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Berdebar.setName("Berdebar"); // NOI18N
        Berdebar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BerdebarItemStateChanged(evt);
            }
        });
        Berdebar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BerdebarKeyPressed(evt);
            }
        });
        FormInput.add(Berdebar);
        Berdebar.setBounds(289, 670, 60, 23);

        jLabel122.setText("Nyeri Di Dada :");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(388, 670, 100, 23);

        NyeriDiDada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        NyeriDiDada.setName("NyeriDiDada"); // NOI18N
        NyeriDiDada.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NyeriDiDadaItemStateChanged(evt);
            }
        });
        NyeriDiDada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriDiDadaKeyPressed(evt);
            }
        });
        FormInput.add(NyeriDiDada);
        NyeriDiDada.setBounds(492, 670, 60, 23);

        jLabel123.setText("Denyut Nadi Mengeras :");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(585, 670, 140, 23);

        DenyutNadiMengeras.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        DenyutNadiMengeras.setName("DenyutNadiMengeras"); // NOI18N
        DenyutNadiMengeras.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DenyutNadiMengerasItemStateChanged(evt);
            }
        });
        DenyutNadiMengeras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DenyutNadiMengerasKeyPressed(evt);
            }
        });
        FormInput.add(DenyutNadiMengeras);
        DenyutNadiMengeras.setBounds(729, 670, 60, 23);

        PerasaanLesu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        PerasaanLesu.setName("PerasaanLesu"); // NOI18N
        PerasaanLesu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PerasaanLesuItemStateChanged(evt);
            }
        });
        PerasaanLesu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerasaanLesuKeyPressed(evt);
            }
        });
        FormInput.add(PerasaanLesu);
        PerasaanLesu.setBounds(266, 700, 60, 23);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("Perasaan Lesu/Lemas Seperti Akan Pingsan");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(44, 700, 240, 23);

        jLabel125.setText("Detak Jantung Menghilang (Berhenti Sekejap) :");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(465, 700, 260, 23);

        DetakJantungMenghilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        DetakJantungMenghilang.setName("DetakJantungMenghilang"); // NOI18N
        DetakJantungMenghilang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DetakJantungMenghilangItemStateChanged(evt);
            }
        });
        DetakJantungMenghilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetakJantungMenghilangKeyPressed(evt);
            }
        });
        FormInput.add(DetakJantungMenghilang);
        DetakJantungMenghilang.setBounds(729, 700, 60, 23);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 730, 810, 1);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(0, 730, 810, 1);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("10. Gejala Respiratori :");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(10, 730, 350, 23);

        MerasaTertekan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MerasaTertekan.setName("MerasaTertekan"); // NOI18N
        MerasaTertekan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MerasaTertekanItemStateChanged(evt);
            }
        });
        MerasaTertekan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MerasaTertekanKeyPressed(evt);
            }
        });
        FormInput.add(MerasaTertekan);
        MerasaTertekan.setBounds(215, 750, 60, 23);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("Merasa Tertekan/Sempit Di Dada");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(44, 750, 180, 23);

        NapasPendek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        NapasPendek.setName("NapasPendek"); // NOI18N
        NapasPendek.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NapasPendekItemStateChanged(evt);
            }
        });
        NapasPendek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NapasPendekKeyPressed(evt);
            }
        });
        FormInput.add(NapasPendek);
        NapasPendek.setBounds(156, 780, 60, 23);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("Napas Pendek/Sesak");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(44, 780, 130, 23);

        jLabel128.setText("Sering Menarik Napas :");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(595, 750, 130, 23);

        SeringMenarikNapas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SeringMenarikNapas.setName("SeringMenarikNapas"); // NOI18N
        SeringMenarikNapas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SeringMenarikNapasItemStateChanged(evt);
            }
        });
        SeringMenarikNapas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SeringMenarikNapasKeyPressed(evt);
            }
        });
        FormInput.add(SeringMenarikNapas);
        SeringMenarikNapas.setBounds(729, 750, 60, 23);

        jLabel129.setText("Perasaan Tercekik :");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(316, 750, 140, 23);

        PerasaanTercekik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        PerasaanTercekik.setName("PerasaanTercekik"); // NOI18N
        PerasaanTercekik.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PerasaanTercekikItemStateChanged(evt);
            }
        });
        PerasaanTercekik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerasaanTercekikKeyPressed(evt);
            }
        });
        FormInput.add(PerasaanTercekik);
        PerasaanTercekik.setBounds(460, 750, 60, 23);

        jLabel130.setText("Bulu-bulu Berdiri :");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(316, 780, 140, 23);

        BuluBerdiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        BuluBerdiri.setName("BuluBerdiri"); // NOI18N
        BuluBerdiri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BuluBerdiriItemStateChanged(evt);
            }
        });
        BuluBerdiri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuluBerdiriKeyPressed(evt);
            }
        });
        FormInput.add(BuluBerdiri);
        BuluBerdiri.setBounds(460, 780, 60, 23);

        jSeparator21.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator21.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator21.setName("jSeparator21"); // NOI18N
        FormInput.add(jSeparator21);
        jSeparator21.setBounds(0, 810, 810, 1);

        jSeparator22.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator22.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator22.setName("jSeparator22"); // NOI18N
        FormInput.add(jSeparator22);
        jSeparator22.setBounds(0, 810, 810, 1);

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel131.setText("11. Gejala Pencernaan :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(10, 810, 350, 23);

        SulitMenelan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SulitMenelan.setName("SulitMenelan"); // NOI18N
        SulitMenelan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SulitMenelanItemStateChanged(evt);
            }
        });
        SulitMenelan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SulitMenelanKeyPressed(evt);
            }
        });
        FormInput.add(SulitMenelan);
        SulitMenelan.setBounds(121, 830, 60, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("Sulit Menelan");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(44, 830, 90, 23);

        jLabel133.setText("Perut Melilit :");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(199, 830, 90, 23);

        PerutMelilit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        PerutMelilit.setName("PerutMelilit"); // NOI18N
        PerutMelilit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PerutMelilitItemStateChanged(evt);
            }
        });
        PerutMelilit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerutMelilitKeyPressed(evt);
            }
        });
        FormInput.add(PerutMelilit);
        PerutMelilit.setBounds(293, 830, 60, 23);

        jLabel134.setText("Gangguan Pencernaan :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(375, 830, 130, 23);

        GanguanPencernaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        GanguanPencernaan.setName("GanguanPencernaan"); // NOI18N
        GanguanPencernaan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GanguanPencernaanItemStateChanged(evt);
            }
        });
        GanguanPencernaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GanguanPencernaanKeyPressed(evt);
            }
        });
        FormInput.add(GanguanPencernaan);
        GanguanPencernaan.setBounds(509, 830, 60, 23);

        jLabel135.setText("Rasa Penuh & Kembung :");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(585, 830, 140, 23);

        RasaKembung.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        RasaKembung.setName("RasaKembung"); // NOI18N
        RasaKembung.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RasaKembungItemStateChanged(evt);
            }
        });
        RasaKembung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasaKembungKeyPressed(evt);
            }
        });
        FormInput.add(RasaKembung);
        RasaKembung.setBounds(729, 830, 60, 23);

        NyeriMakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        NyeriMakan.setName("NyeriMakan"); // NOI18N
        NyeriMakan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NyeriMakanItemStateChanged(evt);
            }
        });
        NyeriMakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NyeriMakanKeyPressed(evt);
            }
        });
        FormInput.add(NyeriMakan);
        NyeriMakan.setBounds(213, 860, 60, 23);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("Nyeri Sebelum & Sesudah Makan");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(44, 860, 180, 23);

        jLabel137.setText("Perasaan Terbakar Diperut :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(320, 860, 160, 23);

        TerbakarPerut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TerbakarPerut.setName("TerbakarPerut"); // NOI18N
        TerbakarPerut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TerbakarPerutItemStateChanged(evt);
            }
        });
        TerbakarPerut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerbakarPerutKeyPressed(evt);
            }
        });
        FormInput.add(TerbakarPerut);
        TerbakarPerut.setBounds(484, 860, 60, 23);

        jLabel138.setText("Mual :");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(655, 890, 70, 23);

        Mual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Mual.setName("Mual"); // NOI18N
        Mual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MualItemStateChanged(evt);
            }
        });
        Mual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MualKeyPressed(evt);
            }
        });
        FormInput.add(Mual);
        Mual.setBounds(729, 890, 60, 23);

        Muntah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Muntah.setName("Muntah"); // NOI18N
        Muntah.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MuntahItemStateChanged(evt);
            }
        });
        Muntah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MuntahKeyPressed(evt);
            }
        });
        FormInput.add(Muntah);
        Muntah.setBounds(91, 890, 60, 23);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("Muntah");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(44, 890, 60, 23);

        jLabel140.setText("Buang Air Besar Lembek :");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(175, 890, 160, 23);

        BABLembek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        BABLembek.setName("BABLembek"); // NOI18N
        BABLembek.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BABLembekItemStateChanged(evt);
            }
        });
        BABLembek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BABLembekKeyPressed(evt);
            }
        });
        FormInput.add(BABLembek);
        BABLembek.setBounds(339, 890, 60, 23);

        jLabel141.setText("Kehilangan Berat Badan :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(407, 890, 170, 23);

        KehilanganBB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        KehilanganBB.setName("KehilanganBB"); // NOI18N
        KehilanganBB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KehilanganBBItemStateChanged(evt);
            }
        });
        KehilanganBB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KehilanganBBKeyPressed(evt);
            }
        });
        FormInput.add(KehilanganBB);
        KehilanganBB.setBounds(581, 890, 60, 23);

        jLabel142.setText("Sukar Buang Air Besar :");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(575, 860, 150, 23);

        SukarBAB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SukarBAB.setName("SukarBAB"); // NOI18N
        SukarBAB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SukarBABItemStateChanged(evt);
            }
        });
        SukarBAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SukarBABKeyPressed(evt);
            }
        });
        FormInput.add(SukarBAB);
        SukarBAB.setBounds(729, 860, 60, 23);

        jSeparator23.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator23.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator23.setName("jSeparator23"); // NOI18N
        FormInput.add(jSeparator23);
        jSeparator23.setBounds(0, 920, 810, 1);

        jSeparator24.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator24.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator24.setName("jSeparator24"); // NOI18N
        FormInput.add(jSeparator24);
        jSeparator24.setBounds(0, 920, 810, 1);

        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel143.setText("12. Gejala Urogenital :");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(10, 920, 350, 23);

        SeringBAK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SeringBAK.setName("SeringBAK"); // NOI18N
        SeringBAK.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SeringBAKItemStateChanged(evt);
            }
        });
        SeringBAK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SeringBAKKeyPressed(evt);
            }
        });
        FormInput.add(SeringBAK);
        SeringBAK.setBounds(162, 940, 60, 23);

        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel144.setText("Sering Buang Air Kecil");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(44, 940, 140, 23);

        jLabel145.setText("Tidak Dapat Menahan Air Seni :");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(275, 940, 180, 23);

        TidakBisaMenahanKencing.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TidakBisaMenahanKencing.setName("TidakBisaMenahanKencing"); // NOI18N
        TidakBisaMenahanKencing.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TidakBisaMenahanKencingItemStateChanged(evt);
            }
        });
        TidakBisaMenahanKencing.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TidakBisaMenahanKencingKeyPressed(evt);
            }
        });
        FormInput.add(TidakBisaMenahanKencing);
        TidakBisaMenahanKencing.setBounds(459, 940, 60, 23);

        jLabel146.setText("Menjadi Dingin (Frigid) :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(575, 940, 150, 23);

        MenjadiDingin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MenjadiDingin.setName("MenjadiDingin"); // NOI18N
        MenjadiDingin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MenjadiDinginItemStateChanged(evt);
            }
        });
        MenjadiDingin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenjadiDinginKeyPressed(evt);
            }
        });
        FormInput.add(MenjadiDingin);
        MenjadiDingin.setBounds(729, 940, 60, 23);

        jLabel147.setText("Amenorrhoea (Tidak Menstruasi Pada Perempuan) :");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(455, 970, 270, 23);

        Amenorrhoea.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Amenorrhoea.setName("Amenorrhoea"); // NOI18N
        Amenorrhoea.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AmenorrhoeaItemStateChanged(evt);
            }
        });
        Amenorrhoea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AmenorrhoeaKeyPressed(evt);
            }
        });
        FormInput.add(Amenorrhoea);
        Amenorrhoea.setBounds(729, 970, 60, 23);

        Manorrhagia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Manorrhagia.setName("Manorrhagia"); // NOI18N
        Manorrhagia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ManorrhagiaItemStateChanged(evt);
            }
        });
        Manorrhagia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManorrhagiaKeyPressed(evt);
            }
        });
        FormInput.add(Manorrhagia);
        Manorrhagia.setBounds(316, 970, 60, 23);

        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel148.setText("Menorrhagia (Keluar Darah Banyak Ketika Menstruasi)");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(44, 970, 360, 23);

        EjakulasiPraecocks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        EjakulasiPraecocks.setName("EjakulasiPraecocks"); // NOI18N
        EjakulasiPraecocks.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EjakulasiPraecocksItemStateChanged(evt);
            }
        });
        EjakulasiPraecocks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EjakulasiPraecocksKeyPressed(evt);
            }
        });
        FormInput.add(EjakulasiPraecocks);
        EjakulasiPraecocks.setBounds(149, 1000, 60, 23);

        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel149.setText("Ejakulasi Praecocks");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(44, 1000, 120, 23);

        jLabel150.setText("Ereksi Hilang :");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(255, 1000, 100, 23);

        EreksiHilang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        EreksiHilang.setName("EreksiHilang"); // NOI18N
        EreksiHilang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EreksiHilangItemStateChanged(evt);
            }
        });
        EreksiHilang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EreksiHilangKeyPressed(evt);
            }
        });
        FormInput.add(EreksiHilang);
        EreksiHilang.setBounds(359, 1000, 60, 23);

        jLabel151.setText("Impotensi :");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(470, 1000, 80, 23);

        Impotensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        Impotensi.setName("Impotensi"); // NOI18N
        Impotensi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ImpotensiItemStateChanged(evt);
            }
        });
        Impotensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImpotensiKeyPressed(evt);
            }
        });
        FormInput.add(Impotensi);
        Impotensi.setBounds(554, 1000, 60, 23);

        jSeparator25.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator25.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator25.setName("jSeparator25"); // NOI18N
        FormInput.add(jSeparator25);
        jSeparator25.setBounds(0, 1030, 810, 1);

        jSeparator26.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator26.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator26.setName("jSeparator26"); // NOI18N
        FormInput.add(jSeparator26);
        jSeparator26.setBounds(0, 1030, 810, 1);

        jLabel152.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel152.setText("13. Gejala Otonom :");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(10, 1030, 350, 23);

        MulutKering.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MulutKering.setName("MulutKering"); // NOI18N
        MulutKering.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MulutKeringItemStateChanged(evt);
            }
        });
        MulutKering.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MulutKeringKeyPressed(evt);
            }
        });
        FormInput.add(MulutKering);
        MulutKering.setBounds(116, 1050, 60, 23);

        jLabel153.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel153.setText("Mulut Kering");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(44, 1050, 140, 23);

        jLabel154.setText("Muka Merah :");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(206, 1050, 90, 23);

        MukaMerahGejalaOtonom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MukaMerahGejalaOtonom.setName("MukaMerahGejalaOtonom"); // NOI18N
        MukaMerahGejalaOtonom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MukaMerahGejalaOtonomItemStateChanged(evt);
            }
        });
        MukaMerahGejalaOtonom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MukaMerahGejalaOtonomKeyPressed(evt);
            }
        });
        FormInput.add(MukaMerahGejalaOtonom);
        MukaMerahGejalaOtonom.setBounds(300, 1050, 60, 23);

        jLabel155.setText("Mudah Berkeringat :");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(386, 1050, 130, 23);

        MudahBerkeringat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MudahBerkeringat.setName("MudahBerkeringat"); // NOI18N
        MudahBerkeringat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MudahBerkeringatItemStateChanged(evt);
            }
        });
        MudahBerkeringat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MudahBerkeringatKeyPressed(evt);
            }
        });
        FormInput.add(MudahBerkeringat);
        MudahBerkeringat.setBounds(520, 1050, 60, 23);

        jLabel156.setText("Bulu Bulu Berdiri :");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(605, 1050, 120, 23);

        BuluBerdiriGejalaOtonom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        BuluBerdiriGejalaOtonom.setName("BuluBerdiriGejalaOtonom"); // NOI18N
        BuluBerdiriGejalaOtonom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BuluBerdiriGejalaOtonomItemStateChanged(evt);
            }
        });
        BuluBerdiriGejalaOtonom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuluBerdiriGejalaOtonomKeyPressed(evt);
            }
        });
        FormInput.add(BuluBerdiriGejalaOtonom);
        BuluBerdiriGejalaOtonom.setBounds(729, 1050, 60, 23);

        SakitKepala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        SakitKepala.setName("SakitKepala"); // NOI18N
        SakitKepala.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SakitKepalaItemStateChanged(evt);
            }
        });
        SakitKepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SakitKepalaKeyPressed(evt);
            }
        });
        FormInput.add(SakitKepala);
        SakitKepala.setBounds(153, 1080, 60, 23);

        jLabel157.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel157.setText("Pusing, Sakit Kepala");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(44, 1080, 140, 23);

        jSeparator27.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator27.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator27.setName("jSeparator27"); // NOI18N
        FormInput.add(jSeparator27);
        jSeparator27.setBounds(0, 1110, 810, 1);

        jSeparator28.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator28.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator28.setName("jSeparator28"); // NOI18N
        FormInput.add(jSeparator28);
        jSeparator28.setBounds(0, 1110, 810, 1);

        jLabel158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel158.setText("14. Tingkah Laku Pada Wawancara :");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(10, 1110, 350, 23);

        GelisahWawancara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        GelisahWawancara.setName("GelisahWawancara"); // NOI18N
        GelisahWawancara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                GelisahWawancaraItemStateChanged(evt);
            }
        });
        GelisahWawancara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GelisahWawancaraKeyPressed(evt);
            }
        });
        FormInput.add(GelisahWawancara);
        GelisahWawancara.setBounds(92, 1130, 60, 23);

        jLabel159.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel159.setText("Gelisah");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(44, 1130, 60, 23);

        jLabel160.setText("Napas Pendek & Cepat :");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(190, 1130, 140, 23);

        NapasPendekWawancara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        NapasPendekWawancara.setName("NapasPendekWawancara"); // NOI18N
        NapasPendekWawancara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NapasPendekWawancaraItemStateChanged(evt);
            }
        });
        NapasPendekWawancara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NapasPendekWawancaraKeyPressed(evt);
            }
        });
        FormInput.add(NapasPendekWawancara);
        NapasPendekWawancara.setBounds(334, 1130, 60, 23);

        jLabel161.setText("Jari Gemetar :");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(425, 1130, 100, 23);

        JariGemetar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        JariGemetar.setName("JariGemetar"); // NOI18N
        JariGemetar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JariGemetarItemStateChanged(evt);
            }
        });
        JariGemetar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JariGemetarKeyPressed(evt);
            }
        });
        FormInput.add(JariGemetar);
        JariGemetar.setBounds(529, 1130, 60, 23);

        jLabel162.setText("Kerut Kening :");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(625, 1130, 100, 23);

        KerutKening.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        KerutKening.setName("KerutKening"); // NOI18N
        KerutKening.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KerutKeningItemStateChanged(evt);
            }
        });
        KerutKening.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KerutKeningKeyPressed(evt);
            }
        });
        FormInput.add(KerutKening);
        KerutKening.setBounds(729, 1130, 60, 23);

        jLabel163.setText("Muka Merah :");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(625, 1160, 100, 23);

        MukaMerahWawancara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MukaMerahWawancara.setName("MukaMerahWawancara"); // NOI18N
        MukaMerahWawancara.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MukaMerahWawancaraItemStateChanged(evt);
            }
        });
        MukaMerahWawancara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MukaMerahWawancaraKeyPressed(evt);
            }
        });
        FormInput.add(MukaMerahWawancara);
        MukaMerahWawancara.setBounds(729, 1160, 60, 23);

        TidakTenang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TidakTenang.setName("TidakTenang"); // NOI18N
        TidakTenang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TidakTenangItemStateChanged(evt);
            }
        });
        TidakTenang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TidakTenangKeyPressed(evt);
            }
        });
        FormInput.add(TidakTenang);
        TidakTenang.setBounds(545, 1160, 60, 23);

        jLabel164.setText("Tidak Tenang :");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(441, 1160, 100, 23);

        TonusMeningkat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        TonusMeningkat.setName("TonusMeningkat"); // NOI18N
        TonusMeningkat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TonusMeningkatItemStateChanged(evt);
            }
        });
        TonusMeningkat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TonusMeningkatKeyPressed(evt);
            }
        });
        FormInput.add(TonusMeningkat);
        TonusMeningkat.setBounds(354, 1160, 60, 23);

        jLabel165.setText("Tonus Otot Meningkat :");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(210, 1160, 140, 23);

        MukaTegang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        MukaTegang.setName("MukaTegang"); // NOI18N
        MukaTegang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MukaTegangItemStateChanged(evt);
            }
        });
        MukaTegang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MukaTegangKeyPressed(evt);
            }
        });
        FormInput.add(MukaTegang);
        MukaTegang.setBounds(119, 1160, 60, 23);

        jLabel166.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel166.setText("Muka Tegang");
        jLabel166.setName("jLabel166"); // NOI18N
        FormInput.add(jLabel166);
        jLabel166.setBounds(44, 1160, 140, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 90, 84, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 140, 125, 23);

        jLabel77.setText(":");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(0, 170, 132, 23);

        jLabel79.setText(":");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(0, 220, 137, 23);

        jLabel82.setText(":");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 250, 182, 23);

        jLabel85.setText(":");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(0, 300, 102, 23);

        jLabel88.setText(":");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(0, 330, 152, 23);

        jLabel92.setText(":");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(0, 380, 133, 23);

        jLabel95.setText(":");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(0, 430, 129, 23);

        jLabel101.setText(":");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(0, 460, 132, 23);

        jLabel103.setText(":");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(0, 510, 175, 23);

        jLabel167.setText(":");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(0, 540, 139, 23);

        jLabel168.setText(":");
        jLabel168.setName("jLabel168"); // NOI18N
        FormInput.add(jLabel168);
        jLabel168.setBounds(0, 590, 90, 23);

        jLabel169.setText(":");
        jLabel169.setName("jLabel169"); // NOI18N
        FormInput.add(jLabel169);
        jLabel169.setBounds(0, 620, 166, 23);

        jLabel170.setText(":");
        jLabel170.setName("jLabel170"); // NOI18N
        FormInput.add(jLabel170);
        jLabel170.setBounds(0, 670, 107, 23);

        jLabel171.setText(":");
        jLabel171.setName("jLabel171"); // NOI18N
        FormInput.add(jLabel171);
        jLabel171.setBounds(0, 700, 262, 23);

        jLabel172.setText(":");
        jLabel172.setName("jLabel172"); // NOI18N
        FormInput.add(jLabel172);
        jLabel172.setBounds(0, 750, 211, 23);

        jLabel173.setText(":");
        jLabel173.setName("jLabel173"); // NOI18N
        FormInput.add(jLabel173);
        jLabel173.setBounds(0, 780, 152, 23);

        jLabel174.setText(":");
        jLabel174.setName("jLabel174"); // NOI18N
        FormInput.add(jLabel174);
        jLabel174.setBounds(0, 830, 117, 23);

        jLabel175.setText(":");
        jLabel175.setName("jLabel175"); // NOI18N
        FormInput.add(jLabel175);
        jLabel175.setBounds(0, 860, 209, 23);

        jLabel176.setText(":");
        jLabel176.setName("jLabel176"); // NOI18N
        FormInput.add(jLabel176);
        jLabel176.setBounds(0, 890, 87, 23);

        jLabel177.setText(":");
        jLabel177.setName("jLabel177"); // NOI18N
        FormInput.add(jLabel177);
        jLabel177.setBounds(0, 940, 158, 23);

        jLabel178.setText(":");
        jLabel178.setName("jLabel178"); // NOI18N
        FormInput.add(jLabel178);
        jLabel178.setBounds(0, 970, 312, 23);

        jLabel179.setText(":");
        jLabel179.setName("jLabel179"); // NOI18N
        FormInput.add(jLabel179);
        jLabel179.setBounds(0, 1000, 145, 23);

        jLabel180.setText(":");
        jLabel180.setName("jLabel180"); // NOI18N
        FormInput.add(jLabel180);
        jLabel180.setBounds(0, 1050, 112, 23);

        jLabel181.setText(":");
        jLabel181.setName("jLabel181"); // NOI18N
        FormInput.add(jLabel181);
        jLabel181.setBounds(0, 1080, 149, 23);

        jLabel182.setText(":");
        jLabel182.setName("jLabel182"); // NOI18N
        FormInput.add(jLabel182);
        jLabel182.setBounds(0, 1130, 88, 23);

        jLabel183.setText(":");
        jLabel183.setName("jLabel183"); // NOI18N
        FormInput.add(jLabel183);
        jLabel183.setBounds(0, 1160, 115, 23);

        jSeparator29.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator29.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator29.setName("jSeparator29"); // NOI18N
        FormInput.add(jSeparator29);
        jSeparator29.setBounds(0, 1190, 810, 1);

        jSeparator30.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator30.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator30.setName("jSeparator30"); // NOI18N
        FormInput.add(jSeparator30);
        jSeparator30.setBounds(0, 1190, 810, 1);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Total Skor Kecemasan");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(10, 1200, 130, 23);

        KeteranganSkor.setEditable(false);
        KeteranganSkor.setHighlighter(null);
        KeteranganSkor.setName("KeteranganSkor"); // NOI18N
        FormInput.add(KeteranganSkor);
        KeteranganSkor.setBounds(180, 1200, 609, 23);

        TotalSkor.setEditable(false);
        TotalSkor.setHighlighter(null);
        TotalSkor.setName("TotalSkor"); // NOI18N
        FormInput.add(TotalSkor);
        TotalSkor.setBounds(128, 1200, 50, 23);

        jLabel184.setText(":");
        jLabel184.setName("jLabel184"); // NOI18N
        FormInput.add(jLabel184);
        jLabel184.setBounds(0, 1200, 124, 23);

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
            Valid.textKosong(TNoRw,"pasien");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Petugas");
        }else{
            if(Sequel.menyimpantf("penilaian_level_kecemasan_ranap_anak","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",89,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Cemas.getSelectedItem().toString(),FirasatBuruk.getSelectedItem().toString(),TakutPikiranSendiri.getSelectedItem().toString(),MudahTersinggung.getSelectedItem().toString(),
                MerasaTegang.getSelectedItem().toString(),Lesu.getSelectedItem().toString(),TakBisaIstirahatTenang.getSelectedItem().toString(),MudahTerkejut.getSelectedItem().toString(),MudahMenangis.getSelectedItem().toString(),Gemetar.getSelectedItem().toString(),Gelisah.getSelectedItem().toString(),
                TakutPadaGelap.getSelectedItem().toString(),TakutPadaOrangAsing.getSelectedItem().toString(),TakutPadaKerumunanBanyakOrang.getSelectedItem().toString(),TakutPadaBinatangBesar.getSelectedItem().toString(),TakutPadaKeramaianLaluLintas.getSelectedItem().toString(),
                TakutDitinggalSendiri.getSelectedItem().toString(),SulitTidur.getSelectedItem().toString(),TerbangunMalamHari.getSelectedItem().toString(),TidurTidakNyeyak.getSelectedItem().toString(),MimpiBuruk.getSelectedItem().toString(),BangunDenganLesu.getSelectedItem().toString(),
                BanyakMengalamiMimpi.getSelectedItem().toString(),MimpiMenakutkan.getSelectedItem().toString(),SulitKonsentrasi.getSelectedItem().toString(),DayaIngatBuruk.getSelectedItem().toString(),HilangnyaMinat.getSelectedItem().toString(),BerkurangnyaKesenanganPadaHobi.getSelectedItem().toString(),
                Sedih.getSelectedItem().toString(),BangunDiniHari.getSelectedItem().toString(),PerasaanBerubah.getSelectedItem().toString(),SakitNyeriDiOtot.getSelectedItem().toString(),Kaku.getSelectedItem().toString(),KedutanOtot.getSelectedItem().toString(),GigiGemerutuk.getSelectedItem().toString(),
                SuaraTidakStabil.getSelectedItem().toString(),Tinnitus.getSelectedItem().toString(),PenglihatanKabur.getSelectedItem().toString(),MukaMerahGejalaSomatic.getSelectedItem().toString(),MerasaLemah.getSelectedItem().toString(),PerasaanDitusuk.getSelectedItem().toString(),
                Takhikardia.getSelectedItem().toString(),Berdebar.getSelectedItem().toString(),NyeriDiDada.getSelectedItem().toString(),DenyutNadiMengeras.getSelectedItem().toString(),PerasaanLesu.getSelectedItem().toString(),DetakJantungMenghilang.getSelectedItem().toString(),
                MerasaTertekan.getSelectedItem().toString(),PerasaanTercekik.getSelectedItem().toString(),SeringMenarikNapas.getSelectedItem().toString(),NapasPendek.getSelectedItem().toString(),BuluBerdiri.getSelectedItem().toString(),SulitMenelan.getSelectedItem().toString(),
                PerutMelilit.getSelectedItem().toString(),GanguanPencernaan.getSelectedItem().toString(),RasaKembung.getSelectedItem().toString(),NyeriMakan.getSelectedItem().toString(),TerbakarPerut.getSelectedItem().toString(),SukarBAB.getSelectedItem().toString(),Muntah.getSelectedItem().toString(),
                BABLembek.getSelectedItem().toString(),KehilanganBB.getSelectedItem().toString(),Mual.getSelectedItem().toString(),SeringBAK.getSelectedItem().toString(),TidakBisaMenahanKencing.getSelectedItem().toString(),MenjadiDingin.getSelectedItem().toString(),Manorrhagia.getSelectedItem().toString(),
                Amenorrhoea.getSelectedItem().toString(),EjakulasiPraecocks.getSelectedItem().toString(),EreksiHilang.getSelectedItem().toString(),Impotensi.getSelectedItem().toString(),MulutKering.getSelectedItem().toString(),MukaMerahGejalaOtonom.getSelectedItem().toString(),
                MudahBerkeringat.getSelectedItem().toString(),BuluBerdiriGejalaOtonom.getSelectedItem().toString(),SakitKepala.getSelectedItem().toString(),GelisahWawancara.getSelectedItem().toString(),NapasPendekWawancara.getSelectedItem().toString(),JariGemetar.getSelectedItem().toString(),
                KerutKening.getSelectedItem().toString(),MukaTegang.getSelectedItem().toString(),TonusMeningkat.getSelectedItem().toString(),TidakTenang.getSelectedItem().toString(),MukaMerahWawancara.getSelectedItem().toString(),TotalSkor.getText(),KeteranganSkor.getText(),KodePetugas.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Cemas.getSelectedItem().toString(),FirasatBuruk.getSelectedItem().toString(),TakutPikiranSendiri.getSelectedItem().toString(),MudahTersinggung.getSelectedItem().toString(),
                    MerasaTegang.getSelectedItem().toString(),Lesu.getSelectedItem().toString(),TakBisaIstirahatTenang.getSelectedItem().toString(),MudahTerkejut.getSelectedItem().toString(),MudahMenangis.getSelectedItem().toString(),Gemetar.getSelectedItem().toString(),Gelisah.getSelectedItem().toString(),
                    TakutPadaGelap.getSelectedItem().toString(),TakutPadaOrangAsing.getSelectedItem().toString(),TakutPadaKerumunanBanyakOrang.getSelectedItem().toString(),TakutPadaBinatangBesar.getSelectedItem().toString(),TakutPadaKeramaianLaluLintas.getSelectedItem().toString(),
                    TakutDitinggalSendiri.getSelectedItem().toString(),SulitTidur.getSelectedItem().toString(),TerbangunMalamHari.getSelectedItem().toString(),TidurTidakNyeyak.getSelectedItem().toString(),MimpiBuruk.getSelectedItem().toString(),BangunDenganLesu.getSelectedItem().toString(),
                    BanyakMengalamiMimpi.getSelectedItem().toString(),MimpiMenakutkan.getSelectedItem().toString(),SulitKonsentrasi.getSelectedItem().toString(),DayaIngatBuruk.getSelectedItem().toString(),HilangnyaMinat.getSelectedItem().toString(),BerkurangnyaKesenanganPadaHobi.getSelectedItem().toString(),
                    Sedih.getSelectedItem().toString(),BangunDiniHari.getSelectedItem().toString(),PerasaanBerubah.getSelectedItem().toString(),SakitNyeriDiOtot.getSelectedItem().toString(),Kaku.getSelectedItem().toString(),KedutanOtot.getSelectedItem().toString(),GigiGemerutuk.getSelectedItem().toString(),
                    SuaraTidakStabil.getSelectedItem().toString(),Tinnitus.getSelectedItem().toString(),PenglihatanKabur.getSelectedItem().toString(),MukaMerahGejalaSomatic.getSelectedItem().toString(),MerasaLemah.getSelectedItem().toString(),PerasaanDitusuk.getSelectedItem().toString(),
                    Takhikardia.getSelectedItem().toString(),Berdebar.getSelectedItem().toString(),NyeriDiDada.getSelectedItem().toString(),DenyutNadiMengeras.getSelectedItem().toString(),PerasaanLesu.getSelectedItem().toString(),DetakJantungMenghilang.getSelectedItem().toString(),
                    MerasaTertekan.getSelectedItem().toString(),PerasaanTercekik.getSelectedItem().toString(),SeringMenarikNapas.getSelectedItem().toString(),NapasPendek.getSelectedItem().toString(),BuluBerdiri.getSelectedItem().toString(),SulitMenelan.getSelectedItem().toString(),
                    PerutMelilit.getSelectedItem().toString(),GanguanPencernaan.getSelectedItem().toString(),RasaKembung.getSelectedItem().toString(),NyeriMakan.getSelectedItem().toString(),TerbakarPerut.getSelectedItem().toString(),SukarBAB.getSelectedItem().toString(),Muntah.getSelectedItem().toString(),
                    BABLembek.getSelectedItem().toString(),KehilanganBB.getSelectedItem().toString(),Mual.getSelectedItem().toString(),SeringBAK.getSelectedItem().toString(),TidakBisaMenahanKencing.getSelectedItem().toString(),MenjadiDingin.getSelectedItem().toString(),Manorrhagia.getSelectedItem().toString(),
                    Amenorrhoea.getSelectedItem().toString(),EjakulasiPraecocks.getSelectedItem().toString(),EreksiHilang.getSelectedItem().toString(),Impotensi.getSelectedItem().toString(),MulutKering.getSelectedItem().toString(),MukaMerahGejalaOtonom.getSelectedItem().toString(),
                    MudahBerkeringat.getSelectedItem().toString(),BuluBerdiriGejalaOtonom.getSelectedItem().toString(),SakitKepala.getSelectedItem().toString(),GelisahWawancara.getSelectedItem().toString(),NapasPendekWawancara.getSelectedItem().toString(),JariGemetar.getSelectedItem().toString(),
                    KerutKening.getSelectedItem().toString(),MukaTegang.getSelectedItem().toString(),TonusMeningkat.getSelectedItem().toString(),TidakTenang.getSelectedItem().toString(),MukaMerahWawancara.getSelectedItem().toString(),TotalSkor.getText(),KeteranganSkor.getText(),KodePetugas.getText(),NamaPetugas.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            } 
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,MukaMerahWawancara,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
            Valid.textKosong(TNoRw,"pasien");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Petugas");
        }else{  
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cemas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Firasat Buruk</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Takut Pikiran Sendiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mudah Tersinggung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merasa Tegang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lesu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tak Bisa Istirahat Tenang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mudah Terkejut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mudah Menangis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gemetar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gelisah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Takut Pada Gelap</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Takut Pada OrangAsing</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Takut Pada Kerumunan Banyak Orang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Takut Pada Binatang Besar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Takut Pada Keramaian Lalu Lintas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Takut Ditinggal Sendiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sulit Tidur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Terbangun Malam Hari</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidur Tidak Nyeyak</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mimpi Buruk</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bangun Dengan Lesu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Banyak Mengalami Mimpi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mimpi Menakutkan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sulit Konsentrasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Daya Ingat Buruk</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hilangnya Minat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Berkurangnya Kesenangan Pada Hobi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sedih</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bangun Dini Hari</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perasaan Berubah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sakit Nyeri Di Otot</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kaku</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kedutan Otot</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gigi Gemerutuk</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suara Tidak Stabil</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tinnitus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penglihatan Kabur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Muka Merah Gejala Somatic</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merasa Lemah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perasaan Ditusuk</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Takhikardia</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Berdebar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri Di Dada</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Denyut Nadi Mengeras</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perasaan Lesu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Detak Jantung Menghilang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Merasa Tertekan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perasaan Tercekik</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sering Menarik Napas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Napas Pendek</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bulu Berdiri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sulit Menelan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perut Melilit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ganguan Pencernaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasa Kembung</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nyeri Makan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Terbakar Perut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sukar BAB</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Muntah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>BAB Lembek</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kehilangan BB</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mual</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sering BAK</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Bisa Menahan Kencing</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Menjadi Dingin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Manorrhagia</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Amenorrhoea</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ejakulasi Praecocks</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ereksi Hilang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Impotensi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mulut Kering</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Muka Merah Gejala Otonom</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mudah Berkeringat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bulu Berdiri Gejala Otonom</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Sakit Kepala</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Gelisah Wawancara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Napas Pendek Wawancara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jari Gemetar</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kerut Kening</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Muka Tegang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tonus Meningkat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tidak Tenang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Muka Merah Wawancara</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Skor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
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
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='8000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataPenilaianevelKecemasanRanapAnak.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='8000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN LEVEL KECEMASAN PASIEN RAWAT INAP ANAK<br><br></font>"+        
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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

    private void MnLevelKecemasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLevelKecemasanActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronip oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),46).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),45).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirLevelKecemasanRanapAnak.jasper","report","::[ Formulir Level Kecemasan Rawat Inap Anak ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,penilaian_level_kecemasan_ranap_anak.tanggal,"+
                    "penilaian_level_kecemasan_ranap_anak.cemas,penilaian_level_kecemasan_ranap_anak.firasat_buruk,penilaian_level_kecemasan_ranap_anak.takut_pikiran_sendiri,"+
                    "penilaian_level_kecemasan_ranap_anak.mudah_tersinggung,penilaian_level_kecemasan_ranap_anak.merasa_tegang,penilaian_level_kecemasan_ranap_anak.lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.tak_bisa_istirahat_tenang,penilaian_level_kecemasan_ranap_anak.mudah_terkejut,penilaian_level_kecemasan_ranap_anak.mudah_menangis,"+
                    "penilaian_level_kecemasan_ranap_anak.gemetar,penilaian_level_kecemasan_ranap_anak.gelisah,penilaian_level_kecemasan_ranap_anak.takut_pada_gelap,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_pada_orangasing,penilaian_level_kecemasan_ranap_anak.takut_pada_kerumunan_banyak_orang,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_pada_binatang_besar,penilaian_level_kecemasan_ranap_anak.takut_pada_keramaian_lalu_lintas,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_ditinggal_sendiri,penilaian_level_kecemasan_ranap_anak.sulit_tidur,penilaian_level_kecemasan_ranap_anak.terbangun_malam_hari,"+
                    "penilaian_level_kecemasan_ranap_anak.tidur_tidak_nyeyak,penilaian_level_kecemasan_ranap_anak.mimpi_buruk,penilaian_level_kecemasan_ranap_anak.bangun_dengan_lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.banyak_mengalami_mimpi,penilaian_level_kecemasan_ranap_anak.mimpi_menakutkan,penilaian_level_kecemasan_ranap_anak.sulit_konsentrasi,"+
                    "penilaian_level_kecemasan_ranap_anak.daya_ingat_buruk,penilaian_level_kecemasan_ranap_anak.hilangnya_minat,penilaian_level_kecemasan_ranap_anak.berkurangnya_kesenangan_pada_hobi,"+
                    "penilaian_level_kecemasan_ranap_anak.sedih,penilaian_level_kecemasan_ranap_anak.bangun_dini_hari,penilaian_level_kecemasan_ranap_anak.perasaan_berubah,"+
                    "penilaian_level_kecemasan_ranap_anak.sakit_nyeri_di_otot,penilaian_level_kecemasan_ranap_anak.kaku,penilaian_level_kecemasan_ranap_anak.kedutan_otot,"+
                    "penilaian_level_kecemasan_ranap_anak.gigi_gemerutuk,penilaian_level_kecemasan_ranap_anak.suara_tidak_stabil,penilaian_level_kecemasan_ranap_anak.tinnitus,"+
                    "penilaian_level_kecemasan_ranap_anak.penglihatan_kabur,penilaian_level_kecemasan_ranap_anak.muka_merah_gejala_somatic,penilaian_level_kecemasan_ranap_anak.merasa_lemah,"+
                    "penilaian_level_kecemasan_ranap_anak.perasaan_ditusuk,penilaian_level_kecemasan_ranap_anak.takhikardia,penilaian_level_kecemasan_ranap_anak.berdebar,"+
                    "penilaian_level_kecemasan_ranap_anak.nyeri_di_dada,penilaian_level_kecemasan_ranap_anak.denyut_nadi_mengeras,penilaian_level_kecemasan_ranap_anak.perasaan_lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.detak_jantung_menghilang,penilaian_level_kecemasan_ranap_anak.merasa_tertekan,penilaian_level_kecemasan_ranap_anak.perasaan_tercekik,"+
                    "penilaian_level_kecemasan_ranap_anak.sering_menarik_napas,penilaian_level_kecemasan_ranap_anak.napas_pendek,penilaian_level_kecemasan_ranap_anak.bulu_berdiri,"+
                    "penilaian_level_kecemasan_ranap_anak.sulit_menelan,penilaian_level_kecemasan_ranap_anak.perut_melilit,penilaian_level_kecemasan_ranap_anak.ganguan_pencernaan,"+
                    "penilaian_level_kecemasan_ranap_anak.rasa_kembung,penilaian_level_kecemasan_ranap_anak.nyeri_makan,penilaian_level_kecemasan_ranap_anak.terbakar_perut,"+
                    "penilaian_level_kecemasan_ranap_anak.sukar_bab,penilaian_level_kecemasan_ranap_anak.muntah,penilaian_level_kecemasan_ranap_anak.bab_lembek,"+
                    "penilaian_level_kecemasan_ranap_anak.kehilangan_bb,penilaian_level_kecemasan_ranap_anak.mual,penilaian_level_kecemasan_ranap_anak.sering_bak,"+
                    "penilaian_level_kecemasan_ranap_anak.tidak_bisa_menahan_kencing,penilaian_level_kecemasan_ranap_anak.menjadi_dingin,penilaian_level_kecemasan_ranap_anak.manorrhagia,"+
                    "penilaian_level_kecemasan_ranap_anak.amenorrhoea,penilaian_level_kecemasan_ranap_anak.ejakulasi_praecocks,penilaian_level_kecemasan_ranap_anak.ereksi_hilang,"+
                    "penilaian_level_kecemasan_ranap_anak.impotensi,penilaian_level_kecemasan_ranap_anak.mulut_kering,penilaian_level_kecemasan_ranap_anak.muka_merah_gejala_otonom,"+
                    "penilaian_level_kecemasan_ranap_anak.mudah_berkeringat,penilaian_level_kecemasan_ranap_anak.bulu_berdiri_gejala_otonom,penilaian_level_kecemasan_ranap_anak.sakit_kepala,"+
                    "penilaian_level_kecemasan_ranap_anak.gelisah_wawancara,penilaian_level_kecemasan_ranap_anak.napas_pendek_wawancara,penilaian_level_kecemasan_ranap_anak.jari_gemetar,"+
                    "penilaian_level_kecemasan_ranap_anak.kerut_kening,penilaian_level_kecemasan_ranap_anak.muka_tegang,penilaian_level_kecemasan_ranap_anak.tonus_meningkat,"+
                    "penilaian_level_kecemasan_ranap_anak.tidak_tenang,penilaian_level_kecemasan_ranap_anak.muka_merah_wawancara,penilaian_level_kecemasan_ranap_anak.total_skor,"+
                    "penilaian_level_kecemasan_ranap_anak.keterangan_skor,penilaian_level_kecemasan_ranap_anak.nip,petugas.nama "+
                    "from penilaian_level_kecemasan_ranap_anak inner join reg_periksa on penilaian_level_kecemasan_ranap_anak.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=penilaian_level_kecemasan_ranap_anak.nip "+
                    "where penilaian_level_kecemasan_ranap_anak.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and penilaian_level_kecemasan_ranap_anak.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnLevelKecemasanActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
       Valid.pindah(evt,TCari,btnPetugas);
    }//GEN-LAST:event_TanggalKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
       Valid.pindah(evt,Tanggal,Cemas);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void FirasatBurukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FirasatBurukKeyPressed
        Valid.pindah(evt,Cemas,TakutPikiranSendiri);
    }//GEN-LAST:event_FirasatBurukKeyPressed

    private void TakutPikiranSendiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakutPikiranSendiriKeyPressed
        Valid.pindah(evt,FirasatBuruk,MudahTersinggung);
    }//GEN-LAST:event_TakutPikiranSendiriKeyPressed

    private void CemasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CemasKeyPressed
        Valid.pindah(evt,btnPetugas,FirasatBuruk);
    }//GEN-LAST:event_CemasKeyPressed

    private void MudahTersinggungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MudahTersinggungKeyPressed
        Valid.pindah(evt,TakutPikiranSendiri,MerasaTegang);
    }//GEN-LAST:event_MudahTersinggungKeyPressed

    private void MerasaTegangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerasaTegangKeyPressed
        Valid.pindah(evt,MudahTersinggung,Lesu);
    }//GEN-LAST:event_MerasaTegangKeyPressed

    private void TakBisaIstirahatTenangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakBisaIstirahatTenangKeyPressed
        Valid.pindah(evt,Lesu,MudahTerkejut);
    }//GEN-LAST:event_TakBisaIstirahatTenangKeyPressed

    private void LesuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LesuKeyPressed
        Valid.pindah(evt,MerasaTegang,TakBisaIstirahatTenang);
    }//GEN-LAST:event_LesuKeyPressed

    private void MudahTerkejutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MudahTerkejutKeyPressed
        Valid.pindah(evt,TakBisaIstirahatTenang,MudahMenangis);
    }//GEN-LAST:event_MudahTerkejutKeyPressed

    private void MudahMenangisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MudahMenangisKeyPressed
        Valid.pindah(evt,MudahTerkejut,Gemetar);
    }//GEN-LAST:event_MudahMenangisKeyPressed

    private void GemetarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GemetarKeyPressed
        Valid.pindah(evt,MudahMenangis,Gelisah);
    }//GEN-LAST:event_GemetarKeyPressed

    private void TakutPadaGelapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakutPadaGelapKeyPressed
        Valid.pindah(evt,Gelisah,TakutPadaOrangAsing);
    }//GEN-LAST:event_TakutPadaGelapKeyPressed

    private void GelisahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GelisahKeyPressed
        Valid.pindah(evt,Gemetar,TakutPadaGelap);
    }//GEN-LAST:event_GelisahKeyPressed

    private void TakutPadaOrangAsingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakutPadaOrangAsingKeyPressed
        Valid.pindah(evt,TakutPadaGelap,TakutPadaKerumunanBanyakOrang);
    }//GEN-LAST:event_TakutPadaOrangAsingKeyPressed

    private void TakutDitinggalSendiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakutDitinggalSendiriKeyPressed
        Valid.pindah(evt,TakutPadaKeramaianLaluLintas,SulitTidur);
    }//GEN-LAST:event_TakutDitinggalSendiriKeyPressed

    private void TakutPadaBinatangBesarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakutPadaBinatangBesarKeyPressed
        Valid.pindah(evt,TakutPadaKerumunanBanyakOrang,TakutPadaKeramaianLaluLintas);
    }//GEN-LAST:event_TakutPadaBinatangBesarKeyPressed

    private void TakutPadaKeramaianLaluLintasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakutPadaKeramaianLaluLintasKeyPressed
        Valid.pindah(evt,TakutPadaBinatangBesar,TakutDitinggalSendiri);
    }//GEN-LAST:event_TakutPadaKeramaianLaluLintasKeyPressed

    private void TakutPadaKerumunanBanyakOrangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakutPadaKerumunanBanyakOrangKeyPressed
        Valid.pindah(evt,TakutPadaOrangAsing,TakutPadaBinatangBesar);
    }//GEN-LAST:event_TakutPadaKerumunanBanyakOrangKeyPressed

    private void SulitTidurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SulitTidurKeyPressed
        Valid.pindah(evt,TakutDitinggalSendiri,TerbangunMalamHari);
    }//GEN-LAST:event_SulitTidurKeyPressed

    private void TerbangunMalamHariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerbangunMalamHariKeyPressed
        Valid.pindah(evt,SulitTidur,TidurTidakNyeyak);
    }//GEN-LAST:event_TerbangunMalamHariKeyPressed

    private void TidurTidakNyeyakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TidurTidakNyeyakKeyPressed
        Valid.pindah(evt,TerbangunMalamHari,MimpiBuruk);
    }//GEN-LAST:event_TidurTidakNyeyakKeyPressed

    private void MimpiBurukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MimpiBurukKeyPressed
        Valid.pindah(evt,TidurTidakNyeyak,BangunDenganLesu);
    }//GEN-LAST:event_MimpiBurukKeyPressed

    private void BangunDenganLesuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BangunDenganLesuKeyPressed
        Valid.pindah(evt,MimpiBuruk,BanyakMengalamiMimpi);
    }//GEN-LAST:event_BangunDenganLesuKeyPressed

    private void DayaIngatBurukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DayaIngatBurukKeyPressed
        Valid.pindah(evt,SulitKonsentrasi,HilangnyaMinat);
    }//GEN-LAST:event_DayaIngatBurukKeyPressed

    private void MimpiMenakutkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MimpiMenakutkanKeyPressed
        Valid.pindah(evt,BanyakMengalamiMimpi,SulitKonsentrasi);
    }//GEN-LAST:event_MimpiMenakutkanKeyPressed

    private void SulitKonsentrasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SulitKonsentrasiKeyPressed
        Valid.pindah(evt,MimpiMenakutkan,DayaIngatBuruk);
    }//GEN-LAST:event_SulitKonsentrasiKeyPressed

    private void BanyakMengalamiMimpiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BanyakMengalamiMimpiKeyPressed
        Valid.pindah(evt,BangunDenganLesu,MimpiMenakutkan);
    }//GEN-LAST:event_BanyakMengalamiMimpiKeyPressed

    private void HilangnyaMinatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HilangnyaMinatKeyPressed
        Valid.pindah(evt,DayaIngatBuruk,BerkurangnyaKesenanganPadaHobi);
    }//GEN-LAST:event_HilangnyaMinatKeyPressed

    private void BerkurangnyaKesenanganPadaHobiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BerkurangnyaKesenanganPadaHobiKeyPressed
        Valid.pindah(evt,HilangnyaMinat,Sedih);
    }//GEN-LAST:event_BerkurangnyaKesenanganPadaHobiKeyPressed

    private void SedihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SedihKeyPressed
        Valid.pindah(evt,BerkurangnyaKesenanganPadaHobi,BangunDiniHari);
    }//GEN-LAST:event_SedihKeyPressed

    private void BangunDiniHariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BangunDiniHariKeyPressed
        Valid.pindah(evt,Sedih,PerasaanBerubah);
    }//GEN-LAST:event_BangunDiniHariKeyPressed

    private void PerasaanBerubahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerasaanBerubahKeyPressed
        Valid.pindah(evt,BangunDiniHari,SakitNyeriDiOtot);
    }//GEN-LAST:event_PerasaanBerubahKeyPressed

    private void KedutanOtotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KedutanOtotKeyPressed
        Valid.pindah(evt,Kaku,GigiGemerutuk);
    }//GEN-LAST:event_KedutanOtotKeyPressed

    private void SakitNyeriDiOtotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SakitNyeriDiOtotKeyPressed
        Valid.pindah(evt,PerasaanBerubah,Kaku);
    }//GEN-LAST:event_SakitNyeriDiOtotKeyPressed

    private void KakuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KakuKeyPressed
        Valid.pindah(evt,SakitNyeriDiOtot,KedutanOtot);
    }//GEN-LAST:event_KakuKeyPressed

    private void GigiGemerutukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GigiGemerutukKeyPressed
        Valid.pindah(evt,KedutanOtot,SuaraTidakStabil);
    }//GEN-LAST:event_GigiGemerutukKeyPressed

    private void SuaraTidakStabilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuaraTidakStabilKeyPressed
        Valid.pindah(evt,GigiGemerutuk,Tinnitus);
    }//GEN-LAST:event_SuaraTidakStabilKeyPressed

    private void TinnitusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinnitusKeyPressed
        Valid.pindah(evt,SuaraTidakStabil,PenglihatanKabur);
    }//GEN-LAST:event_TinnitusKeyPressed

    private void PenglihatanKaburKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenglihatanKaburKeyPressed
        Valid.pindah(evt,Tinnitus,MukaMerahGejalaSomatic);
    }//GEN-LAST:event_PenglihatanKaburKeyPressed

    private void MukaMerahGejalaSomaticKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MukaMerahGejalaSomaticKeyPressed
        Valid.pindah(evt,PenglihatanKabur,MerasaLemah);
    }//GEN-LAST:event_MukaMerahGejalaSomaticKeyPressed

    private void MerasaLemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerasaLemahKeyPressed
        Valid.pindah(evt,MukaMerahGejalaSomatic,PerasaanDitusuk);
    }//GEN-LAST:event_MerasaLemahKeyPressed

    private void PerasaanDitusukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerasaanDitusukKeyPressed
        Valid.pindah(evt,MerasaLemah,Takhikardia);
    }//GEN-LAST:event_PerasaanDitusukKeyPressed

    private void TakhikardiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TakhikardiaKeyPressed
        Valid.pindah(evt,PerasaanDitusuk,Berdebar);
    }//GEN-LAST:event_TakhikardiaKeyPressed

    private void BerdebarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BerdebarKeyPressed
        Valid.pindah(evt,Takhikardia,NyeriDiDada);
    }//GEN-LAST:event_BerdebarKeyPressed

    private void NyeriDiDadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriDiDadaKeyPressed
        Valid.pindah(evt,Berdebar,DenyutNadiMengeras);
    }//GEN-LAST:event_NyeriDiDadaKeyPressed

    private void DenyutNadiMengerasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DenyutNadiMengerasKeyPressed
        Valid.pindah(evt,NyeriDiDada,PerasaanLesu);
    }//GEN-LAST:event_DenyutNadiMengerasKeyPressed

    private void PerasaanLesuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerasaanLesuKeyPressed
        Valid.pindah(evt,DenyutNadiMengeras,DetakJantungMenghilang);
    }//GEN-LAST:event_PerasaanLesuKeyPressed

    private void DetakJantungMenghilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetakJantungMenghilangKeyPressed
        Valid.pindah(evt,PerasaanLesu,MerasaTertekan);
    }//GEN-LAST:event_DetakJantungMenghilangKeyPressed

    private void MerasaTertekanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MerasaTertekanKeyPressed
        Valid.pindah(evt,DetakJantungMenghilang,PerasaanTercekik);
    }//GEN-LAST:event_MerasaTertekanKeyPressed

    private void NapasPendekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NapasPendekKeyPressed
        Valid.pindah(evt,SeringMenarikNapas,BuluBerdiri);
    }//GEN-LAST:event_NapasPendekKeyPressed

    private void SeringMenarikNapasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SeringMenarikNapasKeyPressed
        Valid.pindah(evt,PerasaanTercekik,NapasPendek);
    }//GEN-LAST:event_SeringMenarikNapasKeyPressed

    private void PerasaanTercekikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerasaanTercekikKeyPressed
        Valid.pindah(evt,MerasaTertekan,SeringMenarikNapas);
    }//GEN-LAST:event_PerasaanTercekikKeyPressed

    private void BuluBerdiriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuluBerdiriKeyPressed
        Valid.pindah(evt,NapasPendek,SulitMenelan);
    }//GEN-LAST:event_BuluBerdiriKeyPressed

    private void SulitMenelanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SulitMenelanKeyPressed
        Valid.pindah(evt,BuluBerdiri,PerutMelilit);
    }//GEN-LAST:event_SulitMenelanKeyPressed

    private void PerutMelilitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerutMelilitKeyPressed
        Valid.pindah(evt,SulitMenelan,GanguanPencernaan);
    }//GEN-LAST:event_PerutMelilitKeyPressed

    private void GanguanPencernaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GanguanPencernaanKeyPressed
        Valid.pindah(evt,PerutMelilit,RasaKembung);
    }//GEN-LAST:event_GanguanPencernaanKeyPressed

    private void RasaKembungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasaKembungKeyPressed
        Valid.pindah(evt,GanguanPencernaan,NyeriMakan);
    }//GEN-LAST:event_RasaKembungKeyPressed

    private void NyeriMakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NyeriMakanKeyPressed
        Valid.pindah(evt,RasaKembung,TerbakarPerut);
    }//GEN-LAST:event_NyeriMakanKeyPressed

    private void TerbakarPerutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerbakarPerutKeyPressed
        Valid.pindah(evt,NyeriMakan,SukarBAB);
    }//GEN-LAST:event_TerbakarPerutKeyPressed

    private void MualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MualKeyPressed
        Valid.pindah(evt,KehilanganBB,SeringBAK);
    }//GEN-LAST:event_MualKeyPressed

    private void MuntahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MuntahKeyPressed
        Valid.pindah(evt,SukarBAB,BABLembek);
    }//GEN-LAST:event_MuntahKeyPressed

    private void BABLembekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BABLembekKeyPressed
        Valid.pindah(evt,Muntah,KehilanganBB);
    }//GEN-LAST:event_BABLembekKeyPressed

    private void KehilanganBBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KehilanganBBKeyPressed
        Valid.pindah(evt,BABLembek,Mual);
    }//GEN-LAST:event_KehilanganBBKeyPressed

    private void SukarBABKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SukarBABKeyPressed
        Valid.pindah(evt,TerbakarPerut,Muntah);
    }//GEN-LAST:event_SukarBABKeyPressed

    private void SeringBAKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SeringBAKKeyPressed
        Valid.pindah(evt,Mual,TidakBisaMenahanKencing);
    }//GEN-LAST:event_SeringBAKKeyPressed

    private void TidakBisaMenahanKencingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TidakBisaMenahanKencingKeyPressed
        Valid.pindah(evt,SeringBAK,MenjadiDingin);
    }//GEN-LAST:event_TidakBisaMenahanKencingKeyPressed

    private void MenjadiDinginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenjadiDinginKeyPressed
        Valid.pindah(evt,TidakBisaMenahanKencing,Manorrhagia);
    }//GEN-LAST:event_MenjadiDinginKeyPressed

    private void AmenorrhoeaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AmenorrhoeaKeyPressed
        Valid.pindah(evt,Manorrhagia,EjakulasiPraecocks);
    }//GEN-LAST:event_AmenorrhoeaKeyPressed

    private void ManorrhagiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManorrhagiaKeyPressed
        Valid.pindah(evt,MenjadiDingin,Amenorrhoea);
    }//GEN-LAST:event_ManorrhagiaKeyPressed

    private void EjakulasiPraecocksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EjakulasiPraecocksKeyPressed
        Valid.pindah(evt,Amenorrhoea,EreksiHilang);
    }//GEN-LAST:event_EjakulasiPraecocksKeyPressed

    private void EreksiHilangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EreksiHilangKeyPressed
        Valid.pindah(evt,EjakulasiPraecocks,Impotensi);
    }//GEN-LAST:event_EreksiHilangKeyPressed

    private void ImpotensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImpotensiKeyPressed
        Valid.pindah(evt,EreksiHilang,MulutKering);
    }//GEN-LAST:event_ImpotensiKeyPressed

    private void MulutKeringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MulutKeringKeyPressed
        Valid.pindah(evt,Impotensi,MukaMerahGejalaOtonom);
    }//GEN-LAST:event_MulutKeringKeyPressed

    private void MukaMerahGejalaOtonomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MukaMerahGejalaOtonomKeyPressed
        Valid.pindah(evt,MulutKering,MudahBerkeringat);
    }//GEN-LAST:event_MukaMerahGejalaOtonomKeyPressed

    private void MudahBerkeringatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MudahBerkeringatKeyPressed
        Valid.pindah(evt,MukaMerahGejalaOtonom,BuluBerdiriGejalaOtonom);
    }//GEN-LAST:event_MudahBerkeringatKeyPressed

    private void BuluBerdiriGejalaOtonomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuluBerdiriGejalaOtonomKeyPressed
        Valid.pindah(evt,MudahBerkeringat,SakitKepala);
    }//GEN-LAST:event_BuluBerdiriGejalaOtonomKeyPressed

    private void SakitKepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SakitKepalaKeyPressed
        Valid.pindah(evt,BangunDenganLesu,GelisahWawancara);
    }//GEN-LAST:event_SakitKepalaKeyPressed

    private void GelisahWawancaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GelisahWawancaraKeyPressed
        Valid.pindah(evt,SakitKepala,NapasPendekWawancara);
    }//GEN-LAST:event_GelisahWawancaraKeyPressed

    private void NapasPendekWawancaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NapasPendekWawancaraKeyPressed
        Valid.pindah(evt,GelisahWawancara,JariGemetar);
    }//GEN-LAST:event_NapasPendekWawancaraKeyPressed

    private void JariGemetarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JariGemetarKeyPressed
        Valid.pindah(evt,NapasPendek,KerutKening);
    }//GEN-LAST:event_JariGemetarKeyPressed

    private void KerutKeningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KerutKeningKeyPressed
        Valid.pindah(evt,JariGemetar,MukaTegang);
    }//GEN-LAST:event_KerutKeningKeyPressed

    private void MukaMerahWawancaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MukaMerahWawancaraKeyPressed
        Valid.pindah(evt,TidakTenang,BtnSimpan);
    }//GEN-LAST:event_MukaMerahWawancaraKeyPressed

    private void TidakTenangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TidakTenangKeyPressed
        Valid.pindah(evt,TonusMeningkat,MukaMerahWawancara);
    }//GEN-LAST:event_TidakTenangKeyPressed

    private void TonusMeningkatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TonusMeningkatKeyPressed
        Valid.pindah(evt,MukaTegang,TidakTenang);
    }//GEN-LAST:event_TonusMeningkatKeyPressed

    private void MukaTegangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MukaTegangKeyPressed
        Valid.pindah(evt,KerutKening,TonusMeningkat);
    }//GEN-LAST:event_MukaTegangKeyPressed

    private void CemasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CemasItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_CemasItemStateChanged

    private void FirasatBurukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FirasatBurukItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_FirasatBurukItemStateChanged

    private void TakutPikiranSendiriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakutPikiranSendiriItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakutPikiranSendiriItemStateChanged

    private void MudahTersinggungItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MudahTersinggungItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MudahTersinggungItemStateChanged

    private void MerasaTegangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MerasaTegangItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MerasaTegangItemStateChanged

    private void LesuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LesuItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_LesuItemStateChanged

    private void TakBisaIstirahatTenangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakBisaIstirahatTenangItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakBisaIstirahatTenangItemStateChanged

    private void MudahTerkejutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MudahTerkejutItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MudahTerkejutItemStateChanged

    private void MudahMenangisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MudahMenangisItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MudahMenangisItemStateChanged

    private void GemetarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GemetarItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_GemetarItemStateChanged

    private void GelisahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GelisahItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_GelisahItemStateChanged

    private void TakutPadaGelapItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakutPadaGelapItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakutPadaGelapItemStateChanged

    private void TakutPadaOrangAsingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakutPadaOrangAsingItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakutPadaOrangAsingItemStateChanged

    private void TakutPadaKerumunanBanyakOrangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakutPadaKerumunanBanyakOrangItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakutPadaKerumunanBanyakOrangItemStateChanged

    private void TakutPadaBinatangBesarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakutPadaBinatangBesarItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakutPadaBinatangBesarItemStateChanged

    private void TakutPadaKeramaianLaluLintasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakutPadaKeramaianLaluLintasItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakutPadaKeramaianLaluLintasItemStateChanged

    private void TakutDitinggalSendiriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakutDitinggalSendiriItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakutDitinggalSendiriItemStateChanged

    private void SulitTidurItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SulitTidurItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SulitTidurItemStateChanged

    private void TerbangunMalamHariItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TerbangunMalamHariItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TerbangunMalamHariItemStateChanged

    private void TidurTidakNyeyakItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TidurTidakNyeyakItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TidurTidakNyeyakItemStateChanged

    private void MimpiBurukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MimpiBurukItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MimpiBurukItemStateChanged

    private void BangunDenganLesuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BangunDenganLesuItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_BangunDenganLesuItemStateChanged

    private void BanyakMengalamiMimpiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BanyakMengalamiMimpiItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_BanyakMengalamiMimpiItemStateChanged

    private void MimpiMenakutkanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MimpiMenakutkanItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MimpiMenakutkanItemStateChanged

    private void SulitKonsentrasiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SulitKonsentrasiItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SulitKonsentrasiItemStateChanged

    private void DayaIngatBurukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DayaIngatBurukItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_DayaIngatBurukItemStateChanged

    private void HilangnyaMinatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HilangnyaMinatItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_HilangnyaMinatItemStateChanged

    private void BerkurangnyaKesenanganPadaHobiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BerkurangnyaKesenanganPadaHobiItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_BerkurangnyaKesenanganPadaHobiItemStateChanged

    private void SedihItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SedihItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SedihItemStateChanged

    private void BangunDiniHariItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BangunDiniHariItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_BangunDiniHariItemStateChanged

    private void PerasaanBerubahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PerasaanBerubahItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_PerasaanBerubahItemStateChanged

    private void SakitNyeriDiOtotItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SakitNyeriDiOtotItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SakitNyeriDiOtotItemStateChanged

    private void KakuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KakuItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_KakuItemStateChanged

    private void KedutanOtotItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KedutanOtotItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_KedutanOtotItemStateChanged

    private void GigiGemerutukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GigiGemerutukItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_GigiGemerutukItemStateChanged

    private void SuaraTidakStabilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SuaraTidakStabilItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SuaraTidakStabilItemStateChanged

    private void TinnitusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TinnitusItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TinnitusItemStateChanged

    private void PenglihatanKaburItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PenglihatanKaburItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_PenglihatanKaburItemStateChanged

    private void MukaMerahGejalaSomaticItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MukaMerahGejalaSomaticItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MukaMerahGejalaSomaticItemStateChanged

    private void MerasaLemahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MerasaLemahItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MerasaLemahItemStateChanged

    private void PerasaanDitusukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PerasaanDitusukItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_PerasaanDitusukItemStateChanged

    private void TakhikardiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TakhikardiaItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TakhikardiaItemStateChanged

    private void BerdebarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BerdebarItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_BerdebarItemStateChanged

    private void NyeriDiDadaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NyeriDiDadaItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_NyeriDiDadaItemStateChanged

    private void DenyutNadiMengerasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DenyutNadiMengerasItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_DenyutNadiMengerasItemStateChanged

    private void PerasaanLesuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PerasaanLesuItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_PerasaanLesuItemStateChanged

    private void DetakJantungMenghilangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DetakJantungMenghilangItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_DetakJantungMenghilangItemStateChanged

    private void MerasaTertekanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MerasaTertekanItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MerasaTertekanItemStateChanged

    private void PerasaanTercekikItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PerasaanTercekikItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_PerasaanTercekikItemStateChanged

    private void SeringMenarikNapasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SeringMenarikNapasItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SeringMenarikNapasItemStateChanged

    private void NapasPendekItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NapasPendekItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_NapasPendekItemStateChanged

    private void BuluBerdiriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BuluBerdiriItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_BuluBerdiriItemStateChanged

    private void SulitMenelanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SulitMenelanItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SulitMenelanItemStateChanged

    private void PerutMelilitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PerutMelilitItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_PerutMelilitItemStateChanged

    private void GanguanPencernaanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GanguanPencernaanItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_GanguanPencernaanItemStateChanged

    private void RasaKembungItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RasaKembungItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_RasaKembungItemStateChanged

    private void NyeriMakanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NyeriMakanItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_NyeriMakanItemStateChanged

    private void TerbakarPerutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TerbakarPerutItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TerbakarPerutItemStateChanged

    private void SukarBABItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SukarBABItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SukarBABItemStateChanged

    private void MuntahItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MuntahItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MuntahItemStateChanged

    private void BABLembekItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BABLembekItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_BABLembekItemStateChanged

    private void KehilanganBBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KehilanganBBItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_KehilanganBBItemStateChanged

    private void MualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MualItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MualItemStateChanged

    private void SeringBAKItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SeringBAKItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SeringBAKItemStateChanged

    private void TidakBisaMenahanKencingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TidakBisaMenahanKencingItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TidakBisaMenahanKencingItemStateChanged

    private void MenjadiDinginItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MenjadiDinginItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MenjadiDinginItemStateChanged

    private void ManorrhagiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ManorrhagiaItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_ManorrhagiaItemStateChanged

    private void AmenorrhoeaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AmenorrhoeaItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_AmenorrhoeaItemStateChanged

    private void EjakulasiPraecocksItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EjakulasiPraecocksItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_EjakulasiPraecocksItemStateChanged

    private void EreksiHilangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EreksiHilangItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_EreksiHilangItemStateChanged

    private void ImpotensiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ImpotensiItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_ImpotensiItemStateChanged

    private void MulutKeringItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MulutKeringItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MulutKeringItemStateChanged

    private void MukaMerahGejalaOtonomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MukaMerahGejalaOtonomItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MukaMerahGejalaOtonomItemStateChanged

    private void MudahBerkeringatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MudahBerkeringatItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MudahBerkeringatItemStateChanged

    private void BuluBerdiriGejalaOtonomItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BuluBerdiriGejalaOtonomItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_BuluBerdiriGejalaOtonomItemStateChanged

    private void SakitKepalaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SakitKepalaItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_SakitKepalaItemStateChanged

    private void GelisahWawancaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_GelisahWawancaraItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_GelisahWawancaraItemStateChanged

    private void NapasPendekWawancaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NapasPendekWawancaraItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_NapasPendekWawancaraItemStateChanged

    private void JariGemetarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JariGemetarItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_JariGemetarItemStateChanged

    private void KerutKeningItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KerutKeningItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_KerutKeningItemStateChanged

    private void MukaTegangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MukaTegangItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MukaTegangItemStateChanged

    private void TonusMeningkatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TonusMeningkatItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TonusMeningkatItemStateChanged

    private void TidakTenangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TidakTenangItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_TidakTenangItemStateChanged

    private void MukaMerahWawancaraItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MukaMerahWawancaraItemStateChanged
        isTotalSkor();
    }//GEN-LAST:event_MukaMerahWawancaraItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianLevelKecemasanRanapAnak dialog = new RMPenilaianLevelKecemasanRanapAnak(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Amenorrhoea;
    private widget.ComboBox BABLembek;
    private widget.ComboBox BangunDenganLesu;
    private widget.ComboBox BangunDiniHari;
    private widget.ComboBox BanyakMengalamiMimpi;
    private widget.ComboBox Berdebar;
    private widget.ComboBox BerkurangnyaKesenanganPadaHobi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox BuluBerdiri;
    private widget.ComboBox BuluBerdiriGejalaOtonom;
    private widget.ComboBox Cemas;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox DayaIngatBuruk;
    private widget.ComboBox DenyutNadiMengeras;
    private widget.ComboBox DetakJantungMenghilang;
    private widget.ComboBox EjakulasiPraecocks;
    private widget.ComboBox EreksiHilang;
    private widget.ComboBox FirasatBuruk;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox GanguanPencernaan;
    private widget.ComboBox Gelisah;
    private widget.ComboBox GelisahWawancara;
    private widget.ComboBox Gemetar;
    private widget.ComboBox GigiGemerutuk;
    private widget.ComboBox HilangnyaMinat;
    private widget.ComboBox Impotensi;
    private widget.TextBox JK;
    private widget.ComboBox JariGemetar;
    private widget.ComboBox Kaku;
    private widget.ComboBox KedutanOtot;
    private widget.ComboBox KehilanganBB;
    private widget.ComboBox KerutKening;
    private widget.TextBox KeteranganSkor;
    private widget.TextBox KodePetugas;
    private widget.Label LCount;
    private widget.ComboBox Lesu;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Manorrhagia;
    private widget.ComboBox MenjadiDingin;
    private widget.ComboBox MerasaLemah;
    private widget.ComboBox MerasaTegang;
    private widget.ComboBox MerasaTertekan;
    private widget.ComboBox MimpiBuruk;
    private widget.ComboBox MimpiMenakutkan;
    private javax.swing.JMenuItem MnLevelKecemasan;
    private widget.ComboBox Mual;
    private widget.ComboBox MudahBerkeringat;
    private widget.ComboBox MudahMenangis;
    private widget.ComboBox MudahTerkejut;
    private widget.ComboBox MudahTersinggung;
    private widget.ComboBox MukaMerahGejalaOtonom;
    private widget.ComboBox MukaMerahGejalaSomatic;
    private widget.ComboBox MukaMerahWawancara;
    private widget.ComboBox MukaTegang;
    private widget.ComboBox MulutKering;
    private widget.ComboBox Muntah;
    private widget.TextBox NamaPetugas;
    private widget.ComboBox NapasPendek;
    private widget.ComboBox NapasPendekWawancara;
    private widget.ComboBox NyeriDiDada;
    private widget.ComboBox NyeriMakan;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PenglihatanKabur;
    private widget.ComboBox PerasaanBerubah;
    private widget.ComboBox PerasaanDitusuk;
    private widget.ComboBox PerasaanLesu;
    private widget.ComboBox PerasaanTercekik;
    private widget.ComboBox PerutMelilit;
    private widget.ComboBox RasaKembung;
    private widget.ComboBox SakitKepala;
    private widget.ComboBox SakitNyeriDiOtot;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Sedih;
    private widget.ComboBox SeringBAK;
    private widget.ComboBox SeringMenarikNapas;
    private widget.ComboBox SuaraTidakStabil;
    private widget.ComboBox SukarBAB;
    private widget.ComboBox SulitKonsentrasi;
    private widget.ComboBox SulitMenelan;
    private widget.ComboBox SulitTidur;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.ComboBox TakBisaIstirahatTenang;
    private widget.ComboBox Takhikardia;
    private widget.ComboBox TakutDitinggalSendiri;
    private widget.ComboBox TakutPadaBinatangBesar;
    private widget.ComboBox TakutPadaGelap;
    private widget.ComboBox TakutPadaKeramaianLaluLintas;
    private widget.ComboBox TakutPadaKerumunanBanyakOrang;
    private widget.ComboBox TakutPadaOrangAsing;
    private widget.ComboBox TakutPikiranSendiri;
    private widget.Tanggal Tanggal;
    private widget.ComboBox TerbakarPerut;
    private widget.ComboBox TerbangunMalamHari;
    private widget.TextBox TglLahir;
    private widget.ComboBox TidakBisaMenahanKencing;
    private widget.ComboBox TidakTenang;
    private widget.ComboBox TidurTidakNyeyak;
    private widget.ComboBox Tinnitus;
    private widget.ComboBox TonusMeningkat;
    private widget.TextBox TotalSkor;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel151;
    private widget.Label jLabel152;
    private widget.Label jLabel153;
    private widget.Label jLabel154;
    private widget.Label jLabel155;
    private widget.Label jLabel156;
    private widget.Label jLabel157;
    private widget.Label jLabel158;
    private widget.Label jLabel159;
    private widget.Label jLabel16;
    private widget.Label jLabel160;
    private widget.Label jLabel161;
    private widget.Label jLabel162;
    private widget.Label jLabel163;
    private widget.Label jLabel164;
    private widget.Label jLabel165;
    private widget.Label jLabel166;
    private widget.Label jLabel167;
    private widget.Label jLabel168;
    private widget.Label jLabel169;
    private widget.Label jLabel170;
    private widget.Label jLabel171;
    private widget.Label jLabel172;
    private widget.Label jLabel173;
    private widget.Label jLabel174;
    private widget.Label jLabel175;
    private widget.Label jLabel176;
    private widget.Label jLabel177;
    private widget.Label jLabel178;
    private widget.Label jLabel179;
    private widget.Label jLabel180;
    private widget.Label jLabel181;
    private widget.Label jLabel182;
    private widget.Label jLabel183;
    private widget.Label jLabel184;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel6;
    private widget.Label jLabel61;
    private widget.Label jLabel63;
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
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,penilaian_level_kecemasan_ranap_anak.tanggal,"+
                    "penilaian_level_kecemasan_ranap_anak.cemas,penilaian_level_kecemasan_ranap_anak.firasat_buruk,penilaian_level_kecemasan_ranap_anak.takut_pikiran_sendiri,"+
                    "penilaian_level_kecemasan_ranap_anak.mudah_tersinggung,penilaian_level_kecemasan_ranap_anak.merasa_tegang,penilaian_level_kecemasan_ranap_anak.lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.tak_bisa_istirahat_tenang,penilaian_level_kecemasan_ranap_anak.mudah_terkejut,penilaian_level_kecemasan_ranap_anak.mudah_menangis,"+
                    "penilaian_level_kecemasan_ranap_anak.gemetar,penilaian_level_kecemasan_ranap_anak.gelisah,penilaian_level_kecemasan_ranap_anak.takut_pada_gelap,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_pada_orangasing,penilaian_level_kecemasan_ranap_anak.takut_pada_kerumunan_banyak_orang,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_pada_binatang_besar,penilaian_level_kecemasan_ranap_anak.takut_pada_keramaian_lalu_lintas,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_ditinggal_sendiri,penilaian_level_kecemasan_ranap_anak.sulit_tidur,penilaian_level_kecemasan_ranap_anak.terbangun_malam_hari,"+
                    "penilaian_level_kecemasan_ranap_anak.tidur_tidak_nyeyak,penilaian_level_kecemasan_ranap_anak.mimpi_buruk,penilaian_level_kecemasan_ranap_anak.bangun_dengan_lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.banyak_mengalami_mimpi,penilaian_level_kecemasan_ranap_anak.mimpi_menakutkan,penilaian_level_kecemasan_ranap_anak.sulit_konsentrasi,"+
                    "penilaian_level_kecemasan_ranap_anak.daya_ingat_buruk,penilaian_level_kecemasan_ranap_anak.hilangnya_minat,penilaian_level_kecemasan_ranap_anak.berkurangnya_kesenangan_pada_hobi,"+
                    "penilaian_level_kecemasan_ranap_anak.sedih,penilaian_level_kecemasan_ranap_anak.bangun_dini_hari,penilaian_level_kecemasan_ranap_anak.perasaan_berubah,"+
                    "penilaian_level_kecemasan_ranap_anak.sakit_nyeri_di_otot,penilaian_level_kecemasan_ranap_anak.kaku,penilaian_level_kecemasan_ranap_anak.kedutan_otot,"+
                    "penilaian_level_kecemasan_ranap_anak.gigi_gemerutuk,penilaian_level_kecemasan_ranap_anak.suara_tidak_stabil,penilaian_level_kecemasan_ranap_anak.tinnitus,"+
                    "penilaian_level_kecemasan_ranap_anak.penglihatan_kabur,penilaian_level_kecemasan_ranap_anak.muka_merah_gejala_somatic,penilaian_level_kecemasan_ranap_anak.merasa_lemah,"+
                    "penilaian_level_kecemasan_ranap_anak.perasaan_ditusuk,penilaian_level_kecemasan_ranap_anak.takhikardia,penilaian_level_kecemasan_ranap_anak.berdebar,"+
                    "penilaian_level_kecemasan_ranap_anak.nyeri_di_dada,penilaian_level_kecemasan_ranap_anak.denyut_nadi_mengeras,penilaian_level_kecemasan_ranap_anak.perasaan_lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.detak_jantung_menghilang,penilaian_level_kecemasan_ranap_anak.merasa_tertekan,penilaian_level_kecemasan_ranap_anak.perasaan_tercekik,"+
                    "penilaian_level_kecemasan_ranap_anak.sering_menarik_napas,penilaian_level_kecemasan_ranap_anak.napas_pendek,penilaian_level_kecemasan_ranap_anak.bulu_berdiri,"+
                    "penilaian_level_kecemasan_ranap_anak.sulit_menelan,penilaian_level_kecemasan_ranap_anak.perut_melilit,penilaian_level_kecemasan_ranap_anak.ganguan_pencernaan,"+
                    "penilaian_level_kecemasan_ranap_anak.rasa_kembung,penilaian_level_kecemasan_ranap_anak.nyeri_makan,penilaian_level_kecemasan_ranap_anak.terbakar_perut,"+
                    "penilaian_level_kecemasan_ranap_anak.sukar_bab,penilaian_level_kecemasan_ranap_anak.muntah,penilaian_level_kecemasan_ranap_anak.bab_lembek,"+
                    "penilaian_level_kecemasan_ranap_anak.kehilangan_bb,penilaian_level_kecemasan_ranap_anak.mual,penilaian_level_kecemasan_ranap_anak.sering_bak,"+
                    "penilaian_level_kecemasan_ranap_anak.tidak_bisa_menahan_kencing,penilaian_level_kecemasan_ranap_anak.menjadi_dingin,penilaian_level_kecemasan_ranap_anak.manorrhagia,"+
                    "penilaian_level_kecemasan_ranap_anak.amenorrhoea,penilaian_level_kecemasan_ranap_anak.ejakulasi_praecocks,penilaian_level_kecemasan_ranap_anak.ereksi_hilang,"+
                    "penilaian_level_kecemasan_ranap_anak.impotensi,penilaian_level_kecemasan_ranap_anak.mulut_kering,penilaian_level_kecemasan_ranap_anak.muka_merah_gejala_otonom,"+
                    "penilaian_level_kecemasan_ranap_anak.mudah_berkeringat,penilaian_level_kecemasan_ranap_anak.bulu_berdiri_gejala_otonom,penilaian_level_kecemasan_ranap_anak.sakit_kepala,"+
                    "penilaian_level_kecemasan_ranap_anak.gelisah_wawancara,penilaian_level_kecemasan_ranap_anak.napas_pendek_wawancara,penilaian_level_kecemasan_ranap_anak.jari_gemetar,"+
                    "penilaian_level_kecemasan_ranap_anak.kerut_kening,penilaian_level_kecemasan_ranap_anak.muka_tegang,penilaian_level_kecemasan_ranap_anak.tonus_meningkat,"+
                    "penilaian_level_kecemasan_ranap_anak.tidak_tenang,penilaian_level_kecemasan_ranap_anak.muka_merah_wawancara,penilaian_level_kecemasan_ranap_anak.total_skor,"+
                    "penilaian_level_kecemasan_ranap_anak.keterangan_skor,penilaian_level_kecemasan_ranap_anak.nip,petugas.nama "+
                    "from penilaian_level_kecemasan_ranap_anak inner join reg_periksa on penilaian_level_kecemasan_ranap_anak.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=penilaian_level_kecemasan_ranap_anak.nip "+
                    "where penilaian_level_kecemasan_ranap_anak.tanggal between ? and ? order by penilaian_level_kecemasan_ranap_anak.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,penilaian_level_kecemasan_ranap_anak.tanggal,"+
                    "penilaian_level_kecemasan_ranap_anak.cemas,penilaian_level_kecemasan_ranap_anak.firasat_buruk,penilaian_level_kecemasan_ranap_anak.takut_pikiran_sendiri,"+
                    "penilaian_level_kecemasan_ranap_anak.mudah_tersinggung,penilaian_level_kecemasan_ranap_anak.merasa_tegang,penilaian_level_kecemasan_ranap_anak.lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.tak_bisa_istirahat_tenang,penilaian_level_kecemasan_ranap_anak.mudah_terkejut,penilaian_level_kecemasan_ranap_anak.mudah_menangis,"+
                    "penilaian_level_kecemasan_ranap_anak.gemetar,penilaian_level_kecemasan_ranap_anak.gelisah,penilaian_level_kecemasan_ranap_anak.takut_pada_gelap,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_pada_orangasing,penilaian_level_kecemasan_ranap_anak.takut_pada_kerumunan_banyak_orang,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_pada_binatang_besar,penilaian_level_kecemasan_ranap_anak.takut_pada_keramaian_lalu_lintas,"+
                    "penilaian_level_kecemasan_ranap_anak.takut_ditinggal_sendiri,penilaian_level_kecemasan_ranap_anak.sulit_tidur,penilaian_level_kecemasan_ranap_anak.terbangun_malam_hari,"+
                    "penilaian_level_kecemasan_ranap_anak.tidur_tidak_nyeyak,penilaian_level_kecemasan_ranap_anak.mimpi_buruk,penilaian_level_kecemasan_ranap_anak.bangun_dengan_lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.banyak_mengalami_mimpi,penilaian_level_kecemasan_ranap_anak.mimpi_menakutkan,penilaian_level_kecemasan_ranap_anak.sulit_konsentrasi,"+
                    "penilaian_level_kecemasan_ranap_anak.daya_ingat_buruk,penilaian_level_kecemasan_ranap_anak.hilangnya_minat,penilaian_level_kecemasan_ranap_anak.berkurangnya_kesenangan_pada_hobi,"+
                    "penilaian_level_kecemasan_ranap_anak.sedih,penilaian_level_kecemasan_ranap_anak.bangun_dini_hari,penilaian_level_kecemasan_ranap_anak.perasaan_berubah,"+
                    "penilaian_level_kecemasan_ranap_anak.sakit_nyeri_di_otot,penilaian_level_kecemasan_ranap_anak.kaku,penilaian_level_kecemasan_ranap_anak.kedutan_otot,"+
                    "penilaian_level_kecemasan_ranap_anak.gigi_gemerutuk,penilaian_level_kecemasan_ranap_anak.suara_tidak_stabil,penilaian_level_kecemasan_ranap_anak.tinnitus,"+
                    "penilaian_level_kecemasan_ranap_anak.penglihatan_kabur,penilaian_level_kecemasan_ranap_anak.muka_merah_gejala_somatic,penilaian_level_kecemasan_ranap_anak.merasa_lemah,"+
                    "penilaian_level_kecemasan_ranap_anak.perasaan_ditusuk,penilaian_level_kecemasan_ranap_anak.takhikardia,penilaian_level_kecemasan_ranap_anak.berdebar,"+
                    "penilaian_level_kecemasan_ranap_anak.nyeri_di_dada,penilaian_level_kecemasan_ranap_anak.denyut_nadi_mengeras,penilaian_level_kecemasan_ranap_anak.perasaan_lesu,"+
                    "penilaian_level_kecemasan_ranap_anak.detak_jantung_menghilang,penilaian_level_kecemasan_ranap_anak.merasa_tertekan,penilaian_level_kecemasan_ranap_anak.perasaan_tercekik,"+
                    "penilaian_level_kecemasan_ranap_anak.sering_menarik_napas,penilaian_level_kecemasan_ranap_anak.napas_pendek,penilaian_level_kecemasan_ranap_anak.bulu_berdiri,"+
                    "penilaian_level_kecemasan_ranap_anak.sulit_menelan,penilaian_level_kecemasan_ranap_anak.perut_melilit,penilaian_level_kecemasan_ranap_anak.ganguan_pencernaan,"+
                    "penilaian_level_kecemasan_ranap_anak.rasa_kembung,penilaian_level_kecemasan_ranap_anak.nyeri_makan,penilaian_level_kecemasan_ranap_anak.terbakar_perut,"+
                    "penilaian_level_kecemasan_ranap_anak.sukar_bab,penilaian_level_kecemasan_ranap_anak.muntah,penilaian_level_kecemasan_ranap_anak.bab_lembek,"+
                    "penilaian_level_kecemasan_ranap_anak.kehilangan_bb,penilaian_level_kecemasan_ranap_anak.mual,penilaian_level_kecemasan_ranap_anak.sering_bak,"+
                    "penilaian_level_kecemasan_ranap_anak.tidak_bisa_menahan_kencing,penilaian_level_kecemasan_ranap_anak.menjadi_dingin,penilaian_level_kecemasan_ranap_anak.manorrhagia,"+
                    "penilaian_level_kecemasan_ranap_anak.amenorrhoea,penilaian_level_kecemasan_ranap_anak.ejakulasi_praecocks,penilaian_level_kecemasan_ranap_anak.ereksi_hilang,"+
                    "penilaian_level_kecemasan_ranap_anak.impotensi,penilaian_level_kecemasan_ranap_anak.mulut_kering,penilaian_level_kecemasan_ranap_anak.muka_merah_gejala_otonom,"+
                    "penilaian_level_kecemasan_ranap_anak.mudah_berkeringat,penilaian_level_kecemasan_ranap_anak.bulu_berdiri_gejala_otonom,penilaian_level_kecemasan_ranap_anak.sakit_kepala,"+
                    "penilaian_level_kecemasan_ranap_anak.gelisah_wawancara,penilaian_level_kecemasan_ranap_anak.napas_pendek_wawancara,penilaian_level_kecemasan_ranap_anak.jari_gemetar,"+
                    "penilaian_level_kecemasan_ranap_anak.kerut_kening,penilaian_level_kecemasan_ranap_anak.muka_tegang,penilaian_level_kecemasan_ranap_anak.tonus_meningkat,"+
                    "penilaian_level_kecemasan_ranap_anak.tidak_tenang,penilaian_level_kecemasan_ranap_anak.muka_merah_wawancara,penilaian_level_kecemasan_ranap_anak.total_skor,"+
                    "penilaian_level_kecemasan_ranap_anak.keterangan_skor,penilaian_level_kecemasan_ranap_anak.nip,petugas.nama "+
                    "from penilaian_level_kecemasan_ranap_anak inner join reg_periksa on penilaian_level_kecemasan_ranap_anak.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=penilaian_level_kecemasan_ranap_anak.nip "+
                    "where penilaian_level_kecemasan_ranap_anak.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or petugas.nama like ? or penilaian_level_kecemasan_ranap_anak.nip like ?) order by penilaian_level_kecemasan_ranap_anak.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("cemas"),rs.getString("firasat_buruk"),rs.getString("takut_pikiran_sendiri"),
                        rs.getString("mudah_tersinggung"),rs.getString("merasa_tegang"),rs.getString("lesu"),rs.getString("tak_bisa_istirahat_tenang"),
                        rs.getString("mudah_terkejut"),rs.getString("mudah_menangis"),rs.getString("gemetar"),rs.getString("gelisah"),
                        rs.getString("takut_pada_gelap"),rs.getString("takut_pada_orangasing"),rs.getString("takut_pada_kerumunan_banyak_orang"),
                        rs.getString("takut_pada_binatang_besar"),rs.getString("takut_pada_keramaian_lalu_lintas"),rs.getString("takut_ditinggal_sendiri"),
                        rs.getString("sulit_tidur"),rs.getString("terbangun_malam_hari"),rs.getString("tidur_tidak_nyeyak"),rs.getString("mimpi_buruk"),
                        rs.getString("bangun_dengan_lesu"),rs.getString("banyak_mengalami_mimpi"),rs.getString("mimpi_menakutkan"),
                        rs.getString("sulit_konsentrasi"),rs.getString("daya_ingat_buruk"),rs.getString("hilangnya_minat"),
                        rs.getString("berkurangnya_kesenangan_pada_hobi"),rs.getString("sedih"),rs.getString("bangun_dini_hari"),
                        rs.getString("perasaan_berubah"),rs.getString("sakit_nyeri_di_otot"),rs.getString("kaku"),rs.getString("kedutan_otot"),
                        rs.getString("gigi_gemerutuk"),rs.getString("suara_tidak_stabil"),rs.getString("tinnitus"),rs.getString("penglihatan_kabur"),
                        rs.getString("muka_merah_gejala_somatic"),rs.getString("merasa_lemah"),rs.getString("perasaan_ditusuk"),rs.getString("takhikardia"),
                        rs.getString("berdebar"),rs.getString("nyeri_di_dada"),rs.getString("denyut_nadi_mengeras"),rs.getString("perasaan_lesu"),
                        rs.getString("detak_jantung_menghilang"),rs.getString("merasa_tertekan"),rs.getString("perasaan_tercekik"),
                        rs.getString("sering_menarik_napas"),rs.getString("napas_pendek"),rs.getString("bulu_berdiri"),rs.getString("sulit_menelan"),
                        rs.getString("perut_melilit"),rs.getString("ganguan_pencernaan"),rs.getString("rasa_kembung"),rs.getString("nyeri_makan"),
                        rs.getString("terbakar_perut"),rs.getString("sukar_bab"),rs.getString("muntah"),rs.getString("bab_lembek"),
                        rs.getString("kehilangan_bb"),rs.getString("mual"),rs.getString("sering_bak"),rs.getString("tidak_bisa_menahan_kencing"),
                        rs.getString("menjadi_dingin"),rs.getString("manorrhagia"),rs.getString("amenorrhoea"),rs.getString("ejakulasi_praecocks"),
                        rs.getString("ereksi_hilang"),rs.getString("impotensi"),rs.getString("mulut_kering"),rs.getString("muka_merah_gejala_otonom"),
                        rs.getString("mudah_berkeringat"),rs.getString("bulu_berdiri_gejala_otonom"),rs.getString("sakit_kepala"),
                        rs.getString("gelisah_wawancara"),rs.getString("napas_pendek_wawancara"),rs.getString("jari_gemetar"),rs.getString("kerut_kening"),
                        rs.getString("muka_tegang"),rs.getString("tonus_meningkat"),rs.getString("tidak_tenang"),rs.getString("muka_merah_wawancara"),
                        rs.getString("total_skor"),rs.getString("keterangan_skor"),rs.getString("nip"),rs.getString("nama")
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
        Cemas.setSelectedIndex(0);
        FirasatBuruk.setSelectedIndex(0);
        TakutPikiranSendiri.setSelectedIndex(0);
        MudahTersinggung.setSelectedIndex(0);
        MerasaTegang.setSelectedIndex(0);
        Lesu.setSelectedIndex(0);
        TakBisaIstirahatTenang.setSelectedIndex(0);
        MudahTerkejut.setSelectedIndex(0);
        MudahMenangis.setSelectedIndex(0);
        Gemetar.setSelectedIndex(0);
        Gelisah.setSelectedIndex(0);
        TakutPadaGelap.setSelectedIndex(0);
        TakutPadaOrangAsing.setSelectedIndex(0);
        TakutPadaKerumunanBanyakOrang.setSelectedIndex(0);
        TakutPadaBinatangBesar.setSelectedIndex(0);
        TakutPadaKeramaianLaluLintas.setSelectedIndex(0);
        TakutDitinggalSendiri.setSelectedIndex(0);
        SulitTidur.setSelectedIndex(0);
        TerbangunMalamHari.setSelectedIndex(0);
        TidurTidakNyeyak.setSelectedIndex(0);
        MimpiBuruk.setSelectedIndex(0);
        BangunDenganLesu.setSelectedIndex(0);
        BanyakMengalamiMimpi.setSelectedIndex(0);
        MimpiMenakutkan.setSelectedIndex(0);
        SulitKonsentrasi.setSelectedIndex(0);
        DayaIngatBuruk.setSelectedIndex(0);
        HilangnyaMinat.setSelectedIndex(0);
        BerkurangnyaKesenanganPadaHobi.setSelectedIndex(0);
        Sedih.setSelectedIndex(0);
        BangunDiniHari.setSelectedIndex(0);
        PerasaanBerubah.setSelectedIndex(0);
        SakitNyeriDiOtot.setSelectedIndex(0);
        Kaku.setSelectedIndex(0);
        KedutanOtot.setSelectedIndex(0);
        GigiGemerutuk.setSelectedIndex(0);
        SuaraTidakStabil.setSelectedIndex(0);
        Tinnitus.setSelectedIndex(0);
        PenglihatanKabur.setSelectedIndex(0);
        MukaMerahGejalaSomatic.setSelectedIndex(0);
        MerasaLemah.setSelectedIndex(0);
        PerasaanDitusuk.setSelectedIndex(0);
        Takhikardia.setSelectedIndex(0);
        Berdebar.setSelectedIndex(0);
        NyeriDiDada.setSelectedIndex(0);
        DenyutNadiMengeras.setSelectedIndex(0);
        PerasaanLesu.setSelectedIndex(0);
        DetakJantungMenghilang.setSelectedIndex(0);
        MerasaTertekan.setSelectedIndex(0);
        PerasaanTercekik.setSelectedIndex(0);
        SeringMenarikNapas.setSelectedIndex(0);
        NapasPendek.setSelectedIndex(0);
        BuluBerdiri.setSelectedIndex(0);
        SulitMenelan.setSelectedIndex(0);
        PerutMelilit.setSelectedIndex(0);
        GanguanPencernaan.setSelectedIndex(0);
        RasaKembung.setSelectedIndex(0);
        NyeriMakan.setSelectedIndex(0);
        TerbakarPerut.setSelectedIndex(0);
        SukarBAB.setSelectedIndex(0);
        Muntah.setSelectedIndex(0);
        BABLembek.setSelectedIndex(0);
        KehilanganBB.setSelectedIndex(0);
        Mual.setSelectedIndex(0);
        SeringBAK.setSelectedIndex(0);
        TidakBisaMenahanKencing.setSelectedIndex(0);
        MenjadiDingin.setSelectedIndex(0);
        Manorrhagia.setSelectedIndex(0);
        Amenorrhoea.setSelectedIndex(0);
        EjakulasiPraecocks.setSelectedIndex(0);
        EreksiHilang.setSelectedIndex(0);
        Impotensi.setSelectedIndex(0);
        MulutKering.setSelectedIndex(0);
        MukaMerahGejalaOtonom.setSelectedIndex(0);
        MudahBerkeringat.setSelectedIndex(0);
        BuluBerdiriGejalaOtonom.setSelectedIndex(0);
        SakitKepala.setSelectedIndex(0);
        GelisahWawancara.setSelectedIndex(0);
        NapasPendekWawancara.setSelectedIndex(0);
        JariGemetar.setSelectedIndex(0);
        KerutKening.setSelectedIndex(0);
        MukaTegang.setSelectedIndex(0);
        TonusMeningkat.setSelectedIndex(0);
        TidakTenang.setSelectedIndex(0);
        MukaMerahWawancara.setSelectedIndex(0);
        TotalSkor.setText("0");
        KeteranganSkor.setText("Tidak Mengalami Kecemasan");
        Tanggal.setDate(new Date());
        Cemas.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Cemas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            FirasatBuruk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            TakutPikiranSendiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            MudahTersinggung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            MerasaTegang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Lesu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            TakBisaIstirahatTenang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            MudahTerkejut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            MudahMenangis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Gemetar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Gelisah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            TakutPadaGelap.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            TakutPadaOrangAsing.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            TakutPadaKerumunanBanyakOrang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            TakutPadaBinatangBesar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            TakutPadaKeramaianLaluLintas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            TakutDitinggalSendiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            SulitTidur.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            TerbangunMalamHari.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            TidurTidakNyeyak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            MimpiBuruk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            BangunDenganLesu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            BanyakMengalamiMimpi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            MimpiMenakutkan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            SulitKonsentrasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            DayaIngatBuruk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            HilangnyaMinat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            BerkurangnyaKesenanganPadaHobi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Sedih.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            BangunDiniHari.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            PerasaanBerubah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            SakitNyeriDiOtot.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Kaku.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            KedutanOtot.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            GigiGemerutuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            SuaraTidakStabil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Tinnitus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            PenglihatanKabur.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            MukaMerahGejalaSomatic.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            MerasaLemah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            PerasaanDitusuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Takhikardia.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Berdebar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            NyeriDiDada.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            DenyutNadiMengeras.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            PerasaanLesu.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            DetakJantungMenghilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            MerasaTertekan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            PerasaanTercekik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            SeringMenarikNapas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            NapasPendek.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            BuluBerdiri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            SulitMenelan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            PerutMelilit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            GanguanPencernaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            RasaKembung.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            NyeriMakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            TerbakarPerut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            SukarBAB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            Muntah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            BABLembek.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            KehilanganBB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            Mual.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            SeringBAK.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            TidakBisaMenahanKencing.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            MenjadiDingin.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Manorrhagia.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            Amenorrhoea.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            EjakulasiPraecocks.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            EreksiHilang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            Impotensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            MulutKering.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            MukaMerahGejalaOtonom.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            MudahBerkeringat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            BuluBerdiriGejalaOtonom.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            SakitKepala.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            GelisahWawancara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            NapasPendekWawancara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            JariGemetar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            KerutKening.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            MukaTegang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            TonusMeningkat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            TidakTenang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            MukaMerahWawancara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            TotalSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            KeteranganSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-182));
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
        BtnSimpan.setEnabled(akses.getpenilaian_level_kecemasan_ranap_anak());
        BtnHapus.setEnabled(akses.getpenilaian_level_kecemasan_ranap_anak());
        BtnEdit.setEnabled(akses.getpenilaian_level_kecemasan_ranap_anak());
        if(akses.getjml2()>=1){
            KodePetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KodePetugas.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(KodePetugas.getText()));
            if(NamaPetugas.getText().equals("")){
                KodePetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        } 
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_level_kecemasan_ranap_anak","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,cemas=?,firasat_buruk=?,takut_pikiran_sendiri=?,mudah_tersinggung=?,merasa_tegang=?,lesu=?,tak_bisa_istirahat_tenang=?,mudah_terkejut=?,mudah_menangis=?,gemetar=?,gelisah=?,takut_pada_gelap=?,"+
                "takut_pada_orangasing=?,takut_pada_kerumunan_banyak_orang=?,takut_pada_binatang_besar=?,takut_pada_keramaian_lalu_lintas=?,takut_ditinggal_sendiri=?,sulit_tidur=?,terbangun_malam_hari=?,tidur_tidak_nyeyak=?,mimpi_buruk=?,bangun_dengan_lesu=?,banyak_mengalami_mimpi=?,mimpi_menakutkan=?,"+
                "sulit_konsentrasi=?,daya_ingat_buruk=?,hilangnya_minat=?,berkurangnya_kesenangan_pada_hobi=?,sedih=?,bangun_dini_hari=?,perasaan_berubah=?,sakit_nyeri_di_otot=?,kaku=?,kedutan_otot=?,gigi_gemerutuk=?,suara_tidak_stabil=?,tinnitus=?,penglihatan_kabur=?,muka_merah_gejala_somatic=?,merasa_lemah=?,"+
                "perasaan_ditusuk=?,takhikardia=?,berdebar=?,nyeri_di_dada=?,denyut_nadi_mengeras=?,perasaan_lesu=?,detak_jantung_menghilang=?,merasa_tertekan=?,perasaan_tercekik=?,sering_menarik_napas=?,napas_pendek=?,bulu_berdiri=?,sulit_menelan=?,perut_melilit=?,ganguan_pencernaan=?,rasa_kembung=?,nyeri_makan=?,"+
                "terbakar_perut=?,sukar_bab=?,muntah=?,bab_lembek=?,kehilangan_bb=?,mual=?,sering_bak=?,tidak_bisa_menahan_kencing=?,menjadi_dingin=?,manorrhagia=?,amenorrhoea=?,ejakulasi_praecocks=?,ereksi_hilang=?,impotensi=?,mulut_kering=?,muka_merah_gejala_otonom=?,mudah_berkeringat=?,bulu_berdiri_gejala_otonom=?,"+
                "sakit_kepala=?,gelisah_wawancara=?,napas_pendek_wawancara=?,jari_gemetar=?,kerut_kening=?,muka_tegang=?,tonus_meningkat=?,tidak_tenang=?,muka_merah_wawancara=?,total_skor=?,keterangan_skor=?,nip=?",91,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),Cemas.getSelectedItem().toString(),FirasatBuruk.getSelectedItem().toString(),TakutPikiranSendiri.getSelectedItem().toString(),MudahTersinggung.getSelectedItem().toString(),
                MerasaTegang.getSelectedItem().toString(),Lesu.getSelectedItem().toString(),TakBisaIstirahatTenang.getSelectedItem().toString(),MudahTerkejut.getSelectedItem().toString(),MudahMenangis.getSelectedItem().toString(),Gemetar.getSelectedItem().toString(),Gelisah.getSelectedItem().toString(),
                TakutPadaGelap.getSelectedItem().toString(),TakutPadaOrangAsing.getSelectedItem().toString(),TakutPadaKerumunanBanyakOrang.getSelectedItem().toString(),TakutPadaBinatangBesar.getSelectedItem().toString(),TakutPadaKeramaianLaluLintas.getSelectedItem().toString(),
                TakutDitinggalSendiri.getSelectedItem().toString(),SulitTidur.getSelectedItem().toString(),TerbangunMalamHari.getSelectedItem().toString(),TidurTidakNyeyak.getSelectedItem().toString(),MimpiBuruk.getSelectedItem().toString(),BangunDenganLesu.getSelectedItem().toString(),
                BanyakMengalamiMimpi.getSelectedItem().toString(),MimpiMenakutkan.getSelectedItem().toString(),SulitKonsentrasi.getSelectedItem().toString(),DayaIngatBuruk.getSelectedItem().toString(),HilangnyaMinat.getSelectedItem().toString(),BerkurangnyaKesenanganPadaHobi.getSelectedItem().toString(),
                Sedih.getSelectedItem().toString(),BangunDiniHari.getSelectedItem().toString(),PerasaanBerubah.getSelectedItem().toString(),SakitNyeriDiOtot.getSelectedItem().toString(),Kaku.getSelectedItem().toString(),KedutanOtot.getSelectedItem().toString(),GigiGemerutuk.getSelectedItem().toString(),
                SuaraTidakStabil.getSelectedItem().toString(),Tinnitus.getSelectedItem().toString(),PenglihatanKabur.getSelectedItem().toString(),MukaMerahGejalaSomatic.getSelectedItem().toString(),MerasaLemah.getSelectedItem().toString(),PerasaanDitusuk.getSelectedItem().toString(),
                Takhikardia.getSelectedItem().toString(),Berdebar.getSelectedItem().toString(),NyeriDiDada.getSelectedItem().toString(),DenyutNadiMengeras.getSelectedItem().toString(),PerasaanLesu.getSelectedItem().toString(),DetakJantungMenghilang.getSelectedItem().toString(),
                MerasaTertekan.getSelectedItem().toString(),PerasaanTercekik.getSelectedItem().toString(),SeringMenarikNapas.getSelectedItem().toString(),NapasPendek.getSelectedItem().toString(),BuluBerdiri.getSelectedItem().toString(),SulitMenelan.getSelectedItem().toString(),
                PerutMelilit.getSelectedItem().toString(),GanguanPencernaan.getSelectedItem().toString(),RasaKembung.getSelectedItem().toString(),NyeriMakan.getSelectedItem().toString(),TerbakarPerut.getSelectedItem().toString(),SukarBAB.getSelectedItem().toString(),Muntah.getSelectedItem().toString(),
                BABLembek.getSelectedItem().toString(),KehilanganBB.getSelectedItem().toString(),Mual.getSelectedItem().toString(),SeringBAK.getSelectedItem().toString(),TidakBisaMenahanKencing.getSelectedItem().toString(),MenjadiDingin.getSelectedItem().toString(),Manorrhagia.getSelectedItem().toString(),
                Amenorrhoea.getSelectedItem().toString(),EjakulasiPraecocks.getSelectedItem().toString(),EreksiHilang.getSelectedItem().toString(),Impotensi.getSelectedItem().toString(),MulutKering.getSelectedItem().toString(),MukaMerahGejalaOtonom.getSelectedItem().toString(),
                MudahBerkeringat.getSelectedItem().toString(),BuluBerdiriGejalaOtonom.getSelectedItem().toString(),SakitKepala.getSelectedItem().toString(),GelisahWawancara.getSelectedItem().toString(),NapasPendekWawancara.getSelectedItem().toString(),JariGemetar.getSelectedItem().toString(),
                KerutKening.getSelectedItem().toString(),MukaTegang.getSelectedItem().toString(),TonusMeningkat.getSelectedItem().toString(),TidakTenang.getSelectedItem().toString(),MukaMerahWawancara.getSelectedItem().toString(),TotalSkor.getText(),KeteranganSkor.getText(),KodePetugas.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Cemas.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(FirasatBuruk.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(TakutPikiranSendiri.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(MudahTersinggung.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(MerasaTegang.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Lesu.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(TakBisaIstirahatTenang.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(MudahTerkejut.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(MudahMenangis.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Gemetar.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Gelisah.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(TakutPadaGelap.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(TakutPadaOrangAsing.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(TakutPadaKerumunanBanyakOrang.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(TakutPadaBinatangBesar.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(TakutPadaKeramaianLaluLintas.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(TakutDitinggalSendiri.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(SulitTidur.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(TerbangunMalamHari.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(TidurTidakNyeyak.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(MimpiBuruk.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(BangunDenganLesu.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(BanyakMengalamiMimpi.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(MimpiMenakutkan.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(SulitKonsentrasi.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(DayaIngatBuruk.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(HilangnyaMinat.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(BerkurangnyaKesenanganPadaHobi.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(Sedih.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
            tbObat.setValueAt(BangunDiniHari.getSelectedItem().toString(),tbObat.getSelectedRow(),35);
            tbObat.setValueAt(PerasaanBerubah.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
            tbObat.setValueAt(SakitNyeriDiOtot.getSelectedItem().toString(),tbObat.getSelectedRow(),37);
            tbObat.setValueAt(Kaku.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
            tbObat.setValueAt(KedutanOtot.getSelectedItem().toString(),tbObat.getSelectedRow(),39);
            tbObat.setValueAt(GigiGemerutuk.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
            tbObat.setValueAt(SuaraTidakStabil.getSelectedItem().toString(),tbObat.getSelectedRow(),41);
            tbObat.setValueAt(Tinnitus.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
            tbObat.setValueAt(PenglihatanKabur.getSelectedItem().toString(),tbObat.getSelectedRow(),43);
            tbObat.setValueAt(MukaMerahGejalaSomatic.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
            tbObat.setValueAt(MerasaLemah.getSelectedItem().toString(),tbObat.getSelectedRow(),45);
            tbObat.setValueAt(PerasaanDitusuk.getSelectedItem().toString(),tbObat.getSelectedRow(),46);
            tbObat.setValueAt(Takhikardia.getSelectedItem().toString(),tbObat.getSelectedRow(),47);
            tbObat.setValueAt(Berdebar.getSelectedItem().toString(),tbObat.getSelectedRow(),48);
            tbObat.setValueAt(NyeriDiDada.getSelectedItem().toString(),tbObat.getSelectedRow(),49);
            tbObat.setValueAt(DenyutNadiMengeras.getSelectedItem().toString(),tbObat.getSelectedRow(),50);
            tbObat.setValueAt(PerasaanLesu.getSelectedItem().toString(),tbObat.getSelectedRow(),51);
            tbObat.setValueAt(DetakJantungMenghilang.getSelectedItem().toString(),tbObat.getSelectedRow(),52);
            tbObat.setValueAt(MerasaTertekan.getSelectedItem().toString(),tbObat.getSelectedRow(),53);
            tbObat.setValueAt(PerasaanTercekik.getSelectedItem().toString(),tbObat.getSelectedRow(),54);
            tbObat.setValueAt(SeringMenarikNapas.getSelectedItem().toString(),tbObat.getSelectedRow(),55);
            tbObat.setValueAt(NapasPendek.getSelectedItem().toString(),tbObat.getSelectedRow(),56);
            tbObat.setValueAt(BuluBerdiri.getSelectedItem().toString(),tbObat.getSelectedRow(),57);
            tbObat.setValueAt(SulitMenelan.getSelectedItem().toString(),tbObat.getSelectedRow(),58);
            tbObat.setValueAt(PerutMelilit.getSelectedItem().toString(),tbObat.getSelectedRow(),59);
            tbObat.setValueAt(GanguanPencernaan.getSelectedItem().toString(),tbObat.getSelectedRow(),60);
            tbObat.setValueAt(RasaKembung.getSelectedItem().toString(),tbObat.getSelectedRow(),61);
            tbObat.setValueAt(NyeriMakan.getSelectedItem().toString(),tbObat.getSelectedRow(),62);
            tbObat.setValueAt(TerbakarPerut.getSelectedItem().toString(),tbObat.getSelectedRow(),63);
            tbObat.setValueAt(SukarBAB.getSelectedItem().toString(),tbObat.getSelectedRow(),64);
            tbObat.setValueAt(Muntah.getSelectedItem().toString(),tbObat.getSelectedRow(),65);
            tbObat.setValueAt(BABLembek.getSelectedItem().toString(),tbObat.getSelectedRow(),66);
            tbObat.setValueAt(KehilanganBB.getSelectedItem().toString(),tbObat.getSelectedRow(),67);
            tbObat.setValueAt(Mual.getSelectedItem().toString(),tbObat.getSelectedRow(),68);
            tbObat.setValueAt(SeringBAK.getSelectedItem().toString(),tbObat.getSelectedRow(),69);
            tbObat.setValueAt(TidakBisaMenahanKencing.getSelectedItem().toString(),tbObat.getSelectedRow(),70);
            tbObat.setValueAt(MenjadiDingin.getSelectedItem().toString(),tbObat.getSelectedRow(),71);
            tbObat.setValueAt(Manorrhagia.getSelectedItem().toString(),tbObat.getSelectedRow(),72);
            tbObat.setValueAt(Amenorrhoea.getSelectedItem().toString(),tbObat.getSelectedRow(),73);
            tbObat.setValueAt(EjakulasiPraecocks.getSelectedItem().toString(),tbObat.getSelectedRow(),74);
            tbObat.setValueAt(EreksiHilang.getSelectedItem().toString(),tbObat.getSelectedRow(),75);
            tbObat.setValueAt(Impotensi.getSelectedItem().toString(),tbObat.getSelectedRow(),76);
            tbObat.setValueAt(MulutKering.getSelectedItem().toString(),tbObat.getSelectedRow(),77);
            tbObat.setValueAt(MukaMerahGejalaOtonom.getSelectedItem().toString(),tbObat.getSelectedRow(),78);
            tbObat.setValueAt(MudahBerkeringat.getSelectedItem().toString(),tbObat.getSelectedRow(),79);
            tbObat.setValueAt(BuluBerdiriGejalaOtonom.getSelectedItem().toString(),tbObat.getSelectedRow(),80);
            tbObat.setValueAt(SakitKepala.getSelectedItem().toString(),tbObat.getSelectedRow(),81);
            tbObat.setValueAt(GelisahWawancara.getSelectedItem().toString(),tbObat.getSelectedRow(),82);
            tbObat.setValueAt(NapasPendekWawancara.getSelectedItem().toString(),tbObat.getSelectedRow(),83);
            tbObat.setValueAt(JariGemetar.getSelectedItem().toString(),tbObat.getSelectedRow(),84);
            tbObat.setValueAt(KerutKening.getSelectedItem().toString(),tbObat.getSelectedRow(),85);
            tbObat.setValueAt(MukaTegang.getSelectedItem().toString(),tbObat.getSelectedRow(),86);
            tbObat.setValueAt(TonusMeningkat.getSelectedItem().toString(),tbObat.getSelectedRow(),87);
            tbObat.setValueAt(TidakTenang.getSelectedItem().toString(),tbObat.getSelectedRow(),88);
            tbObat.setValueAt(MukaMerahWawancara.getSelectedItem().toString(),tbObat.getSelectedRow(),89);
            tbObat.setValueAt(TotalSkor.getText(),tbObat.getSelectedRow(),90);
            tbObat.setValueAt(KeteranganSkor.getText(),tbObat.getSelectedRow(),91);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),92);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),93);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_level_kecemasan_ranap_anak where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void isTotalSkor() {
        try {
            TotalSkor.setText(
                    (Integer.parseInt(Cemas.getSelectedItem().toString())+Integer.parseInt(FirasatBuruk.getSelectedItem().toString())+
                     Integer.parseInt(TakutPikiranSendiri.getSelectedItem().toString())+Integer.parseInt(MudahTersinggung.getSelectedItem().toString())+
                     Integer.parseInt(MerasaTegang.getSelectedItem().toString())+Integer.parseInt(Lesu.getSelectedItem().toString())+
                     Integer.parseInt(TakBisaIstirahatTenang.getSelectedItem().toString())+Integer.parseInt(MudahTerkejut.getSelectedItem().toString())+
                     Integer.parseInt(MudahMenangis.getSelectedItem().toString())+Integer.parseInt(Gemetar.getSelectedItem().toString())+
                     Integer.parseInt(Gelisah.getSelectedItem().toString())+Integer.parseInt(TakutPadaGelap.getSelectedItem().toString())+
                     Integer.parseInt(TakutPadaOrangAsing.getSelectedItem().toString())+Integer.parseInt(TakutPadaKerumunanBanyakOrang.getSelectedItem().toString())+
                     Integer.parseInt(TakutPadaBinatangBesar.getSelectedItem().toString())+Integer.parseInt(TakutPadaKeramaianLaluLintas.getSelectedItem().toString())+
                     Integer.parseInt(TakutDitinggalSendiri.getSelectedItem().toString())+Integer.parseInt(SulitTidur.getSelectedItem().toString())+
                     Integer.parseInt(TerbangunMalamHari.getSelectedItem().toString())+Integer.parseInt(TidurTidakNyeyak.getSelectedItem().toString())+
                     Integer.parseInt(MimpiBuruk.getSelectedItem().toString())+Integer.parseInt(BangunDenganLesu.getSelectedItem().toString())+
                     Integer.parseInt(BanyakMengalamiMimpi.getSelectedItem().toString())+Integer.parseInt(MimpiMenakutkan.getSelectedItem().toString())+
                     Integer.parseInt(SulitKonsentrasi.getSelectedItem().toString())+Integer.parseInt(DayaIngatBuruk.getSelectedItem().toString())+
                     Integer.parseInt(HilangnyaMinat.getSelectedItem().toString())+Integer.parseInt(BerkurangnyaKesenanganPadaHobi.getSelectedItem().toString())+
                     Integer.parseInt(Sedih.getSelectedItem().toString())+Integer.parseInt(BangunDiniHari.getSelectedItem().toString())+
                     Integer.parseInt(PerasaanBerubah.getSelectedItem().toString())+Integer.parseInt(SakitNyeriDiOtot.getSelectedItem().toString())+
                     Integer.parseInt(Kaku.getSelectedItem().toString())+Integer.parseInt(KedutanOtot.getSelectedItem().toString())+
                     Integer.parseInt(GigiGemerutuk.getSelectedItem().toString())+Integer.parseInt(SuaraTidakStabil.getSelectedItem().toString())+
                     Integer.parseInt(Tinnitus.getSelectedItem().toString())+Integer.parseInt(PenglihatanKabur.getSelectedItem().toString())+
                     Integer.parseInt(MukaMerahGejalaSomatic.getSelectedItem().toString())+Integer.parseInt(MerasaLemah.getSelectedItem().toString())+
                     Integer.parseInt(PerasaanDitusuk.getSelectedItem().toString())+Integer.parseInt(Takhikardia.getSelectedItem().toString())+
                     Integer.parseInt(Berdebar.getSelectedItem().toString())+Integer.parseInt(NyeriDiDada.getSelectedItem().toString())+
                     Integer.parseInt(DenyutNadiMengeras.getSelectedItem().toString())+Integer.parseInt(PerasaanLesu.getSelectedItem().toString())+
                     Integer.parseInt(DetakJantungMenghilang.getSelectedItem().toString())+Integer.parseInt(MerasaTertekan.getSelectedItem().toString())+
                     Integer.parseInt(PerasaanTercekik.getSelectedItem().toString())+Integer.parseInt(SeringMenarikNapas.getSelectedItem().toString())+
                     Integer.parseInt(NapasPendek.getSelectedItem().toString())+Integer.parseInt(BuluBerdiri.getSelectedItem().toString())+
                     Integer.parseInt(SulitMenelan.getSelectedItem().toString())+Integer.parseInt(PerutMelilit.getSelectedItem().toString())+
                     Integer.parseInt(GanguanPencernaan.getSelectedItem().toString())+Integer.parseInt(RasaKembung.getSelectedItem().toString())+
                     Integer.parseInt(NyeriMakan.getSelectedItem().toString())+Integer.parseInt(TerbakarPerut.getSelectedItem().toString())+
                     Integer.parseInt(SukarBAB.getSelectedItem().toString())+Integer.parseInt(Muntah.getSelectedItem().toString())+
                     Integer.parseInt(BABLembek.getSelectedItem().toString())+Integer.parseInt(KehilanganBB.getSelectedItem().toString())+
                     Integer.parseInt(Mual.getSelectedItem().toString())+Integer.parseInt(SeringBAK.getSelectedItem().toString())+
                     Integer.parseInt(TidakBisaMenahanKencing.getSelectedItem().toString())+Integer.parseInt(MenjadiDingin.getSelectedItem().toString())+
                     Integer.parseInt(Manorrhagia.getSelectedItem().toString())+Integer.parseInt(Amenorrhoea.getSelectedItem().toString())+
                     Integer.parseInt(EjakulasiPraecocks.getSelectedItem().toString())+Integer.parseInt(EreksiHilang.getSelectedItem().toString())+
                     Integer.parseInt(Impotensi.getSelectedItem().toString())+Integer.parseInt(MulutKering.getSelectedItem().toString())+
                     Integer.parseInt(MukaMerahGejalaOtonom.getSelectedItem().toString())+Integer.parseInt(MudahBerkeringat.getSelectedItem().toString())+
                     Integer.parseInt(BuluBerdiriGejalaOtonom.getSelectedItem().toString())+Integer.parseInt(SakitKepala.getSelectedItem().toString())+
                     Integer.parseInt(GelisahWawancara.getSelectedItem().toString())+Integer.parseInt(NapasPendekWawancara.getSelectedItem().toString())+
                     Integer.parseInt(JariGemetar.getSelectedItem().toString())+Integer.parseInt(KerutKening.getSelectedItem().toString())+
                     Integer.parseInt(MukaTegang.getSelectedItem().toString())+Integer.parseInt(TonusMeningkat.getSelectedItem().toString())+
                     Integer.parseInt(TidakTenang.getSelectedItem().toString())+Integer.parseInt(MukaMerahWawancara.getSelectedItem().toString()))+"");
            if(Integer.parseInt(TotalSkor.getText())<14){
                KeteranganSkor.setText("Tidak Mengalami Kecemasan");
            }else if(Integer.parseInt(TotalSkor.getText())<=20){
                KeteranganSkor.setText("Kecemasan Ringan");
            }else if(Integer.parseInt(TotalSkor.getText())<=27){
                KeteranganSkor.setText("Kecemasan Sedang");
            }else if(Integer.parseInt(TotalSkor.getText())<=41){
                KeteranganSkor.setText("Kecemasan Berat");
            }else if(Integer.parseInt(TotalSkor.getText())>41){
                KeteranganSkor.setText("Kecemasan Sangat Berat");
            }
        }catch (Exception e) {
            TotalSkor.setText("0");
            KeteranganSkor.setText("Tidak Mengalami Kecemasan");
        }
    }
}

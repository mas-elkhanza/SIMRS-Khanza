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
public final class RMSkriningRisikoKankerPayudara extends javax.swing.JDialog {
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
    public RMSkriningRisikoKankerPayudara(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Umur","Kode Petugas","Nama Petugas","Tanggal",
            "Faktor Risiko Awal 1","N.R.A.1","Faktor Risiko Awal 2","N.R.A.2","Faktor Risiko Awal 3","N.R.A.3",
            "Faktor Risiko Awal 4","N.R.A.4","Faktor Risiko Awal 5","N.R.A.5","Faktor Risiko Awal 6","N.R.A.6",
            "Faktor Risiko Awal 7","N.R.A.7","Faktor Risiko Awal 8","N.R.A.8","Faktor Risiko Awal 9","N.R.A.9", 
            "Faktor Risiko Awal 10","N.R.A.10","Faktor Risiko Awal 11","N.R.A.11","Faktor Risiko Awal 12","N.R.A.12",
            "Faktor Risiko Awal 13","N.R.A.13","Faktor Risiko Awal 14","N.R.A.14","Faktor Risiko Tinggi 1","N.R.T.1",
            "Faktor Risiko Tinggi 2","N.R.T.2","Faktor Risiko Tinggi 3","N.R.T.3","Faktor Risiko Tinggi 4","N.R.T.4", 
            "Faktor Risiko Tinggi 5","N.R.T.5","Faktor Risiko Tinggi 6","N.R.T.6","Faktor Risiko Tinggi 7","N.R.T.7", 
            "Faktor Risiko Tinggi 8","N.R.T.8","Faktor Risiko Tinggi 9","N.R.T.9","Faktor Risiko Tinggi 10","N.R.T.10", 
            "Faktor Risiko Tinggi 11","N.R.T 11","Faktor Risiko Tinggi 12","N.R.T 12","Faktor Risiko Tinggi 13","N.R.T 13", 
            "Kecurigaan Keganasan 1","N.K.G.1","Kecurigaan Keganasan 2","N.K.G.2","Kecurigaan Keganasan 3","N.K.G.3", 
            "Kecurigaan Keganasan 4","N.K.G.4","Kecurigaan Keganasan 5","N.K.G.5","Kecurigaan Keganasan 6","N.K.G.6", 
            "Kecurigaan Keganasan 7","N.K.G.7","Kecurigaan Keganasan 8","N.K.G.8","Total Skor","Hasil Pemeriksaan SADANIS",
            "Tindak Lanjut SADANIS","Hasil Skrining","Keterangan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 83; i++) {
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
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(109);
            }else if(i==9){
                column.setPreferredWidth(45);
            }else if(i==10){
                column.setPreferredWidth(109);
            }else if(i==11){
                column.setPreferredWidth(45);
            }else if(i==12){
                column.setPreferredWidth(109);
            }else if(i==13){
                column.setPreferredWidth(45);
            }else if(i==14){
                column.setPreferredWidth(109);
            }else if(i==15){
                column.setPreferredWidth(45);
            }else if(i==16){
                column.setPreferredWidth(109);
            }else if(i==17){
                column.setPreferredWidth(45);
            }else if(i==18){
                column.setPreferredWidth(109);
            }else if(i==19){
                column.setPreferredWidth(45);
            }else if(i==20){
                column.setPreferredWidth(109);
            }else if(i==21){
                column.setPreferredWidth(45);
            }else if(i==22){
                column.setPreferredWidth(109);
            }else if(i==23){
                column.setPreferredWidth(45);
            }else if(i==24){
                column.setPreferredWidth(109);
            }else if(i==25){
                column.setPreferredWidth(45);
            }else if(i==26){
                column.setPreferredWidth(116);
            }else if(i==27){
                column.setPreferredWidth(52);
            }else if(i==28){
                column.setPreferredWidth(116);
            }else if(i==29){
                column.setPreferredWidth(52);
            }else if(i==30){
                column.setPreferredWidth(116);
            }else if(i==31){
                column.setPreferredWidth(52);
            }else if(i==32){
                column.setPreferredWidth(116);
            }else if(i==33){
                column.setPreferredWidth(52);
            }else if(i==34){
                column.setPreferredWidth(116);
            }else if(i==35){
                column.setPreferredWidth(52);
            }else if(i==36){
                column.setPreferredWidth(115);
            }else if(i==37){
                column.setPreferredWidth(45);
            }else if(i==38){
                column.setPreferredWidth(115);
            }else if(i==39){
                column.setPreferredWidth(45);
            }else if(i==40){
                column.setPreferredWidth(115);
            }else if(i==41){
                column.setPreferredWidth(45);
            }else if(i==42){
                column.setPreferredWidth(115);
            }else if(i==43){
                column.setPreferredWidth(45);
            }else if(i==44){
                column.setPreferredWidth(115);
            }else if(i==45){
                column.setPreferredWidth(45);
            }else if(i==46){
                column.setPreferredWidth(115);
            }else if(i==47){
                column.setPreferredWidth(45);
            }else if(i==48){
                column.setPreferredWidth(115);
            }else if(i==49){
                column.setPreferredWidth(45);
            }else if(i==50){
                column.setPreferredWidth(115);
            }else if(i==51){
                column.setPreferredWidth(45);
            }else if(i==52){
                column.setPreferredWidth(115);
            }else if(i==53){
                column.setPreferredWidth(45);
            }else if(i==54){
                column.setPreferredWidth(121);
            }else if(i==55){
                column.setPreferredWidth(51);
            }else if(i==56){
                column.setPreferredWidth(121);
            }else if(i==57){
                column.setPreferredWidth(51);
            }else if(i==58){
                column.setPreferredWidth(121);
            }else if(i==59){
                column.setPreferredWidth(51);
            }else if(i==60){
                column.setPreferredWidth(121);
            }else if(i==61){
                column.setPreferredWidth(51);
            }else if(i==62){
                column.setPreferredWidth(128);
            }else if(i==63){
                column.setPreferredWidth(45);
            }else if(i==64){
                column.setPreferredWidth(128);
            }else if(i==65){
                column.setPreferredWidth(45);
            }else if(i==66){
                column.setPreferredWidth(128);
            }else if(i==67){
                column.setPreferredWidth(45);
            }else if(i==68){
                column.setPreferredWidth(128);
            }else if(i==69){
                column.setPreferredWidth(45);
            }else if(i==70){
                column.setPreferredWidth(128);
            }else if(i==71){
                column.setPreferredWidth(45);
            }else if(i==72){
                column.setPreferredWidth(128);
            }else if(i==73){
                column.setPreferredWidth(45);
            }else if(i==74){
                column.setPreferredWidth(128);
            }else if(i==75){
                column.setPreferredWidth(45);
            }else if(i==76){
                column.setPreferredWidth(128);
            }else if(i==77){
                column.setPreferredWidth(45);
            }else if(i==78){
                column.setPreferredWidth(60);
            }else if(i==79){
                column.setPreferredWidth(145);
            }else if(i==80){
                column.setPreferredWidth(120);
            }else if(i==81){
                column.setPreferredWidth(193);
            }else if(i==82){
                column.setPreferredWidth(200);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KdPetugas.setDocument(new batasInput((byte)20).getKata(KdPetugas));
        Keterangan.setDocument(new batasInput((byte)50).getKata(Keterangan));
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
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                KdPetugas.requestFocus();
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
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
        jam();
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
        MnSkriningRisikoKankerPayudara = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML = new widget.editorpane();
        Umur = new widget.TextBox();
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
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        FaktorAwal1 = new widget.ComboBox();
        jLabel92 = new widget.Label();
        FaktorAwal2 = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel73 = new widget.Label();
        TotalHasil = new widget.TextBox();
        NilaiFaktorAwal1 = new widget.TextBox();
        NilaiFaktorAwal2 = new widget.TextBox();
        FaktorRisikoTinggi1 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        NilaiFaktorRisikoTinggi1 = new widget.TextBox();
        jLabel71 = new widget.Label();
        NilaiFaktorRisikoTinggi2 = new widget.TextBox();
        FaktorRisikoTinggi2 = new widget.ComboBox();
        jLabel148 = new widget.Label();
        Rekomendasi = new widget.TextBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel99 = new widget.Label();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        jLabel81 = new widget.Label();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        jLabel84 = new widget.Label();
        FaktorRisikoTinggi3 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        NilaiFaktorRisikoTinggi3 = new widget.TextBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        FaktorRisikoTinggi4 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        NilaiFaktorRisikoTinggi4 = new widget.TextBox();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        FaktorRisikoTinggi5 = new widget.ComboBox();
        jLabel89 = new widget.Label();
        NilaiFaktorRisikoTinggi5 = new widget.TextBox();
        jLabel90 = new widget.Label();
        FaktorRisikoTinggi6 = new widget.ComboBox();
        jLabel91 = new widget.Label();
        NilaiFaktorRisikoTinggi6 = new widget.TextBox();
        jLabel93 = new widget.Label();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel101 = new widget.Label();
        jLabel149 = new widget.Label();
        jLabel94 = new widget.Label();
        jLabel95 = new widget.Label();
        jLabel96 = new widget.Label();
        FaktorAwal3 = new widget.ComboBox();
        jLabel97 = new widget.Label();
        NilaiFaktorAwal3 = new widget.TextBox();
        jLabel98 = new widget.Label();
        jLabel102 = new widget.Label();
        FaktorAwal4 = new widget.ComboBox();
        jLabel103 = new widget.Label();
        NilaiFaktorAwal4 = new widget.TextBox();
        jLabel104 = new widget.Label();
        jLabel105 = new widget.Label();
        FaktorAwal5 = new widget.ComboBox();
        jLabel106 = new widget.Label();
        NilaiFaktorAwal5 = new widget.TextBox();
        jLabel107 = new widget.Label();
        jLabel108 = new widget.Label();
        FaktorAwal6 = new widget.ComboBox();
        jLabel109 = new widget.Label();
        NilaiFaktorAwal6 = new widget.TextBox();
        jLabel110 = new widget.Label();
        jLabel111 = new widget.Label();
        FaktorAwal7 = new widget.ComboBox();
        jLabel112 = new widget.Label();
        NilaiFaktorAwal7 = new widget.TextBox();
        jLabel113 = new widget.Label();
        jLabel114 = new widget.Label();
        jLabel115 = new widget.Label();
        FaktorAwal8 = new widget.ComboBox();
        jLabel116 = new widget.Label();
        NilaiFaktorAwal8 = new widget.TextBox();
        jLabel117 = new widget.Label();
        jLabel118 = new widget.Label();
        FaktorAwal9 = new widget.ComboBox();
        jLabel119 = new widget.Label();
        NilaiFaktorAwal9 = new widget.TextBox();
        NilaiFaktorAwal10 = new widget.TextBox();
        jLabel120 = new widget.Label();
        FaktorAwal10 = new widget.ComboBox();
        jLabel121 = new widget.Label();
        jLabel122 = new widget.Label();
        jLabel123 = new widget.Label();
        jLabel124 = new widget.Label();
        FaktorAwal11 = new widget.ComboBox();
        jLabel125 = new widget.Label();
        NilaiFaktorAwal11 = new widget.TextBox();
        jLabel126 = new widget.Label();
        jLabel127 = new widget.Label();
        FaktorAwal12 = new widget.ComboBox();
        jLabel128 = new widget.Label();
        NilaiFaktorAwal12 = new widget.TextBox();
        jLabel129 = new widget.Label();
        jLabel130 = new widget.Label();
        FaktorAwal13 = new widget.ComboBox();
        jLabel131 = new widget.Label();
        NilaiFaktorAwal13 = new widget.TextBox();
        jLabel132 = new widget.Label();
        jLabel133 = new widget.Label();
        FaktorAwal14 = new widget.ComboBox();
        jLabel134 = new widget.Label();
        NilaiFaktorAwal14 = new widget.TextBox();
        jLabel135 = new widget.Label();
        jLabel100 = new widget.Label();
        jLabel136 = new widget.Label();
        FaktorRisikoTinggi7 = new widget.ComboBox();
        jLabel137 = new widget.Label();
        NilaiFaktorRisikoTinggi7 = new widget.TextBox();
        jLabel138 = new widget.Label();
        jLabel139 = new widget.Label();
        FaktorRisikoTinggi8 = new widget.ComboBox();
        jLabel140 = new widget.Label();
        NilaiFaktorRisikoTinggi8 = new widget.TextBox();
        NilaiFaktorRisikoTinggi9 = new widget.TextBox();
        jLabel141 = new widget.Label();
        FaktorRisikoTinggi9 = new widget.ComboBox();
        jLabel142 = new widget.Label();
        jLabel143 = new widget.Label();
        jLabel144 = new widget.Label();
        jLabel145 = new widget.Label();
        FaktorRisikoTinggi10 = new widget.ComboBox();
        jLabel146 = new widget.Label();
        NilaiFaktorRisikoTinggi10 = new widget.TextBox();
        jLabel147 = new widget.Label();
        jLabel150 = new widget.Label();
        FaktorRisikoTinggi11 = new widget.ComboBox();
        jLabel151 = new widget.Label();
        NilaiFaktorRisikoTinggi11 = new widget.TextBox();
        jLabel152 = new widget.Label();
        jLabel153 = new widget.Label();
        FaktorRisikoTinggi12 = new widget.ComboBox();
        jLabel154 = new widget.Label();
        NilaiFaktorRisikoTinggi12 = new widget.TextBox();
        jLabel155 = new widget.Label();
        jLabel156 = new widget.Label();
        FaktorRisikoTinggi13 = new widget.ComboBox();
        jLabel157 = new widget.Label();
        NilaiFaktorRisikoTinggi13 = new widget.TextBox();
        jLabel158 = new widget.Label();
        jLabel159 = new widget.Label();
        jLabel160 = new widget.Label();
        KecurigaanKeganasan1 = new widget.ComboBox();
        jLabel161 = new widget.Label();
        NilaiKecurigaanKeganasan1 = new widget.TextBox();
        jLabel162 = new widget.Label();
        jLabel163 = new widget.Label();
        KecurigaanKeganasan2 = new widget.ComboBox();
        jLabel164 = new widget.Label();
        NilaiKecurigaanKeganasan2 = new widget.TextBox();
        jLabel165 = new widget.Label();
        jLabel166 = new widget.Label();
        KecurigaanKeganasan3 = new widget.ComboBox();
        jLabel167 = new widget.Label();
        NilaiKecurigaanKeganasan3 = new widget.TextBox();
        jLabel168 = new widget.Label();
        jLabel169 = new widget.Label();
        jLabel170 = new widget.Label();
        KecurigaanKeganasan4 = new widget.ComboBox();
        jLabel171 = new widget.Label();
        NilaiKecurigaanKeganasan4 = new widget.TextBox();
        jLabel172 = new widget.Label();
        jLabel173 = new widget.Label();
        KecurigaanKeganasan5 = new widget.ComboBox();
        jLabel174 = new widget.Label();
        NilaiKecurigaanKeganasan5 = new widget.TextBox();
        jLabel175 = new widget.Label();
        jLabel176 = new widget.Label();
        KecurigaanKeganasan6 = new widget.ComboBox();
        jLabel177 = new widget.Label();
        NilaiKecurigaanKeganasan6 = new widget.TextBox();
        jLabel178 = new widget.Label();
        jLabel179 = new widget.Label();
        KecurigaanKeganasan7 = new widget.ComboBox();
        jLabel180 = new widget.Label();
        NilaiKecurigaanKeganasan7 = new widget.TextBox();
        jLabel181 = new widget.Label();
        jLabel182 = new widget.Label();
        KecurigaanKeganasan8 = new widget.ComboBox();
        jLabel183 = new widget.Label();
        NilaiKecurigaanKeganasan8 = new widget.TextBox();
        jLabel184 = new widget.Label();
        HasilPemeriksaanSadanis = new widget.ComboBox();
        jLabel185 = new widget.Label();
        jLabel186 = new widget.Label();
        TindakLanjutSadanis = new widget.ComboBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel187 = new widget.Label();
        jLabel188 = new widget.Label();
        HasilSkrining = new widget.ComboBox();
        jLabel189 = new widget.Label();
        jLabel190 = new widget.Label();
        Keterangan = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningRisikoKankerPayudara.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningRisikoKankerPayudara.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningRisikoKankerPayudara.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningRisikoKankerPayudara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningRisikoKankerPayudara.setText("Formulir Skrining Risiko Kanker Payudara");
        MnSkriningRisikoKankerPayudara.setName("MnSkriningRisikoKankerPayudara"); // NOI18N
        MnSkriningRisikoKankerPayudara.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningRisikoKankerPayudara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningRisikoKankerPayudaraActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningRisikoKankerPayudara);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        Umur.setEditable(false);
        Umur.setFocusTraversalPolicyProvider(true);
        Umur.setName("Umur"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Risiko Kanker Payudara ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-07-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-07-2024" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 426));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1343));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

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

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-07-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 40, 94, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(570, 40, 187, 23);

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

        FaktorAwal1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal1.setName("FaktorAwal1"); // NOI18N
        FaktorAwal1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal1ItemStateChanged(evt);
            }
        });
        FaktorAwal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal1KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal1);
        FaktorAwal1.setBounds(620, 110, 80, 23);

        jLabel92.setText("Nilai :");
        jLabel92.setName("jLabel92"); // NOI18N
        FormInput.add(jLabel92);
        jLabel92.setBounds(690, 110, 50, 23);

        FaktorAwal2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal2.setName("FaktorAwal2"); // NOI18N
        FaktorAwal2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal2ItemStateChanged(evt);
            }
        });
        FaktorAwal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal2KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal2);
        FaktorAwal2.setBounds(620, 140, 80, 23);

        jLabel69.setText("Nilai :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(690, 140, 50, 23);

        jLabel73.setText("Total Skor :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(670, 1210, 70, 23);

        TotalHasil.setEditable(false);
        TotalHasil.setText("0");
        TotalHasil.setFocusTraversalPolicyProvider(true);
        TotalHasil.setName("TotalHasil"); // NOI18N
        FormInput.add(TotalHasil);
        TotalHasil.setBounds(744, 1210, 45, 23);

        NilaiFaktorAwal1.setEditable(false);
        NilaiFaktorAwal1.setText("0");
        NilaiFaktorAwal1.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal1.setName("NilaiFaktorAwal1"); // NOI18N
        FormInput.add(NilaiFaktorAwal1);
        NilaiFaktorAwal1.setBounds(744, 110, 45, 23);

        NilaiFaktorAwal2.setEditable(false);
        NilaiFaktorAwal2.setText("0");
        NilaiFaktorAwal2.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal2.setName("NilaiFaktorAwal2"); // NOI18N
        FormInput.add(NilaiFaktorAwal2);
        NilaiFaktorAwal2.setBounds(744, 140, 45, 23);

        FaktorRisikoTinggi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi1.setName("FaktorRisikoTinggi1"); // NOI18N
        FaktorRisikoTinggi1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi1ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi1KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi1);
        FaktorRisikoTinggi1.setBounds(620, 550, 80, 23);

        jLabel70.setText("Nilai :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(690, 550, 50, 23);

        NilaiFaktorRisikoTinggi1.setEditable(false);
        NilaiFaktorRisikoTinggi1.setText("0");
        NilaiFaktorRisikoTinggi1.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi1.setName("NilaiFaktorRisikoTinggi1"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi1);
        NilaiFaktorRisikoTinggi1.setBounds(744, 550, 45, 23);

        jLabel71.setText("Nilai :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(690, 580, 50, 23);

        NilaiFaktorRisikoTinggi2.setEditable(false);
        NilaiFaktorRisikoTinggi2.setText("0");
        NilaiFaktorRisikoTinggi2.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi2.setName("NilaiFaktorRisikoTinggi2"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi2);
        NilaiFaktorRisikoTinggi2.setBounds(744, 580, 45, 23);

        FaktorRisikoTinggi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi2.setName("FaktorRisikoTinggi2"); // NOI18N
        FaktorRisikoTinggi2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi2ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi2KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi2);
        FaktorRisikoTinggi2.setBounds(620, 580, 80, 23);

        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel148.setText("Rekomendasi");
        jLabel148.setName("jLabel148"); // NOI18N
        FormInput.add(jLabel148);
        jLabel148.setBounds(44, 1210, 90, 23);

        Rekomendasi.setEditable(false);
        Rekomendasi.setFocusTraversalPolicyProvider(true);
        Rekomendasi.setName("Rekomendasi"); // NOI18N
        Rekomendasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RekomendasiKeyPressed(evt);
            }
        });
        FormInput.add(Rekomendasi);
        Rekomendasi.setBounds(120, 1210, 520, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 807, 1);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("I. SKOR ANANTO SIDOHUTOMO");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 200, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("1.");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(54, 110, 20, 23);

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("Apakah anda mengalami infeksi yang masuk melalui puting susu, areola, atau kulit payudara ?");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(72, 110, 530, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("2.");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(54, 140, 20, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Apakah anda tidak memperhatikan kebersihan puting susu, areola, atau kulit payudara ?");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(72, 140, 530, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Apakah anda tidak mempunyai anak ?");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(72, 550, 470, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("1.");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(54, 550, 20, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("2.");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(54, 580, 20, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Apakah anda menyusui anak kurang dari 6 bulan ?");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(72, 580, 470, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("3.");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(54, 610, 20, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Apakah anda memakai hormonal kontrasepsi atau terapi sulih hormon ?");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(72, 610, 470, 23);

        FaktorRisikoTinggi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi3.setName("FaktorRisikoTinggi3"); // NOI18N
        FaktorRisikoTinggi3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi3ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi3KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi3);
        FaktorRisikoTinggi3.setBounds(620, 610, 80, 23);

        jLabel72.setText("Nilai :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(690, 610, 50, 23);

        NilaiFaktorRisikoTinggi3.setEditable(false);
        NilaiFaktorRisikoTinggi3.setText("0");
        NilaiFaktorRisikoTinggi3.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi3.setName("NilaiFaktorRisikoTinggi3"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi3);
        NilaiFaktorRisikoTinggi3.setBounds(744, 610, 45, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("4.");
        jLabel85.setName("jLabel85"); // NOI18N
        FormInput.add(jLabel85);
        jLabel85.setBounds(54, 640, 20, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Apakah anda dalam setahun terkena radiasi sinar-X (rontgen) lebih dari 1 kali ?");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(72, 640, 470, 23);

        FaktorRisikoTinggi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi4.setName("FaktorRisikoTinggi4"); // NOI18N
        FaktorRisikoTinggi4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi4ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi4KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi4);
        FaktorRisikoTinggi4.setBounds(620, 640, 80, 23);

        jLabel74.setText("Nilai :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(690, 640, 50, 23);

        NilaiFaktorRisikoTinggi4.setEditable(false);
        NilaiFaktorRisikoTinggi4.setText("0");
        NilaiFaktorRisikoTinggi4.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi4.setName("NilaiFaktorRisikoTinggi4"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi4);
        NilaiFaktorRisikoTinggi4.setBounds(744, 640, 45, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("Apakah anda pernah menjalani tindakan pembedahan pada payudara ?");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(72, 670, 470, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("5.");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(54, 670, 20, 23);

        FaktorRisikoTinggi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi5.setName("FaktorRisikoTinggi5"); // NOI18N
        FaktorRisikoTinggi5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi5ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi5KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi5);
        FaktorRisikoTinggi5.setBounds(620, 670, 80, 23);

        jLabel89.setText("Nilai :");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(690, 670, 50, 23);

        NilaiFaktorRisikoTinggi5.setEditable(false);
        NilaiFaktorRisikoTinggi5.setText("0");
        NilaiFaktorRisikoTinggi5.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi5.setName("NilaiFaktorRisikoTinggi5"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi5);
        NilaiFaktorRisikoTinggi5.setBounds(744, 670, 45, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("Apakah anda mendapat trauma payudara akibat aktifitas seksual berlebihan ?");
        jLabel90.setName("jLabel90"); // NOI18N
        FormInput.add(jLabel90);
        jLabel90.setBounds(72, 700, 470, 23);

        FaktorRisikoTinggi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi6.setName("FaktorRisikoTinggi6"); // NOI18N
        FaktorRisikoTinggi6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi6ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi6KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi6);
        FaktorRisikoTinggi6.setBounds(620, 700, 80, 23);

        jLabel91.setText("Nilai :");
        jLabel91.setName("jLabel91"); // NOI18N
        FormInput.add(jLabel91);
        jLabel91.setBounds(690, 700, 50, 23);

        NilaiFaktorRisikoTinggi6.setEditable(false);
        NilaiFaktorRisikoTinggi6.setText("0");
        NilaiFaktorRisikoTinggi6.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi6.setName("NilaiFaktorRisikoTinggi6"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi6);
        NilaiFaktorRisikoTinggi6.setBounds(744, 700, 45, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("6.");
        jLabel93.setName("jLabel93"); // NOI18N
        FormInput.add(jLabel93);
        jLabel93.setBounds(54, 700, 20, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 1240, 807, 1);

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel101.setText("II. PEMERIKSAAN SADANIS ");
        jLabel101.setName("jLabel101"); // NOI18N
        FormInput.add(jLabel101);
        jLabel101.setBounds(10, 1240, 200, 23);

        jLabel149.setText(":");
        jLabel149.setName("jLabel149"); // NOI18N
        FormInput.add(jLabel149);
        jLabel149.setBounds(0, 1210, 116, 23);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("A. Faktor Awal (Ya=1, Tidak=0)");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(44, 90, 310, 23);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Apakah anda melahirkan anak pertama saat usia lebih dari 25 tahun ?");
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput.add(jLabel95);
        jLabel95.setBounds(72, 170, 530, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("3.");
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput.add(jLabel96);
        jLabel96.setBounds(54, 170, 20, 23);

        FaktorAwal3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal3.setName("FaktorAwal3"); // NOI18N
        FaktorAwal3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal3ItemStateChanged(evt);
            }
        });
        FaktorAwal3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal3KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal3);
        FaktorAwal3.setBounds(620, 170, 80, 23);

        jLabel97.setText("Nilai :");
        jLabel97.setName("jLabel97"); // NOI18N
        FormInput.add(jLabel97);
        jLabel97.setBounds(690, 170, 50, 23);

        NilaiFaktorAwal3.setEditable(false);
        NilaiFaktorAwal3.setText("0");
        NilaiFaktorAwal3.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal3.setName("NilaiFaktorAwal3"); // NOI18N
        FormInput.add(NilaiFaktorAwal3);
        NilaiFaktorAwal3.setBounds(744, 170, 45, 23);

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel98.setText("Apakah anda atau orang di sekitar anda merokok ?");
        jLabel98.setName("jLabel98"); // NOI18N
        FormInput.add(jLabel98);
        jLabel98.setBounds(72, 200, 530, 23);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("4.");
        jLabel102.setName("jLabel102"); // NOI18N
        FormInput.add(jLabel102);
        jLabel102.setBounds(54, 200, 20, 23);

        FaktorAwal4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal4.setName("FaktorAwal4"); // NOI18N
        FaktorAwal4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal4ItemStateChanged(evt);
            }
        });
        FaktorAwal4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal4KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal4);
        FaktorAwal4.setBounds(620, 200, 80, 23);

        jLabel103.setText("Nilai :");
        jLabel103.setName("jLabel103"); // NOI18N
        FormInput.add(jLabel103);
        jLabel103.setBounds(690, 200, 50, 23);

        NilaiFaktorAwal4.setEditable(false);
        NilaiFaktorAwal4.setText("0");
        NilaiFaktorAwal4.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal4.setName("NilaiFaktorAwal4"); // NOI18N
        FormInput.add(NilaiFaktorAwal4);
        NilaiFaktorAwal4.setBounds(744, 200, 45, 23);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel104.setText("Apakah anda mengkonsumsi alkohol ?");
        jLabel104.setName("jLabel104"); // NOI18N
        FormInput.add(jLabel104);
        jLabel104.setBounds(72, 230, 530, 23);

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel105.setText("5.");
        jLabel105.setName("jLabel105"); // NOI18N
        FormInput.add(jLabel105);
        jLabel105.setBounds(54, 230, 20, 23);

        FaktorAwal5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal5.setName("FaktorAwal5"); // NOI18N
        FaktorAwal5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal5ItemStateChanged(evt);
            }
        });
        FaktorAwal5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal5KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal5);
        FaktorAwal5.setBounds(620, 230, 80, 23);

        jLabel106.setText("Nilai :");
        jLabel106.setName("jLabel106"); // NOI18N
        FormInput.add(jLabel106);
        jLabel106.setBounds(690, 230, 50, 23);

        NilaiFaktorAwal5.setEditable(false);
        NilaiFaktorAwal5.setText("0");
        NilaiFaktorAwal5.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal5.setName("NilaiFaktorAwal5"); // NOI18N
        FormInput.add(NilaiFaktorAwal5);
        NilaiFaktorAwal5.setBounds(744, 230, 45, 23);

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel107.setText("6.");
        jLabel107.setName("jLabel107"); // NOI18N
        FormInput.add(jLabel107);
        jLabel107.setBounds(54, 260, 20, 23);

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Apakah anda tinggal di daerah tinggi polusi (banyak asap kendaraan, asap pabrik, pemanasan global) ?");
        jLabel108.setName("jLabel108"); // NOI18N
        FormInput.add(jLabel108);
        jLabel108.setBounds(72, 260, 530, 23);

        FaktorAwal6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal6.setName("FaktorAwal6"); // NOI18N
        FaktorAwal6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal6ItemStateChanged(evt);
            }
        });
        FaktorAwal6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal6KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal6);
        FaktorAwal6.setBounds(620, 260, 80, 23);

        jLabel109.setText("Nilai :");
        jLabel109.setName("jLabel109"); // NOI18N
        FormInput.add(jLabel109);
        jLabel109.setBounds(690, 260, 50, 23);

        NilaiFaktorAwal6.setEditable(false);
        NilaiFaktorAwal6.setText("0");
        NilaiFaktorAwal6.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal6.setName("NilaiFaktorAwal6"); // NOI18N
        FormInput.add(NilaiFaktorAwal6);
        NilaiFaktorAwal6.setBounds(744, 260, 45, 23);

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel110.setText("Apakah anda sering mengkonsumsi makanan yang prosesnya dibakar, digoreng, diasap, diasinkan,");
        jLabel110.setName("jLabel110"); // NOI18N
        FormInput.add(jLabel110);
        jLabel110.setBounds(72, 285, 530, 23);

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("7.");
        jLabel111.setName("jLabel111"); // NOI18N
        FormInput.add(jLabel111);
        jLabel111.setBounds(54, 290, 20, 23);

        FaktorAwal7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal7.setName("FaktorAwal7"); // NOI18N
        FaktorAwal7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal7ItemStateChanged(evt);
            }
        });
        FaktorAwal7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal7KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal7);
        FaktorAwal7.setBounds(620, 290, 80, 23);

        jLabel112.setText("Nilai :");
        jLabel112.setName("jLabel112"); // NOI18N
        FormInput.add(jLabel112);
        jLabel112.setBounds(690, 290, 50, 23);

        NilaiFaktorAwal7.setEditable(false);
        NilaiFaktorAwal7.setText("0");
        NilaiFaktorAwal7.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal7.setName("NilaiFaktorAwal7"); // NOI18N
        FormInput.add(NilaiFaktorAwal7);
        NilaiFaktorAwal7.setBounds(744, 290, 45, 23);

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel113.setText("diacar, mengandung bahan pengawet, berlemak, dan cepat saji ?");
        jLabel113.setName("jLabel113"); // NOI18N
        FormInput.add(jLabel113);
        jLabel113.setBounds(72, 298, 530, 23);

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("8.");
        jLabel114.setName("jLabel114"); // NOI18N
        FormInput.add(jLabel114);
        jLabel114.setBounds(54, 320, 20, 23);

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel115.setText("Apakah anda mengalami menarche (saat haid pertama) di usia sangat muda ?");
        jLabel115.setName("jLabel115"); // NOI18N
        FormInput.add(jLabel115);
        jLabel115.setBounds(72, 320, 530, 23);

        FaktorAwal8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal8.setName("FaktorAwal8"); // NOI18N
        FaktorAwal8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal8ItemStateChanged(evt);
            }
        });
        FaktorAwal8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal8KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal8);
        FaktorAwal8.setBounds(620, 320, 80, 23);

        jLabel116.setText("Nilai :");
        jLabel116.setName("jLabel116"); // NOI18N
        FormInput.add(jLabel116);
        jLabel116.setBounds(690, 320, 50, 23);

        NilaiFaktorAwal8.setEditable(false);
        NilaiFaktorAwal8.setText("0");
        NilaiFaktorAwal8.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal8.setName("NilaiFaktorAwal8"); // NOI18N
        FormInput.add(NilaiFaktorAwal8);
        NilaiFaktorAwal8.setBounds(744, 320, 45, 23);

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("9.");
        jLabel117.setName("jLabel117"); // NOI18N
        FormInput.add(jLabel117);
        jLabel117.setBounds(54, 350, 20, 23);

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel118.setText("Apakah selisih kehamilan pertama anda dengan haid pertama lebih dari 15 tahun ?");
        jLabel118.setName("jLabel118"); // NOI18N
        FormInput.add(jLabel118);
        jLabel118.setBounds(72, 350, 530, 23);

        FaktorAwal9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal9.setName("FaktorAwal9"); // NOI18N
        FaktorAwal9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal9ItemStateChanged(evt);
            }
        });
        FaktorAwal9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal9KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal9);
        FaktorAwal9.setBounds(620, 350, 80, 23);

        jLabel119.setText("Nilai :");
        jLabel119.setName("jLabel119"); // NOI18N
        FormInput.add(jLabel119);
        jLabel119.setBounds(690, 350, 50, 23);

        NilaiFaktorAwal9.setEditable(false);
        NilaiFaktorAwal9.setText("0");
        NilaiFaktorAwal9.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal9.setName("NilaiFaktorAwal9"); // NOI18N
        FormInput.add(NilaiFaktorAwal9);
        NilaiFaktorAwal9.setBounds(744, 350, 45, 23);

        NilaiFaktorAwal10.setEditable(false);
        NilaiFaktorAwal10.setText("0");
        NilaiFaktorAwal10.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal10.setName("NilaiFaktorAwal10"); // NOI18N
        FormInput.add(NilaiFaktorAwal10);
        NilaiFaktorAwal10.setBounds(744, 380, 45, 23);

        jLabel120.setText("Nilai :");
        jLabel120.setName("jLabel120"); // NOI18N
        FormInput.add(jLabel120);
        jLabel120.setBounds(690, 380, 50, 23);

        FaktorAwal10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal10.setName("FaktorAwal10"); // NOI18N
        FaktorAwal10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal10ItemStateChanged(evt);
            }
        });
        FaktorAwal10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal10KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal10);
        FaktorAwal10.setBounds(620, 380, 80, 23);

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel121.setText("Apakah anda mengalami menopause (henti haid) di usia lebih dari 50 tahun ?");
        jLabel121.setName("jLabel121"); // NOI18N
        FormInput.add(jLabel121);
        jLabel121.setBounds(72, 380, 530, 23);

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel122.setText("10.");
        jLabel122.setName("jLabel122"); // NOI18N
        FormInput.add(jLabel122);
        jLabel122.setBounds(54, 380, 20, 23);

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("11.");
        jLabel123.setName("jLabel123"); // NOI18N
        FormInput.add(jLabel123);
        jLabel123.setBounds(54, 410, 20, 23);

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel124.setText("Apakah anda termasuk golongan ras kulit putih (kaukasia) ?");
        jLabel124.setName("jLabel124"); // NOI18N
        FormInput.add(jLabel124);
        jLabel124.setBounds(72, 410, 530, 23);

        FaktorAwal11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal11.setName("FaktorAwal11"); // NOI18N
        FaktorAwal11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal11ItemStateChanged(evt);
            }
        });
        FaktorAwal11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal11KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal11);
        FaktorAwal11.setBounds(620, 410, 80, 23);

        jLabel125.setText("Nilai :");
        jLabel125.setName("jLabel125"); // NOI18N
        FormInput.add(jLabel125);
        jLabel125.setBounds(690, 410, 50, 23);

        NilaiFaktorAwal11.setEditable(false);
        NilaiFaktorAwal11.setText("0");
        NilaiFaktorAwal11.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal11.setName("NilaiFaktorAwal11"); // NOI18N
        FormInput.add(NilaiFaktorAwal11);
        NilaiFaktorAwal11.setBounds(744, 410, 45, 23);

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("Apakah anda terdiagnosa mengalami mutasi gen BRCA 1 & BRCA 2 ?");
        jLabel126.setName("jLabel126"); // NOI18N
        FormInput.add(jLabel126);
        jLabel126.setBounds(72, 440, 530, 23);

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel127.setText("12.");
        jLabel127.setName("jLabel127"); // NOI18N
        FormInput.add(jLabel127);
        jLabel127.setBounds(54, 440, 20, 23);

        FaktorAwal12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal12.setName("FaktorAwal12"); // NOI18N
        FaktorAwal12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal12ItemStateChanged(evt);
            }
        });
        FaktorAwal12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal12KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal12);
        FaktorAwal12.setBounds(620, 440, 80, 23);

        jLabel128.setText("Nilai :");
        jLabel128.setName("jLabel128"); // NOI18N
        FormInput.add(jLabel128);
        jLabel128.setBounds(690, 440, 50, 23);

        NilaiFaktorAwal12.setEditable(false);
        NilaiFaktorAwal12.setText("0");
        NilaiFaktorAwal12.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal12.setName("NilaiFaktorAwal12"); // NOI18N
        FormInput.add(NilaiFaktorAwal12);
        NilaiFaktorAwal12.setBounds(744, 440, 45, 23);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("13.");
        jLabel129.setName("jLabel129"); // NOI18N
        FormInput.add(jLabel129);
        jLabel129.setBounds(54, 470, 20, 23);

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel130.setText("Apakah anda seorang perempuan (lebih tinggi resiko dibandingkan laki-laki) ?");
        jLabel130.setName("jLabel130"); // NOI18N
        FormInput.add(jLabel130);
        jLabel130.setBounds(72, 470, 530, 23);

        FaktorAwal13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal13.setName("FaktorAwal13"); // NOI18N
        FaktorAwal13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal13ItemStateChanged(evt);
            }
        });
        FaktorAwal13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal13KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal13);
        FaktorAwal13.setBounds(620, 470, 80, 23);

        jLabel131.setText("Nilai :");
        jLabel131.setName("jLabel131"); // NOI18N
        FormInput.add(jLabel131);
        jLabel131.setBounds(690, 470, 50, 23);

        NilaiFaktorAwal13.setEditable(false);
        NilaiFaktorAwal13.setText("0");
        NilaiFaktorAwal13.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal13.setName("NilaiFaktorAwal13"); // NOI18N
        FormInput.add(NilaiFaktorAwal13);
        NilaiFaktorAwal13.setBounds(744, 470, 45, 23);

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel132.setText("Apakah anda termasuk golongan obesitas/kegemukan ?");
        jLabel132.setName("jLabel132"); // NOI18N
        FormInput.add(jLabel132);
        jLabel132.setBounds(72, 500, 530, 23);

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel133.setText("14.");
        jLabel133.setName("jLabel133"); // NOI18N
        FormInput.add(jLabel133);
        jLabel133.setBounds(54, 500, 20, 23);

        FaktorAwal14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorAwal14.setName("FaktorAwal14"); // NOI18N
        FaktorAwal14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorAwal14ItemStateChanged(evt);
            }
        });
        FaktorAwal14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorAwal14KeyPressed(evt);
            }
        });
        FormInput.add(FaktorAwal14);
        FaktorAwal14.setBounds(620, 500, 80, 23);

        jLabel134.setText("Nilai :");
        jLabel134.setName("jLabel134"); // NOI18N
        FormInput.add(jLabel134);
        jLabel134.setBounds(690, 500, 50, 23);

        NilaiFaktorAwal14.setEditable(false);
        NilaiFaktorAwal14.setText("0");
        NilaiFaktorAwal14.setFocusTraversalPolicyProvider(true);
        NilaiFaktorAwal14.setName("NilaiFaktorAwal14"); // NOI18N
        FormInput.add(NilaiFaktorAwal14);
        NilaiFaktorAwal14.setBounds(744, 500, 45, 23);

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel135.setText("B. Faktor Risiko Tinggi (Ya=5, Tidak=0)");
        jLabel135.setName("jLabel135"); // NOI18N
        FormInput.add(jLabel135);
        jLabel135.setBounds(44, 530, 310, 23);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("Apakah anda bila sakit tidak kontrol atau tidak tuntas berobat ?");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(72, 730, 470, 23);

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("7.");
        jLabel136.setName("jLabel136"); // NOI18N
        FormInput.add(jLabel136);
        jLabel136.setBounds(54, 730, 20, 23);

        FaktorRisikoTinggi7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi7.setName("FaktorRisikoTinggi7"); // NOI18N
        FaktorRisikoTinggi7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi7ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi7KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi7);
        FaktorRisikoTinggi7.setBounds(620, 730, 80, 23);

        jLabel137.setText("Nilai :");
        jLabel137.setName("jLabel137"); // NOI18N
        FormInput.add(jLabel137);
        jLabel137.setBounds(690, 730, 50, 23);

        NilaiFaktorRisikoTinggi7.setEditable(false);
        NilaiFaktorRisikoTinggi7.setText("0");
        NilaiFaktorRisikoTinggi7.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi7.setName("NilaiFaktorRisikoTinggi7"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi7);
        NilaiFaktorRisikoTinggi7.setBounds(744, 730, 45, 23);

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel138.setText("Apakah anda berusia di atas 25 tahun (semakin tua usia semakin tinggi resiko) ?");
        jLabel138.setName("jLabel138"); // NOI18N
        FormInput.add(jLabel138);
        jLabel138.setBounds(72, 760, 470, 23);

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel139.setText("8.");
        jLabel139.setName("jLabel139"); // NOI18N
        FormInput.add(jLabel139);
        jLabel139.setBounds(54, 760, 20, 23);

        FaktorRisikoTinggi8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi8.setName("FaktorRisikoTinggi8"); // NOI18N
        FaktorRisikoTinggi8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi8ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi8KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi8);
        FaktorRisikoTinggi8.setBounds(620, 760, 80, 23);

        jLabel140.setText("Nilai :");
        jLabel140.setName("jLabel140"); // NOI18N
        FormInput.add(jLabel140);
        jLabel140.setBounds(690, 760, 50, 23);

        NilaiFaktorRisikoTinggi8.setEditable(false);
        NilaiFaktorRisikoTinggi8.setText("0");
        NilaiFaktorRisikoTinggi8.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi8.setName("NilaiFaktorRisikoTinggi8"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi8);
        NilaiFaktorRisikoTinggi8.setBounds(744, 760, 45, 23);

        NilaiFaktorRisikoTinggi9.setEditable(false);
        NilaiFaktorRisikoTinggi9.setText("0");
        NilaiFaktorRisikoTinggi9.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi9.setName("NilaiFaktorRisikoTinggi9"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi9);
        NilaiFaktorRisikoTinggi9.setBounds(744, 790, 45, 23);

        jLabel141.setText("Nilai :");
        jLabel141.setName("jLabel141"); // NOI18N
        FormInput.add(jLabel141);
        jLabel141.setBounds(690, 790, 50, 23);

        FaktorRisikoTinggi9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi9.setName("FaktorRisikoTinggi9"); // NOI18N
        FaktorRisikoTinggi9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi9ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi9KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi9);
        FaktorRisikoTinggi9.setBounds(620, 790, 80, 23);

        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel142.setText("Apakah anda pernah memiliki tumor (benjolan) payudara ?");
        jLabel142.setName("jLabel142"); // NOI18N
        FormInput.add(jLabel142);
        jLabel142.setBounds(72, 790, 470, 23);

        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel143.setText("9.");
        jLabel143.setName("jLabel143"); // NOI18N
        FormInput.add(jLabel143);
        jLabel143.setBounds(54, 790, 20, 23);

        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel144.setText("10.");
        jLabel144.setName("jLabel144"); // NOI18N
        FormInput.add(jLabel144);
        jLabel144.setBounds(54, 820, 20, 23);

        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel145.setText("Apakah anda menderita kanker pada salah satu payudara (resiko bagi satu payudara yang lainnya) ?");
        jLabel145.setName("jLabel145"); // NOI18N
        FormInput.add(jLabel145);
        jLabel145.setBounds(72, 820, 530, 23);

        FaktorRisikoTinggi10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi10.setName("FaktorRisikoTinggi10"); // NOI18N
        FaktorRisikoTinggi10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi10ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi10KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi10);
        FaktorRisikoTinggi10.setBounds(620, 820, 80, 23);

        jLabel146.setText("Nilai :");
        jLabel146.setName("jLabel146"); // NOI18N
        FormInput.add(jLabel146);
        jLabel146.setBounds(690, 820, 50, 23);

        NilaiFaktorRisikoTinggi10.setEditable(false);
        NilaiFaktorRisikoTinggi10.setText("0");
        NilaiFaktorRisikoTinggi10.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi10.setName("NilaiFaktorRisikoTinggi10"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi10);
        NilaiFaktorRisikoTinggi10.setBounds(744, 820, 45, 23);

        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel147.setText("11.");
        jLabel147.setName("jLabel147"); // NOI18N
        FormInput.add(jLabel147);
        jLabel147.setBounds(54, 850, 20, 23);

        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel150.setText("Apakah anda memiliki riwayat sakit kanker endometrium ?");
        jLabel150.setName("jLabel150"); // NOI18N
        FormInput.add(jLabel150);
        jLabel150.setBounds(72, 850, 530, 23);

        FaktorRisikoTinggi11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi11.setName("FaktorRisikoTinggi11"); // NOI18N
        FaktorRisikoTinggi11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi11ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi11KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi11);
        FaktorRisikoTinggi11.setBounds(620, 850, 80, 23);

        jLabel151.setText("Nilai :");
        jLabel151.setName("jLabel151"); // NOI18N
        FormInput.add(jLabel151);
        jLabel151.setBounds(690, 850, 50, 23);

        NilaiFaktorRisikoTinggi11.setEditable(false);
        NilaiFaktorRisikoTinggi11.setText("0");
        NilaiFaktorRisikoTinggi11.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi11.setName("NilaiFaktorRisikoTinggi11"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi11);
        NilaiFaktorRisikoTinggi11.setBounds(744, 850, 45, 23);

        jLabel152.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel152.setText("Apakah anda diperiksa secara radiologis dan ditemukan hasil densitas yang sangat tinggi ?");
        jLabel152.setName("jLabel152"); // NOI18N
        FormInput.add(jLabel152);
        jLabel152.setBounds(72, 880, 530, 23);

        jLabel153.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel153.setText("12.");
        jLabel153.setName("jLabel153"); // NOI18N
        FormInput.add(jLabel153);
        jLabel153.setBounds(54, 880, 20, 23);

        FaktorRisikoTinggi12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi12.setName("FaktorRisikoTinggi12"); // NOI18N
        FaktorRisikoTinggi12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi12ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi12KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi12);
        FaktorRisikoTinggi12.setBounds(620, 880, 80, 23);

        jLabel154.setText("Nilai :");
        jLabel154.setName("jLabel154"); // NOI18N
        FormInput.add(jLabel154);
        jLabel154.setBounds(690, 880, 50, 23);

        NilaiFaktorRisikoTinggi12.setEditable(false);
        NilaiFaktorRisikoTinggi12.setText("0");
        NilaiFaktorRisikoTinggi12.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi12.setName("NilaiFaktorRisikoTinggi12"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi12);
        NilaiFaktorRisikoTinggi12.setBounds(744, 880, 45, 23);

        jLabel155.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel155.setText("Apakah anda memiliki silsilah keluarga yang menderita kanker ?");
        jLabel155.setName("jLabel155"); // NOI18N
        FormInput.add(jLabel155);
        jLabel155.setBounds(72, 910, 530, 23);

        jLabel156.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel156.setText("13.");
        jLabel156.setName("jLabel156"); // NOI18N
        FormInput.add(jLabel156);
        jLabel156.setBounds(54, 910, 20, 23);

        FaktorRisikoTinggi13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        FaktorRisikoTinggi13.setName("FaktorRisikoTinggi13"); // NOI18N
        FaktorRisikoTinggi13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FaktorRisikoTinggi13ItemStateChanged(evt);
            }
        });
        FaktorRisikoTinggi13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FaktorRisikoTinggi13KeyPressed(evt);
            }
        });
        FormInput.add(FaktorRisikoTinggi13);
        FaktorRisikoTinggi13.setBounds(620, 910, 80, 23);

        jLabel157.setText("Nilai :");
        jLabel157.setName("jLabel157"); // NOI18N
        FormInput.add(jLabel157);
        jLabel157.setBounds(690, 910, 50, 23);

        NilaiFaktorRisikoTinggi13.setEditable(false);
        NilaiFaktorRisikoTinggi13.setText("0");
        NilaiFaktorRisikoTinggi13.setFocusTraversalPolicyProvider(true);
        NilaiFaktorRisikoTinggi13.setName("NilaiFaktorRisikoTinggi13"); // NOI18N
        FormInput.add(NilaiFaktorRisikoTinggi13);
        NilaiFaktorRisikoTinggi13.setBounds(744, 910, 45, 23);

        jLabel158.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel158.setText("C. Kecurigaan Keganasan (Ya=10,Tidak=0)");
        jLabel158.setName("jLabel158"); // NOI18N
        FormInput.add(jLabel158);
        jLabel158.setBounds(44, 940, 310, 23);

        jLabel159.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel159.setText("1.");
        jLabel159.setName("jLabel159"); // NOI18N
        FormInput.add(jLabel159);
        jLabel159.setBounds(54, 960, 20, 23);

        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel160.setText("Apakah ada benjolan/tumor di payudara anda ?");
        jLabel160.setName("jLabel160"); // NOI18N
        FormInput.add(jLabel160);
        jLabel160.setBounds(72, 960, 470, 23);

        KecurigaanKeganasan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KecurigaanKeganasan1.setName("KecurigaanKeganasan1"); // NOI18N
        KecurigaanKeganasan1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KecurigaanKeganasan1ItemStateChanged(evt);
            }
        });
        KecurigaanKeganasan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecurigaanKeganasan1KeyPressed(evt);
            }
        });
        FormInput.add(KecurigaanKeganasan1);
        KecurigaanKeganasan1.setBounds(620, 960, 80, 23);

        jLabel161.setText("Nilai :");
        jLabel161.setName("jLabel161"); // NOI18N
        FormInput.add(jLabel161);
        jLabel161.setBounds(690, 960, 50, 23);

        NilaiKecurigaanKeganasan1.setEditable(false);
        NilaiKecurigaanKeganasan1.setText("0");
        NilaiKecurigaanKeganasan1.setFocusTraversalPolicyProvider(true);
        NilaiKecurigaanKeganasan1.setName("NilaiKecurigaanKeganasan1"); // NOI18N
        FormInput.add(NilaiKecurigaanKeganasan1);
        NilaiKecurigaanKeganasan1.setBounds(744, 960, 45, 23);

        jLabel162.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel162.setText("2.");
        jLabel162.setName("jLabel162"); // NOI18N
        FormInput.add(jLabel162);
        jLabel162.setBounds(54, 990, 20, 23);

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel163.setText("Apakah kulit/puting susu anda tertarik ke dalam ?");
        jLabel163.setName("jLabel163"); // NOI18N
        FormInput.add(jLabel163);
        jLabel163.setBounds(72, 990, 470, 23);

        KecurigaanKeganasan2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KecurigaanKeganasan2.setName("KecurigaanKeganasan2"); // NOI18N
        KecurigaanKeganasan2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KecurigaanKeganasan2ItemStateChanged(evt);
            }
        });
        KecurigaanKeganasan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecurigaanKeganasan2KeyPressed(evt);
            }
        });
        FormInput.add(KecurigaanKeganasan2);
        KecurigaanKeganasan2.setBounds(620, 990, 80, 23);

        jLabel164.setText("Nilai :");
        jLabel164.setName("jLabel164"); // NOI18N
        FormInput.add(jLabel164);
        jLabel164.setBounds(690, 990, 50, 23);

        NilaiKecurigaanKeganasan2.setEditable(false);
        NilaiKecurigaanKeganasan2.setText("0");
        NilaiKecurigaanKeganasan2.setFocusTraversalPolicyProvider(true);
        NilaiKecurigaanKeganasan2.setName("NilaiKecurigaanKeganasan2"); // NOI18N
        FormInput.add(NilaiKecurigaanKeganasan2);
        NilaiKecurigaanKeganasan2.setBounds(744, 990, 45, 23);

        jLabel165.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel165.setText("Apakah areola payudara anda berwarna merah muda atau kecoklat-coklatan sampai menjadi oedema");
        jLabel165.setName("jLabel165"); // NOI18N
        FormInput.add(jLabel165);
        jLabel165.setBounds(72, 1015, 530, 23);

        jLabel166.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel166.setText("3.");
        jLabel166.setName("jLabel166"); // NOI18N
        FormInput.add(jLabel166);
        jLabel166.setBounds(54, 1020, 20, 23);

        KecurigaanKeganasan3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KecurigaanKeganasan3.setName("KecurigaanKeganasan3"); // NOI18N
        KecurigaanKeganasan3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KecurigaanKeganasan3ItemStateChanged(evt);
            }
        });
        KecurigaanKeganasan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecurigaanKeganasan3KeyPressed(evt);
            }
        });
        FormInput.add(KecurigaanKeganasan3);
        KecurigaanKeganasan3.setBounds(620, 1020, 80, 23);

        jLabel167.setText("Nilai :");
        jLabel167.setName("jLabel167"); // NOI18N
        FormInput.add(jLabel167);
        jLabel167.setBounds(690, 1020, 50, 23);

        NilaiKecurigaanKeganasan3.setEditable(false);
        NilaiKecurigaanKeganasan3.setText("0");
        NilaiKecurigaanKeganasan3.setFocusTraversalPolicyProvider(true);
        NilaiKecurigaanKeganasan3.setName("NilaiKecurigaanKeganasan3"); // NOI18N
        FormInput.add(NilaiKecurigaanKeganasan3);
        NilaiKecurigaanKeganasan3.setBounds(744, 1020, 45, 23);

        jLabel168.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel168.setText("hingga kulit kelihatan seperti kulit jeruk (peau dorange), mengkerut, atau timbul borok (ulkus) ?");
        jLabel168.setName("jLabel168"); // NOI18N
        FormInput.add(jLabel168);
        jLabel168.setBounds(72, 1028, 490, 23);

        jLabel169.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel169.setText("4.");
        jLabel169.setName("jLabel169"); // NOI18N
        FormInput.add(jLabel169);
        jLabel169.setBounds(54, 1050, 20, 23);

        jLabel170.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel170.setText("Apakah puting susu anda mengeluarkan cairan tidak normal, darah atau nanah ?");
        jLabel170.setName("jLabel170"); // NOI18N
        FormInput.add(jLabel170);
        jLabel170.setBounds(72, 1050, 470, 23);

        KecurigaanKeganasan4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KecurigaanKeganasan4.setName("KecurigaanKeganasan4"); // NOI18N
        KecurigaanKeganasan4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KecurigaanKeganasan4ItemStateChanged(evt);
            }
        });
        KecurigaanKeganasan4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecurigaanKeganasan4KeyPressed(evt);
            }
        });
        FormInput.add(KecurigaanKeganasan4);
        KecurigaanKeganasan4.setBounds(620, 1050, 80, 23);

        jLabel171.setText("Nilai :");
        jLabel171.setName("jLabel171"); // NOI18N
        FormInput.add(jLabel171);
        jLabel171.setBounds(690, 1050, 50, 23);

        NilaiKecurigaanKeganasan4.setEditable(false);
        NilaiKecurigaanKeganasan4.setText("0");
        NilaiKecurigaanKeganasan4.setFocusTraversalPolicyProvider(true);
        NilaiKecurigaanKeganasan4.setName("NilaiKecurigaanKeganasan4"); // NOI18N
        FormInput.add(NilaiKecurigaanKeganasan4);
        NilaiKecurigaanKeganasan4.setBounds(744, 1050, 45, 23);

        jLabel172.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel172.setText("5.");
        jLabel172.setName("jLabel172"); // NOI18N
        FormInput.add(jLabel172);
        jLabel172.setBounds(54, 1080, 20, 23);

        jLabel173.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel173.setText("Apakah ada benjolan di ketiak anda berdiameter lebih 2,5 cm, dapat melekat satu sama lain ?");
        jLabel173.setName("jLabel173"); // NOI18N
        FormInput.add(jLabel173);
        jLabel173.setBounds(72, 1080, 470, 23);

        KecurigaanKeganasan5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KecurigaanKeganasan5.setName("KecurigaanKeganasan5"); // NOI18N
        KecurigaanKeganasan5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KecurigaanKeganasan5ItemStateChanged(evt);
            }
        });
        KecurigaanKeganasan5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecurigaanKeganasan5KeyPressed(evt);
            }
        });
        FormInput.add(KecurigaanKeganasan5);
        KecurigaanKeganasan5.setBounds(620, 1080, 80, 23);

        jLabel174.setText("Nilai :");
        jLabel174.setName("jLabel174"); // NOI18N
        FormInput.add(jLabel174);
        jLabel174.setBounds(690, 1080, 50, 23);

        NilaiKecurigaanKeganasan5.setEditable(false);
        NilaiKecurigaanKeganasan5.setText("0");
        NilaiKecurigaanKeganasan5.setFocusTraversalPolicyProvider(true);
        NilaiKecurigaanKeganasan5.setName("NilaiKecurigaanKeganasan5"); // NOI18N
        FormInput.add(NilaiKecurigaanKeganasan5);
        NilaiKecurigaanKeganasan5.setBounds(744, 1080, 45, 23);

        jLabel175.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel175.setText("Apakah ada benjolan di sekitar tulang belikat ?");
        jLabel175.setName("jLabel175"); // NOI18N
        FormInput.add(jLabel175);
        jLabel175.setBounds(72, 1110, 470, 23);

        jLabel176.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel176.setText("6.");
        jLabel176.setName("jLabel176"); // NOI18N
        FormInput.add(jLabel176);
        jLabel176.setBounds(54, 1110, 20, 23);

        KecurigaanKeganasan6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KecurigaanKeganasan6.setName("KecurigaanKeganasan6"); // NOI18N
        KecurigaanKeganasan6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KecurigaanKeganasan6ItemStateChanged(evt);
            }
        });
        KecurigaanKeganasan6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecurigaanKeganasan6KeyPressed(evt);
            }
        });
        FormInput.add(KecurigaanKeganasan6);
        KecurigaanKeganasan6.setBounds(620, 1110, 80, 23);

        jLabel177.setText("Nilai :");
        jLabel177.setName("jLabel177"); // NOI18N
        FormInput.add(jLabel177);
        jLabel177.setBounds(690, 1110, 50, 23);

        NilaiKecurigaanKeganasan6.setEditable(false);
        NilaiKecurigaanKeganasan6.setText("0");
        NilaiKecurigaanKeganasan6.setFocusTraversalPolicyProvider(true);
        NilaiKecurigaanKeganasan6.setName("NilaiKecurigaanKeganasan6"); // NOI18N
        FormInput.add(NilaiKecurigaanKeganasan6);
        NilaiKecurigaanKeganasan6.setBounds(744, 1110, 45, 23);

        jLabel178.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel178.setText("Apakah lengan anda bengkak ?");
        jLabel178.setName("jLabel178"); // NOI18N
        FormInput.add(jLabel178);
        jLabel178.setBounds(72, 1140, 470, 23);

        jLabel179.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel179.setText("7.");
        jLabel179.setName("jLabel179"); // NOI18N
        FormInput.add(jLabel179);
        jLabel179.setBounds(54, 1140, 20, 23);

        KecurigaanKeganasan7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KecurigaanKeganasan7.setName("KecurigaanKeganasan7"); // NOI18N
        KecurigaanKeganasan7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KecurigaanKeganasan7ItemStateChanged(evt);
            }
        });
        KecurigaanKeganasan7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecurigaanKeganasan7KeyPressed(evt);
            }
        });
        FormInput.add(KecurigaanKeganasan7);
        KecurigaanKeganasan7.setBounds(620, 1140, 80, 23);

        jLabel180.setText("Nilai :");
        jLabel180.setName("jLabel180"); // NOI18N
        FormInput.add(jLabel180);
        jLabel180.setBounds(690, 1140, 50, 23);

        NilaiKecurigaanKeganasan7.setEditable(false);
        NilaiKecurigaanKeganasan7.setText("0");
        NilaiKecurigaanKeganasan7.setFocusTraversalPolicyProvider(true);
        NilaiKecurigaanKeganasan7.setName("NilaiKecurigaanKeganasan7"); // NOI18N
        FormInput.add(NilaiKecurigaanKeganasan7);
        NilaiKecurigaanKeganasan7.setBounds(744, 1140, 45, 23);

        jLabel181.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel181.setText("Apakah ada erosi atau luka yang tidak sembuh-sembuh pada puting susu ?");
        jLabel181.setName("jLabel181"); // NOI18N
        FormInput.add(jLabel181);
        jLabel181.setBounds(72, 1170, 470, 23);

        jLabel182.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel182.setText("8.");
        jLabel182.setName("jLabel182"); // NOI18N
        FormInput.add(jLabel182);
        jLabel182.setBounds(54, 1170, 20, 23);

        KecurigaanKeganasan8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        KecurigaanKeganasan8.setName("KecurigaanKeganasan8"); // NOI18N
        KecurigaanKeganasan8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                KecurigaanKeganasan8ItemStateChanged(evt);
            }
        });
        KecurigaanKeganasan8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecurigaanKeganasan8KeyPressed(evt);
            }
        });
        FormInput.add(KecurigaanKeganasan8);
        KecurigaanKeganasan8.setBounds(620, 1170, 80, 23);

        jLabel183.setText("Nilai :");
        jLabel183.setName("jLabel183"); // NOI18N
        FormInput.add(jLabel183);
        jLabel183.setBounds(690, 1170, 50, 23);

        NilaiKecurigaanKeganasan8.setEditable(false);
        NilaiKecurigaanKeganasan8.setText("0");
        NilaiKecurigaanKeganasan8.setFocusTraversalPolicyProvider(true);
        NilaiKecurigaanKeganasan8.setName("NilaiKecurigaanKeganasan8"); // NOI18N
        FormInput.add(NilaiKecurigaanKeganasan8);
        NilaiKecurigaanKeganasan8.setBounds(744, 1170, 45, 23);

        jLabel184.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel184.setText("Hasil Pemeriksaan SADANIS");
        jLabel184.setName("jLabel184"); // NOI18N
        FormInput.add(jLabel184);
        jLabel184.setBounds(44, 1260, 150, 23);

        HasilPemeriksaanSadanis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada Benjolan", "Benjolan", "Curiga Kanker" }));
        HasilPemeriksaanSadanis.setName("HasilPemeriksaanSadanis"); // NOI18N
        HasilPemeriksaanSadanis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilPemeriksaanSadanisKeyPressed(evt);
            }
        });
        FormInput.add(HasilPemeriksaanSadanis);
        HasilPemeriksaanSadanis.setBounds(191, 1260, 170, 23);

        jLabel185.setText(":");
        jLabel185.setName("jLabel185"); // NOI18N
        FormInput.add(jLabel185);
        jLabel185.setBounds(0, 1260, 187, 23);

        jLabel186.setText("Tindak Lanjut Hasil Pemeriksaan SADANIS :");
        jLabel186.setName("jLabel186"); // NOI18N
        FormInput.add(jLabel186);
        jLabel186.setBounds(419, 1260, 240, 23);

        TindakLanjutSadanis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Dirujuk", "Dirujuk" }));
        TindakLanjutSadanis.setName("TindakLanjutSadanis"); // NOI18N
        TindakLanjutSadanis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutSadanisKeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjutSadanis);
        TindakLanjutSadanis.setBounds(663, 1260, 126, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 1290, 807, 1);

        jLabel187.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel187.setText("III. INTERPRETASI");
        jLabel187.setName("jLabel187"); // NOI18N
        FormInput.add(jLabel187);
        jLabel187.setBounds(10, 1290, 200, 23);

        jLabel188.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel188.setText("Hasil Skrining");
        jLabel188.setName("jLabel188"); // NOI18N
        FormInput.add(jLabel188);
        jLabel188.setBounds(44, 1310, 80, 23);

        HasilSkrining.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Kemungkinan Kelainan Payudara Jinak", "Curiga Kelainan Payudara Ganas" }));
        HasilSkrining.setName("HasilSkrining"); // NOI18N
        HasilSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilSkriningKeyPressed(evt);
            }
        });
        FormInput.add(HasilSkrining);
        HasilSkrining.setBounds(121, 1310, 240, 23);

        jLabel189.setText(":");
        jLabel189.setName("jLabel189"); // NOI18N
        FormInput.add(jLabel189);
        jLabel189.setBounds(0, 1310, 117, 23);

        jLabel190.setText("Keterangan :");
        jLabel190.setName("jLabel190"); // NOI18N
        FormInput.add(jLabel190);
        jLabel190.setBounds(356, 1310, 90, 23);

        Keterangan.setFocusTraversalPolicyProvider(true);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(450, 1310, 339, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(44, 530, 763, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(44, 940, 763, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(44, 1200, 763, 1);

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
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
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
            Valid.pindah(evt,FaktorRisikoTinggi6,BtnBatal);
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
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
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
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdPetugas.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Awal 14</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.A.14</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T.10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T 11</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T 12</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Faktor Risiko Tinggi 13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.R.T 13</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kecurigaan Keganasan 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>N.K.G.8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Total Skor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Pemeriksaan SADANIS</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindak Lanjut Sadanis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hasil Skrining</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='6000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataSkriningRisikoKankerPayudara.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='6000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA SEKRINING RISIKO KANKER PAYUDARA<br><br></font>"+        
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

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

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
        Valid.pindah(evt,TCari,FaktorAwal1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnSkriningRisikoKankerPayudaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningRisikoKankerPayudaraActionPerformed
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
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirSkriningRisikoKankerPayudara.jasper","report","::[ Formulir Skrining Risiko Kanker Payudara ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_payudara.nip,"+
                    "petugas.nama,skrining_risiko_kanker_payudara.tanggal,skrining_risiko_kanker_payudara.faktor_risiko_awal1,skrining_risiko_kanker_payudara.nilai_risiko_awal1,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal2,skrining_risiko_kanker_payudara.nilai_risiko_awal2,skrining_risiko_kanker_payudara.faktor_risiko_awal3,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal3,skrining_risiko_kanker_payudara.faktor_risiko_awal4,skrining_risiko_kanker_payudara.nilai_risiko_awal4,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal5,skrining_risiko_kanker_payudara.nilai_risiko_awal5,skrining_risiko_kanker_payudara.faktor_risiko_awal6,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal6,skrining_risiko_kanker_payudara.faktor_risiko_awal7,skrining_risiko_kanker_payudara.nilai_risiko_awal7,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal8,skrining_risiko_kanker_payudara.nilai_risiko_awal8,skrining_risiko_kanker_payudara.faktor_risiko_awal9,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal9,skrining_risiko_kanker_payudara.faktor_risiko_awal10,skrining_risiko_kanker_payudara.nilai_risiko_awal10,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal11,skrining_risiko_kanker_payudara.nilai_risiko_awal11,skrining_risiko_kanker_payudara.faktor_risiko_awal12,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal12,skrining_risiko_kanker_payudara.faktor_risiko_awal13,skrining_risiko_kanker_payudara.nilai_risiko_awal13,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal14,skrining_risiko_kanker_payudara.nilai_risiko_awal14,skrining_risiko_kanker_payudara.faktor_risiko_tinggi1,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi1,skrining_risiko_kanker_payudara.faktor_risiko_tinggi2,skrining_risiko_kanker_payudara.nilai_risiko_tinggi2,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi3,skrining_risiko_kanker_payudara.nilai_risiko_tinggi3,skrining_risiko_kanker_payudara.faktor_risiko_tinggi4,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi4,skrining_risiko_kanker_payudara.faktor_risiko_tinggi5,skrining_risiko_kanker_payudara.nilai_risiko_tinggi5,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi6,skrining_risiko_kanker_payudara.nilai_risiko_tinggi6,skrining_risiko_kanker_payudara.faktor_risiko_tinggi7,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi7,skrining_risiko_kanker_payudara.faktor_risiko_tinggi8,skrining_risiko_kanker_payudara.nilai_risiko_tinggi8,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi9,skrining_risiko_kanker_payudara.nilai_risiko_tinggi9,skrining_risiko_kanker_payudara.faktor_risiko_tinggi10,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi10,skrining_risiko_kanker_payudara.faktor_risiko_tinggi11,skrining_risiko_kanker_payudara.nilai_risiko_tinggi11,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi12,skrining_risiko_kanker_payudara.nilai_risiko_tinggi12,skrining_risiko_kanker_payudara.faktor_risiko_tinggi13,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi13,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas1,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas1,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas2,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas2,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas3,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas3,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas4,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas4,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas5,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas5,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas6,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas6,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas7,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas7,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas8,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas8,skrining_risiko_kanker_payudara.total_skor,"+
                    "skrining_risiko_kanker_payudara.hasil_sadanis,skrining_risiko_kanker_payudara.tindak_lanjut_sadanis,skrining_risiko_kanker_payudara.hasil_skrining,"+
                    "skrining_risiko_kanker_payudara.keterangan from skrining_risiko_kanker_payudara inner join reg_periksa on skrining_risiko_kanker_payudara.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_payudara.nip=petugas.nip "+
                    "where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSkriningRisikoKankerPayudaraActionPerformed

    private void FaktorAwal1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal1ItemStateChanged
        if(FaktorAwal1.getSelectedIndex()==1){
            NilaiFaktorAwal1.setText("1");
        }else{
            NilaiFaktorAwal1.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal1ItemStateChanged

    private void FaktorAwal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal1KeyPressed
        Valid.pindah(evt,TCari,FaktorAwal2);
    }//GEN-LAST:event_FaktorAwal1KeyPressed

    private void FaktorAwal2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal2ItemStateChanged
        if(FaktorAwal2.getSelectedIndex()==1){
            NilaiFaktorAwal2.setText("1");
        }else{
            NilaiFaktorAwal2.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal2ItemStateChanged

    private void FaktorAwal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal2KeyPressed
        Valid.pindah(evt,FaktorAwal1,FaktorAwal3);
    }//GEN-LAST:event_FaktorAwal2KeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void FaktorRisikoTinggi1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi1ItemStateChanged
        if(FaktorRisikoTinggi1.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi1.setText("5");
        }else{
            NilaiFaktorRisikoTinggi1.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi1ItemStateChanged

    private void FaktorRisikoTinggi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi1KeyPressed
        Valid.pindah(evt,FaktorAwal14,FaktorRisikoTinggi2);
    }//GEN-LAST:event_FaktorRisikoTinggi1KeyPressed

    private void FaktorRisikoTinggi2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi2ItemStateChanged
        if(FaktorRisikoTinggi2.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi2.setText("5");
        }else{
            NilaiFaktorRisikoTinggi2.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi2ItemStateChanged

    private void FaktorRisikoTinggi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi2KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi1,FaktorRisikoTinggi3);
    }//GEN-LAST:event_FaktorRisikoTinggi2KeyPressed

    private void RekomendasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RekomendasiKeyPressed
        //Valid.pindah(evt,Lapor,SG1);
    }//GEN-LAST:event_RekomendasiKeyPressed

    private void FaktorRisikoTinggi3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi3ItemStateChanged
        if(FaktorRisikoTinggi3.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi3.setText("5");
        }else{
            NilaiFaktorRisikoTinggi3.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi3ItemStateChanged

    private void FaktorRisikoTinggi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi3KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi2,FaktorRisikoTinggi4);
    }//GEN-LAST:event_FaktorRisikoTinggi3KeyPressed

    private void FaktorRisikoTinggi4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi4ItemStateChanged
        if(FaktorRisikoTinggi4.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi4.setText("5");
        }else{
            NilaiFaktorRisikoTinggi4.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi4ItemStateChanged

    private void FaktorRisikoTinggi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi4KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi3,FaktorRisikoTinggi5);
    }//GEN-LAST:event_FaktorRisikoTinggi4KeyPressed

    private void FaktorRisikoTinggi5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi5ItemStateChanged
        if(FaktorRisikoTinggi5.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi5.setText("5");
        }else{
            NilaiFaktorRisikoTinggi5.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi5ItemStateChanged

    private void FaktorRisikoTinggi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi5KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi4,FaktorRisikoTinggi6);
    }//GEN-LAST:event_FaktorRisikoTinggi5KeyPressed

    private void FaktorRisikoTinggi6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi6ItemStateChanged
        if(FaktorRisikoTinggi6.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi6.setText("5");
        }else{
            NilaiFaktorRisikoTinggi6.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi6ItemStateChanged

    private void FaktorRisikoTinggi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi6KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi5,FaktorRisikoTinggi7);
    }//GEN-LAST:event_FaktorRisikoTinggi6KeyPressed

    private void FaktorAwal3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal3ItemStateChanged
        if(FaktorAwal3.getSelectedIndex()==1){
            NilaiFaktorAwal3.setText("1");
        }else{
            NilaiFaktorAwal3.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal3ItemStateChanged

    private void FaktorAwal3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal3KeyPressed
        Valid.pindah(evt,FaktorAwal2,FaktorAwal4);
    }//GEN-LAST:event_FaktorAwal3KeyPressed

    private void FaktorAwal4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal4ItemStateChanged
        if(FaktorAwal4.getSelectedIndex()==1){
            NilaiFaktorAwal4.setText("1");
        }else{
            NilaiFaktorAwal4.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal4ItemStateChanged

    private void FaktorAwal4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal4KeyPressed
        Valid.pindah(evt,FaktorAwal3,FaktorAwal5);
    }//GEN-LAST:event_FaktorAwal4KeyPressed

    private void FaktorAwal5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal5ItemStateChanged
        if(FaktorAwal5.getSelectedIndex()==1){
            NilaiFaktorAwal5.setText("1");
        }else{
            NilaiFaktorAwal5.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal5ItemStateChanged

    private void FaktorAwal5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal5KeyPressed
        Valid.pindah(evt,FaktorAwal4,FaktorAwal6);
    }//GEN-LAST:event_FaktorAwal5KeyPressed

    private void FaktorAwal6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal6ItemStateChanged
        if(FaktorAwal6.getSelectedIndex()==1){
            NilaiFaktorAwal6.setText("1");
        }else{
            NilaiFaktorAwal6.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal6ItemStateChanged

    private void FaktorAwal6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal6KeyPressed
        Valid.pindah(evt,FaktorAwal5,FaktorAwal7);
    }//GEN-LAST:event_FaktorAwal6KeyPressed

    private void FaktorAwal7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal7ItemStateChanged
        if(FaktorAwal7.getSelectedIndex()==1){
            NilaiFaktorAwal7.setText("1");
        }else{
            NilaiFaktorAwal7.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal7ItemStateChanged

    private void FaktorAwal7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal7KeyPressed
        Valid.pindah(evt,FaktorAwal6,FaktorAwal8);
    }//GEN-LAST:event_FaktorAwal7KeyPressed

    private void FaktorAwal8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal8ItemStateChanged
        if(FaktorAwal8.getSelectedIndex()==1){
            NilaiFaktorAwal8.setText("1");
        }else{
            NilaiFaktorAwal8.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal8ItemStateChanged

    private void FaktorAwal8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal8KeyPressed
        Valid.pindah(evt,FaktorAwal7,FaktorAwal9);
    }//GEN-LAST:event_FaktorAwal8KeyPressed

    private void FaktorAwal9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal9ItemStateChanged
        if(FaktorAwal9.getSelectedIndex()==1){
            NilaiFaktorAwal9.setText("1");
        }else{
            NilaiFaktorAwal9.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal9ItemStateChanged

    private void FaktorAwal9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal9KeyPressed
        Valid.pindah(evt,FaktorAwal8,FaktorAwal10);
    }//GEN-LAST:event_FaktorAwal9KeyPressed

    private void FaktorAwal10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal10ItemStateChanged
        if(FaktorAwal10.getSelectedIndex()==1){
            NilaiFaktorAwal10.setText("1");
        }else{
            NilaiFaktorAwal10.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal10ItemStateChanged

    private void FaktorAwal10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal10KeyPressed
        Valid.pindah(evt,FaktorAwal9,FaktorAwal11);
    }//GEN-LAST:event_FaktorAwal10KeyPressed

    private void FaktorAwal11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal11ItemStateChanged
        if(FaktorAwal11.getSelectedIndex()==1){
            NilaiFaktorAwal11.setText("1");
        }else{
            NilaiFaktorAwal11.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal11ItemStateChanged

    private void FaktorAwal11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal11KeyPressed
        Valid.pindah(evt,FaktorAwal10,FaktorAwal12);
    }//GEN-LAST:event_FaktorAwal11KeyPressed

    private void FaktorAwal12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal12ItemStateChanged
        if(FaktorAwal12.getSelectedIndex()==1){
            NilaiFaktorAwal12.setText("1");
        }else{
            NilaiFaktorAwal12.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal12ItemStateChanged

    private void FaktorAwal12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal12KeyPressed
        Valid.pindah(evt,FaktorAwal11,FaktorAwal13);
    }//GEN-LAST:event_FaktorAwal12KeyPressed

    private void FaktorAwal13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal13ItemStateChanged
        if(FaktorAwal13.getSelectedIndex()==1){
            NilaiFaktorAwal13.setText("1");
        }else{
            NilaiFaktorAwal13.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal13ItemStateChanged

    private void FaktorAwal13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal13KeyPressed
        Valid.pindah(evt,FaktorAwal12,FaktorAwal14);
    }//GEN-LAST:event_FaktorAwal13KeyPressed

    private void FaktorAwal14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorAwal14ItemStateChanged
        if(FaktorAwal14.getSelectedIndex()==1){
            NilaiFaktorAwal14.setText("1");
        }else{
            NilaiFaktorAwal14.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorAwal14ItemStateChanged

    private void FaktorAwal14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorAwal14KeyPressed
        Valid.pindah(evt,FaktorAwal13,FaktorRisikoTinggi1); 
    }//GEN-LAST:event_FaktorAwal14KeyPressed

    private void FaktorRisikoTinggi7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi7ItemStateChanged
        if(FaktorRisikoTinggi7.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi7.setText("5");
        }else{
            NilaiFaktorRisikoTinggi7.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi7ItemStateChanged

    private void FaktorRisikoTinggi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi7KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi6,FaktorRisikoTinggi8);
    }//GEN-LAST:event_FaktorRisikoTinggi7KeyPressed

    private void FaktorRisikoTinggi8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi8ItemStateChanged
        if(FaktorRisikoTinggi8.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi8.setText("5");
        }else{
            NilaiFaktorRisikoTinggi8.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi8ItemStateChanged

    private void FaktorRisikoTinggi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi8KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi7,FaktorRisikoTinggi9);
    }//GEN-LAST:event_FaktorRisikoTinggi8KeyPressed

    private void FaktorRisikoTinggi9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi9ItemStateChanged
        if(FaktorRisikoTinggi9.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi9.setText("5");
        }else{
            NilaiFaktorRisikoTinggi9.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi9ItemStateChanged

    private void FaktorRisikoTinggi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi9KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi8,FaktorRisikoTinggi10);
    }//GEN-LAST:event_FaktorRisikoTinggi9KeyPressed

    private void FaktorRisikoTinggi10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi10ItemStateChanged
        if(FaktorRisikoTinggi10.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi10.setText("5");
        }else{
            NilaiFaktorRisikoTinggi10.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi10ItemStateChanged

    private void FaktorRisikoTinggi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi10KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi9,FaktorRisikoTinggi11);
    }//GEN-LAST:event_FaktorRisikoTinggi10KeyPressed

    private void FaktorRisikoTinggi11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi11ItemStateChanged
        if(FaktorRisikoTinggi11.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi11.setText("5");
        }else{
            NilaiFaktorRisikoTinggi11.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi11ItemStateChanged

    private void FaktorRisikoTinggi11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi11KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi10,FaktorRisikoTinggi12);
    }//GEN-LAST:event_FaktorRisikoTinggi11KeyPressed

    private void FaktorRisikoTinggi12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi12ItemStateChanged
        if(FaktorRisikoTinggi12.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi12.setText("5");
        }else{
            NilaiFaktorRisikoTinggi12.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi12ItemStateChanged

    private void FaktorRisikoTinggi12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi12KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi11,FaktorRisikoTinggi13);
    }//GEN-LAST:event_FaktorRisikoTinggi12KeyPressed

    private void FaktorRisikoTinggi13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi13ItemStateChanged
        if(FaktorRisikoTinggi13.getSelectedIndex()==1){
            NilaiFaktorRisikoTinggi13.setText("5");
        }else{
            NilaiFaktorRisikoTinggi13.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_FaktorRisikoTinggi13ItemStateChanged

    private void FaktorRisikoTinggi13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FaktorRisikoTinggi13KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi12,KecurigaanKeganasan1);
    }//GEN-LAST:event_FaktorRisikoTinggi13KeyPressed

    private void KecurigaanKeganasan1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan1ItemStateChanged
        if(KecurigaanKeganasan1.getSelectedIndex()==1){
            NilaiKecurigaanKeganasan1.setText("10");
        }else{
            NilaiKecurigaanKeganasan1.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KecurigaanKeganasan1ItemStateChanged

    private void KecurigaanKeganasan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan1KeyPressed
        Valid.pindah(evt,FaktorRisikoTinggi13,KecurigaanKeganasan2);
    }//GEN-LAST:event_KecurigaanKeganasan1KeyPressed

    private void KecurigaanKeganasan2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan2ItemStateChanged
        if(KecurigaanKeganasan2.getSelectedIndex()==1){
            NilaiKecurigaanKeganasan2.setText("10");
        }else{
            NilaiKecurigaanKeganasan2.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KecurigaanKeganasan2ItemStateChanged

    private void KecurigaanKeganasan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan2KeyPressed
        Valid.pindah(evt,KecurigaanKeganasan1,KecurigaanKeganasan3);
    }//GEN-LAST:event_KecurigaanKeganasan2KeyPressed

    private void KecurigaanKeganasan3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan3ItemStateChanged
        if(KecurigaanKeganasan3.getSelectedIndex()==1){
            NilaiKecurigaanKeganasan3.setText("10");
        }else{
            NilaiKecurigaanKeganasan3.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KecurigaanKeganasan3ItemStateChanged

    private void KecurigaanKeganasan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan3KeyPressed
        Valid.pindah(evt,KecurigaanKeganasan2,KecurigaanKeganasan4);
    }//GEN-LAST:event_KecurigaanKeganasan3KeyPressed

    private void KecurigaanKeganasan4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan4ItemStateChanged
        if(KecurigaanKeganasan4.getSelectedIndex()==1){
            NilaiKecurigaanKeganasan4.setText("10");
        }else{
            NilaiKecurigaanKeganasan4.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KecurigaanKeganasan4ItemStateChanged

    private void KecurigaanKeganasan4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan4KeyPressed
        Valid.pindah(evt,KecurigaanKeganasan3,KecurigaanKeganasan5);
    }//GEN-LAST:event_KecurigaanKeganasan4KeyPressed

    private void KecurigaanKeganasan5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan5ItemStateChanged
        if(KecurigaanKeganasan5.getSelectedIndex()==1){
            NilaiKecurigaanKeganasan5.setText("10");
        }else{
            NilaiKecurigaanKeganasan5.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KecurigaanKeganasan5ItemStateChanged

    private void KecurigaanKeganasan5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan5KeyPressed
        Valid.pindah(evt,KecurigaanKeganasan4,KecurigaanKeganasan6);
    }//GEN-LAST:event_KecurigaanKeganasan5KeyPressed

    private void KecurigaanKeganasan6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan6ItemStateChanged
        if(KecurigaanKeganasan6.getSelectedIndex()==1){
            NilaiKecurigaanKeganasan6.setText("10");
        }else{
            NilaiKecurigaanKeganasan6.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KecurigaanKeganasan6ItemStateChanged

    private void KecurigaanKeganasan6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan6KeyPressed
        Valid.pindah(evt,KecurigaanKeganasan5,KecurigaanKeganasan7);
    }//GEN-LAST:event_KecurigaanKeganasan6KeyPressed

    private void KecurigaanKeganasan7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan7ItemStateChanged
        if(KecurigaanKeganasan7.getSelectedIndex()==1){
            NilaiKecurigaanKeganasan7.setText("10");
        }else{
            NilaiKecurigaanKeganasan7.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KecurigaanKeganasan7ItemStateChanged

    private void KecurigaanKeganasan7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan7KeyPressed
        Valid.pindah(evt,KecurigaanKeganasan6,KecurigaanKeganasan8);
    }//GEN-LAST:event_KecurigaanKeganasan7KeyPressed

    private void KecurigaanKeganasan8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan8ItemStateChanged
        if(KecurigaanKeganasan8.getSelectedIndex()==1){
            NilaiKecurigaanKeganasan8.setText("10");
        }else{
            NilaiKecurigaanKeganasan8.setText("0");
        }
        isTotal();
    }//GEN-LAST:event_KecurigaanKeganasan8ItemStateChanged

    private void KecurigaanKeganasan8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecurigaanKeganasan8KeyPressed
        Valid.pindah(evt,KecurigaanKeganasan7,HasilPemeriksaanSadanis);
    }//GEN-LAST:event_KecurigaanKeganasan8KeyPressed

    private void HasilPemeriksaanSadanisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilPemeriksaanSadanisKeyPressed
        Valid.pindah(evt,KecurigaanKeganasan8,TindakLanjutSadanis);
    }//GEN-LAST:event_HasilPemeriksaanSadanisKeyPressed

    private void TindakLanjutSadanisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutSadanisKeyPressed
        Valid.pindah(evt,HasilPemeriksaanSadanis,HasilSkrining);
    }//GEN-LAST:event_TindakLanjutSadanisKeyPressed

    private void HasilSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilSkriningKeyPressed
        Valid.pindah(evt,TindakLanjutSadanis,Keterangan);
    }//GEN-LAST:event_HasilSkriningKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,HasilSkrining,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningRisikoKankerPayudara dialog = new RMSkriningRisikoKankerPayudara(new javax.swing.JFrame(), true);
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
    private widget.ComboBox FaktorAwal1;
    private widget.ComboBox FaktorAwal10;
    private widget.ComboBox FaktorAwal11;
    private widget.ComboBox FaktorAwal12;
    private widget.ComboBox FaktorAwal13;
    private widget.ComboBox FaktorAwal14;
    private widget.ComboBox FaktorAwal2;
    private widget.ComboBox FaktorAwal3;
    private widget.ComboBox FaktorAwal4;
    private widget.ComboBox FaktorAwal5;
    private widget.ComboBox FaktorAwal6;
    private widget.ComboBox FaktorAwal7;
    private widget.ComboBox FaktorAwal8;
    private widget.ComboBox FaktorAwal9;
    private widget.ComboBox FaktorRisikoTinggi1;
    private widget.ComboBox FaktorRisikoTinggi10;
    private widget.ComboBox FaktorRisikoTinggi11;
    private widget.ComboBox FaktorRisikoTinggi12;
    private widget.ComboBox FaktorRisikoTinggi13;
    private widget.ComboBox FaktorRisikoTinggi2;
    private widget.ComboBox FaktorRisikoTinggi3;
    private widget.ComboBox FaktorRisikoTinggi4;
    private widget.ComboBox FaktorRisikoTinggi5;
    private widget.ComboBox FaktorRisikoTinggi6;
    private widget.ComboBox FaktorRisikoTinggi7;
    private widget.ComboBox FaktorRisikoTinggi8;
    private widget.ComboBox FaktorRisikoTinggi9;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HasilPemeriksaanSadanis;
    private widget.ComboBox HasilSkrining;
    private widget.ComboBox Jam;
    private widget.TextBox KdPetugas;
    private widget.ComboBox KecurigaanKeganasan1;
    private widget.ComboBox KecurigaanKeganasan2;
    private widget.ComboBox KecurigaanKeganasan3;
    private widget.ComboBox KecurigaanKeganasan4;
    private widget.ComboBox KecurigaanKeganasan5;
    private widget.ComboBox KecurigaanKeganasan6;
    private widget.ComboBox KecurigaanKeganasan7;
    private widget.ComboBox KecurigaanKeganasan8;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnSkriningRisikoKankerPayudara;
    private widget.TextBox NilaiFaktorAwal1;
    private widget.TextBox NilaiFaktorAwal10;
    private widget.TextBox NilaiFaktorAwal11;
    private widget.TextBox NilaiFaktorAwal12;
    private widget.TextBox NilaiFaktorAwal13;
    private widget.TextBox NilaiFaktorAwal14;
    private widget.TextBox NilaiFaktorAwal2;
    private widget.TextBox NilaiFaktorAwal3;
    private widget.TextBox NilaiFaktorAwal4;
    private widget.TextBox NilaiFaktorAwal5;
    private widget.TextBox NilaiFaktorAwal6;
    private widget.TextBox NilaiFaktorAwal7;
    private widget.TextBox NilaiFaktorAwal8;
    private widget.TextBox NilaiFaktorAwal9;
    private widget.TextBox NilaiFaktorRisikoTinggi1;
    private widget.TextBox NilaiFaktorRisikoTinggi10;
    private widget.TextBox NilaiFaktorRisikoTinggi11;
    private widget.TextBox NilaiFaktorRisikoTinggi12;
    private widget.TextBox NilaiFaktorRisikoTinggi13;
    private widget.TextBox NilaiFaktorRisikoTinggi2;
    private widget.TextBox NilaiFaktorRisikoTinggi3;
    private widget.TextBox NilaiFaktorRisikoTinggi4;
    private widget.TextBox NilaiFaktorRisikoTinggi5;
    private widget.TextBox NilaiFaktorRisikoTinggi6;
    private widget.TextBox NilaiFaktorRisikoTinggi7;
    private widget.TextBox NilaiFaktorRisikoTinggi8;
    private widget.TextBox NilaiFaktorRisikoTinggi9;
    private widget.TextBox NilaiKecurigaanKeganasan1;
    private widget.TextBox NilaiKecurigaanKeganasan2;
    private widget.TextBox NilaiKecurigaanKeganasan3;
    private widget.TextBox NilaiKecurigaanKeganasan4;
    private widget.TextBox NilaiKecurigaanKeganasan5;
    private widget.TextBox NilaiKecurigaanKeganasan6;
    private widget.TextBox NilaiKecurigaanKeganasan7;
    private widget.TextBox NilaiKecurigaanKeganasan8;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Rekomendasi;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.ComboBox TindakLanjutSadanis;
    private widget.TextBox TotalHasil;
    private widget.TextBox Umur;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private widget.Label jLabel18;
    private widget.Label jLabel180;
    private widget.Label jLabel181;
    private widget.Label jLabel182;
    private widget.Label jLabel183;
    private widget.Label jLabel184;
    private widget.Label jLabel185;
    private widget.Label jLabel186;
    private widget.Label jLabel187;
    private widget.Label jLabel188;
    private widget.Label jLabel189;
    private widget.Label jLabel19;
    private widget.Label jLabel190;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
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
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_payudara.nip,"+
                    "petugas.nama,skrining_risiko_kanker_payudara.tanggal,skrining_risiko_kanker_payudara.faktor_risiko_awal1,skrining_risiko_kanker_payudara.nilai_risiko_awal1,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal2,skrining_risiko_kanker_payudara.nilai_risiko_awal2,skrining_risiko_kanker_payudara.faktor_risiko_awal3,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal3,skrining_risiko_kanker_payudara.faktor_risiko_awal4,skrining_risiko_kanker_payudara.nilai_risiko_awal4,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal5,skrining_risiko_kanker_payudara.nilai_risiko_awal5,skrining_risiko_kanker_payudara.faktor_risiko_awal6,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal6,skrining_risiko_kanker_payudara.faktor_risiko_awal7,skrining_risiko_kanker_payudara.nilai_risiko_awal7,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal8,skrining_risiko_kanker_payudara.nilai_risiko_awal8,skrining_risiko_kanker_payudara.faktor_risiko_awal9,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal9,skrining_risiko_kanker_payudara.faktor_risiko_awal10,skrining_risiko_kanker_payudara.nilai_risiko_awal10,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal11,skrining_risiko_kanker_payudara.nilai_risiko_awal11,skrining_risiko_kanker_payudara.faktor_risiko_awal12,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal12,skrining_risiko_kanker_payudara.faktor_risiko_awal13,skrining_risiko_kanker_payudara.nilai_risiko_awal13,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal14,skrining_risiko_kanker_payudara.nilai_risiko_awal14,skrining_risiko_kanker_payudara.faktor_risiko_tinggi1,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi1,skrining_risiko_kanker_payudara.faktor_risiko_tinggi2,skrining_risiko_kanker_payudara.nilai_risiko_tinggi2,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi3,skrining_risiko_kanker_payudara.nilai_risiko_tinggi3,skrining_risiko_kanker_payudara.faktor_risiko_tinggi4,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi4,skrining_risiko_kanker_payudara.faktor_risiko_tinggi5,skrining_risiko_kanker_payudara.nilai_risiko_tinggi5,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi6,skrining_risiko_kanker_payudara.nilai_risiko_tinggi6,skrining_risiko_kanker_payudara.faktor_risiko_tinggi7,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi7,skrining_risiko_kanker_payudara.faktor_risiko_tinggi8,skrining_risiko_kanker_payudara.nilai_risiko_tinggi8,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi9,skrining_risiko_kanker_payudara.nilai_risiko_tinggi9,skrining_risiko_kanker_payudara.faktor_risiko_tinggi10,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi10,skrining_risiko_kanker_payudara.faktor_risiko_tinggi11,skrining_risiko_kanker_payudara.nilai_risiko_tinggi11,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi12,skrining_risiko_kanker_payudara.nilai_risiko_tinggi12,skrining_risiko_kanker_payudara.faktor_risiko_tinggi13,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi13,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas1,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas1,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas2,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas2,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas3,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas3,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas4,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas4,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas5,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas5,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas6,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas6,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas7,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas7,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas8,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas8,skrining_risiko_kanker_payudara.total_skor,"+
                    "skrining_risiko_kanker_payudara.hasil_sadanis,skrining_risiko_kanker_payudara.tindak_lanjut_sadanis,skrining_risiko_kanker_payudara.hasil_skrining,"+
                    "skrining_risiko_kanker_payudara.keterangan from skrining_risiko_kanker_payudara inner join reg_periksa on skrining_risiko_kanker_payudara.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_payudara.nip=petugas.nip "+
                    "where skrining_risiko_kanker_payudara.tanggal between ? and ? order by skrining_risiko_kanker_payudara.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur,skrining_risiko_kanker_payudara.nip,"+
                    "petugas.nama,skrining_risiko_kanker_payudara.tanggal,skrining_risiko_kanker_payudara.faktor_risiko_awal1,skrining_risiko_kanker_payudara.nilai_risiko_awal1,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal2,skrining_risiko_kanker_payudara.nilai_risiko_awal2,skrining_risiko_kanker_payudara.faktor_risiko_awal3,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal3,skrining_risiko_kanker_payudara.faktor_risiko_awal4,skrining_risiko_kanker_payudara.nilai_risiko_awal4,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal5,skrining_risiko_kanker_payudara.nilai_risiko_awal5,skrining_risiko_kanker_payudara.faktor_risiko_awal6,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal6,skrining_risiko_kanker_payudara.faktor_risiko_awal7,skrining_risiko_kanker_payudara.nilai_risiko_awal7,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal8,skrining_risiko_kanker_payudara.nilai_risiko_awal8,skrining_risiko_kanker_payudara.faktor_risiko_awal9,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal9,skrining_risiko_kanker_payudara.faktor_risiko_awal10,skrining_risiko_kanker_payudara.nilai_risiko_awal10,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal11,skrining_risiko_kanker_payudara.nilai_risiko_awal11,skrining_risiko_kanker_payudara.faktor_risiko_awal12,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_awal12,skrining_risiko_kanker_payudara.faktor_risiko_awal13,skrining_risiko_kanker_payudara.nilai_risiko_awal13,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_awal14,skrining_risiko_kanker_payudara.nilai_risiko_awal14,skrining_risiko_kanker_payudara.faktor_risiko_tinggi1,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi1,skrining_risiko_kanker_payudara.faktor_risiko_tinggi2,skrining_risiko_kanker_payudara.nilai_risiko_tinggi2,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi3,skrining_risiko_kanker_payudara.nilai_risiko_tinggi3,skrining_risiko_kanker_payudara.faktor_risiko_tinggi4,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi4,skrining_risiko_kanker_payudara.faktor_risiko_tinggi5,skrining_risiko_kanker_payudara.nilai_risiko_tinggi5,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi6,skrining_risiko_kanker_payudara.nilai_risiko_tinggi6,skrining_risiko_kanker_payudara.faktor_risiko_tinggi7,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi7,skrining_risiko_kanker_payudara.faktor_risiko_tinggi8,skrining_risiko_kanker_payudara.nilai_risiko_tinggi8,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi9,skrining_risiko_kanker_payudara.nilai_risiko_tinggi9,skrining_risiko_kanker_payudara.faktor_risiko_tinggi10,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi10,skrining_risiko_kanker_payudara.faktor_risiko_tinggi11,skrining_risiko_kanker_payudara.nilai_risiko_tinggi11,"+
                    "skrining_risiko_kanker_payudara.faktor_risiko_tinggi12,skrining_risiko_kanker_payudara.nilai_risiko_tinggi12,skrining_risiko_kanker_payudara.faktor_risiko_tinggi13,"+
                    "skrining_risiko_kanker_payudara.nilai_risiko_tinggi13,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas1,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas1,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas2,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas2,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas3,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas3,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas4,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas4,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas5,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas5,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas6,"+
                    "skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas6,skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas7,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas7,"+
                    "skrining_risiko_kanker_payudara.faktor_kecurigaan_ganas8,skrining_risiko_kanker_payudara.nilai_kecurigaan_ganas8,skrining_risiko_kanker_payudara.total_skor,"+
                    "skrining_risiko_kanker_payudara.hasil_sadanis,skrining_risiko_kanker_payudara.tindak_lanjut_sadanis,skrining_risiko_kanker_payudara.hasil_skrining,"+
                    "skrining_risiko_kanker_payudara.keterangan from skrining_risiko_kanker_payudara inner join reg_periksa on skrining_risiko_kanker_payudara.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on skrining_risiko_kanker_payudara.nip=petugas.nip "+
                    "where skrining_risiko_kanker_payudara.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or skrining_risiko_kanker_payudara.nip like ? or petugas.nama like ?) "+
                    "order by skrining_risiko_kanker_payudara.tanggal ");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("tanggal"),rs.getString("faktor_risiko_awal1"),rs.getString("nilai_risiko_awal1"),rs.getString("faktor_risiko_awal2"),
                        rs.getString("nilai_risiko_awal2"),rs.getString("faktor_risiko_awal3"),rs.getString("nilai_risiko_awal3"),rs.getString("faktor_risiko_awal4"),rs.getString("nilai_risiko_awal4"),
                        rs.getString("faktor_risiko_awal5"),rs.getString("nilai_risiko_awal5"),rs.getString("faktor_risiko_awal6"),rs.getString("nilai_risiko_awal6"),rs.getString("faktor_risiko_awal7"),
                        rs.getString("nilai_risiko_awal7"),rs.getString("faktor_risiko_awal8"),rs.getString("nilai_risiko_awal8"),rs.getString("faktor_risiko_awal9"),rs.getString("nilai_risiko_awal9"),
                        rs.getString("faktor_risiko_awal10"),rs.getString("nilai_risiko_awal10"),rs.getString("faktor_risiko_awal11"),rs.getString("nilai_risiko_awal11"),rs.getString("faktor_risiko_awal12"),
                        rs.getString("nilai_risiko_awal12"),rs.getString("faktor_risiko_awal13"),rs.getString("nilai_risiko_awal13"),rs.getString("faktor_risiko_awal14"),rs.getString("nilai_risiko_awal14"),
                        rs.getString("faktor_risiko_tinggi1"),rs.getString("nilai_risiko_tinggi1"),rs.getString("faktor_risiko_tinggi2"),rs.getString("nilai_risiko_tinggi2"),rs.getString("faktor_risiko_tinggi3"),
                        rs.getString("nilai_risiko_tinggi3"),rs.getString("faktor_risiko_tinggi4"),rs.getString("nilai_risiko_tinggi4"),rs.getString("faktor_risiko_tinggi5"),rs.getString("nilai_risiko_tinggi5"),
                        rs.getString("faktor_risiko_tinggi6"),rs.getString("nilai_risiko_tinggi6"),rs.getString("faktor_risiko_tinggi7"),rs.getString("nilai_risiko_tinggi7"),rs.getString("faktor_risiko_tinggi8"),
                        rs.getString("nilai_risiko_tinggi8"),rs.getString("faktor_risiko_tinggi9"),rs.getString("nilai_risiko_tinggi9"),rs.getString("faktor_risiko_tinggi10"),rs.getString("nilai_risiko_tinggi10"),
                        rs.getString("faktor_risiko_tinggi11"),rs.getString("nilai_risiko_tinggi11"),rs.getString("faktor_risiko_tinggi12"),rs.getString("nilai_risiko_tinggi12"),rs.getString("faktor_risiko_tinggi13"),
                        rs.getString("nilai_risiko_tinggi13"),rs.getString("faktor_kecurigaan_ganas1"),rs.getString("nilai_kecurigaan_ganas1"),rs.getString("faktor_kecurigaan_ganas2"),rs.getString("nilai_kecurigaan_ganas2"),
                        rs.getString("faktor_kecurigaan_ganas3"),rs.getString("nilai_kecurigaan_ganas3"),rs.getString("faktor_kecurigaan_ganas4"),rs.getString("nilai_kecurigaan_ganas4"),rs.getString("faktor_kecurigaan_ganas5"),
                        rs.getString("nilai_kecurigaan_ganas5"),rs.getString("faktor_kecurigaan_ganas6"),rs.getString("nilai_kecurigaan_ganas6"),rs.getString("faktor_kecurigaan_ganas7"),rs.getString("nilai_kecurigaan_ganas7"),
                        rs.getString("faktor_kecurigaan_ganas8"),rs.getString("nilai_kecurigaan_ganas8"),rs.getString("total_skor"),rs.getString("hasil_sadanis"),rs.getString("tindak_lanjut_sadanis"),
                        rs.getString("hasil_skrining"),rs.getString("keterangan")
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
        Tanggal.setDate(new Date());
        FaktorAwal1.setSelectedIndex(0);
        NilaiFaktorAwal1.setText("0");
        FaktorAwal2.setSelectedIndex(0);
        NilaiFaktorAwal2.setText("0");
        FaktorAwal3.setSelectedIndex(0);
        NilaiFaktorAwal3.setText("0");
        FaktorAwal4.setSelectedIndex(0);
        NilaiFaktorAwal4.setText("0");
        FaktorAwal5.setSelectedIndex(0);
        NilaiFaktorAwal5.setText("0");
        FaktorAwal6.setSelectedIndex(0);
        NilaiFaktorAwal6.setText("0");
        FaktorAwal7.setSelectedIndex(0);
        NilaiFaktorAwal7.setText("0");
        FaktorAwal8.setSelectedIndex(0);
        NilaiFaktorAwal8.setText("0");
        FaktorAwal9.setSelectedIndex(0);
        NilaiFaktorAwal9.setText("0");
        FaktorAwal10.setSelectedIndex(0);
        NilaiFaktorAwal10.setText("0");
        FaktorAwal11.setSelectedIndex(0);
        NilaiFaktorAwal11.setText("0");
        FaktorAwal12.setSelectedIndex(0);
        NilaiFaktorAwal12.setText("0");
        FaktorAwal13.setSelectedIndex(0);
        NilaiFaktorAwal13.setText("0");
        FaktorAwal14.setSelectedIndex(0);
        NilaiFaktorAwal14.setText("0");
        FaktorRisikoTinggi1.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi1.setText("0");
        FaktorRisikoTinggi2.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi2.setText("0");
        FaktorRisikoTinggi3.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi3.setText("0");
        FaktorRisikoTinggi4.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi4.setText("0");
        FaktorRisikoTinggi5.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi5.setText("0");
        FaktorRisikoTinggi6.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi6.setText("0");
        FaktorRisikoTinggi7.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi7.setText("0");
        FaktorRisikoTinggi8.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi8.setText("0");
        FaktorRisikoTinggi9.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi9.setText("0");
        FaktorRisikoTinggi10.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi10.setText("0");
        FaktorRisikoTinggi11.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi11.setText("0");
        FaktorRisikoTinggi12.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi12.setText("0");
        FaktorRisikoTinggi13.setSelectedIndex(0);
        NilaiFaktorRisikoTinggi13.setText("0");
        KecurigaanKeganasan1.setSelectedIndex(0);
        NilaiKecurigaanKeganasan1.setText("0");
        KecurigaanKeganasan2.setSelectedIndex(0);
        NilaiKecurigaanKeganasan2.setText("0");
        KecurigaanKeganasan3.setSelectedIndex(0);
        NilaiKecurigaanKeganasan3.setText("0");
        KecurigaanKeganasan4.setSelectedIndex(0);
        NilaiKecurigaanKeganasan4.setText("0");
        KecurigaanKeganasan5.setSelectedIndex(0);
        NilaiKecurigaanKeganasan5.setText("0");
        KecurigaanKeganasan6.setSelectedIndex(0);
        NilaiKecurigaanKeganasan6.setText("0");
        KecurigaanKeganasan7.setSelectedIndex(0);
        NilaiKecurigaanKeganasan7.setText("0");
        KecurigaanKeganasan8.setSelectedIndex(0);
        NilaiKecurigaanKeganasan8.setText("0");
        Rekomendasi.setText("Waspada dan upayakan melakukan penanggulangan sehingga skor semakin kecil..!");
        TotalHasil.setText("0");
        HasilPemeriksaanSadanis.setSelectedIndex(0);
        TindakLanjutSadanis.setSelectedIndex(0);
        HasilSkrining.setSelectedIndex(0);
        Keterangan.setText("");
        FaktorAwal1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(14,15));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(17,19));
            FaktorAwal1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NilaiFaktorAwal1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            FaktorAwal2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NilaiFaktorAwal2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            FaktorAwal3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NilaiFaktorAwal3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            FaktorAwal4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NilaiFaktorAwal4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            FaktorAwal5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NilaiFaktorAwal5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            FaktorAwal6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NilaiFaktorAwal6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            FaktorAwal7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NilaiFaktorAwal7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            FaktorAwal8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NilaiFaktorAwal8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            FaktorAwal9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NilaiFaktorAwal9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            FaktorAwal10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NilaiFaktorAwal10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            FaktorAwal11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            NilaiFaktorAwal11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            FaktorAwal12.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            NilaiFaktorAwal12.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            FaktorAwal13.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            NilaiFaktorAwal13.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            FaktorAwal14.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            NilaiFaktorAwal14.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            FaktorRisikoTinggi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            NilaiFaktorRisikoTinggi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            FaktorRisikoTinggi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            NilaiFaktorRisikoTinggi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            FaktorRisikoTinggi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            NilaiFaktorRisikoTinggi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            FaktorRisikoTinggi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            NilaiFaktorRisikoTinggi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            FaktorRisikoTinggi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            NilaiFaktorRisikoTinggi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            FaktorRisikoTinggi6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            NilaiFaktorRisikoTinggi6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            FaktorRisikoTinggi7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            NilaiFaktorRisikoTinggi7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            FaktorRisikoTinggi8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            NilaiFaktorRisikoTinggi8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            FaktorRisikoTinggi9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            NilaiFaktorRisikoTinggi9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            FaktorRisikoTinggi10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            NilaiFaktorRisikoTinggi10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            FaktorRisikoTinggi11.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            NilaiFaktorRisikoTinggi11.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            FaktorRisikoTinggi12.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            NilaiFaktorRisikoTinggi12.setText(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            FaktorRisikoTinggi13.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            NilaiFaktorRisikoTinggi13.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            KecurigaanKeganasan1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            NilaiKecurigaanKeganasan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            KecurigaanKeganasan2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            NilaiKecurigaanKeganasan2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            KecurigaanKeganasan3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            NilaiKecurigaanKeganasan3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            KecurigaanKeganasan4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            NilaiKecurigaanKeganasan4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            KecurigaanKeganasan5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            NilaiKecurigaanKeganasan5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            KecurigaanKeganasan6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            NilaiKecurigaanKeganasan6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            KecurigaanKeganasan7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            NilaiKecurigaanKeganasan7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            KecurigaanKeganasan8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            NilaiKecurigaanKeganasan8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            TotalHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            HasilPemeriksaanSadanis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            TindakLanjutSadanis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            HasilSkrining.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.umurdaftar,reg_periksa.sttsumur "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-172));
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
        BtnSimpan.setEnabled(akses.getskrining_risiko_kanker_payudara());
        BtnHapus.setEnabled(akses.getskrining_risiko_kanker_payudara());
        BtnEdit.setEnabled(akses.getskrining_risiko_kanker_payudara());
        BtnPrint.setEnabled(akses.getskrining_risiko_kanker_payudara()); 
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
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
        if(Sequel.mengedittf("skrining_risiko_kanker_payudara","no_rawat=?","no_rawat=?,tanggal=?,faktor_risiko_awal1=?,nilai_risiko_awal1=?,faktor_risiko_awal2=?,nilai_risiko_awal2=?,faktor_risiko_awal3=?,"+
                "nilai_risiko_awal3=?,faktor_risiko_awal4=?,nilai_risiko_awal4=?,faktor_risiko_awal5=?,nilai_risiko_awal5=?,faktor_risiko_awal6=?,nilai_risiko_awal6=?,faktor_risiko_awal7=?,nilai_risiko_awal7=?,"+
                "faktor_risiko_awal8=?,nilai_risiko_awal8=?,faktor_risiko_awal9=?,nilai_risiko_awal9=?,faktor_risiko_awal10=?,nilai_risiko_awal10=?,faktor_risiko_awal11=?,nilai_risiko_awal11=?,faktor_risiko_awal12=?,"+
                "nilai_risiko_awal12=?,faktor_risiko_awal13=?,nilai_risiko_awal13=?,faktor_risiko_awal14=?,nilai_risiko_awal14=?,faktor_risiko_tinggi1=?,nilai_risiko_tinggi1=?,faktor_risiko_tinggi2=?,nilai_risiko_tinggi2=?,"+
                "faktor_risiko_tinggi3=?,nilai_risiko_tinggi3=?,faktor_risiko_tinggi4=?,nilai_risiko_tinggi4=?,faktor_risiko_tinggi5=?,nilai_risiko_tinggi5=?,faktor_risiko_tinggi6=?,nilai_risiko_tinggi6=?,"+
                "faktor_risiko_tinggi7=?,nilai_risiko_tinggi7=?,faktor_risiko_tinggi8=?,nilai_risiko_tinggi8=?,faktor_risiko_tinggi9=?,nilai_risiko_tinggi9=?,faktor_risiko_tinggi10=?,nilai_risiko_tinggi10=?,"+
                "faktor_risiko_tinggi11=?,nilai_risiko_tinggi11=?,faktor_risiko_tinggi12=?,nilai_risiko_tinggi12=?,faktor_risiko_tinggi13=?,nilai_risiko_tinggi13=?,faktor_kecurigaan_ganas1=?,nilai_kecurigaan_ganas1=?,"+
                "faktor_kecurigaan_ganas2=?,nilai_kecurigaan_ganas2=?,faktor_kecurigaan_ganas3=?,nilai_kecurigaan_ganas3=?,faktor_kecurigaan_ganas4=?,nilai_kecurigaan_ganas4=?,faktor_kecurigaan_ganas5=?,nilai_kecurigaan_ganas5=?,"+
                "faktor_kecurigaan_ganas6=?,nilai_kecurigaan_ganas6=?,faktor_kecurigaan_ganas7=?,nilai_kecurigaan_ganas7=?,faktor_kecurigaan_ganas8=?,nilai_kecurigaan_ganas8=?,total_skor=?,hasil_sadanis=?,tindak_lanjut_sadanis=?,"+
                "hasil_skrining=?,keterangan=?,nip=?",79,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                FaktorAwal1.getSelectedItem().toString(),NilaiFaktorAwal1.getText(),FaktorAwal2.getSelectedItem().toString(),NilaiFaktorAwal2.getText(),FaktorAwal3.getSelectedItem().toString(),NilaiFaktorAwal3.getText(),
                FaktorAwal4.getSelectedItem().toString(),NilaiFaktorAwal4.getText(),FaktorAwal5.getSelectedItem().toString(),NilaiFaktorAwal5.getText(),FaktorAwal6.getSelectedItem().toString(),NilaiFaktorAwal6.getText(),
                FaktorAwal7.getSelectedItem().toString(),NilaiFaktorAwal7.getText(),FaktorAwal8.getSelectedItem().toString(),NilaiFaktorAwal8.getText(),FaktorAwal9.getSelectedItem().toString(),NilaiFaktorAwal9.getText(), 
                FaktorAwal10.getSelectedItem().toString(),NilaiFaktorAwal10.getText(),FaktorAwal11.getSelectedItem().toString(),NilaiFaktorAwal11.getText(),FaktorAwal12.getSelectedItem().toString(),NilaiFaktorAwal12.getText(), 
                FaktorAwal13.getSelectedItem().toString(),NilaiFaktorAwal13.getText(),FaktorAwal14.getSelectedItem().toString(),NilaiFaktorAwal14.getText(),FaktorRisikoTinggi1.getSelectedItem().toString(),NilaiFaktorRisikoTinggi1.getText(), 
                FaktorRisikoTinggi2.getSelectedItem().toString(),NilaiFaktorRisikoTinggi2.getText(),FaktorRisikoTinggi3.getSelectedItem().toString(),NilaiFaktorRisikoTinggi3.getText(),FaktorRisikoTinggi4.getSelectedItem().toString(),NilaiFaktorRisikoTinggi4.getText(), 
                FaktorRisikoTinggi5.getSelectedItem().toString(),NilaiFaktorRisikoTinggi5.getText(),FaktorRisikoTinggi6.getSelectedItem().toString(),NilaiFaktorRisikoTinggi6.getText(),FaktorRisikoTinggi7.getSelectedItem().toString(),NilaiFaktorRisikoTinggi7.getText(), 
                FaktorRisikoTinggi8.getSelectedItem().toString(),NilaiFaktorRisikoTinggi8.getText(),FaktorRisikoTinggi9.getSelectedItem().toString(),NilaiFaktorRisikoTinggi9.getText(),FaktorRisikoTinggi10.getSelectedItem().toString(),NilaiFaktorRisikoTinggi10.getText(), 
                FaktorRisikoTinggi11.getSelectedItem().toString(),NilaiFaktorRisikoTinggi11.getText(),FaktorRisikoTinggi12.getSelectedItem().toString(),NilaiFaktorRisikoTinggi12.getText(),FaktorRisikoTinggi13.getSelectedItem().toString(),NilaiFaktorRisikoTinggi13.getText(), 
                KecurigaanKeganasan1.getSelectedItem().toString(),NilaiKecurigaanKeganasan1.getText(),KecurigaanKeganasan2.getSelectedItem().toString(),NilaiKecurigaanKeganasan2.getText(),KecurigaanKeganasan3.getSelectedItem().toString(),NilaiKecurigaanKeganasan3.getText(), 
                KecurigaanKeganasan4.getSelectedItem().toString(),NilaiKecurigaanKeganasan4.getText(),KecurigaanKeganasan5.getSelectedItem().toString(),NilaiKecurigaanKeganasan5.getText(),KecurigaanKeganasan6.getSelectedItem().toString(),NilaiKecurigaanKeganasan6.getText(),
                KecurigaanKeganasan7.getSelectedItem().toString(),NilaiKecurigaanKeganasan7.getText(),KecurigaanKeganasan8.getSelectedItem().toString(),NilaiKecurigaanKeganasan8.getText(),TotalHasil.getText(),HasilPemeriksaanSadanis.getSelectedItem().toString(), 
                TindakLanjutSadanis.getSelectedItem().toString(),HasilSkrining.getSelectedItem().toString(),Keterangan.getText(),KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdPetugas.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(FaktorAwal1.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(NilaiFaktorAwal1.getText(),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(FaktorAwal2.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(NilaiFaktorAwal2.getText(),tbObat.getSelectedRow(),11);
               tbObat.setValueAt(FaktorAwal3.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
               tbObat.setValueAt(NilaiFaktorAwal3.getText(),tbObat.getSelectedRow(),13);
               tbObat.setValueAt(FaktorAwal4.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
               tbObat.setValueAt(NilaiFaktorAwal4.getText(),tbObat.getSelectedRow(),15);
               tbObat.setValueAt(FaktorAwal5.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
               tbObat.setValueAt(NilaiFaktorAwal5.getText(),tbObat.getSelectedRow(),17);
               tbObat.setValueAt(FaktorAwal6.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
               tbObat.setValueAt(NilaiFaktorAwal6.getText(),tbObat.getSelectedRow(),19);
               tbObat.setValueAt(FaktorAwal7.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
               tbObat.setValueAt(NilaiFaktorAwal7.getText(),tbObat.getSelectedRow(),21);
               tbObat.setValueAt(FaktorAwal8.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
               tbObat.setValueAt(NilaiFaktorAwal8.getText(),tbObat.getSelectedRow(),23);
               tbObat.setValueAt(FaktorAwal9.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
               tbObat.setValueAt(NilaiFaktorAwal9.getText(),tbObat.getSelectedRow(),25);
               tbObat.setValueAt(FaktorAwal10.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
               tbObat.setValueAt(NilaiFaktorAwal10.getText(),tbObat.getSelectedRow(),27);
               tbObat.setValueAt(FaktorAwal11.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
               tbObat.setValueAt(NilaiFaktorAwal11.getText(),tbObat.getSelectedRow(),29);
               tbObat.setValueAt(FaktorAwal12.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
               tbObat.setValueAt(NilaiFaktorAwal12.getText(),tbObat.getSelectedRow(),31);
               tbObat.setValueAt(FaktorAwal13.getSelectedItem().toString(),tbObat.getSelectedRow(),32);
               tbObat.setValueAt(NilaiFaktorAwal13.getText(),tbObat.getSelectedRow(),33);
               tbObat.setValueAt(FaktorAwal14.getSelectedItem().toString(),tbObat.getSelectedRow(),34);
               tbObat.setValueAt(NilaiFaktorAwal14.getText(),tbObat.getSelectedRow(),35);
               tbObat.setValueAt(FaktorRisikoTinggi1.getSelectedItem().toString(),tbObat.getSelectedRow(),36);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi1.getText(),tbObat.getSelectedRow(),37);
               tbObat.setValueAt(FaktorRisikoTinggi2.getSelectedItem().toString(),tbObat.getSelectedRow(),38);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi2.getText(),tbObat.getSelectedRow(),39);
               tbObat.setValueAt(FaktorRisikoTinggi3.getSelectedItem().toString(),tbObat.getSelectedRow(),40);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi3.getText(),tbObat.getSelectedRow(),41);
               tbObat.setValueAt(FaktorRisikoTinggi4.getSelectedItem().toString(),tbObat.getSelectedRow(),42);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi4.getText(),tbObat.getSelectedRow(),43);
               tbObat.setValueAt(FaktorRisikoTinggi5.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi5.getText(),tbObat.getSelectedRow(),45);
               tbObat.setValueAt(FaktorRisikoTinggi6.getSelectedItem().toString(),tbObat.getSelectedRow(),46);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi6.getText(),tbObat.getSelectedRow(),47);
               tbObat.setValueAt(FaktorRisikoTinggi7.getSelectedItem().toString(),tbObat.getSelectedRow(),48);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi7.getText(),tbObat.getSelectedRow(),49);
               tbObat.setValueAt(FaktorRisikoTinggi8.getSelectedItem().toString(),tbObat.getSelectedRow(),50);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi8.getText(),tbObat.getSelectedRow(),51);
               tbObat.setValueAt(FaktorRisikoTinggi9.getSelectedItem().toString(),tbObat.getSelectedRow(),52);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi9.getText(),tbObat.getSelectedRow(),53);
               tbObat.setValueAt(FaktorRisikoTinggi10.getSelectedItem().toString(),tbObat.getSelectedRow(),54);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi10.getText(),tbObat.getSelectedRow(),55);
               tbObat.setValueAt(FaktorRisikoTinggi11.getSelectedItem().toString(),tbObat.getSelectedRow(),56);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi11.getText(),tbObat.getSelectedRow(),57);
               tbObat.setValueAt(FaktorRisikoTinggi12.getSelectedItem().toString(),tbObat.getSelectedRow(),58);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi12.getText(),tbObat.getSelectedRow(),59);
               tbObat.setValueAt(FaktorRisikoTinggi13.getSelectedItem().toString(),tbObat.getSelectedRow(),60);
               tbObat.setValueAt(NilaiFaktorRisikoTinggi13.getText(),tbObat.getSelectedRow(),61);
               tbObat.setValueAt(KecurigaanKeganasan1.getSelectedItem().toString(),tbObat.getSelectedRow(),62);
               tbObat.setValueAt(NilaiKecurigaanKeganasan1.getText(),tbObat.getSelectedRow(),63);
               tbObat.setValueAt(KecurigaanKeganasan2.getSelectedItem().toString(),tbObat.getSelectedRow(),64);
               tbObat.setValueAt(NilaiKecurigaanKeganasan2.getText(),tbObat.getSelectedRow(),65);
               tbObat.setValueAt(KecurigaanKeganasan3.getSelectedItem().toString(),tbObat.getSelectedRow(),66);
               tbObat.setValueAt(NilaiKecurigaanKeganasan3.getText(),tbObat.getSelectedRow(),67);
               tbObat.setValueAt(KecurigaanKeganasan4.getSelectedItem().toString(),tbObat.getSelectedRow(),68);
               tbObat.setValueAt(NilaiKecurigaanKeganasan4.getText(),tbObat.getSelectedRow(),69);
               tbObat.setValueAt(KecurigaanKeganasan5.getSelectedItem().toString(),tbObat.getSelectedRow(),70);
               tbObat.setValueAt(NilaiKecurigaanKeganasan5.getText(),tbObat.getSelectedRow(),71);
               tbObat.setValueAt(KecurigaanKeganasan6.getSelectedItem().toString(),tbObat.getSelectedRow(),72);
               tbObat.setValueAt(NilaiKecurigaanKeganasan6.getText(),tbObat.getSelectedRow(),73);
               tbObat.setValueAt(KecurigaanKeganasan7.getSelectedItem().toString(),tbObat.getSelectedRow(),74);
               tbObat.setValueAt(NilaiKecurigaanKeganasan7.getText(),tbObat.getSelectedRow(),75);
               tbObat.setValueAt(KecurigaanKeganasan8.getSelectedItem().toString(),tbObat.getSelectedRow(),76);
               tbObat.setValueAt(NilaiKecurigaanKeganasan8.getText(),tbObat.getSelectedRow(),77);
               tbObat.setValueAt(TotalHasil.getText(),tbObat.getSelectedRow(),78);
               tbObat.setValueAt(HasilPemeriksaanSadanis.getSelectedItem().toString(),tbObat.getSelectedRow(),79);
               tbObat.setValueAt(TindakLanjutSadanis.getSelectedItem().toString(),tbObat.getSelectedRow(),80);
               tbObat.setValueAt(HasilSkrining.getSelectedItem().toString(),tbObat.getSelectedRow(),81);
               tbObat.setValueAt(Keterangan.getText(),tbObat.getSelectedRow(),82);
               emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_risiko_kanker_payudara where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void isTotal() {
        try {
            TotalHasil.setText(""+(
                    Integer.parseInt(NilaiFaktorAwal1.getText())+Integer.parseInt(NilaiFaktorAwal2.getText())+
                    Integer.parseInt(NilaiFaktorAwal3.getText())+Integer.parseInt(NilaiFaktorAwal4.getText())+
                    Integer.parseInt(NilaiFaktorAwal5.getText())+Integer.parseInt(NilaiFaktorAwal6.getText())+
                    Integer.parseInt(NilaiFaktorAwal7.getText())+Integer.parseInt(NilaiFaktorAwal8.getText())+
                    Integer.parseInt(NilaiFaktorAwal9.getText())+Integer.parseInt(NilaiFaktorAwal10.getText())+
                    Integer.parseInt(NilaiFaktorAwal11.getText())+Integer.parseInt(NilaiFaktorAwal12.getText())+
                    Integer.parseInt(NilaiFaktorAwal13.getText())+Integer.parseInt(NilaiFaktorAwal14.getText())+
                    Integer.parseInt(NilaiFaktorRisikoTinggi1.getText())+Integer.parseInt(NilaiFaktorRisikoTinggi2.getText())+
                    Integer.parseInt(NilaiFaktorRisikoTinggi3.getText())+Integer.parseInt(NilaiFaktorRisikoTinggi4.getText())+
                    Integer.parseInt(NilaiFaktorRisikoTinggi5.getText())+Integer.parseInt(NilaiFaktorRisikoTinggi6.getText())+
                    Integer.parseInt(NilaiFaktorRisikoTinggi7.getText())+Integer.parseInt(NilaiFaktorRisikoTinggi8.getText())+
                    Integer.parseInt(NilaiFaktorRisikoTinggi9.getText())+Integer.parseInt(NilaiFaktorRisikoTinggi10.getText())+
                    Integer.parseInt(NilaiFaktorRisikoTinggi11.getText())+Integer.parseInt(NilaiFaktorRisikoTinggi12.getText())+
                    Integer.parseInt(NilaiFaktorRisikoTinggi13.getText())+Integer.parseInt(NilaiKecurigaanKeganasan1.getText())+
                    Integer.parseInt(NilaiKecurigaanKeganasan2.getText())+Integer.parseInt(NilaiKecurigaanKeganasan3.getText())+
                    Integer.parseInt(NilaiKecurigaanKeganasan4.getText())+Integer.parseInt(NilaiKecurigaanKeganasan5.getText())+
                    Integer.parseInt(NilaiKecurigaanKeganasan6.getText())+Integer.parseInt(NilaiKecurigaanKeganasan7.getText())+
                    Integer.parseInt(NilaiKecurigaanKeganasan8.getText())
            ));
            if(Integer.parseInt(TotalHasil.getText())>=16){
                Rekomendasi.setText("Resiko Tinggi. Segera periksa payudara, ikuti nasehat tenaga kesehatan/dokter...!");
            }else{
                Rekomendasi.setText("Waspada dan upayakan melakukan penanggulangan sehingga skor semakin kecil...!");
            }
            if((Integer.parseInt(NilaiKecurigaanKeganasan1.getText())+
                    Integer.parseInt(NilaiKecurigaanKeganasan2.getText())+Integer.parseInt(NilaiKecurigaanKeganasan3.getText())+
                    Integer.parseInt(NilaiKecurigaanKeganasan4.getText())+Integer.parseInt(NilaiKecurigaanKeganasan5.getText())+
                    Integer.parseInt(NilaiKecurigaanKeganasan6.getText())+Integer.parseInt(NilaiKecurigaanKeganasan7.getText())+
                    Integer.parseInt(NilaiKecurigaanKeganasan8.getText()))>0){
                Rekomendasi.setText(Rekomendasi.getText()+". Sangat curiga ada keganasan pada payudara. Segera ke dokter...!");    
            }
        } catch (Exception e) {
            Rekomendasi.setText("Waspada dan upayakan melakukan penanggulangan sehingga skor semakin kecil...!");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("skrining_risiko_kanker_payudara","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",78,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            FaktorAwal1.getSelectedItem().toString(),NilaiFaktorAwal1.getText(),FaktorAwal2.getSelectedItem().toString(),NilaiFaktorAwal2.getText(),FaktorAwal3.getSelectedItem().toString(),NilaiFaktorAwal3.getText(),
            FaktorAwal4.getSelectedItem().toString(),NilaiFaktorAwal4.getText(),FaktorAwal5.getSelectedItem().toString(),NilaiFaktorAwal5.getText(),FaktorAwal6.getSelectedItem().toString(),NilaiFaktorAwal6.getText(),
            FaktorAwal7.getSelectedItem().toString(),NilaiFaktorAwal7.getText(),FaktorAwal8.getSelectedItem().toString(),NilaiFaktorAwal8.getText(),FaktorAwal9.getSelectedItem().toString(),NilaiFaktorAwal9.getText(), 
            FaktorAwal10.getSelectedItem().toString(),NilaiFaktorAwal10.getText(),FaktorAwal11.getSelectedItem().toString(),NilaiFaktorAwal11.getText(),FaktorAwal12.getSelectedItem().toString(),NilaiFaktorAwal12.getText(), 
            FaktorAwal13.getSelectedItem().toString(),NilaiFaktorAwal13.getText(),FaktorAwal14.getSelectedItem().toString(),NilaiFaktorAwal14.getText(),FaktorRisikoTinggi1.getSelectedItem().toString(),NilaiFaktorRisikoTinggi1.getText(), 
            FaktorRisikoTinggi2.getSelectedItem().toString(),NilaiFaktorRisikoTinggi2.getText(),FaktorRisikoTinggi3.getSelectedItem().toString(),NilaiFaktorRisikoTinggi3.getText(),FaktorRisikoTinggi4.getSelectedItem().toString(),NilaiFaktorRisikoTinggi4.getText(), 
            FaktorRisikoTinggi5.getSelectedItem().toString(),NilaiFaktorRisikoTinggi5.getText(),FaktorRisikoTinggi6.getSelectedItem().toString(),NilaiFaktorRisikoTinggi6.getText(),FaktorRisikoTinggi7.getSelectedItem().toString(),NilaiFaktorRisikoTinggi7.getText(), 
            FaktorRisikoTinggi8.getSelectedItem().toString(),NilaiFaktorRisikoTinggi8.getText(),FaktorRisikoTinggi9.getSelectedItem().toString(),NilaiFaktorRisikoTinggi9.getText(),FaktorRisikoTinggi10.getSelectedItem().toString(),NilaiFaktorRisikoTinggi10.getText(), 
            FaktorRisikoTinggi11.getSelectedItem().toString(),NilaiFaktorRisikoTinggi11.getText(),FaktorRisikoTinggi12.getSelectedItem().toString(),NilaiFaktorRisikoTinggi12.getText(),FaktorRisikoTinggi13.getSelectedItem().toString(),NilaiFaktorRisikoTinggi13.getText(), 
            KecurigaanKeganasan1.getSelectedItem().toString(),NilaiKecurigaanKeganasan1.getText(),KecurigaanKeganasan2.getSelectedItem().toString(),NilaiKecurigaanKeganasan2.getText(),KecurigaanKeganasan3.getSelectedItem().toString(),NilaiKecurigaanKeganasan3.getText(), 
            KecurigaanKeganasan4.getSelectedItem().toString(),NilaiKecurigaanKeganasan4.getText(),KecurigaanKeganasan5.getSelectedItem().toString(),NilaiKecurigaanKeganasan5.getText(),KecurigaanKeganasan6.getSelectedItem().toString(),NilaiKecurigaanKeganasan6.getText(),
            KecurigaanKeganasan7.getSelectedItem().toString(),NilaiKecurigaanKeganasan7.getText(),KecurigaanKeganasan8.getSelectedItem().toString(),NilaiKecurigaanKeganasan8.getText(),TotalHasil.getText(),HasilPemeriksaanSadanis.getSelectedItem().toString(), 
            TindakLanjutSadanis.getSelectedItem().toString(),HasilSkrining.getSelectedItem().toString(),Keterangan.getText(),KdPetugas.getText()
        })==true){
            tabMode.addRow(new String[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Umur.getText(),KdPetugas.getText(),NmPetugas.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                FaktorAwal1.getSelectedItem().toString(),NilaiFaktorAwal1.getText(),FaktorAwal2.getSelectedItem().toString(),NilaiFaktorAwal2.getText(),FaktorAwal3.getSelectedItem().toString(),NilaiFaktorAwal3.getText(),
                FaktorAwal4.getSelectedItem().toString(),NilaiFaktorAwal4.getText(),FaktorAwal5.getSelectedItem().toString(),NilaiFaktorAwal5.getText(),FaktorAwal6.getSelectedItem().toString(),NilaiFaktorAwal6.getText(),
                FaktorAwal7.getSelectedItem().toString(),NilaiFaktorAwal7.getText(),FaktorAwal8.getSelectedItem().toString(),NilaiFaktorAwal8.getText(),FaktorAwal9.getSelectedItem().toString(),NilaiFaktorAwal9.getText(), 
                FaktorAwal10.getSelectedItem().toString(),NilaiFaktorAwal10.getText(),FaktorAwal11.getSelectedItem().toString(),NilaiFaktorAwal11.getText(),FaktorAwal12.getSelectedItem().toString(),NilaiFaktorAwal12.getText(), 
                FaktorAwal13.getSelectedItem().toString(),NilaiFaktorAwal13.getText(),FaktorAwal14.getSelectedItem().toString(),NilaiFaktorAwal14.getText(),FaktorRisikoTinggi1.getSelectedItem().toString(),NilaiFaktorRisikoTinggi1.getText(), 
                FaktorRisikoTinggi2.getSelectedItem().toString(),NilaiFaktorRisikoTinggi2.getText(),FaktorRisikoTinggi3.getSelectedItem().toString(),NilaiFaktorRisikoTinggi3.getText(),FaktorRisikoTinggi4.getSelectedItem().toString(),NilaiFaktorRisikoTinggi4.getText(), 
                FaktorRisikoTinggi5.getSelectedItem().toString(),NilaiFaktorRisikoTinggi5.getText(),FaktorRisikoTinggi6.getSelectedItem().toString(),NilaiFaktorRisikoTinggi6.getText(),FaktorRisikoTinggi7.getSelectedItem().toString(),NilaiFaktorRisikoTinggi7.getText(), 
                FaktorRisikoTinggi8.getSelectedItem().toString(),NilaiFaktorRisikoTinggi8.getText(),FaktorRisikoTinggi9.getSelectedItem().toString(),NilaiFaktorRisikoTinggi9.getText(),FaktorRisikoTinggi10.getSelectedItem().toString(),NilaiFaktorRisikoTinggi10.getText(), 
                FaktorRisikoTinggi11.getSelectedItem().toString(),NilaiFaktorRisikoTinggi11.getText(),FaktorRisikoTinggi12.getSelectedItem().toString(),NilaiFaktorRisikoTinggi12.getText(),FaktorRisikoTinggi13.getSelectedItem().toString(),NilaiFaktorRisikoTinggi13.getText(), 
                KecurigaanKeganasan1.getSelectedItem().toString(),NilaiKecurigaanKeganasan1.getText(),KecurigaanKeganasan2.getSelectedItem().toString(),NilaiKecurigaanKeganasan2.getText(),KecurigaanKeganasan3.getSelectedItem().toString(),NilaiKecurigaanKeganasan3.getText(), 
                KecurigaanKeganasan4.getSelectedItem().toString(),NilaiKecurigaanKeganasan4.getText(),KecurigaanKeganasan5.getSelectedItem().toString(),NilaiKecurigaanKeganasan5.getText(),KecurigaanKeganasan6.getSelectedItem().toString(),NilaiKecurigaanKeganasan6.getText(),
                KecurigaanKeganasan7.getSelectedItem().toString(),NilaiKecurigaanKeganasan7.getText(),KecurigaanKeganasan8.getSelectedItem().toString(),NilaiKecurigaanKeganasan8.getText(),TotalHasil.getText(),HasilPemeriksaanSadanis.getSelectedItem().toString(), 
                TindakLanjutSadanis.getSelectedItem().toString(),HasilSkrining.getSelectedItem().toString(),Keterangan.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
}

/*
 * By Mas Elkhanza
 */


package rekammedis;

import bridging.ApiOrthanc;
import bridging.OrthancDICOM;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import java.nio.file.Files;
import java.util.Base64;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;


/**
 *
 * @author perpustakaan
 */
public final class RMHasilPemeriksaanSpirometri extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDicom;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private StringBuilder htmlContent;
    private String finger="";
    private JsonNode root;
    private String TANGGALMUNDUR="yes";
    private ObjectMapper mapper= new ObjectMapper();
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMHasilPemeriksaanSpirometri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","Kode Dokter","Nama Dokter","Tanggal","Kiriman Dari","Diagnosa Klinis","Tinggi (cm)","Berat (kg)","Etnis","Persamaan Referensi","Suhu (°C)","Tek. Barometrik (mmHg)","Obat Dikonsumsi","Bronkodilator Uji","FVC Pre","FVC Pred","FVC LLN","FVC %Pred","FVC Z","FVC Post","FVC Post %Pred","FVC %Perubahan","FEV1 Pre","FEV1 Pred","FEV1 LLN","FEV1 %Pred","FEV1 Z","FEV1 Post","FEV1 Post %Pred","FEV1 %Perubahan","Rasio Pre","Rasio Pred","Rasio LLN","Rasio %Pred","Rasio Z","Rasio Post","Rasio Post %Pred","Rasio %Perubahan","PEF Pre","PEF Pred","PEF LLN","PEF %Pred","PEF Z","PEF Post","PEF Post %Pred","PEF %Perubahan","FEF25-75 Pre","FEF25-75 Pred","FEF25-75 LLN","FEF25-75 %Pred","FEF25-75 Z","FEF25-75 Post","FEF25-75 Post %Pred","FEF25-75 %Perubahan","Manuver Pre","Grade FVC Pre","Grade FEV1 Pre","Manuver Post","Grade FVC Post","Grade FEV1 Post","Operator","Catatan Pelaksanaan","Pola Gangguan","Derajat","Respons Bronkodilator","Kesimpulan","Rekomendasi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 70; i++) {
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
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(115);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(110);
            }else if(i==12){
                column.setPreferredWidth(130);
            }else if(i==13){
                column.setPreferredWidth(60);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(200);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(62);
            }else if(i==18){
                column.setPreferredWidth(62);
            }else if(i==19){
                column.setPreferredWidth(62);
            }else if(i==20){
                column.setPreferredWidth(62);
            }else if(i==21){
                column.setPreferredWidth(62);
            }else if(i==22){
                column.setPreferredWidth(62);
            }else if(i==23){
                column.setPreferredWidth(62);
            }else if(i==24){
                column.setPreferredWidth(62);
            }else if(i==25){
                column.setPreferredWidth(62);
            }else if(i==26){
                column.setPreferredWidth(62);
            }else if(i==27){
                column.setPreferredWidth(62);
            }else if(i==28){
                column.setPreferredWidth(62);
            }else if(i==29){
                column.setPreferredWidth(62);
            }else if(i==30){
                column.setPreferredWidth(62);
            }else if(i==31){
                column.setPreferredWidth(62);
            }else if(i==32){
                column.setPreferredWidth(62);
            }else if(i==33){
                column.setPreferredWidth(62);
            }else if(i==34){
                column.setPreferredWidth(62);
            }else if(i==35){
                column.setPreferredWidth(62);
            }else if(i==36){
                column.setPreferredWidth(62);
            }else if(i==37){
                column.setPreferredWidth(62);
            }else if(i==38){
                column.setPreferredWidth(62);
            }else if(i==39){
                column.setPreferredWidth(62);
            }else if(i==40){
                column.setPreferredWidth(62);
            }else if(i==41){
                column.setPreferredWidth(62);
            }else if(i==42){
                column.setPreferredWidth(62);
            }else if(i==43){
                column.setPreferredWidth(62);
            }else if(i==44){
                column.setPreferredWidth(62);
            }else if(i==45){
                column.setPreferredWidth(62);
            }else if(i==46){
                column.setPreferredWidth(62);
            }else if(i==47){
                column.setPreferredWidth(62);
            }else if(i==48){
                column.setPreferredWidth(62);
            }else if(i==49){
                column.setPreferredWidth(62);
            }else if(i==50){
                column.setPreferredWidth(62);
            }else if(i==51){
                column.setPreferredWidth(62);
            }else if(i==52){
                column.setPreferredWidth(62);
            }else if(i==53){
                column.setPreferredWidth(62);
            }else if(i==54){
                column.setPreferredWidth(62);
            }else if(i==55){
                column.setPreferredWidth(62);
            }else if(i==56){
                column.setPreferredWidth(62);
            }else if(i==57){
                column.setPreferredWidth(60);
            }else if(i==58){
                column.setPreferredWidth(60);
            }else if(i==59){
                column.setPreferredWidth(60);
            }else if(i==60){
                column.setPreferredWidth(60);
            }else if(i==61){
                column.setPreferredWidth(60);
            }else if(i==62){
                column.setPreferredWidth(60);
            }else if(i==63){
                column.setPreferredWidth(150);
            }else if(i==64){
                column.setPreferredWidth(250);
            }else if(i==65){
                column.setPreferredWidth(130);
            }else if(i==66){
                column.setPreferredWidth(90);
            }else if(i==67){
                column.setPreferredWidth(130);
            }else if(i==68){
                column.setPreferredWidth(250);
            }else if(i==69){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDicom=new DefaultTableModel(null,new Object[]{
            "UUID Pasien","ID Studies","ID Series"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbListDicom.setModel(tabModeDicom);
        tbListDicom.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbListDicom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbListDicom.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(270);
            }else if(i==2){
                column.setPreferredWidth(270);
            }
        }
        tbListDicom.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        KirimanDari.setDocument(new batasInput((int)50).getKata(KirimanDari));
        DiagnosaKlinis.setDocument(new batasInput((int)50).getKata(DiagnosaKlinis));
        TinggiBadan.setDocument(new batasInput((int)6).getKata(TinggiBadan));
        BeratBadan.setDocument(new batasInput((int)6).getKata(BeratBadan));
        PersamaanReferensi.setDocument(new batasInput((int)50).getKata(PersamaanReferensi));
        SuhuRuangan.setDocument(new batasInput((int)5).getKata(SuhuRuangan));
        TekananBarometrik.setDocument(new batasInput((int)6).getKata(TekananBarometrik));
        ObatDikonsumsi.setDocument(new batasInput((int)200).getKata(ObatDikonsumsi));
        Bronkodilator.setDocument(new batasInput((int)100).getKata(Bronkodilator));
        FVCPre.setDocument(new batasInput((int)10).getKata(FVCPre));
        FVCPred.setDocument(new batasInput((int)10).getKata(FVCPred));
        FVCLLN.setDocument(new batasInput((int)10).getKata(FVCLLN));
        FVCPersen.setDocument(new batasInput((int)10).getKata(FVCPersen));
        FVCZ.setDocument(new batasInput((int)10).getKata(FVCZ));
        FVCPost.setDocument(new batasInput((int)10).getKata(FVCPost));
        FVCPostPersen.setDocument(new batasInput((int)10).getKata(FVCPostPersen));
        FVCPerubahan.setDocument(new batasInput((int)10).getKata(FVCPerubahan));
        FEV1Pre.setDocument(new batasInput((int)10).getKata(FEV1Pre));
        FEV1Pred.setDocument(new batasInput((int)10).getKata(FEV1Pred));
        FEV1LLN.setDocument(new batasInput((int)10).getKata(FEV1LLN));
        FEV1Persen.setDocument(new batasInput((int)10).getKata(FEV1Persen));
        FEV1Z.setDocument(new batasInput((int)10).getKata(FEV1Z));
        FEV1Post.setDocument(new batasInput((int)10).getKata(FEV1Post));
        FEV1PostPersen.setDocument(new batasInput((int)10).getKata(FEV1PostPersen));
        FEV1Perubahan.setDocument(new batasInput((int)10).getKata(FEV1Perubahan));
        RasioPre.setDocument(new batasInput((int)10).getKata(RasioPre));
        RasioPred.setDocument(new batasInput((int)10).getKata(RasioPred));
        RasioLLN.setDocument(new batasInput((int)10).getKata(RasioLLN));
        RasioPersen.setDocument(new batasInput((int)10).getKata(RasioPersen));
        RasioZ.setDocument(new batasInput((int)10).getKata(RasioZ));
        RasioPost.setDocument(new batasInput((int)10).getKata(RasioPost));
        RasioPostPersen.setDocument(new batasInput((int)10).getKata(RasioPostPersen));
        RasioPerubahan.setDocument(new batasInput((int)10).getKata(RasioPerubahan));
        PEFPre.setDocument(new batasInput((int)10).getKata(PEFPre));
        PEFPred.setDocument(new batasInput((int)10).getKata(PEFPred));
        PEFLLN.setDocument(new batasInput((int)10).getKata(PEFLLN));
        PEFPersen.setDocument(new batasInput((int)10).getKata(PEFPersen));
        PEFZ.setDocument(new batasInput((int)10).getKata(PEFZ));
        PEFPost.setDocument(new batasInput((int)10).getKata(PEFPost));
        PEFPostPersen.setDocument(new batasInput((int)10).getKata(PEFPostPersen));
        PEFPerubahan.setDocument(new batasInput((int)10).getKata(PEFPerubahan));
        FEF2575Pre.setDocument(new batasInput((int)10).getKata(FEF2575Pre));
        FEF2575Pred.setDocument(new batasInput((int)10).getKata(FEF2575Pred));
        FEF2575LLN.setDocument(new batasInput((int)10).getKata(FEF2575LLN));
        FEF2575Persen.setDocument(new batasInput((int)10).getKata(FEF2575Persen));
        FEF2575Z.setDocument(new batasInput((int)10).getKata(FEF2575Z));
        FEF2575Post.setDocument(new batasInput((int)10).getKata(FEF2575Post));
        FEF2575PostPersen.setDocument(new batasInput((int)10).getKata(FEF2575PostPersen));
        FEF2575Perubahan.setDocument(new batasInput((int)10).getKata(FEF2575Perubahan));
        ManuverPre.setDocument(new batasInput((int)2).getKata(ManuverPre));
        ManuverPost.setDocument(new batasInput((int)2).getKata(ManuverPost));
        Operator.setDocument(new batasInput((int)50).getKata(Operator));
        CatatanPelaksanaan.setDocument(new batasInput((int)300).getKata(CatatanPelaksanaan));
        Kesimpulan.setDocument(new batasInput((int)300).getKata(Kesimpulan));
        Rekomendasi.setDocument(new batasInput((int)200).getKata(Rekomendasi));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(false);
        LoadHTML2.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
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
        jPopupMenu1 = new widget.PopupMenu();
        MnPenilaianMedis = new widget.MenuItem();
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
        TabRawat = new widget.TabPane();
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
        jLabel10 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel30 = new widget.Label();
        KirimanDari = new widget.TextBox();
        jLabel32 = new widget.Label();
        DiagnosaKlinis = new widget.TextBox();
        jLabel_GrupPre = new widget.Label();
        jLabel_GrupPost = new widget.Label();
        jLabel_KolPre = new widget.Label();
        jLabel_KolPred = new widget.Label();
        jLabel_KolLLN = new widget.Label();
        jLabel_KolPersen = new widget.Label();
        jLabel_KolZ = new widget.Label();
        jLabel_KolPost = new widget.Label();
        jLabel_KolPostPersen = new widget.Label();
        jLabel_KolPerubahan = new widget.Label();
        jLabel_ParFVC = new widget.Label();
        jLabel_ParFEV1 = new widget.Label();
        jLabel_ParRasio = new widget.Label();
        jLabel_ParPEF = new widget.Label();
        jLabel_ParFEF2575 = new widget.Label();
        jLabel_TinggiBadan = new widget.Label();
        TinggiBadan = new widget.TextBox();
        jLabel_BeratBadan = new widget.Label();
        BeratBadan = new widget.TextBox();
        jLabel_Etnis = new widget.Label();
        Etnis = new widget.ComboBox();
        jLabel_PersamaanReferensi = new widget.Label();
        PersamaanReferensi = new widget.TextBox();
        jLabel_SuhuRuangan = new widget.Label();
        SuhuRuangan = new widget.TextBox();
        jLabel_TekananBarometrik = new widget.Label();
        TekananBarometrik = new widget.TextBox();
        jLabel_ObatDikonsumsi = new widget.Label();
        ObatDikonsumsi = new widget.TextBox();
        jLabel_Bronkodilator = new widget.Label();
        Bronkodilator = new widget.TextBox();
        FVCPre = new widget.TextBox();
        FVCPred = new widget.TextBox();
        FVCLLN = new widget.TextBox();
        FVCPersen = new widget.TextBox();
        FVCZ = new widget.TextBox();
        FVCPost = new widget.TextBox();
        FVCPostPersen = new widget.TextBox();
        FVCPerubahan = new widget.TextBox();
        FEV1Pre = new widget.TextBox();
        FEV1Pred = new widget.TextBox();
        FEV1LLN = new widget.TextBox();
        FEV1Persen = new widget.TextBox();
        FEV1Z = new widget.TextBox();
        FEV1Post = new widget.TextBox();
        FEV1PostPersen = new widget.TextBox();
        FEV1Perubahan = new widget.TextBox();
        RasioPre = new widget.TextBox();
        RasioPred = new widget.TextBox();
        RasioLLN = new widget.TextBox();
        RasioPersen = new widget.TextBox();
        RasioZ = new widget.TextBox();
        RasioPost = new widget.TextBox();
        RasioPostPersen = new widget.TextBox();
        RasioPerubahan = new widget.TextBox();
        PEFPre = new widget.TextBox();
        PEFPred = new widget.TextBox();
        PEFLLN = new widget.TextBox();
        PEFPersen = new widget.TextBox();
        PEFZ = new widget.TextBox();
        PEFPost = new widget.TextBox();
        PEFPostPersen = new widget.TextBox();
        PEFPerubahan = new widget.TextBox();
        FEF2575Pre = new widget.TextBox();
        FEF2575Pred = new widget.TextBox();
        FEF2575LLN = new widget.TextBox();
        FEF2575Persen = new widget.TextBox();
        FEF2575Z = new widget.TextBox();
        FEF2575Post = new widget.TextBox();
        FEF2575PostPersen = new widget.TextBox();
        FEF2575Perubahan = new widget.TextBox();
        jLabel_ManuverPre = new widget.Label();
        ManuverPre = new widget.TextBox();
        jLabel_GradeFVCPre = new widget.Label();
        GradeFVCPre = new widget.ComboBox();
        jLabel_GradeFEV1Pre = new widget.Label();
        GradeFEV1Pre = new widget.ComboBox();
        jLabel_ManuverPost = new widget.Label();
        ManuverPost = new widget.TextBox();
        jLabel_GradeFVCPost = new widget.Label();
        GradeFVCPost = new widget.ComboBox();
        jLabel_GradeFEV1Post = new widget.Label();
        GradeFEV1Post = new widget.ComboBox();
        jLabel_Operator = new widget.Label();
        Operator = new widget.TextBox();
        jLabel_CatatanPelaksanaan = new widget.Label();
        scrollPane_CatatanPelaksanaan = new widget.ScrollPane();
        CatatanPelaksanaan = new widget.TextArea();
        jLabel_PolaGangguan = new widget.Label();
        PolaGangguan = new widget.ComboBox();
        jLabel_Derajat = new widget.Label();
        Derajat = new widget.ComboBox();
        jLabel_ResponsBronkodilator = new widget.Label();
        ResponsBronkodilator = new widget.ComboBox();
        jLabel_Kesimpulan = new widget.Label();
        scrollPane_Kesimpulan = new widget.ScrollPane();
        Kesimpulan = new widget.TextArea();
        jLabel_Rekomendasi = new widget.Label();
        Rekomendasi = new widget.TextBox();
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
        TabData = new widget.TabPane();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        FormOrthan = new widget.PanelBiasa();
        Scroll6 = new widget.ScrollPane();
        tbListDicom = new widget.Table();
        panelGlass7 = new widget.panelisi();
        btnDicom = new widget.Button();
        btnUploud = new widget.Button();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianMedis.setText("Formulir Hasil Pemeriksaan Spirometri");
        MnPenilaianMedis.setName("MnPenilaianMedis"); // NOI18N
        MnPenilaianMedis.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenilaianMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianMedis);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Hasil Pemeriksaan Spirometri ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        FormInput.setPreferredSize(new java.awt.Dimension(750, 765));
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
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 40, 110, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(186, 40, 295, 23);

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
        BtnDokter.setBounds(484, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 750, 1);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(528, 40, 52, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-05-2026 20:44:43" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(584, 40, 140, 23);

        jLabel30.setText("Kiriman Dari :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 80, 120, 23);

        KirimanDari.setFocusTraversalPolicyProvider(true);
        KirimanDari.setName("KirimanDari"); // NOI18N
        KirimanDari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KirimanDariKeyPressed(evt);
            }
        });
        FormInput.add(KirimanDari);
        KirimanDari.setBounds(124, 80, 210, 23);

        jLabel32.setText("Diagnosa Klinis :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(338, 80, 112, 23);

        DiagnosaKlinis.setFocusTraversalPolicyProvider(true);
        DiagnosaKlinis.setName("DiagnosaKlinis"); // NOI18N
        DiagnosaKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKlinisKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaKlinis);
        DiagnosaKlinis.setBounds(454, 80, 270, 23);

        jLabel_GrupPre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_GrupPre.setText("Pre-Bronkodilator");
        jLabel_GrupPre.setName("jLabel_GrupPre"); // NOI18N
        FormInput.add(jLabel_GrupPre);
        jLabel_GrupPre.setBounds(124, 232, 381, 23);

        jLabel_GrupPost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_GrupPost.setText("Post-Bronkodilator");
        jLabel_GrupPost.setName("jLabel_GrupPost"); // NOI18N
        FormInput.add(jLabel_GrupPost);
        jLabel_GrupPost.setBounds(509, 232, 227, 23);

        jLabel_KolPre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_KolPre.setText("Terukur");
        jLabel_KolPre.setName("jLabel_KolPre"); // NOI18N
        FormInput.add(jLabel_KolPre);
        jLabel_KolPre.setBounds(124, 256, 73, 23);

        jLabel_KolPred.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_KolPred.setText("Prediksi");
        jLabel_KolPred.setName("jLabel_KolPred"); // NOI18N
        FormInput.add(jLabel_KolPred);
        jLabel_KolPred.setBounds(201, 256, 73, 23);

        jLabel_KolLLN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_KolLLN.setText("LLN");
        jLabel_KolLLN.setName("jLabel_KolLLN"); // NOI18N
        FormInput.add(jLabel_KolLLN);
        jLabel_KolLLN.setBounds(278, 256, 73, 23);

        jLabel_KolPersen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_KolPersen.setText("%Pred");
        jLabel_KolPersen.setName("jLabel_KolPersen"); // NOI18N
        FormInput.add(jLabel_KolPersen);
        jLabel_KolPersen.setBounds(355, 256, 73, 23);

        jLabel_KolZ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_KolZ.setText("Z-score");
        jLabel_KolZ.setName("jLabel_KolZ"); // NOI18N
        FormInput.add(jLabel_KolZ);
        jLabel_KolZ.setBounds(432, 256, 73, 23);

        jLabel_KolPost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_KolPost.setText("Terukur");
        jLabel_KolPost.setName("jLabel_KolPost"); // NOI18N
        FormInput.add(jLabel_KolPost);
        jLabel_KolPost.setBounds(509, 256, 73, 23);

        jLabel_KolPostPersen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_KolPostPersen.setText("%Pred");
        jLabel_KolPostPersen.setName("jLabel_KolPostPersen"); // NOI18N
        FormInput.add(jLabel_KolPostPersen);
        jLabel_KolPostPersen.setBounds(586, 256, 73, 23);

        jLabel_KolPerubahan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_KolPerubahan.setText("%Perubahan");
        jLabel_KolPerubahan.setName("jLabel_KolPerubahan"); // NOI18N
        FormInput.add(jLabel_KolPerubahan);
        jLabel_KolPerubahan.setBounds(663, 256, 73, 23);

        jLabel_ParFVC.setText("FVC (L)");
        jLabel_ParFVC.setName("jLabel_ParFVC"); // NOI18N
        FormInput.add(jLabel_ParFVC);
        jLabel_ParFVC.setBounds(0, 282, 120, 23);

        jLabel_ParFEV1.setText("FEV1 (L)");
        jLabel_ParFEV1.setName("jLabel_ParFEV1"); // NOI18N
        FormInput.add(jLabel_ParFEV1);
        jLabel_ParFEV1.setBounds(0, 310, 120, 23);

        jLabel_ParRasio.setText("FEV1/FVC (%)");
        jLabel_ParRasio.setName("jLabel_ParRasio"); // NOI18N
        FormInput.add(jLabel_ParRasio);
        jLabel_ParRasio.setBounds(0, 338, 120, 23);

        jLabel_ParPEF.setText("PEF (L/s)");
        jLabel_ParPEF.setName("jLabel_ParPEF"); // NOI18N
        FormInput.add(jLabel_ParPEF);
        jLabel_ParPEF.setBounds(0, 366, 120, 23);

        jLabel_ParFEF2575.setText("FEF25-75 (L/s)");
        jLabel_ParFEF2575.setName("jLabel_ParFEF2575"); // NOI18N
        FormInput.add(jLabel_ParFEF2575);
        jLabel_ParFEF2575.setBounds(0, 394, 120, 23);

        jLabel_TinggiBadan.setText("Tinggi (cm) :");
        jLabel_TinggiBadan.setName("jLabel_TinggiBadan"); // NOI18N
        FormInput.add(jLabel_TinggiBadan);
        jLabel_TinggiBadan.setBounds(0, 110, 120, 23);

        TinggiBadan.setFocusTraversalPolicyProvider(true);
        TinggiBadan.setName("TinggiBadan"); // NOI18N
        TinggiBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggiBadanKeyPressed(evt);
            }
        });
        FormInput.add(TinggiBadan);
        TinggiBadan.setBounds(124, 110, 70, 23);

        jLabel_BeratBadan.setText("Berat (kg) :");
        jLabel_BeratBadan.setName("jLabel_BeratBadan"); // NOI18N
        FormInput.add(jLabel_BeratBadan);
        jLabel_BeratBadan.setBounds(210, 110, 90, 23);

        BeratBadan.setFocusTraversalPolicyProvider(true);
        BeratBadan.setName("BeratBadan"); // NOI18N
        BeratBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratBadanKeyPressed(evt);
            }
        });
        FormInput.add(BeratBadan);
        BeratBadan.setBounds(304, 110, 70, 23);

        jLabel_Etnis.setText("Etnis :");
        jLabel_Etnis.setName("jLabel_Etnis"); // NOI18N
        FormInput.add(jLabel_Etnis);
        jLabel_Etnis.setBounds(390, 110, 50, 23);

        Etnis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Asia Tenggara", "Asia Timur Laut", "Kaukasia", "Afrika-Amerika", "Lainnya/Campuran" }));
        Etnis.setToolTipText("Kategori etnis mengikuti Global Lung Function Initiative (GLI) yang direkomendasikan ATS/ERS 2019");
        Etnis.setName("Etnis"); // NOI18N
        Etnis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EtnisKeyPressed(evt);
            }
        });
        FormInput.add(Etnis);
        Etnis.setBounds(444, 110, 280, 23);

        jLabel_PersamaanReferensi.setText("Ref. Prediksi :");
        jLabel_PersamaanReferensi.setName("jLabel_PersamaanReferensi"); // NOI18N
        FormInput.add(jLabel_PersamaanReferensi);
        jLabel_PersamaanReferensi.setBounds(0, 140, 120, 23);

        PersamaanReferensi.setFocusTraversalPolicyProvider(true);
        PersamaanReferensi.setName("PersamaanReferensi"); // NOI18N
        PersamaanReferensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersamaanReferensiKeyPressed(evt);
            }
        });
        FormInput.add(PersamaanReferensi);
        PersamaanReferensi.setBounds(124, 140, 170, 23);

        jLabel_SuhuRuangan.setText("Suhu (°C) :");
        jLabel_SuhuRuangan.setName("jLabel_SuhuRuangan"); // NOI18N
        FormInput.add(jLabel_SuhuRuangan);
        jLabel_SuhuRuangan.setBounds(300, 140, 70, 23);

        SuhuRuangan.setFocusTraversalPolicyProvider(true);
        SuhuRuangan.setName("SuhuRuangan"); // NOI18N
        SuhuRuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuRuanganKeyPressed(evt);
            }
        });
        FormInput.add(SuhuRuangan);
        SuhuRuangan.setBounds(374, 140, 50, 23);

        jLabel_TekananBarometrik.setText("Tek. Barometrik (mmHg) :");
        jLabel_TekananBarometrik.setName("jLabel_TekananBarometrik"); // NOI18N
        FormInput.add(jLabel_TekananBarometrik);
        jLabel_TekananBarometrik.setBounds(432, 140, 150, 23);

        TekananBarometrik.setFocusTraversalPolicyProvider(true);
        TekananBarometrik.setName("TekananBarometrik"); // NOI18N
        TekananBarometrik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TekananBarometrikKeyPressed(evt);
            }
        });
        FormInput.add(TekananBarometrik);
        TekananBarometrik.setBounds(586, 140, 60, 23);

        jLabel_ObatDikonsumsi.setText("Obat Dikonsumsi :");
        jLabel_ObatDikonsumsi.setName("jLabel_ObatDikonsumsi"); // NOI18N
        FormInput.add(jLabel_ObatDikonsumsi);
        jLabel_ObatDikonsumsi.setBounds(0, 170, 120, 23);

        ObatDikonsumsi.setFocusTraversalPolicyProvider(true);
        ObatDikonsumsi.setName("ObatDikonsumsi"); // NOI18N
        ObatDikonsumsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatDikonsumsiKeyPressed(evt);
            }
        });
        FormInput.add(ObatDikonsumsi);
        ObatDikonsumsi.setBounds(124, 170, 600, 23);

        jLabel_Bronkodilator.setText("Bronkodilator Uji :");
        jLabel_Bronkodilator.setName("jLabel_Bronkodilator"); // NOI18N
        FormInput.add(jLabel_Bronkodilator);
        jLabel_Bronkodilator.setBounds(0, 200, 120, 23);

        Bronkodilator.setFocusTraversalPolicyProvider(true);
        Bronkodilator.setName("Bronkodilator"); // NOI18N
        Bronkodilator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BronkodilatorKeyPressed(evt);
            }
        });
        FormInput.add(Bronkodilator);
        Bronkodilator.setBounds(124, 200, 600, 23);

        FVCPre.setFocusTraversalPolicyProvider(true);
        FVCPre.setName("FVCPre"); // NOI18N
        FVCPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FVCPreKeyPressed(evt);
            }
        });
        FormInput.add(FVCPre);
        FVCPre.setBounds(124, 282, 73, 23);

        FVCPred.setFocusTraversalPolicyProvider(true);
        FVCPred.setName("FVCPred"); // NOI18N
        FVCPred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FVCPredKeyPressed(evt);
            }
        });
        FormInput.add(FVCPred);
        FVCPred.setBounds(201, 282, 73, 23);

        FVCLLN.setFocusTraversalPolicyProvider(true);
        FVCLLN.setName("FVCLLN"); // NOI18N
        FVCLLN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FVCLLNKeyPressed(evt);
            }
        });
        FormInput.add(FVCLLN);
        FVCLLN.setBounds(278, 282, 73, 23);

        FVCPersen.setFocusTraversalPolicyProvider(true);
        FVCPersen.setName("FVCPersen"); // NOI18N
        FVCPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FVCPersenKeyPressed(evt);
            }
        });
        FormInput.add(FVCPersen);
        FVCPersen.setBounds(355, 282, 73, 23);

        FVCZ.setFocusTraversalPolicyProvider(true);
        FVCZ.setName("FVCZ"); // NOI18N
        FVCZ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FVCZKeyPressed(evt);
            }
        });
        FormInput.add(FVCZ);
        FVCZ.setBounds(432, 282, 73, 23);

        FVCPost.setFocusTraversalPolicyProvider(true);
        FVCPost.setName("FVCPost"); // NOI18N
        FVCPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FVCPostKeyPressed(evt);
            }
        });
        FormInput.add(FVCPost);
        FVCPost.setBounds(509, 282, 73, 23);

        FVCPostPersen.setFocusTraversalPolicyProvider(true);
        FVCPostPersen.setName("FVCPostPersen"); // NOI18N
        FVCPostPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FVCPostPersenKeyPressed(evt);
            }
        });
        FormInput.add(FVCPostPersen);
        FVCPostPersen.setBounds(586, 282, 73, 23);

        FVCPerubahan.setFocusTraversalPolicyProvider(true);
        FVCPerubahan.setName("FVCPerubahan"); // NOI18N
        FVCPerubahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FVCPerubahanKeyPressed(evt);
            }
        });
        FormInput.add(FVCPerubahan);
        FVCPerubahan.setBounds(663, 282, 73, 23);

        FEV1Pre.setFocusTraversalPolicyProvider(true);
        FEV1Pre.setName("FEV1Pre"); // NOI18N
        FEV1Pre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEV1PreKeyPressed(evt);
            }
        });
        FormInput.add(FEV1Pre);
        FEV1Pre.setBounds(124, 310, 73, 23);

        FEV1Pred.setFocusTraversalPolicyProvider(true);
        FEV1Pred.setName("FEV1Pred"); // NOI18N
        FEV1Pred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEV1PredKeyPressed(evt);
            }
        });
        FormInput.add(FEV1Pred);
        FEV1Pred.setBounds(201, 310, 73, 23);

        FEV1LLN.setFocusTraversalPolicyProvider(true);
        FEV1LLN.setName("FEV1LLN"); // NOI18N
        FEV1LLN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEV1LLNKeyPressed(evt);
            }
        });
        FormInput.add(FEV1LLN);
        FEV1LLN.setBounds(278, 310, 73, 23);

        FEV1Persen.setFocusTraversalPolicyProvider(true);
        FEV1Persen.setName("FEV1Persen"); // NOI18N
        FEV1Persen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEV1PersenKeyPressed(evt);
            }
        });
        FormInput.add(FEV1Persen);
        FEV1Persen.setBounds(355, 310, 73, 23);

        FEV1Z.setFocusTraversalPolicyProvider(true);
        FEV1Z.setName("FEV1Z"); // NOI18N
        FEV1Z.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEV1ZKeyPressed(evt);
            }
        });
        FormInput.add(FEV1Z);
        FEV1Z.setBounds(432, 310, 73, 23);

        FEV1Post.setFocusTraversalPolicyProvider(true);
        FEV1Post.setName("FEV1Post"); // NOI18N
        FEV1Post.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEV1PostKeyPressed(evt);
            }
        });
        FormInput.add(FEV1Post);
        FEV1Post.setBounds(509, 310, 73, 23);

        FEV1PostPersen.setFocusTraversalPolicyProvider(true);
        FEV1PostPersen.setName("FEV1PostPersen"); // NOI18N
        FEV1PostPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEV1PostPersenKeyPressed(evt);
            }
        });
        FormInput.add(FEV1PostPersen);
        FEV1PostPersen.setBounds(586, 310, 73, 23);

        FEV1Perubahan.setFocusTraversalPolicyProvider(true);
        FEV1Perubahan.setName("FEV1Perubahan"); // NOI18N
        FEV1Perubahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEV1PerubahanKeyPressed(evt);
            }
        });
        FormInput.add(FEV1Perubahan);
        FEV1Perubahan.setBounds(663, 310, 73, 23);

        RasioPre.setFocusTraversalPolicyProvider(true);
        RasioPre.setName("RasioPre"); // NOI18N
        RasioPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasioPreKeyPressed(evt);
            }
        });
        FormInput.add(RasioPre);
        RasioPre.setBounds(124, 338, 73, 23);

        RasioPred.setFocusTraversalPolicyProvider(true);
        RasioPred.setName("RasioPred"); // NOI18N
        RasioPred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasioPredKeyPressed(evt);
            }
        });
        FormInput.add(RasioPred);
        RasioPred.setBounds(201, 338, 73, 23);

        RasioLLN.setFocusTraversalPolicyProvider(true);
        RasioLLN.setName("RasioLLN"); // NOI18N
        RasioLLN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasioLLNKeyPressed(evt);
            }
        });
        FormInput.add(RasioLLN);
        RasioLLN.setBounds(278, 338, 73, 23);

        RasioPersen.setFocusTraversalPolicyProvider(true);
        RasioPersen.setName("RasioPersen"); // NOI18N
        RasioPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasioPersenKeyPressed(evt);
            }
        });
        FormInput.add(RasioPersen);
        RasioPersen.setBounds(355, 338, 73, 23);

        RasioZ.setFocusTraversalPolicyProvider(true);
        RasioZ.setName("RasioZ"); // NOI18N
        RasioZ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasioZKeyPressed(evt);
            }
        });
        FormInput.add(RasioZ);
        RasioZ.setBounds(432, 338, 73, 23);

        RasioPost.setFocusTraversalPolicyProvider(true);
        RasioPost.setName("RasioPost"); // NOI18N
        RasioPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasioPostKeyPressed(evt);
            }
        });
        FormInput.add(RasioPost);
        RasioPost.setBounds(509, 338, 73, 23);

        RasioPostPersen.setFocusTraversalPolicyProvider(true);
        RasioPostPersen.setName("RasioPostPersen"); // NOI18N
        RasioPostPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasioPostPersenKeyPressed(evt);
            }
        });
        FormInput.add(RasioPostPersen);
        RasioPostPersen.setBounds(586, 338, 73, 23);

        RasioPerubahan.setFocusTraversalPolicyProvider(true);
        RasioPerubahan.setName("RasioPerubahan"); // NOI18N
        RasioPerubahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RasioPerubahanKeyPressed(evt);
            }
        });
        FormInput.add(RasioPerubahan);
        RasioPerubahan.setBounds(663, 338, 73, 23);

        PEFPre.setFocusTraversalPolicyProvider(true);
        PEFPre.setName("PEFPre"); // NOI18N
        PEFPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PEFPreKeyPressed(evt);
            }
        });
        FormInput.add(PEFPre);
        PEFPre.setBounds(124, 366, 73, 23);

        PEFPred.setFocusTraversalPolicyProvider(true);
        PEFPred.setName("PEFPred"); // NOI18N
        PEFPred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PEFPredKeyPressed(evt);
            }
        });
        FormInput.add(PEFPred);
        PEFPred.setBounds(201, 366, 73, 23);

        PEFLLN.setFocusTraversalPolicyProvider(true);
        PEFLLN.setName("PEFLLN"); // NOI18N
        PEFLLN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PEFLLNKeyPressed(evt);
            }
        });
        FormInput.add(PEFLLN);
        PEFLLN.setBounds(278, 366, 73, 23);

        PEFPersen.setFocusTraversalPolicyProvider(true);
        PEFPersen.setName("PEFPersen"); // NOI18N
        PEFPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PEFPersenKeyPressed(evt);
            }
        });
        FormInput.add(PEFPersen);
        PEFPersen.setBounds(355, 366, 73, 23);

        PEFZ.setFocusTraversalPolicyProvider(true);
        PEFZ.setName("PEFZ"); // NOI18N
        PEFZ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PEFZKeyPressed(evt);
            }
        });
        FormInput.add(PEFZ);
        PEFZ.setBounds(432, 366, 73, 23);

        PEFPost.setFocusTraversalPolicyProvider(true);
        PEFPost.setName("PEFPost"); // NOI18N
        PEFPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PEFPostKeyPressed(evt);
            }
        });
        FormInput.add(PEFPost);
        PEFPost.setBounds(509, 366, 73, 23);

        PEFPostPersen.setFocusTraversalPolicyProvider(true);
        PEFPostPersen.setName("PEFPostPersen"); // NOI18N
        PEFPostPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PEFPostPersenKeyPressed(evt);
            }
        });
        FormInput.add(PEFPostPersen);
        PEFPostPersen.setBounds(586, 366, 73, 23);

        PEFPerubahan.setFocusTraversalPolicyProvider(true);
        PEFPerubahan.setName("PEFPerubahan"); // NOI18N
        PEFPerubahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PEFPerubahanKeyPressed(evt);
            }
        });
        FormInput.add(PEFPerubahan);
        PEFPerubahan.setBounds(663, 366, 73, 23);

        FEF2575Pre.setFocusTraversalPolicyProvider(true);
        FEF2575Pre.setName("FEF2575Pre"); // NOI18N
        FEF2575Pre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEF2575PreKeyPressed(evt);
            }
        });
        FormInput.add(FEF2575Pre);
        FEF2575Pre.setBounds(124, 394, 73, 23);

        FEF2575Pred.setFocusTraversalPolicyProvider(true);
        FEF2575Pred.setName("FEF2575Pred"); // NOI18N
        FEF2575Pred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEF2575PredKeyPressed(evt);
            }
        });
        FormInput.add(FEF2575Pred);
        FEF2575Pred.setBounds(201, 394, 73, 23);

        FEF2575LLN.setFocusTraversalPolicyProvider(true);
        FEF2575LLN.setName("FEF2575LLN"); // NOI18N
        FEF2575LLN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEF2575LLNKeyPressed(evt);
            }
        });
        FormInput.add(FEF2575LLN);
        FEF2575LLN.setBounds(278, 394, 73, 23);

        FEF2575Persen.setFocusTraversalPolicyProvider(true);
        FEF2575Persen.setName("FEF2575Persen"); // NOI18N
        FEF2575Persen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEF2575PersenKeyPressed(evt);
            }
        });
        FormInput.add(FEF2575Persen);
        FEF2575Persen.setBounds(355, 394, 73, 23);

        FEF2575Z.setFocusTraversalPolicyProvider(true);
        FEF2575Z.setName("FEF2575Z"); // NOI18N
        FEF2575Z.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEF2575ZKeyPressed(evt);
            }
        });
        FormInput.add(FEF2575Z);
        FEF2575Z.setBounds(432, 394, 73, 23);

        FEF2575Post.setFocusTraversalPolicyProvider(true);
        FEF2575Post.setName("FEF2575Post"); // NOI18N
        FEF2575Post.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEF2575PostKeyPressed(evt);
            }
        });
        FormInput.add(FEF2575Post);
        FEF2575Post.setBounds(509, 394, 73, 23);

        FEF2575PostPersen.setFocusTraversalPolicyProvider(true);
        FEF2575PostPersen.setName("FEF2575PostPersen"); // NOI18N
        FEF2575PostPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEF2575PostPersenKeyPressed(evt);
            }
        });
        FormInput.add(FEF2575PostPersen);
        FEF2575PostPersen.setBounds(586, 394, 73, 23);

        FEF2575Perubahan.setFocusTraversalPolicyProvider(true);
        FEF2575Perubahan.setName("FEF2575Perubahan"); // NOI18N
        FEF2575Perubahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FEF2575PerubahanKeyPressed(evt);
            }
        });
        FormInput.add(FEF2575Perubahan);
        FEF2575Perubahan.setBounds(663, 394, 73, 23);

        jLabel_ManuverPre.setText("Manuver Pre :");
        jLabel_ManuverPre.setName("jLabel_ManuverPre"); // NOI18N
        FormInput.add(jLabel_ManuverPre);
        jLabel_ManuverPre.setBounds(0, 430, 120, 23);

        ManuverPre.setFocusTraversalPolicyProvider(true);
        ManuverPre.setName("ManuverPre"); // NOI18N
        ManuverPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManuverPreKeyPressed(evt);
            }
        });
        FormInput.add(ManuverPre);
        ManuverPre.setBounds(124, 430, 40, 23);

        jLabel_GradeFVCPre.setText("Grade FVC :");
        jLabel_GradeFVCPre.setName("jLabel_GradeFVCPre"); // NOI18N
        FormInput.add(jLabel_GradeFVCPre);
        jLabel_GradeFVCPre.setBounds(180, 430, 80, 23);

        GradeFVCPre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "C", "D", "E", "U", "F" }));
        GradeFVCPre.setToolTipText("Grade kualitas ATS/ERS 2019: A (>=3 manuver acceptable, repeatability <=0,15 L), B (2 acceptable, <=0,15 L), C (>=2, <=0,20 L), D (>=2, <=0,25 L), E (>=2 atau 1, >0,25 L), U (0 acceptable, >=1 usable), F (tidak ada)");
        GradeFVCPre.setName("GradeFVCPre"); // NOI18N
        GradeFVCPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GradeFVCPreKeyPressed(evt);
            }
        });
        FormInput.add(GradeFVCPre);
        GradeFVCPre.setBounds(264, 430, 60, 23);

        jLabel_GradeFEV1Pre.setText("Grade FEV1 :");
        jLabel_GradeFEV1Pre.setName("jLabel_GradeFEV1Pre"); // NOI18N
        FormInput.add(jLabel_GradeFEV1Pre);
        jLabel_GradeFEV1Pre.setBounds(340, 430, 80, 23);

        GradeFEV1Pre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "C", "D", "E", "U", "F" }));
        GradeFEV1Pre.setToolTipText("Grade kualitas ATS/ERS 2019: A (>=3 manuver acceptable, repeatability <=0,15 L), B (2 acceptable, <=0,15 L), C (>=2, <=0,20 L), D (>=2, <=0,25 L), E (>=2 atau 1, >0,25 L), U (0 acceptable, >=1 usable), F (tidak ada)");
        GradeFEV1Pre.setName("GradeFEV1Pre"); // NOI18N
        GradeFEV1Pre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GradeFEV1PreKeyPressed(evt);
            }
        });
        FormInput.add(GradeFEV1Pre);
        GradeFEV1Pre.setBounds(424, 430, 60, 23);

        jLabel_ManuverPost.setText("Manuver Post :");
        jLabel_ManuverPost.setName("jLabel_ManuverPost"); // NOI18N
        FormInput.add(jLabel_ManuverPost);
        jLabel_ManuverPost.setBounds(0, 460, 120, 23);

        ManuverPost.setFocusTraversalPolicyProvider(true);
        ManuverPost.setName("ManuverPost"); // NOI18N
        ManuverPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManuverPostKeyPressed(evt);
            }
        });
        FormInput.add(ManuverPost);
        ManuverPost.setBounds(124, 460, 40, 23);

        jLabel_GradeFVCPost.setText("Grade FVC :");
        jLabel_GradeFVCPost.setName("jLabel_GradeFVCPost"); // NOI18N
        FormInput.add(jLabel_GradeFVCPost);
        jLabel_GradeFVCPost.setBounds(180, 460, 80, 23);

        GradeFVCPost.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "C", "D", "E", "U", "F" }));
        GradeFVCPost.setToolTipText("Grade kualitas ATS/ERS 2019: A (>=3 manuver acceptable, repeatability <=0,15 L), B (2 acceptable, <=0,15 L), C (>=2, <=0,20 L), D (>=2, <=0,25 L), E (>=2 atau 1, >0,25 L), U (0 acceptable, >=1 usable), F (tidak ada)");
        GradeFVCPost.setName("GradeFVCPost"); // NOI18N
        GradeFVCPost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GradeFVCPostKeyPressed(evt);
            }
        });
        FormInput.add(GradeFVCPost);
        GradeFVCPost.setBounds(264, 460, 60, 23);

        jLabel_GradeFEV1Post.setText("Grade FEV1 :");
        jLabel_GradeFEV1Post.setName("jLabel_GradeFEV1Post"); // NOI18N
        FormInput.add(jLabel_GradeFEV1Post);
        jLabel_GradeFEV1Post.setBounds(340, 460, 80, 23);

        GradeFEV1Post.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "C", "D", "E", "U", "F" }));
        GradeFEV1Post.setToolTipText("Grade kualitas ATS/ERS 2019: A (>=3 manuver acceptable, repeatability <=0,15 L), B (2 acceptable, <=0,15 L), C (>=2, <=0,20 L), D (>=2, <=0,25 L), E (>=2 atau 1, >0,25 L), U (0 acceptable, >=1 usable), F (tidak ada)");
        GradeFEV1Post.setName("GradeFEV1Post"); // NOI18N
        GradeFEV1Post.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GradeFEV1PostKeyPressed(evt);
            }
        });
        FormInput.add(GradeFEV1Post);
        GradeFEV1Post.setBounds(424, 460, 60, 23);

        jLabel_Operator.setText("Operator :");
        jLabel_Operator.setName("jLabel_Operator"); // NOI18N
        FormInput.add(jLabel_Operator);
        jLabel_Operator.setBounds(0, 490, 120, 23);

        Operator.setFocusTraversalPolicyProvider(true);
        Operator.setName("Operator"); // NOI18N
        Operator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OperatorKeyPressed(evt);
            }
        });
        FormInput.add(Operator);
        Operator.setBounds(124, 490, 300, 23);

        jLabel_CatatanPelaksanaan.setText("Catatan Uji :");
        jLabel_CatatanPelaksanaan.setName("jLabel_CatatanPelaksanaan"); // NOI18N
        FormInput.add(jLabel_CatatanPelaksanaan);
        jLabel_CatatanPelaksanaan.setBounds(0, 520, 120, 23);

        scrollPane_CatatanPelaksanaan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane_CatatanPelaksanaan.setName("scrollPane_CatatanPelaksanaan"); // NOI18N

        CatatanPelaksanaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        CatatanPelaksanaan.setColumns(20);
        CatatanPelaksanaan.setRows(5);
        CatatanPelaksanaan.setName("CatatanPelaksanaan"); // NOI18N
        CatatanPelaksanaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanPelaksanaanKeyPressed(evt);
            }
        });
        scrollPane_CatatanPelaksanaan.setViewportView(CatatanPelaksanaan);

        FormInput.add(scrollPane_CatatanPelaksanaan);
        scrollPane_CatatanPelaksanaan.setBounds(124, 520, 600, 63);

        jLabel_PolaGangguan.setText("Pola Gangguan :");
        jLabel_PolaGangguan.setName("jLabel_PolaGangguan"); // NOI18N
        FormInput.add(jLabel_PolaGangguan);
        jLabel_PolaGangguan.setBounds(0, 595, 120, 23);

        PolaGangguan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Obstruksi", "Restriksi (dugaan)", "Campuran (dugaan)", "Non-spesifik" }));
        PolaGangguan.setToolTipText("ERS/ATS 2022: obstruksi = FEV1/FVC < LLN; restriksi (dugaan) = FEV1/FVC normal dengan FVC < LLN; campuran (dugaan) = FEV1/FVC < LLN dan FVC < LLN. Restriksi dan campuran pasti perlu volume paru (TLC)");
        PolaGangguan.setName("PolaGangguan"); // NOI18N
        PolaGangguan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PolaGangguanKeyPressed(evt);
            }
        });
        FormInput.add(PolaGangguan);
        PolaGangguan.setBounds(124, 595, 200, 23);

        jLabel_Derajat.setText("Derajat :");
        jLabel_Derajat.setName("jLabel_Derajat"); // NOI18N
        FormInput.add(jLabel_Derajat);
        jLabel_Derajat.setBounds(340, 595, 70, 23);

        Derajat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ringan", "Sedang", "Berat" }));
        Derajat.setToolTipText("ERS/ATS 2022, berdasarkan z-score FEV1: ringan -1,65 s.d. -2,5; sedang -2,51 s.d. -4,0; berat < -4,1");
        Derajat.setName("Derajat"); // NOI18N
        Derajat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DerajatKeyPressed(evt);
            }
        });
        FormInput.add(Derajat);
        Derajat.setBounds(414, 595, 150, 23);

        jLabel_ResponsBronkodilator.setText("Respons BD :");
        jLabel_ResponsBronkodilator.setName("jLabel_ResponsBronkodilator"); // NOI18N
        FormInput.add(jLabel_ResponsBronkodilator);
        jLabel_ResponsBronkodilator.setBounds(0, 625, 120, 23);

        ResponsBronkodilator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak dilakukan", "Tidak signifikan", "Signifikan" }));
        ResponsBronkodilator.setToolTipText("ERS/ATS 2022: signifikan bila FEV1 dan/atau FVC naik >= 10% dari nilai prediksi (kriteria 2005: >= 12% dan >= 200 mL dari nilai awal)");
        ResponsBronkodilator.setName("ResponsBronkodilator"); // NOI18N
        ResponsBronkodilator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResponsBronkodilatorKeyPressed(evt);
            }
        });
        FormInput.add(ResponsBronkodilator);
        ResponsBronkodilator.setBounds(124, 625, 200, 23);

        jLabel_Kesimpulan.setText("Kesimpulan :");
        jLabel_Kesimpulan.setName("jLabel_Kesimpulan"); // NOI18N
        FormInput.add(jLabel_Kesimpulan);
        jLabel_Kesimpulan.setBounds(0, 655, 120, 23);

        scrollPane_Kesimpulan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane_Kesimpulan.setName("scrollPane_Kesimpulan"); // NOI18N

        Kesimpulan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulan.setColumns(20);
        Kesimpulan.setRows(5);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        scrollPane_Kesimpulan.setViewportView(Kesimpulan);

        FormInput.add(scrollPane_Kesimpulan);
        scrollPane_Kesimpulan.setBounds(124, 655, 600, 63);

        jLabel_Rekomendasi.setText("Rekomendasi :");
        jLabel_Rekomendasi.setName("jLabel_Rekomendasi"); // NOI18N
        FormInput.add(jLabel_Rekomendasi);
        jLabel_Rekomendasi.setBounds(0, 725, 120, 23);

        Rekomendasi.setFocusTraversalPolicyProvider(true);
        Rekomendasi.setName("Rekomendasi"); // NOI18N
        Rekomendasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RekomendasiKeyPressed(evt);
            }
        });
        FormInput.add(Rekomendasi);
        Rekomendasi.setBounds(124, 725, 600, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Hasil Pemeriksaan Spirometri", internalFrame2);

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

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-05-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-05-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
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
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
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

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(null);
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabData.addTab("Gambar Pemeriksaan Spirometri", FormPhoto);

        FormOrthan.setBackground(new java.awt.Color(255, 255, 255));
        FormOrthan.setBorder(null);
        FormOrthan.setName("FormOrthan"); // NOI18N
        FormOrthan.setPreferredSize(new java.awt.Dimension(115, 73));
        FormOrthan.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbListDicom.setName("tbListDicom"); // NOI18N
        Scroll6.setViewportView(tbListDicom);

        FormOrthan.add(Scroll6, java.awt.BorderLayout.CENTER);

        panelGlass7.setBorder(null);
        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(115, 40));

        btnDicom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        btnDicom.setMnemonic('T');
        btnDicom.setText("Tampilkan DICOM");
        btnDicom.setToolTipText("Alt+T");
        btnDicom.setName("btnDicom"); // NOI18N
        btnDicom.setPreferredSize(new java.awt.Dimension(150, 30));
        btnDicom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDicomActionPerformed(evt);
            }
        });
        panelGlass7.add(btnDicom);

        btnUploud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/2276087_document_extension_format_jpg_paper_icon.png"))); // NOI18N
        btnUploud.setMnemonic('T');
        btnUploud.setText("Uploud Photo");
        btnUploud.setToolTipText("Alt+T");
        btnUploud.setName("btnUploud"); // NOI18N
        btnUploud.setPreferredSize(new java.awt.Dimension(127, 30));
        btnUploud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploudActionPerformed(evt);
            }
        });
        panelGlass7.add(btnUploud);

        FormOrthan.add(panelGlass7, java.awt.BorderLayout.PAGE_END);

        TabData.addTab("Integrasi Orthanc", FormOrthan);

        PanelAccor.add(TabData, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Hasil Pemeriksaan Spirometri", internalFrame3);

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
        }else if(DiagnosaKlinis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKlinis,"Diagnosa Klinis");
        }else if(TinggiBadan.getText().trim().equals("")){
            Valid.textKosong(TinggiBadan,"Tinggi (cm)");
        }else if(PersamaanReferensi.getText().trim().equals("")){
            Valid.textKosong(PersamaanReferensi,"Persamaan Referensi");
        }else if(FVCPre.getText().trim().equals("")){
            Valid.textKosong(FVCPre,"FVC Pre");
        }else if(FEV1Pre.getText().trim().equals("")){
            Valid.textKosong(FEV1Pre,"FEV1 Pre");
        }else if(RasioPre.getText().trim().equals("")){
            Valid.textKosong(RasioPre,"Rasio Pre");
        }else if(Kesimpulan.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan,"Kesimpulan");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Rekomendasi,BtnBatal);
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),Sequel.ambiltanggalsekarang())==true){
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
        }else if(DiagnosaKlinis.getText().trim().equals("")){
            Valid.textKosong(DiagnosaKlinis,"Diagnosa Klinis");
        }else if(TinggiBadan.getText().trim().equals("")){
            Valid.textKosong(TinggiBadan,"Tinggi (cm)");
        }else if(PersamaanReferensi.getText().trim().equals("")){
            Valid.textKosong(PersamaanReferensi,"Persamaan Referensi");
        }else if(FVCPre.getText().trim().equals("")){
            Valid.textKosong(FVCPre,"FVC Pre");
        }else if(FEV1Pre.getText().trim().equals("")){
            Valid.textKosong(FEV1Pre,"FEV1 Pre");
        }else if(RasioPre.getText().trim().equals("")){
            Valid.textKosong(RasioPre,"Rasio Pre");
        }else if(Kesimpulan.getText().trim().equals("")){
            Valid.textKosong(Kesimpulan,"Kesimpulan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19))==true){
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kiriman Dari</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa Klinis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tinggi (cm)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Berat (kg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Etnis</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Persamaan Referensi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Suhu (°C)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tek. Barometrik (mmHg)</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Obat Dikonsumsi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bronkodilator Uji</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FVC Pre</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FVC Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FVC LLN</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FVC %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FVC Z</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FVC Post</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FVC Post %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FVC %Perubahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEV1 Pre</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEV1 Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEV1 LLN</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEV1 %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEV1 Z</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEV1 Post</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEV1 Post %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEV1 %Perubahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasio Pre</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasio Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasio LLN</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasio %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasio Z</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasio Post</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasio Post %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rasio %Perubahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PEF Pre</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PEF Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PEF LLN</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PEF %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PEF Z</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PEF Post</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PEF Post %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>PEF %Perubahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEF25-75 Pre</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEF25-75 Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEF25-75 LLN</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEF25-75 %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEF25-75 Z</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEF25-75 Post</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEF25-75 Post %Pred</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>FEF25-75 %Perubahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Manuver Pre</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Grade FVC Pre</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Grade FEV1 Pre</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Manuver Post</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Grade FVC Post</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Grade FEV1 Post</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Operator</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Catatan Pelaksanaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pola Gangguan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Derajat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Respons Bronkodilator</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesimpulan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Rekomendasi</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='6125px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataHasilPemeriksaanSpirometri.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='6125px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA HASIL PEMERIKSAAN SPIROMETRI<br><br></font>"+        
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
        runBackground(() ->tampil());
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
        runBackground(() ->tampil());
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            runBackground(() ->tampil());
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                isPhoto();
                panggilPhoto();
                getData();
                tampilOrthanc();
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
        if (dokter == null || !dokter.isDisplayable()) {
            dokter=new DlgCariDokter(null,false);
            dokter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dokter.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dokter.getTable().getSelectedRow()!= -1){        
                         KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                         NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }  
                    BtnDokter.requestFocus();
                    dokter=null;
                }
            });
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
        }   
        if (dokter == null) return;
        dokter.isCek();
        if (dokter.isVisible()) {
            dokter.toFront();
            return;
        }
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Edukasi,Hubungan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        //Valid.pindah(evt,Edukasi,Anamnesis);
    }//GEN-LAST:event_TanggalKeyPressed

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
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),4).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString())); 
            
            Valid.MyReportqry("rptCetakHasilPemeriksaanSpirometri.jasper","report","::[ Formulir Hasil Pemeriksaan Spirometri ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_spirometri.tanggal,"+
                "hasil_pemeriksaan_spirometri.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_spirometri.diagnosa_klinis,hasil_pemeriksaan_spirometri.kiriman_dari,"+
                "hasil_pemeriksaan_spirometri.tinggi_badan,hasil_pemeriksaan_spirometri.berat_badan,hasil_pemeriksaan_spirometri.etnis,"+
                "hasil_pemeriksaan_spirometri.persamaan_referensi,hasil_pemeriksaan_spirometri.suhu_ruangan,hasil_pemeriksaan_spirometri.tekanan_barometrik,"+
                "hasil_pemeriksaan_spirometri.obat_dikonsumsi,hasil_pemeriksaan_spirometri.bronkodilator,hasil_pemeriksaan_spirometri.fvc_pre,"+
                "hasil_pemeriksaan_spirometri.fvc_pred,hasil_pemeriksaan_spirometri.fvc_lln,hasil_pemeriksaan_spirometri.fvc_persen,hasil_pemeriksaan_spirometri.fvc_z,"+
                "hasil_pemeriksaan_spirometri.fvc_post,hasil_pemeriksaan_spirometri.fvc_post_persen,hasil_pemeriksaan_spirometri.fvc_perubahan,"+
                "hasil_pemeriksaan_spirometri.fev1_pre,hasil_pemeriksaan_spirometri.fev1_pred,hasil_pemeriksaan_spirometri.fev1_lln,"+
                "hasil_pemeriksaan_spirometri.fev1_persen,hasil_pemeriksaan_spirometri.fev1_z,hasil_pemeriksaan_spirometri.fev1_post,"+
                "hasil_pemeriksaan_spirometri.fev1_post_persen,hasil_pemeriksaan_spirometri.fev1_perubahan,hasil_pemeriksaan_spirometri.rasio_pre,"+
                "hasil_pemeriksaan_spirometri.rasio_pred,hasil_pemeriksaan_spirometri.rasio_lln,hasil_pemeriksaan_spirometri.rasio_persen,"+
                "hasil_pemeriksaan_spirometri.rasio_z,hasil_pemeriksaan_spirometri.rasio_post,hasil_pemeriksaan_spirometri.rasio_post_persen,"+
                "hasil_pemeriksaan_spirometri.rasio_perubahan,hasil_pemeriksaan_spirometri.pef_pre,hasil_pemeriksaan_spirometri.pef_pred,"+
                "hasil_pemeriksaan_spirometri.pef_lln,hasil_pemeriksaan_spirometri.pef_persen,hasil_pemeriksaan_spirometri.pef_z,hasil_pemeriksaan_spirometri.pef_post,"+
                "hasil_pemeriksaan_spirometri.pef_post_persen,hasil_pemeriksaan_spirometri.pef_perubahan,hasil_pemeriksaan_spirometri.fef2575_pre,"+
                "hasil_pemeriksaan_spirometri.fef2575_pred,hasil_pemeriksaan_spirometri.fef2575_lln,hasil_pemeriksaan_spirometri.fef2575_persen,"+
                "hasil_pemeriksaan_spirometri.fef2575_z,hasil_pemeriksaan_spirometri.fef2575_post,hasil_pemeriksaan_spirometri.fef2575_post_persen,"+
                "hasil_pemeriksaan_spirometri.fef2575_perubahan,hasil_pemeriksaan_spirometri.manuver_pre,hasil_pemeriksaan_spirometri.grade_fvc_pre,"+
                "hasil_pemeriksaan_spirometri.grade_fev1_pre,hasil_pemeriksaan_spirometri.manuver_post,hasil_pemeriksaan_spirometri.grade_fvc_post,"+
                "hasil_pemeriksaan_spirometri.grade_fev1_post,hasil_pemeriksaan_spirometri.operator,hasil_pemeriksaan_spirometri.catatan_pelaksanaan,"+
                "hasil_pemeriksaan_spirometri.pola_gangguan,hasil_pemeriksaan_spirometri.derajat,hasil_pemeriksaan_spirometri.respons_bronkodilator,"+
                "hasil_pemeriksaan_spirometri.kesimpulan,"+
                "hasil_pemeriksaan_spirometri.rekomendasi from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join hasil_pemeriksaan_spirometri on reg_periksa.no_rawat=hasil_pemeriksaan_spirometri.no_rawat "+
                "inner join dokter on hasil_pemeriksaan_spirometri.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_spirometri.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnPenilaianMedisActionPerformed

    private void KirimanDariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KirimanDariKeyPressed
       Valid.pindah(evt,BtnDokter,DiagnosaKlinis);
    }//GEN-LAST:event_KirimanDariKeyPressed

    private void DiagnosaKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKlinisKeyPressed
        Valid.pindah(evt,KirimanDari,TinggiBadan);
    }//GEN-LAST:event_DiagnosaKlinisKeyPressed

    private void TinggiBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TinggiBadanKeyPressed
        Valid.pindah(evt,DiagnosaKlinis,BeratBadan);
    }//GEN-LAST:event_TinggiBadanKeyPressed

    private void BeratBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BeratBadanKeyPressed
        Valid.pindah(evt,TinggiBadan,Etnis);
    }//GEN-LAST:event_BeratBadanKeyPressed

    private void EtnisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EtnisKeyPressed
        Valid.pindah(evt,BeratBadan,PersamaanReferensi);
    }//GEN-LAST:event_EtnisKeyPressed

    private void PersamaanReferensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersamaanReferensiKeyPressed
        Valid.pindah(evt,Etnis,SuhuRuangan);
    }//GEN-LAST:event_PersamaanReferensiKeyPressed

    private void SuhuRuanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuRuanganKeyPressed
        Valid.pindah(evt,PersamaanReferensi,TekananBarometrik);
    }//GEN-LAST:event_SuhuRuanganKeyPressed

    private void TekananBarometrikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TekananBarometrikKeyPressed
        Valid.pindah(evt,SuhuRuangan,ObatDikonsumsi);
    }//GEN-LAST:event_TekananBarometrikKeyPressed

    private void ObatDikonsumsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatDikonsumsiKeyPressed
        Valid.pindah(evt,TekananBarometrik,Bronkodilator);
    }//GEN-LAST:event_ObatDikonsumsiKeyPressed

    private void BronkodilatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BronkodilatorKeyPressed
        Valid.pindah(evt,ObatDikonsumsi,FVCPre);
    }//GEN-LAST:event_BronkodilatorKeyPressed

    private void FVCPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FVCPreKeyPressed
        Valid.pindah(evt,Bronkodilator,FVCPred);
    }//GEN-LAST:event_FVCPreKeyPressed

    private void FVCPredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FVCPredKeyPressed
        Valid.pindah(evt,FVCPre,FVCLLN);
    }//GEN-LAST:event_FVCPredKeyPressed

    private void FVCLLNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FVCLLNKeyPressed
        Valid.pindah(evt,FVCPred,FVCPersen);
    }//GEN-LAST:event_FVCLLNKeyPressed

    private void FVCPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FVCPersenKeyPressed
        Valid.pindah(evt,FVCLLN,FVCZ);
    }//GEN-LAST:event_FVCPersenKeyPressed

    private void FVCZKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FVCZKeyPressed
        Valid.pindah(evt,FVCPersen,FVCPost);
    }//GEN-LAST:event_FVCZKeyPressed

    private void FVCPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FVCPostKeyPressed
        Valid.pindah(evt,FVCZ,FVCPostPersen);
    }//GEN-LAST:event_FVCPostKeyPressed

    private void FVCPostPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FVCPostPersenKeyPressed
        Valid.pindah(evt,FVCPost,FVCPerubahan);
    }//GEN-LAST:event_FVCPostPersenKeyPressed

    private void FVCPerubahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FVCPerubahanKeyPressed
        Valid.pindah(evt,FVCPostPersen,FEV1Pre);
    }//GEN-LAST:event_FVCPerubahanKeyPressed

    private void FEV1PreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEV1PreKeyPressed
        Valid.pindah(evt,FVCPerubahan,FEV1Pred);
    }//GEN-LAST:event_FEV1PreKeyPressed

    private void FEV1PredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEV1PredKeyPressed
        Valid.pindah(evt,FEV1Pre,FEV1LLN);
    }//GEN-LAST:event_FEV1PredKeyPressed

    private void FEV1LLNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEV1LLNKeyPressed
        Valid.pindah(evt,FEV1Pred,FEV1Persen);
    }//GEN-LAST:event_FEV1LLNKeyPressed

    private void FEV1PersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEV1PersenKeyPressed
        Valid.pindah(evt,FEV1LLN,FEV1Z);
    }//GEN-LAST:event_FEV1PersenKeyPressed

    private void FEV1ZKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEV1ZKeyPressed
        Valid.pindah(evt,FEV1Persen,FEV1Post);
    }//GEN-LAST:event_FEV1ZKeyPressed

    private void FEV1PostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEV1PostKeyPressed
        Valid.pindah(evt,FEV1Z,FEV1PostPersen);
    }//GEN-LAST:event_FEV1PostKeyPressed

    private void FEV1PostPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEV1PostPersenKeyPressed
        Valid.pindah(evt,FEV1Post,FEV1Perubahan);
    }//GEN-LAST:event_FEV1PostPersenKeyPressed

    private void FEV1PerubahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEV1PerubahanKeyPressed
        Valid.pindah(evt,FEV1PostPersen,RasioPre);
    }//GEN-LAST:event_FEV1PerubahanKeyPressed

    private void RasioPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasioPreKeyPressed
        Valid.pindah(evt,FEV1Perubahan,RasioPred);
    }//GEN-LAST:event_RasioPreKeyPressed

    private void RasioPredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasioPredKeyPressed
        Valid.pindah(evt,RasioPre,RasioLLN);
    }//GEN-LAST:event_RasioPredKeyPressed

    private void RasioLLNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasioLLNKeyPressed
        Valid.pindah(evt,RasioPred,RasioPersen);
    }//GEN-LAST:event_RasioLLNKeyPressed

    private void RasioPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasioPersenKeyPressed
        Valid.pindah(evt,RasioLLN,RasioZ);
    }//GEN-LAST:event_RasioPersenKeyPressed

    private void RasioZKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasioZKeyPressed
        Valid.pindah(evt,RasioPersen,RasioPost);
    }//GEN-LAST:event_RasioZKeyPressed

    private void RasioPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasioPostKeyPressed
        Valid.pindah(evt,RasioZ,RasioPostPersen);
    }//GEN-LAST:event_RasioPostKeyPressed

    private void RasioPostPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasioPostPersenKeyPressed
        Valid.pindah(evt,RasioPost,RasioPerubahan);
    }//GEN-LAST:event_RasioPostPersenKeyPressed

    private void RasioPerubahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RasioPerubahanKeyPressed
        Valid.pindah(evt,RasioPostPersen,PEFPre);
    }//GEN-LAST:event_RasioPerubahanKeyPressed

    private void PEFPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PEFPreKeyPressed
        Valid.pindah(evt,RasioPerubahan,PEFPred);
    }//GEN-LAST:event_PEFPreKeyPressed

    private void PEFPredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PEFPredKeyPressed
        Valid.pindah(evt,PEFPre,PEFLLN);
    }//GEN-LAST:event_PEFPredKeyPressed

    private void PEFLLNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PEFLLNKeyPressed
        Valid.pindah(evt,PEFPred,PEFPersen);
    }//GEN-LAST:event_PEFLLNKeyPressed

    private void PEFPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PEFPersenKeyPressed
        Valid.pindah(evt,PEFLLN,PEFZ);
    }//GEN-LAST:event_PEFPersenKeyPressed

    private void PEFZKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PEFZKeyPressed
        Valid.pindah(evt,PEFPersen,PEFPost);
    }//GEN-LAST:event_PEFZKeyPressed

    private void PEFPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PEFPostKeyPressed
        Valid.pindah(evt,PEFZ,PEFPostPersen);
    }//GEN-LAST:event_PEFPostKeyPressed

    private void PEFPostPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PEFPostPersenKeyPressed
        Valid.pindah(evt,PEFPost,PEFPerubahan);
    }//GEN-LAST:event_PEFPostPersenKeyPressed

    private void PEFPerubahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PEFPerubahanKeyPressed
        Valid.pindah(evt,PEFPostPersen,FEF2575Pre);
    }//GEN-LAST:event_PEFPerubahanKeyPressed

    private void FEF2575PreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEF2575PreKeyPressed
        Valid.pindah(evt,PEFPerubahan,FEF2575Pred);
    }//GEN-LAST:event_FEF2575PreKeyPressed

    private void FEF2575PredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEF2575PredKeyPressed
        Valid.pindah(evt,FEF2575Pre,FEF2575LLN);
    }//GEN-LAST:event_FEF2575PredKeyPressed

    private void FEF2575LLNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEF2575LLNKeyPressed
        Valid.pindah(evt,FEF2575Pred,FEF2575Persen);
    }//GEN-LAST:event_FEF2575LLNKeyPressed

    private void FEF2575PersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEF2575PersenKeyPressed
        Valid.pindah(evt,FEF2575LLN,FEF2575Z);
    }//GEN-LAST:event_FEF2575PersenKeyPressed

    private void FEF2575ZKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEF2575ZKeyPressed
        Valid.pindah(evt,FEF2575Persen,FEF2575Post);
    }//GEN-LAST:event_FEF2575ZKeyPressed

    private void FEF2575PostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEF2575PostKeyPressed
        Valid.pindah(evt,FEF2575Z,FEF2575PostPersen);
    }//GEN-LAST:event_FEF2575PostKeyPressed

    private void FEF2575PostPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEF2575PostPersenKeyPressed
        Valid.pindah(evt,FEF2575Post,FEF2575Perubahan);
    }//GEN-LAST:event_FEF2575PostPersenKeyPressed

    private void FEF2575PerubahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FEF2575PerubahanKeyPressed
        Valid.pindah(evt,FEF2575PostPersen,ManuverPre);
    }//GEN-LAST:event_FEF2575PerubahanKeyPressed

    private void ManuverPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManuverPreKeyPressed
        Valid.pindah(evt,FEF2575Perubahan,GradeFVCPre);
    }//GEN-LAST:event_ManuverPreKeyPressed

    private void GradeFVCPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GradeFVCPreKeyPressed
        Valid.pindah(evt,ManuverPre,GradeFEV1Pre);
    }//GEN-LAST:event_GradeFVCPreKeyPressed

    private void GradeFEV1PreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GradeFEV1PreKeyPressed
        Valid.pindah(evt,GradeFVCPre,ManuverPost);
    }//GEN-LAST:event_GradeFEV1PreKeyPressed

    private void ManuverPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManuverPostKeyPressed
        Valid.pindah(evt,GradeFEV1Pre,GradeFVCPost);
    }//GEN-LAST:event_ManuverPostKeyPressed

    private void GradeFVCPostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GradeFVCPostKeyPressed
        Valid.pindah(evt,ManuverPost,GradeFEV1Post);
    }//GEN-LAST:event_GradeFVCPostKeyPressed

    private void GradeFEV1PostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GradeFEV1PostKeyPressed
        Valid.pindah(evt,GradeFVCPost,Operator);
    }//GEN-LAST:event_GradeFEV1PostKeyPressed

    private void OperatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OperatorKeyPressed
        Valid.pindah(evt,GradeFEV1Post,CatatanPelaksanaan);
    }//GEN-LAST:event_OperatorKeyPressed

    private void CatatanPelaksanaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanPelaksanaanKeyPressed
        Valid.pindah2(evt,Operator,PolaGangguan);
    }//GEN-LAST:event_CatatanPelaksanaanKeyPressed

    private void PolaGangguanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PolaGangguanKeyPressed
        Valid.pindah(evt,CatatanPelaksanaan,Derajat);
    }//GEN-LAST:event_PolaGangguanKeyPressed

    private void DerajatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DerajatKeyPressed
        Valid.pindah(evt,PolaGangguan,ResponsBronkodilator);
    }//GEN-LAST:event_DerajatKeyPressed

    private void ResponsBronkodilatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResponsBronkodilatorKeyPressed
        Valid.pindah(evt,Derajat,Kesimpulan);
    }//GEN-LAST:event_ResponsBronkodilatorKeyPressed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah2(evt,ResponsBronkodilator,Rekomendasi);
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void RekomendasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RekomendasiKeyPressed
        Valid.pindah(evt,Kesimpulan,BtnSimpan);
    }//GEN-LAST:event_RekomendasiKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Rawat..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.panggilUrl("hasilpemeriksaanspirometri/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                this.setCursor(Cursor.getDefaultCursor()); 
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Rawat terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Rawat terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void btnDicomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicomActionPerformed
        if(tabModeDicom.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else {
            if(tbListDicom.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                OrthancDICOM orthan=new OrthancDICOM(null,false);
                orthan.setJudul("::[ DICOM Orthanc Pasien "+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+" "+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()+", Series "+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString()+" ]::",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString().replaceAll("/","")+"_"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"_"+tbObat.getValueAt(tbObat.getSelectedRow(),2).toString().replaceAll(" ","_").replaceAll("/",""),tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString(),tbListDicom.getValueAt(tbListDicom.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                try {
                    orthan.loadURL(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/web-viewer/app/viewer.html?series="+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString());
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }
                orthan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                orthan.setLocationRelativeTo(internalFrame1);
                orthan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
            }
        }
    }//GEN-LAST:event_btnDicomActionPerformed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        tampilOrthanc();
    }//GEN-LAST:event_TabDataMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(() ->tampil());
                    }
                }
            });
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnUploudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploudActionPerformed
        if(tabModeDicom.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else {
            if(tbListDicom.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.queryu2("delete from hasil_pemeriksaan_spirometri_gambar where no_rawat=?",1,new String[]{tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()});
                ApiOrthanc orthanc=new ApiOrthanc();
                orthanc.AmbilJpg2(tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString());
                try {
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    HttpPost post = new HttpPost("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanspirometri/pages/upload/service.php");
                    System.out.println("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanspirometri/pages/upload/service.php");
                    post.setHeader("Content-Type", "application/json");
                    post.addHeader("username", koneksiDB.USERHYBRIDWEB());
                    post.addHeader("password", koneksiDB.PASHYBRIDWEB());
                    File f = new File("./gambarradiologi/"+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString()+".jpg");
                    byte[] fileContent = Files.readAllBytes(f.toPath());
                    String json="{" +
                                    "\"file\":\""+Base64.getEncoder().encodeToString(fileContent)+"\"," +
                                    "\"namafile\":\""+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString()+".jpg\"," +
                                    "\"norawat\":\""+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"\"" +
                                "}";
                    post.setEntity(new StringEntity(json));
                    try (CloseableHttpResponse response = httpClient.execute(post)) {
                        json=EntityUtils.toString(response.getEntity());
                        root = mapper.readTree(json);
                        JOptionPane.showMessageDialog(null,root.path("metadata").path("message").asText());
                    } catch (IOException a) {
                        System.out.println("Notifikasi : " + a);
                        JOptionPane.showMessageDialog(null,""+a);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                    JOptionPane.showMessageDialog(null,""+e);
                }
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
            }
        }
    }//GEN-LAST:event_btnUploudActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMHasilPemeriksaanSpirometri dialog = new RMHasilPemeriksaanSpirometri(new javax.swing.JFrame(), true);
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
    private widget.TextBox BeratBadan;
    private widget.TextBox Bronkodilator;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.TextArea CatatanPelaksanaan;
    private widget.CekBox ChkAccor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Derajat;
    private widget.TextBox DiagnosaKlinis;
    private widget.ComboBox Etnis;
    private widget.TextBox FEF2575LLN;
    private widget.TextBox FEF2575Persen;
    private widget.TextBox FEF2575Perubahan;
    private widget.TextBox FEF2575Post;
    private widget.TextBox FEF2575PostPersen;
    private widget.TextBox FEF2575Pre;
    private widget.TextBox FEF2575Pred;
    private widget.TextBox FEF2575Z;
    private widget.TextBox FEV1LLN;
    private widget.TextBox FEV1Persen;
    private widget.TextBox FEV1Perubahan;
    private widget.TextBox FEV1Post;
    private widget.TextBox FEV1PostPersen;
    private widget.TextBox FEV1Pre;
    private widget.TextBox FEV1Pred;
    private widget.TextBox FEV1Z;
    private widget.TextBox FVCLLN;
    private widget.TextBox FVCPersen;
    private widget.TextBox FVCPerubahan;
    private widget.TextBox FVCPost;
    private widget.TextBox FVCPostPersen;
    private widget.TextBox FVCPre;
    private widget.TextBox FVCPred;
    private widget.TextBox FVCZ;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormOrthan;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox GradeFEV1Post;
    private widget.ComboBox GradeFEV1Pre;
    private widget.ComboBox GradeFVCPost;
    private widget.ComboBox GradeFVCPre;
    private widget.TextBox KdDokter;
    private widget.TextArea Kesimpulan;
    private widget.TextBox KirimanDari;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.TextBox ManuverPost;
    private widget.TextBox ManuverPre;
    private widget.MenuItem MnPenilaianMedis;
    private widget.TextBox NmDokter;
    private widget.TextBox ObatDikonsumsi;
    private widget.TextBox Operator;
    private widget.TextBox PEFLLN;
    private widget.TextBox PEFPersen;
    private widget.TextBox PEFPerubahan;
    private widget.TextBox PEFPost;
    private widget.TextBox PEFPostPersen;
    private widget.TextBox PEFPre;
    private widget.TextBox PEFPred;
    private widget.TextBox PEFZ;
    private widget.PanelBiasa PanelAccor;
    private widget.TextBox PersamaanReferensi;
    private widget.ComboBox PolaGangguan;
    private widget.TextBox RasioLLN;
    private widget.TextBox RasioPersen;
    private widget.TextBox RasioPerubahan;
    private widget.TextBox RasioPost;
    private widget.TextBox RasioPostPersen;
    private widget.TextBox RasioPre;
    private widget.TextBox RasioPred;
    private widget.TextBox RasioZ;
    private widget.TextBox Rekomendasi;
    private widget.ComboBox ResponsBronkodilator;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox SuhuRuangan;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TabPane TabData;
    private widget.TabPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TekananBarometrik;
    private widget.TextBox TglLahir;
    private widget.TextBox TinggiBadan;
    private widget.Button btnAmbil;
    private widget.Button btnDicom;
    private widget.Button btnUploud;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel30;
    private widget.Label jLabel32;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel_BeratBadan;
    private widget.Label jLabel_Bronkodilator;
    private widget.Label jLabel_CatatanPelaksanaan;
    private widget.Label jLabel_Derajat;
    private widget.Label jLabel_Etnis;
    private widget.Label jLabel_GradeFEV1Post;
    private widget.Label jLabel_GradeFEV1Pre;
    private widget.Label jLabel_GradeFVCPost;
    private widget.Label jLabel_GradeFVCPre;
    private widget.Label jLabel_GrupPost;
    private widget.Label jLabel_GrupPre;
    private widget.Label jLabel_Kesimpulan;
    private widget.Label jLabel_KolLLN;
    private widget.Label jLabel_KolPersen;
    private widget.Label jLabel_KolPerubahan;
    private widget.Label jLabel_KolPost;
    private widget.Label jLabel_KolPostPersen;
    private widget.Label jLabel_KolPre;
    private widget.Label jLabel_KolPred;
    private widget.Label jLabel_KolZ;
    private widget.Label jLabel_ManuverPost;
    private widget.Label jLabel_ManuverPre;
    private widget.Label jLabel_ObatDikonsumsi;
    private widget.Label jLabel_Operator;
    private widget.Label jLabel_ParFEF2575;
    private widget.Label jLabel_ParFEV1;
    private widget.Label jLabel_ParFVC;
    private widget.Label jLabel_ParPEF;
    private widget.Label jLabel_ParRasio;
    private widget.Label jLabel_PersamaanReferensi;
    private widget.Label jLabel_PolaGangguan;
    private widget.Label jLabel_Rekomendasi;
    private widget.Label jLabel_ResponsBronkodilator;
    private widget.Label jLabel_SuhuRuangan;
    private widget.Label jLabel_TekananBarometrik;
    private widget.Label jLabel_TinggiBadan;
    private widget.PopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane_CatatanPelaksanaan;
    private widget.ScrollPane scrollPane_Kesimpulan;
    private widget.Table tbListDicom;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_spirometri.tanggal,"+
                        "hasil_pemeriksaan_spirometri.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_spirometri.diagnosa_klinis,hasil_pemeriksaan_spirometri.kiriman_dari,"+
                        "hasil_pemeriksaan_spirometri.tinggi_badan,hasil_pemeriksaan_spirometri.berat_badan,hasil_pemeriksaan_spirometri.etnis,"+
                        "hasil_pemeriksaan_spirometri.persamaan_referensi,hasil_pemeriksaan_spirometri.suhu_ruangan,hasil_pemeriksaan_spirometri.tekanan_barometrik,"+
                        "hasil_pemeriksaan_spirometri.obat_dikonsumsi,hasil_pemeriksaan_spirometri.bronkodilator,hasil_pemeriksaan_spirometri.fvc_pre,"+
                        "hasil_pemeriksaan_spirometri.fvc_pred,hasil_pemeriksaan_spirometri.fvc_lln,hasil_pemeriksaan_spirometri.fvc_persen,hasil_pemeriksaan_spirometri.fvc_z,"+
                        "hasil_pemeriksaan_spirometri.fvc_post,hasil_pemeriksaan_spirometri.fvc_post_persen,hasil_pemeriksaan_spirometri.fvc_perubahan,"+
                        "hasil_pemeriksaan_spirometri.fev1_pre,hasil_pemeriksaan_spirometri.fev1_pred,hasil_pemeriksaan_spirometri.fev1_lln,"+
                        "hasil_pemeriksaan_spirometri.fev1_persen,hasil_pemeriksaan_spirometri.fev1_z,hasil_pemeriksaan_spirometri.fev1_post,"+
                        "hasil_pemeriksaan_spirometri.fev1_post_persen,hasil_pemeriksaan_spirometri.fev1_perubahan,hasil_pemeriksaan_spirometri.rasio_pre,"+
                        "hasil_pemeriksaan_spirometri.rasio_pred,hasil_pemeriksaan_spirometri.rasio_lln,hasil_pemeriksaan_spirometri.rasio_persen,"+
                        "hasil_pemeriksaan_spirometri.rasio_z,hasil_pemeriksaan_spirometri.rasio_post,hasil_pemeriksaan_spirometri.rasio_post_persen,"+
                        "hasil_pemeriksaan_spirometri.rasio_perubahan,hasil_pemeriksaan_spirometri.pef_pre,hasil_pemeriksaan_spirometri.pef_pred,"+
                        "hasil_pemeriksaan_spirometri.pef_lln,hasil_pemeriksaan_spirometri.pef_persen,hasil_pemeriksaan_spirometri.pef_z,hasil_pemeriksaan_spirometri.pef_post,"+
                        "hasil_pemeriksaan_spirometri.pef_post_persen,hasil_pemeriksaan_spirometri.pef_perubahan,hasil_pemeriksaan_spirometri.fef2575_pre,"+
                        "hasil_pemeriksaan_spirometri.fef2575_pred,hasil_pemeriksaan_spirometri.fef2575_lln,hasil_pemeriksaan_spirometri.fef2575_persen,"+
                        "hasil_pemeriksaan_spirometri.fef2575_z,hasil_pemeriksaan_spirometri.fef2575_post,hasil_pemeriksaan_spirometri.fef2575_post_persen,"+
                        "hasil_pemeriksaan_spirometri.fef2575_perubahan,hasil_pemeriksaan_spirometri.manuver_pre,hasil_pemeriksaan_spirometri.grade_fvc_pre,"+
                        "hasil_pemeriksaan_spirometri.grade_fev1_pre,hasil_pemeriksaan_spirometri.manuver_post,hasil_pemeriksaan_spirometri.grade_fvc_post,"+
                        "hasil_pemeriksaan_spirometri.grade_fev1_post,hasil_pemeriksaan_spirometri.operator,hasil_pemeriksaan_spirometri.catatan_pelaksanaan,"+
                        "hasil_pemeriksaan_spirometri.pola_gangguan,hasil_pemeriksaan_spirometri.derajat,hasil_pemeriksaan_spirometri.respons_bronkodilator,"+
                        "hasil_pemeriksaan_spirometri.kesimpulan,"+
                        "hasil_pemeriksaan_spirometri.rekomendasi from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_pemeriksaan_spirometri on reg_periksa.no_rawat=hasil_pemeriksaan_spirometri.no_rawat "+
                        "inner join dokter on hasil_pemeriksaan_spirometri.kd_dokter=dokter.kd_dokter where "+
                        "hasil_pemeriksaan_spirometri.tanggal between ? and ? order by hasil_pemeriksaan_spirometri.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,hasil_pemeriksaan_spirometri.tanggal,"+
                        "hasil_pemeriksaan_spirometri.kd_dokter,dokter.nm_dokter,hasil_pemeriksaan_spirometri.diagnosa_klinis,hasil_pemeriksaan_spirometri.kiriman_dari,"+
                        "hasil_pemeriksaan_spirometri.tinggi_badan,hasil_pemeriksaan_spirometri.berat_badan,hasil_pemeriksaan_spirometri.etnis,"+
                        "hasil_pemeriksaan_spirometri.persamaan_referensi,hasil_pemeriksaan_spirometri.suhu_ruangan,hasil_pemeriksaan_spirometri.tekanan_barometrik,"+
                        "hasil_pemeriksaan_spirometri.obat_dikonsumsi,hasil_pemeriksaan_spirometri.bronkodilator,hasil_pemeriksaan_spirometri.fvc_pre,"+
                        "hasil_pemeriksaan_spirometri.fvc_pred,hasil_pemeriksaan_spirometri.fvc_lln,hasil_pemeriksaan_spirometri.fvc_persen,hasil_pemeriksaan_spirometri.fvc_z,"+
                        "hasil_pemeriksaan_spirometri.fvc_post,hasil_pemeriksaan_spirometri.fvc_post_persen,hasil_pemeriksaan_spirometri.fvc_perubahan,"+
                        "hasil_pemeriksaan_spirometri.fev1_pre,hasil_pemeriksaan_spirometri.fev1_pred,hasil_pemeriksaan_spirometri.fev1_lln,"+
                        "hasil_pemeriksaan_spirometri.fev1_persen,hasil_pemeriksaan_spirometri.fev1_z,hasil_pemeriksaan_spirometri.fev1_post,"+
                        "hasil_pemeriksaan_spirometri.fev1_post_persen,hasil_pemeriksaan_spirometri.fev1_perubahan,hasil_pemeriksaan_spirometri.rasio_pre,"+
                        "hasil_pemeriksaan_spirometri.rasio_pred,hasil_pemeriksaan_spirometri.rasio_lln,hasil_pemeriksaan_spirometri.rasio_persen,"+
                        "hasil_pemeriksaan_spirometri.rasio_z,hasil_pemeriksaan_spirometri.rasio_post,hasil_pemeriksaan_spirometri.rasio_post_persen,"+
                        "hasil_pemeriksaan_spirometri.rasio_perubahan,hasil_pemeriksaan_spirometri.pef_pre,hasil_pemeriksaan_spirometri.pef_pred,"+
                        "hasil_pemeriksaan_spirometri.pef_lln,hasil_pemeriksaan_spirometri.pef_persen,hasil_pemeriksaan_spirometri.pef_z,hasil_pemeriksaan_spirometri.pef_post,"+
                        "hasil_pemeriksaan_spirometri.pef_post_persen,hasil_pemeriksaan_spirometri.pef_perubahan,hasil_pemeriksaan_spirometri.fef2575_pre,"+
                        "hasil_pemeriksaan_spirometri.fef2575_pred,hasil_pemeriksaan_spirometri.fef2575_lln,hasil_pemeriksaan_spirometri.fef2575_persen,"+
                        "hasil_pemeriksaan_spirometri.fef2575_z,hasil_pemeriksaan_spirometri.fef2575_post,hasil_pemeriksaan_spirometri.fef2575_post_persen,"+
                        "hasil_pemeriksaan_spirometri.fef2575_perubahan,hasil_pemeriksaan_spirometri.manuver_pre,hasil_pemeriksaan_spirometri.grade_fvc_pre,"+
                        "hasil_pemeriksaan_spirometri.grade_fev1_pre,hasil_pemeriksaan_spirometri.manuver_post,hasil_pemeriksaan_spirometri.grade_fvc_post,"+
                        "hasil_pemeriksaan_spirometri.grade_fev1_post,hasil_pemeriksaan_spirometri.operator,hasil_pemeriksaan_spirometri.catatan_pelaksanaan,"+
                        "hasil_pemeriksaan_spirometri.pola_gangguan,hasil_pemeriksaan_spirometri.derajat,hasil_pemeriksaan_spirometri.respons_bronkodilator,"+
                        "hasil_pemeriksaan_spirometri.kesimpulan,"+
                        "hasil_pemeriksaan_spirometri.rekomendasi from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_pemeriksaan_spirometri on reg_periksa.no_rawat=hasil_pemeriksaan_spirometri.no_rawat "+
                        "inner join dokter on hasil_pemeriksaan_spirometri.kd_dokter=dokter.kd_dokter where "+
                        "hasil_pemeriksaan_spirometri.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "hasil_pemeriksaan_spirometri.kd_dokter like ? or dokter.nm_dokter like ?) order by hasil_pemeriksaan_spirometri.tanggal");
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
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("tanggal"),
                        rs.getString("kiriman_dari"),rs.getString("diagnosa_klinis"),rs.getString("tinggi_badan"),rs.getString("berat_badan"),rs.getString("etnis"),rs.getString("persamaan_referensi"),rs.getString("suhu_ruangan"),rs.getString("tekanan_barometrik"),rs.getString("obat_dikonsumsi"),rs.getString("bronkodilator"),rs.getString("fvc_pre"),rs.getString("fvc_pred"),rs.getString("fvc_lln"),rs.getString("fvc_persen"),rs.getString("fvc_z"),rs.getString("fvc_post"),rs.getString("fvc_post_persen"),rs.getString("fvc_perubahan"),rs.getString("fev1_pre"),rs.getString("fev1_pred"),rs.getString("fev1_lln"),rs.getString("fev1_persen"),rs.getString("fev1_z"),rs.getString("fev1_post"),rs.getString("fev1_post_persen"),rs.getString("fev1_perubahan"),rs.getString("rasio_pre"),rs.getString("rasio_pred"),rs.getString("rasio_lln"),rs.getString("rasio_persen"),rs.getString("rasio_z"),rs.getString("rasio_post"),rs.getString("rasio_post_persen"),rs.getString("rasio_perubahan"),rs.getString("pef_pre"),rs.getString("pef_pred"),rs.getString("pef_lln"),rs.getString("pef_persen"),rs.getString("pef_z"),rs.getString("pef_post"),rs.getString("pef_post_persen"),rs.getString("pef_perubahan"),rs.getString("fef2575_pre"),rs.getString("fef2575_pred"),rs.getString("fef2575_lln"),rs.getString("fef2575_persen"),rs.getString("fef2575_z"),rs.getString("fef2575_post"),rs.getString("fef2575_post_persen"),rs.getString("fef2575_perubahan"),rs.getString("manuver_pre"),rs.getString("grade_fvc_pre"),rs.getString("grade_fev1_pre"),rs.getString("manuver_post"),rs.getString("grade_fvc_post"),rs.getString("grade_fev1_post"),rs.getString("operator"),rs.getString("catatan_pelaksanaan"),rs.getString("pola_gangguan"),rs.getString("derajat"),rs.getString("respons_bronkodilator"),rs.getString("kesimpulan"),rs.getString("rekomendasi")
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
        KirimanDari.setText("");
        DiagnosaKlinis.setText("");
        TinggiBadan.setText("");
        BeratBadan.setText("");
        Etnis.setSelectedIndex(0);
        PersamaanReferensi.setText("");
        SuhuRuangan.setText("");
        TekananBarometrik.setText("");
        ObatDikonsumsi.setText("");
        Bronkodilator.setText("");
        FVCPre.setText("");
        FVCPred.setText("");
        FVCLLN.setText("");
        FVCPersen.setText("");
        FVCZ.setText("");
        FVCPost.setText("");
        FVCPostPersen.setText("");
        FVCPerubahan.setText("");
        FEV1Pre.setText("");
        FEV1Pred.setText("");
        FEV1LLN.setText("");
        FEV1Persen.setText("");
        FEV1Z.setText("");
        FEV1Post.setText("");
        FEV1PostPersen.setText("");
        FEV1Perubahan.setText("");
        RasioPre.setText("");
        RasioPred.setText("");
        RasioLLN.setText("");
        RasioPersen.setText("");
        RasioZ.setText("");
        RasioPost.setText("");
        RasioPostPersen.setText("");
        RasioPerubahan.setText("");
        PEFPre.setText("");
        PEFPred.setText("");
        PEFLLN.setText("");
        PEFPersen.setText("");
        PEFZ.setText("");
        PEFPost.setText("");
        PEFPostPersen.setText("");
        PEFPerubahan.setText("");
        FEF2575Pre.setText("");
        FEF2575Pred.setText("");
        FEF2575LLN.setText("");
        FEF2575Persen.setText("");
        FEF2575Z.setText("");
        FEF2575Post.setText("");
        FEF2575PostPersen.setText("");
        FEF2575Perubahan.setText("");
        ManuverPre.setText("");
        GradeFVCPre.setSelectedIndex(0);
        GradeFEV1Pre.setSelectedIndex(0);
        ManuverPost.setText("");
        GradeFVCPost.setSelectedIndex(0);
        GradeFEV1Post.setSelectedIndex(0);
        Operator.setText("");
        CatatanPelaksanaan.setText("");
        PolaGangguan.setSelectedIndex(0);
        Derajat.setSelectedIndex(0);
        ResponsBronkodilator.setSelectedIndex(0);
        Kesimpulan.setText("");
        Rekomendasi.setText("");
        Tanggal.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        KirimanDari.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            KirimanDari.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            DiagnosaKlinis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            TinggiBadan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            BeratBadan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Etnis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            PersamaanReferensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            SuhuRuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            TekananBarometrik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            ObatDikonsumsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Bronkodilator.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            FVCPre.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            FVCPred.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            FVCLLN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            FVCPersen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            FVCZ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            FVCPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            FVCPostPersen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            FVCPerubahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            FEV1Pre.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            FEV1Pred.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            FEV1LLN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            FEV1Persen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            FEV1Z.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            FEV1Post.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            FEV1PostPersen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            FEV1Perubahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            RasioPre.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            RasioPred.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            RasioLLN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            RasioPersen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            RasioZ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            RasioPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            RasioPostPersen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            RasioPerubahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            PEFPre.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            PEFPred.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            PEFLLN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            PEFPersen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            PEFZ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            PEFPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            PEFPostPersen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            PEFPerubahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            FEF2575Pre.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            FEF2575Pred.setText(tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            FEF2575LLN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            FEF2575Persen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            FEF2575Z.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            FEF2575Post.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            FEF2575PostPersen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            FEF2575Perubahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            ManuverPre.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            GradeFVCPre.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            GradeFEV1Pre.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            ManuverPost.setText(tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            GradeFVCPost.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            GradeFEV1Post.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            Operator.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            CatatanPelaksanaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            PolaGangguan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Derajat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            ResponsBronkodilator.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            Kesimpulan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            Rekomendasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
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
        runBackground(() ->tampil());
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gethasil_spirometri());
        BtnHapus.setEnabled(akses.gethasil_spirometri());
        BtnEdit.setEnabled(akses.gethasil_spirometri());
        BtnPrint.setEnabled(akses.gethasil_spirometri());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }    

        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
            }
        }
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from hasil_pemeriksaan_spirometri where no_rawat=?",1,new String[]{
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
        if(Sequel.mengedittf("hasil_pemeriksaan_spirometri","no_rawat=?","no_rawat=?,tanggal=?,kd_dokter=?,diagnosa_klinis=?,kiriman_dari=?,tinggi_badan=?,berat_badan=?,etnis=?,persamaan_referensi=?,suhu_ruangan=?,tekanan_barometrik=?,obat_dikonsumsi=?,bronkodilator=?,fvc_pre=?,fvc_pred=?,fvc_lln=?,fvc_persen=?,fvc_z=?,fvc_post=?,fvc_post_persen=?,fvc_perubahan=?,fev1_pre=?,fev1_pred=?,fev1_lln=?,fev1_persen=?,fev1_z=?,fev1_post=?,fev1_post_persen=?,fev1_perubahan=?,rasio_pre=?,rasio_pred=?,rasio_lln=?,rasio_persen=?,rasio_z=?,rasio_post=?,rasio_post_persen=?,rasio_perubahan=?,pef_pre=?,pef_pred=?,pef_lln=?,pef_persen=?,pef_z=?,pef_post=?,pef_post_persen=?,pef_perubahan=?,fef2575_pre=?,fef2575_pred=?,fef2575_lln=?,fef2575_persen=?,fef2575_z=?,fef2575_post=?,fef2575_post_persen=?,fef2575_perubahan=?,manuver_pre=?,grade_fvc_pre=?,grade_fev1_pre=?,manuver_post=?,grade_fvc_post=?,grade_fev1_post=?,operator=?,catatan_pelaksanaan=?,pola_gangguan=?,derajat=?,respons_bronkodilator=?,kesimpulan=?,rekomendasi=?",67,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                DiagnosaKlinis.getText(),KirimanDari.getText(),TinggiBadan.getText(),BeratBadan.getText(),Etnis.getSelectedItem().toString(),PersamaanReferensi.getText(),SuhuRuangan.getText(),TekananBarometrik.getText(),ObatDikonsumsi.getText(),Bronkodilator.getText(),FVCPre.getText(),FVCPred.getText(),FVCLLN.getText(),FVCPersen.getText(),FVCZ.getText(),FVCPost.getText(),FVCPostPersen.getText(),FVCPerubahan.getText(),FEV1Pre.getText(),FEV1Pred.getText(),FEV1LLN.getText(),FEV1Persen.getText(),FEV1Z.getText(),FEV1Post.getText(),FEV1PostPersen.getText(),FEV1Perubahan.getText(),RasioPre.getText(),RasioPred.getText(),RasioLLN.getText(),RasioPersen.getText(),RasioZ.getText(),RasioPost.getText(),RasioPostPersen.getText(),RasioPerubahan.getText(),PEFPre.getText(),PEFPred.getText(),PEFLLN.getText(),PEFPersen.getText(),PEFZ.getText(),PEFPost.getText(),PEFPostPersen.getText(),PEFPerubahan.getText(),FEF2575Pre.getText(),FEF2575Pred.getText(),FEF2575LLN.getText(),FEF2575Persen.getText(),FEF2575Z.getText(),FEF2575Post.getText(),FEF2575PostPersen.getText(),FEF2575Perubahan.getText(),ManuverPre.getText(),GradeFVCPre.getSelectedItem().toString(),GradeFEV1Pre.getSelectedItem().toString(),ManuverPost.getText(),GradeFVCPost.getSelectedItem().toString(),GradeFEV1Post.getSelectedItem().toString(),Operator.getText(),CatatanPelaksanaan.getText(),PolaGangguan.getSelectedItem().toString(),Derajat.getSelectedItem().toString(),ResponsBronkodilator.getSelectedItem().toString(),Kesimpulan.getText(),Rekomendasi.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
                tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),4);
                tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),5);
                tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),6);
                tbObat.setValueAt(KirimanDari.getText(),tbObat.getSelectedRow(),7);
                tbObat.setValueAt(DiagnosaKlinis.getText(),tbObat.getSelectedRow(),8);
                tbObat.setValueAt(TinggiBadan.getText(),tbObat.getSelectedRow(),9);
                tbObat.setValueAt(BeratBadan.getText(),tbObat.getSelectedRow(),10);
                tbObat.setValueAt(Etnis.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
                tbObat.setValueAt(PersamaanReferensi.getText(),tbObat.getSelectedRow(),12);
                tbObat.setValueAt(SuhuRuangan.getText(),tbObat.getSelectedRow(),13);
                tbObat.setValueAt(TekananBarometrik.getText(),tbObat.getSelectedRow(),14);
                tbObat.setValueAt(ObatDikonsumsi.getText(),tbObat.getSelectedRow(),15);
                tbObat.setValueAt(Bronkodilator.getText(),tbObat.getSelectedRow(),16);
                tbObat.setValueAt(FVCPre.getText(),tbObat.getSelectedRow(),17);
                tbObat.setValueAt(FVCPred.getText(),tbObat.getSelectedRow(),18);
                tbObat.setValueAt(FVCLLN.getText(),tbObat.getSelectedRow(),19);
                tbObat.setValueAt(FVCPersen.getText(),tbObat.getSelectedRow(),20);
                tbObat.setValueAt(FVCZ.getText(),tbObat.getSelectedRow(),21);
                tbObat.setValueAt(FVCPost.getText(),tbObat.getSelectedRow(),22);
                tbObat.setValueAt(FVCPostPersen.getText(),tbObat.getSelectedRow(),23);
                tbObat.setValueAt(FVCPerubahan.getText(),tbObat.getSelectedRow(),24);
                tbObat.setValueAt(FEV1Pre.getText(),tbObat.getSelectedRow(),25);
                tbObat.setValueAt(FEV1Pred.getText(),tbObat.getSelectedRow(),26);
                tbObat.setValueAt(FEV1LLN.getText(),tbObat.getSelectedRow(),27);
                tbObat.setValueAt(FEV1Persen.getText(),tbObat.getSelectedRow(),28);
                tbObat.setValueAt(FEV1Z.getText(),tbObat.getSelectedRow(),29);
                tbObat.setValueAt(FEV1Post.getText(),tbObat.getSelectedRow(),30);
                tbObat.setValueAt(FEV1PostPersen.getText(),tbObat.getSelectedRow(),31);
                tbObat.setValueAt(FEV1Perubahan.getText(),tbObat.getSelectedRow(),32);
                tbObat.setValueAt(RasioPre.getText(),tbObat.getSelectedRow(),33);
                tbObat.setValueAt(RasioPred.getText(),tbObat.getSelectedRow(),34);
                tbObat.setValueAt(RasioLLN.getText(),tbObat.getSelectedRow(),35);
                tbObat.setValueAt(RasioPersen.getText(),tbObat.getSelectedRow(),36);
                tbObat.setValueAt(RasioZ.getText(),tbObat.getSelectedRow(),37);
                tbObat.setValueAt(RasioPost.getText(),tbObat.getSelectedRow(),38);
                tbObat.setValueAt(RasioPostPersen.getText(),tbObat.getSelectedRow(),39);
                tbObat.setValueAt(RasioPerubahan.getText(),tbObat.getSelectedRow(),40);
                tbObat.setValueAt(PEFPre.getText(),tbObat.getSelectedRow(),41);
                tbObat.setValueAt(PEFPred.getText(),tbObat.getSelectedRow(),42);
                tbObat.setValueAt(PEFLLN.getText(),tbObat.getSelectedRow(),43);
                tbObat.setValueAt(PEFPersen.getText(),tbObat.getSelectedRow(),44);
                tbObat.setValueAt(PEFZ.getText(),tbObat.getSelectedRow(),45);
                tbObat.setValueAt(PEFPost.getText(),tbObat.getSelectedRow(),46);
                tbObat.setValueAt(PEFPostPersen.getText(),tbObat.getSelectedRow(),47);
                tbObat.setValueAt(PEFPerubahan.getText(),tbObat.getSelectedRow(),48);
                tbObat.setValueAt(FEF2575Pre.getText(),tbObat.getSelectedRow(),49);
                tbObat.setValueAt(FEF2575Pred.getText(),tbObat.getSelectedRow(),50);
                tbObat.setValueAt(FEF2575LLN.getText(),tbObat.getSelectedRow(),51);
                tbObat.setValueAt(FEF2575Persen.getText(),tbObat.getSelectedRow(),52);
                tbObat.setValueAt(FEF2575Z.getText(),tbObat.getSelectedRow(),53);
                tbObat.setValueAt(FEF2575Post.getText(),tbObat.getSelectedRow(),54);
                tbObat.setValueAt(FEF2575PostPersen.getText(),tbObat.getSelectedRow(),55);
                tbObat.setValueAt(FEF2575Perubahan.getText(),tbObat.getSelectedRow(),56);
                tbObat.setValueAt(ManuverPre.getText(),tbObat.getSelectedRow(),57);
                tbObat.setValueAt(GradeFVCPre.getSelectedItem().toString(),tbObat.getSelectedRow(),58);
                tbObat.setValueAt(GradeFEV1Pre.getSelectedItem().toString(),tbObat.getSelectedRow(),59);
                tbObat.setValueAt(ManuverPost.getText(),tbObat.getSelectedRow(),60);
                tbObat.setValueAt(GradeFVCPost.getSelectedItem().toString(),tbObat.getSelectedRow(),61);
                tbObat.setValueAt(GradeFEV1Post.getSelectedItem().toString(),tbObat.getSelectedRow(),62);
                tbObat.setValueAt(Operator.getText(),tbObat.getSelectedRow(),63);
                tbObat.setValueAt(CatatanPelaksanaan.getText(),tbObat.getSelectedRow(),64);
                tbObat.setValueAt(PolaGangguan.getSelectedItem().toString(),tbObat.getSelectedRow(),65);
                tbObat.setValueAt(Derajat.getSelectedItem().toString(),tbObat.getSelectedRow(),66);
                tbObat.setValueAt(ResponsBronkodilator.getSelectedItem().toString(),tbObat.getSelectedRow(),67);
                tbObat.setValueAt(Kesimpulan.getText(),tbObat.getSelectedRow(),68);
                tbObat.setValueAt(Rekomendasi.getText(),tbObat.getSelectedRow(),69);
                emptTeks();
                TabRawat.setSelectedIndex(1);
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(530,HEIGHT));
            TabData.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            TabData.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            try {
                ps=koneksi.prepareStatement("select hasil_pemeriksaan_spirometri_gambar.photo from hasil_pemeriksaan_spirometri_gambar where hasil_pemeriksaan_spirometri_gambar.no_rawat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            LoadHTML2.setText("<html><body><center><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanspirometri/"+rs.getString("photo")+"'><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/hasilpemeriksaanspirometri/"+rs.getString("photo")+"' alt='photo' width='550' height='550'/></a></center></body></html>");
                        }  
                    }else{
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
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
    
    private void tampilOrthanc() {
        if(TabData.isVisible()==true){
            if(tbObat.getSelectedRow()!= -1){
                 if(TabData.getSelectedIndex()==1){
                     try {
                         Valid.tabelKosong(tabModeDicom);
                         ApiOrthanc orthanc=new ApiOrthanc();
                         root=orthanc.AmbilSeries(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),Valid.SetTgl(DTPCari1.getSelectedItem()+"").replaceAll("-",""),Valid.SetTgl(DTPCari2.getSelectedItem()+"").replaceAll("-",""));
                         for(JsonNode list:root){
                             for(JsonNode sublist:list.path("Series")){
                                  tabModeDicom.addRow(new Object[]{
                                       list.path("PatientMainDicomTags").path("PatientID").asText(),list.path("ID").asText(),sublist.asText()
                                  });   
                             }        
                         }
                     } catch (Exception e) {
                         System.out.println("Notif : "+e);
                     }
                 }
            }
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("hasil_pemeriksaan_spirometri","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",66,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KdDokter.getText(),
                DiagnosaKlinis.getText(),KirimanDari.getText(),TinggiBadan.getText(),BeratBadan.getText(),Etnis.getSelectedItem().toString(),PersamaanReferensi.getText(),SuhuRuangan.getText(),TekananBarometrik.getText(),ObatDikonsumsi.getText(),Bronkodilator.getText(),FVCPre.getText(),FVCPred.getText(),FVCLLN.getText(),FVCPersen.getText(),FVCZ.getText(),FVCPost.getText(),FVCPostPersen.getText(),FVCPerubahan.getText(),FEV1Pre.getText(),FEV1Pred.getText(),FEV1LLN.getText(),FEV1Persen.getText(),FEV1Z.getText(),FEV1Post.getText(),FEV1PostPersen.getText(),FEV1Perubahan.getText(),RasioPre.getText(),RasioPred.getText(),RasioLLN.getText(),RasioPersen.getText(),RasioZ.getText(),RasioPost.getText(),RasioPostPersen.getText(),RasioPerubahan.getText(),PEFPre.getText(),PEFPred.getText(),PEFLLN.getText(),PEFPersen.getText(),PEFZ.getText(),PEFPost.getText(),PEFPostPersen.getText(),PEFPerubahan.getText(),FEF2575Pre.getText(),FEF2575Pred.getText(),FEF2575LLN.getText(),FEF2575Persen.getText(),FEF2575Z.getText(),FEF2575Post.getText(),FEF2575PostPersen.getText(),FEF2575Perubahan.getText(),ManuverPre.getText(),GradeFVCPre.getSelectedItem().toString(),GradeFEV1Pre.getSelectedItem().toString(),ManuverPost.getText(),GradeFVCPost.getSelectedItem().toString(),GradeFEV1Post.getSelectedItem().toString(),Operator.getText(),CatatanPelaksanaan.getText(),PolaGangguan.getSelectedItem().toString(),Derajat.getSelectedItem().toString(),ResponsBronkodilator.getSelectedItem().toString(),Kesimpulan.getText(),Rekomendasi.getText()
            })==true){
                tabMode.addRow(new Object[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),KdDokter.getText(),NmDokter.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    KirimanDari.getText(),DiagnosaKlinis.getText(),TinggiBadan.getText(),BeratBadan.getText(),Etnis.getSelectedItem().toString(),PersamaanReferensi.getText(),SuhuRuangan.getText(),TekananBarometrik.getText(),ObatDikonsumsi.getText(),Bronkodilator.getText(),FVCPre.getText(),FVCPred.getText(),FVCLLN.getText(),FVCPersen.getText(),FVCZ.getText(),FVCPost.getText(),FVCPostPersen.getText(),FVCPerubahan.getText(),FEV1Pre.getText(),FEV1Pred.getText(),FEV1LLN.getText(),FEV1Persen.getText(),FEV1Z.getText(),FEV1Post.getText(),FEV1PostPersen.getText(),FEV1Perubahan.getText(),RasioPre.getText(),RasioPred.getText(),RasioLLN.getText(),RasioPersen.getText(),RasioZ.getText(),RasioPost.getText(),RasioPostPersen.getText(),RasioPerubahan.getText(),PEFPre.getText(),PEFPred.getText(),PEFLLN.getText(),PEFPersen.getText(),PEFZ.getText(),PEFPost.getText(),PEFPostPersen.getText(),PEFPerubahan.getText(),FEF2575Pre.getText(),FEF2575Pred.getText(),FEF2575LLN.getText(),FEF2575Persen.getText(),FEF2575Z.getText(),FEF2575Post.getText(),FEF2575PostPersen.getText(),FEF2575Perubahan.getText(),ManuverPre.getText(),GradeFVCPre.getSelectedItem().toString(),GradeFEV1Pre.getSelectedItem().toString(),ManuverPost.getText(),GradeFVCPost.getSelectedItem().toString(),GradeFEV1Post.getSelectedItem().toString(),Operator.getText(),CatatanPelaksanaan.getText(),PolaGangguan.getSelectedItem().toString(),Derajat.getSelectedItem().toString(),ResponsBronkodilator.getSelectedItem().toString(),Kesimpulan.getText(),Rekomendasi.getText()
                });
                emptTeks();
                LCount.setText(""+tabMode.getRowCount());
        }
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

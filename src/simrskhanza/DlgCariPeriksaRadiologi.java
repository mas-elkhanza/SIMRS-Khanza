package simrskhanza;
import bridging.ApiOrthanc;
import bridging.OrthancDICOM;
import com.fasterxml.jackson.databind.JsonNode;
import kepegawaian.DlgCariPetugas;
import keuangan.Jurnal;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import laporan.DlgBerkasRawat;
import rekammedis.MasterCariTemplateHasilRadiologi;
import rekammedis.RMRiwayatPerawatan;

import java.io.File; //tambahan by ichsan
import java.io.FileInputStream;
import java.sql.SQLException;
import org.apache.commons.io.FileUtils;  //tambahan ichsan
import org.apache.http.HttpResponse; //tambahan ichsan
import org.apache.http.client.HttpClient; //tambahan ichsan
import org.apache.http.client.methods.HttpPost; //tambahan ichsan
import org.apache.http.entity.mime.HttpMultipartMode; //tambahan ichsan
import org.apache.http.entity.mime.MultipartEntity; //tambahan ichsan
import org.apache.http.entity.mime.content.ByteArrayBody; //tambahan ichsan
import org.apache.http.entity.mime.content.InputStreamBody; //tambahan ichsan
import org.apache.http.impl.client.DefaultHttpClient; //tambahan ichsan


public class DlgCariPeriksaRadiologi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDicom;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private boolean sukses=false;
    private JsonNode root;
    private int i;
    private StringBuilder htmlContent;
    private PreparedStatement ps,ps2,ps3,ps4,ps5,psrekening;
    private ResultSet rs,rs2,rs3,rs5,rsrekening;
    private String kamar,namakamar,pemeriksaan="",pilihan="",status="",finger="",statushasil="", lokasifile="", SQLException="", FileName ="",kodeberkas=""; //tambahan ichsan FileName ="",kodeberkas="", lokasifile="" SQLException=""
    private double ttl=0,item=0;
    private double ttljmdokter=0,ttljmpetugas=0,ttlkso=0,ttlpendapatan=0,ttlbhp=0,ttljasasarana=0,ttljmperujuk=0,ttlmenejemen=0;;
    private String kdpetugas="",kdpenjab="",Suspen_Piutang_Radiologi_Ranap="",Radiologi_Ranap="",Beban_Jasa_Medik_Dokter_Radiologi_Ranap="",Utang_Jasa_Medik_Dokter_Radiologi_Ranap="",
            Beban_Jasa_Medik_Petugas_Radiologi_Ranap="",Utang_Jasa_Medik_Petugas_Radiologi_Ranap="",Beban_Kso_Radiologi_Ranap="",Utang_Kso_Radiologi_Ranap="",
            HPP_Persediaan_Radiologi_Rawat_Inap="",Persediaan_BHP_Radiologi_Rawat_Inap="",Beban_Jasa_Sarana_Radiologi_Ranap="",Utang_Jasa_Sarana_Radiologi_Ranap="",
            Beban_Jasa_Perujuk_Radiologi_Ranap="",Utang_Jasa_Perujuk_Radiologi_Ranap="",Beban_Jasa_Menejemen_Radiologi_Ranap="",Utang_Jasa_Menejemen_Radiologi_Ranap="",
            Suspen_Piutang_Radiologi_Ralan="",Radiologi_Ralan="",Beban_Jasa_Medik_Dokter_Radiologi_Ralan="",Utang_Jasa_Medik_Dokter_Radiologi_Ralan="",
            Beban_Jasa_Medik_Petugas_Radiologi_Ralan="",Utang_Jasa_Medik_Petugas_Radiologi_Ralan="",Beban_Kso_Radiologi_Ralan="",Utang_Kso_Radiologi_Ralan="",
            HPP_Persediaan_Radiologi_Rawat_Jalan="",Persediaan_BHP_Radiologi_Rawat_Jalan="",Beban_Jasa_Sarana_Radiologi_Ralan="",Utang_Jasa_Sarana_Radiologi_Ralan="",
            Beban_Jasa_Perujuk_Radiologi_Ralan="",Utang_Jasa_Perujuk_Radiologi_Ralan="",Beban_Jasa_Menejemen_Radiologi_Ralan="",Utang_Jasa_Menejemen_Radiologi_Ralan="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPeriksaRadiologi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Rawat","Pasien","Petugas","Tgl.Periksa","Jam Periksa","Dokter Perujuk","Penanggung Jawab"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(350);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(170);
            }else if(i==6){
                column.setPreferredWidth(170);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
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
                column.setPreferredWidth(310);
            }else if(i==2){
                column.setPreferredWidth(310);
            }
        }
        tbListDicom.setDefaultRenderer(Object.class, new WarnaTable());

        NoRawat.setDocument(new batasInput((byte)17).getKata(NoRawat));
        kdmem.setDocument(new batasInput((byte)8).getKata(kdmem));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        statushasil="";
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        statushasil="";
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        statushasil="";
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
                if(akses.getform().equals("DlgCariPeriksaRadiologi")){
                    if(petugas.getTable().getSelectedRow()!= -1){     
                        if(pilihan.equals("ubahpetugas")){
                            KdPtgUbah.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPtgUbah.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            KdPtgUbah.requestFocus();
                        }else if(pilihan.equals("caripetugas")){
                            kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            kdptg.requestFocus();
                        }                            
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPeriksaRadiologi")){
                    if(dokter.getTable().getSelectedRow()!= -1){
                        if(pilihan.equals("perujuk")){
                            KodePerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmPerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KodePerujuk.requestFocus();
                        }else if(pilihan.equals("penjab")){
                            KodePj.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterPj.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KodePj.requestFocus();
                        }  
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
        
        try {
            psrekening=koneksi.prepareStatement(
                    "select set_akun_ranap.Suspen_Piutang_Radiologi_Ranap,set_akun_ranap.Radiologi_Ranap,set_akun_ranap.Beban_Jasa_Medik_Dokter_Radiologi_Ranap,"+
                    "set_akun_ranap.Utang_Jasa_Medik_Dokter_Radiologi_Ranap,set_akun_ranap.Beban_Jasa_Medik_Petugas_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Medik_Petugas_Radiologi_Ranap,"+
                    "set_akun_ranap.Beban_Kso_Radiologi_Ranap,set_akun_ranap.Utang_Kso_Radiologi_Ranap,set_akun_ranap.HPP_Persediaan_Radiologi_Rawat_Inap,"+
                    "set_akun_ranap.Persediaan_BHP_Radiologi_Rawat_Inap,set_akun_ranap.Beban_Jasa_Sarana_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Sarana_Radiologi_Ranap,"+
                    "set_akun_ranap.Beban_Jasa_Perujuk_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Perujuk_Radiologi_Ranap,set_akun_ranap.Beban_Jasa_Menejemen_Radiologi_Ranap,"+
                    "set_akun_ranap.Utang_Jasa_Menejemen_Radiologi_Ranap from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Radiologi_Ranap=rsrekening.getString("Suspen_Piutang_Radiologi_Ranap");
                    Radiologi_Ranap=rsrekening.getString("Radiologi_Ranap");
                    Beban_Jasa_Medik_Dokter_Radiologi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Beban_Jasa_Medik_Petugas_Radiologi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ranap");
                    Utang_Jasa_Medik_Petugas_Radiologi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ranap");
                    Beban_Kso_Radiologi_Ranap=rsrekening.getString("Beban_Kso_Radiologi_Ranap");
                    Utang_Kso_Radiologi_Ranap=rsrekening.getString("Utang_Kso_Radiologi_Ranap");
                    HPP_Persediaan_Radiologi_Rawat_Inap=rsrekening.getString("HPP_Persediaan_Radiologi_Rawat_Inap");
                    Persediaan_BHP_Radiologi_Rawat_Inap=rsrekening.getString("Persediaan_BHP_Radiologi_Rawat_Inap");
                    Beban_Jasa_Sarana_Radiologi_Ranap=rsrekening.getString("Beban_Jasa_Sarana_Radiologi_Ranap");
                    Utang_Jasa_Sarana_Radiologi_Ranap=rsrekening.getString("Utang_Jasa_Sarana_Radiologi_Ranap");
                    Beban_Jasa_Perujuk_Radiologi_Ranap=rsrekening.getString("Beban_Jasa_Perujuk_Radiologi_Ranap");
                    Utang_Jasa_Perujuk_Radiologi_Ranap=rsrekening.getString("Utang_Jasa_Perujuk_Radiologi_Ranap");
                    Beban_Jasa_Menejemen_Radiologi_Ranap=rsrekening.getString("Beban_Jasa_Menejemen_Radiologi_Ranap");
                    Utang_Jasa_Menejemen_Radiologi_Ranap=rsrekening.getString("Utang_Jasa_Menejemen_Radiologi_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }            
            
            psrekening=koneksi.prepareStatement(
                    "select set_akun_ralan.Suspen_Piutang_Radiologi_Ralan,set_akun_ralan.Radiologi_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Radiologi_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Dokter_Radiologi_Ralan,set_akun_ralan.Beban_Jasa_Medik_Petugas_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Medik_Petugas_Radiologi_Ralan,"+
                    "set_akun_ralan.Beban_Kso_Radiologi_Ralan,set_akun_ralan.Utang_Kso_Radiologi_Ralan,set_akun_ralan.HPP_Persediaan_Radiologi_Rawat_Jalan,"+
                    "set_akun_ralan.Persediaan_BHP_Radiologi_Rawat_Jalan,set_akun_ralan.Beban_Jasa_Sarana_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Sarana_Radiologi_Ralan,"+
                    "set_akun_ralan.Beban_Jasa_Perujuk_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Perujuk_Radiologi_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Radiologi_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Menejemen_Radiologi_Ralan from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Radiologi_Ralan=rsrekening.getString("Suspen_Piutang_Radiologi_Ralan");
                    Radiologi_Ralan=rsrekening.getString("Radiologi_Ralan");
                    Beban_Jasa_Medik_Dokter_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Beban_Jasa_Medik_Petugas_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ralan");
                    Utang_Jasa_Medik_Petugas_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ralan");
                    Beban_Kso_Radiologi_Ralan=rsrekening.getString("Beban_Kso_Radiologi_Ralan");
                    Utang_Kso_Radiologi_Ralan=rsrekening.getString("Utang_Kso_Radiologi_Ralan");
                    HPP_Persediaan_Radiologi_Rawat_Jalan=rsrekening.getString("HPP_Persediaan_Radiologi_Rawat_Jalan");
                    Persediaan_BHP_Radiologi_Rawat_Jalan=rsrekening.getString("Persediaan_BHP_Radiologi_Rawat_Jalan");
                    Beban_Jasa_Sarana_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Radiologi_Ralan");
                    Utang_Jasa_Sarana_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Radiologi_Ralan");
                    Beban_Jasa_Perujuk_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Perujuk_Radiologi_Ralan");
                    Utang_Jasa_Perujuk_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Perujuk_Radiologi_Ralan");
                    Beban_Jasa_Menejemen_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Radiologi_Ralan");
                    Utang_Jasa_Menejemen_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Radiologi_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }  
        } catch (Exception e) {
            System.out.println(e);
        }    
        
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
        LoadHTML.setEditable(false);
        LoadHTML.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
     
        ChkAccor.setSelected(false);
        isPhoto();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakNota = new javax.swing.JMenuItem();
        MnUbahDokterPetugas = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        ppSudahKeluarBacaan = new javax.swing.JMenuItem();
        ppBelumKeluarBacaan = new javax.swing.JMenuItem();
        Penjab = new widget.TextBox();
        Jk = new widget.TextBox();
        Umur = new widget.TextBox();
        Alamat = new widget.TextBox();
        NoRM = new widget.TextBox();
        WindowGantiDokterParamedis = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        FormInput = new widget.panelisi();
        BtnSimpan4 = new widget.Button();
        BtnCloseIn4 = new widget.Button();
        jLabel7 = new widget.Label();
        KodePj = new widget.TextBox();
        NmDokterPj = new widget.TextBox();
        btnDokterPj = new widget.Button();
        btnDokter = new widget.Button();
        NmPerujuk = new widget.TextBox();
        KodePerujuk = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel12 = new widget.Label();
        KdPtgUbah = new widget.TextBox();
        NmPtgUbah = new widget.TextBox();
        btnPetugas1 = new widget.Button();
        Petugas = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoRawat = new widget.TextBox();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdmem = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmmem = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPasien = new widget.Button();
        btnPetugas = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        NoHPWAPasien = new widget.TextBox();
        label22 = new widget.Label();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        UploadRadiologi = new widget.Button();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        TabData = new javax.swing.JTabbedPane();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        btnAmbilPhoto = new widget.Button();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        FormHasilRadiologi = new widget.PanelBiasa();
        Scroll3 = new widget.ScrollPane();
        HasilPeriksa = new widget.TextArea();
        panelGlass6 = new widget.panelisi();
        btnAmbilPhoto1 = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnPrint1 = new widget.Button();
        TombolWA = new widget.Button();
        panelisi8 = new widget.panelisi();
        InformasiTambahan = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel6 = new widget.Label();
        DiagnosisKlinis = new widget.TextBox();
        FormOrthan = new widget.PanelBiasa();
        Scroll5 = new widget.ScrollPane();
        tbListDicom = new widget.Table();
        panelGlass7 = new widget.panelisi();
        btnDicom = new widget.Button();
        PanelDataDicari = new widget.panelisi();
        label17 = new widget.Label();
        NoRawatDicari = new widget.Label();
        label19 = new widget.Label();
        TglDicari = new widget.Label();
        JamDicari = new widget.Label();
        label20 = new widget.Label();
        PasienDicari = new widget.Label();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakNota.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakNota.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakNota.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakNota.setText("Cetak Nota Radiologi");
        MnCetakNota.setName("MnCetakNota"); // NOI18N
        MnCetakNota.setPreferredSize(new java.awt.Dimension(220, 28));
        MnCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakNotaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakNota);

        MnUbahDokterPetugas.setBackground(new java.awt.Color(255, 255, 254));
        MnUbahDokterPetugas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbahDokterPetugas.setForeground(new java.awt.Color(50, 50, 50));
        MnUbahDokterPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbahDokterPetugas.setText("Ubah P.J.Lab, Perujuk & Petugas");
        MnUbahDokterPetugas.setName("MnUbahDokterPetugas"); // NOI18N
        MnUbahDokterPetugas.setPreferredSize(new java.awt.Dimension(220, 28));
        MnUbahDokterPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbahDokterPetugasActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUbahDokterPetugas);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(220, 28));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBerkasDigital);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(50, 50, 50));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(220, 28));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        ppSudahKeluarBacaan.setBackground(new java.awt.Color(255, 255, 254));
        ppSudahKeluarBacaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSudahKeluarBacaan.setForeground(new java.awt.Color(50, 50, 50));
        ppSudahKeluarBacaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSudahKeluarBacaan.setText("Sudah Keluar Bacaan");
        ppSudahKeluarBacaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSudahKeluarBacaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSudahKeluarBacaan.setName("ppSudahKeluarBacaan"); // NOI18N
        ppSudahKeluarBacaan.setPreferredSize(new java.awt.Dimension(220, 28));
        ppSudahKeluarBacaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSudahKeluarBacaanBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppSudahKeluarBacaan);

        ppBelumKeluarBacaan.setBackground(new java.awt.Color(255, 255, 254));
        ppBelumKeluarBacaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBelumKeluarBacaan.setForeground(new java.awt.Color(50, 50, 50));
        ppBelumKeluarBacaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBelumKeluarBacaan.setText("Belum Keluar Bacaan");
        ppBelumKeluarBacaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBelumKeluarBacaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBelumKeluarBacaan.setName("ppBelumKeluarBacaan"); // NOI18N
        ppBelumKeluarBacaan.setPreferredSize(new java.awt.Dimension(220, 28));
        ppBelumKeluarBacaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBelumKeluarBacaanBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBelumKeluarBacaan);

        Penjab.setEditable(false);
        Penjab.setFocusTraversalPolicyProvider(true);
        Penjab.setName("Penjab"); // NOI18N
        Penjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenjabKeyPressed(evt);
            }
        });

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N

        NoRM.setName("NoRM"); // NOI18N
        NoRM.setPreferredSize(new java.awt.Dimension(207, 23));

        WindowGantiDokterParamedis.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiDokterParamedis.setName("WindowGantiDokterParamedis"); // NOI18N
        WindowGantiDokterParamedis.setUndecorated(true);
        WindowGantiDokterParamedis.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), "::[ Ubah P.J.Rad, Perujuk & Petugas ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(89, 434));
        FormInput.setLayout(null);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSimpan4);
        BtnSimpan4.setBounds(470, 15, 100, 30);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(470, 60, 100, 30);

        jLabel7.setText("Dokter P.J. :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 12, 92, 23);

        KodePj.setEditable(false);
        KodePj.setName("KodePj"); // NOI18N
        FormInput.add(KodePj);
        KodePj.setBounds(95, 12, 113, 23);

        NmDokterPj.setEditable(false);
        NmDokterPj.setHighlighter(null);
        NmDokterPj.setName("NmDokterPj"); // NOI18N
        FormInput.add(NmDokterPj);
        NmDokterPj.setBounds(210, 12, 208, 23);

        btnDokterPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterPj.setMnemonic('4');
        btnDokterPj.setToolTipText("ALt+4");
        btnDokterPj.setName("btnDokterPj"); // NOI18N
        btnDokterPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterPjActionPerformed(evt);
            }
        });
        FormInput.add(btnDokterPj);
        btnDokterPj.setBounds(420, 12, 28, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('4');
        btnDokter.setToolTipText("ALt+4");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(420, 42, 28, 23);

        NmPerujuk.setEditable(false);
        NmPerujuk.setHighlighter(null);
        NmPerujuk.setName("NmPerujuk"); // NOI18N
        FormInput.add(NmPerujuk);
        NmPerujuk.setBounds(210, 42, 208, 23);

        KodePerujuk.setName("KodePerujuk"); // NOI18N
        KodePerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePerujukKeyPressed(evt);
            }
        });
        FormInput.add(KodePerujuk);
        KodePerujuk.setBounds(95, 42, 113, 23);

        jLabel9.setText("Dokter Perujuk :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 42, 92, 23);

        jLabel12.setText("Petugas :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 72, 92, 23);

        KdPtgUbah.setName("KdPtgUbah"); // NOI18N
        KdPtgUbah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPtgUbahKeyPressed(evt);
            }
        });
        FormInput.add(KdPtgUbah);
        KdPtgUbah.setBounds(95, 72, 113, 23);

        NmPtgUbah.setEditable(false);
        NmPtgUbah.setName("NmPtgUbah"); // NOI18N
        FormInput.add(NmPtgUbah);
        NmPtgUbah.setBounds(210, 72, 208, 23);

        btnPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas1.setMnemonic('2');
        btnPetugas1.setToolTipText("Alt+2");
        btnPetugas1.setName("btnPetugas1"); // NOI18N
        btnPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugas1ActionPerformed(evt);
            }
        });
        FormInput.add(btnPetugas1);
        btnPetugas1.setBounds(420, 72, 28, 23);

        internalFrame5.add(FormInput, java.awt.BorderLayout.CENTER);

        WindowGantiDokterParamedis.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        Petugas.setEditable(false);
        Petugas.setHighlighter(null);
        Petugas.setName("Petugas"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemeriksaan Radiologi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        panelisi3.add(NoRawat);
        NoRawat.setBounds(79, 10, 226, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 75, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(79, 40, 100, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(330, 10, 60, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(330, 40, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(400, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(400, 40, 80, 23);

        nmmem.setEditable(false);
        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(480, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(480, 40, 240, 23);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('1');
        btnPasien.setToolTipText("Alt+1");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        panelisi3.add(btnPasien);
        btnPasien.setBounds(720, 10, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(720, 40, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(178, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(205, 40, 100, 23);

        NoHPWAPasien.setName("NoHPWAPasien"); // NOI18N
        NoHPWAPasien.setPreferredSize(new java.awt.Dimension(207, 23));
        NoHPWAPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoHPWAPasienKeyPressed(evt);
            }
        });
        panelisi3.add(NoHPWAPasien);
        NoHPWAPasien.setBounds(860, 10, 226, 23);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label22.setText("Nomor WA Pasien:");
        label22.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label22);
        label22.setBounds(760, 10, 100, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi1.add(BtnCari);

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(100, 30));
        panelisi1.add(label9);

        UploadRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/upload24.png"))); // NOI18N
        UploadRadiologi.setMnemonic('T');
        UploadRadiologi.setText("Upload ke Berkas Digital");
        UploadRadiologi.setToolTipText("Alt+T");
        UploadRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        UploadRadiologi.setName("UploadRadiologi"); // NOI18N
        UploadRadiologi.setPreferredSize(new java.awt.Dimension(190, 30));
        UploadRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadRadiologiActionPerformed(evt);
            }
        });
        UploadRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UploadRadiologiKeyPressed(evt);
            }
        });
        panelisi1.add(UploadRadiologi);

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
        panelisi1.add(BtnHapus);

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
        panelisi1.add(BtnAll);

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
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
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
        TabData.setForeground(new java.awt.Color(50, 50, 50));
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
        FormPhoto.setPreferredSize(new java.awt.Dimension(400, 700));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbilPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbilPhoto.setMnemonic('U');
        btnAmbilPhoto.setText("Ambil");
        btnAmbilPhoto.setToolTipText("Alt+U");
        btnAmbilPhoto.setName("btnAmbilPhoto"); // NOI18N
        btnAmbilPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbilPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(btnAmbilPhoto);

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(400, 400));

        LoadHTML.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabData.addTab("Photo Radiologi", FormPhoto);

        FormHasilRadiologi.setBackground(new java.awt.Color(255, 255, 255));
        FormHasilRadiologi.setBorder(null);
        FormHasilRadiologi.setName("FormHasilRadiologi"); // NOI18N
        FormHasilRadiologi.setPreferredSize(new java.awt.Dimension(115, 73));
        FormHasilRadiologi.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        HasilPeriksa.setBorder(null);
        HasilPeriksa.setColumns(25);
        HasilPeriksa.setRows(40);
        HasilPeriksa.setName("HasilPeriksa"); // NOI18N
        Scroll3.setViewportView(HasilPeriksa);

        FormHasilRadiologi.add(Scroll3, java.awt.BorderLayout.CENTER);

        panelGlass6.setBorder(null);
        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbilPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAmbilPhoto1.setMnemonic('U');
        btnAmbilPhoto1.setText("Template");
        btnAmbilPhoto1.setToolTipText("Alt+U");
        btnAmbilPhoto1.setName("btnAmbilPhoto1"); // NOI18N
        btnAmbilPhoto1.setPreferredSize(new java.awt.Dimension(105, 30));
        btnAmbilPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilPhoto1ActionPerformed(evt);
            }
        });
        panelGlass6.add(btnAmbilPhoto1);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Update");
        BtnSimpan.setToolTipText("Alt+U");
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
        panelGlass6.add(BtnSimpan);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Cetak");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelGlass6.add(BtnPrint1);

        TombolWA.setBackground(new java.awt.Color(204, 204, 204));
        TombolWA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/wa.png"))); // NOI18N
        TombolWA.setMnemonic('T');
        TombolWA.setText("Kirim Foto dan Expertise ke Pasien via Whatsapp");
        TombolWA.setToolTipText("Alt+T");
        TombolWA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TombolWA.setName("TombolWA"); // NOI18N
        TombolWA.setPreferredSize(new java.awt.Dimension(180, 30));
        TombolWA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TombolWAActionPerformed(evt);
            }
        });
        panelGlass6.add(TombolWA);

        FormHasilRadiologi.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelisi8.setBorder(null);
        panelisi8.setName("panelisi8"); // NOI18N
        panelisi8.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi8.setLayout(null);

        InformasiTambahan.setEditable(false);
        InformasiTambahan.setHighlighter(null);
        InformasiTambahan.setName("InformasiTambahan"); // NOI18N
        panelisi8.add(InformasiTambahan);
        InformasiTambahan.setBounds(124, 10, 220, 23);

        jLabel5.setText("Informasi Tambahan :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelisi8.add(jLabel5);
        jLabel5.setBounds(0, 10, 120, 23);

        jLabel6.setText("Diagnosis Klinis :");
        jLabel6.setName("jLabel6"); // NOI18N
        panelisi8.add(jLabel6);
        jLabel6.setBounds(0, 40, 120, 23);

        DiagnosisKlinis.setEditable(false);
        DiagnosisKlinis.setHighlighter(null);
        DiagnosisKlinis.setName("DiagnosisKlinis"); // NOI18N
        panelisi8.add(DiagnosisKlinis);
        DiagnosisKlinis.setBounds(124, 40, 220, 23);

        FormHasilRadiologi.add(panelisi8, java.awt.BorderLayout.PAGE_START);

        TabData.addTab("Hasil Bacaan Radiologi", FormHasilRadiologi);

        FormOrthan.setBackground(new java.awt.Color(255, 255, 255));
        FormOrthan.setBorder(null);
        FormOrthan.setName("FormOrthan"); // NOI18N
        FormOrthan.setPreferredSize(new java.awt.Dimension(115, 73));
        FormOrthan.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbListDicom.setName("tbListDicom"); // NOI18N
        tbListDicom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListDicomMouseClicked(evt);
            }
        });
        tbListDicom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbListDicomKeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbListDicom);

        FormOrthan.add(Scroll5, java.awt.BorderLayout.CENTER);

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

        FormOrthan.add(panelGlass7, java.awt.BorderLayout.PAGE_END);

        TabData.addTab("Integrasi Orthanc", FormOrthan);

        PanelAccor.add(TabData, java.awt.BorderLayout.CENTER);

        PanelDataDicari.setBackground(new java.awt.Color(255, 250, 250));
        PanelDataDicari.setBorder(null);
        PanelDataDicari.setName("PanelDataDicari"); // NOI18N
        PanelDataDicari.setPreferredSize(new java.awt.Dimension(100, 44));
        PanelDataDicari.setWarnaAtas(new java.awt.Color(255, 250, 250));
        PanelDataDicari.setWarnaBawah(new java.awt.Color(255, 250, 250));
        PanelDataDicari.setLayout(null);

        label17.setText("No.Rawat :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        PanelDataDicari.add(label17);
        label17.setBounds(0, 10, 74, 23);

        NoRawatDicari.setBackground(new java.awt.Color(255, 255, 255));
        NoRawatDicari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        NoRawatDicari.setName("NoRawatDicari"); // NOI18N
        NoRawatDicari.setPreferredSize(new java.awt.Dimension(105, 23));
        PanelDataDicari.add(NoRawatDicari);
        NoRawatDicari.setBounds(78, 10, 105, 23);

        label19.setText("Tanggal :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(53, 23));
        PanelDataDicari.add(label19);
        label19.setBounds(185, 10, 55, 23);

        TglDicari.setBackground(new java.awt.Color(255, 255, 255));
        TglDicari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TglDicari.setName("TglDicari"); // NOI18N
        TglDicari.setPreferredSize(new java.awt.Dimension(65, 23));
        PanelDataDicari.add(TglDicari);
        TglDicari.setBounds(244, 10, 65, 23);

        JamDicari.setBackground(new java.awt.Color(255, 255, 255));
        JamDicari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        JamDicari.setName("JamDicari"); // NOI18N
        JamDicari.setPreferredSize(new java.awt.Dimension(52, 23));
        PanelDataDicari.add(JamDicari);
        JamDicari.setBounds(311, 10, 52, 23);

        label20.setText("Pasien :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(50, 23));
        PanelDataDicari.add(label20);
        label20.setBounds(368, 10, 51, 23);

        PasienDicari.setBackground(new java.awt.Color(255, 255, 255));
        PasienDicari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PasienDicari.setName("PasienDicari"); // NOI18N
        PasienDicari.setPreferredSize(new java.awt.Dimension(470, 23));
        PanelDataDicari.add(PasienDicari);
        PasienDicari.setBounds(423, 10, 270, 23);

        PanelAccor.add(PanelDataDicari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("DlgCariPeriksaRadiologi");
        DlgCariPasien member=new DlgCariPasien(null,false);
        member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPeriksaRadiologi")){
                    if(member.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),0).toString());
                        nmmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),1).toString());
                    } 
                    kdmem.requestFocus();
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
        
        member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPeriksaRadiologi")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        member.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariPeriksaRadiologi");
        pilihan="caripetugas";
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdmem,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem,kdmem.getText());      
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem,kdmem.getText());
            NoRawat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPasienActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem,kdmem.getText());
            Tgl1.requestFocus();      
        }
    }//GEN-LAST:event_kdmemKeyPressed

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        Valid.pindah(evt, BtnKeluar, kdptg);
    }//GEN-LAST:event_NoRawatKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
            NoRawat.requestFocus();            
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdptg);
    }//GEN-LAST:event_Tgl2KeyPressed

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
        statushasil="";
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
        NoRawat.setText("");
        kdmem.setText("");
        nmmem.setText("");
        kdptg.setText("");
        nmptg.setText("");
        statushasil="";
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary_radiologi");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary_radiologi","'"+i+"','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Radiologi"); 
            }
            
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReport("rptDataRadiologi.jasper","report","::[ Data Pemeriksaan Radiologi ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,NoRawat);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        TCari.requestFocus();
    }else if(tbDokter.getSelectedRow()<= -1){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
    }else {
        if(Kd2.getText().equals("")||Petugas.getText().equals("")){
           JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
        }else{
            if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try{
                        Sequel.AutoComitFalse();
                        sukses=true;
                        status="";
                        ttljmdokter=0;ttljmpetugas=0;ttlkso=0;ttlpendapatan=0;ttlbhp=0;ttljasasarana=0;ttljmperujuk=0;ttlmenejemen=0;
                        ttljmdokter=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_dokter) from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                        ttljmpetugas=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_tindakan_petugas) from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                        ttlkso=Sequel.cariIsiAngka("select sum(periksa_radiologi.kso) from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                        ttlbhp=Sequel.cariIsiAngka("select sum(periksa_radiologi.bhp) from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                        ttlpendapatan=Sequel.cariIsiAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                        ttljasasarana=Sequel.cariIsiAngka("select sum(periksa_radiologi.bagian_rs) from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                        ttljmperujuk=Sequel.cariIsiAngka("select sum(periksa_radiologi.tarif_perujuk) from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                        ttlmenejemen=Sequel.cariIsiAngka("select sum(periksa_radiologi.menejemen) from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");

                        status=Sequel.cariIsi("select periksa_radiologi.status from periksa_radiologi where periksa_radiologi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                      "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                      "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");

                        if(Sequel.queryu2tf("delete from periksa_radiologi where periksa_radiologi.no_rawat=? and tgl_periksa=? and jam=?",3,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()
                        })==true){
                            Sequel.queryu2("delete from hasil_radiologi where no_rawat=? and tgl_periksa=? and jam=?",3,new String[]{
                                tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()
                            });
                            Sequel.queryu2("delete from gambar_radiologi where no_rawat=? and tgl_periksa=? and jam=?",3,new String[]{
                                tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()
                            });

                            ttlbhp=ttlbhp+Sequel.cariIsiAngka("select sum(beri_bhp_radiologi.total) from beri_bhp_radiologi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                      "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                      "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                            if(Sequel.queryu2tf("delete from beri_bhp_radiologi where no_rawat=? and tgl_periksa=? and jam=?",3,new String[]{
                                tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()
                            })==false){
                                sukses=false;
                            }
                        }else{
                            sukses=false;
                        } 

                        if(sukses==true){
                            if(status.equals("Ranap")){
                                Sequel.queryu("delete from tampjurnal");    
                                if(ttlpendapatan>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Radiologi_Ranap+"','Suspen Piutang Radiologi Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }     
                                    if(Sequel.menyimpantf("tampjurnal","'"+Radiologi_Ranap+"','Pendapatan Radiologi Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                              
                                }
                                if(ttljmdokter>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"','Beban Jasa Medik Dokter Radiologi Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }    
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"','Utang Jasa Medik Dokter Radiologi Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(ttljmpetugas>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"','Beban Jasa Medik Petugas Radiologi Ranap','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }    
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"','Utang Jasa Medik Petugas Radiologi Ranap','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                             
                                }
                                if(ttlbhp>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+HPP_Persediaan_Radiologi_Rawat_Inap+"','HPP Persediaan Radiologi Rawat Inap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_Persediaan_Radiologi_Rawat_Inap+"'")==false){
                                        sukses=false;
                                    }   
                                    if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Radiologi_Rawat_Inap+"','Persediaan BHP Radiologi Rawat Inap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Radiologi_Rawat_Inap+"'")==false){
                                        sukses=false;
                                    }                                
                                }
                                if(ttlkso>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Kso_Radiologi_Ranap+"','HPP Persediaan Radiologi Rawat Inap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_Kso_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }   
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Kso_Radiologi_Ranap+"','Persediaan BHP Radiologi Rawat Inap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_Kso_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                                
                                }
                                if(ttljasasarana>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ranap+"','Beban Jasa Sarana Radiologi Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }   
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ranap+"','Utang Jasa Sarana Radiologi Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                              
                                }
                                if(ttljmperujuk>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ranap+"','Beban Jasa Perujuk Radiologi Ranap','0','"+ttljmperujuk+"'","kredit=kredit+'"+(ttljmperujuk)+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }   
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ranap+"','Utang Jasa Perujuk Radiologi Ranap','"+ttljmperujuk+"','0'","debet=debet+'"+(ttljmperujuk)+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                               
                                }
                                if(ttlmenejemen>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ranap+"','Beban Jasa Menejemen Radiologi Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }     
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ranap+"','Utang Jasa Menejemen Radiologi Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                               
                                }
                                if(sukses==true){
                                    sukses=jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),"U","PEMBATALAN PEMERIKSAAN RADIOLOGI RAWAT INAP PASIEN "+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+" OLEH "+akses.getkode());
                                }  
                            }else if(status.equals("Ralan")){
                                Sequel.queryu("delete from tampjurnal");    
                                if(ttlpendapatan>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Suspen_Piutang_Radiologi_Ralan+"','Suspen Piutang Radiologi Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }     
                                    if(Sequel.menyimpantf("tampjurnal","'"+Radiologi_Ralan+"','Pendapatan Radiologi Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                              
                                }
                                if(ttljmdokter>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"','Beban Jasa Medik Dokter Radiologi Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }    
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Utang Jasa Medik Dokter Radiologi Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(ttljmpetugas>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"','Beban Jasa Medik Petugas Radiologi Ralan','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }    
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"','Utang Jasa Medik Petugas Radiologi Ralan','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                             
                                }
                                if(ttlbhp>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+HPP_Persediaan_Radiologi_Rawat_Jalan+"','HPP Persediaan Radiologi Rawat Jalan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_Persediaan_Radiologi_Rawat_Jalan+"'")==false){
                                        sukses=false;
                                    }   
                                    if(Sequel.menyimpantf("tampjurnal","'"+Persediaan_BHP_Radiologi_Rawat_Jalan+"','Persediaan BHP Radiologi Rawat Jalan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Radiologi_Rawat_Jalan+"'")==false){
                                        sukses=false;
                                    }                                
                                }
                                if(ttlkso>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Kso_Radiologi_Ralan+"','HPP Persediaan Radiologi Rawat Inap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_Kso_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }   
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Kso_Radiologi_Ralan+"','Persediaan BHP Radiologi Rawat Inap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_Kso_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                                
                                }
                                if(ttljasasarana>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ralan+"','Beban Jasa Sarana Radiologi Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }   
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ralan+"','Utang Jasa Sarana Radiologi Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                              
                                }
                                if(ttljmperujuk>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ralan+"','Beban Jasa Perujuk Radiologi Ralan','0','"+ttljmperujuk+"'","kredit=kredit+'"+(ttljmperujuk)+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }   
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ralan+"','Utang Jasa Perujuk Radiologi Ralan','"+ttljmperujuk+"','0'","debet=debet+'"+(ttljmperujuk)+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                               
                                }
                                if(ttlmenejemen>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ralan+"','Beban Jasa Menejemen Radiologi Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }     
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ralan+"','Utang Jasa Menejemen Radiologi Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                               
                                }
                                if(sukses==true){
                                    sukses=jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),"U","PEMBATALAN PEMERIKSAAN RADIOLOGI RAWAT JALAN PASIEN "+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+" OLEH "+akses.getkode()); 
                                } 
                            }
                        }

                        if(sukses==true){
                            Sequel.Commit();
                            statushasil="";
                            tampil();
                        }else{
                            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();
                    }catch(Exception e){
                        System.out.println("Notifikasi : "+e);
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                    }
                }                    
            }     
        }        
    }
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
    if(tabMode.getRowCount()!=0){
        try {
            getData();
            isPhoto();
            panggilPhoto();
            tampilOrthanc();
        } catch (java.lang.NullPointerException e) {
        }
    }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
   if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        statushasil="";
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else {
            if(Kd2.getText().equals("")||Petugas.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
            }else{
                try {
                    ps4=koneksi.prepareStatement(
                        "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,"+
                        "periksa_radiologi.dokter_perujuk,periksa_radiologi.kd_dokter,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,pasien.alamat,dokter.nm_dokter from periksa_radiologi inner join reg_periksa inner join pasien inner join petugas  inner join dokter "+
                        "on periksa_radiologi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_radiologi.nip=petugas.nip and periksa_radiologi.kd_dokter=dokter.kd_dokter where "+
                        "periksa_radiologi.tgl_periksa=? and periksa_radiologi.jam=? and periksa_radiologi.no_rawat=? group by concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam)");
                    try {
                        ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                        ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                        ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps4.executeQuery();
                        while(rs.next()){
                            Sequel.queryu("delete from temporary_radiologi");
                            koneksi.setAutoCommit(false);
                            ps2=koneksi.prepareStatement(
                                "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya from periksa_radiologi inner join jns_perawatan_radiologi "+
                                "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "+
                                "and periksa_radiologi.jam=?");   
                            try {
                                ps2.setString(1,rs.getString("no_rawat"));
                                ps2.setString(2,rs.getString("tgl_periksa"));
                                ps2.setString(3,rs.getString("jam"));
                                rs2=ps2.executeQuery();
                                ttl=0;
                                i=0;
                                while(rs2.next()){
                                    item=rs2.getDouble("biaya");
                                    ttl=ttl+item;                    
                                    Sequel.menyimpan("temporary_radiologi","'"+i+"','"+rs2.getString("nm_perawatan")+"','"+item+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Biaya Lab");
                                    i++;
                                } 
                            } catch (Exception e) {
                                System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnCetakNotaActionPerformed() ps2 : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                            i++;
                            Sequel.menyimpan("temporary_radiologi","'"+i+"','Total Biaya Pemeriksaan Radiologi','"+ttl+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Biaya Lab");
                            Valid.panggilUrl("billing/LaporanBiayaRadiologi.php?norm="+rs.getString("no_rkm_medis")+"&pasien="+rs.getString("nm_pasien").replaceAll(" ","_")
                                    +"&tanggal="+rs.getString("tgl_periksa")+"&jam="+rs.getString("jam")+"&pjlab="+rs.getString("nm_dokter").replaceAll(" ","_")
                                    +"&petugas="+rs.getString("nama").replaceAll(" ","_")+"&kasir="+Sequel.cariIsi("select pegawai.nama from pegawai where pegawai.nik=?",akses.getkode())
                                    +"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());
                            koneksi.setAutoCommit(true);  
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnCetakNotaActionPerformed() ps4 : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps4!=null){
                            ps4.close();
                        }
                    }                                        
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }         
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCetakNotaActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(Kd2.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            //////////////////////// start - tambahan code buat fetch foto radiologi by ichsan
            try {
                    ps = koneksi.prepareStatement(
                        "SELECT gambar_radiologi.lokasi_gambar FROM gambar_radiologi " +
                        "WHERE gambar_radiologi.no_rawat = ? AND gambar_radiologi.tgl_periksa = ? " +
                        "AND gambar_radiologi.jam = ?"
                    );
                        
                    if (tbDokter.getSelectedRow() != -1) {
                        ps.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        ps.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());

                        rs = ps.executeQuery();
                        lokasifile = ""; // Initialize to empty string

                        if (rs.next()) {
                            lokasifile = rs.getString("lokasi_gambar");
                        }
                
                        System.out.println("Lokasi Gambar Radiologi: " + lokasifile); // Debugging Output
                    } else {
                        System.out.println("No row selected in tbDokter.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error fetching radiology image location: " + e);
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (ps != null) ps.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing resources: " + e);
                    }
                }


            //////////////////////// end - tambahan code buat fetch foto radiologi by ichsan
            
            pemeriksaan="";
            try {
                ps2=koneksi.prepareStatement(
                            "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya,"+
                            "periksa_radiologi.kd_dokter,periksa_radiologi.nip,periksa_radiologi.proyeksi,periksa_radiologi.kV,periksa_radiologi.mAS,periksa_radiologi.FFD,"+
                            "periksa_radiologi.BSF,periksa_radiologi.inak,periksa_radiologi.jml_penyinaran,periksa_radiologi.dosis from periksa_radiologi inner join jns_perawatan_radiologi "+
                            "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "+
                            "and periksa_radiologi.jam=?"); 
                try {
                    ps2.setString(1,NoRawatDicari.getText());
                    ps2.setString(2,TglDicari.getText());
                    ps2.setString(3,JamDicari.getText());
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        pemeriksaan=rs2.getString("nm_perawatan")+", "+pemeriksaan;
                        kdpenjab=rs2.getString("kd_dokter");
                        kdpetugas=rs2.getString("nip");
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgCariPeriksaRadiologi.BtnPrint1ActionPerformed() ps2 : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Notifikasi Pemeriksaan : "+e);
            }          
            Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",NoRM,Kd2.getText());
            Sequel.cariIsi("select pasien.jk from pasien where pasien.no_rkm_medis=? ",Jk,NoRM.getText());
            Sequel.cariIsi("select pasien.umur from pasien where pasien.no_rkm_medis=?",Umur,NoRM.getText());
            Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ",Alamat,NoRM.getText());
            
            kamar=Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat='"+Kd2.getText()+"' order by kamar_inap.tgl_masuk desc limit 1");
            if(!kamar.equals("")){
                namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                            " where kamar.kd_kamar='"+kamar+"' ");            
                kamar="Kamar";
            }else if(kamar.equals("")){
                kamar="Poli";
                namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                            "where reg_periksa.no_rawat='"+Kd2.getText()+"'");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("noperiksa",Kd2.getText());
            param.put("norm",NoRM.getText());
            param.put("namapasien",Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",NoRM.getText()));
            param.put("jkel",Jk.getText());
            param.put("umur",Umur.getText());
            param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",NoRM.getText()));
            param.put("pengirim",tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString());
            param.put("tanggal",Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));
            param.put("penjab",tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString());
            param.put("petugas",tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString());
            param.put("alamat",Alamat.getText());
            param.put("kamar",kamar);
            param.put("namakamar",namakamar);
            param.put("pemeriksaan",pemeriksaan);
            param.put("jam",tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("hasil",HasilPeriksa.getText());
            param.put("photo", "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/radiologi/" + lokasifile);  //tambahan untuk ambil lokasi foto
            System.out.println("URL Image for Report: " + param.get("photo")); // Debugging Output
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));  
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdpenjab);
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?kdpenjab:finger)+"\n"+Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));  
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdpetugas);
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString()+"\nID "+(finger.equals("")?kdpetugas:finger)+"\n"+Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));  

            pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih hasil pemeriksaan..!","Hasil Pemeriksaan",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Model 1","Model 2", "Model 3 (Dengan Foto)","PDF Model 1","PDF Model 2","PDF Model 3"},"Model 1");
            switch (pilihan) {
                case "Model 1":
                      Valid.MyReport("rptPeriksaRadiologi.jasper","report","::[ Pemeriksaan Radiologi ]::",param);
                      break;
                case "Model 2":
                      Valid.MyReport("rptPeriksaRadiologi2.jasper","report","::[ Pemeriksaan Radiologi ]::",param);
                      break;
                case "Model 3 (Dengan Foto)":
                      Valid.MyReport("rptPeriksaRadiologi3.jasper","report","::[ Pemeriksaan Radiologi ]::",param);
                      break;
                case "PDF Model 1":
                      Valid.MyReportPDF("rptPeriksaRadiologi.jasper","report","::[ Pemeriksaan Radiologi ]::",param);
                      break;
                case "PDF Model 2":
                      Valid.MyReportPDF("rptPeriksaRadiologi2.jasper","report","::[ Pemeriksaan Radiologi ]::",param);
                      break;
                case "PDF Model 3":
                      Valid.MyReportPDF("rptPeriksaRadiologi3.jasper","report","::[ Pemeriksaan Radiologi ]::",param);
                      break;
            }                        
            
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
          //  Valid.pindah(evt, BtnBatal,BtnCari);
        }
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Kd2.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
        }else{
            if(HasilPeriksa.getText().equals("")){
                Sequel.queryu2("delete from hasil_radiologi where no_rawat=? and tgl_periksa=? and jam=?",3,new String[]{
                    NoRawatDicari.getText(),TglDicari.getText(),JamDicari.getText()
                });
            }else{
                Sequel.queryu2("delete from hasil_radiologi where no_rawat=? and tgl_periksa=? and jam=?",3,new String[]{
                    NoRawatDicari.getText(),TglDicari.getText(),JamDicari.getText()
                });
                Sequel.menyimpan("hasil_radiologi","?,?,?,?","Hasil Pemeriksaan", 4,new String[]{
                    NoRawatDicari.getText(),TglDicari.getText(),JamDicari.getText(),HasilPeriksa.getText()
                });
                if(akses.getjml2()>=1){
                    NmDokterPj.setText(dokter.tampil3(akses.getkode()));
                    if(!NmDokterPj.getText().equals("")){
                        if(Sequel.queryu2tf("update periksa_radiologi set kd_dokter=? where no_rawat=? and tgl_periksa=? and jam=?",4,new String[]{
                            akses.getkode(),NoRawatDicari.getText(),TglDicari.getText(),JamDicari.getText()
                        })==true){
                            tampil();
                        } 
                    }
                }
            }   
            JOptionPane.showMessageDialog(null,"Proses update selesai...!!!!"); 
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void PenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenjabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenjabKeyPressed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(tbDokter.getSelectedRow()>-1){
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().equals("")){
                Sequel.queryu2("update periksa_radiologi set nip=?,dokter_perujuk=?,kd_dokter=? where no_rawat=? and tgl_periksa=? and jam=?",6,new String[]{
                    KdPtgUbah.getText(),KodePerujuk.getText(),KodePj.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()
                }); 
                statushasil="";
                tampil();
                dokter.dispose();
                petugas.dispose();
                WindowGantiDokterParamedis.dispose();
            }
        }                
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        dokter.dispose();
        petugas.dispose();
        WindowGantiDokterParamedis.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void btnDokterPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterPjActionPerformed
        pilihan="penjab";
        akses.setform("DlgCariPeriksaRadiologi");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterPjActionPerformed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        pilihan="perujuk";
        akses.setform("DlgCariPeriksaRadiologi");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void KodePerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePerujukKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPerujuk.setText(dokter.tampil3(KodePerujuk.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,KdPtgUbah,BtnSimpan4);
        }
    }//GEN-LAST:event_KodePerujukKeyPressed

    private void KdPtgUbahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPtgUbahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPtgUbah.setText(petugas.tampil3(KdPtgUbah.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,KodePj,KodePerujuk);
        }
    }//GEN-LAST:event_KdPtgUbahKeyPressed

    private void btnPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugas1ActionPerformed
        akses.setform("DlgCariPeriksaRadiologi");
        pilihan="ubahpetugas";
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugas1ActionPerformed

    private void MnUbahDokterPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUbahDokterPetugasActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else {
            if(Kd2.getText().equals("")||Petugas.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
            }else{
                if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh diubah.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                }else{
                    panggilMedis();
                }
            } 
        }
    }//GEN-LAST:event_MnUbahDokterPetugasActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else {
            if(Kd2.getText().equals("")||Petugas.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
            }else{
                DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
                berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
                try {
                    if(akses.gethapus_berkas_digital_perawatan()==true){
                        berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                    }else{
                        berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                    }   
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }

                berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                berkas.setLocationRelativeTo(internalFrame1);
                berkas.setVisible(true);
            } 
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPhotoActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else {
            if(Kd2.getText().equals("")||Petugas.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.panggilUrl("radiologi/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+NoRawatDicari.getText()+"&tanggal="+TglDicari.getText()+"&jam="+JamDicari.getText());
                this.setCursor(Cursor.getDefaultCursor()); 
            }         
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAmbilPhotoActionPerformed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void btnAmbilPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPhoto1ActionPerformed
        akses.setform("DlgCariPeriksaRadiologi");
        MasterCariTemplateHasilRadiologi templatehasil=new MasterCariTemplateHasilRadiologi(null,false);
        templatehasil.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPeriksaRadiologi")){
                    if(templatehasil.getTable().getSelectedRow()!= -1){                   
                        HasilPeriksa.setText(templatehasil.getTable().getValueAt(templatehasil.getTable().getSelectedRow(),2).toString());
                    } 
                    HasilPeriksa.requestFocus();
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
        templatehasil.emptTeks();
        templatehasil.isCek();
        templatehasil.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        templatehasil.setLocationRelativeTo(internalFrame1);
        templatehasil.setVisible(true);
    }//GEN-LAST:event_btnAmbilPhoto1ActionPerformed

    private void btnDicomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicomActionPerformed
        if(tabModeDicom.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else {
            if(tbListDicom.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                OrthancDICOM orthan=new OrthancDICOM(null,false);
                orthan.setJudul("::[ DICOM Orthanc Pasien "+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+", Series "+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString()+" ]::",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().replaceAll("/","")+"_"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().replaceAll(" ","_").replaceAll("/","").replaceAll(":","").replaceAll(",",""),tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString());
                try {
                    System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/web-viewer/app/viewer.html?series="+tbListDicom.getValueAt(tbListDicom.getSelectedRow(),2).toString());
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

    private void tbListDicomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListDicomMouseClicked
        if(tabModeDicom.getRowCount()!=0){
            if(evt.getClickCount()==2){
                btnDicomActionPerformed(null);
            }
        }
    }//GEN-LAST:event_tbListDicomMouseClicked

    private void tbListDicomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbListDicomKeyPressed
        if(tabModeDicom.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                btnDicomActionPerformed(null);
            }
        }
    }//GEN-LAST:event_tbListDicomKeyPressed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else {
            if(Kd2.getText().equals("")||Petugas.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
                pilihan=Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?",Kd2.getText());
                resume.setNoRm(pilihan,tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().replaceAll(pilihan+" ",""));
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            } 
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void ppSudahKeluarBacaanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSudahKeluarBacaanBtnPrintActionPerformed
        statushasil="concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam) in (select concat(hasil_radiologi.no_rawat,hasil_radiologi.tgl_periksa,hasil_radiologi.jam) from hasil_radiologi) and ";
        tampil();
    }//GEN-LAST:event_ppSudahKeluarBacaanBtnPrintActionPerformed

    private void ppBelumKeluarBacaanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBelumKeluarBacaanBtnPrintActionPerformed
        statushasil="concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam) not in (select concat(hasil_radiologi.no_rawat,hasil_radiologi.tgl_periksa,hasil_radiologi.jam) from hasil_radiologi) and ";
        tampil();
    }//GEN-LAST:event_ppBelumKeluarBacaanBtnPrintActionPerformed

    private void UploadRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadRadiologiActionPerformed
        FileName = "Berkas_Radiologi_" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().replaceAll("/", "") + "_" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString().replaceAll(" ", "");
        CreatePDF(FileName);
        String filePath = "tmpPDF/" + FileName;
        UploadPDF(FileName, "berkasrawat/pages/upload/");
        HapusPDF();
        ppBerkasDigitalBtnPrintActionPerformed(evt);
    }//GEN-LAST:event_UploadRadiologiActionPerformed

    private void CreatePDF(String FileName) {   //isi kode ini, mengambil dari isi ppSEPBtnPrintActionPerformed (button untuk cetak PDF dari SEP / form apa pun itu)
        if(Kd2.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            //////////////////////// start - tambahan code buat fetch foto radiologi by ichsan
            try {
                    ps = koneksi.prepareStatement(
                        "SELECT gambar_radiologi.lokasi_gambar FROM gambar_radiologi " +
                        "WHERE gambar_radiologi.no_rawat = ? AND gambar_radiologi.tgl_periksa = ? " +
                        "AND gambar_radiologi.jam = ?"
                    );
                        
                    if (tbDokter.getSelectedRow() != -1) {
                        ps.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        ps.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());

                        rs = ps.executeQuery();
                        lokasifile = ""; // Initialize to empty string

                        if (rs.next()) {
                            lokasifile = rs.getString("lokasi_gambar");
                        }
                
                        System.out.println("Lokasi Gambar Radiologi: " + lokasifile); // Debugging Output
                    } else {
                        System.out.println("No row selected in tbDokter.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error fetching radiology image location: " + e);
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (ps != null) ps.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing resources: " + e);
                    }
                }


            //////////////////////// end - tambahan code buat fetch foto radiologi by ichsan
            
            pemeriksaan="";
            try {
                ps2=koneksi.prepareStatement(
                            "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya,"+
                            "periksa_radiologi.kd_dokter,periksa_radiologi.nip,periksa_radiologi.proyeksi,periksa_radiologi.kV,periksa_radiologi.mAS,periksa_radiologi.FFD,"+
                            "periksa_radiologi.BSF,periksa_radiologi.inak,periksa_radiologi.jml_penyinaran,periksa_radiologi.dosis from periksa_radiologi inner join jns_perawatan_radiologi "+
                            "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "+
                            "and periksa_radiologi.jam=?"); 
                try {
                    ps2.setString(1,NoRawatDicari.getText());
                    ps2.setString(2,TglDicari.getText());
                    ps2.setString(3,JamDicari.getText());
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        pemeriksaan=rs2.getString("nm_perawatan")+", "+pemeriksaan;
                        kdpenjab=rs2.getString("kd_dokter");
                        kdpetugas=rs2.getString("nip");
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgCariPeriksaRadiologi.BtnPrint1ActionPerformed() ps2 : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Notifikasi Pemeriksaan : "+e);
            }          
            Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",NoRM,Kd2.getText());
            Sequel.cariIsi("select pasien.jk from pasien where pasien.no_rkm_medis=? ",Jk,NoRM.getText());
            Sequel.cariIsi("select pasien.umur from pasien where pasien.no_rkm_medis=?",Umur,NoRM.getText());
            Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ",Alamat,NoRM.getText());
            
            kamar=Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat='"+Kd2.getText()+"' order by kamar_inap.tgl_masuk desc limit 1");
            if(!kamar.equals("")){
                namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                            " where kamar.kd_kamar='"+kamar+"' ");            
                kamar="Kamar";
            }else if(kamar.equals("")){
                kamar="Poli";
                namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                            "where reg_periksa.no_rawat='"+Kd2.getText()+"'");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("noperiksa",Kd2.getText());
            param.put("norm",NoRM.getText());
            param.put("namapasien",Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",NoRM.getText()));
            param.put("jkel",Jk.getText());
            param.put("umur",Umur.getText());
            param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",NoRM.getText()));
            param.put("pengirim",tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString());
            param.put("tanggal",Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));
            param.put("penjab",tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString());
            param.put("petugas",tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString());
            param.put("alamat",Alamat.getText());
            param.put("kamar",kamar);
            param.put("namakamar",namakamar);
            param.put("pemeriksaan",pemeriksaan);
            param.put("jam",tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("hasil",HasilPeriksa.getText());
            param.put("photo", "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/radiologi/" + lokasifile);  //tambahan untuk ambil lokasi foto
            System.out.println("URL Image for Report: " + param.get("photo")); // Debugging Output
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));  
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdpenjab);
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?kdpenjab:finger)+"\n"+Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));  
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdpetugas);
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString()+"\nID "+(finger.equals("")?kdpetugas:finger)+"\n"+Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));  
            
            Valid.MyReportPDFUpload("rptPeriksaRadiologi3.jasper","report","::[ Pemeriksaan Radiologi ]::",FileName,param);
                     
                                  
            
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void UploadRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UploadRadiologiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UploadRadiologiKeyPressed

    private void TombolWAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TombolWAActionPerformed
        try {  //////////////// start - fungsi untuk cek ke database.xml, kalau disetting yes pada WA Notif Pasien,  maka jalankan script untuk kirim WA - ichsan
            if(koneksiDB.WANOTIFPASIEN().equals("yes")){
                FileName = "RAD_" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString().replaceAll(":", "") + "_" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().replaceAll("/", "") + "_" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString().replaceAll("[/()\\-:, ]", "");
                CreatePDFWA(FileName);
                String filePath = "tmpPDF/" + FileName;
                UploadPDF2(FileName, "media/");
                HapusPDF();
                JOptionPane.showMessageDialog(null, "OK, ditunggu sampai hasil Radiologi-nya dikirim via WA ke nomor hp pasien yah~  ;-)");
            }else{
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "WA Gateway-nya belum disetting, pack..!");
        } ////////////////////// end - fungsi untuk cek ke database.xml, kalau disetting yes pada WA Notif Pasien,  maka jalankan script untuk kirim WA - ichsan
    }//GEN-LAST:event_TombolWAActionPerformed

    private void NoHPWAPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoHPWAPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoHPWAPasienKeyPressed

    private void CreatePDFWA(String FileName) {
     if(Kd2.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu...!!!!"); 
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            //////////////////////// start - tambahan code buat fetch foto radiologi by ichsan
            try {
                    ps = koneksi.prepareStatement(
                        "SELECT gambar_radiologi.lokasi_gambar FROM gambar_radiologi " +
                        "WHERE gambar_radiologi.no_rawat = ? AND gambar_radiologi.tgl_periksa = ? " +
                        "AND gambar_radiologi.jam = ?"
                    );
                        
                    if (tbDokter.getSelectedRow() != -1) {
                        ps.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        ps.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());

                        rs = ps.executeQuery();
                        lokasifile = ""; // Initialize to empty string

                        if (rs.next()) {
                            lokasifile = rs.getString("lokasi_gambar");
                        }
                
                        System.out.println("Lokasi Gambar Radiologi: " + lokasifile); // Debugging Output
                    } else {
                        System.out.println("No row selected in tbDokter.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error fetching radiology image location: " + e);
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (ps != null) ps.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing resources: " + e);
                    }
                }
            //////////////////////// end - tambahan code buat fetch foto radiologi by ichsan
            
            pemeriksaan="";
            try {
                ps2=koneksi.prepareStatement(
                            "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya,"+
                            "periksa_radiologi.kd_dokter,periksa_radiologi.nip,periksa_radiologi.proyeksi,periksa_radiologi.kV,periksa_radiologi.mAS,periksa_radiologi.FFD,"+
                            "periksa_radiologi.BSF,periksa_radiologi.inak,periksa_radiologi.jml_penyinaran,periksa_radiologi.dosis from periksa_radiologi inner join jns_perawatan_radiologi "+
                            "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "+
                            "and periksa_radiologi.jam=?"); 
                try {
                    ps2.setString(1,NoRawatDicari.getText());
                    ps2.setString(2,TglDicari.getText());
                    ps2.setString(3,JamDicari.getText());
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        pemeriksaan=rs2.getString("nm_perawatan")+", "+pemeriksaan;
                        kdpenjab=rs2.getString("kd_dokter");
                        kdpetugas=rs2.getString("nip");
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgCariPeriksaRadiologi.BtnPrint1ActionPerformed() ps2 : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Notifikasi Pemeriksaan : "+e);
            }          
            Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",NoRM,Kd2.getText());
            Sequel.cariIsi("select pasien.jk from pasien where pasien.no_rkm_medis=? ",Jk,NoRM.getText());
            Sequel.cariIsi("select pasien.umur from pasien where pasien.no_rkm_medis=?",Umur,NoRM.getText());
            Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ",Alamat,NoRM.getText());
            
            kamar=Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat='"+Kd2.getText()+"' order by kamar_inap.tgl_masuk desc limit 1");
            if(!kamar.equals("")){
                namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                            " where kamar.kd_kamar='"+kamar+"' ");            
                kamar="Kamar";
            }else if(kamar.equals("")){
                kamar="Poli";
                namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                            "where reg_periksa.no_rawat='"+Kd2.getText()+"'");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("noperiksa",Kd2.getText());
            param.put("norm",NoRM.getText());
            param.put("namapasien",Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",NoRM.getText()));
            param.put("jkel",Jk.getText());
            param.put("umur",Umur.getText());
            param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",NoRM.getText()));
            param.put("pengirim",tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString());
            param.put("tanggal",Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));
            param.put("penjab",tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString());
            param.put("petugas",tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString());
            param.put("alamat",Alamat.getText());
            param.put("kamar",kamar);
            param.put("namakamar",namakamar);
            param.put("pemeriksaan",pemeriksaan);
            param.put("jam",tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("hasil",HasilPeriksa.getText());
            param.put("photo", "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/radiologi/" + lokasifile);  //tambahan untuk ambil lokasi foto
            System.out.println("URL Image for Report: " + param.get("photo")); // Debugging Output
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));  
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdpenjab);
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?kdpenjab:finger)+"\n"+Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));  
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdpetugas);
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString()+"\nID "+(finger.equals("")?kdpetugas:finger)+"\n"+Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()));  
            
            Valid.MyReportPDFUpload("rptPeriksaRadiologi3.jasper","report","::[ Pemeriksaan Radiologi ]::",FileName,param);
                     
                                  
            
            this.setCursor(Cursor.getDefaultCursor());
        }
     }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPeriksaRadiologi dialog = new DlgCariPeriksaRadiologi(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.CekBox ChkAccor;
    private widget.TextBox DiagnosisKlinis;
    private widget.PanelBiasa FormHasilRadiologi;
    private widget.panelisi FormInput;
    private widget.PanelBiasa FormOrthan;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.TextArea HasilPeriksa;
    private widget.TextBox InformasiTambahan;
    private widget.Label JamDicari;
    private widget.TextBox Jk;
    private widget.TextBox Kd2;
    private widget.TextBox KdPtgUbah;
    private widget.TextBox KodePerujuk;
    private widget.TextBox KodePj;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnCetakNota;
    private javax.swing.JMenuItem MnUbahDokterPetugas;
    private widget.TextBox NmDokterPj;
    private widget.TextBox NmPerujuk;
    private widget.TextBox NmPtgUbah;
    private widget.TextBox NoHPWAPasien;
    private widget.TextBox NoRM;
    private widget.TextBox NoRawat;
    private widget.Label NoRawatDicari;
    private widget.PanelBiasa PanelAccor;
    private widget.panelisi PanelDataDicari;
    private widget.Label PasienDicari;
    private widget.TextBox Penjab;
    private widget.TextBox Petugas;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabData;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Label TglDicari;
    private widget.Button TombolWA;
    private widget.TextBox Umur;
    private widget.Button UploadRadiologi;
    private javax.swing.JDialog WindowGantiDokterParamedis;
    private widget.Button btnAmbilPhoto;
    private widget.Button btnAmbilPhoto1;
    private widget.Button btnDicom;
    private widget.Button btnDokter;
    private widget.Button btnDokterPj;
    private widget.Button btnPasien;
    private widget.Button btnPetugas;
    private widget.Button btnPetugas1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel12;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdmem;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label22;
    private widget.Label label9;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi8;
    private javax.swing.JMenuItem ppBelumKeluarBacaan;
    private javax.swing.JMenuItem ppBerkasDigital;
    private javax.swing.JMenuItem ppRiwayat;
    private javax.swing.JMenuItem ppSudahKeluarBacaan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    private widget.Table tbListDicom;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            Valid.tabelKosong(tabMode);   
            if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_radiologi.tgl_periksa,"+
                        "periksa_radiologi.jam,periksa_radiologi.dokter_perujuk,periksa_radiologi.kd_dokter,penjab.png_jawab,dokter.nm_dokter "+
                        "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter where "+statushasil+
                        "periksa_radiologi.tgl_periksa between ? and ? group by concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam) "+
                        "order by periksa_radiologi.tgl_periksa desc,periksa_radiologi.jam desc");
            }else{
                ps=koneksi.prepareStatement(
                        "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_radiologi.tgl_periksa,"+
                        "periksa_radiologi.jam,periksa_radiologi.dokter_perujuk,periksa_radiologi.kd_dokter,penjab.png_jawab,dokter.nm_dokter "+
                        "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter where "+statushasil+
                        "periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.no_rawat like ? and reg_periksa.no_rkm_medis like ? "+
                        "and petugas.nip like ? and (pasien.nm_pasien like ? or petugas.nama like ? or reg_periksa.no_rkm_medis like ? or penjab.png_jawab like ? ) "+
                        "group by concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam) "+
                        "order by periksa_radiologi.tgl_periksa desc,periksa_radiologi.jam desc");
            }
                
            try {
                if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+NoRawat.getText()+"%");
                    ps.setString(4,"%"+kdmem.getText()+"%");
                    ps.setString(5,"%"+kdptg.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                ttl=0;
                while(rs.next()){
                    kamar=Sequel.cariIsi("select ifnull(kamar_inap.kd_kamar,'') from kamar_inap where kamar_inap.no_rawat='"+rs.getString("no_rawat")+"' order by kamar_inap.tgl_masuk desc limit 1");
                    if(!kamar.equals("")){
                        namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                    " where kamar.kd_kamar='"+kamar+"' ");            
                        kamar="Kamar";
                    }else if(kamar.equals("")){
                        kamar="Poli";
                        namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                    }
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+kamar+" : "+namakamar+")",rs.getString("nama"),
                        rs.getString("tgl_periksa"),rs.getString("jam"),dokter.tampil3(rs.getString("dokter_perujuk")),rs.getString("nm_dokter")
                    });
                    tabMode.addRow(new Object[]{"","Proyeksi & Dosis Radiasi","Kode Periksa","Nama Pemeriksaan","Biaya","Cara Bayar : "+rs.getString("png_jawab"),""});
                    ps2=koneksi.prepareStatement(
                            "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya,"+"concat("+
                            "if(periksa_radiologi.proyeksi<>'',concat('Proyeksi : ',periksa_radiologi.proyeksi,', '),''),"+
                            "if(periksa_radiologi.kV<>'',concat('kV : ',periksa_radiologi.kV,', '),''),"+
                            "if(periksa_radiologi.mAS<>'',concat('mAS : ',periksa_radiologi.mAS,', '),''),"+
                            "if(periksa_radiologi.FFD<>'',concat('FFD : ',periksa_radiologi.FFD,', '),''),"+
                            "if(periksa_radiologi.BSF<>'',concat('BSF : ',periksa_radiologi.BSF,', '),''),"+
                            "if(periksa_radiologi.inak<>'',concat('Inak : ',periksa_radiologi.inak,', '),''),"+
                            "if(periksa_radiologi.jml_penyinaran<>'',concat('Jml Penyinaran : ',periksa_radiologi.jml_penyinaran,', '),''),"+
                            "if(periksa_radiologi.dosis<>'',concat('Dosis Radiasi : ',periksa_radiologi.dosis),'')) as proyeksi from periksa_radiologi "+
                            "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "+
                            "and periksa_radiologi.jam=?"); 
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        ps2.setString(2,rs.getString("tgl_periksa"));
                        ps2.setString(3,rs.getString("jam"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){  
                            ttl=ttl+rs2.getDouble("biaya");
                            tabMode.addRow(new Object[]{"",rs2.getString("proyeksi"),rs2.getString("kd_jenis_prw"),rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("biaya")),"",""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    ps3=koneksi.prepareStatement(
                            "select beri_bhp_radiologi.kode_brng,ipsrsbarang.nama_brng,beri_bhp_radiologi.kode_sat,beri_bhp_radiologi.jumlah, "+
                            "beri_bhp_radiologi.total from beri_bhp_radiologi inner join ipsrsbarang on ipsrsbarang.kode_brng=beri_bhp_radiologi.kode_brng "+
                            "where beri_bhp_radiologi.no_rawat=? and beri_bhp_radiologi.tgl_periksa=? and beri_bhp_radiologi.jam=?");  
                    try {
                        ps3.setString(1,rs.getString("no_rawat"));
                        ps3.setString(2,rs.getString("tgl_periksa"));
                        ps3.setString(3,rs.getString("jam"));
                        rs3=ps3.executeQuery();
                        rs3.last();
                        if(rs3.getRow()>0){
                            tabMode.addRow(new Object[]{"","","Kode BHP","Nama BHP","Satuan","Jumlah",""});
                            rs3.beforeFirst();
                            while(rs3.next()){  
                                tabMode.addRow(new Object[]{"","",rs3.getString("kode_brng"),rs3.getString("nama_brng"),rs3.getString("kode_sat"),rs3.getString("jumlah"),""});
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif ps3 : "+e);
                    } finally{
                        if(rs3!=null){
                            rs3.close();
                        }
                        if(ps3!=null){
                            ps3.close();
                        }
                    }    
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() PS : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                
            if(ttl>0){
                tabMode.addRow(new Object[]{">>","Total : "+Valid.SetAngka(ttl),"","","","",""});
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
    
    public void SetNoRw(String norw){
        NoRawat.setText(norw);
        statushasil="";
        tampil();
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norw+"'", Tgl1);
    }
    
    private void getData() {
        Kd2.setText("");
        
        if (tbDokter.getSelectedRow() != -1) {  //tambahan buat memunculkan nomor wa pasien
        String noRawat = tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(); // Get no_rawat
        NoHPWAPasien.setText(Sequel.cariIsi(
            "SELECT pasien.no_tlp FROM reg_periksa " +
            "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
            "WHERE reg_periksa.no_rawat = ?", 
            noRawat // Use noRawat instead of TNoRw
        ));
        }
        
        if(tbDokter.getSelectedRow()!= -1){
            Kd2.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());     
            Petugas.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString());            
        }
    }
    
    public void isCek(){
        MnCetakNota.setEnabled(akses.getperiksa_radiologi());
        BtnHapus.setEnabled(akses.getperiksa_radiologi());
        MnUbahDokterPetugas.setEnabled(akses.getperiksa_radiologi());
        btnAmbilPhoto.setEnabled(akses.getperiksa_radiologi());
        BtnSimpan.setEnabled(akses.getperiksa_radiologi());
        BtnPrint.setEnabled(akses.getperiksa_radiologi());
        ppRiwayat.setEnabled(akses.getresume_pasien());
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());     
    }
 
    public void setPasien(String pasien){
        NoRawat.setText(pasien);
    }

    private void panggilMedis() {
        try {
            ps5=koneksi.prepareStatement(
                "select periksa_radiologi.nip,petugas.nama,periksa_radiologi.dokter_perujuk,"+
                "periksa_radiologi.kd_dokter,dokter.nm_dokter from periksa_radiologi "+
                "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                "where periksa_radiologi.no_rawat=? "+
                "and periksa_radiologi.tgl_periksa=? and periksa_radiologi.jam=?");  
            try {
                ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                rs5=ps5.executeQuery();
                if(rs5.next()){      
                    KodePerujuk.setText(rs5.getString("dokter_perujuk"));
                    NmPerujuk.setText(dokter.tampil3(rs5.getString("dokter_perujuk")));
                    KodePj.setText(rs5.getString("kd_dokter"));
                    NmDokterPj.setText(rs5.getString("nm_dokter"));
                    KdPtgUbah.setText(rs5.getString("nip"));
                    NmPtgUbah.setText(rs5.getString("nama"));
                    WindowGantiDokterParamedis.setSize(600,130);
                    WindowGantiDokterParamedis.setLocationRelativeTo(internalFrame1);
                    WindowGantiDokterParamedis.setVisible(true);
                } 
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs5!=null){
                    rs5.close();
                }
                if(ps5!=null){
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
 
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            PanelDataDicari.setVisible(true);
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame1.getWidth()-300,HEIGHT));
            TabData.setVisible(true);  
            ChkAccor.setVisible(true);
            InformasiTambahan.setSize(new Dimension(internalFrame1.getWidth()-473,23));
            DiagnosisKlinis.setSize(new Dimension(internalFrame1.getWidth()-473,23));
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            InformasiTambahan.setSize(new Dimension(15,23));
            DiagnosisKlinis.setSize(new Dimension(15,23));
            TabData.setVisible(false);
            PanelDataDicari.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilPhoto() {
        if(TabData.isVisible()==true){
            if(tbDokter.getSelectedRow()!= -1){
                if((!Kd2.getText().equals(""))&&(!Petugas.getText().equals(""))){
                     NoRawatDicari.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                     PasienDicari.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString());
                     TglDicari.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                     JamDicari.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                     try {
                        ps=koneksi.prepareStatement("select gambar_radiologi.lokasi_gambar from gambar_radiologi where gambar_radiologi.no_rawat=? and gambar_radiologi.tgl_periksa=? and gambar_radiologi.jam=? ");
                        try {
                            ps.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs=ps.executeQuery();
                            htmlContent = new StringBuilder();
                            while(rs.next()){
                                htmlContent.append("<tr><td border='0' align='center'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/radiologi/"+rs.getString("lokasi_gambar")+"'><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/radiologi/"+rs.getString("lokasi_gambar")+"' alt='photo' width='"+(internalFrame1.getWidth()-370)+"' height='"+(internalFrame1.getWidth()-370)+"'/></a></td></tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='1px' class='tbl_form'>"+
                                    htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );
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
                        
                        ps5=koneksi.prepareStatement("select permintaan_radiologi.informasi_tambahan,permintaan_radiologi.diagnosa_klinis from permintaan_radiologi where permintaan_radiologi.no_rawat=? and permintaan_radiologi.tgl_hasil=? and permintaan_radiologi.jam_hasil=?");  
                        try {
                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs5=ps5.executeQuery();
                            if(rs5.next()){  
                                InformasiTambahan.setText(rs5.getString("informasi_tambahan"));
                                DiagnosisKlinis.setText(rs5.getString("diagnosa_klinis"));
                            }else{
                                InformasiTambahan.setText("");
                                DiagnosisKlinis.setText("");
                            }
                        } catch (Exception e) {
                            System.out.println("Notif ps5 : "+e);
                        } finally{
                            if(rs5!=null){
                                rs5.close();
                            }
                            if(ps5!=null){
                                ps5.close();
                            }
                        }
                        
                        ps5=koneksi.prepareStatement("select hasil_radiologi.hasil from hasil_radiologi where hasil_radiologi.no_rawat=? and hasil_radiologi.tgl_periksa=? and hasil_radiologi.jam=?");  
                        try {
                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs5=ps5.executeQuery();
                            if(rs5.next()){  
                                HasilPeriksa.setText(rs5.getString("hasil"));
                            }else{
                                HasilPeriksa.setText("");
                            }
                        } catch (Exception e) {
                            System.out.println("Notif ps5 : "+e);
                        } finally{
                            if(rs5!=null){
                                rs5.close();
                            }
                            if(ps5!=null){
                                ps5.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } 
                }else{
                    NoRawatDicari.setText("");
                    PasienDicari.setText("");
                    TglDicari.setText("");
                    JamDicari.setText("");
                    LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'></font></center></body></html>");
                    HasilPeriksa.setText("");
                }
            }
        }
    }

    private void tampilOrthanc() {
        if(TabData.isVisible()==true){
            if(tbDokter.getSelectedRow()!= -1){
                if((!Kd2.getText().equals(""))&&(!Petugas.getText().equals(""))){
                     if(TabData.getSelectedIndex()==2){
                         try {
                             Valid.tabelKosong(tabModeDicom);
                             ApiOrthanc orthanc=new ApiOrthanc();
                             root=orthanc.AmbilSeries(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()),Valid.SetTgl(Tgl1.getSelectedItem()+"").replaceAll("-",""),Valid.SetTgl(Tgl2.getSelectedItem()+"").replaceAll("-",""));
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
    }
    
    ////////////////////// start - fungsi upload pdf by ichsan
    private void UploadPDF(String FileName, String docpath) {
        try {
            File file = new File("tmpPDF/" + FileName + ".pdf");
            byte[] data = FileUtils.readFileToByteArray(file);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/upload.php?doc=" + docpath);
            ByteArrayBody fileData = new ByteArrayBody(data, FileName + ".pdf");
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);

            // Menyimpan ke database
            boolean uploadSuccess = false;
            kodeberkas = Sequel.cariIsi("SELECT kode FROM master_berkas_digital WHERE nama LIKE '%Klaim%'");
            if (Sequel.cariInteger("SELECT COUNT(no_rawat) AS jumlah FROM berkas_digital_perawatan WHERE lokasi_file='pages/upload/" + FileName + ".pdf'") > 0) {
                uploadSuccess = Sequel.mengedittf("berkas_digital_perawatan", "lokasi_file=?","no_rawat=?,kode=?, lokasi_file=?", 4, new String[]{
                    tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().trim(),kodeberkas,"pages/upload/" + FileName + ".pdf", "pages/upload/" + FileName + ".pdf"
                });
            } else {
                uploadSuccess = Sequel.menyimpantf("berkas_digital_perawatan", "?,?,?", "No.Rawat", 3, new String[]{
                    tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().trim(), kodeberkas, "pages/upload/" + FileName + ".pdf"
                });
            }

            // Menampilkan notifikasi
            if (uploadSuccess) {
                JOptionPane.showMessageDialog(null, "Upload berhasil!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Upload gagal disimpan ke database.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Upload error: " + e);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat upload: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void HapusPDF() {
        File file = new File("tmpPDF");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }
    
    
    private void UploadPDF2(String FileName, String docpath) {
    try {
        
        // Step 1: Fetch patient data (phone number, gender, and name)
        //String nohppasien = ""; 
        String nohppasien = NoHPWAPasien.getText(); 
        String jk = "";
        String noRawat = tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString();
        String noRkmMedis = "";
        String nmPasien = "";        
        try {
            PreparedStatement ps1 = koneksi.prepareStatement(
              //"SELECT pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_tlp, pasien.jk " +
                "SELECT pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk " +
                "FROM reg_periksa " +
                "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis " +
              //"WHERE no_tlp IS NOT NULL and reg_periksa.no_rawat = ?"
                "WHERE reg_periksa.no_rawat = ?"
            );
            ps1.setString(1, noRawat);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                noRkmMedis = rs1.getString("no_rkm_medis");
                nmPasien = rs1.getString("nm_pasien");
                //nohppasien = rs1.getString("no_tlp");  //diubah metodenya mengambil dari textbox
                jk = rs1.getString("jk");
                
                // Validation: Ensure the phone number is valid
                nohppasien = nohppasien.replaceAll("\\s+", ""); // remove all whitespace
                // Validation: Check if phone number is at least 9 digits and contains only numbers
                 if (nohppasien == null || nohppasien.length() < 9 || !nohppasien.trim().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Nomor HP tidak sesuai! (" + nohppasien + ")", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                    return; // Stop execution if phone number is invalid
                    }

                // Convert phone number from 08xxxxxx to 628xxxxxx
                if (nohppasien.startsWith("0")) {
                    nohppasien = "62" + nohppasien.substring(1);
                }
            }

            rs1.close();
            ps1.close();
        } catch (Exception e) {
            System.out.println("Error fetching patient data: " + e);
        }
        
        
         // Step 2: Open the file
        File file = new File("tmpPDF/" + FileName + ".pdf");
        FileInputStream fis = new FileInputStream(file);
        
         // Step 3: Create HTTP request using DefaultHttpClient (same as UploadPDF)
        HttpClient httpClient = new DefaultHttpClient();
        String uploadURL = "http://" + koneksiDB.IPFOLDERFILEWA() + ":" +
                           koneksiDB.PORTWEBWA() + "/" +
                           koneksiDB.FOLDERFILEWA() + "/upload.php?doc=" + docpath;
        HttpPost postRequest = new HttpPost(uploadURL);
        System.out.println("Uploading to: " + uploadURL);  //debugging untuk meliat url di atas, sudah benar atau belum

        // Step 4: Build multipart request
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.addPart("file", new InputStreamBody(fis, "application/pdf", FileName + ".pdf"));
        postRequest.setEntity(reqEntity);

        // Step 5: Execute HTTP request
        HttpResponse response = httpClient.execute(postRequest);

         // Step 6: Check response
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("File uploaded successfully.");
        } else {
            System.out.println("File upload failed. Response: " + response.getStatusLine().getStatusCode());
        }

        // Step 7: Close resources
        fis.close();

        // Step 8: Add greeting based on time of day
        int currentHour = java.time.LocalTime.now().getHour();
        String greeting;

        if (currentHour >= 4 && currentHour <= 10) {
            greeting = "Selamat Pagi";
        } else if (currentHour >= 10 && currentHour <= 15) {
            greeting = "Selamat Siang";
        } else if (currentHour >= 15 && currentHour <= 18) {
            greeting = "Selamat Sore";
        } else {
            greeting = "Selamat Malam";
        }

        // Step 9: Format WhatsApp message
        String salampembuka = greeting + ", " + ("L".equalsIgnoreCase(jk) ? "Bpk " : "P".equalsIgnoreCase(jk) ? "Ibu " : "Bpk / Ibu ") + nmPasien + " (" + noRkmMedis + ")\n \n";
        String pesan = salampembuka + "Terima kasih telah melakukan Pemeriksaan Radiologi di " + akses.getnamars() + ".\n \n" +
            "Berikut kami kirimkan berkas PDF untuk hasil pemeriksaannya. \n" +
            "Silakan unduh file terlampir. \n \n"+
            "Terima kasih atas perhatiannya. \n Salam sehat. \n \n" +
            "*Unit Radiologi " + akses.getnamars() + "*";        
        
        // Step 11: Insert message into WA outbox
        // KODE UNTUK KIRIM WA BY ICHSAN
        String waktukirim = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));        
    try {
        String sql = "INSERT INTO wa_outbox (NOMOR, NOWA, PESAN, TANGGAL_JAM, STATUS, SOURCE, SENDER, SUCCESS, RESPONSE, REQUEST, TYPE, FILE) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setLong(1, 0);
            ps.setString(2, nohppasien + "@c.us");
            ps.setString(3, pesan);
            ps.setString(4, waktukirim);
            ps.setString(5, "ANTRIAN");
            ps.setString(6, "KHANZA");
            ps.setString(7, "NODEJS");
            ps.setString(8, "");
            ps.setString(9, "");
            ps.setString(10, "");
            ps.setString(11, "FILE");
            ps.setString(12, FileName + ".pdf");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
        
    } catch (Exception e) {
        System.out.println("Upload error: " + e);
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat upload: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
    } 
    
    ////////////////////// end - fungsi upload pdf dan kirim pdf via WA  by ichsan
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rekammedis;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Files;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import kepegawaian.DlgCariDokter;
import laporan.DlgBerkasRawat;
import laporan.DlgDiagnosaPenyakit;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.apache.hc.core5.http.io.entity.StringEntity;


/**
 *
 * @author perpustakaan
 */
public final class RMDataResumePasienRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0;    
    private DlgCariDokter dokter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private String kodekamar="",namakamar="",tglkeluar="",jamkeluar="",finger="",json;
    private ObjectMapper mapper= new ObjectMapper();
    private JsonNode root;
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataResumePasienRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        WindowURLSertisign.setSize(570,100);
        WindowPhrase.setSize(320,100);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Kode Dokter","Dokter Penanggung Jawab","Kode Pengirim","Dokter Pegirim",
            "Kode Kamar","Kamar/Ruang/Bangsal","Tgl.Masuk","Jam Masuk","Tgl.Keluar","Jam Keluar","Diagnosa Awal Masuk","Alasan Masuk Dirawat",
            "Keluhan Utama Riwayat Penyakit","Pemeriksaan Fisik","Jalannya Penyakit Selama Perawatan","Pemeriksaan Penunjang Rad Terpenting",
            "Pemeriksaan Penunjang Lab Terpenting","Tindakan/Operasi Selama Perawatan","Obat-obatan Selama Perawatan","Diagnosa Utama",
            "ICD10 Utama","Diagnosa Sekunder 1","ICD10 Sek 1","Diagnosa Sekunder 2","ICD10 Sek 2","Diagnosa Sekunder 3","ICD10 Sek 3",
            "Diagnosa Sekunder 4","ICD10 Sek 4","Prosedur Utama","ICD9 Utama","Prosedur Sekunder 1","ICD9 Sek1","Prosedur Sekunder 2",
            "ICD9 Sek2","Prosedur Sekunder 3","ICD9 Sek3","Alergi Obat","Diet","Hasil Lab Yang Belum Selesai (Pending)",
            "Instruksi/Anjuran Dan Edukasi (Follow Up)","Keadaan Pulang","Ket.Keadaan Pulang","Cara Keluar","Ket.Cara Keluar","Dilanjutkan",
            "Ket.Dilanjutkan","Kontrol Kembali","Obat Pulang","Kode Bayar","Cara Bayar"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 54; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(65);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(65);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(200);
            }else if(i==16){
                column.setPreferredWidth(250);
            }else if(i==17){
                column.setPreferredWidth(250);
            }else if(i==18){
                column.setPreferredWidth(250);
            }else if(i==19){
                column.setPreferredWidth(250);
            }else if(i==20){
                column.setPreferredWidth(250);
            }else if(i==21){
                column.setPreferredWidth(250);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(75);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(75);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(75);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(75);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(75);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(75);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(75);
            }else if(i==36){
                column.setPreferredWidth(150);
            }else if(i==37){
                column.setPreferredWidth(75);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(75);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(250);
            }else if(i==42){
                column.setPreferredWidth(250);
            }else if(i==43){
                column.setPreferredWidth(250);
            }else if(i==44){
                column.setPreferredWidth(90);
            }else if(i==45){
                column.setPreferredWidth(120);
            }else if(i==46){
                column.setPreferredWidth(100);
            }else if(i==47){
                column.setPreferredWidth(120);
            }else if(i==48){
                column.setPreferredWidth(90);
            }else if(i==49){
                column.setPreferredWidth(120);
            }else if(i==50){
                column.setPreferredWidth(120);
            }else if(i==51){
                column.setPreferredWidth(250);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        DiagnosaAwal.setDocument(new batasInput((int)70).getKata(DiagnosaAwal));
        Alasan.setDocument(new batasInput((int)70).getKata(Alasan));
        KeluhanUtama.setDocument(new batasInput((int)2000).getKata(KeluhanUtama));
        PemeriksaanFisik.setDocument(new batasInput((int)2000).getKata(PemeriksaanFisik));
        JalannyaPenyakit.setDocument(new batasInput((int)2000).getKata(JalannyaPenyakit));
        PemeriksaanRad.setDocument(new batasInput((int)2000).getKata(PemeriksaanRad));
        HasilLaborat.setDocument(new batasInput((int)2000).getKata(HasilLaborat));
        TindakanSelamaDiRS.setDocument(new batasInput((int)2000).getKata(TindakanSelamaDiRS));
        ObatSelamaDiRS.setDocument(new batasInput((int)2000).getKata(ObatSelamaDiRS));
        DiagnosaUtama.setDocument(new batasInput((int)80).getKata(DiagnosaUtama));
        DiagnosaSekunder1.setDocument(new batasInput((int)80).getKata(DiagnosaSekunder1));
        DiagnosaSekunder2.setDocument(new batasInput((int)80).getKata(DiagnosaSekunder2));
        DiagnosaSekunder3.setDocument(new batasInput((int)80).getKata(DiagnosaSekunder3));
        DiagnosaSekunder4.setDocument(new batasInput((int)80).getKata(DiagnosaSekunder4));
        ProsedurUtama.setDocument(new batasInput((int)80).getKata(ProsedurUtama));
        ProsedurSekunder1.setDocument(new batasInput((int)80).getKata(ProsedurSekunder1));
        ProsedurSekunder2.setDocument(new batasInput((int)80).getKata(ProsedurSekunder2));
        ProsedurSekunder3.setDocument(new batasInput((int)80).getKata(ProsedurSekunder3));
        KodeDiagnosaUtama.setDocument(new batasInput((int)10).getKata(KodeDiagnosaUtama));
        KodeDiagnosaSekunder1.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder1));
        KodeDiagnosaSekunder2.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder2));
        KodeDiagnosaSekunder3.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder3));
        KodeDiagnosaSekunder4.setDocument(new batasInput((int)10).getKata(KodeDiagnosaSekunder4));
        KodeProsedurUtama.setDocument(new batasInput((int)8).getKata(KodeProsedurUtama));
        KodeProsedurSekunder1.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder1));
        KodeProsedurSekunder2.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder2));
        KodeProsedurSekunder3.setDocument(new batasInput((int)8).getKata(KodeProsedurSekunder3));
        Alergi.setDocument(new batasInput((int)100).getKata(Alergi));
        Diet.setDocument(new batasInput((int)2000).getKata(Diet));
        LabBelum.setDocument(new batasInput((int)2000).getKata(LabBelum));
        Edukasi.setDocument(new batasInput((int)2000).getKata(Edukasi));
        KetKeadaanPulang.setDocument(new batasInput((int)50).getKata(KetKeadaanPulang));
        KetKeluar.setDocument(new batasInput((int)50).getKata(KetKeluar));
        KetDilanjutkan.setDocument(new batasInput((int)50).getKata(KetDilanjutkan));
        ObatPulang.setDocument(new batasInput((int)2000).getKata(ObatPulang));
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
        
        ChkInput.setSelected(false);
        isForm();
      
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
        MnLaporanResume = new javax.swing.JMenuItem();
        MnLaporanResumeESign = new javax.swing.JMenuItem();
        MnLaporanResumeSertisign = new javax.swing.JMenuItem();
        MnInputDiagnosa = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        WindowURLSertisign = new javax.swing.JDialog();
        internalFrame9 = new widget.InternalFrame();
        jLabel43 = new widget.Label();
        panelisi6 = new widget.panelisi();
        BtnCloseUrl = new widget.Button();
        BtnBukaURL = new widget.Button();
        jLabel44 = new widget.Label();
        URLSertisign = new widget.TextBox();
        BtnDownloadFile = new widget.Button();
        BtnDownloadBukaFile = new widget.Button();
        WindowPhrase = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        jLabel45 = new widget.Label();
        panelisi5 = new widget.panelisi();
        BtnClosePhrase = new widget.Button();
        BtnSimpanTandaTangan = new widget.Button();
        jLabel46 = new widget.Label();
        Phrase = new widget.TextBox();
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
        jLabel25 = new widget.Label();
        DiagnosaSekunder2 = new widget.TextBox();
        jLabel26 = new widget.Label();
        DiagnosaUtama = new widget.TextBox();
        jLabel27 = new widget.Label();
        DiagnosaSekunder3 = new widget.TextBox();
        jLabel28 = new widget.Label();
        DiagnosaSekunder4 = new widget.TextBox();
        scrollPane2 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel5 = new widget.Label();
        jLabel8 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        PemeriksaanFisik = new widget.TextArea();
        jLabel9 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanRad = new widget.TextArea();
        jLabel10 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        HasilLaborat = new widget.TextArea();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        DiagnosaSekunder1 = new widget.TextBox();
        jLabel31 = new widget.Label();
        KodeDiagnosaUtama = new widget.TextBox();
        KodeDiagnosaSekunder1 = new widget.TextBox();
        KodeDiagnosaSekunder2 = new widget.TextBox();
        KodeDiagnosaSekunder3 = new widget.TextBox();
        KodeDiagnosaSekunder4 = new widget.TextBox();
        jLabel32 = new widget.Label();
        ProsedurUtama = new widget.TextBox();
        KodeProsedurUtama = new widget.TextBox();
        ProsedurSekunder1 = new widget.TextBox();
        jLabel33 = new widget.Label();
        KodeProsedurSekunder1 = new widget.TextBox();
        jLabel34 = new widget.Label();
        ProsedurSekunder2 = new widget.TextBox();
        KodeProsedurSekunder2 = new widget.TextBox();
        KodeProsedurSekunder3 = new widget.TextBox();
        ProsedurSekunder3 = new widget.TextBox();
        jLabel35 = new widget.Label();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        BtnDokter1 = new widget.Button();
        BtnDokter2 = new widget.Button();
        BtnDokter3 = new widget.Button();
        jLabel37 = new widget.Label();
        CaraKeluar = new widget.ComboBox();
        jLabel36 = new widget.Label();
        Keadaan = new widget.ComboBox();
        BtnDokter5 = new widget.Button();
        jLabel15 = new widget.Label();
        KdRuang = new widget.TextBox();
        jLabel16 = new widget.Label();
        Masuk = new widget.TextBox();
        jLabel17 = new widget.Label();
        Keluar = new widget.TextBox();
        jLabel18 = new widget.Label();
        JamMasuk = new widget.TextBox();
        jLabel20 = new widget.Label();
        JamKeluar = new widget.TextBox();
        jLabel22 = new widget.Label();
        KdPj = new widget.TextBox();
        label15 = new widget.Label();
        KodeDokterPengirim = new widget.TextBox();
        NamaDokterPengirim = new widget.TextBox();
        jLabel23 = new widget.Label();
        Alasan = new widget.TextBox();
        jLabel24 = new widget.Label();
        DiagnosaAwal = new widget.TextBox();
        jLabel12 = new widget.Label();
        BtnDokter16 = new widget.Button();
        scrollPane7 = new widget.ScrollPane();
        TindakanSelamaDiRS = new widget.TextArea();
        jLabel38 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel39 = new widget.Label();
        KetKeluar = new widget.TextBox();
        jLabel40 = new widget.Label();
        BtnDokter17 = new widget.Button();
        scrollPane8 = new widget.ScrollPane();
        Diet = new widget.TextArea();
        jLabel41 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        KetKeadaanPulang = new widget.TextBox();
        jLabel42 = new widget.Label();
        DIlanjutkan = new widget.ComboBox();
        KetDilanjutkan = new widget.TextBox();
        Kontrol = new widget.Tanggal();
        label13 = new widget.Label();
        label16 = new widget.Label();
        CaraBayar = new widget.TextBox();
        NmRuang = new widget.TextBox();
        BtnDokter18 = new widget.Button();
        scrollPane10 = new widget.ScrollPane();
        LabBelum = new widget.TextArea();
        jLabel11 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        JalannyaPenyakit = new widget.TextArea();
        scrollPane11 = new widget.ScrollPane();
        ObatPulang = new widget.TextArea();
        BtnDokter19 = new widget.Button();
        BtnDokter20 = new widget.Button();
        scrollPane12 = new widget.ScrollPane();
        ObatSelamaDiRS = new widget.TextArea();
        jLabel13 = new widget.Label();
        BtnDokter6 = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Laporan Resume Pasien");
        MnLaporanResume.setName("MnLaporanResume"); // NOI18N
        MnLaporanResume.setPreferredSize(new java.awt.Dimension(250, 26));
        MnLaporanResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResume);

        MnLaporanResumeESign.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResumeESign.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResumeESign.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResumeESign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResumeESign.setText("Laporan Resume Pasien PDF E-Sign");
        MnLaporanResumeESign.setName("MnLaporanResumeESign"); // NOI18N
        MnLaporanResumeESign.setPreferredSize(new java.awt.Dimension(250, 26));
        MnLaporanResumeESign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeESignActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResumeESign);

        MnLaporanResumeSertisign.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResumeSertisign.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResumeSertisign.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResumeSertisign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResumeSertisign.setText("Laporan Resume Pasien PDF Sertisign");
        MnLaporanResumeSertisign.setName("MnLaporanResumeSertisign"); // NOI18N
        MnLaporanResumeSertisign.setPreferredSize(new java.awt.Dimension(250, 26));
        MnLaporanResumeSertisign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeSertisignActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResumeSertisign);

        MnInputDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnInputDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        MnInputDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputDiagnosa.setText("Input Diagnosa Pasien");
        MnInputDiagnosa.setName("MnInputDiagnosa"); // NOI18N
        MnInputDiagnosa.setPreferredSize(new java.awt.Dimension(250, 26));
        MnInputDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputDiagnosaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnInputDiagnosa);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(250, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBerkasDigital);

        WindowURLSertisign.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowURLSertisign.setModal(true);
        WindowURLSertisign.setName("WindowURLSertisign"); // NOI18N
        WindowURLSertisign.setUndecorated(true);
        WindowURLSertisign.setResizable(false);

        internalFrame9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ URL File Hasil Tanda Tangan Sertisign ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(new java.awt.BorderLayout());

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("%");
        jLabel43.setName("jLabel43"); // NOI18N
        internalFrame9.add(jLabel43, java.awt.BorderLayout.CENTER);

        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi6.setLayout(null);

        BtnCloseUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseUrl.setMnemonic('T');
        BtnCloseUrl.setText("Tutup");
        BtnCloseUrl.setToolTipText("Alt+T");
        BtnCloseUrl.setName("BtnCloseUrl"); // NOI18N
        BtnCloseUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseUrlActionPerformed(evt);
            }
        });
        panelisi6.add(BtnCloseUrl);
        BtnCloseUrl.setBounds(450, 40, 100, 30);

        BtnBukaURL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBukaURL.setMnemonic('B');
        BtnBukaURL.setText("Buka URL");
        BtnBukaURL.setToolTipText("Alt+B");
        BtnBukaURL.setName("BtnBukaURL"); // NOI18N
        BtnBukaURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBukaURLActionPerformed(evt);
            }
        });
        panelisi6.add(BtnBukaURL);
        BtnBukaURL.setBounds(10, 40, 105, 30);

        jLabel44.setText("URL :");
        jLabel44.setName("jLabel44"); // NOI18N
        panelisi6.add(jLabel44);
        jLabel44.setBounds(0, 10, 40, 23);

        URLSertisign.setEditable(false);
        URLSertisign.setHighlighter(null);
        URLSertisign.setName("URLSertisign"); // NOI18N
        panelisi6.add(URLSertisign);
        URLSertisign.setBounds(44, 10, 505, 23);

        BtnDownloadFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnDownloadFile.setMnemonic('D');
        BtnDownloadFile.setText("Download File");
        BtnDownloadFile.setToolTipText("Alt+D");
        BtnDownloadFile.setName("BtnDownloadFile"); // NOI18N
        BtnDownloadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDownloadFileActionPerformed(evt);
            }
        });
        panelisi6.add(BtnDownloadFile);
        BtnDownloadFile.setBounds(125, 40, 130, 30);

        BtnDownloadBukaFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/preview-16x16.png"))); // NOI18N
        BtnDownloadBukaFile.setMnemonic('F');
        BtnDownloadBukaFile.setText("Download & Buka File");
        BtnDownloadBukaFile.setToolTipText("Alt+F");
        BtnDownloadBukaFile.setName("BtnDownloadBukaFile"); // NOI18N
        BtnDownloadBukaFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDownloadBukaFileActionPerformed(evt);
            }
        });
        panelisi6.add(BtnDownloadBukaFile);
        BtnDownloadBukaFile.setBounds(265, 40, 175, 30);

        internalFrame9.add(panelisi6, java.awt.BorderLayout.CENTER);

        WindowURLSertisign.getContentPane().add(internalFrame9, java.awt.BorderLayout.CENTER);

        WindowPhrase.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPhrase.setModal(true);
        WindowPhrase.setName("WindowPhrase"); // NOI18N
        WindowPhrase.setUndecorated(true);
        WindowPhrase.setResizable(false);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ E-Sign / Tanda Tangan Elektronik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout());

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("%");
        jLabel45.setName("jLabel45"); // NOI18N
        internalFrame8.add(jLabel45, java.awt.BorderLayout.CENTER);

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi5.setLayout(null);

        BtnClosePhrase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnClosePhrase.setMnemonic('U');
        BtnClosePhrase.setText("Batal");
        BtnClosePhrase.setToolTipText("Alt+U");
        BtnClosePhrase.setName("BtnClosePhrase"); // NOI18N
        BtnClosePhrase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnClosePhraseActionPerformed(evt);
            }
        });
        panelisi5.add(BtnClosePhrase);
        BtnClosePhrase.setBounds(200, 40, 100, 30);

        BtnSimpanTandaTangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanTandaTangan.setMnemonic('S');
        BtnSimpanTandaTangan.setText("Simpan");
        BtnSimpanTandaTangan.setToolTipText("Alt+S");
        BtnSimpanTandaTangan.setName("BtnSimpanTandaTangan"); // NOI18N
        BtnSimpanTandaTangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanTandaTanganActionPerformed(evt);
            }
        });
        panelisi5.add(BtnSimpanTandaTangan);
        BtnSimpanTandaTangan.setBounds(10, 40, 100, 30);

        jLabel46.setText("Masukkan Passphrase :");
        jLabel46.setName("jLabel46"); // NOI18N
        panelisi5.add(jLabel46);
        jLabel46.setBounds(0, 10, 130, 23);

        Phrase.setHighlighter(null);
        Phrase.setName("Phrase"); // NOI18N
        panelisi5.add(Phrase);
        Phrase.setBounds(134, 10, 160, 23);

        internalFrame8.add(panelisi5, java.awt.BorderLayout.CENTER);

        WindowPhrase.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Resume Medis Pasien Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
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

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-01-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-01-2026" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 448));
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

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 1172));
        FormInput.setLayout(null);

        jLabel4.setText("Keluhan Utama Riwayat Penyakit :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 160, 220, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(104, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(361, 10, 424, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 10, 112, 23);

        jLabel25.setText("Diagnosa Sekunder 2 :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 640, 145, 23);

        DiagnosaSekunder2.setHighlighter(null);
        DiagnosaSekunder2.setName("DiagnosaSekunder2"); // NOI18N
        DiagnosaSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder2);
        DiagnosaSekunder2.setBounds(150, 640, 520, 23);

        jLabel26.setText("Diagnosa Sekunder 3 :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 670, 145, 23);

        DiagnosaUtama.setHighlighter(null);
        DiagnosaUtama.setName("DiagnosaUtama"); // NOI18N
        DiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaUtama);
        DiagnosaUtama.setBounds(150, 580, 520, 23);

        jLabel27.setText("Diagnosa Utama :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 580, 145, 23);

        DiagnosaSekunder3.setHighlighter(null);
        DiagnosaSekunder3.setName("DiagnosaSekunder3"); // NOI18N
        DiagnosaSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder3);
        DiagnosaSekunder3.setBounds(150, 670, 520, 23);

        jLabel28.setText("Diagnosa Sekunder 4 :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 700, 145, 23);

        DiagnosaSekunder4.setHighlighter(null);
        DiagnosaSekunder4.setName("DiagnosaSekunder4"); // NOI18N
        DiagnosaSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder4KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder4);
        DiagnosaSekunder4.setBounds(150, 700, 520, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(224, 160, 561, 50);

        jLabel5.setText("No.Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 100, 23);

        jLabel8.setText("Pemeriksaan Fisik :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 217, 220, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        PemeriksaanFisik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanFisik.setColumns(20);
        PemeriksaanFisik.setRows(5);
        PemeriksaanFisik.setName("PemeriksaanFisik"); // NOI18N
        PemeriksaanFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanFisikKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(PemeriksaanFisik);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(224, 217, 561, 50);

        jLabel9.setText("Pemeriksaan Penunjang Rad Terpenting :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 331, 220, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        PemeriksaanRad.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanRad.setColumns(20);
        PemeriksaanRad.setRows(5);
        PemeriksaanRad.setName("PemeriksaanRad"); // NOI18N
        PemeriksaanRad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanRadKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(PemeriksaanRad);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(224, 331, 561, 50);

        jLabel10.setText("Pemeriksaan Penunjang Lab Terpenting :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 388, 220, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        HasilLaborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        HasilLaborat.setColumns(20);
        HasilLaborat.setRows(5);
        HasilLaborat.setName("HasilLaborat"); // NOI18N
        HasilLaborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilLaboratKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(HasilLaborat);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(224, 388, 561, 50);

        jLabel29.setText("Diagnosa Akhir :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 560, 100, 23);

        jLabel30.setText("Diagnosa Sekunder 1 :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 610, 145, 23);

        DiagnosaSekunder1.setHighlighter(null);
        DiagnosaSekunder1.setName("DiagnosaSekunder1"); // NOI18N
        DiagnosaSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder1);
        DiagnosaSekunder1.setBounds(150, 610, 520, 23);

        jLabel31.setText("Kode ICD :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(530, 560, 210, 23);

        KodeDiagnosaUtama.setHighlighter(null);
        KodeDiagnosaUtama.setName("KodeDiagnosaUtama"); // NOI18N
        KodeDiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaUtama);
        KodeDiagnosaUtama.setBounds(710, 580, 75, 23);

        KodeDiagnosaSekunder1.setHighlighter(null);
        KodeDiagnosaSekunder1.setName("KodeDiagnosaSekunder1"); // NOI18N
        KodeDiagnosaSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder1);
        KodeDiagnosaSekunder1.setBounds(710, 610, 75, 23);

        KodeDiagnosaSekunder2.setHighlighter(null);
        KodeDiagnosaSekunder2.setName("KodeDiagnosaSekunder2"); // NOI18N
        KodeDiagnosaSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder2);
        KodeDiagnosaSekunder2.setBounds(710, 640, 75, 23);

        KodeDiagnosaSekunder3.setHighlighter(null);
        KodeDiagnosaSekunder3.setName("KodeDiagnosaSekunder3"); // NOI18N
        KodeDiagnosaSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder3);
        KodeDiagnosaSekunder3.setBounds(710, 670, 75, 23);

        KodeDiagnosaSekunder4.setHighlighter(null);
        KodeDiagnosaSekunder4.setName("KodeDiagnosaSekunder4"); // NOI18N
        KodeDiagnosaSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder4KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder4);
        KodeDiagnosaSekunder4.setBounds(710, 700, 75, 23);

        jLabel32.setText("Prosedur Utama :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 730, 145, 23);

        ProsedurUtama.setHighlighter(null);
        ProsedurUtama.setName("ProsedurUtama"); // NOI18N
        ProsedurUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurUtamaKeyPressed(evt);
            }
        });
        FormInput.add(ProsedurUtama);
        ProsedurUtama.setBounds(150, 730, 520, 23);

        KodeProsedurUtama.setHighlighter(null);
        KodeProsedurUtama.setName("KodeProsedurUtama"); // NOI18N
        KodeProsedurUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurUtamaKeyPressed(evt);
            }
        });
        FormInput.add(KodeProsedurUtama);
        KodeProsedurUtama.setBounds(710, 730, 75, 23);

        ProsedurSekunder1.setHighlighter(null);
        ProsedurSekunder1.setName("ProsedurSekunder1"); // NOI18N
        ProsedurSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(ProsedurSekunder1);
        ProsedurSekunder1.setBounds(150, 760, 520, 23);

        jLabel33.setText("Prosedur Sekunder 1 :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 760, 145, 23);

        KodeProsedurSekunder1.setHighlighter(null);
        KodeProsedurSekunder1.setName("KodeProsedurSekunder1"); // NOI18N
        KodeProsedurSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(KodeProsedurSekunder1);
        KodeProsedurSekunder1.setBounds(710, 760, 75, 23);

        jLabel34.setText("Prosedur Sekunder 2 :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 790, 145, 23);

        ProsedurSekunder2.setHighlighter(null);
        ProsedurSekunder2.setName("ProsedurSekunder2"); // NOI18N
        ProsedurSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(ProsedurSekunder2);
        ProsedurSekunder2.setBounds(150, 790, 520, 23);

        KodeProsedurSekunder2.setHighlighter(null);
        KodeProsedurSekunder2.setName("KodeProsedurSekunder2"); // NOI18N
        KodeProsedurSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(KodeProsedurSekunder2);
        KodeProsedurSekunder2.setBounds(710, 790, 75, 23);

        KodeProsedurSekunder3.setHighlighter(null);
        KodeProsedurSekunder3.setName("KodeProsedurSekunder3"); // NOI18N
        KodeProsedurSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(KodeProsedurSekunder3);
        KodeProsedurSekunder3.setBounds(710, 820, 75, 23);

        ProsedurSekunder3.setHighlighter(null);
        ProsedurSekunder3.setName("ProsedurSekunder3"); // NOI18N
        ProsedurSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(ProsedurSekunder3);
        ProsedurSekunder3.setBounds(150, 820, 520, 23);

        jLabel35.setText("Prosedur Sekunder 3 :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 820, 145, 23);

        label14.setText("Dokter P.J. :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 100, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(104, 40, 100, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(206, 40, 200, 23);

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
        BtnDokter.setBounds(409, 40, 28, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('2');
        BtnDokter1.setToolTipText("Alt+2");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter1);
        BtnDokter1.setBounds(192, 186, 28, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(192, 357, 28, 23);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter3.setMnemonic('2');
        BtnDokter3.setToolTipText("Alt+2");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter3);
        BtnDokter3.setBounds(192, 414, 28, 23);

        jLabel37.setText("Cara Keluar :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(379, 1051, 70, 23);

        CaraKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Atas Izin Dokter", "Pindah RS", "Pulang Atas Permintaan Sendiri", "Lainnya" }));
        CaraKeluar.setName("CaraKeluar"); // NOI18N
        CaraKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraKeluarKeyPressed(evt);
            }
        });
        FormInput.add(CaraKeluar);
        CaraKeluar.setBounds(453, 1051, 205, 23);

        jLabel36.setText("Keadaan Pulang :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 1051, 100, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Membaik", "Sembuh", "Keadaan Khusus", "Meninggal" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        FormInput.add(Keadaan);
        Keadaan.setBounds(104, 1051, 130, 23);

        BtnDokter5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter5.setMnemonic('2');
        BtnDokter5.setToolTipText("Alt+2");
        BtnDokter5.setName("BtnDokter5"); // NOI18N
        BtnDokter5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter5);
        BtnDokter5.setBounds(192, 243, 28, 23);

        jLabel15.setText("Bangsal/Kamar :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(445, 40, 90, 23);

        KdRuang.setEditable(false);
        KdRuang.setHighlighter(null);
        KdRuang.setName("KdRuang"); // NOI18N
        FormInput.add(KdRuang);
        KdRuang.setBounds(539, 40, 75, 23);

        jLabel16.setText("Tanggal Masuk :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 100, 100, 23);

        Masuk.setEditable(false);
        Masuk.setHighlighter(null);
        Masuk.setName("Masuk"); // NOI18N
        FormInput.add(Masuk);
        Masuk.setBounds(104, 100, 80, 23);

        jLabel17.setText("Tanggal Keluar :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 130, 100, 23);

        Keluar.setEditable(false);
        Keluar.setHighlighter(null);
        Keluar.setName("Keluar"); // NOI18N
        FormInput.add(Keluar);
        Keluar.setBounds(104, 130, 80, 23);

        jLabel18.setText("Jam Masuk :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(185, 100, 70, 23);

        JamMasuk.setEditable(false);
        JamMasuk.setHighlighter(null);
        JamMasuk.setName("JamMasuk"); // NOI18N
        FormInput.add(JamMasuk);
        JamMasuk.setBounds(259, 100, 70, 23);

        jLabel20.setText("Jam Keluar :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(185, 130, 70, 23);

        JamKeluar.setEditable(false);
        JamKeluar.setHighlighter(null);
        JamKeluar.setName("JamKeluar"); // NOI18N
        FormInput.add(JamKeluar);
        JamKeluar.setBounds(259, 130, 70, 23);

        jLabel22.setText("Cara Bayar :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(445, 70, 90, 23);

        KdPj.setEditable(false);
        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N
        FormInput.add(KdPj);
        KdPj.setBounds(539, 70, 50, 23);

        label15.setText("Dokter Pengirim :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(0, 70, 100, 23);

        KodeDokterPengirim.setEditable(false);
        KodeDokterPengirim.setName("KodeDokterPengirim"); // NOI18N
        KodeDokterPengirim.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokterPengirim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterPengirimKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokterPengirim);
        KodeDokterPengirim.setBounds(104, 70, 100, 23);

        NamaDokterPengirim.setEditable(false);
        NamaDokterPengirim.setName("NamaDokterPengirim"); // NOI18N
        NamaDokterPengirim.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokterPengirim);
        NamaDokterPengirim.setBounds(206, 70, 231, 23);

        jLabel23.setText("Alasan Masuk Dirawat :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(351, 130, 120, 23);

        Alasan.setHighlighter(null);
        Alasan.setName("Alasan"); // NOI18N
        Alasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanKeyPressed(evt);
            }
        });
        FormInput.add(Alasan);
        Alasan.setBounds(475, 130, 310, 23);

        jLabel24.setText("Diagnosa Awal Masuk :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(351, 100, 120, 23);

        DiagnosaAwal.setHighlighter(null);
        DiagnosaAwal.setName("DiagnosaAwal"); // NOI18N
        DiagnosaAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaAwalKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaAwal);
        DiagnosaAwal.setBounds(475, 100, 310, 23);

        jLabel12.setText("Tindakan/Operasi Selama Perawatan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 445, 220, 23);

        BtnDokter16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter16.setMnemonic('2');
        BtnDokter16.setToolTipText("Alt+2");
        BtnDokter16.setName("BtnDokter16"); // NOI18N
        BtnDokter16.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter16ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter16);
        BtnDokter16.setBounds(192, 471, 28, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        TindakanSelamaDiRS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakanSelamaDiRS.setColumns(20);
        TindakanSelamaDiRS.setRows(5);
        TindakanSelamaDiRS.setName("TindakanSelamaDiRS"); // NOI18N
        TindakanSelamaDiRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanSelamaDiRSKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(TindakanSelamaDiRS);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(224, 445, 561, 50);

        jLabel38.setText("Alergi Obat :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 850, 100, 23);

        Alergi.setHighlighter(null);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(104, 850, 681, 23);

        jLabel39.setText("Diet :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(0, 880, 100, 23);

        KetKeluar.setHighlighter(null);
        KetKeluar.setName("KetKeluar"); // NOI18N
        KetKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKeluarKeyPressed(evt);
            }
        });
        FormInput.add(KetKeluar);
        KetKeluar.setBounds(660, 1051, 125, 23);

        jLabel40.setText("Hasil Lab Yang Belum Selesai (Pending) :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 937, 230, 23);

        BtnDokter17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter17.setMnemonic('2');
        BtnDokter17.setToolTipText("Alt+2");
        BtnDokter17.setName("BtnDokter17"); // NOI18N
        BtnDokter17.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter17ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter17);
        BtnDokter17.setBounds(202, 963, 28, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        Diet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diet.setColumns(20);
        Diet.setRows(5);
        Diet.setName("Diet"); // NOI18N
        Diet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DietKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(Diet);

        FormInput.add(scrollPane8);
        scrollPane8.setBounds(104, 880, 681, 50);

        jLabel41.setText("Instruksi/Anjuran Dan Edukasi (Follow Up) :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 994, 230, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(Edukasi);

        FormInput.add(scrollPane9);
        scrollPane9.setBounds(234, 994, 551, 50);

        KetKeadaanPulang.setHighlighter(null);
        KetKeadaanPulang.setName("KetKeadaanPulang"); // NOI18N
        KetKeadaanPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKeadaanPulangKeyPressed(evt);
            }
        });
        FormInput.add(KetKeadaanPulang);
        KetKeadaanPulang.setBounds(236, 1051, 130, 23);

        jLabel42.setText("Dilanjutkan :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 1081, 100, 23);

        DIlanjutkan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kembali Ke RS", "RS Lain", "Dokter Luar", "Puskesmes", "Lainnya" }));
        DIlanjutkan.setName("DIlanjutkan"); // NOI18N
        DIlanjutkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DIlanjutkanKeyPressed(evt);
            }
        });
        FormInput.add(DIlanjutkan);
        DIlanjutkan.setBounds(104, 1081, 130, 23);

        KetDilanjutkan.setHighlighter(null);
        KetDilanjutkan.setName("KetDilanjutkan"); // NOI18N
        KetDilanjutkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDilanjutkanKeyPressed(evt);
            }
        });
        FormInput.add(KetDilanjutkan);
        KetDilanjutkan.setBounds(236, 1081, 270, 23);

        Kontrol.setForeground(new java.awt.Color(50, 70, 50));
        Kontrol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-01-2026 18:04:27" }));
        Kontrol.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Kontrol.setName("Kontrol"); // NOI18N
        Kontrol.setOpaque(false);
        Kontrol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontrolKeyPressed(evt);
            }
        });
        FormInput.add(Kontrol);
        Kontrol.setBounds(650, 1081, 135, 23);

        label13.setText("Tanggal & Jam Kontrol :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(516, 1081, 130, 23);

        label16.setText("Obat Pulang :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(0, 1111, 100, 23);

        CaraBayar.setEditable(false);
        CaraBayar.setHighlighter(null);
        CaraBayar.setName("CaraBayar"); // NOI18N
        FormInput.add(CaraBayar);
        CaraBayar.setBounds(591, 70, 194, 23);

        NmRuang.setEditable(false);
        NmRuang.setHighlighter(null);
        NmRuang.setName("NmRuang"); // NOI18N
        FormInput.add(NmRuang);
        NmRuang.setBounds(616, 40, 169, 23);

        BtnDokter18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter18.setMnemonic('2');
        BtnDokter18.setToolTipText("Alt+2");
        BtnDokter18.setName("BtnDokter18"); // NOI18N
        BtnDokter18.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter18ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter18);
        BtnDokter18.setBounds(72, 906, 28, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        LabBelum.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        LabBelum.setColumns(20);
        LabBelum.setRows(5);
        LabBelum.setName("LabBelum"); // NOI18N
        LabBelum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabBelumKeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(LabBelum);

        FormInput.add(scrollPane10);
        scrollPane10.setBounds(234, 937, 551, 50);

        jLabel11.setText("Jalannya Penyakit Selama Perawatan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 274, 220, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        JalannyaPenyakit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JalannyaPenyakit.setColumns(20);
        JalannyaPenyakit.setRows(5);
        JalannyaPenyakit.setName("JalannyaPenyakit"); // NOI18N
        JalannyaPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalannyaPenyakitKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(JalannyaPenyakit);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(224, 274, 561, 50);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        ObatPulang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ObatPulang.setColumns(20);
        ObatPulang.setRows(5);
        ObatPulang.setName("ObatPulang"); // NOI18N
        ObatPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatPulangKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(ObatPulang);

        FormInput.add(scrollPane11);
        scrollPane11.setBounds(104, 1111, 681, 50);

        BtnDokter19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter19.setMnemonic('2');
        BtnDokter19.setToolTipText("Alt+2");
        BtnDokter19.setName("BtnDokter19"); // NOI18N
        BtnDokter19.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter19ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter19);
        BtnDokter19.setBounds(72, 1137, 28, 23);

        BtnDokter20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter20.setMnemonic('2');
        BtnDokter20.setToolTipText("Alt+2");
        BtnDokter20.setName("BtnDokter20"); // NOI18N
        BtnDokter20.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter20ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter20);
        BtnDokter20.setBounds(192, 528, 28, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        ObatSelamaDiRS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ObatSelamaDiRS.setColumns(20);
        ObatSelamaDiRS.setRows(5);
        ObatSelamaDiRS.setName("ObatSelamaDiRS"); // NOI18N
        ObatSelamaDiRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatSelamaDiRSKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(ObatSelamaDiRS);

        FormInput.add(scrollPane12);
        scrollPane12.setBounds(224, 502, 561, 50);

        jLabel13.setText("Obat-obatan Selama Perawatan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 502, 220, 23);

        BtnDokter6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter6.setMnemonic('2');
        BtnDokter6.setToolTipText("Alt+2");
        BtnDokter6.setName("BtnDokter6"); // NOI18N
        BtnDokter6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter6ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter6);
        BtnDokter6.setBounds(192, 300, 28, 23);

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
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdDokter.getText().equals("")||NmDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
        }else if(KeluhanUtama.getText().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan utama riwayat penyakit yang postif");
        }else if(JalannyaPenyakit.getText().equals("")){
            Valid.textKosong(JalannyaPenyakit,"Jalannya penyakit selama perawatan");
        }else if(DiagnosaUtama.getText().equals("")){
            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
        }else{
            if(Sequel.menyimpantf("resume_pasien_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",41,new String[]{
                    TNoRw.getText(),KdDokter.getText(),DiagnosaAwal.getText(),Alasan.getText(),KeluhanUtama.getText(),PemeriksaanFisik.getText(),JalannyaPenyakit.getText(),
                    PemeriksaanRad.getText(),HasilLaborat.getText(),TindakanSelamaDiRS.getText(),ObatSelamaDiRS.getText(),DiagnosaUtama.getText(),KodeDiagnosaUtama.getText(),
                    DiagnosaSekunder1.getText(),KodeDiagnosaSekunder1.getText(),DiagnosaSekunder2.getText(),KodeDiagnosaSekunder2.getText(),DiagnosaSekunder3.getText(),
                    KodeDiagnosaSekunder3.getText(),DiagnosaSekunder4.getText(),KodeDiagnosaSekunder4.getText(),ProsedurUtama.getText(),KodeProsedurUtama.getText(),
                    ProsedurSekunder1.getText(),KodeProsedurSekunder1.getText(),ProsedurSekunder2.getText(),KodeProsedurSekunder2.getText(),ProsedurSekunder3.getText(), 
                    KodeProsedurSekunder3.getText(),Alergi.getText(),Diet.getText(),LabBelum.getText(),Edukasi.getText(),CaraKeluar.getSelectedItem().toString(),KetKeluar.getText(),
                    Keadaan.getSelectedItem().toString(),KetKeadaanPulang.getText(),DIlanjutkan.getSelectedItem().toString(),KetDilanjutkan.getText(),
                    Valid.SetTgl(Kontrol.getSelectedItem()+"")+" "+Kontrol.getSelectedItem().toString().substring(11,19),ObatPulang.getText()
                })==true){
                    tabMode.addRow(new Object[]{
                        TNoRw.getText(),TNoRM.getText(),TPasien.getText(),KdDokter.getText(),NmDokter.getText(),KodeDokterPengirim.getText(),NamaDokterPengirim.getText(),
                        KdRuang.getText(),NmRuang.getText(),Masuk.getText(),JamMasuk.getText(),Keluar.getText(),JamKeluar.getText(),DiagnosaAwal.getText(),Alasan.getText(),
                        KeluhanUtama.getText(),PemeriksaanFisik.getText(),JalannyaPenyakit.getText(),PemeriksaanRad.getText(),HasilLaborat.getText(),TindakanSelamaDiRS.getText(),
                        ObatSelamaDiRS.getText(),DiagnosaUtama.getText(),KodeDiagnosaUtama.getText(),DiagnosaSekunder1.getText(),KodeDiagnosaSekunder1.getText(),DiagnosaSekunder2.getText(),
                        KodeDiagnosaSekunder2.getText(),DiagnosaSekunder3.getText(),KodeDiagnosaSekunder3.getText(),DiagnosaSekunder4.getText(),KodeDiagnosaSekunder4.getText(),
                        ProsedurUtama.getText(),KodeProsedurUtama.getText(),ProsedurSekunder1.getText(),KodeProsedurSekunder1.getText(),ProsedurSekunder2.getText(),KodeProsedurSekunder2.getText(),
                        ProsedurSekunder3.getText(),KodeProsedurSekunder3.getText(),Alergi.getText(),Diet.getText(),LabBelum.getText(),Edukasi.getText(),Keadaan.getSelectedItem().toString(),
                        KetKeadaanPulang.getText(),CaraKeluar.getSelectedItem().toString(),KetKeluar.getText(),DIlanjutkan.getSelectedItem().toString(),KetDilanjutkan.getText(),
                        Valid.SetTgl(Kontrol.getSelectedItem()+"")+" "+Kontrol.getSelectedItem().toString().substring(11,19),ObatPulang.getText(),KdPj.getText(),CaraBayar.getText()
                    });
                    emptTeks();
                    LCount.setText(""+tabMode.getRowCount());
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,ObatPulang,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
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
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString())){
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
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdDokter.getText().equals("")||NmDokter.getText().equals("")){
            Valid.textKosong(BtnDokter,"Dokter Penanggung Jawab");
        }else if(KeluhanUtama.getText().equals("")){
            Valid.textKosong(KeluhanUtama,"Keluhan utama riwayat penyakit yang postif");
        }else if(JalannyaPenyakit.getText().equals("")){
            Valid.textKosong(JalannyaPenyakit,"Jalannya penyakit selama perawatan");
        }else if(DiagnosaUtama.getText().equals("")){
            Valid.textKosong(DiagnosaUtama,"Diagnosa Utama");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString())){
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
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptDataResumePasienRanap.jasper","report","::[ Data Resume Pasien ]::",
                    "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,resume_pasien_ranap.kd_dokter,dokter.nm_dokter,reg_periksa.kd_dokter as kodepengirim,pengirim.nm_dokter as pengirim,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,resume_pasien_ranap.diagnosa_awal,resume_pasien_ranap.alasan,resume_pasien_ranap.keluhan_utama,resume_pasien_ranap.pemeriksaan_fisik,"+
                    "resume_pasien_ranap.jalannya_penyakit,resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,resume_pasien_ranap.tindakan_dan_operasi,resume_pasien_ranap.obat_di_rs,"+
                    "resume_pasien_ranap.diagnosa_utama,resume_pasien_ranap.kd_diagnosa_utama,resume_pasien_ranap.diagnosa_sekunder,resume_pasien_ranap.kd_diagnosa_sekunder,resume_pasien_ranap.diagnosa_sekunder2,"+
                    "resume_pasien_ranap.kd_diagnosa_sekunder2,resume_pasien_ranap.diagnosa_sekunder3,resume_pasien_ranap.kd_diagnosa_sekunder3,resume_pasien_ranap.diagnosa_sekunder4,"+
                    "resume_pasien_ranap.kd_diagnosa_sekunder4,resume_pasien_ranap.prosedur_utama,resume_pasien_ranap.kd_prosedur_utama,resume_pasien_ranap.prosedur_sekunder,resume_pasien_ranap.kd_prosedur_sekunder,"+
                    "resume_pasien_ranap.prosedur_sekunder2,resume_pasien_ranap.kd_prosedur_sekunder2,resume_pasien_ranap.prosedur_sekunder3,resume_pasien_ranap.kd_prosedur_sekunder3,resume_pasien_ranap.alergi,"+
                    "resume_pasien_ranap.diet,resume_pasien_ranap.lab_belum,resume_pasien_ranap.edukasi,resume_pasien_ranap.cara_keluar,resume_pasien_ranap.ket_keluar,resume_pasien_ranap.keadaan,"+
                    "resume_pasien_ranap.ket_keadaan,resume_pasien_ranap.dilanjutkan,resume_pasien_ranap.ket_dilanjutkan,resume_pasien_ranap.kontrol,resume_pasien_ranap.obat_pulang "+
                    "from resume_pasien_ranap inner join reg_periksa on resume_pasien_ranap.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on resume_pasien_ranap.kd_dokter=dokter.kd_dokter inner join dokter as pengirim on reg_periksa.kd_dokter=pengirim.kd_dokter "+
                    "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "resume_pasien_ranap.kd_dokter like '%"+TCari.getText().trim()+"%' or dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or resume_pasien_ranap.keadaan like '%"+TCari.getText().trim()+"%' or "+
                    "resume_pasien_ranap.kd_diagnosa_utama like '%"+TCari.getText().trim()+"%' or resume_pasien_ranap.diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                    "resume_pasien_ranap.prosedur_utama like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    "resume_pasien_ranap.kd_prosedur_utama like '%"+TCari.getText().trim()+"%')")+"order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
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
            runBackground(() ->tampil());
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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    ChkInput.setSelected(true);
                    isForm(); 
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void DiagnosaSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder2KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder1,KodeDiagnosaSekunder2);
    }//GEN-LAST:event_DiagnosaSekunder2KeyPressed

    private void DiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtamaKeyPressed
       Valid.pindah(evt,ObatSelamaDiRS,KodeDiagnosaUtama);
    }//GEN-LAST:event_DiagnosaUtamaKeyPressed

    private void DiagnosaSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder3KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder2,KodeDiagnosaSekunder3);
    }//GEN-LAST:event_DiagnosaSekunder3KeyPressed

    private void DiagnosaSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder4KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder3,KodeDiagnosaSekunder4);
    }//GEN-LAST:event_DiagnosaSekunder4KeyPressed

    private void DiagnosaSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder1KeyPressed
        Valid.pindah(evt,KodeDiagnosaUtama,KodeDiagnosaSekunder1);
    }//GEN-LAST:event_DiagnosaSekunder1KeyPressed

    private void KodeDiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaUtamaKeyPressed
        Valid.pindah(evt,DiagnosaUtama,DiagnosaSekunder1);
    }//GEN-LAST:event_KodeDiagnosaUtamaKeyPressed

    private void KodeDiagnosaSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder1KeyPressed
        Valid.pindah(evt,DiagnosaSekunder1,DiagnosaSekunder2);
    }//GEN-LAST:event_KodeDiagnosaSekunder1KeyPressed

    private void KodeDiagnosaSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder2KeyPressed
        Valid.pindah(evt,DiagnosaSekunder2,DiagnosaSekunder3);
    }//GEN-LAST:event_KodeDiagnosaSekunder2KeyPressed

    private void KodeDiagnosaSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder3KeyPressed
        Valid.pindah(evt,DiagnosaSekunder3,DiagnosaSekunder4);
    }//GEN-LAST:event_KodeDiagnosaSekunder3KeyPressed

    private void KodeDiagnosaSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder4KeyPressed
        Valid.pindah(evt,DiagnosaSekunder4,ProsedurUtama);
    }//GEN-LAST:event_KodeDiagnosaSekunder4KeyPressed

    private void ProsedurUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurUtamaKeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder4,KodeProsedurUtama);
    }//GEN-LAST:event_ProsedurUtamaKeyPressed

    private void KodeProsedurUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurUtamaKeyPressed
        Valid.pindah(evt,ProsedurUtama,ProsedurSekunder1);
    }//GEN-LAST:event_KodeProsedurUtamaKeyPressed

    private void ProsedurSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder1KeyPressed
        Valid.pindah(evt,KodeProsedurUtama,KodeProsedurSekunder1);
    }//GEN-LAST:event_ProsedurSekunder1KeyPressed

    private void KodeProsedurSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder1KeyPressed
        Valid.pindah(evt,ProsedurSekunder1,ProsedurSekunder2);
    }//GEN-LAST:event_KodeProsedurSekunder1KeyPressed

    private void ProsedurSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder2KeyPressed
        Valid.pindah(evt,KodeProsedurSekunder1,KodeProsedurSekunder2);
    }//GEN-LAST:event_ProsedurSekunder2KeyPressed

    private void KodeProsedurSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder2KeyPressed
        Valid.pindah(evt,ProsedurSekunder2,ProsedurSekunder3);
    }//GEN-LAST:event_KodeProsedurSekunder2KeyPressed

    private void KodeProsedurSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder3KeyPressed
        Valid.pindah(evt,ProsedurSekunder3,Alergi);
    }//GEN-LAST:event_KodeProsedurSekunder3KeyPressed

    private void ProsedurSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder3KeyPressed
        Valid.pindah(evt,KodeProsedurSekunder2,KodeProsedurSekunder3);
    }//GEN-LAST:event_ProsedurSekunder3KeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        Valid.pindah(evt,TCari,CaraKeluar);
    }//GEN-LAST:event_KdDokterKeyPressed

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
       Valid.pindah(evt,TCari,CaraKeluar);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Alasan,PemeriksaanFisik);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void PemeriksaanFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanFisikKeyPressed
        Valid.pindah2(evt,KeluhanUtama,JalannyaPenyakit);
    }//GEN-LAST:event_PemeriksaanFisikKeyPressed

    private void PemeriksaanRadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanRadKeyPressed
        Valid.pindah2(evt,JalannyaPenyakit,HasilLaborat);
    }//GEN-LAST:event_PemeriksaanRadKeyPressed

    private void HasilLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilLaboratKeyPressed
        Valid.pindah2(evt,PemeriksaanRad,TindakanSelamaDiRS);
    }//GEN-LAST:event_HasilLaboratKeyPressed

    private void MnLaporanResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanResumeActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),3).toString():finger)+"\n"+Valid.SetTgl3(Keluar.getText())); 
            try {
                ps=koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    ps.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                    rs=ps.executeQuery();
                    i=2;
                    while(rs.next()){
                       if(i==2){
                           finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                           param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+Valid.SetTgl3(Keluar.getText()));
                           param.put("namadokter2",rs.getString("nm_dokter")); 
                       }
                       if(i==3){
                           finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                           param.put("finger3","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+Valid.SetTgl3(Keluar.getText()));
                           param.put("namadokter3",rs.getString("nm_dokter")); 
                       }
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
            param.put("ruang",KdRuang.getText()+" "+NmRuang.getText());
            param.put("tanggalkeluar",Valid.SetTgl3(Keluar.getText()));
            param.put("jamkeluar",JamKeluar.getText());
            Valid.MyReport("rptLaporanResumeRanap.jasper","report","::[ Laporan Resume Pasien ]::",param);
        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariKeluhan carikeluhan=new RMCariKeluhan(null,false);
            carikeluhan.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(carikeluhan.getTable().getSelectedRow()!= -1){
                        KeluhanUtama.append(carikeluhan.getTable().getValueAt(carikeluhan.getTable().getSelectedRow(),2).toString()+", ");
                        KeluhanUtama.requestFocus();
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
            carikeluhan.setNoRawat(TNoRw.getText());
            carikeluhan.tampil();
            carikeluhan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carikeluhan.setLocationRelativeTo(internalFrame1);
            carikeluhan.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariHasilRadiologi cariradiologi=new RMCariHasilRadiologi(null,false);
            cariradiologi.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(cariradiologi.getTable().getSelectedRow()!= -1){
                        PemeriksaanRad.append(cariradiologi.getTable().getValueAt(cariradiologi.getTable().getSelectedRow(),2).toString()+", ");
                        PemeriksaanRad.requestFocus();
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
            cariradiologi.setNoRawat(TNoRw.getText());
            cariradiologi.tampil();
            cariradiologi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariradiologi.setLocationRelativeTo(internalFrame1);
            cariradiologi.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariHasilLaborat carilaborat=new RMCariHasilLaborat(null,false);
            carilaborat.getTable().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        if(carilaborat.getTable().getSelectedRow()!= -1){
                            HasilLaborat.append(carilaborat.getTable().getValueAt(carilaborat.getTable().getSelectedRow(),3).toString()+", ");
                            HasilLaborat.requestFocus();
                        }
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            }); 

            carilaborat.BtnKeluar.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (i= 0; i < carilaborat.getTable().getRowCount(); i++) {
                        if(carilaborat.getTable().getValueAt(i,0).toString().equals("true")){
                            HasilLaborat.append(carilaborat.getTable().getValueAt(i,3).toString()+", ");
                        }
                    }
                    HasilLaborat.requestFocus();
                }
            });
            carilaborat.setNoRawat(TNoRw.getText());
            carilaborat.tampil();
            carilaborat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carilaborat.setLocationRelativeTo(internalFrame1);
            carilaborat.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void MnInputDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputDiagnosaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            DlgDiagnosaPenyakit penyakit=new DlgDiagnosaPenyakit(null,false);
            penyakit.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    runBackground(() ->tampil());
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
            penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            penyakit.setLocationRelativeTo(internalFrame1);
            penyakit.isCek();
            penyakit.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),Sequel.cariIsi("select reg_periksa.status_lanjut from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
            penyakit.setVisible(true);
        }
    }//GEN-LAST:event_MnInputDiagnosaActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(!tbObat.getValueAt(tbObat.getSelectedRow(),0).toString().equals("")){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
                    berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
                    try {
                        if(akses.gethapus_berkas_digital_perawatan()==true){
                            berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                        }else{
                            berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                        }   
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                    }

                    berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    berkas.setLocationRelativeTo(internalFrame1);
                    berkas.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void CaraKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraKeluarKeyPressed
        Valid.pindah(evt, KetKeadaanPulang,KetKeluar);
    }//GEN-LAST:event_CaraKeluarKeyPressed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,Edukasi,KetKeadaanPulang);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void BtnDokter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter5ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariPemeriksaan caripemeriksaan=new RMCariPemeriksaan(null,false);
            caripemeriksaan.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(caripemeriksaan.getTable().getSelectedRow()!= -1){
                        PemeriksaanFisik.append(caripemeriksaan.getTable().getValueAt(caripemeriksaan.getTable().getSelectedRow(),2).toString()+", ");
                        PemeriksaanFisik.requestFocus();
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
            caripemeriksaan.setNoRawat(TNoRw.getText());
            caripemeriksaan.tampil();
            caripemeriksaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            caripemeriksaan.setLocationRelativeTo(internalFrame1);
            caripemeriksaan.setVisible(true);
        } 
    }//GEN-LAST:event_BtnDokter5ActionPerformed

    private void KodeDokterPengirimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterPengirimKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokterPengirimKeyPressed

    private void BtnDokter16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter16ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariTindakan caritindakan=new RMCariTindakan(null,false);
            caritindakan.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(caritindakan.getTable().getSelectedRow()!= -1){
                        TindakanSelamaDiRS.append(caritindakan.getTable().getValueAt(caritindakan.getTable().getSelectedRow(),2).toString()+", ");
                        TindakanSelamaDiRS.requestFocus();
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
            caritindakan.setNoRawat(TNoRw.getText());
            caritindakan.tampil();
            caritindakan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            caritindakan.setLocationRelativeTo(internalFrame1);
            caritindakan.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter16ActionPerformed

    private void TindakanSelamaDiRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanSelamaDiRSKeyPressed
        Valid.pindah2(evt,HasilLaborat,ObatSelamaDiRS);
    }//GEN-LAST:event_TindakanSelamaDiRSKeyPressed

    private void BtnDokter17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter17ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariLabPending carilabpending=new RMCariLabPending(null,false);
            carilabpending.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(carilabpending.getTable().getSelectedRow()!= -1){
                        LabBelum.append(carilabpending.getTable().getValueAt(carilabpending.getTable().getSelectedRow(),2).toString()+", ");
                        LabBelum.requestFocus();
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
            carilabpending.setNoRawat(TNoRw.getText());
            carilabpending.tampil();
            carilabpending.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            carilabpending.setLocationRelativeTo(internalFrame1);
            carilabpending.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter17ActionPerformed

    private void DietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DietKeyPressed
        Valid.pindah2(evt,Alergi,LabBelum);
    }//GEN-LAST:event_DietKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,LabBelum,Keadaan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void DIlanjutkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DIlanjutkanKeyPressed
        Valid.pindah(evt,KetKeluar,KetDilanjutkan);
    }//GEN-LAST:event_DIlanjutkanKeyPressed

    private void KontrolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontrolKeyPressed
        Valid.pindah2(evt,KetDilanjutkan,ObatPulang);
    }//GEN-LAST:event_KontrolKeyPressed

    private void BtnDokter18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter18ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariDiet caridiet=new RMCariDiet(null,false);
            caridiet.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(caridiet.getTable().getSelectedRow()!= -1){
                        Diet.append(caridiet.getTable().getValueAt(caridiet.getTable().getSelectedRow(),2).toString()+", ");
                        Diet.requestFocus();
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
            caridiet.setNoRawat(TNoRw.getText());
            caridiet.tampil();
            caridiet.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            caridiet.setLocationRelativeTo(internalFrame1);
            caridiet.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter18ActionPerformed

    private void LabBelumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabBelumKeyPressed
        Valid.pindah2(evt,Diet,Edukasi);
    }//GEN-LAST:event_LabBelumKeyPressed

    private void JalannyaPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalannyaPenyakitKeyPressed
        Valid.pindah2(evt,PemeriksaanFisik,PemeriksaanRad);
    }//GEN-LAST:event_JalannyaPenyakitKeyPressed

    private void ObatPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatPulangKeyPressed
        Valid.pindah2(evt,Kontrol,BtnSimpan);
    }//GEN-LAST:event_ObatPulangKeyPressed

    private void BtnDokter19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter19ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariObatPulang cariobatpulang=new RMCariObatPulang(null,false);
            cariobatpulang.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {}
                @Override
                public void windowClosing(WindowEvent e) {}
                @Override
                public void windowClosed(WindowEvent e) {
                    if(cariobatpulang.getTable().getSelectedRow()!= -1){
                        ObatPulang.append(cariobatpulang.getTable().getValueAt(cariobatpulang.getTable().getSelectedRow(),2).toString()+"\n");
                        ObatPulang.requestFocus();
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
            cariobatpulang.setNoRawat(TNoRw.getText());
            cariobatpulang.tampil();
            cariobatpulang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatpulang.setLocationRelativeTo(internalFrame1);
            cariobatpulang.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter19ActionPerformed

    private void DiagnosaAwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaAwalKeyPressed
        Valid.pindah(evt,TCari,Alasan);
    }//GEN-LAST:event_DiagnosaAwalKeyPressed

    private void AlasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanKeyPressed
        Valid.pindah(evt,DiagnosaAwal,KeluhanUtama);
    }//GEN-LAST:event_AlasanKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,KodeProsedurSekunder3,Diet);
    }//GEN-LAST:event_AlergiKeyPressed

    private void KetKeadaanPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKeadaanPulangKeyPressed
        Valid.pindah(evt,Keadaan,CaraKeluar);
    }//GEN-LAST:event_KetKeadaanPulangKeyPressed

    private void KetKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKeluarKeyPressed
        Valid.pindah(evt,CaraKeluar,DIlanjutkan);
    }//GEN-LAST:event_KetKeluarKeyPressed

    private void KetDilanjutkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetDilanjutkanKeyPressed
        Valid.pindah(evt,DIlanjutkan,Kontrol);
    }//GEN-LAST:event_KetDilanjutkanKeyPressed

    private void BtnDokter20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter20ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            RMCariJumlahObat cariobat=new RMCariJumlahObat(null,false);
            cariobat.getTable().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        if(cariobat.getTable().getSelectedRow()!= -1){
                            ObatSelamaDiRS.append(cariobat.getTable().getValueAt(cariobat.getTable().getSelectedRow(),3).toString()+", ");
                            ObatSelamaDiRS.requestFocus();
                        }
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            }); 

            cariobat.BtnKeluar.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (i= 0; i < cariobat.getTable().getRowCount(); i++) {
                        if(cariobat.getTable().getValueAt(i,0).toString().equals("true")){
                            ObatSelamaDiRS.append(cariobat.getTable().getValueAt(i,3).toString()+", ");
                        }
                    }
                    ObatSelamaDiRS.requestFocus();
                }
            });
            cariobat.setNoRawat(TNoRw.getText());
            cariobat.tampil();
            cariobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobat.setLocationRelativeTo(internalFrame1);
            cariobat.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter20ActionPerformed

    private void ObatSelamaDiRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatSelamaDiRSKeyPressed
        Valid.pindah2(evt,TindakanSelamaDiRS,DiagnosaUtama);
    }//GEN-LAST:event_ObatSelamaDiRSKeyPressed

    private void BtnDokter6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter6ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
        resume.setNoRm(TNoRM.getText(),TPasien.getText());
        resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        resume.setLocationRelativeTo(internalFrame1);
        resume.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnDokter6ActionPerformed

    private void MnLaporanResumeESignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanResumeESignActionPerformed
        WindowPhrase.setAlwaysOnTop(true);
        WindowPhrase.setLocationRelativeTo(internalFrame1);
        WindowPhrase.setVisible(true);
    }//GEN-LAST:event_MnLaporanResumeESignActionPerformed

    private void MnLaporanResumeSertisignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanResumeSertisignActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            param.put("finger","#1A"); 
            try {
                ps=koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    ps.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                    rs=ps.executeQuery();
                    i=2;
                    while(rs.next()){
                       if(i==2){
                           finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                           param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+Valid.SetTgl3(Keluar.getText()));
                           param.put("namadokter2",rs.getString("nm_dokter")); 
                       }
                       if(i==3){
                           finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                           param.put("finger3","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+Valid.SetTgl3(Keluar.getText()));
                           param.put("namadokter3",rs.getString("nm_dokter")); 
                       }
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
            param.put("ruang",KdRuang.getText()+" "+NmRuang.getText());
            param.put("tanggalkeluar",Valid.SetTgl3(Keluar.getText()));
            param.put("jamkeluar",JamKeluar.getText());
            Valid.MyReportPDF2("rptLaporanResumeRanap2.jasper","report","::[ Laporan Resume Pasien ]::",param);
            File f = new File("./report/rptLaporanResumeRanap2.pdf");  
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost post = new HttpPost(koneksiDB.URLAPISERTISIGN());
                post.addHeader("apikey",koneksiDB.APIKEYSERTISIGN());
                post.addHeader("Accept","application/json");
                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
                    .addBinaryBody("document", f, ContentType.APPLICATION_PDF, f.getName())
                    .addTextBody("signer",Sequel.cariIsi("select dokter.email from dokter where dokter.kd_dokter=?", akses.getkode()))
                    .addTextBody("keyword","#1A")
                    .addTextBody("qrValue","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmDokter.getText()+"\nID "+Sequel.cariIsi("select pegawai.no_ktp from pegawai where pegawai.nik=?", akses.getkode())+"\n"+Valid.SetTgl3(Keluar.getText()))
                    .addTextBody("w", "40")
                    .addTextBody("h", "40");
                HttpEntity entity = entityBuilder.build();
                post.setEntity(entity);
                System.out.println("URL Kirim File : "+koneksiDB.URLAPISERTISIGN());
                try (CloseableHttpResponse response = httpClient.execute(post)) {
                    if (response.getCode() == 200) {
                        json=EntityUtils.toString(response.getEntity());
                        System.out.println("Respon Kirim File : " + json);
                        root = mapper.readTree(json);
                        System.out.println("Id File : " +root.path("data").path("transaction_id").asText());
                        System.out.println("URL Callback File : "+koneksiDB.URLDOKUMENSERTISIGN()+"/"+root.path("data").path("transaction_id").asText()+".pdf");
                        URLSertisign.setText(koneksiDB.URLDOKUMENSERTISIGN()+"/"+root.path("data").path("transaction_id").asText()+".pdf");
                        WindowURLSertisign.setAlwaysOnTop(true);
                        WindowURLSertisign.setLocationRelativeTo(internalFrame1);
                        WindowURLSertisign.setVisible(true);
                    } else {
                        System.out.println("Notifikasi : " + EntityUtils.toString(response.getEntity()));
                    }
                } catch (Exception a) {
                    System.out.println("Notifikasi : " + a);
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }//GEN-LAST:event_MnLaporanResumeSertisignActionPerformed

    private void BtnCloseUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseUrlActionPerformed
        URLSertisign.setText("");
        WindowURLSertisign.dispose();
    }//GEN-LAST:event_BtnCloseUrlActionPerformed

    private void BtnBukaURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBukaURLActionPerformed
        try {
            Desktop.getDesktop().browse(new URI(URLSertisign.getText()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane,"File belum tersedia, silahkan tunggu beberapa saat lagi..!!");
            System.out.println("Notifikasi : " + e);
        }
    }//GEN-LAST:event_BtnBukaURLActionPerformed

    private void BtnDownloadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDownloadFileActionPerformed
        try {
            URL url = new URL(URLSertisign.getText());
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("Resume"+TNoRw.getText().trim().replaceAll("/","")+".pdf");
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
            readableByteChannel.close();
            System.out.println("Download Selesai : " + "Resume"+TNoRw.getText().trim().replaceAll("/","")+".pdf");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane,"File belum tersedia, silahkan tunggu & ulangi beberapa saat lagi..!!");
            System.out.println("Notifikasi : " + e);
        }
    }//GEN-LAST:event_BtnDownloadFileActionPerformed

    private void BtnDownloadBukaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDownloadBukaFileActionPerformed
        try {
            URL url = new URL(URLSertisign.getText());
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("Resume"+TNoRw.getText().trim().replaceAll("/","")+".pdf");
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
            readableByteChannel.close();
            System.out.println("Download Selesai : " + "Resume"+TNoRw.getText().trim().replaceAll("/","")+".pdf");
            Desktop.getDesktop().browse(new File("Resume"+TNoRw.getText().trim().replaceAll("/","")+".pdf").toURI());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane,"File belum tersedia, silahkan tunggu & ulangi beberapa saat lagi..!!");
            System.out.println("Notifikasi : " + e);
        }
    }//GEN-LAST:event_BtnDownloadBukaFileActionPerformed

    private void BtnClosePhraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClosePhraseActionPerformed
        Phrase.setText("");
        WindowPhrase.dispose();
    }//GEN-LAST:event_BtnClosePhraseActionPerformed

    private void BtnSimpanTandaTanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanTandaTanganActionPerformed
        if(Phrase.getText().equals("")){
            Valid.textKosong(Phrase,"Phrase");
        }else{
            if(tbObat.getSelectedRow()>-1){
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                param.put("finger","#"); 
                try {
                    ps=koneksi.prepareStatement("select dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? and dpjp_ranap.kd_dokter<>?");
                    try {
                        ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                        ps.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
                        rs=ps.executeQuery();
                        i=2;
                        while(rs.next()){
                           if(i==2){
                               finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                               param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+Valid.SetTgl3(Keluar.getText()));
                               param.put("namadokter2",rs.getString("nm_dokter")); 
                           }
                           if(i==3){
                               finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                               param.put("finger3","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+Valid.SetTgl3(Keluar.getText()));
                               param.put("namadokter3",rs.getString("nm_dokter")); 
                           }
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
                param.put("ruang",KdRuang.getText()+" "+NmRuang.getText());
                param.put("tanggalkeluar",Valid.SetTgl3(Keluar.getText()));
                param.put("jamkeluar",JamKeluar.getText());
                Valid.MyReportPDF2("rptLaporanResumeRanap2.jasper","report","::[ Laporan Resume Pasien ]::",param);
                File f = new File("./report/rptLaporanResumeRanap2.pdf");  
                try {
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    HttpPost post = new HttpPost(koneksiDB.URLAKSESFILEESIGN());
                    post.setHeader("Content-Type", "application/json");
                    post.addHeader("username", koneksiDB.USERNAMEAPIESIGN());
                    post.addHeader("password", koneksiDB.PASSAPIESIGN());
                    post.addHeader("url", koneksiDB.URLAPIESIGN());
                    
                    byte[] fileContent = Files.readAllBytes(f.toPath());
                    
                    json="{" +
                             "\"file\":\""+Base64.getEncoder().encodeToString(fileContent)+"\"," +
                             "\"nik\":\""+Sequel.cariIsi("select pegawai.no_ktp from pegawai where pegawai.nik=?", akses.getkode())+"\"," +
                             "\"passphrase\":\""+Phrase.getText()+"\"," +
                             "\"tampilan\":\"visible\"," +
                             "\"image\":\"false\"," +
                             "\"linkQR\":\"Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+" dan ditandatangani secara elektronik oleh "+NmDokter.getText()+" ID "+KdDokter.getText()+" Tanggal "+Valid.SetTgl3(Keluar.getText())+"\"," +
                             "\"width\":\"55\"," +
                             "\"height\":\"55\"," +
                             "\"tag_koordinat\":\"#\"" +
                          "}";
                    
                    System.out.println("URL Akses file :"+koneksiDB.URLAKSESFILEESIGN());
                    System.out.println("JSON Dikirim :"+json);
                    post.setEntity(new StringEntity(json));
                    try (CloseableHttpResponse response = httpClient.execute(post)) {
                        System.out.println("Response Status : " + response.getCode());
                        json=EntityUtils.toString(response.getEntity());
                        root = mapper.readTree(json);
                        if (response.getCode() == 200) {
                            try (FileOutputStream fos = new FileOutputStream(new File("Resume"+TNoRw.getText().trim().replaceAll("/","")+"_"+TNoRM.getText().trim().replaceAll(" ","")+"_"+TPasien.getText().trim().replaceAll(" ","")+".pdf"))) {
                                byte[] fileBytes = Base64.getDecoder().decode(root.path("response").asText());
                                fos.write(fileBytes);
                                WindowPhrase.dispose();
                                JOptionPane.showMessageDialog(null,"Proses tanda tangan berhasil...");
                                Desktop.getDesktop().browse(new File("Resume"+TNoRw.getText().trim().replaceAll("/","")+"_"+TNoRM.getText().trim().replaceAll(" ","")+"_"+TPasien.getText().trim().replaceAll(" ","")+".pdf").toURI());
                            } catch (Exception e) {
                                WindowPhrase.dispose();
                                JOptionPane.showMessageDialog(null,"Gagal mengkonversi base64 ke file...");
                                System.out.println("Notif : " +e);
                            }
                        } else {
                            WindowPhrase.dispose();
                            JOptionPane.showMessageDialog(null,"Code : "+root.path("metadata").path("code").asText()+" Pesan : "+root.path("metadata").path("message").asText());
                        }
                    } catch (IOException a) {
                        System.out.println("Notifikasi : " + a);
                        WindowPhrase.dispose();
                        JOptionPane.showMessageDialog(null,""+a);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                    WindowPhrase.dispose();
                    JOptionPane.showMessageDialog(null,""+e);
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanTandaTanganActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataResumePasienRanap dialog = new RMDataResumePasienRanap(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alasan;
    private widget.TextBox Alergi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnBukaURL;
    private widget.Button BtnCari;
    private widget.Button BtnClosePhrase;
    private widget.Button BtnCloseUrl;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter16;
    private widget.Button BtnDokter17;
    private widget.Button BtnDokter18;
    private widget.Button BtnDokter19;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter20;
    private widget.Button BtnDokter3;
    private widget.Button BtnDokter5;
    private widget.Button BtnDokter6;
    private widget.Button BtnDownloadBukaFile;
    private widget.Button BtnDownloadFile;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanTandaTangan;
    private widget.TextBox CaraBayar;
    private widget.ComboBox CaraKeluar;
    private widget.CekBox ChkInput;
    private widget.ComboBox DIlanjutkan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaAwal;
    private widget.TextBox DiagnosaSekunder1;
    private widget.TextBox DiagnosaSekunder2;
    private widget.TextBox DiagnosaSekunder3;
    private widget.TextBox DiagnosaSekunder4;
    private widget.TextBox DiagnosaUtama;
    private widget.TextArea Diet;
    private widget.TextArea Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.TextArea HasilLaborat;
    private widget.TextArea JalannyaPenyakit;
    private widget.TextBox JamKeluar;
    private widget.TextBox JamMasuk;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPj;
    private widget.TextBox KdRuang;
    private widget.ComboBox Keadaan;
    private widget.TextBox Keluar;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetDilanjutkan;
    private widget.TextBox KetKeadaanPulang;
    private widget.TextBox KetKeluar;
    private widget.TextBox KodeDiagnosaSekunder1;
    private widget.TextBox KodeDiagnosaSekunder2;
    private widget.TextBox KodeDiagnosaSekunder3;
    private widget.TextBox KodeDiagnosaSekunder4;
    private widget.TextBox KodeDiagnosaUtama;
    private widget.TextBox KodeDokterPengirim;
    private widget.TextBox KodeProsedurSekunder1;
    private widget.TextBox KodeProsedurSekunder2;
    private widget.TextBox KodeProsedurSekunder3;
    private widget.TextBox KodeProsedurUtama;
    private widget.Tanggal Kontrol;
    private widget.Label LCount;
    private widget.TextArea LabBelum;
    private widget.TextBox Masuk;
    private javax.swing.JMenuItem MnInputDiagnosa;
    private javax.swing.JMenuItem MnLaporanResume;
    private javax.swing.JMenuItem MnLaporanResumeESign;
    private javax.swing.JMenuItem MnLaporanResumeSertisign;
    private widget.TextBox NamaDokterPengirim;
    private widget.TextBox NmDokter;
    private widget.TextBox NmRuang;
    private widget.TextArea ObatPulang;
    private widget.TextArea ObatSelamaDiRS;
    private javax.swing.JPanel PanelInput;
    private widget.TextArea PemeriksaanFisik;
    private widget.TextArea PemeriksaanRad;
    private widget.TextBox Phrase;
    private widget.TextBox ProsedurSekunder1;
    private widget.TextBox ProsedurSekunder2;
    private widget.TextBox ProsedurSekunder3;
    private widget.TextBox ProsedurUtama;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextArea TindakanSelamaDiRS;
    private widget.TextBox URLSertisign;
    private javax.swing.JDialog WindowPhrase;
    private javax.swing.JDialog WindowURLSertisign;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
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
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private javax.swing.JMenuItem ppBerkasDigital;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,resume_pasien_ranap.kd_dokter,dokter.nm_dokter,reg_periksa.kd_dokter as kodepengirim,pengirim.nm_dokter as pengirim,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,resume_pasien_ranap.diagnosa_awal,resume_pasien_ranap.alasan,resume_pasien_ranap.keluhan_utama,resume_pasien_ranap.pemeriksaan_fisik,"+
                    "resume_pasien_ranap.jalannya_penyakit,resume_pasien_ranap.pemeriksaan_penunjang,resume_pasien_ranap.hasil_laborat,resume_pasien_ranap.tindakan_dan_operasi,resume_pasien_ranap.obat_di_rs,"+
                    "resume_pasien_ranap.diagnosa_utama,resume_pasien_ranap.kd_diagnosa_utama,resume_pasien_ranap.diagnosa_sekunder,resume_pasien_ranap.kd_diagnosa_sekunder,resume_pasien_ranap.diagnosa_sekunder2,"+
                    "resume_pasien_ranap.kd_diagnosa_sekunder2,resume_pasien_ranap.diagnosa_sekunder3,resume_pasien_ranap.kd_diagnosa_sekunder3,resume_pasien_ranap.diagnosa_sekunder4,"+
                    "resume_pasien_ranap.kd_diagnosa_sekunder4,resume_pasien_ranap.prosedur_utama,resume_pasien_ranap.kd_prosedur_utama,resume_pasien_ranap.prosedur_sekunder,resume_pasien_ranap.kd_prosedur_sekunder,"+
                    "resume_pasien_ranap.prosedur_sekunder2,resume_pasien_ranap.kd_prosedur_sekunder2,resume_pasien_ranap.prosedur_sekunder3,resume_pasien_ranap.kd_prosedur_sekunder3,resume_pasien_ranap.alergi,"+
                    "resume_pasien_ranap.diet,resume_pasien_ranap.lab_belum,resume_pasien_ranap.edukasi,resume_pasien_ranap.cara_keluar,resume_pasien_ranap.ket_keluar,resume_pasien_ranap.keadaan,"+
                    "resume_pasien_ranap.ket_keadaan,resume_pasien_ranap.dilanjutkan,resume_pasien_ranap.ket_dilanjutkan,resume_pasien_ranap.kontrol,resume_pasien_ranap.obat_pulang,reg_periksa.kd_pj,penjab.png_jawab "+
                    "from resume_pasien_ranap inner join reg_periksa on resume_pasien_ranap.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on resume_pasien_ranap.kd_dokter=dokter.kd_dokter inner join dokter as pengirim on reg_periksa.kd_dokter=pengirim.kd_dokter "+
                    "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj where reg_periksa.tgl_registrasi between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or resume_pasien_ranap.kd_dokter like ? or "+
                    "dokter.nm_dokter like ? or resume_pasien_ranap.keadaan like ? or resume_pasien_ranap.kd_diagnosa_utama like ? or resume_pasien_ranap.diagnosa_utama like ? or "+
                    "resume_pasien_ranap.prosedur_utama like ? or reg_periksa.no_rawat like ? or resume_pasien_ranap.kd_prosedur_utama like ?)")+
                    "order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                }

                rs=ps.executeQuery();
                while(rs.next()){
                    kodekamar="";namakamar="";tglkeluar="";jamkeluar="";
                    ps2=koneksi.prepareStatement(
                        "select if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar,"+
                        "if(kamar_inap.jam_keluar='00:00:00',current_time(),kamar_inap.jam_keluar) as jam_keluar,kamar_inap.kd_kamar,bangsal.nm_bangsal "+
                        "from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                        "where kamar_inap.no_rawat=? order by kamar_inap.tgl_keluar desc,kamar_inap.jam_keluar desc limit 1");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            kodekamar=rs2.getString("kd_kamar");
                            namakamar=rs2.getString("nm_bangsal");
                            tglkeluar=rs2.getString("tgl_keluar");
                            jamkeluar=rs2.getString("jam_keluar");
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
                    
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),
                        rs.getString("kodepengirim"),rs.getString("pengirim"),kodekamar,namakamar,rs.getString("tgl_registrasi"),rs.getString("jam_reg"),tglkeluar,
                        jamkeluar,rs.getString("diagnosa_awal"),rs.getString("alasan"),rs.getString("keluhan_utama"),rs.getString("pemeriksaan_fisik"),
                        rs.getString("jalannya_penyakit"),rs.getString("pemeriksaan_penunjang"),rs.getString("hasil_laborat"),rs.getString("tindakan_dan_operasi"),
                        rs.getString("obat_di_rs"),rs.getString("diagnosa_utama"),rs.getString("kd_diagnosa_utama"),rs.getString("diagnosa_sekunder"),
                        rs.getString("kd_diagnosa_sekunder"),rs.getString("diagnosa_sekunder2"),rs.getString("kd_diagnosa_sekunder2"),rs.getString("diagnosa_sekunder3"),
                        rs.getString("kd_diagnosa_sekunder3"),rs.getString("diagnosa_sekunder4"),rs.getString("kd_diagnosa_sekunder4"),rs.getString("prosedur_utama"),
                        rs.getString("kd_prosedur_utama"),rs.getString("prosedur_sekunder"),rs.getString("kd_prosedur_sekunder"),rs.getString("prosedur_sekunder2"),
                        rs.getString("kd_prosedur_sekunder2"),rs.getString("prosedur_sekunder3"),rs.getString("kd_prosedur_sekunder3"),rs.getString("alergi"),
                        rs.getString("diet"),rs.getString("lab_belum"),rs.getString("edukasi"),rs.getString("keadaan"),rs.getString("ket_keadaan"),
                        rs.getString("cara_keluar"),rs.getString("ket_keluar"),rs.getString("dilanjutkan"),rs.getString("ket_dilanjutkan"),rs.getString("kontrol"),
                        rs.getString("obat_pulang"),rs.getString("kd_pj"),rs.getString("png_jawab")
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
        Alasan.setText("");
        KeluhanUtama.setText("");
        PemeriksaanFisik.setText("");
        JalannyaPenyakit.setText("");
        PemeriksaanRad.setText("");
        HasilLaborat.setText("");
        TindakanSelamaDiRS.setText("");
        ObatSelamaDiRS.setText("");
        DiagnosaUtama.setText("");
        DiagnosaSekunder1.setText("");
        DiagnosaSekunder2.setText("");
        DiagnosaSekunder3.setText("");
        DiagnosaSekunder4.setText("");
        ProsedurUtama.setText("");
        ProsedurSekunder1.setText("");
        ProsedurSekunder2.setText("");
        ProsedurSekunder3.setText("");
        KodeDiagnosaUtama.setText("");
        KodeDiagnosaSekunder1.setText("");
        KodeDiagnosaSekunder2.setText("");
        KodeDiagnosaSekunder3.setText("");
        KodeDiagnosaSekunder4.setText("");
        KodeProsedurUtama.setText("");
        KodeProsedurSekunder1.setText("");
        KodeProsedurSekunder2.setText("");
        KodeProsedurSekunder3.setText("");
        Alergi.setText("");
        Diet.setText("");
        LabBelum.setText("");
        Edukasi.setText("");
        KetKeadaanPulang.setText("");
        KetKeluar.setText("");
        KetDilanjutkan.setText("");
        ObatPulang.setText("");
        Keadaan.setSelectedIndex(0);
        CaraKeluar.setSelectedIndex(0);
        DIlanjutkan.setSelectedIndex(0);
        Kontrol.setDate(new Date());
        DiagnosaAwal.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());  
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());  
            KodeDokterPengirim.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());  
            NamaDokterPengirim.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());  
            KdRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());  
            NmRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());  
            Masuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());  
            JamMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());  
            Keluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());  
            JamKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());  
            DiagnosaAwal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());  
            Alasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());  
            KeluhanUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());  
            PemeriksaanFisik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());  
            JalannyaPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());  
            PemeriksaanRad.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());  
            HasilLaborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());  
            TindakanSelamaDiRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());  
            ObatSelamaDiRS.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());  
            DiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());  
            KodeDiagnosaUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());  
            DiagnosaSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());  
            KodeDiagnosaSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());  
            DiagnosaSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());  
            KodeDiagnosaSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());  
            DiagnosaSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());  
            KodeDiagnosaSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());  
            DiagnosaSekunder4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());  
            KodeDiagnosaSekunder4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());  
            ProsedurUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());  
            KodeProsedurUtama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());  
            ProsedurSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());  
            KodeProsedurSekunder1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());  
            ProsedurSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());  
            KodeProsedurSekunder2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());  
            ProsedurSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());  
            KodeProsedurSekunder3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());  
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());  
            Diet.setText(tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());  
            LabBelum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());  
            Edukasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());  
            Keadaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());  
            KetKeadaanPulang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString()); 
            CaraKeluar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());  
            KetKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());  
            DIlanjutkan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());  
            KetDilanjutkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());   
            ObatPulang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());     
            KdPj.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());     
            CaraBayar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());  
            Valid.SetTgl2(Kontrol,tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,concat(pasien.nm_pasien,' (',reg_periksa.umurdaftar,' ',reg_periksa.sttsumur,')') as nm_pasien,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.kd_pj,penjab.png_jawab,"+
                    "if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar,"+
                    "if(kamar_inap.jam_keluar='00:00:00',current_time(),kamar_inap.jam_keluar) as jam_keluar,"+
                    "kamar_inap.diagnosa_awal,kamar_inap.kd_kamar,bangsal.nm_bangsal from reg_periksa "+
                    "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                    "inner join dokter on dokter.kd_dokter=reg_periksa.kd_dokter "+
                    "inner join penjab on penjab.kd_pj=reg_periksa.kd_pj "+
                    "inner join kamar_inap on kamar_inap.no_rawat=reg_periksa.no_rawat "+
                    "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "where reg_periksa.no_rawat=? order by kamar_inap.tgl_keluar desc,kamar_inap.jam_keluar desc limit 1");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Masuk.setText(rs.getString("tgl_registrasi"));
                    JamMasuk.setText(rs.getString("jam_reg"));
                    Keluar.setText(rs.getString("tgl_keluar"));
                    JamKeluar.setText(rs.getString("jam_keluar"));
                    DiagnosaAwal.setText(rs.getString("diagnosa_awal"));
                    KdPj.setText(rs.getString("kd_pj"));
                    CaraBayar.setText(rs.getString("png_jawab"));
                    KdRuang.setText(rs.getString("kd_kamar"));
                    NmRuang.setText(rs.getString("nm_bangsal"));
                    KodeDokterPengirim.setText(rs.getString("kd_dokter"));
                    NamaDokterPengirim.setText(rs.getString("nm_dokter"));
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
        CaraKeluar.requestFocus();
        try {
            ps=koneksi.prepareStatement(
                    "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit,diagnosa_pasien.prioritas "+
                    "from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where diagnosa_pasien.no_rawat=? and diagnosa_pasien.status='Ranap' order by diagnosa_pasien.prioritas ");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
                    if(rs.getInt("prioritas")==1){
                        KodeDiagnosaUtama.setText(rs.getString("kd_penyakit"));
                        DiagnosaUtama.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==2){
                        KodeDiagnosaSekunder1.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder1.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==3){
                        KodeDiagnosaSekunder2.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder2.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==4){
                        KodeDiagnosaSekunder3.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder3.setText(rs.getString("nm_penyakit"));
                    }
                    
                    if(rs.getInt("prioritas")==5){
                        KodeDiagnosaSekunder4.setText(rs.getString("kd_penyakit"));
                        DiagnosaSekunder4.setText(rs.getString("nm_penyakit"));
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
        
        try {
            ps=koneksi.prepareStatement(
                    "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.prioritas "+
                    "from prosedur_pasien inner join icd9 on prosedur_pasien.kode=icd9.kode "+
                    "where prosedur_pasien.no_rawat=? order by prosedur_pasien.prioritas ");
            try {
                ps.setString(1,norwt);
                rs=ps.executeQuery();
                while(rs.next()){
                    if(rs.getInt("prioritas")==1){
                        KodeProsedurUtama.setText(rs.getString("kode"));
                        ProsedurUtama.setText(rs.getString("deskripsi_panjang"));
                    }
                    
                    if(rs.getInt("prioritas")==2){
                        KodeProsedurSekunder1.setText(rs.getString("kode"));
                        ProsedurSekunder1.setText(rs.getString("deskripsi_panjang"));
                    }
                    
                    if(rs.getInt("prioritas")==3){
                        KodeProsedurSekunder2.setText(rs.getString("kode"));
                        ProsedurSekunder2.setText(rs.getString("deskripsi_panjang"));
                    }
                    
                    if(rs.getInt("prioritas")==4){
                        KodeProsedurSekunder3.setText(rs.getString("kode"));
                        ProsedurSekunder3.setText(rs.getString("deskripsi_panjang"));
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
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-122));
            scrollInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getdata_resume_pasien());
        BtnHapus.setEnabled(akses.getdata_resume_pasien());
        BtnEdit.setEnabled(akses.getdata_resume_pasien());
        BtnPrint.setEnabled(akses.getdata_resume_pasien()); 
        MnInputDiagnosa.setEnabled(akses.getdiagnosa_pasien());   
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());    
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(Sequel.CariDokter(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan dokter...!!");
            }
        }            
    }

    private void ganti() {
        if(Sequel.mengedittf("resume_pasien_ranap","no_rawat=?","no_rawat=?,kd_dokter=?,diagnosa_awal=?,alasan=?,keluhan_utama=?,pemeriksaan_fisik=?,jalannya_penyakit=?,pemeriksaan_penunjang=?,"+
                "hasil_laborat=?,tindakan_dan_operasi=?,obat_di_rs=?,diagnosa_utama=?,kd_diagnosa_utama=?,diagnosa_sekunder=?,kd_diagnosa_sekunder=?,diagnosa_sekunder2=?,kd_diagnosa_sekunder2=?,"+
                "diagnosa_sekunder3=?,kd_diagnosa_sekunder3=?,diagnosa_sekunder4=?,kd_diagnosa_sekunder4=?,prosedur_utama=?,kd_prosedur_utama=?,prosedur_sekunder=?,kd_prosedur_sekunder=?,"+
                "prosedur_sekunder2=?,kd_prosedur_sekunder2=?,prosedur_sekunder3=?,kd_prosedur_sekunder3=?,alergi=?,diet=?,lab_belum=?,edukasi=?,cara_keluar=?,ket_keluar=?,keadaan=?,"+
                "ket_keadaan=?,dilanjutkan=?,ket_dilanjutkan=?,kontrol=?,obat_pulang=?",42,new String[]{
                TNoRw.getText(),KdDokter.getText(),DiagnosaAwal.getText(),Alasan.getText(),KeluhanUtama.getText(),PemeriksaanFisik.getText(),JalannyaPenyakit.getText(),
                PemeriksaanRad.getText(),HasilLaborat.getText(),TindakanSelamaDiRS.getText(),ObatSelamaDiRS.getText(),DiagnosaUtama.getText(),KodeDiagnosaUtama.getText(),
                DiagnosaSekunder1.getText(),KodeDiagnosaSekunder1.getText(),DiagnosaSekunder2.getText(),KodeDiagnosaSekunder2.getText(),DiagnosaSekunder3.getText(),
                KodeDiagnosaSekunder3.getText(),DiagnosaSekunder4.getText(),KodeDiagnosaSekunder4.getText(),ProsedurUtama.getText(),KodeProsedurUtama.getText(),
                ProsedurSekunder1.getText(),KodeProsedurSekunder1.getText(),ProsedurSekunder2.getText(),KodeProsedurSekunder2.getText(),ProsedurSekunder3.getText(), 
                KodeProsedurSekunder3.getText(),Alergi.getText(),Diet.getText(),LabBelum.getText(),Edukasi.getText(),CaraKeluar.getSelectedItem().toString(),KetKeluar.getText(),
                Keadaan.getSelectedItem().toString(),KetKeadaanPulang.getText(),DIlanjutkan.getSelectedItem().toString(),KetDilanjutkan.getText(),
                Valid.SetTgl(Kontrol.getSelectedItem()+"")+" "+Kontrol.getSelectedItem().toString().substring(11,19),ObatPulang.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                })==true){
                   tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
                   tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
                   tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
                   tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),3);
                   tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),4);
                   tbObat.setValueAt(KodeDokterPengirim.getText(),tbObat.getSelectedRow(),5);
                   tbObat.setValueAt(NamaDokterPengirim.getText(),tbObat.getSelectedRow(),6);
                   tbObat.setValueAt(KdRuang.getText(),tbObat.getSelectedRow(),7);
                   tbObat.setValueAt(NmRuang.getText(),tbObat.getSelectedRow(),8);
                   tbObat.setValueAt(Masuk.getText(),tbObat.getSelectedRow(),9);
                   tbObat.setValueAt(JamMasuk.getText(),tbObat.getSelectedRow(),10);
                   tbObat.setValueAt(Keluar.getText(),tbObat.getSelectedRow(),11);
                   tbObat.setValueAt(JamKeluar.getText(),tbObat.getSelectedRow(),12);
                   tbObat.setValueAt(DiagnosaAwal.getText(),tbObat.getSelectedRow(),13);
                   tbObat.setValueAt(Alasan.getText(),tbObat.getSelectedRow(),14);
                   tbObat.setValueAt(KeluhanUtama.getText(),tbObat.getSelectedRow(),15);
                   tbObat.setValueAt(PemeriksaanFisik.getText(),tbObat.getSelectedRow(),16);
                   tbObat.setValueAt(JalannyaPenyakit.getText(),tbObat.getSelectedRow(),17);
                   tbObat.setValueAt(PemeriksaanRad.getText(),tbObat.getSelectedRow(),18);
                   tbObat.setValueAt(HasilLaborat.getText(),tbObat.getSelectedRow(),19);
                   tbObat.setValueAt(TindakanSelamaDiRS.getText(),tbObat.getSelectedRow(),20);
                   tbObat.setValueAt(ObatSelamaDiRS.getText(),tbObat.getSelectedRow(),21);
                   tbObat.setValueAt(DiagnosaUtama.getText(),tbObat.getSelectedRow(),22);
                   tbObat.setValueAt(KodeDiagnosaUtama.getText(),tbObat.getSelectedRow(),23);
                   tbObat.setValueAt(DiagnosaSekunder1.getText(),tbObat.getSelectedRow(),24);
                   tbObat.setValueAt(KodeDiagnosaSekunder1.getText(),tbObat.getSelectedRow(),25);
                   tbObat.setValueAt(DiagnosaSekunder2.getText(),tbObat.getSelectedRow(),26);
                   tbObat.setValueAt(KodeDiagnosaSekunder2.getText(),tbObat.getSelectedRow(),27);
                   tbObat.setValueAt(DiagnosaSekunder3.getText(),tbObat.getSelectedRow(),28);
                   tbObat.setValueAt(KodeDiagnosaSekunder3.getText(),tbObat.getSelectedRow(),29);
                   tbObat.setValueAt(DiagnosaSekunder4.getText(),tbObat.getSelectedRow(),30);
                   tbObat.setValueAt(KodeDiagnosaSekunder4.getText(),tbObat.getSelectedRow(),31);
                   tbObat.setValueAt(ProsedurUtama.getText(),tbObat.getSelectedRow(),32);
                   tbObat.setValueAt(KodeProsedurUtama.getText(),tbObat.getSelectedRow(),33);
                   tbObat.setValueAt(ProsedurSekunder1.getText(),tbObat.getSelectedRow(),34);
                   tbObat.setValueAt(KodeProsedurSekunder1.getText(),tbObat.getSelectedRow(),35);
                   tbObat.setValueAt(ProsedurSekunder2.getText(),tbObat.getSelectedRow(),36);
                   tbObat.setValueAt(KodeProsedurSekunder2.getText(),tbObat.getSelectedRow(),37);
                   tbObat.setValueAt(ProsedurSekunder3.getText(),tbObat.getSelectedRow(),38);
                   tbObat.setValueAt(KodeProsedurSekunder3.getText(),tbObat.getSelectedRow(),39);
                   tbObat.setValueAt(Alergi.getText(),tbObat.getSelectedRow(),40);
                   tbObat.setValueAt(Diet.getText(),tbObat.getSelectedRow(),41);
                   tbObat.setValueAt(LabBelum.getText(),tbObat.getSelectedRow(),42);
                   tbObat.setValueAt(Edukasi.getText(),tbObat.getSelectedRow(),43);
                   tbObat.setValueAt(Keadaan.getSelectedItem().toString(),tbObat.getSelectedRow(),44);
                   tbObat.setValueAt(KetKeadaanPulang.getText(),tbObat.getSelectedRow(),45);
                   tbObat.setValueAt(CaraKeluar.getSelectedItem().toString(),tbObat.getSelectedRow(),46);
                   tbObat.setValueAt(KetKeluar.getText(),tbObat.getSelectedRow(),47);
                   tbObat.setValueAt(DIlanjutkan.getSelectedItem().toString(),tbObat.getSelectedRow(),48);
                   tbObat.setValueAt(KetDilanjutkan.getText(),tbObat.getSelectedRow(),49);
                   tbObat.setValueAt(Valid.SetTgl(Kontrol.getSelectedItem()+"")+" "+Kontrol.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),50);
                   tbObat.setValueAt(ObatPulang.getText(),tbObat.getSelectedRow(),51);
                   tbObat.setValueAt(KdPj.getText(),tbObat.getSelectedRow(),52);
                   tbObat.setValueAt(CaraBayar.getText(),tbObat.getSelectedRow(),53);
                   emptTeks();
            }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from resume_pasien_ranap where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
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

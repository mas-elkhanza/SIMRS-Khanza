/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import simrskhanza.DlgResumePerawatan;


/**
 *
 * @author perpustakaan
 */
public final class SisruteRujukanKeluar extends javax.swing.JDialog {
    private DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,pilihan=0;
    private final Properties prop = new Properties();
    private String idrs="",StatusDirespon="",StatusDiterima="",penyakit="",penyakit2="",keluar="",link="",requestJson="",URL="",cari="",cari2="";
    private SisruteApi api=new SisruteApi();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private SisruteCekReferensiFaskes faskes=new SisruteCekReferensiFaskes(null,false);
    private SisruteCekReferensiAlasanRujuk alasanrujuk=new SisruteCekReferensiAlasanRujuk(null,false);
    private SisruteCekReferensiDiagnosa diagnosa=new SisruteCekReferensiDiagnosa(null,false);
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public SisruteRujukanKeluar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);        
   
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rujuk","No.Rawat","No.RM","No.KTP/NIK","No.Kartu JKN","Nama Pasien","J.K.",
                "Tgl.Lahir","Tempat Lahir","Alamat","Kontak","Jenis Rujukan","Tanggal Rujuk",
                "Kd.Fas.Tujuan","Nama Faskes Tujuan","Kd Alasan","Alasan Rujuk","Alasan Lainnya",
                "ICD X","Diagnosa Rujuk","NIK Dokter","Dokter Perujuk","NIK Petugas",
                "Petugas Entry","Anamnesis & Pemeriksaan Fisik","Kesadaran",
                "Tekanan Darah","Nadi","Suhu","Respirasi","Keadaan Umum","Tingkat Nyeri","Alergi",
                "Laboratorium","Radiologi","Terapi/Tindakan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 36; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(75);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(160);
            }else if(i==6){
                column.setPreferredWidth(25);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(110);
            }else if(i==9){
                column.setPreferredWidth(170);
            }else if(i==10){
                column.setPreferredWidth(85);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(120);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(160);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(40);
            }else if(i==19){
                column.setPreferredWidth(170);
            }else if(i==20){
                column.setPreferredWidth(110);
            }else if(i==21){
                column.setPreferredWidth(160);
            }else if(i==22){
                column.setPreferredWidth(110);
            }else if(i==23){
                column.setPreferredWidth(160);
            }else if(i==24){
                column.setPreferredWidth(170);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(40);
            }else if(i==28){
                column.setPreferredWidth(40);
            }else if(i==29){
                column.setPreferredWidth(55);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(90);
            }else if(i==32){
                column.setPreferredWidth(110);
            }else if(i==33){
                column.setPreferredWidth(200);
            }else if(i==34){
                column.setPreferredWidth(200);
            }else if(i==35){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Rujuk","No.RM","Nama Pasien","Nama Faskes Tujuan","Status Direspon","Status Diterima"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat2.setModel(tabMode2);

        //tbObat2.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat2.getBackground()));
        tbObat2.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbObat2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(170);
            }
        }
        tbObat2.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        AlasanLainnya.setDocument(new batasInput((byte)50).getKata(AlasanLainnya));
        Anamnesis.setDocument(new batasInput((int)700).getKata(Anamnesis));
        TekananDarah.setDocument(new batasInput((byte)7).getKata(TekananDarah));
        FrekuensiNadi.setDocument(new batasInput((byte)3).getKata(FrekuensiNadi));
        SuhuBadan.setDocument(new batasInput((byte)5).getKata(SuhuBadan));
        Respirasi.setDocument(new batasInput((byte)3).getKata(Respirasi));
        KeadaanUmum.setDocument(new batasInput((int)300).getKata(KeadaanUmum));
        Alergi.setDocument(new batasInput((byte)50).getKata(Alergi));
        Laborat.setDocument(new batasInput((int)1000).getKata(Laborat));
        Radiologi.setDocument(new batasInput((int)1000).getKata(Radiologi));
        TerapiTindakan.setDocument(new batasInput((int)1000).getKata(TerapiTindakan));
        
        if(koneksiDB.cariCepat().equals("aktif")){
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
        
        faskes.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(faskes.getTable().getSelectedRow()!= -1){  
                    KdFaskes.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),1).toString());
                    NmFaskes.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(),2).toString());
                    KdFaskes.requestFocus();                
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
        
        faskes.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    faskes.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        alasanrujuk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(alasanrujuk.getTable().getSelectedRow()!= -1){  
                    KdAlasan.setText(alasanrujuk.getTable().getValueAt(alasanrujuk.getTable().getSelectedRow(),1).toString());
                    NmAlasan.setText(alasanrujuk.getTable().getValueAt(alasanrujuk.getTable().getSelectedRow(),2).toString());
                    KdAlasan.requestFocus();                
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
        
        alasanrujuk.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    alasanrujuk.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(diagnosa.getTable().getSelectedRow()!= -1){  
                    KdDiagnosa.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),1).toString());
                    NmDiagnosa.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),2).toString());
                    KdDiagnosa.requestFocus();                
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
        
        diagnosa.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    diagnosa.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){  
                    if(pilihan==1){
                        KdDokter.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),23).toString());
                        DokterPerujuk.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                        KdDokter.requestFocus();
                    }else if(pilihan==2){
                        KdPetugas.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),23).toString());
                        PetugasEntry.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                        KdPetugas.requestFocus();
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
        
        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pegawai.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml")); 
            link=prop.getProperty("URLAPISISRUTE");
            idrs=prop.getProperty("IDSISRUTE");
        } catch (Exception e) {
            System.out.println("E : "+e);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSuratRujukan = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel22 = new widget.Label();
        TanggalRujuk = new widget.Tanggal();
        BtnFaskes = new widget.Button();
        jLabel10 = new widget.Label();
        KdFaskes = new widget.TextBox();
        NmFaskes = new widget.TextBox();
        jLabel11 = new widget.Label();
        KdAlasan = new widget.TextBox();
        NmAlasan = new widget.TextBox();
        BtnAlasan = new widget.Button();
        jLabel8 = new widget.Label();
        NoKTP = new widget.TextBox();
        jLabel9 = new widget.Label();
        NoKartu = new widget.TextBox();
        jLabel13 = new widget.Label();
        JK = new widget.TextBox();
        TglLahir = new widget.TextBox();
        jLabel14 = new widget.Label();
        TmpLahir = new widget.TextBox();
        jLabel15 = new widget.Label();
        Kontak = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        AlasanLainnya = new widget.TextBox();
        jLabel18 = new widget.Label();
        JenisRujukan = new widget.ComboBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        Alamat = new widget.TextBox();
        jLabel24 = new widget.Label();
        KdDiagnosa = new widget.TextBox();
        NmDiagnosa = new widget.TextBox();
        BtnDiagnosa = new widget.Button();
        jLabel25 = new widget.Label();
        KdDokter = new widget.TextBox();
        DokterPerujuk = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel27 = new widget.Label();
        KdPetugas = new widget.TextBox();
        PetugasEntry = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel28 = new widget.Label();
        jLabel29 = new widget.Label();
        Anamnesis = new widget.TextBox();
        jLabel36 = new widget.Label();
        JenisKesadaran = new widget.ComboBox();
        jLabel37 = new widget.Label();
        TekananDarah = new widget.TextBox();
        FrekuensiNadi = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        SuhuBadan = new widget.TextBox();
        jLabel40 = new widget.Label();
        Respirasi = new widget.TextBox();
        jLabel42 = new widget.Label();
        TingkatNyeri = new widget.ComboBox();
        jLabel43 = new widget.Label();
        KeadaanUmum = new widget.TextBox();
        Alergi = new widget.TextBox();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        Laborat = new widget.TextBox();
        jLabel47 = new widget.Label();
        Radiologi = new widget.TextBox();
        jLabel48 = new widget.Label();
        TerapiTindakan = new widget.TextBox();
        BtnCari1 = new widget.Button();
        internalFrame4 = new widget.InternalFrame();
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
        TabRujukan = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbObat2 = new widget.Table();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSuratRujukan.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratRujukan.setForeground(new java.awt.Color(70, 70, 70));
        MnSuratRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratRujukan.setText("Surat Rujukan");
        MnSuratRujukan.setName("MnSuratRujukan"); // NOI18N
        MnSuratRujukan.setPreferredSize(new java.awt.Dimension(150, 26));
        MnSuratRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratRujukanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSuratRujukan);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(150, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rujukan Keluar Sisrute ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
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

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 257));
        FormInput.setLayout(null);

        jLabel4.setText("Data Pasien :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 85, 23);

        TNoRw.setEditable(false);
        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(89, 10, 152, 23);

        TPasien.setEditable(false);
        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(355, 10, 354, 23);

        TNoRM.setEditable(false);
        TNoRM.setBackground(new java.awt.Color(245, 250, 240));
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(243, 10, 110, 23);

        jLabel22.setText("Tgl.Rujuk :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(380, 150, 90, 23);

        TanggalRujuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019 16:51:00" }));
        TanggalRujuk.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalRujuk.setName("TanggalRujuk"); // NOI18N
        TanggalRujuk.setOpaque(false);
        TanggalRujuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalRujuk);
        TanggalRujuk.setBounds(474, 150, 160, 23);

        BtnFaskes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnFaskes.setMnemonic('X');
        BtnFaskes.setToolTipText("Alt+X");
        BtnFaskes.setName("BtnFaskes"); // NOI18N
        BtnFaskes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFaskesActionPerformed(evt);
            }
        });
        BtnFaskes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnFaskesKeyPressed(evt);
            }
        });
        FormInput.add(BtnFaskes);
        BtnFaskes.setBounds(343, 180, 28, 23);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Penunjang :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(12, 408, 80, 23);

        KdFaskes.setEditable(false);
        KdFaskes.setBackground(new java.awt.Color(245, 250, 240));
        KdFaskes.setHighlighter(null);
        KdFaskes.setName("KdFaskes"); // NOI18N
        KdFaskes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdFaskesActionPerformed(evt);
            }
        });
        FormInput.add(KdFaskes);
        KdFaskes.setBounds(104, 180, 65, 23);

        NmFaskes.setEditable(false);
        NmFaskes.setBackground(new java.awt.Color(245, 250, 240));
        NmFaskes.setHighlighter(null);
        NmFaskes.setName("NmFaskes"); // NOI18N
        FormInput.add(NmFaskes);
        NmFaskes.setBounds(171, 180, 170, 23);

        jLabel11.setText("Alasan Rujuk :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(10, 210, 90, 23);

        KdAlasan.setEditable(false);
        KdAlasan.setBackground(new java.awt.Color(245, 250, 240));
        KdAlasan.setHighlighter(null);
        KdAlasan.setName("KdAlasan"); // NOI18N
        FormInput.add(KdAlasan);
        KdAlasan.setBounds(104, 210, 65, 23);

        NmAlasan.setEditable(false);
        NmAlasan.setBackground(new java.awt.Color(245, 250, 240));
        NmAlasan.setHighlighter(null);
        NmAlasan.setName("NmAlasan"); // NOI18N
        FormInput.add(NmAlasan);
        NmAlasan.setBounds(171, 210, 170, 23);

        BtnAlasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAlasan.setMnemonic('X');
        BtnAlasan.setToolTipText("Alt+X");
        BtnAlasan.setName("BtnAlasan"); // NOI18N
        BtnAlasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAlasanActionPerformed(evt);
            }
        });
        BtnAlasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAlasanKeyPressed(evt);
            }
        });
        FormInput.add(BtnAlasan);
        BtnAlasan.setBounds(343, 210, 28, 23);

        jLabel8.setText("No.KTP/NIK :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 40, 85, 23);

        NoKTP.setEditable(false);
        NoKTP.setBackground(new java.awt.Color(245, 250, 240));
        NoKTP.setHighlighter(null);
        NoKTP.setName("NoKTP"); // NOI18N
        FormInput.add(NoKTP);
        NoKTP.setBounds(89, 40, 220, 23);

        jLabel9.setText("No.Kartu JKN :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(336, 40, 100, 23);

        NoKartu.setEditable(false);
        NoKartu.setBackground(new java.awt.Color(245, 250, 240));
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        NoKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoKartuActionPerformed(evt);
            }
        });
        FormInput.add(NoKartu);
        NoKartu.setBounds(440, 40, 160, 23);

        jLabel13.setText("J.K. :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(635, 40, 50, 23);

        JK.setEditable(false);
        JK.setBackground(new java.awt.Color(245, 250, 240));
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(689, 40, 50, 23);

        TglLahir.setEditable(false);
        TglLahir.setBackground(new java.awt.Color(245, 250, 240));
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        TglLahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TglLahirActionPerformed(evt);
            }
        });
        FormInput.add(TglLahir);
        TglLahir.setBounds(383, 70, 120, 23);

        jLabel14.setText("Tgl.Lahir :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(320, 70, 60, 23);

        TmpLahir.setEditable(false);
        TmpLahir.setBackground(new java.awt.Color(245, 250, 240));
        TmpLahir.setHighlighter(null);
        TmpLahir.setName("TmpLahir"); // NOI18N
        FormInput.add(TmpLahir);
        TmpLahir.setBounds(89, 70, 200, 23);

        jLabel15.setText("Tempat Lahir :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 70, 85, 23);

        Kontak.setEditable(false);
        Kontak.setBackground(new java.awt.Color(245, 250, 240));
        Kontak.setHighlighter(null);
        Kontak.setName("Kontak"); // NOI18N
        Kontak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KontakActionPerformed(evt);
            }
        });
        FormInput.add(Kontak);
        Kontak.setBounds(599, 70, 140, 23);

        jLabel16.setText("Kontak :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(535, 70, 60, 23);

        jLabel17.setText("Alasan Lainnya :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(10, 240, 90, 23);

        AlasanLainnya.setBackground(new java.awt.Color(245, 250, 240));
        AlasanLainnya.setHighlighter(null);
        AlasanLainnya.setName("AlasanLainnya"); // NOI18N
        AlasanLainnya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlasanLainnyaActionPerformed(evt);
            }
        });
        AlasanLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(AlasanLainnya);
        AlasanLainnya.setBounds(104, 240, 267, 23);

        jLabel18.setText("Jenis Rujukan :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(10, 150, 90, 23);

        JenisRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rawat Jalan", "2. Rawat Darurat/Inap", "3. Parsial" }));
        JenisRujukan.setName("JenisRujukan"); // NOI18N
        JenisRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisRujukanKeyPressed(evt);
            }
        });
        FormInput.add(JenisRujukan);
        JenisRujukan.setBounds(104, 150, 237, 23);

        jLabel20.setText("Faskes Tujuan :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(10, 180, 90, 23);

        jLabel23.setText("Alamat :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 100, 85, 23);

        Alamat.setEditable(false);
        Alamat.setBackground(new java.awt.Color(245, 250, 240));
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        FormInput.add(Alamat);
        Alamat.setBounds(89, 100, 650, 23);

        jLabel24.setText("Diagnosa Rujuk :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(380, 180, 90, 23);

        KdDiagnosa.setEditable(false);
        KdDiagnosa.setBackground(new java.awt.Color(245, 250, 240));
        KdDiagnosa.setHighlighter(null);
        KdDiagnosa.setName("KdDiagnosa"); // NOI18N
        FormInput.add(KdDiagnosa);
        KdDiagnosa.setBounds(474, 180, 65, 23);

        NmDiagnosa.setEditable(false);
        NmDiagnosa.setBackground(new java.awt.Color(245, 250, 240));
        NmDiagnosa.setHighlighter(null);
        NmDiagnosa.setName("NmDiagnosa"); // NOI18N
        FormInput.add(NmDiagnosa);
        NmDiagnosa.setBounds(541, 180, 170, 23);

        BtnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDiagnosa.setMnemonic('X');
        BtnDiagnosa.setToolTipText("Alt+X");
        BtnDiagnosa.setName("BtnDiagnosa"); // NOI18N
        BtnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDiagnosaActionPerformed(evt);
            }
        });
        BtnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(BtnDiagnosa);
        BtnDiagnosa.setBounds(712, 180, 28, 23);

        jLabel25.setText("Dokter Perujuk :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(380, 210, 90, 23);

        KdDokter.setEditable(false);
        KdDokter.setBackground(new java.awt.Color(245, 250, 240));
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(474, 210, 65, 23);

        DokterPerujuk.setEditable(false);
        DokterPerujuk.setBackground(new java.awt.Color(245, 250, 240));
        DokterPerujuk.setHighlighter(null);
        DokterPerujuk.setName("DokterPerujuk"); // NOI18N
        FormInput.add(DokterPerujuk);
        DokterPerujuk.setBounds(541, 210, 170, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('X');
        BtnDokter.setToolTipText("Alt+X");
        BtnDokter.setName("BtnDokter"); // NOI18N
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
        BtnDokter.setBounds(712, 210, 28, 23);

        jLabel27.setText("Petugas Entry :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(380, 240, 90, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setBackground(new java.awt.Color(245, 250, 240));
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(474, 240, 65, 23);

        PetugasEntry.setEditable(false);
        PetugasEntry.setBackground(new java.awt.Color(245, 250, 240));
        PetugasEntry.setHighlighter(null);
        PetugasEntry.setName("PetugasEntry"); // NOI18N
        FormInput.add(PetugasEntry);
        PetugasEntry.setBounds(541, 240, 170, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('X');
        BtnPetugas.setToolTipText("Alt+X");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(712, 240, 28, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("Data Rujukan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(12, 128, 80, 23);

        jLabel29.setText("Anamnesis & Pemeriksaan Fisik :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(10, 290, 165, 23);

        Anamnesis.setBackground(new java.awt.Color(245, 250, 240));
        Anamnesis.setHighlighter(null);
        Anamnesis.setName("Anamnesis"); // NOI18N
        Anamnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnamnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anamnesis);
        Anamnesis.setBounds(179, 290, 561, 23);

        jLabel36.setText("Kesadaran :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(10, 320, 165, 23);

        JenisKesadaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Sadar", "2. Tidak Sadar" }));
        JenisKesadaran.setName("JenisKesadaran"); // NOI18N
        JenisKesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKesadaranKeyPressed(evt);
            }
        });
        FormInput.add(JenisKesadaran);
        JenisKesadaran.setBounds(179, 320, 140, 23);

        jLabel37.setText("Tekanan Darah :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(336, 320, 100, 23);

        TekananDarah.setBackground(new java.awt.Color(245, 250, 240));
        TekananDarah.setHighlighter(null);
        TekananDarah.setName("TekananDarah"); // NOI18N
        TekananDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TekananDarahKeyPressed(evt);
            }
        });
        FormInput.add(TekananDarah);
        TekananDarah.setBounds(440, 320, 90, 23);

        FrekuensiNadi.setBackground(new java.awt.Color(245, 250, 240));
        FrekuensiNadi.setHighlighter(null);
        FrekuensiNadi.setName("FrekuensiNadi"); // NOI18N
        FrekuensiNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiNadiKeyPressed(evt);
            }
        });
        FormInput.add(FrekuensiNadi);
        FrekuensiNadi.setBounds(680, 320, 60, 23);

        jLabel38.setText("Frekuensi Nadi(/menit) :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(537, 320, 139, 23);

        jLabel39.setText("Suhu Badan(C) :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(10, 350, 165, 23);

        SuhuBadan.setBackground(new java.awt.Color(245, 250, 240));
        SuhuBadan.setHighlighter(null);
        SuhuBadan.setName("SuhuBadan"); // NOI18N
        SuhuBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuBadanKeyPressed(evt);
            }
        });
        FormInput.add(SuhuBadan);
        SuhuBadan.setBounds(179, 350, 90, 23);

        jLabel40.setText("Respirasi(/menit) :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(316, 350, 120, 23);

        Respirasi.setBackground(new java.awt.Color(245, 250, 240));
        Respirasi.setHighlighter(null);
        Respirasi.setName("Respirasi"); // NOI18N
        Respirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiKeyPressed(evt);
            }
        });
        FormInput.add(Respirasi);
        Respirasi.setBounds(440, 350, 60, 23);

        jLabel42.setText("Tingkat Nyeri :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(530, 350, 90, 23);

        TingkatNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak Nyeri", "1. Ringan", "2. Sedang", "3. Berat" }));
        TingkatNyeri.setName("TingkatNyeri"); // NOI18N
        TingkatNyeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TingkatNyeriKeyPressed(evt);
            }
        });
        FormInput.add(TingkatNyeri);
        TingkatNyeri.setBounds(624, 350, 116, 23);

        jLabel43.setText("Keadaan Umum :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(10, 380, 165, 23);

        KeadaanUmum.setBackground(new java.awt.Color(245, 250, 240));
        KeadaanUmum.setHighlighter(null);
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmum);
        KeadaanUmum.setBounds(179, 380, 321, 23);

        Alergi.setBackground(new java.awt.Color(245, 250, 240));
        Alergi.setHighlighter(null);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(580, 380, 160, 23);

        jLabel44.setText("Alergi :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(486, 380, 90, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Kondisi Umum :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(12, 268, 80, 23);

        jLabel46.setText("Laboratorium :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(10, 430, 90, 23);

        Laborat.setBackground(new java.awt.Color(245, 250, 240));
        Laborat.setHighlighter(null);
        Laborat.setName("Laborat"); // NOI18N
        Laborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaboratKeyPressed(evt);
            }
        });
        FormInput.add(Laborat);
        Laborat.setBounds(104, 430, 636, 23);

        jLabel47.setText("Radiologi :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(10, 460, 90, 23);

        Radiologi.setBackground(new java.awt.Color(245, 250, 240));
        Radiologi.setHighlighter(null);
        Radiologi.setName("Radiologi"); // NOI18N
        Radiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadiologiKeyPressed(evt);
            }
        });
        FormInput.add(Radiologi);
        Radiologi.setBounds(104, 460, 636, 23);

        jLabel48.setText("Terapi/Tindakan :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(10, 490, 90, 23);

        TerapiTindakan.setBackground(new java.awt.Color(245, 250, 240));
        TerapiTindakan.setHighlighter(null);
        TerapiTindakan.setName("TerapiTindakan"); // NOI18N
        TerapiTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiTindakanKeyPressed(evt);
            }
        });
        FormInput.add(TerapiTindakan);
        TerapiTindakan.setBounds(104, 490, 636, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCari1);
        BtnCari1.setBounds(711, 10, 28, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Rujukan Keluar Sisrute", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rujuk :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-04-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRujukan.setBackground(new java.awt.Color(255, 255, 254));
        TabRujukan.setForeground(new java.awt.Color(70, 70, 70));
        TabRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRujukan.setName("TabRujukan"); // NOI18N
        TabRujukan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRujukanMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

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

        TabRujukan.addTab("Rujukan Keluar", Scroll);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbObat2.setAutoCreateRowSorter(true);
        tbObat2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat2.setComponentPopupMenu(jPopupMenu1);
        tbObat2.setName("tbObat2"); // NOI18N
        tbObat2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObat2MouseClicked(evt);
            }
        });
        tbObat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObat2KeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbObat2);

        TabRujukan.addTab("Respon Faskes Rujukan", Scroll2);

        internalFrame4.add(TabRujukan, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Data Rujukan Keluar Sisrute", internalFrame4);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        }else if (KdFaskes.getText().trim().equals("")||NmFaskes.getText().trim().equals("")) {
            Valid.textKosong(KdFaskes, "Faskes Tujuan");
        }else if (KdAlasan.getText().trim().equals("")||NmAlasan.getText().trim().equals("")) {
            Valid.textKosong(KdAlasan, "Alasan Rujuk");
        }else if (AlasanLainnya.getText().trim().equals("")) {
            Valid.textKosong(AlasanLainnya, "Alasan Lainnya");
        }else if (KdDiagnosa.getText().trim().equals("")||NmDiagnosa.getText().trim().equals("")) {
            Valid.textKosong(KdDiagnosa, "Diagnosa Rujuk");
        }else if (KdDokter.getText().trim().equals("")||DokterPerujuk.getText().trim().equals("")) {
            Valid.textKosong(KdDokter, "Dokter Perujuk");
        }else if (KdPetugas.getText().trim().equals("")||PetugasEntry.getText().trim().equals("")) {
            Valid.textKosong(KdPetugas, "Petugas Entry");
        }else if (Anamnesis.getText().trim().equals("")) {
            Valid.textKosong(Anamnesis, "Anamnesis");
        }else if (TekananDarah.getText().trim().equals("")) {
            Valid.textKosong(TekananDarah, "Tekanan Darah");
        }else if (FrekuensiNadi.getText().trim().equals("")) {
            Valid.textKosong(FrekuensiNadi, "Frekuensi Nadi");
        }else if (SuhuBadan.getText().trim().equals("")) {
            Valid.textKosong(SuhuBadan, "Suhu Badan");
        }else if (Respirasi.getText().trim().equals("")) {
            Valid.textKosong(Respirasi, "Respirasi");
        }else if (KeadaanUmum.getText().trim().equals("")) {
            Valid.textKosong(KeadaanUmum, "Keadaan Umum");
        }else if (Alergi.getText().trim().equals("")) {
            Valid.textKosong(Alergi, "Alergi");
        }else if (Laborat.getText().trim().equals("")) {
            Valid.textKosong(Laborat, "Laborat");
        }else if (Radiologi.getText().trim().equals("")) {
            Valid.textKosong(Radiologi, "Radiologi");
        }else if (TerapiTindakan.getText().trim().equals("")) {
            Valid.textKosong(TerapiTindakan, "Terapi/Tindakan");
        }else{
            try {
                URL = link+"/rujukan";	
                System.out.println(URL);
                headers = new HttpHeaders();
                headers.add("X-cons-id",idrs);
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
                headers.add("X-signature",api.getHmac()); 
                headers.add("Content-type","application/json");
                requestJson ="{" +
                                "\"PASIEN\": {" +
                                    "\"NORM\":\""+TNoRM.getText()+"\"," +
                                    "\"NIK\": \""+NoKTP.getText()+"\"," +
                                    "\"NO_KARTU_JKN\": \""+NoKartu.getText()+"\"," +
                                    "\"NAMA\": \""+TPasien.getText()+"\"," +
                                    "\"JENIS_KELAMIN\": \""+JK.getText().replaceAll("L","1").replaceAll("P","2")+"\"," +
                                    "\"TANGGAL_LAHIR\": \""+TglLahir.getText()+"\"," +
                                    "\"TEMPAT_LAHIR\": \""+TmpLahir.getText()+"\"," +
                                    "\"ALAMAT\": \""+Alamat.getText()+"\"," +
                                    "\"KONTAK\": \""+Kontak.getText()+"\"" +
                                "}," +
                                "\"RUJUKAN\": {" +
                                    "\"JENIS_RUJUKAN\": \""+JenisRujukan.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"TANGGAL\": \""+Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19)+"\"," +
                                    "\"FASKES_TUJUAN\": \""+KdFaskes.getText()+"\"," +
                                    "\"ALASAN\": \""+KdAlasan.getText()+"\"," +
                                    "\"ALASAN_LAINNYA\": \""+AlasanLainnya.getText()+"\"," +
                                    "\"DIAGNOSA\": \""+KdDiagnosa.getText()+"\"," +
                                    "\"DOKTER\": {" +
                                        "\"NIK\": \""+KdDokter.getText()+"\"," +
                                        "\"NAMA\": \""+DokterPerujuk.getText()+"\"" +
                                    "}," +
                                    "\"PETUGAS\": {" +
                                        "\"NIK\": \""+KdPetugas.getText()+"\"," +
                                        "\"NAMA\": \""+PetugasEntry.getText()+"\"" +
                                    "}" +
                                "}," +
                                "\"KONDISI_UMUM\": {" +
                                    "\"ANAMNESIS_DAN_PEMERIKSAAN_FISIK\": \""+Anamnesis.getText()+"\"," +
                                    "\"KESADARAN\": \""+JenisKesadaran.getSelectedItem().toString().substring(0,1)+"\"," +
                                    "\"TEKANAN_DARAH\": \""+TekananDarah.getText()+"\"," +
                                    "\"FREKUENSI_NADI\": \""+FrekuensiNadi.getText()+"\"," +
                                    "\"SUHU\": \""+SuhuBadan.getText()+"\"," +
                                    "\"PERNAPASAN\": \""+Respirasi.getText()+"\"," +
                                    "\"KEADAAN_UMUM\": \""+KeadaanUmum.getText()+"\"," +
                                    "\"NYERI\": "+TingkatNyeri.getSelectedItem().toString().substring(0,1)+"," +
                                    "\"ALERGI\": \""+Alergi.getText()+"\"" +
                                "}," +
                                "\"PENUNJANG\": {" +
                                    "\"LABORATORIUM\": \""+Laborat.getText()+"\"," +
                                    "\"RADIOLOGI\": \""+Radiologi.getText()+"\"," +
                                    "\"TERAPI_ATAU_TINDAKAN\": \""+TerapiTindakan.getText()+"\"" +
                                "}" +
                            "}";              
                headers.add("Content-length",Integer.toString(requestJson.length())); 
                System.out.println(requestJson);
                requestEntity = new HttpEntity(requestJson,headers);
                requestJson=api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody();
                System.out.println(requestJson);
                root = mapper.readTree(requestJson);
                JOptionPane.showMessageDialog(null,root.path("detail").asText());
                if(root.path("status").asText().equals("200")){
                    if(Sequel.menyimpantf("sisrute_rujukan_keluar","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Nomor Rujuk",36,new String[]{
                            TNoRw.getText(),root.path("data").path("RUJUKAN").path("NOMOR").asText(),TNoRM.getText(),TPasien.getText(),NoKTP.getText(),NoKartu.getText(),JK.getText(), 
                            TglLahir.getText(),TmpLahir.getText(),Valid.MaxTeks(Alamat.getText(),200),Kontak.getText(),JenisRujukan.getSelectedItem().toString(),Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19), 
                            KdFaskes.getText(),NmFaskes.getText(),KdAlasan.getText(),NmAlasan.getText(),AlasanLainnya.getText(),KdDiagnosa.getText(),Valid.MaxTeks(NmDiagnosa.getText(),400),KdDokter.getText(), 
                            DokterPerujuk.getText(),KdPetugas.getText(),PetugasEntry.getText(),Valid.MaxTeks(Anamnesis.getText(),700),JenisKesadaran.getSelectedItem().toString(),TekananDarah.getText(), 
                            FrekuensiNadi.getText(),SuhuBadan.getText(),Respirasi.getText(),Valid.MaxTeks(KeadaanUmum.getText(),300),TingkatNyeri.getSelectedItem().toString(),Alergi.getText(),
                            Valid.MaxTeks(Laborat.getText(),1000),Valid.MaxTeks(Radiologi.getText(),1000),Valid.MaxTeks(TerapiTindakan.getText(),1000)
                        })){
                        Sequel.menyimpan2("rujuk","'"+root.path("data").path("RUJUKAN").path("NOMOR").asText()+"','"+
                                TNoRw.getText()+"','"+Valid.MaxTeks(NmFaskes.getText(),150)+"','"+
                                Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+"','"+NmDiagnosa.getText()+"','"+
                                Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText())+
                                "','-','-','"+KeadaanUmum.getText()+"','"+TanggalRujuk.getSelectedItem().toString().substring(11,19)+"'","No.Rujuk");
                        emptTeks();
                    }
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
                }else if(ex.toString().contains("404")){
                    JOptionPane.showMessageDialog(rootPane,"Tidak ditemukan....!");
                }else if(ex.toString().contains("500")){
                    JOptionPane.showMessageDialog(rootPane,"Server interenal error....!");
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TerapiTindakan,BtnBatal);
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
        if(TabRujukan.getSelectedIndex()==0){
            if(tbObat.getSelectedRow()!= -1){
                try {
                    URL = link+"/rujukan/batal/"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString();	
                    System.out.println(URL);
                    headers = new HttpHeaders();
                    headers.add("X-cons-id",idrs);
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
                    headers.add("X-signature",api.getHmac()); 
                    headers.add("Content-type","application/json");
                    requestJson ="{" +
                                    "\"PETUGAS\": {" +
                                        "\"NIK\": \""+KdPetugas.getText()+"\"," +
                                        "\"NAMA\": \""+PetugasEntry.getText()+"\"" +
                                    "}" +                                
                                "}";              
                    headers.add("Content-length",Integer.toString(requestJson.length())); 
                    //System.out.println(Integer.toString(requestJson.length()));
                    System.out.println(requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);
                    requestJson=api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody();
                    System.out.println(requestJson);
                    root = mapper.readTree(requestJson);
                    JOptionPane.showMessageDialog(null,root.path("detail").asText());
                    if(root.path("status").asText().equals("200")){                    
                        Sequel.meghapus3("sisrute_rujukan_keluar","no_rawat",TNoRw.getText());          
                        Sequel.meghapus3("rujuk","no_rawat",TNoRw.getText());
                        emptTeks();
                        tampil();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
                    }else if(ex.toString().contains("404")){
                        JOptionPane.showMessageDialog(rootPane,"Tidak ditemukan....!");
                    }else if(ex.toString().contains("500")){
                        JOptionPane.showMessageDialog(rootPane,"Server interenal error....!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau diganti..!!");
            } 
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
            TabRujukan.setSelectedIndex(0);
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
        if(tbObat.getSelectedRow()!= -1){
            if (TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
                Valid.textKosong(TNoRw, "Pasien");
            }else if (KdFaskes.getText().trim().equals("")||NmFaskes.getText().trim().equals("")) {
                Valid.textKosong(KdFaskes, "Faskes Tujuan");
            }else if (KdAlasan.getText().trim().equals("")||NmAlasan.getText().trim().equals("")) {
                Valid.textKosong(KdAlasan, "Alasan Rujuk");
            }else if (AlasanLainnya.getText().trim().equals("")) {
                Valid.textKosong(AlasanLainnya, "Alasan Lainnya");
            }else if (KdDiagnosa.getText().trim().equals("")||NmDiagnosa.getText().trim().equals("")) {
                Valid.textKosong(KdDiagnosa, "Diagnosa Rujuk");
            }else if (KdDokter.getText().trim().equals("")||DokterPerujuk.getText().trim().equals("")) {
                Valid.textKosong(KdDokter, "Dokter Perujuk");
            }else if (KdPetugas.getText().trim().equals("")||PetugasEntry.getText().trim().equals("")) {
                Valid.textKosong(KdPetugas, "Petugas Entry");
            }else if (Anamnesis.getText().trim().equals("")) {
                Valid.textKosong(Anamnesis, "Anamnesis");
            }else if (TekananDarah.getText().trim().equals("")) {
                Valid.textKosong(TekananDarah, "Tekanan Darah");
            }else if (FrekuensiNadi.getText().trim().equals("")) {
                Valid.textKosong(FrekuensiNadi, "Frekuensi Nadi");
            }else if (SuhuBadan.getText().trim().equals("")) {
                Valid.textKosong(SuhuBadan, "Suhu Badan");
            }else if (Respirasi.getText().trim().equals("")) {
                Valid.textKosong(Respirasi, "Respirasi");
            }else if (KeadaanUmum.getText().trim().equals("")) {
                Valid.textKosong(KeadaanUmum, "Keadaan Umum");
            }else if (Alergi.getText().trim().equals("")) {
                Valid.textKosong(Alergi, "Alergi");
            }else if (Laborat.getText().trim().equals("")) {
                Valid.textKosong(Laborat, "Laborat");
            }else if (Radiologi.getText().trim().equals("")) {
                Valid.textKosong(Radiologi, "Radiologi");
            }else if (TerapiTindakan.getText().trim().equals("")) {
                Valid.textKosong(TerapiTindakan, "Terapi/Tindakan");
            }else{
                try {
                    URL = link+"/rujukan/"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString();	
                    System.out.println(URL);
                    headers = new HttpHeaders();
                    headers.add("X-cons-id",idrs);
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
                    headers.add("X-signature",api.getHmac()); 
                    headers.add("Content-type","application/json");
                    requestJson ="{" +
                                    "\"PASIEN\": {" +
                                        "\"NORM\":\""+TNoRM.getText()+"\"," +
                                        "\"NIK\": \""+NoKTP.getText()+"\"," +
                                        "\"NO_KARTU_JKN\": \""+NoKartu.getText()+"\"," +
                                        "\"NAMA\": \""+TPasien.getText()+"\"," +
                                        "\"JENIS_KELAMIN\": \""+JK.getText().replaceAll("L","1").replaceAll("P","2")+"\"," +
                                        "\"TANGGAL_LAHIR\": \""+TglLahir.getText()+"\"," +
                                        "\"TEMPAT_LAHIR\": \""+TmpLahir.getText()+"\"," +
                                        "\"ALAMAT\": \""+Alamat.getText()+"\"," +
                                        "\"KONTAK\": \""+Kontak.getText()+"\"" +
                                    "}," +
                                    "\"RUJUKAN\": {" +
                                        "\"JENIS_RUJUKAN\": \""+JenisRujukan.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"TANGGAL\": \""+Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19)+"\"," +
                                        "\"FASKES_TUJUAN\": \""+KdFaskes.getText()+"\"," +
                                        "\"ALASAN\": \""+KdAlasan.getText()+"\"," +
                                        "\"ALASAN_LAINNYA\": \""+AlasanLainnya.getText()+"\"," +
                                        "\"DIAGNOSA\": \""+KdDiagnosa.getText()+"\"," +
                                        "\"DOKTER\": {" +
                                            "\"NIK\": \""+KdDokter.getText()+"\"," +
                                            "\"NAMA\": \""+DokterPerujuk.getText()+"\"" +
                                        "}," +
                                        "\"PETUGAS\": {" +
                                            "\"NIK\": \""+KdPetugas.getText()+"\"," +
                                            "\"NAMA\": \""+PetugasEntry.getText()+"\"" +
                                        "}" +
                                    "}," +
                                    "\"KONDISI_UMUM\": {" +
                                        "\"ANAMNESIS_DAN_PEMERIKSAAN_FISIK\": \""+Anamnesis.getText()+"\"," +
                                        "\"KESADARAN\": \""+JenisKesadaran.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"TEKANAN_DARAH\": \""+TekananDarah.getText()+"\"," +
                                        "\"FREKUENSI_NADI\": \""+FrekuensiNadi.getText()+"\"," +
                                        "\"SUHU\": \""+SuhuBadan.getText()+"\"," +
                                        "\"PERNAPASAN\": \""+Respirasi.getText()+"\"," +
                                        "\"KEADAAN_UMUM\": \""+KeadaanUmum.getText()+"\"," +
                                        "\"NYERI\": "+TingkatNyeri.getSelectedItem().toString().substring(0,1)+"," +
                                        "\"ALERGI\": \""+Alergi.getText()+"\"" +
                                    "}," +
                                    "\"PENUNJANG\": {" +
                                        "\"LABORATORIUM\": \""+Laborat.getText()+"\"," +
                                        "\"RADIOLOGI\": \""+Radiologi.getText()+"\"," +
                                        "\"TERAPI_ATAU_TINDAKAN\": \""+TerapiTindakan.getText()+"\"" +
                                    "}" +
                                "}";              
                    headers.add("Content-length",Integer.toString(requestJson.length())); 
                    //System.out.println(Integer.toString(requestJson.length()));
                    System.out.println(requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);
                    requestJson=api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody();
                    System.out.println(requestJson);
                    root = mapper.readTree(requestJson);
                    JOptionPane.showMessageDialog(null,root.path("detail").asText());
                    if(root.path("status").asText().equals("200")){
                        if(Sequel.mengedittf("sisrute_rujukan_keluar","no_rawat=?","no_rujuk=?,no_rkm_medis=?,nm_pasien=?,no_ktp=?,no_peserta=?,jk=?,tgl_lahir=?,tmp_lahir=?,alamat=?,no_tlp=?,jns_rujukan=?,tgl_rujuk=?,kd_faskes_tujuan=?,nm_faskes_tujuan=?,kd_alasan=?,alasan_rujuk=?,alasan_lainnya=?,kd_diagnosa=?,diagnosa_rujuk=?,nik_dokter=?,dokter_perujuk=?,nik_petugas=?,petugas_entry=?,anamnesis_pemeriksaan=?,kesadaran=?,tekanan_darah=?,nadi=?,suhu=?,respirasi=?,keadaan_umum=?,tingkat_nyeri=?,alergi=?,laboratorium=?,radiologi=?,terapitindakan=?",36,new String[]{
                            root.path("data").path("RUJUKAN").path("NOMOR").asText(),TNoRM.getText(),TPasien.getText(),NoKTP.getText(),NoKartu.getText(),JK.getText(), 
                            TglLahir.getText(),TmpLahir.getText(),Valid.MaxTeks(Alamat.getText(),200),Kontak.getText(),JenisRujukan.getSelectedItem().toString(),Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+" "+TanggalRujuk.getSelectedItem().toString().substring(11,19), 
                            KdFaskes.getText(),NmFaskes.getText(),KdAlasan.getText(),NmAlasan.getText(),AlasanLainnya.getText(),KdDiagnosa.getText(),Valid.MaxTeks(NmDiagnosa.getText(),400),KdDokter.getText(), 
                            DokterPerujuk.getText(),KdPetugas.getText(),PetugasEntry.getText(),Valid.MaxTeks(Anamnesis.getText(),700),JenisKesadaran.getSelectedItem().toString(),TekananDarah.getText(), 
                            FrekuensiNadi.getText(),SuhuBadan.getText(),Respirasi.getText(),Valid.MaxTeks(KeadaanUmum.getText(),300),TingkatNyeri.getSelectedItem().toString(),Alergi.getText(),
                            Valid.MaxTeks(Laborat.getText(),1000),Valid.MaxTeks(Radiologi.getText(),1000),Valid.MaxTeks(TerapiTindakan.getText(),1000),TNoRw.getText()
                        })==true){
                            Sequel.meghapus3("rujuk","no_rawat",TNoRw.getText());
                            Sequel.menyimpan2("rujuk","'"+root.path("data").path("RUJUKAN").path("NOMOR").asText()+"','"+
                                TNoRw.getText()+"','"+Valid.MaxTeks(NmFaskes.getText(),150)+"','"+
                                Valid.SetTgl(TanggalRujuk.getSelectedItem()+"")+"','"+NmDiagnosa.getText()+"','"+
                                Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText())+
                                "','-','-','"+KeadaanUmum.getText()+"','"+TanggalRujuk.getSelectedItem().toString().substring(11,19)+"'","No.Rujuk");
                            emptTeks();
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
                    }else if(ex.toString().contains("404")){
                        JOptionPane.showMessageDialog(rootPane,"Tidak ditemukan....!");
                    }else if(ex.toString().contains("500")){
                        JOptionPane.showMessageDialog(rootPane,"Server interenal error....!");
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau diganti..!!");
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
        if(TabRujukan.getSelectedIndex()==0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){            
                Map<String, Object> param = new HashMap<>(); 
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    param.put("tanggal1",Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    param.put("tanggal2",Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    param.put("parameter","%"+TCari.getText()+"%"); 
                Valid.MyReport("rptCariSisruteRujukanKeluar.jasper","report","::[ Data Bridging SEP ]::",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRujukan.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Silahkan tampilkan data yang mau dicetak..!!");
            TabRujukan.setSelectedIndex(0);
        }
            
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
                TabRawat.setSelectedIndex(0);
            }   
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnAlasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAlasanActionPerformed
        alasanrujuk.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        alasanrujuk.setLocationRelativeTo(internalFrame1);        
        alasanrujuk.setVisible(true);
    }//GEN-LAST:event_BtnAlasanActionPerformed

    private void BtnAlasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAlasanKeyPressed
        Valid.pindah(evt,BtnFaskes,AlasanLainnya);
    }//GEN-LAST:event_BtnAlasanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void TanggalRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujukKeyPressed
        Valid.pindah(evt,AlasanLainnya,BtnDiagnosa);
    }//GEN-LAST:event_TanggalRujukKeyPressed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getHeight()<650){   
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormInput.setPreferredSize(new Dimension(FormInput.WIDTH,525));
            if(this.getWidth()<785){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(770,525));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }else{
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);            
            if(this.getWidth()<785){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(770,FormInput.HEIGHT));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void BtnFaskesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnFaskesKeyPressed
        Valid.pindah(evt,JenisRujukan,BtnAlasan);
    }//GEN-LAST:event_BtnFaskesKeyPressed

    private void BtnFaskesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFaskesActionPerformed
        faskes.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        faskes.setLocationRelativeTo(internalFrame1);        
        faskes.setVisible(true);
    }//GEN-LAST:event_BtnFaskesActionPerformed

    private void NoKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoKartuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoKartuActionPerformed

    private void TglLahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TglLahirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglLahirActionPerformed

    private void KontakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KontakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KontakActionPerformed

    private void JenisRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisRujukanKeyPressed
        Valid.pindah(evt,BtnSimpan,BtnFaskes);
    }//GEN-LAST:event_JenisRujukanKeyPressed

    private void AlasanLainnyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlasanLainnyaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlasanLainnyaActionPerformed

    private void BtnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDiagnosaActionPerformed
        diagnosa.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        diagnosa.setLocationRelativeTo(internalFrame1);        
        diagnosa.setVisible(true);
    }//GEN-LAST:event_BtnDiagnosaActionPerformed

    private void BtnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDiagnosaKeyPressed
        Valid.pindah(evt,TanggalRujuk,BtnDokter);
    }//GEN-LAST:event_BtnDiagnosaKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        pilihan=1;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,BtnDiagnosa,BtnPetugas);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        pilihan=2;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,BtnDokter,Anamnesis);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void JenisKesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKesadaranKeyPressed
        Valid.pindah(evt,Anamnesis,TekananDarah);
    }//GEN-LAST:event_JenisKesadaranKeyPressed

    private void TingkatNyeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TingkatNyeriKeyPressed
        Valid.pindah(evt,Respirasi,KeadaanUmum);
    }//GEN-LAST:event_TingkatNyeriKeyPressed

    private void KdFaskesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdFaskesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdFaskesActionPerformed

    private void AlasanLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanLainnyaKeyPressed
        Valid.pindah(evt,BtnAlasan,TanggalRujuk);
    }//GEN-LAST:event_AlasanLainnyaKeyPressed

    private void AnamnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnamnesisKeyPressed
        Valid.pindah(evt,BtnPetugas,JenisKesadaran);
    }//GEN-LAST:event_AnamnesisKeyPressed

    private void TekananDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TekananDarahKeyPressed
        Valid.pindah(evt,JenisKesadaran,FrekuensiNadi);
    }//GEN-LAST:event_TekananDarahKeyPressed

    private void FrekuensiNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiNadiKeyPressed
        Valid.pindah(evt,TekananDarah,SuhuBadan);
    }//GEN-LAST:event_FrekuensiNadiKeyPressed

    private void SuhuBadanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuBadanKeyPressed
        Valid.pindah(evt,FrekuensiNadi,Respirasi);
    }//GEN-LAST:event_SuhuBadanKeyPressed

    private void RespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiKeyPressed
        Valid.pindah(evt,SuhuBadan,TingkatNyeri);
    }//GEN-LAST:event_RespirasiKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        Valid.pindah(evt,TingkatNyeri,Alergi);
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,KeadaanUmum,Laborat);
    }//GEN-LAST:event_AlergiKeyPressed

    private void LaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaboratKeyPressed
        Valid.pindah(evt,Alergi,Radiologi);
    }//GEN-LAST:event_LaboratKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        Valid.pindah(evt,Laborat,TerapiTindakan);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void TerapiTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiTindakanKeyPressed
        Valid.pindah(evt,Radiologi,BtnSimpan);
    }//GEN-LAST:event_TerapiTindakanKeyPressed

    private void MnSuratRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratRujukanActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            penyakit="";
            keluar="";
            try {
                ps=koneksi.prepareStatement("select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                   "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=? group by databarang.nama_brng ");
                try {
                    ps.setString(1,TNoRw.getText());
                    rs=ps.executeQuery();
                    while(rs.next()){
                        if(penyakit.equals("")){
                            penyakit=rs.getString(1);
                        }else {
                            penyakit=penyakit+", "+rs.getString(1);
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
                System.out.println("Notifikasi : "+e);
            }

            penyakit2="";
            i=Sequel.cariInteger("select count(no_rawat) from rawat_inap_dr where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(penyakit2.equals("")){
                    penyakit2="rawat inap";
                }else {
                    penyakit2=penyakit2+", rawat inap";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(penyakit2.equals("")){
                    penyakit2="pemeriksaan laboratorium";
                }else {
                    penyakit2=penyakit2+", pemeriksaan laboratorium";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(penyakit2.equals("")){
                    penyakit2="pemeriksaan radiologi";
                }else {
                    penyakit2=penyakit2+", pemeriksaan radiologi";
                }
            }

            i=Sequel.cariInteger("select count(no_rawat) from operasi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(penyakit2.equals("")){
                    penyakit2="operasi";
                }else {
                    penyakit2=penyakit2+", operasi";
                }
            }

            keluar=Sequel.cariIsi("select stts_pulang from kamar_inap where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());

            param.put("html","Demikianlah riwayat perawatan selama di "+akses.getnamars()+" dengan penyakit akhir "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+". "+
                "Atas kerjasamanya kami ucapkan terima kasih");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("penyakit",tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            param.put("tindakan",penyakit2);
            param.put("terpi",penyakit);
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("no_rawat",TNoRw.getText());
            Valid.MyReport("rptSuratRujukan.jasper","report","::[ Surat Rujukan ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSuratRujukanActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbObat.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void TabRujukanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRujukanMouseClicked
        tampil();
    }//GEN-LAST:event_TabRujukanMouseClicked

    private void tbObat2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObat2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbObat2MouseClicked

    private void tbObat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObat2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbObat2KeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        if(TNoRw.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih data rujukan terlebih dahulu");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                setPasien2(TNoRw.getText());
            }else {
                setPasien(TNoRw.getText());
            }
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SisruteRujukanKeluar dialog = new SisruteRujukanKeluar(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlasanLainnya;
    private widget.TextBox Alergi;
    private widget.TextBox Anamnesis;
    private widget.Button BtnAlasan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnDiagnosa;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnFaskes;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DokterPerujuk;
    private widget.PanelBiasa FormInput;
    private widget.TextBox FrekuensiNadi;
    private widget.TextBox JK;
    private widget.ComboBox JenisKesadaran;
    private widget.ComboBox JenisRujukan;
    private widget.TextBox KdAlasan;
    private widget.TextBox KdDiagnosa;
    private widget.TextBox KdDokter;
    private widget.TextBox KdFaskes;
    private widget.TextBox KdPetugas;
    private widget.TextBox KeadaanUmum;
    private widget.TextBox Kontak;
    private widget.Label LCount;
    private widget.TextBox Laborat;
    private javax.swing.JMenuItem MnSuratRujukan;
    private widget.TextBox NmAlasan;
    private widget.TextBox NmDiagnosa;
    private widget.TextBox NmFaskes;
    private widget.TextBox NoKTP;
    private widget.TextBox NoKartu;
    private widget.TextBox PetugasEntry;
    private widget.TextBox Radiologi;
    private widget.TextBox Respirasi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox SuhuBadan;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRujukan;
    private widget.Tanggal TanggalRujuk;
    private widget.TextBox TekananDarah;
    private widget.TextBox TerapiTindakan;
    private widget.TextBox TglLahir;
    private widget.ComboBox TingkatNyeri;
    private widget.TextBox TmpLahir;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
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
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppRiwayat;
    private widget.Table tbObat;
    private widget.Table tbObat2;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        if(TabRujukan.getSelectedIndex()==0){
            tampilData();
        }else if(TabRujukan.getSelectedIndex()==1){
            tampilData2();
        }
    }
    
    private void tampilData(){
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select sisrute_rujukan_keluar.no_rawat,sisrute_rujukan_keluar.no_rujuk,sisrute_rujukan_keluar.no_rkm_medis,"+
                "sisrute_rujukan_keluar.nm_pasien,sisrute_rujukan_keluar.no_ktp,sisrute_rujukan_keluar.no_peserta,"+
                "sisrute_rujukan_keluar.jk,sisrute_rujukan_keluar.tgl_lahir,sisrute_rujukan_keluar.tmp_lahir,"+
                "sisrute_rujukan_keluar.alamat,sisrute_rujukan_keluar.no_tlp,sisrute_rujukan_keluar.jns_rujukan,"+
                "sisrute_rujukan_keluar.tgl_rujuk,sisrute_rujukan_keluar.kd_faskes_tujuan,sisrute_rujukan_keluar.nm_faskes_tujuan,"+
                "sisrute_rujukan_keluar.kd_alasan,sisrute_rujukan_keluar.alasan_rujuk,sisrute_rujukan_keluar.alasan_lainnya,"+
                "sisrute_rujukan_keluar.kd_diagnosa,sisrute_rujukan_keluar.diagnosa_rujuk,sisrute_rujukan_keluar.nik_dokter,"+
                "sisrute_rujukan_keluar.dokter_perujuk,sisrute_rujukan_keluar.nik_petugas,sisrute_rujukan_keluar.petugas_entry,"+
                "sisrute_rujukan_keluar.anamnesis_pemeriksaan,sisrute_rujukan_keluar.kesadaran,sisrute_rujukan_keluar.tekanan_darah,"+
                "sisrute_rujukan_keluar.nadi,sisrute_rujukan_keluar.suhu,sisrute_rujukan_keluar.respirasi,"+
                "sisrute_rujukan_keluar.keadaan_umum,sisrute_rujukan_keluar.tingkat_nyeri,sisrute_rujukan_keluar.alergi,"+
                "sisrute_rujukan_keluar.laboratorium,sisrute_rujukan_keluar.radiologi,sisrute_rujukan_keluar.terapitindakan from sisrute_rujukan_keluar where "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_rawat like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_rujuk like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_rkm_medis like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.nm_pasien like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_ktp like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_peserta like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.alamat like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.jns_rujukan like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.nm_faskes_tujuan like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.alasan_rujuk like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.kd_diagnosa like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.diagnosa_rujuk like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.dokter_perujuk like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.petugas_entry like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.kesadaran like ? "+
                "order by sisrute_rujukan_keluar.tgl_rujuk desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                ps.setString(37,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(38,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(39,"%"+TCari.getText().trim()+"%");
                ps.setString(40,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(41,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(42,"%"+TCari.getText().trim()+"%");
                ps.setString(43,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(44,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(45,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rujuk"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                        rs.getString("no_ktp"),rs.getString("no_peserta"),rs.getString("nm_pasien"),
                        rs.getString("jk"),rs.getString("tgl_lahir"),rs.getString("tmp_lahir"),
                        rs.getString("alamat"),rs.getString("no_tlp"),rs.getString("jns_rujukan"),
                        rs.getString("tgl_rujuk"),rs.getString("kd_faskes_tujuan"),rs.getString("nm_faskes_tujuan"),
                        rs.getString("kd_alasan"),rs.getString("alasan_rujuk"),rs.getString("alasan_lainnya"),
                        rs.getString("kd_diagnosa"),rs.getString("diagnosa_rujuk"),rs.getString("nik_dokter"),
                        rs.getString("dokter_perujuk"),rs.getString("nik_petugas"),rs.getString("petugas_entry"),
                        rs.getString("anamnesis_pemeriksaan"),rs.getString("kesadaran"),rs.getString("tekanan_darah"),
                        rs.getString("nadi"),rs.getString("suhu"),rs.getString("respirasi"),rs.getString("keadaan_umum"),
                        rs.getString("tingkat_nyeri"),rs.getString("alergi"),rs.getString("laboratorium"),
                        rs.getString("radiologi"),rs.getString("terapitindakan")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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
    
    private void tampilData2(){
        Valid.tabelKosong(tabMode2);
        try{
            ps=koneksi.prepareStatement(
                "select sisrute_rujukan_keluar.no_rujuk,sisrute_rujukan_keluar.no_rkm_medis,"+
                "sisrute_rujukan_keluar.nm_pasien,sisrute_rujukan_keluar.nm_faskes_tujuan from sisrute_rujukan_keluar where "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_rawat like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_rujuk like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_rkm_medis like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.nm_pasien like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_ktp like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.no_peserta like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.alamat like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.jns_rujukan like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.nm_faskes_tujuan like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.alasan_rujuk like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.kd_diagnosa like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.diagnosa_rujuk like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.dokter_perujuk like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.petugas_entry like ? or "+
                "sisrute_rujukan_keluar.tgl_rujuk between ? and ? and sisrute_rujukan_keluar.kesadaran like ? "+
                "order by sisrute_rujukan_keluar.tgl_rujuk desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                ps.setString(37,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(38,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(39,"%"+TCari.getText().trim()+"%");
                ps.setString(40,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(41,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(42,"%"+TCari.getText().trim()+"%");
                ps.setString(43,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(44,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(45,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    StatusDirespon="";StatusDiterima="";
                    try {
                        Valid.tabelKosong(tabMode);
                        URL = link+"/rujukan?nomor="+rs.getString("no_rujuk")+"&create=true";
                        headers = new HttpHeaders();
                        headers.add("X-cons-id",idrs);
                        headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
                        headers.add("X-signature",api.getHmac()); 
                        headers.add("Content-type","application/json");             
                        headers.add("Content-length",null);            
                        requestEntity = new HttpEntity(headers);
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                        nameNode = root.path("status");
                        System.out.println("Result : "+root.path("status").asText());
                        if(nameNode.asText().equals("200")){                
                            response = root.path("data");
                            if(response.isArray()){
                                for(JsonNode list:response){
                                    StatusDirespon=list.path("RUJUKAN").path("STATUS").path("NAMA").asText();
                                    if(!list.path("RUJUKAN").path("STATUS_DITERIMA").path("NAMA").asText().equals("")){
                                        StatusDiterima=list.path("RUJUKAN").path("STATUS_DITERIMA").path("NAMA").asText()+", "+list.path("RUJUKAN").path("STATUS_DITERIMA").path("KETERANGAN").asText();
                                    }                                        
                                }
                            }    
                        }  
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                    }
                    tabMode2.addRow(new Object[]{
                        rs.getString("no_rujuk"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_faskes_tujuan"),StatusDirespon,StatusDiterima
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
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
        LCount.setText(""+tabMode2.getRowCount()); 
    }    
    
    private void emptTeks(){
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        NoKTP.setText("");
        NoKartu.setText("");
        JK.setText("");
        TmpLahir.setText("");
        TglLahir.setText("");
        Kontak.setText("");
        Alamat.setText("");
        KdFaskes.setText("");
        NmFaskes.setText("");
        KdAlasan.setText("");
        NmAlasan.setText("");
        AlasanLainnya.setText("");
        KdDiagnosa.setText("");
        NmDiagnosa.setText("");
        Anamnesis.setText("");
        TekananDarah.setText("");
        FrekuensiNadi.setText("");
        SuhuBadan.setText("");
        Respirasi.setText("");
        KeadaanUmum.setText("");
        Alergi.setText("");
        Laborat.setText("");
        Radiologi.setText("");
        TerapiTindakan.setText("");
        JenisRujukan.requestFocus();
    }
         
    public void isCek(){
        BtnSimpan.setEnabled(akses.getsisrute_rujukan_keluar());
        BtnHapus.setEnabled(akses.getsisrute_rujukan_keluar());
        BtnPrint.setEnabled(akses.getsisrute_rujukan_keluar());
        BtnEdit.setEnabled(akses.getsisrute_rujukan_keluar());   
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            Sequel.cariIsi("select no_ktp from pegawai where nik=?",KdPetugas,akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?",PetugasEntry,akses.getkode());
        }  
    }
    
    public void tutupInput(){
        TabRawat.setSelectedIndex(1);
        tampil();
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            NoKTP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            NoKartu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            TmpLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Kontak.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            JenisRujukan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());            
            Valid.SetTgl2(TanggalRujuk,tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KdFaskes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            NmFaskes.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KdAlasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            NmAlasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            AlasanLainnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KdDiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NmDiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            DokterPerujuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            PetugasEntry.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Anamnesis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            JenisKesadaran.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            TekananDarah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            FrekuensiNadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            SuhuBadan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Respirasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            KeadaanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            TingkatNyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Alergi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Laborat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Radiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            TerapiTindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
        }
    }
    
    public void setPasien(String NoRawat){
        TabRawat.setSelectedIndex(0);
        TNoRw.setText(NoRawat);
        try {
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rawat,nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) asal,"+
                "pasien.no_rkm_medis,pasien.no_ktp,pasien.no_peserta,pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.no_tlp, "+
                "pegawai.nama,pegawai.no_ktp as ktpdokter from pasien inner join reg_periksa inner join kelurahan inner join kecamatan "+
                "inner join kabupaten inner join propinsi inner join pegawai "+
                "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_prop=propinsi.kd_prop "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pegawai.nik=reg_periksa.kd_dokter "+
                "where reg_periksa.no_rawat=?");
            try {            
                ps.setString(1,NoRawat);
                rs=ps.executeQuery();
                while(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    NoKTP.setText(rs.getString("no_ktp"));
                    NoKartu.setText(rs.getString("no_peserta"));
                    JK.setText(rs.getString("jk"));
                    TmpLahir.setText(rs.getString("tmp_lahir"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Kontak.setText(rs.getString("no_tlp"));
                    Alamat.setText(rs.getString("asal"));
                    KdDokter.setText(rs.getString("ktpdokter"));
                    DokterPerujuk.setText(rs.getString("nama"));
                    ps2=koneksi.prepareStatement(
                        "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit from penyakit "+
                        "inner join diagnosa_pasien on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit "+
                        "where diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat=?");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            KdDiagnosa.setText(rs2.getString("kd_penyakit"));
                            NmDiagnosa.setText(rs2.getString("nm_penyakit"));
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
                    
                    ps2=koneksi.prepareStatement(
                        "select suhu_tubuh, tensi, nadi, respirasi, tinggi, berat, gcs,keluhan, pemeriksaan, "+
                        "alergi, imun_ke, rtl from pemeriksaan_ralan where no_rawat=? order by tgl_perawatan desc limit 1");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            Anamnesis.setText(rs2.getString("pemeriksaan"));
                            SuhuBadan.setText(rs2.getString("suhu_tubuh"));
                            TekananDarah.setText(rs2.getString("tensi"));
                            FrekuensiNadi.setText(rs2.getString("nadi"));
                            Respirasi.setText(rs2.getString("respirasi"));
                            Alergi.setText(rs2.getString("alergi"));
                            KeadaanUmum.setText(rs2.getString("keluhan"));
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
                    
                    ps2=koneksi.prepareStatement(
                        "select template_laboratorium.Pemeriksaan,detail_periksa_lab.nilai from template_laboratorium "+
                        "inner join detail_periksa_lab on template_laboratorium.id_template=detail_periksa_lab.id_template "+
                        "where detail_periksa_lab.no_rawat=?");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        cari="";
                        while(rs2.next()){                            
                            cari=cari+rs2.getString("Pemeriksaan")+":"+rs2.getString("nilai")+";";
                        }
                        
                        Laborat.setText(cari);
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
                    
                    ps2=koneksi.prepareStatement(
                        "select hasil_radiologi.hasil from hasil_radiologi where hasil_radiologi.no_rawat=?");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        cari="";
                        while(rs2.next()){                            
                            cari=cari+rs2.getString("hasil")+";";
                        }
                        
                        Radiologi.setText(cari);
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
                    
                    ps2=koneksi.prepareStatement(
                        "select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                        "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=?");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        cari="";
                        while(rs2.next()){                            
                            cari=cari+rs2.getString("nama_brng")+";";
                        }
                        
                        if(!cari.equals("")){
                            cari="TRP:"+cari;
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
                    
                    cari2="";
                    ps2=koneksi.prepareStatement(
                        "select jns_perawatan.nm_perawatan from jns_perawatan inner join rawat_jl_dr "+
                        "on jns_perawatan.kd_jenis_prw=rawat_jl_dr.kd_jenis_prw where no_rawat=? group by jns_perawatan.nm_perawatan");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            cari2=cari2+rs2.getString("nm_perawatan")+";";
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
                    
                    ps2=koneksi.prepareStatement(
                        "select jns_perawatan.nm_perawatan from jns_perawatan inner join rawat_jl_pr "+
                        "on jns_perawatan.kd_jenis_prw=rawat_jl_pr.kd_jenis_prw where no_rawat=? group by jns_perawatan.nm_perawatan");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            cari2=cari2+rs2.getString("nm_perawatan")+";";
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
                    
                    ps2=koneksi.prepareStatement(
                        "select jns_perawatan.nm_perawatan from jns_perawatan inner join rawat_jl_drpr "+
                        "on jns_perawatan.kd_jenis_prw=rawat_jl_drpr.kd_jenis_prw where no_rawat=? group by jns_perawatan.nm_perawatan");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            cari2=cari2+rs2.getString("nm_perawatan")+";";
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
                    
                    if(!cari2.equals("")){
                        if(cari.equals("")){
                            cari2="TDK:"+cari2;
                        }else{
                            cari2="#TDK:"+cari2;
                        } 
                    }
                    TerapiTindakan.setText(cari+cari2);
                }
                JenisRujukan.requestFocus();
            } catch (Exception ex) {
                System.out.println(ex);
            }finally{
                if(rs != null ){
                    rs.close();
                }                
                if(ps != null ){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void setPasien2(String NoRawat){
        TabRawat.setSelectedIndex(0);
        TNoRw.setText(NoRawat);
        try {
            ps=koneksi.prepareStatement(
                "select reg_periksa.no_rawat,nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) asal,"+
                "pasien.no_rkm_medis,pasien.no_ktp,pasien.no_peserta,pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.no_tlp, "+
                "pegawai.nama,pegawai.no_ktp as ktpdokter from pasien inner join reg_periksa inner join kelurahan inner join kecamatan "+
                "inner join kabupaten inner join propinsi inner join pegawai "+
                "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_prop=propinsi.kd_prop "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pegawai.nik=reg_periksa.kd_dokter "+
                "where reg_periksa.no_rawat=?");
            try {            
                ps.setString(1,NoRawat);
                rs=ps.executeQuery();
                while(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    NoKTP.setText(rs.getString("no_ktp"));
                    NoKartu.setText(rs.getString("no_peserta"));
                    JK.setText(rs.getString("jk"));
                    TmpLahir.setText(rs.getString("tmp_lahir"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Kontak.setText(rs.getString("no_tlp"));
                    Alamat.setText(rs.getString("asal"));
                    KdDokter.setText(rs.getString("ktpdokter"));
                    DokterPerujuk.setText(rs.getString("nama"));
                    ps2=koneksi.prepareStatement(
                        "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit from penyakit "+
                        "inner join diagnosa_pasien on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit "+
                        "where diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat=?");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            KdDiagnosa.setText(rs2.getString("kd_penyakit"));
                            NmDiagnosa.setText(rs2.getString("nm_penyakit"));
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
                    
                    ps2=koneksi.prepareStatement(
                        "select suhu_tubuh, tensi, nadi, respirasi, tinggi, berat, gcs,keluhan, pemeriksaan, "+
                        "alergi from pemeriksaan_ranap where no_rawat=? order by tgl_perawatan desc limit 1");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            Anamnesis.setText(rs2.getString("pemeriksaan"));
                            SuhuBadan.setText(rs2.getString("suhu_tubuh"));
                            TekananDarah.setText(rs2.getString("tensi"));
                            FrekuensiNadi.setText(rs2.getString("nadi"));
                            Respirasi.setText(rs2.getString("respirasi"));
                            Alergi.setText(rs2.getString("alergi"));
                            KeadaanUmum.setText(rs2.getString("keluhan"));
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
                    
                    ps2=koneksi.prepareStatement(
                        "select template_laboratorium.Pemeriksaan,detail_periksa_lab.nilai from template_laboratorium "+
                        "inner join detail_periksa_lab on template_laboratorium.id_template=detail_periksa_lab.id_template "+
                        "where detail_periksa_lab.no_rawat=?");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        cari="";
                        while(rs2.next()){                            
                            cari=cari+rs2.getString("Pemeriksaan")+":"+rs2.getString("nilai")+";";
                        }
                        
                        Laborat.setText(cari);
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
                    
                    ps2=koneksi.prepareStatement(
                        "select hasil_radiologi.hasil from hasil_radiologi where hasil_radiologi.no_rawat=?");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        cari="";
                        while(rs2.next()){                            
                            cari=cari+rs2.getString("hasil")+";";
                        }
                        
                        Radiologi.setText(cari);
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
                    
                    ps2=koneksi.prepareStatement(
                        "select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                        "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=?");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        cari="";
                        while(rs2.next()){                            
                            cari=cari+rs2.getString("nama_brng")+";";
                        }
                        if(!cari.equals("")){
                            cari="TRP:"+cari;
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
                    
                    cari2="";
                    ps2=koneksi.prepareStatement(
                        "select jns_perawatan.nm_perawatan from jns_perawatan inner join rawat_inap_dr "+
                        "on jns_perawatan.kd_jenis_prw=rawat_inap_dr.kd_jenis_prw where no_rawat=? group by jns_perawatan.nm_perawatan");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            cari2=cari2+rs2.getString("nm_perawatan")+";";
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
                    
                    ps2=koneksi.prepareStatement(
                        "select jns_perawatan.nm_perawatan from jns_perawatan inner join rawat_inap_pr "+
                        "on jns_perawatan.kd_jenis_prw=rawat_inap_pr.kd_jenis_prw where no_rawat=? group by jns_perawatan.nm_perawatan");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            cari2=cari2+rs2.getString("nm_perawatan")+";";
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
                    
                    ps2=koneksi.prepareStatement(
                        "select jns_perawatan.nm_perawatan from jns_perawatan inner join rawat_inap_drpr "+
                        "on jns_perawatan.kd_jenis_prw=rawat_inap_drpr.kd_jenis_prw where no_rawat=? group by jns_perawatan.nm_perawatan");
                    try {
                        ps2.setString(1,NoRawat);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            cari2=cari2+rs2.getString("nm_perawatan")+";";
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
                    
                    if(!cari2.equals("")){
                        if(cari.equals("")){
                            cari2="TDK:"+cari2;
                        }else{
                            cari2="#TDK:"+cari2;
                        }                            
                    }
                    TerapiTindakan.setText(cari+cari2);
                }
                JenisRujukan.requestFocus();
            } catch (Exception ex) {
                System.out.println(ex);
            }finally{
                if(rs != null ){
                    rs.close();
                }                
                if(ps != null ){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

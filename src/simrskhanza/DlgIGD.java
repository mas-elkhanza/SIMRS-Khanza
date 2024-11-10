/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgReg.java
 *
 * Created on Jun 8, 2010, choose Tools | Templates
 * and open the template in10:28:56 PM
 */

package simrskhanza;
import bridging.BPJSCekDataIndukKecelakaan;
import bridging.BPJSCekSuplesiJasaRaharja;
import rekammedis.RMRiwayatPerawatan;
import permintaan.DlgBookingOperasi;
import kepegawaian.DlgCariDokter;
import inventory.DlgResepObat;
import inventory.DlgPemberianObat;
import bridging.BPJSDataSEP;
import bridging.BPJSProgramPRB;
import bridging.BPJSSPRI;
import bridging.BPJSSuratKontrol;
import bridging.CoronaPasien;
import bridging.DlgDataTB;
import bridging.ICareRiwayatPerawatan;
import bridging.ICareRiwayatPerawatanFKTP;
import surat.SuratKontrol;
import bridging.INACBGPerawatanCorona;
import bridging.InhealthDataSJP;
import bridging.PCareDataPendaftaran;
import bridging.SisruteRujukanKeluar;
import laporan.DlgFrekuensiPenyakitRalan;
import keuangan.DlgBilingRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import grafikanalisa.grafikperiksaperagama;
import grafikanalisa.grafikperiksaperbulan;
import grafikanalisa.grafikperiksaperdokter;
import grafikanalisa.grafikperiksaperhari;
import grafikanalisa.grafikperiksaperjk;
import grafikanalisa.grafikperiksaperpekerjaan;
import grafikanalisa.grafikperiksaperpoli;
import grafikanalisa.grafikperiksapertahun;
import grafikanalisa.grafiksql;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCopyResep;
import inventory.DlgPeresepanDokter;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingParsialRalan;
import keuangan.DlgLhtPiutang;
import laporan.DlgBerkasRawat;
import laporan.DlgDataInsidenKeselamatan;
import rekammedis.RMDataResumePasien;
import laporan.DlgDiagnosaPenyakit;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanPelayananInformasiObat;
import permintaan.DlgPermintaanRadiologi;
import permintaan.DlgPermintaanRanap;
import rekammedis.RMCatatanADIMEGizi;
import rekammedis.RMCatatanAnastesiSedasi;
import rekammedis.RMCatatanPersalinan;
import rekammedis.RMChecklistKriteriaMasukHCU;
import rekammedis.RMChecklistKriteriaMasukICU;
import rekammedis.RMChecklistPemberianFibrinolitik;
import rekammedis.RMChecklistPostOperasi;
import rekammedis.RMChecklistPreOperasi;
import rekammedis.RMDataAsuhanGizi;
import rekammedis.RMDataCatatanCekGDS;
import rekammedis.RMDataCatatanKeperawatanRalan;
import rekammedis.RMDataCatatanKeseimbanganCairan;
import rekammedis.RMDataCatatanObservasiCHBP;
import rekammedis.RMDataCatatanObservasiIGD;
import rekammedis.RMDataCatatanObservasiInduksiPersalinan;
import rekammedis.RMDataMonitoringAsuhanGizi;
import rekammedis.RMDataMonitoringReaksiTranfusi;
import rekammedis.RMDataSkriningGiziLanjut;
import rekammedis.RMHemodialisa;
import rekammedis.RMDeteksiDiniCorona;
import rekammedis.RMEdukasiPasienKeluargaRawatJalan;
import rekammedis.RMHasilPemeriksaanEKG;
import rekammedis.RMKonselingFarmasi;
import rekammedis.RMMonitoringAldrettePascaAnestesi;
import rekammedis.RMMonitoringBromagePascaAnestesi;
import rekammedis.RMMonitoringStewardPascaAnestesi;
import rekammedis.RMPemantauanMEOWS;
import rekammedis.RMPemantauanPEWS;
import rekammedis.RMPemantauanEWSD;
import rekammedis.RMPemantauanEWSNeonatus;
import rekammedis.RMPengkajianRestrain;
import rekammedis.RMPenilaianAwalKeperawatanIGD;
import rekammedis.RMPenilaianAwalMedisIGD;
import rekammedis.RMPenilaianAwalMedisHemodialisa;
import rekammedis.RMPenilaianAwalMedisIGDPsikiatri;
import rekammedis.RMPenilaianFisioterapi;
import rekammedis.RMPenilaianKorbanKekerasan;
import rekammedis.RMPenilaianLanjutanRisikoJatuhAnak;
import rekammedis.RMPenilaianLanjutanRisikoJatuhDewasa;
import rekammedis.RMPenilaianLanjutanRisikoJatuhGeriatri;
import rekammedis.RMPenilaianLanjutanRisikoJatuhLansia;
import rekammedis.RMPenilaianLanjutanRisikoJatuhPsikiatri;
import rekammedis.RMPenilaianLanjutanSkriningFungsional;
import rekammedis.RMPenilaianPasienImunitasRendah;
import rekammedis.RMPenilaianPasienKeracunan;
import rekammedis.RMPenilaianPasienPenyakitMenular;
import rekammedis.RMPenilaianPasienTerminal;
import rekammedis.RMPenilaianPreAnastesi;
import rekammedis.RMPenilaianPreInduksi;
import rekammedis.RMPenilaianPreOperasi;
import rekammedis.RMPenilaianPsikologi;
import rekammedis.RMPenilaianRisikoJatuhNeonatus;
import rekammedis.RMPenilaianTambahanBunuhDiri;
import rekammedis.RMPenilaianTambahanGeriatri;
import rekammedis.RMPenilaianTambahanMelarikanDiri;
import rekammedis.RMPenilaianTambahanPerilakuKekerasan;
import rekammedis.RMPenilaianUlangNyeri;
import rekammedis.RMRekonsiliasiObat;
import rekammedis.RMSignInSebelumAnastesi;
import rekammedis.RMSignOutSebelumMenutupLuka;
import rekammedis.RMSkriningAdiksiNikotin;
import rekammedis.RMSkriningInstrumenSDQ;
import rekammedis.RMSkriningKekerasanPadaPerempuan;
import rekammedis.RMSkriningKesehatanGigiMulutRemaja;
import rekammedis.RMSkriningMerokokUsiaSekolahRemaja;
import rekammedis.RMSkriningNutrisiAnak;
import rekammedis.RMSkriningNutrisiDewasa;
import rekammedis.RMSkriningNutrisiLansia;
import rekammedis.RMSkriningObesitas;
import rekammedis.RMSkriningPUMA;
import rekammedis.RMSkriningRisikoKankerParu;
import rekammedis.RMSkriningRisikoKankerPayudara;
import rekammedis.RMSkriningSRQ;
import rekammedis.RMSkriningTBC;
import rekammedis.RMSkriningTalasemia;
import rekammedis.RMTimeOutSebelumInsisi;
import rekammedis.RMTransferPasienAntarRuang;
import rekammedis.RMTriaseIGD;
import surat.SuratBebasNarkoba;
import surat.SuratBebasTato;
import surat.SuratButaWarna;
import surat.SuratCutiHamil;
import surat.SuratKeteranganBebasTBC;
import surat.SuratKeteranganCovid;
import surat.SuratKewaspadaanKesehatan;
import surat.SuratPenolakanAnjuranMedis;
import surat.SuratPernyataanPasienUmum;
import surat.SuratPersetujuanPenolakanTindakan;
import surat.SuratPersetujuanPenundaanPelayanan;
import surat.SuratPersetujuanRawatInap;
import surat.SuratPersetujuanUmum;
import surat.SuratPulangAtasPermintaanSendiri;
import surat.SuratSakit;
import surat.SuratSakitPihak2;
import surat.SuratTidakHamil;

/**
 *
 * @author dosen
 */
public final class DlgIGD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgPasien pasien=new DlgPasien(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgRujukMasuk rujukmasuk=new DlgRujukMasuk(null,false);
    private PreparedStatement ps,ps3,pscaripiutang;
    private ResultSet rs;
    private boolean ceksukses=false;
    private int i=0,jmlparsial=0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private double biaya=0,biayabaru=0,biayalama=0;
    private String kdigd="",nosisrute="",aktifkanparsial="no",URUTNOREG="",terbitsep="",
            status="Baru",alamatperujuk="-",umur="0",sttsumur="Th",IPPRINTERTRACER="",norawatdipilih="",normdipilih="",
            validasiregistrasi=Sequel.cariIsi("select set_validasi_registrasi.wajib_closing_kasir from set_validasi_registrasi"),
            validasicatatan=Sequel.cariIsi("select set_validasi_catatan.tampilkan_catatan from set_validasi_catatan"),variabel="";
    private char ESC = 27;
    // ganti kertas
    private char[] FORM_FEED = {12};
    // reset setting
    private char[] RESET = {ESC,'@'};
    // huruf tebal diaktifkan
    private char[] BOLD_ON = {ESC,'E'};
    // huruf tebal dimatikan
    private char[] BOLD_OFF = {ESC,'F'};
    // huruf miring diaktifkan
    private char[] ITALIC_ON = {ESC,'4'};
    // huruf miring dimatikan
    private char[] ITALIC_OFF = {ESC,'5'};
    // mode draft diaktifkan
    private char[] MODE_DRAFT = {ESC,'x',0};
    private char[] MODE_NLQ = {ESC,'x',1};
    // font Roman (halaman 47)
    private char[] FONT_ROMAN = {ESC,'k',0};
    // font Sans serif
    private char[] FONT_SANS_SERIF = {ESC,'k',1};
    // font size (halaman 49)
    private char[] SIZE_5_CPI = {ESC,'W','1',ESC,'P'};
    private char[] SIZE_6_CPI = {ESC,'W','1',ESC,'M'};
    private char[] SIZE_10_CPI = {ESC,'P'};
    private char[] SIZE_12_CPI = {ESC,'M'};
    //font height
    private char[] HEIGHT_NORMAL = {ESC,'w', '0'};
    private char[] HEIGHT_DOUBLE = {ESC,'w', '1'};
    // double strike (satu dot dicetak 2 kali)
    private char[] DOUBLE_STRIKE_ON = {ESC,'G'};
    private char[] DOUBLE_STRIKE_OFF = {ESC,'H'};
    // http://www.berklix.com/~jhs/standards/escapes.epson
    // condensed (huruf kurus)
    private char[] CONDENSED_ON = {15};
    private char[] CONDENSED_OFF = {18};
    // condensed (huruf gemuk)
    private char[] ENLARGED_ON = {(char) 14};
    private char[] ENLARGED_OFF = {(char) 20};
    // line spacing
    private char[] SPACING_9_72 = {ESC, '0'};
    private char[] SPACING_7_72 = {ESC, '1'};
    private char[] SPACING_12_72 = {ESC, '2'};
    // set unit for margin setting
    private char[] UNIT_1_360 = {ESC,40, 'U', '1', '0'};
    // move vertical print position
    private char[] VERTICAL_PRINT_POSITION = {ESC, 'J', '1'};

    /** Creates new form DlgReg
     * @param parent
     * @param modal */
    public DlgIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initIGD();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"P","No.Reg","No.Rawat","Tanggal","Jam","Kd.Dokter","Dokter Dituju","Nomer RM",
            "Pasien","J.K.","Umur","Poliklinik","Penanggung Jawab","Alamat P.J.","Hubungan dg P.J.",
            "Biaya Regristrasi","Status","Jenis Bayar","Stts Rawat","Kd PJ","Status Bayar"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPetugas.setModel(tabMode);

        tbPetugas.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPetugas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbPetugas.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(45);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(50);   
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(30);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(140);
            }else if(i==12){
                column.setPreferredWidth(140);
            }else if(i==13){
                column.setPreferredWidth(200);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(50);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==20){
                column.setPreferredWidth(70);
            }
        }
        tbPetugas.setDefaultRenderer(Object.class, new WarnaTable());

        TNoReg.setDocument(new batasInput((byte)8).getKata(TNoReg));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TNoRM.setDocument(new batasInput((byte)10).getKata(TNoRM));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        kdpnj.setDocument(new batasInput((byte)3).getKata(kdpnj));
        TPngJwb.setDocument(new batasInput((byte)30).getKata(TPngJwb));
        AsalRujukan.setDocument(new batasInput((byte)60).getKata(AsalRujukan));
        THbngn.setDocument(new batasInput((byte)20).getKata(THbngn));
        TAlmt.setDocument(new batasInput((byte)60).getKata(TAlmt));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
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
        jam();                        
        
        ChkInput.setSelected(false);
        isForm(); 
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(pasien.getTable().getSelectedRow()!= -1){ 
                        TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                        isPas();
                        isNumber();                                        
                    }  
                    if(pasien.getTable2().getSelectedRow()!= -1){ 
                        TNoRM.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),1).toString());
                        isPas();
                        isNumber();                                        
                    } 
                    if(pasien.getTable3().getSelectedRow()!= -1){ 
                        TNoRM.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),1).toString());
                        isPas();
                        isNumber();                                        
                    }  
                    TNoRM.requestFocus();
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        pasien.getTable2().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        pasien.getTable3().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        rujukmasuk.WindowPerujuk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgReg")){
                    if(rujukmasuk.tbPerujuk.getSelectedRow()!= -1){
                        AsalRujukan.setText(rujukmasuk.tbPerujuk.getValueAt(rujukmasuk.tbPerujuk.getSelectedRow(),0).toString());
                        alamatperujuk=rujukmasuk.tbPerujuk.getValueAt(rujukmasuk.tbPerujuk.getSelectedRow(),1).toString();
                    }    
                    AsalRujukan.requestFocus();
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
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(dokter.getTable().getSelectedRow()!= -1){    
                            KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            isNumber();
                            KdDokter.requestFocus();
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
                           
        
        pasien.kab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(pasien.kab.getTable().getSelectedRow()!= -1){                   
                        Kabupaten2.setText(pasien.kab.getTable().getValueAt(pasien.kab.getTable().getSelectedRow(),0).toString());
                    }     
                    Kabupaten2.requestFocus();
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
        
        pasien.kec.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(pasien.kec.getTable().getSelectedRow()!= -1){                   
                        Kecamatan2.setText(pasien.kec.getTable().getValueAt(pasien.kec.getTable().getSelectedRow(),0).toString());
                    }                
                    Kecamatan2.requestFocus();
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
        
        pasien.kel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(pasien.kel.getTable().getSelectedRow()!= -1){                   
                        Kelurahan2.setText(pasien.kel.getTable().getValueAt(pasien.kel.getTable().getSelectedRow(),0).toString());
                    }  
                    Kelurahan2.requestFocus();
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
        
        pasien.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(pasien.penjab.getTable().getSelectedRow()!= -1){
                        kdpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(),1).toString());
                        nmpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(),2).toString());
                    }    
                    kdpnj.requestFocus();
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
        
        pasien.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgIGD")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.penjab.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        DlgCatatan.setSize(595,34);
        
        try {
            IPPRINTERTRACER=koneksiDB.IPPRINTERTRACER();
            URUTNOREG=koneksiDB.URUTNOREG();            
            aktifkanparsial=koneksiDB.AKTIFKANBILLINGPARSIAL();
            ps3=koneksi.prepareStatement("select poliklinik.registrasi,poliklinik.registrasilama,poliklinik.kd_poli from poliklinik where poliklinik.kd_poli='IGDK'");
            try {
                rs=ps3.executeQuery();
                if(rs.next()){
                    biayabaru=rs.getDouble("registrasi");
                    biayalama=rs.getDouble("registrasilama");
                    kdigd=rs.getString("kd_poli");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps3!=null){
                    ps3.close();
                }
            }
        } catch (Exception ex) {
            IPPRINTERTRACER="";
            URUTNOREG="";
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
        MnDataRM = new javax.swing.JMenu();
        MnRMIGD = new javax.swing.JMenu();
        MnDataTriaseIGD = new javax.swing.JMenuItem();
        MnPenilaianAwalKeperawatanIGD = new javax.swing.JMenuItem();
        MnPeniliaianAwalMedisIGD = new javax.swing.JMenuItem();
        MnPeniliaianAwalMedisIGDPsikiatri = new javax.swing.JMenuItem();
        MnPenilaianPasienKeracunan = new javax.swing.JMenuItem();
        MnCatatanObservasiIGD = new javax.swing.JMenuItem();
        MnPengkajianRestrain = new javax.swing.JMenuItem();
        MnPemantauanPEWSAnak = new javax.swing.JMenuItem();
        MnPemantauanPEWSDewasa = new javax.swing.JMenuItem();
        MnPemantauanMEOWS = new javax.swing.JMenuItem();
        MnPemantauanEWSNeonatus = new javax.swing.JMenuItem();
        MnPeniliaianAwalMedisHemodialisa = new javax.swing.JMenuItem();
        MnRMOperasi = new javax.swing.JMenu();
        MnChecklistPreOperasi = new javax.swing.JMenuItem();
        MnSignInSebelumAnestesi = new javax.swing.JMenuItem();
        MnTimeOutSebelumInsisi = new javax.swing.JMenuItem();
        MnSignOutSebelumMenutupLuka = new javax.swing.JMenuItem();
        MnChecklistPostOperasi = new javax.swing.JMenuItem();
        MnPenilaianPreOp = new javax.swing.JMenuItem();
        MnPenilaianPreAnestesi = new javax.swing.JMenuItem();
        MnSkorAldrettePascaAnestesi = new javax.swing.JMenuItem();
        MnSkorStewardPascaAnestesi = new javax.swing.JMenuItem();
        MnSkorBromagePascaAnestesi = new javax.swing.JMenuItem();
        MnRMHCU = new javax.swing.JMenu();
        MnCheckListKriteriaMasukHCU = new javax.swing.JMenuItem();
        MnCheckListKriteriaMasukICU = new javax.swing.JMenuItem();
        MnRMRisikoJatuh = new javax.swing.JMenu();
        MnPenilaianRisikoJatuhDewasa = new javax.swing.JMenuItem();
        MnPenilaianRisikoJatuhAnak = new javax.swing.JMenuItem();
        MnPenilaianRisikoJatuhLansia = new javax.swing.JMenuItem();
        MnPenilaianRisikoJatuhNeonatus = new javax.swing.JMenuItem();
        MnPenilaianRisikoJatuhGeriatri = new javax.swing.JMenuItem();
        MnPenilaianRisikoJatuhPsikiatri = new javax.swing.JMenuItem();
        MnPenilaianLanjutanSkriningFungsional = new javax.swing.JMenuItem();
        MnPenilaianLain = new javax.swing.JMenu();
        MnPenilaianTambahanGeriatri = new javax.swing.JMenuItem();
        MnPenilaianTambahanBunuhDiri = new javax.swing.JMenuItem();
        MnPenilaianTambahanPerilakuKekerasan = new javax.swing.JMenuItem();
        MnPenilaianTambahanMelarikanDiri = new javax.swing.JMenuItem();
        MnPenilaianPasienTerminal = new javax.swing.JMenuItem();
        MnPenilaianKorbanKekerasan = new javax.swing.JMenuItem();
        MnPenilaianPasienPenyakitMenular = new javax.swing.JMenuItem();
        MnPenilaianFisioterapi = new javax.swing.JMenuItem();
        MnPenilaianPsikolog = new javax.swing.JMenuItem();
        MnHemodialisa = new javax.swing.JMenuItem();
        MnRMFarmasi = new javax.swing.JMenu();
        MnKonselingFarmasi = new javax.swing.JMenuItem();
        MnRekonsiliasiObat = new javax.swing.JMenuItem();
        MnRMCatatanMonitoring = new javax.swing.JMenu();
        MnCatatanCekGDS = new javax.swing.JMenuItem();
        MnMonitoringReaksiTranfusi = new javax.swing.JMenuItem();
        MnPenilaianUlangNyeri = new javax.swing.JMenuItem();
        MnCatatanKeperawatan = new javax.swing.JMenuItem();
        MnCatatanPersalinan = new javax.swing.JMenuItem();
        MnGizi = new javax.swing.JMenu();
        ppSkriningNutrisiDewasa = new javax.swing.JMenuItem();
        ppSkriningNutrisiLansia = new javax.swing.JMenuItem();
        ppSkriningNutrisiAnak = new javax.swing.JMenuItem();
        ppSkriningGizi = new javax.swing.JMenuItem();
        ppAsuhanGizi = new javax.swing.JMenuItem();
        ppMonitoringAsuhanGizi = new javax.swing.JMenuItem();
        ppCatatanADIMEGizi = new javax.swing.JMenuItem();
        MnTransferAntarRuang = new javax.swing.JMenuItem();
        MnEdukasiPasienKeluarga = new javax.swing.JMenuItem();
        ppResume = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        ppDeteksiDIniCorona = new javax.swing.JMenuItem();
        MnPermintaan = new javax.swing.JMenu();
        MnJadwalOperasi = new javax.swing.JMenuItem();
        MnPermintaanLab = new javax.swing.JMenuItem();
        MnPermintaanRadiologi = new javax.swing.JMenuItem();
        MnPermintaanRanap = new javax.swing.JMenuItem();
        MnPermintaanInformasiObat = new javax.swing.JMenuItem();
        MnKamarInap = new javax.swing.JMenuItem();
        MnTindakan = new javax.swing.JMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaLabPA = new javax.swing.JMenuItem();
        MnPeriksaLabMB = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnObat = new javax.swing.JMenu();
        MnResepDOkter = new javax.swing.JMenuItem();
        MnNoResep = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnCopyResep = new javax.swing.JMenuItem();
        MnPilihBilling = new javax.swing.JMenu();
        MnBillingParsial = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        MnLaporanRekapKunjunganPoli = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganDokter = new javax.swing.JMenuItem();
        MnLaporanRekapJenisBayar = new javax.swing.JMenuItem();
        MnLaporanRekapRawatDarurat = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganBulanan = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganBulananPoli = new javax.swing.JMenuItem();
        MnLaporanRekapPenyakitRalan = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ppGrafikPerpoli = new javax.swing.JMenuItem();
        ppGrafikPerdokter = new javax.swing.JMenuItem();
        ppGrafikPerJK = new javax.swing.JMenuItem();
        ppGrafikPerPekerjaan = new javax.swing.JMenuItem();
        ppGrafikPerAgama = new javax.swing.JMenuItem();
        ppGrafikPerTahun = new javax.swing.JMenuItem();
        ppGrafikPerBulan = new javax.swing.JMenuItem();
        ppGrafikPerTanggal = new javax.swing.JMenuItem();
        ppGrafikDemografi = new javax.swing.JMenuItem();
        MnSuratSurat = new javax.swing.JMenu();
        SuratKontrol = new javax.swing.JMenuItem();
        MnSuratButaWarna = new javax.swing.JMenuItem();
        MnSuratBebasTato = new javax.swing.JMenuItem();
        MnSuratKewaspadaanKesehatan = new javax.swing.JMenuItem();
        MnCetakSuratBebasTBC = new javax.swing.JMenuItem();
        MnCetakSuratSakit = new javax.swing.JMenuItem();
        MnCetakSuratSakit2 = new javax.swing.JMenuItem();
        MnCetakSuratSakitPihak2 = new javax.swing.JMenuItem();
        MnCetakSuratHamil = new javax.swing.JMenuItem();
        MnCetakSuratCutiHamil = new javax.swing.JMenuItem();
        MnCetakSuratCovid = new javax.swing.JMenuItem();
        MnCetakBebasNarkoba = new javax.swing.JMenuItem();
        MnPersetujuanUmum = new javax.swing.JMenuItem();
        MnPersetujuanPenolakanTindakan = new javax.swing.JMenuItem();
        MnPulangAtasPermintaanSendiri = new javax.swing.JMenuItem();
        MnPernyataanPasienUmum = new javax.swing.JMenuItem();
        MnPersetujuanRawatInap = new javax.swing.JMenuItem();
        MnPersetujuanPenundaanPelayanan = new javax.swing.JMenuItem();
        MnPenolakanAnjuranMedis = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        MnCetakRegister = new javax.swing.JMenuItem();
        MnCetakRegister2 = new javax.swing.JMenuItem();
        MnCetakRegister1 = new javax.swing.JMenuItem();
        MnLembarCasemix = new javax.swing.JMenuItem();
        MnLembarCasemix1 = new javax.swing.JMenuItem();
        MnSPBK = new javax.swing.JMenuItem();
        MnSPBK1 = new javax.swing.JMenuItem();
        MnJKRA = new javax.swing.JMenuItem();
        MnLembarRalan = new javax.swing.JMenuItem();
        MnBlangkoResep = new javax.swing.JMenuItem();
        MnSuratJaminanPelayananIGD = new javax.swing.JMenuItem();
        MnLembarKeluarMasuk2 = new javax.swing.JMenuItem();
        MnGelang = new javax.swing.JMenu();
        MnGelang1 = new javax.swing.JMenuItem();
        MnGelang2 = new javax.swing.JMenuItem();
        MnGelang3 = new javax.swing.JMenuItem();
        MnGelang4 = new javax.swing.JMenuItem();
        MnGelang5 = new javax.swing.JMenuItem();
        MnGelang6 = new javax.swing.JMenuItem();
        MnLabelTracker = new javax.swing.JMenuItem();
        MnLabelTracker1 = new javax.swing.JMenuItem();
        MnLabelTracker2 = new javax.swing.JMenuItem();
        MnLabelTracker3 = new javax.swing.JMenuItem();
        MnBarcode1 = new javax.swing.JMenuItem();
        MnBarcode2 = new javax.swing.JMenuItem();
        MnBarcode3 = new javax.swing.JMenuItem();
        MnBarcode = new javax.swing.JMenuItem();
        MnBarcodeRM9 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        MnCheckList4 = new javax.swing.JMenuItem();
        MnCheckList5 = new javax.swing.JMenuItem();
        MnCheckList6 = new javax.swing.JMenuItem();
        MnCheckList7 = new javax.swing.JMenuItem();
        MnCheckList8 = new javax.swing.JMenuItem();
        MnCheckList9 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        MnCheckList = new javax.swing.JMenuItem();
        MnCheckList1 = new javax.swing.JMenuItem();
        MnCheckList2 = new javax.swing.JMenuItem();
        MnCheckList3 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        MnRujukan = new javax.swing.JMenu();
        MnRujuk = new javax.swing.JMenuItem();
        MnRujukMasuk = new javax.swing.JMenuItem();
        MnPoliInternal = new javax.swing.JMenuItem();
        MnBridging = new javax.swing.JMenu();
        MnSEP = new javax.swing.JMenuItem();
        ppSuratKontrol = new javax.swing.JMenuItem();
        ppSuratPRI = new javax.swing.JMenuItem();
        ppProgramPRB = new javax.swing.JMenuItem();
        ppSuplesiJasaRaharja = new javax.swing.JMenuItem();
        ppDataIndukKecelakaan = new javax.swing.JMenuItem();
        MnBelumTerbitSEP = new javax.swing.JMenuItem();
        MnSJP = new javax.swing.JMenuItem();
        MnPCare = new javax.swing.JMenuItem();
        MnRujukSisrute = new javax.swing.JMenuItem();
        ppPerawatanCorona = new javax.swing.JMenuItem();
        ppPasienCorona = new javax.swing.JMenuItem();
        MnTeridentifikasiTB = new javax.swing.JMenuItem();
        MnRiwayatPerawatanICareNIK = new javax.swing.JMenuItem();
        MnRiwayatPerawatanICareNoKartu = new javax.swing.JMenuItem();
        MnRiwayatPerawatanICareNIK1 = new javax.swing.JMenuItem();
        MnRiwayatPerawatanICareNoKartu1 = new javax.swing.JMenuItem();
        MenuInputData = new javax.swing.JMenu();
        MnDiagnosa = new javax.swing.JMenuItem();
        ppCatatanPasien = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        ppIKP = new javax.swing.JMenuItem();
        MnHapusData = new javax.swing.JMenu();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnGabungNoRawat = new javax.swing.JMenuItem();
        MnStatus = new javax.swing.JMenu();
        ppBerkas = new javax.swing.JMenuItem();
        MnSudah = new javax.swing.JMenuItem();
        MnBelum = new javax.swing.JMenuItem();
        MnBatal = new javax.swing.JMenuItem();
        MnDirujuk = new javax.swing.JMenuItem();
        MnDIrawat = new javax.swing.JMenuItem();
        MnMeninggal = new javax.swing.JMenuItem();
        MnPulangPaksa = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        MnStatusBaru = new javax.swing.JMenuItem();
        MnStatusLama = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        DlgDemografi = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa3 = new widget.PanelBiasa();
        BtnPrint3 = new widget.Button();
        BtnKeluar3 = new widget.Button();
        Kelurahan2 = new widget.TextBox();
        btnKel = new widget.Button();
        Kecamatan2 = new widget.TextBox();
        btnKec = new widget.Button();
        Kabupaten2 = new widget.TextBox();
        btnKab = new widget.Button();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        BtnPrint4 = new widget.Button();
        DlgSakit2 = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        panelBiasa4 = new widget.PanelBiasa();
        jLabel37 = new widget.Label();
        BtnPrint5 = new widget.Button();
        BtnKeluar4 = new widget.Button();
        NomorSurat = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        CrDokter3 = new widget.TextBox();
        jLabel24 = new widget.Label();
        NoBalasan = new widget.TextBox();
        DlgCatatan = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        LabelCatatan = new widget.Label();
        JK = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPetugas = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TDokter = new widget.TextBox();
        TNoRw = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel9 = new widget.Label();
        DTPReg = new widget.Tanggal();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        TPngJwb = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TNoReg = new widget.TextBox();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        jLabel7 = new widget.Label();
        TAlmt = new widget.TextBox();
        BtnPasien = new widget.Button();
        TPasien = new widget.TextBox();
        jLabel22 = new widget.Label();
        THbngn = new widget.TextBox();
        ChkJln = new widget.CekBox();
        KdDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel30 = new widget.Label();
        TStatus = new widget.TextBox();
        jLabel18 = new widget.Label();
        kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        btnPenjab = new widget.Button();
        AsalRujukan = new widget.TextBox();
        jLabel23 = new widget.Label();
        btnPenjab1 = new widget.Button();
        ChkTracker = new widget.CekBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDataRM.setBackground(new java.awt.Color(255, 255, 254));
        MnDataRM.setForeground(new java.awt.Color(50, 50, 50));
        MnDataRM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataRM.setText("Data Rekam Medis");
        MnDataRM.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataRM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataRM.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataRM.setName("MnDataRM"); // NOI18N
        MnDataRM.setPreferredSize(new java.awt.Dimension(200, 26));

        MnRMIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnRMIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnRMIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMIGD.setText("RM Gawat Darurat");
        MnRMIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMIGD.setName("MnRMIGD"); // NOI18N
        MnRMIGD.setPreferredSize(new java.awt.Dimension(250, 26));

        MnDataTriaseIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnDataTriaseIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataTriaseIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnDataTriaseIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDataTriaseIGD.setText("Data Triase Gawat Darurat");
        MnDataTriaseIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataTriaseIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataTriaseIGD.setName("MnDataTriaseIGD"); // NOI18N
        MnDataTriaseIGD.setPreferredSize(new java.awt.Dimension(230, 26));
        MnDataTriaseIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataTriaseIGDActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnDataTriaseIGD);

        MnPenilaianAwalKeperawatanIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianAwalKeperawatanIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianAwalKeperawatanIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianAwalKeperawatanIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianAwalKeperawatanIGD.setText("Penilaian Awal Keperawatan IGD");
        MnPenilaianAwalKeperawatanIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianAwalKeperawatanIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianAwalKeperawatanIGD.setName("MnPenilaianAwalKeperawatanIGD"); // NOI18N
        MnPenilaianAwalKeperawatanIGD.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPenilaianAwalKeperawatanIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianAwalKeperawatanIGDActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPenilaianAwalKeperawatanIGD);

        MnPeniliaianAwalMedisIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalMedisIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalMedisIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalMedisIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalMedisIGD.setText("Penilaian Awal Medis IGD");
        MnPeniliaianAwalMedisIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalMedisIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalMedisIGD.setName("MnPeniliaianAwalMedisIGD"); // NOI18N
        MnPeniliaianAwalMedisIGD.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPeniliaianAwalMedisIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalMedisIGDActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPeniliaianAwalMedisIGD);

        MnPeniliaianAwalMedisIGDPsikiatri.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalMedisIGDPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalMedisIGDPsikiatri.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalMedisIGDPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalMedisIGDPsikiatri.setText("Penilaian Awal Medis IGD Psikiatri");
        MnPeniliaianAwalMedisIGDPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalMedisIGDPsikiatri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalMedisIGDPsikiatri.setName("MnPeniliaianAwalMedisIGDPsikiatri"); // NOI18N
        MnPeniliaianAwalMedisIGDPsikiatri.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPeniliaianAwalMedisIGDPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalMedisIGDPsikiatriActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPeniliaianAwalMedisIGDPsikiatri);

        MnPenilaianPasienKeracunan.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPasienKeracunan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianPasienKeracunan.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPasienKeracunan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPasienKeracunan.setText("Penilaian Pasien Keracunan");
        MnPenilaianPasienKeracunan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPasienKeracunan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPasienKeracunan.setName("MnPenilaianPasienKeracunan"); // NOI18N
        MnPenilaianPasienKeracunan.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPenilaianPasienKeracunan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianPasienKeracunanActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPenilaianPasienKeracunan);

        MnCatatanObservasiIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanObservasiIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanObservasiIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanObservasiIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanObservasiIGD.setText("Observasi IGD");
        MnCatatanObservasiIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanObservasiIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanObservasiIGD.setName("MnCatatanObservasiIGD"); // NOI18N
        MnCatatanObservasiIGD.setPreferredSize(new java.awt.Dimension(230, 26));
        MnCatatanObservasiIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanObservasiIGDActionPerformed(evt);
            }
        });

        MnPengkajianRestrain.setBackground(new java.awt.Color(255, 255, 254));
        MnPengkajianRestrain.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPengkajianRestrain.setForeground(new java.awt.Color(50, 50, 50));
        MnPengkajianRestrain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPengkajianRestrain.setText("Pengkajian Restrain");
        MnPengkajianRestrain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPengkajianRestrain.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPengkajianRestrain.setName("MnPengkajianRestrain"); // NOI18N
        MnPengkajianRestrain.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPengkajianRestrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPengkajianRestrainActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPengkajianRestrain);

        MnPemantauanPEWSAnak.setBackground(new java.awt.Color(255, 255, 254));
        MnPemantauanPEWSAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemantauanPEWSAnak.setForeground(new java.awt.Color(50, 50, 50));
        MnPemantauanPEWSAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemantauanPEWSAnak.setText("Pemantauan PEWS Anak");
        MnPemantauanPEWSAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemantauanPEWSAnak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemantauanPEWSAnak.setName("MnPemantauanPEWSAnak"); // NOI18N
        MnPemantauanPEWSAnak.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPemantauanPEWSAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemantauanPEWSAnakActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPemantauanPEWSAnak);

        MnPemantauanPEWSDewasa.setBackground(new java.awt.Color(255, 255, 254));
        MnPemantauanPEWSDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemantauanPEWSDewasa.setForeground(new java.awt.Color(50, 50, 50));
        MnPemantauanPEWSDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemantauanPEWSDewasa.setText("Pemantauan EWS Dewasa");
        MnPemantauanPEWSDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemantauanPEWSDewasa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemantauanPEWSDewasa.setName("MnPemantauanPEWSDewasa"); // NOI18N
        MnPemantauanPEWSDewasa.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPemantauanPEWSDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemantauanPEWSDewasaActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPemantauanPEWSDewasa);

        MnPemantauanMEOWS.setBackground(new java.awt.Color(255, 255, 254));
        MnPemantauanMEOWS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemantauanMEOWS.setForeground(new java.awt.Color(50, 50, 50));
        MnPemantauanMEOWS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemantauanMEOWS.setText("Pemantauan MEOWS Obstetri");
        MnPemantauanMEOWS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemantauanMEOWS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemantauanMEOWS.setName("MnPemantauanMEOWS"); // NOI18N
        MnPemantauanMEOWS.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPemantauanMEOWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemantauanMEOWSActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPemantauanMEOWS);

        MnPemantauanEWSNeonatus.setBackground(new java.awt.Color(255, 255, 254));
        MnPemantauanEWSNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemantauanEWSNeonatus.setForeground(new java.awt.Color(50, 50, 50));
        MnPemantauanEWSNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemantauanEWSNeonatus.setText("Pemantauan EWS Neonatus");
        MnPemantauanEWSNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemantauanEWSNeonatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemantauanEWSNeonatus.setName("MnPemantauanEWSNeonatus"); // NOI18N
        MnPemantauanEWSNeonatus.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPemantauanEWSNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemantauanEWSNeonatusActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPemantauanEWSNeonatus);

        MnPeniliaianAwalMedisHemodialisa.setBackground(new java.awt.Color(255, 255, 254));
        MnPeniliaianAwalMedisHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeniliaianAwalMedisHemodialisa.setForeground(new java.awt.Color(50, 50, 50));
        MnPeniliaianAwalMedisHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeniliaianAwalMedisHemodialisa.setText("Penilaian Awal Medis Hemodialisa");
        MnPeniliaianAwalMedisHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeniliaianAwalMedisHemodialisa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeniliaianAwalMedisHemodialisa.setName("MnPeniliaianAwalMedisHemodialisa"); // NOI18N
        MnPeniliaianAwalMedisHemodialisa.setPreferredSize(new java.awt.Dimension(230, 26));
        MnPeniliaianAwalMedisHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeniliaianAwalMedisHemodialisaActionPerformed(evt);
            }
        });
        MnRMIGD.add(MnPeniliaianAwalMedisHemodialisa);

        MnRMOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnRMOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnRMOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMOperasi.setText("RM Operasi");
        MnRMOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMOperasi.setName("MnRMOperasi"); // NOI18N
        MnRMOperasi.setPreferredSize(new java.awt.Dimension(250, 26));

        MnChecklistPreOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnChecklistPreOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnChecklistPreOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnChecklistPreOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnChecklistPreOperasi.setText("Check List Pre Operasi");
        MnChecklistPreOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnChecklistPreOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnChecklistPreOperasi.setName("MnChecklistPreOperasi"); // NOI18N
        MnChecklistPreOperasi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnChecklistPreOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnChecklistPreOperasiActionPerformed(evt);
            }
        });

        MnSignInSebelumAnestesi.setBackground(new java.awt.Color(255, 255, 254));
        MnSignInSebelumAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSignInSebelumAnestesi.setForeground(new java.awt.Color(50, 50, 50));
        MnSignInSebelumAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSignInSebelumAnestesi.setText("Sign-In Sebelum Anestesi");
        MnSignInSebelumAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSignInSebelumAnestesi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSignInSebelumAnestesi.setName("MnSignInSebelumAnestesi"); // NOI18N
        MnSignInSebelumAnestesi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSignInSebelumAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSignInSebelumAnestesiActionPerformed(evt);
            }
        });

        MnTimeOutSebelumInsisi.setBackground(new java.awt.Color(255, 255, 254));
        MnTimeOutSebelumInsisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTimeOutSebelumInsisi.setForeground(new java.awt.Color(50, 50, 50));
        MnTimeOutSebelumInsisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTimeOutSebelumInsisi.setText("Time-Out Sebelum Insisi");
        MnTimeOutSebelumInsisi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTimeOutSebelumInsisi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTimeOutSebelumInsisi.setName("MnTimeOutSebelumInsisi"); // NOI18N
        MnTimeOutSebelumInsisi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnTimeOutSebelumInsisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTimeOutSebelumInsisiActionPerformed(evt);
            }
        });

        MnSignOutSebelumMenutupLuka.setBackground(new java.awt.Color(255, 255, 254));
        MnSignOutSebelumMenutupLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSignOutSebelumMenutupLuka.setForeground(new java.awt.Color(50, 50, 50));
        MnSignOutSebelumMenutupLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSignOutSebelumMenutupLuka.setText("Sign-Out Sebelum Menutup Luka");
        MnSignOutSebelumMenutupLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSignOutSebelumMenutupLuka.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSignOutSebelumMenutupLuka.setName("MnSignOutSebelumMenutupLuka"); // NOI18N
        MnSignOutSebelumMenutupLuka.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSignOutSebelumMenutupLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSignOutSebelumMenutupLukaActionPerformed(evt);
            }
        });

        MnChecklistPostOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnChecklistPostOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnChecklistPostOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnChecklistPostOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnChecklistPostOperasi.setText("Check List Post Operasi");
        MnChecklistPostOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnChecklistPostOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnChecklistPostOperasi.setName("MnChecklistPostOperasi"); // NOI18N
        MnChecklistPostOperasi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnChecklistPostOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnChecklistPostOperasiActionPerformed(evt);
            }
        });

        MnPenilaianPreOp.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPreOp.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianPreOp.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPreOp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPreOp.setText("Penilaian Pre Operasi");
        MnPenilaianPreOp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPreOp.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPreOp.setName("MnPenilaianPreOp"); // NOI18N
        MnPenilaianPreOp.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPenilaianPreOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianPreOpActionPerformed(evt);
            }
        });

        MnPenilaianPreAnestesi.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPreAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianPreAnestesi.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPreAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPreAnestesi.setText("Penilaian Pre Anestesi");
        MnPenilaianPreAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPreAnestesi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPreAnestesi.setName("MnPenilaianPreAnestesi"); // NOI18N
        MnPenilaianPreAnestesi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPenilaianPreAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianPreAnestesiActionPerformed(evt);
            }
        });

        MnSkorAldrettePascaAnestesi.setBackground(new java.awt.Color(255, 255, 254));
        MnSkorAldrettePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkorAldrettePascaAnestesi.setForeground(new java.awt.Color(50, 50, 50));
        MnSkorAldrettePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkorAldrettePascaAnestesi.setText("Skor Aldrette Pasca Anestesi");
        MnSkorAldrettePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkorAldrettePascaAnestesi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkorAldrettePascaAnestesi.setName("MnSkorAldrettePascaAnestesi"); // NOI18N
        MnSkorAldrettePascaAnestesi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSkorAldrettePascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkorAldrettePascaAnestesiActionPerformed(evt);
            }
        });

        MnSkorStewardPascaAnestesi.setBackground(new java.awt.Color(255, 255, 254));
        MnSkorStewardPascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkorStewardPascaAnestesi.setForeground(new java.awt.Color(50, 50, 50));
        MnSkorStewardPascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkorStewardPascaAnestesi.setText("Skor Steward Pasca Anestesi");
        MnSkorStewardPascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkorStewardPascaAnestesi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkorStewardPascaAnestesi.setName("MnSkorStewardPascaAnestesi"); // NOI18N
        MnSkorStewardPascaAnestesi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSkorStewardPascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkorStewardPascaAnestesiActionPerformed(evt);
            }
        });

        MnSkorBromagePascaAnestesi.setBackground(new java.awt.Color(255, 255, 254));
        MnSkorBromagePascaAnestesi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkorBromagePascaAnestesi.setForeground(new java.awt.Color(50, 50, 50));
        MnSkorBromagePascaAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkorBromagePascaAnestesi.setText("Skor Bromage Pasca Anestesi");
        MnSkorBromagePascaAnestesi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkorBromagePascaAnestesi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkorBromagePascaAnestesi.setName("MnSkorBromagePascaAnestesi"); // NOI18N
        MnSkorBromagePascaAnestesi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSkorBromagePascaAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkorBromagePascaAnestesiActionPerformed(evt);
            }
        });

        MnRMHCU.setBackground(new java.awt.Color(255, 255, 254));
        MnRMHCU.setForeground(new java.awt.Color(50, 50, 50));
        MnRMHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMHCU.setText("RM HCU & ICU");
        MnRMHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMHCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMHCU.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMHCU.setName("MnRMHCU"); // NOI18N
        MnRMHCU.setPreferredSize(new java.awt.Dimension(220, 26));

        MnCheckListKriteriaMasukHCU.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckListKriteriaMasukHCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckListKriteriaMasukHCU.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckListKriteriaMasukHCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckListKriteriaMasukHCU.setText("Check List Kriteria Masuk HCU");
        MnCheckListKriteriaMasukHCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckListKriteriaMasukHCU.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckListKriteriaMasukHCU.setName("MnCheckListKriteriaMasukHCU"); // NOI18N
        MnCheckListKriteriaMasukHCU.setPreferredSize(new java.awt.Dimension(200, 26));
        MnCheckListKriteriaMasukHCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckListKriteriaMasukHCUActionPerformed(evt);
            }
        });
        MnRMHCU.add(MnCheckListKriteriaMasukHCU);

        MnCheckListKriteriaMasukICU.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckListKriteriaMasukICU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckListKriteriaMasukICU.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckListKriteriaMasukICU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckListKriteriaMasukICU.setText("Check List Kriteria Masuk ICU");
        MnCheckListKriteriaMasukICU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckListKriteriaMasukICU.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckListKriteriaMasukICU.setName("MnCheckListKriteriaMasukICU"); // NOI18N
        MnCheckListKriteriaMasukICU.setPreferredSize(new java.awt.Dimension(200, 26));
        MnCheckListKriteriaMasukICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckListKriteriaMasukICUActionPerformed(evt);
            }
        });
        MnRMHCU.add(MnCheckListKriteriaMasukICU);

        MnRMRisikoJatuh.setBackground(new java.awt.Color(255, 255, 254));
        MnRMRisikoJatuh.setForeground(new java.awt.Color(50, 50, 50));
        MnRMRisikoJatuh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMRisikoJatuh.setText("Risiko Jatuh & Fungsional");
        MnRMRisikoJatuh.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMRisikoJatuh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMRisikoJatuh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMRisikoJatuh.setName("MnRMRisikoJatuh"); // NOI18N
        MnRMRisikoJatuh.setPreferredSize(new java.awt.Dimension(250, 26));

        MnPenilaianRisikoJatuhDewasa.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianRisikoJatuhDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianRisikoJatuhDewasa.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianRisikoJatuhDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianRisikoJatuhDewasa.setText("Penilaian Lanjutan Risiko Jatuh Dewasa");
        MnPenilaianRisikoJatuhDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianRisikoJatuhDewasa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianRisikoJatuhDewasa.setName("MnPenilaianRisikoJatuhDewasa"); // NOI18N
        MnPenilaianRisikoJatuhDewasa.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianRisikoJatuhDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianRisikoJatuhDewasaActionPerformed(evt);
            }
        });
        MnRMRisikoJatuh.add(MnPenilaianRisikoJatuhDewasa);

        MnPenilaianRisikoJatuhAnak.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianRisikoJatuhAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianRisikoJatuhAnak.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianRisikoJatuhAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianRisikoJatuhAnak.setText("Penilaian Lanjutan Risiko Jatuh Anak");
        MnPenilaianRisikoJatuhAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianRisikoJatuhAnak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianRisikoJatuhAnak.setName("MnPenilaianRisikoJatuhAnak"); // NOI18N
        MnPenilaianRisikoJatuhAnak.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianRisikoJatuhAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianRisikoJatuhAnakActionPerformed(evt);
            }
        });
        MnRMRisikoJatuh.add(MnPenilaianRisikoJatuhAnak);

        MnPenilaianRisikoJatuhLansia.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianRisikoJatuhLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianRisikoJatuhLansia.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianRisikoJatuhLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianRisikoJatuhLansia.setText("Penilaian Lanjutan Risiko Jatuh Lansia");
        MnPenilaianRisikoJatuhLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianRisikoJatuhLansia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianRisikoJatuhLansia.setName("MnPenilaianRisikoJatuhLansia"); // NOI18N
        MnPenilaianRisikoJatuhLansia.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianRisikoJatuhLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianRisikoJatuhLansiaActionPerformed(evt);
            }
        });
        MnRMRisikoJatuh.add(MnPenilaianRisikoJatuhLansia);

        MnPenilaianRisikoJatuhNeonatus.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianRisikoJatuhNeonatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianRisikoJatuhNeonatus.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianRisikoJatuhNeonatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianRisikoJatuhNeonatus.setText("Penilaian Lanjutan Risiko Jatuh Neonatus");
        MnPenilaianRisikoJatuhNeonatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianRisikoJatuhNeonatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianRisikoJatuhNeonatus.setName("MnPenilaianRisikoJatuhNeonatus"); // NOI18N
        MnPenilaianRisikoJatuhNeonatus.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianRisikoJatuhNeonatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianRisikoJatuhNeonatusActionPerformed(evt);
            }
        });
        MnRMRisikoJatuh.add(MnPenilaianRisikoJatuhNeonatus);

        MnPenilaianRisikoJatuhGeriatri.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianRisikoJatuhGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianRisikoJatuhGeriatri.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianRisikoJatuhGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianRisikoJatuhGeriatri.setText("Penilaian Lanjutan Risiko Jatuh Geriatri");
        MnPenilaianRisikoJatuhGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianRisikoJatuhGeriatri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianRisikoJatuhGeriatri.setName("MnPenilaianRisikoJatuhGeriatri"); // NOI18N
        MnPenilaianRisikoJatuhGeriatri.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianRisikoJatuhGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianRisikoJatuhGeriatriActionPerformed(evt);
            }
        });
        MnRMRisikoJatuh.add(MnPenilaianRisikoJatuhGeriatri);

        MnPenilaianRisikoJatuhPsikiatri.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianRisikoJatuhPsikiatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianRisikoJatuhPsikiatri.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianRisikoJatuhPsikiatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianRisikoJatuhPsikiatri.setText("Penilaian Lanjutan Risiko Jatuh Psikiatri");
        MnPenilaianRisikoJatuhPsikiatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianRisikoJatuhPsikiatri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianRisikoJatuhPsikiatri.setName("MnPenilaianRisikoJatuhPsikiatri"); // NOI18N
        MnPenilaianRisikoJatuhPsikiatri.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianRisikoJatuhPsikiatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianRisikoJatuhPsikiatriActionPerformed(evt);
            }
        });
        MnRMRisikoJatuh.add(MnPenilaianRisikoJatuhPsikiatri);

        MnPenilaianLanjutanSkriningFungsional.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianLanjutanSkriningFungsional.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianLanjutanSkriningFungsional.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianLanjutanSkriningFungsional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianLanjutanSkriningFungsional.setText("Penilaian Lanjutan Skrining Fungsional");
        MnPenilaianLanjutanSkriningFungsional.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianLanjutanSkriningFungsional.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianLanjutanSkriningFungsional.setName("MnPenilaianLanjutanSkriningFungsional"); // NOI18N
        MnPenilaianLanjutanSkriningFungsional.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianLanjutanSkriningFungsional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianLanjutanSkriningFungsionalActionPerformed(evt);
            }
        });
        MnRMRisikoJatuh.add(MnPenilaianLanjutanSkriningFungsional);

        MnPenilaianLain.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianLain.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianLain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianLain.setText("Penilaian Lain-lain");
        MnPenilaianLain.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianLain.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianLain.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianLain.setName("MnPenilaianLain"); // NOI18N
        MnPenilaianLain.setPreferredSize(new java.awt.Dimension(250, 26));

        MnPenilaianTambahanGeriatri.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianTambahanGeriatri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianTambahanGeriatri.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianTambahanGeriatri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianTambahanGeriatri.setText("Penilaian Tambahan Pasien Geriatri");
        MnPenilaianTambahanGeriatri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianTambahanGeriatri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianTambahanGeriatri.setName("MnPenilaianTambahanGeriatri"); // NOI18N
        MnPenilaianTambahanGeriatri.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianTambahanGeriatri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianTambahanGeriatriActionPerformed(evt);
            }
        });

        MnPenilaianTambahanBunuhDiri.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianTambahanBunuhDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianTambahanBunuhDiri.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianTambahanBunuhDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianTambahanBunuhDiri.setText("Penilaian Tambahan Bunuh Diri");
        MnPenilaianTambahanBunuhDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianTambahanBunuhDiri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianTambahanBunuhDiri.setName("MnPenilaianTambahanBunuhDiri"); // NOI18N
        MnPenilaianTambahanBunuhDiri.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianTambahanBunuhDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianTambahanBunuhDiriActionPerformed(evt);
            }
        });

        MnPenilaianTambahanPerilakuKekerasan.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianTambahanPerilakuKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianTambahanPerilakuKekerasan.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianTambahanPerilakuKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianTambahanPerilakuKekerasan.setText("Penilaian Tambahan Perilaku Kekerasan");
        MnPenilaianTambahanPerilakuKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianTambahanPerilakuKekerasan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianTambahanPerilakuKekerasan.setName("MnPenilaianTambahanPerilakuKekerasan"); // NOI18N
        MnPenilaianTambahanPerilakuKekerasan.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianTambahanPerilakuKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianTambahanPerilakuKekerasanActionPerformed(evt);
            }
        });

        MnPenilaianTambahanMelarikanDiri.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianTambahanMelarikanDiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianTambahanMelarikanDiri.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianTambahanMelarikanDiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianTambahanMelarikanDiri.setText("Penilaian Tambahan Melarikan Diri");
        MnPenilaianTambahanMelarikanDiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianTambahanMelarikanDiri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianTambahanMelarikanDiri.setName("MnPenilaianTambahanMelarikanDiri"); // NOI18N
        MnPenilaianTambahanMelarikanDiri.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianTambahanMelarikanDiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianTambahanMelarikanDiriActionPerformed(evt);
            }
        });

        MnPenilaianPasienTerminal.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPasienTerminal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianPasienTerminal.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPasienTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPasienTerminal.setText("Penilaian Pasien Terminal");
        MnPenilaianPasienTerminal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPasienTerminal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPasienTerminal.setName("MnPenilaianPasienTerminal"); // NOI18N
        MnPenilaianPasienTerminal.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianPasienTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianPasienTerminalActionPerformed(evt);
            }
        });

        MnPenilaianKorbanKekerasan.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianKorbanKekerasan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianKorbanKekerasan.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianKorbanKekerasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianKorbanKekerasan.setText("Penilaian Korban Kekerasan");
        MnPenilaianKorbanKekerasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianKorbanKekerasan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianKorbanKekerasan.setName("MnPenilaianKorbanKekerasan"); // NOI18N
        MnPenilaianKorbanKekerasan.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianKorbanKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianKorbanKekerasanActionPerformed(evt);
            }
        });

        MnPenilaianPasienPenyakitMenular.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPasienPenyakitMenular.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianPasienPenyakitMenular.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPasienPenyakitMenular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPasienPenyakitMenular.setText("Penilaian Pasien Penyakit Menular");
        MnPenilaianPasienPenyakitMenular.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPasienPenyakitMenular.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPasienPenyakitMenular.setName("MnPenilaianPasienPenyakitMenular"); // NOI18N
        MnPenilaianPasienPenyakitMenular.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianPasienPenyakitMenular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianPasienPenyakitMenularActionPerformed(evt);
            }
        });

        MnPenilaianFisioterapi.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianFisioterapi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianFisioterapi.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianFisioterapi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianFisioterapi.setText("Penilaian Awal Fisioterapi");
        MnPenilaianFisioterapi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianFisioterapi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianFisioterapi.setName("MnPenilaianFisioterapi"); // NOI18N
        MnPenilaianFisioterapi.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianFisioterapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianFisioterapiActionPerformed(evt);
            }
        });

        MnPenilaianPsikolog.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPsikolog.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianPsikolog.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPsikolog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPsikolog.setText("Penilaian Psikolog");
        MnPenilaianPsikolog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPsikolog.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPsikolog.setName("MnPenilaianPsikolog"); // NOI18N
        MnPenilaianPsikolog.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianPsikolog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianPsikologActionPerformed(evt);
            }
        });

        MnHemodialisa.setBackground(new java.awt.Color(255, 255, 254));
        MnHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHemodialisa.setForeground(new java.awt.Color(50, 50, 50));
        MnHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHemodialisa.setText("Hemodialisa");
        MnHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHemodialisa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHemodialisa.setName("MnHemodialisa"); // NOI18N
        MnHemodialisa.setPreferredSize(new java.awt.Dimension(250, 26));
        MnHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHemodialisaActionPerformed(evt);
            }
        });

        MnRMFarmasi.setBackground(new java.awt.Color(255, 255, 254));
        MnRMFarmasi.setForeground(new java.awt.Color(50, 50, 50));
        MnRMFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMFarmasi.setText("RM Farmasi");
        MnRMFarmasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMFarmasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMFarmasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMFarmasi.setName("MnRMFarmasi"); // NOI18N
        MnRMFarmasi.setPreferredSize(new java.awt.Dimension(250, 26));

        MnKonselingFarmasi.setBackground(new java.awt.Color(255, 255, 254));
        MnKonselingFarmasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKonselingFarmasi.setForeground(new java.awt.Color(50, 50, 50));
        MnKonselingFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKonselingFarmasi.setText("Konseling Farmasi");
        MnKonselingFarmasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKonselingFarmasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKonselingFarmasi.setName("MnKonselingFarmasi"); // NOI18N
        MnKonselingFarmasi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnKonselingFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKonselingFarmasiActionPerformed(evt);
            }
        });
        MnRMFarmasi.add(MnKonselingFarmasi);

        MnRekonsiliasiObat.setBackground(new java.awt.Color(255, 255, 254));
        MnRekonsiliasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekonsiliasiObat.setForeground(new java.awt.Color(50, 50, 50));
        MnRekonsiliasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekonsiliasiObat.setText("Rekonsiliasi Obat");
        MnRekonsiliasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekonsiliasiObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekonsiliasiObat.setName("MnRekonsiliasiObat"); // NOI18N
        MnRekonsiliasiObat.setPreferredSize(new java.awt.Dimension(170, 26));
        MnRekonsiliasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekonsiliasiObatActionPerformed(evt);
            }
        });
        MnRMFarmasi.add(MnRekonsiliasiObat);

        MnRMCatatanMonitoring.setBackground(new java.awt.Color(255, 255, 254));
        MnRMCatatanMonitoring.setForeground(new java.awt.Color(50, 50, 50));
        MnRMCatatanMonitoring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRMCatatanMonitoring.setText("Catatan & Monitoring");
        MnRMCatatanMonitoring.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRMCatatanMonitoring.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMCatatanMonitoring.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMCatatanMonitoring.setName("MnRMCatatanMonitoring"); // NOI18N
        MnRMCatatanMonitoring.setPreferredSize(new java.awt.Dimension(250, 26));

        MnCatatanCekGDS.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanCekGDS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanCekGDS.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanCekGDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanCekGDS.setText("Catatan Cek GDS");
        MnCatatanCekGDS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanCekGDS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanCekGDS.setName("MnCatatanCekGDS"); // NOI18N
        MnCatatanCekGDS.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCatatanCekGDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanCekGDSActionPerformed(evt);
            }
        });

        MnMonitoringReaksiTranfusi.setBackground(new java.awt.Color(255, 255, 254));
        MnMonitoringReaksiTranfusi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMonitoringReaksiTranfusi.setForeground(new java.awt.Color(50, 50, 50));
        MnMonitoringReaksiTranfusi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnMonitoringReaksiTranfusi.setText("Monitoring Reaksi Tranfusi");
        MnMonitoringReaksiTranfusi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnMonitoringReaksiTranfusi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnMonitoringReaksiTranfusi.setName("MnMonitoringReaksiTranfusi"); // NOI18N
        MnMonitoringReaksiTranfusi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnMonitoringReaksiTranfusi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnMonitoringReaksiTranfusiActionPerformed(evt);
            }
        });

        MnPenilaianUlangNyeri.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianUlangNyeri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianUlangNyeri.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianUlangNyeri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianUlangNyeri.setText("Penilaian Ulang Nyeri");
        MnPenilaianUlangNyeri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianUlangNyeri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianUlangNyeri.setName("MnPenilaianUlangNyeri"); // NOI18N
        MnPenilaianUlangNyeri.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPenilaianUlangNyeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianUlangNyeriActionPerformed(evt);
            }
        });

        MnCatatanKeperawatan.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanKeperawatan.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanKeperawatan.setText("Catatan Keperawatan");
        MnCatatanKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanKeperawatan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanKeperawatan.setName("MnCatatanKeperawatan"); // NOI18N
        MnCatatanKeperawatan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCatatanKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanKeperawatanActionPerformed(evt);
            }
        });

        MnCatatanPersalinan.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanPersalinan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanPersalinan.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanPersalinan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanPersalinan.setText("Catatan Persalinan");
        MnCatatanPersalinan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanPersalinan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanPersalinan.setName("MnCatatanPersalinan"); // NOI18N
        MnCatatanPersalinan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCatatanPersalinan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanPersalinanActionPerformed(evt);
            }
        });

        MnGizi.setBackground(new java.awt.Color(255, 255, 254));
        MnGizi.setForeground(new java.awt.Color(50, 50, 50));
        MnGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGizi.setText("RM Gizi");
        MnGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGizi.setName("MnGizi"); // NOI18N
        MnGizi.setPreferredSize(new java.awt.Dimension(250, 26));

        ppSkriningNutrisiDewasa.setBackground(new java.awt.Color(255, 255, 254));
        ppSkriningNutrisiDewasa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSkriningNutrisiDewasa.setForeground(new java.awt.Color(50, 50, 50));
        ppSkriningNutrisiDewasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSkriningNutrisiDewasa.setText("Skrining Nutrisi Pasien Dewasa");
        ppSkriningNutrisiDewasa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSkriningNutrisiDewasa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSkriningNutrisiDewasa.setName("ppSkriningNutrisiDewasa"); // NOI18N
        ppSkriningNutrisiDewasa.setPreferredSize(new java.awt.Dimension(210, 26));
        ppSkriningNutrisiDewasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSkriningNutrisiDewasaBtnPrintActionPerformed(evt);
            }
        });
        MnGizi.add(ppSkriningNutrisiDewasa);

        ppSkriningNutrisiLansia.setBackground(new java.awt.Color(255, 255, 254));
        ppSkriningNutrisiLansia.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSkriningNutrisiLansia.setForeground(new java.awt.Color(50, 50, 50));
        ppSkriningNutrisiLansia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSkriningNutrisiLansia.setText("Skrining Nutrisi Pasien Lansia");
        ppSkriningNutrisiLansia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSkriningNutrisiLansia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSkriningNutrisiLansia.setName("ppSkriningNutrisiLansia"); // NOI18N
        ppSkriningNutrisiLansia.setPreferredSize(new java.awt.Dimension(210, 26));
        ppSkriningNutrisiLansia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSkriningNutrisiLansiaBtnPrintActionPerformed(evt);
            }
        });
        MnGizi.add(ppSkriningNutrisiLansia);

        ppSkriningNutrisiAnak.setBackground(new java.awt.Color(255, 255, 254));
        ppSkriningNutrisiAnak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSkriningNutrisiAnak.setForeground(new java.awt.Color(50, 50, 50));
        ppSkriningNutrisiAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSkriningNutrisiAnak.setText("Skrining Nutrisi Pasien Anak");
        ppSkriningNutrisiAnak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSkriningNutrisiAnak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSkriningNutrisiAnak.setName("ppSkriningNutrisiAnak"); // NOI18N
        ppSkriningNutrisiAnak.setPreferredSize(new java.awt.Dimension(210, 26));
        ppSkriningNutrisiAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSkriningNutrisiAnakBtnPrintActionPerformed(evt);
            }
        });
        MnGizi.add(ppSkriningNutrisiAnak);

        ppSkriningGizi.setBackground(new java.awt.Color(255, 255, 254));
        ppSkriningGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSkriningGizi.setForeground(new java.awt.Color(50, 50, 50));
        ppSkriningGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSkriningGizi.setText("Skrining Gizi Lanjut");
        ppSkriningGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSkriningGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSkriningGizi.setName("ppSkriningGizi"); // NOI18N
        ppSkriningGizi.setPreferredSize(new java.awt.Dimension(210, 26));
        ppSkriningGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSkriningGiziBtnPrintActionPerformed(evt);
            }
        });
        MnGizi.add(ppSkriningGizi);

        ppAsuhanGizi.setBackground(new java.awt.Color(255, 255, 254));
        ppAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppAsuhanGizi.setForeground(new java.awt.Color(50, 50, 50));
        ppAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppAsuhanGizi.setText("Asuhan Gizi");
        ppAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppAsuhanGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppAsuhanGizi.setName("ppAsuhanGizi"); // NOI18N
        ppAsuhanGizi.setPreferredSize(new java.awt.Dimension(210, 26));
        ppAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppAsuhanGiziBtnPrintActionPerformed(evt);
            }
        });
        MnGizi.add(ppAsuhanGizi);

        ppMonitoringAsuhanGizi.setBackground(new java.awt.Color(255, 255, 254));
        ppMonitoringAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppMonitoringAsuhanGizi.setForeground(new java.awt.Color(50, 50, 50));
        ppMonitoringAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppMonitoringAsuhanGizi.setText("Monitoring Gizi");
        ppMonitoringAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppMonitoringAsuhanGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppMonitoringAsuhanGizi.setName("ppMonitoringAsuhanGizi"); // NOI18N
        ppMonitoringAsuhanGizi.setPreferredSize(new java.awt.Dimension(210, 26));
        ppMonitoringAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppMonitoringAsuhanGiziBtnPrintActionPerformed(evt);
            }
        });
        MnGizi.add(ppMonitoringAsuhanGizi);

        ppCatatanADIMEGizi.setBackground(new java.awt.Color(255, 255, 254));
        ppCatatanADIMEGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanADIMEGizi.setForeground(new java.awt.Color(50, 50, 50));
        ppCatatanADIMEGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCatatanADIMEGizi.setText("Catatan ADIME Gizi");
        ppCatatanADIMEGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanADIMEGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanADIMEGizi.setName("ppCatatanADIMEGizi"); // NOI18N
        ppCatatanADIMEGizi.setPreferredSize(new java.awt.Dimension(210, 26));
        ppCatatanADIMEGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanADIMEGiziBtnPrintActionPerformed(evt);
            }
        });
        MnGizi.add(ppCatatanADIMEGizi);

        MnTransferAntarRuang.setBackground(new java.awt.Color(255, 255, 254));
        MnTransferAntarRuang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTransferAntarRuang.setForeground(new java.awt.Color(50, 50, 50));
        MnTransferAntarRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTransferAntarRuang.setText("Transfer Antar Ruang");
        MnTransferAntarRuang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTransferAntarRuang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTransferAntarRuang.setName("MnTransferAntarRuang"); // NOI18N
        MnTransferAntarRuang.setPreferredSize(new java.awt.Dimension(250, 26));
        MnTransferAntarRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTransferAntarRuangActionPerformed(evt);
            }
        });
        
        MnEdukasiPasienKeluarga.setBackground(new java.awt.Color(255, 255, 254));
        MnEdukasiPasienKeluarga.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnEdukasiPasienKeluarga.setForeground(new java.awt.Color(50, 50, 50));
        MnEdukasiPasienKeluarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnEdukasiPasienKeluarga.setText("Edukasi Pasien & Keluarga");
        MnEdukasiPasienKeluarga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnEdukasiPasienKeluarga.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnEdukasiPasienKeluarga.setName("MnEdukasiPasienKeluarga"); // NOI18N
        MnEdukasiPasienKeluarga.setPreferredSize(new java.awt.Dimension(250, 26));
        MnEdukasiPasienKeluarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnEdukasiPasienKeluargaActionPerformed(evt);
            }
        });
        
        ppResume.setBackground(new java.awt.Color(255, 255, 254));
        ppResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppResume.setForeground(new java.awt.Color(50, 50, 50));
        ppResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppResume.setText("Data Resume Pasien");
        ppResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppResume.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppResume.setName("ppResume"); // NOI18N
        ppResume.setPreferredSize(new java.awt.Dimension(250, 26));
        ppResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppResumeBtnPrintActionPerformed(evt);
            }
        });
        
        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(50, 50, 50));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(250, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        
        ppDeteksiDIniCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppDeteksiDIniCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDeteksiDIniCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppDeteksiDIniCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDeteksiDIniCorona.setText("Deteksi Dini Corona");
        ppDeteksiDIniCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDeteksiDIniCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDeteksiDIniCorona.setName("ppDeteksiDIniCorona"); // NOI18N
        ppDeteksiDIniCorona.setPreferredSize(new java.awt.Dimension(250, 26));
        ppDeteksiDIniCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDeteksiDIniCoronaBtnPrintActionPerformed(evt);
            }
        });
        
        jPopupMenu1.add(MnDataRM);

        MnPermintaan.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaan.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaan.setText("Permintaan");
        MnPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan.setName("MnPermintaan"); // NOI18N
        MnPermintaan.setPreferredSize(new java.awt.Dimension(250, 26));

        MnJadwalOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadwalOperasi.setText("Jadwal Operasi");
        MnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi.setName("MnJadwalOperasi"); // NOI18N
        MnJadwalOperasi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadwalOperasiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnJadwalOperasi);

        MnPermintaanLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanLab.setText("Pemeriksaan Lab");
        MnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab.setName("MnPermintaanLab"); // NOI18N
        MnPermintaanLab.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLabActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanLab);

        MnPermintaanRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRadiologi.setText("Pemeriksaan Radiologi");
        MnPermintaanRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRadiologi.setName("MnPermintaanRadiologi"); // NOI18N
        MnPermintaanRadiologi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRadiologi);

        MnPermintaanRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRanap.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRanap.setText("Rawat Inap");
        MnPermintaanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRanap.setName("MnPermintaanRanap"); // NOI18N
        MnPermintaanRanap.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRanapActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRanap);

        MnPermintaanInformasiObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanInformasiObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanInformasiObat.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanInformasiObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanInformasiObat.setText("Informasi Obat");
        MnPermintaanInformasiObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanInformasiObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanInformasiObat.setName("MnPermintaanInformasiObat"); // NOI18N
        MnPermintaanInformasiObat.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanInformasiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanInformasiObatActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanInformasiObat);

        jPopupMenu1.add(MnPermintaan);

        MnKamarInap.setBackground(new java.awt.Color(255, 255, 254));
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap.setForeground(new java.awt.Color(50, 50, 50));
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(250, 26));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnTindakan.setBackground(new java.awt.Color(255, 255, 254));
        MnTindakan.setForeground(new java.awt.Color(50, 50, 50));
        MnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan.setText("Tindakan & Pemeriksaan");
        MnTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan.setName("MnTindakan"); // NOI18N
        MnTindakan.setPreferredSize(new java.awt.Dimension(250, 26));

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(50, 50, 50));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(240, 26));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        MnTindakan.add(MnRawatJalan);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Laborat");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaLab);

        MnPeriksaLabPA.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLabPA.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLabPA.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLabPA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLabPA.setText("Periksa Laborat PA");
        MnPeriksaLabPA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLabPA.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLabPA.setName("MnPeriksaLabPA"); // NOI18N
        MnPeriksaLabPA.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLabPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabPAActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaLabPA);

        MnPeriksaLabMB.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLabMB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLabMB.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaLabMB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLabMB.setText("Periksa Laborat MB");
        MnPeriksaLabMB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLabMB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLabMB.setName("MnPeriksaLabMB"); // NOI18N
        MnPeriksaLabMB.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLabMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabMBActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaLabMB);

        MnPeriksaRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        MnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi.setText("Periksa Radiologi");
        MnPeriksaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi.setName("MnPeriksaRadiologi"); // NOI18N
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaRadiologi);

        MnOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(240, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnOperasi);

        jPopupMenu1.add(MnTindakan);

        MnObat.setBackground(new java.awt.Color(255, 255, 254));
        MnObat.setForeground(new java.awt.Color(50, 50, 50));
        MnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat.setText("Obat");
        MnObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat.setName("MnObat"); // NOI18N
        MnObat.setPreferredSize(new java.awt.Dimension(250, 26));

        MnResepDOkter.setBackground(new java.awt.Color(255, 255, 254));
        MnResepDOkter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter.setForeground(new java.awt.Color(50, 50, 50));
        MnResepDOkter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepDOkter.setText("Input Resep Dokter");
        MnResepDOkter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter.setName("MnResepDOkter"); // NOI18N
        MnResepDOkter.setPreferredSize(new java.awt.Dimension(160, 23));
        MnResepDOkter.setRequestFocusEnabled(false);
        MnResepDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkterActionPerformed(evt);
            }
        });
        MnObat.add(MnResepDOkter);

        MnNoResep.setBackground(new java.awt.Color(255, 255, 254));
        MnNoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep.setForeground(new java.awt.Color(50, 50, 50));
        MnNoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep.setText("Input No.Resep");
        MnNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep.setName("MnNoResep"); // NOI18N
        MnNoResep.setPreferredSize(new java.awt.Dimension(160, 23));
        MnNoResep.setRequestFocusEnabled(false);
        MnNoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResepActionPerformed(evt);
            }
        });
        MnObat.add(MnNoResep);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(50, 50, 50));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat/BHP");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(160, 23));
        MnPemberianObat.setRequestFocusEnabled(false);
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        MnObat.add(MnPemberianObat);

        MnCopyResep.setBackground(new java.awt.Color(255, 255, 254));
        MnCopyResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCopyResep.setForeground(new java.awt.Color(50, 50, 50));
        MnCopyResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCopyResep.setText("Copy Resep Dokter");
        MnCopyResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCopyResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCopyResep.setName("MnCopyResep"); // NOI18N
        MnCopyResep.setPreferredSize(new java.awt.Dimension(160, 26));
        MnCopyResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCopyResepActionPerformed(evt);
            }
        });
        MnObat.add(MnCopyResep);

        jPopupMenu1.add(MnObat);

        MnPilihBilling.setBackground(new java.awt.Color(255, 255, 254));
        MnPilihBilling.setForeground(new java.awt.Color(50, 50, 50));
        MnPilihBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPilihBilling.setText("Billing/Pembayaran Pasien");
        MnPilihBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPilihBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPilihBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPilihBilling.setName("MnPilihBilling"); // NOI18N
        MnPilihBilling.setPreferredSize(new java.awt.Dimension(250, 26));

        MnBillingParsial.setBackground(new java.awt.Color(255, 255, 254));
        MnBillingParsial.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBillingParsial.setForeground(new java.awt.Color(50, 50, 50));
        MnBillingParsial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBillingParsial.setText("Billing Parsial");
        MnBillingParsial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBillingParsial.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBillingParsial.setName("MnBillingParsial"); // NOI18N
        MnBillingParsial.setPreferredSize(new java.awt.Dimension(130, 26));
        MnBillingParsial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingParsialActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBillingParsial);

        MnBilling.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(50, 50, 50));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing Total");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(130, 26));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBilling);

        jPopupMenu1.add(MnPilihBilling);

        jSeparator12.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator12.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator12.setName("jSeparator12"); // NOI18N
        jSeparator12.setPreferredSize(new java.awt.Dimension(250, 1));
        jPopupMenu1.add(jSeparator12);

        jMenu1.setBackground(new java.awt.Color(255, 255, 254));
        jMenu1.setForeground(new java.awt.Color(50, 50, 50));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu1.setText("Laporan");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(250, 26));
        jMenu1.setRequestFocusEnabled(false);

        MnLaporanRekapKunjunganPoli.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapKunjunganPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganPoli.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanRekapKunjunganPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganPoli.setText("Laporan Rekap Kunjungan Per Poli");
        MnLaporanRekapKunjunganPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapKunjunganPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapKunjunganPoli.setName("MnLaporanRekapKunjunganPoli"); // NOI18N
        MnLaporanRekapKunjunganPoli.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapKunjunganPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganPoliActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganPoli);

        MnLaporanRekapKunjunganDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapKunjunganDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganDokter.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanRekapKunjunganDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganDokter.setText("Laporan Rekap Kunjungan Per Dokter");
        MnLaporanRekapKunjunganDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapKunjunganDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapKunjunganDokter.setName("MnLaporanRekapKunjunganDokter"); // NOI18N
        MnLaporanRekapKunjunganDokter.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapKunjunganDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganDokterActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganDokter);

        MnLaporanRekapJenisBayar.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapJenisBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapJenisBayar.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanRekapJenisBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapJenisBayar.setText("Laporan RL 315 Cara bayar");
        MnLaporanRekapJenisBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapJenisBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapJenisBayar.setName("MnLaporanRekapJenisBayar"); // NOI18N
        MnLaporanRekapJenisBayar.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapJenisBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapJenisBayarActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapJenisBayar);

        MnLaporanRekapRawatDarurat.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapRawatDarurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapRawatDarurat.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanRekapRawatDarurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapRawatDarurat.setText("Laporan RL 32 Rawat Darurat");
        MnLaporanRekapRawatDarurat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapRawatDarurat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapRawatDarurat.setName("MnLaporanRekapRawatDarurat"); // NOI18N
        MnLaporanRekapRawatDarurat.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapRawatDarurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapRawatDaruratActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapRawatDarurat);

        MnLaporanRekapKunjunganBulanan.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapKunjunganBulanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganBulanan.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanRekapKunjunganBulanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganBulanan.setText("Laporan RL 51");
        MnLaporanRekapKunjunganBulanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapKunjunganBulanan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapKunjunganBulanan.setName("MnLaporanRekapKunjunganBulanan"); // NOI18N
        MnLaporanRekapKunjunganBulanan.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapKunjunganBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganBulananActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganBulanan);

        MnLaporanRekapKunjunganBulananPoli.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapKunjunganBulananPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanRekapKunjunganBulananPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setText("Laporan RL 52");
        MnLaporanRekapKunjunganBulananPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapKunjunganBulananPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapKunjunganBulananPoli.setName("MnLaporanRekapKunjunganBulananPoli"); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapKunjunganBulananPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganBulananPoliActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganBulananPoli);

        MnLaporanRekapPenyakitRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapPenyakitRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapPenyakitRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanRekapPenyakitRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapPenyakitRalan.setText("Laporan RL 53 Penyakit Ralan");
        MnLaporanRekapPenyakitRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapPenyakitRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapPenyakitRalan.setName("MnLaporanRekapPenyakitRalan"); // NOI18N
        MnLaporanRekapPenyakitRalan.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapPenyakitRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapPenyakitRalanActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapPenyakitRalan);

        jPopupMenu1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(255, 255, 254));
        jMenu2.setForeground(new java.awt.Color(50, 50, 50));
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu2.setText("Grafik Analisa");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu2.setName("jMenu2"); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(250, 26));
        jMenu2.setRequestFocusEnabled(false);

        ppGrafikPerpoli.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPerpoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerpoli.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPerpoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerpoli.setText("Grafik Kunjungan Per Poli");
        ppGrafikPerpoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerpoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerpoli.setName("ppGrafikPerpoli"); // NOI18N
        ppGrafikPerpoli.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerpoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerpoliActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerpoli);

        ppGrafikPerdokter.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPerdokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerdokter.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPerdokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerdokter.setText("Grafik Kunjungan Per Dokter");
        ppGrafikPerdokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerdokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerdokter.setName("ppGrafikPerdokter"); // NOI18N
        ppGrafikPerdokter.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerdokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerdokterActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerdokter);

        ppGrafikPerJK.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPerJK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerJK.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPerJK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerJK.setText("Grafik Kunjungan Per Jenis Kelamin");
        ppGrafikPerJK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerJK.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerJK.setName("ppGrafikPerJK"); // NOI18N
        ppGrafikPerJK.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerJK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerJKActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerJK);

        ppGrafikPerPekerjaan.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPerPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerPekerjaan.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPerPekerjaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerPekerjaan.setText("Grafik Kunjungan Per Pekerjaan");
        ppGrafikPerPekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerPekerjaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerPekerjaan.setName("ppGrafikPerPekerjaan"); // NOI18N
        ppGrafikPerPekerjaan.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerPekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerPekerjaanActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerPekerjaan);

        ppGrafikPerAgama.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPerAgama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerAgama.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPerAgama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerAgama.setText("Grafik Kunjungan Per Agama");
        ppGrafikPerAgama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerAgama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerAgama.setName("ppGrafikPerAgama"); // NOI18N
        ppGrafikPerAgama.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerAgama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerAgamaActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerAgama);

        ppGrafikPerTahun.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPerTahun.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerTahun.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerTahun.setText("Grafik Kunjungan Per Tahun");
        ppGrafikPerTahun.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerTahun.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerTahun.setName("ppGrafikPerTahun"); // NOI18N
        ppGrafikPerTahun.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerTahunActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerTahun);

        ppGrafikPerBulan.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPerBulan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerBulan.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPerBulan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerBulan.setText("Grafik Kunjungan Per Bulan");
        ppGrafikPerBulan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerBulan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerBulan.setName("ppGrafikPerBulan"); // NOI18N
        ppGrafikPerBulan.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerBulanActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerBulan);

        ppGrafikPerTanggal.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPerTanggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerTanggal.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerTanggal.setText("Grafik Kunjungan Per Tanggal");
        ppGrafikPerTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerTanggal.setName("ppGrafikPerTanggal"); // NOI18N
        ppGrafikPerTanggal.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerTanggalActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerTanggal);

        ppGrafikDemografi.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikDemografi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikDemografi.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikDemografi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikDemografi.setText("Demografi Pendaftar");
        ppGrafikDemografi.setActionCommand("Grafik Demografi Pasien");
        ppGrafikDemografi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikDemografi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikDemografi.setName("ppGrafikDemografi"); // NOI18N
        ppGrafikDemografi.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikDemografi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikDemografiActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikDemografi);

        jPopupMenu1.add(jMenu2);

        MnSuratSurat.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratSurat.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratSurat.setText("Surat-Surat");
        MnSuratSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratSurat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratSurat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratSurat.setName("MnSuratSurat"); // NOI18N
        MnSuratSurat.setPreferredSize(new java.awt.Dimension(250, 26));
        MnSuratSurat.setRequestFocusEnabled(false);

        SuratKontrol.setBackground(new java.awt.Color(255, 255, 254));
        SuratKontrol.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        SuratKontrol.setForeground(new java.awt.Color(50, 50, 50));
        SuratKontrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        SuratKontrol.setText("Surat Kontrol");
        SuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SuratKontrol.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        SuratKontrol.setName("SuratKontrol"); // NOI18N
        SuratKontrol.setPreferredSize(new java.awt.Dimension(250, 26));
        SuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuratKontrolActionPerformed(evt);
            }
        });
        MnSuratSurat.add(SuratKontrol);

        MnSuratButaWarna.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratButaWarna.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratButaWarna.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratButaWarna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratButaWarna.setText("Surat Buta Warna");
        MnSuratButaWarna.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratButaWarna.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratButaWarna.setName("MnSuratButaWarna"); // NOI18N
        MnSuratButaWarna.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSuratButaWarna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratButaWarnaActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnSuratButaWarna);

        MnSuratBebasTato.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratBebasTato.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratBebasTato.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratBebasTato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratBebasTato.setText("Surat Bebas Tato");
        MnSuratBebasTato.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratBebasTato.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratBebasTato.setName("MnSuratBebasTato"); // NOI18N
        MnSuratBebasTato.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSuratBebasTato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratBebasTatoActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnSuratBebasTato);

        MnSuratKewaspadaanKesehatan.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratKewaspadaanKesehatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratKewaspadaanKesehatan.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratKewaspadaanKesehatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratKewaspadaanKesehatan.setText("Surat Kewaspadaan Kesehatan");
        MnSuratKewaspadaanKesehatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratKewaspadaanKesehatan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratKewaspadaanKesehatan.setName("MnSuratKewaspadaanKesehatan"); // NOI18N
        MnSuratKewaspadaanKesehatan.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSuratKewaspadaanKesehatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratKewaspadaanKesehatanActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnSuratKewaspadaanKesehatan);

        MnCetakSuratBebasTBC.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratBebasTBC.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratBebasTBC.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratBebasTBC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratBebasTBC.setText("Surat Keterangan Bebas TBC");
        MnCetakSuratBebasTBC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratBebasTBC.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratBebasTBC.setName("MnCetakSuratBebasTBC"); // NOI18N
        MnCetakSuratBebasTBC.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratBebasTBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratBebasTBCActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnCetakSuratBebasTBC);

        MnCetakSuratSakit.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit.setText("Surat Cuti Sakit");
        MnCetakSuratSakit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit.setName("MnCetakSuratSakit"); // NOI18N
        MnCetakSuratSakit.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakitActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnCetakSuratSakit);

        MnCetakSuratSakit2.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit2.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSakit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit2.setText("Surat Keterangan Rawat Inap");
        MnCetakSuratSakit2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit2.setName("MnCetakSuratSakit2"); // NOI18N
        MnCetakSuratSakit2.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSakit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakit2ActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnCetakSuratSakit2);

        MnCetakSuratSakitPihak2.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakitPihak2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakitPihak2.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratSakitPihak2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakitPihak2.setText("Surat Cuti Sakit Pihak Ke 2");
        MnCetakSuratSakitPihak2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakitPihak2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakitPihak2.setName("MnCetakSuratSakitPihak2"); // NOI18N
        MnCetakSuratSakitPihak2.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratSakitPihak2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakitPihak2ActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnCetakSuratSakitPihak2);

        MnCetakSuratHamil.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratHamil.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratHamil.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratHamil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratHamil.setText("Surat Keterangan Hamil/Tidak Hamil");
        MnCetakSuratHamil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratHamil.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratHamil.setName("MnCetakSuratHamil"); // NOI18N
        MnCetakSuratHamil.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratHamil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratHamilActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnCetakSuratHamil);

        MnCetakSuratCutiHamil.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratCutiHamil.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratCutiHamil.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratCutiHamil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratCutiHamil.setText("Surat Cuti Hamil");
        MnCetakSuratCutiHamil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratCutiHamil.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratCutiHamil.setName("MnCetakSuratCutiHamil"); // NOI18N
        MnCetakSuratCutiHamil.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratCutiHamil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratCutiHamilActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnCetakSuratCutiHamil);

        MnCetakSuratCovid.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratCovid.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratCovid.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakSuratCovid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratCovid.setText("Surat Keterangan Covid");
        MnCetakSuratCovid.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratCovid.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratCovid.setName("MnCetakSuratCovid"); // NOI18N
        MnCetakSuratCovid.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakSuratCovid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratCovidActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnCetakSuratCovid);

        MnCetakBebasNarkoba.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakBebasNarkoba.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakBebasNarkoba.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakBebasNarkoba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakBebasNarkoba.setText("Surat Keterangan Bebas Narkoba");
        MnCetakBebasNarkoba.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakBebasNarkoba.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakBebasNarkoba.setName("MnCetakBebasNarkoba"); // NOI18N
        MnCetakBebasNarkoba.setPreferredSize(new java.awt.Dimension(250, 26));
        MnCetakBebasNarkoba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakBebasNarkobaActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnCetakBebasNarkoba);

        MnPersetujuanUmum.setBackground(new java.awt.Color(255, 255, 254));
        MnPersetujuanUmum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanUmum.setForeground(new java.awt.Color(50, 50, 50));
        MnPersetujuanUmum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPersetujuanUmum.setText("Persetujuan Umum");
        MnPersetujuanUmum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanUmum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanUmum.setName("MnPersetujuanUmum"); // NOI18N
        MnPersetujuanUmum.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPersetujuanUmum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanUmumActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnPersetujuanUmum);

        MnPersetujuanPenolakanTindakan.setBackground(new java.awt.Color(255, 255, 254));
        MnPersetujuanPenolakanTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanPenolakanTindakan.setForeground(new java.awt.Color(50, 50, 50));
        MnPersetujuanPenolakanTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPersetujuanPenolakanTindakan.setText("Persetujuan/Penolakan Tindakan");
        MnPersetujuanPenolakanTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanPenolakanTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanPenolakanTindakan.setName("MnPersetujuanPenolakanTindakan"); // NOI18N
        MnPersetujuanPenolakanTindakan.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPersetujuanPenolakanTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanPenolakanTindakanActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnPersetujuanPenolakanTindakan);

        MnPulangAtasPermintaanSendiri.setBackground(new java.awt.Color(255, 255, 254));
        MnPulangAtasPermintaanSendiri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPulangAtasPermintaanSendiri.setForeground(new java.awt.Color(50, 50, 50));
        MnPulangAtasPermintaanSendiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPulangAtasPermintaanSendiri.setText("Pulang Atas Permintaan Sendiri");
        MnPulangAtasPermintaanSendiri.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPulangAtasPermintaanSendiri.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPulangAtasPermintaanSendiri.setName("MnPulangAtasPermintaanSendiri"); // NOI18N
        MnPulangAtasPermintaanSendiri.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPulangAtasPermintaanSendiri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPulangAtasPermintaanSendiriActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnPulangAtasPermintaanSendiri);

        MnPernyataanPasienUmum.setBackground(new java.awt.Color(255, 255, 254));
        MnPernyataanPasienUmum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPernyataanPasienUmum.setForeground(new java.awt.Color(50, 50, 50));
        MnPernyataanPasienUmum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPernyataanPasienUmum.setText("Pernyataan Pasien Umum");
        MnPernyataanPasienUmum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPernyataanPasienUmum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPernyataanPasienUmum.setName("MnPernyataanPasienUmum"); // NOI18N
        MnPernyataanPasienUmum.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPernyataanPasienUmum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPernyataanPasienUmumActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnPernyataanPasienUmum);

        MnPersetujuanRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        MnPersetujuanRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        MnPersetujuanRawatInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPersetujuanRawatInap.setText("Persetujuan Rawat Inap");
        MnPersetujuanRawatInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanRawatInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanRawatInap.setName("MnPersetujuanRawatInap"); // NOI18N
        MnPersetujuanRawatInap.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPersetujuanRawatInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanRawatInapActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnPersetujuanRawatInap);

        MnPersetujuanPenundaanPelayanan.setBackground(new java.awt.Color(255, 255, 254));
        MnPersetujuanPenundaanPelayanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanPenundaanPelayanan.setForeground(new java.awt.Color(50, 50, 50));
        MnPersetujuanPenundaanPelayanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPersetujuanPenundaanPelayanan.setText("Persetujuan Penundaan Pelayanan");
        MnPersetujuanPenundaanPelayanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanPenundaanPelayanan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanPenundaanPelayanan.setName("MnPersetujuanPenundaanPelayanan"); // NOI18N
        MnPersetujuanPenundaanPelayanan.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPersetujuanPenundaanPelayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanPenundaanPelayananActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnPersetujuanPenundaanPelayanan);

        MnPenolakanAnjuranMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPenolakanAnjuranMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenolakanAnjuranMedis.setForeground(new java.awt.Color(50, 50, 50));
        MnPenolakanAnjuranMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenolakanAnjuranMedis.setText("Penolakan Anjuran Medis");
        MnPenolakanAnjuranMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenolakanAnjuranMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenolakanAnjuranMedis.setName("MnPenolakanAnjuranMedis"); // NOI18N
        MnPenolakanAnjuranMedis.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPenolakanAnjuranMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenolakanAnjuranMedisActionPerformed(evt);
            }
        });
        MnSuratSurat.add(MnPenolakanAnjuranMedis);

        jMenu8.setBackground(new java.awt.Color(255, 255, 254));
        jMenu8.setForeground(new java.awt.Color(50, 50, 50));
        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu8.setText("Administrasi");
        jMenu8.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu8.setName("jMenu8"); // NOI18N
        jMenu8.setPreferredSize(new java.awt.Dimension(250, 26));

        MnCetakRegister.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakRegister.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakRegister.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakRegister.setText("Bukti Register 1");
        MnCetakRegister.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakRegister.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakRegister.setName("MnCetakRegister"); // NOI18N
        MnCetakRegister.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCetakRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakRegisterActionPerformed(evt);
            }
        });
        jMenu8.add(MnCetakRegister);

        MnCetakRegister2.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakRegister2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakRegister2.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakRegister2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakRegister2.setText("Bukti Register 2");
        MnCetakRegister2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakRegister2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakRegister2.setName("MnCetakRegister2"); // NOI18N
        MnCetakRegister2.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakRegister2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakRegister2ActionPerformed(evt);
            }
        });
        jMenu8.add(MnCetakRegister2);

        MnCetakRegister1.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakRegister1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakRegister1.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakRegister1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakRegister1.setText("Bukti Register 3");
        MnCetakRegister1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakRegister1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakRegister1.setName("MnCetakRegister1"); // NOI18N
        MnCetakRegister1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCetakRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakRegister1ActionPerformed(evt);
            }
        });
        jMenu8.add(MnCetakRegister1);

        MnLembarCasemix.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarCasemix.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCasemix.setForeground(new java.awt.Color(50, 50, 50));
        MnLembarCasemix.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCasemix.setText("Lembar Casemix");
        MnLembarCasemix.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarCasemix.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarCasemix.setName("MnLembarCasemix"); // NOI18N
        MnLembarCasemix.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLembarCasemix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCasemixActionPerformed(evt);
            }
        });
        jMenu8.add(MnLembarCasemix);

        MnLembarCasemix1.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarCasemix1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCasemix1.setForeground(new java.awt.Color(50, 50, 50));
        MnLembarCasemix1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCasemix1.setText("Lembar Casemix 2");
        MnLembarCasemix1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarCasemix1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarCasemix1.setName("MnLembarCasemix1"); // NOI18N
        MnLembarCasemix1.setPreferredSize(new java.awt.Dimension(320, 26));
        MnLembarCasemix1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCasemix1ActionPerformed(evt);
            }
        });
        jMenu8.add(MnLembarCasemix1);

        MnSPBK.setBackground(new java.awt.Color(255, 255, 254));
        MnSPBK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSPBK.setForeground(new java.awt.Color(50, 50, 50));
        MnSPBK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSPBK.setText("Surat Bukti Pelayanan Kesehatan (SBPK)");
        MnSPBK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSPBK.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSPBK.setName("MnSPBK"); // NOI18N
        MnSPBK.setPreferredSize(new java.awt.Dimension(240, 26));
        MnSPBK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSPBKActionPerformed(evt);
            }
        });
        jMenu8.add(MnSPBK);

        MnSPBK1.setBackground(new java.awt.Color(255, 255, 254));
        MnSPBK1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSPBK1.setForeground(new java.awt.Color(50, 50, 50));
        MnSPBK1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSPBK1.setMnemonic('z');
        MnSPBK1.setText("Surat Bukti Pelayanan Kesehatan (SBPK) 2");
        MnSPBK1.setToolTipText("Z");
        MnSPBK1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSPBK1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSPBK1.setName("MnSPBK1"); // NOI18N
        MnSPBK1.setPreferredSize(new java.awt.Dimension(320, 26));
        MnSPBK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSPBK1ActionPerformed(evt);
            }
        });
        jMenu8.add(MnSPBK1);

        MnJKRA.setBackground(new java.awt.Color(255, 255, 254));
        MnJKRA.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJKRA.setForeground(new java.awt.Color(50, 50, 50));
        MnJKRA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJKRA.setText("Surat Jaminan Kesehatan Nasional (JKRA) Rawat Jalan");
        MnJKRA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJKRA.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJKRA.setName("MnJKRA"); // NOI18N
        MnJKRA.setPreferredSize(new java.awt.Dimension(320, 26));
        MnJKRA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJKRAActionPerformed(evt);
            }
        });
        jMenu8.add(MnJKRA);

        MnLembarRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarRalan.setForeground(new java.awt.Color(50, 50, 50));
        MnLembarRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarRalan.setMnemonic('w');
        MnLembarRalan.setText("Lembar Rawat Jalan");
        MnLembarRalan.setToolTipText("W");
        MnLembarRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarRalan.setName("MnLembarRalan"); // NOI18N
        MnLembarRalan.setPreferredSize(new java.awt.Dimension(320, 26));
        MnLembarRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarRalanActionPerformed(evt);
            }
        });
        jMenu8.add(MnLembarRalan);

        MnBlangkoResep.setBackground(new java.awt.Color(255, 255, 254));
        MnBlangkoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBlangkoResep.setForeground(new java.awt.Color(50, 50, 50));
        MnBlangkoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBlangkoResep.setMnemonic('w');
        MnBlangkoResep.setText("Blanko Resep");
        MnBlangkoResep.setToolTipText("W");
        MnBlangkoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBlangkoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBlangkoResep.setName("MnBlangkoResep"); // NOI18N
        MnBlangkoResep.setPreferredSize(new java.awt.Dimension(320, 26));
        MnBlangkoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBlangkoResepActionPerformed(evt);
            }
        });
        jMenu8.add(MnBlangkoResep);

        MnSuratJaminanPelayananIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratJaminanPelayananIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratJaminanPelayananIGD.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratJaminanPelayananIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratJaminanPelayananIGD.setMnemonic('w');
        MnSuratJaminanPelayananIGD.setText("Surat Jaminan Pelayanan BPJS");
        MnSuratJaminanPelayananIGD.setToolTipText("W");
        MnSuratJaminanPelayananIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratJaminanPelayananIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratJaminanPelayananIGD.setName("MnSuratJaminanPelayananIGD"); // NOI18N
        MnSuratJaminanPelayananIGD.setPreferredSize(new java.awt.Dimension(320, 26));
        MnSuratJaminanPelayananIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratJaminanPelayananIGDActionPerformed(evt);
            }
        });
        jMenu8.add(MnSuratJaminanPelayananIGD);

        MnLembarKeluarMasuk2.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarKeluarMasuk2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarKeluarMasuk2.setForeground(new java.awt.Color(50, 50, 50));
        MnLembarKeluarMasuk2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarKeluarMasuk2.setText("Lembar Masuk Keluar");
        MnLembarKeluarMasuk2.setName("MnLembarKeluarMasuk2"); // NOI18N
        MnLembarKeluarMasuk2.setPreferredSize(new java.awt.Dimension(320, 26));
        MnLembarKeluarMasuk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarKeluarMasuk2ActionPerformed(evt);
            }
        });
        jMenu8.add(MnLembarKeluarMasuk2);

        MnSuratSurat.add(jMenu8);

        jPopupMenu1.add(MnSuratSurat);

        MnGelang.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang.setText("Label & Barcode");
        MnGelang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang.setName("MnGelang"); // NOI18N
        MnGelang.setPreferredSize(new java.awt.Dimension(250, 26));
        MnGelang.setRequestFocusEnabled(false);

        MnGelang1.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang1.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang1.setText("Gelang Pasien 1");
        MnGelang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang1.setName("MnGelang1"); // NOI18N
        MnGelang1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang1ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang1);

        MnGelang2.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang2.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang2.setText("Gelang Pasien 2");
        MnGelang2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang2.setName("MnGelang2"); // NOI18N
        MnGelang2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang2ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang2);

        MnGelang3.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang3.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang3.setText("Gelang Pasien 3");
        MnGelang3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang3.setName("MnGelang3"); // NOI18N
        MnGelang3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang3ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang3);

        MnGelang4.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang4.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang4.setText("Gelang Pasien 4");
        MnGelang4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang4.setName("MnGelang4"); // NOI18N
        MnGelang4.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang4ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang4);

        MnGelang5.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang5.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang5.setText("Gelang Pasien 5");
        MnGelang5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang5.setName("MnGelang5"); // NOI18N
        MnGelang5.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang5ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang5);

        MnGelang6.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang6.setForeground(new java.awt.Color(50, 50, 50));
        MnGelang6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang6.setText("Gelang Pasien 6");
        MnGelang6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang6.setName("MnGelang6"); // NOI18N
        MnGelang6.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang6ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang6);

        MnLabelTracker.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker.setText("Label Tracker 1");
        MnLabelTracker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker.setName("MnLabelTracker"); // NOI18N
        MnLabelTracker.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTrackerActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker);

        MnLabelTracker1.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker1.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker1.setText("Label Tracker 2");
        MnLabelTracker1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker1.setName("MnLabelTracker1"); // NOI18N
        MnLabelTracker1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker1ActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker1);

        MnLabelTracker2.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker2.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker2.setText("Label Tracker 3");
        MnLabelTracker2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker2.setName("MnLabelTracker2"); // NOI18N
        MnLabelTracker2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker2ActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker2);

        MnLabelTracker3.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker3.setForeground(new java.awt.Color(50, 50, 50));
        MnLabelTracker3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker3.setText("Label Tracker 4");
        MnLabelTracker3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker3.setName("MnLabelTracker3"); // NOI18N
        MnLabelTracker3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker3ActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker3);

        MnBarcode1.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode1.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode1.setText("Barcode Perawatan 1");
        MnBarcode1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode1.setName("MnBarcode1"); // NOI18N
        MnBarcode1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode1ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcode1);

        MnBarcode2.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode2.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode2.setText("Barcode Perawatan 2");
        MnBarcode2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode2.setName("MnBarcode2"); // NOI18N
        MnBarcode2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcode2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode2ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcode2);

        MnBarcode3.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode3.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode3.setText("Barcode RM");
        MnBarcode3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode3.setName("MnBarcode3"); // NOI18N
        MnBarcode3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcode3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode3ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcode3);

        MnBarcode.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode.setText("Barcode Perawatan");
        MnBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode.setName("MnBarcode"); // NOI18N
        MnBarcode.setPreferredSize(new java.awt.Dimension(240, 26));
        MnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcode);

        MnBarcodeRM9.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodeRM9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM9.setForeground(new java.awt.Color(50, 50, 50));
        MnBarcodeRM9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM9.setMnemonic('L');
        MnBarcodeRM9.setText("Label Rekam Medis 10");
        MnBarcodeRM9.setToolTipText("L");
        MnBarcodeRM9.setName("MnBarcodeRM9"); // NOI18N
        MnBarcodeRM9.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM9ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcodeRM9);

        jPopupMenu1.add(MnGelang);

        jMenu5.setBackground(new java.awt.Color(255, 255, 254));
        jMenu5.setForeground(new java.awt.Color(50, 50, 50));
        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu5.setText("Lembar Periksa Pasien");
        jMenu5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu5.setName("jMenu5"); // NOI18N
        jMenu5.setPreferredSize(new java.awt.Dimension(250, 26));
        jMenu5.setRequestFocusEnabled(false);

        MnCheckList4.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList4.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList4.setText("Lembar Periksa Pasien Kanan");
        MnCheckList4.setToolTipText("");
        MnCheckList4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList4.setName("MnCheckList4"); // NOI18N
        MnCheckList4.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList4ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList4);

        MnCheckList5.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList5.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList5.setText("Lembar Periksa Pasien Kiri");
        MnCheckList5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList5.setName("MnCheckList5"); // NOI18N
        MnCheckList5.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList5ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList5);

        MnCheckList6.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList6.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList6.setText("Lembar Periksa Pasien Kanan 2");
        MnCheckList6.setToolTipText("");
        MnCheckList6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList6.setName("MnCheckList6"); // NOI18N
        MnCheckList6.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList6ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList6);

        MnCheckList7.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList7.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList7.setText("Lembar Periksa Pasien Kiri 2");
        MnCheckList7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList7.setName("MnCheckList7"); // NOI18N
        MnCheckList7.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList7ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList7);

        MnCheckList8.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList8.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList8.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList8.setText("Lembar Periksa Pasien Kanan 3");
        MnCheckList8.setToolTipText("");
        MnCheckList8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList8.setName("MnCheckList8"); // NOI18N
        MnCheckList8.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList8ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList8);

        MnCheckList9.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList9.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList9.setText("Lembar Periksa Pasien Kiri 3");
        MnCheckList9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList9.setName("MnCheckList9"); // NOI18N
        MnCheckList9.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList9ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList9);

        jPopupMenu1.add(jMenu5);

        jMenu3.setBackground(new java.awt.Color(255, 255, 254));
        jMenu3.setForeground(new java.awt.Color(50, 50, 50));
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu3.setText("Check List Kelengkapan Pendaftaran");
        jMenu3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu3.setName("jMenu3"); // NOI18N
        jMenu3.setPreferredSize(new java.awt.Dimension(250, 26));
        jMenu3.setRequestFocusEnabled(false);

        MnCheckList.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList.setText("Check List Kelengkapan Pendaftaran Kanan");
        MnCheckList.setToolTipText("");
        MnCheckList.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList.setName("MnCheckList"); // NOI18N
        MnCheckList.setPreferredSize(new java.awt.Dimension(330, 26));
        MnCheckList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckListActionPerformed(evt);
            }
        });
        jMenu3.add(MnCheckList);

        MnCheckList1.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList1.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList1.setText("Check List Kelengkapan Pendaftaran Kiri");
        MnCheckList1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList1.setName("MnCheckList1"); // NOI18N
        MnCheckList1.setPreferredSize(new java.awt.Dimension(330, 26));
        MnCheckList1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList1ActionPerformed(evt);
            }
        });
        jMenu3.add(MnCheckList1);

        MnCheckList2.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList2.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList2.setText("Check List Kelengkapan Pendaftaran Kanan+Tracker");
        MnCheckList2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList2.setName("MnCheckList2"); // NOI18N
        MnCheckList2.setPreferredSize(new java.awt.Dimension(330, 26));
        MnCheckList2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList2ActionPerformed(evt);
            }
        });
        jMenu3.add(MnCheckList2);

        MnCheckList3.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList3.setForeground(new java.awt.Color(50, 50, 50));
        MnCheckList3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList3.setText("Check List Kelengkapan Pendaftaran Kiri+Tracker");
        MnCheckList3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList3.setName("MnCheckList3"); // NOI18N
        MnCheckList3.setPreferredSize(new java.awt.Dimension(330, 26));
        MnCheckList3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList3ActionPerformed(evt);
            }
        });
        jMenu3.add(MnCheckList3);

        jPopupMenu1.add(jMenu3);

        jSeparator13.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator13.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator13.setName("jSeparator13"); // NOI18N
        jSeparator13.setPreferredSize(new java.awt.Dimension(250, 1));
        jPopupMenu1.add(jSeparator13);

        MnRujukan.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukan.setForeground(new java.awt.Color(50, 50, 50));
        MnRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukan.setText("Rujukan");
        MnRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukan.setName("MnRujukan"); // NOI18N
        MnRujukan.setPreferredSize(new java.awt.Dimension(250, 26));

        MnRujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(new java.awt.Color(50, 50, 50));
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Rujukan Keluar");
        MnRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(160, 26));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujuk);

        MnRujukMasuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukMasuk.setForeground(new java.awt.Color(50, 50, 50));
        MnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukMasuk.setText("Rujukan Masuk");
        MnRujukMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukMasuk.setName("MnRujukMasuk"); // NOI18N
        MnRujukMasuk.setPreferredSize(new java.awt.Dimension(160, 26));
        MnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukMasukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujukMasuk);

        MnPoliInternal.setBackground(new java.awt.Color(255, 255, 254));
        MnPoliInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoliInternal.setForeground(new java.awt.Color(50, 50, 50));
        MnPoliInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPoliInternal.setText("Poli Internal");
        MnPoliInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoliInternal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoliInternal.setName("MnPoliInternal"); // NOI18N
        MnPoliInternal.setPreferredSize(new java.awt.Dimension(160, 26));
        MnPoliInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliInternalActionPerformed(evt);
            }
        });
        MnRujukan.add(MnPoliInternal);

        jPopupMenu1.add(MnRujukan);

        MnBridging.setBackground(new java.awt.Color(255, 255, 254));
        MnBridging.setForeground(new java.awt.Color(50, 50, 50));
        MnBridging.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBridging.setText("Bridging");
        MnBridging.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBridging.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBridging.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBridging.setName("MnBridging"); // NOI18N
        MnBridging.setPreferredSize(new java.awt.Dimension(250, 26));

        MnSEP.setBackground(new java.awt.Color(255, 255, 254));
        MnSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSEP.setForeground(new java.awt.Color(50, 50, 50));
        MnSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSEP.setText("SEP BPJS");
        MnSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSEP.setName("MnSEP"); // NOI18N
        MnSEP.setPreferredSize(new java.awt.Dimension(320, 26));
        MnSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSEPActionPerformed(evt);
            }
        });

        ppSuratKontrol.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratKontrol.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratKontrol.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratKontrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratKontrol.setText("Rencana Kontrol BPJS");
        ppSuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratKontrol.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratKontrol.setName("ppSuratKontrol"); // NOI18N
        ppSuratKontrol.setPreferredSize(new java.awt.Dimension(320, 26));
        ppSuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratKontrolBtnPrintActionPerformed(evt);
            }
        });

        ppSuratPRI.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratPRI.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratPRI.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratPRI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratPRI.setText("Perintah Rawat Inap BPJS");
        ppSuratPRI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratPRI.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratPRI.setName("ppSuratPRI"); // NOI18N
        ppSuratPRI.setPreferredSize(new java.awt.Dimension(320, 26));
        ppSuratPRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratPRIBtnPrintActionPerformed(evt);
            }
        });

        ppProgramPRB.setBackground(new java.awt.Color(255, 255, 254));
        ppProgramPRB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppProgramPRB.setForeground(new java.awt.Color(50, 50, 50));
        ppProgramPRB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppProgramPRB.setText("Program PRB BPJS");
        ppProgramPRB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppProgramPRB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppProgramPRB.setName("ppProgramPRB"); // NOI18N
        ppProgramPRB.setPreferredSize(new java.awt.Dimension(320, 26));
        ppProgramPRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppProgramPRBActionPerformed(evt);
            }
        });

        ppSuplesiJasaRaharja.setBackground(new java.awt.Color(255, 255, 254));
        ppSuplesiJasaRaharja.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuplesiJasaRaharja.setForeground(new java.awt.Color(50, 50, 50));
        ppSuplesiJasaRaharja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuplesiJasaRaharja.setText("Suplesi Jasa Raharja BPJS");
        ppSuplesiJasaRaharja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuplesiJasaRaharja.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuplesiJasaRaharja.setName("ppSuplesiJasaRaharja"); // NOI18N
        ppSuplesiJasaRaharja.setPreferredSize(new java.awt.Dimension(320, 26));
        ppSuplesiJasaRaharja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuplesiJasaRaharjaBtnPrintActionPerformed(evt);
            }
        });

        ppDataIndukKecelakaan.setBackground(new java.awt.Color(255, 255, 254));
        ppDataIndukKecelakaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDataIndukKecelakaan.setForeground(new java.awt.Color(50, 50, 50));
        ppDataIndukKecelakaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDataIndukKecelakaan.setText("Data Induk Kecelakaan BPJS");
        ppDataIndukKecelakaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDataIndukKecelakaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDataIndukKecelakaan.setName("ppDataIndukKecelakaan"); // NOI18N
        ppDataIndukKecelakaan.setPreferredSize(new java.awt.Dimension(320, 26));
        ppDataIndukKecelakaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDataIndukKecelakaanBtnPrintActionPerformed(evt);
            }
        });

        MnBelumTerbitSEP.setBackground(new java.awt.Color(255, 255, 254));
        MnBelumTerbitSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelumTerbitSEP.setForeground(new java.awt.Color(50, 50, 50));
        MnBelumTerbitSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBelumTerbitSEP.setText("Belum Terbit SEP BPJS");
        MnBelumTerbitSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBelumTerbitSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBelumTerbitSEP.setName("MnBelumTerbitSEP"); // NOI18N
        MnBelumTerbitSEP.setPreferredSize(new java.awt.Dimension(320, 26));
        MnBelumTerbitSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumTerbitSEPActionPerformed(evt);
            }
        });

        MnSJP.setBackground(new java.awt.Color(255, 255, 254));
        MnSJP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSJP.setForeground(new java.awt.Color(50, 50, 50));
        MnSJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSJP.setText("SJP Inhealth");
        MnSJP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSJP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSJP.setName("MnSJP"); // NOI18N
        MnSJP.setPreferredSize(new java.awt.Dimension(320, 26));
        MnSJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSJPActionPerformed(evt);
            }
        });

        MnPCare.setBackground(new java.awt.Color(255, 255, 254));
        MnPCare.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPCare.setForeground(new java.awt.Color(50, 50, 50));
        MnPCare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPCare.setText("Data PCare");
        MnPCare.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPCare.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPCare.setName("MnPCare"); // NOI18N
        MnPCare.setPreferredSize(new java.awt.Dimension(320, 26));
        MnPCare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPCareActionPerformed(evt);
            }
        });

        MnRujukSisrute.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukSisrute.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukSisrute.setForeground(new java.awt.Color(50, 50, 50));
        MnRujukSisrute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukSisrute.setText("Rujuk Keluar Via Sisrute");
        MnRujukSisrute.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukSisrute.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukSisrute.setName("MnRujukSisrute"); // NOI18N
        MnRujukSisrute.setPreferredSize(new java.awt.Dimension(320, 26));
        MnRujukSisrute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukSisruteActionPerformed(evt);
            }
        });

        ppPerawatanCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppPerawatanCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPerawatanCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppPerawatanCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPerawatanCorona.setText("Perawatan Pasien Corona INACBG");
        ppPerawatanCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPerawatanCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPerawatanCorona.setName("ppPerawatanCorona"); // NOI18N
        ppPerawatanCorona.setPreferredSize(new java.awt.Dimension(320, 26));
        ppPerawatanCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPerawatanCoronaBtnPrintActionPerformed(evt);
            }
        });

        ppPasienCorona.setBackground(new java.awt.Color(255, 255, 254));
        ppPasienCorona.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPasienCorona.setForeground(new java.awt.Color(50, 50, 50));
        ppPasienCorona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPasienCorona.setText("Pasien Corona Kemenkes");
        ppPasienCorona.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPasienCorona.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPasienCorona.setName("ppPasienCorona"); // NOI18N
        ppPasienCorona.setPreferredSize(new java.awt.Dimension(320, 26));
        ppPasienCorona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPasienCoronaBtnPrintActionPerformed(evt);
            }
        });

        MnTeridentifikasiTB.setBackground(new java.awt.Color(255, 255, 254));
        MnTeridentifikasiTB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTeridentifikasiTB.setForeground(new java.awt.Color(50, 50, 50));
        MnTeridentifikasiTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTeridentifikasiTB.setText("Teridentifikasi TB Kemenkes");
        MnTeridentifikasiTB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTeridentifikasiTB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTeridentifikasiTB.setName("MnTeridentifikasiTB"); // NOI18N
        MnTeridentifikasiTB.setPreferredSize(new java.awt.Dimension(320, 26));
        MnTeridentifikasiTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTeridentifikasiTBActionPerformed(evt);
            }
        });

        MnRiwayatPerawatanICareNIK.setBackground(new java.awt.Color(255, 255, 254));
        MnRiwayatPerawatanICareNIK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRiwayatPerawatanICareNIK.setForeground(new java.awt.Color(50, 50, 50));
        MnRiwayatPerawatanICareNIK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRiwayatPerawatanICareNIK.setText("Cek Riwayat Perawatan ICare BPJS Via NIK FKTL");
        MnRiwayatPerawatanICareNIK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRiwayatPerawatanICareNIK.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRiwayatPerawatanICareNIK.setName("MnRiwayatPerawatanICareNIK"); // NOI18N
        MnRiwayatPerawatanICareNIK.setPreferredSize(new java.awt.Dimension(320, 26));
        MnRiwayatPerawatanICareNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRiwayatPerawatanICareNIKActionPerformed(evt);
            }
        });

        MnRiwayatPerawatanICareNoKartu.setBackground(new java.awt.Color(255, 255, 254));
        MnRiwayatPerawatanICareNoKartu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRiwayatPerawatanICareNoKartu.setForeground(new java.awt.Color(50, 50, 50));
        MnRiwayatPerawatanICareNoKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRiwayatPerawatanICareNoKartu.setText("Cek Riwayat Perawatan ICare BPJS Via No.Kartu FKTL");
        MnRiwayatPerawatanICareNoKartu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRiwayatPerawatanICareNoKartu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRiwayatPerawatanICareNoKartu.setName("MnRiwayatPerawatanICareNoKartu"); // NOI18N
        MnRiwayatPerawatanICareNoKartu.setPreferredSize(new java.awt.Dimension(320, 26));
        MnRiwayatPerawatanICareNoKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRiwayatPerawatanICareNoKartuActionPerformed(evt);
            }
        });

        MnRiwayatPerawatanICareNIK1.setBackground(new java.awt.Color(255, 255, 254));
        MnRiwayatPerawatanICareNIK1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRiwayatPerawatanICareNIK1.setForeground(new java.awt.Color(50, 50, 50));
        MnRiwayatPerawatanICareNIK1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRiwayatPerawatanICareNIK1.setText("Cek Riwayat Perawatan ICare BPJS Via NIK FKTP");
        MnRiwayatPerawatanICareNIK1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRiwayatPerawatanICareNIK1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRiwayatPerawatanICareNIK1.setName("MnRiwayatPerawatanICareNIK1"); // NOI18N
        MnRiwayatPerawatanICareNIK1.setPreferredSize(new java.awt.Dimension(320, 26));
        MnRiwayatPerawatanICareNIK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRiwayatPerawatanICareNIK1ActionPerformed(evt);
            }
        });

        MnRiwayatPerawatanICareNoKartu1.setBackground(new java.awt.Color(255, 255, 254));
        MnRiwayatPerawatanICareNoKartu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRiwayatPerawatanICareNoKartu1.setForeground(new java.awt.Color(50, 50, 50));
        MnRiwayatPerawatanICareNoKartu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRiwayatPerawatanICareNoKartu1.setText("Cek Riwayat Perawatan ICare BPJS Via No.Kartu FKTP");
        MnRiwayatPerawatanICareNoKartu1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRiwayatPerawatanICareNoKartu1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRiwayatPerawatanICareNoKartu1.setName("MnRiwayatPerawatanICareNoKartu1"); // NOI18N
        MnRiwayatPerawatanICareNoKartu1.setPreferredSize(new java.awt.Dimension(320, 26));
        MnRiwayatPerawatanICareNoKartu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRiwayatPerawatanICareNoKartu1ActionPerformed(evt);
            }
        });

        jPopupMenu1.add(MnBridging);

        MenuInputData.setBackground(new java.awt.Color(255, 255, 254));
        MenuInputData.setForeground(new java.awt.Color(50, 50, 50));
        MenuInputData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData.setText("Input Data");
        MenuInputData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData.setName("MenuInputData"); // NOI18N
        MenuInputData.setPreferredSize(new java.awt.Dimension(250, 26));

        MnDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa.setForeground(new java.awt.Color(50, 50, 50));
        MnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa.setText("Diagnosa Pasien");
        MnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa.setName("MnDiagnosa"); // NOI18N
        MnDiagnosa.setPreferredSize(new java.awt.Dimension(240, 26));
        MnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaActionPerformed(evt);
            }
        });
        MenuInputData.add(MnDiagnosa);

        ppCatatanPasien.setBackground(new java.awt.Color(255, 255, 254));
        ppCatatanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanPasien.setForeground(new java.awt.Color(50, 50, 50));
        ppCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCatatanPasien.setText("Catatan Untuk Pasien");
        ppCatatanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanPasien.setName("ppCatatanPasien"); // NOI18N
        ppCatatanPasien.setPreferredSize(new java.awt.Dimension(240, 26));
        ppCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanPasienBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppCatatanPasien);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(240, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppBerkasDigital);

        ppIKP.setBackground(new java.awt.Color(255, 255, 254));
        ppIKP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP.setForeground(new java.awt.Color(50, 50, 50));
        ppIKP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppIKP.setText("Insiden Keselamatan Pasien");
        ppIKP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP.setName("ppIKP"); // NOI18N
        ppIKP.setPreferredSize(new java.awt.Dimension(240, 26));
        ppIKP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKPBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppIKP);

        jPopupMenu1.add(MenuInputData);

        MnHapusData.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusData.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusData.setText("Hapus & Ubah Data");
        MnHapusData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusData.setName("MnHapusData"); // NOI18N
        MnHapusData.setPreferredSize(new java.awt.Dimension(250, 26));
        MnHapusData.setRequestFocusEnabled(false);

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Hapus Tagihan Operasi");
        MnHapusTagihanOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihanOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(240, 26));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTagihanOperasi);

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Hapus Obat Operasi");
        MnHapusObatOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObatOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(240, 26));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusObatOperasi);

        MnGabungNoRawat.setBackground(new java.awt.Color(255, 255, 254));
        MnGabungNoRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGabungNoRawat.setForeground(new java.awt.Color(50, 50, 50));
        MnGabungNoRawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGabungNoRawat.setText("Gabung Nomor Rawat");
        MnGabungNoRawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGabungNoRawat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGabungNoRawat.setName("MnGabungNoRawat"); // NOI18N
        MnGabungNoRawat.setPreferredSize(new java.awt.Dimension(190, 26));
        MnGabungNoRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGabungNoRawatActionPerformed(evt);
            }
        });
        MnHapusData.add(MnGabungNoRawat);

        MnStatus.setBackground(new java.awt.Color(255, 255, 254));
        MnStatus.setForeground(new java.awt.Color(50, 50, 50));
        MnStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatus.setText("Set Status");
        MnStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatus.setName("MnStatus"); // NOI18N
        MnStatus.setPreferredSize(new java.awt.Dimension(250, 26));

        ppBerkas.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkas.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkas.setText("Berkas R.M. Diterima");
        ppBerkas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkas.setName("ppBerkas"); // NOI18N
        ppBerkas.setPreferredSize(new java.awt.Dimension(220, 26));
        ppBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppBerkas);

        MnSudah.setBackground(new java.awt.Color(255, 255, 254));
        MnSudah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudah.setForeground(new java.awt.Color(50, 50, 50));
        MnSudah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSudah.setText("Sudah Periksa");
        MnSudah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSudah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSudah.setName("MnSudah"); // NOI18N
        MnSudah.setPreferredSize(new java.awt.Dimension(220, 26));
        MnSudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahActionPerformed(evt);
            }
        });
        MnStatus.add(MnSudah);

        MnBelum.setBackground(new java.awt.Color(255, 255, 254));
        MnBelum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelum.setForeground(new java.awt.Color(50, 50, 50));
        MnBelum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBelum.setText("Belum Periksa");
        MnBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBelum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBelum.setName("MnBelum"); // NOI18N
        MnBelum.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBelum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumActionPerformed(evt);
            }
        });
        MnStatus.add(MnBelum);

        MnBatal.setBackground(new java.awt.Color(255, 255, 254));
        MnBatal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBatal.setForeground(new java.awt.Color(50, 50, 50));
        MnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBatal.setText("Batal Periksa");
        MnBatal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBatal.setName("MnBatal"); // NOI18N
        MnBatal.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBatalActionPerformed(evt);
            }
        });
        MnStatus.add(MnBatal);

        MnDirujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnDirujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDirujuk.setForeground(new java.awt.Color(50, 50, 50));
        MnDirujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDirujuk.setText("Dirujuk");
        MnDirujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDirujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDirujuk.setName("MnDirujuk"); // NOI18N
        MnDirujuk.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDirujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDirujukActionPerformed(evt);
            }
        });
        MnStatus.add(MnDirujuk);

        MnDIrawat.setBackground(new java.awt.Color(255, 255, 254));
        MnDIrawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDIrawat.setForeground(new java.awt.Color(50, 50, 50));
        MnDIrawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDIrawat.setText("Dirawat");
        MnDIrawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDIrawat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDIrawat.setName("MnDIrawat"); // NOI18N
        MnDIrawat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDIrawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDIrawatActionPerformed(evt);
            }
        });
        MnStatus.add(MnDIrawat);

        MnMeninggal.setBackground(new java.awt.Color(255, 255, 254));
        MnMeninggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMeninggal.setForeground(new java.awt.Color(50, 50, 50));
        MnMeninggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnMeninggal.setText("Meninggal");
        MnMeninggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnMeninggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnMeninggal.setName("MnMeninggal"); // NOI18N
        MnMeninggal.setPreferredSize(new java.awt.Dimension(220, 26));
        MnMeninggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnMeninggalActionPerformed(evt);
            }
        });
        MnStatus.add(MnMeninggal);

        MnPulangPaksa.setBackground(new java.awt.Color(255, 255, 254));
        MnPulangPaksa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPulangPaksa.setForeground(new java.awt.Color(50, 50, 50));
        MnPulangPaksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPulangPaksa.setText("Pulang Paksa");
        MnPulangPaksa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPulangPaksa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPulangPaksa.setName("MnPulangPaksa"); // NOI18N
        MnPulangPaksa.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPulangPaksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPulangPaksaBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(MnPulangPaksa);

        jMenu7.setBackground(new java.awt.Color(255, 255, 254));
        jMenu7.setForeground(new java.awt.Color(50, 50, 50));
        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu7.setText("Status Poli");
        jMenu7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu7.setName("jMenu7"); // NOI18N
        jMenu7.setPreferredSize(new java.awt.Dimension(240, 26));

        MnStatusBaru.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBaru.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusBaru.setText("Status Poli Baru");
        MnStatusBaru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBaru.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBaru.setName("MnStatusBaru"); // NOI18N
        MnStatusBaru.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBaruActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusBaru);

        MnStatusLama.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusLama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusLama.setForeground(new java.awt.Color(50, 50, 50));
        MnStatusLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusLama.setText("Status Poli Lama");
        MnStatusLama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusLama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusLama.setName("MnStatusLama"); // NOI18N
        MnStatusLama.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusLamaActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusLama);

        MnStatus.add(jMenu7);

        MnHapusData.add(MnStatus);

        jPopupMenu1.add(MnHapusData);

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        DlgDemografi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgDemografi.setName("DlgDemografi"); // NOI18N
        DlgDemografi.setUndecorated(true);
        DlgDemografi.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Demografi Pendaftar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa3.setName("panelBiasa3"); // NOI18N
        panelBiasa3.setLayout(null);

        BtnPrint3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint3.setMnemonic('G');
        BtnPrint3.setText("Grafik");
        BtnPrint3.setToolTipText("Alt+G");
        BtnPrint3.setName("BtnPrint3"); // NOI18N
        BtnPrint3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint3ActionPerformed(evt);
            }
        });
        BtnPrint3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint3KeyPressed(evt);
            }
        });
        panelBiasa3.add(BtnPrint3);
        BtnPrint3.setBounds(110, 110, 100, 30);

        BtnKeluar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar3.setMnemonic('K');
        BtnKeluar3.setText("Keluar");
        BtnKeluar3.setToolTipText("Alt+K");
        BtnKeluar3.setName("BtnKeluar3"); // NOI18N
        BtnKeluar3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar3ActionPerformed(evt);
            }
        });
        BtnKeluar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar3KeyPressed(evt);
            }
        });
        panelBiasa3.add(BtnKeluar3);
        BtnKeluar3.setBounds(430, 110, 100, 30);

        Kelurahan2.setHighlighter(null);
        Kelurahan2.setName("Kelurahan2"); // NOI18N
        Kelurahan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kelurahan2KeyPressed(evt);
            }
        });
        panelBiasa3.add(Kelurahan2);
        Kelurahan2.setBounds(105, 70, 350, 23);

        btnKel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKel.setMnemonic('1');
        btnKel.setToolTipText("ALt+1");
        btnKel.setName("btnKel"); // NOI18N
        btnKel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelActionPerformed(evt);
            }
        });
        panelBiasa3.add(btnKel);
        btnKel.setBounds(460, 70, 28, 23);

        Kecamatan2.setHighlighter(null);
        Kecamatan2.setName("Kecamatan2"); // NOI18N
        Kecamatan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kecamatan2KeyPressed(evt);
            }
        });
        panelBiasa3.add(Kecamatan2);
        Kecamatan2.setBounds(105, 40, 350, 23);

        btnKec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKec.setMnemonic('1');
        btnKec.setToolTipText("ALt+1");
        btnKec.setName("btnKec"); // NOI18N
        btnKec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKecActionPerformed(evt);
            }
        });
        panelBiasa3.add(btnKec);
        btnKec.setBounds(460, 40, 28, 23);

        Kabupaten2.setHighlighter(null);
        Kabupaten2.setName("Kabupaten2"); // NOI18N
        Kabupaten2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kabupaten2KeyPressed(evt);
            }
        });
        panelBiasa3.add(Kabupaten2);
        Kabupaten2.setBounds(105, 10, 350, 23);

        btnKab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKab.setMnemonic('1');
        btnKab.setToolTipText("ALt+1");
        btnKab.setName("btnKab"); // NOI18N
        btnKab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKabActionPerformed(evt);
            }
        });
        panelBiasa3.add(btnKab);
        btnKab.setBounds(460, 10, 28, 23);

        jLabel34.setText("Kelurahan :");
        jLabel34.setName("jLabel34"); // NOI18N
        panelBiasa3.add(jLabel34);
        jLabel34.setBounds(0, 70, 100, 23);

        jLabel35.setText("Kabupaten :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelBiasa3.add(jLabel35);
        jLabel35.setBounds(0, 10, 100, 23);

        jLabel36.setText("Kecamatan :");
        jLabel36.setName("jLabel36"); // NOI18N
        panelBiasa3.add(jLabel36);
        jLabel36.setBounds(0, 40, 100, 23);

        BtnPrint4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint4.setMnemonic('T');
        BtnPrint4.setText("Cetak");
        BtnPrint4.setToolTipText("Alt+T");
        BtnPrint4.setName("BtnPrint4"); // NOI18N
        BtnPrint4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint4ActionPerformed(evt);
            }
        });
        BtnPrint4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint4KeyPressed(evt);
            }
        });
        panelBiasa3.add(BtnPrint4);
        BtnPrint4.setBounds(10, 110, 100, 30);

        internalFrame4.add(panelBiasa3, java.awt.BorderLayout.CENTER);

        DlgDemografi.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        DlgSakit2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit2.setName("DlgSakit2"); // NOI18N
        DlgSakit2.setUndecorated(true);
        DlgSakit2.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Keterangan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(null);

        jLabel37.setText("Nomor Surat Keterangan :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelBiasa4.add(jLabel37);
        jLabel37.setBounds(7, 10, 140, 23);

        BtnPrint5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint5.setMnemonic('T');
        BtnPrint5.setText("Cetak");
        BtnPrint5.setToolTipText("Alt+T");
        BtnPrint5.setName("BtnPrint5"); // NOI18N
        BtnPrint5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnPrint5);
        BtnPrint5.setBounds(10, 80, 100, 30);

        BtnKeluar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar4.setMnemonic('K');
        BtnKeluar4.setText("Keluar");
        BtnKeluar4.setToolTipText("Alt+K");
        BtnKeluar4.setName("BtnKeluar4"); // NOI18N
        BtnKeluar4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar4ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnKeluar4);
        BtnKeluar4.setBounds(430, 80, 100, 30);

        NomorSurat.setHighlighter(null);
        NomorSurat.setName("NomorSurat"); // NOI18N
        panelBiasa4.add(NomorSurat);
        NomorSurat.setBounds(150, 10, 370, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnSeek5);
        BtnSeek5.setBounds(492, 40, 28, 23);

        CrDokter3.setEditable(false);
        CrDokter3.setName("CrDokter3"); // NOI18N
        CrDokter3.setPreferredSize(new java.awt.Dimension(300, 23));
        panelBiasa4.add(CrDokter3);
        CrDokter3.setBounds(150, 40, 340, 23);

        jLabel24.setText("Dokter Png. Jawab :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel24);
        jLabel24.setBounds(7, 40, 140, 23);

        internalFrame5.add(panelBiasa4, java.awt.BorderLayout.CENTER);

        DlgSakit2.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        DlgCatatan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgCatatan.setName("DlgCatatan"); // NOI18N
        DlgCatatan.setUndecorated(true);
        DlgCatatan.setResizable(false);

        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setWarnaAtas(new java.awt.Color(100, 100, 10));
        internalFrame6.setWarnaBawah(new java.awt.Color(100, 100, 10));
        internalFrame6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                internalFrame6MouseClicked(evt);
            }
        });
        internalFrame6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        LabelCatatan.setForeground(new java.awt.Color(255, 255, 255));
        LabelCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelCatatan.setText("Catatan");
        LabelCatatan.setName("LabelCatatan"); // NOI18N
        LabelCatatan.setPreferredSize(new java.awt.Dimension(580, 23));
        LabelCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelCatatanMouseClicked(evt);
            }
        });
        internalFrame6.add(LabelCatatan);

        DlgCatatan.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        JK.setName("JK"); // NOI18N
        JK.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Registrasi IGD Hari Ini ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setToolTipText("Klik data di table, kemudian klik kanan untuk menampilkan menu yang dibutuhkan");
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPetugas.setAutoCreateRowSorter(true);
        tbPetugas.setToolTipText("Klik data di table, kemudian klik kanan untuk menampilkan menu yang dibutuhkan");
        tbPetugas.setComponentPopupMenu(jPopupMenu1);
        tbPetugas.setName("tbPetugas"); // NOI18N
        tbPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugasMouseClicked(evt);
            }
        });
        tbPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugasKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPetugas);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass6.add(BtnSimpan);

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
        panelGlass6.add(BtnBatal);

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
        panelGlass6.add(BtnHapus);

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
        panelGlass6.add(BtnEdit);

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
        panelGlass6.add(BtnPrint);

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
        panelGlass6.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelGlass6.add(LCount);

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
        panelGlass6.add(BtnKeluar);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-12-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-12-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(158, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass7.add(BtnCari);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(490, 167));
        FormInput.setLayout(null);

        jLabel3.setText("No. Reg. :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 70, 23);

        jLabel4.setText("No. Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 42, 70, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormInput.add(TDokter);
        TDokter.setBounds(183, 102, 209, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 42, 190, 23);

        jLabel8.setText("Tgl. Reg. :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 72, 70, 23);

        jLabel13.setText("Dr Dituju :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 102, 70, 23);
        jLabel13.getAccessibleContext().setAccessibleDescription("");

        jLabel9.setText("Jam :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(165, 72, 36, 23);

        DTPReg.setForeground(new java.awt.Color(50, 70, 50));
        DTPReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-12-2023" }));
        DTPReg.setDisplayFormat("dd-MM-yyyy");
        DTPReg.setName("DTPReg"); // NOI18N
        DTPReg.setOpaque(false);
        DTPReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPRegKeyPressed(evt);
            }
        });
        FormInput.add(DTPReg);
        DTPReg.setBounds(74, 72, 90, 23);

        jLabel20.setText("Png. Jawab :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(416, 42, 100, 23);

        jLabel21.setText("Almt Png. Jwb :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(426, 72, 90, 23);

        TPngJwb.setHighlighter(null);
        TPngJwb.setName("TPngJwb"); // NOI18N
        TPngJwb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPngJwbKeyPressed(evt);
            }
        });
        FormInput.add(TPngJwb);
        TPngJwb.setBounds(520, 42, 150, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(520, 12, 110, 23);

        TNoReg.setHighlighter(null);
        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRegKeyPressed(evt);
            }
        });
        FormInput.add(TNoReg);
        TNoReg.setBounds(74, 12, 120, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKeyPressed(evt);
            }
        });
        FormInput.add(CmbJam);
        CmbJam.setBounds(205, 72, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKeyPressed(evt);
            }
        });
        FormInput.add(CmbMenit);
        CmbMenit.setBounds(270, 72, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKeyPressed(evt);
            }
        });
        FormInput.add(CmbDetik);
        CmbDetik.setBounds(335, 72, 62, 23);

        jLabel7.setText("No.Rekam Medik :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(416, 12, 100, 23);

        TAlmt.setHighlighter(null);
        TAlmt.setName("TAlmt"); // NOI18N
        TAlmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlmtKeyPressed(evt);
            }
        });
        FormInput.add(TAlmt);
        TAlmt.setBounds(520, 72, 170, 23);

        BtnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPasien.setMnemonic('1');
        BtnPasien.setToolTipText("ALt+1");
        BtnPasien.setName("BtnPasien"); // NOI18N
        BtnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPasienActionPerformed(evt);
            }
        });
        FormInput.add(BtnPasien);
        BtnPasien.setBounds(852, 12, 28, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(632, 12, 218, 23);

        jLabel22.setText("Hubungan :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(648, 42, 98, 23);

        THbngn.setHighlighter(null);
        THbngn.setName("THbngn"); // NOI18N
        THbngn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THbngnKeyPressed(evt);
            }
        });
        FormInput.add(THbngn);
        THbngn.setBounds(750, 42, 130, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(400, 72, 23, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 102, 107, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('3');
        BtnDokter.setToolTipText("ALt+3");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(395, 102, 28, 23);

        jLabel30.setText("Status :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(648, 72, 98, 23);

        TStatus.setEditable(false);
        TStatus.setHighlighter(null);
        TStatus.setName("TStatus"); // NOI18N
        FormInput.add(TStatus);
        TStatus.setBounds(750, 72, 130, 23);

        jLabel18.setText("Jenis Bayar :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(426, 102, 90, 23);

        kdpnj.setHighlighter(null);
        kdpnj.setName("kdpnj"); // NOI18N
        kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpnjKeyPressed(evt);
            }
        });
        FormInput.add(kdpnj);
        kdpnj.setBounds(520, 102, 70, 23);

        nmpnj.setEditable(false);
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(592, 102, 259, 23);

        btnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenjab.setMnemonic('2');
        btnPenjab.setToolTipText("ALt+2");
        btnPenjab.setName("btnPenjab"); // NOI18N
        btnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjabActionPerformed(evt);
            }
        });
        FormInput.add(btnPenjab);
        btnPenjab.setBounds(852, 102, 28, 23);

        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan);
        AsalRujukan.setBounds(520, 132, 331, 23);

        jLabel23.setText("Asal Rujukan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(426, 132, 90, 23);

        btnPenjab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenjab1.setMnemonic('2');
        btnPenjab1.setToolTipText("ALt+2");
        btnPenjab1.setName("btnPenjab1"); // NOI18N
        btnPenjab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjab1ActionPerformed(evt);
            }
        });
        FormInput.add(btnPenjab1);
        btnPenjab1.setBounds(852, 132, 28, 23);

        ChkTracker.setBorder(null);
        ChkTracker.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkTracker.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkTracker.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkTracker.setName("ChkTracker"); // NOI18N
        FormInput.add(ChkTracker);
        ChkTracker.setBounds(203, 12, 23, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        Valid.pindah(evt,TNoReg,DTPReg);
}//GEN-LAST:event_TNoRwKeyPressed

    private void DTPRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPRegKeyPressed
        Valid.pindah(evt,TNoRw,CmbJam);
}//GEN-LAST:event_DTPRegKeyPressed

    private void TPngJwbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPngJwbKeyPressed
        Valid.pindah(evt,TNoRM,THbngn);
}//GEN-LAST:event_TPngJwbKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPas();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPasienActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            if(!TPasien.getText().isEmpty()){
                Map<String, Object> data = new HashMap<>();
                data.put("poli","IGD");
                data.put("antrian",TNoReg.getText());
                data.put("nama",TPasien.getText());
                data.put("norm",TNoRM.getText());
                data.put("bayar",nmpnj.getText());
                data.put("namars",akses.getnamars());
                data.put("alamatrs",akses.getalamatrs());
                data.put("kotars",akses.getkabupatenrs());
                data.put("propinsirs",akses.getpropinsirs());
                data.put("kontakrs",akses.getkontakrs());
                data.put("emailrs",akses.getemailrs());
                data.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                Valid.MyReport("rptCheckList.jasper","report","::[ Check List ]::",data); 
            }             
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isPas();
            TPngJwb.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            KdDokter.requestFocus();
        }
}//GEN-LAST:event_TNoRMKeyPressed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        Valid.pindah(evt,TCari,TNoRw);
}//GEN-LAST:event_TNoRegKeyPressed

    private void CmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKeyPressed
        Valid.pindah(evt,DTPReg,CmbMenit);
}//GEN-LAST:event_CmbJamKeyPressed

    private void CmbMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKeyPressed
        Valid.pindah(evt,CmbJam,CmbDetik);
}//GEN-LAST:event_CmbMenitKeyPressed

    private void CmbDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKeyPressed
        Valid.pindah(evt,CmbMenit,KdDokter);
}//GEN-LAST:event_CmbDetikKeyPressed

    private void TAlmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlmtKeyPressed
        Valid.pindah(evt,THbngn,kdpnj);
}//GEN-LAST:event_TAlmtKeyPressed

    private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
        akses.setform("DlgIGD");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnPasienActionPerformed

    private void THbngnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THbngnKeyPressed
        Valid.pindah(evt,TPngJwb,TAlmt);
}//GEN-LAST:event_THbngnKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       if(TNoReg.getText().trim().equals("")){
            Valid.textKosong(TNoReg,"No.Regristrasi");
        }else if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"dokter");
        }else if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else if(kdpnj.getText().trim().equals("")||nmpnj.getText().trim().equals("")){
            Valid.textKosong(kdpnj,"Jenis Bayar");
        }else if(Sequel.cariInteger("select count(pasien.no_rkm_medis) from pasien inner join reg_periksa inner join kamar_inap "+
                 "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat "+
                 "where kamar_inap.stts_pulang='-' and pasien.no_rkm_medis=?",TNoRM.getText())>0){
            JOptionPane.showMessageDialog(null,"Pasien sedang dalam masa perawatan di kamar inap..!!");
            TNoRM.requestFocus();
        }else{
            ceksukses=false;
            switch (TStatus.getText()) {
                case "Baru":
                        biaya=biayabaru;
                        break;
                case "Lama":
                        biaya=biayalama;
                        break;
                default:
                        biaya=biayabaru;
                        break;
            }
            if(kdigd.equals("")){
                Sequel.menyimpan2("poliklinik","?,?,?,?,?",5,new String[]{"IGDK","Unit IGD","0","0","1"});
            }
                
            status="Baru";
            if(Sequel.cariInteger("select count(reg_periksa.no_rkm_medis) from reg_periksa where reg_periksa.no_rkm_medis=? and reg_periksa.kd_poli='IGDK'",TNoRM.getText())>0){
                status="Lama";
            }
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                    KdDokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                    TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                ceksukses=true;               
            }else{
                Kd2.setText("");
                isNumber();
                if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                        new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                        KdDokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                        TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                    ceksukses=true;           
                }else{
                    Kd2.setText("");
                    isNumber();
                    if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                            new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                            KdDokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                            TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                        ceksukses=true;              
                    }else{
                        Kd2.setText("");
                        isNumber();
                        if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                                new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                KdDokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                                TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                            ceksukses=true;                
                        }else{
                            Kd2.setText("");
                            isNumber();
                            if(Sequel.menyimpantf("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                    KdDokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                                    TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                                ceksukses=true;               
                            }else{
                                Kd2.setText("");
                                TNoRM.requestFocus();
                                isNumber();
                            } 
                        } 
                    } 
                }                
            } 
            if(ceksukses==true){
                UpdateUmur(); 
                if(!AsalRujukan.getText().equals("")){
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"' ","BR/"+dateformat.format(DTPReg.getDate())+"/",4,NoBalasan);

                    if(nosisrute.equals("")){
                        Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+AsalRujukan.getText()+"','"+alamatperujuk+"','-','0','"+AsalRujukan.getText()+"','-','-','-','"+NoBalasan.getText()+"'","No.Rujuk");
                    }else{
                        Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+AsalRujukan.getText()+"','"+alamatperujuk+"','"+nosisrute+"','0','"+AsalRujukan.getText()+"','-','-','-','"+NoBalasan.getText()+"'","No.Rujuk"); 
                        nosisrute="";
                    }                    
                }
                if(ChkTracker.isSelected()==true){
                    ctk();
                }
                tabMode.addRow(new Object[] {
                    false,TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                    KdDokter.getText(),TDokter.getText(),TNoRM.getText(),TPasien.getText(),JK.getText(),umur+" "+sttsumur,"IGD",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),Valid.SetAngka(biaya),
                    TStatus.getText(),nmpnj.getText(),"Belum",kdpnj.getText(),"Belum Bayar"
                });
                emptTeks(); 
            }
            
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,kdpnj,BtnBatal);
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
        for(i=0;i<tbPetugas.getRowCount();i++){ 
            if(tbPetugas.getValueAt(i,0).toString().equals("true")){
                if(Sequel.meghapustf("reg_periksa","no_rawat",tbPetugas.getValueAt(i,2).toString())==true){
                    tabMode.removeRow(i);
                    i--;
                }
            }
        }
        
        LCount.setText(""+tabMode.getRowCount());
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
               Valid.MyReportqry("rptReg.jasper","report","::[ Data Registrasi Periksa ]::","select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab  "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "where poliklinik.kd_poli='IGDK' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and "+
                   "(reg_periksa.no_reg like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.tgl_registrasi like '%"+TCari.getText().trim()+"%' or "+
                   "reg_periksa.kd_dokter like '%"+TCari.getText().trim()+"%' or dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                   "reg_periksa.stts_daftar like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' or "+
                   "reg_periksa.p_jawab like '%"+TCari.getText().trim()+"%' or reg_periksa.almt_pj like '%"+TCari.getText().trim()+"%' or penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                   "reg_periksa.hubunganpj like '%"+TCari.getText().trim()+"%') "+terbitsep+" order by reg_periksa.no_rawat desc",param);   
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        pasien.dispose();
        pasien.bahasa.dispose();
        pasien.cacat.dispose();
        pasien.golonganpolri.dispose();
        pasien.golongantni.dispose();
        pasien.jabatanpolri.dispose();
        pasien.jabatantni.dispose();
        pasien.kab.dispose();
        pasien.kec.dispose();
        pasien.kel.dispose();
        pasien.pangkatpolri.dispose();
        pasien.pangkattni.dispose();
        pasien.penjab.dispose();
        pasien.perusahaan.dispose();
        pasien.prop.dispose();
        pasien.satuanpolri.dispose();
        pasien.satuantni.dispose();
        pasien.suku.dispose();
        dokter.dispose();
        rujukmasuk.WindowPerujuk.dispose();   
        DlgSakit2.dispose();
        DlgDemografi.dispose();
        DlgCatatan.dispose();
        akses.setAktif(false);
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        terbitsep="";
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoReg.getText().trim().equals("")){
            Valid.textKosong(TNoReg,"No.Regristrasi");
        }else if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"dokter");
        }else if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else{
            if(tbPetugas.getSelectedRow()>-1){
                switch (TStatus.getText()) {
                    case "Baru":
                            biaya=Sequel.cariIsiAngka("select poliklinik.registrasi from poliklinik where poliklinik.kd_poli='IGDK'");
                            break;
                    case "Lama":
                            biaya=Sequel.cariIsiAngka("select poliklinik.registrasilama from poliklinik where poliklinik.kd_poli='IGDK'");
                            break;
                    default:
                            biaya=Sequel.cariIsiAngka("select poliklinik.registrasi from poliklinik where poliklinik.kd_poli='IGDK'");
                            break;
                }
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh diubah.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                }else{
                    if(akses.getedit_registrasi()==true){
                        if(Sequel.queryu2tf("update reg_periksa set no_rawat=?,no_reg=?,tgl_registrasi=?,jam_reg=?,kd_dokter=?,no_rkm_medis=?,kd_poli=?,"+
                                "p_jawab=?,almt_pj=?,biaya_reg=?,hubunganpj=?,stts_daftar=?,kd_pj=?,umurdaftar=?,sttsumur=? where no_rawat=?",16,new String[]{
                                TNoRw.getText(),TNoReg.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                KdDokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),""+biaya,THbngn.getText(),
                                TStatus.getText(),kdpnj.getText(),umur,sttsumur,tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString()
                            })==true){
                            tabMode.setValueAt(TNoReg.getText(),tbPetugas.getSelectedRow(),1);
                            tabMode.setValueAt(TNoRw.getText(),tbPetugas.getSelectedRow(),2);
                            tabMode.setValueAt(Valid.SetTgl(DTPReg.getSelectedItem()+""),tbPetugas.getSelectedRow(),3);
                            tabMode.setValueAt(CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),tbPetugas.getSelectedRow(),4);
                            tabMode.setValueAt(KdDokter.getText(),tbPetugas.getSelectedRow(),5);
                            tabMode.setValueAt(TDokter.getText(),tbPetugas.getSelectedRow(),6);
                            tabMode.setValueAt(TNoRM.getText(),tbPetugas.getSelectedRow(),7);
                            tabMode.setValueAt(TPasien.getText(),tbPetugas.getSelectedRow(),8);
                            tabMode.setValueAt(JK.getText(),tbPetugas.getSelectedRow(),9);
                            tabMode.setValueAt(umur+" "+sttsumur,tbPetugas.getSelectedRow(),10);
                            tabMode.setValueAt("IGD",tbPetugas.getSelectedRow(),11);
                            tabMode.setValueAt(TPngJwb.getText(),tbPetugas.getSelectedRow(),12);
                            tabMode.setValueAt(TAlmt.getText(),tbPetugas.getSelectedRow(),13);
                            tabMode.setValueAt(THbngn.getText(),tbPetugas.getSelectedRow(),14);
                            tabMode.setValueAt(Valid.SetAngka(biaya),tbPetugas.getSelectedRow(),15);
                            tabMode.setValueAt(TStatus.getText(),tbPetugas.getSelectedRow(),16);
                            tabMode.setValueAt(nmpnj.getText(),tbPetugas.getSelectedRow(),17);
                            tabMode.setValueAt(kdpnj.getText(),tbPetugas.getSelectedRow(),19);
                            emptTeks();
                        }
                    }else{
                        JOptionPane.showMessageDialog(rootPane,"Maaf hak akses anda dibatasi, silahkan konfirmasi kepada admin..!!! ");
                        TCari.requestFocus();
                    }
                }
            }                
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

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

    private void tbPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugasMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==1){
                if(norawatdipilih.equals("")){
                    i=tbPetugas.getSelectedColumn();
                    if(i==8){
                        if(validasicatatan.equals("Yes")){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            LabelCatatan.setText(Sequel.cariIsi("select catatan_pasien.catatan from catatan_pasien where catatan_pasien.no_rkm_medis=?",TNoRM.getText()));
                            if(!LabelCatatan.getText().equals("")){
                                DlgCatatan.setLocationRelativeTo(Scroll);
                                DlgCatatan.setVisible(true);
                            }else{
                                DlgCatatan.setVisible(false);
                            }                           
                            this.setCursor(Cursor.getDefaultCursor());
                        }  
                    }   
                }else{
                    if(tbPetugas.getSelectedRow()!= -1){
                        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                        }else{
                            if(normdipilih.equals(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),7).toString())){
                                Sequel.queryu2("update asuhan_gizi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update aturan_pakai set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_detail_periksa_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_detail_periksa_lab_perujuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_dokter_anak set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_dokter_anestesi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_dokter_pjanak set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_dokter_umum set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_operator1 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_operator2 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_operasi_operator3 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_periksa_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_periksa_lab_perujuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_periksa_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_periksa_radiologi_perujuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_rawat_inap_dr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_rawat_inap_drpr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_rawat_jl_dr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bayar_rawat_jl_drpr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update beri_bhp_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update beri_obat_operasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update berkas_digital_perawatan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update billing set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update booking_operasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bridging_inhealth set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bridging_sep set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bridging_sep_internal set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update bridging_surat_pri_bpjs set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update catatan_perawatan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_HAIs set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_klasifikasi_pasien_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_tb set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igd set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala1 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala2 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala3 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala4 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igddetail_skala5 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igdprimer set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update data_triase_igdsekunder set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update deposit set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_beri_diet set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_nota_inap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_nota_jalan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_obat_racikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_pemberian_obat set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_penagihan_piutang set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_periksa_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_periksa_labpa set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_periksa_labpa_gambar set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update detail_piutang_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update deteksi_dini_corona set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update diagnosa_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update dpjp_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update gambar_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update hasil_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update hemodialisa set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update inacbg_klaim_baru2 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update inacbg_noklaim_corona set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update insiden_keselamatan_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update kamar_inap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update laporan_operasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update monitoring_asuhan_gizi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update mutasi_berkas set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update nota_inap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update nota_jalan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update obat_racikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update operasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_kunjungan_umum set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_obat_diberikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_pendaftaran set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_tindakan_ralan_diberikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pcare_tindakan_ranap_diberikan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_ginekologi_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_ginekologi_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_obstetri_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_obstetri_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pemeriksaan_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pengembalian_deposit set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update pengurangan_biaya set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_gigi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_gigi_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_igd set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_igd_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_kebidanan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_kebidanan_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_mata set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_mata_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan_bayi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan_bayi_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan_masalah set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_awal_keperawatan_ralan_rencana set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_fisioterapi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_mcu set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_igd set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ralan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ralan_anak set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ralan_kandungan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update penilaian_medis_ranap_kandungan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update perawatan_corona set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update periksa_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update periksa_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update perkiraan_biaya_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_labpa set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_obat set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_radiologi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_registrasi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_resep_pulang set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update permintaan_stok_obat_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update piutang_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update prosedur_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update ranap_gabung set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_inap_dr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_inap_drpr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_inap_pr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_jl_dr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_jl_drpr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rawat_jl_pr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update referensi_mobilejkn_bpjs_taskid set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resep_luar set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resep_obat set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resep_pulang set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resume_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resume_pasien_ranap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update returpasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                if(Sequel.queryu2tf("update rujuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                )==false){
                                    Sequel.meghapus("rujuk","no_rawat", norawatdipilih);
                                }
                                if(Sequel.queryu2tf("update rujuk_masuk set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                )==false){
                                    Sequel.meghapus("rujuk_masuk","no_rawat", norawatdipilih);
                                }
                                Sequel.queryu2("update rujukan_internal_poli set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update rvp_klaim_bpjs set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update saran_kesan_lab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update sisrute_rujukan_keluar set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update skrining_gizi set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update stok_obat_pasien set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_cuti_hamil set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_bebas_tato set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_bebas_tbc set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_hamil set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_buta_warna set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_keterangan_covid set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_keterangan_rawat_inap set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_keterangan_sehat set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_kewaspadaan_kesehatan set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_skbn set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update suratsakit set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update suratsakitpihak2 set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update tagihan_obat_langsung set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update resep_luar set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_bebas_tbc set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_buta_warna set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update surat_bebas_tato set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update tambahan_biaya set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update ubah_penjab set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                Sequel.queryu2("update uji_fungsi_kfr set no_rawat=? where no_rawat=?",2,
                                    new String[]{
                                        tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString(),norawatdipilih
                                    }
                                );
                                if(Sequel.queryu2tf("delete from reg_periksa where no_rawat=?",1,new String[]{norawatdipilih})==true){
                                    for(i=0;i<tbPetugas.getRowCount();i++){
                                        if(tbPetugas.getValueAt(i,2).toString().equals(norawatdipilih)){
                                            tabMode.removeRow(i);
                                        }
                                    }
                                    emptTeks();
                                    LCount.setText(""+tabMode.getRowCount());
                                }
                            }else{
                                JOptionPane.showMessageDialog(rootPane,"Tidak bisa digabung karena No.RM berbeda");
                            }
                            norawatdipilih="";
                            normdipilih="";
                        }   
                    } 
                }               
            }else if(evt.getClickCount()==2){
                i=tbPetugas.getSelectedColumn();
                if(i==1){
                    if(MnKamarInap.isEnabled()==true){
                        MnKamarInapActionPerformed(null);
                    }                    
                }else if(i==2){
                    if(akses.gettindakan_ralan()==true){
                        MnRawatJalanActionPerformed(null);
                    }                    
                }else if(i==3){
                    if(akses.getberi_obat()==true){
                        MnPemberianObatActionPerformed(null);
                    }                   
                }else if(i==4){
                    if(akses.getperiksa_lab()==true){
                        MnPeriksaLabActionPerformed(null);
                    }                    
                }else if(i==5){
                    if(akses.getrujukan_masuk()==true){
                        MnRujukMasukActionPerformed(null);
                    }                    
                }
            }
            
        }
        
}//GEN-LAST:event_tbPetugasMouseClicked

    private void tbPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbPetugas.getSelectedColumn();
                if(i==1){
                    if(MnKamarInap.isEnabled()==true){
                        MnKamarInapActionPerformed(null);
                    }                    
                }else if(i==2){
                    if(akses.gettindakan_ralan()==true){
                        MnRawatJalanActionPerformed(null);
                    }                    
                }else if(i==3){
                    if(akses.getberi_obat()==true){
                        MnPemberianObatActionPerformed(null);
                    }                   
                }else if(i==4){
                    if(akses.getperiksa_lab()==true){
                        MnPeriksaLabActionPerformed(null);
                    }                    
                }else if(i==5){
                    if(akses.getrujukan_masuk()==true){
                        MnRujukMasukActionPerformed(null);
                    }                    
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_L){
                MnBarcodeRM9ActionPerformed(null);
            }else if(evt.getKeyCode()==KeyEvent.VK_Z){
                MnSPBK1ActionPerformed(null);
            }else if(evt.getKeyCode()==KeyEvent.VK_W){
                MnLembarRalanActionPerformed(null);
            }
        }
}//GEN-LAST:event_tbPetugasKeyPressed

private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isNumber();
            TDokter.setText(dokter.tampil3(KdDokter.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnDokterActionPerformed(null);
        }else{
            isNumber();
            TDokter.setText(dokter.tampil3(KdDokter.getText()));
            Valid.pindah(evt,CmbDetik,TNoRM);
        }
}//GEN-LAST:event_KdDokterKeyPressed

private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        akses.setform("DlgIGD");
        dokter.isCek();        
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnDokterActionPerformed

private void MnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInapActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                if(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),18).toString().equals("Batal")){
                    JOptionPane.showMessageDialog(null,"Pasien berstatus batal periksa...!");
                }else{
                    if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                    }else{
                        akses.setstatus(true);
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgKamarInap dlgki=new DlgKamarInap(null,false);
                        dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        dlgki.setLocationRelativeTo(internalFrame1);
                        dlgki.emptTeks();
                        dlgki.isCek();
                        dlgki.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());   
                        dlgki.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }
        }
}//GEN-LAST:event_MnKamarInapActionPerformed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
                    dlgrwjl.isCek();
                    dlgrwjl.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    dlgrwjl.setLocationRelativeTo(internalFrame1);
                    dlgrwjl.SetPoli("IGDK");
                    dlgrwjl.SetPj(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),19).toString());
                    dlgrwjl.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                    dlgrwjl.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
                                
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{            
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgRujuk dlgrjk=new DlgRujuk(null,false);
            dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.emptTeks();
            dlgrjk.isCek();
            dlgrjk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
            dlgrjk.tampil();
            dlgrjk.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
}//GEN-LAST:event_MnRujukActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
                dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrwinap.setLocationRelativeTo(internalFrame1);
                dlgrwinap.isCek();
                dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ralan");
                dlgrwinap.tampilPO();
                dlgrwinap.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }                
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    try {
                        pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                        try {
                            pscaripiutang.setString(1,TNoRM.getText());
                            rs=pscaripiutang.executeQuery();
                            if(rs.next()){
                                i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                                if(i==JOptionPane.YES_OPTION){
                                     this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                     DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                                     piutang.setNoRm(TNoRM.getText(),rs.getDate(1));
                                     piutang.tampil();
                                     piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                     piutang.setLocationRelativeTo(internalFrame1);
                                     piutang.setVisible(true);
                                     this.setCursor(Cursor.getDefaultCursor());
                                }else{   
                                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    DlgBilingRalan dlgbil=new DlgBilingRalan(null,false);
                                    dlgbil.TNoRw.setText(TNoRw.getText());  
                                    dlgbil.isCek();
                                    dlgbil.isRawat(); 
                                    dlgbil.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                                    dlgbil.setLocationRelativeTo(internalFrame1);
                                    dlgbil.setVisible(true);
                                    this.setCursor(Cursor.getDefaultCursor());
                                }
                            }else{   
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgBilingRalan dlgbil=new DlgBilingRalan(null,false);
                                dlgbil.TNoRw.setText(TNoRw.getText());  
                                dlgbil.isCek();
                                dlgbil.isRawat(); 
                                dlgbil.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                                dlgbil.setLocationRelativeTo(internalFrame1);
                                dlgbil.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(pscaripiutang!=null){
                                pscaripiutang.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }                            
                }                    
            }              
        }
}//GEN-LAST:event_MnBillingActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void ppGrafikPerpoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerpoliActionPerformed
       grafikperiksaperpoli kas=new grafikperiksaperpoli("Grafik Periksa Per Unit/Poli Tanggal "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"where tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerpoliActionPerformed

private void ppGrafikPerdokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerdokterActionPerformed
       grafikperiksaperdokter kas=new grafikperiksaperdokter("Grafik Periksa Per Dokter "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"where tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerdokterActionPerformed

private void ppGrafikPerJKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerJKActionPerformed
       grafikperiksaperjk kas=new grafikperiksaperjk("Grafik Periksa Per Jenis Kelamin "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"where tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerJKActionPerformed

private void ppGrafikPerPekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerPekerjaanActionPerformed
       grafikperiksaperpekerjaan kas=new grafikperiksaperpekerjaan("Grafik Periksa Per Pekerjaan "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"where tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerPekerjaanActionPerformed

private void ppGrafikPerAgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerAgamaActionPerformed
       grafikperiksaperagama kas=new grafikperiksaperagama("Grafik Periksa Per Agama "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"where tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerAgamaActionPerformed

private void ppGrafikPerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerTahunActionPerformed
       grafikperiksapertahun kas=new grafikperiksapertahun("Grafik Periksa Per Tahun "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"where tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerTahunActionPerformed

private void ppGrafikPerBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerBulanActionPerformed
       grafikperiksaperbulan kas=new grafikperiksaperbulan("Grafik Periksa Per Bulan "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"where tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerBulanActionPerformed

private void ppGrafikPerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerTanggalActionPerformed
       grafikperiksaperhari kas=new grafikperiksaperhari("Grafik Periksa Per Hari "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"where tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerTanggalActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");  
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }                
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void MnCetakSuratSakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakitActionPerformed
     if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
        TNoRw.requestFocus();
    }else if(TPasien.getText().trim().equals("")){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
        TCari.requestFocus();
    }else{
        if(tbPetugas.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            SuratSakit resume=new SuratSakit(null,false);
            resume.isCek();
            resume.emptTeks();
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
            resume.tampil();
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
}//GEN-LAST:event_MnCetakSuratSakitActionPerformed

private void BtnPrint3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint3ActionPerformed
                
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                        " reg_periksa inner join pasien inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                        " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and kelurahan.nm_kel='"+Kelurahan2.getText()+"'  and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"'   ", 
                        "pasien.alamat","Area");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                        " reg_periksa inner join pasien inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                         "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                         "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"'  ", 
                         "kelurahan.nm_kel","Kelurahan");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                         " reg_periksa inner join pasien inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab "+
                         "and pasien.kd_kec=kecamatan.kd_kec and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where kabupaten.nm_kab='"+Kabupaten2.getText()+"'  and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"'  ", 
                         "kecamatan.nm_kec","Kecamatan");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Kabupaten ]::",
                         " reg_periksa inner join pasien inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ", 
                          "kabupaten.nm_kab","Kabupaten");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            } 
}//GEN-LAST:event_BtnPrint3ActionPerformed

private void BtnKeluar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar3ActionPerformed
   DlgDemografi.dispose();
}//GEN-LAST:event_BtnKeluar3ActionPerformed

private void btnKelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelActionPerformed
        akses.setform("DlgIGD");
        pasien.kel.emptTeks();
        pasien.kel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kel.setLocationRelativeTo(internalFrame1);
        pasien.kel.setVisible(true);
     
}//GEN-LAST:event_btnKelActionPerformed

private void btnKecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKecActionPerformed
   akses.setform("DlgIGD");
   pasien.kec.emptTeks();
        pasien.kec.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kec.setLocationRelativeTo(internalFrame1);
        pasien.kec.setVisible(true);      
}//GEN-LAST:event_btnKecActionPerformed

private void btnKabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKabActionPerformed
        akses.setform("DlgIGD");
        pasien.kab.emptTeks();
        pasien.kab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kab.setLocationRelativeTo(internalFrame1);
        pasien.kab.setVisible(true);   
}//GEN-LAST:event_btnKabActionPerformed

private void BtnPrint4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                data.put("area","Area");
                data.put("namars",akses.getnamars());
                data.put("alamatrs",akses.getalamatrs());
                data.put("kotars",akses.getkabupatenrs());
                data.put("propinsirs",akses.getpropinsirs());
                data.put("kontakrs",akses.getkontakrs());
                data.put("emailrs",akses.getemailrs());
                data.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptDemografi.jasper","report","::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select  pasien.alamat as area,count(pasien.alamat) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' "+
                   "and kelurahan.nm_kel='"+Kelurahan2.getText()+"' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by pasien.alamat order by pasien.alamat",data);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                data.put("area","Kelurahan");
                data.put("namars",akses.getnamars());
                data.put("alamatrs",akses.getalamatrs());
                data.put("kotars",akses.getkabupatenrs());
                data.put("propinsirs",akses.getpropinsirs());
                data.put("kontakrs",akses.getkontakrs());
                data.put("emailrs",akses.getemailrs());
                data.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptDemografi.jasper","report","::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kelurahan.nm_kel as area,count(kelurahan.nm_kel) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by kelurahan.nm_kel order by kelurahan.nm_kel",data);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kecamatan Kabupaten "+Kabupaten2.getText());
                data.put("area","Kecamatan");
                data.put("namars",akses.getnamars());
                data.put("alamatrs",akses.getalamatrs());
                data.put("kotars",akses.getkabupatenrs());
                data.put("propinsirs",akses.getpropinsirs());
                data.put("kontakrs",akses.getkontakrs());
                data.put("emailrs",akses.getemailrs());
                data.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptDemografi.jasper","report","::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kecamatan.nm_kec as area,count(kecamatan.nm_kec) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and pasien.kd_kec=kecamatan.kd_kec where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by kecamatan.nm_kec order by kecamatan.nm_kec",data);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kabupaten");
                data.put("area","Kabupaten");
                data.put("namars",akses.getnamars());
                data.put("alamatrs",akses.getalamatrs());
                data.put("kotars",akses.getkabupatenrs());
                data.put("propinsirs",akses.getpropinsirs());
                data.put("kontakrs",akses.getkontakrs());
                data.put("emailrs",akses.getemailrs());
                data.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptDemografi.jasper","report","::[ Data Demografi Per Kabupaten ]::","select kabupaten.nm_kab as area,count(kabupaten.nm_kab) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by kabupaten.nm_kab order by kabupaten.nm_kab",data);
            }                        
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrint4ActionPerformed

private void ppGrafikDemografiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikDemografiActionPerformed
        DlgDemografi.setSize(550,180);
        DlgDemografi.setLocationRelativeTo(internalFrame1);
        DlgDemografi.setVisible(true);
}//GEN-LAST:event_ppGrafikDemografiActionPerformed

private void MnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukMasukActionPerformed
   if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbPetugas.requestFocus();
      }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgRujukMasuk rujukmasuk=new DlgRujukMasuk(null,false);
            rujukmasuk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            rujukmasuk.setLocationRelativeTo(internalFrame1);
            rujukmasuk.emptTeks();
            rujukmasuk.isCek();
            rujukmasuk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
            rujukmasuk.tampil();
            rujukmasuk.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
}//GEN-LAST:event_MnRujukMasukActionPerformed

private void kdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpnjKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",nmpnj,kdpnj.getText());      
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",nmpnj,kdpnj.getText());
            TAlmt.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",nmpnj,kdpnj.getText());
            AsalRujukan.requestFocus();      
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenjabActionPerformed(null);
        }
}//GEN-LAST:event_kdpnjKeyPressed

private void btnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjabActionPerformed
        akses.setform("DlgIGD");
        pasien.penjab.onCari();
        pasien.penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.penjab.setLocationRelativeTo(internalFrame1);
        pasien.penjab.setVisible(true);
}//GEN-LAST:event_btnPenjabActionPerformed

private void MnLaporanRekapKunjunganPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganPoliActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganPoli.php?tanggal1="+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"&tanggal2="+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());                       
        } 
}//GEN-LAST:event_MnLaporanRekapKunjunganPoliActionPerformed

private void MnLaporanRekapKunjunganDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganDokterActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganDokter.php?tanggal1="+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"&tanggal2="+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());                       
        } 
}//GEN-LAST:event_MnLaporanRekapKunjunganDokterActionPerformed

private void MnLaporanRekapJenisBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapJenisBayarActionPerformed
     if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganJenisBayar.php?tanggal1="+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"&tanggal2="+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());                       
        }
}//GEN-LAST:event_MnLaporanRekapJenisBayarActionPerformed

private void MnLaporanRekapRawatDaruratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapRawatDaruratActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanRawatDarurat.php?tanggal1="+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"&tanggal2="+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());                       
        }
}//GEN-LAST:event_MnLaporanRekapRawatDaruratActionPerformed

private void MnLaporanRekapKunjunganBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganBulananActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganBulanan.php?tanggal1="+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"&tanggal2="+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());                       
        }
}//GEN-LAST:event_MnLaporanRekapKunjunganBulananActionPerformed

private void MnLaporanRekapKunjunganBulananPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganBulananPoliActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganBulananPoli.php?tanggal1="+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"&tanggal2="+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());                       
        }
}//GEN-LAST:event_MnLaporanRekapKunjunganBulananPoliActionPerformed

    private void MnLaporanRekapPenyakitRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapPenyakitRalanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRalan ktginventaris=new DlgFrekuensiPenyakitRalan(null,false);
        ktginventaris.isCek();
        ktginventaris.setSize(this.getWidth()-20,this.getHeight()-20);
        ktginventaris.setLocationRelativeTo(this);
        ktginventaris.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnLaporanRekapPenyakitRalanActionPerformed

    private void MnNoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNoResepActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgResepObat resep=new DlgResepObat(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.emptTeks();
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),CmbJam.getSelectedItem().toString(),CmbMenit.getSelectedItem().toString(),CmbDetik.getSelectedItem().toString(),"ralan");
                resep.tampil();
                resep.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnNoResepActionPerformed

    private void Kabupaten2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kabupaten2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKabActionPerformed(null);
        }else{Valid.pindah(evt,BtnKeluar3,Kecamatan2);}
    }//GEN-LAST:event_Kabupaten2KeyPressed

    private void Kecamatan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kecamatan2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKecActionPerformed(null);
        }else{Valid.pindah(evt,Kabupaten2,Kelurahan2);}
    }//GEN-LAST:event_Kecamatan2KeyPressed

    private void Kelurahan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kelurahan2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKelActionPerformed(null);
        }else{Valid.pindah(evt,Kecamatan2,BtnPrint4);}
    }//GEN-LAST:event_Kelurahan2KeyPressed

    private void BtnPrint4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint4KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrint4ActionPerformed(null);
        }else{Valid.pindah(evt,Kelurahan2,BtnPrint3);}
    }//GEN-LAST:event_BtnPrint4KeyPressed

    private void BtnPrint3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrint3ActionPerformed(null);
        }else{Valid.pindah(evt,BtnPrint4,BtnKeluar3);}
    }//GEN-LAST:event_BtnPrint3KeyPressed

    private void BtnKeluar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluar3ActionPerformed(null);
        }else{Valid.pindah(evt,BtnPrint3,Kabupaten2);}
    }//GEN-LAST:event_BtnKeluar3KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if(tbPetugas.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPeriksaRadiologi dlgro=new DlgPeriksaRadiologi(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");  
                dlgro.tampil();
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void MnBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeActionPerformed
        if(!TPasien.getText().isEmpty()){
                Map<String, Object> data = new HashMap<>();
                data.put("nama",TPasien.getText());
                data.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRM.getText()));
                data.put("norm",TNoRM.getText());
                data.put("parameter","%"+TCari.getText().trim()+"%");     
                data.put("namars",akses.getnamars());
                data.put("alamatrs",akses.getalamatrs());
                data.put("kotars",akses.getkabupatenrs());
                data.put("propinsirs",akses.getpropinsirs());
                data.put("kontakrs",akses.getkontakrs());
                data.put("emailrs",akses.getemailrs());   
                data.put("no_rawat",TNoRw.getText());   
                Valid.MyReport("rptBarcodeRawat.jasper","report","::[ Barcode No.Rawat ]::",data); 
        }
    }//GEN-LAST:event_MnBarcodeActionPerformed

    private void MnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ralan");
                resep.panelDiagnosa1.tampil();
                resep.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnDiagnosaActionPerformed

    private void AsalRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kdpnj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenjabActionPerformed(null);
        }
    }//GEN-LAST:event_AsalRujukanKeyPressed

    private void btnPenjab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjab1ActionPerformed
        akses.setform("DlgReg");
        rujukmasuk.tampil2();
        rujukmasuk.TCariPerujuk.requestFocus();
        rujukmasuk.WindowPerujuk.setSize(this.getWidth()-20,this.getHeight()-20);
        rujukmasuk.WindowPerujuk.setLocationRelativeTo(internalFrame1);
        rujukmasuk.WindowPerujuk.setVisible(true);
    }//GEN-LAST:event_btnPenjab1ActionPerformed

    private void MnCetakSuratSakit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakit2ActionPerformed
        DlgSakit2.setSize(550,151);
        DlgSakit2.setLocationRelativeTo(internalFrame1);
        DlgSakit2.setVisible(true);
    }//GEN-LAST:event_MnCetakSuratSakit2ActionPerformed

    private void MnCetakRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakRegisterActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBuktiRegister.jasper","report","::[ Bukti Register ]::",
                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,poliklinik.nm_poli,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakRegisterActionPerformed

    private void BtnPrint5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint5ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("nomersurat",NomorSurat.getText());
            param.put("dokterpj",CrDokter3.getText());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptSuratSakit2.jasper","report","::[ Surat Sakit ]::",
                "select reg_periksa.no_rkm_medis,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk," +
                " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat" +
                " from reg_periksa inner join pasien inner join dokter" +
                " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrint5ActionPerformed

    private void BtnKeluar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar4ActionPerformed
        DlgSakit2.dispose();
    }//GEN-LAST:event_BtnKeluar4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        akses.setform("DlgReg");
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.setNoRm(TNoRw.getText(),TNoRM.getText()+", "+TPasien.getText(),"Ralan");
            dlgro.setVisible(true);
        }
    }//GEN-LAST:event_MnOperasiActionPerformed

    private void MnGelang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",DTPReg.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            param.put("parameter",TNoRM.getText());
            Valid.MyReport("rptBarcodeRM6.jasper","report","::[ Gelang Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang1ActionPerformed

    private void MnGelang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",DTPReg.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM7.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang2ActionPerformed

    private void MnCheckListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckListActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("bayar",nmpnj.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptCheckList.jasper","report","::[ Check List ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCheckListActionPerformed

    private void MnCheckList1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList1ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("bayar",nmpnj.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptCheckList2.jasper","report","::[ Check List ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCheckList1ActionPerformed

    private void MnCheckList2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList2ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("bayar",nmpnj.getText());
            param.put("dokter",TDokter.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("alamat",TAlmt.getText());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptCheckList4.jasper","report","::[ Check List ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCheckList2ActionPerformed

    private void MnCheckList3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList3ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("dokter",TDokter.getText());
            param.put("bayar",nmpnj.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("alamat",TAlmt.getText());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptCheckList3.jasper","report","::[ Check List ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCheckList3ActionPerformed

    private void MnCheckList4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(!TPasien.getText().equals("")){
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("dokter",TDokter.getText());
            param.put("no_rawat",TNoRw.getText());
            param.put("bayar",nmpnj.getText());
            param.put("penjab",TPngJwb.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptLembarPeriksa.jasper","report","::[ Lembar Periksa ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList4ActionPerformed

    private void MnCheckList5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList5ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(!TPasien.getText().equals("")){
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("dokter",TDokter.getText());
            param.put("no_rawat",TNoRw.getText());
            param.put("bayar",nmpnj.getText());
            param.put("penjab",TPngJwb.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptLembarPeriksa2.jasper","report","::[ Lembar Periksa ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList5ActionPerformed

    private void MnCheckList6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList6ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(!TPasien.getText().equals("")){
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TNoRM.getText()));
            param.put("dokter",TDokter.getText());
            param.put("no_rawat",TNoRw.getText());
            param.put("bayar",nmpnj.getText());
            param.put("penjab",TPngJwb.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptLembarPeriksa3.jasper","report","::[ Lembar Periksa]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList6ActionPerformed

    private void MnCheckList7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList7ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(!TPasien.getText().equals("")){
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TNoRM.getText()));
            param.put("dokter",TDokter.getText());
            param.put("no_rawat",TNoRw.getText());
            param.put("bayar",nmpnj.getText());
            param.put("penjab",TPngJwb.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptLembarPeriksa4.jasper","report","::[ Lembar Periksa]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList7ActionPerformed

    private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from operasi where no_rawat='"+TNoRw.getText()+"'");
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

    private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='"+TNoRw.getText()+"'");
        }
    }//GEN-LAST:event_MnHapusObatOperasiActionPerformed

    private void MnGelang3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang3ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",DTPReg.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM8.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang3ActionPerformed

    private void MnSEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSEPActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            akses.setform("DlgIGD");
            BPJSDataSEP dlgki=new BPJSDataSEP(null,false);
            dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText(),DTPReg.getDate(),"2. Ralan","IGDK","Unit IGD/UGD");
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSEPActionPerformed

    private void MnLembarCasemixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCasemixActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLembarCasemix.jasper","report","::[ Surat Jaminan & Bukti Pelayanan Rawat Jalan ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"+
                "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli," +
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg," +
                "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir " +
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarCasemixActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan=new DlgCatatan(null,false);
            catatan.setNoRm(TNoRM.getText());
            catatan.setSize(720,330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void MnSPBKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSPBKActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            variabel="Atas Persetujuan Dokter";
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                variabel="MRS";
            }
            if(Sequel.cariInteger("select count(rujuk.no_rawat) from rujuk where rujuk.no_rawat=?",TNoRw.getText())>0){
                variabel="Dirujuk";
            }
            param.put("carapulang",variabel);
            variabel=Sequel.cariIsi("select bridging_sep.tujuankunjungan from bridging_sep where bridging_sep.no_rawat=?",TNoRw.getText());
            param.put("tujuankunjungan","- "+(variabel.equals("0")?"Konsultasi dokter(pertama)":"Kunjungan Kontrol(ulangan)"));
            variabel=Sequel.cariIsi("select bridging_sep.flagprosedur from bridging_sep where bridging_sep.no_rawat=?",TNoRw.getText());
            param.put("flagprosedur",variabel.replaceAll("0","- Prosedur Tidak Berkelanjutan").replaceAll("1","- Prosedur dan Terapi Berkelanjutan"));
            variabel=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdDokter.getText());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+TDokter.getText()+"\nID "+(variabel.equals("")?KdDokter.getText():variabel)+"\n"+DTPReg.getSelectedItem());  
            param.put("fingerpasien",Sequel.cariIsi("select sha1(sidikjaripasien.sidikjari) from sidikjaripasien where sidikjaripasien.no_rkm_medis=?",TNoRM.getText()));  
            
            try {
                ps=koneksi.prepareStatement(
                    "select resume_pasien.keluhan_utama,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama,resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,"+
                    "resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2,resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,"+
                    "resume_pasien.kd_diagnosa_sekunder4,resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder "+
                    "from resume_pasien where resume_pasien.no_rawat=?");
                try {
                    ps.setString(1,TNoRw.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                       param.put("anamnesa",rs.getString("keluhan_utama")); 
                       param.put("diagnosa1",rs.getString("diagnosa_utama")); 
                       param.put("icdx1",rs.getString("kd_diagnosa_utama")); 
                       param.put("diagnosa2",rs.getString("diagnosa_sekunder")); 
                       param.put("icdx2",rs.getString("kd_diagnosa_sekunder")); 
                       param.put("diagnosa3",rs.getString("diagnosa_sekunder2")); 
                       param.put("icdx3",rs.getString("kd_diagnosa_sekunder2")); 
                       param.put("diagnosa4",rs.getString("diagnosa_sekunder3")); 
                       param.put("icdx4",rs.getString("kd_diagnosa_sekunder3")); 
                       param.put("prosedur1",rs.getString("prosedur_utama")); 
                       param.put("icd91",rs.getString("kd_prosedur_utama")); 
                       param.put("prosedur2",rs.getString("prosedur_sekunder")); 
                       param.put("icd92",rs.getString("kd_prosedur_sekunder"));
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
            
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptSBPK.jasper","report","::[ Surat Bukti Pelayanan Kesehatan ]::",
                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"+
                   "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli," +
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg," +
                   "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir " +
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSPBKActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void MnResepDOkterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepDOkterActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),new Date(),CmbJam.getSelectedItem().toString(),CmbMenit.getSelectedItem().toString(),CmbDetik.getSelectedItem().toString(),
                    KdDokter.getText(),TDokter.getText(),"ralan");
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnResepDOkterActionPerformed

    private void ppBerkasBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            Sequel.menyimpan("mutasi_berkas","'"+TNoRw.getText()+"','Sudah Diterima',now(),now(),'0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00'","status='Sudah Diterima',diterima=now()","no_rawat='"+TNoRw.getText()+"'");
            Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Berkas Diterima'");
            if(tbPetugas.getSelectedRow()>-1){
                tabMode.setValueAt("Berkas Diterima",tbPetugas.getSelectedRow(),18);
            }
        }
    }//GEN-LAST:event_ppBerkasBtnPrintActionPerformed

    private void MnSudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Sudah'");
                if(tbPetugas.getSelectedRow()>-1){
                    tabMode.setValueAt("Sudah",tbPetugas.getSelectedRow(),18);
                }
            }
        }
    }//GEN-LAST:event_MnSudahActionPerformed

    private void MnBelumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Belum'");
                if(tbPetugas.getSelectedRow()>-1){
                    tabMode.setValueAt("Belum",tbPetugas.getSelectedRow(),18);
                }
            }

        }
    }//GEN-LAST:event_MnBelumActionPerformed

    private void MnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBatalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Batal',biaya_reg='0'");
                if(tbPetugas.getSelectedRow()>-1){
                    tabMode.setValueAt("Batal",tbPetugas.getSelectedRow(),18);
                }
            }
        }
    }//GEN-LAST:event_MnBatalActionPerformed

    private void MnDirujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDirujukActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Dirujuk'");
                MnRujukActionPerformed(evt);
                if(tbPetugas.getSelectedRow()>-1){
                    tabMode.setValueAt("Dirujuk",tbPetugas.getSelectedRow(),18);
                }
            }
        }
    }//GEN-LAST:event_MnDirujukActionPerformed

    private void MnDIrawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDIrawatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Dirawat'");
                MnKamarInapActionPerformed(evt);
                if(tbPetugas.getSelectedRow()>-1){
                    tabMode.setValueAt("Dirawat",tbPetugas.getSelectedRow(),18);
                }
            }
        }
    }//GEN-LAST:event_MnDIrawatActionPerformed

    private void MnMeninggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnMeninggalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Meninggal'");
                DlgPasienMati dlgPasienMati=new DlgPasienMati(null,false);
                dlgPasienMati.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgPasienMati.setLocationRelativeTo(internalFrame1);
                dlgPasienMati.emptTeks();
                dlgPasienMati.setNoRm(TNoRM.getText(),TPasien.getText());
                dlgPasienMati.isCek();
                dlgPasienMati.setVisible(true);
                if(tbPetugas.getSelectedRow()>-1){
                    tabMode.setValueAt("Meninggal",tbPetugas.getSelectedRow(),18);
                }
            }

        }
    }//GEN-LAST:event_MnMeninggalActionPerformed

    private void LabelCatatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelCatatanMouseClicked
        DlgCatatan.dispose();
    }//GEN-LAST:event_LabelCatatanMouseClicked

    private void internalFrame6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_internalFrame6MouseClicked
        DlgCatatan.dispose();
    }//GEN-LAST:event_internalFrame6MouseClicked

    private void MnLabelTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTrackerActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptLabelTracker.jasper",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTrackerActionPerformed

    private void MnLabelTracker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptLabelTracker2.jasper",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker1ActionPerformed

    private void MnLabelTracker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker2ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLabelTracker3.jasper","report","::[ Label Tracker ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker2ActionPerformed

    private void MnLabelTracker3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker3ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLabelTracker4.jasper","report","::[ Label Tracker ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker3ActionPerformed

    private void MnBarcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode1ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasien.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRM.getText()));
            param.put("norm",TNoRM.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            Valid.MyReportqry("rptBarcodeRawat.jasper","report","::[ Barcode No.Rawat ]::",
                "select reg_periksa.no_rawat from reg_periksa where no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode1ActionPerformed

    private void MnGelang4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang4ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",DTPReg.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM10.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang4ActionPerformed

    private void MnGelang5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang5ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",DTPReg.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM14.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, DATE_FORMAT(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang5ActionPerformed

    private void MnGelang6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang6ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("tanggal",DTPReg.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM16.jasper","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang6ActionPerformed

    private void MnLembarCasemix1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCasemix1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLembarCasemix2.jasper","report","::[ Surat Jaminan & Bukti Pelayanan Rawat Jalan ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"+
                "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli," +
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg," +
                "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir " +
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarCasemix1ActionPerformed

    private void MnJKRAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJKRAActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("perujuk",Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?",TNoRw.getText()));
            param.put("no_rawat",TNoRw.getText());
            param.put("petugas",Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",akses.getkode()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptJkra.jasper",param,"::[ SURAT JAMINAN KESEHATAN NASIONAL (JKRA) RAWAT JALAN ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnJKRAActionPerformed

    private void MnBarcode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode2ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasien.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRM.getText()));
            param.put("norm",TNoRM.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            Valid.MyReportqry("rptBarcodeRawat2.jasper","report","::[ Barcode No.Rawat ]::",
                "select reg_periksa.no_rawat from reg_periksa where no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode2ActionPerformed

    private void MnSPBK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSPBK1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            param.put("logo2",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptSBPK2.jasper","report","::[ Surat Bukti Pelayanan Kesehatan ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"+
                "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,"+
                "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,"+
                "pasien.no_tlp,poliklinik.nm_poli,reg_periksa.p_jawab,"+
                "reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg," +
                "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,"+
                "pasien.tgl_lahir from reg_periksa inner join dokter inner join "+
                "pasien inner join poliklinik inner join penjab on "+
                "reg_periksa.kd_dokter=dokter.kd_dokter and "+
                "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                "reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSPBK1ActionPerformed

    private void MnLembarRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarRalanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptRM11.jasper","report","::[ Lembar Rawat Jalan ]::",
                "SELECT reg_periksa.tgl_registrasi, reg_periksa.jam_reg, "+
                "poliklinik.nm_poli, pasien.no_rkm_medis, pasien.nm_pasien, "+
                "pasien.no_ktp, pasien.jk, pasien.tmp_lahir, pasien.tgl_lahir,"+
                "pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel"+
                ",', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) AS alamat,"+
                "pasien.gol_darah,pasien.pekerjaan,pasien.stts_nikah,"+
                "pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd,pasien.keluarga,pasien.namakeluarga,"+
                "penjab.png_jawab,pasien.pekerjaanpj,concat(pasien.alamatpj,"+
                "', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',"+
                "pasien.kabupatenpj) AS alamatpj FROM pasien INNER JOIN "+
                "kelurahan INNER JOIN kecamatan INNER JOIN kabupaten "+
                "INNER JOIN penjab ON pasien.kd_kel = kelurahan.kd_kel "+
                "AND pasien.kd_kec = kecamatan.kd_kec AND pasien.kd_kab = kabupaten.kd_kab "+
                "INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "+
                "AND reg_periksa.kd_pj = penjab.kd_pj INNER JOIN poliklinik "+
                "ON poliklinik.kd_poli = reg_periksa.kd_poli WHERE "+
                "reg_periksa.no_rawat ='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarRalanActionPerformed

    private void MnBarcodeRM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM9ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBarcodeRM18.jasper","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM9ActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            try {
                if(akses.gethapus_berkas_digital_perawatan()==true){
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }else{
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }            
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void MnSJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSJPActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            akses.setform("DlgIGD");
            InhealthDataSJP dlgki=new InhealthDataSJP(null,false);
            dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText(),DTPReg.getDate(),"1 RJTP RAWAT JALAN TINGKAT PERTAMA","IGDK","Unit IGD/UGD");
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSJPActionPerformed

    private void ppIKPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppIKPBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgDataInsidenKeselamatan aplikasi=new DlgDataInsidenKeselamatan(null,false);
            aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.isCek();
            aplikasi.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Unit Gawat Darurat");
            aplikasi.tampil();
            aplikasi.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppIKPBtnPrintActionPerformed

    private void MnPoliInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPoliInternalActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgRujukanPoliInternal dlgrjk=new DlgRujukanPoliInternal(null,false);
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.isCek();
            dlgrjk.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrjk.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPoliInternalActionPerformed

    private void MnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadwalOperasiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgBookingOperasi form=new DlgBookingOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),"IGD","Ralan");
                form.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnJadwalOperasiActionPerformed

    private void SuratKontrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuratKontrolActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                SuratKontrol form=new SuratKontrol(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);      
                form.emptTeks();      
                form.setNoRm(TNoRM.getText(),TPasien.getText()); 
                form.setVisible(true);
            }                
        }
    }//GEN-LAST:event_SuratKontrolActionPerformed

    private void MnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanLabActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
                dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPermintaanLabActionPerformed

    private void MnPermintaanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRadiologiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
                dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPermintaanRadiologiActionPerformed

    private void MnPulangPaksaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPulangPaksaBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Pulang Paksa'");
                if(tbPetugas.getSelectedRow()>-1){
                    tabMode.setValueAt("Pulang Paksa",tbPetugas.getSelectedRow(),18);
                }
            }
        }
    }//GEN-LAST:event_MnPulangPaksaBtnPrintActionPerformed

    private void MnStatusBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusBaruActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"status_poli='Baru'");
            if(tbPetugas.getSelectedRow()>-1){
                tabMode.setValueAt("Baru",tbPetugas.getSelectedRow(),16);
            }
        }
    }//GEN-LAST:event_MnStatusBaruActionPerformed

    private void MnStatusLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusLamaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"status_poli='Lama'");
            if(tbPetugas.getSelectedRow()>-1){
                tabMode.setValueAt("Lama",tbPetugas.getSelectedRow(),18);
            }
        }
    }//GEN-LAST:event_MnStatusLamaActionPerformed

    private void MnBillingParsialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingParsialActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                try {
                    pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                    try {
                        pscaripiutang.setString(1,TNoRM.getText());
                        rs=pscaripiutang.executeQuery();
                        if(rs.next()){
                            i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                            if(i==JOptionPane.YES_OPTION){
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                                piutang.setNoRm(TNoRM.getText(),rs.getDate(1));
                                piutang.tampil();
                                piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                piutang.setLocationRelativeTo(internalFrame1);
                                piutang.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }else{
                                billingprasial();
                            }
                        }else{
                            billingprasial();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }finally{
                        if( rs != null){
                            rs.close();
                        }
                        if( pscaripiutang != null){
                            pscaripiutang.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }//GEN-LAST:event_MnBillingParsialActionPerformed

    private void tbPetugasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }              
        }
    }//GEN-LAST:event_tbPetugasKeyReleased

    private void MnBlangkoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBlangkoResepActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBlankoResep.jasper","report","::[ Bukti Register ]::",
                "select pasien.no_ktp,pasien.no_peserta,reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBlangkoResepActionPerformed

    private void MnRujukSisruteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukSisruteActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            akses.setform("DlgIGD");
            SisruteRujukanKeluar dlgki=new SisruteRujukanKeluar(null,false);
            dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setPasien(TNoRw.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnRujukSisruteActionPerformed

    private void MnCetakRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakRegister1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBuktiRegister8.jasper","report","::[ Bukti Register ]::",
                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,poliklinik.nm_poli,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.no_ktp,pasien.tgl_lahir "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakRegister1ActionPerformed

    private void MnSuratJaminanPelayananIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratJaminanPelayananIGDActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("SuratJaminanPelayananIGD.jasper","report","::[ Surat Jaminan Pelayanan ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg, reg_periksa.kd_dokter,"+
                "dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,poliklinik.nm_poli," +
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg," +
                "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir " +
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                "reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSuratJaminanPelayananIGDActionPerformed

    private void MnLembarKeluarMasuk2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarKeluarMasuk2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("kiriman",AsalRujukan.getText());
            param.put("doktermasuk",TDokter.getText());
            param.put("dirawatke",Sequel.cariIsi("select count(no_rkm_medis)+1 from reg_periksa where no_rkm_medis=?",TNoRM.getText()));
            param.put("terakhirdirawat",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi,'%d/%m/%Y') from reg_periksa where no_rkm_medis=? order by tgl_registrasi desc limit 1",TNoRM.getText()));
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLembarKeluarMasuk3.jasper","report","::[ Ringkasan Masuk Keluar ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj, suku_bangsa.nama_suku_bangsa,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj,', ',pasien.propinsipj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten inner join suku_bangsa "+
                "inner join penjab inner join propinsi on pasien.kd_prop=propinsi.kd_prop and pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and suku_bangsa.id=pasien.suku_bangsa where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarKeluarMasuk2ActionPerformed

    private void MnDataTriaseIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataTriaseIGDActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMTriaseIGD form=new RMTriaseIGD(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnDataTriaseIGDActionPerformed

    private void ppResumeBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppResumeBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataResumePasien resume=new RMDataResumePasien(null,false);
                resume.isCek();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppResumeBtnPrintActionPerformed

    private void ppPerawatanCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPerawatanCoronaBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            INACBGPerawatanCorona form=new INACBGPerawatanCorona(null,false);
            form.emptTeks();
            form.setPasien(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
            form.tampil();
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppPerawatanCoronaBtnPrintActionPerformed

    private void ppPasienCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPasienCoronaBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            CoronaPasien form=new CoronaPasien(null,false);
            form.setPasien(TNoRM.getText());
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppPasienCoronaBtnPrintActionPerformed

    private void ppDeteksiDIniCoronaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDeteksiDIniCoronaBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDeteksiDiniCorona form=new RMDeteksiDiniCorona(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppDeteksiDIniCoronaBtnPrintActionPerformed

    private void MnCetakSuratHamilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratHamilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratTidakHamil resume=new SuratTidakHamil(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratHamilActionPerformed

    private void MnCetakBebasNarkobaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakBebasNarkobaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratBebasNarkoba resume=new SuratBebasNarkoba(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),KdDokter.getText(),TDokter.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakBebasNarkobaActionPerformed

    private void MnPCareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPCareActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            akses.setform("DlgReg");
            PCareDataPendaftaran dlgki=new PCareDataPendaftaran(null,false);
            dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPCareActionPerformed

    private void MnBarcode3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode3ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptBarcodeRawat3.jasper",param,"::[ Barcode No.RM ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode3ActionPerformed

    private void MnCetakSuratCovidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratCovidActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratKeteranganCovid resume=new SuratKeteranganCovid(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratCovidActionPerformed

    private void MnHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHemodialisaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMHemodialisa resume=new RMHemodialisa(null,false);
            resume.emptTeks();
            resume.setNoRm(TNoRw.getText());
            resume.isCek();
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnHemodialisaActionPerformed

    private void MnTeridentifikasiTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTeridentifikasiTBActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgDataTB resep=new DlgDataTB(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.emptTeks();
                resep.setNoRM(TNoRw.getText());
                resep.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnTeridentifikasiTBActionPerformed

    private void MnCetakSuratCutiHamilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratCutiHamilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratCutiHamil resume=new SuratCutiHamil(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratCutiHamilActionPerformed

    private void MnPeriksaLabPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabPAActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeriksaLaboratoriumPA dlgro=new DlgPeriksaLaboratoriumPA(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");  
                dlgro.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnPeriksaLabPAActionPerformed

    private void MnPermintaanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRanapActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRanap form=new DlgPermintaanRanap(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TDokter.getText(),TPngJwb.getText(),"UGD/IGD",Sequel.cariIsi("select pasien.no_tlp from pasien where pasien.no_rkm_medis=?",TNoRM.getText()));
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPermintaanRanapActionPerformed

    private void ppSuratKontrolBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratKontrolBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                try {
                    ps=koneksi.prepareStatement("select bridging_sep.no_sep,bridging_sep.no_kartu,bridging_sep.tanggal_lahir,bridging_sep.jkel,bridging_sep.nmdiagnosaawal from bridging_sep where bridging_sep.no_rawat=?");
                    try {
                        ps.setString(1,TNoRw.getText());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSSuratKontrol form=new BPJSSuratKontrol(null,false);
                            form.setNoRm(TNoRw.getText(),rs.getString("no_sep"),rs.getString("no_kartu"),TNoRM.getText(),TPasien.getText(),rs.getString("tanggal_lahir"),rs.getString("jkel"),rs.getString("nmdiagnosaawal"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
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
    }//GEN-LAST:event_ppSuratKontrolBtnPrintActionPerformed

    private void ppSuratPRIBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratPRIBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                try {
                    ps=koneksi.prepareStatement("select pasien.no_peserta,pasien.tgl_lahir,pasien.jk from pasien where pasien.no_rkm_medis=?");
                    try {
                        ps.setString(1,TNoRM.getText());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(rs.getString("no_peserta").length()<13){
                                JOptionPane.showMessageDialog(null,"Kartu BPJS Pasien tidak valid, silahkan hubungi bagian terkait..!!");
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSSPRI form=new BPJSSPRI(null,false);
                                form.setNoRm(TNoRw.getText(),rs.getString("no_peserta"),TNoRM.getText(),TPasien.getText(),rs.getString("tgl_lahir"),rs.getString("jk"),"-");
                                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
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
        }
    }//GEN-LAST:event_ppSuratPRIBtnPrintActionPerformed

    private void MnCetakSuratSakitPihak2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakitPihak2ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratSakitPihak2 resume=new SuratSakitPihak2(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TAlmt.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratSakitPihak2ActionPerformed

    private void MnCetakSuratBebasTBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratBebasTBCActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratKeteranganBebasTBC resume=new SuratKeteranganBebasTBC(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),KdDokter.getText(),TDokter.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratBebasTBCActionPerformed

    private void MnSuratButaWarnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratButaWarnaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratButaWarna resume=new SuratButaWarna(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSuratButaWarnaActionPerformed

    private void MnSuratBebasTatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratBebasTatoActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratBebasTato resume=new SuratBebasTato(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSuratBebasTatoActionPerformed

    private void MnSuratKewaspadaanKesehatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratKewaspadaanKesehatanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratKewaspadaanKesehatan resume=new SuratKewaspadaanKesehatan(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSuratKewaspadaanKesehatanActionPerformed

    private void MnPeniliaianAwalMedisIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalMedisIGDActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisIGD form=new RMPenilaianAwalMedisIGD(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalMedisIGDActionPerformed

    private void MnPenilaianFisioterapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianFisioterapiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianFisioterapi form=new RMPenilaianFisioterapi(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianFisioterapiActionPerformed

    private void ppProgramPRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppProgramPRBActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                try {
                    ps=koneksi.prepareStatement("select bridging_sep.no_sep,bridging_sep.no_kartu,bridging_sep.kddpjp,bridging_sep.nmdpdjp,"+
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,pasien.email "+
                        "from bridging_sep inner join pasien on bridging_sep.nomr=pasien.no_rkm_medis "+
                        "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where bridging_sep.no_rawat=?");
                    try {
                        ps.setString(1,TNoRw.getText());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSProgramPRB form=new BPJSProgramPRB(null,false);
                            form.setNoRm(TNoRw.getText(),rs.getString("no_sep"),rs.getString("no_kartu"),TNoRM.getText(),TPasien.getText(),rs.getString("alamat"),rs.getString("email"),rs.getString("kddpjp"),rs.getString("nmdpdjp"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
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
    }//GEN-LAST:event_ppProgramPRBActionPerformed

    private void ppSuplesiJasaRaharjaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuplesiJasaRaharjaBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                try {
                    ps=koneksi.prepareStatement("select no_kartu,nama_pasien,tglsep from bridging_sep where no_rawat=?");
                    try {
                        ps.setString(1,TNoRw.getText());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekSuplesiJasaRaharja form=new BPJSCekSuplesiJasaRaharja(null,false);
                            form.setRM(rs.getString("no_kartu"),rs.getString("nama_pasien"),rs.getDate("tglsep"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
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
    }//GEN-LAST:event_ppSuplesiJasaRaharjaBtnPrintActionPerformed

    private void ppDataIndukKecelakaanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDataIndukKecelakaanBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                try {
                    ps=koneksi.prepareStatement("select no_kartu,nama_pasien from bridging_sep where no_rawat=?");
                    try {
                        ps.setString(1,TNoRw.getText());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSCekDataIndukKecelakaan form=new BPJSCekDataIndukKecelakaan(null,false);
                            form.setRM(rs.getString("no_kartu"),rs.getString("nama_pasien"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
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
    }//GEN-LAST:event_ppDataIndukKecelakaanBtnPrintActionPerformed

    private void MnPenilaianAwalKeperawatanIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianAwalKeperawatanIGDActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalKeperawatanIGD form=new RMPenilaianAwalKeperawatanIGD(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianAwalKeperawatanIGDActionPerformed

    private void MnCetakRegister2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakRegister2ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("norawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptBuktiRegister3.jasper",param,"::[ Bukti Register ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakRegister2ActionPerformed

    private void MnCatatanObservasiIGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanObservasiIGDActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataCatatanObservasiIGD form=new RMDataCatatanObservasiIGD(null,false);
                form.isCek();
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCatatanObservasiIGDActionPerformed

    private void MnCopyResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCopyResepActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgCopyResep daftar=new DlgCopyResep(null,false);
                    daftar.isCek();
                    daftar.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    daftar.setLocationRelativeTo(internalFrame1);
                    daftar.setVisible(true);
                    daftar.setRM(TNoRw.getText(),TNoRM.getText(),KdDokter.getText(),kdpnj.getText(),"ralan");
                    daftar.tampil();
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnCopyResepActionPerformed

    private void MnPenilaianPsikologActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPsikologActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPsikologi form=new RMPenilaianPsikologi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianPsikologActionPerformed

    private void MnPersetujuanPenolakanTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanPenolakanTindakanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratPersetujuanPenolakanTindakan resume=new SuratPersetujuanPenolakanTindakan(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setVisible(true);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                resume.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPersetujuanPenolakanTindakanActionPerformed

    private void MnPemantauanPEWSAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemantauanPEWSAnakActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPemantauanPEWS form=new RMPemantauanPEWS(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                form.emptTeks();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPemantauanPEWSAnakActionPerformed

    private void MnPeriksaLabMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabMBActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPeriksaLaboratoriumMB dlgro=new DlgPeriksaLaboratoriumMB(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.setVisible(true);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");  
                this.setCursor(Cursor.getDefaultCursor());
            }                
        }
    }//GEN-LAST:event_MnPeriksaLabMBActionPerformed

    private void MnPenilaianPreOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPreOpActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPreOperasi form=new RMPenilaianPreOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianPreOpActionPerformed

    private void MnPenilaianPreAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPreAnestesiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPreAnastesi form=new RMPenilaianPreAnastesi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.emptTeks();
                form.setVisible(true);
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianPreAnestesiActionPerformed

    private void MnPulangAtasPermintaanSendiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPulangAtasPermintaanSendiriActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratPulangAtasPermintaanSendiri resume=new SuratPulangAtasPermintaanSendiri(null,false);
                resume.isCek();
                resume.emptTeks();
                resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                resume.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                resume.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPulangAtasPermintaanSendiriActionPerformed

    private void MnPenilaianRisikoJatuhDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianRisikoJatuhDewasaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianLanjutanRisikoJatuhDewasa form=new RMPenilaianLanjutanRisikoJatuhDewasa(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianRisikoJatuhDewasaActionPerformed

    private void MnPenilaianRisikoJatuhAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianRisikoJatuhAnakActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianLanjutanRisikoJatuhAnak form=new RMPenilaianLanjutanRisikoJatuhAnak(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianRisikoJatuhAnakActionPerformed

    private void MnPenilaianTambahanGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianTambahanGeriatriActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianTambahanGeriatri form=new RMPenilaianTambahanGeriatri(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianTambahanGeriatriActionPerformed

    private void ppSkriningNutrisiDewasaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSkriningNutrisiDewasaBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningNutrisiDewasa form=new RMSkriningNutrisiDewasa(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppSkriningNutrisiDewasaBtnPrintActionPerformed

    private void ppSkriningNutrisiLansiaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSkriningNutrisiLansiaBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningNutrisiLansia form=new RMSkriningNutrisiLansia(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppSkriningNutrisiLansiaBtnPrintActionPerformed

    private void ppSkriningNutrisiAnakBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSkriningNutrisiAnakBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningNutrisiAnak form=new RMSkriningNutrisiAnak(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppSkriningNutrisiAnakBtnPrintActionPerformed

    private void ppSkriningGiziBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSkriningGiziBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataSkriningGiziLanjut form=new RMDataSkriningGiziLanjut(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppSkriningGiziBtnPrintActionPerformed

    private void ppAsuhanGiziBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppAsuhanGiziBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataAsuhanGizi form=new RMDataAsuhanGizi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppAsuhanGiziBtnPrintActionPerformed

    private void ppMonitoringAsuhanGiziBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppMonitoringAsuhanGiziBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataMonitoringAsuhanGizi form=new RMDataMonitoringAsuhanGizi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppMonitoringAsuhanGiziBtnPrintActionPerformed

    private void MnPernyataanPasienUmumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPernyataanPasienUmumActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratPernyataanPasienUmum form=new SuratPernyataanPasienUmum(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPernyataanPasienUmumActionPerformed

    private void MnKonselingFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKonselingFarmasiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMKonselingFarmasi form=new RMKonselingFarmasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnKonselingFarmasiActionPerformed

    private void MnPermintaanInformasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanInformasiObatActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanPelayananInformasiObat form=new DlgPermintaanPelayananInformasiObat(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPermintaanInformasiObatActionPerformed

    private void MnPersetujuanUmumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanUmumActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratPersetujuanUmum form=new SuratPersetujuanUmum(null,false);
                form.isCek();
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPersetujuanUmumActionPerformed

    private void MnTransferAntarRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTransferAntarRuangActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMTransferPasienAntarRuang form=new RMTransferPasienAntarRuang(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnTransferAntarRuangActionPerformed

    private void MnCatatanCekGDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanCekGDSActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataCatatanCekGDS form=new RMDataCatatanCekGDS(null,false);
                form.isCek();
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCatatanCekGDSActionPerformed

    private void MnChecklistPreOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnChecklistPreOperasiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMChecklistPreOperasi form=new RMChecklistPreOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnChecklistPreOperasiActionPerformed

    private void MnSignInSebelumAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSignInSebelumAnestesiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSignInSebelumAnastesi form=new RMSignInSebelumAnastesi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSignInSebelumAnestesiActionPerformed

    private void MnTimeOutSebelumInsisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTimeOutSebelumInsisiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMTimeOutSebelumInsisi form=new RMTimeOutSebelumInsisi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnTimeOutSebelumInsisiActionPerformed

    private void MnSignOutSebelumMenutupLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSignOutSebelumMenutupLukaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSignOutSebelumMenutupLuka form=new RMSignOutSebelumMenutupLuka(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSignOutSebelumMenutupLukaActionPerformed

    private void MnChecklistPostOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnChecklistPostOperasiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMChecklistPostOperasi form=new RMChecklistPostOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnChecklistPostOperasiActionPerformed

    private void MnRekonsiliasiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekonsiliasiObatActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMRekonsiliasiObat form=new RMRekonsiliasiObat(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnRekonsiliasiObatActionPerformed

    private void MnPenilaianPasienTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPasienTerminalActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPasienTerminal form=new RMPenilaianPasienTerminal(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianPasienTerminalActionPerformed

    private void MnPersetujuanRawatInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanRawatInapActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratPersetujuanRawatInap form=new SuratPersetujuanRawatInap(null,false);
                form.isCek();
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPersetujuanRawatInapActionPerformed

    private void MnMonitoringReaksiTranfusiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnMonitoringReaksiTranfusiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataMonitoringReaksiTranfusi form=new RMDataMonitoringReaksiTranfusi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnMonitoringReaksiTranfusiActionPerformed

    private void MnPenilaianKorbanKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianKorbanKekerasanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianKorbanKekerasan form=new RMPenilaianKorbanKekerasan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianKorbanKekerasanActionPerformed

    private void MnPenilaianRisikoJatuhLansiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianRisikoJatuhLansiaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianLanjutanRisikoJatuhLansia form=new RMPenilaianLanjutanRisikoJatuhLansia(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianRisikoJatuhLansiaActionPerformed

    private void MnPenilaianPasienPenyakitMenularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPasienPenyakitMenularActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPasienPenyakitMenular form=new RMPenilaianPasienPenyakitMenular(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianPasienPenyakitMenularActionPerformed

    private void MnEdukasiPasienKeluargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnEdukasiPasienKeluargaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMEdukasiPasienKeluargaRawatJalan form=new RMEdukasiPasienKeluargaRawatJalan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnEdukasiPasienKeluargaActionPerformed

    private void MnPemantauanPEWSDewasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemantauanPEWSDewasaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPemantauanEWSD form=new RMPemantauanEWSD(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPemantauanPEWSDewasaActionPerformed

    private void MnPenilaianTambahanBunuhDiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianTambahanBunuhDiriActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianTambahanBunuhDiri form=new RMPenilaianTambahanBunuhDiri(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianTambahanBunuhDiriActionPerformed

    private void MnPenilaianTambahanPerilakuKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianTambahanPerilakuKekerasanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianTambahanPerilakuKekerasan form=new RMPenilaianTambahanPerilakuKekerasan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianTambahanPerilakuKekerasanActionPerformed

    private void MnPenilaianTambahanMelarikanDiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianTambahanMelarikanDiriActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianTambahanMelarikanDiri form=new RMPenilaianTambahanMelarikanDiri(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianTambahanMelarikanDiriActionPerformed

    private void MnPersetujuanPenundaanPelayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanPenundaanPelayananActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratPersetujuanPenundaanPelayanan form=new SuratPersetujuanPenundaanPelayanan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPersetujuanPenundaanPelayananActionPerformed

    private void MnPenilaianPasienKeracunanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPasienKeracunanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPasienKeracunan form=new RMPenilaianPasienKeracunan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianPasienKeracunanActionPerformed

    private void MnPemantauanMEOWSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemantauanMEOWSActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPemantauanMEOWS form=new RMPemantauanMEOWS(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPemantauanMEOWSActionPerformed

    private void ppCatatanADIMEGiziBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanADIMEGiziBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMCatatanADIMEGizi form=new RMCatatanADIMEGizi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppCatatanADIMEGiziBtnPrintActionPerformed

    private void MnBelumTerbitSEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumTerbitSEPActionPerformed
        terbitsep="and reg_periksa.kd_pj in (select password_asuransi.kd_pj from password_asuransi) and reg_periksa.no_rawat not in (select bridging_sep.no_rawat from bridging_sep)";
        tampil();
    }//GEN-LAST:event_MnBelumTerbitSEPActionPerformed

    private void MnCheckListKriteriaMasukHCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckListKriteriaMasukHCUActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMChecklistKriteriaMasukHCU form=new RMChecklistKriteriaMasukHCU(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCheckListKriteriaMasukHCUActionPerformed

    private void MnPenolakanAnjuranMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenolakanAnjuranMedisActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SuratPenolakanAnjuranMedis form=new SuratPenolakanAnjuranMedis(null,false);
                form.isCek();
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenolakanAnjuranMedisActionPerformed

    private void MnCheckListKriteriaMasukICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckListKriteriaMasukICUActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMChecklistKriteriaMasukICU form=new RMChecklistKriteriaMasukICU(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCheckListKriteriaMasukICUActionPerformed

    private void MnPenilaianRisikoJatuhNeonatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianRisikoJatuhNeonatusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianRisikoJatuhNeonatus form=new RMPenilaianRisikoJatuhNeonatus(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianRisikoJatuhNeonatusActionPerformed

    private void MnPenilaianRisikoJatuhGeriatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianRisikoJatuhGeriatriActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianLanjutanRisikoJatuhGeriatri form=new RMPenilaianLanjutanRisikoJatuhGeriatri(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianRisikoJatuhGeriatriActionPerformed

    private void MnPemantauanEWSNeonatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemantauanEWSNeonatusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPemantauanEWSNeonatus form=new RMPemantauanEWSNeonatus(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPemantauanEWSNeonatusActionPerformed

    private void MnRiwayatPerawatanICareNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRiwayatPerawatanICareNIKActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                variabel=Sequel.cariIsi("select maping_dokter_dpjpvclaim.kd_dokter_bpjs from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter=?",KdDokter.getText());
                if(!variabel.equals("")){
                    akses.setform("DlgReg");
                    ICareRiwayatPerawatan dlgki=new ICareRiwayatPerawatan(null,false);
                    dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.setPasien(Sequel.cariIsi("select pasien.no_ktp from pasien where pasien.no_rkm_medis=?",TNoRM.getText()),variabel);   
                    dlgki.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Dokter tidak terdaftar di mapping dokter BPJS...!!!");  
                }       
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnRiwayatPerawatanICareNIKActionPerformed

    private void MnRiwayatPerawatanICareNoKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRiwayatPerawatanICareNoKartuActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                variabel=Sequel.cariIsi("select maping_dokter_dpjpvclaim.kd_dokter_bpjs from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter=?",KdDokter.getText());
                if(!variabel.equals("")){
                    akses.setform("DlgReg");
                    ICareRiwayatPerawatan dlgki=new ICareRiwayatPerawatan(null,false);
                    dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.setPasien(Sequel.cariIsi("select pasien.no_peserta from pasien where pasien.no_rkm_medis=?",TNoRM.getText()),variabel);   
                    dlgki.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Dokter tidak terdaftar di mapping dokter BPJS...!!!"); 
                }
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnRiwayatPerawatanICareNoKartuActionPerformed

    private void MnGabungNoRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGabungNoRawatActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
                }else{
                    norawatdipilih=tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString();
                    normdipilih=tbPetugas.getValueAt(tbPetugas.getSelectedRow(),7).toString();
                    JOptionPane.showMessageDialog(rootPane,"Silahkan pilih No.Rawat yang mau digabung...!");
                }
            }
        }
    }//GEN-LAST:event_MnGabungNoRawatActionPerformed

    private void MnRiwayatPerawatanICareNIK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRiwayatPerawatanICareNIK1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                variabel=Sequel.cariIsi("select maping_dokter_pcare.kd_dokter_pcare from maping_dokter_pcare where maping_dokter_pcare.kd_dokter=?",KdDokter.getText());
                if(!variabel.equals("")){
                    akses.setform("DlgReg");
                    ICareRiwayatPerawatanFKTP dlgki=new ICareRiwayatPerawatanFKTP(null,false);
                    dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.setPasien(Sequel.cariIsi("select pasien.no_ktp from pasien where pasien.no_rkm_medis=?",TNoRM.getText()),variabel);   
                    dlgki.setVisible(true);
                }else{
                   JOptionPane.showMessageDialog(null,"Maaf, Dokter tidak terdaftar di mapping dokter BPJS...!!!"); 
                }       
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnRiwayatPerawatanICareNIK1ActionPerformed

    private void MnRiwayatPerawatanICareNoKartu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRiwayatPerawatanICareNoKartu1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                variabel=Sequel.cariIsi("select maping_dokter_pcare.kd_dokter_pcare from maping_dokter_pcare where maping_dokter_pcare.kd_dokter=?",KdDokter.getText());
                if(!variabel.equals("")){
                    akses.setform("DlgReg");
                    ICareRiwayatPerawatanFKTP dlgki=new ICareRiwayatPerawatanFKTP(null,false);
                    dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.setPasien(Sequel.cariIsi("select pasien.no_peserta from pasien where pasien.no_rkm_medis=?",TNoRM.getText()),variabel);   
                    dlgki.setVisible(true);
                }else{
                   JOptionPane.showMessageDialog(null,"Maaf, Dokter tidak terdaftar di mapping dokter BPJS...!!!"); 
                }
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnRiwayatPerawatanICareNoKartu1ActionPerformed

    private void MnPeniliaianAwalMedisHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalMedisHemodialisaActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisHemodialisa form=new RMPenilaianAwalMedisHemodialisa(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate(),tbPetugas.getValueAt(tbPetugas.getSelectedRow(),11).toString());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalMedisHemodialisaActionPerformed

    private void MnPenilaianRisikoJatuhPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianRisikoJatuhPsikiatriActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianLanjutanRisikoJatuhPsikiatri form=new RMPenilaianLanjutanRisikoJatuhPsikiatri(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianRisikoJatuhPsikiatriActionPerformed

    private void MnPenilaianLanjutanSkriningFungsionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianLanjutanSkriningFungsionalActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianLanjutanSkriningFungsional form=new RMPenilaianLanjutanSkriningFungsional(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianLanjutanSkriningFungsionalActionPerformed

    private void MnPeniliaianAwalMedisIGDPsikiatriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeniliaianAwalMedisIGDPsikiatriActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalMedisIGDPsikiatri form=new RMPenilaianAwalMedisIGDPsikiatri(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.emptTeks();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeniliaianAwalMedisIGDPsikiatriActionPerformed

    private void MnPenilaianUlangNyeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianUlangNyeriActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianUlangNyeri form=new RMPenilaianUlangNyeri(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPenilaianUlangNyeriActionPerformed

    private void MnPengkajianRestrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPengkajianRestrainActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPengkajianRestrain form=new RMPengkajianRestrain(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPengkajianRestrainActionPerformed

    private void MnCheckList8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList8ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(!TPasien.getText().equals("")){
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TNoRM.getText()));
            param.put("dokter",TDokter.getText());
            param.put("no_rawat",TNoRw.getText());
            param.put("bayar",nmpnj.getText());
            param.put("penjab",TPngJwb.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLembarPeriksa5.jasper","report","::[ Lembar Periksa]::",
                "select date_format(current_date(),'%d/%m/%Y') as sekarang",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList8ActionPerformed

    private void MnCheckList9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList9ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(!TPasien.getText().equals("")){
            Map<String, Object> param = new HashMap<>();
            param.put("poli","UGD/IGD");
            param.put("antrian",TNoReg.getText());
            param.put("nama",TPasien.getText());
            param.put("norm",TNoRM.getText());
            param.put("lahir",Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TNoRM.getText()));
            param.put("dokter",TDokter.getText());
            param.put("no_rawat",TNoRw.getText());
            param.put("bayar",nmpnj.getText());
            param.put("penjab",TPngJwb.getText());
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptLembarPeriksa6.jasper","report","::[ Lembar Periksa]::",
                "select date_format(current_date(),'%d/%m/%Y') as sekarang",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList9ActionPerformed

    private void MnCatatanKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanKeperawatanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataCatatanKeperawatanRalan form=new RMDataCatatanKeperawatanRalan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCatatanKeperawatanActionPerformed

    private void MnCatatanPersalinanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanPersalinanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMCatatanPersalinan form=new RMCatatanPersalinan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCatatanPersalinanActionPerformed

    private void MnSkorAldrettePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkorAldrettePascaAnestesiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMMonitoringAldrettePascaAnestesi form=new RMMonitoringAldrettePascaAnestesi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSkorAldrettePascaAnestesiActionPerformed

    private void MnSkorStewardPascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkorStewardPascaAnestesiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMMonitoringStewardPascaAnestesi form=new RMMonitoringStewardPascaAnestesi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSkorStewardPascaAnestesiActionPerformed

    private void MnSkorBromagePascaAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkorBromagePascaAnestesiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMMonitoringBromagePascaAnestesi form=new RMMonitoringBromagePascaAnestesi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSkorBromagePascaAnestesiActionPerformed

    private void MnPenilaianPreInduksiActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPreInduksi form=new RMPenilaianPreInduksi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    } 
    
    private void MnHasilPemeriksaanEKGActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMHasilPemeriksaanEKG form=new RMHasilPemeriksaanEKG(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    private void MnSudahTerbitSEPActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        terbitsep="and reg_periksa.kd_pj in (select password_asuransi.kd_pj from password_asuransi) and reg_periksa.no_rawat in (select bridging_sep.no_rawat from bridging_sep)";
        tampil();
    } 
    
    private void MnPenilaianPasienImunitasRendahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianPasienPenyakitMenularActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianPasienImunitasRendah form=new RMPenilaianPasienImunitasRendah(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    private void MnCatatanKeseimbanganCairanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanCekGDSActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataCatatanKeseimbanganCairan form=new RMDataCatatanKeseimbanganCairan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    private void MnCatatanObservasiCHBPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanObservasiIGDActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataCatatanObservasiCHBP form=new RMDataCatatanObservasiCHBP(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    private void MnCatatanObservasiInduksiPersalinanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanObservasiIGDActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataCatatanObservasiInduksiPersalinan form=new RMDataCatatanObservasiInduksiPersalinan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    private void MnDataOperasiActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgCariTagihanOperasi form=new DlgCariTagihanOperasi(null,false);
                //form.emptTeks();      
                form.setPasien(TNoRw.getText(),Valid.SetTgl2(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),3).toString()));
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }                
        }
    }
    
    private void MnSkriningMerokokUsiaSekolahRemajaActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningMerokokUsiaSekolahRemaja form=new RMSkriningMerokokUsiaSekolahRemaja(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningKekerasanPadaWanitaActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningKekerasanPadaPerempuan form=new RMSkriningKekerasanPadaPerempuan(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningObesitasActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningObesitas form=new RMSkriningObesitas(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningRisikoKankerPayudaraActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningRisikoKankerPayudara form=new RMSkriningRisikoKankerPayudara(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningRisikoKankerParuActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningRisikoKankerParu form=new RMSkriningRisikoKankerParu(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningKesehatanGigiMulutRemajaActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningKesehatanGigiMulutRemaja form=new RMSkriningKesehatanGigiMulutRemaja(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningTBCActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningTBC form=new RMSkriningTBC(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnCatatanAnastesiSedasiActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMCatatanAnastesiSedasi form=new RMCatatanAnastesiSedasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    private void MnSkriningPUMAActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningPUMA form=new RMSkriningPUMA(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningAdiksiNikotinActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningAdiksiNikotin form=new RMSkriningAdiksiNikotin(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningThalassemiaActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningTalasemia form=new RMSkriningTalasemia(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningInstrumenSDQActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningInstrumenSDQ form=new RMSkriningInstrumenSDQ(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnSkriningInstrumenSRQActionPerformed(java.awt.event.ActionEvent evt) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMSkriningSRQ form=new RMSkriningSRQ(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());  
            }                
        }
    }
    
    private void MnChecklistPemberianFibrinolitikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanObservasiIGDActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMChecklistPemberianFibrinolitik form=new RMChecklistPemberianFibrinolitik(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                form.emptTeks();
                form.setNoRm(TNoRw.getText(),DTPCari2.getDate());
                form.tampil();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
    /**
    * @data args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgIGD dialog = new DlgIGD(new javax.swing.JFrame(), true);
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
    private widget.TextBox AsalRujukan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar3;
    private widget.Button BtnKeluar4;
    private widget.Button BtnPasien;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint3;
    private widget.Button BtnPrint4;
    private widget.Button BtnPrint5;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkTracker;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox CrDokter3;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPReg;
    private javax.swing.JDialog DlgCatatan;
    private javax.swing.JDialog DlgDemografi;
    private javax.swing.JDialog DlgSakit2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.TextBox Kabupaten2;
    private widget.TextBox Kd2;
    private widget.TextBox KdDokter;
    private widget.TextBox Kecamatan2;
    private widget.TextBox Kelurahan2;
    private widget.Label LCount;
    private widget.Label LabelCatatan;
    private javax.swing.JMenu MenuInputData;
    private javax.swing.JMenuItem MnBarcode;
    private javax.swing.JMenuItem MnBarcode1;
    private javax.swing.JMenuItem MnBarcode2;
    private javax.swing.JMenuItem MnBarcode3;
    private javax.swing.JMenuItem MnBarcodeRM9;
    private javax.swing.JMenuItem MnBatal;
    private javax.swing.JMenuItem MnBelum;
    private javax.swing.JMenuItem MnBelumTerbitSEP;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnBillingParsial;
    private javax.swing.JMenuItem MnBlangkoResep;
    private javax.swing.JMenu MnBridging;
    private javax.swing.JMenuItem MnCatatanCekGDS;
    private javax.swing.JMenuItem MnCatatanKeperawatan;
    private javax.swing.JMenuItem MnCatatanObservasiIGD;
    private javax.swing.JMenuItem MnCatatanPersalinan;
    private javax.swing.JMenuItem MnCetakBebasNarkoba;
    private javax.swing.JMenuItem MnCetakRegister;
    private javax.swing.JMenuItem MnCetakRegister1;
    private javax.swing.JMenuItem MnCetakRegister2;
    private javax.swing.JMenuItem MnCetakSuratBebasTBC;
    private javax.swing.JMenuItem MnCetakSuratCovid;
    private javax.swing.JMenuItem MnCetakSuratCutiHamil;
    private javax.swing.JMenuItem MnCetakSuratHamil;
    private javax.swing.JMenuItem MnCetakSuratSakit;
    private javax.swing.JMenuItem MnCetakSuratSakit2;
    private javax.swing.JMenuItem MnCetakSuratSakitPihak2;
    private javax.swing.JMenuItem MnCheckList;
    private javax.swing.JMenuItem MnCheckList1;
    private javax.swing.JMenuItem MnCheckList2;
    private javax.swing.JMenuItem MnCheckList3;
    private javax.swing.JMenuItem MnCheckList4;
    private javax.swing.JMenuItem MnCheckList5;
    private javax.swing.JMenuItem MnCheckList6;
    private javax.swing.JMenuItem MnCheckList7;
    private javax.swing.JMenuItem MnCheckList8;
    private javax.swing.JMenuItem MnCheckList9;
    private javax.swing.JMenuItem MnCheckListKriteriaMasukHCU;
    private javax.swing.JMenuItem MnCheckListKriteriaMasukICU;
    private javax.swing.JMenuItem MnChecklistPostOperasi;
    private javax.swing.JMenuItem MnChecklistPreOperasi;
    private javax.swing.JMenuItem MnCopyResep;
    private javax.swing.JMenuItem MnDIrawat;
    private javax.swing.JMenu MnDataRM;
    private javax.swing.JMenuItem MnDataTriaseIGD;
    private javax.swing.JMenuItem MnDiagnosa;
    private javax.swing.JMenuItem MnDirujuk;
    private javax.swing.JMenuItem MnEdukasiPasienKeluarga;
    private javax.swing.JMenuItem MnGabungNoRawat;
    private javax.swing.JMenu MnGelang;
    private javax.swing.JMenuItem MnGelang1;
    private javax.swing.JMenuItem MnGelang2;
    private javax.swing.JMenuItem MnGelang3;
    private javax.swing.JMenuItem MnGelang4;
    private javax.swing.JMenuItem MnGelang5;
    private javax.swing.JMenuItem MnGelang6;
    private javax.swing.JMenu MnGizi;
    private javax.swing.JMenu MnHapusData;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private javax.swing.JMenuItem MnHemodialisa;
    private javax.swing.JMenuItem MnJKRA;
    private javax.swing.JMenuItem MnJadwalOperasi;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnKonselingFarmasi;
    private javax.swing.JMenuItem MnLabelTracker;
    private javax.swing.JMenuItem MnLabelTracker1;
    private javax.swing.JMenuItem MnLabelTracker2;
    private javax.swing.JMenuItem MnLabelTracker3;
    private javax.swing.JMenuItem MnLaporanRekapJenisBayar;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganBulanan;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganBulananPoli;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganDokter;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganPoli;
    private javax.swing.JMenuItem MnLaporanRekapPenyakitRalan;
    private javax.swing.JMenuItem MnLaporanRekapRawatDarurat;
    private javax.swing.JMenuItem MnLembarCasemix;
    private javax.swing.JMenuItem MnLembarCasemix1;
    private javax.swing.JMenuItem MnLembarKeluarMasuk2;
    private javax.swing.JMenuItem MnLembarRalan;
    private javax.swing.JMenuItem MnMeninggal;
    private javax.swing.JMenuItem MnMonitoringReaksiTranfusi;
    private javax.swing.JMenuItem MnNoResep;
    private javax.swing.JMenu MnObat;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnPCare;
    private javax.swing.JMenuItem MnPemantauanEWSNeonatus;
    private javax.swing.JMenuItem MnPemantauanMEOWS;
    private javax.swing.JMenuItem MnPemantauanPEWSAnak;
    private javax.swing.JMenuItem MnPemantauanPEWSDewasa;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPengkajianRestrain;
    private javax.swing.JMenuItem MnPenilaianAwalKeperawatanIGD;
    private javax.swing.JMenuItem MnPenilaianFisioterapi;
    private javax.swing.JMenuItem MnPenilaianKorbanKekerasan;
    private javax.swing.JMenu MnPenilaianLain;
    private javax.swing.JMenuItem MnPenilaianLanjutanSkriningFungsional;
    private javax.swing.JMenuItem MnPenilaianPasienKeracunan;
    private javax.swing.JMenuItem MnPenilaianPasienPenyakitMenular;
    private javax.swing.JMenuItem MnPenilaianPasienTerminal;
    private javax.swing.JMenuItem MnPenilaianPreAnestesi;
    private javax.swing.JMenuItem MnPenilaianPreOp;
    private javax.swing.JMenuItem MnPenilaianPsikolog;
    private javax.swing.JMenuItem MnPenilaianRisikoJatuhAnak;
    private javax.swing.JMenuItem MnPenilaianRisikoJatuhDewasa;
    private javax.swing.JMenuItem MnPenilaianRisikoJatuhGeriatri;
    private javax.swing.JMenuItem MnPenilaianRisikoJatuhLansia;
    private javax.swing.JMenuItem MnPenilaianRisikoJatuhNeonatus;
    private javax.swing.JMenuItem MnPenilaianRisikoJatuhPsikiatri;
    private javax.swing.JMenuItem MnPenilaianTambahanBunuhDiri;
    private javax.swing.JMenuItem MnPenilaianTambahanGeriatri;
    private javax.swing.JMenuItem MnPenilaianTambahanMelarikanDiri;
    private javax.swing.JMenuItem MnPenilaianTambahanPerilakuKekerasan;
    private javax.swing.JMenuItem MnPenilaianUlangNyeri;
    private javax.swing.JMenuItem MnPeniliaianAwalMedisHemodialisa;
    private javax.swing.JMenuItem MnPeniliaianAwalMedisIGD;
    private javax.swing.JMenuItem MnPeniliaianAwalMedisIGDPsikiatri;
    private javax.swing.JMenuItem MnPenolakanAnjuranMedis;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaLabMB;
    private javax.swing.JMenuItem MnPeriksaLabPA;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenu MnPermintaan;
    private javax.swing.JMenuItem MnPermintaanInformasiObat;
    private javax.swing.JMenuItem MnPermintaanLab;
    private javax.swing.JMenuItem MnPermintaanRadiologi;
    private javax.swing.JMenuItem MnPermintaanRanap;
    private javax.swing.JMenuItem MnPernyataanPasienUmum;
    private javax.swing.JMenuItem MnPersetujuanPenolakanTindakan;
    private javax.swing.JMenuItem MnPersetujuanPenundaanPelayanan;
    private javax.swing.JMenuItem MnPersetujuanRawatInap;
    private javax.swing.JMenuItem MnPersetujuanUmum;
    private javax.swing.JMenu MnPilihBilling;
    private javax.swing.JMenuItem MnPoliInternal;
    private javax.swing.JMenuItem MnPulangAtasPermintaanSendiri;
    private javax.swing.JMenuItem MnPulangPaksa;
    private javax.swing.JMenu MnRMCatatanMonitoring;
    private javax.swing.JMenu MnRMFarmasi;
    private javax.swing.JMenu MnRMHCU;
    private javax.swing.JMenu MnRMIGD;
    private javax.swing.JMenu MnRMOperasi;
    private javax.swing.JMenu MnRMRisikoJatuh;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnRekonsiliasiObat;
    private javax.swing.JMenuItem MnResepDOkter;
    private javax.swing.JMenuItem MnRiwayatPerawatanICareNIK;
    private javax.swing.JMenuItem MnRiwayatPerawatanICareNIK1;
    private javax.swing.JMenuItem MnRiwayatPerawatanICareNoKartu;
    private javax.swing.JMenuItem MnRiwayatPerawatanICareNoKartu1;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenuItem MnRujukMasuk;
    private javax.swing.JMenuItem MnRujukSisrute;
    private javax.swing.JMenu MnRujukan;
    private javax.swing.JMenuItem MnSEP;
    private javax.swing.JMenuItem MnSJP;
    private javax.swing.JMenuItem MnSPBK;
    private javax.swing.JMenuItem MnSPBK1;
    private javax.swing.JMenuItem MnSignInSebelumAnestesi;
    private javax.swing.JMenuItem MnSignOutSebelumMenutupLuka;
    private javax.swing.JMenuItem MnSkorAldrettePascaAnestesi;
    private javax.swing.JMenuItem MnSkorBromagePascaAnestesi;
    private javax.swing.JMenuItem MnSkorStewardPascaAnestesi;
    private javax.swing.JMenu MnStatus;
    private javax.swing.JMenuItem MnStatusBaru;
    private javax.swing.JMenuItem MnStatusLama;
    private javax.swing.JMenuItem MnSudah;
    private javax.swing.JMenuItem MnSuratBebasTato;
    private javax.swing.JMenuItem MnSuratButaWarna;
    private javax.swing.JMenuItem MnSuratJaminanPelayananIGD;
    private javax.swing.JMenuItem MnSuratKewaspadaanKesehatan;
    private javax.swing.JMenu MnSuratSurat;
    private javax.swing.JMenuItem MnTeridentifikasiTB;
    private javax.swing.JMenuItem MnTimeOutSebelumInsisi;
    private javax.swing.JMenu MnTindakan;
    private javax.swing.JMenuItem MnTransferAntarRuang;
    private widget.TextBox NoBalasan;
    private widget.TextBox NomorSurat;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private javax.swing.JMenuItem SuratKontrol;
    private widget.TextBox TAlmt;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox THbngn;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPngJwb;
    private widget.TextBox TStatus;
    private widget.Button btnKab;
    private widget.Button btnKec;
    private widget.Button btnKel;
    private widget.Button btnPenjab;
    private widget.Button btnPenjab1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel10;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private widget.TextBox kdpnj;
    private widget.TextBox nmpnj;
    private widget.PanelBiasa panelBiasa3;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private javax.swing.JMenuItem ppAsuhanGizi;
    private javax.swing.JMenuItem ppBerkas;
    private javax.swing.JMenuItem ppBerkasDigital;
    private javax.swing.JMenuItem ppCatatanADIMEGizi;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppDataIndukKecelakaan;
    private javax.swing.JMenuItem ppDeteksiDIniCorona;
    private javax.swing.JMenuItem ppGrafikDemografi;
    private javax.swing.JMenuItem ppGrafikPerAgama;
    private javax.swing.JMenuItem ppGrafikPerBulan;
    private javax.swing.JMenuItem ppGrafikPerJK;
    private javax.swing.JMenuItem ppGrafikPerPekerjaan;
    private javax.swing.JMenuItem ppGrafikPerTahun;
    private javax.swing.JMenuItem ppGrafikPerTanggal;
    private javax.swing.JMenuItem ppGrafikPerdokter;
    private javax.swing.JMenuItem ppGrafikPerpoli;
    private javax.swing.JMenuItem ppIKP;
    private javax.swing.JMenuItem ppMonitoringAsuhanGizi;
    private javax.swing.JMenuItem ppPasienCorona;
    private javax.swing.JMenuItem ppPerawatanCorona;
    private javax.swing.JMenuItem ppProgramPRB;
    private javax.swing.JMenuItem ppResume;
    private javax.swing.JMenuItem ppRiwayat;
    private javax.swing.JMenuItem ppSkriningGizi;
    private javax.swing.JMenuItem ppSkriningNutrisiAnak;
    private javax.swing.JMenuItem ppSkriningNutrisiDewasa;
    private javax.swing.JMenuItem ppSkriningNutrisiLansia;
    private javax.swing.JMenuItem ppSuplesiJasaRaharja;
    private javax.swing.JMenuItem ppSuratKontrol;
    private javax.swing.JMenuItem ppSuratPRI;
    private widget.Table tbPetugas;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JMenuItem MnPenilaianPreInduksi,MnHasilPemeriksaanEKG,MnSudahTerbitSEP,MnPenilaianPasienImunitasRendah,MnCatatanKeseimbanganCairan,MnCatatanObservasiCHBP,MnCatatanObservasiInduksiPersalinan,
                                  MnDataOperasi,MnSkriningMerokokUsiaSekolahRemaja,MnSkriningKekerasanPadaWanita,MnSkriningObesitas,MnSkriningRisikoKankerPayudara,MnSkriningRisikoKankerParu,MnSkriningKesehatanGigiMulutRemaja,
                                  MnSkriningTBC,MnCatatanAnastesiSedasi,MnSkriningPUMA,MnSkriningAdiksiNikotin,MnSkriningThalassemia,MnSkriningInstrumenSDQ,MnSkriningInstrumenSRQ,MnChecklistPemberianFibrinolitik;
    private javax.swing.JMenu MnRMSkrining;
    
    private void tampil() {
        Valid.tabelKosong(tabMode);   
        try{  
            ps=koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,reg_periksa.stts,reg_periksa.kd_pj,reg_periksa.status_bayar "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli  where  "+
                   "poliklinik.kd_poli='IGDK' and reg_periksa.tgl_registrasi between ? and ? "+
                   (TCari.getText().trim().equals("")?"":"and (reg_periksa.no_reg like ? or reg_periksa.no_rawat like ? or reg_periksa.tgl_registrasi like ? or "+
                   "reg_periksa.kd_dokter like ? or dokter.nm_dokter like ? or reg_periksa.no_rkm_medis like ? or "+
                   "reg_periksa.stts_daftar like ? or pasien.nm_pasien like ? or poliklinik.nm_poli like ? or "+
                   "reg_periksa.p_jawab like ? or reg_periksa.almt_pj like ? or reg_periksa.hubunganpj like ? or "+
                   "penjab.png_jawab like ?) ")+terbitsep+" order by reg_periksa.no_rawat "); 
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+TCari.getText().trim()+"%");
                    ps.setString(14,"%"+TCari.getText().trim()+"%");
                    ps.setString(15,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[] {
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),
                        Valid.SetAngka(rs.getDouble(15)),rs.getString(16),rs.getString(17),rs.getString(18),
                        rs.getString("kd_pj"),rs.getString("status_bayar")
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
        TNoReg.setText("");
        TNoRw.setText("");
        Kd2.setText("");
        AsalRujukan.setText("");        
        alamatperujuk="";
        TNoRM.setText("");
        TPasien.setText("");
        TPngJwb.setText("");
        THbngn.setText("");
        TAlmt.setText("");
        TStatus.setText("");
        isNumber();       
        TNoRM.requestFocus();
        kdpnj.setText("");
        nmpnj.setText("");
    }

    private void getData() {
        if(tbPetugas.getSelectedRow()!= -1){
            TNoReg.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),1).toString());
            TNoRw.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString());
            Kd2.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString());
            Valid.SetTgl(DTPReg,tbPetugas.getValueAt(tbPetugas.getSelectedRow(),3).toString());
            CmbJam.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),4).toString().substring(0,2));
            CmbMenit.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),4).toString().substring(3,5));
            CmbDetik.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),4).toString().substring(6,8));
            KdDokter.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),5).toString());
            TDokter.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),6).toString());
            TNoRM.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),7).toString());            
            isCekPasien();
            TPngJwb.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),12).toString());
            TAlmt.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),13).toString());
            THbngn.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),14).toString());
            TStatus.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),16).toString());            
            nmpnj.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),17).toString());
            kdpnj.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),19).toString());
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
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
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
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void isPas(){
        if(validasiregistrasi.equals("Yes")){
            if(Sequel.cariInteger("select count(reg_periksa.no_rkm_medis) from reg_periksa where reg_periksa.no_rkm_medis=? and reg_periksa.status_bayar='Belum Bayar' and reg_periksa.stts<>'Batal'",TNoRM.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
            }else{
                if(validasicatatan.equals("Yes")){
                    if(Sequel.cariInteger("select count(catatan_pasien.no_rkm_medis) from catatan_pasien where catatan_pasien.no_rkm_medis=?",TNoRM.getText())>0){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgCatatan catatan=new DlgCatatan(null,false);
                        catatan.setNoRm(TNoRM.getText());
                        catatan.setSize(720,330);
                        catatan.setLocationRelativeTo(internalFrame1);
                        catatan.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                } 
                isCekPasien();
            }
        }else{
            if(validasicatatan.equals("Yes")){
                if(Sequel.cariInteger("select count(catatan_pasien.no_rkm_medis) from catatan_pasien where catatan_pasien.no_rkm_medis=?",TNoRM.getText())>0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgCatatan catatan=new DlgCatatan(null,false);
                    catatan.setNoRm(TNoRM.getText());
                    catatan.setSize(720,330);
                    catatan.setLocationRelativeTo(internalFrame1);
                    catatan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } 
            isCekPasien();
        }        
    }
    
    private void isCekPasien(){
        try {   
            ps3=koneksi.prepareStatement("select pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                        "pasien.namakeluarga,pasien.keluarga,pasien.kd_pj,penjab.png_jawab,if(pasien.tgl_daftar=?,'Baru','Lama') as daftar, "+
                        "TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,pasien.no_peserta,pasien.jk, "+
                        "(TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "+
                        "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(pasien.tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari "+
                        "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                        "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                        "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                        "inner join penjab on pasien.kd_pj=penjab.kd_pj "+
                        "where pasien.no_rkm_medis=?");
            try {
                ps3.setString(1,Valid.SetTgl(DTPReg.getSelectedItem()+""));
                ps3.setString(2,TNoRM.getText());
                rs=ps3.executeQuery();
                while(rs.next()){
                    TAlmt.setText(rs.getString("asal"));
                    TPngJwb.setText(rs.getString("namakeluarga"));
                    THbngn.setText(rs.getString("keluarga"));
                    kdpnj.setText(rs.getString("kd_pj"));
                    nmpnj.setText(rs.getString("png_jawab"));
                    TStatus.setText(rs.getString("daftar"));
                    JK.setText("jk");
                    umur="0";
                    sttsumur="Th";
                    if(rs.getInt("tahun")>0){
                        umur=rs.getString("tahun");
                        sttsumur="Th";
                    }else if(rs.getInt("tahun")==0){
                        if(rs.getInt("bulan")>0){
                            umur=rs.getString("bulan");
                            sttsumur="Bl";
                        }else if(rs.getInt("bulan")==0){
                            umur=rs.getString("hari");
                            sttsumur="Hr";
                        }
                    }
                    TPasien.setText(rs.getString("nm_pasien")+" ("+umur+" "+sttsumur+")");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps3!=null){
                    ps3.close();
                }
            }                
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public JTextField getTextField(){
        return TNoRw;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,188));
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
        DTPReg.setDate(new Date());
        DTPCari1.setDate(new Date());
        DTPCari2.setDate(new Date());
        BtnSimpan.setEnabled(akses.getigd());
        BtnHapus.setEnabled(akses.getigd());
        BtnEdit.setEnabled(akses.getigd());
        BtnPrint.setEnabled(akses.getigd());
        MnKamarInap.setEnabled(akses.getkamar_inap());
        MnRawatJalan.setEnabled(akses.gettindakan_ralan());
        ppRiwayat.setEnabled(akses.getresume_pasien());
        MnHemodialisa.setEnabled(akses.gethemodialisa());
        MnPemberianObat.setEnabled(akses.getberi_obat());
        MnBilling.setEnabled(akses.getbilling_ralan());
        MnPeriksaLab.setEnabled(akses.getperiksa_lab());
        MnPeriksaLabPA.setEnabled(akses.getpemeriksaan_lab_pa());
        MnPeriksaLabMB.setEnabled(akses.getpemeriksaan_lab_mb());
        MnPeriksaRadiologi.setEnabled(akses.getperiksa_radiologi());
        MnNoResep.setEnabled(akses.getresep_obat());
        MnBillingParsial.setEnabled(akses.getbilling_parsial());
        MnOperasi.setEnabled(akses.getoperasi());
        MnDataOperasi.setEnabled(akses.getoperasi());
        MnRujuk.setEnabled(akses.getrujukan_keluar());
        MnRujukMasuk.setEnabled(akses.getrujukan_masuk());
        MnPoliInternal.setEnabled(akses.getrujukan_poli_internal());
        MnDiagnosa.setEnabled(akses.getdiagnosa_pasien());
        MnHapusTagihanOperasi.setEnabled(akses.getoperasi());
        MnHapusObatOperasi.setEnabled(akses.getoperasi());  
        ppCatatanPasien.setEnabled(akses.getcatatan_pasien());
        ppIKP.setEnabled(akses.getinsiden_keselamatan_pasien());
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());   
        MnJadwalOperasi.setEnabled(akses.getbooking_operasi());     
        SuratKontrol.setEnabled(akses.getskdp_bpjs());
        MnPermintaanLab.setEnabled(akses.getpermintaan_lab());
        MnPermintaanRadiologi.setEnabled(akses.getpermintaan_radiologi());
        MnResepDOkter.setEnabled(akses.getresep_dokter());
        MnDataTriaseIGD.setEnabled(akses.getdata_triase_igd());
        ppResume.setEnabled(akses.getdata_resume_pasien());
        MnCetakSuratSakit.setEnabled(akses.getsurat_sakit());
        ppPasienCorona.setEnabled(akses.getpasien_corona());
        ppPerawatanCorona.setEnabled(akses.getpasien_corona());
        ppDeteksiDIniCorona.setEnabled(akses.getdeteksi_corona());
        MnCetakSuratHamil.setEnabled(akses.getsurat_hamil());
        MnCetakSuratCutiHamil.setEnabled(akses.getsurat_cuti_hamil());
        MnCetakBebasNarkoba.setEnabled(akses.getsurat_bebas_narkoba());
        MnPCare.setEnabled(akses.getbridging_pcare_daftar()); 
        MnSEP.setEnabled(akses.getbpjs_sep());  
        MnSJP.setEnabled(akses.getinhealth_sjp());  
        MnCetakSuratCovid.setEnabled(akses.getsurat_keterangan_covid());
        MnRujukSisrute.setEnabled(akses.getsisrute_rujukan_keluar());
        MnTeridentifikasiTB.setEnabled(akses.getkemenkes_sitt());
        MnPermintaanRanap.setEnabled(akses.getpermintaan_ranap());
        ppSuratKontrol.setEnabled(akses.getbpjs_surat_kontrol()); 
        ppSuratPRI.setEnabled(akses.getbpjs_surat_pri());  
        MnCetakSuratSakitPihak2.setEnabled(akses.getsurat_sakit_pihak_2());   
        MnCetakSuratBebasTBC.setEnabled(akses.getsurat_bebas_tbc());     
        MnSuratButaWarna.setEnabled(akses.getsurat_buta_warna());   
        MnSuratBebasTato.setEnabled(akses.getsurat_bebas_tato());  
        MnSuratKewaspadaanKesehatan.setEnabled(akses.getsurat_kewaspadaan_kesehatan());      
        MnPeniliaianAwalMedisIGD.setEnabled(akses.getpenilaian_awal_medis_igd());         
        MnPenilaianFisioterapi.setEnabled(akses.getpenilaian_fisioterapi());               
        ppProgramPRB.setEnabled(akses.getbpjs_program_prb());      
        ppSuplesiJasaRaharja.setEnabled(akses.getbpjs_suplesi_jasaraharja());  
        ppDataIndukKecelakaan.setEnabled(akses.getbpjs_data_induk_kecelakaan());   
        MnPenilaianAwalKeperawatanIGD.setEnabled(akses.getpenilaian_awal_keperawatan_igd());
        MnCatatanObservasiIGD.setEnabled(akses.getcatatan_observasi_igd());
        MnCatatanObservasiCHBP.setEnabled(akses.getcatatan_observasi_chbp());
        MnCopyResep.setVisible(akses.getresep_dokter());
        MnPenilaianPsikolog.setEnabled(akses.getpenilaian_psikologi());
        MnPenilaianPreOp.setEnabled(akses.getpenilaian_pre_operasi());
        MnPenilaianPreAnestesi.setEnabled(akses.getpenilaian_pre_anestesi());
        MnPersetujuanPenolakanTindakan.setEnabled(akses.getpersetujuan_penolakan_tindakan());
        MnPulangAtasPermintaanSendiri.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri());
        MnPemantauanPEWSAnak.setEnabled(akses.getpemantauan_pews_anak());
        MnPenilaianRisikoJatuhDewasa.setEnabled(akses.getpenilaian_lanjutan_resiko_jatuh_dewasa());
        MnPenilaianRisikoJatuhAnak.setEnabled(akses.getpenilaian_lanjutan_resiko_jatuh_anak());
        MnPenilaianTambahanGeriatri.setEnabled(akses.getpenilaian_tambahan_pasien_geriatri());
        ppSkriningNutrisiDewasa.setEnabled(akses.getskrining_nutrisi_dewasa());
        ppSkriningNutrisiLansia.setEnabled(akses.getskrining_nutrisi_lansia());
        ppSkriningNutrisiAnak.setEnabled(akses.getskrining_nutrisi_anak());
        ppSkriningGizi.setEnabled(akses.getskrining_gizi());
        ppMonitoringAsuhanGizi.setEnabled(akses.getmonitoring_asuhan_gizi()); 
        ppAsuhanGizi.setEnabled(akses.getasuhan_gizi());
        MnPernyataanPasienUmum.setEnabled(akses.getsurat_pernyataan_pasien_umum());
        MnKonselingFarmasi.setEnabled(akses.getkonseling_farmasi());
        MnPermintaanInformasiObat.setEnabled(akses.getpelayanan_informasi_obat());
        MnPersetujuanUmum.setEnabled(akses.getsurat_persetujuan_umum());
        MnTransferAntarRuang.setEnabled(akses.gettransfer_pasien_antar_ruang());
        MnCatatanCekGDS.setEnabled(akses.getcatatan_cek_gds());
        MnChecklistPreOperasi.setEnabled(akses.getchecklist_pre_operasi());
        MnSignInSebelumAnestesi.setEnabled(akses.getsignin_sebelum_anestesi());
        MnTimeOutSebelumInsisi.setEnabled(akses.gettimeout_sebelum_insisi());
        MnSignOutSebelumMenutupLuka.setEnabled(akses.getsignout_sebelum_menutup_luka());
        MnChecklistPostOperasi.setEnabled(akses.getchecklist_post_operasi());
        MnRekonsiliasiObat.setEnabled(akses.getrekonsiliasi_obat());
        MnPenilaianPasienTerminal.setEnabled(akses.getpenilaian_pasien_terminal());
        MnPersetujuanRawatInap.setEnabled(akses.getsurat_persetujuan_rawat_inap());
        MnMonitoringReaksiTranfusi.setEnabled(akses.getmonitoring_reaksi_tranfusi());
        MnPenilaianKorbanKekerasan.setEnabled(akses.getpenilaian_korban_kekerasan());
        MnPenilaianRisikoJatuhLansia.setEnabled(akses.getpenilaian_lanjutan_resiko_jatuh_lansia());
        MnPenilaianPasienPenyakitMenular.setEnabled(akses.getpenilaian_pasien_penyakit_menular());
        MnEdukasiPasienKeluarga.setEnabled(akses.getedukasi_pasien_keluarga_rj());
        MnPemantauanPEWSDewasa.setEnabled(akses.getpemantauan_pews_dewasa());
        MnPenilaianTambahanBunuhDiri.setEnabled(akses.getpenilaian_tambahan_bunuh_diri());
        MnPenilaianTambahanPerilakuKekerasan.setEnabled(akses.getpenilaian_tambahan_perilaku_kekerasan());
        MnPenilaianTambahanMelarikanDiri.setEnabled(akses.getpenilaian_tambahan_beresiko_melarikan_diri());
        MnPersetujuanPenundaanPelayanan.setEnabled(akses.getpersetujuan_penundaan_pelayanan());
        MnPenilaianPasienKeracunan.setEnabled(akses.getpenilaian_pasien_keracunan());
        MnPemantauanMEOWS.setEnabled(akses.getpemantauan_meows_obstetri());
        ppCatatanADIMEGizi.setEnabled(akses.getcatatan_adime_gizi());
        MnCheckListKriteriaMasukHCU.setEnabled(akses.getchecklist_kriteria_masuk_hcu());
        MnPenolakanAnjuranMedis.setEnabled(akses.getpenolakan_anjuran_medis());
        MnCheckListKriteriaMasukICU.setEnabled(akses.getchecklist_kriteria_masuk_icu());
        MnPenilaianRisikoJatuhNeonatus.setEnabled(akses.getpenilaian_risiko_jatuh_neonatus());
        MnPenilaianRisikoJatuhGeriatri.setEnabled(akses.getpenilaian_lanjutan_resiko_jatuh_geriatri());
        MnPemantauanEWSNeonatus.setEnabled(akses.getpemantauan_ews_neonatus());
        MnRiwayatPerawatanICareNoKartu.setEnabled(akses.getriwayat_perawatan_icare_bpjs());
        MnRiwayatPerawatanICareNoKartu1.setEnabled(akses.getriwayat_perawatan_icare_bpjs());
        MnRiwayatPerawatanICareNIK.setEnabled(akses.getriwayat_perawatan_icare_bpjs());
        MnRiwayatPerawatanICareNIK1.setEnabled(akses.getriwayat_perawatan_icare_bpjs());
        MnPeniliaianAwalMedisHemodialisa.setEnabled(akses.getpenilaian_medis_ralan_hemodialisa());
        MnPenilaianRisikoJatuhPsikiatri.setEnabled(akses.getpenilaian_lanjutan_resiko_jatuh_psikiatri());
        MnPenilaianLanjutanSkriningFungsional.setEnabled(akses.getpenilaian_lanjutan_skrining_fungsional());
        MnPeniliaianAwalMedisIGDPsikiatri.setEnabled(akses.getpenilaian_medis_ralan_gawat_darurat_psikiatri());
        MnPenilaianUlangNyeri.setEnabled(akses.getpenilaian_ulang_nyeri());
        MnPengkajianRestrain.setEnabled(akses.getpengkajian_restrain());
        MnCatatanKeperawatan.setEnabled(akses.getcatatan_keperawatan_ralan());
        MnCatatanPersalinan.setEnabled(akses.getcatatan_persalinan());
        MnSkorAldrettePascaAnestesi.setEnabled(akses.getskor_aldrette_pasca_anestesi());
        MnSkorStewardPascaAnestesi.setEnabled(akses.getskor_steward_pasca_anestesi());
        MnSkorBromagePascaAnestesi.setEnabled(akses.getskor_bromage_pasca_anestesi());
        MnPenilaianPreInduksi.setEnabled(akses.getpenilaian_pre_induksi());
        MnGabungNoRawat.setEnabled(akses.getgabung_norawat()); 
        MnHasilPemeriksaanEKG.setEnabled(akses.gethasil_pemeriksaan_ekg());
        MnPenilaianPasienImunitasRendah.setEnabled(akses.getpenilaian_pasien_imunitas_rendah());
        MnCatatanKeseimbanganCairan.setEnabled(akses.getbalance_cairan());
        MnCatatanObservasiInduksiPersalinan.setEnabled(akses.getcatatan_observasi_induksi_persalinan());
        MnChecklistPemberianFibrinolitik.setEnabled(akses.getchecklist_pemberian_fibrinolitik());
        MnSkriningMerokokUsiaSekolahRemaja.setEnabled(akses.getskrining_perilaku_merokok_sekolah_remaja());
        MnSkriningKekerasanPadaWanita.setEnabled(akses.getskrining_kekerasan_pada_perempuan());
        MnSkriningObesitas.setEnabled(akses.getskrining_obesitas());
        MnSkriningRisikoKankerPayudara.setEnabled(akses.getskrining_risiko_kanker_payudara());
        MnSkriningRisikoKankerParu.setEnabled(akses.getskrining_risiko_kanker_paru());
        MnSkriningKesehatanGigiMulutRemaja.setEnabled(akses.getskrining_kesehatan_gigi_mulut_remaja());
        MnCatatanAnastesiSedasi.setEnabled(akses.getcatatan_anestesi_sedasi());
        MnSkriningTBC.setEnabled(akses.getskrining_tbc());
        MnSkriningPUMA.setEnabled(akses.getskrining_puma());
        MnSkriningAdiksiNikotin.setEnabled(akses.getskrining_adiksi_nikotin());
        MnSkriningThalassemia.setEnabled(akses.getskrining_thalassemia());
        MnSkriningInstrumenSDQ.setEnabled(akses.getskrining_instrumen_sdq());
        MnSkriningInstrumenSRQ.setEnabled(akses.getskrining_instrumen_srq());
    }
    
    private void isNumber(){
        switch (URUTNOREG) {
            case "poli":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_poli='IGDK' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"'","",3,TNoReg);
                break;
            case "dokter":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"'","",3,TNoReg);
                break;
            case "dokter + poli":             
                Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.kd_poli='IGDK' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"'","",3,TNoReg);
                break;
            default:
                Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"'","",3,TNoReg);
                break;
        }
        if(Kd2.getText().equals("")){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(reg_periksa.no_rawat,6),signed)),0) from reg_periksa where reg_periksa.tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"' ",dateformat.format(DTPReg.getDate())+"/",6,TNoRw);           
        }            
    }
    
    public  void ctk(){
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Runtime rt = Runtime.getRuntime();
            FileWriter writer = null;
            if(os.contains("win")) {
                writer = new FileWriter("//"+IPPRINTERTRACER);
            }else if (os.contains("mac")) {
                writer = new FileWriter("smb://"+IPPRINTERTRACER);
            }else if (os.contains("nix") || os.contains("nux")) {
                writer = new FileWriter("smb://"+IPPRINTERTRACER);
            }
            writer.write(".: TRACER :.");
            cetakStruk("Draft Sans Serif Condensed", writer,
                    MODE_DRAFT,
                    FONT_SANS_SERIF,
                    CONDENSED_ON,
                    SIZE_12_CPI,
                    SPACING_12_72);
            sendCommand(RESET, writer);
            writer.close();
        } catch (Exception ex) {
             System.out.println("Notif Writer 3 : "+ex);
        }
    }
    
    private  void cetakStruk(String title, FileWriter writer, char[]... mode) throws IOException {
        sendCommand(RESET, writer);
        for (int i = 0; i < mode.length; i++) {
            char[] cmd = mode[i];
            sendCommand(cmd, writer);
        }

        cetakStruk2(title,writer);
	sendCommand(VERTICAL_PRINT_POSITION, writer);
    }
    
    public void sendCommand(char[] command, Writer writer) throws IOException {
        writer.write(command);
    }
    
    private void cetakStruk2(  String title, FileWriter writer){
        String strukFile = "tracerRm.txt";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(strukFile));        
            String tgll= Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"'");
            String[] tglref= tgll.split("-");
            boltText(writer);
            writer.write(".: TRACER :.");
            boltTextOff(writer);
            gantiBaris(writer);
            writer.write("No. RM      : ");
            boltText(writer);
            writer.write(TNoRM.getText());
            boltTextOff(writer);
            gantiBaris(writer);
            writer.write("Nama Pasien : ");
            boltText(writer);
            writer.write(TPasien.getText());
            boltTextOff(writer);
            gantiBaris(writer);
            writer.write("Tgl. Daftar : ");
            boltText(writer);
            writer.write(tglref[2]+"-"+tglref[1]+"-"+tglref[0]+"/"+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
            gantiBaris(writer);
            boltTextOff(writer);
            writer.write("Ruangan     : ");
            boltText(writer);
            writer.write(Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli='IGDK'"));
            boltTextOff(writer);

            gantiBaris(writer);
            gantiBaris(writer);
            gantiBaris(writer);
            reader.close();
        } catch (Exception ex) {
            System.out.println("Notif : "+ex);
        }
    }
    
    private void boltText(Writer writer){
        try {
            writer.write(ESC);
            writer.write((char)14);
            writer.write(ESC);
            writer.write('E');
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    private void boltTextOff(Writer writer) {
        try {
            writer.write(ESC);
            writer.write('F');
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void gantiBaris(Writer writer) {
        try {
            writer.write("\n");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    private void UpdateUmur(){
        Sequel.mengedit("pasien","no_rkm_medis=?","umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))",1,new String[]{TNoRM.getText()});
    }
    
    private void billingprasial() {
        if(tbPetugas.getSelectedRow()!= -1){
            jmlparsial=0;
            if(aktifkanparsial.equals("yes")){
                jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",tbPetugas.getValueAt(tbPetugas.getSelectedRow(),19).toString());
            }
            if(jmlparsial>0){
                DlgBilingParsialRalan parsialralan=new DlgBilingParsialRalan(null,false);
                parsialralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                parsialralan.setLocationRelativeTo(internalFrame1);
                //parsialralan.emptTeks();
                parsialralan.isCek();
                parsialralan.setNoRm(TNoRw.getText(),KdDokter.getText(),TDokter.getText(),"IGDK");
                parsialralan.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Cara bayar "+tbPetugas.getValueAt(tbPetugas.getSelectedRow(),17).toString()+" tidak diijinkan menggunakan Billing Parsial...!!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } 
    }
    
    public void SetPasien(String norm,String nosisrute,String FaskesAsal){
        ChkInput.setSelected(true);
        isForm(); 
        TNoRM.setText(norm);
        this.nosisrute=nosisrute;
        AsalRujukan.setText(FaskesAsal);
        isPas();
    }

    public void SetPasien(String norm){
        ChkInput.setSelected(true);
        isForm(); 
        TNoRM.setText(norm);
        isPas();
    }
    
    public void setPasien(String NamaPasien,String Kontak,String Alamat,String TempatLahir,String TglLahir,
            String JK,String NoKartuJKN,String NIK,String nosisrute,String FaskesAsal){
        akses.setform("DlgIGD");
        ChkInput.setSelected(true);
        isForm(); 
        pasien.emptTeks();
        pasien.isCek();
        this.nosisrute=nosisrute;
        AsalRujukan.setText(FaskesAsal);
        pasien.setPasien(NamaPasien, Kontak, Alamat, TempatLahir, TglLahir, JK, NoKartuJKN, NIK);
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }
    
    private void initIGD() {
        MnPenilaianPreInduksi = new javax.swing.JMenuItem();
        MnPenilaianPreInduksi.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPreInduksi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        MnPenilaianPreInduksi.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPreInduksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnPenilaianPreInduksi.setText("Penilaian Pre Induksi");
        MnPenilaianPreInduksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPreInduksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPreInduksi.setName("MnPenilaianPreInduksi"); 
        MnPenilaianPreInduksi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPenilaianPreInduksi.addActionListener(this::MnPenilaianPreInduksiActionPerformed);
        
        MnHasilPemeriksaanEKG = new javax.swing.JMenuItem();
        MnHasilPemeriksaanEKG.setBackground(new java.awt.Color(255, 255, 254));
        MnHasilPemeriksaanEKG.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        MnHasilPemeriksaanEKG.setForeground(new java.awt.Color(50, 50, 50));
        MnHasilPemeriksaanEKG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnHasilPemeriksaanEKG.setText("Hasil Pemeriksaan EKG");
        MnHasilPemeriksaanEKG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHasilPemeriksaanEKG.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHasilPemeriksaanEKG.setName("MnHasilPemeriksaanEKG");
        MnHasilPemeriksaanEKG.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHasilPemeriksaanEKG.addActionListener(this::MnHasilPemeriksaanEKGActionPerformed);
        
        MnSudahTerbitSEP = new javax.swing.JMenuItem();
        MnSudahTerbitSEP.setBackground(new java.awt.Color(255, 255, 254));
        MnSudahTerbitSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        MnSudahTerbitSEP.setForeground(new java.awt.Color(50, 50, 50));
        MnSudahTerbitSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        MnSudahTerbitSEP.setText("Sudah Terbit SEP BPJS");
        MnSudahTerbitSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSudahTerbitSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSudahTerbitSEP.setName("MnSudahTerbitSEP"); 
        MnSudahTerbitSEP.setPreferredSize(new java.awt.Dimension(320, 26));
        MnSudahTerbitSEP.addActionListener(this::MnSudahTerbitSEPActionPerformed);
        
        MnPenilaianPasienImunitasRendah = new javax.swing.JMenuItem();
        MnPenilaianPasienImunitasRendah.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianPasienImunitasRendah.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnPenilaianPasienImunitasRendah.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianPasienImunitasRendah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianPasienImunitasRendah.setText("Penilaian Pasien Imunitas Rendah");
        MnPenilaianPasienImunitasRendah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenilaianPasienImunitasRendah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenilaianPasienImunitasRendah.setName("MnPenilaianPasienImunitasRendah");
        MnPenilaianPasienImunitasRendah.setPreferredSize(new java.awt.Dimension(250, 26));
        MnPenilaianPasienImunitasRendah.addActionListener(this::MnPenilaianPasienImunitasRendahActionPerformed);
        
        MnCatatanKeseimbanganCairan = new javax.swing.JMenuItem();
        MnCatatanKeseimbanganCairan.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanKeseimbanganCairan.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnCatatanKeseimbanganCairan.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanKeseimbanganCairan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnCatatanKeseimbanganCairan.setText("Keseimbangan Cairan");
        MnCatatanKeseimbanganCairan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanKeseimbanganCairan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanKeseimbanganCairan.setName("MnCatatanKeseimbanganCairan");
        MnCatatanKeseimbanganCairan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCatatanKeseimbanganCairan.addActionListener(this::MnCatatanKeseimbanganCairanActionPerformed);
        
        MnCatatanObservasiCHBP = new javax.swing.JMenuItem();
        MnCatatanObservasiCHBP.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanObservasiCHBP.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnCatatanObservasiCHBP.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanObservasiCHBP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnCatatanObservasiCHBP.setText("Observasi CHBP");
        MnCatatanObservasiCHBP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanObservasiCHBP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanObservasiCHBP.setName("MnCatatanObservasiCHBP");
        MnCatatanObservasiCHBP.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCatatanObservasiCHBP.addActionListener(this::MnCatatanObservasiCHBPActionPerformed);
        
        MnDataOperasi = new javax.swing.JMenuItem();
        MnDataOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnDataOperasi.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnDataOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnDataOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnDataOperasi.setText("Data Operasi");
        MnDataOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataOperasi.setName("MnDataOperasi");
        MnDataOperasi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnDataOperasi.addActionListener(this::MnDataOperasiActionPerformed);
        
        MnCatatanObservasiInduksiPersalinan = new javax.swing.JMenuItem();
        MnCatatanObservasiInduksiPersalinan.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanObservasiInduksiPersalinan.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnCatatanObservasiInduksiPersalinan.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanObservasiInduksiPersalinan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnCatatanObservasiInduksiPersalinan.setText("Observasi Induksi Persalinan");
        MnCatatanObservasiInduksiPersalinan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanObservasiInduksiPersalinan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanObservasiInduksiPersalinan.setName("MnCatatanObservasiInduksiPersalinan");
        MnCatatanObservasiInduksiPersalinan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCatatanObservasiInduksiPersalinan.addActionListener(this::MnCatatanObservasiInduksiPersalinanActionPerformed);
        
        MnChecklistPemberianFibrinolitik = new javax.swing.JMenuItem();
        MnChecklistPemberianFibrinolitik.setBackground(new java.awt.Color(255, 255, 254));
        MnChecklistPemberianFibrinolitik.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnChecklistPemberianFibrinolitik.setForeground(new java.awt.Color(50, 50, 50));
        MnChecklistPemberianFibrinolitik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnChecklistPemberianFibrinolitik.setText("Checklist Pemberian Fibrinolistik");
        MnChecklistPemberianFibrinolitik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnChecklistPemberianFibrinolitik.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnChecklistPemberianFibrinolitik.setName("MnChecklistPemberianFibrinolitik");
        MnChecklistPemberianFibrinolitik.setPreferredSize(new java.awt.Dimension(210, 26));
        MnChecklistPemberianFibrinolitik.addActionListener(this::MnChecklistPemberianFibrinolitikActionPerformed);
        
        MnSkriningMerokokUsiaSekolahRemaja = new javax.swing.JMenuItem();
        MnSkriningMerokokUsiaSekolahRemaja.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningMerokokUsiaSekolahRemaja.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningMerokokUsiaSekolahRemaja.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningMerokokUsiaSekolahRemaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningMerokokUsiaSekolahRemaja.setText("Skrining Merokok Usia Sekolah & Remaja");
        MnSkriningMerokokUsiaSekolahRemaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningMerokokUsiaSekolahRemaja.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningMerokokUsiaSekolahRemaja.setName("MnSkriningMerokokUsiaSekolahRemaja");
        MnSkriningMerokokUsiaSekolahRemaja.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningMerokokUsiaSekolahRemaja.addActionListener(this::MnSkriningMerokokUsiaSekolahRemajaActionPerformed);
        
        MnSkriningKekerasanPadaWanita = new javax.swing.JMenuItem();
        MnSkriningKekerasanPadaWanita.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningKekerasanPadaWanita.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningKekerasanPadaWanita.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningKekerasanPadaWanita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningKekerasanPadaWanita.setText("Skrining Kekerasan Pada Perempuan");
        MnSkriningKekerasanPadaWanita.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningKekerasanPadaWanita.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningKekerasanPadaWanita.setName("MnSkriningKekerasanPadaWanita");
        MnSkriningKekerasanPadaWanita.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningKekerasanPadaWanita.addActionListener(this::MnSkriningKekerasanPadaWanitaActionPerformed);
        
        MnSkriningObesitas = new javax.swing.JMenuItem();
        MnSkriningObesitas.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningObesitas.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningObesitas.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningObesitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningObesitas.setText("Skrining Obesitas");
        MnSkriningObesitas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningObesitas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningObesitas.setName("MnSkriningObesitas");
        MnSkriningObesitas.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningObesitas.addActionListener(this::MnSkriningObesitasActionPerformed);
        
        MnSkriningRisikoKankerPayudara = new javax.swing.JMenuItem();
        MnSkriningRisikoKankerPayudara.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningRisikoKankerPayudara.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningRisikoKankerPayudara.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningRisikoKankerPayudara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningRisikoKankerPayudara.setText("Skrining Risiko Kanker Payudara");
        MnSkriningRisikoKankerPayudara.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningRisikoKankerPayudara.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningRisikoKankerPayudara.setName("MnSkriningRisikoKankerPayudara");
        MnSkriningRisikoKankerPayudara.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningRisikoKankerPayudara.addActionListener(this::MnSkriningRisikoKankerPayudaraActionPerformed);
        
        MnSkriningRisikoKankerParu = new javax.swing.JMenuItem();
        MnSkriningRisikoKankerParu.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningRisikoKankerParu.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningRisikoKankerParu.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningRisikoKankerParu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningRisikoKankerParu.setText("Skrining Risiko Kanker Paru");
        MnSkriningRisikoKankerParu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningRisikoKankerParu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningRisikoKankerParu.setName("MnSkriningRisikoKankerParu");
        MnSkriningRisikoKankerParu.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningRisikoKankerParu.addActionListener(this::MnSkriningRisikoKankerParuActionPerformed);
        
        MnSkriningKesehatanGigiMulutRemaja = new javax.swing.JMenuItem();
        MnSkriningKesehatanGigiMulutRemaja.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningKesehatanGigiMulutRemaja.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningKesehatanGigiMulutRemaja.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningKesehatanGigiMulutRemaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningKesehatanGigiMulutRemaja.setText("Skrining Kesehatan Gigi & Mulut Usia Remaja");
        MnSkriningKesehatanGigiMulutRemaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningKesehatanGigiMulutRemaja.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningKesehatanGigiMulutRemaja.setName("MnSkriningKesehatanGigiMulutRemaja");
        MnSkriningKesehatanGigiMulutRemaja.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningKesehatanGigiMulutRemaja.addActionListener(this::MnSkriningKesehatanGigiMulutRemajaActionPerformed);
        
        MnSkriningTBC = new javax.swing.JMenuItem();
        MnSkriningTBC.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningTBC.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningTBC.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningTBC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningTBC.setText("Skrining TBC");
        MnSkriningTBC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningTBC.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningTBC.setName("MnSkriningTBC");
        MnSkriningTBC.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningTBC.addActionListener(this::MnSkriningTBCActionPerformed);
        
        MnSkriningPUMA = new javax.swing.JMenuItem();
        MnSkriningPUMA.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningPUMA.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningPUMA.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningPUMA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningPUMA.setText("Skrining PUMA");
        MnSkriningPUMA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningPUMA.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningPUMA.setName("MnSkriningPUMA");
        MnSkriningPUMA.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningPUMA.addActionListener(this::MnSkriningPUMAActionPerformed);
        
        MnSkriningAdiksiNikotin = new javax.swing.JMenuItem();
        MnSkriningAdiksiNikotin.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningAdiksiNikotin.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningAdiksiNikotin.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningAdiksiNikotin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningAdiksiNikotin.setText("Skrining Adiksi Nikotin");
        MnSkriningAdiksiNikotin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningAdiksiNikotin.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningAdiksiNikotin.setName("MnSkriningAdiksiNikotin");
        MnSkriningAdiksiNikotin.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningAdiksiNikotin.addActionListener(this::MnSkriningAdiksiNikotinActionPerformed);
        
        MnSkriningThalassemia = new javax.swing.JMenuItem();
        MnSkriningThalassemia.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningThalassemia.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningThalassemia.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningThalassemia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningThalassemia.setText("Skrining Thalassemia");
        MnSkriningThalassemia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningThalassemia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningThalassemia.setName("MnSkriningThalassemia");
        MnSkriningThalassemia.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningThalassemia.addActionListener(this::MnSkriningThalassemiaActionPerformed);
        
        MnSkriningInstrumenSDQ = new javax.swing.JMenuItem();
        MnSkriningInstrumenSDQ.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningInstrumenSDQ.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningInstrumenSDQ.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningInstrumenSDQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningInstrumenSDQ.setText("Skrining Instrumen SDQ");
        MnSkriningInstrumenSDQ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningInstrumenSDQ.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningInstrumenSDQ.setName("MnSkriningInstrumenSDQ");
        MnSkriningInstrumenSDQ.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningInstrumenSDQ.addActionListener(this::MnSkriningInstrumenSDQActionPerformed);
        
        MnSkriningInstrumenSRQ = new javax.swing.JMenuItem();
        MnSkriningInstrumenSRQ.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningInstrumenSRQ.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnSkriningInstrumenSRQ.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningInstrumenSRQ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnSkriningInstrumenSRQ.setText("Skrining Instrumen SRQ");
        MnSkriningInstrumenSRQ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSkriningInstrumenSRQ.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSkriningInstrumenSRQ.setName("MnSkriningInstrumenSRQ");
        MnSkriningInstrumenSRQ.setPreferredSize(new java.awt.Dimension(280, 26));
        MnSkriningInstrumenSRQ.addActionListener(this::MnSkriningInstrumenSRQActionPerformed);
        
        MnCatatanAnastesiSedasi = new javax.swing.JMenuItem();
        MnCatatanAnastesiSedasi.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanAnastesiSedasi.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        MnCatatanAnastesiSedasi.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanAnastesiSedasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnCatatanAnastesiSedasi.setText("Catatan Anestesi-Sedasi");
        MnCatatanAnastesiSedasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCatatanAnastesiSedasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCatatanAnastesiSedasi.setName("MnCatatanAnastesiSedasi"); 
        MnCatatanAnastesiSedasi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCatatanAnastesiSedasi.addActionListener(this::MnCatatanAnastesiSedasiActionPerformed);
        
        MnRMSkrining = new javax.swing.JMenu();
        MnRMSkrining.setBackground(new java.awt.Color(255, 255, 254));
        MnRMSkrining.setForeground(new java.awt.Color(50, 50, 50));
        MnRMSkrining.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); 
        MnRMSkrining.setText("Skrining");
        MnRMSkrining.setFont(new java.awt.Font("Tahoma", 0, 11)); 
        MnRMSkrining.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRMSkrining.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRMSkrining.setName("MnRMSkrining"); 
        MnRMSkrining.setPreferredSize(new java.awt.Dimension(200, 26));
        
        MnRMOperasi.add(MnPenilaianPreInduksi);
        MnRMOperasi.add(MnChecklistPreOperasi);
        MnRMOperasi.add(MnSignInSebelumAnestesi);
        MnRMOperasi.add(MnTimeOutSebelumInsisi);
        MnRMOperasi.add(MnSignOutSebelumMenutupLuka);
        MnRMOperasi.add(MnChecklistPostOperasi);
        MnRMOperasi.add(MnPenilaianPreOp);
        MnRMOperasi.add(MnCatatanAnastesiSedasi);
        MnRMOperasi.add(MnPenilaianPreAnestesi);
        MnRMOperasi.add(MnSkorAldrettePascaAnestesi);
        MnRMOperasi.add(MnSkorStewardPascaAnestesi);
        MnRMOperasi.add(MnSkorBromagePascaAnestesi);
        
        MnRMIGD.add(MnHasilPemeriksaanEKG);
        
        MnBridging.add(MnSEP);
        MnBridging.add(ppSuratKontrol);
        MnBridging.add(ppSuratPRI);
        MnBridging.add(ppProgramPRB);
        MnBridging.add(ppSuplesiJasaRaharja);
        MnBridging.add(ppDataIndukKecelakaan);
        MnBridging.add(MnBelumTerbitSEP);
        MnBridging.add(MnSudahTerbitSEP);
        MnBridging.add(MnSJP);
        MnBridging.add(MnPCare);
        MnBridging.add(MnRujukSisrute);
        MnBridging.add(ppPerawatanCorona);
        MnBridging.add(ppPasienCorona);
        MnBridging.add(MnTeridentifikasiTB);
        MnBridging.add(MnRiwayatPerawatanICareNIK);
        MnBridging.add(MnRiwayatPerawatanICareNoKartu);
        MnBridging.add(MnRiwayatPerawatanICareNIK1);
        MnBridging.add(MnRiwayatPerawatanICareNoKartu1);
        
        MnPenilaianLain.add(MnPenilaianTambahanGeriatri);
        MnPenilaianLain.add(MnPenilaianTambahanBunuhDiri);
        MnPenilaianLain.add(MnPenilaianTambahanPerilakuKekerasan);
        MnPenilaianLain.add(MnPenilaianTambahanMelarikanDiri);
        MnPenilaianLain.add(MnPenilaianPasienTerminal);
        MnPenilaianLain.add(MnPenilaianKorbanKekerasan);
        MnPenilaianLain.add(MnPenilaianPasienPenyakitMenular);
        MnPenilaianLain.add(MnPenilaianPasienImunitasRendah);
        MnPenilaianLain.add(MnPenilaianFisioterapi);
        MnPenilaianLain.add(MnPenilaianPsikolog);
        MnPenilaianLain.add(MnHemodialisa);
        
        MnRMCatatanMonitoring.add(MnCatatanCekGDS);
        MnRMCatatanMonitoring.add(MnMonitoringReaksiTranfusi);
        MnRMCatatanMonitoring.add(MnPenilaianUlangNyeri);
        MnRMCatatanMonitoring.add(MnCatatanKeperawatan);
        MnRMCatatanMonitoring.add(MnCatatanPersalinan);
        MnRMCatatanMonitoring.add(MnCatatanKeseimbanganCairan);
        MnRMCatatanMonitoring.add(MnCatatanObservasiIGD);
        MnRMCatatanMonitoring.add(MnCatatanObservasiCHBP);
        MnRMCatatanMonitoring.add(MnCatatanObservasiInduksiPersalinan);
        MnRMCatatanMonitoring.add(MnChecklistPemberianFibrinolitik);
        
        MnRMSkrining.add(MnSkriningMerokokUsiaSekolahRemaja);
        MnRMSkrining.add(MnSkriningKekerasanPadaWanita);
        MnRMSkrining.add(MnSkriningObesitas);
        MnRMSkrining.add(MnSkriningRisikoKankerPayudara);
        MnRMSkrining.add(MnSkriningRisikoKankerParu);
        MnRMSkrining.add(MnSkriningKesehatanGigiMulutRemaja);
        MnRMSkrining.add(MnSkriningTBC);
        MnRMSkrining.add(MnSkriningPUMA);
        MnRMSkrining.add(MnSkriningAdiksiNikotin);
        MnRMSkrining.add(MnSkriningThalassemia);
        MnRMSkrining.add(MnSkriningInstrumenSDQ);
        MnRMSkrining.add(MnSkriningInstrumenSRQ);
        
        MnTindakan.add(MnDataOperasi);
        
        MnDataRM.add(MnRMSkrining);
        MnDataRM.add(MnRMIGD);
        MnDataRM.add(MnRMOperasi);
        MnDataRM.add(MnRMHCU);
        MnDataRM.add(MnRMRisikoJatuh);
        MnDataRM.add(MnPenilaianLain);
        MnDataRM.add(MnRMFarmasi);
        MnDataRM.add(MnRMCatatanMonitoring);
        MnDataRM.add(MnGizi);
        MnDataRM.add(MnTransferAntarRuang);
        MnDataRM.add(MnEdukasiPasienKeluarga);
        MnDataRM.add(ppResume);
        MnDataRM.add(ppRiwayat);
        MnDataRM.add(ppDeteksiDIniCorona);
    }
}

package rekammedis;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariAturanPakai;
import inventory.DlgCariMetodeRacik;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;

public class MasterTemplatePemeriksaanDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDiagnosa,tabModeProsedur,tabModeRadiologi,tabModePK,tabModeDetailPK,
                tabModePA,tabModeMB,tabModeDetailMB,tabModeObatUmum,tabModeObatRacikan,tabModeDetailObatRacikan,
                TabModeTindakan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i,index=0,jml=0,r=0;
    private String[] kode,nama,ciripny,keterangan,kategori,cirium,kode2,panjang,pendek,satuan,nilairujukan,no,validcode,accpdx,asterisk,im,urut,multy;
    private boolean[] pilih;
    private double[] jumlah,p1,p2,kps;
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private File file;
    private FileWriter fileWriter;
    private String la="",ld="",pa="",pd="",noracik="";
    private double jumlahracik=0,persenracik=0,kapasitasracik=0;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private DlgCariMetodeRacik metoderacik=new DlgCariMetodeRacik(null,false);
    private DlgCariAturanPakai aturanpakai=new DlgCariAturanPakai(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public MasterTemplatePemeriksaanDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Template","Kode Dokter","Nama Dokter","Subjek","Objek","Asesmen","Plan","Instruksi","Evaluasi"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(200);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDiagnosa=new DefaultTableModel(null,new Object[]{
            "P","Kode","Nama Penyakit","Ciri-ciri Penyakit","Keterangan","Kategori","Ciri-ciri Umum","VC","AP","Ast","IM","Urut"}){
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
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDiagnosa.setModel(tabModeDiagnosa);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i= 0; i < 12; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(40);
            }else if(i==2){
                column.setPreferredWidth(215);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(25);
            }else if(i==8){
                column.setPreferredWidth(25);
            }else if(i==9){
                column.setPreferredWidth(27);
            }else if(i==10){
                column.setPreferredWidth(25);
            }else if(i==11){
                column.setPreferredWidth(30);
            }
        }
        tbDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeProsedur=new DefaultTableModel(null,new Object[]{
            "P","Kode","Deskripsi Panjang","Deskripsi Pendek","VC","AP","IM","Urut","Jml"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==8)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbProsedur.setModel(tabModeProsedur);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbProsedur.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbProsedur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbProsedur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(50);
            }else if(i==2){
                column.setPreferredWidth(372);
            }else if(i==3){
                column.setPreferredWidth(210);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(25);
            }else if(i==6){
                column.setPreferredWidth(25);
            }else if(i==7){
                column.setPreferredWidth(30);
            }else if(i==8){
                column.setPreferredWidth(25);
            }
        }
        tbProsedur.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRadiologi=new DefaultTableModel(null,new Object[]{"P","Kode Periksa","Nama Pemeriksaan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanRadiologi.setModel(tabModeRadiologi);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPermintaanRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 3; i++) {
            TableColumn column = tbPermintaanRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(130);
            }else if(i==2){
                column.setPreferredWidth(490);
            }
        }
        tbPermintaanRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePK=new DefaultTableModel(null,new Object[]{"P","Kode Periksa","Nama Pemeriksaan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanPK.setModel(tabModePK);

        tbPermintaanPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 3; i++) {
            TableColumn column = tbPermintaanPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanPK.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailPK=new DefaultTableModel(null,new Object[]{"P","Pemeriksaan","Satuan","Nilai Rujukan","id_template","Kode Jenis"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if (colIndex==0) {
                        a=true;
                    }
                    return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }              
        };
        
        tbDetailPK.setModel(tabModeDetailPK);
        //tampilPr();

        tbDetailPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDetailPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(326);
            }else if(i==2){
                column.setPreferredWidth(50);
            }else if(i==3){
                column.setPreferredWidth(315);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }
        }
        
        tbDetailPK.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePA=new DefaultTableModel(null,new Object[]{"P","Kode Periksa","Nama Pemeriksaan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanPA.setModel(tabModePA);

        tbPermintaanPA.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanPA.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 3; i++) {
            TableColumn column = tbPermintaanPA.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanPA.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeMB=new DefaultTableModel(null,new Object[]{"P","Kode Periksa","Nama Pemeriksaan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPermintaanMB.setModel(tabModeMB);

        tbPermintaanMB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPermintaanMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(i = 0; i < 3; i++) {
            TableColumn column = tbPermintaanMB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(520);
            }
        }
        tbPermintaanMB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailMB=new DefaultTableModel(null,new Object[]{"P","Pemeriksaan","Satuan","Nilai Rujukan","id_template","Kode Jenis"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if (colIndex==0) {
                        a=true;
                    }
                    return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }              
        };
        
        tbDetailMB.setModel(tabModeDetailMB);
        //tampilPr();

        tbDetailMB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDetailMB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(326);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);  
            }else if(i==3){
                column.setPreferredWidth(315);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }
        }
        tbDetailMB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeObatUmum=new DefaultTableModel(null,new Object[]{
                "K","Jumlah","Kode Barang","Nama Barang","Satuan","Komposisi",
                "Jenis Obat","Aturan Pakai","I.F.","Kapasitas"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==1)||(colIndex==7)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class,java.lang.Double.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObatNonRacikan.setModel(tabModeObatUmum);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbObatNonRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatNonRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 10; i++) {
            TableColumn column = tbObatNonRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(45);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(240);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(130);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }                
        }
        warna.kolom=1;
        tbObatNonRacikan.setDefaultRenderer(Object.class,warna);
        
        tabModeObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik",
                "Aturan Pakai","Keterangan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==0)||(colIndex==2)||(colIndex==3)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObatRacikan.setModel(tabModeObatRacikan);
        tbObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(250);
            }
        }

        warna2.kolom=4;
        tbObatRacikan.setDefaultRenderer(Object.class,warna2);
        
        tabModeDetailObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Kode Barang","Nama Barang","Satuan","Jenis Obat",
                "Kps","P1","/","P2","Kandungan","Jml","I.F.","Komposisi"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==6)||(colIndex==8)||(colIndex==9)||(colIndex==10)) {
                    a=true;
                }
                return a;
             }             
             Class[] types = new Class[] {
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 13; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(45);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(25);
            }else if(i==7){
                column.setMinWidth(11);
                column.setMaxWidth(11);
            }else if(i==8){
                column.setPreferredWidth(25);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(40);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(150);
            }
        }

        warna3.kolom=9;
        tbDetailObatRacikan.setDefaultRenderer(Object.class,warna3);
        
        TabModeTindakan=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan/Tindakan","Kategori"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan.setModel(TabModeTindakan);
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 4; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(380);
            }else if(i==3){
                column.setPreferredWidth(150);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());

        Kd.setDocument(new batasInput((byte)20).getKata(Kd));    
        Subjek.setDocument(new batasInput((int)2000).getKata(Subjek));  
        Objek.setDocument(new batasInput((int)2000).getKata(Objek));  
        Asesmen.setDocument(new batasInput((int)2000).getKata(Asesmen));  
        Plan.setDocument(new batasInput((int)2000).getKata(Plan));  
        Instruksi.setDocument(new batasInput((int)2000).getKata(Instruksi));  
        Evaluasi.setDocument(new batasInput((int)2000).getKata(Evaluasi));  
        Diagnosa.setDocument(new batasInput((byte)100).getKata(Diagnosa));  
        Prosedur.setDocument(new batasInput((byte)100).getKata(Prosedur));  
        CariRadiologi.setDocument(new batasInput((byte)100).getKata(CariRadiologi));  
        CariPK.setDocument(new batasInput((byte)100).getKata(CariPK));  
        CariDetailPK.setDocument(new batasInput((byte)100).getKata(CariDetailPK));  
        CariPA.setDocument(new batasInput((byte)100).getKata(CariPA));  
        CariMB.setDocument(new batasInput((byte)100).getKata(CariMB));  
        CariDetailMB.setDocument(new batasInput((byte)100).getKata(CariDetailMB));  
        CariObatNonRacikan.setDocument(new batasInput((byte)100).getKata(CariObatNonRacikan)); 
        CariObatRacikan.setDocument(new batasInput((byte)100).getKata(CariObatRacikan)); 
        CariTindakan.setDocument(new batasInput((byte)100).getKata(CariTindakan)); 
        
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
        
        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturanpakai.getTable().getSelectedRow()!= -1){  
                    if(index==1){
                        tbObatNonRacikan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbObatNonRacikan.getSelectedRow(),7);
                        tbObatNonRacikan.requestFocus();
                    }else if(index==2){
                        tbObatRacikan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbObatRacikan.getSelectedRow(),5);
                        tbObatRacikan.requestFocus();
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
        
        metoderacik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(metoderacik.getTable().getSelectedRow()!= -1){  
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),1).toString(),tbObatRacikan.getSelectedRow(),2);
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),2).toString(),tbObatRacikan.getSelectedRow(),3);
                    tbObatRacikan.requestFocus();
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
        
        metoderacik.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    metoderacik.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
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
                }  
                KdDokter.requestFocus();
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
        
        ChkAccor.setSelected(false);
        isDetail();
        
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

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        Kd = new widget.TextBox();
        jLabel40 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Subjek = new widget.TextArea();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel41 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Objek = new widget.TextArea();
        jLabel42 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Asesmen = new widget.TextArea();
        jLabel43 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Plan = new widget.TextArea();
        jLabel44 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Instruksi = new widget.TextArea();
        jLabel45 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Evaluasi = new widget.TextArea();
        Scroll1 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();
        BtnCariPenyakit = new widget.Button();
        Diagnosa = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        Prosedur = new widget.TextBox();
        BtnCariProsedur = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbProsedur = new widget.Table();
        BtnCariRadiologi = new widget.Button();
        Scroll3 = new widget.ScrollPane();
        tbPermintaanRadiologi = new widget.Table();
        CariRadiologi = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        CariPK = new widget.TextBox();
        BtnCariLaboratoriumPK = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        tbPermintaanPK = new widget.Table();
        Scroll5 = new widget.ScrollPane();
        tbDetailPK = new widget.Table();
        CariDetailPK = new widget.TextBox();
        BtnDetailLaboratPK = new widget.Button();
        jLabel17 = new widget.Label();
        CariPA = new widget.TextBox();
        BtnCariPA = new widget.Button();
        Scroll6 = new widget.ScrollPane();
        tbPermintaanPA = new widget.Table();
        jLabel18 = new widget.Label();
        CariMB = new widget.TextBox();
        BtnCariMB = new widget.Button();
        Scroll7 = new widget.ScrollPane();
        tbPermintaanMB = new widget.Table();
        CariDetailMB = new widget.TextBox();
        BtnCariDetailMB = new widget.Button();
        Scroll8 = new widget.ScrollPane();
        tbDetailMB = new widget.Table();
        jLabel19 = new widget.Label();
        BtnCariObatNonRacikan = new widget.Button();
        CariObatNonRacikan = new widget.TextBox();
        Scroll9 = new widget.ScrollPane();
        tbObatNonRacikan = new widget.Table();
        jLabel20 = new widget.Label();
        Scroll10 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        CariObatRacikan = new widget.TextBox();
        BtnCariObatRacikan = new widget.Button();
        Scroll11 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();
        jLabel21 = new widget.Label();
        CariTindakan = new widget.TextBox();
        BtnCariTindakan = new widget.Button();
        Scroll12 = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        BtnAllPenyakit = new widget.Button();
        BtnAllProsedur = new widget.Button();
        BtnAllRadiologi = new widget.Button();
        BtnAllPatologiKlinis = new widget.Button();
        BtnAllDetailLaboratPK = new widget.Button();
        BtnAllPA = new widget.Button();
        BtnAllMB = new widget.Button();
        BtnAllDetailMB = new widget.Button();
        BtnAllObatNonRacikan = new widget.Button();
        BtnAllObatRacikan = new widget.Button();
        BtnAllTindakan = new widget.Button();
        BtnTambah1 = new widget.Button();
        BtnHapus1 = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelGlass9 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormDetail = new widget.PanelBiasa();
        Scroll13 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template Pemeriksaan Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(730, 2966));
        FormInput.setLayout(null);

        label12.setText("No.Template :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label12);
        label12.setBounds(0, 10, 85, 23);

        Kd.setName("Kd"); // NOI18N
        Kd.setPreferredSize(new java.awt.Dimension(207, 23));
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        FormInput.add(Kd);
        Kd.setBounds(89, 10, 150, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("Subjek :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(16, 40, 410, 20);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Subjek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Subjek.setColumns(20);
        Subjek.setRows(7);
        Subjek.setName("Subjek"); // NOI18N
        Subjek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SubjekKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Subjek);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(16, 60, 700, 73);

        label14.setText("Dokter :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(230, 10, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(304, 10, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(426, 10, 260, 23);

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
        BtnDokter.setBounds(688, 10, 28, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("Objek :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(16, 140, 410, 20);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Objek.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Objek.setColumns(20);
        Objek.setRows(7);
        Objek.setName("Objek"); // NOI18N
        Objek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObjekKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Objek);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(16, 160, 700, 73);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("Asesmen :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(16, 240, 410, 20);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Asesmen.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Asesmen.setColumns(20);
        Asesmen.setRows(7);
        Asesmen.setName("Asesmen"); // NOI18N
        Asesmen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsesmenKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Asesmen);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(16, 260, 700, 73);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText("Plan :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(16, 340, 410, 20);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Plan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Plan.setColumns(20);
        Plan.setRows(7);
        Plan.setName("Plan"); // NOI18N
        Plan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PlanKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Plan);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(16, 360, 700, 73);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Instruksi :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(16, 440, 410, 20);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Instruksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Instruksi.setColumns(20);
        Instruksi.setRows(7);
        Instruksi.setName("Instruksi"); // NOI18N
        Instruksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InstruksiKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Instruksi);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(16, 460, 700, 73);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Evaluasi :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(16, 540, 410, 20);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Evaluasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Evaluasi.setColumns(20);
        Evaluasi.setRows(7);
        Evaluasi.setName("Evaluasi"); // NOI18N
        Evaluasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EvaluasiKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Evaluasi);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(16, 560, 700, 73);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbDiagnosa.setName("tbDiagnosa"); // NOI18N
        tbDiagnosa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDiagnosaMouseClicked(evt);
            }
        });
        tbDiagnosa.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDiagnosaPropertyChange(evt);
            }
        });
        tbDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDiagnosaKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbDiagnosa);

        FormInput.add(Scroll1);
        Scroll1.setBounds(16, 687, 700, 116);

        BtnCariPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit.setMnemonic('1');
        BtnCariPenyakit.setToolTipText("Alt+1");
        BtnCariPenyakit.setName("BtnCariPenyakit"); // NOI18N
        BtnCariPenyakit.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakitActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPenyakit);
        BtnCariPenyakit.setBounds(658, 660, 28, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(16, 660, 640, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Diagnosa :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(16, 640, 68, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Prosedur :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(16, 810, 68, 23);

        Prosedur.setHighlighter(null);
        Prosedur.setName("Prosedur"); // NOI18N
        Prosedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurKeyPressed(evt);
            }
        });
        FormInput.add(Prosedur);
        Prosedur.setBounds(16, 830, 640, 23);

        BtnCariProsedur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariProsedur.setMnemonic('1');
        BtnCariProsedur.setToolTipText("Alt+1");
        BtnCariProsedur.setName("BtnCariProsedur"); // NOI18N
        BtnCariProsedur.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariProsedur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariProsedurActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariProsedur);
        BtnCariProsedur.setBounds(658, 830, 28, 23);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbProsedur.setName("tbProsedur"); // NOI18N
        tbProsedur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProsedurMouseClicked(evt);
            }
        });
        tbProsedur.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbProsedurPropertyChange(evt);
            }
        });
        tbProsedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbProsedurKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbProsedur);

        FormInput.add(Scroll2);
        Scroll2.setBounds(16, 857, 700, 116);

        BtnCariRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRadiologi.setMnemonic('1');
        BtnCariRadiologi.setToolTipText("Alt+1");
        BtnCariRadiologi.setName("BtnCariRadiologi"); // NOI18N
        BtnCariRadiologi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRadiologiActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariRadiologi);
        BtnCariRadiologi.setBounds(658, 1000, 28, 23);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPermintaanRadiologi.setName("tbPermintaanRadiologi"); // NOI18N
        Scroll3.setViewportView(tbPermintaanRadiologi);

        FormInput.add(Scroll3);
        Scroll3.setBounds(16, 1027, 700, 116);

        CariRadiologi.setHighlighter(null);
        CariRadiologi.setName("CariRadiologi"); // NOI18N
        CariRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariRadiologiKeyPressed(evt);
            }
        });
        FormInput.add(CariRadiologi);
        CariRadiologi.setBounds(16, 1000, 640, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Permintaan Radiologi :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(16, 980, 120, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Permintaan Laborat Patologi Klinis :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(16, 1150, 190, 23);

        CariPK.setHighlighter(null);
        CariPK.setName("CariPK"); // NOI18N
        CariPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariPKKeyPressed(evt);
            }
        });
        FormInput.add(CariPK);
        CariPK.setBounds(16, 1170, 640, 23);

        BtnCariLaboratoriumPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariLaboratoriumPK.setMnemonic('1');
        BtnCariLaboratoriumPK.setToolTipText("Alt+1");
        BtnCariLaboratoriumPK.setName("BtnCariLaboratoriumPK"); // NOI18N
        BtnCariLaboratoriumPK.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariLaboratoriumPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariLaboratoriumPKActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariLaboratoriumPK);
        BtnCariLaboratoriumPK.setBounds(658, 1170, 28, 23);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbPermintaanPK.setName("tbPermintaanPK"); // NOI18N
        tbPermintaanPK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPermintaanPKMouseClicked(evt);
            }
        });
        Scroll4.setViewportView(tbPermintaanPK);

        FormInput.add(Scroll4);
        Scroll4.setBounds(16, 1197, 700, 109);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll5.setComponentPopupMenu(Popup);
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbDetailPK.setComponentPopupMenu(Popup);
        tbDetailPK.setName("tbDetailPK"); // NOI18N
        Scroll5.setViewportView(tbDetailPK);

        FormInput.add(Scroll5);
        Scroll5.setBounds(16, 1337, 700, 216);

        CariDetailPK.setHighlighter(null);
        CariDetailPK.setName("CariDetailPK"); // NOI18N
        CariDetailPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariDetailPKKeyPressed(evt);
            }
        });
        FormInput.add(CariDetailPK);
        CariDetailPK.setBounds(16, 1310, 640, 23);

        BtnDetailLaboratPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnDetailLaboratPK.setMnemonic('1');
        BtnDetailLaboratPK.setToolTipText("Alt+1");
        BtnDetailLaboratPK.setName("BtnDetailLaboratPK"); // NOI18N
        BtnDetailLaboratPK.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDetailLaboratPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDetailLaboratPKActionPerformed(evt);
            }
        });
        FormInput.add(BtnDetailLaboratPK);
        BtnDetailLaboratPK.setBounds(658, 1310, 28, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Permintaan Laborat Patologi Anatomi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(16, 1560, 250, 23);

        CariPA.setHighlighter(null);
        CariPA.setName("CariPA"); // NOI18N
        CariPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariPAKeyPressed(evt);
            }
        });
        FormInput.add(CariPA);
        CariPA.setBounds(16, 1580, 640, 23);

        BtnCariPA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPA.setMnemonic('1');
        BtnCariPA.setToolTipText("Alt+1");
        BtnCariPA.setName("BtnCariPA"); // NOI18N
        BtnCariPA.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPAActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPA);
        BtnCariPA.setBounds(658, 1580, 28, 23);

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbPermintaanPA.setName("tbPermintaanPA"); // NOI18N
        Scroll6.setViewportView(tbPermintaanPA);

        FormInput.add(Scroll6);
        Scroll6.setBounds(16, 1607, 700, 126);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Permintaan Laborat Mikrobiologi & Bio Molekuler :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(16, 1740, 270, 23);

        CariMB.setHighlighter(null);
        CariMB.setName("CariMB"); // NOI18N
        CariMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariMBKeyPressed(evt);
            }
        });
        FormInput.add(CariMB);
        CariMB.setBounds(16, 1760, 640, 23);

        BtnCariMB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariMB.setMnemonic('1');
        BtnCariMB.setToolTipText("Alt+1");
        BtnCariMB.setName("BtnCariMB"); // NOI18N
        BtnCariMB.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariMBActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariMB);
        BtnCariMB.setBounds(658, 1760, 28, 23);

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbPermintaanMB.setName("tbPermintaanMB"); // NOI18N
        tbPermintaanMB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPermintaanMBMouseClicked(evt);
            }
        });
        Scroll7.setViewportView(tbPermintaanMB);

        FormInput.add(Scroll7);
        Scroll7.setBounds(16, 1787, 700, 109);

        CariDetailMB.setHighlighter(null);
        CariDetailMB.setName("CariDetailMB"); // NOI18N
        CariDetailMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariDetailMBKeyPressed(evt);
            }
        });
        FormInput.add(CariDetailMB);
        CariDetailMB.setBounds(16, 1900, 640, 23);

        BtnCariDetailMB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariDetailMB.setMnemonic('1');
        BtnCariDetailMB.setToolTipText("Alt+1");
        BtnCariDetailMB.setName("BtnCariDetailMB"); // NOI18N
        BtnCariDetailMB.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariDetailMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariDetailMBActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariDetailMB);
        BtnCariDetailMB.setBounds(658, 1900, 28, 23);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbDetailMB.setName("tbDetailMB"); // NOI18N
        Scroll8.setViewportView(tbDetailMB);

        FormInput.add(Scroll8);
        Scroll8.setBounds(16, 1927, 700, 216);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Obat Non Racikan :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(16, 2150, 270, 23);

        BtnCariObatNonRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariObatNonRacikan.setMnemonic('1');
        BtnCariObatNonRacikan.setToolTipText("Alt+1");
        BtnCariObatNonRacikan.setName("BtnCariObatNonRacikan"); // NOI18N
        BtnCariObatNonRacikan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariObatNonRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariObatNonRacikanActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariObatNonRacikan);
        BtnCariObatNonRacikan.setBounds(658, 2170, 28, 23);

        CariObatNonRacikan.setHighlighter(null);
        CariObatNonRacikan.setName("CariObatNonRacikan"); // NOI18N
        CariObatNonRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariObatNonRacikanKeyPressed(evt);
            }
        });
        FormInput.add(CariObatNonRacikan);
        CariObatNonRacikan.setBounds(16, 2170, 640, 23);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbObatNonRacikan.setName("tbObatNonRacikan"); // NOI18N
        tbObatNonRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatNonRacikanKeyPressed(evt);
            }
        });
        Scroll9.setViewportView(tbObatNonRacikan);

        FormInput.add(Scroll9);
        Scroll9.setBounds(16, 2197, 700, 216);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Obat Racikan :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(16, 2420, 270, 23);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        tbObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatRacikanKeyPressed(evt);
            }
        });
        Scroll10.setViewportView(tbObatRacikan);

        FormInput.add(Scroll10);
        Scroll10.setBounds(16, 2440, 700, 96);

        CariObatRacikan.setHighlighter(null);
        CariObatRacikan.setName("CariObatRacikan"); // NOI18N
        CariObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariObatRacikanKeyPressed(evt);
            }
        });
        FormInput.add(CariObatRacikan);
        CariObatRacikan.setBounds(16, 2540, 580, 23);

        BtnCariObatRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariObatRacikan.setMnemonic('1');
        BtnCariObatRacikan.setToolTipText("Alt+1");
        BtnCariObatRacikan.setName("BtnCariObatRacikan"); // NOI18N
        BtnCariObatRacikan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariObatRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariObatRacikanActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariObatRacikan);
        BtnCariObatRacikan.setBounds(598, 2540, 28, 23);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        tbDetailObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailObatRacikanKeyPressed(evt);
            }
        });
        Scroll11.setViewportView(tbDetailObatRacikan);

        FormInput.add(Scroll11);
        Scroll11.setBounds(16, 2567, 700, 216);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Tindakan :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(16, 2790, 120, 23);

        CariTindakan.setHighlighter(null);
        CariTindakan.setName("CariTindakan"); // NOI18N
        CariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CariTindakanKeyPressed(evt);
            }
        });
        FormInput.add(CariTindakan);
        CariTindakan.setBounds(16, 2810, 640, 23);

        BtnCariTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariTindakan.setMnemonic('1');
        BtnCariTindakan.setToolTipText("Alt+1");
        BtnCariTindakan.setName("BtnCariTindakan"); // NOI18N
        BtnCariTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariTindakanActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariTindakan);
        BtnCariTindakan.setBounds(658, 2810, 28, 23);

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll12.setName("Scroll12"); // NOI18N
        Scroll12.setOpaque(true);

        tbTindakan.setName("tbTindakan"); // NOI18N
        Scroll12.setViewportView(tbTindakan);

        FormInput.add(Scroll12);
        Scroll12.setBounds(16, 2840, 700, 116);

        BtnAllPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPenyakit.setMnemonic('2');
        BtnAllPenyakit.setToolTipText("Alt+2");
        BtnAllPenyakit.setName("BtnAllPenyakit"); // NOI18N
        BtnAllPenyakit.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPenyakitActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllPenyakit);
        BtnAllPenyakit.setBounds(688, 660, 28, 23);

        BtnAllProsedur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllProsedur.setMnemonic('2');
        BtnAllProsedur.setToolTipText("Alt+2");
        BtnAllProsedur.setName("BtnAllProsedur"); // NOI18N
        BtnAllProsedur.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllProsedur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllProsedurActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllProsedur);
        BtnAllProsedur.setBounds(688, 830, 28, 23);

        BtnAllRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllRadiologi.setMnemonic('2');
        BtnAllRadiologi.setToolTipText("Alt+2");
        BtnAllRadiologi.setName("BtnAllRadiologi"); // NOI18N
        BtnAllRadiologi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllRadiologiActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllRadiologi);
        BtnAllRadiologi.setBounds(688, 1000, 28, 23);

        BtnAllPatologiKlinis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPatologiKlinis.setMnemonic('2');
        BtnAllPatologiKlinis.setToolTipText("Alt+2");
        BtnAllPatologiKlinis.setName("BtnAllPatologiKlinis"); // NOI18N
        BtnAllPatologiKlinis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllPatologiKlinis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPatologiKlinisActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllPatologiKlinis);
        BtnAllPatologiKlinis.setBounds(688, 1170, 28, 23);

        BtnAllDetailLaboratPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllDetailLaboratPK.setMnemonic('2');
        BtnAllDetailLaboratPK.setToolTipText("Alt+2");
        BtnAllDetailLaboratPK.setName("BtnAllDetailLaboratPK"); // NOI18N
        BtnAllDetailLaboratPK.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllDetailLaboratPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllDetailLaboratPKActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllDetailLaboratPK);
        BtnAllDetailLaboratPK.setBounds(688, 1310, 28, 23);

        BtnAllPA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPA.setMnemonic('2');
        BtnAllPA.setToolTipText("Alt+2");
        BtnAllPA.setName("BtnAllPA"); // NOI18N
        BtnAllPA.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPAActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllPA);
        BtnAllPA.setBounds(688, 1580, 28, 23);

        BtnAllMB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllMB.setMnemonic('2');
        BtnAllMB.setToolTipText("Alt+2");
        BtnAllMB.setName("BtnAllMB"); // NOI18N
        BtnAllMB.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllMBActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllMB);
        BtnAllMB.setBounds(688, 1760, 28, 23);

        BtnAllDetailMB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllDetailMB.setMnemonic('2');
        BtnAllDetailMB.setToolTipText("Alt+2");
        BtnAllDetailMB.setName("BtnAllDetailMB"); // NOI18N
        BtnAllDetailMB.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllDetailMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllDetailMBActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllDetailMB);
        BtnAllDetailMB.setBounds(688, 1900, 28, 23);

        BtnAllObatNonRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllObatNonRacikan.setMnemonic('2');
        BtnAllObatNonRacikan.setToolTipText("Alt+2");
        BtnAllObatNonRacikan.setName("BtnAllObatNonRacikan"); // NOI18N
        BtnAllObatNonRacikan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllObatNonRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllObatNonRacikanActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllObatNonRacikan);
        BtnAllObatNonRacikan.setBounds(688, 2170, 28, 23);

        BtnAllObatRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllObatRacikan.setMnemonic('2');
        BtnAllObatRacikan.setToolTipText("Alt+2");
        BtnAllObatRacikan.setName("BtnAllObatRacikan"); // NOI18N
        BtnAllObatRacikan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllObatRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllObatRacikanActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllObatRacikan);
        BtnAllObatRacikan.setBounds(628, 2540, 28, 23);

        BtnAllTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllTindakan.setMnemonic('2');
        BtnAllTindakan.setToolTipText("Alt+2");
        BtnAllTindakan.setName("BtnAllTindakan"); // NOI18N
        BtnAllTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllTindakanActionPerformed(evt);
            }
        });
        FormInput.add(BtnAllTindakan);
        BtnAllTindakan.setBounds(688, 2810, 28, 23);

        BtnTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnTambah1.setMnemonic('3');
        BtnTambah1.setToolTipText("Alt+3");
        BtnTambah1.setName("BtnTambah1"); // NOI18N
        BtnTambah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnTambah1);
        BtnTambah1.setBounds(658, 2540, 28, 23);

        BtnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus1.setMnemonic('H');
        BtnHapus1.setToolTipText("Alt+H");
        BtnHapus1.setName("BtnHapus1"); // NOI18N
        BtnHapus1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapus1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnHapus1);
        BtnHapus1.setBounds(688, 2540, 28, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Template", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbDokter.setAutoCreateRowSorter(true);
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
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        Scroll.setViewportView(tbDokter);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(530, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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

        FormDetail.setBackground(new java.awt.Color(255, 255, 255));
        FormDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Detail Template Pemeriksaan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormDetail.setName("FormDetail"); // NOI18N
        FormDetail.setPreferredSize(new java.awt.Dimension(115, 73));
        FormDetail.setLayout(new java.awt.BorderLayout());

        Scroll13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll13.setName("Scroll13"); // NOI18N
        Scroll13.setOpaque(true);
        Scroll13.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll13.setViewportView(LoadHTML);

        FormDetail.add(Scroll13, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormDetail, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Template", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(90, 23));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            try {
                panggilDetail();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbDokter.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
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
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    if(ChkAccor.isSelected()==false){  
                        if(tbDokter.getSelectedRow()!= -1){
                            ChkAccor.setSelected(true);
                            isDetail();
                            panggilDetail();
                            ChkAccor.setSelected(false);
                            isDetail();
                        }
                    }
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbDokter.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdDokter.getText().equals(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString())){
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
        if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"No.Template");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Asesmen.getText().trim().equals("")){
            Valid.textKosong(Asesmen,"Asesmen");
        }else{
            if(tbDokter.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString())){
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
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"No.Template");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter");
        }else if(Asesmen.getText().trim().equals("")){
            Valid.textKosong(Asesmen,"Asesmen");
        }else{
            if(Sequel.menyimpantf("template_pemeriksaan_dokter","?,?,?,?,?,?,?,?","No.Template",8,new String[]{
                Kd.getText(),KdDokter.getText(),Subjek.getText(),Objek.getText(),Asesmen.getText(),Plan.getText(),Instruksi.getText(),Evaluasi.getText()
            })==true){
                for(i=0;i<tbDiagnosa.getRowCount();i++){ 
                    if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_penyakit","?,?,?","ICD X",3,new String[]{
                            Kd.getText(),tbDiagnosa.getValueAt(i,1).toString(),tbDiagnosa.getValueAt(i,11).toString()
                        });
                    }
                }
                for(i=0;i<tbProsedur.getRowCount();i++){ 
                    if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_prosedur","?,?,?,?","ICD 9",4,new String[]{
                            Kd.getText(),tbProsedur.getValueAt(i,1).toString(),tbProsedur.getValueAt(i,7).toString(),tbProsedur.getValueAt(i,8).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){ 
                    if(tbPermintaanRadiologi.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_permintaan_radiologi","?,?","Pemeriksaan Radiologi",2,new String[]{
                            Kd.getText(),tbPermintaanRadiologi.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanPK.getRowCount();i++){ 
                    if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_permintaan_lab","?,?","Pemeriksaan Laboratorium PK",2,new String[]{
                            Kd.getText(),tbPermintaanPK.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbDetailPK.getRowCount();i++){ 
                    if((!tbDetailPK.getValueAt(i,4).toString().equals(""))&&tbDetailPK.getValueAt(i,0).toString().equals("true")){  
                        Sequel.menyimpan("template_pemeriksaan_dokter_detail_permintaan_lab","?,?,?","Detail Pemeriksaan Laboratorium PK",3,new String[]{
                            Kd.getText(),tbDetailPK.getValueAt(i,5).toString(),tbDetailPK.getValueAt(i,4).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanPA.getRowCount();i++){ 
                    if(tbPermintaanPA.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_permintaan_lab","?,?","Pemeriksaan Laboratorium PA",2,new String[]{
                            Kd.getText(),tbPermintaanPA.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanMB.getRowCount();i++){ 
                    if(tbPermintaanMB.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_permintaan_lab","?,?","Pemeriksaan Laboratorium PK",2,new String[]{
                            Kd.getText(),tbPermintaanMB.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbDetailMB.getRowCount();i++){ 
                    if((!tbDetailMB.getValueAt(i,4).toString().equals(""))&&tbDetailMB.getValueAt(i,0).toString().equals("true")){  
                        Sequel.menyimpan("template_pemeriksaan_dokter_detail_permintaan_lab","?,?,?","Detail Pemeriksaan Laboratorium PK",3,new String[]{
                            Kd.getText(),tbDetailMB.getValueAt(i,5).toString(),tbDetailMB.getValueAt(i,4).toString()
                        });
                    }
                }
                for(i=0;i<tbObatNonRacikan.getRowCount();i++){ 
                    if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,1).toString())>0){  
                        if(tbObatNonRacikan.getValueAt(i,0).toString().equals("true")){
                            if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,9).toString())>0){
                                Sequel.menyimpan("template_pemeriksaan_dokter_resep","?,?,?,?","Obat Non Racikan",4,new String[]{
                                    Kd.getText(),tbObatNonRacikan.getValueAt(i,2).toString(),""+(Double.parseDouble(tbObatNonRacikan.getValueAt(i,1).toString())/Valid.SetAngka(tbObatNonRacikan.getValueAt(i,9).toString())),tbObatNonRacikan.getValueAt(i,7).toString()
                                });
                            }else{
                                Sequel.menyimpan("template_pemeriksaan_dokter_resep","?,?,?,?","Obat Non Racikan",4,new String[]{
                                    Kd.getText(),tbObatNonRacikan.getValueAt(i,2).toString(),""+Double.parseDouble(tbObatNonRacikan.getValueAt(i,1).toString()),tbObatNonRacikan.getValueAt(i,7).toString()
                                });
                            }
                        }else{
                            Sequel.menyimpan("template_pemeriksaan_dokter_resep","?,?,?,?","Obat Non Racikan",4,new String[]{
                                Kd.getText(),tbObatNonRacikan.getValueAt(i,2).toString(),""+Double.parseDouble(tbObatNonRacikan.getValueAt(i,1).toString()),tbObatNonRacikan.getValueAt(i,7).toString()
                            });
                        }
                    }
                }
                for(i=0;i<tbObatRacikan.getRowCount();i++){ 
                    if(Valid.SetAngka(tbObatRacikan.getValueAt(i,4).toString())>0){ 
                        Sequel.menyimpan("template_pemeriksaan_dokter_resep_racikan","?,?,?,?,?,?,?","Obat Racikan",7,new String[]{
                           Kd.getText(),tbObatRacikan.getValueAt(i,0).toString(),tbObatRacikan.getValueAt(i,1).toString(),
                           tbObatRacikan.getValueAt(i,2).toString(),tbObatRacikan.getValueAt(i,4).toString(),
                           tbObatRacikan.getValueAt(i,5).toString(),tbObatRacikan.getValueAt(i,6).toString()
                        });
                    }
                }
                for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
                        Sequel.menyimpan("template_pemeriksaan_dokter_resep_racikan_detail","?,?,?,?,?,?,?","Detail Obat Racikan",7,new String[]{
                           Kd.getText(),tbDetailObatRacikan.getValueAt(i,0).toString(),tbDetailObatRacikan.getValueAt(i,1).toString(),
                           tbDetailObatRacikan.getValueAt(i,6).toString(),tbDetailObatRacikan.getValueAt(i,8).toString(),
                           tbDetailObatRacikan.getValueAt(i,9).toString(),tbDetailObatRacikan.getValueAt(i,10).toString()
                        });
                    }
                }
                for(i=0;i<tbTindakan.getRowCount();i++){ 
                    if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_tindakan","?,?","Tindakan Dokter",2,new String[]{
                            Kd.getText(),tbTindakan.getValueAt(i,1).toString()
                        });
                    }
                }
                tabMode.addRow(new Object[]{
                    Kd.getText(),KdDokter.getText(),NmDokter.getText(),Subjek.getText(),Objek.getText(),Asesmen.getText(),Plan.getText(),Instruksi.getText(),Evaluasi.getText()
                });
                emptTeks();
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Subjek,BtnBatal);
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
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        //Valid.pindah(evt,TCari,Nm,TCari);
    }//GEN-LAST:event_KdKeyPressed

    private void SubjekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SubjekKeyPressed
        Valid.pindah2(evt,CariTindakan,Objek);
    }//GEN-LAST:event_SubjekKeyPressed

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

    private void ObjekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObjekKeyPressed
        Valid.pindah2(evt,Subjek,Asesmen);
    }//GEN-LAST:event_ObjekKeyPressed

    private void AsesmenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsesmenKeyPressed
        Valid.pindah2(evt,Objek,Plan);
    }//GEN-LAST:event_AsesmenKeyPressed

    private void PlanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PlanKeyPressed
        Valid.pindah2(evt,Asesmen,Instruksi);
    }//GEN-LAST:event_PlanKeyPressed

    private void InstruksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InstruksiKeyPressed
        Valid.pindah2(evt,Plan,Evaluasi);
    }//GEN-LAST:event_InstruksiKeyPressed

    private void EvaluasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EvaluasiKeyPressed
        Valid.pindah2(evt,Instruksi,Diagnosa);
    }//GEN-LAST:event_EvaluasiKeyPressed

    private void BtnCariPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakitActionPerformed
        tampildiagnosa();
    }//GEN-LAST:event_BtnCariPenyakitActionPerformed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampildiagnosa();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Evaluasi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Prosedur.requestFocus();
        }
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void ProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilprosedure();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariRadiologi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Diagnosa.requestFocus();
        }
    }//GEN-LAST:event_ProsedurKeyPressed

    private void BtnCariProsedurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariProsedurActionPerformed
        tampilprosedure();
    }//GEN-LAST:event_BtnCariProsedurActionPerformed

    private void BtnCariRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariRadiologiActionPerformed
        tampilRadiologi2();
    }//GEN-LAST:event_BtnCariRadiologiActionPerformed

    private void CariRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariRadiologiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilRadiologi2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariPK.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Prosedur.requestFocus();
        }
    }//GEN-LAST:event_CariRadiologiKeyPressed

    private void CariPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariPKKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilPK2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariDetailPK.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariRadiologi.requestFocus();
        }
    }//GEN-LAST:event_CariPKKeyPressed

    private void BtnCariLaboratoriumPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariLaboratoriumPKActionPerformed
        tampilPK2();
    }//GEN-LAST:event_BtnCariLaboratoriumPKActionPerformed

    private void CariDetailPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariDetailPKKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilDetailPK();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariPA.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariPK.requestFocus();
        }
    }//GEN-LAST:event_CariDetailPKKeyPressed

    private void BtnDetailLaboratPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDetailLaboratPKActionPerformed
        tampilDetailPK();
    }//GEN-LAST:event_BtnDetailLaboratPKActionPerformed

    private void CariPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariPAKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilPA2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariMB.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariDetailPK.requestFocus();
        }
    }//GEN-LAST:event_CariPAKeyPressed

    private void BtnCariPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPAActionPerformed
        tampilPA2();
    }//GEN-LAST:event_BtnCariPAActionPerformed

    private void CariMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariMBKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilMB2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariDetailMB.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariPA.requestFocus();
        }
    }//GEN-LAST:event_CariMBKeyPressed

    private void BtnCariMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariMBActionPerformed
        tampilMB2();
    }//GEN-LAST:event_BtnCariMBActionPerformed

    private void CariDetailMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariDetailMBKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilDetailMB();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariObatNonRacikan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariMB.requestFocus();
        }
    }//GEN-LAST:event_CariDetailMBKeyPressed

    private void BtnCariDetailMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariDetailMBActionPerformed
        tampilDetailMB();
    }//GEN-LAST:event_BtnCariDetailMBActionPerformed

    private void BtnCariObatNonRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariObatNonRacikanActionPerformed
        tampilObatNonRacikan2();
    }//GEN-LAST:event_BtnCariObatNonRacikanActionPerformed

    private void CariObatNonRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariObatNonRacikanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilObatNonRacikan2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariObatRacikan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariDetailMB.requestFocus();
        }
    }//GEN-LAST:event_CariObatNonRacikanKeyPressed

    private void CariObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariObatRacikanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariObatRacikanActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            CariTindakan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariObatNonRacikan.requestFocus();
        }
    }//GEN-LAST:event_CariObatRacikanKeyPressed

    private void BtnCariObatRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariObatRacikanActionPerformed
        if(tbObatRacikan.getRowCount()!=0){
            if(tbObatRacikan.getSelectedRow()!= -1){
                if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString().equals("")||
                        tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).toString().equals("")||
                        tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),2).toString().equals("")||
                        tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),3).toString().equals("")||
                        tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).toString().equals("")||
                        tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),5).toString().equals("")||
                        tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),6).toString().equals("")){
                    JOptionPane.showMessageDialog(null,"Silahkan lengkapi data racikan..!!");
                }else{
                    tampilDetailObatRacikan();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih racikan..!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan masukkan racikan..!!");
        }
    }//GEN-LAST:event_BtnCariObatRacikanActionPerformed

    private void CariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CariTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilTindakan2();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            CariObatRacikan.requestFocus();
        }
    }//GEN-LAST:event_CariTindakanKeyPressed

    private void BtnCariTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariTindakanActionPerformed
        tampilTindakan2();
    }//GEN-LAST:event_BtnCariTindakanActionPerformed

    private void BtnAllPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPenyakitActionPerformed
        Diagnosa.setText("");
        tampildiagnosa();
    }//GEN-LAST:event_BtnAllPenyakitActionPerformed

    private void BtnAllProsedurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllProsedurActionPerformed
        Prosedur.setText("");
        tampilprosedure();
    }//GEN-LAST:event_BtnAllProsedurActionPerformed

    private void BtnAllRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllRadiologiActionPerformed
        CariRadiologi.setText("");
        tampilRadiologi();
        tampilRadiologi2();
    }//GEN-LAST:event_BtnAllRadiologiActionPerformed

    private void BtnAllPatologiKlinisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPatologiKlinisActionPerformed
        CariPA.setText("");
        tampilPK();
        tampilPK2();
    }//GEN-LAST:event_BtnAllPatologiKlinisActionPerformed

    private void BtnAllDetailLaboratPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllDetailLaboratPKActionPerformed
        CariDetailPK.setText("");
        tampilDetailPK();
    }//GEN-LAST:event_BtnAllDetailLaboratPKActionPerformed

    private void BtnAllPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPAActionPerformed
        CariPA.setText("");
        tampilPA();
        tampilPA2();
    }//GEN-LAST:event_BtnAllPAActionPerformed

    private void BtnAllMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllMBActionPerformed
        CariMB.setText("");
        tampilMB();
        tampilMB2();
    }//GEN-LAST:event_BtnAllMBActionPerformed

    private void BtnAllDetailMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllDetailMBActionPerformed
        CariDetailMB.setText("");
        tampilDetailMB();
    }//GEN-LAST:event_BtnAllDetailMBActionPerformed

    private void BtnAllObatNonRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllObatNonRacikanActionPerformed
        CariObatNonRacikan.setText("");
        tampilObatNonRacikan();
        tampilObatNonRacikan2();
    }//GEN-LAST:event_BtnAllObatNonRacikanActionPerformed

    private void BtnAllObatRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllObatRacikanActionPerformed
        CariObatRacikan.setText("");
        tampilObatNonRacikan();
        BtnCariObatRacikanActionPerformed(null);
    }//GEN-LAST:event_BtnAllObatRacikanActionPerformed

    private void BtnAllTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllTindakanActionPerformed
        CariTindakan.setText("");
        tampilTindakan();
        tampilTindakan2();
    }//GEN-LAST:event_BtnAllTindakanActionPerformed

    private void tbPermintaanPKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPermintaanPKMouseClicked
        if(tabModePK.getRowCount()!=0){
            try {
                Valid.tabelKosong(tabModeDetailPK);
                tampilDetailPK();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPermintaanPKMouseClicked

    private void tbPermintaanMBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPermintaanMBMouseClicked
        if(tabModeMB.getRowCount()!=0){
            try {
                Valid.tabelKosong(tabModeDetailMB);
                tampilDetailMB();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPermintaanMBMouseClicked

    private void BtnTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambah1ActionPerformed
        i=tabModeObatRacikan.getRowCount()+1;
        if(i==99){
            JOptionPane.showMessageDialog(null,"Maksimal 98 Racikan..!!");
        }else{
            tabModeObatRacikan.addRow(new Object[]{""+i,"","","","","",""});
        }
    }//GEN-LAST:event_BtnTambah1ActionPerformed

    private void BtnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapus1ActionPerformed
        if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),5).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),6).equals("")){
            tabModeObatRacikan.removeRow(tbObatRacikan.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf sudah terisi, gak boleh dihapus..!!");
        }
    }//GEN-LAST:event_BtnHapus1ActionPerformed

    private void tbObatNonRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatNonRacikanKeyPressed
        if(tbObatNonRacikan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                i=tbObatNonRacikan.getSelectedColumn();
                if((i==1)||(i==7)){
                    if(tbObatNonRacikan.getSelectedRow()!= -1){
                        tbObatNonRacikan.setValueAt("",tbObatNonRacikan.getSelectedRow(),i);
                    }
                }   
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                i=tbObatNonRacikan.getSelectedColumn();
                if(i!=11){
                    TCari.requestFocus();
                }                
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbObatNonRacikan.getSelectedColumn();
                if(i==7){
                    index=1;
                    aturanpakai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }
            }  
        }
    }//GEN-LAST:event_tbObatNonRacikanKeyPressed

    private void tbObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatRacikanKeyPressed
        if(tbObatRacikan.getRowCount()!=0){
            i=tbObatRacikan.getSelectedColumn();
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                if(i==5){
                    index=2;
                    aturanpakai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }else if(i==3){
                    if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).equals("")){
                        JOptionPane.showMessageDialog(null,"Silahkan masukkan nama racikan..!!");
                        tbObatRacikan.requestFocus();
                    }else{
                        metoderacik.isCek();
                        metoderacik.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        metoderacik.setLocationRelativeTo(internalFrame1);
                        metoderacik.setVisible(true);
                    }
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                if(i==6){
                    TCari.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_tbObatRacikanKeyPressed

    private void tbDetailObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanKeyPressed
        if(tbDetailObatRacikan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                i=tbDetailObatRacikan.getSelectedColumn();
                if((i==8)||(i==5)||(i==10)||(i==11)){
                    try {
                        if(!tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),9).toString())){
                            if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),8).toString())==0){
                                JOptionPane.showMessageDialog(null,"Kapasitas obat masih kosong..!!!");
                            }else{
                                tbDetailObatRacikan.setValueAt(Valid.SetAngka8(Valid.SetAngka(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),5).toString())*
                                    (Valid.SetAngka(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),6).toString())/Valid.SetAngka(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),8).toString())),1),
                                        tbDetailObatRacikan.getSelectedRow(),9);
                            }                                
                        }
                    } catch (Exception e) {
                        tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),9);
                    }      
                }else if(i==9){
                    if(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),9).toString().contains("%")){
                        if(tbDetailObatRacikan.getSelectedRow()!= -1){
                            try {
                                r=tbDetailObatRacikan.getSelectedRow();
                                noracik=tbDetailObatRacikan.getValueAt(r,0).toString();
                                jumlahracik=0;
                                persenracik=Double.parseDouble(tbDetailObatRacikan.getValueAt(r,9).toString().replaceAll("%",""));
                                kapasitasracik=Double.parseDouble(tbDetailObatRacikan.getValueAt(r,5).toString());
                                for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                                    if(noracik==tbDetailObatRacikan.getValueAt(i,0).toString()){
                                        if(!tbDetailObatRacikan.getValueAt(i,9).toString().contains("%")){
                                            jumlahracik=jumlahracik+(Double.parseDouble(tbDetailObatRacikan.getValueAt(i,5).toString())*
                                                    Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString()));
                                        }
                                    }
                                }
                                tbDetailObatRacikan.setValueAt(Valid.SetAngka8((jumlahracik*(persenracik/100))/kapasitasracik,1),r,10);
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,r,10);
                            }
                        }
                    }else{
                        if(tbDetailObatRacikan.getSelectedRow()!= -1){
                            try {
                                tbDetailObatRacikan.setValueAt(Valid.SetAngka8((Double.parseDouble(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).toString())
                                                *Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),9).toString()))
                                                /Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),5).toString()),1)
                                                ,tbDetailObatRacikan.getSelectedRow(),10);
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),10);
                            }
                        }
                    }  
                }
            }
        }
    }//GEN-LAST:event_tbDetailObatRacikanKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            isDetail();
            panggilDetail();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbDetailPK.getRowCount();i++){
            tbDetailPK.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for(i=0;i<tbDetailPK.getRowCount();i++){
            tbDetailPK.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void tbDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDiagnosaKeyPressed
        if(tabModeDiagnosa.getRowCount()!=0){
            try {
                if(tbDiagnosa.getSelectedRow()!= -1){
                    if(tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString().equals("true")&&tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),7).toString().equals("0")){
                        tbDiagnosa.setValueAt(false,tbDiagnosa.getSelectedRow(),0);
                        tbDiagnosa.setValueAt("",tbDiagnosa.getSelectedRow(),11);
                        JOptionPane.showMessageDialog(null,"Maaf, kode diagnosa "+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),1).toString()+" valid code 0. Hanya berfungsi sebagai header..!!");
                    }else if(tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString().equals("true")&&tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),7).toString().equals("1")){
                        if(tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),8).toString().equals("N")&&tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),11).toString().equals("1")){
                            tbDiagnosa.setValueAt(false,tbDiagnosa.getSelectedRow(),0);
                            tbDiagnosa.setValueAt("",tbDiagnosa.getSelectedRow(),11);
                            JOptionPane.showMessageDialog(null,"Maaf, kode diagnosa "+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),1).toString()+" Accpdx N. Tidak bisa menjadi diagnosa utama..!!");
                        }
                    }
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDiagnosaKeyPressed

    private void tbDiagnosaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDiagnosaMouseClicked
        if(tabModeDiagnosa.getRowCount()!=0){
            try {
                if(tbDiagnosa.getSelectedRow()!= -1){
                    if(tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString().equals("true")&&tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),7).toString().equals("0")){
                        tbDiagnosa.setValueAt(false,tbDiagnosa.getSelectedRow(),0);
                        tbDiagnosa.setValueAt("",tbDiagnosa.getSelectedRow(),11);
                        JOptionPane.showMessageDialog(null,"Maaf, kode diagnosa "+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),1).toString()+" valid code 0. Hanya berfungsi sebagai header..!!");
                    }else if(tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString().equals("true")&&tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),7).toString().equals("1")){
                        if(tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),8).toString().equals("N")&&tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),11).toString().equals("1")){
                            tbDiagnosa.setValueAt(false,tbDiagnosa.getSelectedRow(),0);
                            tbDiagnosa.setValueAt("",tbDiagnosa.getSelectedRow(),11);
                            JOptionPane.showMessageDialog(null,"Maaf, kode diagnosa "+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),1).toString()+" Accpdx N. Tidak bisa menjadi diagnosa utama..!!");
                        }
                    }
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDiagnosaMouseClicked

    private void tbDiagnosaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDiagnosaPropertyChange
        i=1;
        for(int z=0;z<tbDiagnosa.getRowCount();z++){ 
            if(tbDiagnosa.getValueAt(z,0).toString().equals("true")){
                tbDiagnosa.setValueAt(i,z,11);
                if(tbDiagnosa.getValueAt(z,0).toString().equals("true")&&tbDiagnosa.getValueAt(z,7).toString().equals("0")){
                    tbDiagnosa.setValueAt(false,z,0);
                    tbDiagnosa.setValueAt("",z,11);
                    JOptionPane.showMessageDialog(null,"Maaf, kode diagnosa "+tbDiagnosa.getValueAt(z,1).toString()+" valid code 0. Hanya berfungsi sebagai header..!!");
                }else if(tbDiagnosa.getValueAt(z,0).toString().equals("true")&&tbDiagnosa.getValueAt(z,7).toString().equals("1")){
                    if(tbDiagnosa.getValueAt(z,8).toString().equals("N")&&tbDiagnosa.getValueAt(z,11).toString().equals("1")){
                        tbDiagnosa.setValueAt(false,z,0);
                        tbDiagnosa.setValueAt("",z,11);
                        JOptionPane.showMessageDialog(null,"Maaf, kode diagnosa "+tbDiagnosa.getValueAt(z,1).toString()+" Accpdx N. Tidak bisa menjadi diagnosa utama..!!");
                    }else{
                        i++;
                    }
                }
            }else{
                tbDiagnosa.setValueAt("",z,11);
            }
        }
    }//GEN-LAST:event_tbDiagnosaPropertyChange

    private void tbProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbProsedurKeyPressed
        if(tabModeProsedur.getRowCount()!=0){
            try {
                if(tbProsedur.getSelectedRow()!= -1){
                    if(tbProsedur.getValueAt(tbProsedur.getSelectedRow(),0).toString().equals("true")){
                        if(tbProsedur.getValueAt(tbProsedur.getSelectedRow(),4).toString().equals("0")){
                            tbProsedur.setValueAt(false,tbProsedur.getSelectedRow(),0);
                            tbProsedur.setValueAt("",tbProsedur.getSelectedRow(),7);
                            tbProsedur.setValueAt("",tbProsedur.getSelectedRow(),8);
                            JOptionPane.showMessageDialog(null,"Maaf, kode prosedur "+tbProsedur.getValueAt(tbProsedur.getSelectedRow(),1).toString()+" valid code 0. Hanya berfungsi sebagai header..!!");
                        }else if(tbProsedur.getValueAt(tbProsedur.getSelectedRow(),4).toString().equals("1")){
                            if(tbProsedur.getValueAt(tbProsedur.getSelectedRow(),5).toString().equals("N")&&tbProsedur.getValueAt(tbProsedur.getSelectedRow(),7).toString().equals("1")){
                                tbProsedur.setValueAt(false,tbProsedur.getSelectedRow(),0);
                                tbProsedur.setValueAt("",tbProsedur.getSelectedRow(),7);
                                tbProsedur.setValueAt("",tbProsedur.getSelectedRow(),8);
                                JOptionPane.showMessageDialog(null,"Maaf, kode prosedur "+tbProsedur.getValueAt(tbProsedur.getSelectedRow(),1).toString()+" Accpdx N. Tidak bisa menjadi prosedur utama..!!");
                            }
                        }
                    }
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbProsedurKeyPressed

    private void tbProsedurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProsedurMouseClicked
        if(tabModeProsedur.getRowCount()!=0){
            try {
                if(tbProsedur.getSelectedRow()!= -1){
                    if(tbProsedur.getValueAt(tbProsedur.getSelectedRow(),0).toString().equals("true")){
                        if(tbProsedur.getValueAt(tbProsedur.getSelectedRow(),4).toString().equals("0")){
                            tbProsedur.setValueAt(false,tbProsedur.getSelectedRow(),0);
                            tbProsedur.setValueAt("",tbProsedur.getSelectedRow(),7);
                            tbProsedur.setValueAt("",tbProsedur.getSelectedRow(),8);
                            JOptionPane.showMessageDialog(null,"Maaf, kode prosedur "+tbProsedur.getValueAt(tbProsedur.getSelectedRow(),1).toString()+" valid code 0. Hanya berfungsi sebagai header..!!");
                        }else if(tbProsedur.getValueAt(tbProsedur.getSelectedRow(),4).toString().equals("1")){
                            if(tbProsedur.getValueAt(tbProsedur.getSelectedRow(),5).toString().equals("N")&&tbProsedur.getValueAt(tbProsedur.getSelectedRow(),7).toString().equals("1")){
                                tbProsedur.setValueAt(false,tbProsedur.getSelectedRow(),0);
                                tbProsedur.setValueAt("",tbProsedur.getSelectedRow(),7);
                                tbProsedur.setValueAt("",tbProsedur.getSelectedRow(),8);
                                JOptionPane.showMessageDialog(null,"Maaf, kode prosedur "+tbProsedur.getValueAt(tbProsedur.getSelectedRow(),1).toString()+" Accpdx N. Tidak bisa menjadi prosedur utama..!!");
                            }
                        }
                    }
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbProsedurMouseClicked

    private void tbProsedurPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbProsedurPropertyChange
        i=1;
        for(int z=0;z<tbProsedur.getRowCount();z++){ 
            if(tbProsedur.getValueAt(z,0).toString().equals("true")){
                tbProsedur.setValueAt(i,z,7);
                if(tbProsedur.getValueAt(z,8).toString().equals("")){
                    tbProsedur.setValueAt("1",z,8);
                }
                if(tbProsedur.getValueAt(z,0).toString().equals("true")&&tbProsedur.getValueAt(z,4).toString().equals("0")){
                    tbProsedur.setValueAt(false,z,0);
                    tbProsedur.setValueAt("",z,7);
                    tbProsedur.setValueAt("",z,8);
                    JOptionPane.showMessageDialog(null,"Maaf, kode prosedur "+tbProsedur.getValueAt(z,1).toString()+" valid code 0. Hanya berfungsi sebagai header..!!");
                }else if(tbProsedur.getValueAt(z,0).toString().equals("true")&&tbProsedur.getValueAt(z,4).toString().equals("1")){
                    if(tbProsedur.getValueAt(z,5).toString().equals("N")&&tbProsedur.getValueAt(z,7).toString().equals("1")){
                        tbProsedur.setValueAt(false,z,0);
                        tbProsedur.setValueAt("",z,7);
                        tbProsedur.setValueAt("",z,8);
                        JOptionPane.showMessageDialog(null,"Maaf, kode prosedur "+tbProsedur.getValueAt(z,1).toString()+" Accpdx N. Tidak bisa menjadi prosedur utama..!!");
                    }else{
                        i++;
                    }
                }
            }else{
                tbProsedur.setValueAt("",z,7);
                tbProsedur.setValueAt("",z,8);
            }
        }
    }//GEN-LAST:event_tbProsedurPropertyChange

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MasterTemplatePemeriksaanDokter dialog = new MasterTemplatePemeriksaanDokter(new javax.swing.JFrame(), true);
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
    private widget.TextArea Asesmen;
    private widget.Button BtnAll;
    private widget.Button BtnAllDetailLaboratPK;
    private widget.Button BtnAllDetailMB;
    private widget.Button BtnAllMB;
    private widget.Button BtnAllObatNonRacikan;
    private widget.Button BtnAllObatRacikan;
    private widget.Button BtnAllPA;
    private widget.Button BtnAllPatologiKlinis;
    private widget.Button BtnAllPenyakit;
    private widget.Button BtnAllProsedur;
    private widget.Button BtnAllRadiologi;
    private widget.Button BtnAllTindakan;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariDetailMB;
    private widget.Button BtnCariLaboratoriumPK;
    private widget.Button BtnCariMB;
    private widget.Button BtnCariObatNonRacikan;
    private widget.Button BtnCariObatRacikan;
    private widget.Button BtnCariPA;
    private widget.Button BtnCariPenyakit;
    private widget.Button BtnCariProsedur;
    private widget.Button BtnCariRadiologi;
    private widget.Button BtnCariTindakan;
    private widget.Button BtnDetailLaboratPK;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnHapus1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah1;
    public widget.TextBox CariDetailMB;
    public widget.TextBox CariDetailPK;
    public widget.TextBox CariMB;
    public widget.TextBox CariObatNonRacikan;
    public widget.TextBox CariObatRacikan;
    public widget.TextBox CariPA;
    public widget.TextBox CariPK;
    public widget.TextBox CariRadiologi;
    public widget.TextBox CariTindakan;
    private widget.CekBox ChkAccor;
    public widget.TextBox Diagnosa;
    private widget.TextArea Evaluasi;
    private widget.PanelBiasa FormDetail;
    private widget.PanelBiasa FormInput;
    private widget.TextArea Instruksi;
    private widget.TextBox Kd;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox NmDokter;
    private widget.TextArea Objek;
    private widget.PanelBiasa PanelAccor;
    private widget.TextArea Plan;
    private javax.swing.JPopupMenu Popup;
    public widget.TextBox Prosedur;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll13;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextArea Subjek;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    public widget.Table tbDetailMB;
    public widget.Table tbDetailObatRacikan;
    public widget.Table tbDetailPK;
    public widget.Table tbDiagnosa;
    private widget.Table tbDokter;
    public widget.Table tbObatNonRacikan;
    public widget.Table tbObatRacikan;
    public widget.Table tbPermintaanMB;
    public widget.Table tbPermintaanPA;
    public widget.Table tbPermintaanPK;
    public widget.Table tbPermintaanRadiologi;
    public widget.Table tbProsedur;
    public widget.Table tbTindakan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(akses.getkode().equals("Admin Utama")){
                ps=koneksi.prepareStatement(
                        "select template_pemeriksaan_dokter.no_template,template_pemeriksaan_dokter.kd_dokter,dokter.nm_dokter,"+
                        "template_pemeriksaan_dokter.keluhan,template_pemeriksaan_dokter.pemeriksaan,template_pemeriksaan_dokter.penilaian,"+
                        "template_pemeriksaan_dokter.rencana,template_pemeriksaan_dokter.instruksi,template_pemeriksaan_dokter.evaluasi "+
                        "from template_pemeriksaan_dokter inner join dokter on dokter.kd_dokter=template_pemeriksaan_dokter.kd_dokter "+
                        (TCari.getText().equals("")?"":"where template_pemeriksaan_dokter.no_template like ? or template_pemeriksaan_dokter.nm_dokter like ? or "+
                        "template_pemeriksaan_dokter.keluhan like ? or template_pemeriksaan_dokter.pemeriksaan like ? or "+
                        "template_pemeriksaan_dokter.penilaian like ? or template_pemeriksaan_dokter.rencana like ? or "+
                        "template_pemeriksaan_dokter.instruksi like ? or template_pemeriksaan_dokter.evaluasi like ? ")+
                        "order by template_pemeriksaan_dokter.no_template");
            }else{
                ps=koneksi.prepareStatement(
                        "select template_pemeriksaan_dokter.no_template,template_pemeriksaan_dokter.kd_dokter,dokter.nm_dokter,"+
                        "template_pemeriksaan_dokter.keluhan,template_pemeriksaan_dokter.pemeriksaan,template_pemeriksaan_dokter.penilaian,"+
                        "template_pemeriksaan_dokter.rencana,template_pemeriksaan_dokter.instruksi,template_pemeriksaan_dokter.evaluasi "+
                        "from template_pemeriksaan_dokter inner join dokter on dokter.kd_dokter=template_pemeriksaan_dokter.kd_dokter "+
                        "where template_pemeriksaan_dokter.kd_dokter=? "+(TCari.getText().equals("")?"":"and (template_pemeriksaan_dokter.no_template like ? or "+
                        "template_pemeriksaan_dokter.keluhan like ? or template_pemeriksaan_dokter.pemeriksaan like ? or "+
                        "template_pemeriksaan_dokter.penilaian like ? or template_pemeriksaan_dokter.rencana like ? or "+
                        "template_pemeriksaan_dokter.instruksi like ? or template_pemeriksaan_dokter.evaluasi like ?) ")+
                        "order by template_pemeriksaan_dokter.no_template");
            }
                
            try {
                if(akses.getkode().equals("Admin Utama")){
                    if(!TCari.getText().equals("")){
                        ps.setString(1,"%"+TCari.getText().trim()+"%");
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                        ps.setString(7,"%"+TCari.getText().trim()+"%");
                        ps.setString(8,"%"+TCari.getText().trim()+"%");
                    }
                }else{
                    ps.setString(1,akses.getkode());
                    if(!TCari.getText().equals("")){
                        ps.setString(2,"%"+TCari.getText().trim()+"%");
                        ps.setString(3,"%"+TCari.getText().trim()+"%");
                        ps.setString(4,"%"+TCari.getText().trim()+"%");
                        ps.setString(5,"%"+TCari.getText().trim()+"%");
                        ps.setString(6,"%"+TCari.getText().trim()+"%");
                        ps.setString(7,"%"+TCari.getText().trim()+"%");
                        ps.setString(8,"%"+TCari.getText().trim()+"%");
                    }
                }
                
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_template"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("keluhan"),rs.getString("pemeriksaan"),rs.getString("penilaian"),rs.getString("rencana"),rs.getString("instruksi"),rs.getString("evaluasi")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
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
        Kd.setText("");
        Subjek.setText("");
        Objek.setText("");
        Asesmen.setText("");
        Plan.setText("");
        Instruksi.setText("");
        Evaluasi.setText("");
        Diagnosa.setText("");
        Prosedur.setText(""); 
        CariRadiologi.setText("");
        CariPK.setText("");
        CariDetailPK.setText(""); 
        CariPA.setText("");
        CariMB.setText("");
        CariDetailMB.setText("");
        CariObatNonRacikan.setText("");
        CariObatRacikan.setText("");
        CariTindakan.setText("");
        Valid.tabelKosong(tabModeDiagnosa);
        Valid.tabelKosong(tabModeProsedur);
        Valid.tabelKosong(tabModeRadiologi);
        Valid.tabelKosong(tabModePK);
        Valid.tabelKosong(tabModeDetailPK);
        Valid.tabelKosong(tabModePA);
        Valid.tabelKosong(tabModeMB);
        Valid.tabelKosong(tabModeDetailMB);
        Valid.tabelKosong(tabModeObatUmum);
        Valid.tabelKosong(tabModeObatRacikan);
        Valid.tabelKosong(tabModeDetailObatRacikan);
        Valid.tabelKosong(TabModeTindakan);
        Valid.autoNomer("template_pemeriksaan_dokter","TPD",16,Kd);
        TabRawat.setSelectedIndex(0);
        Subjek.requestFocus();
    }

    private void getData() {
        if(tbDokter.getSelectedRow()!= -1){
            Kd.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
            Subjek.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString());
            Objek.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),4).toString());
            Asesmen.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),5).toString());
            Plan.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),6).toString());
            Instruksi.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),7).toString());
            Evaluasi.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),8).toString());
        }
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.gettemplate_pemeriksaan());
        BtnHapus.setEnabled(akses.gettemplate_pemeriksaan());
        BtnEdit.setEnabled(akses.gettemplate_pemeriksaan());
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
    
    private void tampildiagnosa() {
        try{
            jml=0;
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml];
            kode=new String[jml];
            nama=new String[jml];
            ciripny=new String[jml];
            keterangan=new String[jml];
            kategori=new String[jml];
            cirium=new String[jml];
            validcode=new String[jml];
            accpdx=new String[jml];
            asterisk=new String[jml];
            im=new String[jml];
            urut=new String[jml];

            index=0; 
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbDiagnosa.getValueAt(i,1).toString();
                    nama[index]=tbDiagnosa.getValueAt(i,2).toString();
                    ciripny[index]=tbDiagnosa.getValueAt(i,3).toString();
                    keterangan[index]=tbDiagnosa.getValueAt(i,4).toString();
                    kategori[index]=tbDiagnosa.getValueAt(i,5).toString();
                    cirium[index]=tbDiagnosa.getValueAt(i,6).toString();
                    validcode[index]=tbDiagnosa.getValueAt(i,7).toString();
                    accpdx[index]=tbDiagnosa.getValueAt(i,8).toString();
                    asterisk[index]=tbDiagnosa.getValueAt(i,9).toString();
                    im[index]=tbDiagnosa.getValueAt(i,10).toString();
                    urut[index]=tbDiagnosa.getValueAt(i,11).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeDiagnosa);
            for(i=0;i<jml;i++){
                tabModeDiagnosa.addRow(new Object[] {pilih[i],kode[i],nama[i],ciripny[i],keterangan[i],kategori[i],cirium[i],validcode[i],accpdx[i],asterisk[i],im[i],urut[i]});
            }  
            
            pilih=null;
            kode=null;
            nama=null;
            ciripny=null;
            keterangan=null;
            kategori=null;
            cirium=null;
            validcode=null;
            accpdx=null;
            asterisk=null;
            im=null;
            urut=null;

            ps=koneksi.prepareStatement(
                    "select penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan,kategori_penyakit.nm_kategori,"+
                    "kategori_penyakit.ciri_umum,penyakit.validcode,penyakit.accpdx,penyakit.asterisk,penyakit.im from kategori_penyakit "+
                    "inner join penyakit on penyakit.kd_ktg=kategori_penyakit.kd_ktg "+
                    (Diagnosa.getText().trim().equals("")?"":"where penyakit.kd_penyakit like ? or "+
                    "penyakit.nm_penyakit like ? or penyakit.ciri_ciri like ? or penyakit.keterangan like ? or "+
                    "kategori_penyakit.nm_kategori like ? or kategori_penyakit.ciri_umum like ? ")+
                    "order by penyakit.kd_penyakit  LIMIT 1000");
            try {
                if(!Diagnosa.getText().trim().equals("")){
                    ps.setString(1,"%"+Diagnosa.getText().trim()+"%");
                    ps.setString(2,"%"+Diagnosa.getText().trim()+"%");
                    ps.setString(3,"%"+Diagnosa.getText().trim()+"%");
                    ps.setString(4,"%"+Diagnosa.getText().trim()+"%");
                    ps.setString(5,"%"+Diagnosa.getText().trim()+"%");
                    ps.setString(6,"%"+Diagnosa.getText().trim()+"%");  
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeDiagnosa.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                        rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),""
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
    }
    
    private void tampilprosedure() {
        try{
            jml=0;
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml];
            kode2=new String[jml];
            panjang=new String[jml];
            pendek=new String[jml];
            validcode=new String[jml];
            accpdx=new String[jml];
            im=new String[jml];
            urut=new String[jml];
            multy=new String[jml];
            index=0; 
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode2[index]=tbProsedur.getValueAt(i,1).toString();
                    panjang[index]=tbProsedur.getValueAt(i,2).toString();
                    pendek[index]=tbProsedur.getValueAt(i,3).toString();
                    validcode[index]=tbProsedur.getValueAt(i,4).toString();
                    accpdx[index]=tbProsedur.getValueAt(i,5).toString();
                    im[index]=tbProsedur.getValueAt(i,6).toString();
                    urut[index]=tbProsedur.getValueAt(i,7).toString();
                    multy[index]=tbProsedur.getValueAt(i,8).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeProsedur);
            for(i=0;i<jml;i++){
                tabModeProsedur.addRow(new Object[] {pilih[i],kode2[i],panjang[i],pendek[i],validcode[i],accpdx[i],im[i],urut[i],multy[i]});
            }
            
            pilih=null;
            kode2=null;
            panjang=null;
            pendek=null;
            validcode=null;
            accpdx=null;
            im=null;
            urut=null;
            multy=null;
            
            ps=koneksi.prepareStatement(
                    "select * from icd9 "+(Prosedur.getText().trim().equals("")?"":"where kode like ? or deskripsi_panjang like ? or  deskripsi_pendek like ?")+" order by kode");
            try{
                if(!Prosedur.getText().trim().equals("")){
                    ps.setString(1,"%"+Prosedur.getText().trim()+"%");
                    ps.setString(2,"%"+Prosedur.getText().trim()+"%");
                    ps.setString(3,"%"+Prosedur.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeProsedur.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),"",""
                    });
                }
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilRadiologi() {         
        try{
            file=new File("./cache/permintaanradiologi.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan from jns_perawatan_radiologi where jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kelas='-' or jns_perawatan_radiologi.kelas='Rawat Jalan') order by jns_perawatan_radiologi.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodePeriksa\":\"").append(rs.getString(1)).append("\",\"NamaPemeriksaan\":\"").append(rs.getString(2).replaceAll("\"","")).append("\"},");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"permintaanradiologi\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilRadiologi2() {         
        try{
            jml=0;
            for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){
                if(tbPermintaanRadiologi.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml];
            kode=new String[jml];
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){
                if(tbPermintaanRadiologi.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPermintaanRadiologi.getValueAt(i,1).toString();
                    nama[index]=tbPermintaanRadiologi.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeRadiologi);
            for(i=0;i<jml;i++){                
                tabModeRadiologi.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            }    
        
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/permintaanradiologi.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanradiologi");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariRadiologi.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariRadiologi.getText().toLowerCase()))){
                        tabModeRadiologi.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilPK() {         
        try{
            file=new File("./cache/permintaanpk.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from jns_perawatan_lab where jns_perawatan_lab.status='1' and jns_perawatan_lab.kategori='PK' and (jns_perawatan_lab.kelas='-' or jns_perawatan_lab.kelas='Rawat Jalan') order by jns_perawatan_lab.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodePeriksa\":\"").append(rs.getString(1)).append("\",\"NamaPemeriksaan\":\"").append(rs.getString(2).replaceAll("\"","")).append("\"},");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"permintaanpk\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilPK2() {         
        try{
            jml=0;
            for(i=0;i<tbPermintaanPK.getRowCount();i++){
                if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml];
            kode=new String[jml];
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaanPK.getRowCount();i++){
                if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPermintaanPK.getValueAt(i,1).toString();
                    nama[index]=tbPermintaanPK.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModePK);
            for(i=0;i<jml;i++){                
                tabModePK.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            }    
        
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/permintaanpk.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanpk");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariPK.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariPK.getText().toLowerCase()))){
                        tabModePK.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilDetailPK() { 
        try {
            jml=0;
            for(i=0;i<tbDetailPK.getRowCount();i++){
                if(tbDetailPK.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }
            
            pilih=new boolean[jml];
            nama=new String[jml];
            satuan=new String[jml];
            nilairujukan=new String[jml];
            kode=new String[jml];
            kode2=new String[jml];
            
            index=0; 
            for(i=0;i<tbDetailPK.getRowCount();i++){
                if(tbDetailPK.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    nama[index]=tbDetailPK.getValueAt(i,1).toString();
                    satuan[index]=tbDetailPK.getValueAt(i,2).toString();
                    nilairujukan[index]=tbDetailPK.getValueAt(i,3).toString();
                    kode[index]=tbDetailPK.getValueAt(i,4).toString();
                    kode2[index]=tbDetailPK.getValueAt(i,5).toString();
                    index++;
                }
            }
            
            Valid.tabelKosong(tabModeDetailPK);
            
            for(i=0;i<jml;i++){ 
                tabModeDetailPK.addRow(new Object[] {
                    pilih[i],nama[i],satuan[i],nilairujukan[i],kode[i],kode2[i]
                });
            }  
            
            pilih=null;
            nama=null;
            satuan=null;
            nilairujukan=null;
            kode=null;
            kode2=null;
            
            for(i=0;i<tbPermintaanPK.getRowCount();i++){ 
                if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                    tabModeDetailPK.addRow(new Object[]{false,tbPermintaanPK.getValueAt(i,2).toString(),"","","",""});
                    ps=koneksi.prepareStatement("select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from template_laboratorium where template_laboratorium.kd_jenis_prw=? and template_laboratorium.Pemeriksaan like ? order by template_laboratorium.urut");
                    try {
                        ps.setString(1,tbPermintaanPK.getValueAt(i,1).toString());
                        ps.setString(2,"%"+CariDetailPK.getText().trim()+"%");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            la="";ld="";pa="";pd="";
                            if(!rs.getString("nilai_rujukan_ld").equals("")){
                                ld="LD : "+rs.getString("nilai_rujukan_ld");
                            }
                            if(!rs.getString("nilai_rujukan_la").equals("")){
                                la=", LA : "+rs.getString("nilai_rujukan_la");
                            }
                            if(!rs.getString("nilai_rujukan_pa").equals("")){
                                pd=", PD : "+rs.getString("nilai_rujukan_pd");
                            }
                            if(!rs.getString("nilai_rujukan_pd").equals("")){
                                pa=" PA : "+rs.getString("nilai_rujukan_pa");
                            }
                            tabModeDetailPK.addRow(new Object[]{
                                false,"   "+rs.getString("Pemeriksaan"),rs.getString("satuan"),ld+la+pd+pa,rs.getString("id_template"),tbPermintaanPK.getValueAt(i,1).toString()
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
                }
            }
        } catch (Exception e) {
            System.out.println("Error Detail : "+e);
        }
    }
    
    private void tampilPA() {         
        try{
            file=new File("./cache/permintaanpa.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from jns_perawatan_lab where jns_perawatan_lab.status='1' and jns_perawatan_lab.kategori='PA' and (jns_perawatan_lab.kelas='-' or jns_perawatan_lab.kelas='Rawat Jalan') order by jns_perawatan_lab.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodePeriksa\":\"").append(rs.getString(1)).append("\",\"NamaPemeriksaan\":\"").append(rs.getString(2).replaceAll("\"","")).append("\"},");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"permintaanpa\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilPA2() {         
        try{
            jml=0;
            for(i=0;i<tbPermintaanPA.getRowCount();i++){
                if(tbPermintaanPA.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml];
            kode=new String[jml];
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaanPA.getRowCount();i++){
                if(tbPermintaanPA.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPermintaanPA.getValueAt(i,1).toString();
                    nama[index]=tbPermintaanPA.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModePA);
            for(i=0;i<jml;i++){                
                tabModePA.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            }    
        
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/permintaanpa.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanpa");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariPA.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariPA.getText().toLowerCase()))){
                        tabModePA.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilMB() {         
        try{
            file=new File("./cache/permintaanmb.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from jns_perawatan_lab where jns_perawatan_lab.status='1' and jns_perawatan_lab.kategori='MB' and (jns_perawatan_lab.kelas='-' or jns_perawatan_lab.kelas='Rawat Jalan') order by jns_perawatan_lab.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodePeriksa\":\"").append(rs.getString(1)).append("\",\"NamaPemeriksaan\":\"").append(rs.getString(2).replaceAll("\"","")).append("\"},");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"permintaanmb\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilMB2() {         
        try{
            jml=0;
            for(i=0;i<tbPermintaanMB.getRowCount();i++){
                if(tbPermintaanMB.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml];
            kode=new String[jml];
            nama=new String[jml];
            
            index=0; 
            for(i=0;i<tbPermintaanMB.getRowCount();i++){
                if(tbPermintaanMB.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbPermintaanMB.getValueAt(i,1).toString();
                    nama[index]=tbPermintaanMB.getValueAt(i,2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeMB);
            for(i=0;i<jml;i++){                
                tabModeMB.addRow(new Object[] {pilih[i],kode[i],nama[i]});
            } 
            
            pilih=null;
            kode=null;
            nama=null;
            
            myObj = new FileReader("./cache/permintaanmb.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanmb");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariMB.getText().toLowerCase())||list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariMB.getText().toLowerCase()))){
                        tabModeMB.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilDetailMB() { 
        try {
            jml=0;
            for(i=0;i<tbDetailMB.getRowCount();i++){
                if(tbDetailMB.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }
            
            pilih=new boolean[jml];
            nama=new String[jml];
            satuan=new String[jml];
            nilairujukan=new String[jml];
            kode=new String[jml];
            kode2=new String[jml];
            
            index=0; 
            for(i=0;i<tbDetailMB.getRowCount();i++){
                if(tbDetailMB.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    nama[index]=tbDetailMB.getValueAt(i,1).toString();
                    satuan[index]=tbDetailMB.getValueAt(i,2).toString();
                    nilairujukan[index]=tbDetailMB.getValueAt(i,3).toString();
                    kode[index]=tbDetailMB.getValueAt(i,4).toString();
                    kode2[index]=tbDetailMB.getValueAt(i,5).toString();
                    index++;
                }
            }
            
            Valid.tabelKosong(tabModeDetailMB);
            
            for(i=0;i<jml;i++){ 
                tabModeDetailMB.addRow(new Object[] {
                    pilih[i],nama[i],satuan[i],nilairujukan[i],kode[i],kode2[i]
                });
            }  
            
            pilih=null;
            nama=null;
            satuan=null;
            nilairujukan=null;
            kode=null;
            kode2=null;
            
            for(i=0;i<tbPermintaanMB.getRowCount();i++){ 
                if(tbPermintaanMB.getValueAt(i,0).toString().equals("true")){
                    tabModeDetailMB.addRow(new Object[]{false,tbPermintaanMB.getValueAt(i,2).toString(),"","","",""});
                    ps=koneksi.prepareStatement("select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa from template_laboratorium where template_laboratorium.kd_jenis_prw=? and template_laboratorium.Pemeriksaan like ? order by template_laboratorium.urut");
                    try {
                        ps.setString(1,tbPermintaanMB.getValueAt(i,1).toString());
                        ps.setString(2,"%"+CariDetailMB.getText().trim()+"%");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            la="";ld="";pa="";pd="";
                            if(!rs.getString("nilai_rujukan_ld").equals("")){
                                ld="LD : "+rs.getString("nilai_rujukan_ld");
                            }
                            if(!rs.getString("nilai_rujukan_la").equals("")){
                                la=", LA : "+rs.getString("nilai_rujukan_la");
                            }
                            if(!rs.getString("nilai_rujukan_pa").equals("")){
                                pd=", PD : "+rs.getString("nilai_rujukan_pd");
                            }
                            if(!rs.getString("nilai_rujukan_pd").equals("")){
                                pa=" PA : "+rs.getString("nilai_rujukan_pa");
                            }
                            tabModeDetailMB.addRow(new Object[]{
                                false,"   "+rs.getString("Pemeriksaan"),rs.getString("satuan"),ld+la+pd+pa,rs.getString("id_template"),tbPermintaanMB.getValueAt(i,1).toString()
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
                                              
                }
            }
        } catch (Exception e) {
            System.out.println("Error Detail : "+e);
        }
    }
    
    private void tampilObatNonRacikan() {         
        try{
            file=new File("./cache/permintaanobatnonracikan.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
            ps=koneksi.prepareStatement(
                    "select databarang.kode_brng,databarang.nama_brng,kodesatuan.satuan,databarang.letak_barang,jenis.nama,industrifarmasi.nama_industri,databarang.kapasitas "+
                    "from databarang inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns "+
                    "inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri where databarang.status='1' order by databarang.nama_brng");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodeBarang\":\"").append(rs.getString(1)).append("\",\"NamaBarang\":\"").append(rs.getString(2).replaceAll("\"","")).append("\",\"Satuan\":\"").append(rs.getString(3)).append("\",\"Komposisi\":\"").append(rs.getString(4)).append("\",\"JenisObat\":\"").append(rs.getString(5)).append("\",\"Industri\":\"").append(rs.getString(6)).append("\",\"Kapasitas\":\"").append(rs.getString(7)).append("\"},");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"permintaanobatnonracikan\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilObatNonRacikan2() {         
        try{
            jml=0;
            for(i=0;i<tbObatNonRacikan.getRowCount();i++){
                if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,1).toString())>0){
                    jml++;
                }
            }
            
            pilih=new boolean[jml];
            jumlah=new double[jml];
            kode=new String[jml];
            nama=new String[jml];
            satuan=new String[jml];
            cirium=new String[jml];
            kategori=new String[jml];
            keterangan=new String[jml];
            ciripny=new String[jml];
            kps=new double[jml];
            
            index=0; 
            for(i=0;i<tbObatNonRacikan.getRowCount();i++){
                if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,1).toString())>0){
                    pilih[index]=Boolean.parseBoolean(tbObatNonRacikan.getValueAt(i,0).toString());                
                    try {
                        jumlah[index]=Double.parseDouble(tbObatNonRacikan.getValueAt(i,1).toString());
                    } catch (Exception e) {
                        jumlah[index]=0;
                    }
                    kode[index]=tbObatNonRacikan.getValueAt(i,2).toString();
                    nama[index]=tbObatNonRacikan.getValueAt(i,3).toString();
                    satuan[index]=tbObatNonRacikan.getValueAt(i,4).toString();
                    cirium[index]=tbObatNonRacikan.getValueAt(i,5).toString();
                    kategori[index]=tbObatNonRacikan.getValueAt(i,6).toString();
                    keterangan[index]=tbObatNonRacikan.getValueAt(i,7).toString();
                    ciripny[index]=tbObatNonRacikan.getValueAt(i,8).toString();
                    try {
                        kps[index]=Double.parseDouble(tbObatNonRacikan.getValueAt(i,9).toString());
                    } catch (Exception e) {
                        kps[index]=0;
                    }
                    index++;
                }
            }

            Valid.tabelKosong(tabModeObatUmum);
                    
            for(i=0;i<jml;i++){                
                tabModeObatUmum.addRow(new Object[] {pilih[i],jumlah[i],kode[i],nama[i],satuan[i],cirium[i],kategori[i],keterangan[i],ciripny[i],kps[i]});
            }    
            
            pilih=null;
            jumlah=null;
            kode=null;
            nama=null;
            satuan=null;
            cirium=null;
            kategori=null;
            keterangan=null;
            ciripny=null;
            kps=null;
            
            myObj = new FileReader("./cache/permintaanobatnonracikan.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanobatnonracikan");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodeBarang").asText().toLowerCase().contains(CariObatNonRacikan.getText().toLowerCase())||list.path("NamaBarang").asText().toLowerCase().contains(CariObatNonRacikan.getText().toLowerCase())
                            ||list.path("Komposisi").asText().toLowerCase().contains(CariObatNonRacikan.getText().toLowerCase())||list.path("JenisObat").asText().toLowerCase().contains(CariObatNonRacikan.getText().toLowerCase()))){
                        tabModeObatUmum.addRow(new Object[]{
                            false,"",list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Satuan").asText(),list.path("Komposisi").asText(),list.path("JenisObat").asText(),"",list.path("Industri").asText(),list.path("Kapasitas").asDouble()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilDetailObatRacikan(){
        try {
            jml=0;
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
                if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
                    jml++;
                }
            } 

            no=new String[jml]; 
            kode=new String[jml];
            nama=new String[jml];
            satuan=new String[jml];
            kategori=new String[jml];
            kps=new double[jml];
            p1=new double[jml];
            p2=new double[jml];
            keterangan=new String[jml];
            jumlah=new double[jml];
            cirium=new String[jml];
            ciripny=new String[jml];

            index=0;        
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
                if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
                    no[index]=tbDetailObatRacikan.getValueAt(i,0).toString();
                    kode[index]=tbDetailObatRacikan.getValueAt(i,1).toString();
                    nama[index]=tbDetailObatRacikan.getValueAt(i,2).toString();
                    satuan[index]=tbDetailObatRacikan.getValueAt(i,3).toString();
                    kategori[index]=tbDetailObatRacikan.getValueAt(i,4).toString();
                    try {
                        kps[index]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,5).toString());
                    }catch (Exception e) {
                        kps[index]=0;
                    }
                    try {
                        p1[index]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,6).toString());
                    }catch (Exception e) {
                        p1[index]=0;
                    }
                    try {
                        p2[index]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,8).toString());
                    }catch (Exception e) {
                        p2[index]=0;
                    }
                    keterangan[index]=tbDetailObatRacikan.getValueAt(i,9).toString();
                    try {
                        jumlah[index]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString());
                    }catch (Exception e) {
                        jumlah[index]=0;
                    }
                    cirium[index]=tbDetailObatRacikan.getValueAt(i,11).toString();
                    ciripny[index]=tbDetailObatRacikan.getValueAt(i,12).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeDetailObatRacikan); 
            for(i=0;i<index;i++){
                tabModeDetailObatRacikan.addRow(new Object[] {
                    no[i],kode[i],nama[i],satuan[i],kategori[i],kps[i],p1[i],"/",p2[i],keterangan[i],jumlah[i],cirium[i],ciripny[i]
                });
            }
            
            no=null;
            kode=null;
            nama=null;
            satuan=null;
            kategori=null;
            kps=null;
            p1=null;
            p2=null;
            keterangan=null;
            jumlah=null;
            cirium=null;
            ciripny=null;
            
            myObj = new FileReader("./cache/permintaanobatnonracikan.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaanobatnonracikan");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodeBarang").asText().toLowerCase().contains(CariObatRacikan.getText().toLowerCase())||list.path("NamaBarang").asText().toLowerCase().contains(CariObatRacikan.getText().toLowerCase())
                            ||list.path("Komposisi").asText().toLowerCase().contains(CariObatRacikan.getText().toLowerCase())||list.path("JenisObat").asText().toLowerCase().contains(CariObatRacikan.getText().toLowerCase()))){
                        tabModeDetailObatRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),list.path("KodeBarang").asText(),list.path("NamaBarang").asText(),list.path("Satuan").asText(),
                            list.path("JenisObat").asText(),list.path("Kapasitas").asDouble(),1,"/",1,"",0,list.path("Industri").asText(),list.path("Komposisi").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilTindakan() {         
        try{
            file=new File("./cache/permintaantindakan.iyem");
            file.createNewFile();
            fileWriter = new FileWriter(file);
            StringBuilder iyembuilder = new StringBuilder();
        
            ps=koneksi.prepareStatement(
                    "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori from jns_perawatan inner join kategori_perawatan "+
                    "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori where jns_perawatan.status='1' and jns_perawatan.total_byrdr>0 order by jns_perawatan.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    iyembuilder.append("{\"KodePeriksa\":\"").append(rs.getString(1)).append("\",\"NamaPemeriksaan\":\"").append(rs.getString(2).replaceAll("\"","")).append("\",\"Kategori\":\"").append(rs.getString(3).replaceAll("\"","")).append("\"},");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            if (iyembuilder.length() > 0) {
                iyembuilder.setLength(iyembuilder.length() - 1);
                fileWriter.write("{\"permintaantindakan\":["+iyembuilder+"]}");
                fileWriter.flush();
            }
            
            fileWriter.close();
            iyembuilder=null;
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilTindakan2() {         
        try{
            jml=0;
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=new boolean[jml];
            kode=new String[jml];
            nama=new String[jml];
            kategori=new String[jml];
            
            index=0; 
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbTindakan.getValueAt(i,1).toString();
                    nama[index]=tbTindakan.getValueAt(i,2).toString();
                    kategori[index]=tbTindakan.getValueAt(i,3).toString();
                    index++;
                }
            }

            Valid.tabelKosong(TabModeTindakan);
            for(i=0;i<jml;i++){                
                TabModeTindakan.addRow(new Object[] {pilih[i],kode[i],nama[i],kategori[i]});
            }  
            
            pilih=null;
            kode=null;
            nama=null;
            kategori=null;
        
            myObj = new FileReader("./cache/permintaantindakan.iyem");
            root = mapper.readTree(myObj);
            response = root.path("permintaantindakan");
            if(response.isArray()){
                for(JsonNode list:response){
                    if((list.path("KodePeriksa").asText().toLowerCase().contains(CariTindakan.getText().toLowerCase())||
                        list.path("NamaPemeriksaan").asText().toLowerCase().contains(CariTindakan.getText().toLowerCase())||
                        list.path("Kategori").asText().toLowerCase().contains(CariTindakan.getText().toLowerCase()))){
                        TabModeTindakan.addRow(new Object[]{
                            false,list.path("KodePeriksa").asText(),list.path("NamaPemeriksaan").asText(),list.path("Kategori").asText()
                        });
                    }
                }
            }  
            myObj.close(); 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void isDetail(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame3.getWidth()-200,HEIGHT));
            FormDetail.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormDetail.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilDetail() {
        if(FormDetail.isVisible()==true){
            if(tbDokter.getSelectedRow()!= -1){
                try {
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>").append(
                            "<td valign='top' align='left' width='100%'>").append(
                                "Subjek : ").append(tabMode.getValueAt(tbDokter.getSelectedRow(),3).toString()).append(
                            "</td>").append(
                        "</tr>").append(
                        "<tr class='isi'>").append(
                            "<td valign='top' align='left' width='100%'>").append(
                                "Objek : ").append(tabMode.getValueAt(tbDokter.getSelectedRow(),4).toString()).append(
                            "</td>").append(
                        "</tr>").append(
                        "<tr class='isi'>").append(
                            "<td valign='top' align='left' width='100%'>").append(
                                "Asesmen : ").append(tabMode.getValueAt(tbDokter.getSelectedRow(),5).toString()).append(
                            "</td>").append(
                        "</tr>").append(
                        "<tr class='isi'>").append(
                            "<td valign='top' align='left' width='100%'>").append(
                                "Plan : ").append(tabMode.getValueAt(tbDokter.getSelectedRow(),6).toString()).append(
                            "</td>").append(
                        "</tr>").append(
                        "<tr class='isi'>").append(
                            "<td valign='top' align='left' width='100%'>").append(
                                "Instruksi : ").append(tabMode.getValueAt(tbDokter.getSelectedRow(),7).toString()).append(
                            "</td>").append(
                        "</tr>").append(
                        "<tr class='isi'>").append(
                            "<td valign='top' align='left' width='100%'>").append(
                                "Evaluasi : ").append(tabMode.getValueAt(tbDokter.getSelectedRow(),8).toString()).append(
                            "</td>").append(
                        "</tr>"
                    ); 
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                            "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum,penyakit.validcode,penyakit.accpdx,penyakit.asterisk,penyakit.im,"+
                            "template_pemeriksaan_dokter_penyakit.urut from template_pemeriksaan_dokter_penyakit "+
                            "inner join penyakit on penyakit.kd_penyakit=template_pemeriksaan_dokter_penyakit.kd_penyakit "+
                            "inner join kategori_penyakit on penyakit.kd_ktg=kategori_penyakit.kd_ktg where "+
                            "template_pemeriksaan_dokter_penyakit.no_template=? order by template_pemeriksaan_dokter_penyakit.urut");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Diagnosa : ").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Kode Penyakit</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='70%'>Nama Penyakit</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Urut</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModeDiagnosa);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("kd_penyakit")).append("</td>").append(
                                                "<td>").append(rs.getString("nm_penyakit")).append("</td>").append(
                                                "<td>").append(rs.getString("urut")).append("</td>").append(
                                            "</tr>"
                                );
                                tabModeDiagnosa.addRow(new Object[]{
                                    true,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)
                                });
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 

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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_prosedur.kode,icd9.deskripsi_panjang,icd9.deskripsi_pendek,icd9.validcode,icd9.accpdx,icd9.im,template_pemeriksaan_dokter_prosedur.urut,template_pemeriksaan_dokter_prosedur.jumlah "+
                            "from template_pemeriksaan_dokter_prosedur inner join icd9 on template_pemeriksaan_dokter_prosedur.kode=icd9.kode where template_pemeriksaan_dokter_prosedur.no_template=? order by template_pemeriksaan_dokter_prosedur.urut");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Prosedur : ").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Kode Prosedur</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='60%'>Nama Prosedur</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Urut</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Jumlah</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModeProsedur);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("kode")).append("</td>").append(
                                                "<td>").append(rs.getString("deskripsi_panjang")).append("</td>").append(
                                                "<td>").append(rs.getString("urut")).append("</td>").append(
                                                "<td>").append(rs.getString("jumlah")).append("</td>").append(
                                            "</tr>"
                                );
                                tabModeProsedur.addRow(new Object[]{
                                    true,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)
                                });
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 

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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan from template_pemeriksaan_dokter_permintaan_radiologi "+
                            "inner join jns_perawatan_radiologi on template_pemeriksaan_dokter_permintaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_radiologi.no_template=?");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Permintaan Radiologi : ").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Kode Periksa</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='80%'>Nama Pemeriksaan</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModeRadiologi);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("kd_jenis_prw")).append("</td>").append(
                                                "<td>").append(rs.getString("nm_perawatan")).append("</td>").append(
                                            "</tr>"
                                );
                                tabModeRadiologi.addRow(new Object[]{
                                    true,rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")
                                });
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 
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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='PK'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Permintaan Laborat Patologi Klinis : ").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%'>Kode Periksa</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85%'>Nama Pemeriksaan</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModePK);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("kd_jenis_prw")).append("</td>").append(
                                                "<td>").append(rs.getString("nm_perawatan")).append("</td>").append(
                                            "</tr>"
                                );
                                tabModePK.addRow(new Object[]{
                                    true,rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")
                                });
                                try {
                                    ps2=koneksi.prepareStatement(
                                            "select template_pemeriksaan_dokter_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa "+
                                            "from template_pemeriksaan_dokter_detail_permintaan_lab inner join template_laboratorium on template_pemeriksaan_dokter_detail_permintaan_lab.id_template=template_laboratorium.id_template where template_pemeriksaan_dokter_detail_permintaan_lab.no_template=? and "+
                                            "template_pemeriksaan_dokter_detail_permintaan_lab.kd_jenis_prw=? order by template_laboratorium.urut");
                                    ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                    ps2.setString(2,rs.getString("kd_jenis_prw"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        Valid.tabelKosong(tabModeDetailPK);
                                        htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center' width='15%'></td>").append(
                                                "<td width='85%'>").append(
                                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                                        "<tr class='isi'>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%'>Pemeriksaan</td>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Satuan</td>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%'>Nilai Rujukan</td>").append(
                                                        "</tr>"
                                        );
                                        do{
                                            la="";ld="";pa="";pd="";
                                            if(!rs2.getString("nilai_rujukan_ld").equals("")){
                                                ld="LD : "+rs2.getString("nilai_rujukan_ld");
                                            }
                                            if(!rs2.getString("nilai_rujukan_la").equals("")){
                                                la=", LA : "+rs2.getString("nilai_rujukan_la");
                                            }
                                            if(!rs2.getString("nilai_rujukan_pa").equals("")){
                                                pd=", PD : "+rs2.getString("nilai_rujukan_pd");
                                            }
                                            if(!rs2.getString("nilai_rujukan_pd").equals("")){
                                                pa=" PA : "+rs2.getString("nilai_rujukan_pa");
                                            }
                                            htmlContent.append(
                                                        "<tr class='isi'>").append(
                                                            "<td>").append(rs2.getString("Pemeriksaan")).append("</td>").append(
                                                            "<td align='center'>").append(rs2.getString("satuan")).append("</td>").append(
                                                            "<td>").append(ld).append(la).append(pd).append(pa).append("</td>").append(
                                                        "</tr>"
                                            );
                                            tabModeDetailPK.addRow(new Object[]{
                                                true,"   "+rs2.getString("Pemeriksaan"),rs2.getString("satuan"),ld+la+pd+pa,rs2.getString("id_template"),rs.getString("kd_jenis_prw")
                                            });
                                        }while(rs2.next());
                                        htmlContent.append(
                                                    "</table>").append(
                                                "</td>").append(
                                            "</tr>"
                                        );
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
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 
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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='PA'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Permintaan Laborat Patologi Anatomi :").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%'>Kode Periksa</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85%'>Nama Pemeriksaan</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModePA);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("kd_jenis_prw")).append("</td>").append(
                                                "<td>").append(rs.getString("nm_perawatan")).append("</td>").append(
                                            "</tr>"
                                );
                                tabModePA.addRow(new Object[]{
                                    true,rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")
                                });
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 
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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from template_pemeriksaan_dokter_permintaan_lab "+
                            "inner join jns_perawatan_lab on template_pemeriksaan_dokter_permintaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where template_pemeriksaan_dokter_permintaan_lab.no_template=? and jns_perawatan_lab.kategori='MB'");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Permintaan Laborat Mikrobiologi & Bio Molekuler : ").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='15%'>Kode Periksa</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='85%'>Nama Pemeriksaan</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModeMB);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("kd_jenis_prw")).append("</td>").append(
                                                "<td>").append(rs.getString("nm_perawatan")).append("</td>").append(
                                            "</tr>"
                                );
                                tabModeMB.addRow(new Object[]{
                                    true,rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan")
                                });
                                try {
                                    ps2=koneksi.prepareStatement(
                                            "select template_pemeriksaan_dokter_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.satuan,template_laboratorium.nilai_rujukan_ld,template_laboratorium.nilai_rujukan_la,template_laboratorium.nilai_rujukan_pd,template_laboratorium.nilai_rujukan_pa "+
                                            "from template_pemeriksaan_dokter_detail_permintaan_lab inner join template_laboratorium on template_pemeriksaan_dokter_detail_permintaan_lab.id_template=template_laboratorium.id_template where template_pemeriksaan_dokter_detail_permintaan_lab.no_template=? and "+
                                            "template_pemeriksaan_dokter_detail_permintaan_lab.kd_jenis_prw=? order by template_laboratorium.urut");
                                    ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                    ps2.setString(2,rs.getString("kd_jenis_prw"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center' width='15%'></td>").append(
                                                "<td width='85%'>").append(
                                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                                        "<tr class='isi'>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%'>Pemeriksaan</td>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Satuan</td>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='40%'>Nilai Rujukan</td>").append(
                                                        "</tr>"
                                        );
                                        Valid.tabelKosong(tabModeDetailMB);
                                        do{
                                            la="";ld="";pa="";pd="";
                                            if(!rs2.getString("nilai_rujukan_ld").equals("")){
                                                ld="LD : "+rs2.getString("nilai_rujukan_ld");
                                            }
                                            if(!rs2.getString("nilai_rujukan_la").equals("")){
                                                la=", LA : "+rs2.getString("nilai_rujukan_la");
                                            }
                                            if(!rs2.getString("nilai_rujukan_pa").equals("")){
                                                pd=", PD : "+rs2.getString("nilai_rujukan_pd");
                                            }
                                            if(!rs2.getString("nilai_rujukan_pd").equals("")){
                                                pa=" PA : "+rs2.getString("nilai_rujukan_pa");
                                            }
                                            htmlContent.append(
                                                        "<tr class='isi'>").append(
                                                            "<td>").append(rs2.getString("Pemeriksaan")).append("</td>").append(
                                                            "<td align='center'>").append(rs2.getString("satuan")).append("</td>").append(
                                                            "<td>").append(ld).append(la).append(pd).append(pa).append("</td>").append(
                                                        "</tr>"
                                            );
                                            tabModeDetailMB.addRow(new Object[]{
                                                true,"   "+rs2.getString("Pemeriksaan"),rs2.getString("satuan"),ld+la+pd+pa,rs2.getString("id_template"),rs.getString("kd_jenis_prw")
                                            });
                                        }while(rs2.next());
                                        htmlContent.append(
                                                    "</table>").append(
                                                "</td>").append(
                                            "</tr>"
                                        );
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
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 
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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_resep.kode_brng,databarang.nama_brng,kodesatuan.satuan,template_pemeriksaan_dokter_resep.jml,template_pemeriksaan_dokter_resep.aturan_pakai,jenis.nama,industrifarmasi.nama_industri, "+
                            "databarang.kapasitas,databarang.letak_barang from template_pemeriksaan_dokter_resep inner join databarang on template_pemeriksaan_dokter_resep.kode_brng=databarang.kode_brng inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat "+
                            "inner join jenis on databarang.kdjns=jenis.kdjns inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri where template_pemeriksaan_dokter_resep.no_template=? order by databarang.nama_brng");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Obat Non Racikan : ").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='5%'>Jumlah</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Kode Barang</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='45%'>Nama Barang</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Satuan</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30%'>Aturan Pakai</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModeObatUmum);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("jml")).append("</td>").append(
                                                "<td align='center'>").append(rs.getString("kode_brng")).append("</td>").append(
                                                "<td>").append(rs.getString("nama_brng")).append("</td>").append(
                                                "<td align='center'>").append(rs.getString("satuan")).append("</td>").append(
                                                "<td>").append(rs.getString("aturan_pakai")).append("</td>").append(
                                            "</tr>"
                                );
                                tabModeObatUmum.addRow(new Object[]{
                                    false,rs.getString("jml"),rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("satuan"),rs.getString("letak_barang"),rs.getString("nama"),rs.getString("aturan_pakai"),rs.getString("nama_industri"),rs.getDouble("kapasitas")
                                });
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 

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
                    
                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_resep_racikan.no_racik,template_pemeriksaan_dokter_resep_racikan.kd_racik,template_pemeriksaan_dokter_resep_racikan.nama_racik,metode_racik.nm_racik,template_pemeriksaan_dokter_resep_racikan.jml_dr,template_pemeriksaan_dokter_resep_racikan.aturan_pakai,"+
                            "template_pemeriksaan_dokter_resep_racikan.keterangan from template_pemeriksaan_dokter_resep_racikan inner join metode_racik on metode_racik.kd_racik=template_pemeriksaan_dokter_resep_racikan.kd_racik where template_pemeriksaan_dokter_resep_racikan.no_template=? "+
                            "order by template_pemeriksaan_dokter_resep_racikan.no_racik");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Obat Racikan : ").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>No.</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='33%'>Nama Racikan</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='11%'>Metode Racik</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>Jml.Racik</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='25%'>Aturan Pakai</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='22%'>Keterangan</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(tabModeObatRacikan);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("no_racik")).append("</td>").append(
                                                "<td>").append(rs.getString("nama_racik")).append("</td>").append(
                                                "<td align='center'>").append(rs.getString("nm_racik")).append("</td>").append(
                                                "<td align='center'>").append(rs.getString("jml_dr")).append("</td>").append(
                                                "<td>").append(rs.getString("aturan_pakai")).append("</td>").append(
                                                "<td>").append(rs.getString("keterangan")).append("</td>").append(
                                            "</tr>"
                                );
                                tabModeObatRacikan.addRow(new Object[]{
                                    rs.getString("no_racik"),rs.getString("nama_racik"),rs.getString("kd_racik"),rs.getString("nm_racik"),rs.getString("jml_dr"),rs.getString("aturan_pakai"),rs.getString("keterangan")
                                });
                                try {
                                    ps2=koneksi.prepareStatement(
                                            "select template_pemeriksaan_dokter_resep_racikan_detail.kode_brng,databarang.nama_brng,kodesatuan.satuan,template_pemeriksaan_dokter_resep_racikan_detail.jml,jenis.nama,"+
                                            "databarang.kapasitas,template_pemeriksaan_dokter_resep_racikan_detail.p1,template_pemeriksaan_dokter_resep_racikan_detail.p2,template_pemeriksaan_dokter_resep_racikan_detail.kandungan,"+
                                            "industrifarmasi.nama_industri,databarang.letak_barang from template_pemeriksaan_dokter_resep_racikan_detail inner join databarang on template_pemeriksaan_dokter_resep_racikan_detail.kode_brng=databarang.kode_brng "+
                                            "inner join jenis on databarang.kdjns=jenis.kdjns inner join kodesatuan on kodesatuan.kode_sat=databarang.kode_sat inner join industrifarmasi on industrifarmasi.kode_industri=databarang.kode_industri "+
                                            "where template_pemeriksaan_dokter_resep_racikan_detail.no_template=? and template_pemeriksaan_dokter_resep_racikan_detail.no_racik=? order by template_pemeriksaan_dokter_resep_racikan_detail.kode_brng");
                                    ps2.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                    ps2.setString(2,rs.getString("no_racik"));
                                    rs2=ps2.executeQuery();
                                    if(rs2.next()){
                                        htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center' width='15%'></td>").append(
                                                "<td width='85%' colspan='5'>").append(
                                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                                        "<tr class='isi'>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='10%'>Jumlah</td>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Satuan</td>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Kode Obat</td>").append(
                                                            "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>Nama Obat</td>").append(
                                                        "</tr>"
                                        );
                                        Valid.tabelKosong(tabModeDetailObatRacikan);
                                        do{
                                            htmlContent.append(
                                                        "<tr class='isi'>").append(
                                                            "<td align='center'>").append(rs2.getString("jml")).append("</td>").append(
                                                            "<td align='center'>").append(rs2.getString("satuan")).append("</td>").append(
                                                            "<td align='center'>").append(rs2.getString("kode_brng")).append("</td>").append(
                                                            "<td align='left'>").append(rs2.getString("nama_brng")).append("</td>").append(
                                                        "</tr>"
                                            );
                                            tabModeDetailObatRacikan.addRow(new Object[]{
                                                rs.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("satuan"),
                                                rs2.getString("nama"),rs2.getDouble("kapasitas"),rs2.getDouble("p1"),"/",rs2.getDouble("p2"),rs2.getString("kandungan"),
                                                rs2.getDouble("jml"),rs2.getString("nama_industri"),rs2.getString("letak_barang")
                                            });
                                        }while(rs2.next());
                                        htmlContent.append(
                                                    "</table>").append(
                                                "</td>").append(
                                            "</tr>"
                                        );
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
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 
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

                    ps=koneksi.prepareStatement(
                            "select template_pemeriksaan_dokter_tindakan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori from template_pemeriksaan_dokter_tindakan inner join jns_perawatan "+
                            "on template_pemeriksaan_dokter_tindakan.kd_jenis_prw=jns_perawatan.kd_jenis_prw inner join kategori_perawatan on kategori_perawatan.kd_kategori=jns_perawatan.kd_kategori "+
                            "where template_pemeriksaan_dokter_tindakan.no_template=?");
                    try {
                        ps.setString(1,tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            htmlContent.append(                             
                                "<tr class='isi'>").append(
                                    "<td valign='top' align='left' width='100%'>").append(
                                        "Tindakan : ").append(
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                            "<tr class='isi'>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='20%'>Kode</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='50%'>Nama Perawatan/Tindakan</td>").append(
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='30%'>Kategori</td>").append(
                                            "</tr>"
                            );
                            Valid.tabelKosong(TabModeTindakan);
                            do{
                                htmlContent.append(
                                            "<tr class='isi'>").append(
                                                "<td align='center'>").append(rs.getString("kd_jenis_prw")).append("</td>").append(
                                                "<td>").append(rs.getString("nm_perawatan")).append("</td>").append(
                                                "<td align='center'>").append(rs.getString("nm_kategori")).append("</td>").append(
                                            "</tr>"
                                );
                                TabModeTindakan.addRow(new Object[]{
                                    true,rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),rs.getString("nm_kategori")
                                });
                            }while(rs.next());
                            htmlContent.append( 
                                        "</table>").append(
                                    "</td>").append(
                                "</tr>"
                            ); 

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
                    
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                           htmlContent.toString()+
                          "</table>"+
                        "</html>");
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }
    
    private void ganti(){
        if(Sequel.queryu2tf("delete from template_pemeriksaan_dokter where no_template=?",1,new String[]{
            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
        })==true){
            if(Sequel.menyimpantf("template_pemeriksaan_dokter","?,?,?,?,?,?,?,?","No.Template",8,new String[]{
                tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),KdDokter.getText(),Subjek.getText(),Objek.getText(),Asesmen.getText(),Plan.getText(),Instruksi.getText(),Evaluasi.getText()
            })==true){
                for(i=0;i<tbDiagnosa.getRowCount();i++){ 
                    if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_penyakit","?,?,?","ICD X",3,new String[]{
                            Kd.getText(),tbDiagnosa.getValueAt(i,1).toString(),tbDiagnosa.getValueAt(i,11).toString()
                        });
                    }
                }
                for(i=0;i<tbProsedur.getRowCount();i++){ 
                    if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_prosedur","?,?,?,?","ICD 9",4,new String[]{
                            Kd.getText(),tbProsedur.getValueAt(i,1).toString(),tbProsedur.getValueAt(i,7).toString(),tbProsedur.getValueAt(i,8).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanRadiologi.getRowCount();i++){ 
                    if(tbPermintaanRadiologi.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_permintaan_radiologi","?,?","Pemeriksaan Radiologi",2,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbPermintaanRadiologi.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanPK.getRowCount();i++){ 
                    if(tbPermintaanPK.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_permintaan_lab","?,?","Pemeriksaan Laboratorium PK",2,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbPermintaanPK.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbDetailPK.getRowCount();i++){ 
                    if((!tbDetailPK.getValueAt(i,4).toString().equals(""))&&tbDetailPK.getValueAt(i,0).toString().equals("true")){  
                        Sequel.menyimpan("template_pemeriksaan_dokter_detail_permintaan_lab","?,?,?","Detail Pemeriksaan Laboratorium PK",3,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDetailPK.getValueAt(i,5).toString(),tbDetailPK.getValueAt(i,4).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanPA.getRowCount();i++){ 
                    if(tbPermintaanPA.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_permintaan_lab","?,?","Pemeriksaan Laboratorium PA",2,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbPermintaanPA.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbPermintaanMB.getRowCount();i++){ 
                    if(tbPermintaanMB.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_permintaan_lab","?,?","Pemeriksaan Laboratorium PK",2,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbPermintaanMB.getValueAt(i,1).toString()
                        });
                    }
                }
                for(i=0;i<tbDetailMB.getRowCount();i++){ 
                    if((!tbDetailMB.getValueAt(i,4).toString().equals(""))&&tbDetailMB.getValueAt(i,0).toString().equals("true")){  
                        Sequel.menyimpan("template_pemeriksaan_dokter_detail_permintaan_lab","?,?,?","Detail Pemeriksaan Laboratorium PK",3,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDetailMB.getValueAt(i,5).toString(),tbDetailMB.getValueAt(i,4).toString()
                        });
                    }
                }
                for(i=0;i<tbObatNonRacikan.getRowCount();i++){ 
                    if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,1).toString())>0){  
                        if(tbObatNonRacikan.getValueAt(i,0).toString().equals("true")){
                            if(Valid.SetAngka(tbObatNonRacikan.getValueAt(i,9).toString())>0){
                                Sequel.menyimpan("template_pemeriksaan_dokter_resep","?,?,?,?","Obat Non Racikan",4,new String[]{
                                    tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbObatNonRacikan.getValueAt(i,2).toString(),""+(Double.parseDouble(tbObatNonRacikan.getValueAt(i,1).toString())/Valid.SetAngka(tbObatNonRacikan.getValueAt(i,9).toString())),tbObatNonRacikan.getValueAt(i,7).toString()
                                });
                            }else{
                                Sequel.menyimpan("template_pemeriksaan_dokter_resep","?,?,?,?","Obat Non Racikan",4,new String[]{
                                    tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbObatNonRacikan.getValueAt(i,2).toString(),""+Double.parseDouble(tbObatNonRacikan.getValueAt(i,1).toString()),tbObatNonRacikan.getValueAt(i,7).toString()
                                });
                            }
                        }else{
                            Sequel.menyimpan("template_pemeriksaan_dokter_resep","?,?,?,?","Obat Non Racikan",4,new String[]{
                                tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbObatNonRacikan.getValueAt(i,2).toString(),""+Double.parseDouble(tbObatNonRacikan.getValueAt(i,1).toString()),tbObatNonRacikan.getValueAt(i,7).toString()
                            });
                        }
                    }
                }
                for(i=0;i<tbObatRacikan.getRowCount();i++){ 
                    if(Valid.SetAngka(tbObatRacikan.getValueAt(i,4).toString())>0){ 
                        Sequel.menyimpan("template_pemeriksaan_dokter_resep_racikan","?,?,?,?,?,?,?","Obat Racikan",7,new String[]{
                           tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbObatRacikan.getValueAt(i,0).toString(),tbObatRacikan.getValueAt(i,1).toString(),
                           tbObatRacikan.getValueAt(i,2).toString(),tbObatRacikan.getValueAt(i,4).toString(),
                           tbObatRacikan.getValueAt(i,5).toString(),tbObatRacikan.getValueAt(i,6).toString()
                        });
                    }
                }
                for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
                        Sequel.menyimpan("template_pemeriksaan_dokter_resep_racikan_detail","?,?,?,?,?,?,?","Detail Obat Racikan",7,new String[]{
                           tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDetailObatRacikan.getValueAt(i,0).toString(),tbDetailObatRacikan.getValueAt(i,1).toString(),
                           tbDetailObatRacikan.getValueAt(i,6).toString(),tbDetailObatRacikan.getValueAt(i,8).toString(),
                           tbDetailObatRacikan.getValueAt(i,9).toString(),tbDetailObatRacikan.getValueAt(i,10).toString()
                        });
                    }
                }
                for(i=0;i<tbTindakan.getRowCount();i++){ 
                    if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("template_pemeriksaan_dokter_tindakan","?,?","Tindakan Dokter",2,new String[]{
                            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbTindakan.getValueAt(i,1).toString()
                        });
                    }
                }
                tabMode.addRow(new Object[]{
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),KdDokter.getText(),NmDokter.getText(),Subjek.getText(),Objek.getText(),Asesmen.getText(),Plan.getText(),Instruksi.getText(),Evaluasi.getText()
                });
                tabMode.removeRow(tbDokter.getSelectedRow());
                ChkAccor.setSelected(false);
                isDetail();
                TabRawat.setSelectedIndex(1);
            } 
        }else{
            JOptionPane.showMessageDialog(null,"Gagal mengganti..!!");
        }
    }
    
    private void hapus(){
        if(Sequel.queryu2tf("delete from template_pemeriksaan_dokter where no_template=?",1,new String[]{
            tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbDokter.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            LoadHTML.setText("");
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
}

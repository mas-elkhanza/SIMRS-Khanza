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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import kepegawaian.DlgCariPetugas;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import simrskhanza.DlgKelurahan;

/**
 *
 * @author dosen
 */
public class CoronaPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private CoronaReferensiJK jk=new CoronaReferensiJK(null,false);
    private CoronaReferensiKewarganegaraan kewarganegaraan=new CoronaReferensiKewarganegaraan(null,false);
    private CoronaReferensiSumberPenularan penularan=new CoronaReferensiSumberPenularan(null,false);
    private CoronaReferensiKecamatan kecamatan=new CoronaReferensiKecamatan(null,false);
    private CoronaReferensiStatusKeluar statuskeluar=new CoronaReferensiStatusKeluar(null,false);
    private CoronaReferensiStatusRawat statusrawat=new CoronaReferensiStatusRawat(null,false);
    private CoronaReferensiStatusIsolasi statusisolasi=new CoronaReferensiStatusIsolasi(null,false);
    private CoronaReferensiPropinsi propinsi=new CoronaReferensiPropinsi(null,false);
    private CoronaReferensiKabupaten kabupaten=new CoronaReferensiKabupaten(null,false);
    private CoronaReferensiJenisPasien jenispasien=new CoronaReferensiJenisPasien(null,false);
    private DlgKelurahan kelurahan=new DlgKelurahan(null,false);
    private ApiKemenkesCorona api=new ApiKemenkesCorona();
    private String link="",idrs="",body="";
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public CoronaPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.KTP/Paspor","No.RM","Nama Pasien","Inisial","Kode JK","Jenis Kelamin","Tgl.Lahir","E-mail","No.Telp",
                "Tgl.Lapor","Tgl.Masuk","Tgl.Keluar","Kode Kw","Kewarganegaraan","Kode Sumber","Sumber Penularan","Kode Kel",
                "Kelurahan","Kode Kec","Kecamatan","Kode Kab","Kabupaten","Kode Prop","Propinsi","Kode Stts Keluar","Status Keluar",
                "Kode Status Rawat","Status Rawat","Kode isolasi","Status Isolasi","Sebab Kematian","Kode Jenis Pasien","Jenis Pasien"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 33; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(140);
            }else if(i==8){
                column.setPreferredWidth(110);
            }else if(i==9){
                column.setPreferredWidth(120);
            }else if(i==10){
                column.setPreferredWidth(65);
            }else if(i==11){
                column.setPreferredWidth(65);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setPreferredWidth(110);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==21){
                column.setPreferredWidth(100);
            }else if(i==22){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==23){
                column.setPreferredWidth(100);
            }else if(i==24){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==25){
                column.setPreferredWidth(120);
            }else if(i==26){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==27){
                column.setPreferredWidth(140);
            }else if(i==28){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==29){
                column.setPreferredWidth(230);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==32){
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        Inisial.setDocument(new batasInput((byte)15).getKata(Inisial));
        SebabKematian.setDocument(new batasInput((byte)60).getKata(SebabKematian));
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
        
        ChkInput.setSelected(false);
        isForm();
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    KodeJK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaJK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                KodeJK.requestFocus();
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
        
        jk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jk.getTable().getSelectedRow()!= -1){                   
                    KodeJK.setText(jk.getTable().getValueAt(jk.getTable().getSelectedRow(),0).toString());
                    NamaJK.setText(jk.getTable().getValueAt(jk.getTable().getSelectedRow(),1).toString());
                    BtnJK.requestFocus();
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
        
        kewarganegaraan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kewarganegaraan.getTable().getSelectedRow()!= -1){                   
                    KodeKewarganegaraan.setText(kewarganegaraan.getTable().getValueAt(kewarganegaraan.getTable().getSelectedRow(),0).toString());
                    NamaKewarganegaraan.setText(kewarganegaraan.getTable().getValueAt(kewarganegaraan.getTable().getSelectedRow(),1).toString());
                    BtnKewarganegaraan.requestFocus();
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
        
        penularan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penularan.getTable().getSelectedRow()!= -1){                   
                    KodePenularan.setText(penularan.getTable().getValueAt(penularan.getTable().getSelectedRow(),0).toString());
                    NamaPenularan.setText(penularan.getTable().getValueAt(penularan.getTable().getSelectedRow(),1).toString());
                    BtnSumberPenularan.requestFocus();
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
        
        statuskeluar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(statuskeluar.getTable().getSelectedRow()!= -1){                   
                    KodeStatusKeluar.setText(statuskeluar.getTable().getValueAt(statuskeluar.getTable().getSelectedRow(),0).toString());
                    NamaStatusKeluar.setText(statuskeluar.getTable().getValueAt(statuskeluar.getTable().getSelectedRow(),1).toString());
                    BtnStatusKeluar.requestFocus();
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
        
        statusrawat.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(statusrawat.getTable().getSelectedRow()!= -1){                   
                    KodeStatusRawat.setText(statusrawat.getTable().getValueAt(statusrawat.getTable().getSelectedRow(),0).toString());
                    NamaStatusRawat.setText(statusrawat.getTable().getValueAt(statusrawat.getTable().getSelectedRow(),1).toString());
                    BtnStatusRawat.requestFocus();
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
        
        statusisolasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(statusisolasi.getTable().getSelectedRow()!= -1){                   
                    KodeStatusIsolasi.setText(statusisolasi.getTable().getValueAt(statusisolasi.getTable().getSelectedRow(),0).toString());
                    NamaStatusIsolasi.setText(statusisolasi.getTable().getValueAt(statusisolasi.getTable().getSelectedRow(),1).toString());
                    BtnStatusIsolasi.requestFocus();
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
        
        propinsi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(propinsi.getTable().getSelectedRow()!= -1){                   
                    KodePropinsi.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(),0).toString());
                    NamaPropinsi.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(),1).toString());
                    BtnPropinsi.requestFocus();
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
        
        kabupaten.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kabupaten.getTable().getSelectedRow()!= -1){                   
                    KodeKabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(),0).toString());
                    NamaKabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(),1).toString());
                    BtnKabupaten.requestFocus();
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
        
        kecamatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kecamatan.getTable().getSelectedRow()!= -1){                   
                    KodeKecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(),0).toString());
                    NamaKecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(),1).toString());
                    BtnKecamatan.requestFocus();
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
        
        kelurahan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kelurahan.getTable().getSelectedRow()!= -1){                   
                    KodeKelurahan.setText(kelurahan.getTable().getValueAt(kelurahan.getTable().getSelectedRow(),1).toString());
                    NamaKelurahan.setText(kelurahan.getTable().getValueAt(kelurahan.getTable().getSelectedRow(),0).toString());
                    BtnKelurahan.requestFocus();
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
        
        jenispasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jenispasien.getTable().getSelectedRow()!= -1){                   
                    KodeJenisPasien.setText(jenispasien.getTable().getValueAt(jenispasien.getTable().getSelectedRow(),0).toString());
                    NamaJenisPasien.setText(jenispasien.getTable().getValueAt(jenispasien.getTable().getSelectedRow(),1).toString());
                    BtnJenisPasien.requestFocus();
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
            link=koneksiDB.URLAPICORONA();
            idrs=koneksiDB.IDCORONA();
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

        Popup = new javax.swing.JPopupMenu();
        ppDiagnosaPasien = new javax.swing.JMenuItem();
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
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        NoRM = new widget.TextBox();
        NamaPasien = new widget.TextBox();
        Inisial = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel20 = new widget.Label();
        KodeJK = new widget.TextBox();
        NamaJK = new widget.TextBox();
        BtnJK = new widget.Button();
        jLabel7 = new widget.Label();
        NoKTP = new widget.TextBox();
        jLabel11 = new widget.Label();
        TglMasuk = new widget.Tanggal();
        jLabel12 = new widget.Label();
        jLabel21 = new widget.Label();
        KodeKewarganegaraan = new widget.TextBox();
        NamaKewarganegaraan = new widget.TextBox();
        BtnKewarganegaraan = new widget.Button();
        jLabel22 = new widget.Label();
        KodePenularan = new widget.TextBox();
        NamaPenularan = new widget.TextBox();
        BtnSumberPenularan = new widget.Button();
        jLabel23 = new widget.Label();
        KodeKecamatan = new widget.TextBox();
        NamaKecamatan = new widget.TextBox();
        BtnKecamatan = new widget.Button();
        jLabel13 = new widget.Label();
        TglKeluar = new widget.Tanggal();
        jLabel24 = new widget.Label();
        KodeStatusKeluar = new widget.TextBox();
        NamaStatusKeluar = new widget.TextBox();
        BtnStatusKeluar = new widget.Button();
        jLabel14 = new widget.Label();
        TglLapor = new widget.Tanggal();
        jLabel25 = new widget.Label();
        KodeStatusRawat = new widget.TextBox();
        NamaStatusRawat = new widget.TextBox();
        BtnStatusRawat = new widget.Button();
        jLabel26 = new widget.Label();
        KodeStatusIsolasi = new widget.TextBox();
        NamaStatusIsolasi = new widget.TextBox();
        BtnStatusIsolasi = new widget.Button();
        jLabel8 = new widget.Label();
        Email = new widget.TextBox();
        jLabel9 = new widget.Label();
        NoTelp = new widget.TextBox();
        jLabel16 = new widget.Label();
        SebabKematian = new widget.TextBox();
        jLabel27 = new widget.Label();
        KodeKelurahan = new widget.TextBox();
        NamaKelurahan = new widget.TextBox();
        BtnKelurahan = new widget.Button();
        jLabel28 = new widget.Label();
        KodeKabupaten = new widget.TextBox();
        NamaKabupaten = new widget.TextBox();
        BtnKabupaten = new widget.Button();
        jLabel29 = new widget.Label();
        KodePropinsi = new widget.TextBox();
        NamaPropinsi = new widget.TextBox();
        BtnPropinsi = new widget.Button();
        TglLahir = new widget.TextBox();
        jLabel30 = new widget.Label();
        KodeJenisPasien = new widget.TextBox();
        NamaJenisPasien = new widget.TextBox();
        BtnJenisPasien = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppDiagnosaPasien.setBackground(new java.awt.Color(255, 255, 254));
        ppDiagnosaPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDiagnosaPasien.setForeground(new java.awt.Color(50, 50, 50));
        ppDiagnosaPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDiagnosaPasien.setText("Diagnosa Pasien");
        ppDiagnosaPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDiagnosaPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDiagnosaPasien.setName("ppDiagnosaPasien"); // NOI18N
        ppDiagnosaPasien.setPreferredSize(new java.awt.Dimension(145, 25));
        ppDiagnosaPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDiagnosaPasienBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppDiagnosaPasien);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Bridging Kemenkes Pasien Teridentifikasi Corona ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setComponentPopupMenu(Popup);
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
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(95, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Tgl.Masuk :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(63, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2020" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2020" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(360, 23));
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
        panelGlass7.add(BtnAll);

        jPanel3.add(panelGlass7, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 306));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(190, 107));
        FormInput.setLayout(null);

        jLabel4.setText("Pasien :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 40, 110, 23);

        NoRM.setEditable(false);
        NoRM.setHighlighter(null);
        NoRM.setName("NoRM"); // NOI18N
        FormInput.add(NoRM);
        NoRM.setBounds(114, 40, 90, 23);

        NamaPasien.setEditable(false);
        NamaPasien.setHighlighter(null);
        NamaPasien.setName("NamaPasien"); // NOI18N
        FormInput.add(NamaPasien);
        NamaPasien.setBounds(206, 40, 220, 23);

        Inisial.setHighlighter(null);
        Inisial.setName("Inisial"); // NOI18N
        Inisial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InisialKeyPressed(evt);
            }
        });
        FormInput.add(Inisial);
        Inisial.setBounds(114, 70, 95, 23);

        jLabel5.setText("Inisial :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 70, 110, 23);

        jLabel20.setText("J.K. :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(209, 70, 35, 23);

        KodeJK.setEditable(false);
        KodeJK.setHighlighter(null);
        KodeJK.setName("KodeJK"); // NOI18N
        FormInput.add(KodeJK);
        KodeJK.setBounds(248, 70, 35, 23);

        NamaJK.setEditable(false);
        NamaJK.setHighlighter(null);
        NamaJK.setName("NamaJK"); // NOI18N
        FormInput.add(NamaJK);
        NamaJK.setBounds(285, 70, 110, 23);

        BtnJK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJK.setMnemonic('X');
        BtnJK.setToolTipText("Alt+X");
        BtnJK.setName("BtnJK"); // NOI18N
        BtnJK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJKActionPerformed(evt);
            }
        });
        BtnJK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJKKeyPressed(evt);
            }
        });
        FormInput.add(BtnJK);
        BtnJK.setBounds(398, 70, 28, 23);

        jLabel7.setText("No.KTP/Paspor :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 10, 110, 23);

        NoKTP.setEditable(false);
        NoKTP.setHighlighter(null);
        NoKTP.setName("NoKTP"); // NOI18N
        FormInput.add(NoKTP);
        NoKTP.setBounds(114, 10, 170, 23);

        jLabel11.setText("Tanggal Masuk :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 130, 110, 23);

        TglMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2020" }));
        TglMasuk.setDisplayFormat("dd-MM-yyyy");
        TglMasuk.setName("TglMasuk"); // NOI18N
        TglMasuk.setOpaque(false);
        TglMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMasukKeyPressed(evt);
            }
        });
        FormInput.add(TglMasuk);
        TglMasuk.setBounds(114, 130, 90, 23);

        jLabel12.setText("Tgl.Lahir :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 100, 110, 23);

        jLabel21.setText("Kewarganegaraan :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(0, 190, 110, 23);

        KodeKewarganegaraan.setEditable(false);
        KodeKewarganegaraan.setHighlighter(null);
        KodeKewarganegaraan.setName("KodeKewarganegaraan"); // NOI18N
        FormInput.add(KodeKewarganegaraan);
        KodeKewarganegaraan.setBounds(114, 190, 35, 23);

        NamaKewarganegaraan.setEditable(false);
        NamaKewarganegaraan.setHighlighter(null);
        NamaKewarganegaraan.setName("NamaKewarganegaraan"); // NOI18N
        FormInput.add(NamaKewarganegaraan);
        NamaKewarganegaraan.setBounds(151, 190, 244, 23);

        BtnKewarganegaraan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKewarganegaraan.setMnemonic('X');
        BtnKewarganegaraan.setToolTipText("Alt+X");
        BtnKewarganegaraan.setName("BtnKewarganegaraan"); // NOI18N
        BtnKewarganegaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKewarganegaraanActionPerformed(evt);
            }
        });
        BtnKewarganegaraan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKewarganegaraanKeyPressed(evt);
            }
        });
        FormInput.add(BtnKewarganegaraan);
        BtnKewarganegaraan.setBounds(398, 190, 28, 23);

        jLabel22.setText("Sumber Penularan :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 220, 110, 23);

        KodePenularan.setEditable(false);
        KodePenularan.setHighlighter(null);
        KodePenularan.setName("KodePenularan"); // NOI18N
        FormInput.add(KodePenularan);
        KodePenularan.setBounds(114, 220, 35, 23);

        NamaPenularan.setEditable(false);
        NamaPenularan.setHighlighter(null);
        NamaPenularan.setName("NamaPenularan"); // NOI18N
        FormInput.add(NamaPenularan);
        NamaPenularan.setBounds(151, 220, 244, 23);

        BtnSumberPenularan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSumberPenularan.setMnemonic('X');
        BtnSumberPenularan.setToolTipText("Alt+X");
        BtnSumberPenularan.setName("BtnSumberPenularan"); // NOI18N
        BtnSumberPenularan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSumberPenularanActionPerformed(evt);
            }
        });
        BtnSumberPenularan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSumberPenularanKeyPressed(evt);
            }
        });
        FormInput.add(BtnSumberPenularan);
        BtnSumberPenularan.setBounds(398, 220, 28, 23);

        jLabel23.setText("Kecamatan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(435, 40, 89, 23);

        KodeKecamatan.setEditable(false);
        KodeKecamatan.setHighlighter(null);
        KodeKecamatan.setName("KodeKecamatan"); // NOI18N
        FormInput.add(KodeKecamatan);
        KodeKecamatan.setBounds(528, 40, 70, 23);

        NamaKecamatan.setEditable(false);
        NamaKecamatan.setHighlighter(null);
        NamaKecamatan.setName("NamaKecamatan"); // NOI18N
        FormInput.add(NamaKecamatan);
        NamaKecamatan.setBounds(600, 40, 190, 23);

        BtnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatan.setMnemonic('X');
        BtnKecamatan.setToolTipText("Alt+X");
        BtnKecamatan.setName("BtnKecamatan"); // NOI18N
        BtnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanActionPerformed(evt);
            }
        });
        BtnKecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKecamatanKeyPressed(evt);
            }
        });
        FormInput.add(BtnKecamatan);
        BtnKecamatan.setBounds(793, 40, 28, 23);

        jLabel13.setText("Tanggal Keluar :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(237, 130, 95, 23);

        TglKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2020" }));
        TglKeluar.setDisplayFormat("dd-MM-yyyy");
        TglKeluar.setName("TglKeluar"); // NOI18N
        TglKeluar.setOpaque(false);
        TglKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKeluarKeyPressed(evt);
            }
        });
        FormInput.add(TglKeluar);
        TglKeluar.setBounds(336, 130, 90, 23);

        jLabel24.setText("Status Keluar :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(435, 130, 89, 23);

        KodeStatusKeluar.setEditable(false);
        KodeStatusKeluar.setHighlighter(null);
        KodeStatusKeluar.setName("KodeStatusKeluar"); // NOI18N
        FormInput.add(KodeStatusKeluar);
        KodeStatusKeluar.setBounds(528, 130, 35, 23);

        NamaStatusKeluar.setEditable(false);
        NamaStatusKeluar.setHighlighter(null);
        NamaStatusKeluar.setName("NamaStatusKeluar"); // NOI18N
        FormInput.add(NamaStatusKeluar);
        NamaStatusKeluar.setBounds(565, 130, 225, 23);

        BtnStatusKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnStatusKeluar.setMnemonic('X');
        BtnStatusKeluar.setToolTipText("Alt+X");
        BtnStatusKeluar.setName("BtnStatusKeluar"); // NOI18N
        BtnStatusKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnStatusKeluarActionPerformed(evt);
            }
        });
        BtnStatusKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnStatusKeluarKeyPressed(evt);
            }
        });
        FormInput.add(BtnStatusKeluar);
        BtnStatusKeluar.setBounds(793, 130, 28, 23);

        jLabel14.setText("Tgl. Lapor :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(217, 100, 70, 23);

        TglLapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2020 15:43:40" }));
        TglLapor.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TglLapor.setName("TglLapor"); // NOI18N
        TglLapor.setOpaque(false);
        TglLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLaporKeyPressed(evt);
            }
        });
        FormInput.add(TglLapor);
        TglLapor.setBounds(291, 100, 135, 23);

        jLabel25.setText("Status Rawat :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(435, 160, 89, 23);

        KodeStatusRawat.setEditable(false);
        KodeStatusRawat.setHighlighter(null);
        KodeStatusRawat.setName("KodeStatusRawat"); // NOI18N
        FormInput.add(KodeStatusRawat);
        KodeStatusRawat.setBounds(528, 160, 35, 23);

        NamaStatusRawat.setEditable(false);
        NamaStatusRawat.setHighlighter(null);
        NamaStatusRawat.setName("NamaStatusRawat"); // NOI18N
        FormInput.add(NamaStatusRawat);
        NamaStatusRawat.setBounds(565, 160, 225, 23);

        BtnStatusRawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnStatusRawat.setMnemonic('X');
        BtnStatusRawat.setToolTipText("Alt+X");
        BtnStatusRawat.setName("BtnStatusRawat"); // NOI18N
        BtnStatusRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnStatusRawatActionPerformed(evt);
            }
        });
        BtnStatusRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnStatusRawatKeyPressed(evt);
            }
        });
        FormInput.add(BtnStatusRawat);
        BtnStatusRawat.setBounds(793, 160, 28, 23);

        jLabel26.setText("Status Isolasi :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(435, 190, 89, 23);

        KodeStatusIsolasi.setEditable(false);
        KodeStatusIsolasi.setHighlighter(null);
        KodeStatusIsolasi.setName("KodeStatusIsolasi"); // NOI18N
        FormInput.add(KodeStatusIsolasi);
        KodeStatusIsolasi.setBounds(528, 190, 35, 23);

        NamaStatusIsolasi.setEditable(false);
        NamaStatusIsolasi.setHighlighter(null);
        NamaStatusIsolasi.setName("NamaStatusIsolasi"); // NOI18N
        FormInput.add(NamaStatusIsolasi);
        NamaStatusIsolasi.setBounds(565, 190, 225, 23);

        BtnStatusIsolasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnStatusIsolasi.setMnemonic('X');
        BtnStatusIsolasi.setToolTipText("Alt+X");
        BtnStatusIsolasi.setName("BtnStatusIsolasi"); // NOI18N
        BtnStatusIsolasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnStatusIsolasiActionPerformed(evt);
            }
        });
        BtnStatusIsolasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnStatusIsolasiKeyPressed(evt);
            }
        });
        FormInput.add(BtnStatusIsolasi);
        BtnStatusIsolasi.setBounds(793, 190, 28, 23);

        jLabel8.setText("Email :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 160, 110, 23);

        Email.setEditable(false);
        Email.setHighlighter(null);
        Email.setName("Email"); // NOI18N
        FormInput.add(Email);
        Email.setBounds(114, 160, 150, 23);

        jLabel9.setText("No.Telp :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(267, 160, 50, 23);

        NoTelp.setEditable(false);
        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        FormInput.add(NoTelp);
        NoTelp.setBounds(321, 160, 105, 23);

        jLabel16.setText("Sebab Kematian :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(435, 220, 89, 23);

        SebabKematian.setHighlighter(null);
        SebabKematian.setName("SebabKematian"); // NOI18N
        FormInput.add(SebabKematian);
        SebabKematian.setBounds(528, 220, 293, 23);

        jLabel27.setText("Kelurahan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(435, 10, 89, 23);

        KodeKelurahan.setEditable(false);
        KodeKelurahan.setHighlighter(null);
        KodeKelurahan.setName("KodeKelurahan"); // NOI18N
        FormInput.add(KodeKelurahan);
        KodeKelurahan.setBounds(528, 10, 80, 23);

        NamaKelurahan.setEditable(false);
        NamaKelurahan.setHighlighter(null);
        NamaKelurahan.setName("NamaKelurahan"); // NOI18N
        FormInput.add(NamaKelurahan);
        NamaKelurahan.setBounds(610, 10, 180, 23);

        BtnKelurahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahan.setMnemonic('X');
        BtnKelurahan.setToolTipText("Alt+X");
        BtnKelurahan.setName("BtnKelurahan"); // NOI18N
        BtnKelurahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanActionPerformed(evt);
            }
        });
        BtnKelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKelurahanKeyPressed(evt);
            }
        });
        FormInput.add(BtnKelurahan);
        BtnKelurahan.setBounds(793, 10, 28, 23);

        jLabel28.setText("Kabupaten :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(435, 70, 89, 23);

        KodeKabupaten.setEditable(false);
        KodeKabupaten.setHighlighter(null);
        KodeKabupaten.setName("KodeKabupaten"); // NOI18N
        FormInput.add(KodeKabupaten);
        KodeKabupaten.setBounds(528, 70, 50, 23);

        NamaKabupaten.setEditable(false);
        NamaKabupaten.setHighlighter(null);
        NamaKabupaten.setName("NamaKabupaten"); // NOI18N
        FormInput.add(NamaKabupaten);
        NamaKabupaten.setBounds(580, 70, 210, 23);

        BtnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupaten.setMnemonic('X');
        BtnKabupaten.setToolTipText("Alt+X");
        BtnKabupaten.setName("BtnKabupaten"); // NOI18N
        BtnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenActionPerformed(evt);
            }
        });
        BtnKabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKabupatenKeyPressed(evt);
            }
        });
        FormInput.add(BtnKabupaten);
        BtnKabupaten.setBounds(793, 70, 28, 23);

        jLabel29.setText("Propinsi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(435, 100, 89, 23);

        KodePropinsi.setEditable(false);
        KodePropinsi.setHighlighter(null);
        KodePropinsi.setName("KodePropinsi"); // NOI18N
        FormInput.add(KodePropinsi);
        KodePropinsi.setBounds(528, 100, 40, 23);

        NamaPropinsi.setEditable(false);
        NamaPropinsi.setHighlighter(null);
        NamaPropinsi.setName("NamaPropinsi"); // NOI18N
        FormInput.add(NamaPropinsi);
        NamaPropinsi.setBounds(570, 100, 220, 23);

        BtnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPropinsi.setMnemonic('X');
        BtnPropinsi.setToolTipText("Alt+X");
        BtnPropinsi.setName("BtnPropinsi"); // NOI18N
        BtnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPropinsiActionPerformed(evt);
            }
        });
        BtnPropinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPropinsiKeyPressed(evt);
            }
        });
        FormInput.add(BtnPropinsi);
        BtnPropinsi.setBounds(793, 100, 28, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(114, 100, 90, 23);

        jLabel30.setText("Jenis Pasien :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 250, 110, 23);

        KodeJenisPasien.setEditable(false);
        KodeJenisPasien.setHighlighter(null);
        KodeJenisPasien.setName("KodeJenisPasien"); // NOI18N
        FormInput.add(KodeJenisPasien);
        KodeJenisPasien.setBounds(114, 250, 35, 23);

        NamaJenisPasien.setEditable(false);
        NamaJenisPasien.setHighlighter(null);
        NamaJenisPasien.setName("NamaJenisPasien"); // NOI18N
        FormInput.add(NamaJenisPasien);
        NamaJenisPasien.setBounds(151, 250, 244, 23);

        BtnJenisPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenisPasien.setMnemonic('X');
        BtnJenisPasien.setToolTipText("Alt+X");
        BtnJenisPasien.setName("BtnJenisPasien"); // NOI18N
        BtnJenisPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisPasienActionPerformed(evt);
            }
        });
        BtnJenisPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJenisPasienKeyPressed(evt);
            }
        });
        FormInput.add(BtnJenisPasien);
        BtnJenisPasien.setBounds(398, 250, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRM.getText().trim().equals("")||NamaPasien.getText().trim().equals("")){
            Valid.textKosong(NoRM,"Pasien");
        }else if(Inisial.getText().trim().equals("")){
            Valid.textKosong(Inisial,"Inisial");
        }else if(KodeJK.getText().trim().equals("")||NamaJK.getText().trim().equals("")){
            Valid.textKosong(BtnJK,"Jenis Kelamin");
        }else if(KodeKewarganegaraan.getText().trim().equals("")||NamaKewarganegaraan.getText().trim().equals("")){
            Valid.textKosong(BtnKewarganegaraan,"Kewarganegaraan");
        }else if(KodePenularan.getText().trim().equals("")||NamaPenularan.getText().trim().equals("")){
            Valid.textKosong(BtnSumberPenularan,"Sumber Penularan");
        }else if(KodeKelurahan.getText().trim().equals("")||NamaKelurahan.getText().trim().equals("")){
            Valid.textKosong(BtnKelurahan,"Kelurahan");
        }else if(KodeKecamatan.getText().trim().equals("")||NamaKecamatan.getText().trim().equals("")){
            Valid.textKosong(BtnKecamatan,"Kecamatan");
        }else if(KodeKabupaten.getText().trim().equals("")||NamaKabupaten.getText().trim().equals("")){
            Valid.textKosong(BtnKabupaten,"Kabupaten");
        }else if(KodePropinsi.getText().trim().equals("")||NamaPropinsi.getText().trim().equals("")){
            Valid.textKosong(BtnPropinsi,"Propinsi");
        }else if(KodeStatusKeluar.getText().trim().equals("")||NamaStatusKeluar.getText().trim().equals("")){
            Valid.textKosong(BtnStatusKeluar,"Status Keluar");
        }else if(KodeStatusRawat.getText().trim().equals("")||NamaStatusRawat.getText().trim().equals("")){
            Valid.textKosong(BtnStatusRawat,"Status Rawat");
        }else if(KodeStatusIsolasi.getText().trim().equals("")||NamaStatusIsolasi.getText().trim().equals("")){
            Valid.textKosong(BtnStatusIsolasi,"Status Isolasi");
        }else if(KodeJenisPasien.getText().trim().equals("")||NamaJenisPasien.getText().trim().equals("")){
            Valid.textKosong(BtnJenisPasien,"Jenis Pasien");
        }else{
            try {
                headers = new HttpHeaders();
                headers.add("X-rs-id",idrs);
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
                headers.add("X-pass",api.getHmac()); 
                body="{"+
                        "\"noc\": \""+NoKTP.getText()+"\"," +
                        "\"nomr\": \""+NoRM.getText()+"\"," +
                        "\"initial\": \""+Inisial.getText()+"\"," +
                        "\"nama_lengkap\": \""+NamaPasien.getText()+"\"," +
                        "\"tglmasuk\": \""+Valid.SetTgl(TglMasuk.getSelectedItem()+"")+"\"," +
                        "\"gender\": \""+KodeJK.getText()+"\"," +
                        "\"birthdate\": \""+Valid.SetTgl(TglLahir.getText()+"")+"\"," +
                        "\"kewarganegaraan\": \""+KodeKewarganegaraan.getText()+"\"," +
                        "\"sumber_penularan\": \""+KodePenularan.getText()+"\"," +
                        "\"kecamatan\": \""+KodeKecamatan.getText()+"\"," +
                        "\"tglkeluar\": \""+Valid.SetTgl(TglKeluar.getSelectedItem()+"")+"\"," +
                        "\"status_keluar\": \""+KodeStatusKeluar.getText()+"\"," +
                        "\"tgl_lapor\": \""+Valid.SetTgl(TglLapor.getSelectedItem()+"")+" "+TglLapor.getSelectedItem().toString().substring(11,19)+"\"," +
                        "\"status_rawat\": \""+KodeStatusRawat.getText()+"\"," +
                        "\"status_isolasi\": \""+KodeStatusIsolasi.getText()+"\"," +
                        "\"jenis_pasien\": \""+KodeJenisPasien.getText()+"\"," +
                        "\"email\": \""+Email.getText()+"\"," +
                        "\"notelp\": \""+NoTelp.getText()+"\"," +
                        "\"sebab_kematian\": \""+SebabKematian.getText()+"\"" +
                      "}";
                requestEntity = new HttpEntity(body,headers);
                //System.out.println(api.getRest().exchange(link+"/Pasien", HttpMethod.POST, requestEntity, String.class).getBody());
                root = mapper.readTree(api.getRest().exchange(link+"/Pasien", HttpMethod.POST, requestEntity, String.class).getBody());
                response = root.path("pasien");
                if(response.isArray()){
                    for(JsonNode list:response){
                        if(list.path("status").asText().equals("200")){
                            if(Sequel.menyimpantf("pasien_corona","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Pasien",33,new String[]{
                                NoKTP.getText(),NoRM.getText(),Inisial.getText(),NamaPasien.getText(),Valid.SetTgl(TglMasuk.getSelectedItem()+""),KodeJK.getText(), NamaJK.getText(), 
                                Valid.SetTgl(TglLahir.getText()+""),KodeKewarganegaraan.getText(),NamaKewarganegaraan.getText(),KodePenularan.getText(),NamaPenularan.getText(), 
                                KodeKelurahan.getText(),NamaKelurahan.getText(),KodeKecamatan.getText(),NamaKecamatan.getText(),KodeKabupaten.getText(),NamaKabupaten.getText(), 
                                KodePropinsi.getText(),NamaPropinsi.getText(),Valid.SetTgl(TglKeluar.getSelectedItem()+""),KodeStatusKeluar.getText(),NamaStatusKeluar.getText(),
                                Valid.SetTgl(TglLapor.getSelectedItem()+"")+" "+TglLapor.getSelectedItem().toString().substring(11,19),KodeStatusRawat.getText(),NamaStatusRawat.getText(), 
                                KodeStatusIsolasi.getText(),NamaStatusIsolasi.getText(),Email.getText(),NoTelp.getText(),SebabKematian.getText(),KodeJenisPasien.getText(),NamaJenisPasien.getText()
                            })==true){
                                tampil();
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,list.path("message").asText());
                        }
                    }
                } 
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
                }else if(ex.toString().contains("404")){
                    JOptionPane.showMessageDialog(rootPane,"Tidak ditemukan....!");
                }else if(ex.toString().contains("500")){
                    JOptionPane.showMessageDialog(rootPane,"Server internal error....!");
                }else if(ex.toString().contains("502")){
                    JOptionPane.showMessageDialog(rootPane,"Server kemenkes lelah broo....!");
                }
            }       
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,BtnJK,BtnBatal);
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
        if(tbObat.getSelectedRow()> -1){ 
            try {
                headers = new HttpHeaders();
                headers.add("X-rs-id",idrs);
                headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
                headers.add("X-pass",api.getHmac()); 
                headers.add("nomr",tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
                requestEntity = new HttpEntity(headers);
                root = mapper.readTree(api.getRest().exchange(link+"/Pasien", HttpMethod.DELETE,requestEntity, String.class).getBody());
                response = root.path("pasien");
                if(response.isArray()){
                    for(JsonNode list:response){
                        if(list.path("status").asText().equals("200")){
                            if(Sequel.queryu2tf("delete from pasien_corona where no_rkm_medis=?",1,new String[]{
                                    tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()
                                })==true){
                                tampil();
                            }
                        }else{
                            JOptionPane.showMessageDialog(rootPane,list.path("message").asText());
                        }
                    }
                } 
            } catch (Exception ex) {   
                System.out.println("Notif : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
                }else if(ex.toString().contains("404")){
                    JOptionPane.showMessageDialog(rootPane,"Tidak ditemukan....!");
                }else if(ex.toString().contains("500")){
                    JOptionPane.showMessageDialog(rootPane,"Server internal error....!");
                }else if(ex.toString().contains("502")){
                    JOptionPane.showMessageDialog(rootPane,"Server kemenkes lelah broo....!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

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
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptPasienCorona.jasper","report","::[ Data Bridging Pasien Corona Kemenkes ]::",
                    "select no_pengenal,no_rkm_medis,inisial,nama_lengkap,tgl_masuk,kode_jk,nama_jk,tgl_lahir,kode_kewarganegaraan,"+
                    "nama_kewarganegaraan,kode_penularan,sumber_penularan,kd_kelurahan,nm_kelurahan,kd_kecamatan,nm_kecamatan,kd_kabupaten,"+
                    "nm_kabupaten,kd_propinsi,nm_propinsi,tgl_keluar,kode_statuskeluar,nama_statuskeluar,tgl_lapor,kode_statusrawat,"+
                    "nama_statusrawat,kode_statusisolasi,nama_statusisolasi,email,notelp,sebab_kematian,kode_jenis_pasien,nama_jenis_pasien from pasien_corona "+
                    "where tgl_masuk between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":"and (no_pengenal like '"+TCari.getText().trim()+"' or no_rkm_medis like '"+TCari.getText().trim()+"' or "+
                    "nama_kewarganegaraan like '"+TCari.getText().trim()+"' or sumber_penularan like '"+TCari.getText().trim()+"' or "+
                    "nm_kelurahan like '"+TCari.getText().trim()+"' or nm_kecamatan like '"+TCari.getText().trim()+"' or nm_kabupaten like '"+TCari.getText().trim()+"' or "+
                    "nm_propinsi like '"+TCari.getText().trim()+"' or nama_statuskeluar like '"+TCari.getText().trim()+"' or "+
                    "nama_statusrawat like '"+TCari.getText().trim()+"' or nama_statusisolasi like '"+TCari.getText().trim()+"' or "+
                    "sebab_kematian like '"+TCari.getText().trim()+"' or nama_jenis_pasien like '"+TCari.getText().trim()+"') ")+
                    "order by tgl_masuk",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, NamaPasien);
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
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

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

    private void BtnJKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJKActionPerformed
        jk.setSize(300,300);
        jk.setLocationRelativeTo(null);
        jk.setVisible(true);
    }//GEN-LAST:event_BtnJKActionPerformed

    private void BtnJKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJKKeyPressed
        Valid.pindah(evt,Inisial,TglLapor);
    }//GEN-LAST:event_BtnJKKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoRM.getText().trim().equals("")||NamaPasien.getText().trim().equals("")){
            Valid.textKosong(NoRM,"Pasien");
        }else if(Inisial.getText().trim().equals("")){
            Valid.textKosong(Inisial,"Inisial");
        }else if(KodeJK.getText().trim().equals("")||NamaJK.getText().trim().equals("")){
            Valid.textKosong(BtnJK,"Jenis Kelamin");
        }else if(KodeKewarganegaraan.getText().trim().equals("")||NamaKewarganegaraan.getText().trim().equals("")){
            Valid.textKosong(BtnKewarganegaraan,"Kewarganegaraan");
        }else if(KodePenularan.getText().trim().equals("")||NamaPenularan.getText().trim().equals("")){
            Valid.textKosong(BtnSumberPenularan,"Sumber Penularan");
        }else if(KodeKelurahan.getText().trim().equals("")||NamaKelurahan.getText().trim().equals("")){
            Valid.textKosong(BtnKelurahan,"Kelurahan");
        }else if(KodeKecamatan.getText().trim().equals("")||NamaKecamatan.getText().trim().equals("")){
            Valid.textKosong(BtnKecamatan,"Kecamatan");
        }else if(KodeKabupaten.getText().trim().equals("")||NamaKabupaten.getText().trim().equals("")){
            Valid.textKosong(BtnKabupaten,"Kabupaten");
        }else if(KodePropinsi.getText().trim().equals("")||NamaPropinsi.getText().trim().equals("")){
            Valid.textKosong(BtnPropinsi,"Propinsi");
        }else if(KodeStatusKeluar.getText().trim().equals("")||NamaStatusKeluar.getText().trim().equals("")){
            Valid.textKosong(BtnStatusKeluar,"Status Keluar");
        }else if(KodeStatusRawat.getText().trim().equals("")||NamaStatusRawat.getText().trim().equals("")){
            Valid.textKosong(BtnStatusRawat,"Status Rawat");
        }else if(KodeStatusIsolasi.getText().trim().equals("")||NamaStatusIsolasi.getText().trim().equals("")){
            Valid.textKosong(BtnStatusIsolasi,"Status Isolasi");
        }else if(KodeJenisPasien.getText().trim().equals("")||NamaJenisPasien.getText().trim().equals("")){
            Valid.textKosong(BtnJenisPasien,"Jenis Pasien");
        }else{
            if(tbObat.getSelectedRow()!= -1){
                try {
                    headers = new HttpHeaders();
                    headers.add("X-rs-id",idrs);
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
                    headers.add("X-pass",api.getHmac()); 
                    body="{"+
                            "\"noc\": \""+NoKTP.getText()+"\"," +
                            "\"nomr\": \""+NoRM.getText()+"\"," +
                            "\"initial\": \""+Inisial.getText()+"\"," +
                            "\"nama_lengkap\": \""+NamaPasien.getText()+"\"," +
                            "\"tglmasuk\": \""+Valid.SetTgl(TglMasuk.getSelectedItem()+"")+"\"," +
                            "\"gender\": \""+KodeJK.getText()+"\"," +
                            "\"birthdate\": \""+Valid.SetTgl(TglLahir.getText()+"")+"\"," +
                            "\"kewarganegaraan\": \""+KodeKewarganegaraan.getText()+"\"," +
                            "\"sumber_penularan\": \""+KodePenularan.getText()+"\"," +
                            "\"kecamatan\": \""+KodeKecamatan.getText()+"\"," +
                            "\"tglkeluar\": \""+Valid.SetTgl(TglKeluar.getSelectedItem()+"")+"\"," +
                            "\"status_keluar\": \""+KodeStatusKeluar.getText()+"\"," +
                            "\"tgl_lapor\": \""+Valid.SetTgl(TglLapor.getSelectedItem()+"")+" "+TglLapor.getSelectedItem().toString().substring(11,19)+"\"," +
                            "\"status_rawat\": \""+KodeStatusRawat.getText()+"\"," +
                            "\"status_isolasi\": \""+KodeStatusIsolasi.getText()+"\"," +
                            "\"jenis_pasien\": \""+KodeJenisPasien.getText()+"\"," +
                            "\"email\": \""+Email.getText()+"\"," +
                            "\"notelp\": \""+NoTelp.getText()+"\"," +
                            "\"sebab_kematian\": \""+SebabKematian.getText()+"\"" +
                          "}";
                    requestEntity = new HttpEntity(body,headers);
                    root = mapper.readTree(api.getRest().exchange(link+"/Pasien", HttpMethod.PUT, requestEntity, String.class).getBody());
                    response = root.path("pasien");
                    if(response.isArray()){
                        for(JsonNode list:response){
                            if(list.path("status").asText().equals("200")){
                                if(Sequel.mengedittf("pasien_corona","no_rkm_medis=?","no_pengenal=?,no_rkm_medis=?,inisial=?,nama_lengkap=?,tgl_masuk=?,kode_jk=?,nama_jk=?,tgl_lahir=?,kode_kewarganegaraan=?,nama_kewarganegaraan=?,kode_penularan=?,sumber_penularan=?,kd_kelurahan=?,nm_kelurahan=?,kd_kecamatan=?,nm_kecamatan=?,kd_kabupaten=?,nm_kabupaten=?,kd_propinsi=?,nm_propinsi=?,tgl_keluar=?,kode_statuskeluar=?,nama_statuskeluar=?,tgl_lapor=?,kode_statusrawat=?,nama_statusrawat=?,kode_statusisolasi=?,nama_statusisolasi=?,email=?,notelp=?,sebab_kematian=?,kode_jenis_pasien=?,nama_jenis_pasien=?",34,new String[]{
                                    NoKTP.getText(),NoRM.getText(),Inisial.getText(),NamaPasien.getText(),Valid.SetTgl(TglMasuk.getSelectedItem()+""),KodeJK.getText(), NamaJK.getText(),Valid.SetTgl(TglLahir.getText()+""),KodeKewarganegaraan.getText(),NamaKewarganegaraan.getText(),KodePenularan.getText(),NamaPenularan.getText(),KodeKelurahan.getText(),NamaKelurahan.getText(),KodeKecamatan.getText(),NamaKecamatan.getText(),KodeKabupaten.getText(),NamaKabupaten.getText(),KodePropinsi.getText(),NamaPropinsi.getText(),Valid.SetTgl(TglKeluar.getSelectedItem()+""),KodeStatusKeluar.getText(),NamaStatusKeluar.getText(),
                                    Valid.SetTgl(TglLapor.getSelectedItem()+"")+" "+TglLapor.getSelectedItem().toString().substring(11,19),KodeStatusRawat.getText(),NamaStatusRawat.getText(),KodeStatusIsolasi.getText(),NamaStatusIsolasi.getText(),Email.getText(),NoTelp.getText(),SebabKematian.getText(),KodeJenisPasien.getText(),NamaJenisPasien.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()
                                })==true){
                                    tampil();
                                    emptTeks();
                                }
                            }else{
                                JOptionPane.showMessageDialog(rootPane,list.path("message").asText());
                            }
                        }
                    } 
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
                    }else if(ex.toString().contains("404")){
                        JOptionPane.showMessageDialog(rootPane,"Tidak ditemukan....!");
                    }else if(ex.toString().contains("500")){
                        JOptionPane.showMessageDialog(rootPane,"Server internal error....!");
                    }else if(ex.toString().contains("502")){
                        JOptionPane.showMessageDialog(rootPane,"Server kemenkes lelah broo....!");
                    }
                }  
                    
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

    private void BtnKewarganegaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKewarganegaraanActionPerformed
        kewarganegaraan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kewarganegaraan.setLocationRelativeTo(internalFrame1);
        kewarganegaraan.setVisible(true);
    }//GEN-LAST:event_BtnKewarganegaraanActionPerformed

    private void BtnKewarganegaraanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKewarganegaraanKeyPressed
        Valid.pindah(evt, TglKeluar,BtnSumberPenularan);
    }//GEN-LAST:event_BtnKewarganegaraanKeyPressed

    private void BtnSumberPenularanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSumberPenularanActionPerformed
        penularan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penularan.setLocationRelativeTo(internalFrame1);
        penularan.setVisible(true);
    }//GEN-LAST:event_BtnSumberPenularanActionPerformed

    private void BtnSumberPenularanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSumberPenularanKeyPressed
        Valid.pindah(evt,BtnKewarganegaraan,BtnJenisPasien);
    }//GEN-LAST:event_BtnSumberPenularanKeyPressed

    private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        if(KodeKabupaten.getText().equals("")||NamaKabupaten.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Silahkan pilih kabupaten terlebih dahulu..!!");
            BtnKabupaten.requestFocus();
        }else{
            kecamatan.setCari(NamaKecamatan.getText());
            kecamatan.SetKab(KodeKabupaten.getText());
            kecamatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kecamatan.setLocationRelativeTo(internalFrame1);
            kecamatan.setVisible(true);
        }
    }//GEN-LAST:event_BtnKecamatanActionPerformed

    private void BtnKecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKecamatanKeyPressed
        Valid.pindah(evt,BtnKelurahan,BtnKabupaten);
    }//GEN-LAST:event_BtnKecamatanKeyPressed

    private void BtnStatusKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnStatusKeluarActionPerformed
        statuskeluar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        statuskeluar.setLocationRelativeTo(internalFrame1);
        statuskeluar.setVisible(true);
    }//GEN-LAST:event_BtnStatusKeluarActionPerformed

    private void BtnStatusKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnStatusKeluarKeyPressed
        Valid.pindah(evt,BtnPropinsi,BtnStatusRawat);
    }//GEN-LAST:event_BtnStatusKeluarKeyPressed

    private void BtnStatusRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnStatusRawatActionPerformed
        statusrawat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        statusrawat.setLocationRelativeTo(internalFrame1);
        statusrawat.setVisible(true);
    }//GEN-LAST:event_BtnStatusRawatActionPerformed

    private void BtnStatusRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnStatusRawatKeyPressed
        Valid.pindah(evt, BtnStatusKeluar,BtnStatusIsolasi);
    }//GEN-LAST:event_BtnStatusRawatKeyPressed

    private void BtnStatusIsolasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnStatusIsolasiActionPerformed
        statusisolasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        statusisolasi.setLocationRelativeTo(internalFrame1);
        statusisolasi.setVisible(true);
    }//GEN-LAST:event_BtnStatusIsolasiActionPerformed

    private void BtnStatusIsolasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnStatusIsolasiKeyPressed
        Valid.pindah(evt,BtnStatusRawat,SebabKematian);
    }//GEN-LAST:event_BtnStatusIsolasiKeyPressed

    private void BtnKelurahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanActionPerformed
        kelurahan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kelurahan.setLocationRelativeTo(internalFrame1);
        kelurahan.setVisible(true);
    }//GEN-LAST:event_BtnKelurahanActionPerformed

    private void BtnKelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKelurahanKeyPressed
        Valid.pindah(evt, BtnJenisPasien,BtnKecamatan);
    }//GEN-LAST:event_BtnKelurahanKeyPressed

    private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
        if(KodePropinsi.getText().equals("")||NamaPropinsi.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Silahkan pilih propinsi terlebih dahulu..!!");
            BtnPropinsi.requestFocus();
        }else{
            kabupaten.setCari(NamaKabupaten.getText());
            kabupaten.SetProp(KodePropinsi.getText());
            kabupaten.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            kabupaten.setLocationRelativeTo(internalFrame1);
            kabupaten.setVisible(true);
        }
    }//GEN-LAST:event_BtnKabupatenActionPerformed

    private void BtnKabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKabupatenKeyPressed
        Valid.pindah(evt, BtnKecamatan,BtnPropinsi);
    }//GEN-LAST:event_BtnKabupatenKeyPressed

    private void BtnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPropinsiActionPerformed
        propinsi.setCari(NamaPropinsi.getText());
        propinsi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        propinsi.setLocationRelativeTo(internalFrame1);
        propinsi.setVisible(true);
    }//GEN-LAST:event_BtnPropinsiActionPerformed

    private void BtnPropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPropinsiKeyPressed
        Valid.pindah(evt,BtnKabupaten,BtnStatusKeluar);
    }//GEN-LAST:event_BtnPropinsiKeyPressed

    private void InisialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InisialKeyPressed
        Valid.pindah(evt, TCari, BtnJK);
    }//GEN-LAST:event_InisialKeyPressed

    private void TglLaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglLaporKeyPressed
        Valid.pindah(evt, BtnJK,TglMasuk);
    }//GEN-LAST:event_TglLaporKeyPressed

    private void TglMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMasukKeyPressed
        Valid.pindah(evt, TglLapor,TglKeluar);
    }//GEN-LAST:event_TglMasukKeyPressed

    private void TglKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKeluarKeyPressed
        Valid.pindah(evt, TglMasuk,BtnKewarganegaraan);
    }//GEN-LAST:event_TglKeluarKeyPressed

    private void ppDiagnosaPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDiagnosaPasienBtnPrintActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            CoronaDiagnosa form=new CoronaDiagnosa(null,false);
            form.SetPasien(NoRM.getText(),NamaPasien.getText(),DTPCari1.getDate(),DTPCari2.getDate());
            form.isCek();
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data pasien...!!!!");
            BtnBatal.requestFocus();
        } 
            
    }//GEN-LAST:event_ppDiagnosaPasienBtnPrintActionPerformed

    private void BtnJenisPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisPasienActionPerformed
        jenispasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jenispasien.setLocationRelativeTo(internalFrame1);
        jenispasien.setVisible(true);
    }//GEN-LAST:event_BtnJenisPasienActionPerformed

    private void BtnJenisPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJenisPasienKeyPressed
        Valid.pindah(evt,BtnSumberPenularan,BtnKelurahan);
    }//GEN-LAST:event_BtnJenisPasienKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            CoronaPasien dialog = new CoronaPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnJK;
    private widget.Button BtnJenisPasien;
    private widget.Button BtnKabupaten;
    private widget.Button BtnKecamatan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKelurahan;
    private widget.Button BtnKewarganegaraan;
    private widget.Button BtnPrint;
    private widget.Button BtnPropinsi;
    private widget.Button BtnSimpan;
    private widget.Button BtnStatusIsolasi;
    private widget.Button BtnStatusKeluar;
    private widget.Button BtnStatusRawat;
    private widget.Button BtnSumberPenularan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Email;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Inisial;
    private widget.TextBox KodeJK;
    private widget.TextBox KodeJenisPasien;
    private widget.TextBox KodeKabupaten;
    private widget.TextBox KodeKecamatan;
    private widget.TextBox KodeKelurahan;
    private widget.TextBox KodeKewarganegaraan;
    private widget.TextBox KodePenularan;
    private widget.TextBox KodePropinsi;
    private widget.TextBox KodeStatusIsolasi;
    private widget.TextBox KodeStatusKeluar;
    private widget.TextBox KodeStatusRawat;
    private widget.Label LCount;
    private widget.TextBox NamaJK;
    private widget.TextBox NamaJenisPasien;
    private widget.TextBox NamaKabupaten;
    private widget.TextBox NamaKecamatan;
    private widget.TextBox NamaKelurahan;
    private widget.TextBox NamaKewarganegaraan;
    private widget.TextBox NamaPasien;
    private widget.TextBox NamaPenularan;
    private widget.TextBox NamaPropinsi;
    private widget.TextBox NamaStatusIsolasi;
    private widget.TextBox NamaStatusKeluar;
    private widget.TextBox NamaStatusRawat;
    private widget.TextBox NoKTP;
    private widget.TextBox NoRM;
    private widget.TextBox NoTelp;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox SebabKematian;
    private widget.TextBox TCari;
    private widget.Tanggal TglKeluar;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglLapor;
    private widget.Tanggal TglMasuk;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppDiagnosaPasien;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {     
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement(
                    "select no_pengenal,no_rkm_medis,inisial,nama_lengkap,tgl_masuk,kode_jk,nama_jk,tgl_lahir,kode_kewarganegaraan,"+
                    "nama_kewarganegaraan,kode_penularan,sumber_penularan,kd_kelurahan,nm_kelurahan,kd_kecamatan,nm_kecamatan,kd_kabupaten,"+
                    "nm_kabupaten,kd_propinsi,nm_propinsi,tgl_keluar,kode_statuskeluar,nama_statuskeluar,tgl_lapor,kode_statusrawat,"+
                    "nama_statusrawat,kode_statusisolasi,nama_statusisolasi,email,notelp,sebab_kematian,kode_jenis_pasien,nama_jenis_pasien "+
                    "from pasien_corona where tgl_masuk between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (no_pengenal like ? or no_rkm_medis like ? or nama_kewarganegaraan like ? or "+
                    "sumber_penularan like ? or nm_kelurahan like ? or nm_kecamatan like ? or nm_kabupaten like ? or nm_propinsi like ? or "+
                    "nama_statuskeluar like ? or nama_statusrawat like ? or nama_statusisolasi like ? or sebab_kematian like ? or nama_jenis_pasien like ?) ")+
                    "order by tgl_masuk");
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
                    tabMode.addRow(new String[]{
                        rs.getString("no_pengenal"),rs.getString("no_rkm_medis"),rs.getString("nama_lengkap"),rs.getString("inisial"),rs.getString("kode_jk"),rs.getString("nama_jk"),
                        rs.getString("tgl_lahir"),rs.getString("email"),rs.getString("notelp"),rs.getString("tgl_lapor"),rs.getString("tgl_masuk"),rs.getString("tgl_keluar"),
                        rs.getString("kode_kewarganegaraan"),rs.getString("nama_kewarganegaraan"),rs.getString("kode_penularan"),rs.getString("sumber_penularan"),rs.getString("kd_kelurahan"),
                        rs.getString("nm_kelurahan"),rs.getString("kd_kecamatan"),rs.getString("nm_kecamatan"),rs.getString("kd_kabupaten"),rs.getString("nm_kabupaten"),
                        rs.getString("kd_propinsi"),rs.getString("nm_propinsi"),rs.getString("kode_statuskeluar"),rs.getString("nama_statuskeluar"),rs.getString("kode_statusrawat"),
                        rs.getString("nama_statusrawat"),rs.getString("kode_statusisolasi"),rs.getString("nama_statusisolasi"),rs.getString("sebab_kematian"),rs.getString("kode_jenis_pasien"),
                        rs.getString("nama_jenis_pasien")
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
        NoRM.setText("");
        NamaPasien.setText("");
        NoKTP.setText("");
        Inisial.setText("");
        KodeJK.setText("");
        NamaJK.setText("");
        TglLahir.setText("");
        TglLapor.setDate(new Date());
        TglMasuk.setDate(new Date());
        TglKeluar.setDate(new Date());
        Email.setText("");
        NoTelp.setText("");
        KodeKewarganegaraan.setText("");
        NamaKewarganegaraan.setText("");
        KodePenularan.setText("");
        NamaPenularan.setText("");
        KodeKelurahan.setText("");
        NamaKelurahan.setText("");
        KodeKecamatan.setText("");
        NamaKecamatan.setText("");
        KodeKabupaten.setText("");
        NamaKabupaten.setText("");
        KodePropinsi.setText("");
        NamaPropinsi.setText("");
        KodeStatusKeluar.setText("");
        NamaStatusKeluar.setText("");
        KodeStatusRawat.setText("");
        NamaStatusRawat.setText("");
        KodeStatusIsolasi.setText("");
        NamaStatusIsolasi.setText("");
        KodeJenisPasien.setText("");
        NamaJenisPasien.setText("");
        SebabKematian.setText("");
        Inisial.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){      
            NoKTP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            NoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            NamaPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Inisial.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            KodeJK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            NamaJK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Email.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KodeKewarganegaraan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            NamaKewarganegaraan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KodePenularan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NamaPenularan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KodeKelurahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NamaKelurahan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KodeKecamatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            NamaKecamatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KodeKabupaten.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            NamaKabupaten.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KodePropinsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NamaPropinsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KodeStatusKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            NamaStatusKeluar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KodeStatusRawat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            NamaStatusRawat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            KodeStatusIsolasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            NamaStatusIsolasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            SebabKematian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            KodeJenisPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            NamaJenisPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            TglLahir.setText(Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()));
            Valid.SetTgl2(TglLapor,tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Valid.SetTgl(TglMasuk,tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Valid.SetTgl(TglKeluar,tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,306));
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
        BtnSimpan.setEnabled(akses.getpasien_corona());
        BtnHapus.setEnabled(akses.getpasien_corona());
        BtnEdit.setEnabled(akses.getpasien_corona());
        ppDiagnosaPasien.setEnabled(akses.getdiagnosa_pasien_corona());
    }
    
    public void setPasien(String nomr){
        ChkInput.setSelected(true);
        isForm(); 
        try {
            ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.kd_kel,kelurahan.nm_kel,pasien.no_ktp,pasien.jk, "+
                    "date_format(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir,pasien.email,pasien.no_tlp,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop "+
                    "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where pasien.no_rkm_medis=?");
            try {            
                ps.setString(1,nomr);
                rs=ps.executeQuery();
                if(rs.next()){
                    NoKTP.setText(rs.getString("no_ktp"));
                    NoRM.setText(rs.getString("no_rkm_medis"));
                    NamaPasien.setText(rs.getString("nm_pasien"));
                    KodeJK.setText(rs.getString("jk").replaceAll("L","1").replaceAll("P","2"));
                    NamaJK.setText(rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Email.setText(rs.getString("email"));
                    NoTelp.setText(rs.getString("no_tlp"));
                    KodeKelurahan.setText(rs.getString("kd_kel"));
                    NamaKelurahan.setText(rs.getString("nm_kel"));
                    NamaKecamatan.setText(rs.getString("nm_kec"));
                    NamaKabupaten.setText(rs.getString("nm_kab"));
                    NamaPropinsi.setText(rs.getString("nm_prop"));
                }
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
    
    public JTable getTable(){
        return tbObat;
    }

}

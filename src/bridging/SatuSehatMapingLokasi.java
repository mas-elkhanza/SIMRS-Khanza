/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgKamar;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;

/**
 *
 * @author dosen
 */
public final class SatuSehatMapingLokasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeKamar,tabModeRuangOK,tabModeRuangLabPK,tabModeRuangLabPA,tabModeRuangLabMB,tabModeRuangRadiologi,tabModeRuangFarmasi;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;    
    private int i=0,pilih=0;
    private SatuSehatCariOrganisasi organisasi=new SatuSehatCariOrganisasi(null,false);
    private String link="",json="";
    private ApiSatuSehat api=new ApiSatuSehat();
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;

    /** Creates new form DlgJnsPerawatanRalan
     * @param parent
     * @param modal */
    public SatuSehatMapingLokasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "Kode Unit","Nama Unit","ID Lokasi Satu Sehat","Longitude","Latitude","Altitude",
                "Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(215);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(215);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeKamar=new DefaultTableModel(null,new Object[]{
                "Nomor Ruang","Kamar/Ruang","ID Lokasi Satu Sehat","Longitude","Latitude","Altitude",
                "Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLokasiKamar.setModel(tabModeKamar);

        tbLokasiKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLokasiKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbLokasiKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(215);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(215);
            }
        }
        tbLokasiKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRuangOK=new DefaultTableModel(null,new Object[]{
                "ID Lokasi Satu Sehat","Longitude","Latitude","Altitude","Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLokasiRuangOK.setModel(tabModeRuangOK);

        tbLokasiRuangOK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLokasiRuangOK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbLokasiRuangOK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(215);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(215);
            }
        }
        tbLokasiRuangOK.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeRuangLabPK=new DefaultTableModel(null,new Object[]{
                "ID Lokasi Satu Sehat","Longitude","Latitude","Altitude","Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLokasiRuangLabPK.setModel(tabModeRuangLabPK);

        tbLokasiRuangLabPK.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLokasiRuangLabPK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbLokasiRuangLabPK.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(215);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(215);
            }
        }
        tbLokasiRuangLabPK.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRuangLabPA=new DefaultTableModel(null,new Object[]{
                "ID Lokasi Satu Sehat","Longitude","Latitude","Altitude","Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLokasiRuangLabPA.setModel(tabModeRuangLabPA);

        tbLokasiRuangLabPA.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLokasiRuangLabPA.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbLokasiRuangLabPA.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(215);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(215);
            }
        }
        tbLokasiRuangLabPA.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRuangLabMB=new DefaultTableModel(null,new Object[]{
                "ID Lokasi Satu Sehat","Longitude","Latitude","Altitude","Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLokasiRuangLabMB.setModel(tabModeRuangLabMB);

        tbLokasiRuangLabMB.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLokasiRuangLabMB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbLokasiRuangLabMB.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(215);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(215);
            }
        }
        tbLokasiRuangLabMB.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRuangRadiologi=new DefaultTableModel(null,new Object[]{
                "ID Lokasi Satu Sehat","Longitude","Latitude","Altitude","Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLokasiRuangRadiologi.setModel(tabModeRuangRadiologi);

        tbLokasiRuangRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLokasiRuangRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbLokasiRuangRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(215);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(215);
            }
        }
        tbLokasiRuangRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRuangFarmasi=new DefaultTableModel(null,new Object[]{
                "Kode Farmasi","Nama Depo Farmasi","ID Lokasi Satu Sehat","Longitude","Latitude","Altitude",
                "Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbLokasiFarmasi.setModel(tabModeRuangFarmasi);

        tbLokasiFarmasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLokasiFarmasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbLokasiFarmasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(215);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(215);
            }
        }
        tbLokasiFarmasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        KodeDepartemen.setDocument(new batasInput((byte)5).getKata(KodeDepartemen)); 
        Longitude.setDocument(new batasInput((byte)30).getKata(Longitude)); 
        Latitude.setDocument(new batasInput((byte)30).getKata(Latitude)); 
        Altitude.setDocument(new batasInput((byte)30).getKata(Altitude)); 
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
        
        organisasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(organisasi.getTable().getSelectedRow()!= -1){                
                    if(pilih==1){
                        KodeDepartemen.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),0).toString());
                        NamaDepartemen.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),1).toString());
                        IDOrganisasi.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),2).toString());
                        KodeDepartemen.requestFocus();
                    }else if(pilih==2){
                        KodeDepartemenKamar.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),0).toString());
                        NamaDepartemenKamar.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),1).toString());
                        IDOrganisasiKamar.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),2).toString());
                        KodeDepartemenKamar.requestFocus();
                    }else if(pilih==3){
                        KodeDepartemenRuangOK.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),0).toString());
                        NamaDepartemenRuangOK.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),1).toString());
                        IDOrganisasiRuangOK.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),2).toString());
                        KodeDepartemenRuangOK.requestFocus();
                    }else if(pilih==4){
                        KodeDepartemenRuangLabPK.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),0).toString());
                        NamaDepartemenRuangLabPK.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),1).toString());
                        IDOrganisasiRuangLabPK.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),2).toString());
                        KodeDepartemenRuangLabPK.requestFocus();
                    }else if(pilih==5){
                        KodeDepartemenRuangLabPA.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),0).toString());
                        NamaDepartemenRuangLabPA.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),1).toString());
                        IDOrganisasiRuangLabPA.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),2).toString());
                        KodeDepartemenRuangLabPA.requestFocus();
                    }else if(pilih==6){
                        KodeDepartemenRuangLabMB.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),0).toString());
                        NamaDepartemenRuangLabMB.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),1).toString());
                        IDOrganisasiRuangLabMB.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),2).toString());
                        KodeDepartemenRuangLabMB.requestFocus();
                    }else if(pilih==7){
                        KodeDepartemenRuangRadiologi.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),0).toString());
                        NamaDepartemenRuangRadiologi.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),1).toString());
                        IDOrganisasiRuangRadiologi.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),2).toString());
                        KodeDepartemenRuangRadiologi.requestFocus();
                    }else if(pilih==8){
                        KodeDepartemenFarmasi.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),0).toString());
                        NamaDepartemenFarmasi.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),1).toString());
                        IDOrganisasiFarmasi.setText(organisasi.getTable().getValueAt(organisasi.getTable().getSelectedRow(),2).toString());
                        KodeDepartemenFarmasi.requestFocus();
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
            link=koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
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

        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        KodeDepartemen = new widget.TextBox();
        NamaDepartemen = new widget.TextBox();
        btnDepartemenRS = new widget.Button();
        jLabel5 = new widget.Label();
        KodePoli = new widget.TextBox();
        NamaPoli = new widget.TextBox();
        btnPoliRS = new widget.Button();
        IDOrganisasi = new widget.TextBox();
        Longitude = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        Latitude = new widget.TextBox();
        jLabel10 = new widget.Label();
        Altitude = new widget.TextBox();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        FormInput1 = new widget.PanelBiasa();
        jLabel11 = new widget.Label();
        KodeDepartemenKamar = new widget.TextBox();
        NamaDepartemenKamar = new widget.TextBox();
        btnDepartemenKamar = new widget.Button();
        jLabel12 = new widget.Label();
        KodeKamar = new widget.TextBox();
        NamaKamar = new widget.TextBox();
        btnKamar = new widget.Button();
        IDOrganisasiKamar = new widget.TextBox();
        LongitudeKamar = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        LatitudeKamar = new widget.TextBox();
        jLabel15 = new widget.Label();
        AltitudeKamar = new widget.TextBox();
        Scroll1 = new widget.ScrollPane();
        tbLokasiKamar = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        FormInput2 = new widget.PanelBiasa();
        jLabel16 = new widget.Label();
        KodeDepartemenRuangOK = new widget.TextBox();
        NamaDepartemenRuangOK = new widget.TextBox();
        btnDepartemenRuangOK = new widget.Button();
        IDOrganisasiRuangOK = new widget.TextBox();
        LongitudeRuangOK = new widget.TextBox();
        jLabel18 = new widget.Label();
        jLabel19 = new widget.Label();
        LatitudeRuangOK = new widget.TextBox();
        jLabel20 = new widget.Label();
        AltitudeRuangOK = new widget.TextBox();
        Scroll2 = new widget.ScrollPane();
        tbLokasiRuangOK = new widget.Table();
        internalFrame5 = new widget.InternalFrame();
        FormInput3 = new widget.PanelBiasa();
        jLabel17 = new widget.Label();
        KodeDepartemenRuangLabPK = new widget.TextBox();
        NamaDepartemenRuangLabPK = new widget.TextBox();
        btnDepartemenRuangLabPK = new widget.Button();
        IDOrganisasiRuangLabPK = new widget.TextBox();
        LongitudeRuangLabPK = new widget.TextBox();
        jLabel21 = new widget.Label();
        jLabel22 = new widget.Label();
        LatitudeRuangLabPK = new widget.TextBox();
        jLabel23 = new widget.Label();
        AltitudeRuangLabPK = new widget.TextBox();
        Scroll3 = new widget.ScrollPane();
        tbLokasiRuangLabPK = new widget.Table();
        internalFrame6 = new widget.InternalFrame();
        FormInput4 = new widget.PanelBiasa();
        jLabel24 = new widget.Label();
        KodeDepartemenRuangLabPA = new widget.TextBox();
        NamaDepartemenRuangLabPA = new widget.TextBox();
        btnDepartemenRuangLabPA = new widget.Button();
        IDOrganisasiRuangLabPA = new widget.TextBox();
        LongitudeRuangLabPA = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel26 = new widget.Label();
        LatitudeRuangLabPA = new widget.TextBox();
        jLabel27 = new widget.Label();
        AltitudeRuangLabPA = new widget.TextBox();
        Scroll4 = new widget.ScrollPane();
        tbLokasiRuangLabPA = new widget.Table();
        internalFrame7 = new widget.InternalFrame();
        FormInput5 = new widget.PanelBiasa();
        jLabel28 = new widget.Label();
        KodeDepartemenRuangLabMB = new widget.TextBox();
        NamaDepartemenRuangLabMB = new widget.TextBox();
        btnDepartemenRuangLabMB = new widget.Button();
        IDOrganisasiRuangLabMB = new widget.TextBox();
        LongitudeRuangLabMB = new widget.TextBox();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        LatitudeRuangLabMB = new widget.TextBox();
        jLabel31 = new widget.Label();
        AltitudeRuangLabMB = new widget.TextBox();
        Scroll5 = new widget.ScrollPane();
        tbLokasiRuangLabMB = new widget.Table();
        internalFrame8 = new widget.InternalFrame();
        FormInput6 = new widget.PanelBiasa();
        jLabel32 = new widget.Label();
        KodeDepartemenRuangRadiologi = new widget.TextBox();
        NamaDepartemenRuangRadiologi = new widget.TextBox();
        btnDepartemenRuangRadiologi = new widget.Button();
        IDOrganisasiRuangRadiologi = new widget.TextBox();
        LongitudeRuangRadiologi = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        LatitudeRuangRadiologi = new widget.TextBox();
        jLabel35 = new widget.Label();
        AltitudeRuangRadiologi = new widget.TextBox();
        Scroll6 = new widget.ScrollPane();
        tbLokasiRuangRadiologi = new widget.Table();
        internalFrame9 = new widget.InternalFrame();
        FormInput7 = new widget.PanelBiasa();
        jLabel36 = new widget.Label();
        KodeDepartemenFarmasi = new widget.TextBox();
        NamaDepartemenFarmasi = new widget.TextBox();
        btnDepartemenFarmasi = new widget.Button();
        jLabel37 = new widget.Label();
        KodeFarmasi = new widget.TextBox();
        NamaFarmasi = new widget.TextBox();
        btnFarmasi = new widget.Button();
        IDOrganisasiFarmasi = new widget.TextBox();
        LongitudeFarmasi = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        LatitudeFarmasi = new widget.TextBox();
        jLabel40 = new widget.Label();
        AltitudeFarmasi = new widget.TextBox();
        Scroll7 = new widget.ScrollPane();
        tbLokasiFarmasi = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Mapping Lokasi Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(null);

        jLabel4.setText("Organisasi :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(170, 40, 74, 23);

        KodeDepartemen.setEditable(false);
        KodeDepartemen.setHighlighter(null);
        KodeDepartemen.setName("KodeDepartemen"); // NOI18N
        FormInput.add(KodeDepartemen);
        KodeDepartemen.setBounds(248, 40, 55, 23);

        NamaDepartemen.setEditable(false);
        NamaDepartemen.setHighlighter(null);
        NamaDepartemen.setName("NamaDepartemen"); // NOI18N
        FormInput.add(NamaDepartemen);
        NamaDepartemen.setBounds(305, 40, 176, 23);

        btnDepartemenRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenRS.setMnemonic('1');
        btnDepartemenRS.setToolTipText("Alt+1");
        btnDepartemenRS.setName("btnDepartemenRS"); // NOI18N
        btnDepartemenRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenRSActionPerformed(evt);
            }
        });
        btnDepartemenRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenRSKeyPressed(evt);
            }
        });
        FormInput.add(btnDepartemenRS);
        btnDepartemenRS.setBounds(705, 40, 28, 23);

        jLabel5.setText("Unit RS :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 59, 23);

        KodePoli.setEditable(false);
        KodePoli.setHighlighter(null);
        KodePoli.setName("KodePoli"); // NOI18N
        FormInput.add(KodePoli);
        KodePoli.setBounds(63, 10, 70, 23);

        NamaPoli.setEditable(false);
        NamaPoli.setHighlighter(null);
        NamaPoli.setName("NamaPoli"); // NOI18N
        FormInput.add(NamaPoli);
        NamaPoli.setBounds(135, 10, 195, 23);

        btnPoliRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoliRS.setMnemonic('1');
        btnPoliRS.setToolTipText("Alt+1");
        btnPoliRS.setName("btnPoliRS"); // NOI18N
        btnPoliRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliRSActionPerformed(evt);
            }
        });
        btnPoliRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliRSKeyPressed(evt);
            }
        });
        FormInput.add(btnPoliRS);
        btnPoliRS.setBounds(332, 10, 28, 23);

        IDOrganisasi.setEditable(false);
        IDOrganisasi.setHighlighter(null);
        IDOrganisasi.setName("IDOrganisasi"); // NOI18N
        FormInput.add(IDOrganisasi);
        IDOrganisasi.setBounds(483, 40, 220, 23);

        Longitude.setHighlighter(null);
        Longitude.setName("Longitude"); // NOI18N
        Longitude.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LongitudeKeyPressed(evt);
            }
        });
        FormInput.add(Longitude);
        Longitude.setBounds(430, 10, 120, 23);

        jLabel8.setText("Longitude :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(357, 10, 69, 23);

        jLabel9.setText("Latitude :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(549, 10, 60, 23);

        Latitude.setHighlighter(null);
        Latitude.setName("Latitude"); // NOI18N
        Latitude.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatitudeKeyPressed(evt);
            }
        });
        FormInput.add(Latitude);
        Latitude.setBounds(613, 10, 120, 23);

        jLabel10.setText("Altitude :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 40, 59, 23);

        Altitude.setHighlighter(null);
        Altitude.setName("Altitude"); // NOI18N
        Altitude.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AltitudeKeyPressed(evt);
            }
        });
        FormInput.add(Altitude);
        Altitude.setBounds(63, 40, 110, 23);

        internalFrame2.add(FormInput, java.awt.BorderLayout.PAGE_START);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Lokasi Ralan", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput1.setLayout(null);

        jLabel11.setText("Organisasi :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput1.add(jLabel11);
        jLabel11.setBounds(170, 40, 74, 23);

        KodeDepartemenKamar.setEditable(false);
        KodeDepartemenKamar.setHighlighter(null);
        KodeDepartemenKamar.setName("KodeDepartemenKamar"); // NOI18N
        FormInput1.add(KodeDepartemenKamar);
        KodeDepartemenKamar.setBounds(248, 40, 55, 23);

        NamaDepartemenKamar.setEditable(false);
        NamaDepartemenKamar.setHighlighter(null);
        NamaDepartemenKamar.setName("NamaDepartemenKamar"); // NOI18N
        FormInput1.add(NamaDepartemenKamar);
        NamaDepartemenKamar.setBounds(305, 40, 176, 23);

        btnDepartemenKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenKamar.setMnemonic('1');
        btnDepartemenKamar.setToolTipText("Alt+1");
        btnDepartemenKamar.setName("btnDepartemenKamar"); // NOI18N
        btnDepartemenKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenKamarActionPerformed(evt);
            }
        });
        btnDepartemenKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenKamarKeyPressed(evt);
            }
        });
        FormInput1.add(btnDepartemenKamar);
        btnDepartemenKamar.setBounds(705, 40, 28, 23);

        jLabel12.setText("Kamar :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput1.add(jLabel12);
        jLabel12.setBounds(0, 10, 59, 23);

        KodeKamar.setEditable(false);
        KodeKamar.setHighlighter(null);
        KodeKamar.setName("KodeKamar"); // NOI18N
        FormInput1.add(KodeKamar);
        KodeKamar.setBounds(63, 10, 70, 23);

        NamaKamar.setEditable(false);
        NamaKamar.setHighlighter(null);
        NamaKamar.setName("NamaKamar"); // NOI18N
        FormInput1.add(NamaKamar);
        NamaKamar.setBounds(135, 10, 195, 23);

        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamar.setMnemonic('1');
        btnKamar.setToolTipText("Alt+1");
        btnKamar.setName("btnKamar"); // NOI18N
        btnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarActionPerformed(evt);
            }
        });
        btnKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKamarKeyPressed(evt);
            }
        });
        FormInput1.add(btnKamar);
        btnKamar.setBounds(332, 10, 28, 23);

        IDOrganisasiKamar.setEditable(false);
        IDOrganisasiKamar.setHighlighter(null);
        IDOrganisasiKamar.setName("IDOrganisasiKamar"); // NOI18N
        FormInput1.add(IDOrganisasiKamar);
        IDOrganisasiKamar.setBounds(483, 40, 220, 23);

        LongitudeKamar.setHighlighter(null);
        LongitudeKamar.setName("LongitudeKamar"); // NOI18N
        LongitudeKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LongitudeKamarKeyPressed(evt);
            }
        });
        FormInput1.add(LongitudeKamar);
        LongitudeKamar.setBounds(430, 10, 120, 23);

        jLabel13.setText("Longitude :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput1.add(jLabel13);
        jLabel13.setBounds(357, 10, 69, 23);

        jLabel14.setText("Latitude :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput1.add(jLabel14);
        jLabel14.setBounds(549, 10, 60, 23);

        LatitudeKamar.setHighlighter(null);
        LatitudeKamar.setName("LatitudeKamar"); // NOI18N
        LatitudeKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatitudeKamarKeyPressed(evt);
            }
        });
        FormInput1.add(LatitudeKamar);
        LatitudeKamar.setBounds(613, 10, 120, 23);

        jLabel15.setText("Altitude :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput1.add(jLabel15);
        jLabel15.setBounds(0, 40, 59, 23);

        AltitudeKamar.setHighlighter(null);
        AltitudeKamar.setName("AltitudeKamar"); // NOI18N
        AltitudeKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AltitudeKamarKeyPressed(evt);
            }
        });
        FormInput1.add(AltitudeKamar);
        AltitudeKamar.setBounds(63, 40, 110, 23);

        internalFrame3.add(FormInput1, java.awt.BorderLayout.PAGE_START);

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbLokasiKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLokasiKamar.setName("tbLokasiKamar"); // NOI18N
        tbLokasiKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLokasiKamarMouseClicked(evt);
            }
        });
        tbLokasiKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbLokasiKamarKeyReleased(evt);
            }
        });
        Scroll1.setViewportView(tbLokasiKamar);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Lokasi Ranap", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput2.setName("FormInput2"); // NOI18N
        FormInput2.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput2.setLayout(null);

        jLabel16.setText("Organisasi :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput2.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        KodeDepartemenRuangOK.setEditable(false);
        KodeDepartemenRuangOK.setHighlighter(null);
        KodeDepartemenRuangOK.setName("KodeDepartemenRuangOK"); // NOI18N
        FormInput2.add(KodeDepartemenRuangOK);
        KodeDepartemenRuangOK.setBounds(79, 40, 75, 23);

        NamaDepartemenRuangOK.setEditable(false);
        NamaDepartemenRuangOK.setHighlighter(null);
        NamaDepartemenRuangOK.setName("NamaDepartemenRuangOK"); // NOI18N
        FormInput2.add(NamaDepartemenRuangOK);
        NamaDepartemenRuangOK.setBounds(156, 40, 275, 23);

        btnDepartemenRuangOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenRuangOK.setMnemonic('1');
        btnDepartemenRuangOK.setToolTipText("Alt+1");
        btnDepartemenRuangOK.setName("btnDepartemenRuangOK"); // NOI18N
        btnDepartemenRuangOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenRuangOKActionPerformed(evt);
            }
        });
        btnDepartemenRuangOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenRuangOKKeyPressed(evt);
            }
        });
        FormInput2.add(btnDepartemenRuangOK);
        btnDepartemenRuangOK.setBounds(705, 40, 28, 23);

        IDOrganisasiRuangOK.setEditable(false);
        IDOrganisasiRuangOK.setHighlighter(null);
        IDOrganisasiRuangOK.setName("IDOrganisasiRuangOK"); // NOI18N
        FormInput2.add(IDOrganisasiRuangOK);
        IDOrganisasiRuangOK.setBounds(433, 40, 270, 23);

        LongitudeRuangOK.setHighlighter(null);
        LongitudeRuangOK.setName("LongitudeRuangOK"); // NOI18N
        LongitudeRuangOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LongitudeRuangOKKeyPressed(evt);
            }
        });
        FormInput2.add(LongitudeRuangOK);
        LongitudeRuangOK.setBounds(79, 10, 165, 23);

        jLabel18.setText("Longitude :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput2.add(jLabel18);
        jLabel18.setBounds(0, 10, 75, 23);

        jLabel19.setText("Latitude :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput2.add(jLabel19);
        jLabel19.setBounds(261, 10, 60, 23);

        LatitudeRuangOK.setHighlighter(null);
        LatitudeRuangOK.setName("LatitudeRuangOK"); // NOI18N
        LatitudeRuangOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatitudeRuangOKKeyPressed(evt);
            }
        });
        FormInput2.add(LatitudeRuangOK);
        LatitudeRuangOK.setBounds(325, 10, 165, 23);

        jLabel20.setText("Altitude :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput2.add(jLabel20);
        jLabel20.setBounds(505, 10, 59, 23);

        AltitudeRuangOK.setHighlighter(null);
        AltitudeRuangOK.setName("AltitudeRuangOK"); // NOI18N
        AltitudeRuangOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AltitudeRuangOKKeyPressed(evt);
            }
        });
        FormInput2.add(AltitudeRuangOK);
        AltitudeRuangOK.setBounds(568, 10, 165, 23);

        internalFrame4.add(FormInput2, java.awt.BorderLayout.PAGE_START);

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbLokasiRuangOK.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLokasiRuangOK.setName("tbLokasiRuangOK"); // NOI18N
        tbLokasiRuangOK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLokasiRuangOKMouseClicked(evt);
            }
        });
        tbLokasiRuangOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbLokasiRuangOKKeyReleased(evt);
            }
        });
        Scroll2.setViewportView(tbLokasiRuangOK);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Lokasi OK", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput3.setName("FormInput3"); // NOI18N
        FormInput3.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput3.setLayout(null);

        jLabel17.setText("Organisasi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput3.add(jLabel17);
        jLabel17.setBounds(0, 40, 75, 23);

        KodeDepartemenRuangLabPK.setEditable(false);
        KodeDepartemenRuangLabPK.setHighlighter(null);
        KodeDepartemenRuangLabPK.setName("KodeDepartemenRuangLabPK"); // NOI18N
        FormInput3.add(KodeDepartemenRuangLabPK);
        KodeDepartemenRuangLabPK.setBounds(79, 40, 75, 23);

        NamaDepartemenRuangLabPK.setEditable(false);
        NamaDepartemenRuangLabPK.setHighlighter(null);
        NamaDepartemenRuangLabPK.setName("NamaDepartemenRuangLabPK"); // NOI18N
        FormInput3.add(NamaDepartemenRuangLabPK);
        NamaDepartemenRuangLabPK.setBounds(156, 40, 275, 23);

        btnDepartemenRuangLabPK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenRuangLabPK.setMnemonic('1');
        btnDepartemenRuangLabPK.setToolTipText("Alt+1");
        btnDepartemenRuangLabPK.setName("btnDepartemenRuangLabPK"); // NOI18N
        btnDepartemenRuangLabPK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenRuangLabPKActionPerformed(evt);
            }
        });
        btnDepartemenRuangLabPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenRuangLabPKKeyPressed(evt);
            }
        });
        FormInput3.add(btnDepartemenRuangLabPK);
        btnDepartemenRuangLabPK.setBounds(705, 40, 28, 23);

        IDOrganisasiRuangLabPK.setEditable(false);
        IDOrganisasiRuangLabPK.setHighlighter(null);
        IDOrganisasiRuangLabPK.setName("IDOrganisasiRuangLabPK"); // NOI18N
        FormInput3.add(IDOrganisasiRuangLabPK);
        IDOrganisasiRuangLabPK.setBounds(433, 40, 270, 23);

        LongitudeRuangLabPK.setHighlighter(null);
        LongitudeRuangLabPK.setName("LongitudeRuangLabPK"); // NOI18N
        LongitudeRuangLabPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LongitudeRuangLabPKKeyPressed(evt);
            }
        });
        FormInput3.add(LongitudeRuangLabPK);
        LongitudeRuangLabPK.setBounds(79, 10, 165, 23);

        jLabel21.setText("Longitude :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput3.add(jLabel21);
        jLabel21.setBounds(0, 10, 75, 23);

        jLabel22.setText("Latitude :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput3.add(jLabel22);
        jLabel22.setBounds(261, 10, 60, 23);

        LatitudeRuangLabPK.setHighlighter(null);
        LatitudeRuangLabPK.setName("LatitudeRuangLabPK"); // NOI18N
        LatitudeRuangLabPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatitudeRuangLabPKKeyPressed(evt);
            }
        });
        FormInput3.add(LatitudeRuangLabPK);
        LatitudeRuangLabPK.setBounds(325, 10, 165, 23);

        jLabel23.setText("Altitude :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput3.add(jLabel23);
        jLabel23.setBounds(505, 10, 59, 23);

        AltitudeRuangLabPK.setHighlighter(null);
        AltitudeRuangLabPK.setName("AltitudeRuangLabPK"); // NOI18N
        AltitudeRuangLabPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AltitudeRuangLabPKKeyPressed(evt);
            }
        });
        FormInput3.add(AltitudeRuangLabPK);
        AltitudeRuangLabPK.setBounds(568, 10, 165, 23);

        internalFrame5.add(FormInput3, java.awt.BorderLayout.PAGE_START);

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbLokasiRuangLabPK.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLokasiRuangLabPK.setName("tbLokasiRuangLabPK"); // NOI18N
        tbLokasiRuangLabPK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLokasiRuangLabPKMouseClicked(evt);
            }
        });
        tbLokasiRuangLabPK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbLokasiRuangLabPKKeyReleased(evt);
            }
        });
        Scroll3.setViewportView(tbLokasiRuangLabPK);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Lokasi Lab PK", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput4.setName("FormInput4"); // NOI18N
        FormInput4.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput4.setLayout(null);

        jLabel24.setText("Organisasi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput4.add(jLabel24);
        jLabel24.setBounds(0, 40, 75, 23);

        KodeDepartemenRuangLabPA.setEditable(false);
        KodeDepartemenRuangLabPA.setHighlighter(null);
        KodeDepartemenRuangLabPA.setName("KodeDepartemenRuangLabPA"); // NOI18N
        FormInput4.add(KodeDepartemenRuangLabPA);
        KodeDepartemenRuangLabPA.setBounds(79, 40, 75, 23);

        NamaDepartemenRuangLabPA.setEditable(false);
        NamaDepartemenRuangLabPA.setHighlighter(null);
        NamaDepartemenRuangLabPA.setName("NamaDepartemenRuangLabPA"); // NOI18N
        FormInput4.add(NamaDepartemenRuangLabPA);
        NamaDepartemenRuangLabPA.setBounds(156, 40, 275, 23);

        btnDepartemenRuangLabPA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenRuangLabPA.setMnemonic('1');
        btnDepartemenRuangLabPA.setToolTipText("Alt+1");
        btnDepartemenRuangLabPA.setName("btnDepartemenRuangLabPA"); // NOI18N
        btnDepartemenRuangLabPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenRuangLabPAActionPerformed(evt);
            }
        });
        btnDepartemenRuangLabPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenRuangLabPAKeyPressed(evt);
            }
        });
        FormInput4.add(btnDepartemenRuangLabPA);
        btnDepartemenRuangLabPA.setBounds(705, 40, 28, 23);

        IDOrganisasiRuangLabPA.setEditable(false);
        IDOrganisasiRuangLabPA.setHighlighter(null);
        IDOrganisasiRuangLabPA.setName("IDOrganisasiRuangLabPA"); // NOI18N
        FormInput4.add(IDOrganisasiRuangLabPA);
        IDOrganisasiRuangLabPA.setBounds(433, 40, 270, 23);

        LongitudeRuangLabPA.setHighlighter(null);
        LongitudeRuangLabPA.setName("LongitudeRuangLabPA"); // NOI18N
        LongitudeRuangLabPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LongitudeRuangLabPAKeyPressed(evt);
            }
        });
        FormInput4.add(LongitudeRuangLabPA);
        LongitudeRuangLabPA.setBounds(79, 10, 165, 23);

        jLabel25.setText("Longitude :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput4.add(jLabel25);
        jLabel25.setBounds(0, 10, 75, 23);

        jLabel26.setText("Latitude :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput4.add(jLabel26);
        jLabel26.setBounds(261, 10, 60, 23);

        LatitudeRuangLabPA.setHighlighter(null);
        LatitudeRuangLabPA.setName("LatitudeRuangLabPA"); // NOI18N
        LatitudeRuangLabPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatitudeRuangLabPAKeyPressed(evt);
            }
        });
        FormInput4.add(LatitudeRuangLabPA);
        LatitudeRuangLabPA.setBounds(325, 10, 165, 23);

        jLabel27.setText("Altitude :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput4.add(jLabel27);
        jLabel27.setBounds(505, 10, 59, 23);

        AltitudeRuangLabPA.setHighlighter(null);
        AltitudeRuangLabPA.setName("AltitudeRuangLabPA"); // NOI18N
        AltitudeRuangLabPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AltitudeRuangLabPAKeyPressed(evt);
            }
        });
        FormInput4.add(AltitudeRuangLabPA);
        AltitudeRuangLabPA.setBounds(568, 10, 165, 23);

        internalFrame6.add(FormInput4, java.awt.BorderLayout.PAGE_START);

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbLokasiRuangLabPA.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLokasiRuangLabPA.setName("tbLokasiRuangLabPA"); // NOI18N
        tbLokasiRuangLabPA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLokasiRuangLabPAMouseClicked(evt);
            }
        });
        tbLokasiRuangLabPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbLokasiRuangLabPAKeyReleased(evt);
            }
        });
        Scroll4.setViewportView(tbLokasiRuangLabPA);

        internalFrame6.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Lokasi Lab PA", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput5.setName("FormInput5"); // NOI18N
        FormInput5.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput5.setLayout(null);

        jLabel28.setText("Organisasi :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput5.add(jLabel28);
        jLabel28.setBounds(0, 40, 75, 23);

        KodeDepartemenRuangLabMB.setEditable(false);
        KodeDepartemenRuangLabMB.setHighlighter(null);
        KodeDepartemenRuangLabMB.setName("KodeDepartemenRuangLabMB"); // NOI18N
        FormInput5.add(KodeDepartemenRuangLabMB);
        KodeDepartemenRuangLabMB.setBounds(79, 40, 75, 23);

        NamaDepartemenRuangLabMB.setEditable(false);
        NamaDepartemenRuangLabMB.setHighlighter(null);
        NamaDepartemenRuangLabMB.setName("NamaDepartemenRuangLabMB"); // NOI18N
        FormInput5.add(NamaDepartemenRuangLabMB);
        NamaDepartemenRuangLabMB.setBounds(156, 40, 275, 23);

        btnDepartemenRuangLabMB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenRuangLabMB.setMnemonic('1');
        btnDepartemenRuangLabMB.setToolTipText("Alt+1");
        btnDepartemenRuangLabMB.setName("btnDepartemenRuangLabMB"); // NOI18N
        btnDepartemenRuangLabMB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenRuangLabMBActionPerformed(evt);
            }
        });
        btnDepartemenRuangLabMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenRuangLabMBKeyPressed(evt);
            }
        });
        FormInput5.add(btnDepartemenRuangLabMB);
        btnDepartemenRuangLabMB.setBounds(705, 40, 28, 23);

        IDOrganisasiRuangLabMB.setEditable(false);
        IDOrganisasiRuangLabMB.setHighlighter(null);
        IDOrganisasiRuangLabMB.setName("IDOrganisasiRuangLabMB"); // NOI18N
        FormInput5.add(IDOrganisasiRuangLabMB);
        IDOrganisasiRuangLabMB.setBounds(433, 40, 270, 23);

        LongitudeRuangLabMB.setHighlighter(null);
        LongitudeRuangLabMB.setName("LongitudeRuangLabMB"); // NOI18N
        LongitudeRuangLabMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LongitudeRuangLabMBKeyPressed(evt);
            }
        });
        FormInput5.add(LongitudeRuangLabMB);
        LongitudeRuangLabMB.setBounds(79, 10, 165, 23);

        jLabel29.setText("Longitude :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput5.add(jLabel29);
        jLabel29.setBounds(0, 10, 75, 23);

        jLabel30.setText("Latitude :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput5.add(jLabel30);
        jLabel30.setBounds(261, 10, 60, 23);

        LatitudeRuangLabMB.setHighlighter(null);
        LatitudeRuangLabMB.setName("LatitudeRuangLabMB"); // NOI18N
        LatitudeRuangLabMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatitudeRuangLabMBKeyPressed(evt);
            }
        });
        FormInput5.add(LatitudeRuangLabMB);
        LatitudeRuangLabMB.setBounds(325, 10, 165, 23);

        jLabel31.setText("Altitude :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput5.add(jLabel31);
        jLabel31.setBounds(505, 10, 59, 23);

        AltitudeRuangLabMB.setHighlighter(null);
        AltitudeRuangLabMB.setName("AltitudeRuangLabMB"); // NOI18N
        AltitudeRuangLabMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AltitudeRuangLabMBKeyPressed(evt);
            }
        });
        FormInput5.add(AltitudeRuangLabMB);
        AltitudeRuangLabMB.setBounds(568, 10, 165, 23);

        internalFrame7.add(FormInput5, java.awt.BorderLayout.PAGE_START);

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbLokasiRuangLabMB.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLokasiRuangLabMB.setName("tbLokasiRuangLabMB"); // NOI18N
        tbLokasiRuangLabMB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLokasiRuangLabMBMouseClicked(evt);
            }
        });
        tbLokasiRuangLabMB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbLokasiRuangLabMBKeyReleased(evt);
            }
        });
        Scroll5.setViewportView(tbLokasiRuangLabMB);

        internalFrame7.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Lokasi Lab MB", internalFrame7);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput6.setName("FormInput6"); // NOI18N
        FormInput6.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput6.setLayout(null);

        jLabel32.setText("Organisasi :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput6.add(jLabel32);
        jLabel32.setBounds(0, 40, 75, 23);

        KodeDepartemenRuangRadiologi.setEditable(false);
        KodeDepartemenRuangRadiologi.setHighlighter(null);
        KodeDepartemenRuangRadiologi.setName("KodeDepartemenRuangRadiologi"); // NOI18N
        FormInput6.add(KodeDepartemenRuangRadiologi);
        KodeDepartemenRuangRadiologi.setBounds(79, 40, 75, 23);

        NamaDepartemenRuangRadiologi.setEditable(false);
        NamaDepartemenRuangRadiologi.setHighlighter(null);
        NamaDepartemenRuangRadiologi.setName("NamaDepartemenRuangRadiologi"); // NOI18N
        FormInput6.add(NamaDepartemenRuangRadiologi);
        NamaDepartemenRuangRadiologi.setBounds(156, 40, 275, 23);

        btnDepartemenRuangRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenRuangRadiologi.setMnemonic('1');
        btnDepartemenRuangRadiologi.setToolTipText("Alt+1");
        btnDepartemenRuangRadiologi.setName("btnDepartemenRuangRadiologi"); // NOI18N
        btnDepartemenRuangRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenRuangRadiologiActionPerformed(evt);
            }
        });
        btnDepartemenRuangRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenRuangRadiologiKeyPressed(evt);
            }
        });
        FormInput6.add(btnDepartemenRuangRadiologi);
        btnDepartemenRuangRadiologi.setBounds(705, 40, 28, 23);

        IDOrganisasiRuangRadiologi.setEditable(false);
        IDOrganisasiRuangRadiologi.setHighlighter(null);
        IDOrganisasiRuangRadiologi.setName("IDOrganisasiRuangRadiologi"); // NOI18N
        FormInput6.add(IDOrganisasiRuangRadiologi);
        IDOrganisasiRuangRadiologi.setBounds(433, 40, 270, 23);

        LongitudeRuangRadiologi.setHighlighter(null);
        LongitudeRuangRadiologi.setName("LongitudeRuangRadiologi"); // NOI18N
        LongitudeRuangRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LongitudeRuangRadiologiKeyPressed(evt);
            }
        });
        FormInput6.add(LongitudeRuangRadiologi);
        LongitudeRuangRadiologi.setBounds(79, 10, 165, 23);

        jLabel33.setText("Longitude :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput6.add(jLabel33);
        jLabel33.setBounds(0, 10, 75, 23);

        jLabel34.setText("Latitude :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput6.add(jLabel34);
        jLabel34.setBounds(261, 10, 60, 23);

        LatitudeRuangRadiologi.setHighlighter(null);
        LatitudeRuangRadiologi.setName("LatitudeRuangRadiologi"); // NOI18N
        LatitudeRuangRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatitudeRuangRadiologiKeyPressed(evt);
            }
        });
        FormInput6.add(LatitudeRuangRadiologi);
        LatitudeRuangRadiologi.setBounds(325, 10, 165, 23);

        jLabel35.setText("Altitude :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput6.add(jLabel35);
        jLabel35.setBounds(505, 10, 59, 23);

        AltitudeRuangRadiologi.setHighlighter(null);
        AltitudeRuangRadiologi.setName("AltitudeRuangRadiologi"); // NOI18N
        AltitudeRuangRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AltitudeRuangRadiologiKeyPressed(evt);
            }
        });
        FormInput6.add(AltitudeRuangRadiologi);
        AltitudeRuangRadiologi.setBounds(568, 10, 165, 23);

        internalFrame8.add(FormInput6, java.awt.BorderLayout.PAGE_START);

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbLokasiRuangRadiologi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLokasiRuangRadiologi.setName("tbLokasiRuangRadiologi"); // NOI18N
        tbLokasiRuangRadiologi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLokasiRuangRadiologiMouseClicked(evt);
            }
        });
        tbLokasiRuangRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbLokasiRuangRadiologiKeyReleased(evt);
            }
        });
        Scroll6.setViewportView(tbLokasiRuangRadiologi);

        internalFrame8.add(Scroll6, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Lokasi Radiologi", internalFrame8);

        internalFrame9.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame9.setBorder(null);
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput7.setName("FormInput7"); // NOI18N
        FormInput7.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput7.setLayout(null);

        jLabel36.setText("Organisasi :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput7.add(jLabel36);
        jLabel36.setBounds(170, 40, 74, 23);

        KodeDepartemenFarmasi.setEditable(false);
        KodeDepartemenFarmasi.setHighlighter(null);
        KodeDepartemenFarmasi.setName("KodeDepartemenFarmasi"); // NOI18N
        FormInput7.add(KodeDepartemenFarmasi);
        KodeDepartemenFarmasi.setBounds(248, 40, 55, 23);

        NamaDepartemenFarmasi.setEditable(false);
        NamaDepartemenFarmasi.setHighlighter(null);
        NamaDepartemenFarmasi.setName("NamaDepartemenFarmasi"); // NOI18N
        FormInput7.add(NamaDepartemenFarmasi);
        NamaDepartemenFarmasi.setBounds(305, 40, 176, 23);

        btnDepartemenFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenFarmasi.setMnemonic('1');
        btnDepartemenFarmasi.setToolTipText("Alt+1");
        btnDepartemenFarmasi.setName("btnDepartemenFarmasi"); // NOI18N
        btnDepartemenFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenFarmasiActionPerformed(evt);
            }
        });
        btnDepartemenFarmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenFarmasiKeyPressed(evt);
            }
        });
        FormInput7.add(btnDepartemenFarmasi);
        btnDepartemenFarmasi.setBounds(705, 40, 28, 23);

        jLabel37.setText("Farmasi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput7.add(jLabel37);
        jLabel37.setBounds(0, 10, 59, 23);

        KodeFarmasi.setEditable(false);
        KodeFarmasi.setHighlighter(null);
        KodeFarmasi.setName("KodeFarmasi"); // NOI18N
        FormInput7.add(KodeFarmasi);
        KodeFarmasi.setBounds(63, 10, 70, 23);

        NamaFarmasi.setEditable(false);
        NamaFarmasi.setHighlighter(null);
        NamaFarmasi.setName("NamaFarmasi"); // NOI18N
        FormInput7.add(NamaFarmasi);
        NamaFarmasi.setBounds(135, 10, 195, 23);

        btnFarmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnFarmasi.setMnemonic('1');
        btnFarmasi.setToolTipText("Alt+1");
        btnFarmasi.setName("btnFarmasi"); // NOI18N
        btnFarmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFarmasiActionPerformed(evt);
            }
        });
        btnFarmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnFarmasiKeyPressed(evt);
            }
        });
        FormInput7.add(btnFarmasi);
        btnFarmasi.setBounds(332, 10, 28, 23);

        IDOrganisasiFarmasi.setEditable(false);
        IDOrganisasiFarmasi.setHighlighter(null);
        IDOrganisasiFarmasi.setName("IDOrganisasiFarmasi"); // NOI18N
        FormInput7.add(IDOrganisasiFarmasi);
        IDOrganisasiFarmasi.setBounds(483, 40, 220, 23);

        LongitudeFarmasi.setHighlighter(null);
        LongitudeFarmasi.setName("LongitudeFarmasi"); // NOI18N
        LongitudeFarmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LongitudeFarmasiKeyPressed(evt);
            }
        });
        FormInput7.add(LongitudeFarmasi);
        LongitudeFarmasi.setBounds(430, 10, 120, 23);

        jLabel38.setText("Longitude :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput7.add(jLabel38);
        jLabel38.setBounds(357, 10, 69, 23);

        jLabel39.setText("Latitude :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput7.add(jLabel39);
        jLabel39.setBounds(549, 10, 60, 23);

        LatitudeFarmasi.setHighlighter(null);
        LatitudeFarmasi.setName("LatitudeFarmasi"); // NOI18N
        LatitudeFarmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatitudeFarmasiKeyPressed(evt);
            }
        });
        FormInput7.add(LatitudeFarmasi);
        LatitudeFarmasi.setBounds(613, 10, 120, 23);

        jLabel40.setText("Altitude :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput7.add(jLabel40);
        jLabel40.setBounds(0, 40, 59, 23);

        AltitudeFarmasi.setHighlighter(null);
        AltitudeFarmasi.setName("AltitudeFarmasi"); // NOI18N
        AltitudeFarmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AltitudeFarmasiKeyPressed(evt);
            }
        });
        FormInput7.add(AltitudeFarmasi);
        AltitudeFarmasi.setBounds(63, 40, 110, 23);

        internalFrame9.add(FormInput7, java.awt.BorderLayout.PAGE_START);

        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbLokasiFarmasi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLokasiFarmasi.setName("tbLokasiFarmasi"); // NOI18N
        tbLokasiFarmasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLokasiFarmasiMouseClicked(evt);
            }
        });
        tbLokasiFarmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbLokasiFarmasiKeyReleased(evt);
            }
        });
        Scroll7.setViewportView(tbLokasiFarmasi);

        internalFrame9.add(Scroll7, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Lokasi Depo Farmasi", internalFrame9);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDepartemenRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenRSActionPerformed
        pilih=1;
        organisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        organisasi.setLocationRelativeTo(internalFrame1);
        organisasi.setVisible(true);
}//GEN-LAST:event_btnDepartemenRSActionPerformed

    private void btnDepartemenRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenRSKeyPressed
        Valid.pindah(evt, Altitude,BtnSimpan);
}//GEN-LAST:event_btnDepartemenRSKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(KodeDepartemen.getText().trim().equals("")||NamaDepartemen.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemen,"Departemen/Organisasi");
            }else if(KodePoli.getText().trim().equals("")||NamaPoli.getText().trim().equals("")){
                Valid.textKosong(KodePoli,"Poli/Unit");
            }else if(Longitude.getText().trim().equals("")){
                Valid.textKosong(Longitude,"Longitude");
            }else if(Latitude.getText().trim().equals("")){
                Valid.textKosong(Latitude,"Latitude");
            }else if(Altitude.getText().trim().equals("")){
                Valid.textKosong(Altitude,"Altitude");
            }else{
                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    json = "{" +
                                "\"resourceType\": \"Location\"," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                        "\"value\": \""+KodePoli.getText()+"\"" +
                                    "}" +
                                "]," +
                                "\"status\": \"active\"," +
                                "\"name\": \""+NamaPoli.getText()+"\"," +
                                "\"description\": \""+NamaPoli.getText()+"\"," +
                                "\"mode\": \"instance\"," +
                                "\"telecom\": [" +
                                    "{" +
                                        "\"system\": \"phone\"," +
                                        "\"value\": \""+akses.getkontakrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"email\"," +
                                        "\"value\": \""+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"url\"," +
                                        "\"value\": \"www."+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}" +
                                "]," +
                                "\"address\": {" +
                                    "\"use\": \"work\"," +
                                    "\"line\": [" +
                                        "\""+akses.getalamatrs()+"\"" +
                                    "]," +
                                    "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                    "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                    "\"country\": \"ID\"," +
                                    "\"extension\": [" +
                                        "{" +
                                            "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                            "\"extension\": [" +
                                                "{" +
                                                    "\"url\": \"province\"," +
                                                    "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"city\"," +
                                                    "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"district\"," +
                                                    "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"village\"," +
                                                    "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                "}" +
                                            "]" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"physicalType\": {" +
                                    "\"coding\": [" +
                                        "{" +
                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                            "\"code\": \"ro\"," +
                                            "\"display\": \"Room\"" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"position\": {" +
                                    "\"longitude\": "+Longitude.getText()+"," +
                                    "\"latitude\": "+Latitude.getText()+"," +
                                    "\"altitude\": "+Altitude.getText()+
                                "}," +
                                "\"managingOrganization\": {" +
                                    "\"reference\": \"Organization/"+IDOrganisasi.getText()+"\"" +
                                "}" +
                            "}";
                    System.out.println("URL : "+link+"/Location");
                    System.out.println("Request JSON : "+json);
                    requestEntity = new HttpEntity(json,headers);
                    json=api.getRest().exchange(link+"/Location", HttpMethod.POST, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);
                    response = root.path("id");
                    if(!response.asText().equals("")){
                        if(Sequel.menyimpantf("satu_sehat_mapping_lokasi_ralan","?,?,?,?,?,?","Kode Poli/Kode Unit/ID Lokasi",6,new String[]{
                                KodePoli.getText(),IDOrganisasi.getText(),response.asText(),Longitude.getText(),Latitude.getText(),Altitude.getText()
                            })==true){
                            tabMode.addRow(new Object[]{
                                KodePoli.getText(),NamaPoli.getText(),response.asText(),Longitude.getText(),Latitude.getText(),
                                Altitude.getText(),KodeDepartemen.getText(),NamaDepartemen.getText(),IDOrganisasi.getText()
                            });
                            emptTeks();
                            LCount.setText(""+tabMode.getRowCount());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                    } 
                }catch(Exception e){
                    System.out.println("Notifikasi Bridging : "+e);
                    JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                }               
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(KodeDepartemenKamar.getText().trim().equals("")||NamaDepartemenKamar.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenKamar,"Departemen/Organisasi");
            }else if(KodeKamar.getText().trim().equals("")||NamaKamar.getText().trim().equals("")){
                Valid.textKosong(KodeKamar,"Kamar/Ruang");
            }else if(LongitudeKamar.getText().trim().equals("")){
                Valid.textKosong(LongitudeKamar,"Longitude");
            }else if(LatitudeKamar.getText().trim().equals("")){
                Valid.textKosong(LatitudeKamar,"Latitude");
            }else if(AltitudeKamar.getText().trim().equals("")){
                Valid.textKosong(AltitudeKamar,"Altitude");
            }else{
                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    json = "{" +
                                "\"resourceType\": \"Location\"," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                        "\"value\": \""+KodeKamar.getText()+"\"" +
                                    "}" +
                                "]," +
                                "\"status\": \"active\"," +
                                "\"name\": \""+NamaKamar.getText()+"\"," +
                                "\"description\": \""+NamaKamar.getText()+" Nomor "+KodeKamar.getText()+"\"," +
                                "\"mode\": \"instance\"," +
                                "\"telecom\": [" +
                                    "{" +
                                        "\"system\": \"phone\"," +
                                        "\"value\": \""+akses.getkontakrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"email\"," +
                                        "\"value\": \""+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"url\"," +
                                        "\"value\": \"www."+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}" +
                                "]," +
                                "\"address\": {" +
                                    "\"use\": \"work\"," +
                                    "\"line\": [" +
                                        "\""+akses.getalamatrs()+"\"" +
                                    "]," +
                                    "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                    "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                    "\"country\": \"ID\"," +
                                    "\"extension\": [" +
                                        "{" +
                                            "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                            "\"extension\": [" +
                                                "{" +
                                                    "\"url\": \"province\"," +
                                                    "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"city\"," +
                                                    "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"district\"," +
                                                    "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"village\"," +
                                                    "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                "}" +
                                            "]" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"physicalType\": {" +
                                    "\"coding\": [" +
                                        "{" +
                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                            "\"code\": \"ro\"," +
                                            "\"display\": \"Room\"" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"position\": {" +
                                    "\"longitude\": "+LongitudeKamar.getText()+"," +
                                    "\"latitude\": "+LatitudeKamar.getText()+"," +
                                    "\"altitude\": "+AltitudeKamar.getText()+
                                "}," +
                                "\"managingOrganization\": {" +
                                    "\"reference\": \"Organization/"+IDOrganisasiKamar.getText()+"\"" +
                                "}" +
                            "}";
                    System.out.println("Request JSON : "+json);
                    requestEntity = new HttpEntity(json,headers);
                    json=api.getRest().exchange(link+"/Location", HttpMethod.POST, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);
                    response = root.path("id");
                    if(!response.asText().equals("")){
                        if(Sequel.menyimpantf("satu_sehat_mapping_lokasi_ranap","?,?,?,?,?,?","Kode Kamar/Kode Ruang/ID Lokasi",6,new String[]{
                                KodeKamar.getText(),IDOrganisasiKamar.getText(),response.asText(),LongitudeKamar.getText(),LatitudeKamar.getText(),AltitudeKamar.getText()
                            })==true){
                            tabModeKamar.addRow(new Object[]{
                                KodeKamar.getText(),NamaKamar.getText(),response.asText(),LongitudeKamar.getText(),LatitudeKamar.getText(),
                                AltitudeKamar.getText(),KodeDepartemenKamar.getText(),NamaDepartemenKamar.getText(),IDOrganisasiKamar.getText()
                            });
                            emptTeks();
                            LCount.setText(""+tabModeKamar.getRowCount());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                    } 
                }catch(Exception e){
                    System.out.println("Notifikasi Bridging : "+e);
                    JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                }               
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabModeRuangOK.getRowCount()==0){
                if(KodeDepartemenRuangOK.getText().trim().equals("")||NamaDepartemenRuangOK.getText().trim().equals("")){
                    Valid.textKosong(KodeDepartemenRuangOK,"Departemen/Organisasi");
                }else if(LongitudeRuangOK.getText().trim().equals("")){
                    Valid.textKosong(LongitudeRuangOK,"Longitude");
                }else if(LatitudeRuangOK.getText().trim().equals("")){
                    Valid.textKosong(LatitudeRuangOK,"Latitude");
                }else if(AltitudeRuangOK.getText().trim().equals("")){
                    Valid.textKosong(AltitudeRuangOK,"Altitude");
                }else{
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.OK.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Operasi\"," +
                                    "\"description\": \"Ruang Operasi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangOK.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangOK.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangOK.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangOK.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.menyimpantf("satu_sehat_mapping_lokasi_ruangok","?,?,?,?,?","ID Lokasi",5,new String[]{
                                    IDOrganisasiRuangOK.getText(),response.asText(),LongitudeRuangOK.getText(),LatitudeRuangOK.getText(),AltitudeRuangOK.getText()
                                })==true){
                                tabModeRuangOK.addRow(new Object[]{
                                    response.asText(),LongitudeRuangOK.getText(),LatitudeRuangOK.getText(),AltitudeRuangOK.getText(),KodeDepartemenRuangOK.getText(),NamaDepartemenRuangOK.getText(),IDOrganisasiRuangOK.getText()
                                });
                                emptTeks();
                                LCount.setText(""+tabModeRuangOK.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }               
                }
            }else{
                JOptionPane.showMessageDialog(null,"Untuk Ruang OK hanya diijinkan satu mapping lokasi");
            }
        }else if(TabRawat.getSelectedIndex()==3){
            if(tabModeRuangLabPK.getRowCount()==0){
                if(KodeDepartemenRuangLabPK.getText().trim().equals("")||NamaDepartemenRuangLabPK.getText().trim().equals("")){
                    Valid.textKosong(KodeDepartemenRuangLabPK,"Departemen/Organisasi");
                }else if(LongitudeRuangLabPK.getText().trim().equals("")){
                    Valid.textKosong(LongitudeRuangLabPK,"Longitude");
                }else if(LatitudeRuangLabPK.getText().trim().equals("")){
                    Valid.textKosong(LatitudeRuangLabPK,"Latitude");
                }else if(AltitudeRuangLabPK.getText().trim().equals("")){
                    Valid.textKosong(AltitudeRuangLabPK,"Altitude");
                }else{
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABPK.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Laboratorium Patologi Klinik\"," +
                                    "\"description\": \"Ruang Laboratorium Patologi Klinik\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabPK.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabPK.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabPK.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabPK.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.menyimpantf("satu_sehat_mapping_lokasi_ruanglab","?,?,?,?,?","ID Lokasi",5,new String[]{
                                    IDOrganisasiRuangLabPK.getText(),response.asText(),LongitudeRuangLabPK.getText(),LatitudeRuangLabPK.getText(),AltitudeRuangLabPK.getText()
                                })==true){
                                tabModeRuangLabPK.addRow(new Object[]{
                                    response.asText(),LongitudeRuangLabPK.getText(),LatitudeRuangLabPK.getText(),AltitudeRuangLabPK.getText(),KodeDepartemenRuangLabPK.getText(),NamaDepartemenRuangLabPK.getText(),IDOrganisasiRuangLabPK.getText()
                                });
                                emptTeks();
                                LCount.setText(""+tabModeRuangLabPK.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }               
                }
            }else{
                JOptionPane.showMessageDialog(null,"Untuk Ruang Lab PK hanya diijinkan satu mapping lokasi");
            }
        }else if(TabRawat.getSelectedIndex()==4){
            if(tabModeRuangLabPA.getRowCount()==0){
                if(KodeDepartemenRuangLabPA.getText().trim().equals("")||NamaDepartemenRuangLabPA.getText().trim().equals("")){
                    Valid.textKosong(KodeDepartemenRuangLabPA,"Departemen/Organisasi");
                }else if(LongitudeRuangLabPA.getText().trim().equals("")){
                    Valid.textKosong(LongitudeRuangLabPA,"Longitude");
                }else if(LatitudeRuangLabPA.getText().trim().equals("")){
                    Valid.textKosong(LatitudeRuangLabPA,"Latitude");
                }else if(AltitudeRuangLabPA.getText().trim().equals("")){
                    Valid.textKosong(AltitudeRuangLabPA,"Altitude");
                }else{
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABPA.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Laboratorium Patologi Anatomi\"," +
                                    "\"description\": \"Ruang Laboratorium Patologi Anatomi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabPA.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabPA.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabPA.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabPA.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.menyimpantf("satu_sehat_mapping_lokasi_ruanglabpa","?,?,?,?,?","ID Lokasi",5,new String[]{
                                    IDOrganisasiRuangLabPA.getText(),response.asText(),LongitudeRuangLabPA.getText(),LatitudeRuangLabPA.getText(),AltitudeRuangLabPA.getText()
                                })==true){
                                tabModeRuangLabPA.addRow(new Object[]{
                                    response.asText(),LongitudeRuangLabPA.getText(),LatitudeRuangLabPA.getText(),AltitudeRuangLabPA.getText(),KodeDepartemenRuangLabPA.getText(),NamaDepartemenRuangLabPA.getText(),IDOrganisasiRuangLabPA.getText()
                                });
                                emptTeks();
                                LCount.setText(""+tabModeRuangLabPA.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }               
                }
            }else{
                JOptionPane.showMessageDialog(null,"Untuk Ruang Lab PA hanya diijinkan satu mapping lokasi");
            }
        }else if(TabRawat.getSelectedIndex()==5){
            if(tabModeRuangLabMB.getRowCount()==0){
                if(KodeDepartemenRuangLabMB.getText().trim().equals("")||NamaDepartemenRuangLabMB.getText().trim().equals("")){
                    Valid.textKosong(KodeDepartemenRuangLabMB,"Departemen/Organisasi");
                }else if(LongitudeRuangLabMB.getText().trim().equals("")){
                    Valid.textKosong(LongitudeRuangLabMB,"Longitude");
                }else if(LatitudeRuangLabMB.getText().trim().equals("")){
                    Valid.textKosong(LatitudeRuangLabMB,"Latitude");
                }else if(AltitudeRuangLabMB.getText().trim().equals("")){
                    Valid.textKosong(AltitudeRuangLabMB,"Altitude");
                }else{
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABMB.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Laboratorium Mikrobiologi & Bio Molekuler\"," +
                                    "\"description\": \"Ruang Laboratorium Mikrobiologi & Bio Molekuler\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabMB.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabMB.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabMB.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabMB.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.menyimpantf("satu_sehat_mapping_lokasi_ruanglabmb","?,?,?,?,?","ID Lokasi",5,new String[]{
                                    IDOrganisasiRuangLabMB.getText(),response.asText(),LongitudeRuangLabMB.getText(),LatitudeRuangLabMB.getText(),AltitudeRuangLabMB.getText()
                                })==true){
                                tabModeRuangLabMB.addRow(new Object[]{
                                    response.asText(),LongitudeRuangLabMB.getText(),LatitudeRuangLabMB.getText(),AltitudeRuangLabMB.getText(),KodeDepartemenRuangLabMB.getText(),NamaDepartemenRuangLabMB.getText(),IDOrganisasiRuangLabMB.getText()
                                });
                                emptTeks();
                                LCount.setText(""+tabModeRuangLabMB.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }               
                }
            }else{
                JOptionPane.showMessageDialog(null,"Untuk Ruang Lab MB hanya diijinkan satu mapping lokasi");
            }
        }else if(TabRawat.getSelectedIndex()==6){
            if(tabModeRuangRadiologi.getRowCount()==0){
                if(KodeDepartemenRuangRadiologi.getText().trim().equals("")||NamaDepartemenRuangRadiologi.getText().trim().equals("")){
                    Valid.textKosong(KodeDepartemenRuangRadiologi,"Departemen/Organisasi");
                }else if(LongitudeRuangRadiologi.getText().trim().equals("")){
                    Valid.textKosong(LongitudeRuangRadiologi,"Longitude");
                }else if(LatitudeRuangRadiologi.getText().trim().equals("")){
                    Valid.textKosong(LatitudeRuangRadiologi,"Latitude");
                }else if(AltitudeRuangRadiologi.getText().trim().equals("")){
                    Valid.textKosong(AltitudeRuangRadiologi,"Altitude");
                }else{
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.RAD.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Radiologi\"," +
                                    "\"description\": \"Ruang Radiologi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangRadiologi.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangRadiologi.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangRadiologi.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangRadiologi.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.menyimpantf("satu_sehat_mapping_lokasi_ruangrad","?,?,?,?,?","ID Lokasi",5,new String[]{
                                    IDOrganisasiRuangRadiologi.getText(),response.asText(),LongitudeRuangRadiologi.getText(),LatitudeRuangRadiologi.getText(),AltitudeRuangRadiologi.getText()
                                })==true){
                                tabModeRuangRadiologi.addRow(new Object[]{
                                    response.asText(),LongitudeRuangRadiologi.getText(),LatitudeRuangRadiologi.getText(),AltitudeRuangRadiologi.getText(),KodeDepartemenRuangRadiologi.getText(),NamaDepartemenRuangRadiologi.getText(),IDOrganisasiRuangRadiologi.getText()
                                });
                                emptTeks();
                                LCount.setText(""+tabModeRuangRadiologi.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }               
                }
            }else{
                JOptionPane.showMessageDialog(null,"Untuk Ruang Lab MB hanya diijinkan satu mapping lokasi");
            }
        }else if(TabRawat.getSelectedIndex()==7){
            if(KodeDepartemenFarmasi.getText().trim().equals("")||NamaDepartemenFarmasi.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenFarmasi,"Departemen/Organisasi");
            }else if(KodeFarmasi.getText().trim().equals("")||NamaFarmasi.getText().trim().equals("")){
                Valid.textKosong(KodeFarmasi,"Farmasi/Depo");
            }else if(LongitudeFarmasi.getText().trim().equals("")){
                Valid.textKosong(LongitudeFarmasi,"Longitude");
            }else if(LatitudeFarmasi.getText().trim().equals("")){
                Valid.textKosong(LatitudeFarmasi,"Latitude");
            }else if(AltitudeFarmasi.getText().trim().equals("")){
                Valid.textKosong(AltitudeFarmasi,"Altitude");
            }else{
                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    json = "{" +
                                "\"resourceType\": \"Location\"," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                        "\"value\": \""+KodeFarmasi.getText()+"\"" +
                                    "}" +
                                "]," +
                                "\"status\": \"active\"," +
                                "\"name\": \""+NamaFarmasi.getText()+"\"," +
                                "\"description\": \""+NamaFarmasi.getText()+"\"," +
                                "\"mode\": \"instance\"," +
                                "\"telecom\": [" +
                                    "{" +
                                        "\"system\": \"phone\"," +
                                        "\"value\": \""+akses.getkontakrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"email\"," +
                                        "\"value\": \""+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"url\"," +
                                        "\"value\": \"www."+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}" +
                                "]," +
                                "\"address\": {" +
                                    "\"use\": \"work\"," +
                                    "\"line\": [" +
                                        "\""+akses.getalamatrs()+"\"" +
                                    "]," +
                                    "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                    "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                    "\"country\": \"ID\"," +
                                    "\"extension\": [" +
                                        "{" +
                                            "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                            "\"extension\": [" +
                                                "{" +
                                                    "\"url\": \"province\"," +
                                                    "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"city\"," +
                                                    "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"district\"," +
                                                    "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"village\"," +
                                                    "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                "}" +
                                            "]" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"physicalType\": {" +
                                    "\"coding\": [" +
                                        "{" +
                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                            "\"code\": \"ro\"," +
                                            "\"display\": \"Room\"" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"position\": {" +
                                    "\"longitude\": "+LongitudeFarmasi.getText()+"," +
                                    "\"latitude\": "+LatitudeFarmasi.getText()+"," +
                                    "\"altitude\": "+AltitudeFarmasi.getText()+
                                "}," +
                                "\"managingOrganization\": {" +
                                    "\"reference\": \"Organization/"+IDOrganisasiFarmasi.getText()+"\"" +
                                "}" +
                            "}";
                    System.out.println("URL : "+link+"/Location");
                    System.out.println("Request JSON : "+json);
                    requestEntity = new HttpEntity(json,headers);
                    json=api.getRest().exchange(link+"/Location", HttpMethod.POST, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);
                    response = root.path("id");
                    if(!response.asText().equals("")){
                        if(Sequel.menyimpantf("satu_sehat_mapping_lokasi_depo_farmasi","?,?,?,?,?,?","Kode Depo/Kode Farmasi",6,new String[]{
                                KodeFarmasi.getText(),IDOrganisasiFarmasi.getText(),response.asText(),LongitudeFarmasi.getText(),LatitudeFarmasi.getText(),AltitudeFarmasi.getText()
                            })==true){
                            tabModeRuangFarmasi.addRow(new Object[]{
                                KodeFarmasi.getText(),NamaFarmasi.getText(),response.asText(),LongitudeFarmasi.getText(),LatitudeFarmasi.getText(),
                                AltitudeFarmasi.getText(),KodeDepartemenFarmasi.getText(),NamaDepartemenFarmasi.getText(),IDOrganisasiFarmasi.getText()
                            });
                            emptTeks();
                            LCount.setText(""+tabModeRuangFarmasi.getRowCount());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                    } 
                }catch(Exception e){
                    System.out.println("Notifikasi Bridging : "+e);
                    JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                }               
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,btnDepartemenRS, BtnBatal);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,btnDepartemenKamar, BtnBatal);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,btnDepartemenRuangOK, BtnBatal);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,btnDepartemenRuangLabPK, BtnBatal);
            }else if(TabRawat.getSelectedIndex()==4){
                Valid.pindah(evt,btnDepartemenRuangLabPA, BtnBatal);
            }else if(TabRawat.getSelectedIndex()==5){
                Valid.pindah(evt,btnDepartemenRuangLabMB, BtnBatal);
            }else if(TabRawat.getSelectedIndex()==6){
                Valid.pindah(evt,btnDepartemenRuangRadiologi, BtnBatal);
            }else if(TabRawat.getSelectedIndex()==7){
                Valid.pindah(evt,btnDepartemenFarmasi, BtnBatal);
            }
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
        if(TabRawat.getSelectedIndex()==0){
            if(tbJnsPerawatan.getSelectedRow()>-1){
                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    json = "{" +
                                "\"resourceType\": \"Location\"," +
                                "\"id\": \""+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString()+"\"," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                        "\"value\": \""+KodePoli.getText()+"\"" +
                                    "}" +
                                "]," +
                                "\"status\": \"inactive\"," +
                                "\"name\": \""+NamaPoli.getText()+"\"," +
                                "\"description\": \""+NamaPoli.getText()+"\"," +
                                "\"mode\": \"instance\"," +
                                "\"telecom\": [" +
                                    "{" +
                                        "\"system\": \"phone\"," +
                                        "\"value\": \""+akses.getkontakrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"email\"," +
                                        "\"value\": \""+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"url\"," +
                                        "\"value\": \"www."+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}" +
                                "]," +
                                "\"address\": {" +
                                    "\"use\": \"work\"," +
                                    "\"line\": [" +
                                        "\""+akses.getalamatrs()+"\"" +
                                    "]," +
                                    "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                    "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                    "\"country\": \"ID\"," +
                                    "\"extension\": [" +
                                        "{" +
                                            "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                            "\"extension\": [" +
                                                "{" +
                                                    "\"url\": \"province\"," +
                                                    "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"city\"," +
                                                    "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"district\"," +
                                                    "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"village\"," +
                                                    "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                "}" +
                                            "]" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"physicalType\": {" +
                                    "\"coding\": [" +
                                        "{" +
                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                            "\"code\": \"ro\"," +
                                            "\"display\": \"Room\"" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"position\": {" +
                                    "\"longitude\": "+Longitude.getText()+"," +
                                    "\"latitude\": "+Latitude.getText()+"," +
                                    "\"altitude\": "+Altitude.getText()+
                                "}," +
                                "\"managingOrganization\": {" +
                                    "\"reference\": \"Organization/"+IDOrganisasi.getText()+"\"" +
                                "}" +
                            "}";
                    System.out.println("URL : "+link+"/Location");
                    System.out.println("Request JSON : "+json);
                    requestEntity = new HttpEntity(json,headers);
                    json=api.getRest().exchange(link+"/Location/"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);
                    response = root.path("id");
                    if(!response.asText().equals("")){
                        if(Valid.hapusTabletf(tabMode,KodePoli,"satu_sehat_mapping_lokasi_ralan","kd_poli")==true){
                            tabMode.removeRow(tbJnsPerawatan.getSelectedRow());
                            emptTeks();
                            LCount.setText(""+tabMode.getRowCount());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                    } 
                }catch(Exception e){
                    System.out.println("Notifikasi Bridging : "+e);
                    JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                }  
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data terlebih dahulu..!!");
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tbLokasiKamar.getSelectedRow()>-1){
                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    json = "{" +
                                "\"resourceType\": \"Location\"," +
                                "\"id\": \""+tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),2).toString()+"\"," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                        "\"value\": \""+KodeKamar.getText()+"\"" +
                                    "}" +
                                "]," +
                                "\"status\": \"inactive\"," +
                                "\"name\": \""+NamaKamar.getText()+"\"," +
                                "\"description\": \""+NamaKamar.getText()+" Nomor "+KodeKamar.getText()+"\"," +
                                "\"mode\": \"instance\"," +
                                "\"telecom\": [" +
                                    "{" +
                                        "\"system\": \"phone\"," +
                                        "\"value\": \""+akses.getkontakrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"email\"," +
                                        "\"value\": \""+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"url\"," +
                                        "\"value\": \"www."+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}" +
                                "]," +
                                "\"address\": {" +
                                    "\"use\": \"work\"," +
                                    "\"line\": [" +
                                        "\""+akses.getalamatrs()+"\"" +
                                    "]," +
                                    "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                    "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                    "\"country\": \"ID\"," +
                                    "\"extension\": [" +
                                        "{" +
                                            "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                            "\"extension\": [" +
                                                "{" +
                                                    "\"url\": \"province\"," +
                                                    "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"city\"," +
                                                    "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"district\"," +
                                                    "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"village\"," +
                                                    "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                "}" +
                                            "]" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"physicalType\": {" +
                                    "\"coding\": [" +
                                        "{" +
                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                            "\"code\": \"ro\"," +
                                            "\"display\": \"Room\"" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"position\": {" +
                                    "\"longitude\": "+LongitudeKamar.getText()+"," +
                                    "\"latitude\": "+LatitudeKamar.getText()+"," +
                                    "\"altitude\": "+AltitudeKamar.getText()+
                                "}," +
                                "\"managingOrganization\": {" +
                                    "\"reference\": \"Organization/"+IDOrganisasiKamar.getText()+"\"" +
                                "}" +
                            "}";
                    System.out.println("Request JSON : "+json);
                    requestEntity = new HttpEntity(json,headers);
                    json=api.getRest().exchange(link+"/Location/"+tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),2).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);
                    response = root.path("id");
                    if(!response.asText().equals("")){
                        if(Valid.hapusTabletf(tabModeKamar,KodeKamar,"satu_sehat_mapping_lokasi_ranap","kd_kamar")==true){
                            tabModeKamar.removeRow(tbLokasiKamar.getSelectedRow());
                            emptTeks();
                            LCount.setText(""+tabModeKamar.getRowCount());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                    } 
                }catch(Exception e){
                    System.out.println("Notifikasi Bridging : "+e);
                    JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                }  
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data terlebih dahulu..!!");
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(KodeDepartemenRuangOK.getText().trim().equals("")||NamaDepartemenRuangOK.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangOK,"Departemen/Organisasi");
            }else if(LongitudeRuangOK.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangOK,"Longitude");
            }else if(LatitudeRuangOK.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangOK,"Latitude");
            }else if(AltitudeRuangOK.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangOK,"Altitude");
            }else{
                if(tbLokasiRuangOK.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.OK.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"inactive\"," +
                                    "\"name\": \"Ruang Operasi\"," +
                                    "\"description\": \"Ruang Operasi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangOK.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangOK.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangOK.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangOK.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.meghapustf("satu_sehat_mapping_lokasi_ruangok","id_lokasi_satusehat",tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),0).toString())==true){
                                tabModeRuangOK.removeRow(tbLokasiRuangOK.getSelectedRow());
                                emptTeks();
                                LCount.setText(""+tabModeRuangOK.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==3){
            if(KodeDepartemenRuangLabPK.getText().trim().equals("")||NamaDepartemenRuangLabPK.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangLabPK,"Departemen/Organisasi");
            }else if(LongitudeRuangLabPK.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangLabPK,"Longitude");
            }else if(LatitudeRuangLabPK.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangLabPK,"Latitude");
            }else if(AltitudeRuangLabPK.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangLabPK,"Altitude");
            }else{
                if(tbLokasiRuangLabPK.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABPK.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"inactive\"," +
                                    "\"name\": \"Ruang Laboratorium Patologi Klinik\"," +
                                    "\"description\": \"Ruang Laboratorium Patologi Klinik\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabPK.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabPK.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabPK.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabPK.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.meghapustf("satu_sehat_mapping_lokasi_ruanglab","id_lokasi_satusehat",tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),0).toString())==true){
                                tabModeRuangLabPK.removeRow(tbLokasiRuangLabPK.getSelectedRow());
                                emptTeks();
                                LCount.setText(""+tabModeRuangLabPK.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==4){
            if(KodeDepartemenRuangLabPA.getText().trim().equals("")||NamaDepartemenRuangLabPA.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangLabPA,"Departemen/Organisasi");
            }else if(LongitudeRuangLabPA.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangLabPA,"Longitude");
            }else if(LatitudeRuangLabPA.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangLabPA,"Latitude");
            }else if(AltitudeRuangLabPA.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangLabPA,"Altitude");
            }else{
                if(tbLokasiRuangLabPA.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABPA.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"inactive\"," +
                                    "\"name\": \"Ruang Laboratorium Patologi Anatomi\"," +
                                    "\"description\": \"Ruang Laboratorium Patologi Anatomi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabPA.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabPA.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabPA.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabPA.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.meghapustf("satu_sehat_mapping_lokasi_ruanglabpa","id_lokasi_satusehat",tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),0).toString())==true){
                                tabModeRuangLabPA.removeRow(tbLokasiRuangLabPA.getSelectedRow());
                                emptTeks();
                                LCount.setText(""+tabModeRuangLabPA.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==5){
            if(KodeDepartemenRuangLabMB.getText().trim().equals("")||NamaDepartemenRuangLabMB.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangLabMB,"Departemen/Organisasi");
            }else if(LongitudeRuangLabMB.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangLabMB,"Longitude");
            }else if(LatitudeRuangLabMB.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangLabMB,"Latitude");
            }else if(AltitudeRuangLabMB.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangLabMB,"Altitude");
            }else{
                if(tbLokasiRuangLabMB.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABMB.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"inactive\"," +
                                    "\"name\": \"Ruang Laboratorium Mikrobiologi & Bio Molekuler\"," +
                                    "\"description\": \"Ruang Laboratorium Mikrobiologi & Bio Molekuler\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabMB.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabMB.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabMB.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabMB.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.meghapustf("satu_sehat_mapping_lokasi_ruanglabmb","id_lokasi_satusehat",tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),0).toString())==true){
                                tabModeRuangLabMB.removeRow(tbLokasiRuangLabMB.getSelectedRow());
                                emptTeks();
                                LCount.setText(""+tabModeRuangLabMB.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==6){
            if(KodeDepartemenRuangRadiologi.getText().trim().equals("")||NamaDepartemenRuangRadiologi.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangRadiologi,"Departemen/Organisasi");
            }else if(LongitudeRuangRadiologi.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangRadiologi,"Longitude");
            }else if(LatitudeRuangRadiologi.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangRadiologi,"Latitude");
            }else if(AltitudeRuangRadiologi.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangRadiologi,"Altitude");
            }else{
                if(tbLokasiRuangRadiologi.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.RAD.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"inactive\"," +
                                    "\"name\": \"Ruang Radiologi\"," +
                                    "\"description\": \"Ruang Radiologi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangRadiologi.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangRadiologi.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangRadiologi.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangRadiologi.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.meghapustf("satu_sehat_mapping_lokasi_ruangrad","id_lokasi_satusehat",tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),0).toString())==true){
                                tabModeRuangRadiologi.removeRow(tbLokasiRuangRadiologi.getSelectedRow());
                                emptTeks();
                                LCount.setText(""+tabModeRuangRadiologi.getRowCount());
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==7){
            if(tbLokasiFarmasi.getSelectedRow()>-1){
                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    json = "{" +
                                "\"resourceType\": \"Location\"," +
                                "\"id\": \""+tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),2).toString()+"\"," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                        "\"value\": \""+KodeFarmasi.getText()+"\"" +
                                    "}" +
                                "]," +
                                "\"status\": \"inactive\"," +
                                "\"name\": \""+NamaFarmasi.getText()+"\"," +
                                "\"description\": \""+NamaFarmasi.getText()+"\"," +
                                "\"mode\": \"instance\"," +
                                "\"telecom\": [" +
                                    "{" +
                                        "\"system\": \"phone\"," +
                                        "\"value\": \""+akses.getkontakrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"email\"," +
                                        "\"value\": \""+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"url\"," +
                                        "\"value\": \"www."+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}" +
                                "]," +
                                "\"address\": {" +
                                    "\"use\": \"work\"," +
                                    "\"line\": [" +
                                        "\""+akses.getalamatrs()+"\"" +
                                    "]," +
                                    "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                    "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                    "\"country\": \"ID\"," +
                                    "\"extension\": [" +
                                        "{" +
                                            "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                            "\"extension\": [" +
                                                "{" +
                                                    "\"url\": \"province\"," +
                                                    "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"city\"," +
                                                    "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"district\"," +
                                                    "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"village\"," +
                                                    "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                "}" +
                                            "]" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"physicalType\": {" +
                                    "\"coding\": [" +
                                        "{" +
                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                            "\"code\": \"ro\"," +
                                            "\"display\": \"Room\"" +
                                        "}" +
                                    "]" +
                                "}," +
                                "\"position\": {" +
                                    "\"longitude\": "+LongitudeFarmasi.getText()+"," +
                                    "\"latitude\": "+LatitudeFarmasi.getText()+"," +
                                    "\"altitude\": "+AltitudeFarmasi.getText()+
                                "}," +
                                "\"managingOrganization\": {" +
                                    "\"reference\": \"Organization/"+IDOrganisasiFarmasi.getText()+"\"" +
                                "}" +
                            "}";
                    System.out.println("URL : "+link+"/Location");
                    System.out.println("Request JSON : "+json);
                    requestEntity = new HttpEntity(json,headers);
                    json=api.getRest().exchange(link+"/Location/"+tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),2).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);
                    response = root.path("id");
                    if(!response.asText().equals("")){
                        if(Valid.hapusTabletf(tabModeRuangFarmasi,KodeFarmasi,"satu_sehat_mapping_lokasi_depo_farmasi","kd_bangsal")==true){
                            tabModeRuangFarmasi.removeRow(tbLokasiFarmasi.getSelectedRow());
                            emptTeks();
                            LCount.setText(""+tabModeRuangFarmasi.getRowCount());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                    } 
                }catch(Exception e){
                    System.out.println("Notifikasi Bridging : "+e);
                    JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                }  
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data terlebih dahulu..!!");
            }
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
        if(TabRawat.getSelectedIndex()==0){
            if(KodeDepartemen.getText().trim().equals("")||NamaDepartemen.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemen,"Departemen/Organisasi");
            }else if(KodePoli.getText().trim().equals("")||NamaPoli.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemen,"Poli/Unit");
            }else if(Longitude.getText().trim().equals("")){
                Valid.textKosong(Longitude,"Longitude");
            }else if(Latitude.getText().trim().equals("")){
                Valid.textKosong(Latitude,"Latitude");
            }else if(Altitude.getText().trim().equals("")){
                Valid.textKosong(Altitude,"Altitude");
            }else{
                if(tbJnsPerawatan.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \""+KodePoli.getText()+"\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \""+NamaPoli.getText()+"\"," +
                                    "\"description\": \""+NamaPoli.getText()+"\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+Longitude.getText()+"," +
                                        "\"latitude\": "+Latitude.getText()+"," +
                                        "\"altitude\": "+Altitude.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasi.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("URL : "+link+"/Location");
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.mengedittf("satu_sehat_mapping_lokasi_ralan","kd_poli=?","kd_poli=?,id_organisasi_satusehat=?,id_lokasi_satusehat=?,longitude=?,latitude=?,altittude=?",7,new String[]{
                                    KodePoli.getText(),IDOrganisasi.getText(),response.asText(),Longitude.getText(),Latitude.getText(),Altitude.getText(),tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString()
                                })==true){
                                tabMode.setValueAt(KodePoli.getText(),tbJnsPerawatan.getSelectedRow(),0);
                                tabMode.setValueAt(NamaPoli.getText(),tbJnsPerawatan.getSelectedRow(),1);
                                tabMode.setValueAt(response.asText(),tbJnsPerawatan.getSelectedRow(),2);
                                tabMode.setValueAt(Longitude.getText(),tbJnsPerawatan.getSelectedRow(),3);
                                tabMode.setValueAt(Latitude.getText(),tbJnsPerawatan.getSelectedRow(),4);
                                tabMode.setValueAt(Altitude.getText(),tbJnsPerawatan.getSelectedRow(),5);
                                tabMode.setValueAt(KodeDepartemen.getText(),tbJnsPerawatan.getSelectedRow(),6);
                                tabMode.setValueAt(NamaDepartemen.getText(),tbJnsPerawatan.getSelectedRow(),7);
                                tabMode.setValueAt(IDOrganisasi.getText(),tbJnsPerawatan.getSelectedRow(),8);
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(KodeDepartemenKamar.getText().trim().equals("")||NamaDepartemenKamar.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenKamar,"Departemen/Organisasi");
            }else if(KodeKamar.getText().trim().equals("")||NamaKamar.getText().trim().equals("")){
                Valid.textKosong(KodeKamar,"Kamar/Ruang");
            }else if(LongitudeKamar.getText().trim().equals("")){
                Valid.textKosong(LongitudeKamar,"Longitude");
            }else if(LatitudeKamar.getText().trim().equals("")){
                Valid.textKosong(LatitudeKamar,"Latitude");
            }else if(AltitudeKamar.getText().trim().equals("")){
                Valid.textKosong(AltitudeKamar,"Altitude");
            }else{
                if(tbLokasiKamar.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),2).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \""+KodeKamar.getText()+"\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \""+NamaKamar.getText()+"\"," +
                                    "\"description\": \""+NamaKamar.getText()+" Nomor "+KodeKamar.getText()+"\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeKamar.getText()+"," +
                                        "\"latitude\": "+LatitudeKamar.getText()+"," +
                                        "\"altitude\": "+AltitudeKamar.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiKamar.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),2).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.mengedittf("satu_sehat_mapping_lokasi_ranap","kd_kamar=?","kd_kamar=?,id_organisasi_satusehat=?,id_lokasi_satusehat=?,longitude=?,latitude=?,altittude=?",7,new String[]{
                                    KodeKamar.getText(),IDOrganisasiKamar.getText(),response.asText(),LongitudeKamar.getText(),LatitudeKamar.getText(),AltitudeKamar.getText(),tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),0).toString()
                                })==true){
                                tabModeKamar.setValueAt(KodeKamar.getText(),tbLokasiKamar.getSelectedRow(),0);
                                tabModeKamar.setValueAt(NamaKamar.getText(),tbLokasiKamar.getSelectedRow(),1);
                                tabModeKamar.setValueAt(response.asText(),tbLokasiKamar.getSelectedRow(),2);
                                tabModeKamar.setValueAt(LongitudeKamar.getText(),tbLokasiKamar.getSelectedRow(),3);
                                tabModeKamar.setValueAt(LatitudeKamar.getText(),tbLokasiKamar.getSelectedRow(),4);
                                tabModeKamar.setValueAt(AltitudeKamar.getText(),tbLokasiKamar.getSelectedRow(),5);
                                tabModeKamar.setValueAt(KodeDepartemenKamar.getText(),tbLokasiKamar.getSelectedRow(),6);
                                tabModeKamar.setValueAt(NamaDepartemenKamar.getText(),tbLokasiKamar.getSelectedRow(),7);
                                tabModeKamar.setValueAt(IDOrganisasiKamar.getText(),tbLokasiKamar.getSelectedRow(),8);
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(KodeDepartemenRuangOK.getText().trim().equals("")||NamaDepartemenRuangOK.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangOK,"Departemen/Organisasi");
            }else if(LongitudeRuangOK.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangOK,"Longitude");
            }else if(LatitudeRuangOK.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangOK,"Latitude");
            }else if(AltitudeRuangOK.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangOK,"Altitude");
            }else{
                if(tbLokasiRuangOK.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.OK.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Operasi\"," +
                                    "\"description\": \"Ruang Operasi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangOK.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangOK.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangOK.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangOK.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.mengedittf("satu_sehat_mapping_lokasi_ruangok","id_lokasi_satusehat=?","id_organisasi_satusehat=?,id_lokasi_satusehat=?,longitude=?,latitude=?,altittude=?",6,new String[]{
                                    IDOrganisasiRuangOK.getText(),response.asText(),LongitudeRuangOK.getText(),LatitudeRuangOK.getText(),AltitudeRuangOK.getText(),tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),0).toString()
                                })==true){
                                tabModeRuangOK.setValueAt(response.asText(),tbLokasiRuangOK.getSelectedRow(),0);
                                tabModeRuangOK.setValueAt(LongitudeRuangOK.getText(),tbLokasiRuangOK.getSelectedRow(),1);
                                tabModeRuangOK.setValueAt(LatitudeRuangOK.getText(),tbLokasiRuangOK.getSelectedRow(),2);
                                tabModeRuangOK.setValueAt(AltitudeRuangOK.getText(),tbLokasiRuangOK.getSelectedRow(),3);
                                tabModeRuangOK.setValueAt(KodeDepartemenRuangOK.getText(),tbLokasiRuangOK.getSelectedRow(),4);
                                tabModeRuangOK.setValueAt(NamaDepartemenRuangOK.getText(),tbLokasiRuangOK.getSelectedRow(),5);
                                tabModeRuangOK.setValueAt(IDOrganisasiRuangOK.getText(),tbLokasiRuangOK.getSelectedRow(),6);
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==3){
            if(KodeDepartemenRuangLabPK.getText().trim().equals("")||NamaDepartemenRuangLabPK.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangLabPK,"Departemen/Organisasi");
            }else if(LongitudeRuangLabPK.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangLabPK,"Longitude");
            }else if(LatitudeRuangLabPK.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangLabPK,"Latitude");
            }else if(AltitudeRuangLabPK.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangLabPK,"Altitude");
            }else{
                if(tbLokasiRuangLabPK.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABPK.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Laboratorium Patologi Klinik\"," +
                                    "\"description\": \"Ruang Laboratorium Patologi Klinik\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabPK.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabPK.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabPK.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabPK.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.mengedittf("satu_sehat_mapping_lokasi_ruanglab","id_lokasi_satusehat=?","id_organisasi_satusehat=?,id_lokasi_satusehat=?,longitude=?,latitude=?,altittude=?",6,new String[]{
                                    IDOrganisasiRuangLabPK.getText(),response.asText(),LongitudeRuangLabPK.getText(),LatitudeRuangLabPK.getText(),AltitudeRuangLabPK.getText(),tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),0).toString()
                                })==true){
                                tabModeRuangLabPK.setValueAt(response.asText(),tbLokasiRuangLabPK.getSelectedRow(),0);
                                tabModeRuangLabPK.setValueAt(LongitudeRuangLabPK.getText(),tbLokasiRuangLabPK.getSelectedRow(),1);
                                tabModeRuangLabPK.setValueAt(LatitudeRuangLabPK.getText(),tbLokasiRuangLabPK.getSelectedRow(),2);
                                tabModeRuangLabPK.setValueAt(AltitudeRuangLabPK.getText(),tbLokasiRuangLabPK.getSelectedRow(),3);
                                tabModeRuangLabPK.setValueAt(KodeDepartemenRuangLabPK.getText(),tbLokasiRuangLabPK.getSelectedRow(),4);
                                tabModeRuangLabPK.setValueAt(NamaDepartemenRuangLabPK.getText(),tbLokasiRuangLabPK.getSelectedRow(),5);
                                tabModeRuangLabPK.setValueAt(IDOrganisasiRuangLabPK.getText(),tbLokasiRuangLabPK.getSelectedRow(),6);
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==4){
            if(KodeDepartemenRuangLabPA.getText().trim().equals("")||NamaDepartemenRuangLabPA.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangLabPA,"Departemen/Organisasi");
            }else if(LongitudeRuangLabPA.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangLabPA,"Longitude");
            }else if(LatitudeRuangLabPA.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangLabPA,"Latitude");
            }else if(AltitudeRuangLabPA.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangLabPA,"Altitude");
            }else{
                if(tbLokasiRuangLabPA.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABPA.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Laboratorium Patologi Anatomi\"," +
                                    "\"description\": \"Ruang Laboratorium Patologi Anatomi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabPA.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabPA.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabPA.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabPA.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.mengedittf("satu_sehat_mapping_lokasi_ruanglabpa","id_lokasi_satusehat=?","id_organisasi_satusehat=?,id_lokasi_satusehat=?,longitude=?,latitude=?,altittude=?",6,new String[]{
                                    IDOrganisasiRuangLabPA.getText(),response.asText(),LongitudeRuangLabPA.getText(),LatitudeRuangLabPA.getText(),AltitudeRuangLabPA.getText(),tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),0).toString()
                                })==true){
                                tabModeRuangLabPA.setValueAt(response.asText(),tbLokasiRuangLabPA.getSelectedRow(),0);
                                tabModeRuangLabPA.setValueAt(LongitudeRuangLabPA.getText(),tbLokasiRuangLabPA.getSelectedRow(),1);
                                tabModeRuangLabPA.setValueAt(LatitudeRuangLabPA.getText(),tbLokasiRuangLabPA.getSelectedRow(),2);
                                tabModeRuangLabPA.setValueAt(AltitudeRuangLabPA.getText(),tbLokasiRuangLabPA.getSelectedRow(),3);
                                tabModeRuangLabPA.setValueAt(KodeDepartemenRuangLabPA.getText(),tbLokasiRuangLabPA.getSelectedRow(),4);
                                tabModeRuangLabPA.setValueAt(NamaDepartemenRuangLabPA.getText(),tbLokasiRuangLabPA.getSelectedRow(),5);
                                tabModeRuangLabPA.setValueAt(IDOrganisasiRuangLabPA.getText(),tbLokasiRuangLabPA.getSelectedRow(),6);
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==5){
            if(KodeDepartemenRuangLabMB.getText().trim().equals("")||NamaDepartemenRuangLabMB.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangLabMB,"Departemen/Organisasi");
            }else if(LongitudeRuangLabMB.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangLabMB,"Longitude");
            }else if(LatitudeRuangLabMB.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangLabMB,"Latitude");
            }else if(AltitudeRuangLabMB.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangLabMB,"Altitude");
            }else{
                if(tbLokasiRuangLabMB.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.LABMB.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Laboratorium Mikrobiologi & Bio Molekuler\"," +
                                    "\"description\": \"Ruang Laboratorium Mikrobiologi & Bio Molekuler\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangLabMB.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangLabMB.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangLabMB.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangLabMB.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.mengedittf("satu_sehat_mapping_lokasi_ruanglabmb","id_lokasi_satusehat=?","id_organisasi_satusehat=?,id_lokasi_satusehat=?,longitude=?,latitude=?,altittude=?",6,new String[]{
                                    IDOrganisasiRuangLabMB.getText(),response.asText(),LongitudeRuangLabMB.getText(),LatitudeRuangLabMB.getText(),AltitudeRuangLabMB.getText(),tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),0).toString()
                                })==true){
                                tabModeRuangLabMB.setValueAt(response.asText(),tbLokasiRuangLabMB.getSelectedRow(),0);
                                tabModeRuangLabMB.setValueAt(LongitudeRuangLabMB.getText(),tbLokasiRuangLabMB.getSelectedRow(),1);
                                tabModeRuangLabMB.setValueAt(LatitudeRuangLabMB.getText(),tbLokasiRuangLabMB.getSelectedRow(),2);
                                tabModeRuangLabMB.setValueAt(AltitudeRuangLabMB.getText(),tbLokasiRuangLabMB.getSelectedRow(),3);
                                tabModeRuangLabMB.setValueAt(KodeDepartemenRuangLabMB.getText(),tbLokasiRuangLabMB.getSelectedRow(),4);
                                tabModeRuangLabMB.setValueAt(NamaDepartemenRuangLabMB.getText(),tbLokasiRuangLabMB.getSelectedRow(),5);
                                tabModeRuangLabMB.setValueAt(IDOrganisasiRuangLabMB.getText(),tbLokasiRuangLabMB.getSelectedRow(),6);
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==6){
            if(KodeDepartemenRuangRadiologi.getText().trim().equals("")||NamaDepartemenRuangRadiologi.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenRuangRadiologi,"Departemen/Organisasi");
            }else if(LongitudeRuangRadiologi.getText().trim().equals("")){
                Valid.textKosong(LongitudeRuangRadiologi,"Longitude");
            }else if(LatitudeRuangRadiologi.getText().trim().equals("")){
                Valid.textKosong(LatitudeRuangRadiologi,"Latitude");
            }else if(AltitudeRuangRadiologi.getText().trim().equals("")){
                Valid.textKosong(AltitudeRuangRadiologi,"Altitude");
            }else{
                if(tbLokasiRuangRadiologi.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),0).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \"R.RAD.FAS\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \"Ruang Radiologi\"," +
                                    "\"description\": \"Ruang Radiologi\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeRuangRadiologi.getText()+"," +
                                        "\"latitude\": "+LatitudeRuangRadiologi.getText()+"," +
                                        "\"altitude\": "+AltitudeRuangRadiologi.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiRuangRadiologi.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),0).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.mengedittf("satu_sehat_mapping_lokasi_ruangrad","id_lokasi_satusehat=?","id_organisasi_satusehat=?,id_lokasi_satusehat=?,longitude=?,latitude=?,altittude=?",6,new String[]{
                                    IDOrganisasiRuangRadiologi.getText(),response.asText(),LongitudeRuangRadiologi.getText(),LatitudeRuangRadiologi.getText(),AltitudeRuangRadiologi.getText(),tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),0).toString()
                                })==true){
                                tabModeRuangRadiologi.setValueAt(response.asText(),tbLokasiRuangRadiologi.getSelectedRow(),0);
                                tabModeRuangRadiologi.setValueAt(LongitudeRuangRadiologi.getText(),tbLokasiRuangRadiologi.getSelectedRow(),1);
                                tabModeRuangRadiologi.setValueAt(LatitudeRuangRadiologi.getText(),tbLokasiRuangRadiologi.getSelectedRow(),2);
                                tabModeRuangRadiologi.setValueAt(AltitudeRuangRadiologi.getText(),tbLokasiRuangRadiologi.getSelectedRow(),3);
                                tabModeRuangRadiologi.setValueAt(KodeDepartemenRuangRadiologi.getText(),tbLokasiRuangRadiologi.getSelectedRow(),4);
                                tabModeRuangRadiologi.setValueAt(NamaDepartemenRuangRadiologi.getText(),tbLokasiRuangRadiologi.getSelectedRow(),5);
                                tabModeRuangRadiologi.setValueAt(IDOrganisasiRuangRadiologi.getText(),tbLokasiRuangRadiologi.getSelectedRow(),6);
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
            }
        }else if(TabRawat.getSelectedIndex()==7){
            if(KodeDepartemenFarmasi.getText().trim().equals("")||NamaDepartemenFarmasi.getText().trim().equals("")){
                Valid.textKosong(KodeDepartemenFarmasi,"Departemen/Organisasi");
            }else if(KodeFarmasi.getText().trim().equals("")||NamaFarmasi.getText().trim().equals("")){
                Valid.textKosong(KodeFarmasi,"Farmasi/Depo");
            }else if(LongitudeFarmasi.getText().trim().equals("")){
                Valid.textKosong(LongitudeFarmasi,"Longitude");
            }else if(LatitudeFarmasi.getText().trim().equals("")){
                Valid.textKosong(LatitudeFarmasi,"Latitude");
            }else if(AltitudeFarmasi.getText().trim().equals("")){
                Valid.textKosong(AltitudeFarmasi,"Altitude");
            }else{
                if(tbLokasiFarmasi.getSelectedRow()>-1){
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Location\"," +
                                    "\"id\": \""+tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),2).toString()+"\"," +
                                    "\"identifier\": [" +
                                        "{" +
                                            "\"system\": \"http://sys-ids.kemkes.go.id/location/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                            "\"value\": \""+KodeFarmasi.getText()+"\"" +
                                        "}" +
                                    "]," +
                                    "\"status\": \"active\"," +
                                    "\"name\": \""+NamaFarmasi.getText()+"\"," +
                                    "\"description\": \""+NamaFarmasi.getText()+"\"," +
                                    "\"mode\": \"instance\"," +
                                    "\"telecom\": [" +
                                        "{" +
                                            "\"system\": \"phone\"," +
                                            "\"value\": \""+akses.getkontakrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"email\"," +
                                            "\"value\": \""+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}," +
                                        "{" +
                                            "\"system\": \"url\"," +
                                            "\"value\": \"www."+akses.getemailrs()+"\"," +
                                            "\"use\": \"work\"" +
                                        "}" +
                                    "]," +
                                    "\"address\": {" +
                                        "\"use\": \"work\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"physicalType\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/location-physical-type\"," +
                                                "\"code\": \"ro\"," +
                                                "\"display\": \"Room\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"position\": {" +
                                        "\"longitude\": "+LongitudeFarmasi.getText()+"," +
                                        "\"latitude\": "+LatitudeFarmasi.getText()+"," +
                                        "\"altitude\": "+AltitudeFarmasi.getText()+
                                    "}," +
                                    "\"managingOrganization\": {" +
                                        "\"reference\": \"Organization/"+IDOrganisasiFarmasi.getText()+"\"" +
                                    "}" +
                                "}";
                        System.out.println("URL : "+link+"/Location");
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Location/"+tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),2).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            if(Sequel.mengedittf("satu_sehat_mapping_lokasi_depo_farmasi","kd_bangsal=?","kd_bangsal=?,id_organisasi_satusehat=?,id_lokasi_satusehat=?,longitude=?,latitude=?,altittude=?",7,new String[]{
                                    KodeFarmasi.getText(),IDOrganisasiFarmasi.getText(),response.asText(),LongitudeFarmasi.getText(),LatitudeFarmasi.getText(),AltitudeFarmasi.getText(),tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),0).toString()
                                })==true){
                                tabModeRuangFarmasi.setValueAt(KodeFarmasi.getText(),tbLokasiFarmasi.getSelectedRow(),0);
                                tabModeRuangFarmasi.setValueAt(NamaFarmasi.getText(),tbLokasiFarmasi.getSelectedRow(),1);
                                tabModeRuangFarmasi.setValueAt(response.asText(),tbLokasiFarmasi.getSelectedRow(),2);
                                tabModeRuangFarmasi.setValueAt(LongitudeFarmasi.getText(),tbLokasiFarmasi.getSelectedRow(),3);
                                tabModeRuangFarmasi.setValueAt(LatitudeFarmasi.getText(),tbLokasiFarmasi.getSelectedRow(),4);
                                tabModeRuangFarmasi.setValueAt(AltitudeFarmasi.getText(),tbLokasiFarmasi.getSelectedRow(),5);
                                tabModeRuangFarmasi.setValueAt(KodeDepartemenFarmasi.getText(),tbLokasiFarmasi.getSelectedRow(),6);
                                tabModeRuangFarmasi.setValueAt(NamaDepartemenFarmasi.getText(),tbLokasiFarmasi.getSelectedRow(),7);
                                tabModeRuangFarmasi.setValueAt(IDOrganisasiFarmasi.getText(),tbLokasiFarmasi.getSelectedRow(),8);
                                emptTeks();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Gagal melakukan mapping lokasi ke server Satu Sehat Kemenkes");
                        } 
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                        JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                    }  
                }                
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
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(TabRawat.getSelectedIndex()==0){
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
                    param.put("parameter","%"+TCari.getText().trim()+"%");
                    Valid.MyReport("rptMapingLokasiSatuSehat.jasper","report","::[ Mapping Poli/Lokasi Satu Sehat Kemenkes ]::",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat.getSelectedIndex()==1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeKamar.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            }else if(tabModeKamar.getRowCount()!=0){            
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    param.put("parameter","%"+TCari.getText().trim()+"%");
                    Valid.MyReport("rptMapingLokasiSatuSehat2.jasper","report","::[ Mapping Kamar Inap/Ruang Inap Satu Sehat Kemenkes ]::",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat.getSelectedIndex()==2){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeRuangOK.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            }else if(tabModeRuangOK.getRowCount()!=0){            
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptMapingLokasiSatuSehat3.jasper","report","::[ Mapping Lokasi Ruang Operasi Satu Sehat Kemenkes ]::",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat.getSelectedIndex()==3){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeRuangLabPK.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            }else if(tabModeRuangLabPK.getRowCount()!=0){            
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptMapingLokasiSatuSehat4.jasper","report","::[ Mapping Lokasi Ruang Laboratorium Patologi Klinis Satu Sehat Kemenkes ]::",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat.getSelectedIndex()==4){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeRuangLabPA.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            }else if(tabModeRuangLabPA.getRowCount()!=0){            
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptMapingLokasiSatuSehat5.jasper","report","::[ Mapping Lokasi Ruang Laboratorium Patologi Anatomi Satu Sehat Kemenkes ]::",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat.getSelectedIndex()==5){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeRuangLabMB.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            }else if(tabModeRuangLabMB.getRowCount()!=0){            
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptMapingLokasiSatuSehat6.jasper","report","::[ Mapping Lokasi Ruang Laboratorium Mikrobiologi & Bio Molekuler Satu Sehat Kemenkes ]::",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat.getSelectedIndex()==6){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeRuangRadiologi.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            }else if(tabModeRuangRadiologi.getRowCount()!=0){            
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReport("rptMapingLokasiSatuSehat7.jasper","report","::[ Mapping Lokasi Ruang Radiologi Satu Sehat Kemenkes ]::",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
        }else if(TabRawat.getSelectedIndex()==7){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabModeRuangFarmasi.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            }else if(tabModeRuangFarmasi.getRowCount()!=0){            
                    Map<String, Object> param = new HashMap<>();    
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    param.put("parameter","%"+TCari.getText().trim()+"%");
                    Valid.MyReport("rptMapingLokasiSatuSehat8.jasper","report","::[ Mapping Lokasi Depo/Farmasi Satu Sehat Kemenkes ]::",param);            
            }
            this.setCursor(Cursor.getDefaultCursor());
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
        TabRawatMouseClicked(null);
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
        TabRawatMouseClicked(null);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        emptTeks();
    }//GEN-LAST:event_formWindowOpened

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    private void btnPoliRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliRSActionPerformed
        DlgCariPoli poli=new DlgCariPoli(null,false);
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){                    
                    KodePoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NamaPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                }
                KodePoli.requestFocus();
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
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoliRSActionPerformed

    private void btnPoliRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliRSKeyPressed
        Valid.pindah(evt, TCari,Longitude);
    }//GEN-LAST:event_btnPoliRSKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilkamar();
        }else if(TabRawat.getSelectedIndex()==2){
            tampilruangok();
        }else if(TabRawat.getSelectedIndex()==3){
            tampilruanglabpk();
        }else if(TabRawat.getSelectedIndex()==4){
            tampilruanglabpa();
        }else if(TabRawat.getSelectedIndex()==5){
            tampilruanglabmb();
        }else if(TabRawat.getSelectedIndex()==6){
            tampilruangradiologi();
        }else if(TabRawat.getSelectedIndex()==7){
            tampilruangfarmasi();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void btnDepartemenKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenKamarActionPerformed
        pilih=2;
        organisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        organisasi.setLocationRelativeTo(internalFrame1);
        organisasi.setVisible(true);
    }//GEN-LAST:event_btnDepartemenKamarActionPerformed

    private void btnDepartemenKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenKamarKeyPressed
        Valid.pindah(evt, AltitudeKamar, BtnSimpan);
    }//GEN-LAST:event_btnDepartemenKamarKeyPressed

    private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarActionPerformed
        DlgKamar kamar=new DlgKamar(null,false);
        kamar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kamar.getTable().getSelectedRow()!= -1){   
                    KodeKamar.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),1).toString());  
                    NamaKamar.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(),3).toString()); 
                }  
                KodeKamar.requestFocus();
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
        
        kamar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kamar.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        kamar.load();
        kamar.isCek();
        kamar.emptTeks();
        kamar.tampil2();
        kamar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setVisible(true);
    }//GEN-LAST:event_btnKamarActionPerformed

    private void btnKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKamarKeyPressed
        Valid.pindah(evt, TCari, LongitudeKamar);
    }//GEN-LAST:event_btnKamarKeyPressed

    private void tbLokasiKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLokasiKamarMouseClicked
        if(tabModeKamar.getRowCount()!=0){
            try {
                getDataKamar();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLokasiKamarMouseClicked

    private void tbLokasiKamarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLokasiKamarKeyReleased
        if(tabModeKamar.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataKamar();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLokasiKamarKeyReleased

    private void LongitudeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LongitudeKeyPressed
        Valid.pindah(evt, btnPoliRS,Latitude);
    }//GEN-LAST:event_LongitudeKeyPressed

    private void LatitudeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatitudeKeyPressed
        Valid.pindah(evt, Longitude,Altitude);
    }//GEN-LAST:event_LatitudeKeyPressed

    private void AltitudeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AltitudeKeyPressed
        Valid.pindah(evt, Latitude,btnDepartemenRS);
    }//GEN-LAST:event_AltitudeKeyPressed

    private void LongitudeKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LongitudeKamarKeyPressed
        Valid.pindah(evt, btnKamar, LatitudeKamar);
    }//GEN-LAST:event_LongitudeKamarKeyPressed

    private void LatitudeKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatitudeKamarKeyPressed
        Valid.pindah(evt, LongitudeKamar, AltitudeKamar);
    }//GEN-LAST:event_LatitudeKamarKeyPressed

    private void AltitudeKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AltitudeKamarKeyPressed
        Valid.pindah(evt, LatitudeKamar, btnDepartemenKamar);
    }//GEN-LAST:event_AltitudeKamarKeyPressed

    private void btnDepartemenRuangOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenRuangOKActionPerformed
        pilih=3;
        organisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        organisasi.setLocationRelativeTo(internalFrame1);
        organisasi.setVisible(true);
    }//GEN-LAST:event_btnDepartemenRuangOKActionPerformed

    private void btnDepartemenRuangOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenRuangOKKeyPressed
        Valid.pindah(evt, AltitudeRuangOK, BtnSimpan);
    }//GEN-LAST:event_btnDepartemenRuangOKKeyPressed

    private void LongitudeRuangOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LongitudeRuangOKKeyPressed
        Valid.pindah(evt, TCari, LatitudeRuangOK);
    }//GEN-LAST:event_LongitudeRuangOKKeyPressed

    private void LatitudeRuangOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatitudeRuangOKKeyPressed
        Valid.pindah(evt, LongitudeRuangOK, AltitudeRuangOK);
    }//GEN-LAST:event_LatitudeRuangOKKeyPressed

    private void AltitudeRuangOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AltitudeRuangOKKeyPressed
        Valid.pindah(evt, LatitudeRuangOK, btnDepartemenRuangOK);
    }//GEN-LAST:event_AltitudeRuangOKKeyPressed

    private void tbLokasiRuangOKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLokasiRuangOKMouseClicked
        if(tabModeRuangOK.getRowCount()!=0){
            try {
                getDataRuangOK();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLokasiRuangOKMouseClicked

    private void tbLokasiRuangOKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLokasiRuangOKKeyReleased
        if(tabModeRuangOK.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataRuangOK();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLokasiRuangOKKeyReleased

    private void btnDepartemenRuangLabPKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenRuangLabPKActionPerformed
        pilih=4;
        organisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        organisasi.setLocationRelativeTo(internalFrame1);
        organisasi.setVisible(true);
    }//GEN-LAST:event_btnDepartemenRuangLabPKActionPerformed

    private void btnDepartemenRuangLabPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenRuangLabPKKeyPressed
        Valid.pindah(evt, AltitudeRuangLabPK, BtnSimpan);
    }//GEN-LAST:event_btnDepartemenRuangLabPKKeyPressed

    private void LongitudeRuangLabPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LongitudeRuangLabPKKeyPressed
        Valid.pindah(evt, TCari, LatitudeRuangLabPK);
    }//GEN-LAST:event_LongitudeRuangLabPKKeyPressed

    private void LatitudeRuangLabPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatitudeRuangLabPKKeyPressed
        Valid.pindah(evt, LongitudeRuangLabPK, AltitudeRuangLabPK);
    }//GEN-LAST:event_LatitudeRuangLabPKKeyPressed

    private void AltitudeRuangLabPKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AltitudeRuangLabPKKeyPressed
        Valid.pindah(evt, LatitudeRuangLabPK, btnDepartemenRuangOK);
    }//GEN-LAST:event_AltitudeRuangLabPKKeyPressed

    private void tbLokasiRuangLabPKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLokasiRuangLabPKMouseClicked
        if(tabModeRuangLabPK.getRowCount()!=0){
            try {
                getDataRuangLabPK();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLokasiRuangLabPKMouseClicked

    private void tbLokasiRuangLabPKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLokasiRuangLabPKKeyReleased
        if(tabModeRuangLabPK.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataRuangLabPK();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLokasiRuangLabPKKeyReleased

    private void btnDepartemenRuangLabPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenRuangLabPAActionPerformed
        pilih=5;
        organisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        organisasi.setLocationRelativeTo(internalFrame1);
        organisasi.setVisible(true);
    }//GEN-LAST:event_btnDepartemenRuangLabPAActionPerformed

    private void btnDepartemenRuangLabPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenRuangLabPAKeyPressed
        Valid.pindah(evt, AltitudeRuangLabPA, BtnSimpan);
    }//GEN-LAST:event_btnDepartemenRuangLabPAKeyPressed

    private void LongitudeRuangLabPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LongitudeRuangLabPAKeyPressed
        Valid.pindah(evt, TCari, LatitudeRuangLabPA);
    }//GEN-LAST:event_LongitudeRuangLabPAKeyPressed

    private void LatitudeRuangLabPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatitudeRuangLabPAKeyPressed
        Valid.pindah(evt,LongitudeRuangLabPA, AltitudeRuangLabPA);
    }//GEN-LAST:event_LatitudeRuangLabPAKeyPressed

    private void AltitudeRuangLabPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AltitudeRuangLabPAKeyPressed
        Valid.pindah(evt, LatitudeRuangLabPA, btnDepartemenRuangLabPA);
    }//GEN-LAST:event_AltitudeRuangLabPAKeyPressed

    private void tbLokasiRuangLabPAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLokasiRuangLabPAMouseClicked
        if(tabModeRuangLabPA.getRowCount()!=0){
            try {
                getDataRuangLabPA();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLokasiRuangLabPAMouseClicked

    private void tbLokasiRuangLabPAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLokasiRuangLabPAKeyReleased
        if(tabModeRuangLabPA.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataRuangLabPA();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLokasiRuangLabPAKeyReleased

    private void btnDepartemenRuangLabMBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenRuangLabMBActionPerformed
        pilih=6;
        organisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        organisasi.setLocationRelativeTo(internalFrame1);
        organisasi.setVisible(true);
    }//GEN-LAST:event_btnDepartemenRuangLabMBActionPerformed

    private void btnDepartemenRuangLabMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenRuangLabMBKeyPressed
        Valid.pindah(evt, AltitudeRuangLabMB, BtnSimpan);
    }//GEN-LAST:event_btnDepartemenRuangLabMBKeyPressed

    private void LongitudeRuangLabMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LongitudeRuangLabMBKeyPressed
        Valid.pindah(evt, TCari, LatitudeRuangLabMB);
    }//GEN-LAST:event_LongitudeRuangLabMBKeyPressed

    private void LatitudeRuangLabMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatitudeRuangLabMBKeyPressed
        Valid.pindah(evt, LongitudeRuangLabMB, AltitudeRuangLabMB);
    }//GEN-LAST:event_LatitudeRuangLabMBKeyPressed

    private void AltitudeRuangLabMBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AltitudeRuangLabMBKeyPressed
        Valid.pindah(evt, LatitudeRuangLabMB, btnDepartemenRuangLabMB);
    }//GEN-LAST:event_AltitudeRuangLabMBKeyPressed

    private void tbLokasiRuangLabMBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLokasiRuangLabMBMouseClicked
        if(tabModeRuangLabMB.getRowCount()!=0){
            try {
                getDataRuangLabMB();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLokasiRuangLabMBMouseClicked

    private void tbLokasiRuangLabMBKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLokasiRuangLabMBKeyReleased
        if(tabModeRuangLabMB.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataRuangLabMB();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLokasiRuangLabMBKeyReleased

    private void btnDepartemenRuangRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenRuangRadiologiActionPerformed
        pilih=7;
        organisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        organisasi.setLocationRelativeTo(internalFrame1);
        organisasi.setVisible(true);
    }//GEN-LAST:event_btnDepartemenRuangRadiologiActionPerformed

    private void btnDepartemenRuangRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenRuangRadiologiKeyPressed
        Valid.pindah(evt, AltitudeRuangRadiologi, BtnSimpan);
    }//GEN-LAST:event_btnDepartemenRuangRadiologiKeyPressed

    private void LongitudeRuangRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LongitudeRuangRadiologiKeyPressed
        Valid.pindah(evt, TCari, LatitudeRuangRadiologi);
    }//GEN-LAST:event_LongitudeRuangRadiologiKeyPressed

    private void LatitudeRuangRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatitudeRuangRadiologiKeyPressed
        Valid.pindah(evt, LongitudeRuangRadiologi, AltitudeRuangRadiologi);
    }//GEN-LAST:event_LatitudeRuangRadiologiKeyPressed

    private void AltitudeRuangRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AltitudeRuangRadiologiKeyPressed
        Valid.pindah(evt, LatitudeRuangRadiologi, btnDepartemenRuangRadiologi);
    }//GEN-LAST:event_AltitudeRuangRadiologiKeyPressed

    private void tbLokasiRuangRadiologiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLokasiRuangRadiologiMouseClicked
        if(tabModeRuangRadiologi.getRowCount()!=0){
            try {
                getDataRuangRadiologi();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLokasiRuangRadiologiMouseClicked

    private void tbLokasiRuangRadiologiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLokasiRuangRadiologiKeyReleased
        if(tabModeRuangRadiologi.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataRuangRadiologi();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLokasiRuangRadiologiKeyReleased

    private void btnDepartemenFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenFarmasiActionPerformed
        pilih=8;
        organisasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        organisasi.setLocationRelativeTo(internalFrame1);
        organisasi.setVisible(true);
    }//GEN-LAST:event_btnDepartemenFarmasiActionPerformed

    private void btnDepartemenFarmasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenFarmasiKeyPressed
        Valid.pindah(evt, AltitudeFarmasi,BtnSimpan);
    }//GEN-LAST:event_btnDepartemenFarmasiKeyPressed

    private void btnFarmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFarmasiActionPerformed
        DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                    
                    KodeFarmasi.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    NamaFarmasi.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }
                KodeFarmasi.requestFocus();
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
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnFarmasiActionPerformed

    private void btnFarmasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnFarmasiKeyPressed
        Valid.pindah(evt, TCari,LongitudeFarmasi);
    }//GEN-LAST:event_btnFarmasiKeyPressed

    private void LongitudeFarmasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LongitudeFarmasiKeyPressed
        Valid.pindah(evt, btnFarmasi,LatitudeFarmasi);
    }//GEN-LAST:event_LongitudeFarmasiKeyPressed

    private void LatitudeFarmasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatitudeFarmasiKeyPressed
        Valid.pindah(evt, LongitudeFarmasi,AltitudeFarmasi);
    }//GEN-LAST:event_LatitudeFarmasiKeyPressed

    private void AltitudeFarmasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AltitudeFarmasiKeyPressed
        Valid.pindah(evt,LatitudeFarmasi,btnDepartemenFarmasi);
    }//GEN-LAST:event_AltitudeFarmasiKeyPressed

    private void tbLokasiFarmasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLokasiFarmasiMouseClicked
        if(tabModeRuangFarmasi.getRowCount()!=0){
            try {
                getDataRuangFarmasi();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLokasiFarmasiMouseClicked

    private void tbLokasiFarmasiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLokasiFarmasiKeyReleased
        if(tabModeRuangFarmasi.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataRuangFarmasi();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLokasiFarmasiKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatMapingLokasi dialog = new SatuSehatMapingLokasi(new javax.swing.JFrame(), true);
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
    private widget.TextBox Altitude;
    private widget.TextBox AltitudeFarmasi;
    private widget.TextBox AltitudeKamar;
    private widget.TextBox AltitudeRuangLabMB;
    private widget.TextBox AltitudeRuangLabPA;
    private widget.TextBox AltitudeRuangLabPK;
    private widget.TextBox AltitudeRuangOK;
    private widget.TextBox AltitudeRuangRadiologi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.PanelBiasa FormInput2;
    private widget.PanelBiasa FormInput3;
    private widget.PanelBiasa FormInput4;
    private widget.PanelBiasa FormInput5;
    private widget.PanelBiasa FormInput6;
    private widget.PanelBiasa FormInput7;
    private widget.TextBox IDOrganisasi;
    private widget.TextBox IDOrganisasiFarmasi;
    private widget.TextBox IDOrganisasiKamar;
    private widget.TextBox IDOrganisasiRuangLabMB;
    private widget.TextBox IDOrganisasiRuangLabPA;
    private widget.TextBox IDOrganisasiRuangLabPK;
    private widget.TextBox IDOrganisasiRuangOK;
    private widget.TextBox IDOrganisasiRuangRadiologi;
    private widget.TextBox KodeDepartemen;
    private widget.TextBox KodeDepartemenFarmasi;
    private widget.TextBox KodeDepartemenKamar;
    private widget.TextBox KodeDepartemenRuangLabMB;
    private widget.TextBox KodeDepartemenRuangLabPA;
    private widget.TextBox KodeDepartemenRuangLabPK;
    private widget.TextBox KodeDepartemenRuangOK;
    private widget.TextBox KodeDepartemenRuangRadiologi;
    private widget.TextBox KodeFarmasi;
    private widget.TextBox KodeKamar;
    private widget.TextBox KodePoli;
    private widget.Label LCount;
    private widget.TextBox Latitude;
    private widget.TextBox LatitudeFarmasi;
    private widget.TextBox LatitudeKamar;
    private widget.TextBox LatitudeRuangLabMB;
    private widget.TextBox LatitudeRuangLabPA;
    private widget.TextBox LatitudeRuangLabPK;
    private widget.TextBox LatitudeRuangOK;
    private widget.TextBox LatitudeRuangRadiologi;
    private widget.TextBox Longitude;
    private widget.TextBox LongitudeFarmasi;
    private widget.TextBox LongitudeKamar;
    private widget.TextBox LongitudeRuangLabMB;
    private widget.TextBox LongitudeRuangLabPA;
    private widget.TextBox LongitudeRuangLabPK;
    private widget.TextBox LongitudeRuangOK;
    private widget.TextBox LongitudeRuangRadiologi;
    private widget.TextBox NamaDepartemen;
    private widget.TextBox NamaDepartemenFarmasi;
    private widget.TextBox NamaDepartemenKamar;
    private widget.TextBox NamaDepartemenRuangLabMB;
    private widget.TextBox NamaDepartemenRuangLabPA;
    private widget.TextBox NamaDepartemenRuangLabPK;
    private widget.TextBox NamaDepartemenRuangOK;
    private widget.TextBox NamaDepartemenRuangRadiologi;
    private widget.TextBox NamaFarmasi;
    private widget.TextBox NamaKamar;
    private widget.TextBox NamaPoli;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnDepartemenFarmasi;
    private widget.Button btnDepartemenKamar;
    private widget.Button btnDepartemenRS;
    private widget.Button btnDepartemenRuangLabMB;
    private widget.Button btnDepartemenRuangLabPA;
    private widget.Button btnDepartemenRuangLabPK;
    private widget.Button btnDepartemenRuangOK;
    private widget.Button btnDepartemenRuangRadiologi;
    private widget.Button btnFarmasi;
    private widget.Button btnKamar;
    private widget.Button btnPoliRS;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
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
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    private widget.Table tbLokasiFarmasi;
    private widget.Table tbLokasiKamar;
    private widget.Table tbLokasiRuangLabMB;
    private widget.Table tbLokasiRuangLabPA;
    private widget.Table tbLokasiRuangLabPK;
    private widget.Table tbLokasiRuangOK;
    private widget.Table tbLokasiRuangRadiologi;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_lokasi_ralan.kd_poli,poliklinik.nm_poli,satu_sehat_mapping_lokasi_ralan.id_lokasi_satusehat,"+
                   "satu_sehat_mapping_lokasi_ralan.longitude,satu_sehat_mapping_lokasi_ralan.latitude,satu_sehat_mapping_lokasi_ralan.altittude,"+
                   "satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_lokasi_ralan.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_lokasi_ralan inner join poliklinik on satu_sehat_mapping_lokasi_ralan.kd_poli=poliklinik.kd_poli "+
                   "inner join satu_sehat_mapping_departemen on satu_sehat_mapping_lokasi_ralan.id_organisasi_satusehat=satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id "+
                   (TCari.getText().equals("")?"":"where satu_sehat_mapping_departemen.dep_id like ? or departemen.nama like ? or "+
                   "poliklinik.nm_poli like ? or satu_sehat_mapping_lokasi_ralan.kd_poli like ?")+" order by departemen.nama");
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("kd_poli"),rs.getString("nm_poli"),rs.getString("id_lokasi_satusehat"),rs.getString("longitude"),rs.getString("latitude"),
                        rs.getString("altittude"),rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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
        if(TabRawat.getSelectedIndex()==0){
            KodePoli.setText("");
            NamaPoli.setText("");
            Longitude.setText("");
            Altitude.setText("");
            Latitude.setText("");
            KodeDepartemen.setText("");
            NamaDepartemen.setText("");
            IDOrganisasi.setText("");
            btnPoliRS.requestFocus();
        }else if(TabRawat.getSelectedIndex()==1){
            KodeKamar.setText("");
            NamaKamar.setText("");
            LongitudeKamar.setText("");
            AltitudeKamar.setText("");
            LatitudeKamar.setText("");
            KodeDepartemenKamar.setText("");
            NamaDepartemenKamar.setText("");
            IDOrganisasiKamar.setText("");
            btnKamar.requestFocus();
        }else if(TabRawat.getSelectedIndex()==2){
            LongitudeRuangOK.setText("");
            AltitudeRuangOK.setText("");
            LatitudeRuangOK.setText("");
            KodeDepartemenRuangOK.setText("");
            NamaDepartemenRuangOK.setText("");
            LongitudeRuangOK.requestFocus();
            IDOrganisasiRuangOK.setText("");
        }else if(TabRawat.getSelectedIndex()==3){
            LongitudeRuangLabPK.setText("");
            AltitudeRuangLabPK.setText("");
            LatitudeRuangLabPK.setText("");
            KodeDepartemenRuangLabPK.setText("");
            NamaDepartemenRuangLabPK.setText("");
            LongitudeRuangLabPK.requestFocus();
            IDOrganisasiRuangLabPK.setText("");
        }else if(TabRawat.getSelectedIndex()==4){
            LongitudeRuangLabPA.setText("");
            AltitudeRuangLabPA.setText("");
            LatitudeRuangLabPA.setText("");
            KodeDepartemenRuangLabPA.setText("");
            NamaDepartemenRuangLabPA.setText("");
            LongitudeRuangLabPA.requestFocus();
            IDOrganisasiRuangLabPA.setText("");
        }else if(TabRawat.getSelectedIndex()==5){
            LongitudeRuangLabMB.setText("");
            AltitudeRuangLabMB.setText("");
            LatitudeRuangLabMB.setText("");
            KodeDepartemenRuangLabMB.setText("");
            NamaDepartemenRuangLabMB.setText("");
            LongitudeRuangLabMB.requestFocus();
            IDOrganisasiRuangLabMB.setText("");
        }else if(TabRawat.getSelectedIndex()==6){
            LongitudeRuangRadiologi.setText("");
            AltitudeRuangRadiologi.setText("");
            LatitudeRuangRadiologi.setText("");
            KodeDepartemenRuangRadiologi.setText("");
            NamaDepartemenRuangRadiologi.setText("");
            LongitudeRuangRadiologi.requestFocus();
            IDOrganisasiRuangRadiologi.setText("");
        }else if(TabRawat.getSelectedIndex()==7){
            KodeFarmasi.setText("");
            NamaFarmasi.setText("");
            LongitudeFarmasi.setText("");
            AltitudeFarmasi.setText("");
            LatitudeFarmasi.setText("");
            KodeDepartemenFarmasi.setText("");
            NamaDepartemenFarmasi.setText("");
            IDOrganisasiFarmasi.setText("");
            btnFarmasi.requestFocus();
        }        
    }

    private void getData() {
       if(tbJnsPerawatan.getSelectedRow()!= -1){
           KodePoli.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
           NamaPoli.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
           Longitude.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
           Latitude.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
           Altitude.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString());
           KodeDepartemen.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),6).toString());
           NamaDepartemen.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
           IDOrganisasi.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString());
        }
    }
    
    private void getDataKamar() {
       if(tbLokasiKamar.getSelectedRow()!= -1){
           KodeKamar.setText(tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),0).toString());
           NamaKamar.setText(tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),1).toString());
           LongitudeKamar.setText(tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),3).toString());
           LatitudeKamar.setText(tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),4).toString());
           AltitudeKamar.setText(tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),5).toString());
           KodeDepartemenKamar.setText(tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),6).toString());
           NamaDepartemenKamar.setText(tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),7).toString());
           IDOrganisasiKamar.setText(tbLokasiKamar.getValueAt(tbLokasiKamar.getSelectedRow(),8).toString());
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getsatu_sehat_mapping_lokasi());
        BtnHapus.setEnabled(akses.getsatu_sehat_mapping_lokasi());
        BtnEdit.setEnabled(akses.getsatu_sehat_mapping_lokasi());
        BtnPrint.setEnabled(akses.getsatu_sehat_mapping_lokasi());
    }
    
    private void tampilkamar() {
        Valid.tabelKosong(tabModeKamar);
        try{
            ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_lokasi_ranap.kd_kamar,bangsal.nm_bangsal,satu_sehat_mapping_lokasi_ranap.id_lokasi_satusehat,"+
                   "satu_sehat_mapping_lokasi_ranap.longitude,satu_sehat_mapping_lokasi_ranap.latitude,satu_sehat_mapping_lokasi_ranap.altittude,"+
                   "satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_lokasi_ranap.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_lokasi_ranap inner join kamar on satu_sehat_mapping_lokasi_ranap.kd_kamar=kamar.kd_kamar "+
                   "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal inner join satu_sehat_mapping_departemen "+
                   "on satu_sehat_mapping_lokasi_ranap.id_organisasi_satusehat=satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id "+
                   (TCari.getText().equals("")?"":"where satu_sehat_mapping_departemen.dep_id like ? or departemen.nama like ? or "+
                   "bangsal.nm_bangsal like ? or satu_sehat_mapping_lokasi_ranap.kd_kamar like ?")+" order by departemen.nama");
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeKamar.addRow(new Object[]{
                        rs.getString("kd_kamar"),rs.getString("nm_bangsal"),rs.getString("id_lokasi_satusehat"),rs.getString("longitude"),rs.getString("latitude"),
                        rs.getString("altittude"),rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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
        LCount.setText(""+tabModeKamar.getRowCount());
    }
    
    private void tampilruangok() {
        Valid.tabelKosong(tabModeRuangOK);
        try{
            ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_lokasi_ruangok.id_lokasi_satusehat,satu_sehat_mapping_lokasi_ruangok.longitude,"+
                   "satu_sehat_mapping_lokasi_ruangok.latitude,satu_sehat_mapping_lokasi_ruangok.altittude,"+
                   "satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_lokasi_ruangok.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_lokasi_ruangok inner join satu_sehat_mapping_departemen "+
                   "on satu_sehat_mapping_lokasi_ruangok.id_organisasi_satusehat=satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRuangOK.addRow(new Object[]{
                        rs.getString("id_lokasi_satusehat"),rs.getString("longitude"),rs.getString("latitude"),
                        rs.getString("altittude"),rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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
        LCount.setText(""+tabModeRuangOK.getRowCount());
    }
    
    private void tampilruanglabpk() {
        Valid.tabelKosong(tabModeRuangLabPK);
        try{
            ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_lokasi_ruanglab.id_lokasi_satusehat,satu_sehat_mapping_lokasi_ruanglab.longitude,"+
                   "satu_sehat_mapping_lokasi_ruanglab.latitude,satu_sehat_mapping_lokasi_ruanglab.altittude,"+
                   "satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_lokasi_ruanglab.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_lokasi_ruanglab inner join satu_sehat_mapping_departemen "+
                   "on satu_sehat_mapping_lokasi_ruanglab.id_organisasi_satusehat=satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRuangLabPK.addRow(new Object[]{
                        rs.getString("id_lokasi_satusehat"),rs.getString("longitude"),rs.getString("latitude"),
                        rs.getString("altittude"),rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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
        LCount.setText(""+tabModeRuangLabPK.getRowCount());
    }
    
    private void tampilruanglabpa() {
        Valid.tabelKosong(tabModeRuangLabPA);
        try{
            ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_lokasi_ruanglabpa.id_lokasi_satusehat,satu_sehat_mapping_lokasi_ruanglabpa.longitude,"+
                   "satu_sehat_mapping_lokasi_ruanglabpa.latitude,satu_sehat_mapping_lokasi_ruanglabpa.altittude,"+
                   "satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_lokasi_ruanglabpa.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_lokasi_ruanglabpa inner join satu_sehat_mapping_departemen "+
                   "on satu_sehat_mapping_lokasi_ruanglabpa.id_organisasi_satusehat=satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRuangLabPA.addRow(new Object[]{
                        rs.getString("id_lokasi_satusehat"),rs.getString("longitude"),rs.getString("latitude"),
                        rs.getString("altittude"),rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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
        LCount.setText(""+tabModeRuangLabPA.getRowCount());
    }
    
    private void tampilruanglabmb() {
        Valid.tabelKosong(tabModeRuangLabMB);
        try{
            ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_lokasi_ruanglabmb.id_lokasi_satusehat,satu_sehat_mapping_lokasi_ruanglabmb.longitude,"+
                   "satu_sehat_mapping_lokasi_ruanglabmb.latitude,satu_sehat_mapping_lokasi_ruanglabmb.altittude,"+
                   "satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_lokasi_ruanglabmb.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_lokasi_ruanglabmb inner join satu_sehat_mapping_departemen "+
                   "on satu_sehat_mapping_lokasi_ruanglabmb.id_organisasi_satusehat=satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRuangLabMB.addRow(new Object[]{
                        rs.getString("id_lokasi_satusehat"),rs.getString("longitude"),rs.getString("latitude"),
                        rs.getString("altittude"),rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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
        LCount.setText(""+tabModeRuangLabMB.getRowCount());
    }
    
    private void tampilruangradiologi() {
        Valid.tabelKosong(tabModeRuangRadiologi);
        try{
            ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_lokasi_ruangrad.id_lokasi_satusehat,satu_sehat_mapping_lokasi_ruangrad.longitude,"+
                   "satu_sehat_mapping_lokasi_ruangrad.latitude,satu_sehat_mapping_lokasi_ruangrad.altittude,"+
                   "satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_lokasi_ruangrad.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_lokasi_ruangrad inner join satu_sehat_mapping_departemen "+
                   "on satu_sehat_mapping_lokasi_ruangrad.id_organisasi_satusehat=satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRuangRadiologi.addRow(new Object[]{
                        rs.getString("id_lokasi_satusehat"),rs.getString("longitude"),rs.getString("latitude"),
                        rs.getString("altittude"),rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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
        LCount.setText(""+tabModeRuangRadiologi.getRowCount());
    }
    
    private void tampilruangfarmasi() {
        Valid.tabelKosong(tabModeRuangFarmasi);
        try{
            ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_lokasi_depo_farmasi.kd_bangsal,bangsal.nm_bangsal,satu_sehat_mapping_lokasi_depo_farmasi.id_lokasi_satusehat,"+
                   "satu_sehat_mapping_lokasi_depo_farmasi.longitude,satu_sehat_mapping_lokasi_depo_farmasi.latitude,satu_sehat_mapping_lokasi_depo_farmasi.altittude,"+
                   "satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_lokasi_depo_farmasi.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_lokasi_depo_farmasi inner join bangsal on satu_sehat_mapping_lokasi_depo_farmasi.kd_bangsal=bangsal.kd_bangsal "+
                   "inner join satu_sehat_mapping_departemen on satu_sehat_mapping_lokasi_depo_farmasi.id_organisasi_satusehat=satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id "+
                   (TCari.getText().equals("")?"":"where satu_sehat_mapping_departemen.dep_id like ? or departemen.nama like ? or "+
                   "bangsal.nm_bangsal like ? or satu_sehat_mapping_lokasi_depo_farmasi.kd_bangsal like ?")+" order by departemen.nama");
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRuangFarmasi.addRow(new Object[]{
                        rs.getString("kd_bangsal"),rs.getString("nm_bangsal"),rs.getString("id_lokasi_satusehat"),rs.getString("longitude"),rs.getString("latitude"),
                        rs.getString("altittude"),rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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
        LCount.setText(""+tabModeRuangFarmasi.getRowCount());
    }
    
    private void getDataRuangOK() {
       if(tbLokasiRuangOK.getSelectedRow()!= -1){
           LongitudeRuangOK.setText(tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),1).toString());
           LatitudeRuangOK.setText(tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),2).toString());
           AltitudeRuangOK.setText(tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),3).toString());
           KodeDepartemenRuangOK.setText(tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),4).toString());
           NamaDepartemenRuangOK.setText(tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),5).toString());
           IDOrganisasiRuangOK.setText(tbLokasiRuangOK.getValueAt(tbLokasiRuangOK.getSelectedRow(),6).toString());
        }
    }
    
    private void getDataRuangLabPK() {
       if(tbLokasiRuangLabPK.getSelectedRow()!= -1){
           LongitudeRuangLabPK.setText(tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),1).toString());
           LatitudeRuangLabPK.setText(tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),2).toString());
           AltitudeRuangLabPK.setText(tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),3).toString());
           KodeDepartemenRuangLabPK.setText(tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),4).toString());
           NamaDepartemenRuangLabPK.setText(tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),5).toString());
           IDOrganisasiRuangLabPK.setText(tbLokasiRuangLabPK.getValueAt(tbLokasiRuangLabPK.getSelectedRow(),6).toString());
        }
    }
    
    private void getDataRuangLabPA() {
       if(tbLokasiRuangLabPA.getSelectedRow()!= -1){
           LongitudeRuangLabPA.setText(tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),1).toString());
           LatitudeRuangLabPA.setText(tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),2).toString());
           AltitudeRuangLabPA.setText(tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),3).toString());
           KodeDepartemenRuangLabPA.setText(tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),4).toString());
           NamaDepartemenRuangLabPA.setText(tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),5).toString());
           IDOrganisasiRuangLabPA.setText(tbLokasiRuangLabPA.getValueAt(tbLokasiRuangLabPA.getSelectedRow(),6).toString());
        }
    }
    
    private void getDataRuangLabMB() {
       if(tbLokasiRuangLabMB.getSelectedRow()!= -1){
           LongitudeRuangLabMB.setText(tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),1).toString());
           LatitudeRuangLabMB.setText(tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),2).toString());
           AltitudeRuangLabMB.setText(tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),3).toString());
           KodeDepartemenRuangLabMB.setText(tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),4).toString());
           NamaDepartemenRuangLabMB.setText(tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),5).toString());
           IDOrganisasiRuangLabMB.setText(tbLokasiRuangLabMB.getValueAt(tbLokasiRuangLabMB.getSelectedRow(),6).toString());
        }
    }
    
    private void getDataRuangRadiologi() {
       if(tbLokasiRuangRadiologi.getSelectedRow()!= -1){
           LongitudeRuangRadiologi.setText(tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),1).toString());
           LatitudeRuangRadiologi.setText(tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),2).toString());
           AltitudeRuangRadiologi.setText(tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),3).toString());
           KodeDepartemenRuangRadiologi.setText(tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),4).toString());
           NamaDepartemenRuangRadiologi.setText(tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),5).toString());
           IDOrganisasiRuangRadiologi.setText(tbLokasiRuangRadiologi.getValueAt(tbLokasiRuangRadiologi.getSelectedRow(),6).toString());
        }
    }
    
    private void getDataRuangFarmasi() {
       if(tbLokasiFarmasi.getSelectedRow()!= -1){
           KodeFarmasi.setText(tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),0).toString());
           NamaFarmasi.setText(tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),1).toString());
           LongitudeFarmasi.setText(tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),3).toString());
           LatitudeFarmasi.setText(tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),4).toString());
           AltitudeFarmasi.setText(tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),5).toString());
           KodeDepartemenFarmasi.setText(tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),6).toString());
           NamaDepartemenFarmasi.setText(tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),7).toString());
           IDOrganisasiFarmasi.setText(tbLokasiFarmasi.getValueAt(tbLokasiFarmasi.getSelectedRow(),8).toString());
        }
    }
}

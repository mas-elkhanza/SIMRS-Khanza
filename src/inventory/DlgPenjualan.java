package inventory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgPenjualan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeObatRacikan,tabModeDetailRacikan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayatobat Trackobat=new riwayatobat();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPenjualan carijual=new DlgCariPenjualan(null,false);
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private double ttl=0,ttlhpp=0,y=0,z=0,stokbarang=0,embalasen=0,tuslahn=0,bayar=0,total=0,ppn=0,besarppn=0,tagihanppn=0;;
    private int jml=0,i=0,row,kolom=0,reply,index;
    public DlgCariAturanPakai aturan_pakai=new DlgCariAturanPakai(null,false);
    private String verifikasi_penjualan_di_kasir="",Penjualan_Obat="",HPP_Obat_Jual_Bebas="",Persediaan_Obat_Jual_Bebas="",
            status="Belum Dibayar",pilihanetiket="",hppfarmasi="",kode_akun_bayar="";
    private PreparedStatement ps,psstok,pscaribatch;
    private ResultSet rs,rsstok;
    private String[] no,kodebarang,kandungan,namabarang,kategori,satuan,aturanpakai,nobatch,nofaktur,kadaluarsa;
    private double[] harga,hbeli,jumlah,kps,subtotal,diskon,besardiskon,totaljual,tambahan,embalase,tuslah,stok;
    private WarnaTable2 warna=new WarnaTable2();
    private String notapenjualan="No",aktifkanbatch="no";
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private DlgCariMetodeRacik metoderacik=new DlgCariMetodeRacik(null,false);
    private boolean sukses=true;
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgPenjualan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        try {
            aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
            if(aktifkanbatch.equals("no")){
                ppStok.setVisible(true);
            }else{
                ppStok.setVisible(false);
            }
        } catch (Exception e) {
            System.out.println("E : "+e);
            aktifkanbatch = "no";
            ppStok.setVisible(true);
        }
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "Jml","Kode Barang","Nama Barang","Kategori","Satuan","Harga(Rp)",
                "Subtotal(Rp)","Ptg(%)","Ptg(Rp)","Tmb(Rp)","Emb(Rp)",
                "Tsl(Rp)","Aturan Pakai","Total(Rp)","Stok","H Beli","No.Batch",
                "No.Faktur","Kadaluarsa"
        }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==16)||(colIndex==0)||(colIndex==7)||(colIndex==8)||(colIndex==9)||(colIndex==10)||(colIndex==11)||(colIndex==12)||(colIndex==16)||(colIndex==17)) {
                    a=true;
                }
                return a;
            }
            
            Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.String.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
               return types [columnIndex];
            }
        };
        tbObat.setModel(tabMode);

        tbObat.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(42);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(45);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(60);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(40);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setPreferredWidth(70);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(65);
            }
        }
        warna.kolom=0;
        tbObat.setDefaultRenderer(Object.class,warna);

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
        
        tabModeDetailRacikan=new DefaultTableModel(null,new Object[]{
                "No","Kode Barang","Nama Barang","Kategori","Satuan","Harga(Rp)","Kps",
                "Knd","Jml","Subtotal(Rp)","Ptg(%)","Ptg(Rp)","Tmb(Rp)",
                "Emb(Rp)","Tsl(Rp)","Total(Rp)","Stok","H Beli",
                "No.Batch","No.Faktur","Kadaluarsa"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==18)||(colIndex==7)||(colIndex==8)||(colIndex==10)||(colIndex==11)||(colIndex==12)||(colIndex==13)||(colIndex==14)||(colIndex==18)||(colIndex==19)) {
                    a=true;
                }
                return a;
            }
            
            Class[] types = new Class[] {
                java.lang.Object.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.String.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
               return types [columnIndex];
            }
        };
        tbDetailObatRacikan.setModel(tabModeDetailRacikan);

        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(42);
            }else if(i==7){
                column.setPreferredWidth(45);
            }else if(i==8){
                column.setPreferredWidth(42);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(45);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(60);
            }else if(i==13){
                column.setPreferredWidth(60);
            }else if(i==14){
                column.setPreferredWidth(60);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(40);
            }else if(i==17){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(65);
            }
        }
        warna3.kolom=7;
        tbDetailObatRacikan.setDefaultRenderer(Object.class,warna3);
        
        NoNota.setDocument(new batasInput((byte)20).getKata(NoNota));
        kdmem.setDocument(new batasInput((byte)15).getKata(kdmem));
        kdgudang.setDocument(new batasInput((byte)5).getKata(kdgudang));
        catatan.setDocument(new batasInput((byte)40).getKata(catatan));
        Bayar.setDocument(new batasInput((byte)14).getOnlyAngka(Bayar));   
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
        
        Bayar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void removeUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void changedUpdate(DocumentEvent e) {isKembali();}
        });
        
        carijual.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                autoNomor();
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
        
        carijual.pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPenjualan")){
                    if(carijual.pasien.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(carijual.pasien.getTable().getValueAt(carijual.pasien.getTable().getSelectedRow(),1).toString());
                        nmmem.setText(carijual.pasien.getTable().getValueAt(carijual.pasien.getTable().getSelectedRow(),2).toString());
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
        
        carijual.pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPenjualan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        carijual.pasien.dispose();
                        carijual.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });       
        
        carijual.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPenjualan")){
                    if(carijual.petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(carijual.petugas.getTable().getValueAt(carijual.petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(carijual.petugas.getTable().getValueAt(carijual.petugas.getTable().getSelectedRow(),1).toString());
                    }    
                    kdptg.requestFocus();
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPenjualan")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                   
                        kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                        tampil();
                    } 
                    kdgudang.requestFocus();
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
        
        aturan_pakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturan_pakai.getTable().getSelectedRow()!= -1){ 
                    if(TabRawat.getSelectedIndex()==0){
                        tbObat.setValueAt(aturan_pakai.getTable().getValueAt(aturan_pakai.getTable().getSelectedRow(),0).toString(),tbObat.getSelectedRow(),12);
                        tbObat.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==1){
                        tbObatRacikan.setValueAt(aturan_pakai.getTable().getValueAt(aturan_pakai.getTable().getSelectedRow(),0).toString(),tbObatRacikan.getSelectedRow(),5);
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
        
        TCari.requestFocus();
        
        try {
            ps=koneksi.prepareStatement("select cetaknotasimpanpenjualan,verifikasi_penjualan_di_kasir from set_nota");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    notapenjualan=rs.getString("cetaknotasimpanpenjualan");
                    verifikasi_penjualan_di_kasir=rs.getString("verifikasi_penjualan_di_kasir");
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
            
            if(notapenjualan.equals("")){
                notapenjualan="No";
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e); 
        }
        
        try {
            ps=koneksi.prepareStatement("select Penjualan_Obat,HPP_Obat_Jual_Bebas,Persediaan_Obat_Jual_Bebas from set_akun");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    Penjualan_Obat=rs.getString("Penjualan_Obat");
                    HPP_Obat_Jual_Bebas=rs.getString("HPP_Obat_Jual_Bebas");
                    Persediaan_Obat_Jual_Bebas=rs.getString("Persediaan_Obat_Jual_Bebas");
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
            ps=koneksi.prepareStatement("select embalase_per_obat,tuslah_per_obat from set_embalase");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    embalasen=rs.getDouble("embalase_per_obat");
                    tuslahn=rs.getDouble("tuslah_per_obat");
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
            hppfarmasi=koneksiDB.HPPFARMASI();
        } catch (Exception e) {
            hppfarmasi="dasar";
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

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        ppStok1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnNota = new widget.Button();
        BtnSimpan = new widget.Button();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnTambah = new widget.Button();
        BtnTambah2 = new widget.Button();
        BtnHapus = new widget.Button();
        label22 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        LTotal = new widget.Label();
        label19 = new widget.Label();
        Bayar = new widget.TextBox();
        label20 = new widget.Label();
        LKembali = new widget.Label();
        jLabel10 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        TagihanPPn = new widget.Label();
        BesarPPN = new widget.TextBox();
        Persenppn = new widget.TextBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoNota = new widget.TextBox();
        label14 = new widget.Label();
        kdmem = new widget.TextBox();
        kdptg = new widget.TextBox();
        label16 = new widget.Label();
        nmmem = new widget.TextBox();
        nmptg = new widget.TextBox();
        BtnMem = new widget.Button();
        BtnPtg = new widget.Button();
        Jenisjual = new widget.ComboBox();
        label18 = new widget.Label();
        catatan = new widget.TextBox();
        label12 = new widget.Label();
        label11 = new widget.Label();
        Tgl = new widget.Tanggal();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll2 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(180, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok.setBackground(new java.awt.Color(255, 255, 254));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(50, 50, 50));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(180, 25));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        ppStok1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok1.setForeground(new java.awt.Color(50, 50, 50));
        ppStok1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok1.setText("Cek Stok Lokasi");
        ppStok1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok1.setName("ppStok1"); // NOI18N
        ppStok1.setPreferredSize(new java.awt.Dimension(180, 25));
        ppStok1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStok1ActionPerformed(evt);
            }
        });
        Popup.add(ppStok1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Penjualan Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 132));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnNota.setMnemonic('S');
        BtnNota.setText("Nota");
        BtnNota.setToolTipText("Alt+S");
        BtnNota.setName("BtnNota"); // NOI18N
        BtnNota.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNotaActionPerformed(evt);
            }
        });
        BtnNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNotaKeyPressed(evt);
            }
        });
        panelisi1.add(BtnNota);

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
        panelisi1.add(BtnSimpan);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(220, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        BtnTambah2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnTambah2.setMnemonic('3');
        BtnTambah2.setToolTipText("Alt+3");
        BtnTambah2.setName("BtnTambah2"); // NOI18N
        BtnTambah2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah2ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnTambah2);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelisi1.add(BtnHapus);

        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(15, 23));
        panelisi1.add(label22);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 54));
        panelisi5.setLayout(null);

        label10.setText("Grand Total :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi5.add(label10);
        label10.setBounds(0, 10, 90, 23);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(200, 23));
        panelisi5.add(LTotal);
        LTotal.setBounds(94, 10, 160, 23);

        label19.setText("Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi5.add(label19);
        label19.setBounds(256, 40, 80, 23);

        Bayar.setText("0");
        Bayar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Bayar.setName("Bayar"); // NOI18N
        Bayar.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi5.add(Bayar);
        Bayar.setBounds(340, 40, 200, 23);

        label20.setText("Kembali :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(130, 23));
        panelisi5.add(label20);
        label20.setBounds(556, 40, 80, 23);

        LKembali.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LKembali.setText("0");
        LKembali.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LKembali.setName("LKembali"); // NOI18N
        LKembali.setPreferredSize(new java.awt.Dimension(120, 23));
        panelisi5.add(LKembali);
        LKembali.setBounds(640, 40, 170, 23);

        jLabel10.setText("Akun Bayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi5.add(jLabel10);
        jLabel10.setBounds(256, 10, 80, 23);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AkunBayarItemStateChanged(evt);
            }
        });
        panelisi5.add(AkunBayar);
        AkunBayar.setBounds(340, 10, 200, 23);

        jLabel11.setText("PPN(%) :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi5.add(jLabel11);
        jLabel11.setBounds(556, 10, 80, 23);

        jLabel12.setText("Tagihan + PPN :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi5.add(jLabel12);
        jLabel12.setBounds(0, 40, 90, 23);

        TagihanPPn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TagihanPPn.setText("0");
        TagihanPPn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TagihanPPn.setName("TagihanPPn"); // NOI18N
        TagihanPPn.setPreferredSize(new java.awt.Dimension(200, 23));
        panelisi5.add(TagihanPPn);
        TagihanPPn.setBounds(94, 40, 160, 23);

        BesarPPN.setText("0");
        BesarPPN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BesarPPN.setName("BesarPPN"); // NOI18N
        BesarPPN.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi5.add(BesarPPN);
        BesarPPN.setBounds(682, 10, 120, 23);

        Persenppn.setText("0");
        Persenppn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Persenppn.setName("Persenppn"); // NOI18N
        Persenppn.setPreferredSize(new java.awt.Dimension(150, 23));
        Persenppn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PersenppnKeyPressed(evt);
            }
        });
        panelisi5.add(Persenppn);
        Persenppn.setBounds(640, 10, 40, 23);

        jPanel1.add(panelisi5, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(89, 104));
        panelisi3.setLayout(null);

        label15.setText("No.Nota :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoNota.setName("NoNota"); // NOI18N
        NoNota.setPreferredSize(new java.awt.Dimension(207, 23));
        NoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoNotaKeyPressed(evt);
            }
        });
        panelisi3.add(NoNota);
        NoNota.setBounds(74, 10, 260, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label14);
        label14.setBounds(376, 40, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(439, 10, 100, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(439, 40, 100, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(376, 10, 60, 23);

        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(540, 10, 232, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(540, 40, 232, 23);

        BtnMem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMem.setMnemonic('1');
        BtnMem.setToolTipText("Alt+1");
        BtnMem.setName("BtnMem"); // NOI18N
        BtnMem.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMemActionPerformed(evt);
            }
        });
        panelisi3.add(BtnMem);
        BtnMem.setBounds(774, 10, 28, 23);

        BtnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg.setMnemonic('2');
        BtnPtg.setToolTipText("Alt+2");
        BtnPtg.setName("BtnPtg"); // NOI18N
        BtnPtg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtgActionPerformed(evt);
            }
        });
        panelisi3.add(BtnPtg);
        BtnPtg.setBounds(774, 40, 28, 23);

        Jenisjual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jual Bebas", "Karyawan", "Beli Luar", "Rawat Jalan", "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP" }));
        Jenisjual.setName("Jenisjual"); // NOI18N
        Jenisjual.setPreferredSize(new java.awt.Dimension(40, 23));
        Jenisjual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisjualItemStateChanged(evt);
            }
        });
        Jenisjual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisjualKeyPressed(evt);
            }
        });
        panelisi3.add(Jenisjual);
        Jenisjual.setBounds(229, 70, 130, 23);

        label18.setText("Catatan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 70, 23);

        catatan.setName("catatan"); // NOI18N
        catatan.setPreferredSize(new java.awt.Dimension(207, 23));
        catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catatanKeyPressed(evt);
            }
        });
        panelisi3.add(catatan);
        catatan.setBounds(74, 40, 285, 23);

        label12.setText("Jns.Jual :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label12);
        label12.setBounds(170, 70, 55, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 70, 70, 23);

        Tgl.setDisplayFormat("dd-MM-yyyy");
        Tgl.setName("Tgl"); // NOI18N
        Tgl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglItemStateChanged(evt);
            }
        });
        Tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKeyPressed(evt);
            }
        });
        panelisi3.add(Tgl);
        Tgl.setBounds(74, 70, 95, 23);

        label21.setText("Lokasi :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label21);
        label21.setBounds(376, 70, 60, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(439, 70, 100, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(540, 70, 232, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisi3.add(BtnGudang);
        BtnGudang.setBounds(774, 70, 28, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbObat);

        TabRawat.addTab("Umum", scrollPane1);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);
        Scroll2.setPreferredSize(new java.awt.Dimension(454, 90));

        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        tbObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatRacikanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.PAGE_START);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbDetailObatRacikan.setAutoCreateRowSorter(true);
        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        tbDetailObatRacikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailObatRacikanMouseClicked(evt);
            }
        });
        tbDetailObatRacikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDetailObatRacikanPropertyChange(evt);
            }
        });
        tbDetailObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailObatRacikanKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbDetailObatRacikan);

        jPanel3.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getData();                     
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
                i=tbObat.getSelectedColumn();
                if(i==10){
                    try {
                        if(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().equals("0")||tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().equals("")||tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().equals("0.0")||tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().equals("0,0")) {
                            tbObat.setValueAt(embalasen,tbObat.getSelectedRow(),10);
                        }
                    } catch (Exception e) {
                        tbObat.setValueAt(0,tbObat.getSelectedRow(),10);
                    }
                }else if(i==11){
                    try {
                        if(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().equals("0")||tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().equals("")||tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().equals("0.0")||tbObat.getValueAt(tbObat.getSelectedRow(),11).toString().equals("0,0")) {
                            tbObat.setValueAt(tuslahn,tbObat.getSelectedRow(),11);
                        }
                    } catch (Exception e) {
                        tbObat.setValueAt(0,tbObat.getSelectedRow(),11);
                    }
                }
            }
            
            if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)){
                try {                                     
                    getData();           
                } catch (java.lang.NullPointerException e) {
                }                
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){   
                if(tbObat.getSelectedColumn()==12){
                    //System.out.println("inventory.DlgPenjualan.tbDokterKeyPressed()");
                    aturan_pakai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aturan_pakai.setLocationRelativeTo(internalFrame1);
                    aturan_pakai.setVisible(true);
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    switch (tbObat.getSelectedColumn()) {
                        case 0:
                            tbObat.setValueAt("", tbObat.getSelectedRow(),0);
                            break;
                        case 7:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),7);
                            break;
                        case 8:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),8);
                            break;
                        case 9:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),9);
                            break;
                        case 10:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),10);
                            break;
                        case 11:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),11);
                            break;
                        case 12:
                            tbObat.setValueAt("", tbObat.getSelectedRow(),12);
                            break;
                        case 16:
                            tbObat.setValueAt("", tbObat.getSelectedRow(),16);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                } 
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_BACK_SPACE){
                try {
                    switch (tbObat.getSelectedColumn()) {
                        case 0:
                            tbObat.setValueAt("", tbObat.getSelectedRow(),0);
                            break;
                        case 7:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),7);
                            break;
                        case 8:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),8);
                            break;
                        case 9:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),9);
                            break;
                        case 10:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),10);
                            break;
                        case 11:
                            tbObat.setValueAt(0, tbObat.getSelectedRow(),11);
                            break;
                        case 12:
                            tbObat.setValueAt("", tbObat.getSelectedRow(),12);
                            break;
                        case 16:
                            tbObat.setValueAt("", tbObat.getSelectedRow(),16);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                }     
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        carijual.emptTeks();  
        carijual.isCek();
        carijual.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carijual.setLocationRelativeTo(internalFrame1);
        carijual.setAlwaysOnTop(false);
        carijual.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        carijual.pasien.dispose();
        carijual.petugas.dispose();
        carijual.barang.dispose();
        carijual.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnCari,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(aktifkanbatch.equals("yes")){
            row=0;
            jml=tbObat.getRowCount();
            for(i=0;i<jml;i++){
                if((Valid.SetAngka(tbObat.getValueAt(i,0).toString())>0)&&(tbObat.getValueAt(i,16).toString().trim().equals("")||tbObat.getValueAt(i,17).toString().trim().equals(""))){
                    row++;
                }
            }
            
            jml=tbDetailObatRacikan.getRowCount();
            for(i=0;i<jml;i++){
                if((Valid.SetAngka(tbObat.getValueAt(i,8).toString())>0)&&(tbObat.getValueAt(i,18).toString().trim().equals("")||tbObat.getValueAt(i,19).toString().trim().equals(""))){
                    row++;
                }
            }
        }
        
        if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Nota");
        }else if(aktifkanbatch.equals("yes")&&(row>0)){
            Valid.textKosong(TCari,"No.Batch/No.Faktur");
        }else if(nmmem.getText().trim().equals("")||kdmem.getText().trim().equals("")){
            Valid.textKosong(kdmem,"Pasien");
        }else if(nmptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")||kdgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Gudang");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            tbObat.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan penjualan...!!!!");
            tbObat.requestFocus();
        }else{
            if(verifikasi_penjualan_di_kasir.equals("No")){
                status="Sudah Dibayar";
            }else{
                status="Belum Dibayar";
            }
            reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.AutoComitFalse();
                sukses=true;
                kode_akun_bayar="";
                try {
                    myObj = new FileReader("./cache/akunbayar.iyem");
                    root = mapper.readTree(myObj);
                    response = root.path("akunbayar");
                    if(response.isArray()){
                       for(JsonNode list:response){
                           if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                                kode_akun_bayar=list.path("KodeRek").asText();  
                           }
                       }
                    }
                    myObj.close();
                } catch (Exception e) {
                    sukses=false;
                } 
                
                if(Sequel.menyimpantf2("penjualan","?,?,?,?,?,?,?,?,?,?,?,?","No.Nota",12,new String[]{
                        NoNota.getText(),Valid.SetTgl(Tgl.getSelectedItem()+""),kdptg.getText(),kdmem.getText(),nmmem.getText(),
                        catatan.getText(),Jenisjual.getSelectedItem().toString(),Double.toString(besarppn),status,kdgudang.getText(),
                        kode_akun_bayar,AkunBayar.getSelectedItem().toString()}
                   )==true){
                        simpan();
                }else{
                    autoNomor();
                    if(Sequel.menyimpantf2("penjualan","?,?,?,?,?,?,?,?,?,?,?,?","No.Nota",12,new String[]{
                        NoNota.getText(),Valid.SetTgl(Tgl.getSelectedItem()+""),kdptg.getText(),kdmem.getText(),nmmem.getText(),
                        catatan.getText(),Jenisjual.getSelectedItem().toString(),Double.toString(besarppn),status,kdgudang.getText(),
                        kode_akun_bayar,AkunBayar.getSelectedItem().toString()}
                       )==true){
                            simpan();
                    }else{
                        sukses=false;
                        autoNomor();
                    } 
                }
                
                if(sukses==true){
                    if(notapenjualan.equals("Yes")){
                        BtnNotaActionPerformed(null);
                    }
                    Sequel.Commit();
                    Valid.tabelKosong(tabModeObatRacikan);

                    for(int r=0;r<tabMode.getRowCount();r++){ 
                        try {
                            if(Valid.SetAngka(tabMode.getValueAt(r,0).toString())>0){
                                tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(r,14).toString())-Double.parseDouble(tabMode.getValueAt(r,0).toString()),r,14);
                            }
                        } catch (Exception e) {
                        }

                        tabMode.setValueAt("",r,0);
                        tabMode.setValueAt(0,r,6);
                        tabMode.setValueAt(0,r,7);
                        tabMode.setValueAt(0,r,8);  
                        tabMode.setValueAt(0,r,9);  
                        tabMode.setValueAt(0,r,10); 
                        tabMode.setValueAt(0,r,11); 
                        tabMode.setValueAt("",r,12); 
                        tabMode.setValueAt(0,r,13);  
                        tabMode.setValueAt("",r,16); 
                        tabMode.setValueAt("",r,17); 
                        tabMode.setValueAt("",r,18);
                    }

                    for(int r=0;r<tbDetailObatRacikan.getRowCount();r++){ 
                        try {
                            if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(r,8).toString())>0){
                                tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(r,16).toString())-Double.parseDouble(tbDetailObatRacikan.getValueAt(r,8).toString()),r,16);
                            }
                        } catch (Exception e) {
                        }

                        tbDetailObatRacikan.setValueAt("",r,7);
                        tbDetailObatRacikan.setValueAt(0,r,8);
                        tbDetailObatRacikan.setValueAt(0,r,9);
                        tbDetailObatRacikan.setValueAt(0,r,10);
                        tbDetailObatRacikan.setValueAt(0,r,11);  
                        tbDetailObatRacikan.setValueAt(0,r,12);  
                        tbDetailObatRacikan.setValueAt(0,r,13); 
                        tbDetailObatRacikan.setValueAt(0,r,14); 
                        tbDetailObatRacikan.setValueAt(0,r,15); 
                        tbDetailObatRacikan.setValueAt("",r,18); 
                        tbDetailObatRacikan.setValueAt("",r,19); 
                        tbDetailObatRacikan.setValueAt("",r,20); 
                    }
                    tagihanppn=0;
                    ttl=0;
                    ttlhpp=0;
                    bayar=0;
                    besarppn=0;
                    total=0;
                    ppn=0;     
                    LTotal.setText("0");
                    Bayar.setText("0");
                    
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    try {
                        pilihanetiket = (String)JOptionPane.showInputDialog(null,"Silahkan pilih cetak aturan pakai..!!","Cetak Aturan Pakai",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Cetak Aturan Pakai Model 1","Cetak Aturan Pakai Model 2","Cetak Aturan Pakai Model 3"},"Cetak Aturan Pakai Model 1");
                        switch (pilihanetiket) {
                            case "Cetak Aturan Pakai Model 1": 
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                if(Sequel.cariInteger(
                                        "select count(*) from detailjual where nota_jual=? and aturan_pakai<>''",NoNota.getText())>0){
                                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                                    Valid.MyReportqry("rptItemResepPenjualan.jasper","report","::[ Aturan Pakai Obat ]::",
                                        "select penjualan.nota_jual,penjualan.tgl_jual, "+
                                        "penjualan.no_rkm_medis,penjualan.nm_pasien,databarang.nama_brng,"+
                                        "detailjual.aturan_pakai,detailjual.jumlah,kodesatuan.satuan "+
                                        "from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                                        "inner join databarang on detailjual.kode_brng=databarang.kode_brng "+
                                        "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                                        "where penjualan.nota_jual='"+NoNota.getText()+"' and detailjual.aturan_pakai<>''",param);
                                }

                                if(Sequel.cariInteger(
                                        "select count(*) from obat_racikan_jual where nota_jual=? and aturan_pakai<>''",NoNota.getText())>0){
                                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                                    Valid.MyReportqry("rptItemResepPenjualan2.jasper","report","::[ Aturan Pakai Obat ]::",
                                        "select penjualan.nota_jual,penjualan.tgl_jual,metode_racik.nm_racik, "+
                                        "penjualan.no_rkm_medis,penjualan.nm_pasien,obat_racikan_jual.nama_racik,"+
                                        "obat_racikan_jual.aturan_pakai,obat_racikan_jual.jml_dr "+
                                        "from penjualan inner join obat_racikan_jual on penjualan.nota_jual=obat_racikan_jual.nota_jual "+
                                        "inner join metode_racik on obat_racikan_jual.kd_racik=metode_racik.kd_racik "+
                                        "where obat_racikan_jual.nota_jual='"+NoNota.getText()+"' and obat_racikan_jual.aturan_pakai<>''",param);
                                }                
                                this.setCursor(Cursor.getDefaultCursor());
                                break;
                            case "Cetak Aturan Pakai Model 2": 
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                if(Sequel.cariInteger(
                                        "select count(*) from detailjual where nota_jual=? and aturan_pakai<>''",NoNota.getText())>0){
                                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                                    Valid.MyReportqry("rptItemResepPenjualan3.jasper","report","::[ Aturan Pakai Obat ]::",
                                        "select penjualan.nota_jual,penjualan.tgl_jual, "+
                                        "penjualan.no_rkm_medis,penjualan.nm_pasien,databarang.nama_brng,"+
                                        "detailjual.aturan_pakai,detailjual.jumlah,kodesatuan.satuan,jenis.nama as jenis "+
                                        "from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                                        "inner join databarang on detailjual.kode_brng=databarang.kode_brng "+
                                        "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                                        "inner join jenis on databarang.kdjns=jenis.kdjns "+
                                        "where penjualan.nota_jual='"+NoNota.getText()+"' and detailjual.aturan_pakai<>''",param);
                                }

                                if(Sequel.cariInteger(
                                        "select count(*) from obat_racikan_jual where nota_jual=? and aturan_pakai<>''",NoNota.getText())>0){
                                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                                    Valid.MyReportqry("rptItemResepPenjualan2.jasper","report","::[ Aturan Pakai Obat ]::",
                                        "select penjualan.nota_jual,penjualan.tgl_jual,metode_racik.nm_racik, "+
                                        "penjualan.no_rkm_medis,penjualan.nm_pasien,obat_racikan_jual.nama_racik,"+
                                        "obat_racikan_jual.aturan_pakai,obat_racikan_jual.jml_dr "+
                                        "from penjualan inner join obat_racikan_jual on penjualan.nota_jual=obat_racikan_jual.nota_jual "+
                                        "inner join metode_racik on obat_racikan_jual.kd_racik=metode_racik.kd_racik "+
                                        "where obat_racikan_jual.nota_jual='"+NoNota.getText()+"' and obat_racikan_jual.aturan_pakai<>''",param);
                                }                
                                this.setCursor(Cursor.getDefaultCursor());
                                break;
                            case "Cetak Aturan Pakai Model 3": 
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                if(Sequel.cariInteger(
                                        "select count(*) from detailjual where nota_jual=? and aturan_pakai<>''",NoNota.getText())>0){
                                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                                    Valid.MyReportqry("rptItemResepPenjualan5.jasper","report","::[ Aturan Pakai Obat ]::",
                                        "select penjualan.nota_jual,penjualan.tgl_jual, "+
                                        "penjualan.no_rkm_medis,penjualan.nm_pasien,databarang.nama_brng,"+
                                        "detailjual.aturan_pakai,detailjual.jumlah,kodesatuan.satuan "+
                                        "from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                                        "inner join databarang on detailjual.kode_brng=databarang.kode_brng "+
                                        "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "+
                                        "where penjualan.nota_jual='"+NoNota.getText()+"' and detailjual.aturan_pakai<>''",param);
                                }

                                if(Sequel.cariInteger(
                                        "select count(*) from obat_racikan_jual where nota_jual=? and aturan_pakai<>''",NoNota.getText())>0){
                                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                                    Valid.MyReportqry("rptItemResepPenjualan6.jasper","report","::[ Aturan Pakai Obat ]::",
                                        "select penjualan.nota_jual,penjualan.tgl_jual,metode_racik.nm_racik, "+
                                        "penjualan.no_rkm_medis,penjualan.nm_pasien,obat_racikan_jual.nama_racik,"+
                                        "obat_racikan_jual.aturan_pakai,obat_racikan_jual.jml_dr "+
                                        "from penjualan inner join obat_racikan_jual on penjualan.nota_jual=obat_racikan_jual.nota_jual "+
                                        "inner join metode_racik on obat_racikan_jual.kd_racik=metode_racik.kd_racik "+
                                        "where obat_racikan_jual.nota_jual='"+NoNota.getText()+"' and obat_racikan_jual.aturan_pakai<>''",param);
                                }                
                                this.setCursor(Cursor.getDefaultCursor());
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                autoNomor();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           Valid.pindah(evt,kdgudang,BtnCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoNota.getText().trim().equals("")){
            Valid.textKosong(NoNota,"No.Nota");
        }else if(nmmem.getText().trim().equals("")||kdmem.getText().trim().equals("")){
            Valid.textKosong(kdmem,"Pasien");
        }else if(nmptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            tbObat.requestFocus();
        }else if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan masukkan penjualan...!!!!");
            tbObat.requestFocus();
        }else {
            Sequel.queryu("truncate table temporary");
            for(i=0;i<tabMode.getRowCount();i++){  
                try {
                    if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                        Sequel.menyimpan2("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            "0",tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,2).toString(),tabMode.getValueAt(i,3).toString(),
                            tabMode.getValueAt(i,4).toString(),tabMode.getValueAt(i,5).toString(),tabMode.getValueAt(i,6).toString(),tabMode.getValueAt(i,8).toString(),
                            tabMode.getValueAt(i,9).toString(),tabMode.getValueAt(i,10).toString(),tabMode.getValueAt(i,11).toString(),tabMode.getValueAt(i,12).toString(),
                            tabMode.getValueAt(i,13).toString(),"","","","","","","","","","","","","","","","","","","","","","","",""
                        });
                    }
                } catch (Exception e) {
                }                
            }
            
            for(i=0;i<tbObatRacikan.getRowCount();i++){  
                try {
                    if(Valid.SetAngka(tbObatRacikan.getValueAt(i,4).toString())>0){
                        Sequel.menyimpan2("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            "0",tbObatRacikan.getValueAt(i,4).toString(),"",tbObatRacikan.getValueAt(i,0).toString()+". "+tbObatRacikan.getValueAt(i,1).toString(),"",
                            tbObatRacikan.getValueAt(i,3).toString(),"","","","","","",tbObatRacikan.getValueAt(i,5).toString(),"0","","","","","","","","","","","",
                            "","","","","","","","","","","","",""
                        });
                    }
                } catch (Exception e) {
                }                
            }
            
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){  
                try {
                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,8).toString())>0){
                        Sequel.menyimpan2("temporary","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            "0","&nbsp;&nbsp;&nbsp;&nbsp;"+tbDetailObatRacikan.getValueAt(i,8).toString(),tbDetailObatRacikan.getValueAt(i,1).toString(),
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+tbDetailObatRacikan.getValueAt(i,2).toString(),tbDetailObatRacikan.getValueAt(i,3).toString(),
                            tbDetailObatRacikan.getValueAt(i,4).toString(),tbDetailObatRacikan.getValueAt(i,5).toString(),tbDetailObatRacikan.getValueAt(i,9).toString(),
                            tbDetailObatRacikan.getValueAt(i,11).toString(),tbDetailObatRacikan.getValueAt(i,12).toString(),tbDetailObatRacikan.getValueAt(i,13).toString(),
                            tbDetailObatRacikan.getValueAt(i,14).toString(),tbDetailObatRacikan.getValueAt(i,15).toString(),"","","","","","","","","","","",
                            "","","","","","","","","","","","",""
                        });
                    }
                } catch (Exception e) {
                }                
            }
            
            Valid.panggilUrl("billing/NotaApotek.php?nonota="+NoNota.getText()+"&besarppn="+besarppn+"&bayar="+Bayar.getText()+"&tanggal="+Valid.SetTgl(Tgl.getSelectedItem()+"")+"&catatan="+catatan.getText().replaceAll(" ","_")+"&petugas="+nmptg.getText().replaceAll(" ","_")+"&pasien="+nmmem.getText().replaceAll(" ","_")+"&norm="+kdmem.getText().replaceAll(" ","_"));
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnNotaActionPerformed

    private void BtnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNotaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnNotaKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari1ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObat.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
    if(TabRawat.getSelectedIndex()==0){
        tampil();
    }else if(TabRawat.getSelectedIndex()==1){
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
                    tampildetailracikanobat();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih racikan..!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan masukkan racikan..!!");
        }
    }
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, Bayar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void NoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoNotaKeyPressed
        Valid.pindah(evt,TCari, Tgl);
}//GEN-LAST:event_NoNotaKeyPressed

private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
                break;
            case KeyEvent.VK_PAGE_UP:
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
                Tgl.requestFocus();
                break;
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
                catatan.requestFocus();
                break;
            case KeyEvent.VK_UP:
                BtnMemActionPerformed(null);
                break;
            default:
                break;
        }
}//GEN-LAST:event_kdmemKeyPressed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
                break;
            case KeyEvent.VK_PAGE_UP:
                Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
                Jenisjual.requestFocus();
                break;
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
                TCari.requestFocus();
                break;
            case KeyEvent.VK_UP:
                BtnPtgActionPerformed(null);
                break;
            default:
                break;
        }
}//GEN-LAST:event_kdptgKeyPressed

private void BtnMemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMemActionPerformed
        akses.setform("DlgPenjualan");
        carijual.pasien.isCek();
        carijual.pasien.emptTeks();
        carijual.pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carijual.pasien.setLocationRelativeTo(internalFrame1);
        carijual.pasien.setAlwaysOnTop(false);
        carijual.pasien.setVisible(true);
}//GEN-LAST:event_BtnMemActionPerformed

private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
        akses.setform("DlgPenjualan");
        carijual.petugas.emptTeks();
        carijual.petugas.isCek();
        carijual.petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carijual.petugas.setLocationRelativeTo(internalFrame1);
        carijual.petugas.setAlwaysOnTop(false);
        carijual.petugas.setVisible(true);
}//GEN-LAST:event_BtnPtgActionPerformed

private void JenisjualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisjualItemStateChanged
       BtnCari1ActionPerformed(null);
}//GEN-LAST:event_JenisjualItemStateChanged

private void JenisjualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisjualKeyPressed
        Valid.pindah(evt, catatan, kdptg);
}//GEN-LAST:event_JenisjualKeyPressed

private void catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catatanKeyPressed
        Valid.pindah(evt, kdmem, Jenisjual);
}//GEN-LAST:event_catatanKeyPressed

private void TglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKeyPressed
        Valid.pindah(evt,NoNota,kdmem);
}//GEN-LAST:event_TglKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){ 
                tabMode.setValueAt("",r,0);
                tabMode.setValueAt(0,r,6);
                tabMode.setValueAt(0,r,7);
                tabMode.setValueAt(0,r,8);
                tabMode.setValueAt(0,r,9);
                tabMode.setValueAt(0,r,10);
                tabMode.setValueAt(0,r,11);
                tabMode.setValueAt(0,r,12);
                tabMode.setValueAt(0,r,13);
                tabMode.setValueAt(0,r,14);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
                tampil();
                break;
            case KeyEvent.VK_PAGE_UP:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
                tampil();
                kdptg.requestFocus();
                break;
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
                tampil();
                BtnSimpan.requestFocus();
                break;
            case KeyEvent.VK_UP:
                BtnGudangActionPerformed(null);
                break;
            default:
                break;
        }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    akses.setform("DlgPenjualan");
    bangsal.isCek();
    bangsal.emptTeks();
    bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        tampilAkunBayar();
        cariPPN(); 
    }//GEN-LAST:event_formWindowOpened

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        carijual.barang.emptTeks();
        carijual.barang.isCek();
        carijual.barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carijual.barang.setLocationRelativeTo(internalFrame1);
        carijual.barang.setAlwaysOnTop(false);
        carijual.barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            for(i=0;i<tbObat.getRowCount();i++){
                try {
                    stokbarang=0;  
                    if(aktifkanbatch.equals("yes")){
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                        try {
                            psstok.setString(1,kdgudang.getText());
                            psstok.setString(2,tbObat.getValueAt(i,1).toString());
                            psstok.setString(3,tbObat.getValueAt(i,16).toString());
                            psstok.setString(4,tbObat.getValueAt(i,17).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stokbarang=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok!=null){
                                psstok.close();
                            }
                        }   
                    }else{
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                        try {
                            psstok.setString(1,kdgudang.getText());
                            psstok.setString(2,tbObat.getValueAt(i,1).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stokbarang=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rsstok!=null){
                                rsstok.close();
                            }
                            if(psstok!=null){
                                psstok.close();
                            }
                        }   
                    }
                                         
                    tbObat.setValueAt(stokbarang,i,14);
                } catch (Exception e) {
                    tbObat.setValueAt(0,i,14);
                }
            }
        }else if(TabRawat.getSelectedIndex()==1){
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                try {
                    stokbarang=0; 
                    if(aktifkanbatch.equals("yes")){
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                        try {
                            psstok.setString(1,kdgudang.getText());
                            psstok.setString(2,tbDetailObatRacikan.getValueAt(i,1).toString());
                            psstok.setString(3,tbDetailObatRacikan.getValueAt(i,18).toString());
                            psstok.setString(4,tbDetailObatRacikan.getValueAt(i,19).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stokbarang=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            stokbarang=0;
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rsstok != null){
                                rsstok.close();
                            }

                            if(psstok != null){
                                psstok.close();
                            }
                        }
                    }else{
                        psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                        try {
                            psstok.setString(1,kdgudang.getText());
                            psstok.setString(2,tbDetailObatRacikan.getValueAt(i,1).toString());
                            rsstok=psstok.executeQuery();
                            if(rsstok.next()){
                                stokbarang=rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            stokbarang=0;
                            System.out.println("Notifikasi : "+e);
                        }finally{
                            if(rsstok != null){
                                rsstok.close();
                            }

                            if(psstok != null){
                                psstok.close();
                            }
                        }
                    }

                    tbDetailObatRacikan.setValueAt(stokbarang,i,7);
                } catch (Exception e) {
                    tbDetailObatRacikan.setValueAt(0,i,7);
                }
            }
        }            
    }//GEN-LAST:event_ppStokActionPerformed

    private void PersenppnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PersenppnKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isKembali();
            Bayar.requestFocus();
        }
    }//GEN-LAST:event_PersenppnKeyPressed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void BtnTambah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambah2ActionPerformed
        i=tabModeObatRacikan.getRowCount()+1;
        if(i==99){
            JOptionPane.showMessageDialog(null,"Maksimal 98 Racikan..!!");
        }else{
            tabModeObatRacikan.addRow(new Object[]{""+i,"","","","","",""});
        }
    }//GEN-LAST:event_BtnTambah2ActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),2).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),3).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),5).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),6).equals("")){
            tabModeObatRacikan.removeRow(tbObatRacikan.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf sudah terisi, gak boleh dihapus..!!");
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            BtnTambah2.setVisible(false);
            BtnHapus.setVisible(false);
            TCari.setPreferredSize(new Dimension(220, 23));
        }else if(TabRawat.getSelectedIndex()==1){
            BtnTambah2.setVisible(true);
            BtnHapus.setVisible(true);
            TCari.setPreferredSize(new Dimension(154, 23));
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(TabRawat.getSelectedIndex()==0){
            BtnTambah2.setVisible(false);
            BtnHapus.setVisible(false);
            TCari.setPreferredSize(new Dimension(220, 23));
        }else if(TabRawat.getSelectedIndex()==1){
            BtnTambah2.setVisible(true);
            BtnHapus.setVisible(true);
            TCari.setPreferredSize(new Dimension(154, 23));
        }
    }//GEN-LAST:event_formWindowActivated

    private void tbObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatRacikanKeyPressed
        if(tbObatRacikan.getRowCount()!=0){
            i=tbObatRacikan.getSelectedColumn();
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){                
                if(i==5){
                    aturan_pakai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    aturan_pakai.setLocationRelativeTo(internalFrame1);
                    aturan_pakai.setVisible(true);
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
                if((i==4)){
                    TCari.requestFocus();
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if((i==6)){
                    if(tbObatRacikan.getSelectedRow()!= -1){
                        if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString().equals("")||
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).toString().equals("")||
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),2).toString().equals("")||
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),3).toString().equals("")||
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).toString().equals("")||
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),5).toString().equals("")){
                            JOptionPane.showMessageDialog(null,"Silahkan lengkapi data racikan..!!");
                        }else{
                            tampildetailracikanobat();
                            TCari.requestFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Silahkan pilih racikan..!!");
                    }
                }
            }
        }
    }//GEN-LAST:event_tbObatRacikanKeyPressed

    private void tbDetailObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanKeyPressed
        if(tabModeDetailRacikan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getData();                     
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
                i=tbDetailObatRacikan.getSelectedColumn();
                if(i==13){
                    try {
                        if(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),13).toString().equals("0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),13).toString().equals("")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),13).toString().equals("0.0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),13).toString().equals("0,0")) {
                            tbDetailObatRacikan.setValueAt(embalasen,tbDetailObatRacikan.getSelectedRow(),13);
                        }
                    } catch (Exception e) {
                        tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),13);
                    }
                }else if(i==14){
                    try {
                        if(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),14).toString().equals("0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),14).toString().equals("")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),14).toString().equals("0.0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),14).toString().equals("0,0")) {
                            tbDetailObatRacikan.setValueAt(tuslahn,tbDetailObatRacikan.getSelectedRow(),14);
                        }
                    } catch (Exception e) {
                        tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),14);
                    }
                }
            }
            
            if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)||(evt.getKeyCode()==KeyEvent.VK_RIGHT)){
                try {    
                    getData();           
                } catch (java.lang.NullPointerException e) {
                }                
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                try {
                    switch (tbDetailObatRacikan.getSelectedColumn()) {
                        case 7:
                            tbDetailObatRacikan.setValueAt("", tbDetailObatRacikan.getSelectedRow(),7);
                            break;
                        case 8:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),8);
                            break;
                        case 10:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),10);
                            break;
                        case 11:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),11);
                            break;
                        case 12:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),12);
                            break;
                        case 13:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),13);
                            break;
                        case 14:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),14);
                            break;
                        case 18:
                            tbDetailObatRacikan.setValueAt("", tbDetailObatRacikan.getSelectedRow(),18);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                } 
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_BACK_SPACE){
                try {
                    switch (tbDetailObatRacikan.getSelectedColumn()) {
                        case 7:
                            tbDetailObatRacikan.setValueAt("", tbDetailObatRacikan.getSelectedRow(),7);
                            break;
                        case 8:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),8);
                            break;
                        case 10:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),10);
                            break;
                        case 11:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),11);
                            break;
                        case 12:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),12);
                            break;
                        case 13:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),13);
                            break;
                        case 14:
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(),14);
                            break;
                        case 18:
                            tbDetailObatRacikan.setValueAt("", tbDetailObatRacikan.getSelectedRow(),18);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                } 
            }            
        }
    }//GEN-LAST:event_tbDetailObatRacikanKeyPressed

    private void tbDetailObatRacikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbDetailObatRacikanPropertyChange

    private void tbDetailObatRacikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanMouseClicked
        if(tabModeDetailRacikan.getRowCount()!=0){
            try {                  
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDetailObatRacikanMouseClicked

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCekStok ceksetok=new DlgCekStok(null,false);
        ceksetok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ceksetok.setLocationRelativeTo(internalFrame1);
        ceksetok.setAlwaysOnTop(false);
        ceksetok.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppStok1ActionPerformed

    private void TglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TglItemStateChanged

    private void AkunBayarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AkunBayarItemStateChanged
        if(this.isVisible()==true){
            cariPPN();
            isKembali();
        }
    }//GEN-LAST:event_AkunBayarItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPenjualan dialog = new DlgPenjualan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AkunBayar;
    private widget.TextBox Bayar;
    private widget.TextBox BesarPPN;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMem;
    private widget.Button BtnNota;
    private widget.Button BtnPtg;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambah2;
    private widget.ComboBox Jenisjual;
    private widget.TextBox Kd2;
    private widget.Label LKembali;
    private widget.Label LTotal;
    private widget.TextBox NoNota;
    private widget.TextBox Persenppn;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Label TagihanPPn;
    private widget.Tanggal Tgl;
    private widget.TextBox catatan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdgudang;
    private widget.TextBox kdmem;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi5;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private javax.swing.JMenuItem ppStok1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDetailObatRacikan;
    private widget.Table tbObat;
    private widget.Table tbObatRacikan;
    // End of variables declaration//GEN-END:variables

    private void tampil1() {
        row=tabMode.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbObat.getValueAt(i,0).toString())>0){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            } 
        }
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        kategori=new String[jml];
        satuan=new String[jml];
        harga=new double[jml];
        jumlah=new double[jml];
        subtotal=new double[jml];
        diskon=new double[jml];
        besardiskon=new double[jml];
        tambahan=new double[jml];
        embalase=new double[jml];
        tuslah=new double[jml];
        aturanpakai=new String[jml];
        totaljual=new double[jml];
        stok=new double[jml];
        hbeli=new double[jml];
        nobatch=new String[jml];
        nofaktur=new String[jml];
        kadaluarsa=new String[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbObat.getValueAt(i,0).toString())>0){
                    jumlah[index]=Double.parseDouble(tabMode.getValueAt(i,0).toString());
                    kodebarang[index]=tabMode.getValueAt(i,1).toString();
                    namabarang[index]=tabMode.getValueAt(i,2).toString();
                    kategori[index]=tabMode.getValueAt(i,3).toString();
                    satuan[index]=tabMode.getValueAt(i,4).toString();
                    harga[index]=Double.parseDouble(tabMode.getValueAt(i,5).toString());
                    subtotal[index]=Double.parseDouble(tabMode.getValueAt(i,6).toString());
                    diskon[index]=Double.parseDouble(tabMode.getValueAt(i,7).toString());
                    besardiskon[index]=Double.parseDouble(tabMode.getValueAt(i,8).toString());
                    tambahan[index]=Double.parseDouble(tabMode.getValueAt(i,9).toString());
                    embalase[index]=Double.parseDouble(tabMode.getValueAt(i,10).toString());
                    tuslah[index]=Double.parseDouble(tabMode.getValueAt(i,11).toString());
                    aturanpakai[index]=tabMode.getValueAt(i,12).toString();
                    totaljual[index]=Double.parseDouble(tabMode.getValueAt(i,13).toString());
                    stok[index]=Double.parseDouble(tabMode.getValueAt(i,14).toString());
                    hbeli[index]=Double.parseDouble(tabMode.getValueAt(i,15).toString());
                    nobatch[index]=tabMode.getValueAt(i,16).toString();
                    nofaktur[index]=tabMode.getValueAt(i,17).toString();
                    kadaluarsa[index]=tabMode.getValueAt(i,18).toString();
                    index++;
                }
            } catch (Exception e) {
            }                
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){            
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],harga[i],subtotal[i],diskon[i],besardiskon[i],tambahan[i],embalase[i],tuslah[i],aturanpakai[i],totaljual[i],stok[i],hbeli[i],nobatch[i],nofaktur[i],kadaluarsa[i]});
        }
        try{
            ps=koneksi.prepareStatement(
                "select databarang.kode_brng, databarang.nama_brng,jenis.nama,gudangbarang.stok, "+
                " databarang.kode_sat, databarang.jualbebas, databarang.karyawan,"+
                " databarang.ralan,databarang.beliluar,databarang.kelas1,databarang.kelas2,"+
                " databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang."+hppfarmasi+" as dasar  "+
                " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.kode_brng like ? or "+
                " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.nama_brng like ? or "+
                " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            try {
                ps.setString(1,kdgudang.getText());
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,kdgudang.getText());
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,kdgudang.getText());
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                if(Jenisjual.getSelectedItem().equals("Karyawan")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("karyawan"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("jualbebas"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("beliluar"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("ralan"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas1"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas2"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas3"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("utama"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VIP")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("vip"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{
                            "",rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("vvip"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
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
    
    private void tampil2() {
        row=tabMode.getRowCount();
        jml=0;
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbObat.getValueAt(i,0).toString())>0){
                    jml++;
                }
            } catch (Exception e) {
                jml=jml+0;
            } 
        }
        
        kodebarang=new String[jml];
        namabarang=new String[jml];
        kategori=new String[jml];
        satuan=new String[jml];
        harga=new double[jml];
        jumlah=new double[jml];
        subtotal=new double[jml];
        diskon=new double[jml];
        besardiskon=new double[jml];
        tambahan=new double[jml];
        embalase=new double[jml];
        tuslah=new double[jml];
        aturanpakai=new String[jml];
        totaljual=new double[jml];
        stok=new double[jml];
        hbeli=new double[jml];
        nobatch=new String[jml];
        nofaktur=new String[jml];
        kadaluarsa=new String[jml];
        index=0;        
        for(i=0;i<row;i++){
            try {
                if(Double.parseDouble(tbObat.getValueAt(i,0).toString())>0){
                    jumlah[index]=Double.parseDouble(tabMode.getValueAt(i,0).toString());
                    kodebarang[index]=tabMode.getValueAt(i,1).toString();
                    namabarang[index]=tabMode.getValueAt(i,2).toString();
                    kategori[index]=tabMode.getValueAt(i,3).toString();
                    satuan[index]=tabMode.getValueAt(i,4).toString();
                    harga[index]=Double.parseDouble(tabMode.getValueAt(i,5).toString());
                    subtotal[index]=Double.parseDouble(tabMode.getValueAt(i,6).toString());
                    diskon[index]=Double.parseDouble(tabMode.getValueAt(i,7).toString());
                    besardiskon[index]=Double.parseDouble(tabMode.getValueAt(i,8).toString());
                    tambahan[index]=Double.parseDouble(tabMode.getValueAt(i,9).toString());
                    embalase[index]=Double.parseDouble(tabMode.getValueAt(i,10).toString());
                    tuslah[index]=Double.parseDouble(tabMode.getValueAt(i,11).toString());
                    aturanpakai[index]=tabMode.getValueAt(i,12).toString();
                    totaljual[index]=Double.parseDouble(tabMode.getValueAt(i,13).toString());
                    stok[index]=Double.parseDouble(tabMode.getValueAt(i,14).toString());
                    hbeli[index]=Double.parseDouble(tabMode.getValueAt(i,15).toString());
                    nobatch[index]=tabMode.getValueAt(i,16).toString();
                    nofaktur[index]=tabMode.getValueAt(i,17).toString();
                    kadaluarsa[index]=tabMode.getValueAt(i,18).toString();
                    index++;
                }
            } catch (Exception e) {
            }                
        }
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){            
            tabMode.addRow(new Object[]{jumlah[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],harga[i],subtotal[i],diskon[i],besardiskon[i],tambahan[i],embalase[i],tuslah[i],aturanpakai[i],totaljual[i],stok[i],hbeli[i],nobatch[i],nofaktur[i],kadaluarsa[i]});
        }
        try{
            ps=koneksi.prepareStatement(
                "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, "+
                " databarang.kode_sat, data_batch.jualbebas, data_batch.karyawan,"+
                " data_batch.ralan,data_batch.beliluar,gudangbarang.stok,data_batch.no_batch,"+
                " data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip, "+
                " data_batch."+hppfarmasi+" as dasar,data_batch.no_faktur,data_batch.tgl_kadaluarsa "+
                " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                " inner join jenis on databarang.kdjns=jenis.kdjns "+
                " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and data_batch.kode_brng like ? or "+
                " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and data_batch.no_batch like ? or "+
                " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and data_batch.no_faktur like ? or "+
                " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by data_batch.tgl_kadaluarsa asc");
            try {
                ps.setString(1,kdgudang.getText());
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,kdgudang.getText());
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,kdgudang.getText());
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,kdgudang.getText());
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                ps.setString(9,kdgudang.getText());
                ps.setString(10,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                if(Jenisjual.getSelectedItem().equals("Karyawan")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("karyawan"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("jualbebas"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("beliluar"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("ralan"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("kelas1"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("kelas2"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("kelas3"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("utama"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VIP")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("vip"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                    while(rs.next()){                              
                        tabMode.addRow(new Object[]{"",rs.getString("kode_brng"),
                                       rs.getString("nama_brng"),
                                       rs.getString("nama"),
                                       rs.getString("kode_sat"),
                                       rs.getDouble("vvip"),0,0,0,0,0,0,"",0,rs.getDouble("stok"),rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")});
                    } 
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
    
    private void tampil3() {
        try{
            row=tabModeDetailRacikan.getRowCount();
            jml=0;
            for(i=0;i<row;i++){
                try {
                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,8).toString())>0){
                        jml++;
                    }
                } catch (Exception e) {
                    jml=jml+0;
                } 
            }

            no=new String[jml];
            kodebarang=new String[jml];
            namabarang=new String[jml];
            kategori=new String[jml];
            satuan=new String[jml];
            harga=new double[jml];
            jumlah=new double[jml];
            subtotal=new double[jml];
            diskon=new double[jml];
            besardiskon=new double[jml];
            tambahan=new double[jml];
            embalase=new double[jml];
            tuslah=new double[jml];
            totaljual=new double[jml];
            stok=new double[jml];
            hbeli=new double[jml];
            nobatch=new String[jml];
            nofaktur=new String[jml];
            kadaluarsa=new String[jml];
            kps=new double[jml];
            kandungan=new String[jml];
            index=0;        
            for(i=0;i<row;i++){
                try {
                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,8).toString())>0){
                        no[index]=tabModeDetailRacikan.getValueAt(i,0).toString();
                        kodebarang[index]=tabModeDetailRacikan.getValueAt(i,1).toString();
                        namabarang[index]=tabModeDetailRacikan.getValueAt(i,2).toString();
                        kategori[index]=tabModeDetailRacikan.getValueAt(i,3).toString();
                        satuan[index]=tabModeDetailRacikan.getValueAt(i,4).toString();
                        harga[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,5).toString());
                        kps[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,6).toString());
                        kandungan[index]=tabModeDetailRacikan.getValueAt(i,7).toString();                    
                        jumlah[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,8).toString());
                        subtotal[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,9).toString());
                        diskon[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,10).toString());
                        besardiskon[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,11).toString());
                        tambahan[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,12).toString());
                        embalase[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,13).toString());
                        tuslah[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,14).toString());
                        totaljual[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,15).toString());
                        stok[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,16).toString());
                        hbeli[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,17).toString());
                        nobatch[index]=tabModeDetailRacikan.getValueAt(i,18).toString();
                        nofaktur[index]=tabModeDetailRacikan.getValueAt(i,19).toString();
                        kadaluarsa[index]=tabModeDetailRacikan.getValueAt(i,20).toString();
                        index++;
                    }
                } catch (Exception e) {
                }                
            }
            
            Valid.tabelKosong(tabModeDetailRacikan);
            for(i=0;i<jml;i++){            
                tabModeDetailRacikan.addRow(new Object[]{
                    no[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],
                    harga[i],kps[i],kandungan[i],jumlah[i],subtotal[i],diskon[i],besardiskon[i],
                    tambahan[i],embalase[i],tuslah[i],totaljual[i],stok[i],hbeli[i],
                    nobatch[i],nofaktur[i],kadaluarsa[i]
                });
            }
            
            ps=koneksi.prepareStatement(
                "select databarang.kode_brng, databarang.nama_brng,jenis.nama,gudangbarang.stok, "+
                " databarang.kode_sat, databarang.jualbebas, databarang.karyawan,"+
                " databarang.ralan,databarang.beliluar,databarang.kapasitas,databarang.kelas1,databarang.kelas2,"+
                " databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang."+hppfarmasi+" as dasar "+
                " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                " inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng "+
                " where gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.kode_brng like ? or "+
                " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and databarang.nama_brng like ? or "+
                " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            try {
                ps.setString(1,kdgudang.getText());
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,kdgudang.getText());
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,kdgudang.getText());
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                if(Jenisjual.getSelectedItem().equals("Karyawan")){
                    while(rs.next()){ 
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("karyawan"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("jualbebas"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("beliluar"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("ralan"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas1"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas2"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas3"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("utama"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VIP")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("vip"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("vvip"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),rs.getDouble("dasar"),"","",""
                        });
                    } 
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
    
    private void tampil4() {
        try{
            row=tabModeDetailRacikan.getRowCount();
            jml=0;
            for(i=0;i<row;i++){
                try {
                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,8).toString())>0){
                        jml++;
                    }
                } catch (Exception e) {
                    jml=jml+0;
                } 
            }

            no=new String[jml];
            kodebarang=new String[jml];
            namabarang=new String[jml];
            kategori=new String[jml];
            satuan=new String[jml];
            harga=new double[jml];
            jumlah=new double[jml];
            subtotal=new double[jml];
            diskon=new double[jml];
            besardiskon=new double[jml];
            tambahan=new double[jml];
            embalase=new double[jml];
            tuslah=new double[jml];
            totaljual=new double[jml];
            stok=new double[jml];
            hbeli=new double[jml];
            nobatch=new String[jml];
            nofaktur=new String[jml];
            kadaluarsa=new String[jml];
            kps=new double[jml];
            kandungan=new String[jml];
            int index=0;        
            for(i=0;i<row;i++){
                try {
                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,8).toString())>0){
                        no[index]=tabModeDetailRacikan.getValueAt(i,0).toString();
                        kodebarang[index]=tabModeDetailRacikan.getValueAt(i,1).toString();
                        namabarang[index]=tabModeDetailRacikan.getValueAt(i,2).toString();
                        kategori[index]=tabModeDetailRacikan.getValueAt(i,3).toString();
                        satuan[index]=tabModeDetailRacikan.getValueAt(i,4).toString();
                        harga[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,5).toString());
                        kps[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,6).toString());
                        kandungan[index]=tabModeDetailRacikan.getValueAt(i,7).toString();                    
                        jumlah[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,8).toString());
                        subtotal[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,9).toString());
                        diskon[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,10).toString());
                        besardiskon[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,11).toString());
                        tambahan[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,12).toString());
                        embalase[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,13).toString());
                        tuslah[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,14).toString());
                        totaljual[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,15).toString());
                        stok[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,16).toString());
                        hbeli[index]=Valid.SetAngka(tabModeDetailRacikan.getValueAt(i,17).toString());
                        nobatch[index]=tabModeDetailRacikan.getValueAt(i,18).toString();
                        nofaktur[index]=tabModeDetailRacikan.getValueAt(i,19).toString();
                        kadaluarsa[index]=tabModeDetailRacikan.getValueAt(i,20).toString();
                        index++;
                    }
                } catch (Exception e) {
                }                
            }
            
            Valid.tabelKosong(tabModeDetailRacikan);
            for(i=0;i<jml;i++){            
                tabModeDetailRacikan.addRow(new Object[]{
                    no[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],
                    harga[i],kps[i],kandungan[i],jumlah[i],subtotal[i],diskon[i],besardiskon[i],
                    tambahan[i],embalase[i],tuslah[i],totaljual[i],stok[i],hbeli[i],
                    nobatch[i],nofaktur[i],kadaluarsa[i]
                });
            }
            
            ps=koneksi.prepareStatement(
                "select data_batch.kode_brng, databarang.nama_brng,jenis.nama,gudangbarang.stok, "+
                " databarang.kode_sat,databarang.kapasitas,data_batch.jualbebas, data_batch.karyawan,"+
                " data_batch.ralan,data_batch.beliluar,gudangbarang.stok,data_batch.no_batch,"+
                " data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip, "+
                " data_batch."+hppfarmasi+" as dasar,data_batch.no_faktur,data_batch.tgl_kadaluarsa "+
                " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                " inner join jenis on databarang.kdjns=jenis.kdjns "+
                " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and data_batch.kode_brng like ? or "+
                " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and data_batch.no_batch like ? or "+
                " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and data_batch.no_faktur like ? or "+
                " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by data_batch.tgl_kadaluarsa");
            try {
                ps.setString(1,kdgudang.getText());
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,kdgudang.getText());
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,kdgudang.getText());
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,kdgudang.getText());
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                ps.setString(9,kdgudang.getText());
                ps.setString(10,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                if(Jenisjual.getSelectedItem().equals("Karyawan")){
                    while(rs.next()){ 
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("karyawan"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("jualbebas"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("beliluar"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("ralan"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas1"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas2"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("kelas3"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("utama"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VIP")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("vip"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
                }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                    while(rs.next()){                              
                        tabModeDetailRacikan.addRow(new Object[]{
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rs.getString("kode_brng"),rs.getString("nama_brng"),rs.getString("nama"),rs.getString("kode_sat"),
                            rs.getDouble("vvip"),rs.getDouble("kapasitas"),"",0,0,0,0,0,0,0,0,rs.getDouble("stok"),
                            rs.getDouble("dasar"),rs.getString("no_batch"),rs.getString("no_faktur"),rs.getString("tgl_kadaluarsa")
                        });
                    } 
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
    
    private void getData(){        
        if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else{
            row=tbObat.getSelectedRow();
            if(row!= -1){ 
                if(!tbObat.getValueAt(row,0).toString().equals("")){
                    try {
                        kolom=tbObat.getSelectedColumn();
                        if(Double.parseDouble(tabMode.getValueAt(row,0).toString())>0){
                            stokbarang=0;  
                            if(aktifkanbatch.equals("yes")){
                                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                                try {
                                    psstok.setString(1,kdgudang.getText());
                                    psstok.setString(2,tbObat.getValueAt(row,1).toString());
                                    psstok.setString(3,tbObat.getValueAt(row,16).toString());
                                    psstok.setString(4,tbObat.getValueAt(row,17).toString());
                                    rsstok=psstok.executeQuery();
                                    if(rsstok.next()){
                                        stokbarang=rsstok.getDouble(1);
                                    }                                
                                } catch (Exception e) {
                                    stokbarang=0;
                                    System.out.println("Notifikasi : "+e);
                                }finally{
                                    if(rsstok != null){
                                        rsstok.close();
                                    }
                                    if(psstok != null){
                                        psstok.close();
                                    }
                                }
                            }else{
                                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                                try {
                                    psstok.setString(1,kdgudang.getText());
                                    psstok.setString(2,tbObat.getValueAt(row,1).toString());
                                    rsstok=psstok.executeQuery();
                                    if(rsstok.next()){
                                        stokbarang=rsstok.getDouble(1);
                                    }                                
                                } catch (Exception e) {
                                    stokbarang=0;
                                    System.out.println("Notifikasi : "+e);
                                }finally{
                                    if(rsstok != null){
                                        rsstok.close();
                                    }
                                    if(psstok != null){
                                        psstok.close();
                                    }
                                }
                            }   

                            tbObat.setValueAt(stokbarang,row,14);

                            y=0;
                            try {
                                y=Double.parseDouble(tabMode.getValueAt(row,0).toString());
                            } catch (Exception e) {
                                y=0;
                            }

                            if(stokbarang<y){
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                tabMode.setValueAt("",row,0);
                            }

                            if(kolom==0){    
                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,6);                   
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,6);                   
                                }

                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,13);                                                     
                                }              
                            }else if(kolom==1){    
                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,6);                   
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,6);                   
                                }

                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,13);                                                     
                                }              
                            }else if(kolom==5){    
                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,6);                   
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,6);                   
                                }

                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,13);                                                     
                                }              
                            }else if(kolom==7){
                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())*(Double.parseDouble(tabMode.getValueAt(row,7).toString())/100), row,8); 
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,8); 
                                    tabMode.setValueAt(0, row,13);
                                    tabMode.setValueAt(0, row,7); 
                                }             
                            }else if(kolom==8){
                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,13);
                                }                 
                            }else if(kolom==9){
                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,13);
                                }              
                            }else if(kolom==10){
                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,13);
                                }              
                            }else if(kolom==11){
                                try {
                                    tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                                } catch (Exception e) {
                                    tabMode.setValueAt(0, row,13);
                                }              
                            }
                        }
                        if((kolom==16)||(kolom==17)){
                            cariBatch();   
                            getData2();
                        }
                    } catch (Exception e) {
                        tabMode.setValueAt("",row,0);
                        tabMode.setValueAt(0,row,6);
                        tabMode.setValueAt(0,row,7);  
                        tabMode.setValueAt(0,row,8); 
                        tabMode.setValueAt(0,row,13);
                    }
                }else{
                    tabMode.setValueAt(0,row,6);
                    tabMode.setValueAt(0,row,7);  
                    tabMode.setValueAt(0,row,8); 
                    tabMode.setValueAt(0,row,13);
                }
            }
            
            if(tbDetailObatRacikan.getSelectedRow()!= -1){
                row=tbDetailObatRacikan.getSelectedRow();
                try {
                    kolom=tbDetailObatRacikan.getSelectedColumn();
                    if(kolom==7){
                        if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),7).toString())>0){
                            try {
                                tbDetailObatRacikan.setValueAt(
                                    Valid.SetAngka8((Double.parseDouble(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).toString())
                                    *Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),7).toString()))
                                    /Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),6).toString()),1)
                                    ,tbDetailObatRacikan.getSelectedRow(),8);
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),8);
                            }    
                        }
                    }
                    
                    if(Valid.SetAngka(tabModeDetailRacikan.getValueAt(row,8).toString())>0){
                        stokbarang=0;   
                        if(aktifkanbatch.equals("yes")){
                            psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                            try {
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,tabModeDetailRacikan.getValueAt(row,1).toString());
                                psstok.setString(3,tabModeDetailRacikan.getValueAt(row,18).toString());
                                psstok.setString(4,tabModeDetailRacikan.getValueAt(row,19).toString());
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                    stokbarang=rsstok.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rsstok!=null){
                                    rsstok.close();
                                }
                                if(psstok!=null){
                                    psstok.close();
                                }
                            }
                        }else{
                            psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                            try {
                                psstok.setString(1,kdgudang.getText());
                                psstok.setString(2,tabModeDetailRacikan.getValueAt(row,1).toString());
                                rsstok=psstok.executeQuery();
                                if(rsstok.next()){
                                    stokbarang=rsstok.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rsstok!=null){
                                    rsstok.close();
                                }
                                if(psstok!=null){
                                    psstok.close();
                                }
                            }
                        }
                            
                        tabModeDetailRacikan.setValueAt(stokbarang,row,16);
                        
                        y=0;
                        try {
                            y=Double.parseDouble(tabModeDetailRacikan.getValueAt(row,8).toString());
                        } catch (Exception e) {
                            y=0;
                        }
                        
                        if(stokbarang<y){
                            JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            tabModeDetailRacikan.setValueAt(0,row,8);
                            tabModeDetailRacikan.setValueAt("",row,7);
                            TCari.requestFocus();
                        }
                        
                        if(kolom==8){    
                            try {
                                tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,8).toString())*Double.parseDouble(tbDetailObatRacikan.getValueAt(row,5).toString()), row,9);                   
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, row,9);                   
                            }

                            try {
                                tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,9).toString())-Double.parseDouble(tbDetailObatRacikan.getValueAt(row,11).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,12).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,13).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,14).toString()), row,15);
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, row,15);                                                     
                            }              
                        }else if(kolom==10){
                            try {
                                tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,9).toString())*(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,10).toString())/100), row,11);                   
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, row,11);                   
                            }
                        }else if((kolom==11)||(kolom==12)||(kolom==13)||(kolom==14)){
                            try {
                                tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,9).toString())-Double.parseDouble(tbDetailObatRacikan.getValueAt(row,11).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,12).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,13).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,14).toString()), row,15);
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, row,15);                                                     
                            } 
                        }
                    }
                    
                    if((kolom==18)||(kolom==19)){
                        cariBatch();   
                        getData2();
                    }
                } catch (Exception e) {
                }
            }
        } 
        
        ttl=0;
        ttlhpp=0;
        y=0;
        z=0;
        
        for(int r=0;r<tabMode.getRowCount();r++){ 
            try {
                y=Double.parseDouble(tabMode.getValueAt(r,13).toString()); 
            } catch (Exception e) {
                y=0;
            }
            ttl=ttl+y;
            
            try {
                z=Double.parseDouble(tabMode.getValueAt(r,15).toString())*Double.parseDouble(tabMode.getValueAt(r,0).toString()); 
            } catch (Exception e) {
                z=0;
            }
            ttlhpp=ttlhpp+z;
        }
        
        for(int r=0;r<tbDetailObatRacikan.getRowCount();r++){ 
            try {
                y=Double.parseDouble(tbDetailObatRacikan.getValueAt(r,15).toString()); 
            } catch (Exception e) {
                y=0;
            }
            ttl=ttl+y;
            
            try {
                z=Double.parseDouble(tbDetailObatRacikan.getValueAt(r,17).toString())*Double.parseDouble(tbDetailObatRacikan.getValueAt(r,8).toString()); 
            } catch (Exception e) {
                z=0;
            }
            ttlhpp=ttlhpp+z;
        }
        
        LTotal.setText(Valid.SetAngka(ttl));
        isKembali();
    }
    
    private void getData2(){
        if(TabRawat.getSelectedIndex()==0){
            row=tbObat.getSelectedRow();
            if(nmgudang.getText().trim().equals("")){
                Valid.textKosong(kdgudang,"Lokasi");
            }else if(row!= -1){ 
                try {
                    if(Valid.SetAngka(tabMode.getValueAt(row,0).toString())>0){                       
                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,0).toString())*Double.parseDouble(tabMode.getValueAt(row,5).toString()), row,6);                   
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,6);                   
                        }

                        try {
                            tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                        } catch (Exception e) {
                            tabMode.setValueAt(0, row,13);                                                     
                        } 

                        if(Double.parseDouble(tabMode.getValueAt(row,7).toString())>0){
                            try {
                                tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())*(Double.parseDouble(tabMode.getValueAt(row,7).toString())/100), row,8); 
                                tabMode.setValueAt(Double.parseDouble(tabMode.getValueAt(row,6).toString())-Double.parseDouble(tabMode.getValueAt(row,8).toString())+Double.parseDouble(tabMode.getValueAt(row,9).toString())+Double.parseDouble(tabMode.getValueAt(row,10).toString())+Double.parseDouble(tabMode.getValueAt(row,11).toString()), row,13);
                            } catch (Exception e) {
                                tabMode.setValueAt(0, row,8); 
                                tabMode.setValueAt(0, row,13);
                                tabMode.setValueAt(0, row,7); 
                            }
                        } 
                    }
                } catch (Exception e) {
                }  
            }
        }else if(TabRawat.getSelectedIndex()==1){
            row=tbDetailObatRacikan.getSelectedRow();
            if(nmgudang.getText().trim().equals("")){
                Valid.textKosong(kdgudang,"Lokasi");
            }else if(row!= -1){ 
                try {
                    if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(row,8).toString())>0){                       
                        try {
                            tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,8).toString())*Double.parseDouble(tbDetailObatRacikan.getValueAt(row,5).toString()), row,9);                   
                        } catch (Exception e) {
                            tbDetailObatRacikan.setValueAt(0, row,9);                   
                        }

                        try {
                            tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,9).toString())-Double.parseDouble(tbDetailObatRacikan.getValueAt(row,11).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,12).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,13).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,14).toString()), row,15);
                        } catch (Exception e) {
                            tbDetailObatRacikan.setValueAt(0, row,15);                                                     
                        } 

                        if(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,10).toString())>0){
                            try {
                                tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,9).toString())*(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,10).toString())/100), row,11); 
                                tbDetailObatRacikan.setValueAt(Double.parseDouble(tbDetailObatRacikan.getValueAt(row,9).toString())-Double.parseDouble(tbDetailObatRacikan.getValueAt(row,11).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,12).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,13).toString())+Double.parseDouble(tbDetailObatRacikan.getValueAt(row,14).toString()), row,15);
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, row,11); 
                                tbDetailObatRacikan.setValueAt(0, row,15);
                                tbDetailObatRacikan.setValueAt(0, row,10); 
                            }
                        } 
                    }
                } catch (Exception e) {
                }  
            }
        }
            
        ttl=0;
        ttlhpp=0;
        y=0;
        z=0;
        
        for(int r=0;r<tabMode.getRowCount();r++){ 
            try {
                y=Double.parseDouble(tabMode.getValueAt(r,13).toString()); 
            } catch (Exception e) {
                y=0;
            }
            ttl=ttl+y;
            
            try {
                z=Double.parseDouble(tabMode.getValueAt(r,15).toString())*Double.parseDouble(tabMode.getValueAt(r,0).toString()); 
            } catch (Exception e) {
                z=0;
            }
            ttlhpp=ttlhpp+z;
        }
        
        for(int r=0;r<tbDetailObatRacikan.getRowCount();r++){ 
            try {
                y=Double.parseDouble(tbDetailObatRacikan.getValueAt(r,15).toString()); 
            } catch (Exception e) {
                y=0;
            }
            ttl=ttl+y;
            
            try {
                z=Double.parseDouble(tbDetailObatRacikan.getValueAt(r,17).toString())*Double.parseDouble(tbDetailObatRacikan.getValueAt(r,8).toString()); 
            } catch (Exception e) {
                z=0;
            }
            ttlhpp=ttlhpp+z;
        }
        LTotal.setText(Valid.SetAngka(ttl));
        isKembali();
    }
    
    private void isKembali(){
        if(!Bayar.getText().trim().equals("")) {
            bayar=Double.parseDouble(Bayar.getText()); 
        }
        if(ttl>0) {
            total=ttl; 
        }
        if(!Persenppn.getText().trim().equals("")) {
            ppn=Double.parseDouble(Persenppn.getText()); 
        }
        if(ppn>0){
            besarppn=(ppn/100)*total;
            BesarPPN.setText(Valid.SetAngka(besarppn));
        }else{
            besarppn=0;
            BesarPPN.setText("0");
        }
        
        tagihanppn=besarppn+total;
        TagihanPPn.setText(Valid.SetAngka(tagihanppn));        
        LKembali.setText(Valid.SetAngka(bayar-tagihanppn));     
    }
    
    public void isCek(){
        autoNomor();
        TCari.requestFocus();
        Sequel.cariIsi("select kd_bangsal from set_lokasi",kdgudang);
        Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",nmgudang,kdgudang.getText());
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getpenjualan_obat());
            BtnTambah.setEnabled(akses.getobat());
            kdptg.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }    
        if(Sequel.cariIsi("select tampilkan_tombol_nota_penjualan from set_nota").equals("Yes")){
            BtnNota.setVisible(true);
        }else{
            if(akses.getkode().equals("Admin Utama")){
                BtnNota.setVisible(true);
            }else{
                BtnNota.setVisible(false);
            }            
        }
    }
    
    public void autoNomor(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_jual,3),signed)),0) from penjualan where tgl_jual='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' ",
                "PJ"+Tgl.getSelectedItem().toString().substring(6,10)+Tgl.getSelectedItem().toString().substring(3,5)+Tgl.getSelectedItem().toString().substring(0,2),3,NoNota); 
    }
    
    public void setPasien(String norm){
        kdmem.setText(norm);
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
    }

    private void cariBatch() {
        if(TabRawat.getSelectedIndex()==0){
            try {
                pscaribatch=koneksi.prepareStatement("select * from data_batch where no_batch=? and kode_brng=? and no_faktur=?");
                try {
                    pscaribatch.setString(1,tabMode.getValueAt(tbObat.getSelectedRow(),16).toString());
                    pscaribatch.setString(2,tabMode.getValueAt(tbObat.getSelectedRow(),1).toString());
                    pscaribatch.setString(3,tabMode.getValueAt(tbObat.getSelectedRow(),17).toString());
                    rs=pscaribatch.executeQuery();
                    if(rs.next()){
                        tabMode.setValueAt(rs.getString("no_faktur"), tbObat.getSelectedRow(),17);
                        tabMode.setValueAt(rs.getString("tgl_kadaluarsa"), tbObat.getSelectedRow(),18);
                        if(aktifkanbatch.equals("yes")){
                            if(Jenisjual.getSelectedItem().equals("Karyawan")){
                                tabMode.setValueAt(rs.getDouble("karyawan"), tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                                tabMode.setValueAt(rs.getDouble("jualbebas"), tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                                tabMode.setValueAt(rs.getDouble("beliluar"), tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                                tabMode.setValueAt(rs.getDouble("ralan"), tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                                tabMode.setValueAt(rs.getDouble("kelas1"), tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                                tabMode.setValueAt(rs.getDouble("kelas2"),tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                                tabMode.setValueAt(rs.getDouble("kelas3"), tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                                tabMode.setValueAt(rs.getDouble("utama"), tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("VIP")){
                                tabMode.setValueAt(rs.getDouble("vip"), tbObat.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                                tabMode.setValueAt(rs.getDouble("vvip"), tbObat.getSelectedRow(),5);
                            }

                            try {
                                stokbarang=0;  
                                if(aktifkanbatch.equals("yes")){
                                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                                    try {
                                        psstok.setString(1,kdgudang.getText());
                                        psstok.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                        psstok.setString(3,tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
                                        psstok.setString(4,tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
                                        rsstok=psstok.executeQuery();
                                        if(rsstok.next()){
                                            stokbarang=rsstok.getDouble(1);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(rsstok!=null){
                                            rsstok.close();
                                        }
                                        if(psstok!=null){
                                            psstok.close();
                                        }
                                    }   
                                }else{
                                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                                    try {
                                        psstok.setString(1,kdgudang.getText());
                                        psstok.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                                        rsstok=psstok.executeQuery();
                                        if(rsstok.next()){
                                            stokbarang=rsstok.getDouble(1);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(rsstok!=null){
                                            rsstok.close();
                                        }
                                        if(psstok!=null){
                                            psstok.close();
                                        }
                                    }   
                                }  
                                tbObat.setValueAt(stokbarang,tbObat.getSelectedRow(),14);
                                y=0;
                                try {
                                    y=Double.parseDouble(tabMode.getValueAt(tbObat.getSelectedRow(),0).toString());
                                } catch (Exception e) {
                                    y=0;
                                }
                                if(stokbarang<y){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                    tbObat.setValueAt("",tbObat.getSelectedRow(),0);
                                }
                            } catch (Exception e) {
                                tbObat.setValueAt(0,tbObat.getSelectedRow(),14);
                            }
                        }                                    
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(pscaribatch!=null){
                        pscaribatch.close();
                    }
                } 
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }else if(TabRawat.getSelectedIndex()==1){
            try {
                pscaribatch=koneksi.prepareStatement("select * from data_batch where no_batch=? and kode_brng=? and no_faktur=?");
                try {
                    pscaribatch.setString(1,tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),18).toString());
                    pscaribatch.setString(2,tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),1).toString());
                    pscaribatch.setString(3,tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),19).toString());
                    rs=pscaribatch.executeQuery();
                    if(rs.next()){
                        tbDetailObatRacikan.setValueAt(rs.getString("no_faktur"), tbDetailObatRacikan.getSelectedRow(),19);
                        tbDetailObatRacikan.setValueAt(rs.getString("tgl_kadaluarsa"), tbDetailObatRacikan.getSelectedRow(),20);
                        if(aktifkanbatch.equals("yes")){
                            if(Jenisjual.getSelectedItem().equals("Karyawan")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("karyawan"), tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("jualbebas"), tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("beliluar"), tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("ralan"), tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("kelas1"), tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("kelas2"),tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("kelas3"), tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("utama"), tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("VIP")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("vip"), tbDetailObatRacikan.getSelectedRow(),5);
                            }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                                tbDetailObatRacikan.setValueAt(rs.getDouble("vvip"), tbDetailObatRacikan.getSelectedRow(),5);
                            }

                            try {
                                stokbarang=0; 
                                if(aktifkanbatch.equals("yes")){
                                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch=? and no_faktur=?");
                                    try {
                                        psstok.setString(1,kdgudang.getText());
                                        psstok.setString(2,tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),1).toString());
                                        psstok.setString(3,tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),18).toString());
                                        psstok.setString(4,tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),19).toString());
                                        rsstok=psstok.executeQuery();
                                        if(rsstok.next()){
                                            stokbarang=rsstok.getDouble(1);
                                        }
                                    } catch (Exception e) {
                                        stokbarang=0;
                                        System.out.println("Notifikasi : "+e);
                                    }finally{
                                        if(rsstok != null){
                                            rsstok.close();
                                        }

                                        if(psstok != null){
                                            psstok.close();
                                        }
                                    }
                                }else{
                                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=? and no_batch='' and no_faktur=''");
                                    try {
                                        psstok.setString(1,kdgudang.getText());
                                        psstok.setString(2,tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),1).toString());
                                        rsstok=psstok.executeQuery();
                                        if(rsstok.next()){
                                            stokbarang=rsstok.getDouble(1);
                                        }
                                    } catch (Exception e) {
                                        stokbarang=0;
                                        System.out.println("Notifikasi : "+e);
                                    }finally{
                                        if(rsstok != null){
                                            rsstok.close();
                                        }

                                        if(psstok != null){
                                            psstok.close();
                                        }
                                    }
                                }
                                tbDetailObatRacikan.setValueAt(stokbarang,tbDetailObatRacikan.getSelectedRow(),16);
                                y=0;
                                try {
                                    y=Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),8).toString());
                                } catch (Exception e) {
                                    y=0;
                                }
                                if(stokbarang<y){
                                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                                    tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),8);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),16);
                            }
                        }                                    
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(pscaribatch!=null){
                        pscaribatch.close();
                    }
                } 
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }            
    }
    
    private void tampil(){
        if(aktifkanbatch.equals("yes")){
            tampil2();
        }else{
            tampil1();
        }
    }

    private void tampildetailracikanobat() {
        if(aktifkanbatch.equals("yes")){
            tampil4();
        }else{
            tampil3();
        }
    }

    private void simpan() {
        for(i=0;i<tabMode.getRowCount();i++){  
            try {
                if(Valid.SetAngka(tabMode.getValueAt(i,0).toString())>0){
                   if(Sequel.menyimpantf2("detailjual","'"+NoNota.getText()+"','"+
                           tabMode.getValueAt(i,1).toString()+"','"+
                           tabMode.getValueAt(i,4).toString()+"','"+
                           tabMode.getValueAt(i,5).toString()+"','"+
                           tabMode.getValueAt(i,15).toString()+"','"+
                           tabMode.getValueAt(i,0).toString()+"','"+
                           tabMode.getValueAt(i,6).toString()+"','"+
                           tabMode.getValueAt(i,7).toString()+"','"+
                           tabMode.getValueAt(i,8).toString()+"','"+
                           tabMode.getValueAt(i,9).toString()+"','"+
                           tabMode.getValueAt(i,10).toString()+"','"+
                           tabMode.getValueAt(i,11).toString()+"','"+
                           tabMode.getValueAt(i,12).toString()+"','"+
                           tabMode.getValueAt(i,13).toString()+"','"+
                           tabMode.getValueAt(i,16).toString()+"','"+
                           tabMode.getValueAt(i,17).toString()+"'","Transaksi Penjualan")==true){
                        if(verifikasi_penjualan_di_kasir.equals("No")){
                            if(aktifkanbatch.equals("yes")){
                                Sequel.mengedit("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                    tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,16).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,17).toString()
                                });
                                Trackobat.catatRiwayat(tabMode.getValueAt(i,1).toString(),0,Valid.SetAngka(tabMode.getValueAt(i,0).toString()),"Penjualan",akses.getkode(),kdgudang.getText(),"Simpan",tabMode.getValueAt(i,16).toString(),tabMode.getValueAt(i,17).toString(),NoNota.getText()+" "+kdmem.getText()+" "+nmmem.getText());
                                Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tabMode.getValueAt(i,0).toString()+"','"+tabMode.getValueAt(i,16).toString()+"','"+tabMode.getValueAt(i,17).toString()+"'", 
                                 "stok=stok-'"+tabMode.getValueAt(i,0).toString()+"'","kode_brng='"+tabMode.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='"+tabMode.getValueAt(i,16).toString()+"' and no_faktur='"+tabMode.getValueAt(i,17).toString()+"'");   
                            }else{
                                Trackobat.catatRiwayat(tabMode.getValueAt(i,1).toString(),0,Valid.SetAngka(tabMode.getValueAt(i,0).toString()),"Penjualan",akses.getkode(),kdgudang.getText(),"Simpan","","",NoNota.getText()+" "+kdmem.getText()+" "+nmmem.getText());
                                Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tabMode.getValueAt(i,0).toString()+"','',''", 
                                 "stok=stok-'"+tabMode.getValueAt(i,0).toString()+"'","kode_brng='"+tabMode.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''");   
                            }                                                
                        }                                       
                   }else{
                       sukses=false;
                   }                                    
                }
            } catch (Exception e) {
            }                
        }   

        for(i=0;i<tbObatRacikan.getRowCount();i++){ 
            if(Valid.SetAngka(tbObatRacikan.getValueAt(i,4).toString())>0){ 
                if(Sequel.menyimpantf2("obat_racikan_jual","?,?,?,?,?,?,?","obat racikan jual",7,new String[]{
                   NoNota.getText(),tbObatRacikan.getValueAt(i,0).toString(),tbObatRacikan.getValueAt(i,1).toString(),
                   tbObatRacikan.getValueAt(i,2).toString(),tbObatRacikan.getValueAt(i,4).toString(),
                   tbObatRacikan.getValueAt(i,5).toString(),tbObatRacikan.getValueAt(i,6).toString()
                })==false){
                    sukses=false;
                }
            }
        }

        for(i=0;i<tbDetailObatRacikan.getRowCount();i++){  
            try {
                if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,8).toString())>0){
                    if(Sequel.menyimpantf2("detailjual","'"+NoNota.getText()+"','"+
                           tbDetailObatRacikan.getValueAt(i,1).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,4).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,5).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,17).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,8).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,9).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,10).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,11).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,12).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,13).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,14).toString()+"','','"+
                           tbDetailObatRacikan.getValueAt(i,15).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,18).toString()+"','"+
                           tbDetailObatRacikan.getValueAt(i,19).toString()+"'","Transaksi Penjualan")==true){
                        if(Sequel.menyimpantf2("detail_obat_racikan_jual","?,?,?","Detail Racikan",3,new String[]{
                            NoNota.getText(),tbDetailObatRacikan.getValueAt(i,0).toString(),tbDetailObatRacikan.getValueAt(i,1).toString()
                        })==false){
                            sukses=false;
                        }                                        
                        if(verifikasi_penjualan_di_kasir.equals("No")){
                            if(aktifkanbatch.equals("yes")){
                                Sequel.mengedit("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                    tbDetailObatRacikan.getValueAt(i,8).toString(),tbDetailObatRacikan.getValueAt(i,18).toString(),tbDetailObatRacikan.getValueAt(i,1).toString(),tbDetailObatRacikan.getValueAt(i,19).toString()
                                });
                                Trackobat.catatRiwayat(tbDetailObatRacikan.getValueAt(i,1).toString(),0,Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,8).toString()),"Penjualan",akses.getkode(),kdgudang.getText(),"Simpan",tbDetailObatRacikan.getValueAt(i,18).toString(),tbDetailObatRacikan.getValueAt(i,19).toString(),NoNota.getText()+" "+kdmem.getText()+" "+nmmem.getText());
                                Sequel.menyimpan("gudangbarang","'"+tbDetailObatRacikan.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tbDetailObatRacikan.getValueAt(i,8).toString()+"','"+tbDetailObatRacikan.getValueAt(i,18).toString()+"','"+tbDetailObatRacikan.getValueAt(i,19).toString()+"'", 
                                 "stok=stok-'"+tbDetailObatRacikan.getValueAt(i,8).toString()+"'","kode_brng='"+tbDetailObatRacikan.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='"+tbDetailObatRacikan.getValueAt(i,18).toString()+"' and no_faktur='"+tbDetailObatRacikan.getValueAt(i,19).toString()+"'");   
                            }else{
                                Trackobat.catatRiwayat(tbDetailObatRacikan.getValueAt(i,1).toString(),0,Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,8).toString()),"Penjualan",akses.getkode(),kdgudang.getText(),"Simpan","","",NoNota.getText()+" "+kdmem.getText()+" "+nmmem.getText());
                                Sequel.menyimpan("gudangbarang","'"+tbDetailObatRacikan.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tbDetailObatRacikan.getValueAt(i,8).toString()+"','',''", 
                                 "stok=stok-'"+tbDetailObatRacikan.getValueAt(i,8).toString()+"'","kode_brng='"+tbDetailObatRacikan.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''");  
                            }                                                
                        }                                       
                    }else{
                        sukses=false;
                    }                                    
                }
            } catch (Exception e) {
            }                
        }                        

        if(verifikasi_penjualan_di_kasir.equals("No")){
            if(sukses==true){
                Sequel.queryu("delete from tampjurnal");                    
                Sequel.menyimpan2("tampjurnal","'"+Penjualan_Obat+"','PENJUALAN OBAT BEBAS','0','"+ttl+"'","Rekening");    
                Sequel.menyimpan2("tampjurnal","'"+kode_akun_bayar+"','"+AkunBayar.getSelectedItem().toString()+"','"+ttl+"','0'","Rekening"); 
                Sequel.menyimpan2("tampjurnal","'"+HPP_Obat_Jual_Bebas+"','HPP Obat Jual Bebas','"+ttlhpp+"','0'","Rekening");    
                Sequel.menyimpan2("tampjurnal","'"+Persediaan_Obat_Jual_Bebas+"','Persediaan Obat Jual Bebas','0','"+ttlhpp+"'","Rekening");                              
                sukses=jur.simpanJurnal(NoNota.getText(),"U","PENJUALAN DI "+nmgudang.getText().toUpperCase()+", OLEH "+akses.getkode());     
                if(sukses==true){
                    sukses=Sequel.menyimpantf2("tagihan_sadewa","'"+NoNota.getText()+"','"+kdmem.getText()+"','"+nmmem.getText().replaceAll("'","")+"','-',concat('"+Valid.SetTgl(Tgl.getSelectedItem()+"")+
                            "',' ',CURTIME()),'Pelunasan','"+ttl+"','"+ttl+"','Sudah','"+akses.getkode()+"'","No.Nota");
                }   
            }
        }
    }
    
    private void tampilAkunBayar() {         
         try{      
             file=new File("./cache/akunbayar.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             iyem="";
             ps=koneksi.prepareStatement("select * from akun_bayar order by nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     iyem=iyem+"{\"NamaAkun\":\""+rs.getString(1).replaceAll("\"","")+"\",\"KodeRek\":\""+rs.getString(2)+"\",\"PPN\":\""+rs.getDouble(3)+"\"},";
                 }
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rs != null){
                     rs.close();
                 } 
                 if(ps != null){
                     ps.close();
                 } 
             }

             fileWriter.write("{\"akunbayar\":["+iyem.substring(0,iyem.length()-1)+"]}");
             fileWriter.flush();
             fileWriter.close();
             iyem=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }

    private void cariPPN() {
        try {
            myObj = new FileReader("./cache/akunbayar.iyem");
            root = mapper.readTree(myObj);
            response = root.path("akunbayar");
            if(response.isArray()){
               for(JsonNode list:response){
                   if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                        Persenppn.setText(list.path("PPN").asText());
                   }
               }
            }
            myObj.close();
        } catch (Exception e) {
            Persenppn.setText("0");
        }
    }
}

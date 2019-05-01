package keuangan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgPenanggungJawab;

public class DlgRBTindakanPoli extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement pspoli,psdokter,pstindakan,pstindakan2,pspasien,psobat,psobatlangsung,pslaborat,psdetailobat,psregistrasi,psdetailregistrasi,
            psdetaillaborat,psdetailtindakan,psdetailtindakan2,psdetailobatlangsung,pstambahan,pspotongan,psdetailtambahan,psdetailpotongan,pscarabayar,
            psitemlaborat,psdetailitemlaborat,psradiologi,psdetailradiologi;
    private ResultSet rspoli,rsdokter,rstindakan,rsobat,rsobatlangsung,rspasien,rslaborat,rsdetailobat,rsregistrasi,rsdetailregistrasi,rscarabayar,
            rsdetailtindakan,rsdetaillaborat,rsdetailobatlangsung,rstambahan,rspotongan,rsdetailtambahan,rsdetailpotongan,rsitemlaborat,
            rsdetailitemlaborat,rsradiologi,rsdetailradiologi; 
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private int i=0,a=0;
    private double obat=0,obatlangsung=0,laborat=0,radiologi=0,jm=0,jm2=0,ttlbiaya=0,detailobat=0,detailobatlangsung=0,ttlobat=0,ttlobatlangsung=0,ttllaborat=0,ttljm=0,
            detailtindakan=0,detailtindakan2=0,detaillaborat=0,tambahan,potongan,detailtambahan,detailpotongan,registrasi=0,detailregistrasi,ttlpotongan=0,ttltambahan=0,
            ttlregistrasi=0,itemlaborat=0,detailitemlaborat=0,detailradiologi=0,ttlradiologi=0;
    private String carabayar="",pilihancarabayar="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRBTindakanPoli(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Poliklinik","Jml.Pas","Obt+Emb+Tsl","Laborat","Paket Tindakan","Tambahan","Potongan","Registrasi","Radiologi","Total"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 11; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(280);
            }else if(i==2){
                column.setPreferredWidth(50);
            }else{
                column.setPreferredWidth(80);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdpoli.setDocument(new batasInput((byte)8).getKata(kdpoli));
                
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    nmpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    prosesCari();
                }    
                kdpoli.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {poli.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    pilihancarabayar=(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                }     
                prosesCari3();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.onCari();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            pspoli=koneksi.prepareStatement("select kd_poli,nm_poli from poliklinik where  kd_poli like ?");
            psdokter=koneksi.prepareStatement(
                    "select dokter.kd_dokter,dokter.nm_dokter,count(dokter.kd_dokter) as jumlah from dokter inner join reg_periksa "+
                    "on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "reg_periksa.kd_poli=? and reg_periksa.tgl_registrasi between ? and ? group by dokter.kd_dokter");
            pstindakan=koneksi.prepareStatement(
                    "select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr inner join reg_periksa "+
                    "on reg_periksa.no_rawat=rawat_jl_dr.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "rawat_jl_dr.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            pstindakan2=koneksi.prepareStatement(
                    "select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr inner join reg_periksa "+
                    "on reg_periksa.no_rawat=rawat_jl_drpr.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "rawat_jl_drpr.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psobat=koneksi.prepareStatement(
                    "select sum(detail_pemberian_obat.total) from detail_pemberian_obat inner join reg_periksa "+
                    "on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");  
            psobatlangsung=koneksi.prepareStatement(
                    "select sum(tagihan_obat_langsung.besar_tagihan) from tagihan_obat_langsung inner join reg_periksa "+
                    "on tagihan_obat_langsung.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");  
            pstambahan=koneksi.prepareStatement(
                    "select sum(tambahan_biaya.besar_biaya) from tambahan_biaya inner join reg_periksa "+
                    "on tambahan_biaya.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?"); 
            pspotongan=koneksi.prepareStatement(
                    "select sum(pengurangan_biaya.besar_pengurangan) from pengurangan_biaya inner join reg_periksa "+
                    "on pengurangan_biaya.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?"); 
            pslaborat=koneksi.prepareStatement(
                    "select sum(periksa_lab.biaya) from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                    "where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psitemlaborat=koneksi.prepareStatement(
                    "select sum(detail_periksa_lab.biaya_item) as total from detail_periksa_lab "+
                    "inner join periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat  "+
                    "and detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                    "and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa and detail_periksa_lab.jam=periksa_lab.jam "+
                    "where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");  
            psradiologi=koneksi.prepareStatement(
                    "select sum(periksa_radiologi.biaya) from periksa_radiologi inner join reg_periksa "+
                    "on periksa_radiologi.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");                    
            psdetaillaborat=koneksi.prepareStatement(
                    "select sum(periksa_lab.biaya) from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                    "where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and periksa_lab.no_rawat=? and reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=? ");
            psdetailradiologi=koneksi.prepareStatement(
                    "select sum(periksa_radiologi.biaya) from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                    "where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and periksa_radiologi.no_rawat=? and reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=? ");
            psdetailitemlaborat=koneksi.prepareStatement("select sum(detail_periksa_lab.biaya_item) as total from detail_periksa_lab inner join periksa_lab "+
                    "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat and detail_periksa_lab.no_rawat=periksa_lab.no_rawat "+
                    "and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw  and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa and detail_periksa_lab.jam=periksa_lab.jam "+
                    "where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and periksa_lab.no_rawat=? and reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            pspasien=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rkm_medis,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.kd_pj "+
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_dokter=? and reg_periksa.kd_poli=? order by reg_periksa.kd_pj,reg_periksa.tgl_registrasi");
            psdetailtindakan=koneksi.prepareStatement(
                    "select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr inner join reg_periksa "+
                    "on reg_periksa.no_rawat=rawat_jl_dr.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and reg_periksa.no_rawat=? and reg_periksa.kd_dokter=? "+
                    "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psdetailtindakan2=koneksi.prepareStatement(
                    "select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr inner join reg_periksa "+
                    "on reg_periksa.no_rawat=rawat_jl_drpr.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and reg_periksa.no_rawat=? and reg_periksa.kd_dokter=? "+
                    "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psdetailobat=koneksi.prepareStatement(
                    "select sum(detail_pemberian_obat.total) from detail_pemberian_obat inner join reg_periksa "+
                    "on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) "+
                    "and reg_periksa.no_rawat=? and reg_periksa.kd_dokter=? "+
                    "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psdetailobatlangsung=koneksi.prepareStatement(
                    "select sum(tagihan_obat_langsung.besar_tagihan) from tagihan_obat_langsung inner join reg_periksa "+
                    "on tagihan_obat_langsung.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "tagihan_obat_langsung.no_rawat=? and reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=? ");            
            psdetailtambahan=koneksi.prepareStatement(
                    "select sum(tambahan_biaya.besar_biaya) from tambahan_biaya inner join reg_periksa "+
                    "on tambahan_biaya.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "tambahan_biaya.no_rawat=? and reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psdetailpotongan=koneksi.prepareStatement(
                    "select sum(pengurangan_biaya.besar_pengurangan) from pengurangan_biaya inner join reg_periksa "+
                    "on pengurangan_biaya.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and "+
                    "pengurangan_biaya.no_rawat=? and reg_periksa.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psregistrasi=koneksi.prepareStatement(
                    "select sum(reg_periksa.biaya_reg) from reg_periksa where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and reg_periksa.kd_dokter=? "+
                    "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psdetailregistrasi=koneksi.prepareStatement(
                    "select sum(reg_periksa.biaya_reg) from reg_periksa where reg_periksa.no_rawat not in(select no_rawat from kamar_inap) and reg_periksa.no_rawat=? and reg_periksa.kd_dokter=? "+
                    "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            pscarabayar=koneksi.prepareStatement("select png_jawab from penjab where kd_pj=?");
        } catch (Exception e) {
            System.out.println(e);
        }     
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppTampilkanPasien = new javax.swing.JMenuItem();
        ppTampilkanSeleksi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppTampilkanPasien.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanPasien.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanPasien.setText("Tampilkan Pasien");
        ppTampilkanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanPasien.setIconTextGap(5);
        ppTampilkanPasien.setName("ppTampilkanPasien"); // NOI18N
        ppTampilkanPasien.setPreferredSize(new java.awt.Dimension(250, 26));
        ppTampilkanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanPasienBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanPasien);

        ppTampilkanSeleksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanSeleksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanSeleksi.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Pasien Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setIconTextGap(5);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(250, 26));
        ppTampilkanSeleksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanSeleksiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanSeleksi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Harian Dokter Per Poli (Non Operasi & VK) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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
        tbDokter.setToolTipText("");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Tindakan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi4.add(Tgl2);

        label17.setText("Poli :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);

        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setPreferredSize(new java.awt.Dimension(70, 23));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        panelisi4.add(kdpoli);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmpoli);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelisi4.add(BtnSeek2);

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
        panelisi4.add(BtnCari);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(450, 30));
        panelisi1.add(label9);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,10).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Pemasukan Perpoli Dokter"); 
            }
            
            Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRBTindakanPoli.jasper","report","[ Rekap Harian Dokter Per Poli ]",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText()); 
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpoliKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdpoli.setText("");
        nmpoli.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kdpoli, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void ppTampilkanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanPasienBtnPrintActionPerformed
     prosesCari2();
}//GEN-LAST:event_ppTampilkanPasienBtnPrintActionPerformed

    private void ppTampilkanSeleksiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanSeleksiBtnPrintActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_ppTampilkanSeleksiBtnPrintActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdpoli);
    }//GEN-LAST:event_Tgl2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRBTindakanPoli dialog = new DlgRBTindakanPoli(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpoli;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmpoli;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppTampilkanPasien;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {            
        try{   
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
           Valid.tabelKosong(tabMode); 
           pspoli.setString(1,"%"+kdpoli.getText()+"%"); 
           rspoli=pspoli.executeQuery();
           i=1;
           ttlbiaya=0;
           ttljm=0;
           ttllaborat=0;
           ttlobat=0;
           ttlobatlangsung=0;
           ttlpotongan=0;
           ttlregistrasi=0;
           ttltambahan=0;
           ttlradiologi=0;
           while(rspoli.next()){
               tabMode.addRow(new Object[]{i+". ",rspoli.getString(2),"","","","","","","","",""});
               psdokter.setString(1,rspoli.getString(1));
               psdokter.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
               psdokter.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
               rsdokter=psdokter.executeQuery();
               a=1;
               while(rsdokter.next()){
                   jm=0;
                   pstindakan.setString(1,rsdokter.getString(1));
                   pstindakan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pstindakan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pstindakan.setString(4,rspoli.getString(1));
                   rstindakan=pstindakan.executeQuery();
                   if(rstindakan.next()){
                      jm=rstindakan.getDouble(1); 
                   }
                   jm2=0;
                   pstindakan2.setString(1,rsdokter.getString(1));
                   pstindakan2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pstindakan2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pstindakan2.setString(4,rspoli.getString(1));
                   rstindakan=pstindakan2.executeQuery();
                   if(rstindakan.next()){
                      jm2=rstindakan.getDouble(1); 
                   }
                   ttljm=ttljm+jm+jm2;
                   
                   obat=0;
                   psobat.setString(1,rsdokter.getString(1));
                   psobat.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psobat.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psobat.setString(4,rspoli.getString(1));
                   rsobat=psobat.executeQuery();
                   if(rsobat.next()){
                      obat=rsobat.getDouble(1); 
                   }
                   ttlobat=ttlobat+obat;
                   
                   obatlangsung=0;
                   psobatlangsung.setString(1,rsdokter.getString(1));
                   psobatlangsung.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psobatlangsung.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psobatlangsung.setString(4,rspoli.getString(1));
                   rsobatlangsung=psobatlangsung.executeQuery();
                   if(rsobatlangsung.next()){
                      obatlangsung=rsobatlangsung.getDouble(1); 
                   }
                   ttlobatlangsung=ttlobatlangsung+obatlangsung;
                   
                   laborat=0;
                   pslaborat.setString(1,rsdokter.getString(1));
                   pslaborat.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pslaborat.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pslaborat.setString(4,rspoli.getString(1));
                   rslaborat=pslaborat.executeQuery();
                   if(rslaborat.next()){
                      laborat=rslaborat.getDouble(1); 
                   }
                   itemlaborat=0;
                   psitemlaborat.setString(1,rsdokter.getString(1));
                   psitemlaborat.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psitemlaborat.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psitemlaborat.setString(4,rspoli.getString(1));
                   rsitemlaborat=psitemlaborat.executeQuery();
                   if(rsitemlaborat.next()){
                      itemlaborat=rsitemlaborat.getDouble(1); 
                   }
                   ttllaborat=ttllaborat+laborat+itemlaborat;
                   
                   tambahan=0;
                   pstambahan.setString(1,rsdokter.getString(1));
                   pstambahan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pstambahan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pstambahan.setString(4,rspoli.getString(1));
                   rstambahan=pstambahan.executeQuery();
                   if(rstambahan.next()){
                      tambahan=rstambahan.getDouble(1); 
                   }
                   ttltambahan=ttltambahan+tambahan;
                   
                   potongan=0;
                   pspotongan.setString(1,rsdokter.getString(1));
                   pspotongan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pspotongan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pspotongan.setString(4,rspoli.getString(1));
                   rspotongan=pspotongan.executeQuery();
                   if(rspotongan.next()){
                      potongan=rspotongan.getDouble(1); 
                   }
                   ttlpotongan=ttlpotongan+potongan;
                   
                   registrasi=0;
                   psregistrasi.setString(1,rsdokter.getString(1));
                   psregistrasi.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psregistrasi.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psregistrasi.setString(4,rspoli.getString(1));
                   rsregistrasi=psregistrasi.executeQuery();
                   if(rsregistrasi.next()){
                      registrasi=rsregistrasi.getDouble(1); 
                   }  
                   ttlregistrasi=ttlregistrasi+registrasi;
                   
                   radiologi=0;
                   psradiologi.setString(1,rsdokter.getString(1));
                   psradiologi.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psradiologi.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psradiologi.setString(4,rspoli.getString(1));
                   rsradiologi=psradiologi.executeQuery();
                   if(rsradiologi.next()){
                      radiologi=rsradiologi.getDouble(1); 
                   }
                   ttlradiologi=ttlradiologi+radiologi;
                   
                   ttlbiaya=ttlbiaya+jm+jm2+obat+obatlangsung+laborat+radiologi+itemlaborat+tambahan+registrasi-potongan;
                   tabMode.addRow(new Object[]{"",a+". "+rsdokter.getString(2),rsdokter.getString(3),Valid.SetAngka(obat+obatlangsung),Valid.SetAngka(laborat+itemlaborat),Valid.SetAngka(jm+jm2),Valid.SetAngka(tambahan),Valid.SetAngka(potongan),Valid.SetAngka(registrasi),Valid.SetAngka(radiologi),Valid.SetAngka(jm+jm2+obat+obatlangsung+laborat+radiologi+itemlaborat+tambahan+registrasi-potongan)});
                   a++;
               }
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total :","",Valid.SetAngka(ttlobat+ttlobatlangsung),Valid.SetAngka(ttllaborat),Valid.SetAngka(ttljm),Valid.SetAngka(ttltambahan),Valid.SetAngka(ttlpotongan),Valid.SetAngka(ttlregistrasi),Valid.SetAngka(ttlradiologi),Valid.SetAngka(ttlbiaya)});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }
        
    }
    
    private void prosesCari2() {            
        try{ 
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
           Valid.tabelKosong(tabMode); 
           pspoli.setString(1,"%"+kdpoli.getText()+"%"); 
           rspoli=pspoli.executeQuery();
           i=1;
           ttlbiaya=0;
           ttljm=0;
           ttllaborat=0;
           ttlobat=0;
           ttlobatlangsung=0;
           ttlpotongan=0;
           ttlregistrasi=0;
           ttltambahan=0;
           ttlradiologi=0;
           while(rspoli.next()){
               tabMode.addRow(new Object[]{i+". ",rspoli.getString(2),"","","","","","","","",""});
               psdokter.setString(1,rspoli.getString(1));
               psdokter.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
               psdokter.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
               rsdokter=psdokter.executeQuery();
               a=1;
               while(rsdokter.next()){
                   jm=0;
                   pstindakan.setString(1,rsdokter.getString(1));
                   pstindakan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pstindakan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pstindakan.setString(4,rspoli.getString(1));
                   rstindakan=pstindakan.executeQuery();
                   if(rstindakan.next()){
                      jm=rstindakan.getDouble(1); 
                   }
                   jm2=0;
                   pstindakan2.setString(1,rsdokter.getString(1));
                   pstindakan2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pstindakan2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pstindakan2.setString(4,rspoli.getString(1));
                   rstindakan=pstindakan2.executeQuery();
                   if(rstindakan.next()){
                      jm2=rstindakan.getDouble(1); 
                   }
                   ttljm=ttljm+jm+jm2;
                   
                   obat=0;
                   psobat.setString(1,rsdokter.getString(1));
                   psobat.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psobat.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psobat.setString(4,rspoli.getString(1));
                   rsobat=psobat.executeQuery();
                   if(rsobat.next()){
                      obat=rsobat.getDouble(1); 
                   }
                   ttlobat=ttlobat+obat;
                   
                   obatlangsung=0;
                   psobatlangsung.setString(1,rsdokter.getString(1));
                   psobatlangsung.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psobatlangsung.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psobatlangsung.setString(4,rspoli.getString(1));
                   rsobatlangsung=psobatlangsung.executeQuery();
                   if(rsobatlangsung.next()){
                      obatlangsung=rsobatlangsung.getDouble(1); 
                   }
                   ttlobatlangsung=ttlobatlangsung+obatlangsung;
                   
                   laborat=0;
                   pslaborat.setString(1,rsdokter.getString(1));
                   pslaborat.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pslaborat.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pslaborat.setString(4,rspoli.getString(1));
                   rslaborat=pslaborat.executeQuery();
                   if(rslaborat.next()){
                      laborat=rslaborat.getDouble(1); 
                   }
                   itemlaborat=0;
                   psitemlaborat.setString(1,rsdokter.getString(1));
                   psitemlaborat.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psitemlaborat.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psitemlaborat.setString(4,rspoli.getString(1));
                   rsitemlaborat=psitemlaborat.executeQuery();
                   if(rsitemlaborat.next()){
                      itemlaborat=rsitemlaborat.getDouble(1); 
                   }
                   ttllaborat=ttllaborat+laborat+itemlaborat;
                   
                   tambahan=0;
                   pstambahan.setString(1,rsdokter.getString(1));
                   pstambahan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pstambahan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pstambahan.setString(4,rspoli.getString(1));
                   rstambahan=pstambahan.executeQuery();
                   if(rstambahan.next()){
                      tambahan=rstambahan.getDouble(1); 
                   }
                   ttltambahan=ttltambahan+tambahan;
                   
                   potongan=0;
                   pspotongan.setString(1,rsdokter.getString(1));
                   pspotongan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pspotongan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pspotongan.setString(4,rspoli.getString(1));
                   rspotongan=pspotongan.executeQuery();
                   if(rspotongan.next()){
                      potongan=rspotongan.getDouble(1); 
                   }
                   ttlpotongan=ttlpotongan+potongan;
                   
                   registrasi=0;
                   psregistrasi.setString(1,rsdokter.getString(1));
                   psregistrasi.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psregistrasi.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psregistrasi.setString(4,rspoli.getString(1));
                   rsregistrasi=psregistrasi.executeQuery();
                   if(rsregistrasi.next()){
                      registrasi=rsregistrasi.getDouble(1); 
                   }  
                   ttlregistrasi=ttlregistrasi+registrasi;
                   
                   radiologi=0;
                   psradiologi.setString(1,rsdokter.getString(1));
                   psradiologi.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   psradiologi.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   psradiologi.setString(4,rspoli.getString(1));
                   rsradiologi=psradiologi.executeQuery();
                   if(rsradiologi.next()){
                      radiologi=rsradiologi.getDouble(1); 
                   }
                   ttlradiologi=ttlradiologi+radiologi;
                   
                   ttlbiaya=ttlbiaya+jm+jm2+obat+obatlangsung+laborat+radiologi+itemlaborat+tambahan+registrasi-potongan;
                   tabMode.addRow(new Object[]{"",a+". "+rsdokter.getString(2),rsdokter.getString(3),Valid.SetAngka(obat+obatlangsung),Valid.SetAngka(laborat+itemlaborat),Valid.SetAngka(jm+jm2),Valid.SetAngka(tambahan),Valid.SetAngka(potongan),Valid.SetAngka(registrasi),Valid.SetAngka(radiologi),Valid.SetAngka(jm+jm2+obat+obatlangsung+laborat+radiologi+itemlaborat+tambahan+registrasi-potongan)});
                                                        
                   pspasien.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pspasien.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pspasien.setString(3,rsdokter.getString(1));
                   pspasien.setString(4,rspoli.getString(1));
                   rspasien=pspasien.executeQuery();
                   while(rspasien.next()){
                       detailtindakan=0;
                       psdetailtindakan.setString(1,rspasien.getString(4));
                       psdetailtindakan.setString(2,rsdokter.getString(1));
                       psdetailtindakan.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetailtindakan.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetailtindakan.setString(5,rspoli.getString(1));
                       rsdetailtindakan=psdetailtindakan.executeQuery();
                       if(rsdetailtindakan.next()){
                           detailtindakan=rsdetailtindakan.getDouble(1);
                       }
                       detailtindakan2=0;
                       psdetailtindakan2.setString(1,rspasien.getString(4));
                       psdetailtindakan2.setString(2,rsdokter.getString(1));
                       psdetailtindakan2.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetailtindakan2.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetailtindakan2.setString(5,rspoli.getString(1));
                       rsdetailtindakan=psdetailtindakan2.executeQuery();
                       if(rsdetailtindakan.next()){
                           detailtindakan2=rsdetailtindakan.getDouble(1);
                       }
                       
                       detailobat=0;
                       psdetailobat.setString(1,rspasien.getString(4));
                       psdetailobat.setString(2,rsdokter.getString(1));
                       psdetailobat.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetailobat.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetailobat.setString(5,rspoli.getString(1));
                       rsdetailobat=psdetailobat.executeQuery();
                       if(rsdetailobat.next()){
                           detailobat=rsdetailobat.getDouble(1);
                       }
                       
                       detailobatlangsung=0;
                       psdetailobatlangsung.setString(1,rspasien.getString(4));
                       psdetailobatlangsung.setString(2,rsdokter.getString(1));
                       psdetailobatlangsung.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetailobatlangsung.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetailobatlangsung.setString(5,rspoli.getString(1));
                       rsdetailobatlangsung=psdetailobatlangsung.executeQuery();
                       if(rsdetailobatlangsung.next()){
                           detailobatlangsung=rsdetailobatlangsung.getDouble(1);
                       }
                       
                       detaillaborat=0;
                       psdetaillaborat.setString(1,rspasien.getString(4));
                       psdetaillaborat.setString(2,rsdokter.getString(1));
                       psdetaillaborat.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetaillaborat.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetaillaborat.setString(5,rspoli.getString(1));
                       rsdetaillaborat=psdetaillaborat.executeQuery();
                       if(rsdetaillaborat.next()){
                            detailitemlaborat=0;
                            psdetailitemlaborat.setString(1,rspasien.getString(4));
                            psdetailitemlaborat.setString(2,rsdokter.getString(1));
                            psdetailitemlaborat.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailitemlaborat.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailitemlaborat.setString(5,rspoli.getString(1));
                            rsdetailitemlaborat=psdetailitemlaborat.executeQuery();
                            if(rsdetailitemlaborat.next()){
                                detailitemlaborat=rsdetailitemlaborat.getDouble(1);
                            }
                            detaillaborat=rsdetaillaborat.getDouble(1)+detailitemlaborat;
                       }
                       
                       detailtambahan=0;
                       psdetailtambahan.setString(1,rspasien.getString(4));
                       psdetailtambahan.setString(2,rsdokter.getString(1));
                       psdetailtambahan.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetailtambahan.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetailtambahan.setString(5,rspoli.getString(1));
                       rsdetailtambahan=psdetailtambahan.executeQuery();
                       if(rsdetailtambahan.next()){
                           detailtambahan=rsdetailtambahan.getDouble(1);
                       }
                       
                       detailpotongan=0;
                       psdetailpotongan.setString(1,rspasien.getString(4));
                       psdetailpotongan.setString(2,rsdokter.getString(1));
                       psdetailpotongan.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetailpotongan.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetailpotongan.setString(5,rspoli.getString(1));
                       rsdetailpotongan=psdetailpotongan.executeQuery();
                       if(rsdetailpotongan.next()){
                           detailpotongan=rsdetailpotongan.getDouble(1);
                       }
                       
                       detailregistrasi=0;
                       psdetailregistrasi.setString(1,rspasien.getString(4));
                       psdetailregistrasi.setString(2,rsdokter.getString(1));
                       psdetailregistrasi.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetailregistrasi.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetailregistrasi.setString(5,rspoli.getString(1));
                       rsdetailregistrasi=psdetailregistrasi.executeQuery();
                       if(rsdetailregistrasi.next()){
                           detailregistrasi=rsdetailregistrasi.getDouble(1);
                       }
                       
                       detailradiologi=0;
                       psdetailradiologi.setString(1,rspasien.getString(4));
                       psdetailradiologi.setString(2,rsdokter.getString(1));
                       psdetailradiologi.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                       psdetailradiologi.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                       psdetailradiologi.setString(5,rspoli.getString(1));
                       rsdetailradiologi=psdetailradiologi.executeQuery();
                       if(rsdetailradiologi.next()){
                            detailradiologi=rsdetailradiologi.getDouble(1);
                       }
                       
                       carabayar="";
                       pscarabayar.setString(1,rspasien.getString(5));
                       rscarabayar=pscarabayar.executeQuery();
                       if(rscarabayar.next()){
                           carabayar=rscarabayar.getString(1);
                       }                       
                       
                       tabMode.addRow(new Object[]{"",rspasien.getString(1)+" "+rspasien.getString(3)+" ("+carabayar+")","",Valid.SetAngka(detailobat+detailobatlangsung),Valid.SetAngka(detaillaborat),Valid.SetAngka(detailtindakan+detailtindakan2),
                           Valid.SetAngka(detailtambahan),Valid.SetAngka(detailpotongan),Valid.SetAngka(detailregistrasi),Valid.SetAngka(detailradiologi),Valid.SetAngka(detailtindakan+detailtindakan2+detailobat+detailobatlangsung+detaillaborat+detailtambahan+detailregistrasi-detailpotongan+detailradiologi)});
                   }
                   a++;
               }
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total :","",Valid.SetAngka(ttlobat+ttlobatlangsung),Valid.SetAngka(ttllaborat),Valid.SetAngka(ttljm),Valid.SetAngka(ttltambahan),Valid.SetAngka(ttlpotongan),Valid.SetAngka(ttlregistrasi),Valid.SetAngka(ttlradiologi),Valid.SetAngka(ttlbiaya)});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }
        
    }
    
    private void prosesCari3() {            
        try{   
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
           Valid.tabelKosong(tabMode); 
           pspoli.setString(1,"%"+kdpoli.getText()+"%"); 
           rspoli=pspoli.executeQuery();
           i=1;
           ttlbiaya=0;
           ttljm=0;
           ttllaborat=0;
           ttlobat=0;
           ttlobatlangsung=0;
           ttlpotongan=0;
           ttlregistrasi=0;
           ttlradiologi=0;
           ttltambahan=0;
           while(rspoli.next()){
               tabMode.addRow(new Object[]{i+". ",rspoli.getString(2),"","","","","","","","",""});
               psdokter.setString(1,rspoli.getString(1));
               psdokter.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
               psdokter.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
               rsdokter=psdokter.executeQuery();
               a=1;
               while(rsdokter.next()){                   
                   tabMode.addRow(new Object[]{"",a+". "+rsdokter.getString(2),rsdokter.getString(3),"","","","","","","",""});                                                        
                   pspasien.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                   pspasien.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                   pspasien.setString(3,rsdokter.getString(1));
                   pspasien.setString(4,rspoli.getString(1));
                   rspasien=pspasien.executeQuery();
                   while(rspasien.next()){
                       if(pilihancarabayar.equals(rspasien.getString(5))){
                            detailtindakan=0;
                            psdetailtindakan.setString(1,rspasien.getString(4));
                            psdetailtindakan.setString(2,rsdokter.getString(1));
                            psdetailtindakan.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailtindakan.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailtindakan.setString(5,rspoli.getString(1));
                            rsdetailtindakan=psdetailtindakan.executeQuery();
                            if(rsdetailtindakan.next()){
                                detailtindakan=rsdetailtindakan.getDouble(1);
                            }
                            detailtindakan2=0;
                            psdetailtindakan2.setString(1,rspasien.getString(4));
                            psdetailtindakan2.setString(2,rsdokter.getString(1));
                            psdetailtindakan2.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailtindakan2.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailtindakan2.setString(5,rspoli.getString(1));
                            rsdetailtindakan=psdetailtindakan2.executeQuery();
                            if(rsdetailtindakan.next()){
                                detailtindakan2=rsdetailtindakan.getDouble(1);
                            }
                            ttljm=ttljm+detailtindakan+detailtindakan2;

                            detailobat=0;
                            psdetailobat.setString(1,rspasien.getString(4));
                            psdetailobat.setString(2,rsdokter.getString(1));
                            psdetailobat.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailobat.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailobat.setString(5,rspoli.getString(1));
                            rsdetailobat=psdetailobat.executeQuery();
                            if(rsdetailobat.next()){
                                detailobat=rsdetailobat.getDouble(1);
                            }
                            ttlobat=ttlobat+detailobat;

                            detailobatlangsung=0;
                            psdetailobatlangsung.setString(1,rspasien.getString(4));
                            psdetailobatlangsung.setString(2,rsdokter.getString(1));
                            psdetailobatlangsung.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailobatlangsung.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailobatlangsung.setString(5,rspoli.getString(1));
                            rsdetailobatlangsung=psdetailobatlangsung.executeQuery();
                            if(rsdetailobatlangsung.next()){
                                detailobatlangsung=rsdetailobatlangsung.getDouble(1);
                            }
                            ttlobatlangsung=ttlobatlangsung+detailobatlangsung;

                            detaillaborat=0;
                            psdetaillaborat.setString(1,rspasien.getString(4));
                            psdetaillaborat.setString(2,rsdokter.getString(1));
                            psdetaillaborat.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetaillaborat.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetaillaborat.setString(5,rspoli.getString(1));
                            rsdetaillaborat=psdetaillaborat.executeQuery();
                            if(rsdetaillaborat.next()){
                                 detailitemlaborat=0;
                                 psdetailitemlaborat.setString(1,rspasien.getString(4));
                                 psdetailitemlaborat.setString(2,rsdokter.getString(1));
                                 psdetailitemlaborat.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                 psdetailitemlaborat.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                 psdetailitemlaborat.setString(5,rspoli.getString(1));
                                 rsdetailitemlaborat=psdetailitemlaborat.executeQuery();
                                 if(rsdetailitemlaborat.next()){
                                     detailitemlaborat=rsdetailitemlaborat.getDouble(1);
                                 }
                                 detaillaborat=rsdetaillaborat.getDouble(1)+detailitemlaborat;
                            }
                            ttllaborat=ttllaborat+detaillaborat;

                            detailtambahan=0;
                            psdetailtambahan.setString(1,rspasien.getString(4));
                            psdetailtambahan.setString(2,rsdokter.getString(1));
                            psdetailtambahan.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailtambahan.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailtambahan.setString(5,rspoli.getString(1));
                            rsdetailtambahan=psdetailtambahan.executeQuery();
                            if(rsdetailtambahan.next()){
                                detailtambahan=rsdetailtambahan.getDouble(1);
                            }
                            ttltambahan=ttltambahan+detailtambahan;

                            detailpotongan=0;
                            psdetailpotongan.setString(1,rspasien.getString(4));
                            psdetailpotongan.setString(2,rsdokter.getString(1));
                            psdetailpotongan.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailpotongan.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailpotongan.setString(5,rspoli.getString(1));
                            rsdetailpotongan=psdetailpotongan.executeQuery();
                            if(rsdetailpotongan.next()){
                                detailpotongan=rsdetailpotongan.getDouble(1);
                            }
                            ttlpotongan=ttlpotongan+detailpotongan;

                            detailregistrasi=0;
                            psdetailregistrasi.setString(1,rspasien.getString(4));
                            psdetailregistrasi.setString(2,rsdokter.getString(1));
                            psdetailregistrasi.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailregistrasi.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailregistrasi.setString(5,rspoli.getString(1));
                            rsdetailregistrasi=psdetailregistrasi.executeQuery();
                            if(rsdetailregistrasi.next()){
                                detailregistrasi=rsdetailregistrasi.getDouble(1);
                            }
                            ttlregistrasi=ttlregistrasi+detailregistrasi;
                            
                            detailradiologi=0;
                            psdetailradiologi.setString(1,rspasien.getString(4));
                            psdetailradiologi.setString(2,rsdokter.getString(1));
                            psdetailradiologi.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            psdetailradiologi.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            psdetailradiologi.setString(5,rspoli.getString(1));
                            rsdetailradiologi=psdetailradiologi.executeQuery();
                            if(rsdetailradiologi.next()){
                                 detailradiologi=rsdetailradiologi.getDouble(1);
                            }
                            ttlradiologi=ttlradiologi+detailradiologi;
                            
                            ttlbiaya=ttlbiaya+detaillaborat+detailradiologi+detailobat+detailobatlangsung-detailpotongan+detailregistrasi+detailtambahan+detailtindakan+detailtindakan2;

                            carabayar="";
                            pscarabayar.setString(1,rspasien.getString(5));
                            rscarabayar=pscarabayar.executeQuery();
                            if(rscarabayar.next()){
                                carabayar=rscarabayar.getString(1);
                            }   
                            tabMode.addRow(new Object[]{"",rspasien.getString(1)+" "+rspasien.getString(3)+" ("+carabayar+")","",Valid.SetAngka(detailobat+detailobatlangsung),Valid.SetAngka(detaillaborat),Valid.SetAngka(detailtindakan+detailtindakan2),
                                Valid.SetAngka(detailtambahan),Valid.SetAngka(detailpotongan),Valid.SetAngka(detailregistrasi),Valid.SetAngka(detailradiologi),Valid.SetAngka(detailtindakan+detailtindakan2+detailobat+detailobatlangsung+detaillaborat+detailradiologi+detailtambahan+detailregistrasi-detailpotongan)});
                       }                       
                   }
                   a++;
               }
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total ",":",Valid.SetAngka(ttlobat+ttlobatlangsung),Valid.SetAngka(ttllaborat),Valid.SetAngka(ttljm),Valid.SetAngka(ttltambahan),Valid.SetAngka(ttlpotongan),Valid.SetAngka(ttlregistrasi),Valid.SetAngka(ttlradiologi),Valid.SetAngka(ttlbiaya)});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }
        
    }
    
}

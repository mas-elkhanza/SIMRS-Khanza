/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPerawatan.java
 *
 * Created on May 23, 2010, 6:36:30 PM
 */

package simrskhanza;

import keuangan.DlgJnsPerawatanLab;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

/**
 *
 * @author dosen
 */
public final class DlgUbahPeriksaLab extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private boolean sukses=false;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstindakan,pstindakan2,pstampil,pstampil2,pstampil3,pstampil4,pscariperawatan,psset_tarif,pscariperiksa,pscaridetailperiksa,psrekening;
    private ResultSet rstindakan,rstampil,rscari,rsset_tarif,rssetpj,rsrekening;
    private boolean[] pilih,pilih2; 
    private String[] kode,nama,pemeriksaan2,hasil2,satuan2,nilai_rujukan2,keterangan2,idtemplate2;
    private double[] total,bagian_rs,bhp,tarif_perujuk,tarif_tindakan_dokter,tarif_tindakan_petugas,kso,menejemen,
            biaya_item2,bagian_rs2,bhp2,bagian_perujuk2,bagian_dokter2,bagian_laborat2,kso2,menejemen2;
    private int jml=0,i=0,index=0,jml2=0,i2=0,index2=0;
    private String cara_bayar_lab="Yes";
    private double ttl=0,item=0;    
    private Jurnal jur=new Jurnal();
    private double ttljmdokter=0,ttljmpetugas=0,ttlkso=0,ttlpendapatan=0,ttlbhp=0,ttljasasarana=0,ttljmperujuk=0,ttlmenejemen=0;
    private String Suspen_Piutang_Laborat_Ranap="",Laborat_Ranap="",Beban_Jasa_Medik_Dokter_Laborat_Ranap="",Utang_Jasa_Medik_Dokter_Laborat_Ranap="",
            Beban_Jasa_Medik_Petugas_Laborat_Ranap="",Utang_Jasa_Medik_Petugas_Laborat_Ranap="",Beban_Kso_Laborat_Ranap="",Utang_Kso_Laborat_Ranap="",
            HPP_Persediaan_Laborat_Rawat_inap="",Persediaan_BHP_Laborat_Rawat_Inap="",Beban_Jasa_Sarana_Laborat_Ranap="",Utang_Jasa_Sarana_Laborat_Ranap="",
            Beban_Jasa_Perujuk_Laborat_Ranap="",Utang_Jasa_Perujuk_Laborat_Ranap="",Beban_Jasa_Menejemen_Laborat_Ranap="",Utang_Jasa_Menejemen_Laborat_Ranap="",
            Suspen_Piutang_Laborat_Ralan="",Laborat_Ralan="",Beban_Jasa_Medik_Dokter_Laborat_Ralan="",Utang_Jasa_Medik_Dokter_Laborat_Ralan="",
            Beban_Jasa_Medik_Petugas_Laborat_Ralan="",Utang_Jasa_Medik_Petugas_Laborat_Ralan="",Beban_Kso_Laborat_Ralan="",Utang_Kso_Laborat_Ralan="",
            HPP_Persediaan_Laborat_Rawat_Jalan="",Persediaan_BHP_Laborat_Rawat_Jalan="",Beban_Jasa_Sarana_Laborat_Ralan="",Utang_Jasa_Sarana_Laborat_Ralan="",
            Beban_Jasa_Perujuk_Laborat_Ralan="",Utang_Jasa_Perujuk_Laborat_Ralan="",Beban_Jasa_Menejemen_Laborat_Ralan="",Utang_Jasa_Menejemen_Laborat_Ralan="",status="";
    

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public DlgUbahPeriksaLab(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"P","Pemeriksaan","Hasil","Satuan","Nilai Rujukan","Keterangan","","","","","","","","",""};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if ((colIndex==0)||(colIndex==2)||(colIndex==4)||(colIndex==5)||(colIndex==7)) {
                        a=true;
                    }
                    return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }              
        };
        
        tbPemeriksaan.setModel(tabMode);
        //tampilPr();

        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(192);
            }else if(i==2){
                column.setPreferredWidth(130);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(130);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());
        
        Object[] row2={"P","Kode Periksa","Nama Pemeriksaan","Tarif","bagian_rs","bhp","tarif_perujuk","tarif_tindakan_dokter","tarif_tindakan_petugas","K.S.O.","Menejemen"};
        tabMode2=new DefaultTableModel(null,row2){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==3)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTarif.setModel(tabMode2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTarif.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTarif.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 11; i++) {
            TableColumn column = tbTarif.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(380);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbTarif.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Jk.setDocument(new batasInput((byte)5).getKata(Jk));
        KdPtg.setDocument(new batasInput((byte)20).getKata(KdPtg));
        KodePerujuk.setDocument(new batasInput((byte)20).getKata(KodePerujuk));
        Pemeriksaan.setDocument(new batasInput((byte)100).getKata(Pemeriksaan));    
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));      
        if(koneksiDB.CARICEPAT().equals("aktif")){
            Pemeriksaan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(Pemeriksaan.getText().length()>2){
                        tampiltarif();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(Pemeriksaan.getText().length()>2){
                        tampiltarif();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(Pemeriksaan.getText().length()>2){
                        tampiltarif();
                    }
                }
            });
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
        try {            
            pstindakan=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                    "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                    "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                    "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                    "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                    " jns_perawatan_lab.kd_pj like ? and jns_perawatan_lab.kd_jenis_prw like ? or "+
                    " jns_perawatan_lab.kd_pj like ? and jns_perawatan_lab.nm_perawatan like ? "+
                    "order by jns_perawatan_lab.kd_jenis_prw");
            pstindakan2=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                    "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                    "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                    "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                    "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                    " jns_perawatan_lab.kd_jenis_prw like ? or "+
                    " jns_perawatan_lab.nm_perawatan like ?  "+
                    "order by jns_perawatan_lab.kd_jenis_prw"); 
            pstampil=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_ld,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen from template_laboratorium where kd_jenis_prw=? order by id_template");
            pstampil2=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_la,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen from template_laboratorium where kd_jenis_prw=? order by id_template");
            pstampil3=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_pd,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen from template_laboratorium where kd_jenis_prw=? order by id_template");
            pstampil4=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_pa,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen from template_laboratorium where kd_jenis_prw=? order by id_template");
            pscariperawatan=koneksi.prepareStatement("select kd_jenis_prw from template_laboratorium where id_template=?");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try {
            psrekening=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Laborat_Ranap=rsrekening.getString("Suspen_Piutang_Laborat_Ranap");
                    Laborat_Ranap=rsrekening.getString("Laborat_Ranap");
                    Beban_Jasa_Medik_Dokter_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Laborat_Ranap");
                    Utang_Jasa_Medik_Dokter_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ranap");
                    Beban_Jasa_Medik_Petugas_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Medik_Petugas_Laborat_Ranap");
                    Utang_Jasa_Medik_Petugas_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Medik_Petugas_Laborat_Ranap");
                    Beban_Kso_Laborat_Ranap=rsrekening.getString("Beban_Kso_Laborat_Ranap");
                    Utang_Kso_Laborat_Ranap=rsrekening.getString("Utang_Kso_Laborat_Ranap");
                    HPP_Persediaan_Laborat_Rawat_inap=rsrekening.getString("HPP_Persediaan_Laborat_Rawat_inap");
                    Persediaan_BHP_Laborat_Rawat_Inap=rsrekening.getString("Persediaan_BHP_Laborat_Rawat_Inap");
                    Beban_Jasa_Sarana_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Sarana_Laborat_Ranap");
                    Utang_Jasa_Sarana_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Sarana_Laborat_Ranap");
                    Beban_Jasa_Perujuk_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Perujuk_Laborat_Ranap");
                    Utang_Jasa_Perujuk_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Perujuk_Laborat_Ranap");
                    Beban_Jasa_Menejemen_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Menejemen_Laborat_Ranap");
                    Utang_Jasa_Menejemen_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Menejemen_Laborat_Ranap");
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
            
            psrekening=koneksi.prepareStatement("select * from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Laborat_Ralan=rsrekening.getString("Suspen_Piutang_Laborat_Ralan");
                    Laborat_Ralan=rsrekening.getString("Laborat_Ralan");
                    Beban_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Laborat_Ralan");
                    Utang_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan");
                    Beban_Jasa_Medik_Petugas_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Medik_Petugas_Laborat_Ralan");
                    Utang_Jasa_Medik_Petugas_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Medik_Petugas_Laborat_Ralan");
                    Beban_Kso_Laborat_Ralan=rsrekening.getString("Beban_Kso_Laborat_Ralan");
                    Utang_Kso_Laborat_Ralan=rsrekening.getString("Utang_Kso_Laborat_Ralan");
                    HPP_Persediaan_Laborat_Rawat_Jalan=rsrekening.getString("HPP_Persediaan_Laborat_Rawat_Jalan");
                    Persediaan_BHP_Laborat_Rawat_Jalan=rsrekening.getString("Persediaan_BHP_Laborat_Rawat_Jalan");
                    Beban_Jasa_Sarana_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Laborat_Ralan");
                    Utang_Jasa_Sarana_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Laborat_Ralan");
                    Beban_Jasa_Perujuk_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Perujuk_Laborat_Ralan");
                    Utang_Jasa_Perujuk_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Perujuk_Laborat_Ralan");
                    Beban_Jasa_Menejemen_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Laborat_Ralan");
                    Utang_Jasa_Menejemen_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Laborat_Ralan");
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
    
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        Jk = new widget.TextBox();
        Penjab = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        jLabel10 = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        PanelInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel11 = new widget.Label();
        jLabel7 = new widget.Label();
        jLabel9 = new widget.Label();
        Pemeriksaan = new widget.TextBox();
        jLabel12 = new widget.Label();
        KdPtg = new widget.TextBox();
        NmPtg = new widget.TextBox();
        jLabel16 = new widget.Label();
        BtnCari1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbTarif = new widget.Table();
        btnTarif = new widget.Button();
        rbAnak = new widget.RadioButton();
        rbDewasa = new widget.RadioButton();
        NmDokterPj = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        KodePj = new widget.TextBox();
        KodePerujuk = new widget.TextBox();
        NmPerujuk = new widget.TextBox();
        jLabel15 = new widget.Label();
        PanelCariUtama = new javax.swing.JPanel();
        Scroll = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        panelGlass11 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari2 = new widget.Button();

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        Penjab.setEditable(false);
        Penjab.setFocusTraversalPolicyProvider(true);
        Penjab.setName("Penjab"); // NOI18N
        Penjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenjabKeyPressed(evt);
            }
        });

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50,50,50));
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
        ppSemua.setForeground(new java.awt.Color(50,50,50));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Data Hasil Periksa Laboratorium ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
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

        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(460, 30));
        panelGlass8.add(jLabel10);

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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setOpaque(false);
        FormInput.setPreferredSize(new java.awt.Dimension(560, 269));
        FormInput.setLayout(new java.awt.BorderLayout(1, 1));

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
        FormInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 168));
        PanelInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        PanelInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 92, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        PanelInput.add(TNoRw);
        TNoRw.setBounds(95, 12, 148, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        PanelInput.add(TNoRM);
        TNoRM.setBounds(245, 12, 125, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        PanelInput.add(TPasien);
        TPasien.setBounds(372, 12, 400, 23);

        jLabel11.setText("Pemeriksaan :");
        jLabel11.setName("jLabel11"); // NOI18N
        PanelInput.add(jLabel11);
        jLabel11.setBounds(0, 102, 92, 23);

        jLabel7.setText("Dokter P.J. :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 42, 92, 23);

        jLabel9.setText("Dokter Perujuk :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(0, 72, 92, 23);

        Pemeriksaan.setHighlighter(null);
        Pemeriksaan.setName("Pemeriksaan"); // NOI18N
        Pemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanKeyPressed(evt);
            }
        });
        PanelInput.add(Pemeriksaan);
        Pemeriksaan.setBounds(245, 102, 465, 23);

        jLabel12.setText("Petugas Lab :");
        jLabel12.setName("jLabel12"); // NOI18N
        PanelInput.add(jLabel12);
        jLabel12.setBounds(375, 42, 87, 23);

        KdPtg.setEditable(false);
        KdPtg.setName("KdPtg"); // NOI18N
        PanelInput.add(KdPtg);
        KdPtg.setBounds(464, 42, 80, 23);

        NmPtg.setEditable(false);
        NmPtg.setName("NmPtg"); // NOI18N
        PanelInput.add(NmPtg);
        NmPtg.setBounds(546, 42, 226, 23);

        jLabel16.setText("Jam :");
        jLabel16.setName("jLabel16"); // NOI18N
        PanelInput.add(jLabel16);
        jLabel16.setBounds(533, 72, 78, 23);

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
        PanelInput.add(BtnCari1);
        BtnCari1.setBounds(713, 102, 28, 23);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N

        tbTarif.setName("tbTarif"); // NOI18N
        tbTarif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTarifMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbTarif);

        PanelInput.add(Scroll1);
        Scroll1.setBounds(95, 127, 677, 110);

        btnTarif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnTarif.setMnemonic('2');
        btnTarif.setToolTipText("Alt+2");
        btnTarif.setName("btnTarif"); // NOI18N
        btnTarif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifActionPerformed(evt);
            }
        });
        PanelInput.add(btnTarif);
        btnTarif.setBounds(744, 102, 28, 23);

        buttonGroup1.add(rbAnak);
        rbAnak.setText("Anak-Anak");
        rbAnak.setIconTextGap(1);
        rbAnak.setName("rbAnak"); // NOI18N
        rbAnak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbAnakMouseClicked(evt);
            }
        });
        PanelInput.add(rbAnak);
        rbAnak.setBounds(165, 102, 80, 23);

        buttonGroup1.add(rbDewasa);
        rbDewasa.setSelected(true);
        rbDewasa.setText("Dewasa");
        rbDewasa.setIconTextGap(1);
        rbDewasa.setName("rbDewasa"); // NOI18N
        rbDewasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbDewasaMouseClicked(evt);
            }
        });
        PanelInput.add(rbDewasa);
        rbDewasa.setBounds(95, 102, 80, 23);

        NmDokterPj.setEditable(false);
        NmDokterPj.setHighlighter(null);
        NmDokterPj.setName("NmDokterPj"); // NOI18N
        PanelInput.add(NmDokterPj);
        NmDokterPj.setBounds(177, 42, 180, 23);

        Tanggal.setEditable(false);
        Tanggal.setName("Tanggal"); // NOI18N
        PanelInput.add(Tanggal);
        Tanggal.setBounds(464, 72, 110, 23);

        Jam.setEditable(false);
        Jam.setName("Jam"); // NOI18N
        PanelInput.add(Jam);
        Jam.setBounds(614, 72, 96, 23);

        KodePj.setEditable(false);
        KodePj.setHighlighter(null);
        KodePj.setName("KodePj"); // NOI18N
        PanelInput.add(KodePj);
        KodePj.setBounds(95, 42, 80, 23);

        KodePerujuk.setEditable(false);
        KodePerujuk.setName("KodePerujuk"); // NOI18N
        PanelInput.add(KodePerujuk);
        KodePerujuk.setBounds(95, 72, 80, 23);

        NmPerujuk.setEditable(false);
        NmPerujuk.setHighlighter(null);
        NmPerujuk.setName("NmPerujuk"); // NOI18N
        PanelInput.add(NmPerujuk);
        NmPerujuk.setBounds(177, 72, 180, 23);

        jLabel15.setText("Tgl.Periksa :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(375, 72, 87, 23);

        FormInput.add(PanelInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 143));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        Scroll.setViewportView(tbPemeriksaan);

        PanelCariUtama.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 43));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel6.setText("Detail Pemeriksaan :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(625, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('4');
        BtnCari2.setToolTipText("Alt+4");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelGlass11.add(BtnCari2);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        jml=0;
        for(i=0;i<tbTarif.getRowCount();i++){
            if(tbTarif.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdPtg.getText().equals("")||NmPtg.getText().equals("")){
            Valid.textKosong(KdPtg,"Petugas");
        }else if(KodePerujuk.getText().equals("")||NmPerujuk.getText().equals("")){
            Valid.textKosong(KodePerujuk,"Dokter Perujuk");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(Pemeriksaan,"Data Pemeriksaan");
        }else if(jml==0){
            Valid.textKosong(Pemeriksaan,"Data Pemeriksaan");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {                
                    Sequel.AutoComitFalse();
                    sukses=true;
                    status="";
                    ttljmdokter=0;ttljmpetugas=0;ttlkso=0;ttlpendapatan=0;ttlbhp=0;ttljasasarana=0;ttljmperujuk=0;ttlmenejemen=0;
                    ttljmdokter=Sequel.cariIsiAngka("select sum(tarif_tindakan_dokter) from periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttljmpetugas=Sequel.cariIsiAngka("select sum(tarif_tindakan_petugas) from periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttlkso=Sequel.cariIsiAngka("select sum(kso) from periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttlbhp=Sequel.cariIsiAngka("select sum(bhp) from periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttlpendapatan=Sequel.cariIsiAngka("select sum(biaya) from periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttljasasarana=Sequel.cariIsiAngka("select sum(bagian_rs) from periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttljmperujuk=Sequel.cariIsiAngka("select sum(tarif_perujuk) from periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttlmenejemen=Sequel.cariIsiAngka("select sum(menejemen) from periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    
                    ttljmdokter=ttljmdokter+Sequel.cariIsiAngka("select sum(bagian_dokter) from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttljmpetugas=ttljmpetugas+Sequel.cariIsiAngka("select sum(bagian_laborat) from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttlkso=ttlkso+Sequel.cariIsiAngka("select sum(kso) from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttlbhp=ttlbhp+Sequel.cariIsiAngka("select sum(bhp) from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttlpendapatan=ttlpendapatan+Sequel.cariIsiAngka("select sum(biaya_item) from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttljasasarana=ttljasasarana+Sequel.cariIsiAngka("select sum(bagian_rs) from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttljmperujuk=ttljmperujuk+Sequel.cariIsiAngka("select sum(bagian_perujuk) from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    ttlmenejemen=ttlmenejemen+Sequel.cariIsiAngka("select sum(menejemen) from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                              "' and tgl_periksa='"+Tanggal.getText() +
                              "' and jam='"+Jam.getText() +"'");
                    
                    status=Sequel.cariIsi("select status from periksa_lab where no_rawat='"+TNoRw.getText() +
                                  "' and tgl_periksa='"+Tanggal.getText() +
                                  "' and jam='"+Jam.getText() +"'");

                    if(Sequel.queryutf("delete from periksa_lab where no_rawat='"+TNoRw.getText() +
                                  "' and tgl_periksa='"+Tanggal.getText() +
                                  "' and jam='"+Jam.getText() +"'")==true){                    
                        if(Sequel.queryutf("delete from detail_periksa_lab where no_rawat='"+TNoRw.getText() +
                                  "' and tgl_periksa='"+Tanggal.getText() +
                                  "' and jam='"+Jam.getText() +"'")==false){
                                sukses=false;                   
                        }
                    }else{
                        sukses=false;
                    }
                    
                    if(sukses==true){
                        if(status.equals("Ranap")){
                            Sequel.queryu("delete from tampjurnal");    
                            if(ttlpendapatan>0){
                                Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Laborat_Ranap+"','Suspen Piutang Laborat Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','Pendapatan Laborat Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Laborat_Ranap+"'");                              
                            }
                            if(ttljmdokter>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"','Beban Jasa Medik Dokter Laborat Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"'");    
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang Jasa Medik Dokter Laborat Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'");                           
                            }
                            if(ttljmpetugas>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"','Beban Jasa Medik Petugas Laborat Ranap','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"'");    
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"','Utang Jasa Medik Petugas Laborat Ranap','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"'");                             
                            }
                            if(ttlbhp>0){
                                Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_inap+"','HPP Persediaan Laborat Rawat Inap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_inap+"'");   
                                Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Inap+"','Persediaan BHP Laborat Rawat Inap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Laborat_Rawat_Inap+"'");                                
                            }
                            if(ttlkso>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ranap+"','HPP Persediaan Laborat Rawat Inap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_Kso_Laborat_Ranap+"'");   
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ranap+"','Persediaan BHP Laborat Rawat Inap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_Kso_Laborat_Ranap+"'");                                
                            }
                            if(ttljasasarana>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ranap+"','Beban Jasa Sarana Laborat Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ranap+"'");   
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ranap+"','Utang Jasa Sarana Laborat Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ranap+"'");                              
                            }
                            if(ttljmperujuk>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ranap+"','Beban Jasa Perujuk Laborat Ranap','0','"+ttljmperujuk+"'","kredit=kredit+'"+(ttljmperujuk)+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ranap+"'");   
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ranap+"','Utang Jasa Perujuk Laborat Ranap','"+ttljmperujuk+"','0'","debet=debet+'"+(ttljmperujuk)+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ranap+"'");                               
                            }
                            if(ttlmenejemen>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ranap+"','Beban Jasa Menejemen Laborat Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ranap+"','Utang Jasa Menejemen Laborat Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ranap+"'");                               
                            }
                            sukses=jur.simpanJurnal(TNoRw.getText(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT INAP PASIEN OLEH "+akses.getkode());  
                        }else if(status.equals("Ralan")){
                            Sequel.queryu("delete from tampjurnal");    
                            if(ttlpendapatan>0){
                                Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Laborat_Ralan+"','Suspen Piutang Laborat Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','Pendapatan Laborat Rawat Jalan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Laborat_Ralan+"'");                              
                            }
                            if(ttljmdokter>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Beban Jasa Medik Dokter Laborat Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");    
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang Jasa Medik Dokter Laborat Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");                           
                            }
                            if(ttljmpetugas>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Beban Jasa Medik Petugas Laborat Ralan','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");    
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Utang Jasa Medik Petugas Laborat Ralan','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");                             
                            }
                            if(ttlbhp>0){
                                Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_Jalan+"','HPP Persediaan Laborat Rawat Jalan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_inap+"'");   
                                Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Jalan+"','Persediaan BHP Laborat Rawat Jalan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Laborat_Rawat_Inap+"'");                                
                            }
                            if(ttlkso>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','HPP Persediaan Laborat Rawat Inap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");   
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Persediaan BHP Laborat Rawat Inap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");                                
                            }
                            if(ttljasasarana>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Beban Jasa Sarana Laborat Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");   
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Utang Jasa Sarana Laborat Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");                              
                            }
                            if(ttljmperujuk>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Beban Jasa Perujuk Laborat Ralan','0','"+ttljmperujuk+"'","kredit=kredit+'"+(ttljmperujuk)+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");   
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Utang Jasa Perujuk Laborat Ralan','"+ttljmperujuk+"','0'","debet=debet+'"+(ttljmperujuk)+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");                               
                            }
                            if(ttlmenejemen>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Beban Jasa Menejemen Laborat Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Utang Jasa Menejemen Laborat Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");                               
                            }
                            sukses=jur.simpanJurnal(TNoRw.getText(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT JALAN PASIEN OLEH "+akses.getkode());  
                        }
                    }
                    
                    if(sukses==true){
                        for(i=0;i<tbTarif.getRowCount();i++){ 
                            if(tbTarif.getValueAt(i,0).toString().equals("true")){
                                if(Sequel.menyimpantf2("periksa_lab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Kode Pemeriksaan",16,new String[]{
                                        TNoRw.getText(),KdPtg.getText(),tbTarif.getValueAt(i,1).toString(),Tanggal.getText(),Jam.getText(),KodePerujuk.getText(),
                                        tbTarif.getValueAt(i,4).toString(),tbTarif.getValueAt(i,5).toString(),tbTarif.getValueAt(i,6).toString(),
                                        tbTarif.getValueAt(i,7).toString(),tbTarif.getValueAt(i,8).toString(),tbTarif.getValueAt(i,9).toString(),
                                        tbTarif.getValueAt(i,10).toString(),tbTarif.getValueAt(i,3).toString(),KodePj.getText(),status  
                                    })==true){
                                        ttlbhp=ttlbhp+Double.parseDouble(tbTarif.getValueAt(i,5).toString());
                                        ttljmdokter=ttljmdokter+Double.parseDouble(tbTarif.getValueAt(i,7).toString());
                                        ttljmpetugas=ttljmpetugas+Double.parseDouble(tbTarif.getValueAt(i,8).toString());
                                        ttlkso=ttlkso+Double.parseDouble(tbTarif.getValueAt(i,9).toString()); 
                                        ttlpendapatan=ttlpendapatan+Double.parseDouble(tbTarif.getValueAt(i,3).toString());      
                                        ttljasasarana=ttljasasarana+Double.parseDouble(tbTarif.getValueAt(i,4).toString());
                                        ttljmperujuk=ttljmperujuk+Double.parseDouble(tbTarif.getValueAt(i,6).toString());
                                        ttlmenejemen=ttlmenejemen+Double.parseDouble(tbTarif.getValueAt(i,10).toString());
                                }else{
                                    sukses=false;
                                }   
                            }                        
                        }                    

                        if(sukses==true){
                            for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                                if((!tbPemeriksaan.getValueAt(i,6).toString().equals(""))&&tbPemeriksaan.getValueAt(i,0).toString().equals("true")){                                
                                    pscariperawatan=koneksi.prepareStatement("select kd_jenis_prw from template_laboratorium where id_template=?");
                                    try {
                                        pscariperawatan.setString(1,tbPemeriksaan.getValueAt(i,6).toString());
                                        rscari=pscariperawatan.executeQuery();
                                        if(rscari.next()){  
                                            if(Sequel.menyimpantf2("detail_periksa_lab","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","ID Template",16,new String[]{
                                                    TNoRw.getText(),rscari.getString(1),Tanggal.getText(),Jam.getText(),
                                                    tbPemeriksaan.getValueAt(i,6).toString(),tbPemeriksaan.getValueAt(i,2).toString(),tbPemeriksaan.getValueAt(i,4).toString(),tbPemeriksaan.getValueAt(i,5).toString(),
                                                    tbPemeriksaan.getValueAt(i,8).toString(),tbPemeriksaan.getValueAt(i,9).toString(),tbPemeriksaan.getValueAt(i,10).toString(),tbPemeriksaan.getValueAt(i,11).toString(),
                                                    tbPemeriksaan.getValueAt(i,12).toString(),tbPemeriksaan.getValueAt(i,13).toString(),tbPemeriksaan.getValueAt(i,14).toString(),tbPemeriksaan.getValueAt(i,7).toString()
                                                })==true){
                                                    ttlbhp=ttlbhp+Double.parseDouble(tbPemeriksaan.getValueAt(i,9).toString());
                                                    ttljmdokter=ttljmdokter+Double.parseDouble(tbPemeriksaan.getValueAt(i,11).toString());
                                                    ttljmpetugas=ttljmpetugas+Double.parseDouble(tbPemeriksaan.getValueAt(i,12).toString());
                                                    ttlkso=ttlkso+Double.parseDouble(tbPemeriksaan.getValueAt(i,13).toString()); 
                                                    ttlpendapatan=ttlpendapatan+Double.parseDouble(tbPemeriksaan.getValueAt(i,7).toString());     
                                                    ttljasasarana=ttljasasarana+Double.parseDouble(tbPemeriksaan.getValueAt(i,8).toString());
                                                    ttljmperujuk=ttljmperujuk+Double.parseDouble(tbPemeriksaan.getValueAt(i,10).toString());
                                                    ttlmenejemen=ttlmenejemen+Double.parseDouble(tbPemeriksaan.getValueAt(i,14).toString()); 
                                            }else{
                                                sukses=false;
                                            }
                                        }                            
                                    } catch (Exception e) {
                                        sukses=false;
                                        System.out.println("Notifikasi 2 : "+e);
                                    } finally{
                                        if(rscari!=null){
                                            rscari.close();
                                        }
                                        if(pscariperawatan!=null){
                                            pscariperawatan.close();
                                        }
                                    }
                                }                        
                            }
                        }   

                        if(sukses==true){
                            if(status.equals("Ranap")){
                                Sequel.queryu("delete from tampjurnal");    
                                if(ttlpendapatan>0){
                                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Laborat_Ranap+"','Suspen Piutang Laborat Ranap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Laborat_Ranap+"'");    
                                    Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','Pendapatan Laborat Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Laborat_Ranap+"'");                                
                                }
                                if(ttljmdokter>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"','Beban Jasa Medik Dokter Laborat Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang Jasa Medik Dokter Laborat Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'");                              
                                }
                                if(ttljmpetugas>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"','Beban Jasa Medik Petugas Laborat Ranap','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"','Utang Jasa Medik Petugas Laborat Ranap','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"'");                              
                                }
                                if(ttlbhp>0){
                                    Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_inap+"','HPP Persediaan Laborat Rawat Inap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_inap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Inap+"','Persediaan BHP Laborat Rawat Inap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Laborat_Rawat_Inap+"'");                             
                                }
                                if(ttlkso>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ranap+"','Beban KSO Laborat Ranap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_Kso_Laborat_Ranap+"'");      
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ranap+"','Utang KSO Laborat Ranap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_Kso_Laborat_Ranap+"'");                              
                                }
                                if(ttljasasarana>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ranap+"','Beban Jasa Sarana Laborat Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ranap+"'");      
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ranap+"','Utang Jasa Sarana Laborat Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ranap+"'");                             
                                }
                                if(ttljmperujuk>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ranap+"','Beban Jasa Perujuk Laborat Ranap','"+ttljmperujuk+"','0'","debet=debet+'"+(ttljmperujuk)+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ranap+"','Utang Jasa Perujuk Laborat Ranap','0','"+ttljmperujuk+"'","kredit=kredit+'"+(ttljmperujuk)+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ranap+"'");                             
                                }
                                if(ttlmenejemen>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ranap+"','Beban Jasa Menejemen Laborat Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ranap+"','Utang Jasa Menejemen Laborat Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ranap+"'");                             
                                }
                                sukses=jur.simpanJurnal(TNoRw.getText(),Sequel.cariIsi("select current_date()"),"U","PEMERIKSAAN LABORAT RAWAT INAP PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode());  
                            }else if(status.equals("Ralan")){
                                Sequel.queryu("delete from tampjurnal");    
                                if(ttlpendapatan>0){
                                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Laborat_Ralan+"','Suspen Piutang Laborat Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Laborat_Ralan+"'");    
                                    Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','Pendapatan Laborat Rawat Jalan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Laborat_Ralan+"'");                                
                                }
                                if(ttljmdokter>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Beban Jasa Medik Dokter Laborat Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang Jasa Medik Dokter Laborat Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");                              
                                }
                                if(ttljmpetugas>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Beban Jasa Medik Petugas Laborat Ralan','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Utang Jasa Medik Petugas Laborat Ralan','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");                              
                                }
                                if(ttlbhp>0){
                                    Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_Jalan+"','HPP Persediaan Laborat Rawat Jalan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_Jalan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Jalan+"','Persediaan BHP Laborat Rawat Jalan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Laborat_Rawat_Jalan+"'");                             
                                }
                                if(ttlkso>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','Beban KSO Laborat Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");      
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Utang KSO Laborat Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");                              
                                }
                                if(ttljasasarana>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Beban Jasa Sarana Laborat Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");      
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Utang Jasa Sarana Laborat Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");                             
                                }
                                if(ttljmperujuk>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Beban Jasa Perujuk Laborat Ralan','"+ttljmperujuk+"','0'","debet=debet+'"+(ttljmperujuk)+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Utang Jasa Perujuk Laborat Ralan','0','"+ttljmperujuk+"'","kredit=kredit+'"+(ttljmperujuk)+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");                             
                                }
                                if(ttlmenejemen>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Beban Jasa Menejemen Laborat Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Utang Jasa Menejemen Laborat Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");                             
                                }
                                sukses=jur.simpanJurnal(TNoRw.getText(),Sequel.cariIsi("select current_date()"),"U","PEMERIKSAAN LABORAT RAWAT JALAN PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode()); 
                            }
                        }
                    }
                    
                    if(sukses==true){
                        Sequel.Commit();
                        JOptionPane.showMessageDialog(null,"Proses ubah hasil periksa selesai...!");
                    }else{
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                        Sequel.RollBack();
                    }
                    Sequel.AutoComitTrue(); 
                    dispose();
                } catch (Exception e) {
                    System.out.println(e);
                }                
            }        
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
               Valid.pindah(evt,Pemeriksaan,BtnHapus);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnHapus,Pemeriksaan);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void PemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampiltarif();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            if(akses.getperiksa_lab()==true){
                btnTarifActionPerformed(null);
            }            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Tanggal.requestFocus();
        }    
}//GEN-LAST:event_PemeriksaanKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
   try{
       tabMode.removeRow(tbPemeriksaan.getSelectedRow());
   }catch(Exception ex){
       JOptionPane.showMessageDialog(null,"Pilih dulu data yang mau dihapus..!!");
   }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
       tampiltarif();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void btnTarifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanLab tariflab=new DlgJnsPerawatanLab(null,false);
        tariflab.emptTeks();
        tariflab.isCek();
        tariflab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        tariflab.setLocationRelativeTo(internalFrame1);
        tariflab.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTarifActionPerformed

    private void tbTarifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTarifMouseClicked
        if(tabMode2.getRowCount()!=0){
            try {
                tampil();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbTarifMouseClicked

    private void rbDewasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbDewasaMouseClicked
        tbTarifMouseClicked(evt);
    }//GEN-LAST:event_rbDewasaMouseClicked

    private void rbAnakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbAnakMouseClicked
        tbTarifMouseClicked(evt);
    }//GEN-LAST:event_rbAnakMouseClicked

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnKeluar);}
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void PenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenjabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenjabKeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbPemeriksaan.getRowCount();i++){
            tbPemeriksaan.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for(i=0;i<tbPemeriksaan.getRowCount();i++){
            tbPemeriksaan.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari2ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari2ActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,Pemeriksaan);
        }
    }//GEN-LAST:event_BtnCari2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgUbahPeriksaLab dialog = new DlgUbahPeriksaLab(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private javax.swing.JPanel FormInput;
    private widget.TextBox Jam;
    private widget.TextBox Jk;
    private widget.TextBox KdPtg;
    private widget.TextBox KodePerujuk;
    private widget.TextBox KodePj;
    private widget.TextBox NmDokterPj;
    private widget.TextBox NmPerujuk;
    private widget.TextBox NmPtg;
    private javax.swing.JPanel PanelCariUtama;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox Pemeriksaan;
    private widget.TextBox Penjab;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox Tanggal;
    private widget.Button btnTarif;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppSemua;
    private widget.RadioButton rbAnak;
    private widget.RadioButton rbDewasa;
    private widget.Table tbPemeriksaan;
    private widget.Table tbTarif;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        try {
            jml2=0;
            for(i2=0;i2<tbPemeriksaan.getRowCount();i2++){
                if(tbPemeriksaan.getValueAt(i2,0).toString().equals("true")){
                    jml2++;
                }
            }
            
            pilih2=null;
            pilih2=new boolean[jml2];
            pemeriksaan2=null;
            pemeriksaan2=new String[jml2];
            hasil2=null;
            hasil2=new String[jml2];
            satuan2=null;
            satuan2=new String[jml2];
            nilai_rujukan2=null;
            nilai_rujukan2=new String[jml2];
            keterangan2=null;
            keterangan2=new String[jml2];
            idtemplate2=null;
            idtemplate2=new String[jml2];
            biaya_item2=null;
            biaya_item2=new double[jml2];
            bagian_rs2=null;
            bagian_rs2=new double[jml2];
            bhp2=null;
            bhp2=new double[jml2];
            bagian_perujuk2=null;
            bagian_perujuk2=new double[jml2];
            bagian_dokter2=null;
            bagian_dokter2=new double[jml2];
            bagian_laborat2=null;
            bagian_laborat2=new double[jml2];
            kso2=null;
            kso2=new double[jml2];
            menejemen2=null;
            menejemen2=new double[jml2];
            
            index2=0; 
            for(i2=0;i2<tbPemeriksaan.getRowCount();i2++){
                if(tbPemeriksaan.getValueAt(i2,0).toString().equals("true")){
                    pilih2[index2]=true;
                    pemeriksaan2[index2]=tbPemeriksaan.getValueAt(i2,1).toString();
                    hasil2[index2]=tbPemeriksaan.getValueAt(i2,2).toString();
                    satuan2[index2]=tbPemeriksaan.getValueAt(i2,3).toString();
                    nilai_rujukan2[index2]=tbPemeriksaan.getValueAt(i2,4).toString();
                    keterangan2[index2]=tbPemeriksaan.getValueAt(i2,5).toString();
                    idtemplate2[index2]=tbPemeriksaan.getValueAt(i2,6).toString();
                    biaya_item2[index2]=Valid.SetAngka(tbPemeriksaan.getValueAt(i2,7).toString());
                    bagian_rs2[index2]=Valid.SetAngka(tbPemeriksaan.getValueAt(i2,8).toString());
                    bhp2[index2]=Valid.SetAngka(tbPemeriksaan.getValueAt(i2,9).toString());
                    bagian_perujuk2[index2]=Valid.SetAngka(tbPemeriksaan.getValueAt(i2,10).toString());
                    bagian_dokter2[index2]=Valid.SetAngka(tbPemeriksaan.getValueAt(i2,11).toString());
                    bagian_laborat2[index2]=Valid.SetAngka(tbPemeriksaan.getValueAt(i2,12).toString());
                    kso2[index2]=Valid.SetAngka(tbPemeriksaan.getValueAt(i2,13).toString());
                    menejemen2[index2]=Valid.SetAngka(tbPemeriksaan.getValueAt(i2,14).toString());                
                    index2++;
                }
            }
            
            Valid.tabelKosong(tabMode);
            
            for(i2=0;i2<jml2;i2++){ 
                tabMode.addRow(new Object[] {
                    pilih2[i2],pemeriksaan2[i2],hasil2[i2],satuan2[i2],
                    nilai_rujukan2[i2],keterangan2[i2],idtemplate2[i2],
                    biaya_item2[i2],bagian_rs2[i2],bhp2[i2],bagian_perujuk2[i2],
                    bagian_dokter2[i2],bagian_laborat2[i2],kso2[i2],menejemen2[i2]
                });
            }  
            
            for(i2=0;i2<tbTarif.getRowCount();i2++){ 
                if(tbTarif.getValueAt(i2,0).toString().equals("true")){
                    tabMode.addRow(new Object[]{false,tbTarif.getValueAt(i2,2).toString(),"","","","","",0,0,0,0,0,0,0,0});
                    if(Jk.getText().equals("L")&&(rbDewasa.isSelected()==true)){
                        pstampil=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_ld,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen "+
                                "from template_laboratorium where kd_jenis_prw=? and Pemeriksaan like ? order by urut");
                        try {
                            pstampil.setString(1,tbTarif.getValueAt(i2,1).toString());
                            pstampil.setString(2,"%"+TCari.getText().trim()+"%");
                            rstampil=pstampil.executeQuery();
                            while(rstampil.next()){
                                tabMode.addRow(new Object[]{
                                    false,"   "+rstampil.getString("Pemeriksaan"),"",
                                         rstampil.getString("satuan"),
                                         rstampil.getString("nilai_rujukan_ld"),"",
                                         rstampil.getString("id_template"),
                                         rstampil.getDouble("biaya_item"),
                                         rstampil.getDouble("bagian_rs"),
                                         rstampil.getDouble("bhp"),
                                         rstampil.getDouble("bagian_perujuk"),
                                         rstampil.getDouble("bagian_dokter"),
                                         rstampil.getDouble("bagian_laborat"),
                                         rstampil.getDouble("kso"),
                                         rstampil.getDouble("menejemen")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rstampil!=null){
                                rstampil.close();
                            }
                            if(pstampil!=null){
                                pstampil.close();
                            }
                        }                        
                    }else if(Jk.getText().equals("L")&&(rbAnak.isSelected()==true)){
                        pstampil2=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_la,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen "+
                                "from template_laboratorium where kd_jenis_prw=? and Pemeriksaan like ? order by urut");
                        try {
                            pstampil2.setString(1,tbTarif.getValueAt(i2,1).toString());                            
                            pstampil2.setString(2,"%"+TCari.getText().trim()+"%");
                            rstampil=pstampil2.executeQuery();
                            while(rstampil.next()){
                                tabMode.addRow(new Object[]{false,"   "+rstampil.getString("Pemeriksaan"),"",
                                         rstampil.getString("satuan"),
                                         rstampil.getString("nilai_rujukan_la"),"",
                                         rstampil.getString("id_template"),
                                         rstampil.getDouble("biaya_item"),
                                         rstampil.getDouble("bagian_rs"),
                                         rstampil.getDouble("bhp"),
                                         rstampil.getDouble("bagian_perujuk"),
                                         rstampil.getDouble("bagian_dokter"),
                                         rstampil.getDouble("bagian_laborat"),
                                         rstampil.getDouble("kso"),
                                         rstampil.getDouble("menejemen")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rstampil!=null){
                                rstampil.close();
                            }
                            if(pstampil2!=null){
                                pstampil2.close();
                            }
                        }                         
                    }else if(Jk.getText().equals("P")&&(rbDewasa.isSelected()==true)){
                        pstampil3=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_pd,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen "+
                                "from template_laboratorium where kd_jenis_prw=? and Pemeriksaan like ? order by urut");
                        try {
                            pstampil3.setString(1,tbTarif.getValueAt(i2,1).toString());
                            pstampil3.setString(2,"%"+TCari.getText().trim()+"%");
                            rstampil=pstampil3.executeQuery();
                            while(rstampil.next()){
                                tabMode.addRow(new Object[]{false,"   "+rstampil.getString("Pemeriksaan"),"",
                                         rstampil.getString("satuan"),
                                         rstampil.getString("nilai_rujukan_pd"),"",
                                         rstampil.getString("id_template"),
                                         rstampil.getDouble("biaya_item"),
                                         rstampil.getDouble("bagian_rs"),
                                         rstampil.getDouble("bhp"),
                                         rstampil.getDouble("bagian_perujuk"),
                                         rstampil.getDouble("bagian_dokter"),
                                         rstampil.getDouble("bagian_laborat"),
                                         rstampil.getDouble("kso"),
                                         rstampil.getDouble("menejemen")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rstampil!=null){
                                rstampil.close();
                            }
                            if(pstampil3!=null){
                                pstampil3.close();
                            }
                        }                         
                    }else if(Jk.getText().equals("P")&&(rbAnak.isSelected()==true)){
                        pstampil4=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_pa,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen "+
                                "from template_laboratorium where kd_jenis_prw=? and Pemeriksaan like ? order by urut");
                        try{
                            pstampil4.setString(1,tbTarif.getValueAt(i2,1).toString());
                            pstampil4.setString(2,"%"+TCari.getText().trim()+"%");
                            rstampil=pstampil4.executeQuery();
                            while(rstampil.next()){
                                tabMode.addRow(new Object[]{false,"   "+rstampil.getString("Pemeriksaan"),"",
                                         rstampil.getString("satuan"),
                                         rstampil.getString("nilai_rujukan_pa"),"",
                                         rstampil.getString("id_template"),
                                         rstampil.getDouble("biaya_item"),
                                         rstampil.getDouble("bagian_rs"),
                                         rstampil.getDouble("bhp"),
                                         rstampil.getDouble("bagian_perujuk"),
                                         rstampil.getDouble("bagian_dokter"),
                                         rstampil.getDouble("bagian_laborat"),
                                         rstampil.getDouble("kso"),
                                         rstampil.getDouble("menejemen")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rstampil!=null){
                                rstampil.close();
                            }
                            if(pstampil4!=null){
                                pstampil4.close();
                            }
                        }                         
                    }                              
                }
            }
        } catch (Exception e) {
            System.out.println("Error Detail : "+e);
        }
        
    }
    
    public void emptTeks() {
        KodePerujuk.setText("");
        NmPerujuk.setText("");
        KdPtg.setText("");
        NmPtg.setText("");
        Pemeriksaan.setText("");
        Pemeriksaan.requestFocus();  
    }
    
    public void onCari(){
        Pemeriksaan.requestFocus(); 
    }

    
    private void isPsien(){
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
    }
    

    public void setNoRm(String norwt,String tanggal,String jam) {        
        try {
            TNoRw.setText(norwt);
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
            Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=? ",Penjab,TNoRw.getText());
            
            isPsien(); 
            psset_tarif=koneksi.prepareStatement("select * from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    cara_bayar_lab=rsset_tarif.getString("cara_bayar_lab");
                }else{
                    cara_bayar_lab="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rsset_tarif!=null){
                    rsset_tarif.close();
                }
                if(psset_tarif!=null){
                    psset_tarif.close();
                }
            }
                
            Tanggal.setText(tanggal);
            Jam.setText(jam);
            Sequel.cariIsi("select kd_dokter from periksa_lab where no_rawat='"+TNoRw.getText()+"' and tgl_periksa='"+tanggal+"' and jam='"+jam+"'",KodePj);
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='"+KodePj.getText()+"'",NmDokterPj);
            Sequel.cariIsi("select nip from periksa_lab where no_rawat='"+TNoRw.getText()+"' and tgl_periksa='"+tanggal+"' and jam='"+jam+"'",KdPtg);
            Sequel.cariIsi("select nama from petugas where nip='"+KdPtg.getText()+"'",NmPtg);
            Sequel.cariIsi("select dokter_perujuk from periksa_lab where no_rawat='"+TNoRw.getText()+"' and tgl_periksa='"+tanggal+"' and jam='"+jam+"'",KodePerujuk);
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=? ",NmPerujuk,KodePerujuk.getText());
            
            pscariperiksa=koneksi.prepareStatement(
                    "select periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya,"+
                    "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                    "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas, "+
                    "periksa_lab.kso,periksa_lab.menejemen from periksa_lab inner join jns_perawatan_lab "+
                    "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                    "and periksa_lab.jam=?");
            try {
                pscariperiksa.setString(1,TNoRw.getText());
                pscariperiksa.setString(2,tanggal);
                pscariperiksa.setString(3,jam);
                rstindakan=pscariperiksa.executeQuery();
                while(rstindakan.next()){                
                    tabMode2.addRow(new Object[]{true,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),rstindakan.getDouble(10)});
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                if(pscariperiksa!=null){
                    pscariperiksa.close();
                }
            }
            
            pscaridetailperiksa=koneksi.prepareStatement(
                    "select detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                    "detail_periksa_lab.bagian_rs,detail_periksa_lab.bhp,detail_periksa_lab.bagian_perujuk,detail_periksa_lab.bagian_dokter,detail_periksa_lab.bagian_laborat,detail_periksa_lab.kso,detail_periksa_lab.menejemen,detail_periksa_lab.keterangan "+
                    "from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=?");
            try {
                pscaridetailperiksa.setString(1,TNoRw.getText());
                pscaridetailperiksa.setString(2,tanggal);
                pscaridetailperiksa.setString(3,jam);
                rstampil=pscaridetailperiksa.executeQuery();
                while(rstampil.next()){
                    tabMode.addRow(new Object[]{true,"   "+rstampil.getString("Pemeriksaan"),rstampil.getString("nilai"),
                         rstampil.getString("satuan"),
                         rstampil.getString("nilai_rujukan"),"",
                         rstampil.getString("id_template"),
                         rstampil.getDouble("biaya_item"),
                         rstampil.getDouble("bagian_rs"),
                         rstampil.getDouble("bhp"),
                         rstampil.getDouble("bagian_perujuk"),
                         rstampil.getDouble("bagian_dokter"),
                         rstampil.getDouble("bagian_laborat"),
                         rstampil.getDouble("kso"),
                         rstampil.getDouble("menejemen")
                    });
                } 
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rstampil!=null){
                    rstampil.close();
                }
                if(pscaridetailperiksa!=null){
                    pscaridetailperiksa.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }         
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getperiksa_lab());
        BtnHapus.setEnabled(akses.getperiksa_lab());
        btnTarif.setEnabled(akses.gettarif_lab());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH,269));
            PanelInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            FormInput.setPreferredSize(new Dimension(WIDTH,20));
            PanelInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void tampiltarif() {          
        try{
            jml=0;
            for(i=0;i<tbTarif.getRowCount();i++){
                if(tbTarif.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            total=null;
            total=new double[jml];
            bagian_rs=null;
            bagian_rs=new double[jml];
            bhp=null;
            bhp=new double[jml];
            tarif_perujuk=null;
            tarif_perujuk=new double[jml];
            tarif_tindakan_dokter=null;
            tarif_tindakan_dokter=new double[jml];
            tarif_tindakan_petugas=null;
            tarif_tindakan_petugas=new double[jml];
            kso=null;
            kso=new double[jml];
            menejemen=null;
            menejemen=new double[jml];

            index=0; 
            for(i=0;i<tbTarif.getRowCount();i++){
                if(tbTarif.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbTarif.getValueAt(i,1).toString();
                    nama[index]=tbTarif.getValueAt(i,2).toString();
                    total[index]=Double.parseDouble(tbTarif.getValueAt(i,3).toString());
                    bagian_rs[index]=Double.parseDouble(tbTarif.getValueAt(i,4).toString());
                    bhp[index]=Double.parseDouble(tbTarif.getValueAt(i,5).toString());
                    tarif_perujuk[index]=Double.parseDouble(tbTarif.getValueAt(i,6).toString());
                    tarif_tindakan_dokter[index]=Double.parseDouble(tbTarif.getValueAt(i,7).toString());
                    tarif_tindakan_petugas[index]=Double.parseDouble(tbTarif.getValueAt(i,8).toString());
                    kso[index]=Double.parseDouble(tbTarif.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(tbTarif.getValueAt(i,10).toString());
                    index++;
                }
            }

            Valid.tabelKosong(tabMode2);
            for(i=0;i<jml;i++){
                tabMode2.addRow(new Object[] {pilih[i],kode[i],nama[i],total[i],bagian_rs[i],bhp[i],tarif_perujuk[i],tarif_tindakan_dokter[i],tarif_tindakan_petugas[i],kso[i],menejemen[i]});
            }       
        
            switch (cara_bayar_lab) {
                case "Yes":
                    pstindakan.setString(1,"%"+Penjab.getText().trim()+"%");
                    pstindakan.setString(2,"%"+Pemeriksaan.getText().trim()+"%");
                    pstindakan.setString(3,"%"+Penjab.getText().trim()+"%");
                    pstindakan.setString(4,"%"+Pemeriksaan.getText().trim()+"%");
                    rstindakan=pstindakan.executeQuery();
                    break;
                case "No":
                    pstindakan2.setString(1,"%"+Pemeriksaan.getText().trim()+"%");                
                    pstindakan2.setString(2,"%"+Pemeriksaan.getText().trim()+"%");
                    rstindakan=pstindakan2.executeQuery();
                    break;
            }
            
            while(rstindakan.next()){                
                tabMode2.addRow(new Object[]{false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),rstindakan.getDouble(10)});
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

}
